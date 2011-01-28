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
package uk.nhs.cfh.dsp.snomed.dao;

import uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.error.ConceptNotFoundException;

import java.util.Collection;


// TODO: Auto-generated Javadoc
/**
 * A generic Data Access Object based on the Java DAO paradigm
 *
 * <br>Version : @#VersionNumber#@
 * <br> Written by @author jay
 * <br> Created on Dec 15, 2008 at 8:56:16 PM
 */
public interface TerminologyConceptDAO {

    /**
     * Save terminology concept.
     *
     * @param concept the concept
     */
    void saveTerminologyConcept(TerminologyConcept concept);

    /**
     * Gets the terminology concept.
     *
     * @param conceptID the concept id
     * @return the terminology concept
     * @throws ConceptNotFoundException the concept not found exception
     */
    TerminologyConcept getTerminologyConcept(String conceptID) throws ConceptNotFoundException;

    /**
     * Gets the matching concepts.
     *
     * @param label the label
     * @return the matching concepts
     */
    Collection<TerminologyConcept> getMatchingConcepts(String label);

    /**
     * Gets the current concept for concept.
     *
     * @param conceptId the concept id
     * @return the current concept for concept
     * @throws ConceptNotFoundException the concept not found exception
     */
    TerminologyConcept getCurrentConceptForConcept(String conceptId) throws ConceptNotFoundException;

    /**
     * Delete concept.
     *
     * @param concept the concept
     */
    void deleteConcept(TerminologyConcept concept);

    /**
     * returns all concepts in the terminology, as specified in the DAO settings.
     * // TODO : need to create a Terminology Object so this can be handled more elegantly
     *
     * @return the list
     */
    Collection<TerminologyConcept> getAllConceptsInTerminology();

    /**
     * Gets the concept count.
     *
     * @return the concept count
     */
    int getConceptCount();
}
