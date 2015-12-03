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
package org.v2020.data.dao.iso;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.v2020.data.entity.Node;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@Repository
public class RelationshipDao implements IRelationshipDao {

    private static final Logger LOG = LoggerFactory.getLogger(RelationshipDao.class);

    @Autowired
    private NodeRepository nodeRepository;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.v2020.data.dao.iso.IRelationshipDao#createRelationshipBetween(java.
     * lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    public void createRelationship(Long startNodeId, Long endNodeId, String type) {
        Node startNode = nodeRepository.findOne(startNodeId);
        if (startNode == null) {
            LOG.warn("Can not create relation, start node not found, id: " + startNodeId);
            return;
        }
        addEdge(startNode, endNodeId, type);
        nodeRepository.save(startNode, 1);
    }

    @Override
    public void createRelationships(Long startNodeId, List<Long> endNodeIdList, String type) {
        Node startNode = nodeRepository.findOne(startNodeId);
        if (startNode == null) {
            LOG.warn("Can not create relation, start node not found, id: " + startNodeId);
            return;
        }
        for (Long endNodeId : endNodeIdList) {
            addEdge(startNode, endNodeId, type);
        }
        nodeRepository.save(startNode, 1);
    }

    private void addEdge(Node startNode, Long endNodeId, String type) {
        Node endNode = nodeRepository.findOne(endNodeId);
        if (endNode == null) {
            LOG.warn("Can not create relation, end node not found, id: " + endNodeId);
            return;
        }
        startNode.addEdge(endNode, type);
        return;
    }

}
