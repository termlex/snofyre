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
 * The Interface AnatomicalLocation which represents a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} of type Body Structure.
 * It should have a laterality of type {@link Laterality} and parity of type
 * {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.AnatomicalLocationEntity.Parity}.
 * Parity represents the property of a Body structure being paired or unpaired in the body.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 4, 2009 at 10:01:00 AM
 */
public interface AnatomicalLocationEntity extends BoundClinicalEntity{

    /**
     * The Enum Parity.
     */
    public static enum Parity {
        PAIRED,
        UNPAIRED,
        UNDEFINED};

    /**
     * Gets the laterality.
     *
     * @return the laterality
     */
    Laterality getLaterality();

    /**
     * Sets the laterality.
     *
     * @param laterality the new laterality
     */
    void setLaterality(Laterality laterality);

    /**
     * Gets the parity.
     *
     * @return the parity
     */
    Parity getParity();

    /**
     * Sets the parity.
     *
     * @param parity the new parity
     */
    void setParity(Parity parity);

}