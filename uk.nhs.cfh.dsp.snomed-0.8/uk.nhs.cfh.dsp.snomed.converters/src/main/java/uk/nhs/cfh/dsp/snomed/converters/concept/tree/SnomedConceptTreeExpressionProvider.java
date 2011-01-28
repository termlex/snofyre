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
package uk.nhs.cfh.dsp.snomed.converters.concept.tree;

import uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.util.Collection;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for representing a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} as a tree
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 5, 2010 at 10:05:14 AM
 */
public interface SnomedConceptTreeExpressionProvider {


    /**
     * Gets the concept.
     *
     * @return the concept
     */
    SnomedConcept getConcept();

    /**
     * Sets the concept.
     *
     * @param concept the new concept
     */
    void setConcept(SnomedConcept concept);

    /**
     * Gets the concept expression.
     *
     * @return the concept expression
     */
    ConceptExpression getConceptExpression();

    /**
     * Sets the concept expression.
     *
     * @param conceptExpression the new concept expression
     */
    void setConceptExpression(ConceptExpression conceptExpression);

    /**
     * Generate expression.
     *
     * @param concept the concept
     * @return the concept expression
     */
    ConceptExpression generateExpression(SnomedConcept concept);

    /**
     * Gets the defining relationship property expressions.
     *
     * @return the defining relationship property expressions
     */
    Collection<SnomedRelationshipPropertyExpression> getDefiningRelationshipPropertyExpressions();

    /**
     * Sets the defining relationship property expressions.
     *
     * @param definingRelationshipPropertyExpressions the new defining relationship property expressions
     */
    void setDefiningRelationshipPropertyExpressions(Collection<SnomedRelationshipPropertyExpression> definingRelationshipPropertyExpressions);

    /**
     * Gets the qualifier relationship property expressions.
     *
     * @return the qualifier relationship property expressions
     */
    Collection<SnomedRelationshipPropertyExpression> getQualifierRelationshipPropertyExpressions();

    /**
     * Sets the qualifier relationship property expressions.
     *
     * @param qualifierRelationshipPropertyExpressions the new qualifier relationship property expressions
     */
    void setQualifierRelationshipPropertyExpressions(Collection<SnomedRelationshipPropertyExpression> qualifierRelationshipPropertyExpressions);

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
    void setRoleGroupExpressions(Set<SnomedRoleGroupExpression> roleGroupExpressions);
}
