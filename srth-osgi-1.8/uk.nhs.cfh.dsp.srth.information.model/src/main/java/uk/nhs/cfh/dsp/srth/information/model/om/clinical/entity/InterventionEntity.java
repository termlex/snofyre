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

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An object representation of a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} of type Procedure that represents
 * a surgical or some other therapeutic intervention. It does not include Pharmacological interventions.
 * Note this interface has not been specialised into the different types of Procedures within SNOMED.
 * A Procedure should have at the very least a target site of type
 * {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.AnatomicalLocationEntity}
 * and a laterality of type {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.Laterality}.
 * Please note that it is not guaranteed that all procedures will have a target site.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br> Created on Sunday; October 4, 2009 at 7:35 PM
 */

public interface InterventionEntity extends ClinicalActivityEntity{

    /**
     * Gets the target site.
     *
     * @return the target site
     */
    Set<AnatomicalLocationEntity> getTargetSites();

    /**
     * Sets the target site.
     *
     * @param location the new target site
     */
    void setTargetSites(Set<AnatomicalLocationEntity> location);

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
     * Adds the target site.
     *
     * @param targetSite the target site
     */
    void addTargetSite(AnatomicalLocationEntity targetSite);

    /**
     * Removes the target site.
     *
     * @param targetSite the target site
     */
    void removeTargetSite(AnatomicalLocationEntity targetSite);
}
