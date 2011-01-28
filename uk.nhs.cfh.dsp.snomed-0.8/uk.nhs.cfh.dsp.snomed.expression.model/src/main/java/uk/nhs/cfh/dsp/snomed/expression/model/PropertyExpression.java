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

package uk.nhs.cfh.dsp.snomed.expression.model;

import uk.nhs.cfh.dsp.snomed.objectmodel.CanonicalRepresentableElement;


// TODO: Auto-generated Javadoc
/**
 * An object representation of an Attribute-Value pair... The attribute can be any
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.Property} and the value can be any
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Friday; October 2, 2009 2:24:09 PM
 * <br> Modified on Wednesday; December 9, 2009 at 12:37 PM to enable OSGI
 * <br> Modified on Wednesday; December 16, 2009 at 3:48 PM to handle recursive expressions
 */

public interface PropertyExpression extends Expression{

    /**
     * Gets the expression.
     *
     * @return the expression
     */
    Expression getExpression();

    /**
     * Gets the operator.
     *
     * @return the operator
     */
    Expression.Logical_Operator getOperator();

    /**
     * Gets the property.
     *
     * @return the property
     */
    Property getProperty();

    /**
     * Sets the expression.
     *
     * @param expression the new expression
     */
    void setExpression(Expression expression);

    /**
     * Sets the operator.
     *
     * @param operator the new operator
     */
    void setOperator(Expression.Logical_Operator operator);

    /**
     * Sets the property.
     *
     * @param property the new property
     */
    void setProperty(Property property);
}