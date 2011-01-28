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

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent.Status;

import javax.persistence.*;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An implementation of an {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEventEntry} that
 * contains an {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.InvestigationEntity}
 * .
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 16, 2009 at 2:16:45 PM.
 */

@Embeddable
@Entity(name = "Investigation_Entry")
@DiscriminatorValue(value = "Investigation_Entry")
public class InvestigationEventEntry extends AbstractBoundClinicalEventEntry{

    /**
     * Instantiates a new abstract investigation event entry.
     */
    public InvestigationEventEntry() {
    	super();
    }

    /**
     * Instantiates a new abstract investigation event entry.
     * 
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     */
    public InvestigationEventEntry(long patientId, String entryType, ClinicalEvent event, Status status) {
        this(event.getName(), patientId, entryType, event, status);
    }

    /**
     * Instantiates a new abstract investigation event entry.
     * 
     * @param freeTextEntry the free text entry
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     */
    public InvestigationEventEntry(String freeTextEntry, long patientId, String entryType, ClinicalEvent event, Status status) {
        this(freeTextEntry, Calendar.getInstance(), patientId, entryType, event, status);
    }

    /**
     * Instantiates a new abstract investigation event entry.
     * 
     * @param freeTextEntry the free text entry
     * @param attestationTime the attestation time
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     */
    public InvestigationEventEntry(String freeTextEntry, Calendar attestationTime, long patientId, String entryType, ClinicalEvent event, Status status) {
        super(freeTextEntry, attestationTime, patientId, entryType, event, status);
    }

    /**
     * Instantiates a new abstract investigation event entry.
     * 
     * @param patientId the patient id
     * @param event the event
     * @param status the status
     */
    public InvestigationEventEntry(long patientId, ClinicalEvent event, Status status) {
        this(patientId, event.getType().name(), event, status);
    }

    /**
     * Instantiates a new abstract investigation event entry.
     * 
     * @param patientId the patient id
     * @param event the event
     */
    public InvestigationEventEntry(long patientId, ClinicalEvent event) {
        this(patientId, event, Status.UNKNOWN);
    }

//    /* (non-Javadoc)
//     * @see uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.AbstractBoundClinicalEntry#getValidEntityType()
//     */
//    @Override
//    @Transient
//    protected ClinicalEntity.Type getValidEntityType() {
//        return ClinicalEntity.Type.INVESTIGATION;
//    }
}
