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
package uk.nhs.cfh.dsp.yasb.indexgenerator.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.error.ConceptNotFoundException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * An implementation of a synonym engine for SNOMED
 * Written by @author jay
 * Created on Jan 12, 2009 at 6:32:22 PM.
 */
public class SnomedSynonymEngine implements SynonymEngine {

    /** The synonyms map. */
    private Map<String, Collection<String>> synonymsMap;
    
    /** The connection. */
    private Connection connection;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SnomedSynonymEngine.class);

    /**
     * Instantiates a new snomed synonym engine.
     *
     * @param terminologyConceptDAO the terminology concept dao
     * @param dataSource the data source
     */
    public SnomedSynonymEngine(TerminologyConceptDAO terminologyConceptDAO,
                               DataSource dataSource) {

        try {
            connection = dataSource.getConnection();
            this.terminologyConceptDAO = terminologyConceptDAO;
            // create dao object
            try
            {
                // create snynonyms map
                synonymsMap = new HashMap<String, Collection<String>>();
                // populate map
                populateSynonymsMap();

            } catch (SQLException e) {
                logger.warn(e.fillInStackTrace());
            }
        }
        catch (SQLException e) {
            logger.warn("Error obtaining connection from datasource.\n" +
                    "Nested exception is : " + e.fillInStackTrace());
        }

    }


    /**
     * Populate synonyms map.
     *
     * @throws SQLException the SQL exception
     */
    private void populateSynonymsMap() throws SQLException {

        Statement statement = connection.createStatement();
        SnomedConcept concept = null;
        ResultSet resultSet = statement.executeQuery("" +
                "SELECT DISTINCT CONCEPTID FROM SNOMED.CONCEPT");
        while(resultSet.next())
        {
            String conceptID = resultSet.getString("CONCEPTID");
            try
            {
                concept = (SnomedConcept) terminologyConceptDAO.getTerminologyConcept(conceptID);
                String preferredLabel = concept.getPreferredLabel();
                String fsn = concept.getFullySpecifiedName();
                Collection<String> syns = concept.getSynonyms();
                syns.add(fsn);
                // add to synonyms map with preferred term as key
                if(preferredLabel != null && ! "".equalsIgnoreCase(preferredLabel))
                {
                    synonymsMap.put(preferredLabel, syns);
                }

            } catch (ConceptNotFoundException e) {
                logger.warn(e.fillInStackTrace());
            }
        }

        // close result set and statement
        resultSet.close();
        statement.close();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.indexgenerator.engine.SynonymEngine#getSynonyms(java.lang.String)
     */
    public Collection<String> getSynonyms(String term) {

        if(synonymsMap.containsKey(term))
        {
            return synonymsMap.get(term);
        }
        else
        {
            logger.info("No match for given term : "+term );
            return null;
        }
    }

}
