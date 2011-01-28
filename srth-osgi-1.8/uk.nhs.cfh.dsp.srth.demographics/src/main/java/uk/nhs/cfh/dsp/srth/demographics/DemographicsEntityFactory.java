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

// TODO: Auto-generated Javadoc
/**
 * an interface specification for a factory for creating demographics entities.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 28, 2009 at 9:59:50 AM
 */
public interface DemographicsEntityFactory {

	/**
	 * Gets the person.
	 * 
	 * @return the person
	 */
	Person getPerson();

	/**
	 * Gets the patient.
	 * 
	 * @return the patient
	 */
	Patient getPatient();

    /**
     * Gets the patient.
     *
     * @param patientID the patient id
     * @return the patient
     */
    Patient getPatient(String patientID);
}