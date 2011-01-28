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
package uk.nhs.cfh.dsp.snomed.objectmodel.impl;

import org.hibernate.annotations.Index;
import uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyRelationship;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link TerminologyRelationship}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 2:35:36 PM
 * 
 */

@MappedSuperclass
@Entity(name = "AbstractRelationship")
public abstract class AbstractTerminologyRelationship implements TerminologyRelationship {

    /** The relationship id. */
    private String relationshipID = "";
    
    /** The source concept id. */
    private String sourceConceptID = "";
    
    /** The target concept id. */
    private String targetConceptID = "";

    /**
     * Instantiates a new abstract terminology relationship.
     */
    public AbstractTerminologyRelationship() {
    }

    /**
     * Instantiates a new abstract terminology relationship.
     *
     * @param relationshipId the relationship id
     * @param sourceConceptId the source concept id
     * @param targetConceptId the target concept id
     */
    public AbstractTerminologyRelationship(String relationshipId, String sourceConceptId,
            String targetConceptId) {
        this.relationshipID = relationshipId;
        this.sourceConceptID = sourceConceptId;
        this.targetConceptID = targetConceptId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyRelationship#getRelationshipID()
     */
    @Id
    @Column(name = "relationship_id", nullable = false, columnDefinition = "VARCHAR(18)")
    @Index(name = "IDX_REL_ID")
    public String getRelationshipID() {
        return relationshipID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyRelationship#getSourceConceptID()
     */
    @Column(name = "source_id", nullable = false)
    @Index(name = "IDX_SOURCE_ID")
    public String getSourceConceptID() {
        return sourceConceptID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyRelationship#getTargetConceptID()
     */
    @Column(name = "target_id", nullable = false, columnDefinition = "VARCHAR(18)")
    @Index(name = "IDX_TARGET_ID")
    public String getTargetConceptID() {
        return targetConceptID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyRelationship#setRelationshipID(java.lang.String)
     */
    public void setRelationshipID(String relationshipID) {
        this.relationshipID = relationshipID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyRelationship#setSourceConceptID(java.lang.String)
     */
    public void setSourceConceptID(String sourceConceptID) {
        this.sourceConceptID = sourceConceptID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyRelationship#setTargetConceptID(java.lang.String)
     */
    public void setTargetConceptID(String targetConceptID) {
        this.targetConceptID = targetConceptID;
    }
}
