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

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider} that uses a
 * transitive closure table to provide concept hierarchical relationships.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 9, 2009 at 4:00:25 PM
 */
public class TransitiveClosureTableBasedHierarchyProvider extends AbstractHierarchyProvider implements HierarchyProvider{

    /** The get ancestors statement. */
    private PreparedStatement getAncestorsStatement;
    
    /** The get descendants statement. */
    private PreparedStatement getDescendantsStatement;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(TransitiveClosureTableBasedHierarchyProvider.class);
    private String tcTableName;
    private String subTypeColumnName;
    private String superTypeColumnName;

    /**
     * Instantiates a new transitive closure table based hierarchy provider.
     *
     * @param dataSource the data source
     */
    public TransitiveClosureTableBasedHierarchyProvider(DataSource dataSource){
        super(dataSource);
    }

    /**
     * Instantiates a new transitive closure table based hierarchy provider.
     *
     * @param connection the connection
     */
    public TransitiveClosureTableBasedHierarchyProvider(Connection connection) {
        super(connection);
    }

    /**
     * Instantiates a new transitive closure table based hierarchy provider.
     */
    public TransitiveClosureTableBasedHierarchyProvider() {
    }

    /**
     * Initialise statements.
     */
    @Override
    public void initialiseStatements() {

        try
        {
            super.initialiseStatements();

            getAncestorsStatement = getConnection().prepareStatement(""
                    +"SELECT "+superTypeColumnName+" FROM "+getSchemaName()+"."+tcTableName
                    +" WHERE "+subTypeColumnName+" = ?");

            getDescendantsStatement = getConnection().prepareStatement(""
                    +"SELECT "+subTypeColumnName+" FROM "+getSchemaName()+"."+tcTableName
                    +" WHERE "+superTypeColumnName+" = ?");

        } catch (SQLException e) {
            logger.warn("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider#getDescendants(java.lang.String)
     */
    public Set<String> getDescendants(String conceptId) {

        Set<String> descendantIds = new HashSet<String>();
        if(conceptId != null && ! "".equalsIgnoreCase(conceptId))
        {
            // get concept id and set parameter
            try
            {
                getDescendantsStatement.setString(1, conceptId);
                ResultSet rs = getDescendantsStatement.executeQuery();
                while(rs.next())
                {
                    String descendantId = rs.getString(1);
                    descendantIds.add(descendantId);
                }

            } catch (SQLException e) {
                logger.warn("Nested exception is : " + e.fillInStackTrace());
            }

            return descendantIds;
        }
        else
        {
            throw new IllegalArgumentException(NULL_ARG_MSG+conceptId);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider#getAncestors(java.lang.String)
     */
    public Set<String> getAncestors(String conceptId) {

        Set<String> ancestorIds = new HashSet<String>();
        if(conceptId != null && ! "".equalsIgnoreCase(conceptId))
        {
            // get concept id and set parameter
            try
            {
                getAncestorsStatement.setString(1, conceptId);
                ResultSet rs = getAncestorsStatement.executeQuery();
                while(rs.next())
                {
                    String ancestorID = rs.getString(1);
                    ancestorIds.add(ancestorID);
                }

            } catch (SQLException e) {
                logger.warn("Nested exception is : " + e.fillInStackTrace());
            }

            return ancestorIds;
        }
        else
        {
            throw new IllegalArgumentException(NULL_ARG_MSG+conceptId);
        }
    }

    public void setTcTableName(String tcTableName) {
        this.tcTableName = tcTableName;
    }

    public void setSubTypeColumnName(String subTypeColumnName) {
        this.subTypeColumnName = subTypeColumnName;
    }

    public void setSuperTypeColumnName(String superTypeColumnName) {
        this.superTypeColumnName = superTypeColumnName;
    }

    public String getTcTableName() {
        return tcTableName;
    }

    public String getSubTypeColumnName() {
        return subTypeColumnName;
    }

    public String getSuperTypeColumnName() {
        return superTypeColumnName;
    }
}
