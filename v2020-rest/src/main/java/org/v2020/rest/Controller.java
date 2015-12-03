package org.v2020.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.v2020.data.entity.Node;
import org.v2020.service.crud.INodeService;

/**
 * REST web service for v2020 service {@link INodeService}.
 * 
 * /archive/node/{id} Get a node GET id: The Id of a node
 * 
 * All service calls are delegated to instances of {@link INodeService}.
 * 
 * @author Daniel Murygin <daniel.murygin[at]gmail[dot]com>
 */
@RestController
@RequestMapping(value = "/v2020")
public class Controller {

    @Autowired
    INodeService nodeService;

    /**
     * Returns the node with the given id.
     * 
     * Url: /node/{id} [GET]
     * 
     * @param id
     *            The id of a node
     * @return The node
     */
    @RequestMapping(value = "/node/{id}", method = RequestMethod.GET)
    public <N extends Node> N getNode(@PathVariable Long id) {
        return getNodeService().getNode(id);
    }

    /**
     * Url: /organizations [GET]
     * 
     * @return All organizations
     */
    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    public List<Node> getOrganizations() {
        return getNodeService().getOrganizations();
    }

    /**
     * Url: /assets [GET]
     * 
     * @return All assets
     */
    @RequestMapping(value = "/assets", method = RequestMethod.GET)
    public List<Node> getAssets() {
        return getNodeService().getAssets();
    }

    public INodeService getNodeService() {
        return nodeService;
    }

    public void setNodeService(INodeService archiveService) {
        this.nodeService = archiveService;
    }

}
