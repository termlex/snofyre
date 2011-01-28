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
package uk.nhs.cfh.dsp.srth.query.converters.xml.impl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.reflection.CGLIBEnhancedConverter;
import com.thoughtworks.xstream.io.xml.JDomDriver;
import com.thoughtworks.xstream.mapper.Mapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import uk.nhs.cfh.dsp.snomed.expression.model.*;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.*;
import uk.nhs.cfh.dsp.snomed.objectmodel.*;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.*;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.model.om.annotation.MetaData;
import uk.nhs.cfh.dsp.srth.query.model.om.annotation.impl.MetaDataImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.*;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 3, 2010 at 2:58:11 PM
 */
public class QueryExpressionXMLConverterImpl implements QueryExpressionXMLConverter {

    /** The x stream. */
    private XStream xStream;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryExpressionXMLConverterImpl.class);
    
    /** The outputter. */
    private XMLOutputter outputter;

    /**
     * Instantiates a new query expression xml converter impl.
     */
    public QueryExpressionXMLConverterImpl() {
        xStream = new XStream(new JDomDriver());
        xStream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
        outputter = new XMLOutputter();
        xStream.addDefaultImplementation(java.util.ArrayList.class, org.hibernate.collection.PersistentList.class);
        xStream.addDefaultImplementation(java.util.HashMap.class, org.hibernate.collection.PersistentMap.class);
        xStream.addDefaultImplementation(java.util.HashSet.class, org.hibernate.collection.PersistentSet.class);

        Mapper mapper = xStream.getMapper();
        xStream.registerConverter(new HibernateCollectionConverter(mapper));
        xStream.registerConverter(new HibernateMapConverter(mapper));
        xStream.registerConverter(new CGLIBEnhancedConverter(mapper,xStream.getReflectionProvider()));

        // register aliases to prettify xml
        xStream.alias("Query_Expression", QueryExpression.class, AbstractQueryExpression.class);
        xStream.alias("Query_Component_Expression", QueryComponentExpression.class, QueryComponentExpressionImpl.class);
        xStream.alias("Query_Intersection_Expression", QueryIntersectionExpression.class, QueryIntersectionExpressionImpl.class);
        xStream.alias("Query_Union_Expression", QueryUnionExpression.class, QueryUnionExpressionImpl.class);
        xStream.alias("Query_Statement", QueryStatement.class, QueryStatementImpl.class);
        xStream.alias("Meta_Data", MetaData.class, MetaDataImpl.class);
        xStream.alias("Constraint", Constraint.class, AbstractConstraint.class);
        xStream.alias("Data_Constraint", DataConstraint.class, AbstractDataConstraint.class);
        xStream.alias("Data_Constant_Constraint", DataConstantConstraint.class, DataConstantConstraintImpl.class);
        xStream.alias("Data_Range_Constraint", DataRangeConstraint.class, DataRangeConstraintImpl.class);
        xStream.alias("Data_Range_Facet_Constraint", DataRangeFacetConstraint.class, DataRangeFacetConstraintImpl.class);
        xStream.alias("Temporal_Constraint", TemporalConstraint.class, AbstractTemporalConstraint.class);
        xStream.alias("Temporal_Constant_Constraint", TemporalConstantConstraint.class, TemporalConstantConstraintImpl.class);
        xStream.alias("Temporal_Range_Facet_Constraint", TemporalRangeFacetConstraint.class, TemporalRangeFacetConstraintImpl.class);
        xStream.alias("Temporal_Range_Constraint", TemporalRangeConstraint.class, TemporalRangeConstraintImpl.class);
        xStream.alias("Terminology_Constraint", TerminologyConstraint.class, TerminologyConstraintImpl.class);

        // set embedded class aliases
        xStream.alias("Expression", Expression.class, AbstractExpressionImpl.class);
        xStream.alias("Expression_With_Focus_Concepts", ExpressionWithFocusConcepts.class, AbstractExpressionWithFocusConcepts.class);
        xStream.alias("Close_To_User_Expression", CloseToUserExpression.class, CloseToUserExpressionImpl.class);
        xStream.alias("Normal_Form_Expression", NormalFormExpression.class, NormalFormExpressionImpl.class);
        xStream.alias("Concept_Expression", ConceptExpression.class, ConceptExpressionImpl.class);
        xStream.alias("Snomed_Role_Group_Expression", SnomedRoleGroupExpression.class);
        xStream.alias("Property_Expression", PropertyExpression.class, PropertyExpressionImpl.class);
        xStream.alias("Snomed_Role_Group_Property", SnomedRoleGroupProperty.class);
        xStream.alias("Snomed_Relationship_Property", SnomedRelationshipProperty.class);
        xStream.alias("Snomed_Relationship_Property_Expression", SnomedRelationshipPropertyExpression.class);
        xStream.alias("Union_Expression", UnionExpression.class, UnionExpressionImpl.class);
        xStream.alias("Intersection_Expression", IntersectionExpression.class, IntersectionExpressionImpl.class);
        xStream.alias("Terminology_Expression", TerminologyExpression.class);

        xStream.alias("Concept", SnomedConcept.class, SnomedConceptImpl.class);
        xStream.alias("Description", SnomedDescription.class, SnomedDescriptionImpl.class);
        xStream.alias("Relationship", SnomedRelationship.class, SnomedRelationshipImpl.class);
        xStream.alias("Role_Group", SnomedRoleGroup.class, SnomedRoleGroupImpl.class);
        xStream.alias("Attribute", SnomedAttribute.class, SnomedAttributeImpl.class);
        // change xml element names for attributes to use caps
        xStream.aliasField("Relationships", SnomedConceptImpl.class, "relationships");
        xStream.aliasField("Descriptions", SnomedConceptImpl.class, "descriptions");
        xStream.aliasField("Synonyms", SnomedConceptImpl.class, "synonyms");
        xStream.aliasField("Role_Groups", SnomedConceptImpl.class, "roleGroups");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter#getElementForQueryExpression(uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression)
     */
    public Element getElementForQueryExpression(QueryExpression queryExpression){
        String xmlString = xStream.toXML(queryExpression);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter#getElementForQueryStatement(uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement)
     */
    public Element getElementForQueryStatement(QueryStatement queryStatement){
        String xmlString = xStream.toXML(queryStatement);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter#getElementForConstraint(uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint)
     */
    public Element getElementForConstraint(Constraint constraint){
        String xmlString = xStream.toXML(constraint);
        return getElementFromInputStream(new ByteArrayInputStream(xmlString.getBytes()));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter#getExpressionFromElement(org.jdom.Element)
     */
    public QueryExpression getExpressionFromElement(Element element){
        return (QueryExpression) xStream.fromXML(outputter.outputString(element));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter#getQueryStatementFromElement(org.jdom.Element)
     */
    public QueryStatement getQueryStatementFromElement(Element element){
        return (QueryStatement) xStream.fromXML(outputter.outputString(element));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter#getConstraintFromElement(org.jdom.Element)
     */
    public Constraint getConstraintFromElement(Element element){
        return (Constraint) xStream.fromXML(outputter.outputString(element));
    }

    /**
     * Gets the element from input stream.
     *
     * @param is the is
     * @return the element from input stream
     */
    private Element getElementFromInputStream(InputStream is){
        // disable xml validation
        SAXBuilder saxBuilder = new SAXBuilder(false);
        try
        {
            Document doc = saxBuilder.build(is);
            return doc.getRootElement();
        }
        catch (JDOMException e) {
            logger.warn("Error processing XML. Nested exception is : " + e.fillInStackTrace().getMessage());
            return null;
        }
        catch (IOException e) {
            logger.warn("IO error processing input stream. Nested exception is : " + e.fillInStackTrace().getMessage());
            return null;
        }
    }

    // custom classes for converting hibernate collections to java util collections
    /**
     * The Class HibernateCollectionConverter.
     */
    class HibernateCollectionConverter extends
            CollectionConverter {
        
        /**
         * Instantiates a new hibernate collection converter.
         *
         * @param mapper the mapper
         */
        HibernateCollectionConverter(Mapper mapper) {
            super(mapper);
        }

        /* (non-Javadoc)
         * @see com.thoughtworks.xstream.converters.collections.CollectionConverter#canConvert(java.lang.Class)
         */
        public boolean canConvert(Class type) {
            return super.canConvert(type)
                    || org.hibernate.collection.PersistentList.class
                    .equals(type)
                    || org.hibernate.collection.PersistentSet.class
                    .equals(type);
        }
    }

    /**
     * The Class HibernateMapConverter.
     */
    class HibernateMapConverter extends MapConverter {

        /**
         * Instantiates a new hibernate map converter.
         *
         * @param mapper the mapper
         */
        HibernateMapConverter(Mapper mapper) {
            super(mapper);
        }

        /* (non-Javadoc)
         * @see com.thoughtworks.xstream.converters.collections.MapConverter#canConvert(java.lang.Class)
         */
        public boolean canConvert(Class type) {
            return super.canConvert(type)
                    || org.hibernate.collection.PersistentMap.class
                    .equals(type);
        }
    }
}
