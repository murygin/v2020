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
package org.v2020.service.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.v2020.data.dao.iso.INodeDao;
import org.v2020.data.dao.iso.IRelationshipDao;
import org.v2020.data.entity.Node;
import org.v2020.data.entity.iso.Asset;
import org.v2020.data.entity.iso.Organization;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@Service("nodeService")
public class NodeService implements INodeService {

    @Autowired
    private INodeDao nodeDao;
    
    @Autowired
    private IRelationshipDao relationshipDao;
    
    /* (non-Javadoc)
     * @see org.v2020.service.crud.INodeService#create(org.v2020.data.entity.Node)
     */
    @Override
    public <N extends Node> N create(N node) {
        return nodeDao.save(node);  
    }

    /* (non-Javadoc)
     * @see org.v2020.service.crud.INodeService#createRelationship(java.lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    public void createRelationship(Long startNodeId, Long endNodeId, String type) {
        relationshipDao.createRelationship(startNodeId, endNodeId, type);     
    }

    /* (non-Javadoc)
     * @see org.v2020.service.crud.INodeService#getNode(java.lang.Long)
     */
    @Override
    public <N extends Node> N getNode(Long id) {
        return nodeDao.get(id);
    }

    /* (non-Javadoc)
     * @see org.v2020.service.crud.INodeService#getOrganizations()
     */
    @Override
    public List<Organization> getOrganizations() {       
        return nodeDao.findByClass(Organization.class.getName());
    }
    
    /* (non-Javadoc)
     * @see org.v2020.service.crud.INodeService#getOrganizations()
     */
    @Override
    public List<Asset> getAssets() {       
        return nodeDao.findByClass(Asset.class.getName());
    }


}
