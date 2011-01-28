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

import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept}
 *
 * <br>Version : @#VersionNumber#@
 * <br> Written by @author Jay Kola
 * <br> Created on Dec 15, 2008 at 8:30:36 PM
 */

@MappedSuperclass
@Entity(name = "AbstractConcept")
public abstract class AbstractTerminologyConcept extends AbstractSnomedComponent implements TerminologyConcept {

    /** The concept id. */
    private String conceptID = "";
    
    /** The synonyms. */
    private Collection<String> synonyms = new ArrayList<String>();
    
    /** The source. */
    private String source = "";
    
    /** The preferred label. */
    private String preferredLabel = "";

    /**
     * no args constructor for persistence.
     */
    public AbstractTerminologyConcept(){

    }

    /**
     * Instantiates a new abstract terminology concept.
     *
     * @param conceptID the concept id
     * @param preferredLabel the preferred label
     */
    public AbstractTerminologyConcept(String conceptID, String preferredLabel) {
        this.conceptID = conceptID;
        this.preferredLabel = preferredLabel;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#getConceptID()
     */
    @Id
    @Column(name="concept_id", nullable=false, columnDefinition = "VARCHAR(18)")
    @org.hibernate.annotations.Index(name= "IDX_CONCEPT_ID")
    public String getConceptID() {
        return conceptID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#setConceptID(java.lang.String)
     */
    public void setConceptID(String conceptID) {
        this.conceptID = conceptID;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#getSynonyms()
     */
    @org.hibernate.annotations.CollectionOfElements(fetch=FetchType.LAZY)
    public Collection<String> getSynonyms() {
        return synonyms;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#setSynonyms(java.util.Collection)
     */
    public void setSynonyms(Collection<String> synonyms) {
        this.synonyms = synonyms;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#getSource()
     */
    @org.hibernate.annotations.Index(name="IDX_SOURCE")
    @Column(name = "source", columnDefinition = "VARCHAR(6)")
    public String getSource() {
        return source;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#setSource(java.lang.String)
     */
    public void setSource(String source) {
        this.source = source;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#getPreferredLabel()
     */
    @Column(name="preferred_term")
    public String getPreferredLabel() {
        return preferredLabel;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#setPreferredLabel(java.lang.String)
     */
    public void setPreferredLabel(String preferredLabel) {
        this.preferredLabel = preferredLabel;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#addSynonym(java.lang.String)
     */
    @Transient
    public void addSynonym(String synonym){
        getSynonyms().add(synonym);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#removeSynonym(java.lang.String)
     */
    @Transient
    public void removeSynonym(String synonym){
        if(getSynonyms().contains(synonym))
        {
            getSynonyms().remove(synonym);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept#isActiveConcept()
     */
    @Transient
    public boolean isActiveConcept() {
        return ComponentStatus.CURRENT == getStatus();
    }
}
