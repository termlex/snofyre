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
package uk.nhs.cfh.dsp.snomed.mrcm;

import uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;

import java.util.List;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that makes MRCM queries available.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 4:04:58 PM
 */
public interface MRCMService {

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
     * Gets the sanctioned attributes.
     *
     * @param type the type
     * @return the sanctioned attributes
     */
    Set<String> getSanctionedAttributes(ConceptType type);

    /**
     * Gets the sanctioned values.
     *
     * @param type the type
     * @param attributeId the attribute id
     * @return the sanctioned values
     */
    Set<String> getSanctionedValues(ConceptType type, String attributeId);

    /**
     * Gets the sanctioned attributes.
     *
     * @param conceptId the concept id
     * @return the sanctioned attributes
     */
    Set<String> getSanctionedAttributes(String conceptId);

    /**
     * Gets the sanctioned values.
     *
     * @param conceptId the concept id
     * @return the sanctioned values
     */
    Set<String> getSanctionedValues(String conceptId);

    /**
     * Gets the sanctioned values.
     *
     * @param type the type
     * @return the sanctioned values
     */
    Set<String> getSanctionedValues(ConceptType type);
}
