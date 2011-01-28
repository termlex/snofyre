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
package uk.nhs.cfh.dsp.srth.query.transform.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.ExpressionCompositionalGrammarConverter;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionMappingObjectImpl;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionSubsumptionRelationshipImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.SubsumptionVocabulary;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;
import uk.nhs.cfh.dsp.srth.query.transform.SubtypeGetter;

import java.util.*;

/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.transform.SubtypeGetter}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 30, 2010 at 11:57:05 AM
 */
public class SubtypeGetterImpl implements SubtypeGetter {

    private ExpressionMappingObjectDAO expressionMappingObjectDAO;
    private ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO;
    private ExpressionCompositionalGrammarConverter expressionCompositionalGrammarConverter;
    private ExpressionComparator expressionComparator;
    private static Log logger = LogFactory.getLog(SubtypeGetterImpl.class);
    private NormalFormGenerator normalFormGenerator;
    private boolean useDistinctUUIDs = false;
    private boolean useProximalPrimitives = false;
    private boolean ignoreTransitiveClosureTable = false;

    /**
     * Empty constructor for IOC
     */
    public SubtypeGetterImpl() {
    }

    public SubtypeGetterImpl(ExpressionMappingObjectDAO expressionMappingObjectDAO,
                         ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO,
                         ExpressionCompositionalGrammarConverter expressionCompositionalGrammarConverter,
                         ExpressionComparator expressionComparator) {
        this.expressionMappingObjectDAO = expressionMappingObjectDAO;
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
        this.expressionCompositionalGrammarConverter = expressionCompositionalGrammarConverter;
        this.expressionComparator = expressionComparator;
    }

    public Set<UUID> getSubTypeIdsForConstraint(TerminologyConstraint terminologyConstraint) {
        if(terminologyConstraint != null)
        {
            /*
            get normal form for expression in included terminology constraint
            */
            String cgNormalForm = getCGNormalFormForExpressionInConstraint(terminologyConstraint);
            if(! isIgnoreTransitiveClosureTable())
            {
                // get a uuid for a normal expression that matches cgNormalForm
                ExpressionMappingObject expressionMappingObject =
                        expressionMappingObjectDAO.findUsingNFECompositionalGrammarForm(cgNormalForm);
                if(expressionMappingObject != null)
                {
                    UUID normalFormUuid = expressionMappingObject.getNormalFormExpressionUuid();
                    if (logger.isDebugEnabled()) {
                        logger.debug("normalFormUuid = " + normalFormUuid);
                    }
                    /*
                        for normalFormUuid  get all known sub types in TC table. Note at the moment the TC
                        table does not distinguish between equivalent and descendant expressions!
                    */
                    Set<UUID> subTypes = expressionSubsumptionRelationshipDAO.getAllSubTypes(normalFormUuid);
                    if (logger.isDebugEnabled()) {
                        logger.debug("subTypes = " + subTypes);
                    }
                    
                    if(subTypes.size() > 0)
                    {
                        logger.debug("subTypes.size() = " + subTypes.size());
                        List<UUID> l = expressionMappingObjectDAO.getCTUIdsUsingNFEFIds(subTypes);
                        logger.debug("l = " + l);
                        return new HashSet<UUID>(l);
                    }
                    else
                    {
                        return checkAndReturnSubsumedTypes(cgNormalForm, terminologyConstraint.getSubsumption());
                    }
                }
                else
                {
                    return checkAndReturnSubsumedTypes(cgNormalForm, terminologyConstraint.getSubsumption());
                }
            }
            else
            {
                return checkAndReturnSubsumedTypes(cgNormalForm, terminologyConstraint.getSubsumption());
            }
        }
        else
        {
            throw new IllegalArgumentException("Terminology Constraint passed must not be null");
        }
    }

    public Set<UUID> checkAndReturnSubsumedTypes(String normalForm, SubsumptionVocabulary subsumptionVocabulary){
        if(SubsumptionVocabulary.SELF_ONLY == subsumptionVocabulary)
        {
            return new HashSet<UUID>(expressionMappingObjectDAO.getCTUIdsUsingNFEFIds(Collections.singleton(checkAndReturnEquivalentsFromUniqueExpressions(normalForm))));
        }
        else if(SubsumptionVocabulary.SELF_OR_ANY_TYPE_OF == subsumptionVocabulary)
        {
            return new HashSet<UUID>(expressionMappingObjectDAO.getCTUIdsUsingNFEFIds(checkAndReturnEquivalentAndSubtypesFromUniqueExpressions(normalForm)));
        }
        else if(SubsumptionVocabulary.ANY_TYPE_OF_BUT_NOT_SELF == subsumptionVocabulary)
        {
            return new HashSet<UUID>(expressionMappingObjectDAO.getCTUIdsUsingNFEFIds(checkAndReturnSubtypesFromUniqueExpressions(normalForm)));
        }
        else
        {
            throw new UnsupportedOperationException("Subsumption vocabulary passed is not supported: "+subsumptionVocabulary);
        }

    }

    public Set<UUID> checkAndReturnEquivalentAndSubtypesFromUniqueExpressions(String normalForm) {

        Set<UUID> subTypeIds = new HashSet<UUID>();
        // get equivalents and add to subtypeIds
        UUID equivalentExpId = checkAndReturnEquivalentsFromUniqueExpressions(normalForm);
        if(equivalentExpId != null)
        {
            subTypeIds.add(equivalentExpId);
        }
        // get subytpeExpression ids and add
        subTypeIds.addAll(checkAndReturnSubtypesFromUniqueExpressions(normalForm));

        return subTypeIds;
    }

    public Set<UUID> checkAndReturnSubtypesFromUniqueExpressions(String normalForm) {

        logger.warn("No matching normal form exists.. this will take ages!");
        Set<UUID> subTypeIds = new HashSet<UUID>();
        if (logger.isDebugEnabled()) {
            logger.debug("cgNormalForm = " + normalForm);
        }
        NormalFormExpression lhsNFE = expressionCompositionalGrammarConverter.getNormalForm(normalForm);
        ExpressionMappingObject e = expressionMappingObjectDAO.findUsingNFECompositionalGrammarForm(normalForm);
        if(e != null)
        {
            lhsNFE.setUuid(e.getNormalFormExpressionUuid());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("lhsNFE.getCompositionalGrammarForm() = " + lhsNFE.getCompositionalGrammarForm());
        }
        
        // get short normal form of lhsNFE
        NormalFormExpression lhsShortNFE = normalFormGenerator.getShortNormalFormExpression(lhsNFE);
        if (logger.isDebugEnabled()) {
            logger.debug("lhsShortNFE.getCompositionalGrammarForm() = " + lhsShortNFE.getCompositionalGrammarForm());
        }

        /*
           first check if long normal forms are equivalent, by comparing canonical forms.
           This is done by checking equals function, which should automatically compare UUIDs and canonical forms
           If they are, then we do not need to check subsumption relationship using the short normal form
        */

        // return mappingobjects based on the optimisation status set
        List<ExpressionMappingObject> allMappingObjects = new ArrayList<ExpressionMappingObject>();
        if(isUseDistinctUUIDs())
        {
            allMappingObjects = expressionMappingObjectDAO.returnAllDistinctLiteObjects();
        }
        else if(isUseProximalPrimitives())
        {
            String proximalPrimitive = "";
            int indexOfColon = normalForm.indexOf(':');
            // get proximal primitive for lhsShortNFE, which is the part before the ':'
            if(indexOfColon > -1)
            {
                proximalPrimitive = normalForm.substring(0, indexOfColon);
            }
            else
            {
                proximalPrimitive = normalForm;
            }

            allMappingObjects = expressionMappingObjectDAO.returnAllMatchingDistinctLiteObjects(proximalPrimitive);
        }
        else
        {
            allMappingObjects = expressionMappingObjectDAO.returnAllAsLiteObjects();
        }

        // loop through returned objects and check for subusmption
        for(ExpressionMappingObject emo : allMappingObjects)
        {
            NormalFormExpression rhsNFE = expressionCompositionalGrammarConverter.getNormalForm(emo.getNormalFormCGForm());
            if (logger.isDebugEnabled()) {
                logger.debug("emo.getNormalFormCGForm() = " + emo.getNormalFormCGForm());
            }

            if(expressionComparator.isSubsumedBy(rhsNFE, lhsShortNFE))
            {
                if (logger.isDebugEnabled()) {
                    logger.debug("Found a subsumed expression");
                }
                // get id of expression object and add to subTypeIds
                subTypeIds.add(emo.getNormalFormExpressionUuid());

                // save to expressions mapping table
                addToExpressionMappingTable(lhsNFE.getUuid(), emo.getNormalFormExpressionUuid());
            }
        }

        if (e == null) {
            // create a new expressionMappingObject and store in repository
            ExpressionMappingObject emo = new ExpressionMappingObjectImpl();
            emo.setCloseToUserCGForm("");
            emo.setCloseToUserExpressionUuid(UUID.randomUUID());
            emo.setNormalFormCGForm(normalForm);
            emo.setNormalFormExpressionUuid(lhsNFE.getUuid());
            emo.setSituationNormalFormCGForm("");
            emo.setSituationNormalFormExpressionUuid(UUID.randomUUID());
            // save emo
            expressionMappingObjectDAO.save(emo);
        }

        return subTypeIds;
    }


    /**
     * This method returns the unique expression UUID that is equivalent to the given normalForm.
     * @param normalForm the normalForm to check for equivalence
     *
     * @return a string representation of the UUID of the equivalent expression.
     */
    public UUID checkAndReturnEquivalentsFromUniqueExpressions(String normalForm) {

        UUID equivalentExpID = null;

        if (logger.isDebugEnabled()) {
            logger.debug("cgNormalForm = " + normalForm);
        }

        ExpressionMappingObject emo = expressionMappingObjectDAO.findUsingNFECompositionalGrammarForm(normalForm);
        if(emo != null)
        {
            equivalentExpID = emo.getNormalFormExpressionUuid();
        }

        return equivalentExpID;
    }

    private void addToExpressionMappingTable(UUID predicateExpressionUUID, UUID candidateExpressionUUID){
        if (predicateExpressionUUID != null && candidateExpressionUUID != null)
        {
            expressionSubsumptionRelationshipDAO.saveRelationship(
                    new ExpressionSubsumptionRelationshipImpl(predicateExpressionUUID, candidateExpressionUUID));
        }
    }

    private String getCGNormalFormForExpressionInConstraint(TerminologyConstraint terminologyConstraint){
        Expression containedExpression = terminologyConstraint.getExpression();
        if(containedExpression instanceof CloseToUserExpression)
        {
            return normalFormGenerator.getLongNormalFormExpression((CloseToUserExpression) containedExpression).getCompositionalGrammarForm();
        }
        else if(containedExpression instanceof NormalFormExpression)
        {
            return ((NormalFormExpression) containedExpression).getCompositionalGrammarForm();
        }
        else
        {
            throw new IllegalArgumentException("Unknown expression type passed : "+containedExpression);
        }
    }

    public boolean isUseProximalPrimitives() {
        return useProximalPrimitives;
    }

    public void setUseProximalPrimitives(boolean useProximalPrimitives) {
        this.useProximalPrimitives = useProximalPrimitives;
    }

    public boolean isUseDistinctUUIDs() {
        return useDistinctUUIDs;
    }

    public void setUseDistinctUUIDs(boolean useDistinctUUIDs) {
        this.useDistinctUUIDs = useDistinctUUIDs;
    }

    public boolean isIgnoreTransitiveClosureTable() {
        return ignoreTransitiveClosureTable;
    }

    public void setIgnoreTransitiveClosureTable(boolean ignoreTransitiveClosureTable) {
        this.ignoreTransitiveClosureTable = ignoreTransitiveClosureTable;
    }

    public synchronized void setExpressionMappingObjectDAO(ExpressionMappingObjectDAO expressionMappingObjectDAO) {
        this.expressionMappingObjectDAO = expressionMappingObjectDAO;
    }

    public synchronized void setExpressionSubsumptionRelationshipDAO(ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO) {
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
    }

    public synchronized void setExpressionCompositionalGrammarConverter(ExpressionCompositionalGrammarConverter expressionCompositionalGrammarConverter) {
        this.expressionCompositionalGrammarConverter = expressionCompositionalGrammarConverter;
    }

    public synchronized void setExpressionComparator(ExpressionComparator expressionComparator) {
        this.expressionComparator = expressionComparator;
    }

    public synchronized void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }
}
