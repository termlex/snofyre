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
package uk.nhs.cfh.dsp.srth.query.repository;

import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a data access object that handles access to.
 *
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression}s.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 30, 2009 at 9:04:51 PM
 */
public interface QueryExpressionDAO {

    /**
     * Save query.
     *
     * @param expression the expression
     */
    void saveQuery(QueryExpression expression);

    /**
     * Delete query.
     *
     * @param expression the expression
     */
    void deleteQuery(QueryExpression expression);

    /**
     * Find query expression.
     *
     * @param uuid the uuid
     * @return the query expression
     */
    QueryExpression findQueryExpression(String uuid);

    /**
     * Find all queries.
     *
     * @return the list
     */
    List<QueryExpression> findAllQueries();

    /**
     * Delete all queries.
     */
    void deleteAllQueries();

    /**
     * Find using search term.
     *
     * @param searchTerm the search term
     * @return the list
     */
    List findUsingSearchTerm( String searchTerm);

    /**
     * Index all queries.
     */
    void indexAllQueries();
}
