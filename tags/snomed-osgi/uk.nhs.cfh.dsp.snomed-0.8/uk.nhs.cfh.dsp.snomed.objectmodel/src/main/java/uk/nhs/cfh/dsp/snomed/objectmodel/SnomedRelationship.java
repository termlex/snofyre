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

// TODO: Auto-generated Javadoc
/**
 * An interface specification of an object representing a SNOMED CT Relationship.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 20, 2009 at 7:56:05 PM
 */
public interface SnomedRelationship extends TerminologyRelationship,CanonicalRepresentableElement,
                                Comparable<SnomedRelationship>{

    /** The Refinability. */
    public enum Refinability{MANDATORY, OPTIONAL, NOT_REFINABLE}

    /** The Relationship type. */
    public enum RelationshipType {DEFINING, QUALIFIER, HISTORICAL, ADDITIONAL}

    /**
     * Gets the source concept.
     *
     * @return the source concept
     */
    SnomedConcept getSourceConcept();

    /**
     * Sets the source concept.
     *
     * @param sourceConcept the new source concept
     */
    void setSourceConcept(SnomedConcept sourceConcept);

    /**
     * Gets the target concept.
     *
     * @return the target concept
     */
    SnomedConcept getTargetConcept();

    /**
     * Sets the target concept.
     *
     * @param targetConcept the new target concept
     */
    void setTargetConcept(SnomedConcept targetConcept);

    /**
     * Gets the relationship type.
     *
     * @return the relationship type
     */
    String getRelationshipType();

    /**
     * Sets the relationship type.
     *
     * @param relationshipType the new relationship type
     */
    void setRelationshipType(String relationshipType);

    /**
     * Gets the refinability.
     *
     * @return the refinability
     */
    Refinability getRefinability();

    /**
     * Sets the refinability.
     *
     * @param refinability the new refinability
     */
    void setRefinability(Refinability refinability);

    /**
     * Gets the type.
     *
     * @return the type
     */
    RelationshipType getType();

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    void setType(RelationshipType type);

    /**
     * Checks if is defining relation.
     *
     * @return true, if is defining relation
     */
    boolean isDefiningRelation();

    /**
     * Checks if is qualifying relation.
     *
     * @return true, if is qualifying relation
     */
    boolean isQualifyingRelation();

    /**
     * Checks if is mandatory.
     *
     * @return true, if is mandatory
     */
    boolean isMandatory();

    /**
     * Checks if is optional.
     *
     * @return true, if is optional
     */
    boolean isOptional();

    /**
     * Checks if is refinable.
     *
     * @return true, if is refinable
     */
    boolean isRefinable();

    /**
     * Gets the relationship group.
     *
     * @return the relationship group
     */
    String getRelationshipGroup();

    /**
     * Sets the relationship group.
     *
     * @param relationshipGroup the new relationship group
     */
    void setRelationshipGroup(String relationshipGroup);

    /**
     * Gets the source.
     *
     * @return the source
     */
    String getSource();

    /**
     * Sets the source.
     *
     * @param source the new source
     */
    void setSource(String source);

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the name.
     *
     * @param relationshipName the new name
     */
    void setName(String relationshipName);
}
