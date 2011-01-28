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
package uk.nhs.cfh.dsp.snomed.expression.comparator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator} that
 * only allows to check if a predicate expression subumes a candidate expression.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 14, 2010 at 1:52:25 PM
 */
public class UnidirectionalExpressionComparatorImpl extends AbstractExpressionComparator implements ExpressionComparator {

    /** The logger. */
    private static Log logger = LogFactory.getLog(UnidirectionalExpressionComparatorImpl.class);

    /**
     * Instantiates a new unidirectional expression comparator impl.
     *
     * @param connection the connection
     * @param terminologyConceptDAO the terminology concept dao
     */
    public UnidirectionalExpressionComparatorImpl(Connection connection,
                                                  TerminologyConceptDAO terminologyConceptDAO) {
        super(connection, terminologyConceptDAO);
    }

    /**
     * Instantiates a new unidirectional expression comparator impl.
     *
     * @param dataSource the data source
     * @param terminologyConceptDAO the terminology concept dao
     */
    public UnidirectionalExpressionComparatorImpl(DataSource dataSource,
                                                  TerminologyConceptDAO terminologyConceptDAO) {
        super(dataSource, terminologyConceptDAO);
    }

    /**
     * Instantiates a new unidirectional expression comparator impl.
     */
    public UnidirectionalExpressionComparatorImpl() {
    }

    /**
     * Checks if is subsumed by.
     *
     * @param candidatePropertyExpression the candidate property expression
     * @param predicatePropertyExpression the predicate property expression
     * @return true, if is subsumed by
     */
    private boolean isSubsumedBy(SnomedRelationshipPropertyExpression candidatePropertyExpression,
                                SnomedRelationshipPropertyExpression predicatePropertyExpression){
        boolean subsumed = false;
        if(candidatePropertyExpression !=null && predicatePropertyExpression != null)
        {
            // check for equality, which automatically checks for referential and uuid equality
            if(candidatePropertyExpression.equals(predicatePropertyExpression))
            {
                return true;
            }
            else
            {
                // get contained properties
                SnomedRelationship candidateRelationship = candidatePropertyExpression.getRelationship();
                SnomedRelationship predicateRelationship = predicatePropertyExpression.getRelationship();
                // get subumption relation for relationship types
                Subsumption_Relation relation =
                        getSubsumptionRelation(candidateRelationship.getRelationshipType(), predicateRelationship.getRelationshipType());
                if(Subsumption_Relation.SAME == relation || Subsumption_Relation.SUBSUMED_BY == relation)
                {
                    // check relation between contained expressions
                    Expression candidateExpression = candidatePropertyExpression.getExpression();
                    Expression predicateExpression = predicatePropertyExpression.getExpression();

                    Subsumption_Relation innerRelation = getSubsumptionRelation(candidateExpression, predicateExpression);
                    return (Subsumption_Relation.SAME == innerRelation
                            || Subsumption_Relation.SUBSUMED_BY == innerRelation);
                }
            }
        }

        return subsumed;
    }

    /**
     * Checks if is subsumed by.
     *
     * @param candidateRoleGroupExpression the candidate role group expression
     * @param predicateRoleGroupExpression the predicate role group expression
     * @return true, if is subsumed by
     */
    private boolean isSubsumedBy(SnomedRoleGroupExpression candidateRoleGroupExpression,
                                SnomedRoleGroupExpression predicateRoleGroupExpression){
        boolean subsumed = false;
        if(candidateRoleGroupExpression != null && predicateRoleGroupExpression != null)
        {
            // check for equality, which automatically checks for referential and uuid equality
            if(candidateRoleGroupExpression.equals(predicateRoleGroupExpression))
            {
                return true;
            }
            else if(! containsNegativeFinding(predicateRoleGroupExpression))
            {
                /*
                loop through attributes in predicateRoleGroup  and check each subsumed at least one attribute
                in the candidateRoleGroup. We process the groups and use a counter to track subsuming attributes
                */

                int subsumingAttributes = 0;
                Collection<SnomedRelationshipPropertyExpression> predicateContainedPropertyExpressions
                        = getContainedRelationshipPropertyExpressions(predicateRoleGroupExpression);
                for(SnomedRelationshipPropertyExpression predicatePropertyExpression : predicateContainedPropertyExpressions)
                {
                    for(SnomedRelationshipPropertyExpression candidatePropertyExpression :
                            getContainedRelationshipPropertyExpressions(candidateRoleGroupExpression))
                    {
                        if(isSubsumedBy(candidatePropertyExpression, predicatePropertyExpression))
                        {
                            // increment susbsumingAttributes
                            subsumingAttributes++;
                            break;
                        }
                    }
                }

                /*
                 check that subsumingAttributes is equal to the number of propertyExpressions in the
                 predicateRoleGroupExpression
                */
                if(subsumingAttributes == predicateContainedPropertyExpressions.size())
                {
                    return true;
                }
            }
            else // handle the special case where predicate expression contains a negative finding
            {
                int subsumingAttributes = 0;
                // get set of matching expressions between rolegroups and check all are matched by comparing sizes
                Map<String, List<SnomedRelationshipPropertyExpression>> matchingExpressions =
                        getMatchingRelationshipPropertyExpressions(predicateRoleGroupExpression, candidateRoleGroupExpression);
                if(matchingExpressions.keySet().size() == predicateRoleGroupExpression.getChildExpressions().size())
                {
                    // loop through matchingExpressions and get subsumption relation
                    for(String attributeName : matchingExpressions.keySet())
                    {
                        SnomedRelationshipPropertyExpression predicatePropertyExpression = matchingExpressions.get(attributeName).get(0);
                        SnomedRelationshipPropertyExpression candidatePropertyExpression = matchingExpressions.get(attributeName).get(1);

                        // handle case based on attribute type
                        if((ConceptType.ATTRIBUTE_FINDING_CONTEXT.getID().equals(attributeName) ||
                                ConceptType.ATTRIBUTE_TEMPORAL_CONTEXT.getID().equals(attributeName))
                                && isSubsumedBy(candidatePropertyExpression, predicatePropertyExpression))
                        {
                            // increment counter
                            subsumingAttributes++;
                        }
                        else if((ConceptType.ATTRIBUTE_ASSOCIATED_FINDING.getID().equals(attributeName) ||
                                ConceptType.ATTRIBUTE_SUBJECT_RELATIONSHIP_CONTEXT.getID().equals(attributeName))
                                && isSubsumedBy(predicatePropertyExpression, candidatePropertyExpression))
                        {
                            /*
                             attributes are either associated finding or subject relationship context, so we
                             invert the direction of subsumption
                             */

                            // increment counter
                            subsumingAttributes++;
                        }
                    }
                }

                if(subsumingAttributes == predicateRoleGroupExpression.getChildExpressions().size())
                {
                    subsumed = true;
                }
            }
        }

        return subsumed;
    }

    /**
     * Gets the matching relationship property expressions.
     *
     * @param predicateRoleGroupExpression the predicate role group expression
     * @param candidateRoleGroupExpression the candidate role group expression
     * @return the matching relationship property expressions
     */
    private Map<String, List<SnomedRelationshipPropertyExpression>> getMatchingRelationshipPropertyExpressions(
            SnomedRoleGroupExpression predicateRoleGroupExpression,
            SnomedRoleGroupExpression candidateRoleGroupExpression){

        Map<String, List<SnomedRelationshipPropertyExpression>> matchingExpressions =
                new HashMap<String, List<SnomedRelationshipPropertyExpression>>();
        for(SnomedRelationshipPropertyExpression predicatePropertyExpression :
                getContainedRelationshipPropertyExpressions(predicateRoleGroupExpression))
        {
            for(SnomedRelationshipPropertyExpression candidatePropertyExpression :
                    getContainedRelationshipPropertyExpressions(candidateRoleGroupExpression))
            {
                String predicateRelationshipType = predicatePropertyExpression.getRelationship().getRelationshipType();
                String candidateRelationshipType = candidatePropertyExpression.getRelationship().getRelationshipType();
                if( predicateRelationshipType!= null &&
                        predicateRelationshipType.equalsIgnoreCase(candidateRelationshipType))
                {
                    List<SnomedRelationshipPropertyExpression> localList =
                            new ArrayList<SnomedRelationshipPropertyExpression>();
                    // add predicatePropertyExpression and candidatePropertyExpression to localList
                    localList.add(predicatePropertyExpression);
                    localList.add(candidatePropertyExpression);

                    // add localList to matchingExpressions
                    matchingExpressions.put(predicateRelationshipType, localList);
                    break;
                }
            }
        }

        return matchingExpressions;
    }

    /**
     * Contains negative finding.
     *
     * @param roleGroupExpression the role group expression
     * @return true, if successful
     */
    private boolean containsNegativeFinding(SnomedRoleGroupExpression roleGroupExpression){

        boolean negativeFinding = false;
        /*
            check if the roleGroupExpression contains a '408729009 |finding-context|' attribute. If it does,
            then we check if the values are '410516002 |known absent|' or '410594000 |definitely not present|'
         */

        for(SnomedRelationship relationship : roleGroupExpression.getRoleGroup().getRelationships())
        {
            if(ConceptType.ATTRIBUTE_FINDING_CONTEXT.getID().equals(relationship.getName()))
            {
                // get value of relationship
                String fillerValue = relationship.getTargetConceptID();
                if(ConceptType.KNOWN_ABSENT.getID().equals(fillerValue) ||
                        ConceptType.DEFINITELY_NOT_PRESENT.getID().equals(fillerValue))
                {
                    negativeFinding = true;
                    break;
                }
            }
        }

        return negativeFinding;
    }

    /**
     * Gets the contained relationship property expressions.
     *
     * @param roleGroupExpression the role group expression
     * @return the contained relationship property expressions
     */
    private Collection<SnomedRelationshipPropertyExpression> getContainedRelationshipPropertyExpressions(
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
     * Checks if is subsumed by.
     *
     * @param candidateExpression the candidate expression
     * @param predicateExpression the predicate expression
     * @return true, if is subsumed by
     */
    private boolean isSubsumedBy(NormalFormExpression candidateExpression, NormalFormExpression predicateExpression){
        boolean subsumed = false;
        if(candidateExpression != null && predicateExpression != null)
        {
            if(candidateExpression.equals(predicateExpression))
            {
                return true;
            }
            else
            {
                int subsumingFocusConcepts = 0;
                /*
                    get focus concepts in predicateExpression and ensure they susbsume at least one focus concept
                    in the candidateExpression
                 */
                for(SnomedConcept predicateFocusConcept : predicateExpression.getFocusConcepts())
                {
                    for(SnomedConcept candidateFocusConcept : candidateExpression.getFocusConcepts())
                    {
                        // check subsumption relation
                        Subsumption_Relation relation = getSubsumptionRelation(candidateFocusConcept, predicateFocusConcept);
                        if(Subsumption_Relation.SAME == relation || Subsumption_Relation.SUBSUMES == relation)
                        {
                            // increment subsumingAttributes and move to next focus concept
                            subsumingFocusConcepts++;
                            break;
                        }
                    }
                }

                // check if number of focus concepts in predicateExpression is equal to subsumingFocusConcepts
                if(subsumingFocusConcepts == predicateExpression.getFocusConcepts().size())
                {
                    int subsumingRoleGroups = 0;
                    /*
                        now test that each role group in the predicateExpression subumes at least one
                        role group in the candidateExpression
                     */

                    for(SnomedRoleGroupExpression predicateRoleGroupExpression : predicateExpression.getRoleGroupExpressions())
                    {
                        for(SnomedRoleGroupExpression candidateRoleGroupExpression : candidateExpression.getRoleGroupExpressions())
                        {
                            Subsumption_Relation relation = this.getSubsumptionRelation(candidateRoleGroupExpression, predicateRoleGroupExpression);
                            // check subsumption relation
                            if(Subsumption_Relation.SAME == relation || Subsumption_Relation.SUBSUMED_BY == relation)
                            {
                                // increment counter and move to next rolegroup
                                subsumingRoleGroups++;
                                break;
                            }
                        }
                    }

                    // check if number of subsuming role groups is equal to number of role groups in predicateExpression
                    if(subsumingRoleGroups == predicateExpression.getRoleGroupExpressions().size())
                    {
                        // get all contained relationship expressions in candidateExpression
                        Collection<SnomedRelationshipPropertyExpression> candidatePropertyExpressions =
                                candidateExpression.getRelationshipExpressions();
                        // for every role group expression in candidateExpression, also add its contained child expressions
                        for(SnomedRoleGroupExpression roleGroupExpression : candidateExpression.getRoleGroupExpressions())
                        {
                            for(SnomedRelationshipPropertyExpression relationshipPropertyExpression :
                                    getContainedRelationshipPropertyExpressions(roleGroupExpression))
                            {
                                candidatePropertyExpressions.add(relationshipPropertyExpression);
                            }
                        }
                        int subsumingRelationships = 0;
                        /*
                            check that every relationship expression in the predicateExpression subsumes at least one
                            grouped or ungrouped relationship expression in the candidateExpression
                         */
                        for(SnomedRelationshipPropertyExpression predicatePropertyExpression :
                                predicateExpression.getRelationshipExpressions())
                        {
                            for(SnomedRelationshipPropertyExpression candidatePropertyExpression : candidatePropertyExpressions)
                            {
                                // get subsumption relation
                                if(isSubsumedBy(candidatePropertyExpression, predicatePropertyExpression))
                                {
                                    // increment counter and move to next predicatePropertyExpression
                                    subsumingRelationships++;
                                    break;
                                }
                            }
                        }

                        // check number of subsuming relations is equal to number of relationships in predicateExpression
                        if(subsumingRelationships == predicateExpression.getRelationshipExpressions().size())
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return subsumed;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.impl.AbstractExpressionComparator#getSubsumptionRelation(Expression, Expression)
     */
    @Override
    public Subsumption_Relation getSubsumptionRelation(Expression predicateExpression, Expression candidateExpression) {
        if(predicateExpression != null && candidateExpression != null)
        {
            if(predicateExpression.equals(candidateExpression))
            {
                return Subsumption_Relation.SAME;
            }
            else if(candidateExpression instanceof NormalFormExpression &&
                    predicateExpression instanceof NormalFormExpression)
            {
                NormalFormExpression lhsNormalFormExpression = (NormalFormExpression) predicateExpression;
                NormalFormExpression rhsNormalFormExpression = (NormalFormExpression) candidateExpression;
                if(isSubsumedBy(rhsNormalFormExpression, lhsNormalFormExpression))
                {
                    return Subsumption_Relation.SUBSUMES;
                }
                else if(isSubsumedBy(lhsNormalFormExpression, rhsNormalFormExpression))
                {
                    return Subsumption_Relation.SUBSUMED_BY;
                }
                else
                {
                    return Subsumption_Relation.NO_RELATION;
                }
            }
            else if(predicateExpression instanceof SnomedRelationshipPropertyExpression &&
                    candidateExpression instanceof SnomedRelationshipPropertyExpression)
            {
                SnomedRelationshipPropertyExpression lhsPropertyExpression = (SnomedRelationshipPropertyExpression) predicateExpression;
                SnomedRelationshipPropertyExpression rhsPropertyExpression = (SnomedRelationshipPropertyExpression) candidateExpression;

                return this.getSubsumptionRelation(lhsPropertyExpression, rhsPropertyExpression);
            }
            else if(predicateExpression instanceof SnomedRoleGroupExpression &&
                    candidateExpression instanceof SnomedRoleGroupExpression)
            {
                SnomedRoleGroupExpression lhsRoleGroupExpression = (SnomedRoleGroupExpression) predicateExpression;
                SnomedRoleGroupExpression rhsRoleGroupExpression = (SnomedRoleGroupExpression) candidateExpression;

                return this.getSubsumptionRelation(lhsRoleGroupExpression, rhsRoleGroupExpression);
            }
            else
            {
                logger.warn("Expressions can not be compared because they are of different types. " +
                        "\nLHSExpression type : "+predicateExpression.getClass()+" \nRHSExpression type : "+candidateExpression.getClass());
                return Subsumption_Relation.NO_RELATION;
            }
        }
        else
        {
            throw new IllegalArgumentException("Expressions passed can not be null. \nLHSExpressions : "+predicateExpression+"" +
                    "\nRHSExpression : "+candidateExpression);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.impl.AbstractExpressionComparator#isSubsumedBy(uk.nhs.cfh.dsp.snomed.expression.model.Expression, uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Override
    public boolean isSubsumedBy(Expression candidateExpression, Expression predicateExpression) {
        return (Subsumption_Relation.SUBSUMES == this.getSubsumptionRelation(predicateExpression, candidateExpression));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.impl.AbstractExpressionComparator#getSubsumptionRelation(PropertyExpression, PropertyExpression)
     */
    @Override
    public Subsumption_Relation getSubsumptionRelation(PropertyExpression predicateExpression,
                                                       PropertyExpression candidateExpression) {

        if(predicateExpression != null && candidateExpression != null)
        {
            if(predicateExpression.equals(candidateExpression))
            {
                return Subsumption_Relation.SAME;
            }
            else if(predicateExpression instanceof SnomedRelationshipPropertyExpression &&
                    candidateExpression instanceof SnomedRelationshipPropertyExpression)
            {
                SnomedRelationshipPropertyExpression lhsPropertyExpression = (SnomedRelationshipPropertyExpression) predicateExpression;
                SnomedRelationshipPropertyExpression rhsPropertyExpression = (SnomedRelationshipPropertyExpression) candidateExpression;
                if(isSubsumedBy(lhsPropertyExpression, rhsPropertyExpression))
                {
                    return Subsumption_Relation.SUBSUMED_BY;
                }
                else if(isSubsumedBy(rhsPropertyExpression , lhsPropertyExpression))
                {
                    return Subsumption_Relation.SUBSUMES;
                }
                else
                {
                    return Subsumption_Relation.NO_RELATION;
                }
            }
            else if(predicateExpression instanceof SnomedRoleGroupExpression &&
                    candidateExpression instanceof SnomedRoleGroupExpression)
            {
                SnomedRoleGroupExpression lhsRoleGroupExpression = (SnomedRoleGroupExpression) predicateExpression;
                SnomedRoleGroupExpression rhsRoleGroupExpression = (SnomedRoleGroupExpression) candidateExpression;
                if(isSubsumedBy(lhsRoleGroupExpression, rhsRoleGroupExpression))
                {
                    return Subsumption_Relation.SUBSUMED_BY;
                }
                else if(isSubsumedBy(rhsRoleGroupExpression, lhsRoleGroupExpression))
                {
                    return Subsumption_Relation.SUBSUMES;
                }
                else
                {
                    return Subsumption_Relation.NO_RELATION;
                }
            }
            else
            {
                logger.warn("Expressions can not be compared because they are of different types. " +
                        "\nLHSExpression type : "+predicateExpression.getClass()+" \nRHSExpression type : "+candidateExpression.getClass());
                return Subsumption_Relation.NO_RELATION;
            }
        }
        else
        {
            throw new IllegalArgumentException("Expressions passed can not be null. \nLHSExpressions : "+predicateExpression+"" +
                    "\nRHSExpression : "+candidateExpression);
        }
    }
}
