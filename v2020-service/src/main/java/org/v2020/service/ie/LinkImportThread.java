package org.v2020.service.ie;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.v2020.service.crud.INodeService;

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

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
public class LinkImportThread implements Callable<LinkImportContext> {

    private static final Logger LOG = LoggerFactory.getLogger(LinkImportThread.class);
    
    private LinkImportContext context;
    
    @Autowired
    private INodeService nodeService;
    
    public LinkImportThread() {
        super();
    }

    public LinkImportThread(LinkImportContext context) {
        super();
        this.context = context;
    }

    /* (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public LinkImportContext call() throws Exception {
        try {
            importLink();
        } catch(Exception e) {
            LOG.error("Error while importing link.", e);
        }
        return context;
    }

    private void importLink() {
        nodeService.createRelationship(context.getStartId(), context.getEndId(), context.getType());  
        if (LOG.isDebugEnabled()) {
            LOG.debug("Link imported, start id: " + context.getStartId() + ", end id: " + context.getEndId());
        }
    }

    public void setContext(LinkImportContext context) {
        this.context = context;
    }

}
