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

import java.util.Collection;

/**
 * A specialised {@link uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider} that returns a random set
 * of descendants of a concept
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 18, 2011 at 8:28:01 PM
 */
public interface RandomSubtypeHierarchyProvider extends HierarchyProvider{
    Collection<String> getRandomSetOfSubTypes(String parentConceptId, int subsetSize);

    String getRandomSubtype(String parentConceptId);
}
