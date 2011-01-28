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
package uk.nhs.cfh.dsp.srth.information.model.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.*;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A factory interface specification for generating types of {@link ClinicalEntry} objects.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 27, 2009 at 12:06:29 PM
 */

public interface ClinicalEntryFactory {

	/**
	 * Gets the clinical finding entry.
	 * 
	 * @return the clinical finding entry
	 */
	ClinicalFindingEntry getClinicalFindingEntry();

	/**
	 * Gets the clinical activity entry.
	 * 
	 * @return the clinical activity entry
	 */
	ClinicalActivityEntry getClinicalActivityEntry();

	/**
	 * Gets the clinical feature entry.
	 * 
	 * @return the clinical feature entry
	 */
	ClinicalFeatureEntry getClinicalFeatureEntry();

	/**
	 * Gets the investigation entry.
	 * 
	 * @return the investigation entry
	 */
	InvestigationEventEntry getInvestigationEntry();

	/**
	 * Gets the intervention entry.
	 * 
	 * @return the intervention entry
	 */
	InterventionEventEntry getInterventionEntry();

	/**
	 * Gets the medication entry.
	 * 
	 * @return the medication entry
	 */
	MedicationEventEntry getMedicationEntry();

	/**
	 * Gets the entry.
	 * 
	 * @param clazz the class of entry to be returned
	 * 
	 * @return the entry
	 */
	ClinicalEntry getEntry(Class clazz);

    /**
     * Gets the clinical finding entry.
     *
     * @param patientId the patient id
     * @param entity the entity
     * @param time the time
     * @return the clinical finding entry
     */
    ClinicalFindingEntry getClinicalFindingEntry(long patientId, ClinicalEntity entity, Calendar time);

    /**
     * Gets the clinical activity entry.
     *
     * @param patientId the patient id
     * @param event the event
     * @param time the time
     * @return the clinical activity entry
     */
    ClinicalActivityEntry getClinicalActivityEntry(long patientId, ClinicalEvent event, Calendar time);

    /**
     * Gets the clinical feature entry.
     *
     * @param patientId the patient id
     * @param entity the entity
     * @param time the time
     * @param value the value
     * @return the clinical feature entry
     */
    ClinicalFeatureEntry getClinicalFeatureEntry(long patientId, ClinicalEntity entity, Calendar time, double value);

    /**
     * Gets the investigation entry.
     *
     * @param patientId the patient id
     * @param event the event
     * @param time the time
     * @return the investigation entry
     */
    InvestigationEventEntry getInvestigationEntry(long patientId, ClinicalEvent event, Calendar time);

    /**
     * Gets the intervention entry.
     *
     * @param patientId the patient id
     * @param event the event
     * @param time the time
     * @return the intervention entry
     */
    InterventionEventEntry getInterventionEntry(long patientId, ClinicalEvent event, Calendar time);

    /**
     * Gets the medication entry.
     *
     * @param patientId the patient id
     * @param event the event
     * @param time the time
     * @param dose the dose
     * @return the medication entry
     */
    MedicationEventEntry getMedicationEntry(long patientId, ClinicalEvent event, Calendar time, double dose);

    /**
     * Gets the entry.
     *
     * @param clazz the class type to be returned
     * @param patientID the patient id
     * @param entity the entity
     * @param time the time
     * @return the entry
     */
    ClinicalEntry getEntry(Class clazz , long patientID, ClinicalEntity entity, Calendar time);

    /**
     * Gets the clinical finding entry.
     *
     * @param patientId the patient id
     * @param expression the expression
     * @param time the time
     * @return the clinical finding entry
     */
    ClinicalFindingEntry getClinicalFindingEntry(long patientId, Expression expression, Calendar time);

    /**
     * Gets the clinical feature entry.
     *
     * @param patientId the patient id
     * @param expression the expression
     * @param time the time
     * @param value the value
     * @return the clinical feature entry
     */
    ClinicalFeatureEntry getClinicalFeatureEntry(long patientId, Expression expression, Calendar time, double value);

    /**
     * Gets the investigation entry.
     *
     * @param patientId the patient id
     * @param expression the expression
     * @param time the time
     * @return the investigation entry
     */
    InvestigationEventEntry getInvestigationEntry(long patientId, Expression expression, Calendar time);

    /**
     * Gets the intervention entry.
     *
     * @param patientId the patient id
     * @param expression the expression
     * @param time the time
     * @return the intervention entry
     */
    InterventionEventEntry getInterventionEntry(long patientId, Expression expression, Calendar time);

    /**
     * Gets the medication entry.
     *
     * @param patientId the patient id
     * @param expression the expression
     * @param time the time
     * @param dose the dose
     * @return the medication entry
     */
    MedicationEventEntry getMedicationEntry(long patientId, Expression expression, Calendar time, double dose);

    /**
     * Sets the entity factory.
     *
     * @param entityFactory the new entity factory
     */
    void setEntityFactory(ClinicalEntityFactory entityFactory);

    ClinicalEntityFactory getEntityFactory();
}