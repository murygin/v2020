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
package org.v2020.data.entity.iso;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.v2020.data.entity.AbstractEdge;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@RelationshipEntity(type = "AFFECTS")
public class ScenarioAsset extends AbstractEdge<Scenario, Asset> {

    @StartNode
    private Scenario scenario;

    @EndNode
    private Asset asset;

    public ScenarioAsset() {
        super();
    }

    public ScenarioAsset(Scenario scenario, Asset asset) {
        super();
        this.scenario = scenario;
        this.asset = asset;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.v2020.data.entity.Edge#getStartNode()
     */
    @Override
    public Scenario getStartNode() {
        return scenario;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.v2020.data.entity.Edge#getEndNode()
     */
    @Override
    public Asset getEndNode() {
        return asset;
    }

}
