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
/**
 * 
 */
package uk.nhs.cfh.dsp.snomed.converters.xml.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import uk.nhs.cfh.dsp.snomed.converters.xml.utils.SnomedConceptDBUtils;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.converters.xml.TerminologyConceptToXMLConverter}
 *
 * @deprecated Use {@link uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter} instead
 *
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 23, 2008 at 12:08:57 AM
 */
public class SnomedConceptToXMLRenderer extends AbstractTerminologyConceptToXMLConverter {

	/** The connection. */
	private Connection connection;
	
	/** The logger. */
	private static Log logger = LogFactory
			.getLog(SnomedConceptToXMLRenderer.class);

	/**
	 * Instantiates a new snomed concept to xml renderer.
	 *
	 * @param connection the connection
	 */
	public SnomedConceptToXMLRenderer(Connection connection) {
		super();
		this.connection = connection;
	}

	/* (non-Javadoc)
	 * @see uk.nhs.cfh.dsp.snomed.converters.xml.impl.AbstractTerminologyConceptToXMLConverter#convertConceptToXMLDocument(uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept)
	 */
	@Override
	public Document convertConceptToXMLDocument(TerminologyConcept concept) {

		Document document = super.convertConceptToXMLDocument(concept);
		Element rootElement = document.getRootElement();

		// add remaining attributes to document
		SnomedConcept snomedConcept = (SnomedConcept) concept;
		Element ctv3IDElement = new Element("CTV3_ID");
		ctv3IDElement.setAttribute("value", snomedConcept.getCtv3ID());
		rootElement.addContent(ctv3IDElement);
		Element snomedIDElement = new Element("SNOMED_ID");
		snomedIDElement.setAttribute("value", snomedConcept.getSnomedID());
		rootElement.addContent(snomedIDElement);
		Element fullySpecifiedNameElement = new Element("Fully_Specified_Name");
		fullySpecifiedNameElement.setAttribute("value", snomedConcept.getFullySpecifiedName());
		rootElement.addContent(fullySpecifiedNameElement);
		Element isDefinedElement = new Element("Is_Primitive");
		isDefinedElement.setAttribute("value", String.valueOf(snomedConcept.isPrimitive()));
		rootElement.addContent(isDefinedElement);

		// add relationships
		Element definingRelationshipsElement = new Element("Defining_Relationships");
		Collection<SnomedRelationship> definingRelationships = snomedConcept.getDefiningRelationships();
		for (uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship snomedRelationship : definingRelationships)
		{
			String targetConceptLabel = SnomedConceptDBUtils.getFullySpecifiedName(connection, snomedRelationship.getTargetConceptID());
			String relationshipLabel = SnomedConceptDBUtils.getFullySpecifiedName(connection, snomedRelationship.getRelationshipType());

			Element definingRelationshipElement = new Element("Defining_Relationship");
			definingRelationshipElement.setAttribute("relation", relationshipLabel);
			definingRelationshipElement.setAttribute("target", targetConceptLabel);
			definingRelationshipsElement.addContent(definingRelationshipElement);
		}
		rootElement.addContent(definingRelationshipsElement);

		Collection<SnomedRelationship> refiningRelationships = snomedConcept.getRefiningRelationships();
		Element refiningRelationshipsElement = new Element("Refining_Relationships");
		for (uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship snomedRelationship : refiningRelationships)
		{
			String targetConceptLabel = SnomedConceptDBUtils.getFullySpecifiedName(connection, snomedRelationship.getTargetConceptID());
			String relationshipLabel = SnomedConceptDBUtils.getFullySpecifiedName(connection, snomedRelationship.getRelationshipType());

			Element refiningRelationshipElement = new Element("Refining_Relationship");
			refiningRelationshipElement.setAttribute("relation", relationshipLabel);
			refiningRelationshipElement.setAttribute("target", targetConceptLabel);
			refiningRelationshipsElement.addContent(refiningRelationshipElement);
		}
		rootElement.addContent(refiningRelationshipsElement);

		Collection<SnomedRelationship> mandatoryRelationships = snomedConcept.getMandatoryRelationships();
		Element mandatoryRelationshipsElement = new Element("Mandatory_Relationships");
		for (uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship snomedRelationship : mandatoryRelationships)
		{
			String targetConceptLabel = SnomedConceptDBUtils.getFullySpecifiedName(connection, snomedRelationship.getTargetConceptID());
			String relationshipLabel = SnomedConceptDBUtils.getFullySpecifiedName(connection, snomedRelationship.getRelationshipType());

			Element mandatoryRelationshipElement = new Element("Mandatory_Relationship");
			mandatoryRelationshipElement.setAttribute("relation", relationshipLabel);
			mandatoryRelationshipElement.setAttribute("target", targetConceptLabel);
			mandatoryRelationshipsElement.addContent(mandatoryRelationshipElement);
		}
		rootElement.addContent(mandatoryRelationshipsElement);

		return document;
	}

	/* (non-Javadoc)
	 * @see uk.nhs.cfh.dsp.snomed.converters.xml.impl.AbstractTerminologyConceptToXMLConverter#exportConceptToXMLDocument(uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept, java.io.File)
	 */
	public void exportConceptToXMLDocument(TerminologyConcept concept, File file) {

		Document doc = convertConceptToXMLDocument(concept);
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		try
		{
			FileWriter writer = new FileWriter(file);
			writer.flush();
			outputter.output(doc, writer);

			// close writer
			writer.close();
			logger.info("Finished exporting concept to file : "+file.getPath());

		} catch (IOException e) {
			logger.warn(e.fillInStackTrace());
		}
	}

}