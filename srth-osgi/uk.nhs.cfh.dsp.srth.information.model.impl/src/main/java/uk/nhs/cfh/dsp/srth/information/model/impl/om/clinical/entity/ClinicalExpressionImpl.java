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
package uk.nhs.cfh.dsp.srth.information.model.impl.om.clinical.entity;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;

import javax.persistence.*;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 11:48:35 AM
 */


@Embeddable
@Entity(name = "Clinical_Expression")
@Table(name = "Clinical_Expressions")
public class ClinicalExpressionImpl implements ClinicalExpression{

    /** The expression. */
    private Expression expression;

    /** The canonical form. */
    private String compositionalForm;

    /** The uuid. */
    private UUID uuid = UUID.randomUUID();

    /** The normal form expression. */
    private NormalFormExpression normalFormExpression;

    /** The compositional normal form. */
    private String compositionalNormalForm;

    /** The normal form uuid. */
    private UUID normalFormUuid;

    /** The situation normal form uuid. */
    private UUID situationNormalFormUuid;

    /** The compositional situation normal form. */
    private String compositionalSituationNormalForm;

    /**
     * Instantiates a new clinical expression impl.
     */
    public ClinicalExpressionImpl() {
        // empty constructor for persistence
    }

    /**
     * Instantiates a new clinical expression impl.
     *
     * @param expression the expression
     */
    public ClinicalExpressionImpl(Expression expression) {
        if(expression != null)
        {
            this.expression = expression;
        }
        else
        {
            throw new IllegalArgumentException("Expression passed can not be null.");
        }
    }

    /**
     * Gets the canonical form.
     *
     * @return the canonical form
     */
    @Column(name = "compositional_grammar_form", nullable = false, columnDefinition = "VARCHAR(2560)")
    @Index(name = "IDX_CE_CG_FORM")
    public String getCompositionalGrammarForm() {
        return compositionalForm;
    }

    /**
     * Sets the canonical form.
     *
     * @param compositionalForm the new canonical form
     */
    public void setCompositionalGrammarForm(String compositionalForm) {
        this.compositionalForm = compositionalForm;
    }

    /**
     * Gets the uuid.
     *
     * @return the uuid
     */
    @Id
    @Column(name = "uuid" , nullable = false, columnDefinition = "VARBINARY(18)")
    @Index(name = "IDX_CE_EXP_UUID")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
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
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CloseToUserExpressionImpl.class)
    @Transient
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

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = NormalFormExpressionImpl.class)
    /* (non-Javadoc)
 * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#getNormalFormExpression()
 */
    @Transient
    public NormalFormExpression getNormalFormExpression() {
        return normalFormExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#setNormalFormExpression(uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression)
     */
    public void setNormalFormExpression(NormalFormExpression normalFormExpression) {
        this.normalFormExpression = normalFormExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#getCompositionalGrammarNormalForm()
     */
    @Column(name = "compositional_nf", nullable = false, columnDefinition = "VARCHAR(2560)")
    @Index(name = "IDX_CE_CG_NF")
    public String getCompositionalGrammarNormalForm() {
        return compositionalNormalForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#setCompositionalGrammarNormalForm(java.lang.String)
     */
    public void setCompositionalGrammarNormalForm(String compositionalNormalForm) {
        this.compositionalNormalForm = compositionalNormalForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#getNormalFormUuid()
     */
    @Column(name = "nf_uuid" , nullable = false, columnDefinition = "VARBINARY(18)")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    @Index(name = "IDX_CE_NF_UUID")
    public UUID getNormalFormUuid() {
        return normalFormUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#setNormalFormUuid(java.lang.String)
     */
    public void setNormalFormUuid(UUID normalFormUuid) {
        this.normalFormUuid = normalFormUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#getSituationNormalFormUuid()
     */
    @Column(name = "snf_uuid" , nullable = false, columnDefinition = "VARBINARY(18)")
    @Index(name = "IDX_CE_SNF_UUID")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    public UUID getSituationNormalFormUuid() {
        return situationNormalFormUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#setSituationNormalFormUuid(java.lang.String)
     */
    public void setSituationNormalFormUuid(UUID situationNormalFormUuid) {
        this.situationNormalFormUuid = situationNormalFormUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#getCompositionalSituationNormalForm()
     */
    @Column(name = "compositional_snf", nullable = false, columnDefinition = "VARCHAR(2560)")
    @Index(name = "IDX_CE_CG_SNF")
    public String getCompositionalSituationNormalForm() {
        return compositionalSituationNormalForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression#setCompositionalSituationNormalForm(java.lang.String)
     */
    public void setCompositionalSituationNormalForm(String compositionalSituationNormalForm) {
        this.compositionalSituationNormalForm = compositionalSituationNormalForm;
    }
}
