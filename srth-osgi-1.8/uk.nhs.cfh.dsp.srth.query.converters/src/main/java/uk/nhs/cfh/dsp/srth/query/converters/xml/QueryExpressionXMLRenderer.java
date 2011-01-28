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
import uk.nhs.cfh.dsp.srth.query.model.om.QueryIntersectionExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryUnionExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintValue;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that renders a.
 *
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression} as an
 * {@link org.jdom.Element}
 * 
 * <br>
 * @deprecated Use {@link uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter} instead
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 1:08:44 AM
 */
public interface QueryExpressionXMLRenderer {

    /**
     * Gets the as xml element.
     *
     * @param queryExpression the query expression
     *
     * @return the as xml element
     */
    Element getAsXMLElement(QueryExpression queryExpression);

    /**
     * Gets the as xml element.
     *
     * @param queryStatement the query statement
     *
     * @return the as xml element
     */
    Element getAsXMLElement(QueryStatement queryStatement);

    /**
     * Gets the as xml element.
     *
     * @param unionObject the union object
     *
     * @return the as xml element
     */
    Element getAsXMLElement(QueryUnionExpression unionObject);

    /**
     * Gets the as xml element.
     *
     * @param intersectionObject the intersection object
     *
     * @return the as xml element
     */
    Element getAsXMLElement(QueryIntersectionExpression intersectionObject);

//    /**
//     * Gets the as xml element.
//     *
//     * @param subQuery the sub query
//     *
//     * @return the as xml element
//     */
//    Element getAsXMLElement(SubQueryExpressionImpl subQuery);
//
//    /**
//     * Gets the as xml element.
//     *
//     * @param queryTerm the query term
//     *
//     * @return the as xml element
//     */
//    Element getAsXMLElement(SnomedQueryTerm queryTerm);

    /**
     * Gets the as xml element.
     *
     * @param constraint the constraint
     *
     * @return the as xml element
     */
    Element getAsXMLElement(Constraint constraint);

//    Element getAsXMLElement(MappedConstraintName mappedConstraintName);

    /**
     * Gets the as xml element.
     *
     * @param value the value
     *
     * @return the as xml element
     */
    Element getAsXMLElement(ConstraintValue<?> value);
}
