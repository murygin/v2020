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
package org.v2020.data;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@org.springframework.context.annotation.Configuration
@EnableNeo4jRepositories(basePackages = "org.v2020.data")
public class Configuration extends Neo4jConfiguration {

    @Bean
    GraphDatabaseService graphDatabaseService() {
        setBasePackage("org.v2020.data");
        // Connect to embedded neo4j server
        return new GraphDatabaseFactory()
        .newEmbeddedDatabaseBuilder( "v2020-neo4j.db" )
        .loadPropertiesFromFile( "target/classes/neo4j.properties" )
        .newGraphDatabase();
        //return new GraphDatabaseFactory().newEmbeddedDatabase("v2020-neo4j.db");
        // Connect to a external neo4j server via REST api
        //return new SpringRestGraphDatabase("http://localhost:7474/db/data/");
    }
}
