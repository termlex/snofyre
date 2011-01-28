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

import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that generates the transitive closure table for.
 *
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression}s contained in the
 * UNIQUE_EXPRESSIONS_TABLE.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 1:34:12 AM
 */
public interface ExpressionTransitiveClosureTableGenerator {

    /**
     * Generate tc table.
     *
     * @param migrateFromUniqueExpressions the migrate from unique expressions
     */
    void generateTCTable(boolean migrateFromUniqueExpressions);

    /**
     * Populate tc table for expressions.
     *
     * @param lhsNormalFormExpression the lhs normal form expression
     * @param rhsNormalFormExpression the rhs normal form expression
     * @param lhsNFUuid the lhs nf uuid
     * @param rhsNFUuid the rhs nf uuid
     */
    void populateTCTableForExpressions(NormalFormExpression lhsNormalFormExpression,
                                               NormalFormExpression rhsNormalFormExpression,
                                               UUID lhsNFUuid, UUID rhsNFUuid);
}
