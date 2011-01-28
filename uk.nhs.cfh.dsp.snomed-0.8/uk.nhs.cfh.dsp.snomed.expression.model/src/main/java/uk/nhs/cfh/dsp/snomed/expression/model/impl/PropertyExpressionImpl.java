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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.Property;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;


// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Friday; October 2, 2009 2:25:21 PM
 * <br> Modified on Wednesday; December 9, 2009 at 12:40 PM to enable OSGI
 * <br> Modified on Wednesday; December 16, 2009 at 3:49 PM to handle recursive expressions
 */

@Embeddable
@Entity(name = "PropertyExpression")
@Table(name = "PROPERTY_EXPRESSIONS")
public class PropertyExpressionImpl extends AbstractPropertyExpressionImpl implements PropertyExpression {

    /**
     * Instantiates a new property expression impl.
     *
     * @param operator the operator
     * @param property the property
     * @param expression the expression
     */
    public PropertyExpressionImpl(Logical_Operator operator, Property property, Expression expression) {
        super(property, expression, operator);
    }

    /**
     * Instantiates a new property expression impl.
     *
     * @param property the property
     * @param expression the expression
     */
    public PropertyExpressionImpl(Property property, Expression expression) {
        this(Logical_Operator.SOME, property, expression);
    }

    /**
     * Instantiates a new property expression impl.
     *
     * @param operator the operator
     * @param property the property
     */
    public PropertyExpressionImpl(Logical_Operator operator, Property property) {
        this(operator, property, null);
    }

    /**
     * Instantiates a new property expression impl.
     *
     * @param property the property
     */
    public PropertyExpressionImpl(Property property) {
        this(Logical_Operator.SOME, property);
    }

    /**
     * Instantiates a new property expression impl.
     */
    public PropertyExpressionImpl() {
    }
}