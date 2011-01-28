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

import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

// TODO: Auto-generated Javadoc
/**
 * An type of {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression} that wraps a
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl} as an
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 3:18:56 PM
 */
public interface ConceptExpression extends Expression{

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
     * Gets the concept id.
     *
     * @return the concept id
     */
    String getConceptId();

    /**
     * Sets the concept id.
     *
     * @param conceptId the new concept id
     */
    void setConceptId(String conceptId);
}