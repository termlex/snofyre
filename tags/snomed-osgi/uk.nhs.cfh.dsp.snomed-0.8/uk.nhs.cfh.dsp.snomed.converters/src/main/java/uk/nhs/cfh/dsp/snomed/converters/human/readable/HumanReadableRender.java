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
package uk.nhs.cfh.dsp.snomed.converters.human.readable;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

// TODO: Auto-generated Javadoc
/**
 * A interface specification for a class that renders a human readable form of a {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 11:55:47 PM
 */
public interface HumanReadableRender {

    /**
     * Gets the human readable label.
     *
     * @param expression the expression
     * @return the human readable label
     */
    String getHumanReadableLabel(Expression expression);

    /**
     * Gets the human readable label.
     *
     * @param concept the concept
     * @return the human readable label
     */
    String getHumanReadableLabel(SnomedConcept concept);

    /**
     * Checks if is render concept ids.
     *
     * @return true, if is render concept ids
     */
    boolean isRenderConceptIds();

    /**
     * Sets the render concept ids.
     *
     * @param renderConceptIds the new render concept ids
     */
    void setRenderConceptIds(boolean renderConceptIds);

    /**
     * Checks if is prefer fsn.
     *
     * @return true, if is prefer fsn
     */
    boolean isPreferFSN();

    /**
     * Sets the prefer fsn.
     *
     * @param preferFSN the new prefer fsn
     */
    void setPreferFSN(boolean preferFSN);

    /**
     * Checks if is recursively render property expressions.
     *
     * @return true, if is recursively render property expressions
     */
    boolean isRecursivelyRenderPropertyExpressions();

    /**
     * Sets the recursively render property expressions.
     *
     * @param recursivelyRenderPropertyExpressions the new recursively render property expressions
     */
    void setRecursivelyRenderPropertyExpressions(boolean recursivelyRenderPropertyExpressions);
}
