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

import uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRelationshipImpl;

import javax.persistence.*;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression} that
 * wraps a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 4:53:39 PM
 */

@Embeddable
@Entity(name = "SnomedRelationshipPropertyExpression")
@Table(name = "SNOMED_RELATIONSHIP_PROPERTY_EXPRESSIONS")
public class SnomedRelationshipPropertyExpression extends AbstractPropertyExpressionImpl
        implements PropertyExpression, Comparable<Expression> {

    /** The relationship. */
    private SnomedRelationship relationship;

    /**
     * Instantiates a new snomed relationship property expression.
     *
     * @param operator the operator
     * @param relationship the relationship
     */
    public SnomedRelationshipPropertyExpression(Logical_Operator operator, SnomedRelationship relationship) {
        super(new SnomedRelationshipProperty(relationship), operator);
        this.relationship = relationship;
    }

    /**
     * Instantiates a new snomed relationship property expression.
     *
     * @param relationship the relationship
     */
    public SnomedRelationshipPropertyExpression(SnomedRelationship relationship) {
        this(Logical_Operator.SOME, relationship);
    }

    /**
     * Instantiates a new snomed relationship property expression.
     */
    public SnomedRelationshipPropertyExpression() {
        super();
    }

    /**
     * Instantiates a new snomed relationship property expression.
     *
     * @param concept the concept
     */
    public SnomedRelationshipPropertyExpression(SnomedConcept concept) {
        if(concept != null)
        {
            // set concept as the
            SnomedRelationship relationship = new SnomedRelationshipImpl();
            relationship.setName(concept.getPreferredLabel());
            relationship.setRelationshipType(concept.getConceptID());
            this.relationship = relationship;
            setProperty(new SnomedRelationshipProperty(relationship));
            setOperator(Logical_Operator.SOME);
            setUuid(UUID.randomUUID());
        }
        else
        {
            throw new IllegalArgumentException("Concept passed can not be null.");
        }
    }

    /**
     * Sets the relationship from concept.
     *
     * @param concept the new relationship from concept
     */
    public void setRelationshipFromConcept(SnomedConcept concept){
        if (concept != null)
        {
            // set concept as the
            SnomedRelationship relationship = new SnomedRelationshipImpl();
            relationship.setName(concept.getPreferredLabel());
            relationship.setRelationshipType(concept.getConceptID());
            this.relationship = relationship;
            setProperty(new SnomedRelationshipProperty(relationship));
            setOperator(Logical_Operator.SOME);
            setUuid(UUID.randomUUID());
        }
        else {
            throw new IllegalArgumentException("Concept passed can not be null.");
        }
    }

    /**
     * Sets the expression and populate fields.
     *
     * @param expression the new expression and populate fields
     */
    public void setExpressionAndPopulateFields(Expression expression)
    {
        setExpression(expression);
        // if expression has focus concepts, then set focus concept as targetConcept in property
        if(expression instanceof ExpressionWithFocusConcepts)
        {
            ExpressionWithFocusConcepts aefc = (ExpressionWithFocusConcepts) expression;
            SnomedConcept fc = aefc.getSingletonFocusConcept();
            getRelationship().setTargetConcept(fc);
            getRelationship().setTargetConceptID(fc.getConceptID());
        }
    }

    /**
     * Populate fields.
     *
     * @param relationship the relationship
     */
    @Transient
    protected void populateFields(SnomedRelationship relationship) {
        SnomedRelationshipProperty property = new SnomedRelationshipProperty(relationship);
        setProperty(property);
        // get target of relationship
        SnomedConcept targetConcept = relationship.getTargetConcept();
        if(targetConcept != null)
        {
            ConceptExpression conceptExpression = new ConceptExpressionImpl(targetConcept);
            setExpression(conceptExpression);
        }
    }

    /**
     * Gets the relationship.
     *
     * @return the relationship
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SnomedRelationshipImpl.class)
    public SnomedRelationship getRelationship() {
        return relationship;
    }

    /**
     * Sets the relationship.
     *
     * @param relationship the new relationship
     */
    public void setRelationship(SnomedRelationship relationship) {
        this.relationship = relationship;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#getCanonicalStringForm()
     */
    @Override
    @Transient
    public String getCanonicalStringForm() {
        String expressionCanonicalString = "";

        if (getExpression() != null)
        {
            // get canonicalForm for expression and check if it has a ':', which means it needs nesting
            expressionCanonicalString = getExpression().getCanonicalStringForm();
        }

        // add indent tabs and refinement symbol
        if(expressionCanonicalString.indexOf(":")>-1)
        {
            expressionCanonicalString = "\n\t("+expressionCanonicalString+")";
        }

        return this.getRelationship().getRelationshipType()+"="+expressionCanonicalString;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#equals(java.lang.Object)
     */
    @Override
    @Transient
    public boolean equals(Object obj) {
        if(obj instanceof SnomedRelationshipPropertyExpression)
        {
            SnomedRelationshipPropertyExpression propertyExpression = (SnomedRelationshipPropertyExpression) obj;
            return propertyExpression.getCanonicalStringForm().equalsIgnoreCase(this.getCanonicalStringForm());
        }
        else
        {
            return super.equals(obj);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#hashCode()
     */
    @Override
    @Transient
    public int hashCode() {
        return getCanonicalStringForm().hashCode();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#compareTo(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Override
    @Transient
    public int compareTo(Expression o) {
        if(o instanceof SnomedRelationshipPropertyExpression)
        {
            return (this.getCanonicalStringForm().compareTo(o.getCanonicalStringForm()));
        }
        else
        {
            throw new IllegalArgumentException("Object being compared must be a type of "+getClass().getName());
        }
    }
}