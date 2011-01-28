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
package uk.nhs.cfh.dsp.srth.query.model.om;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.SubsumptionVocabulary;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a factory object that returns
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression} objects.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 5:44:31 PM
 */
public interface QueryExpressionFactory {


    /**
     * Gets the query intersection expression.
     *
     * @return the query intersection expression
     */
    QueryIntersectionExpression getQueryIntersectionExpression();

    /**
     * Gets the query intersection expression.
     *
     * @param expressions the expressions
     *
     * @return the query intersection expression
     */
    QueryIntersectionExpression getQueryIntersectionExpression(Collection<QueryExpression> expressions);

    /**
     * Gets the query union expression.
     *
     * @return the query union expression
     */
    QueryUnionExpression getQueryUnionExpression();

    /**
     * Gets the query union expression.
     *
     * @param expressions the expressions
     *
     * @return the query union expression
     */
    QueryUnionExpression getQueryUnionExpression(Collection<QueryExpression> expressions);

    /**
     * Gets the query component expression.
     *
     * @param concept the concept
     * @param name the name
     *
     * @return the query component expression
     */
    QueryComponentExpression getQueryComponentExpression(SnomedConcept concept , String name);

    /**
     * Gets the query component expression.
     *
     * @param expression the expression
     * @param name the name
     *
     * @return the query component expression
     */
    QueryComponentExpression getQueryComponentExpression(CloseToUserExpression expression , String name);

    /**
     * Gets the query component expression.
     *
     * @param expression the expression
     * @param subsumptionVocabulary the subsumption vocabulary
     * @param name the name
     *
     * @return the query component expression
     */
    QueryComponentExpression getQueryComponentExpression(CloseToUserExpression expression,
                                                         SubsumptionVocabulary subsumptionVocabulary,
                                                         String name);

    /**
     * Gets the constraint factory.
     *
     * @return the constraint factory
     */
    ConstraintFactory getConstraintFactory();

    /**
     * Sets the constraint factory.
     *
     * @param constraintFactory the new constraint factory
     */
    void setConstraintFactory(ConstraintFactory constraintFactory);

    /**
     * Gets the query component expression.
     *
     * @return the query component expression
     */
    QueryComponentExpression getQueryComponentExpression();

    /**
     * Gets the query statement.
     *
     * @return the query statement
     */
    QueryStatement getQueryStatement();

    /**
     * Gets the query component expression.
     *
     * @param concept the concept
     * @return the query component expression
     */
    QueryComponentExpression getQueryComponentExpression(SnomedConcept concept);

    /**
     * Gets the query component expression.
     *
     * @param expression the expression
     * @return the query component expression
     */
    QueryComponentExpression getQueryComponentExpression(CloseToUserExpression expression);
}
