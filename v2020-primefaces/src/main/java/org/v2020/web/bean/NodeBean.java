package org.v2020.web.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.v2020.data.entity.Node;
import org.v2020.service.crud.INodeService;

@Component
@ManagedBean
@SessionScoped
public class NodeBean {

    private Long id;
    private Node node;
    
    @Autowired
    private INodeService nodeService;
    
    public void load() {
        node = nodeService.getNode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    

    

      
}
