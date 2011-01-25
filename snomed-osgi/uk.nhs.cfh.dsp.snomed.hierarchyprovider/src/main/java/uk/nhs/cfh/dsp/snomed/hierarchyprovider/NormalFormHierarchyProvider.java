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
package uk.nhs.cfh.dsp.snomed.hierarchyprovider;

import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a hierarchy provider that returns primitive, defined
 * and proximal primitive parents for a given concept.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 10, 2009 at 11:45:11 AM
 */
public interface NormalFormHierarchyProvider extends HierarchyProvider{

    /**
     * Gets the primitive parents.
     *
     * @param conceptId the concept id
     * @return the primitive parents
     */
    Set<String> getPrimitiveParents(String conceptId);

    /**
     * Gets the fully defined parents.
     *
     * @param conceptId the concept id
     * @return the fully defined parents
     */
    Set<String> getFullyDefinedParents(String conceptId);

    /**
     * Gets the proximal primitive parents.
     *
     * @param conceptId the concept id
     * @return the proximal primitive parents
     */
    Set<String> getProximalPrimitiveParents(String conceptId);

    /**
     * Gets the primitive ancestors.
     *
     * @param conceptId the concept id
     * @return the primitive ancestors
     */
    Set<String> getPrimitiveAncestors(String conceptId);

    /**
     * Gets the fully defined ancestors.
     *
     * @param conceptId the concept id
     * @return the fully defined ancestors
     */
    Set<String> getFullyDefinedAncestors(String conceptId);

    /**
     * Gets the proximal primitives parents.
     *
     * @param concept the concept
     * @return the proximal primitives parents
     */
    Set<SnomedConcept> getProximalPrimitivesParents(SnomedConcept concept);
}
