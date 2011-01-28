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
package uk.nhs.cfh.dsp.srth.information.model.om.ehr;

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEventEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2009 at 8:07:47 PM
 * <br>Modified on : Sep 23, 2009
 * <br>Replaced previous implementation with {@link ClinicalEntity} types.
 * <br> Modified on : Oct 26, 2009
 * <br> Replaced previous implementation with {@link ClinicalEntry} and its types.
 */

public interface EHR {

    /**
     * Gets the patient id.
     *
     * @return the patient id
     */
    long getPatientId();

    /**
     * Sets the patient id.
     * @param patientId the new patient id
     */
    void setPatientId(long patientId);

    /**
     * Gets the clinical findings.
     * @return the clinical findings
     */
    Set<BoundClinicalEntry> getClinicalFindings();

    /**
     * Sets the clinical findings.
     * @param clinicalFindings the new clinical findings
     */
    void setClinicalFindings(Set<BoundClinicalEntry> clinicalFindings);

    /**
     * Gets the interventions.
     * @return the interventions
     */
    Set<BoundClinicalEventEntry> getInterventions();

    /**
     * Sets the interventions.
     * @param procedures the new interventions
     */
    void setInterventions(Set<BoundClinicalEventEntry> procedures);

    /**
     * Gets the investigations.
     * @return the investigations
     */
    Set<BoundClinicalEventEntry> getInvestigations();

    /**
     * Sets the investigations.
     * @param investigations the new investigations
     */
    void setInvestigations(Set<BoundClinicalEventEntry> investigations);

    /**
     * Adds the finding.
     * @param finding the finding
     */
    void addFinding(BoundClinicalEntry finding);

    /**
     * Adds the all findings.
     * @param findings the findings
     */
    void addAllFindings(Set<BoundClinicalEntry> findings);

    /**
     * Removes the finding.
     * @param finding the finding
     */
    void removeFinding(BoundClinicalEntry finding);

    /**
     * Removes the all findings.
     * @param findings the findings
     */
    void removeAllFindings(
            Set<BoundClinicalEntry> findings);

    /**
     * Contains finding.
     * @param finding the finding
     * @return true, if successful
     */
    boolean containsFinding(BoundClinicalEntry finding);

    /**
     * Adds the investigation.
     * @param investigation the investigation
     */
    void addInvestigation(BoundClinicalEventEntry investigation);

    /**
     * Adds the all investigations.
     * @param invs the invsestigations to add
     */
    void addAllInvestigations(Set<BoundClinicalEventEntry> invs);

    /**
     * Removes the investigation.
     * @param investigation the investigation to remove
     */
    void removeInvestigation(BoundClinicalEventEntry investigation);

    /**
     * Removes the all investigations.
     * @param invs the invsestigations to remove
     */
    void removeAllInvestigations(
            Set<BoundClinicalEventEntry> invs);

    /**
     * Contains investigation.
     * @param investigation the investigation
     * @return true, if successful
     */
    boolean containsInvestigation(BoundClinicalEventEntry investigation);

    /**
     * Adds the intervention.
     * @param intervention the intervention
     */
    void addIntervention(BoundClinicalEventEntry intervention);

    /**
     * Adds the all interventions.
     * @param interventions the interventions
     */
    void addAllInterventions(Set<BoundClinicalEventEntry> interventions);

    /**
     * Removes the intervention.
     * @param intervention the intervention
     */
    void removeIntervention(BoundClinicalEventEntry intervention);

    /**
     * Removes the all interventions.
     * @param interventions the interventions
     */
    void removeAllInterventions(Set<BoundClinicalEventEntry> interventions);

    /**
     * Contains intervention.
     * @param intervention the intervention
     * @return true, if successful
     */
    boolean containsIntervention(BoundClinicalEventEntry intervention);

    /**
     * Gets the medications.
     * @return the medications
     */
    Set<BoundClinicalEventEntry> getMedications();

    /**
     * Sets the medications.
     * @param medications the new medications
     */
    void setMedications(Set<BoundClinicalEventEntry> medications);

    /**
     * Adds the medication.
     * @param medication the medication
     */
    void addMedication(BoundClinicalEventEntry medication);

    /**
     * Removes the medication.
     * @param medication the medication
     */
    void removeMedication(BoundClinicalEventEntry medication);

    /**
     * Removes the all medications.
     * @param medications the medications
     */
    void removeAllMedications(Set<BoundClinicalEventEntry> medications);

    /**
     * Adds the all medications.
     * @param medications the medications
     */
    void addAllMedications(Set<BoundClinicalEventEntry> medications);

    /**
     * Contains medication.
     * @param medication the medication
     * @return true, if successful
     */
    boolean containsMedication(BoundClinicalEventEntry medication);

    /**
     * Gets the features.
     * @return the features
     */
    Set<BoundClinicalEntry> getFeatures();

    /**
     * Sets the features.
     * @param features the new features
     */
    void setFeatures(Set<BoundClinicalEntry> features);

    /**
     * Gets the entries.
     * @return the entries
     */
    Set<BoundClinicalEntry> getEntries();

    /**
     * Sets the entries.
     * @param entries the new entries
     */
    void setEntries(Set<BoundClinicalEntry> entries);

    /**
     * Adds the all features.
     * @param features the features
     */
    void addAllFeatures(Set<BoundClinicalEntry> features);

    /**
     * Adds the feature.
     * @param entry the entry
     */
    void addFeature(BoundClinicalEntry entry);

    /**
     * Removes the all features.
     * @param features the features
     */
    void removeAllFeatures(Set<BoundClinicalEntry> features);

    /**
     * Removes the feature.
     * @param entry the entry
     */
    void removeFeature(BoundClinicalEntry entry);
}