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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;
import uk.nhs.cfh.dsp.snomed.expression.model.*;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.*;
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRoleGroupImpl;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 19, 2009 at 1:57:50 PM
 */
public class NormalFormGeneratorImpl extends AbstractNormalFormGenerator {

    /** The logger. */
    private static Log logger = LogFactory.getLog(NormalFormGeneratorImpl.class);

    /**
     * Instantiates a new normal form generator impl.
     *
     * @param normalFormHierarchyProvider the normal form hierarchy provider
     * @param terminologyConceptDAO the terminology concept dao
     * @param expressionComparator the expression comparator
     */
    public NormalFormGeneratorImpl(NormalFormHierarchyProvider normalFormHierarchyProvider,
                                   TerminologyConceptDAO terminologyConceptDAO,
                                   ExpressionComparator expressionComparator) {
        this.normalFormHierarchyProvider = normalFormHierarchyProvider;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.expressionComparator = expressionComparator;
    }

    /**
     * Empty constructor for IOC.
     */
    public NormalFormGeneratorImpl() {
        // empty constructor
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getShortNormalFormExpression(uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression)
     */
    @Override
    public  NormalFormExpression getShortNormalFormExpression(NormalFormExpression longNormalFormExpression){

        Collection<SnomedConcept> focusConcepts = longNormalFormExpression.getFocusConcepts();
        NormalFormExpression shortNormalFormExpression = new NormalFormExpressionImpl(focusConcepts);
        Collection<SnomedRoleGroupExpression> mergedRoleGroups = new HashSet<SnomedRoleGroupExpression>();
        Collection<SnomedRelationshipPropertyExpression> mergedRelationships = new HashSet<SnomedRelationshipPropertyExpression>();
        Map<SnomedConcept, Collection<SnomedRelationshipPropertyExpression>> conceptRelMap = new HashMap<SnomedConcept, Collection<SnomedRelationshipPropertyExpression>>();
        Map<SnomedConcept, Collection<SnomedRoleGroupExpression>> conceptRoleMap = new HashMap<SnomedConcept, Collection<SnomedRoleGroupExpression>>();
        for(SnomedConcept focusConcept : focusConcepts)
        {
            NormalFormExpression innerNFE = getLongNormalFormExpression(focusConcept);
            // set values in conceptRelMap and conceptRoleMap
            conceptRelMap.put(focusConcept, innerNFE.getRelationshipExpressions());
            conceptRoleMap.put(focusConcept, innerNFE.getRoleGroupExpressions());
        }

        // merge definitions of focus concepts
        List<SnomedConcept> conceptList = new ArrayList<SnomedConcept>(focusConcepts);
        if(conceptList.size() >1)
        {
            for(int i=0; i< conceptList.size(); i++)
            {
                for(int j=i+1; j<conceptList.size(); j++)
                {
                    Collection<SnomedRoleGroupExpression> roleGroups1 = conceptRoleMap.get(conceptList.get(i));
                    Collection<SnomedRoleGroupExpression> roleGroups2 = conceptRoleMap.get(conceptList.get(j));

                    // merge role groups and add to merged role groups
                    Collection<SnomedRoleGroupExpression> localMergedRoleGroups = getMergedRoleGroups(roleGroups1, roleGroups2);
                    mergedRoleGroups.addAll(localMergedRoleGroups);

                    // merge relationships
                    Collection<SnomedRelationshipPropertyExpression> relationshipSet1 = conceptRelMap.get(conceptList.get(i));
                    Collection<SnomedRelationshipPropertyExpression> relationshipSet2 = conceptRelMap.get(conceptList.get(j));
                    Collection<SnomedRelationshipPropertyExpression> localMergedRelationships =
                            getMergedRelationships(relationshipSet1, relationshipSet2, localMergedRoleGroups);
                    // add to outer mergedRelationships
                    mergedRelationships.addAll(localMergedRelationships);
                }
            }
        }
        else
        {
            mergedRoleGroups.addAll(conceptRoleMap.get(conceptList.get(0)));
            mergedRelationships.addAll(conceptRelMap.get(conceptList.get(0)));
        }

        /*
         now remove redundant relationships and role groups, but this can not be the same algorithm that was used
         for generating long normal forms.
         */
        Collection<SnomedRoleGroupExpression> nonRedundantRoleGroupExpressions =
                getStrictNonRedundantMergedRoleGroups(mergedRoleGroups, longNormalFormExpression.getRoleGroupExpressions());
        Collection<SnomedRelationshipPropertyExpression> nonRedundantPropertyExpressions =
                getStrictNonRedundantMergedRelationships(mergedRelationships, longNormalFormExpression.getRelationshipExpressions());
        logger.debug("nonRedundantPropertyExpressions.size() = " + nonRedundantPropertyExpressions.size());
        logger.debug("nonRedundantRoleGroupExpressions.size() = " + nonRedundantRoleGroupExpressions.size());
        /*
        recursively process rolegroups and property expression for normalised values
        */
        for(SnomedRoleGroupExpression roleGroupExpression : nonRedundantRoleGroupExpressions)
        {
            SnomedRoleGroupExpression snfRoleGroupExpression =
                    getExpressionWithShortNormalFormAsValue(roleGroupExpression);
            shortNormalFormExpression.addRelationshipExpression(snfRoleGroupExpression);
//            snfRoleGroupExpression.addParentExpression(shortNormalFormExpression);
        }

        for(SnomedRelationshipPropertyExpression relationshipPropertyExpression : nonRedundantPropertyExpressions)
        {
            SnomedRelationshipPropertyExpression snfPropertyExpression =
                    getExpressionWithShortNormalFormAsValue(relationshipPropertyExpression);
            shortNormalFormExpression.addRelationshipExpression(snfPropertyExpression);
//            snfPropertyExpression.addParentExpression(shortNormalFormExpression);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("shortNormalFormExpression.getCanonicalStringForm() = " + shortNormalFormExpression.getCanonicalStringForm());
        }
        return shortNormalFormExpression;
    }

    /**
     * Gets the strict non redundant merged role groups.
     *
     * @param parentRoleGroupExpressions the parent role group expressions
     * @param conceptRoleGroupExpressions the concept role group expressions
     * @return the strict non redundant merged role groups
     */
    private Collection<SnomedRoleGroupExpression> getStrictNonRedundantMergedRoleGroups(
            Collection<SnomedRoleGroupExpression> parentRoleGroupExpressions,
            Collection<SnomedRoleGroupExpression> conceptRoleGroupExpressions) {
        if(parentRoleGroupExpressions.size() == 0)
        {
            return conceptRoleGroupExpressions;
        }
        else if(conceptRoleGroupExpressions.size() == 0)
        {
            return parentRoleGroupExpressions;
        }
        else
        {
            Collection<SnomedRoleGroupExpression> groupExpressionsCopy = new ArrayList<SnomedRoleGroupExpression>(conceptRoleGroupExpressions);
            for(SnomedRoleGroupExpression parentRoleGroupExpression : parentRoleGroupExpressions)
            {
                for(SnomedRoleGroupExpression conceptRoleGroupExpression : groupExpressionsCopy)
                {
                    ExpressionComparator.Subsumption_Relation relation = expressionComparator.getSubsumptionRelation(
                            parentRoleGroupExpression, conceptRoleGroupExpression);
                    if(ExpressionComparator.Subsumption_Relation.SAME == relation)
                    {
                        conceptRoleGroupExpressions.remove(conceptRoleGroupExpression);
                    }
                }
            }

            return conceptRoleGroupExpressions;
        }
    }

    /**
     * Gets the strict non redundant merged relationships.
     *
     * @param parentPropertyExpressions the parent property expressions
     * @param conceptPropertyExpressions the concept property expressions
     * @return the strict non redundant merged relationships
     */
    private Collection<SnomedRelationshipPropertyExpression> getStrictNonRedundantMergedRelationships(
            Collection<SnomedRelationshipPropertyExpression> parentPropertyExpressions,
            Collection<SnomedRelationshipPropertyExpression> conceptPropertyExpressions) {
        if(parentPropertyExpressions.size() == 0)
        {
            return conceptPropertyExpressions;
        }
        else if(conceptPropertyExpressions.size() == 0)
        {
            return parentPropertyExpressions;
        }
        else
        {
            Collection<SnomedRelationshipPropertyExpression> relationshipPropertyExpressionCopy =
                    new ArrayList<SnomedRelationshipPropertyExpression>(conceptPropertyExpressions);
            for(SnomedRelationshipPropertyExpression parentExpression : parentPropertyExpressions)
            {
                for(SnomedRelationshipPropertyExpression conceptPropertyExpression : relationshipPropertyExpressionCopy)
                {
                    ExpressionComparator.Subsumption_Relation relation =
                            expressionComparator.getSubsumptionRelation(parentExpression, conceptPropertyExpression);
                    if(ExpressionComparator.Subsumption_Relation.SAME == relation ||
                            ExpressionComparator.Subsumption_Relation.SUBSUMES == relation)
                    {
                        conceptPropertyExpressions.remove(conceptPropertyExpression);
                    }
                }
            }

            return conceptPropertyExpressions;
        }
    }

    /**
     * Gets the expression with short normal form as value.
     *
     * @param roleGroupExpression the role group expression
     * @return the expression with short normal form as value
     */
    public SnomedRoleGroupExpression getExpressionWithShortNormalFormAsValue(
            SnomedRoleGroupExpression roleGroupExpression) {

        if (roleGroupExpression.getChildExpressions().size() > 0)
        {
            SnomedRoleGroupExpression expression = new SnomedRoleGroupExpression(roleGroupExpression.getRoleGroup());
            // create an intersection expression of all relationships in rolegroup
            IntersectionExpression intersectionExpression = new IntersectionExpressionImpl();
            for(Expression childExpression : roleGroupExpression.getChildExpressions())
            {
                if(childExpression instanceof  SnomedRelationshipPropertyExpression)
                {
                    SnomedRelationshipPropertyExpression relationshipPropertyExpression =
                            (SnomedRelationshipPropertyExpression) childExpression;
                    logger.debug("relationshipPropertyExpression.getCanonicalStringForm() = " + relationshipPropertyExpression.getCanonicalStringForm());
                    SnomedRelationshipPropertyExpression childRelationshipPropertyExpression =
                            getExpressionWithShortNormalFormAsValue(relationshipPropertyExpression);
                    logger.debug("childRelationshipPropertyExpression.getCanonicalStringForm() = " + childRelationshipPropertyExpression.getCanonicalStringForm());

                    intersectionExpression.addChildExpression(childRelationshipPropertyExpression);
                    expression.addChildExpression(childRelationshipPropertyExpression);
                }
            }

            // set values for expression
            expression.setExpression(intersectionExpression);
            logger.debug("expression.getCanonicalStringForm() = " + expression.getCanonicalStringForm());

            return expression;
        }
        else
        {
            return getExpressionWithShortNormalFormAsValue(roleGroupExpression.getRoleGroup());
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getExpressionWithShortNormalFormAsValue(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup)
     */
    @Override
    public SnomedRoleGroupExpression getExpressionWithShortNormalFormAsValue(SnomedRoleGroup roleGroup) {

        SnomedRoleGroupExpression snomedRoleGroupExpression = new SnomedRoleGroupExpression(roleGroup);
        IntersectionExpression intersectionExpression = new IntersectionExpressionImpl();
        for(SnomedRelationship relationship : roleGroup.getRelationships())
        {
            SnomedRelationshipPropertyExpression snfPropertyExpression =
                    getExpressionWithShortNormalFormAsValue(relationship);
            // add to contained expressions and set as child
            intersectionExpression.addChildExpression(snfPropertyExpression);
            snomedRoleGroupExpression.addChildExpression(snfPropertyExpression);
//            snfPropertyExpression.addParentExpression(snomedRoleGroupExpression);
        }

        // create new intersection object and add contained expressions to it
        snomedRoleGroupExpression.setExpression(intersectionExpression);

        return snomedRoleGroupExpression;
    }

    /**
     * Gets the expression with short normal form as value.
     *
     * @param snomedRelationshipPropertyExpression the snomed relationship property expression
     * @return the expression with short normal form as value
     */
    public SnomedRelationshipPropertyExpression getExpressionWithShortNormalFormAsValue(
            SnomedRelationshipPropertyExpression snomedRelationshipPropertyExpression) {

        SnomedRelationshipPropertyExpression expression = new SnomedRelationshipPropertyExpression();
        expression.setOperator(snomedRelationshipPropertyExpression.getOperator());
        expression.setRelationship(snomedRelationshipPropertyExpression.getRelationship());

        Expression targetExpression = null;
        // check the type of contained expression
        Expression containedExpression = snomedRelationshipPropertyExpression.getExpression();
        if(containedExpression instanceof CloseToUserExpression)
        {
            targetExpression = getShortNormalFormExpression((CloseToUserExpression) containedExpression);
            // set target expression as contained expression
            expression.setExpression(targetExpression);
            expression.addChildExpression(targetExpression);

            return expression;
        }
        else if(containedExpression instanceof NormalFormExpression)
        {
             targetExpression = getShortNormalFormExpression((NormalFormExpression) containedExpression);
            // set target expression as contained expression
            expression.setExpression(targetExpression);
            expression.addChildExpression(targetExpression);

            return expression;
        }
        else
        {
            SnomedRelationship relationship = snomedRelationshipPropertyExpression.getRelationship();
            logger.debug("relationship.getCanonicalStringForm() = " + relationship.getCanonicalStringForm());
            return getExpressionWithShortNormalFormAsValue(relationship);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getExpressionWithShortNormalFormAsValue(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship)
     */
    @Override
    public SnomedRelationshipPropertyExpression getExpressionWithShortNormalFormAsValue(SnomedRelationship relationship) {

        SnomedRelationshipPropertyExpression propertyExpression = new SnomedRelationshipPropertyExpression(relationship);
        // get target concept
        SnomedConcept targetConcept = relationship.getTargetConcept();
        if(targetConcept == null)
        {
            targetConcept = checkAndReturnConceptForID(relationship.getTargetConceptID());
        }

        NormalFormExpression shortTargetExpression = getShortNormalFormExpression(targetConcept);
        // set as expression in propertyExpression
        propertyExpression.setExpression(shortTargetExpression);
        propertyExpression.addChildExpression(shortTargetExpression);

        return propertyExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getLongNormalFormExpressionForRendering(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    @Override
    public  NormalFormExpression getLongNormalFormExpressionForRendering(SnomedConcept concept){
        return getLongNormalFormExpression(concept, true);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getLongNormalFormExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    @Override
    public  NormalFormExpression getLongNormalFormExpression(SnomedConcept concept) {
        return getLongNormalFormExpression(concept, false);
    }

    /**
     * Gets the long normal form expression.
     *
     * @param concept the concept
     * @param addRefiningProperties the add refining properties
     * @return the long normal form expression
     */
    private  NormalFormExpression getLongNormalFormExpression(SnomedConcept concept, boolean addRefiningProperties){

        return getLongNormalFormExpression(Collections.singleton(concept),
                Collections.<SnomedRelationshipPropertyExpression>emptySet(),
                Collections.<SnomedRoleGroupExpression>emptySet(),addRefiningProperties);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getExpressionWithLongNormalFormAsValue(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup)
     */
    @Override
    public Expression getExpressionWithLongNormalFormAsValue(SnomedRoleGroup roleGroup) {
        return getExpressionWithLongNormalFormAsValue(roleGroup, false);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getExpressionWithLongNormalFormAsValue(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship)
     */
    @Override
    public Expression getExpressionWithLongNormalFormAsValue(SnomedRelationship relationship) {
        return getExpressionWithLongNormalFormAsValue(relationship, false);
    }

    /**
     * Gets the expression with long normal form as value.
     *
     * @param roleGroup the role group
     * @param addRefiningProperties the add refining properties
     * @return the expression with long normal form as value
     */
    private SnomedRoleGroupExpression getExpressionWithLongNormalFormAsValue(SnomedRoleGroup roleGroup, boolean addRefiningProperties) {
        return getExpressionWithLongNormalFormAsValue(roleGroup,
                Collections.<SnomedRelationshipPropertyExpression>emptySet(),
                Collections.<SnomedRoleGroupExpression>emptySet(),
                addRefiningProperties);
    }

    /**
     * Gets the expression with long normal form as value.
     *
     * @param relationship the relationship
     * @param addRefiningProperties the add refining properties
     * @return the expression with long normal form as value
     */
    private SnomedRelationshipPropertyExpression getExpressionWithLongNormalFormAsValue(SnomedRelationship relationship,
                                                                                        boolean addRefiningProperties) {
        return getExpressionWithLongNormalFormAsValue(relationship,
                Collections.<SnomedRelationshipPropertyExpression>emptySet(),
                Collections.<SnomedRoleGroupExpression>emptySet(),
                addRefiningProperties);
    }

    /**
     * Gets the expression with long normal form as value.
     *
     * @param roleGroupExpression the role group expression
     * @param addRefiningProperties the add refining properties
     * @return the expression with long normal form as value
     */
    private SnomedRoleGroupExpression getExpressionWithLongNormalFormAsValue(SnomedRoleGroupExpression roleGroupExpression, boolean addRefiningProperties) {
        return getExpressionWithLongNormalFormAsValue(roleGroupExpression,
                Collections.<SnomedRelationshipPropertyExpression>emptySet(),
                Collections.<SnomedRoleGroupExpression>emptySet(),
                addRefiningProperties);
    }

    /**
     * Gets the expression with long normal form as value.
     *
     * @param relationshipPropertyExpression the relationship property expression
     * @param addRefiningProperties the add refining properties
     * @return the expression with long normal form as value
     */
    private SnomedRelationshipPropertyExpression getExpressionWithLongNormalFormAsValue(SnomedRelationshipPropertyExpression relationshipPropertyExpression,
                                                                                        boolean addRefiningProperties) {
        return getExpressionWithLongNormalFormAsValue(relationshipPropertyExpression,
                Collections.<SnomedRelationshipPropertyExpression>emptySet(),
                Collections.<SnomedRoleGroupExpression>emptySet(),
                addRefiningProperties);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getLongNormalFormExpression(java.lang.String)
     */
    @Override
    public NormalFormExpression getLongNormalFormExpression(String conceptId){

        SnomedConcept concept = checkAndReturnConceptForID(conceptId);
        if(concept != null)
        {
            return getLongNormalFormExpression(concept);
        }
        else
        {
            throw new IllegalArgumentException("Concept Id passed does not exist.");
        }
    }

    /**
     * Gets the non redundant merged role groups.
     *
     * @param mergedRoleGroups the merged role groups
     * @return the non redundant merged role groups
     */
    private  Collection<SnomedRoleGroupExpression>
    getNonRedundantMergedRoleGroups(Collection<SnomedRoleGroupExpression> mergedRoleGroups) {

        Collection<SnomedRoleGroupExpression> nonRedundantRoleGroups = new ArrayList<SnomedRoleGroupExpression>();
        for(SnomedRoleGroupExpression roleGroup : mergedRoleGroups)
        {
            ArrayList<SnomedRelationshipPropertyExpression> relationshipList =
                    new ArrayList<SnomedRelationshipPropertyExpression>(getContainedRelationshipPropertyExpressions(roleGroup));
            SnomedRoleGroup rg = new SnomedRoleGroupImpl();
            SnomedRoleGroupExpression rge = new SnomedRoleGroupExpression(rg);
            for(int i=0; i<relationshipList.size(); i++)
            {
                for(int j=i+1; j<relationshipList.size(); j++)
                {
                    SnomedRelationshipPropertyExpression r1 = relationshipList.get(i);
                    SnomedRelationshipPropertyExpression r2 = relationshipList.get(j);
                    ExpressionComparator.Subsumption_Relation relation =
                            expressionComparator.getSubsumptionRelation(r1, getExpressionWithShortNormalFormAsValue(r2));

                    if(ExpressionComparator.Subsumption_Relation.SUBSUMES == relation)
                    {
                        rg.addRelationship(r2.getRelationship());
                        rge.addChildExpression(r2);
                    }
                    else if(ExpressionComparator.Subsumption_Relation.SUBSUMED_BY == relation)
                    {
                        rg.addRelationship(r1.getRelationship());
                        rge.addChildExpression(r1);
                    }
                    else if(ExpressionComparator.Subsumption_Relation.SAME == relation)
                    {
                        // only add one of r2 or r1
                        rg.addRelationship(r1.getRelationship());
                        rge.addChildExpression(r1);
                    }
                    else
                    {
                        // we add both relationships
                        rg.addRelationship(r1.getRelationship());
                        rg.addRelationship(r2.getRelationship());
                        rge.addChildExpression(r1);
                        rge.addChildExpression(r2);
                    }
                }
            }
            // set role group in rge
            rge.setRoleGroup(rg);
            // add rg to nonRedundantRoleGroups
            nonRedundantRoleGroups.add(rge);
        }

        return nonRedundantRoleGroups;
    }

    /**
     * Gets the non redundant merged relationships.
     *
     * @param relationships the relationships
     * @return the non redundant merged relationships
     */
    private  Collection<SnomedRelationshipPropertyExpression>
    getNonRedundantMergedRelationships(Collection<SnomedRelationshipPropertyExpression> relationships) {

        List<SnomedRelationshipPropertyExpression> relationshipList = new ArrayList<SnomedRelationshipPropertyExpression>(relationships);
        for(int i=0; i<relationshipList.size(); i++)
        {
            for(int j=i+1; j<relationshipList.size() ; j++)
            {
                SnomedRelationshipPropertyExpression r1 = relationshipList.get(i);
                SnomedRelationshipPropertyExpression r2 = relationshipList.get(j);
                ExpressionComparator.Subsumption_Relation relation =
                        expressionComparator.getSubsumptionRelation(r1, getExpressionWithShortNormalFormAsValue(r2));
                if(ExpressionComparator.Subsumption_Relation.SUBSUMED_BY == relation)
                {
                    relationships.remove(r2);
                }
                else if(ExpressionComparator.Subsumption_Relation.SUBSUMES == relation)
                {
                    relationships.remove(r1);
                }
                else if(ExpressionComparator.Subsumption_Relation.SAME == relation)
                {
                    // remove one relationship
                    relationships.remove(r2);
                }
            }
        }

        return relationships;
    }

    /**
     * Gets the merged relationships.
     *
     * @param relationships1 the relationships1
     * @param relationships2 the relationships2
     * @param mergedRoleGroups the merged role groups
     * @return the merged relationships
     */
    private  Collection<SnomedRelationshipPropertyExpression>getMergedRelationships(
            Collection<SnomedRelationshipPropertyExpression> relationships1,
            Collection<SnomedRelationshipPropertyExpression> relationships2,
            Collection<SnomedRoleGroupExpression> mergedRoleGroups) {

        Collection<SnomedRelationshipPropertyExpression> mergedRelationships = new ArrayList<SnomedRelationshipPropertyExpression>();

        // create union set of both relationships1 and relationships2
        mergedRelationships.addAll(relationships1);
        mergedRelationships.addAll(relationships2);

        Collection<SnomedRelationshipPropertyExpression> mergedRelationshipsClone = new ArrayList<SnomedRelationshipPropertyExpression>(mergedRelationships);

        // merge relationships2  with role groups
        for(SnomedRelationshipPropertyExpression r : mergedRelationshipsClone)
        {
            // check if r exists in mergedRoleGroups
            for(SnomedRoleGroupExpression roleGroup : mergedRoleGroups)
            {
                for(SnomedRelationshipPropertyExpression relInRoleGroup : getContainedRelationshipPropertyExpressions(roleGroup))
                {
                    ExpressionComparator.Subsumption_Relation relation =
                            expressionComparator.getSubsumptionRelation(r, getExpressionWithShortNormalFormAsValue(relInRoleGroup));
                    /*
                     if the relation is anything but no relation, we know that the role group either contains
                     the an equivalent or a more specific type of the relationship, so we can remove it from
                     the merged relationships.
                    */
                    if(ExpressionComparator.Subsumption_Relation.NO_RELATION != relation)
                    {
                        if (ExpressionComparator.Subsumption_Relation.SUBSUMED_BY == relation) {
                            // add more specific relationship to roleGroup and remove less specific relationship
                            roleGroup.addChildExpression(r);
                            roleGroup.removeChildExpression(relInRoleGroup);
                        }
                        // remove from mergedRelationships
                        mergedRelationships.remove(r);
                        break;
                    }
                }
            }
        }

        return mergedRelationships;
    }

    /**
     * Gets the merged role groups.
     *
     * @param roleGroups1 the role groups1
     * @param roleGroups2 the role groups2
     * @return the merged role groups
     */
    private  Collection<SnomedRoleGroupExpression> getMergedRoleGroups(Collection<SnomedRoleGroupExpression> roleGroups1,
                                                                       Collection<SnomedRoleGroupExpression> roleGroups2) {

        Collection<SnomedRoleGroupExpression> mergedRoleGroups = new ArrayList<SnomedRoleGroupExpression>();
        if(roleGroups1.size() == 0)
        {
            mergedRoleGroups.addAll(roleGroups2);
        }
        else if(roleGroups2.size() == 0)
        {
            mergedRoleGroups.addAll(roleGroups1);
        }
        else
        {
            for(SnomedRoleGroupExpression rg1: roleGroups1)
            {
                for(SnomedRoleGroupExpression rg2: roleGroups2)
                {
                    for(SnomedRelationshipPropertyExpression r1 : getContainedRelationshipPropertyExpressions(rg1))
                    {
                        for(SnomedRelationshipPropertyExpression r2 : getContainedRelationshipPropertyExpressions(rg2))
                        {
                            ExpressionComparator.Subsumption_Relation relation =
                                    expressionComparator.getSubsumptionRelation(r1, getExpressionWithShortNormalFormAsValue(r2));

                            if(ExpressionComparator.Subsumption_Relation.SUBSUMED_BY == relation ||
                                    ExpressionComparator.Subsumption_Relation.SUBSUMES == relation)
                            {
                                // create a new role group and add relationships to it
                                SnomedRoleGroup roleGroup = new SnomedRoleGroupImpl();
                                roleGroup.addRelationship(r1.getRelationship());
                                roleGroup.addRelationship(r2.getRelationship());
                                roleGroup.setRelationshipGroupId(String.valueOf(mergedRoleGroups.size()));

                                // add new role group to mergedRoleGroups
                                mergedRoleGroups.add(new SnomedRoleGroupExpression(roleGroup));
                                break;
                            }
                            else if(ExpressionComparator.Subsumption_Relation.SAME == relation)
                            {
                                // add just one role group -- in this case we choose r1
                                mergedRoleGroups.add(rg1);
                                break;
                            }
                            else
                            {
                                // add to merged roleGroups
                                mergedRoleGroups.add(rg1);
                                mergedRoleGroups.add(rg2);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return mergedRoleGroups;
    }

    /**
     * Gets the long normal form expression.
     *
     * @param focusConcepts the focus concepts
     * @param relationshipPropertyExpressions the relationship property expressions
     * @param roleGroupExpressions the role group expressions
     * @param addRefiningProperties the add refining properties
     * @return the long normal form expression
     */
    private  NormalFormExpression getLongNormalFormExpression(Collection<SnomedConcept> focusConcepts,
                                                              Collection<SnomedRelationshipPropertyExpression> relationshipPropertyExpressions,
                                                              Collection<SnomedRoleGroupExpression> roleGroupExpressions,
                                                              boolean addRefiningProperties){
        NormalFormExpression normalFormExpression = new NormalFormExpressionImpl();
        Collection<SnomedRoleGroupExpression> mergedRoleGroups = new ArrayList<SnomedRoleGroupExpression>();
        Collection<SnomedRelationshipPropertyExpression> mergedRelationships = new ArrayList<SnomedRelationshipPropertyExpression>();
        Map<SnomedConcept, Collection<SnomedRelationshipPropertyExpression>> conceptRelMap = new HashMap<SnomedConcept, Collection<SnomedRelationshipPropertyExpression>>();
        Map<SnomedConcept, Collection<SnomedRoleGroupExpression>> conceptRoleMap = new HashMap<SnomedConcept, Collection<SnomedRoleGroupExpression>>();

        // get and add proximalPrimitives
        Set<SnomedConcept> proximalPrimitives = new HashSet<SnomedConcept>();
        for(SnomedConcept focusConcept : focusConcepts)
        {
            proximalPrimitives.addAll(normalFormHierarchyProvider.getProximalPrimitivesParents(focusConcept));
        }
        normalFormExpression.setProximalPrimitives(proximalPrimitives);
        normalFormExpression.setFocusConcepts(proximalPrimitives);

        for (SnomedConcept conceptToProcess : focusConcepts)
        {
            Collection<SnomedRelationshipPropertyExpression> rels = new ArrayList<SnomedRelationshipPropertyExpression>();
            Collection<SnomedRoleGroupExpression> rols = new ArrayList<SnomedRoleGroupExpression>();
            for(SnomedRelationship relationship : conceptToProcess.getNonSubSumptionDefiningRelationships())
            {
                // create new SnomedRelationshipPropertyExpression 
                rels.add(getExpressionWithLongNormalFormAsValue(relationship, addRefiningProperties));
            }

            if (addRefiningProperties)
            {
                for(SnomedRelationship refiningRelationship : conceptToProcess.getRefiningRelationships())
                {
                    // create new SnomedRelationshipPropertyExpression
                    rels.add(getExpressionWithLongNormalFormAsValue(refiningRelationship, addRefiningProperties));
                }
            }

            conceptRelMap.put(conceptToProcess, rels);

            // add roleGroups
            for(SnomedRoleGroup roleGroup : conceptToProcess.getRoleGroups())
            {
                // create and add roleGroupExpression  to rols
                rols.add(getExpressionWithLongNormalFormAsValue(roleGroup, addRefiningProperties));
            }
            conceptRoleMap.put(conceptToProcess, rols);
        }

        // merge role groups
        List<SnomedConcept> conceptList = new ArrayList<SnomedConcept>(focusConcepts);
        if(conceptList.size() >1)
        {
            for(int i=0; i< conceptList.size(); i++)
            {
                for(int j=i+1; j<conceptList.size(); j++)
                {
                    Collection<SnomedRoleGroupExpression> roleGroups1 = conceptRoleMap.get(conceptList.get(i));
                    Collection<SnomedRoleGroupExpression> roleGroups2 = conceptRoleMap.get(conceptList.get(j));

                    // merge role groups and add to merged role groups
                    Collection<SnomedRoleGroupExpression> localMergedRoleGroups = getMergedRoleGroups(roleGroups1, roleGroups2);
                    mergedRoleGroups.addAll(localMergedRoleGroups);

                    // merge relationships
                    Collection<SnomedRelationshipPropertyExpression> relationshipSet1 = conceptRelMap.get(conceptList.get(i));
                    Collection<SnomedRelationshipPropertyExpression> relationshipSet2 = conceptRelMap.get(conceptList.get(j));
                    Collection<SnomedRelationshipPropertyExpression> localMergedRelationships =
                            getMergedRelationships(relationshipSet1, relationshipSet2, localMergedRoleGroups);
                    // add to outer mergedRelationships
                    mergedRelationships.addAll(localMergedRelationships);
                }
            }
        }
        else
        {
            mergedRoleGroups.addAll(conceptRoleMap.get(conceptList.get(0)));
            mergedRelationships.addAll(conceptRelMap.get(conceptList.get(0)));
        }

        Collection<SnomedRoleGroupExpression> nonRedundantRoleGroups = getNonRedundantMergedRoleGroups(mergedRoleGroups);
        Collection<SnomedRelationshipPropertyExpression> nonRedundantRelationships = getNonRedundantMergedRelationships(mergedRelationships);

        // loop through refinements supplied (relationshipPropertyExpressions  and roleGroupExpressions) and apply them
        for (SnomedConcept focusConcept : focusConcepts)
        {
            Collection<SnomedRelationshipPropertyExpression> localRefinements = new ArrayList<SnomedRelationshipPropertyExpression>();
            for(SnomedRelationshipPropertyExpression relationshipRefinement : relationshipPropertyExpressions)
            {
                String attributeType = relationshipRefinement.getRelationship().getRelationshipType();
                if(ConceptType.ATTRIBUTE_LATERALITY.getID().equalsIgnoreCase(attributeType))
                {
                    applyLateralityToAppropriateBodyPart(normalFormExpression, nonRedundantRelationships, nonRedundantRoleGroups, relationshipRefinement);
                }
                else if(ConceptType.SITUATION_WEC == focusConcept.getType())
                {
                    /*
                     Check if attribute type is any of the situation model sanctioned types. If so,
                     we just add them to local refinements to be applied. If not, we check if the
                     attribute is not a laterality attribute and apply it on the associated focus finding or procedure
                     Note that we do not have to process a laterality attribute because it should have been processed
                     by the previous step.
                    */

                    if(ConceptType.ATTRIBUTE_SUBJECT_RELATIONSHIP_CONTEXT.getID().equalsIgnoreCase(attributeType) ||
                            ConceptType.ATTRIBUTE_FINDING_CONTEXT.getID().equalsIgnoreCase(attributeType) ||
                            ConceptType.ATTRIBUTE_TEMPORAL_CONTEXT.getID().equalsIgnoreCase(attributeType) ||
                            ConceptType.ATTRIBUTE_ASSOCIATED_FINDING.getID().equalsIgnoreCase(attributeType)
                            || ConceptType.ATTRIBUTE_PROCEDURE_CONTEXT.getID().equalsIgnoreCase(attributeType))
                    {
                        // add to localRefinements and resolve as usual
                        localRefinements.add(relationshipRefinement);
                    }
                    else if(ConceptType.ATTRIBUTE_LATERALITY.getID().equalsIgnoreCase(attributeType))
                    {
                        checkAndApplyRefinementToFocusConcept(nonRedundantRoleGroups, relationshipRefinement);
                    }
                }
                else
                {
                    localRefinements.add(relationshipRefinement);
                }
            }

            if (localRefinements.size() >0)
            {
                // merge localRefinements with nonRedundantRelationships
                Collection<SnomedRelationshipPropertyExpression> localMergedRelationships = getMergedRelationships(localRefinements,
                        nonRedundantRelationships, nonRedundantRoleGroups);

                // clear and reset nonRedundantRelationships
                nonRedundantRelationships.clear();
                nonRedundantRelationships.addAll(getNonRedundantMergedRelationships(localMergedRelationships));
            }
        }

        // create expressions for nonRedundantRelationships  and nonRedundantRoleGroups  and add to normalFormExpression
        for(SnomedRelationshipPropertyExpression relationshipExpression : nonRedundantRelationships)
        {
//            relationshipExpression.addParentExpression(normalFormExpression);
            normalFormExpression.addRelationshipExpression(relationshipExpression);
        }

        for(SnomedRoleGroupExpression roleGroupExpression : nonRedundantRoleGroups)
        {
//            roleGroupExpression.addParentExpression(normalFormExpression);
            normalFormExpression.addRelationshipExpression(roleGroupExpression);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("normalFormExpression.getCanonicalStringForm() = " + normalFormExpression.getCanonicalStringForm());
        }

        return normalFormExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getLongNormalFormExpressionForRendering(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    @Override
    public synchronized NormalFormExpression getLongNormalFormExpressionForRendering(CloseToUserExpression closeToUserExpression){
        return getLongNormalFormExpression(closeToUserExpression, true);
    }

    /**
     * Gets the expression with long normal form as value.
     *
     * @param roleGroup the role group
     * @param relationshipPropertyExpressions the relationship property expressions
     * @param roleGroupExpressions the role group expressions
     * @param addRefiningProperties the add refining properties
     * @return the expression with long normal form as value
     */
    private  SnomedRoleGroupExpression getExpressionWithLongNormalFormAsValue(SnomedRoleGroup roleGroup,
                                                                              Collection<SnomedRelationshipPropertyExpression> relationshipPropertyExpressions,
                                                                              Collection<SnomedRoleGroupExpression> roleGroupExpressions,
                                                                              boolean addRefiningProperties) {

        SnomedRoleGroupExpression roleGroupExpression = new SnomedRoleGroupExpression(roleGroup);

        // create an intersection expression of all relationships in rolegroup
        IntersectionExpression intersectionExpression = new IntersectionExpressionImpl();
        for(SnomedRelationship relationship : roleGroup.getRelationships())
        {
            PropertyExpression relExpression = getExpressionWithLongNormalFormAsValue(relationship,
                    relationshipPropertyExpressions,
                    roleGroupExpressions,
                    addRefiningProperties);
            intersectionExpression.addChildExpression(relExpression);
            roleGroupExpression.addChildExpression(relExpression);
        }

        // set values for expression
        roleGroupExpression.setProperty(new SnomedRoleGroupProperty());
        roleGroupExpression.setExpression(intersectionExpression);

        return roleGroupExpression;
    }

    /**
     * Gets the expression with long normal form as value.
     *
     * @param roleGroupExpression the role group expression
     * @param relationshipPropertyExpressions the relationship property expressions
     * @param roleGroupExpressions the role group expressions
     * @param addRefiningProperties the add refining properties
     * @return the expression with long normal form as value
     */
    private  SnomedRoleGroupExpression getExpressionWithLongNormalFormAsValue(SnomedRoleGroupExpression roleGroupExpression,
                                                                              Collection<SnomedRelationshipPropertyExpression> relationshipPropertyExpressions,
                                                                              Collection<SnomedRoleGroupExpression> roleGroupExpressions,
                                                                              boolean addRefiningProperties) {

        if (roleGroupExpression.getChildExpressions().size() > 0)
        {
            // create an intersection expression of all relationships in rolegroup
            IntersectionExpression intersectionExpression = new IntersectionExpressionImpl();
            for(Expression childExpression : roleGroupExpression.getChildExpressions())
            {
                if(childExpression instanceof  SnomedRelationshipPropertyExpression)
                {
                    SnomedRelationshipPropertyExpression relationshipPropertyExpression =
                            (SnomedRelationshipPropertyExpression) childExpression;
                    SnomedRelationshipPropertyExpression childRelationshipPropertyExpression =
                            getExpressionWithLongNormalFormAsValue(relationshipPropertyExpression,
                                    relationshipPropertyExpressions, roleGroupExpressions, addRefiningProperties);

                    intersectionExpression.addChildExpression(childRelationshipPropertyExpression);
                    roleGroupExpression.addChildExpression(childRelationshipPropertyExpression);
                }
            }

            // set values for expression
            roleGroupExpression.setProperty(new SnomedRoleGroupProperty());
            roleGroupExpression.setExpression(intersectionExpression);

            return roleGroupExpression;
        }
        else
        {
            return getExpressionWithLongNormalFormAsValue(roleGroupExpression.getRoleGroup(),
                    relationshipPropertyExpressions, roleGroupExpressions, addRefiningProperties);
        }
    }

    /**
     * Gets the expression with long normal form as value.
     *
     * @param relationship the relationship
     * @param relationshipPropertyExpressions the relationship property expressions
     * @param roleGroupExpressions the role group expressions
     * @param addRefiningProperties the add refining properties
     * @return the expression with long normal form as value
     */
    private  SnomedRelationshipPropertyExpression getExpressionWithLongNormalFormAsValue(
            SnomedRelationship relationship,
            Collection<SnomedRelationshipPropertyExpression> relationshipPropertyExpressions,
            Collection<SnomedRoleGroupExpression> roleGroupExpressions,
            boolean addRefiningProperties) {

        SnomedRelationshipPropertyExpression propertyExpression = new SnomedRelationshipPropertyExpression(relationship);
        SnomedConcept targetConcept = propertyExpression.getRelationship().getTargetConcept();
        if(targetConcept == null)
        {
            targetConcept = checkAndReturnConceptForID(propertyExpression.getRelationship().getTargetConceptID());
            propertyExpression.getRelationship().setTargetConcept(targetConcept);
        }

        NormalFormExpression targetExpression = getLongNormalFormExpression(Collections.singleton(targetConcept),
                relationshipPropertyExpressions,
                roleGroupExpressions, addRefiningProperties);
        // set target expression as contained expression
        propertyExpression.setExpression(targetExpression);
        propertyExpression.addChildExpression(targetExpression);

        return propertyExpression;
    }

    /**
     * Gets the expression with long normal form as value.
     *
     * @param snomedRelationshipPropertyExpression the snomed relationship property expression
     * @param relationshipPropertyExpressions the relationship property expressions
     * @param roleGroupExpressions the role group expressions
     * @param addRefiningProperties the add refining properties
     * @return the expression with long normal form as value
     */
    private  SnomedRelationshipPropertyExpression getExpressionWithLongNormalFormAsValue(
            SnomedRelationshipPropertyExpression snomedRelationshipPropertyExpression,
            Collection<SnomedRelationshipPropertyExpression> relationshipPropertyExpressions,
            Collection<SnomedRoleGroupExpression> roleGroupExpressions,
            boolean addRefiningProperties) {

        NormalFormExpression targetExpression = null;
        // check the type of contained expression
        Expression containedExpression = snomedRelationshipPropertyExpression.getExpression();
        if(containedExpression instanceof CloseToUserExpression)
        {
            targetExpression = getLongNormalFormExpression((CloseToUserExpression) containedExpression, addRefiningProperties);
        }
        else
        {
            SnomedRelationship relationship = snomedRelationshipPropertyExpression.getRelationship();
            if(relationship != null)
            {
                return getExpressionWithLongNormalFormAsValue(relationship,relationshipPropertyExpressions,
                        roleGroupExpressions, addRefiningProperties);
            }
        }

        // set target expression as contained expression
        snomedRelationshipPropertyExpression.setExpression(targetExpression);
        snomedRelationshipPropertyExpression.addChildExpression(targetExpression);

        return snomedRelationshipPropertyExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.normaliser.impl.AbstractNormalFormGenerator#getLongNormalFormExpression(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    @Override
    public synchronized  NormalFormExpression getLongNormalFormExpression(CloseToUserExpression closeToUserExpression) {
        return getLongNormalFormExpression(closeToUserExpression, false);
    }

    /**
     * Gets the long normal form expression.
     *
     * @param closeToUserExpression the close to user expression
     * @param addRefiningProperties the add refining properties
     * @return the long normal form expression
     */
    private  NormalFormExpression getLongNormalFormExpression(CloseToUserExpression closeToUserExpression,
                                                              boolean addRefiningProperties){
        if(closeToUserExpression != null)
        {
            // generate property expressions for relationships and role groups in close to user form
            Collection<SnomedRelationshipPropertyExpression> refinementPropertyExpressions = new ArrayList<SnomedRelationshipPropertyExpression>();
            Collection<SnomedRoleGroupExpression> refinementRoleGroupExpressions = new ArrayList<SnomedRoleGroupExpression>();

            for(PropertyExpression refinement : closeToUserExpression.getRefinementExpressions())
            {
                if(refinement instanceof SnomedRelationshipPropertyExpression)
                {
                    refinementPropertyExpressions.add(
                            getExpressionWithLongNormalFormAsValue(
                                    (SnomedRelationshipPropertyExpression) refinement,
                                    addRefiningProperties));
                }
                else if(refinement instanceof SnomedRoleGroupExpression)
                {
                    refinementRoleGroupExpressions.add(
                            getExpressionWithLongNormalFormAsValue((SnomedRoleGroupExpression) refinement,
                                    addRefiningProperties));
                }
            }

            return getLongNormalFormExpression(closeToUserExpression.getFocusConcepts(),
                    refinementPropertyExpressions, refinementRoleGroupExpressions, addRefiningProperties);
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /**
     * Check and apply refinement to focus concept.
     *
     * @param roleGroupExpressions the role group expressions
     * @param refinement the refinement
     */
    private  void checkAndApplyRefinementToFocusConcept(
            Collection<SnomedRoleGroupExpression> roleGroupExpressions,
            SnomedRelationshipPropertyExpression refinement){
        /**
         * get all contained relationship expressions in roleGroupExpressions and apply refinement on
         * the one that has a property name "associated finding" (246090004) or "associated procedure" (363589002)
         */

        Collection<SnomedRelationshipPropertyExpression> containedExpressions =
                getContainedRelationshipPropertyExpressions(roleGroupExpressions);
        for(SnomedRelationshipPropertyExpression propertyExpression : containedExpressions)
        {
            String relationshipType = propertyExpression.getRelationship().getRelationshipType();
            if(ConceptType.ATTRIBUTE_ASSOCIATED_FINDING.getID().equalsIgnoreCase(relationshipType) ||
                    ConceptType.ATTRIBUTE_ASSOCIATED_PROCEDURE.getID().equalsIgnoreCase(relationshipType))
            {
                // get contained expression and apply refinement to it if its a normal form expression
                Expression expression = propertyExpression.getExpression();
                if(expression instanceof NormalFormExpression)
                {
                    NormalFormExpression normalFormExpression = (NormalFormExpression) expression;
                    // apply refinement
                    applyRefinementToNormalFormExpression(normalFormExpression, refinement);
                }
            }
        }
    }

    /**
     * Apply laterality to appropriate body part.
     *
     * @param normalFormExpression the normal form expression
     * @param nonRedundantRelationships the non redundant relationships
     * @param nonRedundantRoleGroups the non redundant role groups
     * @param lateralityRefinement the laterality refinement
     */
    private  void applyLateralityToAppropriateBodyPart(
            NormalFormExpression normalFormExpression,
            Collection<SnomedRelationshipPropertyExpression> nonRedundantRelationships,
            Collection<SnomedRoleGroupExpression> nonRedundantRoleGroups,
            SnomedRelationshipPropertyExpression lateralityRefinement){

        // check if focus concept is a type of body part
        for(SnomedConcept focusConcept : normalFormExpression.getFocusConcepts())
        {
            if(ConceptType.BODY_STRUCTURE == focusConcept.getType())
            {
                if (logger.isDebugEnabled()) {
                    logger.debug("Focus concept is a body structure");
                    logger.debug("focusConcept.getPreferredLabel() = " + focusConcept.getPreferredLabel());
                    logger.debug("focusConcept.getConceptID() = " + focusConcept.getConceptID());
                }
                // get local refinements and reapply laterality to it
                normalFormExpression.setRelationshipExpressions(nonRedundantRelationships);
                applyRefinementToNormalFormExpression(normalFormExpression, lateralityRefinement);
            }
        }

        /*
         get collection of all contained propertyExpressions in nonRedundantRelationships  and
         nonRedundantRoleGroups so that all relationship expressions can be processed.
          */
        Collection<SnomedRelationshipPropertyExpression> combinedExpressions =
                getContainedRelationshipPropertyExpressions(nonRedundantRoleGroups);
        combinedExpressions.addAll(nonRedundantRelationships);

        for(SnomedRelationshipPropertyExpression propertyExpression : combinedExpressions)
        {
            // get contained expression in propertyExpression and reapply lateralityRefinement
            Expression expression = propertyExpression.getExpression();
            if(expression instanceof NormalFormExpression)
            {
                NormalFormExpression nfe = (NormalFormExpression) expression;
                Collection<SnomedRelationshipPropertyExpression> innerRelationshipPropertyExpressions =
                        nfe.getRelationshipExpressions();
                Collection<SnomedRoleGroupExpression> innerRoleGroupExpressions = nfe.getRoleGroupExpressions();

                // recursive call
                applyLateralityToAppropriateBodyPart(nfe, innerRelationshipPropertyExpressions,
                        innerRoleGroupExpressions, lateralityRefinement);
            }
            else
            {
                logger.warn("expression.getClass() = " + expression.getClass());
            }
        }
    }

    /**
     * Apply refinement to normal form expression.
     *
     * @param expression the expression
     * @param refinement the refinement
     */
    private  void applyRefinementToNormalFormExpression(NormalFormExpression expression,
                                                        SnomedRelationshipPropertyExpression refinement){

        Collection<SnomedRelationshipPropertyExpression> mergedRelationships =
                getMergedRelationships(Collections.singleton(refinement),
                        expression.getRelationshipExpressions(),
                        expression.getRoleGroupExpressions());
        Collection<SnomedRelationshipPropertyExpression> modifiedRefinements =
                new HashSet(getNonRedundantMergedRelationships(mergedRelationships));
        // remove existing relationship expressions
        for(SnomedRelationshipPropertyExpression existingExpression : expression.getRelationshipExpressions())
        {
            expression.removeChildExpression(existingExpression);
        }
        // set modifiedRefinements in nfe
        for(SnomedRelationshipPropertyExpression modifiedExpression : modifiedRefinements)
        {
            expression.addRelationshipExpression(modifiedExpression);
        }
    }
}
