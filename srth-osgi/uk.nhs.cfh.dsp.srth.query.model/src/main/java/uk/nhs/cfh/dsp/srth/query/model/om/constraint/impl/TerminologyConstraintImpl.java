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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl;

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;

import javax.persistence.*;
import java.util.UUID;

// TODO: Auto-generated Javadoc
//TODO Add ability to compare for equivalence using expression comparator

/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 9:18:31 PM
 */

@Indexed
@Entity(name = "TerminologyConstraint")
@Embeddable
@DiscriminatorValue(value = "TerminologyConstraint")
//@TypeDef(name = "uuid", typeClass = uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType.class)
public class TerminologyConstraintImpl extends AbstractConstraint implements TerminologyConstraint {

    /** The subsumption. */
    private SubsumptionVocabulary subsumption;
    
    /** The expression. */
    private Expression expression;
    
    /** The compositional grammar form. */
    private String compositionalGrammarForm;
    /** The uuid. */
    private UUID uuid;

    /**
     * Instantiates a new terminology constraint impl.
     */
    public TerminologyConstraintImpl() {
        // empty constructor for persistence
    }

    /**
     * Instantiates a new terminology constraint impl.
     * 
     * @param expression the expression
     */
    public TerminologyConstraintImpl(CloseToUserExpression expression) {
        this(new ConstraintValue<CloseToUserExpression> (expression), SubsumptionVocabulary.SELF_OR_ANY_TYPE_OF);
    }

    /**
     * Instantiates a new terminology constraint impl.
     * 
     * @param expression the expression
     * @param subsumptionVocabulary the subsumption vocabulary
     */
    public TerminologyConstraintImpl(ConstraintValue<CloseToUserExpression> expression,
                                 SubsumptionVocabulary subsumptionVocabulary) {
        super(ConstraintType.TERMINOLOGY, expression, ConstraintDimensionVocabulary.NULL);
        this.subsumption = subsumptionVocabulary;
        if (expression != null)
        {
            this.expression = expression.getValue();
            this.uuid = this.expression.getUuid();
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see nhs.cfh.dsp.srth.query.model.om.constraint.impl.AbstractConstraint#permittedValue(ConstraintValue)
     */
    @Transient
    protected boolean permittedValue(ConstraintValue<?> value) {
        return (value.getValue() instanceof Expression);
    }

    /**
     * Gets the subsumption.
     * 
     * @return the subsumption
     */
    @Enumerated(EnumType.STRING)
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public SubsumptionVocabulary getSubsumption() {
        return subsumption;
    }

    /**
     * Sets the subsumption.
     * 
     * @param subsumption the new subsumption
     */
    public void setSubsumption(SubsumptionVocabulary subsumption) {
        this.subsumption = subsumption;
    }

    /**
     * Gets the uuid.
     *
     * @return the uuid
     */
//    @Column(name = "uuid", nullable = false, columnDefinition = "VARCHAR(36)")
    @Column(name = "uuid", nullable = false, columnDefinition = "VARBINARY(18)")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
//    @Type(type = "uuid")
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the uuid.
     *
     * @param uuid the new uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the expression.
     * 
     * @return the expression
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = CloseToUserExpressionImpl.class)
    public Expression getExpression() {
        return expression;
    }

    /**
     * Sets the expression.
     * 
     * @param expression the new expression
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint#getCompositionalGrammarForm()
     */
    @Column(name = "cg_form")
    @org.hibernate.annotations.Index(name = "IDX_CGF")
    public String getCompositionalGrammarForm() {
        return compositionalGrammarForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint#setCompositionalGrammarForm(java.lang.String)
     */
    public void setCompositionalGrammarForm(String compositionalGrammarForm) {
        this.compositionalGrammarForm = compositionalGrammarForm;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @Transient
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TerminologyConstraintImpl that = (TerminologyConstraintImpl) o;

        if (subsumption != that.subsumption) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (expression != null ? !expression.equals(that.expression) : that.expression != null) return false;

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    @Transient
    public int hashCode() {
        int result = subsumption != null ? subsumption.hashCode() : 0;
        result = 31 * result + (expression != null ? expression.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
    }
}
