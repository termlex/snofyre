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
package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.IntersectionExpression;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link uk.nhs.cfh.dsp.snomed.expression.model.IntersectionExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 3:31:14 PM
 */

@Embeddable
@Entity(name = "IntersectionExpression")
@Table(name = "INTERSECTION_EXPRESSIONS")
public class IntersectionExpressionImpl extends AbstractExpressionImpl implements IntersectionExpression {

    /**
     * Instantiates a new intersection expression impl.
     *
     * @param containedExpressions the contained expressions
     */
    public IntersectionExpressionImpl(Collection<Expression> containedExpressions) {
        this(UUID.randomUUID(), containedExpressions);
    }

    /**
     * Instantiates a new intersection expression impl.
     */
    public IntersectionExpressionImpl() {
        this(new HashSet<Expression>());
    }

    /**
     * Instantiates a new intersection expression impl.
     *
     * @param uuid the uuid
     * @param containedExpressions the contained expressions
     */
    public IntersectionExpressionImpl(UUID uuid, Collection<Expression> containedExpressions) {
        super(uuid, containedExpressions);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.IntersectionExpression#getContainedExpressions()
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AbstractExpressionImpl.class)
    @JoinTable(
            name="CONTAINED_EXPRESSIONS",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="contained_expression_id"))
    public Collection<Expression> getContainedExpressions() {
        return super.getChildExpressions();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.IntersectionExpression#setContainedExpressions(java.util.Collection)
     */
    public void setContainedExpressions(Collection<Expression> containedExpressions) {
        super.setChildExpressions(containedExpressions);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.IntersectionExpression#addContainedExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Transient
    public void addContainedExpression(Expression expression){
        super.addChildExpression(expression);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.IntersectionExpression#removeContainedExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Transient
    public void removeContainedExpression(Expression expression){
        super.removeChildExpression(expression);
    }
}