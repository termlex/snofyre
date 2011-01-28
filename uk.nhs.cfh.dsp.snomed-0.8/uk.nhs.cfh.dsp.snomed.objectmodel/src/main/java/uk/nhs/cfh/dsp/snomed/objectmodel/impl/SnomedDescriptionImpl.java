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
import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 15, 2008 at 11:17:17 PM
 */


@Embeddable
@Entity(name="Description")
@Table(name="DESCRIPTIONS_TABLE")
public class SnomedDescriptionImpl extends AbstractSnomedComponent implements SnomedDescription {

	/** The description id. */
	private String descriptionID = "";
	
	/** The concept id. */
	private String conceptID = "";
	
	/** The term. */
	private String term = "";
	
	/** The initial cap status. */
	private boolean initialCapStatus = false;
	
	/** The description type. */
	private String descriptionType = "";
	
	/** The language. */
	private String language = "";
	
	/** The source. */
	private String source="";

	/**
 * no argument constructor for persistence.
 */
	public SnomedDescriptionImpl(){
		
	}
	
	/**
	 * Instantiates a new snomed description impl.
	 *
	 * @param descriptionID the description id
	 * @param status the status
	 * @param conceptID the concept id
	 * @param term the term
	 * @param initialCapStatus the initial cap status
	 * @param descriptionType the description type
	 * @param language the language
	 * @param source the source
	 */
	public SnomedDescriptionImpl(String descriptionID, ComponentStatus status,
			String conceptID, String term, boolean initialCapStatus,
			String descriptionType, String language, String source) {
		this.descriptionID = descriptionID;
		this.conceptID = conceptID;
		this.term = term;
		this.initialCapStatus = initialCapStatus;
		this.descriptionType = descriptionType;
		this.language = language;
		this.source = source;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#getId()
     */
    @Id
	@Column(name="description_id", nullable=false, columnDefinition = "VARCHAR(18)")
	@org.hibernate.annotations.Index(name="IDX_DESC_ID")
	public String getId() {
		return descriptionID;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#setId(java.lang.String)
     */
    public void setId(String descriptionID) {
		this.descriptionID = descriptionID;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#getConceptID()
     */
    @Column(name="concept_id", nullable=false, columnDefinition = "VARCHAR(18)")
	@org.hibernate.annotations.Index(name="IDX_CONCEPT_ID")
	public String getConceptID() {
		return conceptID;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#setConceptID(java.lang.String)
     */
    public void setConceptID(String conceptID) {
		this.conceptID = conceptID;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#getTerm()
     */
    @Column(name="term", nullable=false, columnDefinition = "VARCHAR(256)")
	@org.hibernate.annotations.Index(name="IDX_DESC_TERM")
	public String getTerm() {
		return term;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#setTerm(java.lang.String)
     */
    public void setTerm(String term) {
		this.term = term;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#isInitialCapStatus()
     */
    @Column(name="cap_status", nullable=false)
	@org.hibernate.annotations.Index(name="IDX_CAP_STATUS")
	public boolean isInitialCapStatus() {
		return initialCapStatus;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#setInitialCapStatus(boolean)
     */
    public void setInitialCapStatus(boolean initialCapStatus) {
		this.initialCapStatus = initialCapStatus;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#getType()
     */
    @Column(name="type", nullable=false, columnDefinition = "VARCHAR(18)")
	@org.hibernate.annotations.Index(name="IDX_DESC_TYPE")
	public String getType() {
		return descriptionType;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#setType(java.lang.String)
     */
    public void setType(String descriptionType) {
		this.descriptionType = descriptionType;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#getLanguage()
     */
    @Column(name="language", nullable=false, columnDefinition = "VARCHAR(8)")
	@org.hibernate.annotations.Index(name="IDX_DESC_LANG")
	public String getLanguage() {
		return language;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#setLanguage(java.lang.String)
     */
    public void setLanguage(String language) {
		this.language = language;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#getSource()
     */
    @Column(name="source", nullable=false, columnDefinition = "VARCHAR(6)")
	@org.hibernate.annotations.Index(name="IDX_DESC_SOURCE")
	public String getSource() {
		return source;
	}

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription#setSource(java.lang.String)
     */
    public void setSource(String source) {
		this.source = source;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
