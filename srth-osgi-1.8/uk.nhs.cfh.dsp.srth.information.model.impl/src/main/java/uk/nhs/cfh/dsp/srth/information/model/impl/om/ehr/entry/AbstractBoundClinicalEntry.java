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
import org.hibernate.annotations.Type;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.clinical.entity.AbstractClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An abstract class representing an {@link BoundClinicalEntry} implementation.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 6, 2009 at 3:19:46 PM
 */

@Table(name = "CLINICAL_ENTRY")
@Entity(name = "Clinical_Entry")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ER_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractBoundClinicalEntry implements BoundClinicalEntry{

    /** The free text entry. */
    private String freeTextEntry;

    /** The attestation time. */
    private Calendar attestationTime;

    /** The patient id. */
    private long patientId;

    /** The entry type. */
    private String entryType;

    /** The entity. */
    private ClinicalEntity entity;

    /** The canonical form. */
    private String canonicalForm;

    /** The uuid. */
    private UUID uuid = UUID.randomUUID();

    /**
     * Instantiates a new abstract bound clinical entry.
     */
    public AbstractBoundClinicalEntry() {
        // an empty constructor to enable persistence
        attestationTime = Calendar.getInstance();
    }

    /**
     * Instantiates a new abstract bound clinical entry.
     *
     * @param freeTextEntry the free text entry
     * @param attestationTime the attestation time
     * @param patientId the patient id
     * @param entryType the entry type
     * @param entity the entity
     */
    protected AbstractBoundClinicalEntry(String freeTextEntry, Calendar attestationTime, long patientId,
                                      String entryType, ClinicalEntity entity) {
        this.freeTextEntry = freeTextEntry;
        this.attestationTime = attestationTime;
        this.patientId = patientId;
        this.entryType = entryType;
        this.entity = entity;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#getAttestationTime()
     */
    /**
     * Gets the attestation time.
     *
     * @return the attestation time
     */
    @Column(name = "attestation_time", nullable = false, columnDefinition = "date")
    public Calendar getAttestationTime() {
        return attestationTime;
    }

    /**
     * Sets the attestation time.
     *
     * @param attestationTime the attestation time
     *
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#setAttestationTime(java.util.Calendar)
     */
    public void setAttestationTime(Calendar attestationTime) {
        this.attestationTime = attestationTime;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#getEntity()
     */
    /**
     * Gets the entity.
     *
     * @return the entity
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractClinicalEntity.class)
    public ClinicalEntity getEntity() {
        return entity;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#setEntity(uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity)
     */
    /**
     * Sets the entity.
     *
     * @param entity the new entity
     */
    public void setEntity(ClinicalEntity entity) {
        this.entity = entity;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#getEntryType()
     */
    /**
     * Gets the entry type.
     *
     * @return the entry type
     */
    @Column(name = "entry_type")
    public String getEntryType() {
        return entryType;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#setEntryType(java.lang.String)
     */
    /**
     * Sets the entry type.
     *
     * @param entryType the new entry type
     */
    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#getFreeTextEntry()
     */
    /**
     * Gets the free text entry.
     *
     * @return the free text entry
     */
    @Column(name = "free_text_entry", columnDefinition = "VARCHAR(2560)")
    public String getFreeTextEntry() {
        return freeTextEntry;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#setFreeTextEntry(java.lang.String)
     */
    /**
     * Sets the free text entry.
     *
     * @param freeTextEntry the new free text entry
     */
    public void setFreeTextEntry(String freeTextEntry) {
        this.freeTextEntry = freeTextEntry;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#getPatientId()
     */
    /**
     * Gets the patient id.
     *
     * @return the patient id
     */
    @Column(name = "patient_id")
    @Index(name = "IDX_ER_PT_ID")
    public long getPatientId() {
        return patientId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry#setPatientId(java.lang.String)
     */
    /**
     * Sets the patient id.
     *
     * @param patientId the new patient id
     */
    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the canonical form.
     *
     * @return the canonical form
     */
    @Column(name = "canonical_ctu_form", columnDefinition = "VARCHAR(2560)")
    @Index(name = "IDX_ER_CFG")
    public String getCanonicalForm() {
        return canonicalForm;
    }

    /**
     * Sets the canonical form.
     *
     * @param canonicalForm the new canonical form
     */
    public void setCanonicalForm(String canonicalForm) {
        this.canonicalForm = canonicalForm;
    }

    /**
     * Gets the uuid.
     *
     * @return the uuid
     */
    @Id
    @Column(name = "ctu_uuid", nullable = false, columnDefinition = "VARBINARY(18)")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    @Index(name = "IDX_ER_CTU_UUID")
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the uuid.
     *
     * @param uuid the new uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
