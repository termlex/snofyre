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
package uk.nhs.cfh.dsp.snomed.objectmodel.impl;

import org.hibernate.annotations.Index;
import uk.nhs.cfh.dsp.snomed.objectmodel.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedAttribute}.
 *
 * <br>Version : @#VersionNumber#@
 * <br> Written by @author jay
 * <br> Created on Dec 30, 2008 at 9:20:09 AM
 */

@Entity(name = "Attribute")
@Table(name = "ATTRIBUTES_TABLE")
@Embeddable
public class SnomedAttributeImpl implements SnomedAttribute {

    /** The attribute id. */
    private String ID;

    /** The attribute name. */
    private String name;

    /** The allowed attribute value ids. */
    private Set<String> allowedAttributeValueIDs = new HashSet<String>();

    /** The allowed attribute values. */
    private Set<String> allowedAttributeValues = new HashSet<String>();

    /** The concept type. */
    private String conceptType ;
    
    /** The id. */
    private long id;

    /**
     * Instantiates a new snomed concept attribute using the attribute id passed.
     *
     * @param ID the attribute id passed
     */
    public SnomedAttributeImpl(String ID) {
        this.ID = ID;
    }

    /**
     * Instantiates a new snomed attribute impl.
     */
    public SnomedAttributeImpl() {
    }

    /**
     * Instantiates a new snomed attribute impl.
     *
     * @param snomedRelationship the snomed relationship
     */
    public SnomedAttributeImpl(SnomedRelationship snomedRelationship) {
        if(snomedRelationship != null)
        {
            // populate values from relationship
            ID = snomedRelationship.getRelationshipType();
            name = snomedRelationship.getName();
        }
        else
        {
            throw new IllegalArgumentException("Relationship passed can not be null.");
        }
    }

    /**
     * Gets the attribute id.
     *
     * @return the attribute id
     */
    @Column(name = "attribute_id",columnDefinition = "VARCHAR(18)")
    @Index(name = "IDX_ATTR_ID")
    public String getID() {
        return ID;
    }

    /**
     * Sets the attribute id.
     *
     * @param attributeID the new attribute id
     */
    public void setID(String attributeID) {
        this.ID = attributeID;
    }

    /**
     * Gets the attribute name.
     *
     * @return the attribute name
     */
    @Column(name = "name")
    @Index(name = "IDX_ATTR_NAME")
    public String getName() {
        return name;
    }

    /**
     * Sets the attribute name.
     *
     * @param attributeName the new attribute name
     */
    public void setName(String attributeName) {
        this.name = attributeName;
    }

    /**
     * Gets the allowed attribute value i ds.
     *
     * @return the allowed attribute value i ds
     */
    @org.hibernate.annotations.CollectionOfElements(fetch=FetchType.LAZY, targetElement=String.class)
    @JoinTable(name="ALLOWED_VALUE_IDS",
            joinColumns = @JoinColumn(name = "attribute_id"))
    public Set<String> getAllowedAttributeValueIDs() {
        return allowedAttributeValueIDs;
    }

    /**
     * Sets the allowed attribute value i ds.
     *
     * @param allowedAttributeValueIDs the new allowed attribute value i ds
     */
    public void setAllowedAttributeValueIDs(Set<String> allowedAttributeValueIDs) {
        this.allowedAttributeValueIDs = allowedAttributeValueIDs;
    }

    /**
     * Gets the allowed attribute values.
     *
     * @return the allowed attribute values
     */
    @org.hibernate.annotations.CollectionOfElements(fetch=FetchType.LAZY, targetElement=String.class)
    @JoinTable(name="ALLOWED_VALUES",
            joinColumns = @JoinColumn(name = "attribute_id"))
    public Set<String> getAllowedAttributeValues() {
        return allowedAttributeValues;
    }

    /**
     * Sets the allowed attribute values.
     *
     * @param allowedAttributeValues the new allowed attribute values
     */
    public void setAllowedAttributeValues(Set<String> allowedAttributeValues) {
        this.allowedAttributeValues = allowedAttributeValues;
    }

    /**
     * Gets the concept type.
     *
     * @return the concept type
     */
    @Column(name = "concept_type", columnDefinition = "VARCHAR(32)")
    @Index(name = "IDX_CON_TYPE")
    public String getConceptType() {
        return conceptType;
    }

    /**
     * Sets the concept type.
     *
     * @param conceptType the new concept type
     */
    public void setConceptType(String conceptType) {
        this.conceptType = conceptType;
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
