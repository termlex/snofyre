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
package uk.nhs.cfh.dsp.snomed.converters.normalform.tree.impl;

import uk.nhs.cfh.dsp.snomed.converters.normalform.tree.RefinableNormalFormExpressionTreeFormProvider;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.ConceptExpressionImpl;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of  a{@link uk.nhs.cfh.dsp.snomed.converters.normalform.tree.RefinableNormalFormExpressionTreeFormProvider}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 13, 2010 at 1:40:27 PM
 */
public class RefinableNormalFormExpressionTreeFormProviderImpl implements RefinableNormalFormExpressionTreeFormProvider {

    /** The close to user expression. */
    private CloseToUserExpression closeToUserExpression;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;


    /**
     * Gets the refinable expression form.
     *
     * @param expression the expression
     * @param normalFormExpression the normal form expression
     * @return the refinable expression form
     */
    public synchronized void getRefinableExpressionForm(CloseToUserExpression expression, NormalFormExpression normalFormExpression){

        /*
         get the normal form of the expression and for every concept in the normal form, we add the refining
         relationships as a property expressions, unless they have been refined
         */

        if (normalFormExpression == null)
        {
            normalFormExpression = normalFormGenerator.getLongNormalFormExpression(closeToUserExpression);
        }

        List<SnomedConcept> focusConcepts = new ArrayList<SnomedConcept>(closeToUserExpression.getFocusConcepts());
        SnomedConcept focusConcept = focusConcepts.get(0);

        // create a conceptExpression using focus concept
        ConceptExpression focusConceptExpression = new ConceptExpressionImpl(focusConcept);

    }

    /**
     * Adds the refining properties for expression.
     *
     * @param normalFormExpression the normal form expression
     */
    private synchronized void addRefiningPropertiesForExpression(NormalFormExpression normalFormExpression){

    }

}
