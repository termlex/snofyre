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
package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;

import java.util.Collection;
import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 27, 2010 at 11:42:47 PM
 */
public abstract class AbstractSituationExpression extends AbstractExpressionImpl implements SituationExpression {

    /** The subject relationship context expression. */
    private SnomedRelationshipPropertyExpression subjectRelationshipContextExpression;
    
    /** The temporal context expression. */
    private SnomedRelationshipPropertyExpression temporalContextExpression;
    
    /** The associated context expression. */
    private SnomedRelationshipPropertyExpression associatedContextExpression;
    
    /** The focus expression. */
    private SnomedRelationshipPropertyExpression focusExpression;
    
    /** The focus concept. */
    private SnomedConcept focusConcept;
    
    /** The permitted types. */
    protected Collection<ConceptType> permittedTypes = new HashSet<ConceptType>();
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(AbstractSituationExpression.class);

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#getSubjectRelationshipContextExpression()
     */
    public SnomedRelationshipPropertyExpression getSubjectRelationshipContextExpression() {
        return subjectRelationshipContextExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#setSubjectRelationshipContextExpression(uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression)
     */
    public void setSubjectRelationshipContextExpression(SnomedRelationshipPropertyExpression subjectRelationshipContextExpression) {
        this.subjectRelationshipContextExpression = subjectRelationshipContextExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#getTemporalContextExpression()
     */
    public SnomedRelationshipPropertyExpression getTemporalContextExpression() {
        return temporalContextExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#setTemporalContextExpression(uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression)
     */
    public void setTemporalContextExpression(SnomedRelationshipPropertyExpression temporalContextExpression) {
        this.temporalContextExpression = temporalContextExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#getAssociatedContextExpression()
     */
    public SnomedRelationshipPropertyExpression getAssociatedContextExpression() {
        return associatedContextExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#setAssociatedContextExpression(uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression)
     */
    public void setAssociatedContextExpression(SnomedRelationshipPropertyExpression associatedContextExpression) {
        this.associatedContextExpression = associatedContextExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#getFocusExpression()
     */
    public SnomedRelationshipPropertyExpression getFocusExpression() {
        return focusExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#setFocusExpression(uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression)
     */
    public void setFocusExpression(SnomedRelationshipPropertyExpression focusExpression) {
        this.focusExpression = focusExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#getSubjectRelationship()
     */
    public SnomedConcept getSubjectRelationship(){
        SnomedRelationshipPropertyExpression localPropertyExpression = getSubjectRelationshipContextExpression();
        if(localPropertyExpression != null)
        {
            SnomedRelationship relationship = localPropertyExpression.getRelationship();
            if(relationship != null && relationship.getTargetConcept() != null)
            {
                return relationship.getTargetConcept();
            }
            else
            {
                throw new UnsupportedOperationException("Subject Relationship target not set for expression");
            }
        }
        else
        {
            throw new UnsupportedOperationException("Subject Relationship not set for expression");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#getTemporalContext()
     */
    public SnomedConcept getTemporalContext(){
        SnomedRelationshipPropertyExpression localPropertyExpression = getTemporalContextExpression();
        if(localPropertyExpression != null)
        {
            SnomedRelationship relationship = localPropertyExpression.getRelationship();
            if(relationship != null && relationship.getTargetConcept() != null)
            {
                return relationship.getTargetConcept();
            }
            else
            {
                throw new UnsupportedOperationException("Temporal context target not set for expression");
            }
        }
        else
        {
            throw new UnsupportedOperationException("Temporal context not set for expression");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#getAssociatedContext()
     */
    public SnomedConcept getAssociatedContext(){
        SnomedRelationshipPropertyExpression localPropertyExpression = getAssociatedContextExpression();
        if(localPropertyExpression != null)
        {
            SnomedRelationship relationship = localPropertyExpression.getRelationship();
            if(relationship != null && relationship.getTargetConcept() != null)
            {
                return relationship.getTargetConcept();
            }
            else
            {
                throw new UnsupportedOperationException("Associated context target not set for expression");
            }
        }
        else
        {
            throw new UnsupportedOperationException("Associated context not set for expression");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#getPermittedTypes()
     */
    public abstract Collection<ConceptType> getPermittedTypes();

    /**
     * Sets the permitted types.
     *
     * @param permittedTypes the new permitted types
     */
    public void setPermittedTypes(Collection<ConceptType> permittedTypes){
        this.permittedTypes = permittedTypes;
    };

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#getFocusConcept()
     */
    public SnomedConcept getFocusConcept() {
        return focusConcept;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression#setFocusConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public void setFocusConcept(SnomedConcept focusConcept) {
        this.focusConcept = focusConcept;
    }
}
