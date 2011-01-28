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
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.RandomSubtypeHierarchyProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * A concret implementation of a {@link uk.nhs.cfh.dsp.snomed.hierarchyprovider.RandomSubtypeHierarchyProvider}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 18, 2011 at 8:29:58 PM
 */
public class RandomSubtypeHierarchyProviderImpl extends TransitiveClosureTableBasedHierarchyProvider implements RandomSubtypeHierarchyProvider {

    /** The get descendants statement. */
    private PreparedStatement getDescendantsStatement;
    private static Log logger = LogFactory.getLog(RandomSubtypeHierarchyProviderImpl.class);

    /**
     * Initialise statements.
     */
    @Override
    public void initialiseStatements() {

        try
        {
            super.initialiseStatements();

            getDescendantsStatement = getConnection().prepareStatement(""
                    +"SELECT "+getSubTypeColumnName()+" FROM "+getSchemaName()+"."+getTcTableName()
                    +" WHERE "+getSuperTypeColumnName()+" = ? "
                    +" ORDER BY RAND() LIMIT ?");

        } catch (SQLException e) {
            logger.warn("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    /**
     * This method should return a set of random descendants. Note the SQL used is MySQL specific
     * @param parentConceptId the parent concept for which subtypes need to be returned
     * @param subsetSize the max number of subtypes to be returned
     * @return
     */
    public Collection<String> getRandomSetOfSubTypes(String parentConceptId, int subsetSize){

        Set<String> descendantIds = new HashSet<String>();
        if(parentConceptId != null && ! "".equalsIgnoreCase(parentConceptId))
        {
            // get concept id and set parameter
            try
            {
                getDescendantsStatement.setString(1, parentConceptId);
                getDescendantsStatement.setInt(2, subsetSize);
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
            throw new IllegalArgumentException(NULL_ARG_MSG+parentConceptId);
        }
    }

    /**
     * This method should return a single random descendant. Note the SQL used is MySQL specific.
     * This method also relies on the fact that the TC table always returns the concept as descendant
     * of itself, if no other descendant exists!
     * @param parentConceptId the parent concept for which subtype needs to be returned
     * @return
     */
    public String getRandomSubtype(String parentConceptId){
        List<String> list = new ArrayList<String>(getRandomSetOfSubTypes(parentConceptId, 1));
        if (list.size() >0)
        {
            return list.get(0);
        }
        else
        {
            throw new RuntimeException("Something went wrong... Concept "+parentConceptId+" does not have " +
                    "any subtypes in the transitive closure table!");
        }
    }
}
