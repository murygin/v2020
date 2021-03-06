/*******************************************************************************
 * Copyright (c) 2014 Daniel Murygin.
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

import org.neo4j.ogm.annotation.GraphId;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
public abstract class AbstractEdge<S extends Node, E extends Node> {

    @GraphId
    private Long id;

    private String comment;

    public AbstractEdge() {
        super();
    }

    public AbstractEdge(String comment) {
        super();
        this.comment = comment;
    }

    public abstract S getStartNode();

    public abstract E getEndNode();

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
