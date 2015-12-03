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
package org.v2020.data.entity;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@RelationshipEntity(type = "link")
public class Edge extends AbstractEdge<Node, Node> {

    @StartNode
    private Node startNode;

    @EndNode
    private Node endNode;

    private String type;

    public Edge(Node startNode, Node endNode, String type) {
        super();
        this.startNode = startNode;
        this.endNode = endNode;
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.v2020.data.entity.AbstractEdge#getStartNode()
     */
    @Override
    public Node getStartNode() {
        return startNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.v2020.data.entity.AbstractEdge#getEndNode()
     */
    @Override
    public Node getEndNode() {
        return endNode;
    }

    public String getType() {
        return type;
    }

}
