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
package uk.nhs.cfh.dsp.snomed.converters.xml;

import org.jdom.Element;
import uk.nhs.cfh.dsp.snomed.expression.model.*;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an object that converts {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression}
 * objects into {@link org.jdom.Element}s.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 3, 2010 at 11:59:14 AM
 */
public interface ExpressionXMLConverter {


    /**
     * Gets the element for close to user form.
     *
     * @param closeToUserExpression the close to user expression
     * @return the element for close to user form
     */
    Element getElementForCloseToUserForm(CloseToUserExpression closeToUserExpression);

    /**
     * Gets the element for normal form.
     *
     * @param normalFormExpression the normal form expression
     * @return the element for normal form
     */
    Element getElementForNormalForm(NormalFormExpression normalFormExpression);

    /**
     * Gets the element for property expression.
     *
     * @param propertyExpression the property expression
     * @return the element for property expression
     */
    Element getElementForPropertyExpression(PropertyExpression propertyExpression);

    /**
     * Gets the element for concept expression.
     *
     * @param conceptExpression the concept expression
     * @return the element for concept expression
     */
    Element getElementForConceptExpression(ConceptExpression conceptExpression);

    /**
     * Gets the element for intersection expression.
     *
     * @param intersectionExpression the intersection expression
     * @return the element for intersection expression
     */
    Element getElementForIntersectionExpression(IntersectionExpression intersectionExpression);

    /**
     * Gets the element for union expression.
     *
     * @param unionExpression the union expression
     * @return the element for union expression
     */
    Element getElementForUnionExpression(UnionExpression unionExpression);

    /**
     * Gets the normal form expression from xml.
     *
     * @param element the element
     * @return the normal form expression from xml
     */
    NormalFormExpression getNormalFormExpressionFromXML(Element element);

    /**
     * Gets the close to user expression.
     *
     * @param element the element
     * @return the close to user expression
     */
    CloseToUserExpression getCloseToUserExpression(Element element);
}
