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
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import uk.nhs.cfh.dsp.snomed.converters.xml.TerminologyConceptToXMLConverter;
import uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.error.ConceptNotFoundException;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.AbstractTerminologyConcept;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of {@link uk.nhs.cfh.dsp.snomed.converters.xml.TerminologyConceptToXMLConverter} interface.
 * 
 * @deprecated Use {@link uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter} instead
 *
 * <br> Written by @author Jay Kola
 * <br> Created on Dec 22, 2008 at 11:49:26 PM
 * <br> Modified on Tuesday; December 8, 2009 at 7:02 PM to enable OSGI
 */
@SuppressWarnings({"unchecked", "serial"})
public abstract class AbstractTerminologyConceptToXMLConverter implements
        TerminologyConceptToXMLConverter {

	/** The logger. */
	private static Log logger = LogFactory
			.getLog(AbstractTerminologyConceptToXMLConverter.class);

	/**
	 * Instantiates a new abstract terminology concept to xml converter.
	 */
	public AbstractTerminologyConceptToXMLConverter() {
	}

	/* (non-Javadoc)
	 * @see uk.nhs.cfh.dsp.snomed.converters.TerminologyConceptToXMLConverter#convertConceptToXMLDocument(uk.nhs.cfh.dsp.snomed.om.TerminologyConcept)
	 */
	public Document convertConceptToXMLDocument(TerminologyConcept concept) {

		Document document = new Document();
		Element rootElement = new Element("Concept");
		document.setRootElement(rootElement);

		// get attributes and add as child elements
		rootElement.setAttribute("id", concept.getConceptID());

		Element sourceElement = new Element("Source");
		sourceElement.setAttribute("value", concept.getSource());
		rootElement.addContent(sourceElement);

		Element preferredLabelElement = new Element("Preferred_Label");
		preferredLabelElement.setAttribute("value", concept.getPreferredLabel());
		rootElement.addContent(preferredLabelElement);

		Element statusElement = new Element("Status");
		statusElement.setAttribute("value", concept.getStatus().name());
		rootElement.addContent(statusElement);

		// add synonyms
		Element synonymsElement = new Element("Synonyms");
		for(String synonym : concept.getSynonyms())
		{
			Element synonymElement = new Element("Synonym");
			synonymElement.setAttribute("value", synonym);
			synonymsElement.addContent(synonymElement);
		}
		rootElement.addContent(synonymsElement);

		return document;
	}

	/* (non-Javadoc)
	 * @see uk.nhs.cfh.dsp.snomed.converters.TerminologyConceptToXMLConverter#convertXMLToConcept(org.jdom.Document)
	 */
	public TerminologyConcept convertXMLToConcept(Document document) {

		String id = "";
		String label = "";
		Set<String> synonyms = new HashSet<String>();
		// get concept id and label
		try
		{
			List<Element> idList = XPath.selectNodes(document, "//Concept");
			id = idList.get(0).getAttributeValue("id");

			// get label
			label = getValueOfElement(document, "Preferred_Label");

			// get synonyms
			List<Element> synsList = XPath.selectNodes(document, "//Synonym");
			for(Element ele : synsList)
			{
				// add to synonyms
				synonyms.add(ele.getAttributeValue("value"));
			}

		} catch (JDOMException e) {
			logger.warn(e.fillInStackTrace());
		}

		TerminologyConcept concept = null;

		if ("".equalsIgnoreCase(id))
		{
			// throw an error
			new ConceptNotFoundException("ConceptId is not set...");
		}
		else
		{
			concept = new AbstractTerminologyConcept(id, label) {
			};
			// add synonyms
			concept.setSynonyms(new ArrayList<String>(synonyms));
		}

		return concept;
	}

	/* (non-Javadoc)
	 * @see uk.nhs.cfh.dsp.snomed.converters.TerminologyConceptToXMLConverter#exportConceptToXMLDocument(java.io.File)
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

		} catch (IOException e) {
			logger.warn(e.fillInStackTrace());
		}
	}

	/**
	 * Gets the value of element.
	 *
	 * @param document the document
	 * @param elementName the element name
	 * @return the value of element
	 */
	protected String getValueOfElement(Document document, String elementName){

		String value = "";
		try
		{
			List<Element> list = XPath.selectNodes(document, "//"+elementName);
			value = list.get(0).getAttributeValue("value");
		} catch (JDOMException e) {
			logger.warn(e.fillInStackTrace());
		}

		return value;
	}
}