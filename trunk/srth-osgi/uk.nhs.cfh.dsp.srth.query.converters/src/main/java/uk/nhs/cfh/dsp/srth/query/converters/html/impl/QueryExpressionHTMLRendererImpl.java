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
package uk.nhs.cfh.dsp.srth.query.converters.html.impl;

import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.srth.query.converters.html.QueryExpressionHTMLRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.SubsumptionVocabulary;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.converters.html.QueryExpressionHTMLRenderer}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 1:39:02 AM
 */
public class QueryExpressionHTMLRendererImpl implements QueryExpressionHTMLRenderer {

    /** The human readable render. */
    private HumanReadableRender humanReadableRender;

    /**
     * Instantiates a new query expression html renderer impl.
     *
     * @param humanReadableRender the human readable render
     */
    public QueryExpressionHTMLRendererImpl(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Gets the hTML human readable label for expression.
     *
     * @param expression the expression
     *
     * @return the hTML human readable label for expression
     */
    public String getHTMLHumanReadableLabelForExpression(QueryExpression expression){
        if(expression != null)
        {
//            if(expression instanceof SnomedQueryTerm)
//            {
//                return getHTMLHumanReadableLabelForExpression((SnomedQueryTerm) expression);
//            }
//            else
            if(expression instanceof QueryStatement)
            {
                return getHTMLHumanReadableLabelForExpression((QueryStatement) expression);
            }
            else if(expression instanceof QueryComponentExpression)
            {
                return getHTMLHumanReadableLabelForExpression((QueryComponentExpression) expression);
            }
            else if(expression instanceof QueryIntersectionExpression)
            {
                return getHTMLHumanReadableLabelForExpression((QueryIntersectionExpression) expression);
            }
            else if(expression instanceof QueryUnionExpression)
            {
                return getHTMLHumanReadableLabelForExpression((QueryUnionExpression) expression);
            }
//            else if(expression instanceof SubQueryExpressionImpl)
//            {
//                return getHTMLHumanReadableLabelForExpression((SubQueryExpressionImpl) expression);
//            }
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
     * Gets the hTML human readable label for expression.
     *
     * @param queryStatement the query statement
     *
     * @return the hTML human readable label for expression
     */
    public String getHTMLHumanReadableLabelForExpression(QueryStatement queryStatement) {
        if (queryStatement != null)
        {
            // loop through contained expressions and concat their labels
            String label = "<b>ALL patients who have </b>";
            for(QueryExpression exp : queryStatement.getContainedExpressions())
            {
                label = label+getHTMLHumanReadableLabelForExpression(exp);
            }

            return label;
        }
        else
        {
            throw new IllegalArgumentException("Query Statement passed can not be null.");
        }
    }

//    /**
//     * Gets the hTML human readable label for expression.
//     *
//     * @param subQuery the sub query
//     *
//     * @return the hTML human readable label for expression
//     */
//    public String getHTMLHumanReadableLabelForExpression(SubQueryExpressionImpl subQuery){
//        if(subQuery != null)
//        {
//
//            String html = getHTMLHumanReadableLabelForExpression(subQuery.getIncludedQueryTerm());
//            // add exclusion terms text
//            List<SnomedQueryTerm> excludedTerms = new ArrayList<SnomedQueryTerm>(subQuery.getExcludedQueryTerms());
//             if(excludedTerms.size() == 1)
//            {
//                // add single excluded term
//                html = html+"<font color=#ff0000> EXCEPT [</font>"+getHTMLHumanReadableLabelForExpression(excludedTerms.get(0))+
//                        "<font color=#ff0000> ]</font>";
//            }
//            else if(excludedTerms.size() >1)
//            {
//                int counter = 0;
//                for(SnomedQueryTerm excludedTerm : excludedTerms)
//                {
//                    if(counter == 0)
//                    {
//                        // add single excluded term
//                        html = html+"<font color=#ff0000> EXCEPT [</font>"+getHTMLHumanReadableLabelForExpression(excludedTerm)+
//                                "<font color=#ff0000> ]</font>";
//                    }
//                    else
//                    {
//                        // add single excluded term
//                        html = html+"<font color=#ff0000> and [</font>"+getHTMLHumanReadableLabelForExpression(excludedTerms.get(counter))+
//                                "<font color=#ff0000> ]</font>";
//                    }
//
//                    counter++;
//                }
//            }
//
//            return html;
//        }
//        else
//        {
//            throw new IllegalArgumentException("Sub Query passed can not be null.");
//        }
//    }

    /**
     * Gets the hTML human readable label for expression.
     *
     * @param componentExpression the component expression
     * @return the hTML human readable label for expression
     */
    public String getHTMLHumanReadableLabelForExpression(QueryComponentExpression componentExpression){
        if(componentExpression != null)
        {
            String html = getHTMLHumanReadableLabelForExpression(componentExpression.getIncludedConstraint());
            // add exclusion terms text
            List<TerminologyConstraint> excludedConstraints = new ArrayList<TerminologyConstraint>(componentExpression.getExcludedConstraints());
             if(excludedConstraints.size() == 1)
            {
                // add single excluded term
                html = html+"<font color=#ff0000> EXCEPT [</font>"+getHTMLHumanReadableLabelForExpression(excludedConstraints.get(0))+
                        "<font color=#ff0000> ]</font>";
            }
            else if(excludedConstraints.size() >1)
            {
                int counter = 0;
                for(TerminologyConstraint excludedTerm : excludedConstraints)
                {
                    if(counter == 0)
                    {
                        // add single excluded term
                        html = html+"<font color=#ff0000> EXCEPT [</font>"+getHTMLHumanReadableLabelForExpression(excludedTerm)+
                                "<font color=#ff0000> ]</font>";
                    }
                    else
                    {
                        // add single excluded term
                        html = html+"<font color=#ff0000> and [</font>"+getHTMLHumanReadableLabelForExpression(excludedConstraints.get(counter))+
                                "<font color=#ff0000> ]</font>";
                    }

                    counter++;
                }
            }

            return html;
        }
        else
        {
            throw new IllegalArgumentException("Component Query passed can not be null.");
        }
    }

    /**
     * Gets the hTML human readable label for expression.
     *
     * @param intersectionObject the intersection object
     *
     * @return the hTML human readable label for expression
     */
    public String getHTMLHumanReadableLabelForExpression(QueryIntersectionExpression intersectionObject){
        if(intersectionObject != null)
        {
            // loop through contained expressions and concat their labels with AND
            String label = "";
            int counter =0;
            for(QueryExpression exp : intersectionObject.getContainedExpressions())
            {
                if (counter >0)
                {
                    label = label + "<font color =\"#8C1B66\"><b> "+intersectionObject.getOperator().toString()+" </b></font>"
                            + getHTMLHumanReadableLabelForExpression(exp);
                }
                else
                {
                    label = getHTMLHumanReadableLabelForExpression(exp);
                }

                counter++;
            }
            label = "<font color =\"#3B82FE\"><b>(</b></font>"+label+"<font color =\"#3B82FE\"><b>)</b></font>";

            return label;
        }
        else
        {
            throw new IllegalArgumentException("Intersection object passed can not be null.");
        }
    }

    /**
     * Gets the hTML human readable label for expression.
     *
     * @param unionObject the union object
     *
     * @return the hTML human readable label for expression
     */
    public String getHTMLHumanReadableLabelForExpression(QueryUnionExpression unionObject){
        if(unionObject != null)
        {
            // loop through contained expressions and concat their labels with AND
            String label = "";
            int counter =0;
            for(QueryExpression exp : unionObject.getContainedExpressions())
            {
                if (counter >0)
                {
                    label = label + "<font color =\"#8C1B66\"><b> OR </b></font>"
                            + getHTMLHumanReadableLabelForExpression(exp);
                }
                else
                {
                    label = getHTMLHumanReadableLabelForExpression(exp);
                }

                counter++;
            }
            label = "<font color =\"#3B82FE\"><b>(</b></font>"+label+"<font color =\"#3B82FE\"><b>)</b></font>";

            return label;
        }
        else
        {
            throw new IllegalArgumentException("Union object passed can not be null.");
        }
    }

//    /**
//     * Gets the hTML human readable label for expression.
//     *
//     * @param queryTerm the query term
//     *
//     * @return the hTML human readable label for expression
//     */
//    public String getHTMLHumanReadableLabelForExpression(SnomedQueryTerm queryTerm){
//        if(queryTerm != null)
//        {
//            String text = "<i><font color=#eee>"+queryTerm.getTermLabel()+"</font></i>";
//            SnomedQueryTerm.SubSumptionStrategy strategy = queryTerm.getSubSumptionStrategy();
//            // add subsumption strategy
//            if(strategy == SnomedQueryTerm.SubSumptionStrategy.ALL_DESCENDANTS)
//            {
//                text = text+"<font color=#C04838> or ANY of it its types</font>";
//            }
//            else
//            {
//                text = text+"<font color=#C04838> without ANY if its types</font>";
//
//            }
//
//            return text;
//        }
//        else
//        {
//            throw new IllegalArgumentException("Query Term passed can not be null.");
//        }
//    }

    /**
     * Gets the hTML human readable label for expression.
     *
     * @param terminologyConstraint the terminology constraint
     * @return the hTML human readable label for expression
     */
    public String getHTMLHumanReadableLabelForExpression(TerminologyConstraint terminologyConstraint){
        if(terminologyConstraint != null)
        {
            String label = humanReadableRender.getHumanReadableLabel(terminologyConstraint.getExpression());
            String text = "<i><font color=#eee>"+label+"</font></i>";
            SubsumptionVocabulary strategy = terminologyConstraint.getSubsumption();
            // add subsumption strategy
            if(SubsumptionVocabulary.SELF_OR_ANY_TYPE_OF == strategy)
            {
                text = text+"<font color=#C04838> or ANY of it its types</font>";
            }
            else if(SubsumptionVocabulary.ANY_TYPE_OF_BUT_NOT_SELF == strategy)
            {
                text = text+"<font color=#C04838> ANY if its types EXCEPT self</font>";

            }
            else
            {
                text = text+"<font color=#C04838> without ANY if its types</font>";
            }

            return text;
        }
        else
        {
            throw new IllegalArgumentException("Terminology constraint passed can not be null.");
        }
    }
}
