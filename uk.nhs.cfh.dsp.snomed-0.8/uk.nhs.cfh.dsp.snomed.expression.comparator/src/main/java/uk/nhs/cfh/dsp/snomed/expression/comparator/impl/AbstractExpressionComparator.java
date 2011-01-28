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
package uk.nhs.cfh.dsp.snomed.expression.comparator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;
import uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.error.ConceptNotFoundException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 21, 2009 at 4:32:13 PM
 */
public abstract class AbstractExpressionComparator implements ExpressionComparator {

    /** The connection. */
    protected Connection connection;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ExpressionComparatorImpl.class);
    
    /** The check ancestors statement. */
    private PreparedStatement checkAncestorsStatement;
    
    /** The check descendants statement. */
    private PreparedStatement checkDescendantsStatement;
    
    /** The check status statement. */
    private PreparedStatement checkStatusStatement;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The data source. */
    private DataSource dataSource;

    /**
     * Instantiates a new abstract expression comparator.
     *
     * @param dataSource the data source
     * @param terminologyConceptDAO the terminology concept dao
     */
    protected AbstractExpressionComparator(DataSource dataSource, TerminologyConceptDAO terminologyConceptDAO)
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
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Instantiates a new abstract expression comparator.
     *
     * @param connection the connection
     * @param terminologyConceptDAO the terminology concept dao
     */
    protected AbstractExpressionComparator(Connection connection, TerminologyConceptDAO terminologyConceptDAO) {

        // initialise statements
        this.connection = connection;
        initialiseStatements();
        this.terminologyConceptDAO = terminologyConceptDAO;

    }

    /**
     * Instantiates a new abstract expression comparator.
     */
    protected AbstractExpressionComparator() {
    }

    /**
     * Initialise statements.
     */
    protected void initialiseStatements() {

        try {
            checkAncestorsStatement = connection.prepareStatement("" +
                    "SELECT COUNT(SUPERTYPEID) FROM SNOMED.TRANSITIVECLOSURE WHERE " +
                    "SUPERTYPEID = ? AND SUBTYPEID = ?");
            checkDescendantsStatement = connection.prepareStatement("" +
                    "SELECT COUNT(SUBTYPEID) FROM SNOMED.TRANSITIVECLOSURE WHERE " +
                    "SUBTYPEID = ? AND SUPERTYPEID = ?");
            checkStatusStatement = connection.prepareStatement("" +
                    "SELECT CONCEPTSTATUS FROM SNOMED.CONCEPT WHERE CONCEPTID = ?");
        }
        catch (SQLException e) {
            logger.warn(e.fillInStackTrace());
        }

    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator#getSubsumptionRelation(Expression, Expression)
     */
    public Subsumption_Relation getSubsumptionRelation(Expression lhsExpression, Expression rhsExpression){
        throw new UnsupportedOperationException("This method has not been implemented");
    }

    /**
     * Gets the subsumption relation.
     *
     * @param lhsConceptExpression the lhs concept expression
     * @param rhsConceptExpression the rhs concept expression
     * @return the subsumption relation
     */
    public Subsumption_Relation getSubsumptionRelation(ConceptExpression lhsConceptExpression,
                                                       ConceptExpression rhsConceptExpression){
        throw new UnsupportedOperationException("This method has not been implemented");
    }

    /**
     * Gets the subsumption relation.
     *
     * @param lhsPropertyExpression the lhs property expression
     * @param rhsPropertyExpression the rhs property expression
     * @return the subsumption relation
     */
    public Subsumption_Relation getSubsumptionRelation(PropertyExpression lhsPropertyExpression,
                                                       PropertyExpression rhsPropertyExpression){

        throw new UnsupportedOperationException("This method has not been implemented");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator#getSubsumptionRelation(SnomedConcept, SnomedConcept)
     */
    public Subsumption_Relation getSubsumptionRelation(SnomedConcept lhsConcept, SnomedConcept rhsConcept){

        Subsumption_Relation relation = Subsumption_Relation.NO_RELATION;
        if(lhsConcept != null && rhsConcept != null)
        {
            if(rhsConcept.equals(lhsConcept))
            {
                return Subsumption_Relation.SAME;
            }
            else
            {

                relation = getSubsumptionRelation(rhsConcept.getConceptID(), lhsConcept.getConceptID());
            }
        }

        return relation;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator#getSubsumptionRelation(String, String)
     */
    public Subsumption_Relation getSubsumptionRelation(String lhsConceptId, String rhsConceptId){

        Subsumption_Relation relation = Subsumption_Relation.NO_RELATION;

        if(rhsConceptId != null && lhsConceptId != null)
        {
            if (rhsConceptId.equalsIgnoreCase(lhsConceptId))
            {
                return Subsumption_Relation.SAME;
            }
            else
            {
                // check if concepts passed are active, if not replace them with corresponding current versions
                if(! isActiveConcept(lhsConceptId))
                {
                    try
                    {
                        SnomedConcept replacementConcept
                                = (SnomedConcept) terminologyConceptDAO.getCurrentConceptForConcept(lhsConceptId);

                        // call self with replacement concept
                        getSubsumptionRelation(replacementConcept.getConceptID(), rhsConceptId);
                    }
                    catch (ConceptNotFoundException e) {
                        logger.warn("Concept not found. Nested exception is : " + e.fillInStackTrace());
                    }
                }

                if(! isActiveConcept(rhsConceptId))
                {
                    try
                    {
                        SnomedConcept replacementConcept
                                = (SnomedConcept) terminologyConceptDAO.getCurrentConceptForConcept(rhsConceptId);

                        // call self with replacement concept
                        getSubsumptionRelation(lhsConceptId, replacementConcept.getConceptID());
                    }
                    catch (ConceptNotFoundException e) {
                        logger.warn("Concept passed is in not current. Replacement concept not found.\n" +
                                " Nested exception is : " + e.fillInStackTrace());
                    }
                }

                /*
                  get ancestors of rhsConcept first and see if lhsConcept appears in the list. Note this is more
                  efficient than retrieving all descendants of lhsConcept because top level primitives can
                  return large number of descendants
                */

                try
                {
                    int result = 0;

                    checkAncestorsStatement.setString(1, lhsConceptId);
                    checkAncestorsStatement.setString(2, rhsConceptId);
                    ResultSet rs = checkAncestorsStatement.executeQuery();
                    while(rs.next())
                    {
                        result = rs.getInt(1);
                    }
                    // close resultset
                    rs.close();

                    if(result >0)
                    {
                        return Subsumption_Relation.SUBSUMES;
                    }

                    // check descendants now
                    checkDescendantsStatement.setString(1, lhsConceptId);
                    checkDescendantsStatement.setString(2, rhsConceptId);
                    ResultSet rs2 = checkDescendantsStatement.executeQuery();
                    while(rs2.next())
                    {
                        result = rs2.getInt(1);
                    }
                    // close resultset
                    rs2.close();

                    if(result >0)
                    {
                        return Subsumption_Relation.SUBSUMED_BY;
                    }

                    // if at this point result is still 0, then there is no relationship
                    if(result == 0)
                    {
                        return Subsumption_Relation.NO_RELATION;
                    }
                }
                catch (SQLException e) {
                    logger.warn(e.fillInStackTrace());
                }
            }
        }

        return relation;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator#isSubsumedBy(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept, uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public boolean isSubsumedBy(SnomedConcept lhsConcept, SnomedConcept rhsConcept){
        return Subsumption_Relation.SUBSUMED_BY == getSubsumptionRelation(lhsConcept, rhsConcept);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator#isSubsumedBy(java.lang.String, java.lang.String)
     */
    public boolean isSubsumedBy(String lhsConceptId, String rhsConceptId){
        return Subsumption_Relation.SUBSUMED_BY == getSubsumptionRelation(lhsConceptId, rhsConceptId);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator#isSubsumedBy(uk.nhs.cfh.dsp.snomed.expression.model.Expression, uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public boolean isSubsumedBy(Expression lhsExpression, Expression rhsExpression){

        return Subsumption_Relation.SUBSUMED_BY == getSubsumptionRelation(lhsExpression, rhsExpression);
    }
    
    /**
     * Sets the data source.
     *
     * @param dataSource the new data source
     */
    public synchronized void setDataSource(DataSource dataSource) {
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
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Checks if is active concept.
     *
     * @param conceptId the concept id
     * @return true, if is active concept
     */
    private boolean isActiveConcept(String conceptId){

        boolean isActive = false;
        // set value in checkStatusStatement
        try
        {
            int status = 0;
            checkStatusStatement.setString(1, conceptId);
            ResultSet rs = checkStatusStatement.executeQuery();
            while(rs.next())
            {
                status = rs.getInt(1);
            }

            if(status == 0 || status == 6 || status == 11)
            {
                isActive = true;
            }
        }
        catch (SQLException e) {
            logger.warn(e.fillInStackTrace());
        }

        return isActive;
    }
}
