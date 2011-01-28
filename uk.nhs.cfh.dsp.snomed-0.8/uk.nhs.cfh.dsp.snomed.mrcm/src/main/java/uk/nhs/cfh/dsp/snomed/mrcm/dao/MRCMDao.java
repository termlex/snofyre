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
package uk.nhs.cfh.dsp.snomed.mrcm.dao;

import uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint;

import java.util.List;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for the DAO that manages the MRCM constraints.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 11:05:53 AM
 */
public interface MRCMDao {
    
    /**
     * Save constraint.
     *
     * @param constraint the constraint
     */
    void saveConstraint(MRCMConstraint constraint);

    /**
     * Delete constraint.
     *
     * @param constraint the constraint
     */
    void deleteConstraint(MRCMConstraint constraint);

    /**
     * Find all constraints.
     *
     * @return the list
     */
    List<MRCMConstraint> findAllConstraints();

    /**
     * Find constraint.
     *
     * @param sourceId the source id
     * @param attributeId the attribute id
     * @return the list
     */
    List<MRCMConstraint> findConstraint(String sourceId, String attributeId);


    /**
     * Delete all constraints.
     */
    void deleteAllConstraints();

    /**
     * Gets the sanctioned values.
     *
     * @param sourceId the source id
     * @param attributeId the attribute id
     * @return the sanctioned values
     */
    Set<String> getSanctionedValues(String sourceId, String attributeId);

    /**
     * Gets the sanctioned attributes on concept.
     *
     * @param sourceId the source id
     * @return the sanctioned attributes on concept
     */
    Set<String> getSanctionedAttributesOnConcept(String sourceId);

    /**
     * Gets the sanctioned values on concept.
     *
     * @param sourceId the source id
     * @return the sanctioned values on concept
     */
    Set<String> getSanctionedValuesOnConcept(String sourceId);
}
