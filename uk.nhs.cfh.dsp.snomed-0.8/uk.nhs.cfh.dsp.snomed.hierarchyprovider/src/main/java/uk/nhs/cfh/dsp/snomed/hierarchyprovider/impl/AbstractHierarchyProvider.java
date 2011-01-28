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
package uk.nhs.cfh.dsp.snomed.hierarchyprovider.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 7, 2010 at 12:04:55 AM
 */
public class AbstractHierarchyProvider implements HierarchyProvider {

    /** The connection. */
    private Connection connection;

    /** The get parents statement. */
    protected PreparedStatement getParentsStatement;

    /** The get children statement. */
    protected PreparedStatement getChildrenStatement;

    private String schemaName;
    private String relationshipTableName;
    private String sourceColumnName;
    private String targetColumnName;
    private String attributeName;
    private String attributeValue;

    /** The logger. */
    private static Log logger = LogFactory.getLog(TransitiveClosureTableBasedHierarchyProvider.class);

    /** The data source. */
    private DataSource dataSource;
    protected static final String NULL_ARG_MSG = "Argument passed can not be null : ";

        /**
     * Instantiates a new hierarchy provider impl.
     *
     * @param dataSource the data source
     */
    public AbstractHierarchyProvider(DataSource dataSource){
        try
        {
            this.connection = dataSource.getConnection();
            initialiseStatements();
        }
        catch (SQLException e) {
            logger.warn("Error obtaining connection from data source. " +
                    "Nested exception is : " + e.fillInStackTrace());
        }
    }

    /**
     * Instantiates a new hierarchy provider impl.
     */
    public AbstractHierarchyProvider() {

    }

    /**
     * Instantiates a new hierarchy provider impl.
     *
     * @param connection the connection
     */
    public AbstractHierarchyProvider(Connection connection) {
        if (connection != null)
        {
            this.connection = connection;            
            // create prepared statements
            initialiseStatements();
        } else {
            throw new IllegalArgumentException(NULL_ARG_MSG+connection);
        }
    }

    /**
     * Initialise statements.
     */
    protected void initialiseStatements() {

        try
        {
            getParentsStatement = connection.prepareStatement(""
                    + "SELECT DISTINCT "+targetColumnName+" "
                    + "FROM "+schemaName+"."+ relationshipTableName +" WHERE "+attributeName
                    +" = '"+attributeValue+"' AND "+sourceColumnName+" = ?");

            getChildrenStatement = connection.prepareStatement(""
                    + "SELECT DISTINCT "+sourceColumnName+" "
                    + "FROM "+schemaName+"."+ relationshipTableName +" WHERE "+attributeName
                    +" = '"+attributeValue+"' AND "+targetColumnName+" = ?");

        } catch (SQLException e) {
            logger.warn("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider#getParents(java.lang.String)
     */
    public Set<String> getParents(String conceptId) {

        Set<String> parentIds = new HashSet<String>();
        if(conceptId != null && ! "".equalsIgnoreCase(conceptId))
        {
            // get concept id and set parameter
            try
            {
                getParentsStatement.setString(1, conceptId);
                ResultSet rs = getParentsStatement.executeQuery();
                while(rs.next())
                {
                    String parentID = rs.getString(1);
                    parentIds.add(parentID);
                }

            } catch (SQLException e) {
                logger.warn("Nested exception is : " + e.fillInStackTrace());
            }

            return parentIds;
        }
        else
        {
            throw new IllegalArgumentException(NULL_ARG_MSG+conceptId);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider#getChildren(java.lang.String)
     */
    public Set<String> getChildren(String conceptId) {

        Set<String> childrenIds = new HashSet<String>();
        if(conceptId != null && ! "".equalsIgnoreCase(conceptId))
        {
            // get concept id and set parameter
            try
            {
                getChildrenStatement.setString(1, conceptId);
                ResultSet rs = getChildrenStatement.executeQuery();
                while(rs.next())
                {
                    String childID = rs.getString(1);
                    childrenIds.add(childID);
                }

            } catch (SQLException e) {
                logger.warn("Nested exception is : " + e.fillInStackTrace());
            }

            return childrenIds;
        }
        else
        {
            throw new IllegalArgumentException(NULL_ARG_MSG+conceptId);
        }
    }

    /**
     * Gets the descendants.
     *
     * @param conceptId the concept id
     * @return the descendants
     */
    public Set<String> getDescendants(String conceptId) {
        throw new UnsupportedOperationException("This method has not been implemented.");
    }

    /**
     * Gets the ancestors.
     *
     * @param conceptId the concept id
     * @return the ancestors
     */
    public Set<String> getAncestors(String conceptId) {
        throw new UnsupportedOperationException("This method has not been implemented.");
    }

    /**
     * Sets the data source.
     *
     * @param dataSource the new data source
     */
    public synchronized void setDataSource(DataSource dataSource) {

        try
        {
            this.dataSource = dataSource;
            this.connection = dataSource.getConnection();
        }
        catch (SQLException e) {
            logger.warn("Error obtaining connection from data source. " +
                    "Nested exception is : " + e.fillInStackTrace());
        }
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setRelationshipTableName(String relationshipTableName) {
        this.relationshipTableName = relationshipTableName;
    }

    public void setSourceColumnName(String sourceColumnName) {
        this.sourceColumnName = sourceColumnName;
    }

    public void setTargetColumnName(String targetColumnName) {
        this.targetColumnName = targetColumnName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getRelationshipTableName() {
        return relationshipTableName;
    }

    public String getSourceColumnName() {
        return sourceColumnName;
    }

    public String getTargetColumnName() {
        return targetColumnName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }
}
