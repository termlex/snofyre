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
package uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a converter that transforms a given.
 *
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression} or a
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression} into its compositional grammar form
 * or reconstitutes the expressions from a given {@link String} form.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 8, 2010 at 11:24:28 AM
 */
public interface ExpressionCompositionalGrammarConverter {

    /**
     * Gets the normal form.
     *
     * @param input the input
     * @return the normal form
     */
    NormalFormExpression getNormalForm(String input);

    /**
     * Gets the close to user form.
     *
     * @param input the input
     * @return the close to user form
     */
    CloseToUserExpression getCloseToUserForm(String input);

    /**
     * Gets the compositional form.
     *
     * @param expression the expression
     * @return the compositional form
     */
    String getCompositionalForm(Expression expression);
}
