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
package uk.nhs.cfh.dsp.srth.expression.repository.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionSubsumptionRelationshipImpl;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A test case for {@link uk.nhs.cfh.dsp.srth.expression.repository.impl.ExpressionMappingObjectDAOImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 12:23:08 AM.
 */
public class ExpressionSubsumptionRelationshipDAOImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The super type uuid. */
    private static UUID superTypeUuid = UUID.randomUUID();
    
    /** The sub type uuid. */
    private static UUID subTypeUuid = UUID.randomUUID();
    
    /** The test expression subsumption relationship dao. */
    private ExpressionSubsumptionRelationshipDAO testExpressionSubsumptionRelationshipDAO;
    
    /** The relationship. */
    private ExpressionSubsumptionRelationship relationship;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("SuperType uuid can not be null", superTypeUuid);
        assertNotNull("SubType uuid can not be null", subTypeUuid);
        assertNotNull("ExpressionSubsumptionRelationshipDAO can not be null",
                testExpressionSubsumptionRelationshipDAO);
        relationship = new ExpressionSubsumptionRelationshipImpl(superTypeUuid, subTypeUuid);
        assertNotNull("Relationship can not be null", relationship);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Test delete all relationship.
     *
     * @throws Exception the exception
     */
    public void testDeleteAllRelationship() throws Exception {
        testExpressionSubsumptionRelationshipDAO.deleteAll();
        assertEquals("Database must contain 0 relationships after delete... ", 0,
                testExpressionSubsumptionRelationshipDAO.getRelationshipsCount());
    }

    /**
     * Test save relationship.
     *
     * @throws Exception the exception
     */
    public void testSaveRelationship() throws Exception {
        testExpressionSubsumptionRelationshipDAO.saveRelationship(relationship);
        assertEquals("Database must contain one relationship entry", 1,
                testExpressionSubsumptionRelationshipDAO.findAllRelationships().size());
    }

    /**
     * Test find relationship.
     *
     * @throws Exception the exception
     */
    public void testFindRelationship() throws Exception {
        ExpressionSubsumptionRelationship localRelationship =
                testExpressionSubsumptionRelationshipDAO.findRelationship(superTypeUuid, subTypeUuid);
        assertNotNull("Local relationship must not be null", localRelationship);
        assertEquals("SuperType Ids must be the same", superTypeUuid, localRelationship.getSuperTypeExpressionUUuid());
        assertEquals("SubType Ids must be the same", subTypeUuid, localRelationship.getSubTypeExpressionUuid());
    }

    /**
     * Test get all sub types.
     *
     * @throws Exception the exception
     */
    public void testGetAllSubTypes() throws Exception {
        ExpressionSubsumptionRelationship newRelationship =
                new ExpressionSubsumptionRelationshipImpl(superTypeUuid, UUID.randomUUID());
        testExpressionSubsumptionRelationshipDAO.saveRelationship(newRelationship);
        assertEquals("Two sub types for super type must be returned", 2,
                testExpressionSubsumptionRelationshipDAO.getAllSubTypes(superTypeUuid).size());
    }

    /**
     * Test get all super types.
     *
     * @throws Exception the exception
     */
    public void testGetAllSuperTypes() throws Exception {
        ExpressionSubsumptionRelationship newRelationship =
                new ExpressionSubsumptionRelationshipImpl(UUID.randomUUID(), subTypeUuid);
        testExpressionSubsumptionRelationshipDAO.saveRelationship(newRelationship);
        assertEquals("Two supertypes for subtype must be returned", 2,
                testExpressionSubsumptionRelationshipDAO.getAllSuperTypes(subTypeUuid).size());
    }

    /**
     * Sets the test expression subsumption relationship dao.
     *
     * @param testExpressionSubsumptionRelationshipDAO the new test expression subsumption relationship dao
     */
    public void setTestExpressionSubsumptionRelationshipDAO(
            ExpressionSubsumptionRelationshipDAO testExpressionSubsumptionRelationshipDAO) {
        this.testExpressionSubsumptionRelationshipDAO = testExpressionSubsumptionRelationshipDAO;
    }
}
