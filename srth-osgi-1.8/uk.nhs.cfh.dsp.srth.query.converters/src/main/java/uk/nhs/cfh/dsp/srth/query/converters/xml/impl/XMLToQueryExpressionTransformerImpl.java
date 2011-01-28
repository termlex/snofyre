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
package uk.nhs.cfh.dsp.srth.query.converters.xml.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import uk.nhs.cfh.dsp.srth.query.converters.xml.XMLToQueryExpressionTransformer;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression.QueryOperatorType;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression.QueryRunTimeStatus;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryIntersectionExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.DataConstantConstraintImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.DataRangeConstraintImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.DataRangeFacetConstraintImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryIntersectionExpressionImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryUnionExpressionImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * An utility class that implements static utility methods for transforming between
 * a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement} object and an XML {@link Element} object.
 *
 * <br>
 * @deprecated Use {@link uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter} instead
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 30, 2009 at 4:18:20 PM
 */

@SuppressWarnings("unchecked")
public class XMLToQueryExpressionTransformerImpl implements XMLToQueryExpressionTransformer {

    /** The logger. */
    private static Log logger = LogFactory.getLog(XMLToQueryExpressionTransformerImpl.class);
    
    /** The query expression factory. */
    private QueryExpressionFactory queryExpressionFactory;

    /**
     * Instantiates a new xML to query expression transformer impl.
     *
     * @param queryExpressionFactory the query expression factory
     */
    public XMLToQueryExpressionTransformerImpl(QueryExpressionFactory queryExpressionFactory) {
        this.queryExpressionFactory = queryExpressionFactory;
    }

    /**
     * Gets the query statement.
     *
     * @param element the element
     *
     * @return the query statement
     */
    public QueryStatement getQueryStatement(Element element){

        QueryStatement query = queryExpressionFactory.getQueryStatement();
        // get and set logical statement and human readable statement values
        String humanReadableStatement = element.getAttributeValue("human_readable_text");
        String logicalStatement = element.getAttributeValue("logical_statement");
        query.setHumanReadableStatement(humanReadableStatement);
        query.setLogicalStatement(logicalStatement);

        // get children and generate expressions
        List<Element> children = element.getChildren();
        for(Element child : children)
        {
            QueryExpression exp = getReportingQueryExpression(query, child);
            // add exp to query
            query.addChildExpression(exp);
        }

        return query;
    }

    /**
     * Gets the reporting query expression.
     *
     * @param parent the parent
     * @param element the element
     *
     * @return the reporting query expression
     */
    public QueryExpression getReportingQueryExpression(QueryExpression parent, Element element){

        QueryExpression expression = null;
        // create expression type based on name
        String name = element.getName();
        logger.debug("Value of name : " + name);
        if("Union".equalsIgnoreCase(name))
        {
            String id = element.getAttributeValue("id");
            expression = new QueryUnionExpressionImpl();
            expression.setUUID(id);
            String runTimeStatus = element.getAttributeValue("run_time_status");
            QueryRunTimeStatus status = QueryRunTimeStatus.valueOf(runTimeStatus);
            expression.setRunTimeStatus(status);

            List<Element> children = element.getChildren();
            if(children.size() >0)
            {
                // recursive call
                for(Element childEle : children)
                {
                    QueryExpression childExp = getReportingQueryExpression(expression, childEle);
                    // add children to expression
                    expression.addChildExpression(childExp);
                }
            }
        }
        else if("Intersection".equalsIgnoreCase(name))
        {
            String id = element.getAttributeValue("id");
            QueryIntersectionExpression intersectionObject = new QueryIntersectionExpressionImpl();
            intersectionObject.setUUID(id);
            String operator = element.getAttributeValue("operator");
            if(operator != null)
            {
                QueryOperatorType operatorType = QueryOperatorType.valueOf(operator);
                intersectionObject.setOperator(operatorType);
            }
            String runTimeStatus = element.getAttributeValue("run_time_status");
            QueryRunTimeStatus status = QueryRunTimeStatus.valueOf(runTimeStatus);
            intersectionObject.setRunTimeStatus(status);

            List<Element> children = element.getChildren();
            if(children.size() >0)
            {
                // recursive call
                for(Element childEle : children)
                {
                    QueryExpression childExp = getReportingQueryExpression(intersectionObject, childEle);
                    // add child exp to exp
                    intersectionObject.addChildExpression(childExp);
                }
            }

            expression = intersectionObject;
        }
        else
        {
            logger.warn("Unknown element type passed : "+name);
        }

        // add expression to parent
        parent.addChildExpression(expression);

        return expression;
    }

    /**
     * Gets the constraint.
     *
     * @param element the element
     *
     * @return the constraint
     *
     * @throws JDOMException the JDOM exception
     */
    public Constraint getConstraint(Element element) throws JDOMException{

        // return constraint based on type attribute of element
        String type = element.getAttributeValue("type");
        logger.debug("Value of type : " + type);
        String name = element.getAttributeValue("name");

        // get dimension from dimension attribute
        String dimensionString = element.getAttributeValue("dimension");
        ConstraintDimensionVocabulary dimension = ConstraintDimensionVocabulary.valueOf(dimensionString);

        if(DataConstantConstraint.class.getSimpleName().equals(type))
        {
            Element valueElement = element.getChild("Constraint_Value");
            ConstraintValue<Number> value = (ConstraintValue<Number>) getConstraintValue(valueElement);
            DataConstantConstraint constraint = new DataConstantConstraintImpl(value.getValue(), dimension);
            constraint.setName(name);

            return constraint;
        }
        else if (DataRangeFacetConstraint.class.getSimpleName().equals(type))
        {
            Element valueElement = element.getChild("Constraint_Value");
            Element facetElement = element.getChild("Facet");
            RangeFacetVocabulary facet = RangeFacetVocabulary.valueOf(facetElement.getAttributeValue("type"));
            ConstraintValue<Number> value = (ConstraintValue<Number>) getConstraintValue(valueElement);
            DataRangeFacetConstraint fc = new DataRangeFacetConstraintImpl(value.getValue(), dimension , facet);
            fc.setName(name);

            return fc;
        }
        else if(DataRangeConstraint.class.getSimpleName().equals(type))
        {
            /*
                *  we know this element will be made up of two data range facet elements,
                *  so we loop through the children and add them as facets
                */

            Set<DataRangeFacetConstraint> facets = new HashSet<DataRangeFacetConstraint>();
            List<Element> facetElements = XPath.selectNodes(element, "./Constraint");
            for(Element e : facetElements)
            {
                // recursive call
                DataRangeFacetConstraint fc = (DataRangeFacetConstraint) getConstraint(e);
                // add to facets
                facets.add(fc);
            }
            DataRangeConstraint rangeConstraint = new DataRangeConstraintImpl(facets);
            rangeConstraint.setName(name);

            return rangeConstraint;
        }
        else
        {
            // throw error since nothing else has been implemented!
            logger.warn("Unknown/Unsupported constraint type encountered : "+type);

            return null;
        }
    }

    /**
     * Gets the constraint value.
     *
     * @param element the element
     *
     * @return the constraint value
     */
    public ConstraintValue<?> getConstraintValue(Element element){

        logger.debug("Value of element.getName() : " + element.getName());
        ConstraintValue<?> constraintValue = null;
        String value = element.getAttributeValue("value");
        // create value based on attribute 'type'
        String type = element.getAttributeValue("type");
        if("Integer".equals(type))
        {
            int intValue = Integer.parseInt(value);
            constraintValue = new ConstraintValue<Integer>(intValue);
        }
        else if ("Double".equals(type))
        {
            double doubleValue =  Double.parseDouble(value);
            constraintValue = new ConstraintValue<Double>(doubleValue);
        }
        else if ("String".equals(type))
        {
            constraintValue = new ConstraintValue<String>(value);
        }
        else if(type.indexOf("Calendar") > -1)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
            /*
                * we know that this is saved as yyyy-MM-ddThh:mm:ss
                * So we replace 'T' and all instances of ':' with '-'
                */
            String calString = value.replaceAll("T", "-");
            calString = calString.replaceAll(":", "-");

            try
            {
                Date date = sdf.parse(calString);
                Calendar cal = new GregorianCalendar();
                cal.setTime(date);

                constraintValue = new ConstraintValue<Calendar>(cal);
            } catch (ParseException e) {
                logger.warn(e.fillInStackTrace());
            }


        }
        else
        {
            logger.warn("Unknown constraint value type : "+type);
        }

        return constraintValue;
    }
}
