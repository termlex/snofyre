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
package uk.nhs.cfh.dsp.srth.information.model.impl.om.clinical.entity;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link ClinicalEntity}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 6:25:29 PM
 */

@Embeddable
@Table(name = "CLINICAL_ENTITY")
@Entity(name = "Clinical_Entity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EI_TYPE", discriminatorType = DiscriminatorType.STRING)
public class AbstractClinicalEntity implements ClinicalEntity {

    /** The name. */
    private String name;

    /** The instantiation time. */
    private Calendar instantiationTime;

    /** The coreferences. */
    private Set<ClinicalEntity> coreferences = new HashSet<ClinicalEntity>();

    /** The clinically relevant time. */
    private Calendar clinicallyRelevantTime;

    /** The type. */
    private ClinicalEntity.Type type;

    /** The uuid. */
    private UUID uuid = UUID.randomUUID();
    
    /**
     * Instantiates a new abstract clinical entity.
     *
     * @param name the name
     * @param type the type
     */
    public AbstractClinicalEntity(String name, Type type) {
        this(name, Calendar.getInstance(), Calendar.getInstance(), type , UUID.randomUUID());
    }

    /**
     * Instantiates a new abstract clinical entity.
     *
     * @param name the name
     * @param clinicallyRelevantTime the clinically relevant time
     * @param type the type
     * @param uuid the uuid
     */
    public AbstractClinicalEntity(String name, Calendar clinicallyRelevantTime,
                                  Type type, UUID uuid) {
        this(name, Calendar.getInstance(), clinicallyRelevantTime, type , uuid);
    }

    /**
     * Instantiates a new abstract clinical entity.
     *
     * @param name the name
     * @param instantiationTime the instantiation time
     * @param clinicallyRelevantTime the clinically relevant time
     * @param type the type
     */
    public AbstractClinicalEntity(String name, Calendar instantiationTime,
                                  Calendar clinicallyRelevantTime, Type type) {
        this(name, instantiationTime, clinicallyRelevantTime, type, UUID.randomUUID());
    }

    /**
     * Instantiates a new abstract clinical entity.
     *
     * @param name the name
     * @param instantiationTime the instantiation time
     * @param clinicallyRelevantTime the clinically relevant time
     * @param type the type
     * @param uuid the uuid
     */
    public AbstractClinicalEntity(String name, Calendar instantiationTime,
                                  Calendar clinicallyRelevantTime,
                                  Type type, UUID uuid) {
        this.name = name;
        this.instantiationTime = instantiationTime;
        this.clinicallyRelevantTime = clinicallyRelevantTime;
        this.type = type;
        this.uuid = uuid;
    }

    /**
     * Empty constructor for persistence.
     */
    public AbstractClinicalEntity() {
        this(null, Calendar.getInstance(), Calendar.getInstance(), Type.UNKNOWN, UUID.randomUUID());
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Column(name="name")
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the instantiation time.
     *
     * @return the instantiation time
     */
    @Column(name = "instantiation_time", nullable = false, columnDefinition = "date")
    @Index(name = "IDX_ET_INS_TIME")
    public Calendar getInstantiationTime() {
        return instantiationTime;
    }

    /**
     * Sets the instantiation time.
     *
     * @param instantiationTime the new instantiation time
     */
    public void setInstantiationTime(Calendar instantiationTime) {
        this.instantiationTime = instantiationTime;
    }

    /**
     * Gets the coreferences.
     *
     * @return the coreferences
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractClinicalEntity.class)
    @JoinTable(name="ENTITY_COREFERENCES",
            joinColumns = @JoinColumn(name = "entity_id"),
            inverseJoinColumns = @JoinColumn( name="coreference_entity_id"))
    @ForeignKey(name = "FK_ENT_COREF", inverseName = "FK_COREF_ENT")
    public Set<ClinicalEntity> getCoreferences() {
        return coreferences;
    }

    /**
     * Sets the coreferences.
     *
     * @param coreferences the new coreferences
     */
    public void setCoreferences(Set<ClinicalEntity> coreferences) {
        this.coreferences = coreferences;
    }

    /**
     * Gets the clinically relevant time.
     *
     * @return the clinically relevant time
     */
    @Column(name = "clinically_relevant_time" , nullable = true, columnDefinition = "date")
    @Index(name = "IDX_ET_REL_TIME")
    public Calendar getClinicallyRelevantTime() {
        return clinicallyRelevantTime;
    }

    /**
     * Sets the clinically relevant time.
     *
     * @param clinicallyRelevantTime the new clinically relevant time
     */
    public void setClinicallyRelevantTime(Calendar clinicallyRelevantTime) {
        this.clinicallyRelevantTime = clinicallyRelevantTime;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Enumerated(value= EnumType.STRING)
    public ClinicalEntity.Type getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(ClinicalEntity.Type type) {
        this.type = type;
    }

    /**
     * Gets the UUID.
     *
     * @return the UUID
     */
    @Id
    @Column(name = "uuid", nullable = false, columnDefinition = "VARBINARY(18)")
    @Index(name = "IDX_ET_UUID")
    @org.hibernate.annotations.Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Sets the UUID.
     *
     * @param uuid the new UUID
     */
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Adds the coreference.
     *
     * @param entity the entity
     */
    @Transient
    public void addCoreference(ClinicalEntity entity) {
        if(entity == null)
        {
            throw new IllegalArgumentException("Entity passed cant be null");
        }
        else
        {
            getCoreferences().add(entity);
        }
    }

    /**
     * Removes the coreference.
     *
     * @param entity the entity
     */
    @Transient
    public void removeCoreference(ClinicalEntity entity) {
        if(entity == null)
        {
            throw new IllegalArgumentException("Entity passed cant be null");
        }
        else
        {
            getCoreferences().remove(entity);
        }
    }
}
