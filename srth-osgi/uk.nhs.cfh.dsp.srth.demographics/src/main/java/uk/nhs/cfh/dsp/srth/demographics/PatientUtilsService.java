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
import uk.nhs.cfh.dsp.srth.demographics.person.Person;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that implements several utility methods for generating
 * and accessing patient records.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 5, 2010 at 12:50:26 PM
 */
public interface PatientUtilsService extends PersonUtilsService {

    /**
     * Creates the patient.
     *
     * @param gender the gender
     * @param dob the dob
     * @return the patient
     */
    Patient createPatient(Person.Gender gender, Calendar dob);

    /**
     * Gets the date relative to patient age.
     *
     * @param p the p
     * @param ageYears the age years
     * @param ageMonths the age months
     * @param ageDays the age days
     * @return the date relative to patient age
     */
    Calendar getDateRelativeToPatientAge(Patient p, int ageYears, int ageMonths, int ageDays);

    /**
     * Gets the random date in time range.
     *
     * @param anchor the anchor
     * @param yearUpperBound the year upper bound
     * @param yearLowerBound the year lower bound
     * @param monthUpperBound the month upper bound
     * @param monthLowerBound the month lower bound
     * @param dayUpperBound the day upper bound
     * @param dayLowerBound the day lower bound
     * @return the random date in time range
     */
    Calendar getRandomDateInTimeRange(Calendar anchor,
                                      int yearUpperBound, int yearLowerBound,
                                      int monthUpperBound, int monthLowerBound,
                                      int dayUpperBound, int dayLowerBound);

    /**
     * Gets the random date in time range in future.
     *
     * @param anchor the anchor
     * @param yearUpperBound the year upper bound
     * @param yearLowerBound the year lower bound
     * @param monthUpperBound the month upper bound
     * @param monthLowerBound the month lower bound
     * @param dayUpperBound the day upper bound
     * @param dayLowerBound the day lower bound
     * @return the random date in time range in future
     */
    Calendar getRandomDateInTimeRangeInFuture(Calendar anchor,
                                              int yearUpperBound, int yearLowerBound,
                                              int monthUpperBound, int monthLowerBound,
                                              int dayUpperBound, int dayLowerBound);

    /**
     * Gets the sensible random date in time range.
     *
     * @param anchor the anchor
     * @param yearUpperBound the year upper bound
     * @param yearLowerBound the year lower bound
     * @param monthUpperBound the month upper bound
     * @param monthLowerBound the month lower bound
     * @param dayUpperBound the day upper bound
     * @param dayLowerBound the day lower bound
     * @return the sensible random date in time range
     */
    Calendar getSensibleRandomDateInTimeRange(
            Calendar anchor,
            int yearUpperBound, int yearLowerBound,
            int monthUpperBound, int monthLowerBound,
            int dayUpperBound, int dayLowerBound);

    /**
     * Gets the sensible random date in time range relative to patient.
     *
     * @param patient the patient
     * @param yearUpperBound the year upper bound
     * @param yearLowerBound the year lower bound
     * @param monthUpperBound the month upper bound
     * @param monthLowerBound the month lower bound
     * @param dayUpperBound the day upper bound
     * @param dayLowerBound the day lower bound
     * @return the sensible random date in time range relative to patient
     */
    Calendar getSensibleRandomDateInTimeRangeRelativeToPatient(Patient patient,
                                                               int yearUpperBound, int yearLowerBound,
                                                               int monthUpperBound, int monthLowerBound,
                                                               int dayUpperBound, int dayLowerBound);

    /**
     * Gets the sensible random date in time range relative to patient.
     *
     * @param patient the patient
     * @param yearUpperBound the year upper bound
     * @param yearLowerBound the year lower bound
     * @param monthUpperBound the month upper bound
     * @param monthLowerBound the month lower bound
     * @return the sensible random date in time range relative to patient
     */
    Calendar getSensibleRandomDateInTimeRangeRelativeToPatient(Patient patient,
                                                               int yearUpperBound, int yearLowerBound,
                                                               int monthUpperBound, int monthLowerBound
    );

    /**
     * Gets the sensible random date in time range relative to patient.
     *
     * @param patient the patient
     * @param yearUpperBound the year upper bound
     * @param yearLowerBound the year lower bound
     * @return the sensible random date in time range relative to patient
     */
    Calendar getSensibleRandomDateInTimeRangeRelativeToPatient(Patient patient,
                                                               int yearUpperBound, int yearLowerBound);

    /**
     * Creates the random patient.
     *
     * @param dob the dob
     * @return the patient
     */
    Patient createRandomPatient(Calendar dob);

    /**
     * Creates the random patient.
     *
     * @param minAge the min age
     * @return the patient
     */
    Patient createRandomPatient(int minAge);
}
