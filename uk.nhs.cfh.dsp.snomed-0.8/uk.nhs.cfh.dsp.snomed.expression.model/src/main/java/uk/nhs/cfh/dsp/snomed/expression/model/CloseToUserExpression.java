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
package uk.nhs.cfh.dsp.snomed.expression.model;

import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an object that represents a SNOMED CT Expression. The term ' SNOMED CT Expression'
 * is generally applied to an {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} that has been modified
 * by refining or adding qualifiers to its existing definition.
 * <br> Note that a refinement is only represented by a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship}
 * though one mgiht choose to refine all the values within the relationships in a
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup}. However, the way ungrouped attributes are merged during
 * normalisation, it does not make any difference if they are represented as separate ungrouped refinments.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 8, 2010 at 4:10:32 PM
 */
public interface CloseToUserExpression extends ExpressionWithFocusConcepts{

    /**
     * Adds the refinement.
     *
     * @param refinement the refinement
     */
    void addRefinement(PropertyExpression refinement);

    /**
     * Removes the refinement.
     *
     * @param refinement the refinement
     */
    void removeRefinement(PropertyExpression refinement);

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
     * Gets the refinement expressions.
     *
     * @return the refinement expressions
     */
    Collection<PropertyExpression> getRefinementExpressions();

    /**
     * Sets the refinement expressions.
     *
     * @param refinementExpressions the new refinement expressions
     */
    void setRefinementExpressions(Collection<PropertyExpression> refinementExpressions);
}
