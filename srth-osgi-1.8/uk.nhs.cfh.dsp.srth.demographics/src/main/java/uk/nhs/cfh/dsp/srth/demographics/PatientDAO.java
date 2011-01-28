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
package uk.nhs.cfh.dsp.srth.demographics;

import uk.nhs.cfh.dsp.srth.demographics.person.Patient;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a DAO that manages {@link uk.nhs.cfh.dsp.srth.demographics.person.Patient}
 * objects.
 *
 *  <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2010 at 3:30:30 PM
 */
public interface PatientDAO {

    /**
     * Save patient.
     *
     * @param patient the patient
     */
    void savePatient(Patient patient);

    /**
     * Delete patient.
     *
     * @param patient the patient
     */
    void deletePatient(Patient patient);

    /**
     * Find all patients.
     *
     * @return the collection
     */
    Collection<Patient> findAllPatients();

    /**
     * Find patient.
     *
     * @param patientId the patient id
     * @return the patient
     */
    Patient findPatient(String patientId);

    /**
     * Gets the total patient count in database.
     *
     * @return the total patient count in database
     */
    long getTotalPatientCountInDatabase();

    /**
     * Gets the next patient id.
     *
     * @return the next patient id
     */
    long getNextPatientId();

    /**
     * Delete all records.
     */
    void deleteAllRecords();

    /**
     * Find patient.
     *
     * @param id the id
     * @return the patient
     */
    Patient findPatient(long id);

    /**
     * Gets the first patient id.
     *
     * @return the first patient id
     */
    long getFirstPatientId();

    void flushCache();
}
