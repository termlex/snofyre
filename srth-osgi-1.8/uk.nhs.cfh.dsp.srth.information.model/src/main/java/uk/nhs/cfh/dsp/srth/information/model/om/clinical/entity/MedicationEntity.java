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
package uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity;


// TODO: Auto-generated Javadoc
/**
 * An interface representation of a Medication entry in the patient record. It is guaranteed
 * to have a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} of type "Product" associated with it
 * and a time of administration. A {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.MedicationEntity} entity will usually have a dose
 * associated with it.
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Sunday; October 4, 2009 at 8:10 PM
 */

public interface MedicationEntity extends ClinicalActivityEntity {

    /**
     * Gets the dose.
     *
     * @return the dose
     */
    Double getDose();

    /**
     * Sets the dose.
     *
     * @param dose the new dose
     */
    void setDose(Double dose);

    /**
     * Gets the dose units.
     *
     * @return the dose units
     */
    String getDoseUnits();

    /**
     * Sets the dose units.
     *
     * @param units the new dose units
     */
    void setDoseUnits(String units);
}
