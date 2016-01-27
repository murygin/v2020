package org.v2020.web.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.v2020.data.entity.InternalNode;
import org.v2020.data.entity.Node;
import org.v2020.service.crud.INodeService;

@Component
@ManagedBean
@SessionScoped
public class TreeBean {

    @Autowired
    private INodeService nodeService;
    
    private TreeNode root;

 
    public void onNodeExpand(NodeExpandEvent event) {  
        PrimefacesTreeNode parent = (PrimefacesTreeNode) event.getTreeNode();
        if (parent.getChildCount() == 1 && parent.getChildren().get(0).getData().toString().equals("DUMMY")) {
            parent.getChildren().remove(0);
        } 
        Node graphNode = nodeService.getNode(parent.getNodeId());
        if(graphNode instanceof InternalNode) {
            createChildNodes(parent, ((InternalNode)graphNode).getChildNotes());
        }      
   }
    
    private TreeNode createRoot() {
        root = new DefaultTreeNode("Root", null); 
        List<Node> organizations = nodeService.getOrganizations();
        createChildNodes(root, organizations);
        return root;
    }
    
    private void createChildNodes(TreeNode parent, Set<Node> childs) {
        createChildNodes(parent, new LinkedList<>(childs));
    }

    private void createChildNodes(TreeNode parent, List<Node> childs) {
        for (Node node : childs) {
           createTreeNode(parent, node); 
        }
    }
    
    private void createTreeNode(TreeNode parent, Node node) {
        PrimefacesTreeNode treeNode = new PrimefacesTreeNode(node.getTitle(), parent); 
        treeNode.setNodeId(node.getId());
        // Create Dummy node, just to make the parent node expandable
        new DefaultTreeNode("DUMMY", treeNode);
    }
    
    public TreeNode getRoot() {
        if(root==null) {
            root = createRoot();
        }
        return root;
    }
    public void setRoot(TreeNode root) {
        this.root = root;
    }
   
}
