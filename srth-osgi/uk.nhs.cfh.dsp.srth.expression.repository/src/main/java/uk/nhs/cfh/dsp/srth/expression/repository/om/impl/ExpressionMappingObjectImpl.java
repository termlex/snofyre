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
package uk.nhs.cfh.dsp.srth.expression.repository.om.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject;

import javax.persistence.*;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 11, 2010 at 10:51:24 AM
 */

@Entity(name = "ExpressionMappingObject")
@Table(name = "UNIQUE_EXPRESSIONS_TABLE")
//@TypeDef(name = "uuid", typeClass = uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType.class)
public class ExpressionMappingObjectImpl implements ExpressionMappingObject {

    /** The close to user expression uuid. */
    private UUID closeToUserExpressionUuid;
    
    /** The normal form expression uuid. */
    private UUID normalFormExpressionUuid;
    
    /** The situation normal form expression uuid. */
    private UUID situationNormalFormExpressionUuid;
    
    /** The close to user cg form. */
    private String closeToUserCGForm;
    
    /** The normal form cg form. */
    private String normalFormCGForm;
    
    /** The situation normal form cg form. */
    private String situationNormalFormCGForm;
    
    /** The id. */
    private long id;

    /**
     * explicit no args constructor.
     */
    public ExpressionMappingObjectImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#getCloseToUserExpressionUuid()
     */
    @Column(name = "ctu_uuid", nullable = false, columnDefinition = "VARBINARY(18)")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    @Index(name = "IDX_CTU_NFE_ID", columnNames = {"ctu_uuid", "nfe_uuid"})
    public UUID getCloseToUserExpressionUuid() {
        return closeToUserExpressionUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#setCloseToUserExpressionUuid(java.lang.String)
     */
    public void setCloseToUserExpressionUuid(UUID closeToUserExpressionUuid) {
        this.closeToUserExpressionUuid = closeToUserExpressionUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#getNormalFormExpressionUuid()
     */
    @Column(name = "nfe_uuid", nullable = false, columnDefinition = "VARBINARY(18)")
    @Index(name = "IDX_NFE_ID")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    public UUID getNormalFormExpressionUuid() {
        return normalFormExpressionUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#setNormalFormExpressionUuid(java.lang.String)
     */
    public void setNormalFormExpressionUuid(UUID normalFormExpressionUuid) {
        this.normalFormExpressionUuid = normalFormExpressionUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#getSituationNormalFormExpressionUuid()
     */
    @Column(name = "snfe_uuid", nullable = false, columnDefinition = "VARBINARY(18)")
    @Index(name = "IDX_CTU_SNFE_ID", columnNames = {"ctu_uuid", "snfe_uuid"} )
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    public UUID getSituationNormalFormExpressionUuid() {
        return situationNormalFormExpressionUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#setSituationNormalFormExpressionUuid(java.lang.String)
     */
    public void setSituationNormalFormExpressionUuid(UUID situationNormalFormExpressionUuid) {
        this.situationNormalFormExpressionUuid = situationNormalFormExpressionUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#getCloseToUserCGForm()
     */
    @Column(name = "ctu_cgf", nullable = false,  columnDefinition = "VARCHAR(2560)")
    @Index(name = "IDX_CTU_CGF")
    public String getCloseToUserCGForm() {
        return closeToUserCGForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#setCloseToUserCGForm(java.lang.String)
     */
    public void setCloseToUserCGForm(String closeToUserCGForm) {
        this.closeToUserCGForm = closeToUserCGForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#getNormalFormCGForm()
     */
    @Column(name = "nfe_cgf", nullable = false, columnDefinition = "VARCHAR(2560)")
    @Index(name = "IDX_NFE_CGF")
    public String getNormalFormCGForm() {
        return normalFormCGForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#setNormalFormCGForm(java.lang.String)
     */
    public void setNormalFormCGForm(String normalFormCGForm) {
        this.normalFormCGForm = normalFormCGForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#getSituationNormalFormCGForm()
     */
    @Column(name = "snfe_cgf", nullable = false, columnDefinition = "VARCHAR(2560)")
    @Index(name = "IDX_SNFE_CGF")
    public String getSituationNormalFormCGForm() {
        return situationNormalFormCGForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject#setSituationNormalFormCGForm(java.lang.String)
     */
    public void setSituationNormalFormCGForm(String situationNormalFormCGForm) {
        this.situationNormalFormCGForm = situationNormalFormCGForm;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
