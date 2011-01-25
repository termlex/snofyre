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
package uk.nhs.cfh.dsp.srth.demographics.person;

import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a Patient. It extends a {@link uk.nhs.cfh.dsp.srth.demographics.person.Person}
 * and assigns a patient id and {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR} to it.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 30, 2009 at 12:30:45 PM
 */
public interface Patient extends Person {

//    /**
//     * Gets the patient id.
//     *
//     * @return the patient id
//     */
//    String getUUID();
//
//    /**
//     * Sets the patient id.
//     *
//     * @param patientId the new patient id
//     */
//    void setUUID(String patientId);

    /**
     * Gets the ehr.
     * 
     * @return the ehr
     */
    EHR getEhr();

    /**
     * Sets the ehr.
     * 
     * @param ehr the new ehr
     */
    void setEhr(EHR ehr);
}
