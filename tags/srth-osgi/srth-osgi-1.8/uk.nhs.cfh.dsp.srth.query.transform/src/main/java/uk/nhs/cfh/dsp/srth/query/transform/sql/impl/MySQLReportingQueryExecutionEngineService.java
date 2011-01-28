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
/**
 *
 */
package uk.nhs.cfh.dsp.srth.query.transform.sql.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryComponentExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression.QueryOperatorType;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression.QueryRunTimeStatus;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryIntersectionExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryUnionExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;
//import uk.nhs.cfh.dsp.srth.query.model.om.impl.SubQueryExpressionImpl;
import uk.nhs.cfh.dsp.srth.query.transform.SubtypeGetter;
import uk.nhs.cfh.dsp.srth.query.transform.sql.ConstraintColumnNameProvider;
import uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.*;
import java.util.*;


// TODO: Auto-generated Javadoc

/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService} written for the MySQL server. 
 * The default {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService.TableCreationStrategy} is TEMP_TABLE.
 * This class is to be used for the new version of Sappheiros to execute queries. Note: This class does not allow VIEWs
 * to be used as {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService.TableCreationStrategy}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 23, 2009 at 1:40:55 PM
 * <br> Modified on Monday; November 30, 2009 at 4:50 PM to include listeners for modular services
 */
public class MySQLReportingQueryExecutionEngineService extends AbstractSQLQueryEngineService {

    /** The logger. */
    private static Log logger = LogFactory.getLog(MySQLReportingQueryExecutionEngineService.class);
    private SubtypeGetter subtypeGetter;
    private String patientIdCoulumnDef = "BIGINT(20)";
    private String conceptIdCoulumnDef = "VARBINARY(16)";
    private String freeTextEntryCoulumnDef = "VARCHAR(256)";

    /**
     * Instantiates a new my sql reporting query execution engine service.
     */
    public MySQLReportingQueryExecutionEngineService() {
        // empty constructor
    }

    /**
     * Instantiates a new my sql reporting query execution engine service.
     *
     * @param queryExpressionXMLRenderer the query expression xml renderer
     * @param constraintColumnNameProvider the constraint column name provider
     */
    public MySQLReportingQueryExecutionEngineService(DataSource dataSource,
                                                     QueryExpressionXMLRenderer queryExpressionXMLRenderer,
                                                     ConstraintColumnNameProvider constraintColumnNameProvider) {
        super(dataSource, queryExpressionXMLRenderer, constraintColumnNameProvider);
    }

    /**
     * Populate table for a given QueryComponentExpression
     *
     * @param componentExpression the QueryComponentExpression
     * @param collection the collection
     * @param tableName the table name
     *
     * @throws java.sql.SQLException the SQL exception
     */
    public void populateTableForQueryComponentExpression(String tableName, QueryComponentExpression componentExpression,
                                                         QueryStatisticsCollection collection) throws SQLException{

        /*
           * populate the temp_table created with patient_id, concept_id, entry_time as
           * default with an extract from clinical_entry_table based on the included term
           * in the sub query object. If the subquery is set to subsume all descendants of
           * the included term, we handle this as well.
           * If excluded terms are specified, then we have two options:
           * 1. to exclude them in the statement that populates the temp table
           * 2. to delete the them once the temp table has been populated by all descendants
           *
           * This method uses option 2
           */

            // get concept hierarchy table
            String hierarchyTableName = getTableNameForSubtypeHierarchy(componentExpression.getIncludedConstraint(),
                    componentExpression.getExcludedConstraints(),
                    collection);
            // handle constraints
            String additionalCriteriaString = getSQLForConstraints(componentExpression);
            // populate temp table
            String populateTableString = "" +
                    "INSERT INTO "+tableName+" (PATIENT_ID, CONCEPT_ID, ENTRY_TIME, FREE_TEXT_ENTRY) " +
                    "	SELECT ENTRY."+getPatientIdColumnName()+" , "+
                        "ENTRY."+getConceptIdColumnName()+" , "+
                        "ENTRY."+getEntryTimeColumnName()+" , "+
                        "ENTRY."+getFreeTextEntryColumnName()+
                    " FROM "+
                    getSchemaName()+"."+getEntryTableName()+" ENTRY "+
                    " INNER JOIN "+
                    getSchemaName()+"."+hierarchyTableName+" HIERARCHY "+
                    " ON "+
                    "ENTRY."+getConceptIdColumnName()+" = HIERARCHY.CONCEPT_ID "+additionalCriteriaString;

            if (logger.isDebugEnabled()) {
                logger.debug("Value of populateTableString : "+ populateTableString);
            }
            Statement st = connection.createStatement();
            collection.getCommentedSteps().add("Populating table "+tableName+" using statement : ");
            st.execute(populateTableString);
            collection.getCommentedSteps().add(populateTableString);
            collection.getSqlSteps().add(populateTableString);

            // close statement
            st.close();
        logger.debug("completed populating table for subytpe hierarchy");
    }

    /**
     * Gets the sQL for constraints.
     *
     * @param componentExpression the QueryComponentExpression
     *
     * @return the sQL for constraints
     */
    private String getSQLForConstraints(QueryComponentExpression componentExpression){

        String sqlString = "";
        List<Constraint> constraints = new ArrayList<Constraint>(componentExpression.getAdditionalConstraints());
        if (constraints.size() == 1)
        {
            sqlString = " AND "+getSQLForConstraint(constraints.get(0));
        }
        else
        {
            // loop throu constraints and get string
            for(Constraint constraint  : constraints)
            {
                sqlString = sqlString+" AND "+getSQLForConstraint(constraint);
            }
        }

        return sqlString;
    }

    /**
     * Gets the sQL for constraint.
     *
     * @param constraint the constraint
     *
     * @return the sQL for constraint
     */
    private String getSQLForConstraint(Constraint constraint){

        String sqlString = "";
        String columnName = getConstraintColumnNameProvider().getColumnName(constraint);
        // generate sql based on type of constraint passed
        if(constraint instanceof DataConstantConstraint)
        {
            DataConstantConstraint constantConstraint = (DataConstantConstraint) constraint;
            sqlString = columnName+" = "+constantConstraint.asDouble();
        }
        else if (constraint instanceof DataRangeFacetConstraint)
        {
            DataRangeFacetConstraint facetConstraint = (DataRangeFacetConstraint) constraint;
            // generate operator based on facet vocabulary
            RangeFacetVocabulary facet = facetConstraint.getFacet();
            if(facet == RangeFacetVocabulary.MAX_EXCLUSIVE)
            {
                sqlString = columnName+" < "+facetConstraint.asDouble();
            }
            else if (facet == RangeFacetVocabulary.MAX_INCLUSIVE)
            {
                sqlString = columnName+" <= "+facetConstraint.asDouble();
            }
            else if (facet == RangeFacetVocabulary.MIN_EXCLUSIVE)
            {
                sqlString = columnName+" > "+facetConstraint.asDouble();
            }
            else if (facet == RangeFacetVocabulary.MIN_INCLUSIVE)
            {
                sqlString = columnName+" >= "+facetConstraint.asDouble();
            }
        }
        else if (constraint instanceof DataRangeConstraint)
        {
            DataRangeConstraint rangeConstraint = (DataRangeConstraint) constraint;
            List<DataRangeFacetConstraint> facets = new ArrayList<DataRangeFacetConstraint>(rangeConstraint.getFacets());
            sqlString = columnName+" BETWEEN "+facets.get(0).asDouble()+" AND "+facets.get(1).asDouble();
        }
        else if (constraint instanceof TemporalConstantConstraint)
        {
            TemporalConstantConstraint temporalConstantConstraint = (TemporalConstantConstraint) constraint;
            Date sqlDate = new Date(temporalConstantConstraint.date().getTime());
            sqlString = "c."+getEntryTimeColumnName()+" = "+sqlDate;
        }

        return sqlString;
    }

    public String createTableForQueryComponentExpression(QueryComponentExpression componentExpression) throws SQLException{

        // use the id of the sub query to create and name temp table
        String tableName = "QC_TABLE_"+getQueryComponentCounter();
        long startTime = Calendar.getInstance().getTimeInMillis();
        QueryStatisticsCollection collection = new QueryStatisticsCollection(componentExpression, getQueryExpressionXMLRenderer());
        logger.debug("Value of strategy : " + getStrategy());
        if (getStrategy() != TableCreationStrategy.VIEW)
        {
            String tableType = getTableType();
            logger.debug("Value of tableType : " + tableType);
            String tableCreateString = "CREATE " + tableType + " "
                    + tableName + " " + "("
                    + "PATIENT_ID "+patientIdCoulumnDef+" NOT NULL, "
                    + "CONCEPT_ID "+conceptIdCoulumnDef+" NOT NULL, "
                    + "ENTRY_TIME DATE NOT NULL, "
                    + "FREE_TEXT_ENTRY "+freeTextEntryCoulumnDef
                    + " )";

            // create table
            String[] indexCols = {"PATIENT_ID", "CONCEPT_ID"};
            createTable(tableName, tableCreateString, true, indexCols , collection);

            // populate table
            populateTableForQueryComponentExpression(tableName, componentExpression, collection);
        }
        else
        {
            throw new UnsupportedOperationException("TEMP_TABLE is the only table creation strategy supported!");
        }

        // increment queryComponentCounter
        int x = getQueryComponentCounter()+1;
        setQueryComponentCounter(x);
        // store query statistics if engine is set to debug mode
        checkAndPopulateQueryStatistics(startTime, tableName, collection, componentExpression);

        if (logger.isDebugEnabled()) {
            logger.debug("Returning table name for query component expression");
        }
        return tableName;
    }

    private String getTableNameForSubtypeHierarchy(TerminologyConstraint includedConstraint,
                                                   Collection<TerminologyConstraint> excludedConstraints,
                                                   QueryStatisticsCollection collection) throws SQLException{

        String id = includedConstraint.getUuid().toString().replace('-', '_');
        String tableName = "Hierarchy_"+id;
        if (logger.isDebugEnabled()) {
            logger.debug("Value of tableName : " + tableName);
        }

        String tableCreateString = "" +
                "CREATE TABLE "+tableName+" " +
                "(" +
                "CONCEPT_ID "+conceptIdCoulumnDef+" NOT NULL " +
                ")";

        // create table with index as needed
        String[] indexCols = {"CONCEPT_ID"};
        createTable(tableName, tableCreateString, false, indexCols, collection);

        String populateString = "INSERT INTO "+tableName+" VALUES (?)" ;
        PreparedStatement ps = connection.prepareStatement(populateString);
        if (EngineStatus.DEBUG == getStatus()) {
            collection.getCommentedSteps().add("Populating table using statement : ");
            collection.getCommentedSteps().add(populateString);
        }

        // get subtypes for included constraint
        for(UUID subtypeId : subtypeGetter.getSubTypeIdsForConstraint(includedConstraint))
        {
            // add subtypeId to table
            ps.setBytes(1, asByteArray(subtypeId));
            ps.executeUpdate();
            if (EngineStatus.DEBUG == getStatus()) {
                collection.getCommentedSteps().add("Added descendant concept "+subtypeId+" to table "+tableName+" using statement :");
                collection.getCommentedSteps().add("INSERT INTO "+tableName+" VALUES ("+subtypeId+")");
                collection.getSqlSteps().add("INSERT INTO "+tableName+" VALUES ("+subtypeId+")");
            }
        }

        // close statement
        ps.close();
        if (logger.isDebugEnabled()) {
            logger.debug("Created table for subtype hierarchy. Table name = "+tableName);
        }

        /*
           *  handle excluded terms specified recursively since excluded terms might have their
           *  own subsumption strategy specified
           */

        for(TerminologyConstraint excludedConstraint : excludedConstraints)
        {
            // recursive call to get table name
            String excludedTermsTable = getTableNameForSubtypeHierarchy(excludedConstraint,
                                    Collections.<TerminologyConstraint>emptySet(), collection);
            // go through contents of table and remove entries
            PreparedStatement deleteStm = connection.prepareStatement("" +
                    "DELETE FROM "+tableName+" WHERE CONCEPT_ID  = ?");
            ResultSet terms = connection.createStatement().executeQuery("" +
                    "SELECT CONCEPT_ID FROM "+excludedTermsTable);
            while(terms.next())
            {
                String exclTermId = terms.getString(1);
                if (logger.isDebugEnabled()) {
                    logger.debug("Deleting excluded term : " + exclTermId);
                }
                deleteStm.setString(1, exclTermId);
                deleteStm.executeUpdate();
            }

            // close statements
            terms.close();
            deleteStm.close();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Removed excluded terms from table name : "+tableName);
        }
        // add table to temp tables file list
        updateTablesConfigFile(tableName);

        return tableName;
    }

    public static byte[] asByteArray(UUID uuid) {

        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
                buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
                buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }

        return buffer;

    }

    /**
     * Creates the table for intersection object.
     *
     * @param intersectionObject the intersection object
     *
     * @return the string
     *
     * @throws java.sql.SQLException the SQL exception
     */
    public String createTableForIntersectionObject(QueryIntersectionExpression intersectionObject) throws SQLException{

        long startTime = Calendar.getInstance().getTimeInMillis();
        QueryStatisticsCollection collection = new QueryStatisticsCollection(intersectionObject, getQueryExpressionXMLRenderer());
        List<String> tableNames = new ArrayList<String>();

        // assume that all contained objects are just sub query objects
        Set<QueryExpression> containedExpressions = new HashSet<QueryExpression>(intersectionObject.getContainedExpressions());
        for(QueryExpression expression :containedExpressions)
        {
            if (expression.getRunTimeStatus() !=  QueryRunTimeStatus.SKIP)
            {
                if (expression instanceof QueryComponentExpression) {
                    // create table and add table name
                    String tableName = createTableForQueryComponentExpression((QueryComponentExpression) expression);
                    // add table name to set
                    tableNames.add(tableName);
                }
                else if (expression instanceof QueryUnionExpression) {
                    QueryUnionExpression unionObject = (QueryUnionExpression) expression;
                    // create table for union object and get name
                    String tableName = createTableForUnionObject(unionObject);
                    // add to table names
                    tableNames.add(tableName);
                }
                else if (expression instanceof QueryIntersectionExpression) {
                    QueryIntersectionExpression innerIntersectionObject = (QueryIntersectionExpression) expression;
                    // create table for intersection object
                    String tableName = createTableForIntersectionObject(innerIntersectionObject);
                    // add to table names
                    tableNames.add(tableName);
                }
            }
        }

        /*
           * copy contents of sub query table 1 into a new intersection table.
           * then loop through the rest of the sub query tables and perform a join
           * between the intersection table and the sub query table.
           */

        String tableName = "TEMP_INTERSECTION_"+getIntersectionObjectCounter();
        if (logger.isDebugEnabled()) {
            logger.debug("Creating table : " + tableName);
        }
        TableCreationStrategy strategy = getStrategy();
        if (strategy != TableCreationStrategy.VIEW)
        {
            //		String tableType = getTableType();
            String tableCreateString = "" + "CREATE TABLE " + tableName + " " + "("
                    + "PATIENT_ID "+patientIdCoulumnDef+" NOT NULL "
                    + ")";

            // create table performing deletions as appropriate
            String[] indexColumns = {"PATIENT_ID"};
            createTable(tableName, tableCreateString, false, indexColumns, collection);

            Statement st1 = connection.createStatement();
            // loop through table names and perform joins
            for (int i = 0; i < tableNames.size(); i++)
            {
                String colNames = "";
                if (getTableColumnsMap().containsKey(tableName)) {
                    for (String col : getTableColumnsMap().get(tableName)) {
                        colNames = colNames + " , " + col;
                    }
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("Value of colNames = " + colNames);
                }
                String conceptColumnName = "Concept_"+tableName+"_"+i;
                String timeColumnName = "Entry_Time_"+tableName+"_"+i;
                String freeTextEntryColumnName = "Free_Text_Entry_"+tableName+"_"+i;
                // add column names to table columns map
                if(getTableColumnsMap().containsKey(tableName))
                {
                    getTableColumnsMap().get(tableName).add(conceptColumnName);
                    getTableColumnsMap().get(tableName).add(timeColumnName);
                    getTableColumnsMap().get(tableName).add(freeTextEntryColumnName);
                }
                else
                {
                    List<String> list = new ArrayList<String>();
                    list.add(conceptColumnName);
                    list.add(timeColumnName);
                    list.add(freeTextEntryColumnName);
                    getTableColumnsMap().put(tableName, list);
                }
                logger.debug("Value of tableColumnsMap.get(key) : " + getTableColumnsMap().get(tableName));

                // append column to table
                String addColumnStatement =  "ALTER TABLE " + tableName
                        + " ADD COLUMN "+conceptColumnName
                        + " "+conceptIdCoulumnDef+" NOT NULL "
                        + " AFTER PATIENT_ID, "
                        + " ADD COLUMN "+timeColumnName+" DATE NOT NULL "
                        + " AFTER "+conceptColumnName+" , "
                        + "ADD COLUMN "+freeTextEntryColumnName+" "+freeTextEntryCoulumnDef
                        + " AFTER "+timeColumnName;
                logger.debug("Value of addColumnStatement : "+ addColumnStatement);
                st1.execute(addColumnStatement);

                if (i ==0)
                {
                    // copy contents of sub query table 1
                    String copyStatement = "INSERT INTO " + tableName + " SELECT * FROM "+ tableNames.get(i);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Inserting contents of " + tableNames.get(0) + " into "+ tableName);
                        logger.debug("Value of copyStatement : " + copyStatement);
                    }
                    collection.getCommentedSteps().add("Inserting contents of " + tableNames.get(0) + " into "+ tableName);
                    collection.getCommentedSteps().add("Copy Statement : " + copyStatement);
                    st1.execute(copyStatement);
                    collection.getSqlSteps().add(copyStatement);
                }
                else
                {
                    String currentTable = tableNames.get(i);

                    if (logger.isDebugEnabled()) {
                        logger.debug("Joining table : " + currentTable + " with " + tableName);
                    }
                    collection.getCommentedSteps().add(
                            "Joining table : " + currentTable + " with "
                                    + tableName);

                    //drop temp table if it exits
                    dropTableOrViewIfExists("TEMP_INTERSECTION_TABLE", st1, collection);

                    // copy contents of union table into a temp table
                    collection.getCommentedSteps().add("Adding contents of " + tableName
                            + " to a temp table ");
                    st1.execute("CREATE TABLE TEMP_INTERSECTION_TABLE LIKE "
                            + tableName);
                    st1.execute("INSERT INTO TEMP_INTERSECTION_TABLE SELECT * FROM "
                            + tableName);
                    collection.getCommentedSteps().add("Inserting into TEMP_INTERSECTION_TABLE using statement ");
                    collection.getCommentedSteps().add("INSERT INTO TEMP_INTERSECTION_TABLE SELECT * FROM "
                            + tableName);
                    collection.getSqlSteps().add("INSERT INTO TEMP_INTERSECTION_TABLE SELECT * FROM "
                            + tableName);
                    // clear contents of intersection table
                    st1.execute("DELETE FROM " + tableName);
                    collection.getCommentedSteps().add("Deleting all rows in " + tableName);
                    collection.getCommentedSteps().add("DELETE FROM " + tableName);
                    collection.getSqlSteps().add("DELETE FROM " + tableName);
                    collection.getCommentedSteps().add(
                            "Creating join between TEMP_INTERSECTION_TABLE and next table in loop");
                    // create union between TEMP_INTERSECTION_TABLE and next table in loop
                    String joinString = "" + "INSERT INTO "
                            + tableName+ " SELECT c.PATIENT_ID , c.CONCEPT_ID, C.ENTRY_TIME, c.FREE_TEXT_ENTRY "
                            +colNames
                            + " FROM TEMP_INTERSECTION_TABLE t "
                            + " INNER JOIN " + currentTable + " c "
                            + " ON t.PATIENT_ID = "
                            + " c.PATIENT_ID";

                    // get operator type and add conditional based on type
                    QueryOperatorType operator = intersectionObject.getOperator();
                    if(operator == QueryOperatorType.BEFORE)
                    {
                        joinString = joinString+" AND t."+timeColumnName+" < c.entry_time";
                    }
                    else if(operator == QueryOperatorType.AFTER)
                    {
                        joinString = joinString+" AND t."+timeColumnName+" > c.entry_time";
                    }

                    if (logger.isDebugEnabled()) {
                        logger.debug("Value of joinString : " + joinString);
                    }
                    
                    collection.getCommentedSteps().add(
                            "Creating join between " + tableName + " and "
                                    + currentTable + " using statement :");
                    collection.getCommentedSteps().add(joinString);
                    collection.getSqlSteps().add(joinString);
                    // execute joinString
                    st1.execute(joinString);
                }
            }
            // close statement
            st1.close();
        }
        else
        {
            throw new UnsupportedOperationException("This version of Sappheiros does not support VIEWS. Please use " +
                    "TEMP TABLE instead as table creation strategy.");

            /*
                Uncomment text below if support for Views needed in T3
             */

//            // copy value of first table into a view
//            Statement st1 = connection.createStatement();
//
//            String previousViewName = "";
//            String currentViewName = "";
//            String colNames = "";
//            // copy contents of this view into another view
//            for(int i=0; i<tableNames.size(); i++)
//            {
//                String prevColNames = "";
//                if (tableColumnsMap.containsKey(tableName)) {
//                    for (String col : tableColumnsMap.get(tableName)) {
//                        prevColNames = prevColNames + " , " + col;
//                    }
//                }
//                logger.debug("Value of prevColNames : " + prevColNames);
//
//                String conceptColumnName = "Concept_"+tableName+"_"+i;
//                String timeColumnName = "Entry_Time_"+tableName+"_"+i;
//                String freeTextEntryColumnName = "Free_Text_Entry_"+tableName+"_"+i;
//                // add column names to table columns map
//                if(tableColumnsMap.containsKey(tableName))
//                {
//                    tableColumnsMap.get(tableName).add(conceptColumnName);
//                    tableColumnsMap.get(tableName).add(timeColumnName);
//                    tableColumnsMap.get(tableName).add(freeTextEntryColumnName);
//                }
//                else
//                {
//                    List<String> list = new ArrayList<String>();
//                    list.add(conceptColumnName);
//                    list.add(timeColumnName);
//                    list.add(freeTextEntryColumnName);
//                    tableColumnsMap.put(tableName, list);
//                }
//                logger.debug("Value of tableColumnsMap.get(key) : " + tableColumnsMap.get(tableName));
//
//                colNames = "";
//                if (tableColumnsMap.containsKey(tableName)) {
//                    for (String col : tableColumnsMap.get(tableName)) {
//                        colNames = colNames + " , " + col;
//                    }
//                }
//                logger.debug("Value of colNames : " + colNames);
//
//                currentViewName = tableName+"_inner_intersection_"+i;
//                if (i == 0)
//                {
//                    String viewCreationString = ""
//                            + "CREATE ALGORITHM = MERGE VIEW "
//                            + currentViewName
//                            + " (PATIENT_ID "+colNames+" ) AS "
//                            + "(SELECT entry.patient_id, entry.concept_id, entry.entry_time , entry.free_text_entry "
//                            + "FROM " + tableNames.get(i) + " entry )";
//                    // drop table/view if it already exists
//                    dropTableOrViewIfExists(currentViewName, st1, collection);
//                    st1.execute(viewCreationString);
//                    collection.getCommentedSteps().add("Created table/view : " + currentViewName	+ " using statement : ");
//                    collection.getCommentedSteps().add(viewCreationString);
//                    collection.getSqlSteps().add(viewCreationString);
//                    // set current view name to previous view name
//                    previousViewName = currentViewName;
//                }
//                else	// create view as a join of current table and previous view
//                {
//                    String viewCreationString = ""
//                            + "CREATE ALGORITHM = MERGE VIEW "
//                            + currentViewName
//                            + " (PATIENT_ID "+colNames+") AS "
//                            + "SELECT entry.patient_id, entry.concept_id, entry.entry_time , entry.free_text_entry "
//                            +prevColNames
//                            + " FROM " + tableNames.get(i) + " entry "
//                            + " INNER JOIN "
//                            + previousViewName + " previous_view "
//                            + "ON previous_view.patient_id  = entry.patient_id";
//
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("Value of viewCreationString : "
//                                + viewCreationString);
//                    }
//                    // drop table/view if it already exists
//                    dropTableOrViewIfExists(currentViewName, st1, collection);
//                    st1.execute(viewCreationString);
//                    collection.getCommentedSteps().add("Created table/view : " + currentViewName+ " using statement : ");
//                    collection.getCommentedSteps().add(viewCreationString);
//                    collection.getSqlSteps().add(viewCreationString);
//                    // set current view name to previous view name
//                    previousViewName = currentViewName;
//                }
//            }
//
//            // add final current view to union table
//            String viewCreationString = ""
//                    + "CREATE ALGORITHM = MERGE VIEW "
//                    + tableName
//                    + " (PATIENT_ID "+colNames+") AS "
//                    + "(SELECT entry.patient_id "+colNames
//                    + " FROM " + currentViewName + " entry )";
//            // drop table/view if it already exists
//            dropTableOrViewIfExists(tableName, st1, collection);
//            st1.execute(viewCreationString);
//            collection.getCommentedSteps().add("Created table/view : " + tableName	+ " using statement : ");
//            collection.getCommentedSteps().add(viewCreationString);
//            collection.getSqlSteps().add(viewCreationString);
        }
        // increment intersection object counter
        int x = getIntersectionObjectCounter()+1;
        setIntersectionObjectCounter(x);
        // store query statistics if engine is set to debug mode
        checkAndPopulateQueryStatistics(startTime, tableName, collection, intersectionObject);

        if (logger.isDebugEnabled()) {
            logger.debug("Value of tableName in createTableForIntersectionObject : " + tableName);
        }
        return tableName;
    }

    /**
     * Creates the table for union object.
     *
     * @param unionObject the union object
     *
     * @return the string
     *
     * @throws java.sql.SQLException the SQL exception
     */
    public String createTableForUnionObject(QueryUnionExpression unionObject) throws SQLException{

        long startTime = Calendar.getInstance().getTimeInMillis();
        QueryStatisticsCollection collection = new QueryStatisticsCollection(
                unionObject, getQueryExpressionXMLRenderer());
        List<String> tableNames = new ArrayList<String>();
        // assume that all contained objects are just sub query objects
        Set<QueryExpression> containedExpressions = new HashSet<QueryExpression>(unionObject.getContainedExpressions());
        for(QueryExpression expression :containedExpressions)
        {
            if (unionObject.getRunTimeStatus() != QueryRunTimeStatus.SKIP)
            {
                if (expression instanceof QueryComponentExpression) {
                    // create table and add table name
                    String tableName = createTableForQueryComponentExpression((QueryComponentExpression) expression);
                    // add table name to set
                    tableNames.add(tableName);
                }
                else if (expression instanceof QueryUnionExpression) {
                    QueryUnionExpression innerUnionObject = (QueryUnionExpression) expression;
                    // create table for union object and get name
                    String tableName = createTableForUnionObject(innerUnionObject);
                    // add to table names
                    tableNames.add(tableName);
                }
                else if (expression instanceof QueryIntersectionExpression) {
                    QueryIntersectionExpression intersectionObject = (QueryIntersectionExpression) expression;
                    // create table for intersection object
                    String tableName = createTableForIntersectionObject(intersectionObject);
                    // add to table names
                    tableNames.add(tableName);
                }
            }
        }

        // now create a single table for the union object
        String tableName = "TEMP_UNION_"+getUnionObjectCounter();
        if (logger.isDebugEnabled()) {
            logger.debug("Created table : " + tableName);
        }

        TableCreationStrategy strategy = getStrategy();
        logger.debug("Value of strategy.name() : " + strategy.name());
        if (strategy != TableCreationStrategy.VIEW)
        {
            String tableCreateString = "" + "CREATE TABLE " + tableName + " "
                    + "( "
                    + "PATIENT_ID "+patientIdCoulumnDef+" NOT NULL, "
                    + "CONCEPT_ID "+conceptIdCoulumnDef+" NOT NULL, "
                    + "ENTRY_TIME DATE NOT NULL, "
                    + "FREE_TEXT_ENTRY "+freeTextEntryCoulumnDef
                    + " )";

            // create table performing appropriate deletions
            String[] indexColumns = {"PATIENT_ID", "CONCEPT_ID"};
            createTable(tableName, tableCreateString, true, indexColumns, collection);
            // populate union table
            collection.getCommentedSteps().add("Merging contents of all tables in the union object");
            Statement st2 = connection.createStatement();
            for (String tName : tableNames) {
                String unionString = "INSERT INTO " + tableName
                        + " (SELECT * FROM " + tName + ")";
                logger.debug("Value of unionString : " + unionString);
                collection.getCommentedSteps().add("Table contents merged using statement : "
                        + unionString);
                st2.execute(unionString);
                collection.getSqlSteps().add(unionString);
            }
            //close statement
            st2.close();
        }
        else
        {
            throw new UnsupportedOperationException("This version of Sappheiros does not support VIEWS. Please use " +
                    "TEMP TABLE instead as table creation strategy.");

            /*
                Uncomment text below if support for Views needed in T3
             */

//            // copy value of first table into a view
//            Statement st1 = connection.createStatement();
//
//            String previousViewName = "";
//            String currentViewName = "";
//            // copy contents of this view into another view
//            for(int i=0; i<tableNames.size(); i++)
//            {
//                currentViewName = tableName+"_inner_union_"+i;
//                if (i == 0)
//                {
//                    String viewCreationString = ""
//                            + "CREATE ALGORITHM = MERGE VIEW "
//                            + currentViewName
//                            + " (PATIENT_ID, CONCEPT_ID, ENTRY_TIME, FREE_TEXT_ENTRY ) AS "
//                            + "(SELECT entry.patient_id, entry.concept_id, entry.entry_time, entry.free_text_entry "
//                            + "FROM " + tableNames.get(i) + " entry )";
//                    // drop table/view if it already exists
//                    dropTableOrViewIfExists(currentViewName, st1, collection);
//                    st1.execute(viewCreationString);
//                    collection.getCommentedSteps().add("Created table/view : " + currentViewName	+ " using statement : ");
//                    collection.getCommentedSteps().add(viewCreationString);
//                    collection.getSqlSteps().add(viewCreationString);
//                    // set current view name to previous view name
//                    previousViewName = currentViewName;
//                }
//                else	// create view as a join of current table and previous view
//                {
//                    String viewCreationString = ""
//                            + "CREATE ALGORITHM = MERGE VIEW "
//                            + currentViewName
//                            + " (PATIENT_ID, CONCEPT_ID, ENTRY_TIME, FREE_TEXT_ENTRY ) AS "
//                            + "(SELECT entry.patient_id, entry.concept_id, entry.entry_time, entry.free_text_entry "
//                            + "FROM " + tableNames.get(i) + " entry ) "
//                            + "UNION "
//                            + "(SELECT previous_view.patient_id, previous_view.concept_id, previous_view.entry_time, previous_view.free_text_entry "
//                            + "FROM " + previousViewName + " previous_view ) "
//                            + "";
//
//                    // drop table/view if it already exists
//                    dropTableOrViewIfExists(currentViewName, st1, collection);
//                    st1.execute(viewCreationString);
//                    collection.getCommentedSteps().add("Created table/view " + currentViewName+ " using statement : ");
//                    collection.getCommentedSteps().add(viewCreationString);
//                    collection.getSqlSteps().add(viewCreationString);
//                    // set current view name to previous view name
//                    previousViewName = currentViewName;
//                }
//            }
//
//            // add final current view to union table
//            String viewCreationString = ""
//                    + "CREATE ALGORITHM = MERGE VIEW "
//                    + tableName
//                    + " (PATIENT_ID, CONCEPT_ID, ENTRY_TIME, FREE_TEXT_ENTRY ) AS "
//                    + "(SELECT entry.patient_id, entry.concept_id, entry.entry_time, entry.free_text_entry "
//                    + "FROM " + currentViewName + " entry )";
//            // drop table/view if it already exists
//            dropTableOrViewIfExists(tableName, st1, collection);
//            st1.execute(viewCreationString);
//            collection.getCommentedSteps().add("Created table/view " + tableName	+ " using statement : ");
//            collection.getCommentedSteps().add(viewCreationString);
//            collection.getSqlSteps().add(viewCreationString);

        }
        // increment union object counter
        int x = getUnionObjectCounter()+1;
        setUnionObjectCounter(x+1);
        // store query statistics if engine is set to debug mode
        checkAndPopulateQueryStatistics(startTime, tableName, collection, unionObject);

        if (logger.isDebugEnabled()) {
            logger.debug("Value of tableName in createTableForUnionObject : " + tableName);
        }
        return tableName;
    }

    public synchronized void setSubtypeGetter(uk.nhs.cfh.dsp.srth.query.transform.SubtypeGetter subtypeGetter) {
        this.subtypeGetter = subtypeGetter;
    }
}
