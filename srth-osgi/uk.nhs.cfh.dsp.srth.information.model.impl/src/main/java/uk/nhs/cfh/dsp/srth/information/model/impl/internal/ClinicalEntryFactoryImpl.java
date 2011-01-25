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

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.*;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.BoundClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 27, 2009 at 11:54:34 AM
 */

public class ClinicalEntryFactoryImpl implements ClinicalEntryFactory {

    /** The entity factory. */
    private ClinicalEntityFactory entityFactory;

    /**
     * Instantiates a new clinical entry factory impl.
     *
     * @param entityFactory the entity factory
     */
    public ClinicalEntryFactoryImpl(ClinicalEntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    /**
     * Empty constructor for IOC.
     */
    public ClinicalEntryFactoryImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getClinicalFindingEntry()
     */
    public ClinicalFindingEntry getClinicalFindingEntry(){
        return new ClinicalFindingEntry();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getClinicalActivityEntry()
     */
    public ClinicalActivityEntry getClinicalActivityEntry(){
        return new ClinicalActivityEntry();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getClinicalFeatureEntry()
     */
    public ClinicalFeatureEntry getClinicalFeatureEntry(){
        return new ClinicalFeatureEntry();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getInvestigationEntry()
     */
    public InvestigationEventEntry getInvestigationEntry(){
        return new InvestigationEventEntry();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getInterventionEntry()
     */
    public InterventionEventEntry getInterventionEntry(){
        return new InterventionEventEntry();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getMedicationEntry()
     */
    public MedicationEventEntry getMedicationEntry(){
        return new MedicationEventEntry();
    }

    /**
     * Sets the entity and fields.
     *
     * @param entry the entry
     * @param entity the entity
     * @param time the time
     */
    private void setEntityAndFields(BoundClinicalEntry entry, BoundClinicalEntity entity, Calendar time){
        // set entity using setter, which forces type check
        entry.setEntity(entity);
        entry.setAttestationTime(time);
        ClinicalExpression expression = entity.getExpression();
        // populate fields from expression
        entry.setCanonicalForm(expression.getCompositionalGrammarForm());
        entry.setUuid(expression.getUuid());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getClinicalFindingEntry(java.lang.String, uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity, java.util.Calendar)
     */
    public ClinicalFindingEntry getClinicalFindingEntry(long patientId, ClinicalEntity entity, Calendar time){
        ClinicalFindingEntry entry = new ClinicalFindingEntry(patientId, entity);
        setEntityAndFields(entry, (BoundClinicalEntity) entity, time);

        return entry;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getClinicalActivityEntry(java.lang.String, uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent, java.util.Calendar)
     */
    public ClinicalActivityEntry getClinicalActivityEntry(long patientId, ClinicalEvent event, Calendar time){
        ClinicalActivityEntry entry = new ClinicalActivityEntry(patientId, event);
        setEntityAndFields(entry, (BoundClinicalEntity) event, time);

        return entry;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getClinicalFeatureEntry(java.lang.String, uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity, java.util.Calendar, double)
     */
    public ClinicalFeatureEntry getClinicalFeatureEntry(long patientId, ClinicalEntity entity, Calendar time, double value){
        ClinicalFeatureEntry entry =  new ClinicalFeatureEntry(patientId, entity);
        setEntityAndFields(entry, (BoundClinicalEntity) entity, time);
        entry.setValue(value);

        return entry;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getInvestigationEntry(java.lang.String, uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent, java.util.Calendar)
     */
    public InvestigationEventEntry getInvestigationEntry(long patientId, ClinicalEvent event, Calendar time){
        InvestigationEventEntry entry = new InvestigationEventEntry(patientId, event);
        setEntityAndFields(entry, (BoundClinicalEntity) event, time);

        return entry;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getInterventionEntry(java.lang.String, uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent, java.util.Calendar)
     */
    public InterventionEventEntry getInterventionEntry(long patientId, ClinicalEvent event, Calendar time){
        InterventionEventEntry entry = new InterventionEventEntry(patientId, event);
        setEntityAndFields(entry, (BoundClinicalEntity) event, time);

        return entry;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getMedicationEntry(java.lang.String, uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent, java.util.Calendar, double)
     */
    public MedicationEventEntry getMedicationEntry(long patientId, ClinicalEvent event, Calendar time, double dose){
        MedicationEventEntry entry = new MedicationEventEntry(patientId, event);
        setEntityAndFields(entry, (BoundClinicalEntity) event, time);
        entry.setDose(dose);

        return entry;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getClinicalFindingEntry(java.lang.String, uk.nhs.cfh.dsp.snomed.expression.model.Expression, java.util.Calendar)
     */
    public ClinicalFindingEntry getClinicalFindingEntry(long patientId, Expression expression, Calendar time){
        return getClinicalFindingEntry(patientId, entityFactory.getClinicalFindingEntity(expression), time);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getClinicalFeatureEntry(java.lang.String, uk.nhs.cfh.dsp.snomed.expression.model.Expression, java.util.Calendar, double)
     */
    public ClinicalFeatureEntry getClinicalFeatureEntry(long patientId, Expression expression, Calendar time, double value){
        return getClinicalFeatureEntry(patientId, entityFactory.getClinicalFeatureEntity(expression, value), time, value);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getInvestigationEntry(java.lang.String, uk.nhs.cfh.dsp.snomed.expression.model.Expression, java.util.Calendar)
     */
    public InvestigationEventEntry getInvestigationEntry(long patientId, Expression expression, Calendar time){
        return getInvestigationEntry(patientId, entityFactory.getInvestigationEntity(expression), time);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getInterventionEntry(java.lang.String, uk.nhs.cfh.dsp.snomed.expression.model.Expression, java.util.Calendar)
     */
    public InterventionEventEntry getInterventionEntry(long patientId, Expression expression, Calendar time){
        return getInterventionEntry(patientId, entityFactory.getInterventionEntity(expression), time);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getMedicationEntry(java.lang.String, uk.nhs.cfh.dsp.snomed.expression.model.Expression, java.util.Calendar, double)
     */
    public MedicationEventEntry getMedicationEntry(long patientId, Expression expression, Calendar time, double dose){
        return getMedicationEntry(patientId, entityFactory.getMedicationEntity(expression, dose), time, dose);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getEntry(java.lang.Class)
     */
    public ClinicalEntry getEntry(Class clazz){
        if(clazz == null)
        {
            throw new IllegalArgumentException("Entry type can not be null");
        }
        else if (clazz.equals(ClinicalActivityEntry.class))
        {
            return getClinicalActivityEntry();
        }
        else if(clazz.equals(ClinicalFindingEntry.class))
        {
            return getClinicalFindingEntry();
        }
        else if(clazz.equals(ClinicalFeatureEntry.class) )
        {
            return getClinicalFeatureEntry();
        }
        else if(clazz.equals(MedicationEventEntry.class)){
            return getMedicationEntry();
        }
        else if (clazz.equals(InvestigationEventEntry.class)){
            return getInvestigationEntry();
        }
        else if(clazz.equals(InterventionEventEntry.class))
        {
            return getInterventionEntry();
        }
        else
        {
            throw new UnsupportedOperationException("Unsupported entry passed : "+clazz.getName());
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#getEntry(java.lang.Class, java.lang.String, uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity, java.util.Calendar)
     */
    public ClinicalEntry getEntry(Class clazz , long patientID, ClinicalEntity entity, Calendar time){
        if(clazz == null)
        {
            throw new IllegalArgumentException("Entry type can not be null");
        }
        else if (clazz.equals(ClinicalActivityEntry.class))
        {
            return getClinicalActivityEntry(patientID, (ClinicalEvent) entity, time);
        }
        else if(clazz.equals(ClinicalFindingEntry.class))
        {
            return getClinicalFindingEntry(patientID, entity, time);
        }
        else if(clazz.equals(ClinicalFeatureEntry.class))
        {
            return getClinicalFeatureEntry(patientID, entity, time, 0);
        }
        else if(clazz.equals(MedicationEventEntry.class)){
            return getMedicationEntry(patientID, (ClinicalEvent) entity, time, 0);
        }
        else if (clazz.equals(InvestigationEventEntry.class)){
            return getInvestigationEntry();
        }
        else if(clazz.equals(InterventionEventEntry.class))
        {
            return getInterventionEntry(patientID , (ClinicalEvent) entity, time);
        }
        else
        {
            throw new UnsupportedOperationException("Unsupported entry passed : "+clazz.getName());
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory#setEntityFactory(uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory)
     */
    public synchronized void setEntityFactory(ClinicalEntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public ClinicalEntityFactory getEntityFactory() {
        return entityFactory;
    }
}
