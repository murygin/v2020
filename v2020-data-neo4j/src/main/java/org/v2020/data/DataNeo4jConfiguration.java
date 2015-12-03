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

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "org.v2020.data")
@EnableTransactionManagement
public class DataNeo4jConfiguration extends Neo4jConfiguration {

    @Bean
    @Override
    public Neo4jServer neo4jServer() {
        return new RemoteServer("http://localhost:7474", "neo4j", "5erne1");
    }

    @Bean
    @Override
    public SessionFactory getSessionFactory() {
        // with domain entity base package(s)
        return new SessionFactory("org.v2020.data.entity");
    }

}