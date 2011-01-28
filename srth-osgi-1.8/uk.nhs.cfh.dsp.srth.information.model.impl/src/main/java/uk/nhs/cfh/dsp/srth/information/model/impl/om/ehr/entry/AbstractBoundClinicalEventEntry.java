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

import uk.nhs.cfh.dsp.srth.information.model.impl.om.clinical.entity.AbstractClinicalEvent;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent.Status;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEventEntry;

import javax.persistence.*;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEventEntry}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 6, 2009 at 3:37:51 PM
 */

@MappedSuperclass
@DiscriminatorValue(value = "Bound_Event_Entry")
public abstract class AbstractBoundClinicalEventEntry extends AbstractBoundClinicalEntry implements BoundClinicalEventEntry{

    /** The event. */
    private ClinicalEvent event;
    
    /** The status. */
    private ClinicalEvent.Status status;

    /**
     * Instantiates a new abstract bound clinical event entry.
     */
    public AbstractBoundClinicalEventEntry() {
        super();
    }

    /**
     * Instantiates a new abstract bound clinical event entry.
     * 
     * @param freeTextEntry the free text entry
     * @param attestationTime the attestation time
     * @param patientId the patient id
     * @param entryType the entry type
     * @param event the event
     * @param status the status
     */
    public AbstractBoundClinicalEventEntry(String freeTextEntry, Calendar attestationTime, long patientId,
                                           String entryType, ClinicalEvent event, Status status) {
        super(freeTextEntry, attestationTime, patientId, entryType, event);
        this.event = event;
        this.status = status;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEventEntry#getEvent()
     */
    /**
     * Gets the event.
     * 
     * @return the event
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractClinicalEvent.class)
    public ClinicalEvent getEvent() {
        return event;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEventEntry#setEvent(uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent)
     */
    /**
     * Sets the event.
     * 
     * @param event the new event
     */
    public void setEvent(ClinicalEvent event) {
        this.event = event;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEventEntry#getStatus()
     */
    /**
     * Gets the status.
     * 
     * @return the status
     */
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEventEntry#setStatus(uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent.Status)
     */
    /**
     * Sets the status.
     * 
     * @param status the new status
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}
