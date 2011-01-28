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

import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an object representing a SNOMED CT Normal Form.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 19, 2009 at 4:38:18 PM
 */
public interface NormalFormExpression extends ExpressionWithFocusConcepts{

    /**
     * Gets the proximal primitives.
     *
     * @return the proximal primitives
     */
    Collection<SnomedConcept> getProximalPrimitives();

    /**
     * Sets the proximal primitives.
     *
     * @param proximalPrimitives the new proximal primitives
     */
    void setProximalPrimitives(Collection<SnomedConcept> proximalPrimitives);

    /**
     * Gets the relationship expressions.
     *
     * @return the relationship expressions
     */
    Collection<SnomedRelationshipPropertyExpression> getRelationshipExpressions();

    /**
     * Sets the relationship expressions.
     *
     * @param relationshipExpressions the new relationship expressions
     */
    void setRelationshipExpressions(Collection<SnomedRelationshipPropertyExpression> relationshipExpressions);

    /**
     * Gets the role group expressions.
     *
     * @return the role group expressions
     */
    Collection<SnomedRoleGroupExpression> getRoleGroupExpressions();

    /**
     * Sets the role group expressions.
     *
     * @param roleGroupExpressions the new role group expressions
     */
    void setRoleGroupExpressions(Collection<SnomedRoleGroupExpression> roleGroupExpressions);

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
     * Adds the role group.
     *
     * @param roleGroup the role group
     */
    void addRoleGroup(SnomedRoleGroup roleGroup);

    /**
     * Removes the role group.
     *
     * @param roleGroup the role group
     */
    void removeRoleGroup(SnomedRoleGroup roleGroup);

    /**
     * Adds the relationship expression.
     *
     * @param expression the expression
     */
    void addRelationshipExpression(PropertyExpression expression);

    /**
     * Removes the relationship expression.
     *
     * @param expression the expression
     */
    void removeRelationshipExpression(PropertyExpression expression);
}