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
package uk.nhs.cfh.dsp.snomed.normaliser.impl;

import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.NormalFormExpressionImpl;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;

import java.util.ArrayList;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 12-Mar-2010 at 15:27:20
 */
public abstract class AbstractNormalFormGenerator implements NormalFormGenerator {

    /** The normal form hierarchy provider. */
    protected NormalFormHierarchyProvider normalFormHierarchyProvider;
    
    /** The terminology concept dao. */
    protected TerminologyConceptDAO terminologyConceptDAO;
    
    /** The expression comparator. */
    protected ExpressionComparator expressionComparator;

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getShortNormalFormExpression(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    public  NormalFormExpression getShortNormalFormExpression(CloseToUserExpression closeToUserExpression){
        return (getShortNormalFormExpression(getLongNormalFormExpression(closeToUserExpression)));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getShortNormalFormExpression(uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression)
     */
    public abstract NormalFormExpression getShortNormalFormExpression(NormalFormExpression longNormalFormExpression);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getShortNormalFormExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public NormalFormExpression getShortNormalFormExpression(SnomedConcept concept){
        if (! concept.isPrimitive())
        {
            return getShortNormalFormExpression(getLongNormalFormExpression(concept));
        } else {
            return new NormalFormExpressionImpl(concept);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getExpressionWithShortNormalFormAsValue(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup)
     */
    public abstract SnomedRoleGroupExpression getExpressionWithShortNormalFormAsValue(SnomedRoleGroup roleGroup);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getExpressionWithShortNormalFormAsValue(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship)
     */
    public abstract SnomedRelationshipPropertyExpression getExpressionWithShortNormalFormAsValue(SnomedRelationship relationship);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getLongNormalFormExpressionForRendering(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public abstract NormalFormExpression getLongNormalFormExpressionForRendering(SnomedConcept concept);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getLongNormalFormExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public abstract NormalFormExpression getLongNormalFormExpression(SnomedConcept concept);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getExpressionWithLongNormalFormAsValue(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup)
     */
    public abstract Expression getExpressionWithLongNormalFormAsValue(SnomedRoleGroup roleGroup);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getExpressionWithLongNormalFormAsValue(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship)
     */
    public abstract Expression getExpressionWithLongNormalFormAsValue(SnomedRelationship relationship);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getLongNormalFormExpression(java.lang.String)
     */
    public abstract NormalFormExpression getLongNormalFormExpression(String conceptId);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getLongNormalFormExpressionForRendering(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    public abstract NormalFormExpression getLongNormalFormExpressionForRendering(CloseToUserExpression closeToUserExpression);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator#getLongNormalFormExpression(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    public abstract NormalFormExpression getLongNormalFormExpression(CloseToUserExpression closeToUserExpression);

    /**
     * Checks if is subsumption defining relationship.
     *
     * @param relationship the relationship
     * @return true, if is subsumption defining relationship
     */
    protected  boolean isSubsumptionDefiningRelationship(SnomedRelationship relationship) {

        // get relationship type and see if it equals 116680003 |is-a|
        return ConceptType.ATTRIBUTE_IS_A.getID().equals(relationship.getRelationshipType());
    }

    /**
     * Check and return concept for id.
     *
     * @param conceptID the concept id
     * @return the snomed concept
     */
    protected SnomedConcept checkAndReturnConceptForID(String conceptID){

        SnomedConcept concept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, conceptID);
        if(concept != null)
        {
            return concept;
        }
        else
        {
            return null;
        }
    }

    /**
     * Gets the contained relationship property expressions.
     *
     * @param roleGroupExpressions the role group expressions
     * @return the contained relationship property expressions
     */
    protected Collection<SnomedRelationshipPropertyExpression> getContainedRelationshipPropertyExpressions(
            Collection<SnomedRoleGroupExpression> roleGroupExpressions) {
        Collection<SnomedRelationshipPropertyExpression> containedExpressions = new ArrayList<SnomedRelationshipPropertyExpression>();
        // loop through roleGroupExpressions  and add contained expressions
        for(SnomedRoleGroupExpression roleGroupExpression : roleGroupExpressions)
        {
            containedExpressions.addAll(getContainedRelationshipPropertyExpressions(roleGroupExpression));
        }

        return containedExpressions;
    }

    /**
     * Gets the contained relationship property expressions.
     *
     * @param roleGroupExpression the role group expression
     * @return the contained relationship property expressions
     */
    protected Collection<SnomedRelationshipPropertyExpression> getContainedRelationshipPropertyExpressions(
            SnomedRoleGroupExpression roleGroupExpression){
        Collection<SnomedRelationshipPropertyExpression> containedExpressions =
                new ArrayList<SnomedRelationshipPropertyExpression>();
        for(Expression childExpression : roleGroupExpression.getChildExpressions())
        {
            if(childExpression instanceof SnomedRelationshipPropertyExpression)
            {
                containedExpressions.add((SnomedRelationshipPropertyExpression) childExpression);
            }
        }

        return containedExpressions;
    }

    /**
     * Sets the normal form hierarchy provider.
     *
     * @param normalFormHierarchyProvider the new normal form hierarchy provider
     */
    public void setNormalFormHierarchyProvider(NormalFormHierarchyProvider normalFormHierarchyProvider) {
        this.normalFormHierarchyProvider = normalFormHierarchyProvider;
    }

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Sets the expression comparator.
     *
     * @param expressionComparator the new expression comparator
     */
    public void setExpressionComparator(ExpressionComparator expressionComparator) {
        this.expressionComparator = expressionComparator;
    }
}
