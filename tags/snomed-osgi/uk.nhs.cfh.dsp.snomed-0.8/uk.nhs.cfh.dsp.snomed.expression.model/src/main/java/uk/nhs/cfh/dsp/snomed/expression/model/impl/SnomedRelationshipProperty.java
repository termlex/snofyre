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

import uk.nhs.cfh.dsp.snomed.expression.model.Property;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedAttribute;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedAttributeImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRelationshipImpl;

import javax.persistence.*;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.Property} that
 * wraps a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 4:37:21 PM
 */

@Embeddable
@Entity(name = "SnomedRelationshipProperty")
@Table(name = "SNOMED_RELATIONSHIP_PROPERTIES")
public class SnomedRelationshipProperty extends AbstractPropertyImpl implements Property {

    /** The relationship. */
    private SnomedRelationship relationship;
    
    /** The attribute. */
    private SnomedAttribute attribute;

    /**
     * Instantiates a new snomed relationship property.
     *
     * @param propertyName the property name
     * @param uuid the uuid
     * @param relationship the relationship
     */
    public SnomedRelationshipProperty(String propertyName, UUID uuid, SnomedRelationship relationship) {
        super(propertyName, uuid);
        if (relationship != null)
        {
            this.relationship = relationship;
            attribute = new SnomedAttributeImpl(relationship);
        }
        else {
            throw new IllegalArgumentException("Relationship passed can not be null.");
        }
    }

    /**
     * Instantiates a new snomed relationship property.
     *
     * @param relationship the relationship
     */
    public SnomedRelationshipProperty(SnomedRelationship relationship) {
        this(relationship.getName(), UUID.randomUUID(), relationship);
    }

    /**
     * Instantiates a new snomed relationship property.
     */
    public SnomedRelationshipProperty() {
        super();
    }

    /**
     * Gets the relationship.
     *
     * @return the relationship
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SnomedRelationshipImpl.class)
    public SnomedRelationship getRelationship() {
        return relationship;
    }

    /**
     * Sets the relationship.
     *
     * @param relationship the new relationship
     */
    public void setRelationship(SnomedRelationship relationship) {
        this.relationship = relationship;
    }

    /**
     * Gets the attribute.
     *
     * @return the attribute
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SnomedAttributeImpl.class)    
    public SnomedAttribute getAttribute() {
        return attribute;
    }

    /**
     * Sets the attribute.
     *
     * @param attribute the new attribute
     */
    public void setAttribute(SnomedAttribute attribute) {
        this.attribute = attribute;
    }
}