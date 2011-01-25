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
package uk.nhs.cfh.dsp.srth.query.transform.sql;

import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryIntersectionExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryUnionExpression;
import uk.nhs.cfh.dsp.srth.query.transform.QueryEngineServiceListener;
import uk.nhs.cfh.dsp.srth.query.transform.error.IllegalExpressionsNumberException;
import uk.nhs.cfh.dsp.srth.query.transform.error.NullResultSetForQueryException;
import uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;


/**
 * A class that generates SQL statements corresponding to a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement} object
 * and then executes the statements. The engine recursively builds SQL for all the {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression}s
 * objects with the main query and executes the individual queries taking into account
 * any logical operators like {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression.QueryOperatorType} within the query.
 * Query statistics can be collected by setting the {@link EngineStatus} to DEBUG mode.
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 23, 2009 at 1:40:55 PM
 */

public interface SQLQueryEngineService {

    Collection<QueryEngineServiceListener> getListeners();

    void setListeners(Collection<QueryEngineServiceListener> listeners);

    /**
     * The Enum EngineStatus.
     */
    public enum EngineStatus {
        DEBUG,
        CLEAN};

    /**
     * The Enum TableCreationStrategy.
     */
    public enum TableCreationStrategy {
        MEMORY_TABLE,
        TEMP_TABLE,
        NORMAL_TABLE,
        VIEW}

//    /**
//     * Creates the table for sub query.
//     *
//     * @param subQuery the sub query
//     *
//     * @return the string
//     *
//     * @throws SQLException the SQL exception
//     */
//    String createTableForSubQuery(SubQueryExpressionImpl subQuery)
//            throws SQLException;

    /**
     * Creates the table for intersection object.
     *
     * @param intersectionObject the intersection object
     *
     * @return the string
     *
     * @throws SQLException the SQL exception
     */
    String createTableForIntersectionObject(
            QueryIntersectionExpression intersectionObject) throws SQLException;

    /**
     * Creates the table for union object.
     *
     * @param unionObject the union object
     *
     * @return the string
     *
     * @throws SQLException the SQL exception
     */
    String createTableForUnionObject(QueryUnionExpression unionObject)
            throws SQLException;

    /**
     * Gets the table name for expression.
     *
     * @param expression the expression
     *
     * @return the table name for expression
     *
     * @throws SQLException the SQL exception
     */
    String getTableNameForExpression(QueryExpression expression)
            throws SQLException;

    /**
     * Gets the table name for reporting query.
     *
     * @param query the query
     *
     * @return the table name for reporting query
     *
     * @throws IllegalExpressionsNumberException the illegal expressions number exception
     */
    String getTableNameForReportingQuery (QueryStatement query) throws IllegalExpressionsNumberException;

    /**
     * Gets the result set for reporting query.
     *
     * @param query the query
     *
     * @return the result set for reporting query
     *
     * @throws NullResultSetForQueryException the null result set for query exception
     */
    ResultSet getResultSetForReportinQuery(QueryStatement query) throws NullResultSetForQueryException;

    /**
     * Gets the stats map.
     *
     * @return the stats map
     */
    Map<QueryExpression, QueryStatisticsCollection> getQueryStatisticsCollectionMap();

    /**
     * Gets the status.
     *
     * @return the status
     */
    EngineStatus getStatus();

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    void setStatus(EngineStatus status);

    /**
     * Gets the strategy.
     *
     * @return the strategy
     */
    TableCreationStrategy getStrategy();

    /**
     * Sets the strategy.
     *
     * @param strategy the new strategy
     */
    void setStrategy(TableCreationStrategy strategy);

    /**
     * Gets the total records number.
     *
     * @return the total records number
     */
    long getTotalRecordsNumber();

    /**
     * Sets the distinct results.
     *
     * @param distinctResults the new distinct results
     */
    void setDistinctResults(boolean distinctResults);

    /**
     * Checks if is distinct results.
     *
     * @return true, if is distinct results
     */
    boolean isDistinctResults();

    void addListener(QueryEngineServiceListener listener);

    void removeListener(QueryEngineServiceListener listener);
}