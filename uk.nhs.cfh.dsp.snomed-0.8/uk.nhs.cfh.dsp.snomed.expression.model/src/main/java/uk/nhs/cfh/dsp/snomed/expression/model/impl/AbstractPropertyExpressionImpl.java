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
import uk.nhs.cfh.dsp.snomed.expression.model.Property;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;

import javax.persistence.*;
import java.util.HashSet;
import java.util.UUID;


// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 4:57:43 PM
 */

@Embeddable
@MappedSuperclass
@Entity(name = "AbstractPropertyExpression")
public abstract class AbstractPropertyExpressionImpl extends AbstractExpressionImpl implements PropertyExpression {

    /** The property. */
    private Property property;
    
    /** The expression. */
    private Expression expression;
    
    /** The operator. */
    private Expression.Logical_Operator operator = Logical_Operator.SOME;

    /**
     * Instantiates a new abstract property expression impl.
     *
     * @param property the property
     * @param expression the expression
     * @param operator the operator
     */
    public AbstractPropertyExpressionImpl(Property property, Expression expression, Logical_Operator operator) {
        this(UUID.randomUUID(), property, expression, operator);
    }

    /**
     * Instantiates a new abstract property expression impl.
     *
     * @param uuid the uuid
     * @param property the property
     * @param expression the expression
     * @param operator the operator
     */
    public AbstractPropertyExpressionImpl(UUID uuid, Property property,
                                          Expression expression, Logical_Operator operator) {
        super(uuid, new HashSet<Expression>());
        this.property = property;
        this.expression = expression;
        this.operator = operator;
    }

    /**
     * Instantiates a new abstract property expression impl.
     *
     * @param uuid the uuid
     * @param property the property
     */
    protected AbstractPropertyExpressionImpl(UUID uuid, Property property) {
        this(uuid, property, null, Logical_Operator.SOME);
    }

    /**
     * Instantiates a new abstract property expression impl.
     *
     * @param property the property
     * @param expression the expression
     */
    public AbstractPropertyExpressionImpl(Property property, Expression expression) {
        this(property, expression, Logical_Operator.SOME);
    }

    /**
     * Instantiates a new abstract property expression impl.
     *
     * @param property the property
     * @param operator the operator
     */
    protected AbstractPropertyExpressionImpl(Property property, Logical_Operator operator) {
        this(property, null, operator);
    }

    /**
     * Instantiates a new abstract property expression impl.
     *
     * @param property the property
     */
    protected AbstractPropertyExpressionImpl(Property property) {
        this(property, Logical_Operator.SOME);
    }

    /**
     * Instantiates a new abstract property expression impl.
     */
    public AbstractPropertyExpressionImpl() {
        
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression#getProperty()
     */
    @OneToOne(cascade =  CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AbstractPropertyImpl.class)
    public Property getProperty() {
        return property;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression#setProperty(uk.nhs.cfh.dsp.snomed.expression.model.Property)
     */
    public void setProperty(Property property) {
        this.property = property;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression#getExpression()
     */
    @OneToOne(cascade =  CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AbstractExpressionImpl.class)
    public Expression getExpression() {
        return expression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression#setExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression#getOperator()
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "operator")
    public Logical_Operator getOperator() {
        return operator;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression#setOperator(Expression.Logical_Operator)
     */
    public void setOperator(Logical_Operator operator) {
        this.operator = operator;
    }
}