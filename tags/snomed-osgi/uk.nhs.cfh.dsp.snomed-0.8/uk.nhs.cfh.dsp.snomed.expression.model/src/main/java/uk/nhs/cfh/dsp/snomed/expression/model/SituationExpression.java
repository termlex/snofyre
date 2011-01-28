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
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression} that represents the notion of a
 * SNOMED CT Situation with explicit context.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 27, 2010 at 11:41:33 PM
 */
public interface SituationExpression extends Expression{
    
    /**
     * Gets the subject relationship context expression.
     *
     * @return the subject relationship context expression
     */
    SnomedRelationshipPropertyExpression getSubjectRelationshipContextExpression();

    /**
     * Sets the subject relationship context expression.
     *
     * @param subjectRelationshipContextExpression the new subject relationship context expression
     */
    void setSubjectRelationshipContextExpression(SnomedRelationshipPropertyExpression subjectRelationshipContextExpression);

    /**
     * Gets the temporal context expression.
     *
     * @return the temporal context expression
     */
    SnomedRelationshipPropertyExpression getTemporalContextExpression();

    /**
     * Sets the temporal context expression.
     *
     * @param temporalContextExpression the new temporal context expression
     */
    void setTemporalContextExpression(SnomedRelationshipPropertyExpression temporalContextExpression);

    /**
     * Gets the associated context expression.
     *
     * @return the associated context expression
     */
    SnomedRelationshipPropertyExpression getAssociatedContextExpression();

    /**
     * Sets the associated context expression.
     *
     * @param associatedContextExpression the new associated context expression
     */
    void setAssociatedContextExpression(SnomedRelationshipPropertyExpression associatedContextExpression);

    /**
     * Gets the focus expression.
     *
     * @return the focus expression
     */
    SnomedRelationshipPropertyExpression getFocusExpression();

    /**
     * Sets the focus expression.
     *
     * @param focusExpression the new focus expression
     */
    void setFocusExpression(SnomedRelationshipPropertyExpression focusExpression);

    /**
     * Gets the subject relationship.
     *
     * @return the subject relationship
     */
    SnomedConcept getSubjectRelationship();

    /**
     * Gets the temporal context.
     *
     * @return the temporal context
     */
    SnomedConcept getTemporalContext();

    /**
     * Gets the associated context.
     *
     * @return the associated context
     */
    SnomedConcept getAssociatedContext();

    /**
     * Gets the focus concept.
     *
     * @return the focus concept
     */
    SnomedConcept getFocusConcept();

    /**
     * Gets the permitted types.
     *
     * @return the permitted types
     */
    Collection<ConceptType> getPermittedTypes();

    /**
     * Sets the focus concept.
     *
     * @param focusConcept the new focus concept
     */
    void setFocusConcept(SnomedConcept focusConcept);
}
