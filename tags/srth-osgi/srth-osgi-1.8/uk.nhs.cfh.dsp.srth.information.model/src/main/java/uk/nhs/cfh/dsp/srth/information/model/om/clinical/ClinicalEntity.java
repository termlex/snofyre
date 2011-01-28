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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.nhs.cfh.dsp.srth.information.model.om.clinical;

import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface that represents a generic clinical entity. It may or may not
 * have a corresponding @ClinicalEntry in the record.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 4:18:29 PM
 */

public interface ClinicalEntity {

    public enum Type{OBSERVATION, FINDING, FEATURE, ACTIVITY, INVESTIGATION, INTERVENTION, MEDICATION,
        ANATOMICAL_LOCATION, UNKNOWN}

    /**
     * Gets the instantiation time.
     *
     * @return the instantiation time
     */
    Calendar getInstantiationTime();

    /**
     * Sets the instantiation time.
     *
     * @param time the new instantiation time
     */
    void setInstantiationTime(Calendar time);

    /**
     * Gets the type.
     *
     * @return the type
     */
    Type getType();

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    void setName(String name);

    /**
     * Gets the uUID.
     *
     * @return the uUID
     */
    UUID getUUID();

    /**
     * Sets the uUID.
     *
     * @param uuid the new uUID
     */
    void setUUID(UUID uuid);

    /**
     * Gets the coreferences.
     *
     * @return the coreferences
     */
    Set<ClinicalEntity> getCoreferences();

    /**
     * Sets the coreferences.
     *
     * @param coreferences the new coreferences
     */
    void setCoreferences(Set<ClinicalEntity> coreferences);

    /**
     * Adds the coreference.
     *
     * @param entity the entity
     */
    void addCoreference(ClinicalEntity entity);

    /**
     * Removes the coreference.
     *
     * @param entity the entity
     */
    void removeCoreference(ClinicalEntity entity);

    /**
     * Gets the clinically relevant time.
     *
     * @return the clinically relevant time
     */
    Calendar getClinicallyRelevantTime();

    /**
     * Sets the clinically relevant time.
     *
     * @param time the new clinically relevant time
     */
    void setClinicallyRelevantTime(Calendar time);
}
