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
package uk.nhs.cfh.dsp.srth.query.transform.sql.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.transform.QueryEngineServiceListener;
import uk.nhs.cfh.dsp.srth.query.transform.error.IllegalExpressionsNumberException;
import uk.nhs.cfh.dsp.srth.query.transform.error.NullResultSetForQueryException;
import uk.nhs.cfh.dsp.srth.query.transform.sql.ConstraintColumnNameProvider;
import uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService;
import uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 2:09:19 PM
 */
public abstract class AbstractSQLQueryEngineService implements SQLQueryEngineService {

    /** The connection. */
    protected Connection connection;
    private String schemaName;
    private String entryTableName;
    private String freeTextEntryColumnName;
    private String conceptIdColumnName;
    private String entryTimeColumnName;
    private String patientIdColumnName;
    private String valueColumnName;
    private String doseColumnName;

    /** The union object counter. */
    private int unionObjectCounter = 1;
    /** The intersection object counter. */
    private int intersectionObjectCounter = 1;
    private int queryComponentCounter = 1;
    /** The stats map. */
    private Map<QueryExpression, QueryStatisticsCollection> statsMap = new LinkedHashMap<QueryExpression, QueryStatisticsCollection>();
    /** The status. */
    private EngineStatus status = EngineStatus.DEBUG;
    /** The strategy. */
    private TableCreationStrategy strategy = TableCreationStrategy.TEMP_TABLE;
    /** The table columns map. */
    private Map<String, List<String>> tableColumnsMap = new LinkedHashMap<String, List<String>>();
    /** The distinct results. */
    private boolean distinctResults = true;
    /** The listeners. */
    private Collection<QueryEngineServiceListener> listeners = new ArrayList<QueryEngineServiceListener>();
    /** The constraint column name provider. */
    private ConstraintColumnNameProvider constraintColumnNameProvider;
    private static Log logger = LogFactory.getLog(AbstractSQLQueryEngineService.class);

    private NormalFormGenerator normalFormGenerator;
    private ClinicalEntryDAO clinicalEntryDAO;
    private PatientDAO patientDAO;
    private DataSource dataSource;
    /** The query expression xml renderer. */
    private QueryExpressionXMLRenderer queryExpressionXMLRenderer;
    /** The tables config file. */
    private XMLConfiguration tablesConfigFile;
    private URL tablesConfigFileURL;
    

    protected AbstractSQLQueryEngineService(DataSource dataSource, QueryExpressionXMLRenderer queryExpressionXMLRenderer,
                                            ConstraintColumnNameProvider constraintColumnNameProvider) {
        this.dataSource = dataSource;
        this.queryExpressionXMLRenderer = queryExpressionXMLRenderer;
        this.constraintColumnNameProvider = constraintColumnNameProvider;

        // create tables log file
        createTablesLogFile();
    }

    /**
     * Empty constructor for IOC
     */
    protected AbstractSQLQueryEngineService() {
    }

    protected synchronized void createTablesLogFile(){

        try
        {
            File tablesFile = new File(tablesConfigFileURL.toURI());
            // create file if it doesn't exist

            if(!tablesFile.exists())
            {
                Document doc = new Document(new Element("tables"));
                XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
                FileWriter fw = new FileWriter(tablesFile);
                outputter.output(doc, fw);
            }
            tablesConfigFile = new XMLConfiguration(tablesFile);
        } catch (ConfigurationException e) {
            logger.warn("Configuration exception. Nested exception is : " + e.fillInStackTrace().getMessage());
        } catch (IOException e) {
            logger.warn("IO exception. Nested exception is : " + e.fillInStackTrace().getMessage());
        }
        catch (URISyntaxException e) {
            logger.warn("URI syntax exception. Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    protected void updateTablesConfigFile(String tableName){
        // add table to temp tables file list
        tablesConfigFile.addProperty("tables.table", tableName);
        try
        {
            tablesConfigFile.save();
            if (logger.isDebugEnabled()) {
                logger.debug("Updated config file with tableName : "+tableName);
            }
        } catch (ConfigurationException e) {
            logger.warn(e.fillInStackTrace());
        }
    }

    public abstract String createTableForIntersectionObject(QueryIntersectionExpression intersectionObject) throws SQLException;

    public abstract String createTableForUnionObject(QueryUnionExpression unionObject) throws SQLException ;

    public String createTableForQueryComponentExpression(QueryComponentExpression componentExpression) throws SQLException{
        throw new UnsupportedOperationException("This method has not been implemented!");
    }

    /**
     * Gets the table name for expression.
     *
     * @param expression the expression
     *
     * @return the table name for expression
     *
     * @throws java.sql.SQLException the SQL exception
     */
    public String getTableNameForExpression(QueryExpression expression) throws SQLException{

        String tableName = null;
        // handle based on expression type
        if(expression instanceof QueryIntersectionExpression)
        {
            // create tables
            tableName = createTableForIntersectionObject((QueryIntersectionExpression) expression);
        }
        else if (expression instanceof QueryUnionExpression)
        {
            // call appropriate method
            tableName = createTableForUnionObject((QueryUnionExpression) expression);
        }
        else if(expression instanceof QueryComponentExpression)
        {
            tableName = createTableForQueryComponentExpression((QueryComponentExpression) expression);
        }
        else
        {
            throw new UnsupportedOperationException("Unsupported Query Expression passed: "+expression.getClass());
        }

        return tableName;
    }

    /**
     * Drop table or view if exists.
     *
     * @param tableName the table name
     * @param st the st
     * @param collection the collection
     */
    protected void dropTableOrViewIfExists(String tableName, Statement st, QueryStatisticsCollection collection){

        try
        {
            st.execute("DROP TABLE IF EXISTS " + tableName);
            st.execute("DROP VIEW IF EXISTS " + tableName);
            collection.getCommentedSteps().add("Dropped table/view with statement : ");
            collection.getCommentedSteps().add("DROP TABLE IF EXISTS " + tableName);
            collection.getCommentedSteps().add("DROP VIEW IF EXISTS " + tableName);
            collection.getSqlSteps().add("DROP TABLE IF EXISTS " + tableName);
            collection.getSqlSteps().add("DROP VIEW IF EXISTS " + tableName);

        } catch (SQLException e) {
            logger.warn(e.fillInStackTrace());
        }
    }

    protected void checkAndPopulateQueryStatistics(long startTime, String tableName,
                                                   QueryStatisticsCollection collection,
                                                   QueryExpression queryExpression) {
        if (getStatus() == EngineStatus.DEBUG)
        {
            long queryTime = Calendar.getInstance().getTimeInMillis() - startTime;

            collection.setTableName(tableName);
            collection.setQueryTime(queryTime);
            collection.setResultSetSize(getTotalRecordsNumberInTable(tableName, false));
            collection.setDistinctPatientsCount(getTotalRecordsNumberInTable(tableName, true));
            statsMap.put(queryExpression, collection);
        }
    }

    /**
     * Creates the table.
     *
     * @param tableName the table name
     * @param tableCreationString the table creation string
     * @param addColsToColMap the add cols to col map
     * @param indexColumns the index columns
     * @param collection the collection
     */
    protected void createTable(String tableName, String tableCreationString, boolean addColsToColMap,
                               String[] indexColumns, QueryStatisticsCollection collection){

        if(logger.isDebugEnabled()){
            logger.debug("Value of tableCreateString : " + tableCreationString);
        }

        if (addColsToColMap) {
            // add additional column names for future reference
            List<String> list = new ArrayList<String>();
            list.add("CONCEPT_ID");
            list.add("ENTRY_TIME");
            list.add("FREE_TEXT_ENTRY");
            tableColumnsMap.put(tableName, list);
        }
        // execute statements
        try
        {
            Statement st = connection.createStatement();
            // drop table if it already exists
            dropTableOrViewIfExists(tableName, st, collection);
            st.execute(tableCreationString);
            collection.getCommentedSteps().add("Creating table " + tableName + " using statement : ");
            collection.getCommentedSteps().add(tableCreationString);
            collection.getSqlSteps().add(tableCreationString);

            // add indices
            for(String colName : indexColumns)
            {
                addIndexOnColumnOnTable(tableName, st, colName, collection);
            }

            // close statement
            st.close();
        } catch (SQLException e) {
            logger.warn(e.fillInStackTrace());
        }
    }

    /**
     * Adds the index on column on table.
     *
     * @param tableName the table name
     * @param st the st
     * @param columnName the column name
     * @param collection the collection
     */
    private void addIndexOnColumnOnTable(String tableName, Statement st, String columnName, QueryStatisticsCollection collection){
        // create index creation statements
        String createIndexStatement = "ALTER TABLE "+tableName+" ADD INDEX IDX_"+columnName.toUpperCase()+"("+columnName+")";
        try
        {
            st.execute(createIndexStatement);
            collection.getCommentedSteps().add("Created index on " + tableName + " using statement : ");
            collection.getCommentedSteps().add(createIndexStatement);
            collection.getSqlSteps().add(createIndexStatement);
        } catch (SQLException e) {
            logger.warn(e.fillInStackTrace());
        }
    }

    /**
     * Gets the table name for reporting query.
     *
     * @param query the query
     *
     * @return the table name for reporting query
     *
     */
    public String getTableNameForReportingQuery(QueryStatement query){

        String tableName = null;
        // clear existing table names in table names map
        statsMap.clear();
        tableColumnsMap.clear();
        // reset counters
        resetCounters();
        long startTime = Calendar.getInstance().getTimeInMillis();
        /*
           * we know any query will only ever have one clinical expression inside it.
           * This can be a union object, intersection object or a sub query object.
           */
        List<QueryExpression> expressions = new ArrayList<QueryExpression>(query.getContainedExpressions());
        if(expressions.size() ==1)
        {
            try
            {
                tableName = getTableNameForExpression(expressions.get(0));
                if (logger.isDebugEnabled()) {
                    logger.debug("Obtained tableName for query in getTableNameForReportingQuery : " + tableName);
                }

                QueryStatisticsCollection collection = new QueryStatisticsCollection(
                            query, queryExpressionXMLRenderer);
                checkAndPopulateQueryStatistics(startTime, tableName, collection, query);

            } catch (SQLException e) {
                logger.warn("SQL error encountered executing query. Nested exception is : " + e.fillInStackTrace().getMessage());
            }
        }
        else
        {
            throw new IllegalExpressionsNumberException("\nNumber of expressions in query : "+expressions.size());
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Value of tableName in getTableNameForReportingQuery : " + tableName);
        }

        return tableName;
    }

    /**
     * Gets the result set for reporting query.
     *
     * @param query the query
     *
     * @return the result set for reporting query
     *
     */
    public ResultSet getResultSetForReportinQuery(QueryStatement query){

        // get the table name for query
        String tableName = null;

            long startTime = Calendar.getInstance().getTimeInMillis();
            tableName = getTableNameForReportingQuery(query);

            if(tableName != null)
            {
                // get contents of table
                try
                {
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM "+tableName);
                    long queryTime = Calendar.getInstance().getTimeInMillis() - startTime;
                    logger.info("Finished generating result set for query statement in : " + queryTime);

                    logger.info("Making result set available in service");
                    // update result set in service
                    resultSetAvailable(rs);
                    logger.info("Making query time available in service");
                    // update query time
                    queryTimeUpdated(queryTime);
                    logger.info("Making query statistics available in service");
                    // update collection map in service
                    queryStatisticsCollectionAvailable(getQueryStatisticsCollectionMap());

                    return rs;

                } catch (SQLException e) {
                    logger.warn(e.fillInStackTrace());
                    throw new NullResultSetForQueryException("Null result returned for query. " +
                            "Error caused by : \n", e);
                }
            }
            else
            {
                throw new NullResultSetForQueryException();
            }
    }


    /**
     * Gets the query statistics collection map.
     *
     * @return the query statistics collection map
     */
    public Map<QueryExpression, QueryStatisticsCollection> getQueryStatisticsCollectionMap() {
        return statsMap;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public EngineStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(EngineStatus status) {
        this.status = status;
    }

    /**
     * Gets the strategy.
     *
     * @return the strategy
     */
    public TableCreationStrategy getStrategy() {
        return strategy;
    }

    /**
     * Sets the strategy.
     *
     * @param strategy the new strategy
     */
    public void setStrategy(TableCreationStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Reset counters.
     */
    protected void resetCounters(){
        unionObjectCounter = 0;
        intersectionObjectCounter = 0;
        queryComponentCounter = 0;
    }

    /**
     * Gets the total records number.
     *
     * @return the total records number
     */
    public long getTotalRecordsNumber() {
        return patientDAO.getTotalPatientCountInDatabase();
    }

    /**
     * Gets the total records number.
     *
     * @param tableName the table numbe to return the record count for.
     * @param isDistinct boolean to return only distinct patient ids.
     * @return the total records number
     */
    public int getTotalRecordsNumberInTable(String tableName, boolean isDistinct) {

        int count = 0;
        String selectString = "SELECT COUNT(PATIENT_ID) FROM ";

        // add distinct operator if distinct patient ids are to be returned
        if(isDistinct) {
                selectString = "SELECT COUNT(DISTINCT PATIENT_ID) FROM ";
        }
		// create statement
		try
		{
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(selectString+getSchemaName()+"."+tableName);
			// this will return only one value
			while(rs.next())
			{
				count = rs.getInt(1);
                logger.debug("count = " + count);
			}

			rs.close();
            st.close();

		} catch (SQLException e) {
			logger.warn(e.fillInStackTrace());
		}

		return count;
    }

    /**
     * Checks if is distinct results.
     *
     * @return true, if is distinct results
     */
    public boolean isDistinctResults() {
        return distinctResults;
    }

    /**
     * Result set available.
     *
     * @param resultSet the result set
     */
    public void resultSetAvailable(ResultSet resultSet) {
        if (resultSet != null)
        {
            // notify all listeners
            for(QueryEngineServiceListener listener : listeners)
            {
                listener.resultSetChanged(resultSet);
            }
        }
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null : "+resultSet);
        }
    }

    /**
     * Query time updated.
     *
     * @param queryTime the query time
     */
    public void queryTimeUpdated(long queryTime) {
        // notify all listeners
        for(QueryEngineServiceListener listener : listeners)
        {
            listener.queryTimeChanged(queryTime);
        }
    }

    /**
     * Query statistics collection available.
     *
     * @param queryStatisticsCollectionMap the query statistics collection map
     */
    public void queryStatisticsCollectionAvailable(
            Map<QueryExpression, QueryStatisticsCollection> queryStatisticsCollectionMap){
        // notify listeners
        for(QueryEngineServiceListener listener : listeners)
        {
            listener.queryStatisticsCollectionChanged(queryStatisticsCollectionMap);
        }
    }

    public void queryResultsAvailable(Collection<ClinicalEntry> entries){
        // notify listeners
        for(QueryEngineServiceListener listener : listeners)
        {
            listener.queryResultsAvailable(entries);
        }
    }

    /**
     * Adds the listener.
     *
     * @param listener the listener
     */
    public void addListener(QueryEngineServiceListener listener) {
        if(listener != null)
        {
            listeners.add(listener);
        }
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null : "+listener);
        }
    }

    /**
     * Removes the listener.
     *
     * @param listener the listener
     */
    public void removeListener(QueryEngineServiceListener listener) {
        if(listener != null)
        {
            listeners.remove(listener);
        }
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null : "+listener);
        }
    }

    public Collection<QueryEngineServiceListener> getListeners() {
        return listeners;
    }

    public void setListeners(Collection<QueryEngineServiceListener> listeners) {
        this.listeners = listeners;
    }

    /**
     * Sets the distinct results.
     *
     * @param distinctResults the new distinct results
     */
    public void setDistinctResults(boolean distinctResults) {
        this.distinctResults = distinctResults;
    }

    /**
     * Gets the table type.
     *
     * @return the table type
     */
    protected String getTableType(){
        TableCreationStrategy strategy = getStrategy();
        if(strategy == TableCreationStrategy.NORMAL_TABLE)
        {
            return "TABLE";
        }
        else if(strategy == TableCreationStrategy.MEMORY_TABLE)
        {
            return "MEMORY TABLE";
        }
        else if(strategy == TableCreationStrategy.TEMP_TABLE)
        {
            return "TEMPORARY TABLE";
        }
        else if(strategy == TableCreationStrategy.VIEW)
        {
            return "VIEW";
        }
        else
        {
            logger.warn("Unknown strategy passed. Defaulting to normal table");
            return "TABLE";
        }
    }

    public synchronized void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public synchronized void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }

    public synchronized void setClinicalEntryDAO(ClinicalEntryDAO clinicalEntryDAO) {
        this.clinicalEntryDAO = clinicalEntryDAO;
    }

    /**
     * Gets the union object counter.
     *
     * @return the union object counter
     */
    public int getUnionObjectCounter() {
        return unionObjectCounter;
    }

    /**
     * Gets the intersection object counter.
     *
     * @return the intersection object counter
     */
    public int getIntersectionObjectCounter() {
        return intersectionObjectCounter;
    }

    public synchronized DataSource getDataSource() {
        return dataSource;
    }

    public synchronized void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        try
        {
            this.connection = dataSource.getConnection();
        }
        catch (SQLException e) {
            logger.warn("Error creating connection from datasource. " +
                    "Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    public XMLConfiguration getTablesConfigFile() {
        return tablesConfigFile;
    }

    public synchronized void setTablesConfigFileURL(URL tablesConfigFileURL) {
        this.tablesConfigFileURL = tablesConfigFileURL;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public synchronized void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getEntryTableName() {
        return entryTableName;
    }

    public synchronized void setEntryTableName(String entryTableName) {
        this.entryTableName = entryTableName;
    }

    public String getFreeTextEntryColumnName() {
        return freeTextEntryColumnName;
    }

    public synchronized void setFreeTextEntryColumnName(String freeTextEntryColumnName) {
        this.freeTextEntryColumnName = freeTextEntryColumnName;
    }

    public String getConceptIdColumnName() {
        return conceptIdColumnName;
    }

    public synchronized void setConceptIdColumnName(String conceptIdColumnName) {
        this.conceptIdColumnName = conceptIdColumnName;
    }

    public String getEntryTimeColumnName() {
        return entryTimeColumnName;
    }

    public synchronized void setEntryTimeColumnName(String entryTimeColumnName) {
        this.entryTimeColumnName = entryTimeColumnName;
    }

    public String getPatientIdColumnName() {
        return patientIdColumnName;
    }

    public synchronized void setPatientIdColumnName(String patientIdColumnName) {
        this.patientIdColumnName = patientIdColumnName;
    }

    public String getValueColumnName() {
        return valueColumnName;
    }

    public synchronized void setValueColumnName(String valueColumnName) {
        this.valueColumnName = valueColumnName;
    }

    public String getDoseColumnName() {
        return doseColumnName;
    }

    public synchronized void setDoseColumnName(String doseColumnName) {
        this.doseColumnName = doseColumnName;
    }

    public int getQueryComponentCounter() {
        return queryComponentCounter;
    }

    public Map<QueryExpression, QueryStatisticsCollection> getStatsMap() {
        return statsMap;
    }

    public Map<String, List<String>> getTableColumnsMap() {
        return tableColumnsMap;
    }

    public ConstraintColumnNameProvider getConstraintColumnNameProvider() {
        return constraintColumnNameProvider;
    }

    public NormalFormGenerator getNormalFormGenerator() {
        return normalFormGenerator;
    }

    public ClinicalEntryDAO getClinicalEntryDAO() {
        return clinicalEntryDAO;
    }

    public QueryExpressionXMLRenderer getQueryExpressionXMLRenderer() {
        return queryExpressionXMLRenderer;
    }

    public PatientDAO getPatientDAO() {
        return patientDAO;
    }

    public URL getTablesConfigFileURL() {
        return tablesConfigFileURL;
    }

    public void setUnionObjectCounter(int unionObjectCounter) {
        this.unionObjectCounter = unionObjectCounter;
    }

    public void setIntersectionObjectCounter(int intersectionObjectCounter) {
        this.intersectionObjectCounter = intersectionObjectCounter;
    }

    public void setQueryComponentCounter(int queryComponentCounter) {
        this.queryComponentCounter = queryComponentCounter;
    }
}
