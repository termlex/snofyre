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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry;

import org.hibernate.annotations.Index;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent.Status;

import javax.persistence.*;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A implementation of a {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEventEntry} that
 * contains a {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.MedicationEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 16, 2009 at 2:19:50 PM.
 */

@Embeddable
@Entity(name = "Medication_Entry")
@DiscriminatorValue(value = "Medication_Entry")
public class MedicationEventEntry extends AbstractBoundClinicalEventEntry{

    /** The dose. */
    private double dose = 0;

    /**
     * Instantiates a new abstract medication event entry.
     */
    public MedicationEventEntry() {
    	super();
    }

    /**
     * Instantiates a new abstract medication event entry.
     *
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     * @param dose the dose
     */
    public MedicationEventEntry(long patientId, String entryType, ClinicalEvent event,
                                Status status, double dose) {
        this(event.getName(), patientId, entryType, event, status, dose);
    }

    /**
     * Instantiates a new abstract medication event entry.
     *
     * @param freeTextEntry the free text entry
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     * @param dose the dose
     */
    public MedicationEventEntry(String freeTextEntry, long patientId, String entryType, ClinicalEvent event,
                                Status status, double dose) {
        this(freeTextEntry, Calendar.getInstance(), patientId, entryType, event, status, dose);
    }

    /**
     * Instantiates a new abstract medication event entry.
     *
     * @param freeTextEntry the free text entry
     * @param attestationTime the attestation time
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     * @param dose the dose
     */
    public MedicationEventEntry(String freeTextEntry, Calendar attestationTime, long patientId, String entryType,
                                ClinicalEvent event, Status status, double dose) {
        super(freeTextEntry, attestationTime, patientId, entryType, event, status);
        this.dose = dose;
    }

    /**
     * Instantiates a new abstract medication event entry.
     *
     * @param patientId the patient id
     * @param event the event
     * @param status the status
     * @param dose the dose
     */
    public MedicationEventEntry(long patientId, ClinicalEvent event, Status status, double dose) {
        this(patientId, event.getType().name(), event, status, dose);
    }

    /**
     * Instantiates a new abstract medication event entry.
     * 
     * @param patientId the patient id
     * @param event the event
     */
    public MedicationEventEntry(long patientId, ClinicalEvent event) {
        this(patientId, event, Status.UNKNOWN, 0);
    }

//    /* (non-Javadoc)
//     * @see uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.AbstractBoundClinicalEntry#getValidEntityType()
//     */
//    @Override
//    @Transient
//    protected ClinicalEntity.Type getValidEntityType() {
//        return ClinicalEntity.Type.MEDICATION;
//    }

    /**
     * Gets the dose.
     *
     * @return the dose
     */
    @Column(name = "dose")
    @Index(name = "IDX_ENTRY_DOSE")
    public double getDose() {
        return dose;
    }

    /**
     * Sets the dose.
     *
     * @param dose the new dose
     */
    public void setDose(double dose) {
        this.dose = dose;
    }
}
