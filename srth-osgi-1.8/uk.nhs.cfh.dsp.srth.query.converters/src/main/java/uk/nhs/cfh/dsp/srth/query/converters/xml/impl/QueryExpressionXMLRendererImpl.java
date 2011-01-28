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

import org.jdom.Element;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintValue;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLRenderer}.
 *
 * <br>
 * @deprecated Use {@link uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter} instead
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 1:10:14 AM
 */
public class QueryExpressionXMLRendererImpl implements QueryExpressionXMLRenderer {

    /**
     * Gets the as xml element.
     *
     * @param queryExpression the query expression
     *
     * @return the as xml element
     */
    public Element getAsXMLElement(QueryExpression queryExpression){
        if(queryExpression != null)
        {
            if(queryExpression instanceof QueryStatement)
            {
                return getAsXMLElement((QueryStatement) queryExpression);
            }
//            else if(queryExpression instanceof SubQueryExpressionImpl)
//            {
//                return getAsXMLElement((SubQueryExpressionImpl) queryExpression);
//            }
            else if(queryExpression instanceof QueryIntersectionExpression)
            {
                return getAsXMLElement((QueryIntersectionExpression) queryExpression);
            }
            else if(queryExpression instanceof QueryUnionExpression)
            {
                return getAsXMLElement((QueryUnionExpression) queryExpression);
            }
            else
            {
                throw new IllegalArgumentException("Unknown expression type passed : "+queryExpression.getClass());
            }
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /**
     * Gets the as xml element.
     *
     * @param queryStatement the query statement
     *
     * @return the as xml element
     */
    public Element getAsXMLElement(QueryStatement queryStatement){
        if(queryStatement != null)
        {
            Element element = new Element("Query");
            // add logical statement and human readable statement as attributes
            element.setAttribute("logical_statement", String.valueOf(queryStatement.getLogicalStatement()));
            element.setAttribute("human_readable_text", String.valueOf(queryStatement.getHumanReadableStatement()));

            // loop through contained expressions and add to element
            for(QueryExpression expression : queryStatement.getContainedExpressions())
            {
                Element expressionElement = getAsXMLElement(expression);
                // add to main element
                element.addContent(expressionElement);
            }

            return element;
        }
        else
        {
            throw new IllegalArgumentException("Query Statement passed can not be null.");
        }
    }

    /**
     * Gets the as xml element.
     *
     * @param unionObject the union object
     *
     * @return the as xml element
     */
    public Element getAsXMLElement(QueryUnionExpression unionObject){
        if(unionObject != null)
        {
            Element element = new Element("Union");
            // add id
            element.setAttribute("id", String.valueOf(unionObject.getUUID()));
            // add operator
            element.setAttribute("operator", String.valueOf(unionObject.getOperator()));
            // add run time status
            element.setAttribute("run_time_status", String.valueOf(unionObject.getRunTimeStatus()));
            // loop through contained objects and add them as child elements
            for(QueryExpression expression : unionObject.getContainedExpressions())
            {
                Element expressionElement = getAsXMLElement(expression);
                element.addContent(expressionElement);
            }

            return element;
        }
        else
        {
            throw new IllegalArgumentException("Union object passed can not be null.");
        }
    }

    /**
     * Gets the as xml element.
     *
     * @param intersectionObject the intersection object
     *
     * @return the as xml element
     */
    public Element getAsXMLElement(QueryIntersectionExpression intersectionObject){
        if(intersectionObject != null)
        {
            Element element = new Element("Intersection");
            // add id
            element.setAttribute("id", String.valueOf(intersectionObject.getUUID()));
            // add operator
            element.setAttribute("operator", String.valueOf(intersectionObject.getOperator()));
            // add run time status
            element.setAttribute("run_time_status", String.valueOf(intersectionObject.getRunTimeStatus()));
            // loop through contained objects and add them as child elements
            for(QueryExpression expression : intersectionObject.getContainedExpressions())
            {
                Element expressionElement = getAsXMLElement(expression);
                element.addContent(expressionElement);
            }

            return element;
        }
        else
        {
            throw new IllegalArgumentException("Intersection object passed can not be null.");
        }
    }

//    /**
//     * Gets the as xml element.
//     *
//     * @param subQuery the sub query
//     *
//     * @return the as xml element
//     */
//    public Element getAsXMLElement(SubQueryExpressionImpl subQuery){
//        if(subQuery != null)
//        {
//            Element element = new Element("Sub_Query");
//            // set id as attribute
//            element.setAttribute("id", subQuery.getUUID());
//            // add run time status
//            element.setAttribute("run_time_status", String.valueOf(subQuery.getRunTimeStatus()));
//
//            // get included query term as element and add to element
//            SnomedQueryTerm includedQueryTerm = subQuery.getIncludedQueryTerm();
//            if(includedQueryTerm != null)
//            {
//                Element includedTermElement = new Element("Included_Term");
//                Element conceptElement = getAsXMLElement(includedQueryTerm);
//                includedTermElement.addContent(conceptElement);
//                element.addContent(includedTermElement);
//            }
//            // get excluded query term as element and add to element
//            for(SnomedQueryTerm excludedTerm : subQuery.getExcludedQueryTerms())
//            {
//                Element excludedTermElement = new Element("Excluded_Term");
//                Element conceptElement = getAsXMLElement(excludedTerm);
//                excludedTermElement.addContent(conceptElement);
//                element.addContent(excludedTermElement);
//            }
//
//            // get included constraints
//            for(Constraint constraint : subQuery.getConstraints())
//            {
//                Element constraintElement = getAsXMLElement(constraint);
//                element.addContent(constraintElement);
//            }
//
//            return element;
//        }
//        else
//        {
//            throw new IllegalArgumentException("SubQuery passed can not be null.");
//        }
//    }

//    /**
//     * Gets the as xml element.
//     *
//     * @param queryTerm the query term
//     *
//     * @return the as xml element
//     */
//    public Element getAsXMLElement(SnomedQueryTerm queryTerm){
//        if(queryTerm != null)
//        {
//            Element element = new Element("Query_Term");
//            element.setAttribute("subsumption_status", String.valueOf(queryTerm.getSubSumptionStrategy()));
//            // add concept id as child
//            Element conceptEle = new Element("Concept");
//            conceptEle.setAttribute("terminology_version", queryTerm.getTerminologyVersion());
//            conceptEle.setAttribute("id", queryTerm.getConceptID());
//            conceptEle.setAttribute("label", queryTerm.getTermLabel());
//            // add concept element to element
//            element.addContent(conceptEle);
//
//            return element;
//        }
//        else
//        {
//            throw new IllegalArgumentException("Query term passed can not be null.");
//        }
//    }

    /**
     * Gets the as xml element.
     *
     * @param constraint the constraint
     *
     * @return the as xml element
     */
    public Element getAsXMLElement(Constraint constraint){
        if(constraint != null)
        {
            Element element = new Element("Constraint");
            element.setAttribute("type", constraint.getType().name());
            element.setAttribute("dimension", constraint.getDimensionVocabulary().name());
            element.setAttribute("name" , constraint.getName());
            element.addContent(getAsXMLElement(constraint.getValue()));

            return element;
        }
        else
        {
            throw new IllegalArgumentException("Constraint passed can not be null.");
        }
    }

    /**
     * Gets the as xml element.
     *
     * @param value the value
     *
     * @return the as xml element
     */
    public Element getAsXMLElement(ConstraintValue<?> value){
        if (value != null)
        {
            Element element = new Element("Constraint_Value");
            element.setAttribute("type", value.getValue().getClass().getSimpleName());
            element.setAttribute("value", toString());

            return element;
        }
        else
        {
            throw new IllegalArgumentException("Constraint value passed can not be null.");
        }
    }
}
