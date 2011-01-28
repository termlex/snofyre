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

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An object representation of a logical union of {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression}s.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 3:42:51 PM
 */
public interface UnionExpression extends Expression{

    /**
     * Gets the contained expressions.
     *
     * @return the contained expressions
     */
    Collection<Expression> getContainedExpressions();

    /**
     * Sets the contained expressions.
     *
     * @param containedExpressions the new contained expressions
     */
    void setContainedExpressions(Collection<Expression> containedExpressions);

    /**
     * Adds the contained expression.
     *
     * @param expression the expression
     */
    void addContainedExpression(Expression expression);

    /**
     * Removes the contained expression.
     *
     * @param expression the expression
     */
    void removeContainedExpression(Expression expression);
}