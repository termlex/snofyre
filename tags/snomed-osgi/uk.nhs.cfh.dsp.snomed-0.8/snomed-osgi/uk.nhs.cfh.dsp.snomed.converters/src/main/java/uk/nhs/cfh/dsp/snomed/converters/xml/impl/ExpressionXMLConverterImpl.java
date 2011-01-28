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
import com.thoughtworks.xstream.converters.basic.UUIDConverter;
import com.thoughtworks.xstream.io.xml.JDomDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter;
import uk.nhs.cfh.dsp.snomed.expression.model.*;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.*;
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
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 3, 2010 at 12:01:09 PM
 */
public class ExpressionXMLConverterImpl implements ExpressionXMLConverter {

    /** The x stream. */
    private XStream xStream;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ExpressionXMLConverterImpl.class);

    /**
     * Instantiates a new expression xml converter impl.
     */
    public ExpressionXMLConverterImpl() {
        xStream = new XStream(new JDomDriver());
        // register UUIDConverter 
        xStream.registerConverter(new UUIDConverter());
        // set class aliases
        xStream.alias("Expression_With_Focus_Concepts", ExpressionWithFocusConcepts.class, AbstractExpressionWithFocusConcepts.class);
        xStream.alias("Close_To_User_Expression", CloseToUserExpression.class, CloseToUserExpressionImpl.class);
        xStream.alias("Expression", Expression.class, AbstractExpressionImpl.class);
        xStream.alias("Concept_Expression", ConceptExpression.class, ConceptExpressionImpl.class);
        xStream.alias("Property_Expression", PropertyExpression.class, PropertyExpressionImpl.class);
        xStream.alias("Property", Property.class, PropertyImpl.class);
        xStream.alias("Snomed_Relationship_Property_Expression", SnomedRelationshipPropertyExpression.class);
        xStream.alias("Snomed_Role_Group_Expression", SnomedRoleGroupExpression.class);
        xStream.alias("Snomed_Role_Group_Property", SnomedRoleGroupProperty.class);
        xStream.alias("Snomed_Relationship_Property", SnomedRelationshipProperty.class);
        xStream.alias("Normal_Form_Expression", NormalFormExpression.class, NormalFormExpressionImpl.class);
        xStream.alias("Union_Expression", UnionExpression.class, UnionExpressionImpl.class);
        xStream.alias("Intersection_Expression", IntersectionExpression.class, IntersectionExpressionImpl.class);
        xStream.alias("Terminology_Expression", TerminologyExpression.class);

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
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter#getElementForCloseToUserForm(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    public Element getElementForCloseToUserForm(CloseToUserExpression closeToUserExpression){
        String xmlString = xStream.toXML(closeToUserExpression);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter#getElementForNormalForm(uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression)
     */
    public Element getElementForNormalForm(NormalFormExpression normalFormExpression){
        String xmlString = xStream.toXML(normalFormExpression);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter#getElementForPropertyExpression(uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression)
     */
    public Element getElementForPropertyExpression(PropertyExpression propertyExpression){
        String xmlString = xStream.toXML(propertyExpression);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter#getElementForConceptExpression(uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression)
     */
    public Element getElementForConceptExpression(ConceptExpression conceptExpression){
        String xmlString = xStream.toXML(conceptExpression);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter#getElementForIntersectionExpression(uk.nhs.cfh.dsp.snomed.expression.model.IntersectionExpression)
     */
    public Element getElementForIntersectionExpression(IntersectionExpression intersectionExpression){
        String xmlString = xStream.toXML(intersectionExpression);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter#getElementForUnionExpression(uk.nhs.cfh.dsp.snomed.expression.model.UnionExpression)
     */
    public Element getElementForUnionExpression(UnionExpression unionExpression){
        String xmlString = xStream.toXML(unionExpression);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /**
     * Gets the element from input stream.
     *
     * @param is the is
     * @return the element from input stream
     */
    private synchronized Element getElementFromInputStream(InputStream is){
        // disable xml validation
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
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter#getNormalFormExpressionFromXML(org.jdom.Element)
     */
    public NormalFormExpression getNormalFormExpressionFromXML(Element element){
        XMLOutputter outputter = new XMLOutputter();
        return (NormalFormExpression) xStream.fromXML(outputter.outputString(element));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter#getCloseToUserExpression(org.jdom.Element)
     */
    public CloseToUserExpression getCloseToUserExpression(Element element){
        XMLOutputter outputter = new XMLOutputter();
        return (CloseToUserExpression) xStream.fromXML(outputter.outputString(element));
    }
}
