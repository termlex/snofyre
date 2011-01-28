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
package uk.nhs.cfh.dsp.srth.expression.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.ExpressionCompositionalGrammarConverter;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionTransitiveClosureTableGenerator;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionSubsumptionRelationshipImpl;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link uk.nhs.cfh.dsp.srth.expression.repository.ExpressionTransitiveClosureTableGenerator}
 *
 * @scr.component immediate=“true”
 * @scr.service
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 1:35:41 AM
 */
public class ExpressionTransitiveClosureTableGeneratorImpl implements ExpressionTransitiveClosureTableGenerator {

    /** The expression mapping object dao. */
    private ExpressionMappingObjectDAO expressionMappingObjectDAO;
    
    /** The expression subsumption relationship dao. */
    private ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO;
    
    /** The expression compositional grammar converter. */
    private ExpressionCompositionalGrammarConverter expressionCompositionalGrammarConverter;
    
    /** The expression comparator. */
    private ExpressionComparator expressionComparator;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ExpressionTransitiveClosureTableGeneratorImpl.class);

    /**
     * Instantiates a new expression transitive closure table generator impl.
     *
     * @param expressionMappingObjectDAO the expression mapping object dao
     * @param expressionSubsumptionRelationshipDAO the expression subsumption relationship dao
     * @param expressionCompositionalGrammarConverter the expression compositional grammar converter
     * @param expressionComparator the expression comparator
     */
    public ExpressionTransitiveClosureTableGeneratorImpl(ExpressionMappingObjectDAO expressionMappingObjectDAO,
                                                         ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO,
                                                         ExpressionCompositionalGrammarConverter expressionCompositionalGrammarConverter,
                                                         ExpressionComparator expressionComparator) {
        this.expressionMappingObjectDAO = expressionMappingObjectDAO;
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
        this.expressionCompositionalGrammarConverter = expressionCompositionalGrammarConverter;
        this.expressionComparator = expressionComparator;
    }

    /**
     * Empty constructor for IOC.
     */
    public ExpressionTransitiveClosureTableGeneratorImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionTransitiveClosureTableGenerator#generateTCTable(boolean)
     */
    public void generateTCTable(boolean migrateFromUniqueExpressions){


        if(migrateFromUniqueExpressions)
        {
            migrateFromUniqueExpressions();
        }

        List<ExpressionSubsumptionRelationship> subsumptionRelationships = expressionSubsumptionRelationshipDAO.findAllRelationships();
        int lastRunCount = subsumptionRelationships.size();
        logger.debug("lastRunCount = " + lastRunCount);

        /*
          for every relationship, get the children of the subtype and add add as children of the supertype.
          We then get all parents of the supertype and add as super types of the every subtype.
         */
        for(ExpressionSubsumptionRelationship relationship : subsumptionRelationships)
        {
            UUID superTypeUuid = relationship.getSuperTypeExpressionUUuid();
            UUID subTypeUuid = relationship.getSubTypeExpressionUuid();

            Set<UUID> ancestorIds = expressionSubsumptionRelationshipDAO.getAllSuperTypes(superTypeUuid);
            Set<UUID> descendantIds = expressionSubsumptionRelationshipDAO.getAllSubTypes(subTypeUuid);
            // add subTypeUuid  to descendantIds
            descendantIds.add(subTypeUuid);
            // add superTypeUuid  to ancestorIds

            for(UUID ancestorId : ancestorIds)
            {
                for(UUID descendantId : descendantIds)
                {
                    checkAndAddRelationship(ancestorId, descendantId);
                }
            }
        }

        // check current number of relationships
        int currentCount = expressionSubsumptionRelationshipDAO.getRelationshipsCount();
        logger.debug("currentCount = " + currentCount);
        if(currentCount > lastRunCount)
        {
            // make a recursive call, without having to migrate from unique expressions table
            generateTCTable(false);
        }
    }

    /**
     * Migrate from unique expressions.
     */
    private void migrateFromUniqueExpressions(){
        // get all contents of UNIQUE_EXPRESSIONS_TABLE and process them.
        List<ExpressionMappingObject> mappingObjects = expressionMappingObjectDAO.findAll();
        logger.debug("mappingObjects.size() = " + mappingObjects.size());
        long startTime = Calendar.getInstance().getTimeInMillis();
        /*
         Migrate UUIDs from unique expressions table to TC table.
         */
        for(int i=0; i<mappingObjects.size(); i++)
        {
            for(int j=i+1; j<mappingObjects.size(); j++)
            {
                ExpressionMappingObject lhsExpressionMappingObject = mappingObjects.get(i);
                ExpressionMappingObject rhsExpressionMappingObject = mappingObjects.get(j);

                // get compositional form and convert to expressions to compare
                UUID lhsNFUuid = lhsExpressionMappingObject.getNormalFormExpressionUuid();
                UUID rhsNFUuid = rhsExpressionMappingObject.getNormalFormExpressionUuid();
                NormalFormExpression lhsNormalFormExpression =
                        expressionCompositionalGrammarConverter.getNormalForm(lhsExpressionMappingObject.getNormalFormCGForm());
                NormalFormExpression rhsNormalFormExpression =
                        expressionCompositionalGrammarConverter.getNormalForm(rhsExpressionMappingObject.getNormalFormCGForm());

                // get subsumption relationship for expressions
                ExpressionComparator.Subsumption_Relation relation =
                        expressionComparator.getSubsumptionRelation(lhsNormalFormExpression, rhsNormalFormExpression);
                // populate expression subsumption relationships for normal forms
                populateTCTableForExpressions(relation, lhsNFUuid, rhsNFUuid);

                // get situation normal forms and add to table using the same relationship as above
                UUID lhsSNFUuid = lhsExpressionMappingObject.getSituationNormalFormExpressionUuid();
                UUID rhsSNFUuid = rhsExpressionMappingObject.getSituationNormalFormExpressionUuid();

                // populate expression subsumption relationships for normal forms
                populateTCTableForExpressions(relation, lhsSNFUuid, rhsSNFUuid);
            }
            logger.debug("Time to process one mapping object : "+(Calendar.getInstance().getTimeInMillis() - startTime));
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionTransitiveClosureTableGenerator#populateTCTableForExpressions(uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression, uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression, java.lang.String, java.lang.String)
     */
    public void populateTCTableForExpressions(NormalFormExpression lhsNormalFormExpression,
                                              NormalFormExpression rhsNormalFormExpression,
                                              UUID lhsNFUuid, UUID rhsNFUuid) {
        // get subsumption relationship for expressions
        ExpressionComparator.Subsumption_Relation relation =
                expressionComparator.getSubsumptionRelation(lhsNormalFormExpression, rhsNormalFormExpression);
        populateTCTableForExpressions(relation, lhsNFUuid, rhsNFUuid);
    }

    /**
     * Populate tc table for expressions.
     *
     * @param relation the relation
     * @param lhsNFUuid the lhs nf uuid
     * @param rhsNFUuid the rhs nf uuid
     */
    private void populateTCTableForExpressions(ExpressionComparator.Subsumption_Relation relation,
                                              UUID lhsNFUuid, UUID rhsNFUuid) {


        if(ExpressionComparator.Subsumption_Relation.SUBSUMES == relation)
        {
            // add lhsExpressionMappingObject's normal form uuid as supertype id
            checkAndAddRelationship(lhsNFUuid, rhsNFUuid);
        }
        else if(ExpressionComparator.Subsumption_Relation.SUBSUMED_BY == relation)
        {
            // add rhsExpression normal form uuid as super type
            checkAndAddRelationship(rhsNFUuid, lhsNFUuid);
        }
        else if(ExpressionComparator.Subsumption_Relation.SAME == relation)
        {
            // direction of relationship does not matter, so just add any way
            checkAndAddRelationship(rhsNFUuid, lhsNFUuid);
        }
    }

    /**
     * Check and add relationship.
     *
     * @param lhsNFUuid the lhs nf uuid
     * @param rhsNFUuid the rhs nf uuid
     */
    private void checkAndAddRelationship(UUID lhsNFUuid, UUID rhsNFUuid){
        ExpressionSubsumptionRelationship r = expressionSubsumptionRelationshipDAO.findRelationship(lhsNFUuid, rhsNFUuid);
        if(r == null)
        {
            // add lhsExpressionMappingObject's normal form uuid as supertype id
            expressionSubsumptionRelationshipDAO.saveRelationship(
                    new ExpressionSubsumptionRelationshipImpl(lhsNFUuid, rhsNFUuid));
        }
    }

    /**
     * Sets the expression mapping object dao.
     *
     * @param expressionMappingObjectDAO the new expression mapping object dao
     */
    public void setExpressionMappingObjectDAO(ExpressionMappingObjectDAO expressionMappingObjectDAO) {
        this.expressionMappingObjectDAO = expressionMappingObjectDAO;
    }

    /**
     * Sets the expression subsumption relationship dao.
     *
     * @param expressionSubsumptionRelationshipDAO the new expression subsumption relationship dao
     */
    public void setExpressionSubsumptionRelationshipDAO(ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO) {
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
    }

    /**
     * Sets the expression compositional grammar converter.
     *
     * @param expressionCompositionalGrammarConverter the new expression compositional grammar converter
     */
    public void setExpressionCompositionalGrammarConverter(ExpressionCompositionalGrammarConverter expressionCompositionalGrammarConverter) {
        this.expressionCompositionalGrammarConverter = expressionCompositionalGrammarConverter;
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
