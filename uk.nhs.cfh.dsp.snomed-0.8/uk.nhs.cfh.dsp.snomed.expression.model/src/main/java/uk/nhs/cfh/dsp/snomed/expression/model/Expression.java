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

import uk.nhs.cfh.dsp.snomed.objectmodel.CanonicalRepresentableElement;

import java.util.Collection;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface specification of an Expression which can be either a
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} or a combination of some
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship}s with a
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 3:00:25 PM
 */
public interface Expression extends CanonicalRepresentableElement, Comparable<Expression>{

    /**
     * Gets the equivalent expressions.
     *
     * @return the equivalent expressions
     */
    Collection<Expression> getEquivalentExpressions();

    /**
     * Sets the equivalent expressions.
     *
     * @param equivalentExpressions the new equivalent expressions
     */
    void setEquivalentExpressions(Collection<Expression> equivalentExpressions);

    /**
     * Adds the equivalent expression.
     *
     * @param expression the expression
     */
    void addEquivalentExpression(Expression expression);

    /**
     * Removes the equivalent expression.
     *
     * @param expression the expression
     */
    void removeEquivalentExpression(Expression expression);

    /** The Logical_ operator. */
    public enum Logical_Operator{AND, OR, SOME, ALL, MAX, MIN, EQUALS};

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
     * Gets the parent expressions.
     *
     * @return the parent expressions
     */
    Collection<Expression> getParentExpressions();

    /**
     * Sets the parent expressions.
     *
     * @param parentExpressions the new parent expressions
     */
    void setParentExpressions(Collection<Expression> parentExpressions);

    /**
     * Gets the child expressions.
     *
     * @return the child expressions
     */
    Collection<Expression> getChildExpressions();

    /**
     * Sets the child expressions.
     *
     * @param childExpressions the new child expressions
     */
    void setChildExpressions(Collection<Expression> childExpressions);

    /**
     * Adds the child expression.
     *
     * @param expression the expression
     */
    void addChildExpression(Expression expression);

    /**
     * Removes the child expression.
     *
     * @param expression the expression
     */
    void removeChildExpression(Expression expression);

    /**
     * Removes the parent expression.
     *
     * @param expression the expression
     */
    void removeParentExpression(Expression expression);

    /**
     * Adds the parent expression.
     *
     * @param expression the expression
     */
    void addParentExpression(Expression expression);
}