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
package uk.nhs.cfh.dsp.snomed.mrcm.om.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Index;
import uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 10:28:46 AM
 */

@Entity(name = "MRCMConstraint")
@Table(name = "MRCM_CONSTRAINTS_TABLE")
public class MRCMConstraintImpl implements MRCMConstraint {

    /** The source id. */
    private String sourceId;
    
    /** The source name. */
    private String sourceName;
    
    /** The attribute id. */
    private String attributeId;
    
    /** The attribute name. */
    private String attributeName;
    
    /** The value id. */
    private String valueId;
    
    /** The value name. */
    private String valueName;
    
    /** The source concept type. */
    private String sourceConceptType ;
    
    /** The id. */
    private long id;
    
    /** The min cardinality. */
    private int minCardinality = 0;
    
    /** The max cardinality. */
    private int maxCardinality = 1;

    /**
     * Instantiates a new mRCM constraint impl.
     */
    public MRCMConstraintImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#getSourceId()
     */
    @Column(name = "SOURCE_ID")
    @Index(name = "IDX_SOURCE_ID")
    public String getSourceId() {
        return sourceId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#setSourceId(java.lang.String)
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#getSourceName()
     */
    @Column(name = "SOURCE_NAME")
    @Index(name = "IDX_SOURCE_NAME")
    public String getSourceName() {
        return sourceName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#setSourceName(java.lang.String)
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#getAttributeId()
     */
    @Column(name = "ATTRIBUTE_ID")
    @Index(name = "IDX_ATTRIBUTE_ID")
    public String getAttributeId() {
        return attributeId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#setAttributeId(java.lang.String)
     */
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#getAttributeName()
     */
    @Column(name = "ATTRIBUTE_NAME")
    @Index(name = "IDX_ATTRIBUTE_NAME")
    public String getAttributeName() {
        return attributeName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#setAttributeName(java.lang.String)
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#getValueId()
     */
    @Column(name = "VALUE_ID")
    @Index(name = "IDX_VALUE_ID")
    public String getValueId() {
        return valueId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#setValueId(java.lang.String)
     */
    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#getValueName()
     */
    @Column(name = "VALUE_NAME")
    @Index(name = "IDX_VALUE_NAME")
    public String getValueName() {
        return valueName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#setValueName(java.lang.String)
     */
    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#getSourceConceptType()
     */
    @Column(name = "SOURCE_CONCEPT_TYPE")
    @Index(name = "IDX_SOURCE_CONCEPT_TYPE")
    public String getSourceConceptType() {
        return sourceConceptType;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#setSourceConceptType(java.lang.String)
     */
    public void setSourceConceptType(String sourceConceptType) {
        this.sourceConceptType = sourceConceptType;
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

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#getMinCardinality()
     */
    @Column(name = "MIN_CARDINALITY")
    @Index(name = "IDX_MIN_CARDINALITY")
    public int getMinCardinality() {
        return minCardinality;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#setMinCardinality(int)
     */
    public void setMinCardinality(int minCardinality) {
        this.minCardinality = minCardinality;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#getMaxCardinality()
     */
    @Column(name = "MAX_CARDINALITY")
    @Index(name = "IDX_MAX_CARDINALITY")
    public int getMaxCardinality() {
        return maxCardinality;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint#setMaxCardinality(int)
     */
    public void setMaxCardinality(int maxCardinality) {
        this.maxCardinality = maxCardinality;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
