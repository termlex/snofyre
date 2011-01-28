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
/*
 * 
 */
package uk.nhs.cfh.dsp.srth.query.model.om.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.SubsumptionVocabulary;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.ConstraintFactoryImpl;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 5:45:13 PM
 */
public class QueryExpressionFactoryImpl implements QueryExpressionFactory {

    /** The constraint factory. */
    private ConstraintFactory constraintFactory;

    /**
     * no args constructor for IOC.
     */
    public QueryExpressionFactoryImpl() {
        constraintFactory = new ConstraintFactoryImpl();
    }

    /**
     * Instantiates a new query expression factory impl.
     * 
     * @param constraintFactory the constraint factory
     */
    public QueryExpressionFactoryImpl(ConstraintFactory constraintFactory) {
        this.constraintFactory = constraintFactory;
    }

    /**
     * Gets the query intersection expression.
     * 
     * @return the query intersection expression
     */
    public QueryIntersectionExpression getQueryIntersectionExpression(){
        return new QueryIntersectionExpressionImpl();
    }

    /**
     * Gets the query intersection expression.
     * 
     * @param expressions the expressions
     * 
     * @return the query intersection expression
     */
    public QueryIntersectionExpression getQueryIntersectionExpression(Collection<QueryExpression> expressions){
        QueryIntersectionExpression expression = new QueryIntersectionExpressionImpl();
        for(QueryExpression e : expressions)
        {
            expression.addChildExpression(e);
        }

        return expression;
    }

    /**
     * Gets the query union expression.
     * 
     * @return the query union expression
     */
    public QueryUnionExpression getQueryUnionExpression(){
        return new QueryUnionExpressionImpl();
    }

    /**
     * Gets the query union expression.
     * 
     * @param expressions the expressions
     * 
     * @return the query union expression
     */
    public QueryUnionExpression getQueryUnionExpression(Collection<QueryExpression> expressions){
        QueryUnionExpression unionExpression = new QueryUnionExpressionImpl();
        for(QueryExpression e: expressions)
        {
            unionExpression.addChildExpression(e);
        }

        return unionExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory#getQueryComponentExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public QueryComponentExpression getQueryComponentExpression(SnomedConcept concept){
        return getQueryComponentExpression(concept, "EXPRESSION");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory#getQueryComponentExpression(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    public QueryComponentExpression getQueryComponentExpression(CloseToUserExpression expression){
        return getQueryComponentExpression(expression, "EXPRESSION");
    }

    /**
     * Gets the query component expression.
     * 
     * @param concept the concept
     * @param name the name
     * 
     * @return the query component expression
     */
    public QueryComponentExpression getQueryComponentExpression(SnomedConcept concept , String name){
        return getQueryComponentExpression(new CloseToUserExpressionImpl(concept), name);
    }

    /**
     * Gets the query component expression.
     * 
     * @param expression the expression
     * @param name the name
     * 
     * @return the query component expression
     */
    public QueryComponentExpression getQueryComponentExpression(CloseToUserExpression expression , String name){
        return getQueryComponentExpression(expression, SubsumptionVocabulary.SELF_OR_ANY_TYPE_OF, name);
    }

    /**
     * Gets the query component expression.
     * 
     * @param expression the expression
     * @param subsumptionVocabulary the subsumption vocabulary
     * @param name the name
     * 
     * @return the query component expression
     */
    public QueryComponentExpression getQueryComponentExpression(CloseToUserExpression expression,
                                                                SubsumptionVocabulary subsumptionVocabulary,
                                                                String name){

        TerminologyConstraint constraint = constraintFactory.getTerminologyConstraint(expression, name);
        constraint.setSubsumption(subsumptionVocabulary);
        constraint.setName(name);
        return new QueryComponentExpressionImpl(constraint);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory#getQueryStatement()
     */
    public QueryStatement getQueryStatement(){
        return new QueryStatementImpl();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory#getQueryComponentExpression()
     */
    public QueryComponentExpression getQueryComponentExpression(){
        return new QueryComponentExpressionImpl();
    }

    /**
     * Gets the constraint factory.
     * 
     * @return the constraint factory
     */
    public ConstraintFactory getConstraintFactory() {
        return constraintFactory;
    }

    /**
     * Sets the constraint factory.
     * 
     * @param constraintFactory the new constraint factory
     */
    public void setConstraintFactory(ConstraintFactory constraintFactory) {
        this.constraintFactory = constraintFactory;
    }
}
