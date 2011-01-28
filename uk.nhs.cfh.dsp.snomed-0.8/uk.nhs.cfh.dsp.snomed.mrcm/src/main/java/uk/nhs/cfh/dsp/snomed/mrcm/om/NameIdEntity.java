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
package uk.nhs.cfh.dsp.snomed.mrcm.om;

import org.hibernate.annotations.Index;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * A simple class that has a name value and Id that is used to represent the components of a.
 *
 * {@link uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 3:54:13 PM
 */
@Entity(name = "NamedEntity")
@Embeddable
public  class NameIdEntity {

    /** The name. */
    private String name;
    
    /** The entity id. */
    private String entityId;
    
    /** The id. */
    private long id;

    /**
     * Instantiates a new name id entity.
     *
     * @param name the name
     * @param entityId the entity id
     */
    NameIdEntity(String name, String entityId) {
        this.name = name;
        this.entityId = entityId;
    }

    /**
     * Instantiates a new name id entity.
     */
    public NameIdEntity() {
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Column(name = "ENTITY_NAME", insertable = false, updatable = false)
    @Index(name = "IDX_ENTITY_NAME")
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
     * Gets the entity id.
     *
     * @return the entity id
     */
    @Column(name = "ENTITY_ID", insertable = false, updatable = false)
    @Index(name = "IDX_ENTITY_ID")
    public String getEntityId() {
        return entityId;
    }

    /**
     * Sets the entity id.
     *
     * @param entityId the new entity id
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }
}