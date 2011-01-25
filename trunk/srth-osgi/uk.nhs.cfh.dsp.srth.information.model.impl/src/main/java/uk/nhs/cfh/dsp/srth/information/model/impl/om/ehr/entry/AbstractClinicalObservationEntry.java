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
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalObservationEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;

import javax.persistence.*;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An abstract class that represents a {@link BoundClinicalEntry} that holds a.
 *
 * {@link ClinicalObservationEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 6, 2009 at 3:44:57 PM
 */

@MappedSuperclass
@DiscriminatorValue(value = "Abstract_Observation_Event_Entry")
public abstract class AbstractClinicalObservationEntry extends AbstractBoundClinicalEntry{

    /**
     * Instantiates a new abstract clinical observation entry.
     */
    public AbstractClinicalObservationEntry() {
    	super();
    }

    /**
     * Instantiates a new abstract clinical observation entry.
     *
     * @param freeTextEntry the free text entry
     * @param attestationTime the attestation time
     * @param patientId the patient id
     * @param entryType the entry type
     * @param entity the entity
     */
    protected AbstractClinicalObservationEntry(String freeTextEntry, Calendar attestationTime, long patientId, String entryType, ClinicalEntity entity) {
        super(freeTextEntry, attestationTime, patientId, entryType, entity);
    }

//    /* (non-Javadoc)
//     * @see uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.AbstractBoundClinicalEntry#getValidEntityType()
//     */
//    @Override
//    @Transient
//    protected ClinicalEntity.Type getValidEntityType() {
//        return ClinicalEntity.Type.OBSERVATION;
//    }
}
