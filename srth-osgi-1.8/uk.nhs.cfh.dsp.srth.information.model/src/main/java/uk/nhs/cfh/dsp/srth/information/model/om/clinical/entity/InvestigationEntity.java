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
 * A simple representation of an Investigation.
 * It should have a target site which is an instance of an
 * {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.AnatomicalLocationEntity} at the very least.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Sunday; October 4, 2009 at 7:37 PM
 */

public interface InvestigationEntity extends ClinicalActivityEntity{

    /**
     * Gets the target site.
     *
     * @return the target site
     */
    Set<AnatomicalLocationEntity> getTargetSites();

    /**
     * Sets the target site.
     *
     * @param locations the locations
     */
    void setTargetSites(Set<AnatomicalLocationEntity> locations);

    /**
     * Sets the findings.
     *
     * @param findings the new findings
     */
    void setFindings(Set<ClinicalObservationEntity> findings);

    /**
     * Gets the findings.
     *
     * @return the findings
     */
    Set<ClinicalObservationEntity> getFindings();

    /**
     * Adds the finding.
     *
     * @param finding the finding
     */
    void addFinding(ClinicalObservationEntity finding);

    /**
     * Removes the finding.
     *
     * @param finding the finding
     */
    void removeFinding(ClinicalObservationEntity finding);

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
