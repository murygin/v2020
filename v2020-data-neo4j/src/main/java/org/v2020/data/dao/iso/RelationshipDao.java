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

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@Repository
public class RelationshipDao implements IRelationshipDao {

    @Autowired 
    private Neo4jOperations neo4jOperations;
    
    /* (non-Javadoc)
     * @see org.v2020.data.dao.iso.IRelationshipDao#createRelationshipBetween(java.lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    @Transactional
    public Relationship createRelationship(Long startNodeId, Long endNodeId, String type) {
        Node startNode = neo4jOperations.getNode(startNodeId);
        Node endNode = neo4jOperations.getNode(endNodeId);
        return neo4jOperations.createRelationshipBetween(startNode, endNode, type, null);
    }

}
