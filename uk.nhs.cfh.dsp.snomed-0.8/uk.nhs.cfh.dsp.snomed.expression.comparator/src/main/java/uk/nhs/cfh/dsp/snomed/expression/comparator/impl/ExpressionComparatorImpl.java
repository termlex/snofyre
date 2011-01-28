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
import uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;

import javax.sql.DataSource;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 20, 2009 at 8:57:37 PM
 */
public class ExpressionComparatorImpl extends AbstractExpressionComparator implements ExpressionComparator{

    /** The logger. */
    private static Log logger = LogFactory.getLog(ExpressionComparatorImpl.class);
    
    /** The data source. */
    private DataSource dataSource;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;

    /**
     * Instantiates a new expression comparator impl.
     *
     * @param dataSource the data source
     * @param terminologyConceptDAO the terminology concept dao
     */
    public ExpressionComparatorImpl(DataSource dataSource,
                                    TerminologyConceptDAO terminologyConceptDAO) {
        super(dataSource, terminologyConceptDAO);
    }

    /**
     * Instantiates a new expression comparator impl.
     */
    public ExpressionComparatorImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.impl.AbstractExpressionComparator#getSubsumptionRelation(Expression, Expression)
     */
    @Override
    public Subsumption_Relation getSubsumptionRelation(Expression lhsExpression, Expression rhsExpression){

        Subsumption_Relation relation = Subsumption_Relation.NO_RELATION;

        if(lhsExpression != null && lhsExpression != null)
        {
            // check if they are both the same - referential equality
            if(lhsExpression.equals(rhsExpression))
            {
                return Subsumption_Relation.SAME;
            }
            else if(lhsExpression.getUuid().compareTo(rhsExpression.getUuid()) == 0)
            {
                return Subsumption_Relation.SAME;
            }
            else
            {
                // check based on type of expression passed
                if(lhsExpression instanceof PropertyExpression && rhsExpression instanceof PropertyExpression)
                {
                    PropertyExpression lhsPropertyExpression = (PropertyExpression) lhsExpression;
                    PropertyExpression rhsPropertyExpression = (PropertyExpression) rhsExpression;
                    // get expression from within propertyExpressions and compare them
                    return getSubsumptionRelation(lhsPropertyExpression, rhsPropertyExpression);
                }
                else if(lhsExpression instanceof ConceptExpression && rhsExpression instanceof ConceptExpression)
                {
                    ConceptExpression lhsConceptExpression = (ConceptExpression) lhsExpression;
                    ConceptExpression rhsConceptExpression = (ConceptExpression) rhsExpression;

                    // compare concepts themselves and return relationship
                    return getSubsumptionRelation(lhsConceptExpression, rhsConceptExpression);
                }
                else
                {
                    logger.warn("Unknown expression types passed : "+lhsExpression.getClass()+ ", \t "
                            +rhsExpression.getClass());
                }
            }

        }

        return relation;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.impl.AbstractExpressionComparator#getSubsumptionRelation(ConceptExpression, ConceptExpression)
     */
    @Override
    public Subsumption_Relation getSubsumptionRelation(ConceptExpression lhsConceptExpression,
                                                       ConceptExpression rhsConceptExpression){

        Subsumption_Relation relation = Subsumption_Relation.NO_RELATION;
        if(lhsConceptExpression != null && rhsConceptExpression != null)
        {
            // get concepts held
            SnomedConcept lhsConcept = lhsConceptExpression.getConcept();
            SnomedConcept rhsConcept = rhsConceptExpression.getConcept();

            if(lhsConcept != null && rhsConcept != null)
            {
                return getSubsumptionRelation(lhsConcept, rhsConcept);
            }
        }

        return relation;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.impl.AbstractExpressionComparator#getSubsumptionRelation(PropertyExpression, PropertyExpression)
     */
    @Override
    public Subsumption_Relation getSubsumptionRelation(PropertyExpression lhsPropertyExpression,
                                                       PropertyExpression rhsPropertyExpression){

        Subsumption_Relation relation = Subsumption_Relation.NO_RELATION;
        if(lhsPropertyExpression != null && rhsPropertyExpression != null)
        {
            // check first for referential equality
            if(lhsPropertyExpression.equals(rhsPropertyExpression))
            {
                return Subsumption_Relation.SAME;
            }
            else if(lhsPropertyExpression.getUuid() == rhsPropertyExpression.getUuid())
            {
                return Subsumption_Relation.SAME;
            }
            else if(lhsPropertyExpression instanceof SnomedRelationshipPropertyExpression &&
                    rhsPropertyExpression instanceof SnomedRelationshipPropertyExpression)
            {
                SnomedRelationshipPropertyExpression lhsSnomedRelationshipPropertyExpression =
                        (SnomedRelationshipPropertyExpression) lhsPropertyExpression;
                SnomedRelationshipPropertyExpression rhsSnomedRelationshipPropertyExpression =
                        (SnomedRelationshipPropertyExpression) rhsPropertyExpression;

                return getSubsumptionRelationForRelationshipExpressions(lhsSnomedRelationshipPropertyExpression, rhsSnomedRelationshipPropertyExpression);
            }
            else if(lhsPropertyExpression instanceof SnomedRoleGroupExpression &&
                    rhsPropertyExpression instanceof SnomedRoleGroupExpression)
            {
                logger.warn("Asked to compare two role group expressions, but code not implemented.");
            }
            else
            {
                logger.warn("Expressions can not be compared!");
                return Subsumption_Relation.NO_RELATION;
            }
        }


        return relation;
    }

    /**
     * Gets the subsumption relation for relationship expressions.
     *
     * @param lhsSnomedRelationshipPropertyExpression the lhs snomed relationship property expression
     * @param rhsSnomedRelationshipPropertyExpression the rhs snomed relationship property expression
     * @return the subsumption relation for relationship expressions
     */
    private Subsumption_Relation getSubsumptionRelationForRelationshipExpressions(SnomedRelationshipPropertyExpression lhsSnomedRelationshipPropertyExpression, SnomedRelationshipPropertyExpression rhsSnomedRelationshipPropertyExpression){

        SnomedRelationship lhsRelationship = lhsSnomedRelationshipPropertyExpression.getRelationship();
        SnomedRelationship rhsRelationship = rhsSnomedRelationshipPropertyExpression.getRelationship();

        /*
           we can process checking if both properties have some relation with each other. The only relationship
           allowed can be SAME or SUBSUMES.
        */
        Subsumption_Relation propertyRelationship = getSubsumptionRelation(lhsRelationship.getRelationshipType(),
                rhsRelationship.getRelationshipType());
        if(propertyRelationship == Subsumption_Relation.SAME)
        {
            /*
               we can now process for relationship between the expressions themselves depending on type of
               property expression
            */
            Expression lhsFillerExpression = lhsSnomedRelationshipPropertyExpression.getExpression();
            Expression rhsFillerExpression = rhsSnomedRelationshipPropertyExpression.getExpression();
            Subsumption_Relation innerRelation = getSubsumptionRelation(lhsFillerExpression, rhsFillerExpression);

            if(innerRelation == Subsumption_Relation.SAME)
            {
                return Subsumption_Relation.SAME;
            }
            else if(innerRelation == Subsumption_Relation.SUBSUMES)
            {
                return Subsumption_Relation.SUBSUMES;
            }
            else if(innerRelation == Subsumption_Relation.SUBSUMED_BY)
            {
                // log as interesting result
                logger.info("Interesting case of property equality but LHS filler value is subsumed by RHS");
                return Subsumption_Relation.NO_RELATION;
            }
            else
            {
                return Subsumption_Relation.NO_RELATION;
            }
        }
        else if(propertyRelationship == Subsumption_Relation.SUBSUMES)
        {
            /*
               we can now process for relationship between the expressions themselves depending on type of
               property expression
            */
            Expression lhsFillerExpression = lhsSnomedRelationshipPropertyExpression.getExpression();
            Expression rhsFillerExpression = rhsSnomedRelationshipPropertyExpression.getExpression();
            Subsumption_Relation innerRelation = getSubsumptionRelation(lhsFillerExpression, rhsFillerExpression);

            if(innerRelation == Subsumption_Relation.SAME)
            {
                return Subsumption_Relation.SUBSUMES;
            }
            else if(innerRelation == Subsumption_Relation.SUBSUMES)
            {
                return Subsumption_Relation.SUBSUMES;
            }
            else if(innerRelation == Subsumption_Relation.SUBSUMED_BY)
            {
                // log as interesting result
                logger.info("Interesting case of property equality but LHS filler value is subsumed by RHS");
                return Subsumption_Relation.NO_RELATION;
            }
            else
            {
                return Subsumption_Relation.NO_RELATION;
            }
        }
        else if(propertyRelationship == Subsumption_Relation.SUBSUMED_BY)
        {
            /*
               we can now process for relationship between the expressions themselves depending on type of
               property expression
            */
            Expression lhsFillerExpression = lhsSnomedRelationshipPropertyExpression.getExpression();
            Expression rhsFillerExpression = rhsSnomedRelationshipPropertyExpression.getExpression();
            Subsumption_Relation innerRelation = getSubsumptionRelation(lhsFillerExpression, rhsFillerExpression);

            if(innerRelation == Subsumption_Relation.SAME)
            {
                // log as interesting result
                logger.info("Interesting case of property equality but LHS filler value is subsumed by RHS");
                return Subsumption_Relation.SUBSUMED_BY;
            }
            else if(innerRelation == Subsumption_Relation.SUBSUMES)
            {
                // log as interesting result
                logger.info("Interesting case of property equality but LHS filler value is subsumed by RHS");
                return Subsumption_Relation.NO_RELATION;
            }
            else if(innerRelation == Subsumption_Relation.SUBSUMED_BY)
            {
                return Subsumption_Relation.SUBSUMED_BY;
            }
            else
            {
                return Subsumption_Relation.NO_RELATION;
            }
        }
        else
        {
            return Subsumption_Relation.NO_RELATION;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.impl.AbstractExpressionComparator#setDataSource(javax.sql.DataSource)
     */
    public synchronized void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.impl.AbstractExpressionComparator#setTerminologyConceptDAO(uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO)
     */
    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        super.setTerminologyConceptDAO(terminologyConceptDAO);
    }
}
