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
package uk.nhs.cfh.dsp.snomed.dao.impl;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.objectmodel.*;
import uk.nhs.cfh.dsp.snomed.objectmodel.error.ConceptNotFoundException;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedDescriptionImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRelationshipImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRoleGroupImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implemenation of a {@link uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO}
 * that handles a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 *
 * <br>Version : @#VersionNumber#@
 * <br> Written by @author jay
 * <br> Created on Dec 15, 2008 at 9:03:03 PM
 */
public class SnomedConceptDatabaseDAO implements TerminologyConceptDAO {

    /** The logger. */
    private static Log logger = LogFactory.getLog(SnomedConceptDatabaseDAO.class);
    
    /** The get concept with id statement. */
    private PreparedStatement getConceptWithIDStatement;
    
    /** The get descriptions for concept id statement. */
    private PreparedStatement getDescriptionsForConceptIDStatement;
    
    /** The get relationships for concept id statement. */
    private PreparedStatement getRelationshipsForConceptIDStatement;
    
    /** The get concepts like statement. */
    private PreparedStatement getConceptsLikeStatement;
    
    /** The get descendants statement. */
    private PreparedStatement getDescendantsStatement;
    
    /** The get ancestors statement. */
    private PreparedStatement getAncestorsStatement;
    
    /** The get replacement concept statement. */
    private PreparedStatement getReplacementConceptStatement;
    
    /** The get preferred term statement. */
    private PreparedStatement getPreferredTermStatement;
    
    /** The connection. */
    private Connection connection;
    
    /** The concept status map. */
    private Map<String, String> conceptStatusMap = new HashMap<String, String>();
    
    /** The get child relationships for concept id statement. */
    private PreparedStatement getChildRelationshipsForConceptIDStatement;

    /**
     * Instantiates a new snomed concept database dao.
     *
     * @param dataSource the data source
     */
    public SnomedConceptDatabaseDAO(DataSource dataSource){
        if(dataSource != null)
        {
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
    }

    /**
     * Instantiates a new snomed concept database dao.
     *
     * @param connection the connection
     */
    public SnomedConceptDatabaseDAO(Connection connection) {

        if (connection != null)
        {
            this.connection = connection;
            // create prepared statements
            initialiseStatements();
        }
        else
        {
            logger.fatal("Valid connection to database not found, please check database is initialised!" +
                    "\n Shutting down application");
            throw new IllegalArgumentException("Connection passed can not be null.");
        }
    }

    /**
     * No args constructor for IOC.
     */
    public SnomedConceptDatabaseDAO(){

    }

    /**
     * Initialise statements.
     */
    public synchronized void initialiseStatements() {
        // create prepared statements
        try
        {
            getConceptWithIDStatement = connection.prepareStatement(""
                    + "SELECT DISTINCT FULLYSPECIFIEDNAME, ISPRIMITIVE, CONCEPTSTATUS," +
                    "CTV3ID, SNOMEDID, SOURCE FROM SNOMED.CONCEPT "
                    + "WHERE CONCEPTID = ? ");

            // descriptions
            getDescriptionsForConceptIDStatement = connection
                    .prepareStatement(""
                            + "SELECT DISTINCT  DESCRIPTIONID, DESCRIPTIONSTATUS, TERM, INITIALCAPITALSTATUS, "
                            + "DESCRIPTIONTYPE, LANGUAGECODE, SOURCE FROM SNOMED.TERM "
                            + "WHERE CONCEPTID = ? ");

            // relationships
            getRelationshipsForConceptIDStatement = connection
                    .prepareStatement(""
                            + "SELECT DISTINCT RELATIONSHIPID, CONCEPTID1, RELATIONSHIPTYPE, "
                            + "CONCEPTID2, CHARACTERISTICTYPE, REFINABILITY, RELATIONSHIPGROUP, SOURCE "
                            + "FROM SNOMED.REL WHERE CONCEPTID1 = ?");

            getChildRelationshipsForConceptIDStatement = connection
                    .prepareStatement(""
                            + "SELECT DISTINCT CONCEPTID1 "
                            + "FROM SNOMED.REL WHERE RELATIONSHIPTYPE = '116680003' AND CONCEPTID2 = ?");


            // get concepts like statement
            getConceptsLikeStatement = connection.prepareStatement("" +
                    "SELECT DISTINCT CONCEPTID FROM SNOMED.CONCEPT " +
                    "WHERE FULLYSPECIFIEDNAME LIKE ? ");

            // get ancestors statement
            getAncestorsStatement = connection.prepareStatement("" +
                    "SELECT SUPERTYPEID FROM SNOMED.TRANSITIVECLOSURE " +
                    "WHERE SUBTYPEID = ?");

            // get descendants
            getDescendantsStatement = connection.prepareStatement("" +
                    "SELECT SUBTYPEID FROM SNOMED.TRANSITIVECLOSURE " +
                    "WHERE SUPERTYPEID = ?");

            // get replacements
            getReplacementConceptStatement = connection.prepareStatement("" +
                    "SELECT CONCEPTID2 FROM SNOMED.REL WHERE CONCEPTID1 = ? AND " +
                    "RELATIONHSIPTYPE IN ('168666000', '370124000')");

            // get preferred term
            getPreferredTermStatement = connection.prepareStatement("" +
                    "SELECT TERM FROM SNOMED.TERM WHERE DESCRIPTIONTYPE = 1 AND CONCEPTID = ?");

        } catch (SQLException e) {
            logger.warn("SQL error encountered while initialising statements.\n" +
                    "Nested exception is : " + e.fillInStackTrace());
        }
    }


    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getMatchingConcepts(java.lang.String)
     */
    public synchronized Collection<TerminologyConcept> getMatchingConcepts(String label) {

        Set<TerminologyConcept> concepts = new LinkedHashSet<TerminologyConcept>();
        // set value in prepared statement and run query
        try
        {
            // add wild card characters to label
            label = "%"+label+"%";
            getConceptsLikeStatement.setString(1, label);
            ResultSet resultSet = getConceptsLikeStatement.executeQuery();
            while(resultSet.next())
            {
                String conceptID = resultSet.getString("CONCEPTID");
                // get terminology concept for id
                try
                {
                    TerminologyConcept concept = getTerminologyConcept(conceptID);
                    // add concept to concepts list
                    concepts.add(concept);

                } catch (ConceptNotFoundException e) {
                    logger.warn(e.fillInStackTrace());
                }
            }

            // close result set
            resultSet.close();

        } catch (SQLException e) {
            logger.warn("Error retrieving matching concepts. Nested exception is : " + e.fillInStackTrace());
        }

        return concepts;
    }


    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getTerminologyConcept(java.lang.String)
     */
    public synchronized TerminologyConcept getTerminologyConcept(String conceptID) throws ConceptNotFoundException {

        String conceptLabel = "";
        boolean isPrimitive = true;
        String source = "";
        int conceptStatus =0;
        String ctv3ID = "";
        String snomedID = "";
        // set value in prepared statement
        try
        {
            // strip all non digit numbers from concept id
            conceptID = conceptID.replaceAll("[^\\d]", "");
            getConceptWithIDStatement.setString(1, conceptID);
            ResultSet rs = getConceptWithIDStatement.executeQuery();
            while(rs.next())
            {
                conceptLabel = rs.getString(1);
                int isPrim = rs.getInt(2);
                conceptStatus = rs.getInt(3);
                ctv3ID = rs.getString(4);
                snomedID = rs.getString(5);
                if(isPrim == 0)
                {
                    isPrimitive = false;
                }
                source = rs.getString(6);
            }

            // close result set
            rs.close();

        } catch (SQLException e) {
            logger.warn("SQL Error encountered while checking for concept with id : "+conceptID +
                    "\nNested exception is : " + e.fillInStackTrace());
        }catch (Exception e) {
            logger.warn("A variable most likely returned a null value unexpectedly." +
                    "\nNested exception is : " + e.fillInStackTrace());
        }

        // create terminology concept
        if("".equalsIgnoreCase(conceptLabel))
        {
            throw new ConceptNotFoundException(conceptID);
        }
        else
        {
            SnomedConcept concept = new SnomedConceptImpl(conceptID, conceptLabel);
            // add primitve status
            concept.setPrimitive(isPrimitive);
            concept.setSource(source);
            // use utility function of ComponentStatus to get value
            concept.setStatus(ComponentStatus.valueOf(conceptStatus));
            concept.setCtv3ID(ctv3ID);
            concept.setSnomedID(snomedID);
            // set concept type
            concept.setType(getConceptType(conceptID));

            try
            {
                // set descriptions
                setDescriptions(concept);

                // set relationships
                setRelationships(concept);

            } catch (SQLException e) {
                logger.warn("SQL Exception while querying database. Nested exception is : " + e.fillInStackTrace());
            }catch (Exception e) {
                logger.warn("A variable most likely returned a null value unexpectedly." +
                        "\nNested exception is : " + e.fillInStackTrace());
            }

            return concept;
        }
    }

    /**
     * Sets the descriptions.
     *
     * @param concept the new descriptions
     * @throws SQLException the SQL exception
     */
    private synchronized void setDescriptions(SnomedConcept concept) throws SQLException {

        String conceptID = concept.getConceptID();
        Collection<SnomedDescription> descriptions = new ArrayList<SnomedDescription>();

        String descId = "";
        int descStatus = 0;
        String term = "";
        boolean capStatus = false;
        String lang = "";
        int descType = 0;
        String descSource = "";
        String descriptionType = "";

        // set concept status in getDescriptionsForConceptIDStatement and execute it
        getDescriptionsForConceptIDStatement.setString(1, conceptID);
        ResultSet rs = getDescriptionsForConceptIDStatement.executeQuery();
        while(rs.next())
        {
            /*
                Retrieving by column name is expensive but does the conversion automatically for us..
             */
            descId = rs.getString(1);
            descStatus = rs.getInt(2);
            term = rs.getString(3);
            int capStat = rs.getInt(4);
            if(capStat == 1)
            {
                capStatus = true;
            }
            descType = rs.getInt(5);
            lang = rs.getString(6);
            descSource = rs.getString(7);

            // use utility method of ComponentStatus to get value
            ComponentStatus status = ComponentStatus.valueOf(descStatus);

            // set synonyms, preferred label and fullyspecified name
            if (descType == 1)
            {
                descriptionType = "PREFERRED_TERM";
                // set as preferred term?
                concept.setPreferredLabel(term);
            }
            else if (descType == 2)
            {
                descriptionType = "SYNONYM";
                // add as synonym
                concept.addSynonym(term);
            }
            else if (descType == 3)
            {
                descriptionType = "FULLY_SPECIFIED_NAME";
                // set as fully specified name
                concept.setFullySpecifiedName(term);
                if (logger.isDebugEnabled()) {
                    logger.debug("Value of fullySpecifiedName : "+ term);
                }
            }

            // create new description
            SnomedDescription description = new SnomedDescriptionImpl(descId, status, conceptID, term, capStatus,
                    descriptionType, lang, descSource);
            // add to list of descriptions
            descriptions.add(description);
        }

        rs.close();
        // add descriptions to concept
        concept.setDescriptions(descriptions);
    }

    /**
     * Sets the relationships.
     *
     * @param concept the new relationships
     * @throws SQLException the SQL exception
     */
    private synchronized void setRelationships(SnomedConcept concept) throws SQLException {

        String conceptID = concept.getConceptID();
        // get relationships
        Collection<SnomedRelationship> relationships = new ArrayList<SnomedRelationship>();
        Multimap<String, SnomedRelationship> roleGroupsMap = new HashMultimap();

        String relID = "";
        String sourceConceptID = "";
        String relationshipType = "";
        String targetConceptID = "";
        String relationshipGroup = "";
        String relSource = "";

        // set conceptID  in getRelationshipsForConceptIDStatement  and execute it
        getRelationshipsForConceptIDStatement.setString(1, conceptID);
        ResultSet relResultSet = getRelationshipsForConceptIDStatement.executeQuery();

        while(relResultSet.next())
        {
            /*
                Retrieval using column name is expensive but does the conversion for us...
             */
            relID = relResultSet.getString(1);
            sourceConceptID = relResultSet.getString(2);
            relationshipType = relResultSet.getString(3);
            targetConceptID = relResultSet.getString(4);
            int characteristicType = relResultSet.getInt(5);
            int refinability = relResultSet.getInt(6);
            relationshipGroup = relResultSet.getString(7);
            relSource = relResultSet.getString(8);

            // process refinability and characteristic type returned.
            SnomedRelationship.Refinability relationshipRefinability = null;
            SnomedRelationship.RelationshipType type = null;

            // set value based on value passed
            if(refinability == 0)
            {
                relationshipRefinability = SnomedRelationship.Refinability.NOT_REFINABLE;
            }
            else if(refinability == 1)
            {
                relationshipRefinability = SnomedRelationship.Refinability.OPTIONAL;
            }
            else if(refinability == 2)
            {
                relationshipRefinability = SnomedRelationship.Refinability.MANDATORY;

            }
            else
            {
                logger.warn("Unknown refinability value : "+refinability);
            }

            // set characteristic type value based on value passed
            if(characteristicType == 0)
            {
                type = SnomedRelationship.RelationshipType.DEFINING;
            }
            else if(characteristicType == 1)
            {
                type = SnomedRelationship.RelationshipType.QUALIFIER;
            }
            else if(characteristicType == 2)
            {
                type = SnomedRelationship.RelationshipType.HISTORICAL;
            }
            else if(characteristicType == 3)
            {
                type = SnomedRelationship.RelationshipType.ADDITIONAL;
            }
            else
            {
                logger.warn("Unknown characteristic type passed : "+characteristicType);
            }

            // add relationship name
            String relationshipName = null;
            getPreferredTermStatement.setString(1, relationshipType);
            ResultSet rs = getPreferredTermStatement.executeQuery();
            while(rs.next())
            {
                relationshipName = rs.getString(1);
            }

            // close result set
            rs.close();

            // create new relationship
            SnomedRelationship relationship = new SnomedRelationshipImpl(relID, sourceConceptID,
                    relationshipType,targetConceptID, type, relationshipRefinability, relationshipGroup, relSource);

            if(relationshipName != null)
            {
                relationship.setName(relationshipName);
            }

            if (conceptID.equalsIgnoreCase(relationship.getSourceConceptID()))
            {
                concept.getSourceRelationships().add(relationship);
                // add relationship to concept
                concept.getRelationships().add(relationship);

                // check if relationship type is 'is a'
                if (ConceptType.ATTRIBUTE_IS_A.getID().equals(relationship.getRelationshipType())) {
                    // we know this is a parent child relationship, so we add to children
                    concept.getParentIDSet().add(relationship.getTargetConceptID());
                }

                // add defining and refining relationships
                if (relationship.isDefiningRelation())
                {
                    /*
                        we need to check that the relationship is a defining relationship and does not have
                        a relationshipgroup of 0. Any defining relationship without any any relationship with others
                        is assigned the default value of 0.
                    */
                    if (!"0".equalsIgnoreCase(relationship.getRelationshipGroup()))
                    {
                        // add relationship to rolegroups map
                        roleGroupsMap.put(relationshipGroup, relationship);
                    }
                    else
                    {
                        concept.getDefiningRelationships().add(relationship);
                    }
                }

                if (relationship.isQualifyingRelation()) {
                    concept.getRefiningRelationships().add(relationship);
                }

                if (relationship.isMandatory()) {
                    concept.getMandatoryRelationships().add(relationship);
                }

                if (relationship.isOptional()) {
                    concept.getOptionalRelationships().add(relationship);
                }
            }
        }
        relResultSet.close();

        // get child relationship ids
        getChildRelationshipsForConceptIDStatement.setString(1, conceptID);
        ResultSet childrenSet = getChildRelationshipsForConceptIDStatement.executeQuery();
        Collection<String> childIds = new HashSet<String>();
        while(childrenSet.next())
        {
            // add to concept's childIdSet
            childIds.add(childrenSet.getString(1));
        }
        // close result set
        childrenSet.close();

        concept.setChildIDSet(childIds);

        // add role groups
        Collection<SnomedRoleGroup> roleGroups = new ArrayList<SnomedRoleGroup>();
        // loop through role group map and generate role groups as needed
        for(String relationshipGroupId : roleGroupsMap.keys())
        {
            Collection<SnomedRelationship> rels = roleGroupsMap.get(relationshipGroupId);
            // create rolegroup with relationships
            SnomedRoleGroup roleGroup = new SnomedRoleGroupImpl();
            roleGroup.setRelationships(new HashSet<SnomedRelationship>(rels));
            // set relationship group id for role group
            roleGroup.setRelationshipGroupId(relationshipGroupId);
            // add to roleGroups
            roleGroups.add(roleGroup);
        }

        concept.setRoleGroups(roleGroups);
    }

    /**
     * Adds the transitive hierarchy to concept.
     *
     * @param concept the concept
     * @throws SQLException the SQL exception
     */
    private synchronized void addTransitiveHierarchyToConcept(SnomedConcept concept) throws SQLException{

        String conceptId = concept.getConceptID();
        // look up transitive closure table and add hierarchy
        getAncestorsStatement.setString(1, conceptId);
        ResultSet rs1 = getAncestorsStatement.executeQuery();
        while(rs1.next())
        {
            String ancestorId = rs1.getString("SUPERTYPEID");
            // add to ancestorIds
            concept.getAncestorIds().add(ancestorId);
        }
        // close result set
        rs1.close();

        // add descendants
        getDescendantsStatement.setString(1, conceptId);
        ResultSet rs2 = getDescendantsStatement.executeQuery();
        while(rs2.next())
        {
            String descendantId = rs2.getString("SUBTYPEID");
            // add to descendantIds
            concept.getDescendantIds().add(descendantId);
        }
        // close result set
        rs2.close();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getCurrentConceptForConcept(java.lang.String)
     */
    public synchronized TerminologyConcept getCurrentConceptForConcept(String conceptId) throws ConceptNotFoundException {

        if(conceptId != null)
        {
            // replace value in getReplacementConceptStatement
            try
            {
                getReplacementConceptStatement.setString(1, conceptId);
                ResultSet rs = getReplacementConceptStatement.executeQuery();
                Set<String> replacements = new HashSet<String>();
                while(rs.next())
                {
                    replacements.add(rs.getString(1));
                }

                // close result set
                rs.close();
                List<String> concepts = new ArrayList<String>(replacements);

                if(replacements.size() >1)
                {
                    logger.warn("More than one matching replacement concept found. Returning the first " +
                            "concept found. The following is a list of all matching replacements : "+replacements);
                    return getTerminologyConcept(concepts.get(0));
                }
                else if(replacements.size() == 0)
                {
                    logger.warn("No replacement concept found for concept with ID : "+conceptId);
                    return null;
                }
                else
                {
                    return getTerminologyConcept(concepts.get(0));
                }
            }
            catch (SQLException e) {
                logger.warn(e.fillInStackTrace());
                return null;
            }
        }
        else
        {
            throw new IllegalArgumentException("Concept ID passed can not be null.");
        }
    }

    /**
     * Gets the concept type.
     *
     * @param conceptId the concept id
     * @return the concept type
     */
    protected synchronized ConceptType getConceptType(String conceptId){

        ConceptType type = ConceptType.UNKNOWN;
        // we create a sorted concept type list, because we need to check in a specific order
        List<ConceptType> types = new ArrayList<ConceptType>();
        types.add(ConceptType.DISEASE);
        types.add(ConceptType.FINDING);
        types.add(ConceptType.FINDING_WEC);
        types.add(ConceptType.OBSERVABLE_ENTITY);
        types.add(ConceptType.BODY_STRUCTURE);
        types.add(ConceptType.PHARMACEUTICAL_OR_BIOLOGICAL_PRODUCT);
        types.add(ConceptType.EVENT);
        types.add(ConceptType.SURGICAL_PROCEDURE);
        types.add(ConceptType.PROCEDURE);
        types.add(ConceptType.PROCEDURE_WEC);
        types.add(ConceptType.SPECIMEN);
        types.add(ConceptType.DRUG_DEVICE_COMBO_PRODUCT);
        types.add(ConceptType.SITUATION_WEC);

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(SUBTYPEID) FROM " +
                    "SNOMED.TRANSITIVECLOSURE WHERE SUBTYPEID = ? AND  SUPERTYPEID= ?");
            ps.setString(1, conceptId);
            // change supertype and check result
            int result = 0;
            for(ConceptType ct : types)
            {
                ps.setString(2, ct.getID());
                // execute statement and check result
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {
                    result = rs.getInt(1);
                }

                // close result set
                rs.close();
                if(result == 1)
                {
                    // close ps
                    ps.close();
                    return ct;
                }
            }

        }
        catch (SQLException e) {
            logger.warn("Error querying transitive closure table. Nested exception is : " + e.fillInStackTrace());
        }

        return type;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#saveTerminologyConcept(uk.nhs.cfh.dsp.snomed.om.TerminologyConcept)
      */
    public synchronized void saveTerminologyConcept(TerminologyConcept concept) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("This method has not been implemented in this class : "+getClass());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#deleteConcept(uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept)
     */
    public void deleteConcept(TerminologyConcept concept) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("This method has not been implemented in this class : "+getClass());    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getAllConceptsInTerminology()
     */
    public Collection<TerminologyConcept> getAllConceptsInTerminology() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("This method has not been implemented in this class : "+getClass());    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getConceptCount()
     */
    public int getConceptCount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("This method has not been implemented in this class : "+getClass());    }

    /**
     * Sets the data source.
     *
     * @param dataSource the new data source
     */
    public synchronized void setDataSource(DataSource dataSource) {
        if(dataSource != null)
        {
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
    }
}
