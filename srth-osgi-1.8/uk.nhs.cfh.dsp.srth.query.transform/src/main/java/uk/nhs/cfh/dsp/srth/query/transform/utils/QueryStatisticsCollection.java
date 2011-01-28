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
package uk.nhs.cfh.dsp.srth.query.transform.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom object that collates query time, result set size and other relevant
 * details for every {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression} enclosed within a
 *  {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}.
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Apr 5, 2009 at 8:28:20 PM
 * <br>
 */
public class QueryStatisticsCollection {

	/** The expression. */
	private QueryExpression expression;
	
	/** The query time. */
	private long queryTime;
	
	/** The table name. */
	private String tableName;
	
	/** The result set size. */
	private int resultSetSize;
	
	/** The logger. */
	private static Log logger = LogFactory.getLog(QueryStatisticsCollection.class);
	
	/** The commented steps. */
	private List<String> commentedSteps = new ArrayList<String>();
	
	/** The sql steps. */
	private List<String> sqlSteps = new ArrayList<String>();
    
    /** The query expression xml renderer. */
    private QueryExpressionXMLRenderer queryExpressionXMLRenderer;
	
	/** The total records. */
	private long totalRecords = 0;
    private int distinctPatientsCount = 0;

    /**
     * Instantiates a new query statistics collection.
     * 
     * @param expression the expression
     * @param queryExpressionXMLRenderer the query expression xml renderer
     */
    public QueryStatisticsCollection(QueryExpression expression,
                                     QueryExpressionXMLRenderer queryExpressionXMLRenderer) {
        this.expression = expression;
        this.queryExpressionXMLRenderer = queryExpressionXMLRenderer;
    }

    /**
     * Gets the sql steps.
     * 
     * @return the sql steps
     */
    public List<String> getSqlSteps() {
		return sqlSteps;
	}

	/**
	 * Sets the sql steps.
	 * 
	 * @param sqlSteps the new sql steps
	 */
	public void setSqlSteps(List<String> sqlSteps) {
		this.sqlSteps = sqlSteps;
	}

	/**
	 * Gets the expression.
	 * 
	 * @return the expression
	 */
	public QueryExpression getExpression() {
		return expression;
	}

	/**
	 * Sets the expression.
	 * 
	 * @param expression the new expression
	 */
	public void setExpression(QueryExpression expression) {
		this.expression = expression;
	}

	/**
	 * Gets the query time.
	 * 
	 * @return the query time
	 */
	public long getQueryTime() {
		return queryTime;
	}

	/**
	 * Sets the query time.
	 * 
	 * @param queryTime the new query time
	 */
	public void setQueryTime(long queryTime) {
		this.queryTime = queryTime;
	}

	/**
	 * Gets the table name.
	 * 
	 * @return the table name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Sets the table name.
	 * 
	 * @param tableName the new table name
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Gets the result set size.
	 * 
	 * @return the result set size
	 */
	public int getResultSetSize() {
		return resultSetSize;
	}

	/**
	 * Sets the result set size.
	 * 
	 * @param resultSetSize the new result set size
	 */
	public void setResultSetSize(int resultSetSize) {
		this.resultSetSize = resultSetSize;
	}

	/**
	 * Gets the result set size.
	 * 
	 * @param connection the connection
	 * 
	 * @return the result set size
	 */
	public int getResultSetSize(Connection connection){

		int count = 0;
		// create statement
		try 
		{
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("" +
					"SELECT COUNT(PATIENT_ID) FROM "+tableName);
			// this will return only one value
			while(rs.next())
			{
				count = rs.getInt(1);
				resultSetSize = count;
			}
			
			rs.close();
			
		} catch (SQLException e) {
			logger.warn(e.fillInStackTrace());
		}
		
		return count;
	}

	/**
	 * Gets the commented steps.
	 * 
	 * @return the commented steps
	 */
	public List<String> getCommentedSteps() {
		return commentedSteps;
	}

	/**
	 * Sets the commented steps.
	 * 
	 * @param commentedSteps the new commented steps
	 */
	public void setCommentedSteps(List<String> commentedSteps) {
		this.commentedSteps = commentedSteps;
	}

	/**
	 * Gets the total records.
	 * 
	 * @return the total records
	 */
	public long getTotalRecords() {
		return totalRecords;
	}

	/**
	 * Sets the total records.
	 * 
	 * @param totalRecords the new total records
	 */
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

    /**
     * Gets the query expression xml renderer.
     * 
     * @return the query expression xml renderer
     */
    public QueryExpressionXMLRenderer getQueryExpressionXMLRenderer() {
        return queryExpressionXMLRenderer;
    }

    /**
     * Sets the query expression xml renderer.
     * 
     * @param queryExpressionXMLRenderer the new query expression xml renderer
     */
    public void setQueryExpressionXMLRenderer(QueryExpressionXMLRenderer queryExpressionXMLRenderer) {
        this.queryExpressionXMLRenderer = queryExpressionXMLRenderer;
    }

    public int getDistinctPatientsCount() {
        return distinctPatientsCount;
    }

    public void setDistinctPatientsCount(int distinctPatientsCount) {
        this.distinctPatientsCount = distinctPatientsCount;
    }

    /**
     * Gets the as xml element.
     * 
     * @return the as xml element
     */
    public Element getAsXMLElement(){
		Element element = new Element("Query_Statistics");
		// add stats as attributes
		element.setAttribute("query_time", String.valueOf(getQueryTime()));
		element.setAttribute("table_name", String.valueOf(getTableName()));
		element.setAttribute("result_set_size", String.valueOf(getResultSetSize()));
		// add sql step as element with children
		Element sqlStepsEle = new Element("SQL_Steps");
		for(String step : getSqlSteps())
		{
			Element ele = new Element("SQL_Step");
			ele.setText(step);
			// add to steps element
			sqlStepsEle.addContent(ele);
		}
		// add comments as element with children
		Element commentsEle = new Element("Commented_Steps");
		for(String comment : getCommentedSteps())
		{
			Element ele = new Element("Commented_Step");
			ele.setText(comment);
			// add to comments element
			commentsEle.addContent(ele);
		}
		
		// add steps to element
		element.addContent(sqlStepsEle);
		element.addContent(commentsEle);
		// add expression as child
		element.addContent(queryExpressionXMLRenderer.getAsXMLElement(getExpression()));
		
		return element;
	}
}
