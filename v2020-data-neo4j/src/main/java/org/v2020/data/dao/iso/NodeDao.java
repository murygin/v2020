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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;
import org.v2020.data.entity.Node;

/**
 * The @Repository annotation is a marker for any class that fulfills 
 * the role or stereotype (also known as Data Access Object or DAO) of a repository. 
 * 
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@Repository
public class NodeDao implements INodeDao {
    
    @Autowired 
    private Neo4jOperations neo4jOperations;
    
    @Autowired
    private NodeRepository nodeRepository;
    
    @Autowired
    GraphDatabase graphDatabase;
    
    /* (non-Javadoc)
     * @see org.v2020.data.dao.iso.INodeDao#save(org.v2020.data.entity.Node)
     */
    @Override
    public <N extends Node> N save(N node) {
        return nodeRepository.save(node);
  
    }

    /* (non-Javadoc)
     * @see org.v2020.data.dao.iso.INodeDao#get(java.lang.Long)
     */
    @Override
    public Node get(Long id) {
        return nodeRepository.findOne(id);
    }  

}
