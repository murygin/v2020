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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.fieldaccess.DynamicProperties;
import org.springframework.data.neo4j.fieldaccess.DynamicPropertiesContainer;
import org.v2020.data.entity.iso.ScenarioAsset;

/**
 * 
 * Entity equality can be a grey area, and it is debatable whether natural keys
 * or database ids best describe equality, there is the issue of versioning over
 * time, etc. For Spring Data Neo4j we have adopted the convention that
 * database-issued ids are the basis for equality, and that has some
 * consequences:
 * 
 * 1. Before you attach an entity to the database, i.e. before the entity has
 * had its id-field populated, we suggest you rely on object identity for
 * comparisons 2. Once an entity is attached, we suggest you rely solely on the
 * id-field for equality 3. When you attach an entity, its hashcode changes -
 * because you keep equals and hashcode consistent and rely on the database ID,
 * and because Spring Data Neo4j populates the database ID on save
 * 
 * ("Good Relationships: The Spring Data Neo4j Guide Book", version
 * 3.2.1.RELEASE See:
 * http://docs.spring.io/spring-data/data-neo4j/docs/3.2.1.RELEASE
 * /reference/html/#reference_simple-mapping)
 * 
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@NodeEntity
public class Node {
    
    @GraphId
    private Long id;

    private String title;
    
    DynamicProperties properties = new DynamicPropertiesContainer();
    
    @RelatedTo(type="CHILD", direction = Direction.INCOMING)
    private Node parent;
    
    @RelatedToVia(type="link", direction=Direction.BOTH)
    @Fetch
    Set<Edge> edges = new HashSet<Edge>();
    
    public Node() {
        super();
    }
    
    public Node(String title) {
        super();
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void addProperty(String key, Object value) {
        properties.setProperty(key, value);
    }
    
    public Object getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public void addEdge(Node endNode, String type) {
        edges.add(new Edge(this, endNode, type));
    }
    
    public Set<Edge> getEdges() {
        return edges;
    }
    
    public Set<Edge> getEdges(String type) {
        Set<Edge> allEdges = getEdges();
        Set<Edge> edges = new HashSet<Edge>();
        for (Edge edge : allEdges) {
            if(edge.getType().equals(type)) {
                edges.add(edge);
            }
        }
        return edges;
    }
    
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (id == null) {
            return false;
        }
        if (!(other instanceof Node)) {
            return false;
        }
        return id.equals(((Node) other).id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("(").append(id).append(")");    
        return sb.toString();
    }
}
