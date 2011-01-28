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
package uk.nhs.cfh.dsp.snomed.objectmodel.impl;

import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 11:29:45 AM
 */

@Entity(name = "RoleGroup")
@Table(name = "ROLE_GROUPS_TABLE")
@Embeddable
public class SnomedRoleGroupImpl implements SnomedRoleGroup{

    /** The relationships. */
    private Collection<SnomedRelationship> relationships = new ArrayList<SnomedRelationship>();

    /** The relationship group id. */
    private String relationshipGroupId = "1";

    /** The id. */
    private long id;

    /**
     * Instantiates a new snomed role group impl.
     */
    public SnomedRoleGroupImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup#getRelationships()
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SnomedRelationshipImpl.class)
    @JoinTable(
            name="ROLE_GROUP_RELATIONSHIPS",
            joinColumns = @JoinColumn( name="role_id"),
            inverseJoinColumns = @JoinColumn( name="relationships_id"))
    public Collection<SnomedRelationship> getRelationships() {
        return relationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup#setRelationships(java.util.Collection)
     */
    public void setRelationships(Collection<SnomedRelationship> relationships) {
        this.relationships = relationships;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup#addRelationship(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship)
     */
    @Transient
    public void addRelationship(SnomedRelationship relationship){
        if(relationship != null)
        {
            relationships.add(relationship);
        }
        else
        {
            throw new IllegalArgumentException("Relationship passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup#removeRelationship(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship)
     */
    @Transient
    public void removeRelationship(SnomedRelationship relationship){
        if(relationship != null)
        {
            relationships.add(relationship);
        }
        else
        {
            throw new IllegalArgumentException("Relationship passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup#getRelationshipGroupId()
     */
    @Column(name = "relationship_id", columnDefinition = "VARCHAR(2)")
    public String getRelationshipGroupId() {
        return relationshipGroupId;
    }


    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup#setRelationshipGroupId(java.lang.String)
     */
    public void setRelationshipGroupId(String relationshipGroupId) {
        this.relationshipGroupId = relationshipGroupId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.CanonicalRepresentableElement#getCanonicalStringForm()
     */
    @Transient
    public String getCanonicalStringForm() {

        // loop through contained relationships and generate form
        String cananicalForm ="";
        int i =0;
        for(SnomedRelationship relationship : getRelationships())
        {
            if(i== 0)
            {
                cananicalForm = relationship.getCanonicalStringForm();
            }
            else
            {
                cananicalForm = cananicalForm+","+relationship.getCanonicalStringForm();
            }

            // increment counter
            i++;
        }

        // add surrounding braces {}
        cananicalForm = "{"+cananicalForm+"}";

        return cananicalForm;
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
        else if(obj instanceof SnomedRoleGroup)
        {
            SnomedRoleGroup rg = (SnomedRoleGroup) obj;
            // compare canonical forms
            return (rg.getCanonicalStringForm().equalsIgnoreCase(getCanonicalStringForm()));
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
        return getCanonicalStringForm().hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(T)
     */
    @Transient
    public int compareTo(SnomedRoleGroup o) {
        return (this.getCanonicalStringForm().compareTo(o.getCanonicalStringForm()));
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


