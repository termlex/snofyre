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
package uk.nhs.cfh.dsp.srth.query.converters.text.impl;

import uk.nhs.cfh.dsp.srth.query.converters.html.QueryExpressionHTMLRenderer;
import uk.nhs.cfh.dsp.srth.query.converters.text.QueryExpressionPlainTextRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
//import uk.nhs.cfh.dsp.srth.query.model.om.impl.SubQueryExpressionImpl;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.converters.text.QueryExpressionPlainTextRenderer}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 11:18:43 AM
 */
public class QueryExpressionPlainTextRendererImpl implements QueryExpressionPlainTextRenderer {

    /** The query expression html renderer. */
    private QueryExpressionHTMLRenderer queryExpressionHTMLRenderer;

    /**
     * Instantiates a new query expression plain text renderer impl.
     * 
     * @param queryExpressionHTMLRenderer the query expression html renderer
     */
    public QueryExpressionPlainTextRendererImpl(QueryExpressionHTMLRenderer queryExpressionHTMLRenderer) {
        this.queryExpressionHTMLRenderer = queryExpressionHTMLRenderer;
    }

    /**
     * Instantiates a new query expression plain text renderer impl.
     */
    public QueryExpressionPlainTextRendererImpl() {
        // empty constructor for IOC
    }

    /**
     * Gets the plain text human readable label for expression.
     * 
     * @param expression the expression
     * 
     * @return the plain text human readable label for expression
     */
    public String getPlainTextHumanReadableLabelForExpression(QueryExpression expression){
        if(expression != null)
        {
//            if(expression instanceof SnomedQueryTerm)
//            {
//                return getPlainTextHumanReadableLabelForExpression((SnomedQueryTerm) expression);
//            }
//            else
            if(expression instanceof QueryStatement)
            {
                return getPlainTextHumanReadableLabelForExpression((QueryStatement) expression);
            }
            else if(expression instanceof QueryIntersectionExpression)
            {
                return getPlainTextHumanReadableLabelForExpression((QueryIntersectionExpression) expression);
            }
            else if(expression instanceof QueryUnionExpression)
            {
                return getPlainTextHumanReadableLabelForExpression((QueryUnionExpression) expression);
            }
//            else if(expression instanceof SubQueryExpressionImpl)
//            {
//                return getPlainTextHumanReadableLabelForExpression((SubQueryExpressionImpl) expression);
//            }
            else if(expression instanceof QueryComponentExpression)
            {
                return getPlainTextHumanReadableLabelForExpression((QueryComponentExpression) expression);
            }
            else
            {
                throw new IllegalArgumentException("Unknown expression type passed: "+expression.getClass());
            }
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /**
     * Gets the plain text human readable label for expression.
     * 
     * @param queryStatement the query statement
     * 
     * @return the plain text human readable label for expression
     */
    public String getPlainTextHumanReadableLabelForExpression(QueryStatement queryStatement) {
        return getPlainTextForExpression(queryStatement);
    }

//    /**
//     * Gets the plain text human readable label for expression.
//     *
//     * @param subQuery the sub query
//     *
//     * @return the plain text human readable label for expression
//     */
//    public String getPlainTextHumanReadableLabelForExpression(SubQueryExpressionImpl subQuery){
//        return getPlainTextForExpression(subQuery);
//    }

    /**
     * Gets the plain text human readable label for expression.
     *
     * @param queryComponentExpression the query component expression
     * @return the plain text human readable label for expression
     */
    public String getPlainTextHumanReadableLabelForExpression(QueryComponentExpression queryComponentExpression){
        return getPlainTextForExpression(queryComponentExpression);
    }

    /**
     * Gets the plain text human readable label for expression.
     * 
     * @param intersectionObject the intersection object
     * 
     * @return the plain text human readable label for expression
     */
    public String getPlainTextHumanReadableLabelForExpression(QueryIntersectionExpression intersectionObject){
        return getPlainTextForExpression(intersectionObject);
    }

    /**
     * Gets the plain text human readable label for expression.
     * 
     * @param unionObject the union object
     * 
     * @return the plain text human readable label for expression
     */
    public String getPlainTextHumanReadableLabelForExpression(QueryUnionExpression unionObject){
        return getPlainTextForExpression(unionObject);
    }

//    /**
//     * Gets the plain text human readable label for expression.
//     *
//     * @param queryTerm the query term
//     *
//     * @return the plain text human readable label for expression
//     */
//    public String getPlainTextHumanReadableLabelForExpression(SnomedQueryTerm queryTerm){
//        if(queryTerm != null)
//        {
//            String text = queryExpressionHTMLRenderer.getHTMLHumanReadableLabelForExpression(queryTerm);
//            // strip all html formatting
//            text = text.replaceAll("\\<.*?>", "");
//
//            return text;
//        }
//        else
//        {
//            throw new IllegalArgumentException("Query Term passed can not be null.");
//        }
//    }

    /**
     * Gets the plain text for expression.
     *
     * @param expression the expression
     * @return the plain text for expression
     */
    private String getPlainTextForExpression(QueryExpression expression){
        if(expression != null)
        {
            String text = queryExpressionHTMLRenderer.getHTMLHumanReadableLabelForExpression(expression);
            if(text != null)
            {

            // strip all html formatting
            text = text.replaceAll("\\<.*?>", "");

            return text;
            }
            else
            {
                return "";
            }
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /**
     * Sets the query expression html renderer.
     * 
     * @param queryExpressionHTMLRenderer the new query expression html renderer
     */
    public void setQueryExpressionHTMLRenderer(QueryExpressionHTMLRenderer queryExpressionHTMLRenderer) {
        this.queryExpressionHTMLRenderer = queryExpressionHTMLRenderer;
    }
}
