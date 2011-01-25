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
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.impl.SnomedConceptDatabaseDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 10, 2009 at 10:43:00 AM
 */
public class NormalFormHierarchyProviderImpl extends TransitiveClosureTableBasedHierarchyProvider
        implements NormalFormHierarchyProvider {
    
    /** The get primitive parents statement. */
    private PreparedStatement getPrimitiveParentsStatement;
    
    /** The get defined parents statement. */
    private PreparedStatement getDefinedParentsStatement;
    
    /** The get defined ancestors statement. */
    private PreparedStatement getDefinedAncestorsStatement;
    
    /** The get primitive ancestors statement. */
    private PreparedStatement getPrimitiveAncestorsStatement;
    
    /** The check primitive status statement. */
    private PreparedStatement checkPrimitiveStatusStatement;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(NormalFormHierarchyProviderImpl.class);
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    private String conceptTableName;
    private String conceptIsPrimitiveColumnName;
    private String conceptIdColumnName;

    /**
     * Instantiates a new normal form hierarchy provider impl.
     *
     * @param dataSource the data source
     * @param terminologyConceptDAO the terminology concept dao
     */
    public NormalFormHierarchyProviderImpl(DataSource dataSource, TerminologyConceptDAO terminologyConceptDAO){

        super(dataSource);

        if(terminologyConceptDAO != null)
        {
            this.terminologyConceptDAO = terminologyConceptDAO;
        }
        else
        {
            throw new IllegalArgumentException("DAO passed can not be null.");
        }
    }

    /**
     * Instantiates a new normal form hierarchy provider impl.
     *
     * @param connection the connection
     */
    public NormalFormHierarchyProviderImpl(Connection connection) {
        super(connection);
        if (connection != null)
        {
            terminologyConceptDAO = new SnomedConceptDatabaseDAO(connection);

        } else {
            throw new IllegalArgumentException("Argument passed can not be null : "+connection);
        }
    }

    /**
     * Initialise statements.
     */
    @Override
    public void initialiseStatements() {

        try
        {
            super.initialiseStatements();

            getPrimitiveParentsStatement = getConnection().prepareStatement(""
                    + "SELECT DISTINCT "+getRelationshipTableName()+"."+getTargetColumnName()
                    + " FROM "+getSchemaName()+"."+getRelationshipTableName()+" INNER JOIN "
                    +getSchemaName()+"."+conceptTableName+" ON " +
                    getRelationshipTableName()+"."+getAttributeName()+" = '"+getAttributeValue()
                    +"' AND "+conceptTableName+"."+conceptIsPrimitiveColumnName
                    +" =1 AND "+getRelationshipTableName()+"."+getSourceColumnName()+" = ?" +
                    " AND "+conceptTableName+"."+conceptIdColumnName+" = "
                    +getRelationshipTableName()+"."+getTargetColumnName());

            getDefinedParentsStatement = getConnection().prepareStatement(""
                    + "SELECT DISTINCT "+getRelationshipTableName()+"."+getTargetColumnName()
                    + " FROM "+getSchemaName()+"."+getRelationshipTableName()+" INNER JOIN "
                    +getSchemaName()+"."+conceptTableName+" ON " +
                    getRelationshipTableName()+"."+getAttributeName()+" = '"+getAttributeValue()
                    +"' AND "+conceptTableName+"."+conceptIsPrimitiveColumnName
                    +" =0 AND "+getRelationshipTableName()+"."+getSourceColumnName()+" = ?" +
                    " AND "+conceptTableName+"."+conceptIdColumnName+" = "
                    +getRelationshipTableName()+"."+getTargetColumnName());

            getDefinedAncestorsStatement = getConnection().prepareStatement(""
                    + "SELECT T."+getSuperTypeColumnName()
                    + " FROM "+getSchemaName()+"."+getTcTableName()+" AS T INNER JOIN "
                    +getSchemaName()+"."+conceptTableName+" ON "
                    + conceptTableName+"."+conceptIsPrimitiveColumnName
                    +" =0 AND T."+getSubTypeColumnName()+" = ? "
                    +" AND "+conceptTableName+"."+conceptIdColumnName+" = T."+getSuperTypeColumnName());

            getPrimitiveAncestorsStatement = getConnection().prepareStatement(""
                    + "SELECT T."+getSuperTypeColumnName()
                    + " FROM "+getSchemaName()+"."+getTcTableName()+" AS T INNER JOIN "
                    +getSchemaName()+"."+conceptTableName+" ON "
                    + conceptTableName+"."+conceptIsPrimitiveColumnName
                    +" =1 AND T."+getSubTypeColumnName()+" = ? "
                    +" AND "+conceptTableName+"."+conceptIdColumnName+" = T."+getSuperTypeColumnName());

            checkPrimitiveStatusStatement = getConnection().prepareStatement("" +
                    "SELECT "+conceptIsPrimitiveColumnName+" FROM "
                    +getSchemaName()+"."+conceptTableName+" WHERE "+conceptIdColumnName+" = ?");

        } catch (SQLException e) {
            logger.warn(e.fillInStackTrace());
        }
    }


    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider#getPrimitiveParents(java.lang.String)
     */
    public Set<String> getPrimitiveParents(String conceptId){

        Set<String> primitiveParents = new HashSet<String>();
        if (conceptId != null)
        {

            // set and execute getPrimitiveParentsStatement
            try
            {
                getPrimitiveParentsStatement.setString(1, conceptId);
                ResultSet rs = getPrimitiveParentsStatement.executeQuery();
                while(rs.next())
                {
                    String primitiveParent = rs.getString(1);
                    primitiveParents.add(primitiveParent);
                }

                // close result set
                rs.close();
                if (logger.isDebugEnabled()) {
                    logger.debug("primitiveParents = " + primitiveParents);
                }

            } catch (SQLException e) {
                logger.warn(e.fillInStackTrace());
            }
        }
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null : "+conceptId);
        }

        return primitiveParents;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider#getPrimitiveAncestors(java.lang.String)
     */
    public Set<String> getPrimitiveAncestors(String conceptId){

        Set<String> primitiveAncestors = new HashSet<String>();
        if(conceptId != null)
        {
            // set conceptid in getPrimitiveAncestorsStatement  and execute it
            try
            {
                getPrimitiveAncestorsStatement.setString(1, conceptId);
                ResultSet rs = getPrimitiveAncestorsStatement.executeQuery();
                while(rs.next())
                {
                    String primitiveAncestor = rs.getString(1);
                    primitiveAncestors.add(primitiveAncestor);
                }

                // close result set
                rs.close();

                /*
               The TC table currently seems to allow a concept to add self as supertype of self...
               this needs to be handled... so remove concept itself from proximalPrimitives
                */

                primitiveAncestors.remove(conceptId);
                if (logger.isDebugEnabled()) {
                    logger.debug("primitiveAncestors = " + primitiveAncestors);
                }
            }
            catch (SQLException e)
            {
                logger.warn(e.fillInStackTrace());
            }
        }
        else
        {
            throw new IllegalArgumentException("ConceptId passed can not be null");
        }

        return primitiveAncestors;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider#getFullyDefinedAncestors(java.lang.String)
     */
    public Set<String> getFullyDefinedAncestors(String conceptId){

        Set<String> definedAncestors = new HashSet<String>();
        if(conceptId != null)
        {
            // set conceptid in getDefinedAncestorsStatement  and execute it
            try
            {
                getDefinedAncestorsStatement.setString(1, conceptId);
                ResultSet rs = getDefinedAncestorsStatement.executeQuery();
                while(rs.next())
                {
                    String definedAncestor = rs.getString(1);
                    definedAncestors.add(definedAncestor);
                }

                // close result set
                rs.close();
                if (logger.isDebugEnabled()) {
                    logger.debug("definedAncestors = " + definedAncestors);
                }
            }
            catch (SQLException e)
            {
                logger.warn(e.fillInStackTrace());
            }
        }
        else
        {
            throw new IllegalArgumentException("ConceptId passed can not be null");
        }

        return definedAncestors;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider#getFullyDefinedParents(java.lang.String)
     */
    public Set<String> getFullyDefinedParents(String conceptId){

        Set<String> definedParents = new HashSet<String>();
        if (conceptId != null)
        {
            // set and execute getDefinedParentsStatement
            try
            {
                getDefinedParentsStatement.setString(1, conceptId);
                ResultSet rs = getDefinedParentsStatement.executeQuery();
                while(rs.next())
                {
                    String definedParent = rs.getString(1);
                    definedParents.add(definedParent);
                }

                // close result set
                rs.close();

                if (logger.isDebugEnabled()) {
                    logger.debug("definedParents = " + definedParents);
                }

            } catch (SQLException e) {
                logger.warn(e.fillInStackTrace());
            }

        }
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null");
        }

        return definedParents;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider#getProximalPrimitiveParents(java.lang.String)
     */
    public Set<String> getProximalPrimitiveParents(String conceptId){

        if(conceptId != null)
        {
            // check if concept with id is primitive
            try
            {
                int status = 0;
                checkPrimitiveStatusStatement.setString(1, conceptId);
                ResultSet rs = checkPrimitiveStatusStatement.executeQuery();
                while(rs.next())
                {
                    status = rs.getInt(1);
                }

                // close resultset
                rs.close();

                if(status == 1)
                {
                    // we know concept is primitive, so we just have to return itself
                    return Collections.singleton(conceptId);
                }
                else
                {
                   Set<String> proximalPrimitives = new HashSet<String>();
                    /*
                   we get all primitive ancestors. We then loop through the ancestors and remove the ancestors
                   that are parents of another entry already present.
                    */

                    Set<String> primitiveAncestors = getPrimitiveAncestors(conceptId);

                    // add all primitiveAncestors  to proximalPrimitives and then keep removing primitive parents
                    proximalPrimitives.addAll(primitiveAncestors);
                    for(String ancestor : primitiveAncestors)
                    {
                        // get primtive ancestors of ancestor and remove from proximal primitives
                        Set<String> localAncestors = getPrimitiveAncestors(ancestor);
                        proximalPrimitives.removeAll(localAncestors);
                    }

                    return proximalPrimitives;
                }
            }
            catch (SQLException e) {
                logger.warn(e.fillInStackTrace());
                return Collections.emptySet();
            }
        }
        else
        {
            throw new IllegalArgumentException("Concept with ID passed does not exist. Concept ID is : "+conceptId);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider#getProximalPrimitivesParents(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public Set<SnomedConcept> getProximalPrimitivesParents(SnomedConcept concept){

        if(concept != null)
        {
            if (! concept.isPrimitive())
            {
                Set<String> proximalPrimitiveIds = getProximalPrimitiveParents(concept.getConceptID());
                Set<SnomedConcept> proximalPrimitives = new HashSet<SnomedConcept>();
                // loop through ids and add corresponding concept object
                for(String id : proximalPrimitiveIds)
                {
                    SnomedConcept c = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, id);
                    if(c != null)
                    {
                        proximalPrimitives.add(c);
                    }
                }

                return proximalPrimitives;
            }
            else
            {
                // return self since concept is primitive
                return Collections.singleton(concept);
            }
        }
        else
        {
            throw new IllegalArgumentException("Concept passed can not be null.");
        }
    }

    public void setConceptTableName(String conceptTableName) {
        this.conceptTableName = conceptTableName;
    }

    public void setConceptIsPrimitiveColumnName(String conceptIsPrimitiveColumnName) {
        this.conceptIsPrimitiveColumnName = conceptIsPrimitiveColumnName;
    }

    public void setConceptIdColumnName(String conceptIdColumnName) {
        this.conceptIdColumnName = conceptIdColumnName;
    }
}
