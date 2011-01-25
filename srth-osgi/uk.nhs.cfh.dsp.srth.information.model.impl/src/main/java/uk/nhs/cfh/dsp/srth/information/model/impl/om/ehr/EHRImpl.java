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
package uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.*;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEventEntry;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An implementation of an {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 9:31:10 PM
 */

@Entity(name = "EHR")
@Table(name = "EHR_TABLE")
@Embeddable
public class EHRImpl implements EHR{

    /** The patient id. */
    private long patientId;
    
    /** The initial size. */
    private static int initialSize = 20;

    private static final String NULL_ERROR_MESSAGE = "Argument passed can not be null";
    private static final String ENTRY_ERROR_MESSAGE = "Entry must be of type - ";
    private static final String PATIENT_ID = "patient_id";

    /** The entries. */
    private Set<BoundClinicalEntry> entries = new HashSet<BoundClinicalEntry>(initialSize);

    /** The findings. */
    private Set<BoundClinicalEntry> findings = new HashSet<BoundClinicalEntry>(initialSize);

    /** The features. */
    private Set<BoundClinicalEntry> features = new HashSet<BoundClinicalEntry>(initialSize);

    /** The investigations. */
    private Set<BoundClinicalEventEntry> investigations = new HashSet<BoundClinicalEventEntry>(initialSize);

    /** The interventions. */
    private Set<BoundClinicalEventEntry> interventions = new HashSet<BoundClinicalEventEntry>(initialSize);

    /** The medications. */
    private Set<BoundClinicalEventEntry> medications = new HashSet<BoundClinicalEventEntry>(initialSize);

    /**
     * Instantiates a new EHR.
     *
     * @param patientId the patient id
     */
    public EHRImpl(long patientId) {
        this.patientId = patientId;
    }

    /**
     * Instantiates a new eHR impl.
     */
    public EHRImpl() {
        // empty constructor for persistence
    }

    /**
     * Gets the patient id.
     *
     * @return the patient id
     */
    @Id
    @Column(name = "patient_id", nullable = false)
    @Index(name = "IDX_PATIENT_ID")
    public long getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient id.
     *
     * @param patientId the new patient id
     */
    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the features.
     *
     * @return the features
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ClinicalFeatureEntry.class)
    @JoinTable(name="EHR_FEATURES",
            joinColumns = @JoinColumn(name = PATIENT_ID),
            inverseJoinColumns = @JoinColumn( name="feature_id"))
    @ForeignKey(name = "FK_EHR_FEA", inverseName = "FK_FEA_EHR")
    public Set<BoundClinicalEntry> getFeatures() {
        return features;
    }

    /**
     * Sets the features.
     *
     * @param features the new features
     */
    public void setFeatures(Set<BoundClinicalEntry> features) {
        this.features = features;
    }

    /**
     * Gets the investigations.
     *
     * @return the investigations
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = InvestigationEventEntry.class)
    @JoinTable(name="EHR_INVESTIGATIONS",
            joinColumns = @JoinColumn(name = PATIENT_ID),
            inverseJoinColumns = @JoinColumn( name="investigation_id"))
    @ForeignKey(name = "FK_EHR_INVS", inverseName = "FK_INVS_EHR")
    public Set<BoundClinicalEventEntry> getInvestigations() {
        return investigations;
    }

    /**
     * Sets the investigations.
     *
     * @param investigations the new investigations
     */
    public void setInvestigations(Set<BoundClinicalEventEntry> investigations) {
        this.investigations = investigations;
    }

    /**
     * Gets the interventions.
     *
     * @return the interventions
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = InterventionEventEntry.class)
    @JoinTable(name="EHR_INTERVENTIONS",
            joinColumns = @JoinColumn(name = PATIENT_ID),
            inverseJoinColumns = @JoinColumn( name="intervention_id"))
    @ForeignKey(name = "FK_EHR_INTV", inverseName = "FK_INTV_EHR")
    public Set<BoundClinicalEventEntry> getInterventions() {
        return interventions;
    }

    /**
     * Sets the interventions.
     *
     * @param interventions the new interventions
     */
    public void setInterventions(Set<BoundClinicalEventEntry> interventions) {
        this.interventions = interventions;
    }

    /**
     * Gets the medications.
     *
     * @return the medications
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = MedicationEventEntry.class)
    @JoinTable(name="EHR_MEDICATIONS",
            joinColumns = @JoinColumn(name = PATIENT_ID),
            inverseJoinColumns = @JoinColumn( name="medication_id"))
    @ForeignKey(name = "FK_EHR_MED", inverseName = "FK_MED_EHR")
    public Set<BoundClinicalEventEntry> getMedications() {
        return medications;
    }

    /**
     * Sets the medications.
     *
     * @param medications the new medications
     */
    public void setMedications(Set<BoundClinicalEventEntry> medications) {
        this.medications = medications;
    }

    /**
     * Gets the clinical findings.
     *
     * @return the clinical findings
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ClinicalFindingEntry.class)
    @JoinTable(name="EHR_FINDINGS",
            joinColumns = @JoinColumn(name = PATIENT_ID),
            inverseJoinColumns = @JoinColumn( name="finding_id"))
    @ForeignKey(name = "FK_EHR_FIN", inverseName = "FK_FIN_EHR")
    public Set<BoundClinicalEntry> getClinicalFindings() {
        return findings;
    }

    /**
     * Sets the clinical findings.
     *
     * @param findings the new clinical findings
     */
    public void setClinicalFindings(Set<BoundClinicalEntry> findings) {
        this.findings = findings;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR#getEntries()
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractBoundClinicalEntry.class)
    @JoinTable(name="EHR_ENTRIES",
            joinColumns = @JoinColumn(name = PATIENT_ID),
            inverseJoinColumns = @JoinColumn( name="entry_id"))
    @ForeignKey(name = "FK_EHR_ENT", inverseName = "FK_ENT_EHR")    
    public Set<BoundClinicalEntry> getEntries() {
        return entries;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR#setEntries(java.util.Set)
     */
    public void setEntries(Set<BoundClinicalEntry> entries) {
        this.entries = entries;
    }

    /**
     * Adds the all findings.
     *
     * @param features the features
     */
    @Transient
    public void addAllFeatures(Set<BoundClinicalEntry> features) {
        if(findings == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            for(BoundClinicalEntry feature : features)
            {
                this.addFeature(feature);
            }
        }
    }

    /**
     * Adds the all findings.
     *
     * @param findings the findings
     */
    @Transient
    public void addAllFindings(Set<BoundClinicalEntry> findings) {
        if(findings == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            for(BoundClinicalEntry finding : findings)
            {
                this.addFinding(finding);
            }
        }
    }

    /**
     * Adds the all interventions.
     *
     * @param interventions the interventions
     */
    @Transient
    public void addAllInterventions(Set<BoundClinicalEventEntry> interventions) {
        if(interventions == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            for(BoundClinicalEventEntry intervention : interventions)
            {
                this.addIntervention(intervention);
            }
        }
    }

    /**
     * Adds the all investigations.
     *
     * @param investigations the investigations
     */
    @Transient
    public void addAllInvestigations(Set<BoundClinicalEventEntry> investigations) {
        if(investigations == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            for(BoundClinicalEventEntry investigation : investigations)
            {
                this.addInvestigation(investigation);
            }
        }
    }

    /**
     * Adds the all medications.
     *
     * @param medications the medications
     */
    @Transient
    public void addAllMedications(Set<BoundClinicalEventEntry> medications) {
        if(medications == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            for(BoundClinicalEventEntry medication : medications)
            {
                this.addMedication(medication);
            }
        }
    }

    /**
     * Adds the finding.
     *
     * @param entry the entry
     */
    @Transient
    public void addFinding(BoundClinicalEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof ClinicalFindingEntry)
            {
                getEntries().add(entry);
                getClinicalFindings().add(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"finding");
            }
        }
    }

    /**
     * Adds the finding.
     *
     * @param entry the entry
     */
    @Transient
    public void addFeature(BoundClinicalEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof ClinicalFeatureEntry)
            {
                getEntries().add(entry);
                getFeatures().add(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"feature");
            }
        }
    }

    /**
     * Adds the intervention.
     *
     * @param entry the entry
     */
    @Transient
    public void addIntervention(BoundClinicalEventEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof InterventionEventEntry)
            {
                getEntries().add(entry);
                getInterventions().add(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"intervention");
            }
        }
    }

    /**
     * Adds the investigation.
     *
     * @param entry the entry
     */
    @Transient
    public void addInvestigation(BoundClinicalEventEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof InvestigationEventEntry)
            {
                getEntries().add(entry);
                getInvestigations().add(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"investigation");
            }
        }
    }

    /**
     * Adds the medication.
     *
     * @param entry the entry
     */
    @Transient
    public void addMedication(BoundClinicalEventEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof MedicationEventEntry)
            {
                getEntries().add(entry);
                getMedications().add(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"medication");
            }
        }
    }

    /**
     * Contains finding.
     *
     * @param finding the finding
     *
     * @return true, if successful
     */
    @Transient
    public boolean containsFinding(BoundClinicalEntry finding) {
        return getClinicalFindings().contains(finding);
    }

    /**
     * Contains intervention.
     *
     * @param intervention the intervention
     *
     * @return true, if successful
     */
    @Transient
    public boolean containsIntervention(BoundClinicalEventEntry intervention) {
        return getInterventions().contains(intervention);
    }

    /**
     * Contains investigation.
     *
     * @param investigation the investigation
     *
     * @return true, if successful
     */
    @Transient
    public boolean containsInvestigation(BoundClinicalEventEntry investigation) {
        return getInvestigations().contains(investigation);
    }

    /**
     * Removes the all findings.
     *
     * @param features the features
     */
    @Transient
    public void removeAllFeatures(Set<BoundClinicalEntry> features) {
        if(features == null)
        {
            throw new IllegalArgumentException("Features passed can not be null");
        }
        else
        {
            for(BoundClinicalEntry f : features)
            {
                this.removeFeature(f);
            }
        }
    }

    /**
     * Removes the all findings.
     *
     * @param findings the findings
     */
    @Transient
    public void removeAllFindings(Set<BoundClinicalEntry> findings) {
        if(findings == null)
        {
            throw new IllegalArgumentException("Findings passed can not be null");
        }
        else
        {
            for(BoundClinicalEntry finding : findings)
            {
                this.removeFinding(finding);
            }
        }
    }

    /**
     * Removes the all interventions.
     *
     * @param interventions the interventions
     */
    @Transient
    public void removeAllInterventions(Set<BoundClinicalEventEntry> interventions) {

        if(interventions == null)
        {
            throw new IllegalArgumentException("Interventions passed can not be null");
        }
        else
        {
            for(BoundClinicalEventEntry intervention : interventions)
            {
                this.removeIntervention(intervention);
            }
        }
    }

    /**
     * Removes the all investigations.
     *
     * @param investigations the investigations
     */
    @Transient
    public void removeAllInvestigations(Set<BoundClinicalEventEntry> investigations) {
        if(investigations == null)
        {
            throw new IllegalArgumentException("Investigations passed can not be null");
        }
        else
        {
            for(BoundClinicalEventEntry investigation : investigations)
            {
                this.removeInvestigation(investigation);
            }
        }
    }

    /**
     * Removes the all medications.
     *
     * @param medications the medications
     */
    @Transient
    public void removeAllMedications(Set<BoundClinicalEventEntry> medications) {
        if(medications == null)
        {
            throw new IllegalArgumentException("Medications passed can not be null");
        }
        else
        {
            for(BoundClinicalEventEntry medication : medications)
            {
                this.removeMedication(medication);
            }
        }
    }

    /**
     * Removes the finding.
     *
     * @param entry the entry
     */
    @Transient
    public void removeFeature(BoundClinicalEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof ClinicalFeatureEntry)
            {
                getEntries().remove(entry);
                getFeatures().remove(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"finding");
            }
        }
    }

    /**
     * Removes the finding.
     *
     * @param entry the entry
     */
    @Transient
    public void removeFinding(BoundClinicalEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof ClinicalFindingEntry)
            {
                getEntries().remove(entry);
                getClinicalFindings().remove(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"finding");
            }
        }
    }

    /**
     * Removes the intervention.
     *
     * @param entry the entry
     */
    @Transient
    public void removeIntervention(BoundClinicalEventEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof InterventionEventEntry)
            {
                getEntries().remove(entry);
                getInterventions().remove(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"intervention");
            }
        }
    }

    /**
     * Removes the investigation.
     *
     * @param entry the entry
     */
    @Transient
    public void removeInvestigation(BoundClinicalEventEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof InvestigationEventEntry)
            {
                getEntries().remove(entry);
                getInvestigations().remove(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"investigation");
            }
        }
    }

    /**
     * Removes the medication.
     *
     * @param entry the entry
     */
    @Transient
    public void removeMedication(BoundClinicalEventEntry entry) {
        if(entry == null)
        {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
        else
        {
            if(entry instanceof MedicationEventEntry)
            {
                getEntries().remove(entry);
                getMedications().remove(entry);
            }
            else
            {
                throw new IllegalArgumentException(ENTRY_ERROR_MESSAGE+"medication");
            }
        }
    }

    /**
     * Contains medication.
     *
     * @param medication the medication
     *
     * @return true, if successful
     */
    @Transient
    public boolean containsMedication(BoundClinicalEventEntry medication) {
        return getMedications().contains(medication);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
