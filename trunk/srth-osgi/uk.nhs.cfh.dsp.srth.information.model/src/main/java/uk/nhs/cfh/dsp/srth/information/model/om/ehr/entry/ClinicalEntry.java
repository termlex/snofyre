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
package uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry;

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An interface that represents a generic Clinical entry which is guaranteed to have a time stamp
 * and a name
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2009 at 8:07:47 PM
 */

public interface ClinicalEntry {

    /**
     * Gets the free text entry.
     *
     * @return the free text entry
     */
    String getFreeTextEntry();

    /**
     * Sets the free text entry.
     *
     * @param name the new free text entry
     */
    void setFreeTextEntry(String name);

    /**
     * Sets the attestation time.
     *
     * @param time the new attestation time
     */
    void setAttestationTime(Calendar time);

    /**
     * Gets the attestation time.
     *
     * @return the attestation time
     */
    Calendar getAttestationTime();

    /**
     * Gets the patient id.
     *
     * @return the patient id
     */
    long getPatientId();

    /**
     * Sets the patient id.
     *
     * @param id the new patient id
     */
    void setPatientId(long id);

    /**
     * Gets the entry type.
     *
     * @return the entry type
     */
    String getEntryType();

    /**
     * Sets the entry type.
     *
     * @param entryType the new entry type
     */
    void setEntryType(String entryType);

    /**
     * Sets the entity.
     *
     * @param entity the new entity
     */
    void setEntity(ClinicalEntity entity);

    /**
     * Gets the entity.
     *
     * @return the entity
     */
    ClinicalEntity getEntity();
}
