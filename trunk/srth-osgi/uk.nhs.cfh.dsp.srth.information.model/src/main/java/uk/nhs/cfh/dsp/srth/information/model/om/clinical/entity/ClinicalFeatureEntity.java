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
package uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity;

// TODO: Auto-generated Javadoc
/**
 * A class that is named oddly but represents the combination of an
 * SNOMED CT observable entity with a {@link Number} representing a numerical
 * value. Semantically, this is equivalent to a
 * {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalFindingEntity}.
 * The Interface ClinicalObservation.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2009 at 9:12:44 PM.
 * <br>Modified on Sunday; October 4, 2009 at 7:21 PM
 */

public interface ClinicalFeatureEntity extends ClinicalObservationEntity{

    /**
     * Gets the value.
     *
     * @return the value
     */

    Double getValue();

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    void setValue(Double value);
}