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
/**
 *
 */
package uk.nhs.cfh.dsp.srth.information.model.impl.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.clinical.entity.*;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity.Type;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpressionType;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.*;

import java.util.Calendar;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 *  A concrete implementation of a  {@link ClinicalEntityFactory}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 27, 2009 at 11:08:36 AM
 */

public class ClinicalEntityFactoryImpl implements ClinicalEntityFactory {

    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;
    
    /** The situation expression generator. */
    private SituationExpressionGenerator situationExpressionGenerator;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ClinicalEntityFactoryImpl.class);

    /**
     * Instantiates a new clinical entity factory impl.
     *
     * @param normalFormGenerator the normal form generator
     * @param humanReadableRender the human readable render
     * @param situationExpressionGenerator the situation expression generator
     */
    public ClinicalEntityFactoryImpl(NormalFormGenerator normalFormGenerator,
                                     HumanReadableRender humanReadableRender,
                                     SituationExpressionGenerator situationExpressionGenerator) {
        this.normalFormGenerator = normalFormGenerator;
        this.humanReadableRender = humanReadableRender;
        this.situationExpressionGenerator = situationExpressionGenerator;
    }

    /**
     * empty constructor for IOC.
     */
    public ClinicalEntityFactoryImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getClinicalFeatureEntity()
     */
    public ClinicalFeatureEntity getClinicalFeatureEntity(){
        return new ClinicalFeatureEntityImpl();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getClinicalFindingEntity()
     */
    public ClinicalFindingEntity getClinicalFindingEntity(){
        return new ClinicalFindingEntityImpl();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getInvestigationEntity()
     */
    public InvestigationEntity getInvestigationEntity(){
        return new InvestigationEntityImpl();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getInterventionEntity()
     */
    public InterventionEntity getInterventionEntity(){
        return new InterventionEntityImpl();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getMedicationEntity()
     */
    public MedicationEntity getMedicationEntity(){
        return new MedicationEntityImpl();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getAnatomicalLocationEntity()
     */
    public AnatomicalLocationEntity getAnatomicalLocationEntity(){
        return new AnatomicalLocationEntityImpl();
    }

    // -- utility metohds that populate an entity from a normalFormExpression -- //

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getClinicalFeatureEntity(uk.nhs.cfh.dsp.snomed.expression.model.Expression, double)
     */
    public ClinicalFeatureEntity getClinicalFeatureEntity(Expression expression, double value){

        // generate values from normalFormExpression
        ClinicalExpression clinicalExpression = getExpression(expression);
        String name = getName(expression);

        return new ClinicalFeatureEntityImpl(name, Calendar.getInstance(), Calendar.getInstance(),
                Type.OBSERVATION, clinicalExpression, ClinicalExpressionType.OBSERVATION_WEC, value);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getClinicalFindingEntity(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public ClinicalFindingEntity getClinicalFindingEntity(Expression expression){
        // generate values from normalFormExpression
        ClinicalExpression clinicalExpression = getExpression(expression);
        String name = getName(expression);
        Laterality laterality = getLaterality(expression);

        return new ClinicalFindingEntityImpl(name, clinicalExpression, ClinicalExpressionType.FINDING_WEC, laterality);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getInvestigationEntity(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public InvestigationEntity getInvestigationEntity(Expression expression){

        // generate values from normalFormExpression
        ClinicalExpression clinicalExpression = getExpression(expression);
        String name = getName(expression);

        return new InvestigationEntityImpl(name, Calendar.getInstance(), Calendar.getInstance(),
                Type.INVESTIGATION, clinicalExpression, ClinicalExpressionType.INVESTIGATION_PROCEDURE);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getInterventionEntity(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public InterventionEntity getInterventionEntity(Expression expression){

        // generate values from normalFormExpression
        ClinicalExpression clinicalExpression = getExpression(expression);
        String name = getName(expression);
        Laterality laterality = getLaterality(expression);

        return new InterventionEntityImpl(name, Calendar.getInstance(), Calendar.getInstance(),
                Type.INTERVENTION, clinicalExpression, ClinicalExpressionType.PROCEDURE_WEC, laterality);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getMedicationEntity(uk.nhs.cfh.dsp.snomed.expression.model.Expression, double)
     */
    public MedicationEntity getMedicationEntity(Expression expression, double dose){
        // generate values from normalFormExpression
        ClinicalExpression clinicalExpression = getExpression(expression);
        String name = getName(expression);

        return new MedicationEntityImpl(name, Calendar.getInstance(), Calendar.getInstance(),
                Type.MEDICATION, clinicalExpression, ClinicalExpressionType.MEDICATION_PROCEDURE, dose, "NO_UNITS");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getAnatomicalLocationEntity(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public AnatomicalLocationEntity getAnatomicalLocationEntity(Expression expression){
        // generate values from normalFormExpression
        ClinicalExpression clinicalExpression = getExpression(expression);
        String name = getName(expression);
        Laterality laterality = getLaterality(expression);

        return new AnatomicalLocationEntityImpl(name, Calendar.getInstance(), Calendar.getInstance(),
                Type.ANATOMICAL_LOCATION, clinicalExpression, ClinicalExpressionType.BODY_STRUCTURE, laterality,
                AnatomicalLocationEntity.Parity.UNDEFINED);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getEntity(ClinicalEntity.Type)
     */
    public ClinicalEntity getEntity(ClinicalEntity.Type type){

        if(type == null)
        {
            throw new IllegalArgumentException("Type passed can not be null");
        }
        else if(Type.FINDING == type)
        {
            return getClinicalFindingEntity();
        }
        else if(Type.ANATOMICAL_LOCATION == type)
        {
            return getAnatomicalLocationEntity();
        }
        else if (Type.INTERVENTION == type)
        {
            return getInterventionEntity();
        }
        else if (Type.INVESTIGATION == type)
        {
            return getInvestigationEntity();
        }
        else if(Type.MEDICATION == type)
        {
            return getMedicationEntity();
        }
        else if(Type.FEATURE == type)
        {
            return getClinicalFeatureEntity();
        }
        else
        {
            throw new UnsupportedOperationException("The type passed is not supported : "+type.name());
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory#getExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public ClinicalExpression getExpression(Expression expression) {

        if(expression instanceof CloseToUserExpression)
        {
            CloseToUserExpression closeToUserExpression = (CloseToUserExpression) expression;
            if (logger.isDebugEnabled()) {
                logger.debug("closeToUserExpression.getCompositionalGrammarForm() = " + closeToUserExpression.getCompositionalGrammarForm());
            }
            NormalFormExpression normalFormExpression = normalFormGenerator.getLongNormalFormExpression(closeToUserExpression);
            if (logger.isDebugEnabled()) {
                logger.debug("normalFormExpression.getCompositionalGrammarForm() = " + normalFormExpression.getCompositionalGrammarForm());
            }

            ClinicalExpression clinicalExpression = new ClinicalExpressionImpl(closeToUserExpression);
            clinicalExpression.setCompositionalGrammarForm(closeToUserExpression.getCompositionalGrammarForm());
            clinicalExpression.setUuid(closeToUserExpression.getUuid());

            // set values from normal form expression
            clinicalExpression.setNormalFormExpression(normalFormExpression);
            clinicalExpression.setCompositionalGrammarNormalForm(normalFormExpression.getCompositionalGrammarForm());
            clinicalExpression.setNormalFormUuid(normalFormExpression.getUuid());

            ConceptType type = closeToUserExpression.getSingletonFocusConceptType();
            if (ConceptType.SITUATION_WEC == type || ConceptType.FINDING_WEC == type
                    || ConceptType.PROCEDURE_WEC == type)
            {
                // situation normal form is same as normal form
                clinicalExpression.setCompositionalSituationNormalForm(normalFormExpression.getCompositionalGrammarForm());
                clinicalExpression.setSituationNormalFormUuid(normalFormExpression.getUuid());
            }
            else
            {
                // get new close to user form inside situation wrapper
                CloseToUserExpression situationCloseToUserExpression =
                        situationExpressionGenerator.getSituationWithAssertableContext(closeToUserExpression);
                if (logger.isDebugEnabled()) {
                    logger.debug("situationCloseToUserExpression.getCompositionalGrammarForm() = " + situationCloseToUserExpression.getCompositionalGrammarForm());
                }
                NormalFormExpression situationNormalFormExpression =
                        normalFormGenerator.getLongNormalFormExpression(situationCloseToUserExpression);
                if (logger.isDebugEnabled()) {
                    logger.debug("situationNormalFormExpression.getCompositionalGrammarForm() = " + situationNormalFormExpression.getCompositionalGrammarForm());
                }
                clinicalExpression.setCompositionalSituationNormalForm(situationNormalFormExpression.getCompositionalGrammarForm());
                clinicalExpression.setSituationNormalFormUuid(situationNormalFormExpression.getUuid());
            }

            return clinicalExpression;
        }
        else
        {
            throw new IllegalArgumentException("Expression passed must be a type of Close to user expression.");
        }
    }

    /**
     * Gets the name.
     *
     * @param expression the expression
     * @return the name
     */
    private String getName(Expression expression){
        if(expression != null)
        {
            return humanReadableRender.getHumanReadableLabel(expression);
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /**
     * Gets the laterality.
     *
     * @param expression the expression
     * @return the laterality
     */
    private Laterality getLaterality(Expression expression){

        if(expression != null)
        {
            Laterality laterality = Laterality.UNDEFINED;

            Collection<Expression> childExpressions = expression.getChildExpressions();
            if (childExpressions.size() >1)
            {
                // loop through all contained expression and get value of attribute with id
                for(Expression childExpression : childExpressions)
                {
                    if(childExpression instanceof SnomedRelationshipPropertyExpression)
                    {
                        SnomedRelationshipPropertyExpression propertyExpression =
                                (SnomedRelationshipPropertyExpression) childExpression;
                        // get attribute id and see if it is '272741003'
                        String attributeId = propertyExpression.getRelationship().getRelationshipType();
                        if("272741003".equalsIgnoreCase(attributeId))
                        {
                            Expression targetExpression = propertyExpression.getExpression();
                            // get value of attribute and generate laterality from it
                            laterality = Laterality.valueOf(targetExpression.getCanonicalStringForm());

                            return laterality;
                        }
                    }
                    else if(childExpression instanceof SnomedRoleGroupExpression)
                    {
                        SnomedRoleGroupExpression roleGroupExpression = (SnomedRoleGroupExpression) childExpression;
                        for(Expression e : roleGroupExpression.getChildExpressions())
                        {
                            // recursive call
                            getLaterality(e);
                        }
                    }
                }
            }

            return laterality;
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /**
     * Sets the normal form generator.
     *
     * @param normalFormGenerator the new normal form generator
     */
    public void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }

    /**
     * Sets the human readable render.
     *
     * @param humanReadableRender the new human readable render
     */
    public void setHumanReadableRender(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Sets the situation expression generator.
     *
     * @param situationExpressionGenerator the new situation expression generator
     */
    public void setSituationExpressionGenerator(SituationExpressionGenerator situationExpressionGenerator) {
        this.situationExpressionGenerator = situationExpressionGenerator;
    }
}
