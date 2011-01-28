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
package uk.nhs.cfh.dsp.srth.information.model.impl;

import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a DAO that manages {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR}s
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2010 at 11:31:16 AM
 */
public interface EhrDao {

    /**
     * Save ehr.
     *
     * @param ehr the ehr
     */
    void saveEHR(EHR ehr);

    /**
     * Delete ehr.
     *
     * @param ehr the ehr
     */
    void deleteEHR(EHR ehr);

    /**
     * Find all.
     *
     * @return the collection
     */
    Collection<EHR> findAll();

    /**
     * Find by id.
     *
     * @param patientId the patient id
     * @return the eHR
     */
    EHR findById(long patientId);
}
