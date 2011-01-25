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
package uk.nhs.cfh.dsp.srth.expression.repository.om;

import org.hibernate.annotations.Index;

import javax.persistence.Column;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface specification of  a subsumption relationship between two 
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression}s. This is essentially
 * a Supertype-Subtype relationship.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 14, 2010 at 11:44:35 PM
 */
public interface ExpressionSubsumptionRelationship {

    /**
     * Gets the super type expression u uuid.
     *
     * @return the super type expression u uuid
     */
    UUID getSuperTypeExpressionUUuid();

    /**
     * Sets the super type expression u uuid.
     *
     * @param superTypeExpressionUUuid the new super type expression u uuid
     */
    void setSuperTypeExpressionUUuid(UUID superTypeExpressionUUuid);

    /**
     * Gets the sub type expression uuid.
     *
     * @return the sub type expression uuid
     */
    UUID getSubTypeExpressionUuid();

    /**
     * Sets the sub type expression uuid.
     *
     * @param subTypeExpressionUuid the new sub type expression uuid
     */
    void setSubTypeExpressionUuid(UUID subTypeExpressionUuid);

    /**
     * Checks if is equivalent.
     *
     * @return true, if is equivalent
     */
    @Column(name = "is_equivalent", nullable = false)
    @Index(name = "IDX_IS_EQUIV")
    boolean isEquivalent();

    /**
     * Sets the equivalent.
     *
     * @param equivalent the new equivalent
     */
    void setEquivalent(boolean equivalent);
}
