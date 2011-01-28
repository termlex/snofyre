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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship;

import javax.persistence.*;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 14, 2010 at 11:47:18 PM
 */

@Entity(name = "ExpressionSubsumptionRelationship")
@Table(name = "EXPRESSION_TC_TABLE")
//@TypeDef(name = "uuid", typeClass = uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType.class)
public class ExpressionSubsumptionRelationshipImpl implements ExpressionSubsumptionRelationship {

    /** The super type expression u uuid. */
    private UUID superTypeExpressionUUuid;
    
    /** The sub type expression uuid. */
    private UUID subTypeExpressionUuid;
    
    /** The equivalent. */
    private boolean equivalent = false;
    
    /** The id. */
    private long id;

    /**
     * Instantiates a new expression subsumption relationship impl.
     *
     * @param superTypeExpressionUUuid the super type expression u uuid
     * @param subTypeExpressionUuid the sub type expression uuid
     */
    public ExpressionSubsumptionRelationshipImpl(String superTypeExpressionUUuid, String subTypeExpressionUuid) {
        this.superTypeExpressionUUuid = UUID.fromString(superTypeExpressionUUuid);
        this.subTypeExpressionUuid = UUID.fromString(subTypeExpressionUuid);
    }

    /**
     * Instantiates a new expression subsumption relationship impl.
     *
     * @param superTypeExpressionUUuid the super type expression u uuid
     * @param subTypeExpressionUuid the sub type expression uuid
     */
    public ExpressionSubsumptionRelationshipImpl(UUID superTypeExpressionUUuid, UUID subTypeExpressionUuid) {
        this.superTypeExpressionUUuid = superTypeExpressionUUuid;
        this.subTypeExpressionUuid = subTypeExpressionUuid;
    }

    /**
     * Instantiates a new expression subsumption relationship impl.
     */
    public ExpressionSubsumptionRelationshipImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship#getSuperTypeExpressionUUuid()
     */
    @Column(name = "supertype_uuid", nullable = false, columnDefinition = "VARBINARY(18)")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    @Index(name = "IDX_SUPTYPE_ID")
    public UUID getSuperTypeExpressionUUuid() {
        return superTypeExpressionUUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship#setSuperTypeExpressionUUuid(java.lang.String)
     */
    public void setSuperTypeExpressionUUuid(UUID superTypeExpressionUUuid) {
        this.superTypeExpressionUUuid = superTypeExpressionUUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship#getSubTypeExpressionUuid()
     */
    @Column(name = "subtype_uuid", nullable = false, columnDefinition = "VARBINARY(18)")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    @Index(name = "IDX_SUBTYPE_ID")
    public UUID getSubTypeExpressionUuid() {
        return subTypeExpressionUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship#setSubTypeExpressionUuid(java.lang.String)
     */
    public void setSubTypeExpressionUuid(UUID subTypeExpressionUuid) {
        this.subTypeExpressionUuid = subTypeExpressionUuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship#isEquivalent()
     */
    @Column(name = "is_equivalent", nullable = false)
    @Index(name = "IDX_IS_EQUIV")
    public boolean isEquivalent() {
        return equivalent;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship#setEquivalent(boolean)
     */
    public void setEquivalent(boolean equivalent) {
        this.equivalent = equivalent;
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
}
