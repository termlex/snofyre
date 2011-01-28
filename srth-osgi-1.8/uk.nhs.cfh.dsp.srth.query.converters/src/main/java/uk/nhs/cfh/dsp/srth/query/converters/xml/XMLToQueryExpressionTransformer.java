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
import org.jdom.JDOMException;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintValue;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that transforms a given {@link org.jdom.Element}
 * into a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression} or any of its associated
 * entities.
 *
 * <br>
 * @deprecated Use {@link uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter} instead
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 12:13:56 PM
 */
public interface XMLToQueryExpressionTransformer {

//    /**
//     * Gets the query term.
//     *
//     * @param element the element
//     *
//     * @return the query term
//     *
//     * @throws JDOMException the JDOM exception
//     */
//    SnomedQueryTerm getQueryTerm(Element element) throws JDOMException;

    /**
     * Gets the query statement.
     * 
     * @param element the element
     * 
     * @return the query statement
     */
    QueryStatement getQueryStatement(Element element);

    /**
     * Gets the reporting query expression.
     * 
     * @param parent the parent
     * @param element the element
     * 
     * @return the reporting query expression
     */
    QueryExpression getReportingQueryExpression(QueryExpression parent, Element element);

//    /**
//     * Gets the reporting sub query.
//     *
//     * @param subQueryElement the sub query element
//     *
//     * @return the reporting sub query
//     *
//     * @throws JDOMException the JDOM exception
//     */
//    SubQueryExpressionImpl getReportingSubQuery(Element subQueryElement) throws JDOMException;
//
//    /**
//     * Gets the additional constraint.
//     *
//     * @param element the element
//     *
//     * @return the additional constraint
//     */
//    AdditionalConstraint getAdditionalConstraint(Element element);

    /**
     * Gets the constraint.
     * 
     * @param element the element
     * 
     * @return the constraint
     * 
     * @throws JDOMException the JDOM exception
     */
    Constraint getConstraint(Element element) throws JDOMException;

//    MappedConstraintName getMappedConstraintName(Element element);

    /**
 * Gets the constraint value.
 * 
 * @param element the element
 * 
 * @return the constraint value
 */
ConstraintValue<?> getConstraintValue(Element element);
}
