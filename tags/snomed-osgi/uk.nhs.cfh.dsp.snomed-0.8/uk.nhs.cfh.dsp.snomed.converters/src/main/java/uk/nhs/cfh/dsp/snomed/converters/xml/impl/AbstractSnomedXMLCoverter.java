/**
 * Crown Copyright (C) 2008 - 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.nhs.cfh.dsp.snomed.converters.xml.impl;

import org.jdom.Element;
import uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter}.
 *
 * @deprecated Use {@link uk.nhs.cfh.dsp.snomed.converters.xml.impl.XSBasedSnomedXMLConverter} instead
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 18, 2009 at 11:35:43 AM
 */
public abstract class AbstractSnomedXMLCoverter implements SnomedXMLConverter{

    /**
     * Instantiates a new abstract snomed xml coverter.
     */
    public AbstractSnomedXMLCoverter() {
        // empty constructor
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getElementForConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public Element getElementForConcept(SnomedConcept concept){

        Element conceptElement = new Element("Concept");
        // get properties and add as attributes
        conceptElement.setAttribute("conceptId", concept.getConceptID());
        conceptElement.setAttribute("ctv3id", concept.getCtv3ID());
        conceptElement.setAttribute("snomedId", concept.getSnomedID());
        conceptElement.setAttribute("isPrimitive", String.valueOf(concept.isPrimitive()));
        conceptElement.setAttribute("fullySpecifiedName", concept.getFullySpecifiedName());
        conceptElement.setAttribute("conceptStatus", concept.getStatus().name());

        // create descriptions element and add to descriptionsElement
        Element descriptionsElement = new Element("ConceptDescriptions");
        for(SnomedDescription description : concept.getDescriptions())
        {
            Element descriptionElement = getElementForDescription(description);
            descriptionsElement.addContent(descriptionElement);
        }
        conceptElement.addContent(descriptionsElement);


        /*
            create relationships element and add to conceptElement. To populate the element, we
            need to add refining and defining properties and role groups separately and not
            add all relationships
          */
        Element relationshipsElement = new Element("RelationshipSet");
        for(SnomedRelationship relationship : concept.getDefiningRelationships())
        {
            Element relationshipElement = getElementForRelationship(relationship);
            relationshipsElement.addContent(relationshipElement);
        }
        // add all refining relationships
        for(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship relationship : concept.getRefiningRelationships())
        {
            Element relationshipElement = getElementForRelationship(relationship);
            relationshipsElement.addContent(relationshipElement);
        }


        // create role groups and add relationshipgroup element for each role group
        for(SnomedRoleGroup roleGroup : concept.getRoleGroups())
        {
            Element roleGroupElement = getElementForRoleGroup(roleGroup);
            relationshipsElement.addContent(roleGroupElement);
        }
        conceptElement.addContent(relationshipsElement);


        return conceptElement;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getElementForDescription(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription)
     */
    public Element getElementForDescription(SnomedDescription description) {

        Element descriptionElement = new Element("ConceptDescription");
        // get properties and add as attributes
        descriptionElement.setAttribute("descriptionId", description.getId());
        descriptionElement.setAttribute("term", description.getTerm());
        descriptionElement.setAttribute("descriptionType", description.getType());
        descriptionElement.setAttribute("initialCapitalStatus", String.valueOf(description.isInitialCapStatus()));
        descriptionElement.setAttribute("languageCode", description.getLanguage());
        descriptionElement.setAttribute("descriptionStatus", description.getStatus().name());
        descriptionElement.setAttribute("conceptId", description.getConceptID());

        return descriptionElement;
    }


    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getElementForRelationship(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship)
     */
    public Element getElementForRelationship(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship relationship){

        Element relationshipElement = new Element("ConceptRelationship");
        // get properties and add as attributes
        relationshipElement.setAttribute("relationshipId", relationship.getRelationshipID());
        relationshipElement.setAttribute("relationshipType", relationship.getRelationshipType());
        relationshipElement.setAttribute("conceptId2", relationship.getTargetConceptID());
        relationshipElement.setAttribute("characteristicType", relationship.getType().name());
        relationshipElement.setAttribute("refinability", relationship.getRefinability().name());
//        relationshipElement.setAttribute("isStated", "false");
        relationshipElement.setAttribute("r", relationship.getRelationshipID());

        return relationshipElement;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getElementForRoleGroup(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup)
     */
    public Element getElementForRoleGroup(SnomedRoleGroup roleGroup){

        Element roleGroupElement = new Element("RelationshipGroup");
        // set properties as attributes
        roleGroupElement.setAttribute("relationshipGroup", roleGroup.getRelationshipGroupId());
        // loop through relationships in role group and create elements to add
        for(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship relationship : roleGroup.getRelationships())
        {
            Element relationshipElement = getElementForRelationship(relationship);
            roleGroupElement.addContent(relationshipElement);
        }

        return roleGroupElement;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getConceptFromXML(org.jdom.Element)
     */
    public SnomedConcept getConceptFromXML(Element element) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
