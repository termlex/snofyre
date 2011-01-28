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

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 9, 2009 at 11:17:31 AM.
 */
public interface HierarchyProvider {

    /**
     * Gets the parents.
     *
     * @param conceptId the concept id
     * @return the parents
     */
    Set<String> getParents(String conceptId);

    /**
     * Gets the children.
     *
     * @param conceptId the concept id
     * @return the children
     */
    Set<String> getChildren(String conceptId);

    /**
     * Gets the descendants.
     *
     * @param conceptId the concept id
     * @return the descendants
     */
    Set<String> getDescendants(String conceptId);

    /**
     * Gets the ancestors.
     *
     * @param conceptId the concept id
     * @return the ancestors
     */
    Set<String> getAncestors(String conceptId);
}
