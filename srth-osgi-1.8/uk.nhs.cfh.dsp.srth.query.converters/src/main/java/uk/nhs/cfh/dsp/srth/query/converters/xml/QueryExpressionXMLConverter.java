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
package uk.nhs.cfh.dsp.srth.query.converters.xml;

import org.jdom.Element;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an object that marshalls and unmarshalls.
 *
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression} objects to and from XML.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 3, 2010 at 2:56:49 PM
 */
public interface QueryExpressionXMLConverter {

    /**
     * Gets the element for query expression.
     *
     * @param queryExpression the query expression
     * @return the element for query expression
     */
    Element getElementForQueryExpression(QueryExpression queryExpression);

    /**
     * Gets the element for query statement.
     *
     * @param queryStatement the query statement
     * @return the element for query statement
     */
    Element getElementForQueryStatement(QueryStatement queryStatement);

    /**
     * Gets the element for constraint.
     *
     * @param constraint the constraint
     * @return the element for constraint
     */
    Element getElementForConstraint(Constraint constraint);

    /**
     * Gets the expression from element.
     *
     * @param element the element
     * @return the expression from element
     */
    QueryExpression getExpressionFromElement(Element element);

    /**
     * Gets the query statement from element.
     *
     * @param element the element
     * @return the query statement from element
     */
    QueryStatement getQueryStatementFromElement(Element element);

    /**
     * Gets the constraint from element.
     *
     * @param element the element
     * @return the constraint from element
     */
    Constraint getConstraintFromElement(Element element);
}
