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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.JDomDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedDescriptionImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRelationshipImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRoleGroupImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter} that uses
 * XStream (http://xstream.codehaus.org/)
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 18, 2009 at 3:03:33 PM
 */
public abstract class AbstractXSBasedSnomedXMLConverter implements SnomedXMLConverter{

    /** The x stream. */
    private XStream xStream;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(AbstractXSBasedSnomedXMLConverter.class);

    /**
     * Instantiates a new abstract xs based snomed xml converter.
     */
    public AbstractXSBasedSnomedXMLConverter() {
        xStream = new XStream(new JDomDriver());
        // add aliases for classes to pretify xml
        xStream.alias("Concept", SnomedConcept.class, SnomedConceptImpl.class);
        xStream.alias("Description", SnomedDescription.class, SnomedDescriptionImpl.class);
        xStream.alias("Relationship", SnomedRelationship.class, SnomedRelationshipImpl.class);
        xStream.alias("Role_Group", SnomedRoleGroup.class, SnomedRoleGroupImpl.class);
        // change xml element names for attributes to use caps
        xStream.aliasField("Relationships", SnomedConceptImpl.class, "relationships");
        xStream.aliasField("Descriptions", SnomedConceptImpl.class, "descriptions");
        xStream.aliasField("Synonyms", SnomedConceptImpl.class, "synonyms");
        xStream.aliasField("Role_Groups", SnomedConceptImpl.class, "roleGroups");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getElementForConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public Element getElementForConcept(SnomedConcept concept) {
        String xmlString = xStream.toXML(concept);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getElementForDescription(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription)
     */
    public Element getElementForDescription(SnomedDescription description) {
        String xmlString = xStream.toXML(description);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getElementForRelationship(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship)
     */
    public Element getElementForRelationship(SnomedRelationship relationship) {
        String xmlString = xStream.toXML(relationship);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getElementForRoleGroup(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup)
     */
    public Element getElementForRoleGroup(SnomedRoleGroup roleGroup) {
        String xmlString = xStream.toXML(roleGroup);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /**
     * Gets the element from input stream.
     *
     * @param is the is
     * @return the element from input stream
     */
    private synchronized Element getElementFromInputStream(InputStream is){
        SAXBuilder saxBuilder = new SAXBuilder(false);
        try
        {
            Document doc = saxBuilder.build(is);
            return doc.getRootElement();
        }
        catch (JDOMException e) {
            logger.warn(e.fillInStackTrace());
            return null;
        }
        catch (IOException e) {
            logger.warn(e.fillInStackTrace());
            return null;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter#getConceptFromXML(org.jdom.Element)
     */
    public SnomedConcept getConceptFromXML(Element element){
        // get string representation of XML and pass to xStream
        XMLOutputter outputter = new XMLOutputter();
        return (SnomedConcept) xStream.fromXML(outputter.outputString(element));
    }
}
