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

import javax.persistence.*;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An implementation of a {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry} that
 * contains a {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalFeatureEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 16, 2009 at 1:54:01 PM.
 */

@Embeddable
@Entity(name = "Feature_Entry")
@DiscriminatorValue(value = "Feature_Entry")
public class ClinicalFeatureEntry extends AbstractClinicalObservationEntry{

    /** The value. */
    private double value = 0;
    /**
     * Instantiates a new abstract clinical feature entry.
     * 
     * @param patientId the patient id
     * @param entity the entity
     */
    public ClinicalFeatureEntry(long patientId, ClinicalEntity entity) {
        this(patientId, entity.getType().name(), entity, 0);
    }

    /**
     * Instantiates a new clinical feature entry.
     *
     * @param patientId the patient id
     * @param entity the entity
     * @param value the value
     */
    public ClinicalFeatureEntry(long patientId, ClinicalEntity entity, double value) {
        this(patientId, entity.getType().name(), entity, value);
    }
    
    /**
     * Instantiates a new abstract clinical feature entry.
     *
     * @param freeTextEntry the free text entry
     * @param attestationTime the attestation time
     * @param patientId the patient id
     * @param entryType the entry type
     * @param entity the entity
     * @param value the value
     */
    public ClinicalFeatureEntry(String freeTextEntry, Calendar attestationTime, long patientId,
                                String entryType, ClinicalEntity entity, double value) {
        super(freeTextEntry, attestationTime, patientId, entryType, entity);
        this.value = value;
    }

    /**
     * Instantiates a new abstract clinical feature entry.
     *
     * @param freeTextEntry the free text entry
     * @param patientId the patient id
     * @param entryType the entry type
     * @param entity the entity
     * @param value the value
     */
    public ClinicalFeatureEntry(String freeTextEntry, long patientId, String entryType,
                                ClinicalEntity entity, double value) {
        this(freeTextEntry, Calendar.getInstance(), patientId, entryType, entity, value);
    }

    /**
     * Instantiates a new abstract clinical feature entry.
     *
     * @param patientId the patient id
     * @param entryType the entry type
     * @param entity the entity
     * @param value the value
     */
    public ClinicalFeatureEntry(long patientId, String entryType, ClinicalEntity entity, double value) {
        this(entity.getName(), patientId, entryType, entity, value);
    }

    /**
     * Instantiates a new abstract clinical feature entry.
     */
    public ClinicalFeatureEntry() {
    	super();
    }

//    /* (non-Javadoc)
//     * @see uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.AbstractClinicalObservationEntry#getValidEntityType()
//     */
//    @Override
//    @Transient
//    protected ClinicalEntity.Type getValidEntityType() {
//        return ClinicalEntity.Type.FEATURE;
//    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    @Column(name = "value")
    @Index(name = "IDX_ENTRY_VALUE")
    public double getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(double value) {
        this.value = value;
    }
}
