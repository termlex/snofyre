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

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent.Status;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalActivityEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;

import javax.persistence.DiscriminatorValue;
import javax.persistence.MappedSuperclass;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A class that represents a {@link BoundClinicalEntry} that holds a.
 *
 * {@link ClinicalActivityEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 6, 2009 at 6:33:34 PM
 */

@MappedSuperclass
@DiscriminatorValue(value = "Activity_Event_Entry")
public class ClinicalActivityEntry extends AbstractBoundClinicalEventEntry {

    /**
     * Instantiates a new abstract clinical activity entry.
     *
     * @param patientId the patient id
     * @param event the event
     */
    public ClinicalActivityEntry(long patientId, ClinicalEvent event) {
        this(patientId, event, Status.UNKNOWN);
    }

    /**
     * Instantiates a new abstract clinical activity entry.
     *
     * @param patientId the patient id
     * @param event the event
     * @param status the status
     */
    public ClinicalActivityEntry(long patientId, ClinicalEvent event, Status status) {
        this(patientId, event.getType().name(), event, status);
    }

    /**
     * Instantiates a new abstract clinical actvity entry.
     *
     * @param freeTextEntry the free text entry
     * @param attestationTime the attestation time
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     */
    public ClinicalActivityEntry(String freeTextEntry, Calendar attestationTime, long patientId,
                                 String entryType, ClinicalEvent event, Status status) {
        super(freeTextEntry, attestationTime, patientId, entryType, event, status);
    }

    /**
     * Instantiates a new abstract clinical activity entry.
     *
     * @param freeTextEntry the free text entry
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     */
    public ClinicalActivityEntry(String freeTextEntry, long patientId, String entryType,
                                 ClinicalEvent event, Status status) {
        this(freeTextEntry, Calendar.getInstance(), patientId, entryType, event, status);
    }

    /**
     * Instantiates a new abstract clinical actvity entry.
     *
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     */
    public ClinicalActivityEntry(long patientId, String entryType, ClinicalEvent event, Status status) {
        this(event.getName(), patientId, entryType, event, status);
    }

    /**
     * Instantiates a new abstract clinical activity entry.
     */
    public ClinicalActivityEntry() {
        super();
    }

//    /* (non-Javadoc)
//     * @see uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.AbstractBoundClinicalEntry#getValidEntityType()
//     */
//    @Override
//    @Transient
//    protected ClinicalEntity.Type getValidEntityType() {
//        return ClinicalEntity.Type.ACTIVITY;
//    }

//    /* (non-Javadoc)
//    * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.AbstractBoundClinicalEventEntry#setEvent(uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent)
//    */
//    @Override
//    @Transient
//    public void setEvent(ClinicalEvent event) {
//        ClinicalEntity.Type type = event.getType();
//        if(getValidEntityType().equals(type) ||
//                ClinicalEvent.Type.INTERVENTION.equals(type) || ClinicalEntity.Type.INVESTIGATION.equals(type)
//                || ClinicalEvent.Type.MEDICATION.equals(type))
//        {
//            super.setEvent(event);
//        }
//        else
//        {
//            throw new IllegalArgumentException("Event passed must be "+getEntryType()+"." +
//                    "Event found was of type : "+event.getType());
//        }
//    }
}
