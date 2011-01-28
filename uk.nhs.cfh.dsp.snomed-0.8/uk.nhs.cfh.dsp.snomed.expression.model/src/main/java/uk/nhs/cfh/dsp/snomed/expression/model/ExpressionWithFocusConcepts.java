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

import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an expression that contains {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}s
 * as focus concepts.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 17, 2010 at 6:12:08 PM
 */
public interface ExpressionWithFocusConcepts extends Expression{

    /**
     * Gets the focus concepts.
     *
     * @return the focus concepts
     */
    Collection<SnomedConcept> getFocusConcepts();

    /**
     * Sets the focus concepts.
     *
     * @param focusConcepts the new focus concepts
     */
    void setFocusConcepts(Collection<SnomedConcept> focusConcepts);

    /**
     * Adds the focus concept.
     *
     * @param concept the concept
     */
    void addFocusConcept(SnomedConcept concept);

    /**
     * Removes the focus concept.
     *
     * @param concept the concept
     */
    void removeFocusConcept(SnomedConcept concept);

    /**
     * Gets the compositional grammar form.
     *
     * @return the compositional grammar form
     */
    String getCompositionalGrammarForm();

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.CanonicalRepresentableElement#getCanonicalStringForm()
     */
    String getCanonicalStringForm();

    /**
     * Gets the singleton focus concept type.
     *
     * @return the singleton focus concept type
     */
    ConceptType getSingletonFocusConceptType();

    /**
     * Gets the singleton focus concept.
     *
     * @return the singleton focus concept
     */
    SnomedConcept getSingletonFocusConcept();
}
