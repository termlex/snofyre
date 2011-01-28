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
package uk.nhs.cfh.dsp.snomed.expressiongenerator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.*;
import uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRoleGroupImpl;

import java.util.Collection;

// TODO: Auto-generated Javadoc
//TODO Need to move concept ids to a properties file, so they can be configured and not hardcoded!
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2010 at 11:08:39 AM
 */
public class SituationExpressionGeneratorImpl implements SituationExpressionGenerator {

    // the default values
    /** The default temporal context value concept. */
    private SnomedConcept defaultTemporalContextValueConcept;
    
    /** The default finding context value concept. */
    private SnomedConcept defaultFindingContextValueConcept;
    
    /** The default procedure context value concept. */
    private SnomedConcept defaultProcedureContextValueConcept;
    
    /** The default subject relationship context value concept. */
    private SnomedConcept defaultSubjectRelationshipContextValueConcept;

    // the assertable default values
    /** The past temporal context value concept. */
    private SnomedConcept pastTemporalContextValueConcept;
    
    /** The assertable temporal context value concept. */
    private SnomedConcept assertableTemporalContextValueConcept;
    
    /** The goal finding context value concept. */
    private SnomedConcept goalFindingContextValueConcept;
    
    /** The assertable finding context value concept. */
    private SnomedConcept assertableFindingContextValueConcept;
    
    /** The assertable procedure context value concept. */
    private SnomedConcept assertableProcedureContextValueConcept;
    
    /** The planned procedure context value concept. */
    private SnomedConcept plannedProcedureContextValueConcept;
    
    /** The assertable subject relationship context value concept. */
    private SnomedConcept assertableSubjectRelationshipContextValueConcept;
    
    /** The family history subject relationship context value concept. */
    private SnomedConcept familyHistorySubjectRelationshipContextValueConcept;

    /** The situation wec concept. */
    private SnomedConcept situationWECConcept;

    // the attribute concepts
    /** The temporal context attribute concept. */
    private SnomedConcept temporalContextAttributeConcept;
    
    /** The finding context attribute concept. */
    private SnomedConcept findingContextAttributeConcept;
    
    /** The associated finding attribute concept. */
    private SnomedConcept associatedFindingAttributeConcept;
    
    /** The procedure context attribute concept. */
    private SnomedConcept procedureContextAttributeConcept;
    
    /** The associated procedure attributeoncept. */
    private SnomedConcept associatedProcedureAttributeoncept;
    
    /** The subject relationship context attribute concept. */
    private SnomedConcept subjectRelationshipContextAttributeConcept;
    
    /** The direct substance attribute concept. */
    private SnomedConcept directSubstanceAttributeConcept;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SituationExpressionGeneratorImpl.class);



    /**
     * Instantiates a new situation expression generator impl.
     *
     * @param terminologyConceptDAO the terminology concept dao
     */
    public SituationExpressionGeneratorImpl(TerminologyConceptDAO terminologyConceptDAO) {
        if (terminologyConceptDAO != null)
        {
            this.terminologyConceptDAO = terminologyConceptDAO;
        }
        else {
            throw new IllegalArgumentException("Terminology DAO passed can not be null.");
        }
    }

    /**
     * empty constructor for IOC.
     */
    public SituationExpressionGeneratorImpl() {
    }

    /**
     * this method initialises the parent concepts used by the contexts. This method needs to be
     * called before this object is used.
     */
    public synchronized void initialiseParentConcepts(){
        // create situation with explicit context concept
        situationWECConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, ConceptType.SITUATION_WEC.getID());

        // create the attribute concepts
        findingContextAttributeConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, ConceptType.ATTRIBUTE_FINDING_CONTEXT.getID());
        procedureContextAttributeConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, ConceptType.ATTRIBUTE_PROCEDURE_CONTEXT.getID());
        temporalContextAttributeConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, ConceptType.ATTRIBUTE_TEMPORAL_CONTEXT.getID());
        subjectRelationshipContextAttributeConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, ConceptType.ATTRIBUTE_SUBJECT_RELATIONSHIP_CONTEXT.getID());
        associatedFindingAttributeConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, ConceptType.ATTRIBUTE_ASSOCIATED_FINDING.getID());
        associatedProcedureAttributeoncept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, ConceptType.ATTRIBUTE_ASSOCIATED_PROCEDURE.getID());
        directSubstanceAttributeConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, ConceptType.ATTRIBUTE_DIRECT_SUBSTANCE.getID());

        // create the default value concepts
        defaultTemporalContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "410510008");
        defaultFindingContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "410514004");
        defaultProcedureContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "288532009");
        defaultSubjectRelationshipContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "125676002");

        // create the assertable default value concepts
        assertableTemporalContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "410512000");
        pastTemporalContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "410513005");
        assertableFindingContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "410515003");
        assertableProcedureContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "398166005");
        plannedProcedureContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "397943006");
        assertableSubjectRelationshipContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "410604004");
        familyHistorySubjectRelationshipContextValueConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "125677006");
    }

    /* (non-Javadoc)
 * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getAssertableSituationExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
 */
public SituationExpression getAssertableSituationExpression(SnomedConcept concept) {
        /*
         generate the situation expression based on the concept type
         */
        ConceptType conceptType = concept.getType();
        FindingSituationExpression findingSituationExpression = new FindingSituationExpression();
        ProcedureSituationExpression procedureSituationExpression = new ProcedureSituationExpression();
        if(findingSituationExpression.getPermittedTypes().contains(conceptType))
        {
            return getAssertableFindingExpression(concept);
        }
        else if(procedureSituationExpression.getPermittedTypes().contains(conceptType))
        {
            return getAssertableProcedureExpression(concept);
        }
        else if(ConceptType.OBSERVABLE_ENTITY == conceptType)
        {
            return getAssertableFindingExpression(concept);
        }
        else if(ConceptType.PHARMACEUTICAL_OR_BIOLOGICAL_PRODUCT == conceptType)
        {
            return getAssertableProcedureExpression(concept);
        }
        {
            throw new IllegalArgumentException("Situation wrapping for concept passed is not supported. " +
                    "Concept type passed: "+conceptType+". Concept = "+concept.getConceptID());
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getAssertablePlannedProcedureExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public ProcedureSituationExpression getAssertablePlannedProcedureExpression(SnomedConcept concept) {
        ProcedureSituationExpression procedureSituationExpression = getAssertableProcedureExpression(concept);
        setTemporalExpression(procedureSituationExpression, assertableTemporalContextValueConcept);
        setAssociatedContextExpression(procedureSituationExpression, plannedProcedureContextValueConcept);
        wrapAsRoleGroupAndAddAsChildExpression(procedureSituationExpression);

        return procedureSituationExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getAssertableProcedureExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public ProcedureSituationExpression getAssertableProcedureExpression(SnomedConcept concept) {

        ProcedureSituationExpression procedureSituationExpression = new ProcedureSituationExpression();
        setAssociatedContextExpression(procedureSituationExpression, assertableProcedureContextValueConcept);
        setTemporalExpression(procedureSituationExpression, pastTemporalContextValueConcept);
        setSubjectRelationshipExpression(procedureSituationExpression, assertableSubjectRelationshipContextValueConcept);
        setFocusExpression(procedureSituationExpression, concept);
        wrapAsRoleGroupAndAddAsChildExpression(procedureSituationExpression);

        return procedureSituationExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getAssertableFindingExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public FindingSituationExpression getAssertableFindingExpression(SnomedConcept concept) {
        FindingSituationExpression findingSituationExpression = new FindingSituationExpression();
        setAssociatedContextExpression(findingSituationExpression, assertableFindingContextValueConcept);
        setFocusExpression(findingSituationExpression, concept);
        setTemporalExpression(findingSituationExpression, assertableTemporalContextValueConcept);
        setSubjectRelationshipExpression(findingSituationExpression, assertableSubjectRelationshipContextValueConcept);
        wrapAsRoleGroupAndAddAsChildExpression(findingSituationExpression);

        return findingSituationExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getAssertableGoalFindingExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public FindingSituationExpression getAssertableGoalFindingExpression(SnomedConcept concept) {
        FindingSituationExpression findingSituationExpression = new FindingSituationExpression();
        setAssociatedContextExpression(findingSituationExpression, goalFindingContextValueConcept);
        setFocusExpression(findingSituationExpression, concept);
        setTemporalExpression(findingSituationExpression, assertableTemporalContextValueConcept);
        setSubjectRelationshipExpression(findingSituationExpression, assertableSubjectRelationshipContextValueConcept);
        wrapAsRoleGroupAndAddAsChildExpression(findingSituationExpression);

        return findingSituationExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getAssertablePastFindingExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public FindingSituationExpression getAssertablePastFindingExpression(SnomedConcept concept) {
        FindingSituationExpression findingSituationExpression = new FindingSituationExpression();
        setAssociatedContextExpression(findingSituationExpression, assertableFindingContextValueConcept);
        setFocusExpression(findingSituationExpression, concept);
        setTemporalExpression(findingSituationExpression, pastTemporalContextValueConcept);
        setSubjectRelationshipExpression(findingSituationExpression, assertableSubjectRelationshipContextValueConcept);
        wrapAsRoleGroupAndAddAsChildExpression(findingSituationExpression);

        return findingSituationExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getAssertableFamilyHistoryFindingExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public FindingSituationExpression getAssertableFamilyHistoryFindingExpression(SnomedConcept concept) {
        FindingSituationExpression findingSituationExpression = new FindingSituationExpression();
        setAssociatedContextExpression(findingSituationExpression, assertableFindingContextValueConcept);
        setFocusExpression(findingSituationExpression, concept);
        setTemporalExpression(findingSituationExpression, pastTemporalContextValueConcept);
        setSubjectRelationshipExpression(findingSituationExpression, familyHistorySubjectRelationshipContextValueConcept);
        wrapAsRoleGroupAndAddAsChildExpression(findingSituationExpression);

        return findingSituationExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getDefaultContextSituationExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public SituationExpression getDefaultContextSituationExpression(SnomedConcept concept) {
        /*
         generate the situation expression based on the concept type
         */
        ConceptType conceptType = concept.getType();
        FindingSituationExpression findingSituationExpression = new FindingSituationExpression();
        ProcedureSituationExpression procedureSituationExpression = new ProcedureSituationExpression();
        if(findingSituationExpression.getPermittedTypes().contains(conceptType))
        {
            return getFindingWithDefaultContext(concept);
        }
        else if(procedureSituationExpression.getPermittedTypes().contains(conceptType))
        {
            return getProcedureWithDefaultContext(concept);
        }
        else if(ConceptType.OBSERVABLE_ENTITY == conceptType)
        {
            return getFindingWithDefaultContext(concept);
        }
        else if(ConceptType.PHARMACEUTICAL_OR_BIOLOGICAL_PRODUCT == conceptType)
        {
            return getProcedureWithDefaultContext(concept);
        }
        {
            throw new IllegalArgumentException("Situation wrapping for concept passed is not supported. " +
                    "Concept type passed: "+conceptType+". Concept = "+concept.getConceptID());
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getProcedureWithDefaultContext(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public ProcedureSituationExpression getProcedureWithDefaultContext(SnomedConcept concept) {

        ProcedureSituationExpression procedureSituationExpression = new ProcedureSituationExpression();
        setAssociatedContextExpression(procedureSituationExpression, defaultProcedureContextValueConcept);
        setTemporalExpression(procedureSituationExpression, defaultTemporalContextValueConcept);
        setSubjectRelationshipExpression(procedureSituationExpression, defaultSubjectRelationshipContextValueConcept);
        setFocusExpression(procedureSituationExpression, concept);
        wrapAsRoleGroupAndAddAsChildExpression(procedureSituationExpression);

        return procedureSituationExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getFindingWithDefaultContext(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public FindingSituationExpression getFindingWithDefaultContext(SnomedConcept concept) {
        FindingSituationExpression findingSituationExpression = new FindingSituationExpression();
        setAssociatedContextExpression(findingSituationExpression, defaultFindingContextValueConcept);
        setFocusExpression(findingSituationExpression, concept);
        setTemporalExpression(findingSituationExpression, defaultTemporalContextValueConcept);
        setSubjectRelationshipExpression(findingSituationExpression, defaultSubjectRelationshipContextValueConcept);
        wrapAsRoleGroupAndAddAsChildExpression(findingSituationExpression);

        return findingSituationExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getSituationWithDefaultContext(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    public CloseToUserExpression getSituationWithDefaultContext(CloseToUserExpression closeToUserExpression){

        SituationExpression situationExpression = null;
        SnomedConcept focusConcept = closeToUserExpression.getSingletonFocusConcept();
        if(focusConcept != null)
        {
            situationExpression = getDefaultContextSituationExpression(focusConcept);
        }

        // process refinements and return as closeToUserExpression
        return processRefinementsAndReturnExpression(situationExpression, closeToUserExpression.getRefinementExpressions());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator#getSituationWithAssertableContext(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    public CloseToUserExpression getSituationWithAssertableContext(CloseToUserExpression closeToUserExpression){

        SituationExpression situationExpression = null;
        SnomedConcept focusConcept = closeToUserExpression.getSingletonFocusConcept();
        if(focusConcept != null)
        {
            situationExpression = getAssertableSituationExpression(focusConcept);
        }

        // process refinements and return as closeToUserExpression
        return processRefinementsAndReturnExpression(situationExpression, closeToUserExpression.getRefinementExpressions());
    }

    /**
     * Process refinements and return expression.
     *
     * @param situationExpression the situation expression
     * @param refinements the refinements
     * @return the close to user expression
     */
    private CloseToUserExpression processRefinementsAndReturnExpression(SituationExpression situationExpression,
                                                                        Collection<PropertyExpression> refinements){
        // check if closeToUserExpression has any refinements, if not then just return focus concept after wrapping
        for(PropertyExpression refinement : refinements)
        {
            if(refinement instanceof SnomedRelationshipPropertyExpression)
            {
                SnomedRelationshipPropertyExpression relationshipPropertyExpression =
                        (SnomedRelationshipPropertyExpression) refinement;
                String relationshipType = relationshipPropertyExpression.getRelationship().getRelationshipType();
                String targetConceptId = relationshipPropertyExpression.getRelationship().getTargetConceptID();
                SnomedConcept targetConcept = relationshipPropertyExpression.getRelationship().getTargetConcept();
                if(targetConcept == null && targetConceptId != null)
                {
                    targetConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, targetConceptId);
                }

                if(targetConcept != null)
                {
                    if(temporalContextAttributeConcept.getConceptID().equalsIgnoreCase(relationshipType))
                    {
                        // add as temporal property
                        setTemporalExpression(situationExpression, targetConcept);
                    }
                    else if(subjectRelationshipContextAttributeConcept.getConceptID().equalsIgnoreCase(relationshipType))
                    {
                        // add as subject relationship
                        setSubjectRelationshipExpression(situationExpression, targetConcept);
                    }
                    else if(associatedFindingAttributeConcept.getConceptID().equalsIgnoreCase(relationshipType)
                            || associatedProcedureAttributeoncept.getConceptID().equalsIgnoreCase(relationshipType))
                    {
                        // add as associated context
                        setAssociatedContextExpression(situationExpression, targetConcept);
                    }
                    else
                    {
                        Expression focusExpressionValue = situationExpression.getFocusExpression().getExpression();
                        if(focusExpressionValue instanceof CloseToUserExpression)
                        {
                            CloseToUserExpression fec = (CloseToUserExpression) focusExpressionValue;
                            fec.addRefinement(refinement);
                        }
                    }
                }
                else
                {
                    logger.warn("Both target concept and target conceptId for refinement are null!");
                }
            }
        }

        if(situationExpression != null && situationExpression.getFocusConcept() != null)
        {
            CloseToUserExpression closeToUserExpression =
                    new CloseToUserExpressionImpl(situationWECConcept);
            // add context expression as refinements
            closeToUserExpression.addRefinement(situationExpression.getAssociatedContextExpression());
            closeToUserExpression.addRefinement(situationExpression.getSubjectRelationshipContextExpression());
            closeToUserExpression.addRefinement(situationExpression.getTemporalContextExpression());
            closeToUserExpression.addRefinement(situationExpression.getFocusExpression());

            return closeToUserExpression;
        }
        else
        {
            throw new IllegalArgumentException("Focus concepts not detected in expression to be converted.");
        }
    }

    /**
     * Gets the as property expression.
     *
     * @param attributeConcept the attribute concept
     * @param valueConcept the value concept
     * @return the as property expression
     */
    private SnomedRelationshipPropertyExpression getAsPropertyExpression(SnomedConcept attributeConcept,
                                                                         SnomedConcept valueConcept){
        SnomedRelationshipPropertyExpression propertyExpression = new SnomedRelationshipPropertyExpression();
        propertyExpression.setRelationshipFromConcept(attributeConcept);
        propertyExpression.getRelationship().setTargetConcept(valueConcept);
        propertyExpression.setExpression(new CloseToUserExpressionImpl(valueConcept));

        return propertyExpression;
    }

    /**
     * Sets the temporal expression.
     *
     * @param expression the expression
     * @param valueConcept the value concept
     */
    protected void setTemporalExpression(SituationExpression expression, SnomedConcept valueConcept){
        expression.setTemporalContextExpression(getAsPropertyExpression(temporalContextAttributeConcept, valueConcept));
    }

    /**
     * Sets the subject relationship expression.
     *
     * @param expression the expression
     * @param valueConcept the value concept
     */
    protected void setSubjectRelationshipExpression(SituationExpression expression, SnomedConcept valueConcept){
        expression.setSubjectRelationshipContextExpression(getAsPropertyExpression(subjectRelationshipContextAttributeConcept, valueConcept));
    }

    /**
     * Sets the associated context expression.
     *
     * @param expression the expression
     * @param valueConcept the value concept
     */
    protected void setAssociatedContextExpression(SituationExpression expression, SnomedConcept valueConcept){
        if(expression instanceof FindingSituationExpression)
        {
            expression.setAssociatedContextExpression(getAsPropertyExpression(findingContextAttributeConcept, valueConcept));
        }
        else if(expression instanceof ProcedureSituationExpression)
        {
            expression.setAssociatedContextExpression(getAsPropertyExpression(procedureContextAttributeConcept, valueConcept));
        }
    }

    /**
     * Sets the focus expression.
     *
     * @param expression the expression
     * @param concept the concept
     */
    protected void  setFocusExpression(SituationExpression expression, SnomedConcept concept) {
        if(isValidFocusConcept(expression, concept))
        {
            if (expression instanceof FindingSituationExpression)
            {
                expression.setFocusExpression(getAsPropertyExpression(associatedFindingAttributeConcept, concept));
                expression.setFocusConcept(concept);
            }
            else if(expression instanceof ProcedureSituationExpression)
            {
                expression.setFocusExpression(getAsPropertyExpression(associatedProcedureAttributeoncept, concept));
                expression.setFocusConcept(concept);
            }
        }
        else
        {
            throw new IllegalArgumentException("Concept passed is not a valid focus concept for expresion.");
        }
    }

    /**
     * Wrap as role group and add as child expression.
     *
     * @param expression the expression
     */
    private void wrapAsRoleGroupAndAddAsChildExpression(SituationExpression expression){
        // loop through the context expressions and add them to a role group expression and set as child expression
        SnomedRoleGroupExpression roleGroupExpression = new SnomedRoleGroupExpression(new SnomedRoleGroupImpl());
        roleGroupExpression.addChildExpression(expression.getFocusExpression());
        roleGroupExpression.addChildExpression(expression.getAssociatedContextExpression());
        roleGroupExpression.addChildExpression(expression.getTemporalContextExpression());
        roleGroupExpression.addChildExpression(expression.getSubjectRelationshipContextExpression());

        // add as child expression
        expression.addChildExpression(roleGroupExpression);
    }

    /**
     * Checks if is valid focus concept.
     *
     * @param situationExpression the situation expression
     * @param concept the concept
     * @return true, if is valid focus concept
     */
    private boolean isValidFocusConcept(SituationExpression situationExpression, SnomedConcept concept){
        ConceptType type = concept.getType();
        return situationExpression.getPermittedTypes().contains(type);
    }

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }
}
