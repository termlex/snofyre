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
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 27, 2010 at 10:41:21 PM.
 */
public abstract class AbstractHumanReadableRenderer implements HumanReadableRender {

    /** The render concept ids. */
    private boolean renderConceptIds = false;
    
    /** The prefer fsn. */
    private boolean preferFSN = false;
    
    /** The recursively render property expressions. */
    private boolean recursivelyRenderPropertyExpressions = false;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender#getHumanReadableLabel(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public synchronized String getHumanReadableLabel(Expression expression){

        if(expression != null)
        {
            if(expression instanceof CloseToUserExpression)
            {
                return getHumanReadableLabel((CloseToUserExpression) expression);
            }
            else if(expression instanceof NormalFormExpression)
            {
                return getHumanReadableLabel((NormalFormExpression) expression);
            }
            else if(expression instanceof SnomedRelationshipPropertyExpression)
            {
                return getHumanReadableLabel((SnomedRelationshipPropertyExpression) expression);
            }
            else if(expression instanceof SnomedRoleGroupExpression)
            {
                return getHumanReadableLabel((SnomedRoleGroupExpression) expression);
            }
            else if(expression instanceof ConceptExpression)
            {
                ConceptExpression conceptExpression = (ConceptExpression) expression;
                return getHumanReadableLabel(conceptExpression.getConcept());
            }
            else
            {
                throw new IllegalArgumentException("Unknown expression type passed : "+expression.getClass());
            }
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /**
     * Gets the contained expressions text.
     *
     * @param expressions the expressions
     * @return the contained expressions text
     */
    protected synchronized String getContainedExpressionsText(Collection<Expression> expressions) {

        String text = "";
        int counter2 =0;
        // add text of refinements
        for(Expression childExpression : expressions)
        {
            if(counter2 ==0)
            {
                text = getHumanReadableLabel(childExpression);
            }
            else
            {
                text = text+" AND "+getHumanReadableLabel(childExpression);
            }

            // increment counter
            counter2++;
        }

        // add closing brace if counter2 is more than 0
        if(counter2 >0)
        {
            text = "("+text+")";
        }

        return text;
    }

    /**
     * Gets the text for contained focus concepts.
     *
     * @param focusConcepts the focus concepts
     * @return the text for contained focus concepts
     */
    protected synchronized String getTextForContainedFocusConcepts(Collection<SnomedConcept> focusConcepts) {

        String text ="";
        int counter1 = 0;
        for(SnomedConcept focusConcept : focusConcepts)
        {
            if(counter1 ==0)
            {
                text = getHumanReadableLabel(focusConcept);
            }
            else
            {
                text = text+" + "+getHumanReadableLabel(focusConcept);
            }

            // increment counter
            counter1++;
        }

        return text;
    }

    /**
     * Gets the human readable label.
     *
     * @param roleGroupExpression the role group expression
     * @return the human readable label
     */
    protected synchronized String getHumanReadableLabel(SnomedRoleGroupExpression roleGroupExpression){

        if(roleGroupExpression != null)
        {
            return getContainedExpressionsText(roleGroupExpression.getChildExpressions());
        }
        else
        {
            throw new IllegalArgumentException("Role group expression passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender#getHumanReadableLabel(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public synchronized String getHumanReadableLabel(SnomedConcept concept){

        if(concept != null)
        {
            // set preferred term as label, otherwise use FSN
            String text = "";
            if(concept.getPreferredLabel() == null || isPreferFSN())
            {
                text = concept.getFullySpecifiedName();
            }
            else
            {
                text = concept.getPreferredLabel();
            }

            // check and add concept id
            if(isRenderConceptIds())
            {
                text = concept.getConceptID()+"|"+text+"|";
            }

            return text;
        }
        else
        {
            throw new IllegalArgumentException("Concept passed can not be null.");
        }
    }

    /**
     * Gets the human readable label.
     *
     * @param conceptId the concept id
     * @return the human readable label
     */
    public synchronized String getHumanReadableLabel(String conceptId){
        SnomedConcept concept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, conceptId);
        if(concept != null)
        {
            return getHumanReadableLabel(concept);
        }
        else
        {
            return conceptId;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender#isRenderConceptIds()
     */
    public synchronized boolean isRenderConceptIds() {
        return renderConceptIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender#setRenderConceptIds(boolean)
     */
    public synchronized void setRenderConceptIds(boolean renderConceptIds) {
        this.renderConceptIds = renderConceptIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender#isPreferFSN()
     */
    public synchronized boolean isPreferFSN() {
        return preferFSN;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender#setPreferFSN(boolean)
     */
    public synchronized void setPreferFSN(boolean preferFSN) {
        this.preferFSN = preferFSN;
    }

    /**
     * Gets the human readable label.
     *
     * @param closeToUserExpression the close to user expression
     * @return the human readable label
     */
    protected String getHumanReadableLabel(CloseToUserExpression closeToUserExpression) {
        throw new UnsupportedOperationException("This method has not been implemented");
    }

    /**
     * Gets the human readable label.
     *
     * @param normalFormExpression the normal form expression
     * @return the human readable label
     */
    protected String getHumanReadableLabel(NormalFormExpression normalFormExpression) {
        throw new UnsupportedOperationException("This method has not been implemented");
    }

    /**
     * Gets the human readable label.
     *
     * @param relationshipPropertyExpression the relationship property expression
     * @return the human readable label
     */
    protected String getHumanReadableLabel(SnomedRelationshipPropertyExpression relationshipPropertyExpression) {
        throw new UnsupportedOperationException("This method has not been implemented");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender#isRecursivelyRenderPropertyExpressions()
     */
    public boolean isRecursivelyRenderPropertyExpressions() {
        return recursivelyRenderPropertyExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender#setRecursivelyRenderPropertyExpressions(boolean)
     */
    public void setRecursivelyRenderPropertyExpressions(boolean recursivelyRenderPropertyExpressions) {
        this.recursivelyRenderPropertyExpressions = recursivelyRenderPropertyExpressions;
    }

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }
}
