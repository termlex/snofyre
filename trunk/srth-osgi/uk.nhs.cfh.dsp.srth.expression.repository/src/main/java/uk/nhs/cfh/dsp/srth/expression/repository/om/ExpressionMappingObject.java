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
package uk.nhs.cfh.dsp.srth.expression.repository.om;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a object that represents a collection of a.
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression}, its corresponding
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression} with and without
 * Situation context.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 11, 2010 at 10:48:47 AM
 */
public interface ExpressionMappingObject {

    /**
     * Gets the close to user expression uuid.
     *
     * @return the close to user expression uuid
     */
    UUID getCloseToUserExpressionUuid();

    /**
     * Sets the close to user expression uuid.
     *
     * @param closeToUserExpressionUuid the new close to user expression uuid
     */
    void setCloseToUserExpressionUuid(UUID closeToUserExpressionUuid);

    /**
     * Gets the normal form expression uuid.
     *
     * @return the normal form expression uuid
     */
    UUID getNormalFormExpressionUuid();

    /**
     * Sets the normal form expression uuid.
     *
     * @param normalFormExpressionUuid the new normal form expression uuid
     */
    void setNormalFormExpressionUuid(UUID normalFormExpressionUuid);

    /**
     * Gets the situation normal form expression uuid.
     *
     * @return the situation normal form expression uuid
     */
    UUID getSituationNormalFormExpressionUuid();

    /**
     * Sets the situation normal form expression uuid.
     *
     * @param situationNormalFormExpressionUuid the new situation normal form expression uuid
     */
    void setSituationNormalFormExpressionUuid(UUID situationNormalFormExpressionUuid);

    /**
     * Gets the close to user cg form.
     *
     * @return the close to user cg form
     */
    String getCloseToUserCGForm();

    /**
     * Sets the close to user cg form.
     *
     * @param closeToUserCGForm the new close to user cg form
     */
    void setCloseToUserCGForm(String closeToUserCGForm);

    /**
     * Gets the normal form cg form.
     *
     * @return the normal form cg form
     */
    String getNormalFormCGForm();

    /**
     * Sets the normal form cg form.
     *
     * @param normalFormCGForm the new normal form cg form
     */
    void setNormalFormCGForm(String normalFormCGForm);

    /**
     * Gets the situation normal form cg form.
     *
     * @return the situation normal form cg form
     */
    String getSituationNormalFormCGForm();

    /**
     * Sets the situation normal form cg form.
     *
     * @param situationNormalFormCGForm the new situation normal form cg form
     */
    void setSituationNormalFormCGForm(String situationNormalFormCGForm);
}
