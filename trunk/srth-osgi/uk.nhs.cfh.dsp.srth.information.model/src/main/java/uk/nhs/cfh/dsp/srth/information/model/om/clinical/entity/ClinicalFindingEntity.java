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
 * A representation of a clinical finding within the domain
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Sunday; October 4, 2009 at 10:10 AM.
 */

public interface ClinicalFindingEntity extends ClinicalObservationEntity{

    /**
     * Gets the finding sites.
     *
     * @return the finding sites
     */
    Set<AnatomicalLocationEntity> getFindingSites();

    /**
     * Sets the finding sites.
     *
     * @param locations the new finding sites
     */
    void setFindingSites(Set<AnatomicalLocationEntity> locations);

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
     * Adds the finding site.
     *
     * @param location the location
     */
    void addFindingSite(AnatomicalLocationEntity location);

    /**
     * Removes the finding site.
     *
     * @param location the location
     */
    void removeFindingSite(AnatomicalLocationEntity location);
}
