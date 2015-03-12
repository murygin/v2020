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
package org.v2020.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.v2020.service.ie.LinkImportThread;
import org.v2020.service.ie.ObjectImportThread;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@Configuration
@EnableTransactionManagement
public class ServiceConfig {

    @Bean
    @Scope(value = "prototype")
    public ObjectImportThread createObjectImportThread() {
       return new ObjectImportThread();
    }

    
    @Bean
    @Scope(value = "prototype")
    public LinkImportThread createLinkImportThread() {
       return new LinkImportThread();
    }
}
