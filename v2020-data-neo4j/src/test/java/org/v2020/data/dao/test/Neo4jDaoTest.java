package org.v2020.data.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.v2020.data.dao.iso.NodeRepository;
import org.v2020.data.entity.Edge;
import org.v2020.data.entity.Node;
import org.v2020.data.entity.iso.Asset;
import org.v2020.data.entity.iso.AssetGroup;
import org.v2020.data.entity.iso.Control;
import org.v2020.data.entity.iso.Organization;
import org.v2020.data.entity.iso.Scenario;
import org.v2020.data.entity.iso.ScenarioAsset;
import org.v2020.data.entity.iso.ScenarioGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataNeo4jTestConfiguration.class)
@IntegrationTest
@Transactional
public class Neo4jDaoTest {

    private static final String RISK_CATALOGUE_TITLE_EN = "verinice Risk Catalogue EN";
    private static final int MAX_NUMBER_OF_GROUPS = 10;
    private static final int MAX_NUMBER_OF_ELEMENTS = 20;

    private static final boolean DELETE_DATA_AFTER_EXECUTING = false;

    @Autowired
    NodeRepository nodeRepository;

    @Before
    public void setUp() throws IOException {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSaveProperty() {
        String title = UUID.randomUUID().toString();
        Integer maturity = Integer.valueOf(3);
        Control control = new Control(title);
        control.addProperty(Control.PROP_MATURITY, maturity);
        saveNode(control);
        control = nodeRepository.findByTitle(title);
        checkNode(control, title);
        assertEquals(maturity, control.getProperty(Control.PROP_MATURITY));
        deleteNodeIfConfigured(control);
    }

    @Test
    @Ignore
    public void testSaveDate() {
        String title = UUID.randomUUID().toString();
        Date date = Calendar.getInstance().getTime();
        Control control = new Control(title);
        control.addProperty(Control.PROP_MATURITY_DUEDATE, date);
        saveNode(control);
        control = nodeRepository.findByTitle(title);
        checkNode(control, title);
        assertEquals(date, control.getProperty(Control.PROP_MATURITY_DUEDATE));
        deleteNodeIfConfigured(control);
    }

    @Test
    public void testCreateEdge() {
        String titleControl = UUID.randomUUID().toString();
        Control control = new Control(titleControl);
        saveNode(control);

        String titleAsset = UUID.randomUUID().toString();
        Asset asset = new Asset(titleAsset);
        asset.addEdge(control, Asset.EDGE_ASSET_CONTROL);
        saveNode(asset);

        checkEdgesOfControl(titleAsset, titleControl);
        checkEdgesOfAsset(titleControl, titleAsset);
    }

    private void checkEdgesOfAsset(String titleControl, String titleAsset) {
        Asset asset = nodeRepository.findByTitle(titleAsset);
        checkNode(asset, titleAsset);

        Control control = nodeRepository.findByTitle(titleControl);
        checkNode(asset, titleAsset);

        Set<Edge> assetControlEdges = asset.getEdges(Asset.EDGE_ASSET_CONTROL);
        assertNotNull(assetControlEdges);
        assertEquals("Control does not have 1 edge of type: " + Asset.EDGE_ASSET_CONTROL, 1, assetControlEdges.size());
        Edge assetControlEdge = assetControlEdges.iterator().next();

        Node edgeStartNode = assetControlEdge.getStartNode();
        assertNotNull(edgeStartNode);
        assertTrue(edgeStartNode instanceof Asset);
        assertTrue(edgeStartNode.equals(asset));

        Node edgeEndNode = assetControlEdge.getEndNode();
        assertNotNull(edgeEndNode);
        assertTrue(edgeEndNode instanceof Control);
        assertTrue(edgeEndNode.equals(control));
    }

    private Control checkEdgesOfControl(String titleAsset, String titleControl) {
        Control control = nodeRepository.findByTitle(titleControl);
        checkNode(control, titleControl);

        Asset asset = nodeRepository.findByTitle(titleAsset);
        checkNode(asset, titleAsset);

        Set<Edge> assetControlEdges = control.getEdges(Asset.EDGE_ASSET_CONTROL);
        assertNotNull(assetControlEdges);
        assertEquals("Control does not have 1 edge of type: " + Asset.EDGE_ASSET_CONTROL, 1, assetControlEdges.size());
        Edge assetControlEdge = assetControlEdges.iterator().next();

        Node edgeStartNode = assetControlEdge.getStartNode();
        assertNotNull(edgeStartNode);
        assertTrue(edgeStartNode instanceof Asset);
        assertTrue(edgeStartNode.equals(asset));

        Node edgeEndNode = assetControlEdge.getEndNode();
        assertNotNull(edgeEndNode);
        assertTrue(edgeEndNode instanceof Control);
        assertTrue(edgeEndNode.equals(control));
        return control;
    }

    @Test
    public void testFindByTitle() {
        String title = UUID.randomUUID().toString();
        Organization organization = new Organization(title);
        organization = saveOrganisation(organization);
        Organization foundOrganization = nodeRepository.findByTitle(title);
        checkNode(foundOrganization, title);
        deleteNodeIfConfigured(organization);
    }

    @Test
    public void testSaveParent() {
        String title = UUID.randomUUID().toString();
        Organization organization = new Organization(title);
        organization = saveOrganisation(organization);
        AssetGroup assets = new AssetGroup("Assets");
        assets.setParent(organization);
        saveNode(assets);
        Organization foundOrganization = nodeRepository.findByTitle(title);
        checkNode(foundOrganization, title);
        Set<Node> childNotes = foundOrganization.getChildNotes();
        assertNotNull("Organization has no child notes", childNotes);
        assertFalse("Organization has no child notes", childNotes.isEmpty());
        assertTrue("Organization has more than one child", childNotes.size() == 1);
        Node childNote = childNotes.iterator().next();
        assertEquals("Child note of organization is not asset group", assets, childNote);
        deleteNodeIfConfigured(organization);
        deleteNodeIfConfigured(assets);
    }

    @Test
    public void testSaveChildren() {
        String title = UUID.randomUUID().toString();
        Organization organization = new Organization(title);
        String titleAssetGroup = UUID.randomUUID().toString();
        AssetGroup assets = new AssetGroup(titleAssetGroup);
        organization.addChildNote(assets);
        organization = saveOrganisation(organization);

        AssetGroup foundAssetGroup = nodeRepository.findByTitle(titleAssetGroup);
        checkNode(foundAssetGroup, titleAssetGroup);
        Node parent = foundAssetGroup.getParent();
        assertNotNull("Asset group has no parent", parent);
        assertEquals("Parent of asset group is not organization", organization, parent);
        deleteNodeIfConfigured(organization);
        deleteNodeIfConfigured(assets);
    }

    @Test
    // @Transactional
    public void testRiskCatalogNode() {
        Organization riskCatalogue = new Organization(RISK_CATALOGUE_TITLE_EN);

        insertRandomData(riskCatalogue);

        AssetGroup assets = new AssetGroup("Assets");
        riskCatalogue.addChildNote(assets);
        AssetGroup hardware = new AssetGroup("Hardware");
        assets.addChildNote(hardware);
        AssetGroup dataMediumPassive = new AssetGroup("Data medium (passive)");
        hardware.addChildNote(dataMediumPassive);

        Asset cdRom = new Asset("CD Rom");
        hardware.addChildNote(cdRom);
        Asset floppyDisk = new Asset("Floppy disk");
        hardware.addChildNote(floppyDisk);

        ScenarioGroup scenarios = new ScenarioGroup("Scenarios");
        riskCatalogue.addChildNote(scenarios);
        ScenarioGroup basicScenarios = new ScenarioGroup("Basic Scenarios");
        scenarios.addChildNote(basicScenarios);
        ScenarioGroup theftOfMedia = new ScenarioGroup("Theft of media or documents");
        basicScenarios.addChildNote(theftOfMedia);

        Scenario theftOfInformation = new Scenario("Theft of information or documents");
        theftOfMedia.addChildNote(theftOfInformation);

        nodeRepository.save(riskCatalogue);

        theftOfInformation.addScenarioAssets(new ScenarioAsset(theftOfInformation, cdRom));
        theftOfInformation.addScenarioAssets(new ScenarioAsset(theftOfInformation, floppyDisk));

        nodeRepository.save(theftOfInformation);

        deleteNodeIfConfigured(riskCatalogue);
    }

    @Test
    public void testFindByClass() {
        int n = 10;
        List<Organization> orgCreateList = new LinkedList<Organization>();
        for (int i = 0; i < n; i++) {
            orgCreateList.add(createOrganization());

        }
        List<Node> orgList = nodeRepository.findByClass(Organization.class.getName());
        assertNotNull("Organization list is null", orgList);
        assertTrue("Organization list size (" + orgList.size() + ") is not: " + n, orgList.size() >= n);
        for (Node org : orgCreateList) {
            assertTrue("Org not found: " + org.getTitle(), orgList.contains(org));
            deleteNodeIfConfigured(org);
        }

    }

    @Test
    public void testSave() {
        int n = 10;
        List<Asset> nodeCreateList = new LinkedList<Asset>();
        for (int i = 0; i < n; i++) {
            nodeCreateList.add(createAsset());
        }

        for (Asset asset : nodeCreateList) {
            Node assetDb = nodeRepository.findOne(asset.getId());
            assertNotNull("Node with id " + asset.getId() + " not found in DB.", assetDb);
            assertNotNull("Node in DB is not equal to created node with id: " + asset.getId(), assetDb.equals(asset));
        }

        for (Node node : nodeCreateList) {
            deleteNodeIfConfigured(node);
        }

    }

    private Asset createAsset() {
        String title = UUID.randomUUID().toString();
        Asset node = new Asset(title);
        return saveAsset(node);
    }

    private Organization createOrganization() {
        String title = UUID.randomUUID().toString();
        Organization organization = new Organization(title);
        return saveOrganisation(organization);
    }

    // @Transactional
    private void saveNode(Node control) {
        nodeRepository.save(control);
    }

    // @Transactional
    private Organization saveOrganisation(Organization node) {
        return nodeRepository.save(node);
    }

    private Asset saveAsset(Asset node) {
        return nodeRepository.save(node);
    }

    private void deleteNodeIfConfigured(Node node) {
        if (DELETE_DATA_AFTER_EXECUTING) {
            deleteNode(node);
        }
    }

    // @Transactional
    private void deleteNode(Node node) {
        String title = node.getTitle();
        nodeRepository.delete(node);
        Organization deletedOrganization = nodeRepository.findByTitle(title);
        assertNull(deletedOrganization);
    }

    private <N extends Node> void checkNode(N node, String title) {
        assertNotNull(node);
        assertEquals(title, node.getTitle());
    }

    private void insertRandomData(Organization riskCatalogue) {
        for (int i = 0; i < getRandom(MAX_NUMBER_OF_GROUPS); i++) {
            AssetGroup assetGroup = new AssetGroup("Asset Group " + i);
            for (int j = 0; j < getRandom(MAX_NUMBER_OF_ELEMENTS); j++) {
                Asset asset = new Asset("Asset " + j);
                assetGroup.addChildNote(asset);
            }
            riskCatalogue.addChildNote(assetGroup);
        }
    }

    private long getRandom(int max) {
        return Math.round(Math.random() * max);
    }

}
