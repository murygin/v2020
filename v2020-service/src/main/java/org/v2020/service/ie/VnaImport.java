/*******************************************************************************
 * Copyright (c) 2015 Daniel Murygin.
 *
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,    
 * but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Daniel Murygin <dm[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package org.v2020.service.ie;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.v2020.data.entity.Node;

import de.sernet.sync.data.SyncLink;
import de.sernet.sync.data.SyncObject;
import de.sernet.sync.mapping.SyncMapping.MapObjectType;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@Service("vnaImport")
public class VnaImport implements IVnaImport {

    private static final Logger LOG = LoggerFactory.getLogger(VnaImport.class);

    private static final int DEFAULT_NUMBER_OF_THREADS = 8;
    private static final int SHUTDOWN_TIMEOUT_IN_SECONDS = 60;

    private int numberOfThreads = DEFAULT_NUMBER_OF_THREADS;
    
    private ExecutorService taskExecutor;
    private CompletionService<ObjectImportContext> objectImportCompletionService;
    private CompletionService<LinkImportContext> linkImportCompletionService;
    private ImportContext importContext;
    private int number = 0;
    
    @Autowired
    private ObjectFactory<ObjectImportThread> objectImportThreadFactory;
    
    @Autowired
    private ObjectFactory<LinkImportThread> linkImportThreadFactory;
    
    /**
     * (non-Javadoc)
     * @see org.v2020.service.ie.IVnaImport#importVna(byte[])
     */
    @Override
    public void importVna(byte[] vnaFileData) {
        taskExecutor = createExecutor();
        objectImportCompletionService = new ExecutorCompletionService<ObjectImportContext>(taskExecutor);
        linkImportCompletionService = new ExecutorCompletionService<LinkImportContext>(taskExecutor);
        importContext = new ImportContext();
        try {
            Vna vna = new Vna(vnaFileData);
            List<SyncObject> syncObjectList = getSyncObjectList(vna);
            List<MapObjectType> mapObjectTypeList = getMapObjectTypeList(vna);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Starting import of objects...");
            }
            importObjectList(null, syncObjectList, mapObjectTypeList);
            if (LOG.isDebugEnabled()) {
                LOG.debug(number + " objects imported.");
            }
            List<SyncLink> syncLinkList = getSyncLinkList(vna);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Number of links: " + syncLinkList.size() + ", starting import...");
            }
            importLinkList( syncLinkList);
        } catch (Exception e) {
            LOG.error("Error while importing VNA.", e);           
        } finally {
            shutdownAndAwaitTermination(taskExecutor);
        }
    }

    private void importObjectList(Node parent, List<SyncObject> syncObjectList, List<MapObjectType> mapObjectTypeList) throws InterruptedException, ExecutionException {
        if (syncObjectList != null) {
            for (SyncObject syncObject : syncObjectList) {
                ObjectImportThread importThread = objectImportThreadFactory.getObject();
                importThread.setContext(new ObjectImportContext(parent, syncObject, mapObjectTypeList));
                objectImportCompletionService.submit(importThread);        
            }
            waitForObjectResults(syncObjectList.size());
        }
    }

    private void waitForObjectResults(int n) throws InterruptedException, ExecutionException {
        for (int i = 0; i < n; ++i) {
            ObjectImportContext objectContext = objectImportCompletionService.take().get();
            importContext.addObject(objectContext);
            if (objectContext != null) {
                importObjectList(objectContext.getNode(), objectContext.getSyncObject().getChildren(), objectContext.getMapObjectTypeList());
            }
        }
        number+=n;
    }
    
    private void importLinkList(List<SyncLink> syncLinkList) throws InterruptedException, ExecutionException {
        if (syncLinkList != null) {
            for (SyncLink syncLink : syncLinkList) {
                LinkImportThread importThread = linkImportThreadFactory.getObject();
                importThread.setContext(createImportContext(syncLink));
                linkImportCompletionService.submit(importThread);        
            }
            waitForLinkResults(syncLinkList.size());
        }
    }

    private void waitForLinkResults(int n) throws InterruptedException, ExecutionException {
        for (int i = 0; i < n; ++i) {
            linkImportCompletionService.take().get();          
        }
    }
    
    private LinkImportContext createImportContext(SyncLink syncLink) {
        Long startId = importContext.getDbId(syncLink.getDependant());
        Long endId = importContext.getDbId(syncLink.getDependency());
        LinkImportContext context = new LinkImportContext(startId, endId, syncLink.getRelationId());
        context.setComment(syncLink.getComment());
        return context;
    }

    private List<SyncObject> getSyncObjectList(Vna vna) {
        if (vna.getXml() != null && vna.getXml().getSyncData() != null && vna.getXml().getSyncData().getSyncObject() != null) {
            return vna.getXml().getSyncData().getSyncObject();
        } else {
            return Collections.emptyList();
        }
    }
    
    private List<SyncLink> getSyncLinkList(Vna vna) {
        if (vna.getXml() != null && vna.getXml().getSyncData() != null && vna.getXml().getSyncData().getSyncLink() != null) {
            return vna.getXml().getSyncData().getSyncLink();
        } else {
            return Collections.emptyList();
        }
    }
    
    private List<MapObjectType> getMapObjectTypeList(Vna vna) {
        if (vna.getXml() != null && vna.getXml().getSyncMapping() != null && vna.getXml().getSyncMapping().getMapObjectType() != null) {
            return vna.getXml().getSyncMapping().getMapObjectType();
        } else {
            return Collections.emptyList();
        }
    }

    private ExecutorService createExecutor() {
        if (LOG.isInfoEnabled()) {
            LOG.info("Number of threads: " + getNumberOfThreads());
        }
        return Executors.newFixedThreadPool(getNumberOfThreads());
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(getShutdownTimeoutInSeconds(), TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(getShutdownTimeoutInSeconds(), TimeUnit.SECONDS))
                    LOG.error("Thread pool did not terminate", pool);
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
    
    private int getShutdownTimeoutInSeconds() {
        return SHUTDOWN_TIMEOUT_IN_SECONDS;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

}
