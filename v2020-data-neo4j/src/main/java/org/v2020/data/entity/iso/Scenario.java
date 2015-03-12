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

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.v2020.data.entity.ExternalNode;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
public class Scenario extends ExternalNode {
    
    public static final String TYPE = "scenario";
    
    public Scenario() {
        super();
    }

    public Scenario(String title) {
        super(title);
    }
    
    @RelatedToVia
    Set<ScenarioAsset> scenarioAsset;
    
    public Set<ScenarioAsset> getScenarioAssets() {
        if (scenarioAsset == null) {
            scenarioAsset = new HashSet<ScenarioAsset>();
        }
        return scenarioAsset;
    }
    
    public void addScenarioAssets(ScenarioAsset edge) {     
        getScenarioAssets().add(edge);
    }
     
    public boolean hasAScenarioAssets() {
        return !(getScenarioAssets().isEmpty());
    }

}
