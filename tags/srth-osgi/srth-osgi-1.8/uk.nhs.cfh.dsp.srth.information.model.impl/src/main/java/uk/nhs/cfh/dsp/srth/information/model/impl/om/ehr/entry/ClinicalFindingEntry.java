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

import javax.persistence.*;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An abstract class that represents a {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry}
 * that holds a {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalFindingEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 6, 2009 at 6:31:29 PM
 */

@Embeddable
@Entity(name = "Finding_Entry")
@DiscriminatorValue(value = "Finding_Entry")
public class ClinicalFindingEntry extends AbstractClinicalObservationEntry{

    /**
     * Instantiates a new abstract clinical finding entry.
     * 
     * @param patientId the patient id
     * @param entity the entity
     */
    public ClinicalFindingEntry(long patientId, ClinicalEntity entity) {
        this(patientId, entity.getType().name(), entity);
    }

    /**
     * Instantiates a new abstract clinical finding entry.
     * 
     * @param freeTextEntry the free text entry
     * @param attestationTime the attestation time
     * @param patientId the patient id
     * @param entryType the entry type
     * @param entity the entity
     */
    public ClinicalFindingEntry(String freeTextEntry, Calendar attestationTime, long patientId, String entryType,
                                ClinicalEntity entity) {
        super(freeTextEntry, attestationTime, patientId, entryType, entity);
    }

    /**
     * Instantiates a new abstract clinical finding entry.
     * 
     * @param freeTextEntry the free text entry
     * @param patientId the patient id
     * @param entryType the entry type
     * @param entity the entity
     */
    public ClinicalFindingEntry(String freeTextEntry, long patientId, String entryType, ClinicalEntity entity) {
        this(freeTextEntry, Calendar.getInstance(), patientId, entryType, entity);
    }

    /**
     * Instantiates a new abstract clinical finding entry.
     * 
     * @param patientId the patient id
     * @param entryType the entry type
     * @param entity the entity
     */
    public ClinicalFindingEntry(long patientId, String entryType, ClinicalEntity entity) {
        this(entity.getName(), patientId, entryType, entity);
    }

    /**
     * Instantiates a new abstract clinical finding entry.
     */
    public ClinicalFindingEntry() {
        super();
    }

//    /* (non-Javadoc)
//     * @see uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.AbstractClinicalObservationEntry#getValidEntityType()
//     */
//    @Override
//    @Transient
//    protected ClinicalEntity.Type getValidEntityType() {
//        System.out.println("Making a call to getValidEntityType in FindingEntry");
//        return ClinicalEntity.Type.FINDING;
//    }
}
