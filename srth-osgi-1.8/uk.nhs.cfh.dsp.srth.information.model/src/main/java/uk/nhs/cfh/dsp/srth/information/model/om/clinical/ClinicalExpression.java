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
package uk.nhs.cfh.dsp.srth.information.model.om.clinical;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an object that represents an entry
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 11:38:36 AM.
 */
public interface ClinicalExpression {

    /**
     * Gets the canonical form.
     * 
     * @return the canonical form
     */
    String getCompositionalGrammarForm();

    /**
     * Sets the canonical form.
     * 
     * @param canonicalForm the new canonical form
     */
    void setCompositionalGrammarForm(String canonicalForm);

    /**
     * Gets the uuid.
     * 
     * @return the uuid
     */
    UUID getUuid();

    /**
     * Sets the uuid.
     * 
     * @param uuid the new uuid
     */
    void setUuid(UUID uuid);

    /**
     * Gets the expression.
     *
     * @return the expression
     */
    Expression getExpression();

    /**
     * Sets the expression.
     *
     * @param expression the new expression
     */
    void setExpression(Expression expression);

    /**
     * Gets the normal form expression.
     *
     * @return the normal form expression
     */
    NormalFormExpression getNormalFormExpression();

    /**
     * Sets the normal form expression.
     *
     * @param normalFormExpression the new normal form expression
     */
    void setNormalFormExpression(NormalFormExpression normalFormExpression);

    /**
     * Gets the compositional grammar normal form.
     *
     * @return the compositional grammar normal form
     */
    String getCompositionalGrammarNormalForm();

    /**
     * Sets the compositional grammar normal form.
     *
     * @param canonicalNormalForm the new compositional grammar normal form
     */
    void setCompositionalGrammarNormalForm(String canonicalNormalForm);

    /**
     * Gets the normal form uuid.
     *
     * @return the normal form uuid
     */
    UUID getNormalFormUuid();

    /**
     * Sets the normal form uuid.
     *
     * @param normalFormUuid the new normal form uuid
     */
    void setNormalFormUuid(UUID normalFormUuid);

    /**
     * Gets the situation normal form uuid.
     *
     * @return the situation normal form uuid
     */
    UUID getSituationNormalFormUuid();

    /**
     * Sets the situation normal form uuid.
     *
     * @param situationNormalFormUuid the new situation normal form uuid
     */
    void setSituationNormalFormUuid(UUID situationNormalFormUuid);

    /**
     * Gets the compositional situation normal form.
     *
     * @return the compositional situation normal form
     */
    String getCompositionalSituationNormalForm();

    /**
     * Sets the compositional situation normal form.
     *
     * @param compositionalSituationNormalForm the new compositional situation normal form
     */
    void setCompositionalSituationNormalForm(String compositionalSituationNormalForm);
}

