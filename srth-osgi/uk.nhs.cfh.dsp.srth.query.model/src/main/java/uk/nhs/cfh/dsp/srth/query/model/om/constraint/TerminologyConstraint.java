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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A type of {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint} that uses a
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} or a
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression} as its value.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 12:50:09 PM
 */
public interface TerminologyConstraint extends Constraint {

    /**
     * Gets the subsumption.
     * 
     * @return the subsumption
     */
    SubsumptionVocabulary getSubsumption();

    /**
     * Sets the subsumption.
     * 
     * @param subsumption the new subsumption
     */
    void setSubsumption(SubsumptionVocabulary subsumption);

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
     * Gets the compositional grammar form.
     *
     * @return the compositional grammar form
     */
    String getCompositionalGrammarForm();

    /**
     * Sets the compositional grammar form.
     *
     * @param compositionalGrammarForm the new compositional grammar form
     */
    void setCompositionalGrammarForm(String compositionalGrammarForm);
}
