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
package uk.nhs.cfh.dsp.snomed.dao;

import uk.nhs.cfh.dsp.snomed.expression.model.Expression;

import java.util.List;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A DAO object that manages {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression} objects
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 20-Mar-2010 at 21:38:51
 */
public interface SnomedExpressionDAO {

    /**
     * Save expression.
     *
     * @param expression the expression
     */
    void saveExpression(Expression expression);

    /**
     * Delete expression.
     *
     * @param expression the expression
     */
    void deleteExpression(Expression expression);

    /**
     * Find all.
     *
     * @return the list
     */
    List<Expression> findAll();

    /**
     * Find.
     *
     * @param uuid the uuid
     * @return the expression
     */
    Expression find(UUID uuid);
}
