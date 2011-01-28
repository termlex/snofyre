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
package uk.nhs.cfh.dsp.snomed.objectmodel;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a role group construct in SNOMED CT.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 11:29:03 AM
 */
public interface SnomedRoleGroup extends CanonicalRepresentableElement, Comparable<SnomedRoleGroup>{

    /**
     * Gets the relationships.
     *
     * @return the relationships
     */
    Collection<SnomedRelationship> getRelationships();

    /**
     * Sets the relationships.
     *
     * @param relationships the new relationships
     */
    void setRelationships(Collection<SnomedRelationship> relationships);

    /**
     * Adds the relationship.
     *
     * @param relationship the relationship
     */
    void addRelationship(SnomedRelationship relationship);

    /**
     * Removes the relationship.
     *
     * @param relationship the relationship
     */
    void removeRelationship(SnomedRelationship relationship);

    /**
     * Gets the relationship group id.
     *
     * @return the relationship group id
     */
    String getRelationshipGroupId();

    /**
     * Sets the relationship group id.
     *
     * @param relationshipGroupId the new relationship group id
     */
    void setRelationshipGroupId(String relationshipGroupId);
}
