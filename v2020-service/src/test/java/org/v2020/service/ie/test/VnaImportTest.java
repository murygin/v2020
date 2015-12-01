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
package org.v2020.service.ie.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.v2020.data.DataNeo4jTestConfiguration;
import org.v2020.service.ie.IVnaImport;
import org.v2020.service.ie.Vna;
import org.v2020.util.io.FileSystem;
import org.v2020.util.time.TimeFormatter;

import de.sernet.sync.data.SyncData;
import de.sernet.sync.data.SyncObject;
import de.sernet.sync.sync.SyncRequest;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataNeo4jTestConfiguration.class)
@IntegrationTest
public class VnaImportTest {

    private static final Logger LOG = LoggerFactory.getLogger(VnaImportTest.class);
    
    public static final String RISK_CATALOG_FILE_NAME = "BusinessImpactInheritenceTest.vna";
    
    @Autowired
    IVnaImport vnaImport;
    
    @Test
    public void testVna() throws Exception {            
        byte[] vnaFileData = FileSystem.readByteArrayFromClasspath(RISK_CATALOG_FILE_NAME);
        assertNotNull("File data is null, file name: " + RISK_CATALOG_FILE_NAME, vnaFileData);
        Vna vna = new Vna(vnaFileData);
        byte[] xmlFiledata = vna.getXmlFileData();      
        assertNotNull("Xml file data is null.", xmlFiledata);
        SyncRequest syncRequest = vna.getXml();
        assertNotNull("SyncRequest is null.", syncRequest);
        SyncData syncData = syncRequest.getSyncData();
        assertNotNull("SyncData is null.", syncData);    
        List<SyncObject>  syncObjects = syncData.getSyncObject();
        assertNotNull("SyncObject list is null.", syncObjects);
        assertFalse("SyncObject list is empty.", syncObjects.isEmpty());
        SyncObject syncObject = syncObjects.get(0);
        assertNotNull("SyncObject is null.", syncObject);
        vna.clear();
        assertFalse("Temp folder still exists: " + vna.getTempFileName(), Files.exists(Paths.get(vna.getTempFileName()), LinkOption.NOFOLLOW_LINKS));
    }
    
    @Test
    public void testVnaImport() throws Exception {
        long start = System.currentTimeMillis();
        byte[] vnaFileData = FileSystem.readByteArrayFromClasspath(RISK_CATALOG_FILE_NAME);
        assertNotNull("File data is null, file name: " + RISK_CATALOG_FILE_NAME, vnaFileData);
        vnaImport.setNumberOfThreads(1);
        vnaImport.importVna(vnaFileData);
        if (LOG.isInfoEnabled()) {
            long ms = System.currentTimeMillis() - start;
            LOG.info("Runtime: " + TimeFormatter.getHumanRedableTime(ms));
        }
    }

}
