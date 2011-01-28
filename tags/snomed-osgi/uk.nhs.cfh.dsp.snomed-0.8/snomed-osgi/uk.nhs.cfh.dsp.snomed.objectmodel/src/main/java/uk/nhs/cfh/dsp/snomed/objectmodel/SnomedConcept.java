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
 * An interface specification of a SNOMED CT concept.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 20, 2009 at 7:28:11 PM
 */
public interface SnomedConcept extends TerminologyConcept, CanonicalRepresentableElement, Comparable<SnomedConcept>{
    
    /**
 * Gets the type.
 *
 * @return the type
 */
ConceptType getType();

    /**
     * Sets the type.
     *
     * @param conceptType the new type
     */
    void setType(ConceptType conceptType);

    /**
     * Gets the fully specified name.
     *
     * @return the fully specified name
     */
    String getFullySpecifiedName();

    /**
     * Sets the fully specified name.
     *
     * @param fullySpecifiedName the new fully specified name
     */
    void setFullySpecifiedName(String fullySpecifiedName);

    /**
     * Gets the descriptions.
     *
     * @return the descriptions
     */
    Collection<SnomedDescription> getDescriptions();

    /**
     * Sets the descriptions.
     *
     * @param descriptions the new descriptions
     */
    void setDescriptions(Collection<SnomedDescription> descriptions);

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
     * Gets the source relationships.
     *
     * @return the source relationships
     */
    Collection<SnomedRelationship> getSourceRelationships();

    /**
     * Checks if is primitive.
     *
     * @return true, if is primitive
     */
    boolean isPrimitive();

    /**
     * Sets the primitive.
     *
     * @param isPrimitive the new primitive
     */
    void setPrimitive(boolean isPrimitive);

    /**
     * Sets the source relationships.
     *
     * @param sourceRelationships the new source relationships
     */
    void setSourceRelationships(Collection<SnomedRelationship> sourceRelationships);

    /**
     * Gets the target relationships.
     *
     * @return the target relationships
     */
    Collection<SnomedRelationship> getTargetRelationships();

    /**
     * Sets the target relationships.
     *
     * @param targetRelationships the new target relationships
     */
    void setTargetRelationships(Collection<SnomedRelationship> targetRelationships);

    /**
     * Gets the child id set.
     *
     * @return the child id set
     */
    Collection<String> getChildIDSet();

    /**
     * Sets the child id set.
     *
     * @param childIDSet the new child id set
     */
    void setChildIDSet(Collection<String> childIDSet);

    /**
     * Gets the parent id set.
     *
     * @return the parent id set
     */
    Collection<String> getParentIDSet();

    /**
     * Sets the parent id set.
     *
     * @param parentIDSet the new parent id set
     */
    void setParentIDSet(Collection<String> parentIDSet);

    /**
     * Gets the ctv3 id.
     *
     * @return the ctv3 id
     */
    String getCtv3ID();

    /**
     * Sets the ctv3 id.
     *
     * @param ctv3ID the new ctv3 id
     */
    void setCtv3ID(String ctv3ID);

    /**
     * Gets the snomed id.
     *
     * @return the snomed id
     */
    String getSnomedID();

    /**
     * Sets the snomed id.
     *
     * @param snomedID the new snomed id
     */
    void setSnomedID(String snomedID);

    /**
     * Gets the refining relationships.
     *
     * @return the refining relationships
     */
    Collection<SnomedRelationship> getRefiningRelationships();

    /**
     * Gets the defining relationships.
     *
     * @return the defining relationships
     */
    Collection<SnomedRelationship> getDefiningRelationships();

    /**
     * Gets the optional relationships.
     *
     * @return the optional relationships
     */
    Collection<SnomedRelationship> getOptionalRelationships();

    /**
     * Gets the mandatory relationships.
     *
     * @return the mandatory relationships
     */
    Collection<SnomedRelationship> getMandatoryRelationships();

    /**
     * Sets the refining relationships.
     *
     * @param refiningRelationships the new refining relationships
     */
    void setRefiningRelationships(Collection<SnomedRelationship> refiningRelationships);

    /**
     * Sets the defining relationships.
     *
     * @param definingRelationships the new defining relationships
     */
    void setDefiningRelationships(Collection<SnomedRelationship> definingRelationships);

    /**
     * Sets the optional relationships.
     *
     * @param optionalRelationships the new optional relationships
     */
    void setOptionalRelationships(Collection<SnomedRelationship> optionalRelationships);

    /**
     * Sets the mandatory relationships.
     *
     * @param mandatoryRelationships the new mandatory relationships
     */
    void setMandatoryRelationships(Collection<SnomedRelationship> mandatoryRelationships);

    /**
     * Gets the descendant ids.
     *
     * @return the descendant ids
     */
    Collection<String> getDescendantIds();

    /**
     * Sets the descendant ids.
     *
     * @param descendantIds the new descendant ids
     */
    void setDescendantIds(Collection<String> descendantIds);

    /**
     * Gets the ancestor ids.
     *
     * @return the ancestor ids
     */
    Collection<String> getAncestorIds();

    /**
     * Sets the ancestor ids.
     *
     * @param ancestorIds the new ancestor ids
     */
    void setAncestorIds(Collection<String> ancestorIds);

    /**
     * Gets the proximal primtive ids.
     *
     * @return the proximal primtive ids
     */
    Collection<String> getProximalPrimtiveIds();

    /**
     * Sets the proximal primtive ids.
     *
     * @param proximalPrimtiveIds the new proximal primtive ids
     */
    void setProximalPrimtiveIds(Collection<String> proximalPrimtiveIds);

    /**
     * Gets the children.
     *
     * @return the children
     */
    Collection<SnomedConcept> getChildren();

    /**
     * Sets the children.
     *
     * @param children the new children
     */
    void setChildren(Collection<SnomedConcept> children);

    /**
     * Gets the descendants.
     *
     * @return the descendants
     */
    Collection<SnomedConcept> getDescendants();

    /**
     * Sets the descendants.
     *
     * @param descendants the new descendants
     */
    void setDescendants(Collection<SnomedConcept> descendants);

    /**
     * Gets the ancestors.
     *
     * @return the ancestors
     */
    Collection<SnomedConcept> getAncestors();

    /**
     * Sets the ancestors.
     *
     * @param ancestors the new ancestors
     */
    void setAncestors(Collection<SnomedConcept> ancestors);

    /**
     * Gets the parents.
     *
     * @return the parents
     */
    Collection<SnomedConcept> getParents();

    /**
     * Sets the parents.
     *
     * @param parents the new parents
     */
    void setParents(Collection<SnomedConcept> parents);

    /**
     * Gets the role groups.
     *
     * @return the role groups
     */
    Collection<SnomedRoleGroup> getRoleGroups();

    /**
     * Sets the role groups.
     *
     * @param roleGroups the new role groups
     */
    void setRoleGroups(Collection<SnomedRoleGroup> roleGroups);

    /**
     * Gets the non-subsumption defining relationships.
     *
     * @return the non sub sumption defining relationships
     */
    Collection<SnomedRelationship> getNonSubSumptionDefiningRelationships();

    /**
     * Gets the all refinable relationships.
     *
     * @return the all refinable relationships
     */
    Collection<SnomedRelationship> getAllRefinableRelationships();

    /**
     * Gets the all refinable role groups.
     *
     * @return the all refinable role groups
     */
    Collection<SnomedRoleGroup> getAllRefinableRoleGroups();
}
