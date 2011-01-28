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
import uk.nhs.cfh.dsp.snomed.objectmodel.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 15, 2008 at 8:48:03 PM
 */

@Entity(name="Concept")
@Table(name="CONCEPTS_TABLE")
@Embeddable
public class SnomedConceptImpl extends AbstractTerminologyConcept implements SnomedConcept{

    /** The Constant initialSize. */
    private static final int initialSize = 5;
    
    /** The type. */
    private ConceptType type = ConceptType.UNKNOWN;
    
    /** The fully specified name. */
    private String fullySpecifiedName = "";
    
    /** The is primitive. */
    private boolean isPrimitive;
    
    /** The ctv3 id. */
    private String ctv3ID = "";
    
    /** The snomed id. */
    private String snomedID = "";
    
    /** The descriptions. */
    private Collection<SnomedDescription> descriptions = new ArrayList<SnomedDescription>(initialSize);
    
    /** The relationships. */
    private Collection<SnomedRelationship> relationships = new ArrayList<SnomedRelationship>(initialSize);
    
    /** The source relationships. */
    private Collection<SnomedRelationship> sourceRelationships = new ArrayList<SnomedRelationship>(initialSize);
    
    /** The target relationships. */
    private Collection<SnomedRelationship> targetRelationships  = new ArrayList<SnomedRelationship>(initialSize);
    
    /** The child id set. */
    private Collection<String> childIDSet = new HashSet<String>(initialSize);
    
    /** The parent id set. */
    private Collection<String> parentIDSet = new HashSet<String>(initialSize);
    
    /** The refining relationships. */
    private Collection<SnomedRelationship> refiningRelationships = new ArrayList<SnomedRelationship>(initialSize);
    
    /** The defining relationships. */
    private Collection<SnomedRelationship> definingRelationships = new ArrayList<SnomedRelationship>(initialSize);
    
    /** The optional relationships. */
    private Collection<SnomedRelationship> optionalRelationships = new ArrayList<SnomedRelationship>(initialSize);
    
    /** The mandatory relationships. */
    private Collection<SnomedRelationship> mandatoryRelationships = new ArrayList<SnomedRelationship>(initialSize);
    
    /** The descendant ids. */
    private Collection<String> descendantIds = new HashSet<String>(initialSize);
    
    /** The ancestor ids. */
    private Collection<String> ancestorIds = new HashSet<String>(initialSize);
    
    /** The children. */
    private Collection<SnomedConcept> children = new ArrayList<SnomedConcept>(initialSize);
    
    /** The descendants. */
    private Collection<SnomedConcept> descendants = new ArrayList<SnomedConcept>(initialSize);
    
    /** The ancestors. */
    private Collection<SnomedConcept> ancestors = new ArrayList<SnomedConcept>(initialSize);
    
    /** The parents. */
    private Collection<SnomedConcept> parents = new ArrayList<SnomedConcept>(initialSize);
    
    /** The proximal primtive ids. */
    private Collection<String> proximalPrimtiveIds = new HashSet<String>(initialSize);
    
    /** The role groups. */
    private Collection<SnomedRoleGroup> roleGroups = new ArrayList<SnomedRoleGroup>(initialSize);

    /**
     * no args constructor for persistence.
     */
    public SnomedConceptImpl() {
        // empty constructor for persistence
    }

    /**
     * Instantiates a new snomed concept impl.
     *
     * @param conceptID the concept id
     * @param preferredLabel the preferred label
     */
    public SnomedConceptImpl(String conceptID, String preferredLabel) {
        super(conceptID, preferredLabel);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getType()
     */
    @Enumerated(value = EnumType.STRING)
    @org.hibernate.annotations.Index(name="IDX_CONCEPT_TYPE")
    @Column(name = "concept_type")
    public ConceptType getType() {
        return type;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setType(ConceptType)
     */
    public void setType(ConceptType conceptType) {
        this.type = conceptType;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getFullySpecifiedName()
     */
    @Column(name="fully_specified_name", nullable=false)
    @org.hibernate.annotations.Index(name="IDX_FSN")
    public String getFullySpecifiedName() {
        return fullySpecifiedName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setFullySpecifiedName(java.lang.String)
     */
    public void setFullySpecifiedName(String fullySpecifiedName) {
        this.fullySpecifiedName = fullySpecifiedName;

    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getDescriptions()
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedDescriptionImpl.class)
    @JoinTable(name="CONCEPT_DESCRIPTIONS",
            joinColumns = @JoinColumn(name = "concept_id"),
            inverseJoinColumns = @JoinColumn( name="description_id"))
    public Collection<SnomedDescription> getDescriptions() {
        return descriptions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setDescriptions(java.util.Collection)
     */
    public void setDescriptions(Collection<SnomedDescription> descriptions) {
        this.descriptions = descriptions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getRelationships()
     */
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity=SnomedRelationshipImpl.class)
    @JoinTable(
            name="CONCEPT_RELATIONSHIPS",
            joinColumns = @JoinColumn( name="concept_id"),
            inverseJoinColumns = @JoinColumn( name="relationships_id"))
    public Collection<SnomedRelationship> getRelationships() {
        return relationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setRelationships(java.util.Collection)
     */
    public void setRelationships(Collection<SnomedRelationship> relationships) {
        this.relationships = relationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getSourceRelationships()
     */
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity=SnomedRelationshipImpl.class)
    @Column(nullable=true)
    @JoinTable(
            name="CONCEPT_SOURCE_RELATIONSHIPS",
            joinColumns = @JoinColumn( name="concept_id"),
            inverseJoinColumns = @JoinColumn( name="source_relationships_id"))
    public Collection<SnomedRelationship> getSourceRelationships() {
        return sourceRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setSourceRelationships(java.util.Collection)
     */
    public void setSourceRelationships(Collection<SnomedRelationship> sourceRelationships) {
        this.sourceRelationships = sourceRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#isPrimitive()
     */
    @Column(name="is_primitive", columnDefinition="tinyint")
    @org.hibernate.annotations.Index(name="IDX_ISPRIM")
    public boolean isPrimitive() {
        return isPrimitive;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setPrimitive(boolean)
     */
    public void setPrimitive(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getTargetRelationships()
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity=SnomedRelationshipImpl.class)
    @Column(nullable=true)
    @JoinTable(
            name="CONCEPT_TARGET_RELATIONSHIPS",
            joinColumns = @JoinColumn( name="concept_id"),
            inverseJoinColumns = @JoinColumn( name="target_relationships_id"))
    public Collection<SnomedRelationship> getTargetRelationships() {
        return targetRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setTargetRelationships(java.util.Collection)
     */
    public void setTargetRelationships(Collection<SnomedRelationship> targetRelationships) {
        this.targetRelationships = targetRelationships;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Transient
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getChildIDSet()
     */
    @org.hibernate.annotations.CollectionOfElements(fetch=FetchType.LAZY, targetElement=String.class)
    @JoinTable(name="CHILD_IDSET",
            joinColumns = @JoinColumn(name = "concept_id"))
    public Collection<String> getChildIDSet() {
        return childIDSet;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setChildIDSet(java.util.Collection)
     */
    public void setChildIDSet(Collection<String> childIDSet) {
        this.childIDSet = childIDSet;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getParentIDSet()
     */
    @org.hibernate.annotations.CollectionOfElements(fetch=FetchType.LAZY, targetElement=String.class)
    @JoinTable(name="PARENT_IDSET",
            joinColumns = @JoinColumn(name = "concept_id"))
    public Collection<String> getParentIDSet() {
        return parentIDSet;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setParentIDSet(java.util.Collection)
     */
    public void setParentIDSet(Collection<String> parentIDSet) {
        this.parentIDSet = parentIDSet;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getCtv3ID()
     */
    @Column(name="ctv3_id", columnDefinition = "VARCHAR(18)")
    @org.hibernate.annotations.Index(name="IDX_CTV3ID")
    public String getCtv3ID() {
        return ctv3ID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setCtv3ID(java.lang.String)
     */
    public void setCtv3ID(String ctv3ID) {
        this.ctv3ID = ctv3ID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getSnomedID()
     */
    @Column(name="snomed_id", columnDefinition = "VARCHAR(18)")
    @org.hibernate.annotations.Index(name="IDX_SCTID")
    public String getSnomedID() {
        return snomedID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setSnomedID(java.lang.String)
     */
    public void setSnomedID(String snomedID) {
        this.snomedID = snomedID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getRefiningRelationships()
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity=SnomedRelationshipImpl.class)
    @JoinTable(
            name="REFINING_RELATIONSHIPS",
            joinColumns = @JoinColumn( name="concept_id"),
            inverseJoinColumns = @JoinColumn( name="refining_relationships_id"))
    public Collection<SnomedRelationship> getRefiningRelationships() {
        return refiningRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setRefiningRelationships(java.util.Collection)
     */
    public void setRefiningRelationships(Collection<SnomedRelationship> refiningRelationships) {
        this.refiningRelationships = refiningRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getDefiningRelationships()
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity=SnomedRelationshipImpl.class)
    @JoinTable(
            name="DEFINING_RELATIONSHIPS",
            joinColumns = @JoinColumn( name="concept_id"),
            inverseJoinColumns = @JoinColumn( name="defining_relationships_id"))
    public Collection<SnomedRelationship> getDefiningRelationships() {
        return definingRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setDefiningRelationships(java.util.Collection)
     */
    public void setDefiningRelationships(Collection<SnomedRelationship> definingRelationships) {
        this.definingRelationships = definingRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getOptionalRelationships()
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity=SnomedRelationshipImpl.class)
    @JoinTable(
            name="OPTIONAL_RELATIONSHIPS",
            joinColumns = @JoinColumn( name="concept_id"),
            inverseJoinColumns = @JoinColumn( name="optional_relationships_id"))
    public Collection<SnomedRelationship> getOptionalRelationships() {
        return optionalRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setOptionalRelationships(java.util.Collection)
     */
    public void setOptionalRelationships(Collection<SnomedRelationship> optionalRelationships) {
        this.optionalRelationships = optionalRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getMandatoryRelationships()
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity=SnomedRelationshipImpl.class)
    @JoinTable(
            name="MANDATORY_RELATIONSHIPS",
            joinColumns = @JoinColumn( name="concept_id"),
            inverseJoinColumns = @JoinColumn( name="mandatory_relationships_id"))
    public Collection<SnomedRelationship> getMandatoryRelationships() {
        return mandatoryRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setMandatoryRelationships(java.util.Collection)
     */
    public void setMandatoryRelationships(Collection<SnomedRelationship> mandatoryRelationships) {
        this.mandatoryRelationships = mandatoryRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getDescendantIds()
     */
    @org.hibernate.annotations.CollectionOfElements(fetch=FetchType.LAZY, targetElement=String.class)
    @JoinTable(name="DESCENDANT_IDSET",
            joinColumns = @JoinColumn(name = "concept_id"))
    public Collection<String> getDescendantIds() {
        return descendantIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setDescendantIds(java.util.Collection)
     */
    public void setDescendantIds(Collection<String> descendantIds) {
        this.descendantIds = descendantIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getAncestorIds()
     */
    @org.hibernate.annotations.CollectionOfElements(fetch=FetchType.LAZY, targetElement=String.class)
    @JoinTable(name="ANCESTOR_IDSET",
            joinColumns = @JoinColumn(name = "concept_id"))
    public Collection<String> getAncestorIds() {
        return ancestorIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setAncestorIds(java.util.Collection)
     */
    public void setAncestorIds(Collection<String> ancestorIds) {
        this.ancestorIds = ancestorIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getProximalPrimtiveIds()
     */
    @org.hibernate.annotations.CollectionOfElements(fetch=FetchType.LAZY, targetElement=String.class)
    @JoinTable(name="PROXIMAL_PRIMITIVES_IDSET",
            joinColumns = @JoinColumn(name = "concept_id"))
    public Collection<String> getProximalPrimtiveIds() {
        return proximalPrimtiveIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setProximalPrimtiveIds(java.util.Collection)
     */
    public void setProximalPrimtiveIds(Collection<String> proximalPrimtiveIds) {
        this.proximalPrimtiveIds = proximalPrimtiveIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getChildren()
     */
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedConceptImpl.class)
    @JoinTable(name="CHILDREN",
            joinColumns = @JoinColumn(name = "concept_id"),
            inverseJoinColumns = @JoinColumn( name="child_id"))
    public Collection<SnomedConcept> getChildren() {
        return children;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setChildren(java.util.Collection)
     */
    public void setChildren(Collection<SnomedConcept> children) {
        this.children = children;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getDescendants()
     */
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedConceptImpl.class)
    @JoinTable(name="DESCENDANTS",
            joinColumns = @JoinColumn(name = "concept_id"),
            inverseJoinColumns = @JoinColumn( name="descendant_id"))
    public Collection<SnomedConcept> getDescendants() {
        return descendants;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setDescendants(java.util.Collection)
     */
    public void setDescendants(Collection<SnomedConcept> descendants) {
        this.descendants = descendants;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getAncestors()
     */
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedConceptImpl.class)
    @JoinTable(name="ANCESTORS",
            joinColumns = @JoinColumn(name = "concept_id"),
            inverseJoinColumns = @JoinColumn( name="ancestor_id"))
    public Collection<SnomedConcept> getAncestors() {
        return ancestors;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setAncestors(java.util.Collection)
     */
    public void setAncestors(Collection<SnomedConcept> ancestors) {
        this.ancestors = ancestors;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getParents()
     */
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedConceptImpl.class)
    @JoinTable(name="PARENTS",
            joinColumns = @JoinColumn(name = "concept_id"),
            inverseJoinColumns = @JoinColumn( name="parent_id"))
    public Collection<SnomedConcept> getParents() {
        return parents;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setParents(java.util.Collection)
     */
    public void setParents(Collection<SnomedConcept> parents) {
        this.parents = parents;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getRoleGroups()
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SnomedRoleGroupImpl.class)
    @JoinTable(name="CONCEPT_ROLE_GROUPS",
            joinColumns = @JoinColumn(name = "concept_id"),
            inverseJoinColumns = @JoinColumn( name="role_group_id"))
    public Collection<SnomedRoleGroup> getRoleGroups() {
        return roleGroups;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#setRoleGroups(java.util.Collection)
     */
    public void setRoleGroups(Collection<SnomedRoleGroup> roleGroups) {
        this.roleGroups = roleGroups;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.CanonicalRepresentableElement#getCanonicalStringForm()
     */
    @Transient
    public String getCanonicalStringForm() {
        return getConceptID().trim();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.impl.AbstractTerminologyConcept#isActiveConcept()
     */
    @Transient
    public boolean isActiveConcept() {
        return (ComponentStatus.CURRENT == getStatus() || ComponentStatus.LIMITED == getStatus() ||
                ComponentStatus.PENDING_MOVE == getStatus());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @Transient
    public boolean equals(Object obj) {

        // check referential identity
        if(this == obj)
        {
            return true;
        }
        else if(obj instanceof SnomedConcept)
        {
            // compare concept ids
            SnomedConcept concept = (SnomedConcept) obj;
            return concept.getConceptID().equals(getConceptID());
        }
        else
        {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    @Transient
    public int hashCode() {
        return getConceptID().hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(T)
     */
    @Transient
    public int compareTo(SnomedConcept o) {
        return (this.getConceptID()).compareTo(o.getConceptID());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getNonSubSumptionDefiningRelationships()
     */
    @Transient
    public Collection<SnomedRelationship> getNonSubSumptionDefiningRelationships(){

        // get all defining relationships and remove the ones that have a relationship type
        Set<SnomedRelationship> nonSubsumptionDefiningRelationships = new HashSet<SnomedRelationship>(initialSize);
        for(SnomedRelationship relationship : getDefiningRelationships())
        {
            if(! ConceptType.ATTRIBUTE_IS_A.getID().equals(relationship.getRelationshipType()))
            {
                nonSubsumptionDefiningRelationships.add(relationship);
            }
        }

        return nonSubsumptionDefiningRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getAllRefinableRelationships()
     */
    @Transient
    public Collection<SnomedRelationship> getAllRefinableRelationships(){
        Collection<SnomedRelationship> allRefinableRelationships = new HashSet<SnomedRelationship>(initialSize);
        allRefinableRelationships.addAll(getOptionalRelationships());
        allRefinableRelationships.addAll(getMandatoryRelationships());

        return allRefinableRelationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept#getAllRefinableRoleGroups()
     */
    @Transient
    public Collection<SnomedRoleGroup> getAllRefinableRoleGroups(){
        Collection<SnomedRoleGroup> refinableRoleGroups = new ArrayList<SnomedRoleGroup>(initialSize);
        for(SnomedRoleGroup rg : getRoleGroups())
        {
            for(SnomedRelationship r : rg.getRelationships())
            {
                if(SnomedRelationship.Refinability.NOT_REFINABLE != r.getRefinability())
                {
                    // add to refinableRoleGroups
                    refinableRoleGroups.add(rg);
                    break;
                }
            }
        }

        return refinableRoleGroups;
    }
}
