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
package uk.nhs.cfh.dsp.snomed.normaliser;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that generates a SNOMED CT Normal Form for a given.
 *
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} , or a
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 19, 2009 at 1:56:24 PM
 */
public interface NormalFormGenerator {

    /**
     * Gets the short normal form expression.
     *
     * @param concept the concept
     * @return the short normal form expression
     */
    NormalFormExpression getShortNormalFormExpression(SnomedConcept concept);

    /**
     * Gets the long normal form expression.
     *
     * @param concept the concept
     * @return the long normal form expression
     */
    NormalFormExpression getLongNormalFormExpression(SnomedConcept concept);

    /**
     * Gets the expression with long normal form as value.
     *
     * @param roleGroup the role group
     * @return the expression with long normal form as value
     */
    Expression getExpressionWithLongNormalFormAsValue(SnomedRoleGroup roleGroup);

    /**
     * Gets the expression with long normal form as value.
     *
     * @param relationship the relationship
     * @return the expression with long normal form as value
     */
    Expression getExpressionWithLongNormalFormAsValue(SnomedRelationship relationship);

    /**
     * Gets the expression with short normal form as value.
     *
     * @param roleGroup the role group
     * @return the expression with short normal form as value
     */
    Expression getExpressionWithShortNormalFormAsValue(SnomedRoleGroup roleGroup);

    /**
     * Gets the expression with short normal form as value.
     *
     * @param relationship the relationship
     * @return the expression with short normal form as value
     */
    Expression getExpressionWithShortNormalFormAsValue(SnomedRelationship relationship);

    /**
     * Gets the long normal form expression.
     *
     * @param conceptId the concept id
     * @return the long normal form expression
     */
    NormalFormExpression getLongNormalFormExpression(String conceptId);

    /**
     * Gets the long normal form expression.
     *
     * @param closeToUserExpression the close to user expression
     * @return the long normal form expression
     */
    NormalFormExpression getLongNormalFormExpression(CloseToUserExpression closeToUserExpression);

    /**
     * Gets the short normal form expression.
     *
     * @param closeToUserExpression the close to user expression
     * @return the short normal form expression
     */
    NormalFormExpression getShortNormalFormExpression(CloseToUserExpression closeToUserExpression);

    /**
     * Gets the short normal form expression.
     *
     * @param longNormalFormExpression the long normal form expression
     * @return the short normal form expression
     */
    NormalFormExpression getShortNormalFormExpression(NormalFormExpression longNormalFormExpression);

    /**
     * Gets the long normal form expression for rendering.
     *
     * @param concept the concept
     * @return the long normal form expression for rendering
     */
    NormalFormExpression getLongNormalFormExpressionForRendering(SnomedConcept concept);

    /**
     * Gets the long normal form expression for rendering.
     *
     * @param closeToUserExpression the close to user expression
     * @return the long normal form expression for rendering
     */
    NormalFormExpression getLongNormalFormExpressionForRendering(CloseToUserExpression closeToUserExpression);
}
