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
package uk.nhs.cfh.dsp.srth.expression.repository;

import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship;

import java.util.List;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a DAO object that manages.
 *
 * {@link uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship} objects.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 14, 2010 at 11:55:28 PM
 */
public interface ExpressionSubsumptionRelationshipDAO {

    /**
     * Save relationship.
     *
     * @param relationship the relationship
     */
    void saveRelationship(ExpressionSubsumptionRelationship relationship);

    /**
     * Delete relationship.
     *
     * @param relationship the relationship
     */
    void deleteRelationship(ExpressionSubsumptionRelationship relationship);

    /**
     * Find all relationships.
     *
     * @return the list
     */
    List<ExpressionSubsumptionRelationship> findAllRelationships();

    /**
     * Find relationship.
     *
     * @param superTypeUuid the super type uuid
     * @param subtypeUuid the subtype uuid
     * @return the expression subsumption relationship
     */
    ExpressionSubsumptionRelationship findRelationship(UUID superTypeUuid, UUID subtypeUuid);

    /**
     * Gets the all sub types.
     *
     * @param superTypeUuid the super type uuid
     * @return the all sub types
     */
    Set<UUID> getAllSubTypes(UUID superTypeUuid);

    /**
     * Gets the all super types.
     *
     * @param subTypeUuid the sub type uuid
     * @return all super types of the given expression
     */
    Set<UUID> getAllSuperTypes(UUID subTypeUuid);

    /**
     * Delete all.
     */
    void deleteAll();

    /**
     * Gets the relationships count.
     *
     * @return the relationships count
     */
    int getRelationshipsCount();
}
