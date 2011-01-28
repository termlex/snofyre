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
package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import org.hibernate.annotations.Type;
import uk.nhs.cfh.dsp.snomed.expression.model.Property;

import javax.persistence.*;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.Property}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 4:27:43 PM
 */

@Embeddable
@MappedSuperclass
@Entity(name = "AbstractProperty")
//@TypeDef(name = "uuid", typeClass = uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractPropertyImpl implements Property {

    /** The property name. */
    private String propertyName;
    
    /** The uuid. */
    private UUID uuid;

    /**
     * Instantiates a new abstract property impl.
     *
     * @param propertyName the property name
     * @param uuid the uuid
     */
    public AbstractPropertyImpl(String propertyName, UUID uuid) {
        this.propertyName = propertyName;
        this.uuid = uuid;
    }

    /**
     * Instantiates a new abstract property impl.
     *
     * @param propertyName the property name
     */
    public AbstractPropertyImpl(String propertyName) {
        this(propertyName, UUID.randomUUID());
    }

    /**
     * Instantiates a new abstract property impl.
     */
    public AbstractPropertyImpl() {
        this.uuid = UUID.randomUUID();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Property#getPropertyName()
     */
    @Column(name = "name")
    public String getPropertyName() {
        return propertyName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Property#setPropertyName(java.lang.String)
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Property#getUuid()
     */
    @Id
//    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    @Column(name = "uuid", nullable = false, columnDefinition = "VARBINARY(16)")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    public UUID getUuid() {
        return uuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Property#setUuid(java.lang.String)
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}