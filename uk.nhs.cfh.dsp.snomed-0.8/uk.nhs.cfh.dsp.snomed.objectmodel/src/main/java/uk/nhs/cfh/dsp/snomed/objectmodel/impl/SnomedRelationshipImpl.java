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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship}
 *
 * <br>Version : @#VersionNumber#@
 * <br> Written by @author Jay Kola
 * <br> Created on Dec 16, 2008 at 1:53:18 PM
 */

@Embeddable
@Entity(name="Relationship")
@Table(name="RELATIONSHIPS_TABLE")
public class SnomedRelationshipImpl extends AbstractTerminologyRelationship implements SnomedRelationship {

    /** The relationship type. */
    private String relationshipType;

    /** The relationship name. */
    private String relationshipName;

    /** The relationship group. */
    private String relationshipGroup;

    /** The source. */
    private String source;

    /** The source concept. */
    private SnomedConcept sourceConcept;

    /** The target concept. */
    private SnomedConcept targetConcept;

    /** The refinability. */
    private Refinability refinability;

    /** The type. */
    private RelationshipType type;

    /**
     * no argument constructor for persistence.
     */
    public SnomedRelationshipImpl(){

    }

    /**
     * Instantiates a new snomed relationship impl.
     *
     * @param relationshipID the relationship id
     * @param sourceConceptID the source concept id
     * @param relationshipType the relationship type
     * @param targetConceptID the target concept id
     * @param characteristicType the characteristic type
     * @param refinability the refinability
     * @param relationshipGroup the relationship group
     * @param source the source
     */
    public SnomedRelationshipImpl(String relationshipID, String sourceConceptID,
                                  String relationshipType, String targetConceptID,
                                  RelationshipType characteristicType, Refinability refinability,
                                  String relationshipGroup, String source) {

        super(relationshipID, sourceConceptID, targetConceptID);
        this.relationshipType = relationshipType;
        this.relationshipGroup = relationshipGroup;
        this.source = source;
        this.type = characteristicType;
        this.refinability = refinability;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#getSourceConcept()
     */
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedConceptImpl.class)
    @JoinTable(
            name="Sources",
            joinColumns = @JoinColumn( name="relationship_id"),
            inverseJoinColumns = @JoinColumn( name="source_id"))
    public SnomedConcept getSourceConcept() {
        return sourceConcept;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#setSourceConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
    */
    public void setSourceConcept(SnomedConcept sourceConcept) {
        this.sourceConcept = sourceConcept;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#getTargetConcept()
    */
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedConceptImpl.class)
    @JoinTable(
            name="Targets",
            joinColumns = @JoinColumn( name="relationship_id"),
            inverseJoinColumns = @JoinColumn( name="target_id"))
    public SnomedConcept getTargetConcept() {
        return targetConcept;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#setTargetConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
    */
    public void setTargetConcept(SnomedConcept targetConcept) {
        this.targetConcept = targetConcept;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#getRelationshipType()
    */
    @Column(name="relationship_type", nullable=false, columnDefinition = "VARCHAR(18)")
    @org.hibernate.annotations.Index(name="IDX_REL_TYPE")
    public String getRelationshipType() {
        return relationshipType;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#setRelationshipType(java.lang.String)
    */
    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#getRefinability()
    */
    @Column(name="refinability", nullable=false, columnDefinition = "VARCHAR(18)")
    @org.hibernate.annotations.Index(name="IDX_REFINABILITY")
    @Enumerated(EnumType.STRING)
    public Refinability getRefinability() {
        return refinability;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#setRefinability(Refinability)
    */
    public void setRefinability(Refinability refinability) {
        this.refinability = refinability;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#getType()
    */
    @Column(name="type", nullable=false, columnDefinition = "VARCHAR(18)")
    @org.hibernate.annotations.Index(name="IDX_TYPE")
    @Enumerated(EnumType.STRING)
    public RelationshipType getType() {
        return type;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#setType(RelationshipType)
    */
    public void setType(RelationshipType type) {
        this.type = type;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#isDefiningRelation()
    */
    @Transient
    public boolean isDefiningRelation() {
        return RelationshipType.DEFINING == getType();
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#isQualifyingRelation()
    */
    @Transient
    public boolean isQualifyingRelation() {
        return RelationshipType.QUALIFIER == getType();
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#isMandatory()
    */
    @Transient
    public boolean isMandatory() {
        return Refinability.MANDATORY == getRefinability();
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#isOptional()
    */
    @Transient
    public boolean isOptional() {
        return Refinability.OPTIONAL == getRefinability();
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#isRefinable()
    */
    @Transient
    public boolean isRefinable() {
        return (Refinability.NOT_REFINABLE != getRefinability());
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#getRelationshipGroup()
    */
    @Column(name="relationship_group", nullable=false, columnDefinition = "VARCHAR(2)")
    @org.hibernate.annotations.Index(name="IDX_REL_GROUP")
    public String getRelationshipGroup() {
        return relationshipGroup;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#setRelationshipGroup(java.lang.String)
    */
    public void setRelationshipGroup(String relationshipGroup) {
        this.relationshipGroup = relationshipGroup;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#getSource()
    */
    @Column(name="source", nullable=false, columnDefinition = "VARCHAR(8)")
    @org.hibernate.annotations.Index(name="IDX_SOURCE")
    public String getSource() {
        return source;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#setSource(java.lang.String)
    */
    public void setSource(String source) {
        this.source = source;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#getName()
     */
    @Column(name = "name", columnDefinition = "VARCHAR(256)")
    public String getName() {
        return relationshipName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship#setName(java.lang.String)
     */
    public void setName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    @Transient
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.CanonicalRepresentableElement#getCanonicalStringForm()
     */
    @Transient
    public String getCanonicalStringForm() {
        return getRelationshipType().trim()+"="+getTargetConceptID().trim();
    }

    /* (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
    @Override
    @Transient
    public boolean equals(Object obj) {

        // check referential equality
        if(obj == this)
        {
            return true;
        }
        else if(obj instanceof SnomedRelationship)
        {
            SnomedRelationship rel = (SnomedRelationship) obj;
            // compare canonical forms
            return rel.getCanonicalStringForm().equalsIgnoreCase(getCanonicalStringForm());
        }
        else
        {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Transient
    @Override
    public int hashCode() {
        return getCanonicalStringForm().hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(T)
     */
    @Transient
    public int compareTo(SnomedRelationship o) {

        return (this.getCanonicalStringForm().compareTo(o.getCanonicalStringForm()));
    }
}
