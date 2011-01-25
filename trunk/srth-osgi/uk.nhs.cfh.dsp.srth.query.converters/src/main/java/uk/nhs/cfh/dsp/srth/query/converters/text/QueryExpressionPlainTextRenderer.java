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
package uk.nhs.cfh.dsp.srth.query.converters.text;

import uk.nhs.cfh.dsp.srth.query.model.om.*;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that renders
 * a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression} as human friendly plain text.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 11:17:04 AM
 */
public interface QueryExpressionPlainTextRenderer {

    /**
     * Gets the plain text human readable label for expression.
     * 
     * @param expression the expression
     * 
     * @return the plain text human readable label for expression
     */
    String getPlainTextHumanReadableLabelForExpression(QueryExpression expression);

    /**
     * Gets the plain text human readable label for expression.
     * 
     * @param queryStatement the query statement
     * 
     * @return the plain text human readable label for expression
     */
    String getPlainTextHumanReadableLabelForExpression(QueryStatement queryStatement);

//    /**
//     * Gets the plain text human readable label for expression.
//     *
//     * @param subQuery the sub query
//     *
//     * @return the plain text human readable label for expression
//     */
//    String getPlainTextHumanReadableLabelForExpression(SubQueryExpressionImpl subQuery);

    /**
     * Gets the plain text human readable label for expression.
     * 
     * @param intersectionObject the intersection object
     * 
     * @return the plain text human readable label for expression
     */
    String getPlainTextHumanReadableLabelForExpression(QueryIntersectionExpression intersectionObject);

    /**
     * Gets the plain text human readable label for expression.
     * 
     * @param unionObject the union object
     * 
     * @return the plain text human readable label for expression
     */
    String getPlainTextHumanReadableLabelForExpression(QueryUnionExpression unionObject);

//    /**
//     * Gets the plain text human readable label for expression.
//     *
//     * @param queryTerm the query term
//     *
//     * @return the plain text human readable label for expression
//     */
//    String getPlainTextHumanReadableLabelForExpression(SnomedQueryTerm queryTerm);
}
