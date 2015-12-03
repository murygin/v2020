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
package org.v2020.service.ie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.v2020.data.entity.Node;
import org.v2020.data.entity.iso.Asset;
import org.v2020.data.entity.iso.AssetGroup;
import org.v2020.data.entity.iso.Audit;
import org.v2020.data.entity.iso.AuditGroup;
import org.v2020.data.entity.iso.Control;
import org.v2020.data.entity.iso.ControlGroup;
import org.v2020.data.entity.iso.Document;
import org.v2020.data.entity.iso.DocumentGroup;
import org.v2020.data.entity.iso.Evidence;
import org.v2020.data.entity.iso.EvidenceGroup;
import org.v2020.data.entity.iso.ExceptionGroup;
import org.v2020.data.entity.iso.Finding;
import org.v2020.data.entity.iso.FindingGroup;
import org.v2020.data.entity.iso.Incident;
import org.v2020.data.entity.iso.IncidentGroup;
import org.v2020.data.entity.iso.Interview;
import org.v2020.data.entity.iso.InterviewGroup;
import org.v2020.data.entity.iso.Organization;
import org.v2020.data.entity.iso.Person;
import org.v2020.data.entity.iso.PersonGroup;
import org.v2020.data.entity.iso.ProcessGroup;
import org.v2020.data.entity.iso.Record;
import org.v2020.data.entity.iso.RecordGroup;
import org.v2020.data.entity.iso.Requirement;
import org.v2020.data.entity.iso.RequirementGroup;
import org.v2020.data.entity.iso.Response;
import org.v2020.data.entity.iso.ResponseGroup;
import org.v2020.data.entity.iso.SamtTopic;
import org.v2020.data.entity.iso.Scenario;
import org.v2020.data.entity.iso.ScenarioGroup;
import org.v2020.data.entity.iso.Threat;
import org.v2020.data.entity.iso.ThreatGroup;
import org.v2020.data.entity.iso.Vulnerability;
import org.v2020.data.entity.iso.VulnerabilityGroup;

/**
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
public final class NodeFactory {

    private static final Logger LOG = LoggerFactory.getLogger(NodeFactory.class);

    public static final String ASSET_GROUP_TYPE = "assetgroup";
    public static final String ASSET_TYPE = "asset";
    public static final String AUDIT_GROUP_TYPE = "auditgroup";
    public static final String AUDIT_TYPE = "audit";
    public static final String CONTROL_GROUP_TYPE = "controlgroup";
    public static final String CONTROL_TYPE = "control";
    public static final String DOCUMENT_GROUP_TYPE = "document_group";
    public static final String DOCUMENT_TYPE = "document";
    public static final String EVIDENCE_GROUP_TYPE = "evidence_group";
    public static final String EVIDENCE_TYPE = "evidence";
    public static final String EXCEPTION_GROUP_TYPE = "exceptiongroup";
    public static final String EXCEPTION_TYPE = "exception";
    public static final String FINDING_GROUP_TYPE = "finding_group";
    public static final String FINDING_TYPE = "finding";
    public static final String INCIDENT_GROUP_TYPE = "incident_group";
    public static final String INCIDENT_TYPE = "incident";
    public static final String INTERVIEW_GROUP_TYPE = "interview_group";
    public static final String INTERVIEW_TYPE = "interview";
    public static final String ORGANIZATION_TYPE = "org";
    public static final String PERSON_GROUP_TYPE = "persongroup";
    public static final String PERSON_TYPE = "person";
    public static final String PROCESS_GROUP_TYPE = "process_group";
    public static final String PROCESS_TYPE = "process";
    public static final String RECORD_GROUP_TYPE = "record_group";
    public static final String RECORD_TYPE = "record";
    public static final String REQUIREMENT_GROUP_TYPE = "requirementgroup";
    public static final String REQUIREMENT_TYPE = "requirement";
    public static final String RESPONSE_GROUP_TYPE = "response_group";
    public static final String RESPONSE_TYPE = "response";
    public static final String SAMT_TOPIC_TYPE = "samt_topic";
    public static final String SCENARIO_GROUP_TYPE = "incident_scenario_group";
    public static final String SCENARIO_TYPE = "incident_scenario";
    public static final String THREAT_GROUP_TYPE = "threat_group";
    public static final String THREAT_TYPE = "threat";
    public static final String VULNERABILITY_GROUP_TYPE = "vulnerability_group";
    public static final String VULNERABILITY_TYPE = "vulnerability";

    private NodeFactory() {
        // do not instantiate this class
    }

    public static Node createNode(String type) {
        Node node;
        switch (type) {
        case ORGANIZATION_TYPE:
            node = new Organization();
            break;
        case ASSET_TYPE:
            node = new Asset();
            break;
        case ASSET_GROUP_TYPE:
            node = new AssetGroup();
            break;
        case CONTROL_TYPE:
            node = new Control();
            break;
        case CONTROL_GROUP_TYPE:
            node = new ControlGroup();
            break;
        case SCENARIO_TYPE:
            node = new Scenario();
            break;
        case SCENARIO_GROUP_TYPE:
            node = new ScenarioGroup();
            break;
        case THREAT_TYPE:
            node = new Threat();
            break;
        case THREAT_GROUP_TYPE:
            node = new ThreatGroup();
            break;
        case VULNERABILITY_TYPE:
            node = new Vulnerability();
            break;
        case VULNERABILITY_GROUP_TYPE:
            node = new VulnerabilityGroup();
            break;
        case DOCUMENT_TYPE:
            node = new Document();
            break;
        case DOCUMENT_GROUP_TYPE:
            node = new DocumentGroup();
            break;
        case EXCEPTION_GROUP_TYPE:
            node = new ExceptionGroup();
            break;
        case EXCEPTION_TYPE:
            node = new org.v2020.data.entity.iso.Exception();
            break;
        case RESPONSE_TYPE:
            node = new Response();
            break;
        case RESPONSE_GROUP_TYPE:
            node = new ResponseGroup();
            break;
        case AUDIT_TYPE:
            node = new Audit();
            break;
        case AUDIT_GROUP_TYPE:
            node = new AuditGroup();
            break;
        case REQUIREMENT_TYPE:
            node = new Requirement();
            break;
        case REQUIREMENT_GROUP_TYPE:
            node = new RequirementGroup();
            break;
        case RECORD_TYPE:
            node = new Record();
            break;
        case RECORD_GROUP_TYPE:
            node = new RecordGroup();
            break;
        case INCIDENT_TYPE:
            node = new Incident();
            break;
        case INCIDENT_GROUP_TYPE:
            node = new IncidentGroup();
            break;
        case PROCESS_TYPE:
            node = new org.v2020.data.entity.iso.Process();
            break;
        case PROCESS_GROUP_TYPE:
            node = new ProcessGroup();
            break;
        case PERSON_TYPE:
            node = new Person();
            break;
        case PERSON_GROUP_TYPE:
            node = new PersonGroup();
            break;
        case FINDING_GROUP_TYPE:
            node = new FindingGroup();
            break;
        case FINDING_TYPE:
            node = new Finding();
            break;
        case INTERVIEW_GROUP_TYPE:
            node = new InterviewGroup();
            break;
        case INTERVIEW_TYPE:
            node = new Interview();
            break;
        case EVIDENCE_GROUP_TYPE:
            node = new EvidenceGroup();
            break;
        case EVIDENCE_TYPE:
            node = new Evidence();
            break;
        case SAMT_TOPIC_TYPE:
            node = new SamtTopic();
            break;
        default:
            LOG.warn("Unknown element type: " + type);
            node = new Node();
            break;
        }
        return node;
    }
}
