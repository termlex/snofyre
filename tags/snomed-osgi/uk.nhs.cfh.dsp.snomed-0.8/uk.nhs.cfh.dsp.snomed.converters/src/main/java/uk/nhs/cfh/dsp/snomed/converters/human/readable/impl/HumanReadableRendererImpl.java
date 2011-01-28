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
package uk.nhs.cfh.dsp.snomed.converters.human.readable.impl;

import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 11:06:02 PM
 */
public class HumanReadableRendererImpl extends AbstractHumanReadableRenderer implements HumanReadableRender{
    
    /**
     * No args empty constructor for IOC.
     */
    public HumanReadableRendererImpl() {
        // empty constructor
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.impl.AbstractHumanReadableRenderer#getHumanReadableLabel(uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression)
     */
    @Override
    protected synchronized String getHumanReadableLabel(CloseToUserExpression closeToUserExpression){

        if(closeToUserExpression != null)
        {
            String text = "";
            // get rendering for focus concepts and contained refinements
             text = getTextForContainedFocusConcepts(closeToUserExpression.getFocusConcepts());

            // add text for refinements
            String refinementsText = getContainedExpressionsText(closeToUserExpression.getChildExpressions());
            if(! "".equalsIgnoreCase(refinementsText))
            {
                text = text+" WITH "+refinementsText;
            }
            return text;
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.impl.AbstractHumanReadableRenderer#getHumanReadableLabel(uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression)
     */
    @Override
    protected synchronized String getHumanReadableLabel(NormalFormExpression normalFormExpression){

        if(normalFormExpression != null)
        {
            String text = "";
            // get rendering for focus concepts and contained refinements
             text = getTextForContainedFocusConcepts(normalFormExpression.getFocusConcepts());

            // add text for refinements
            String refinementsText = getContainedExpressionsText(normalFormExpression.getChildExpressions());
            if(! "".equalsIgnoreCase(refinementsText))
            {
                text = text+" WITH "+refinementsText;
            }
            return text;
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.impl.AbstractHumanReadableRenderer#getHumanReadableLabel(uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression)
     */
    @Override
    protected synchronized String getHumanReadableLabel(SnomedRelationshipPropertyExpression relationshipPropertyExpression){

        if(relationshipPropertyExpression != null)
        {
            String text = "";
            SnomedRelationship relationship = relationshipPropertyExpression.getRelationship();
            if (relationship != null)
            {
                // get attribute name and rendering for contained expression
                String attributeName = relationship.getName();
                String attributeId = relationship.getRelationshipType();

                if(attributeName != null)
                {
                    text = attributeName;
                }

                // render concept ids if set
                if(isRenderConceptIds() && attributeId != null)
                {
                    text = attributeId+"|"+text+"|";
                }

                /**
                 * add filler value of relationships, after checking if contained expression is to be recursively
                 * expanded and rendered.
                 */
                String containedExpressionText = null;

                if (isRecursivelyRenderPropertyExpressions())
                {
                    // get contained expression and add human readable rendering of expression
                    containedExpressionText = getHumanReadableLabel(relationshipPropertyExpression.getExpression());
                }
                else
                {
                    SnomedConcept targetConcept = relationship.getTargetConcept();
                    if(targetConcept != null)
                    {
                        containedExpressionText = getHumanReadableLabel(targetConcept);
                    }
                }
                if(containedExpressionText != null)
                {
                    text = text+" OF "+containedExpressionText;
                }
            }
            
            return text;

        }
        else
        {
            throw new IllegalArgumentException("Property expression passed can not be null.");
        }
    }

}
