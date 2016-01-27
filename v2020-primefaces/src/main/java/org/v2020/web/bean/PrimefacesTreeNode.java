/*******************************************************************************
 * Copyright (c) 2016 <Vorname> <Nachname>.
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
 *     <FirstName> <LastName> <Email> - initial API and implementation
 ******************************************************************************/
package org.v2020.web.bean;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * @author dm
 *
 */
public class PrimefacesTreeNode extends DefaultTreeNode {

    private Long nodeId;
    
    public PrimefacesTreeNode() {
        super();
    }

    public PrimefacesTreeNode(Object data, TreeNode parent) {
        super(data, parent);
    }

    public PrimefacesTreeNode(Object data) {
        super(data);
    }

    public PrimefacesTreeNode(String type, Object data, TreeNode parent) {
        super(type, data, parent);
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

}
