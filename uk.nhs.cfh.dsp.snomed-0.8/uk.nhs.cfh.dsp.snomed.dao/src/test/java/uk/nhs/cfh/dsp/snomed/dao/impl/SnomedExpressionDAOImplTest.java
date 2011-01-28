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
package uk.nhs.cfh.dsp.snomed.dao.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.dao.SnomedExpressionDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A test class for {@link uk.nhs.cfh.dsp.snomed.dao.impl.SnomedExpressionDAOImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 21-Mar-2010 at 18:54:46.
 */
public class SnomedExpressionDAOImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test terminology dao. */
    private SnomedConceptDatabaseDAO testTerminologyDAO;

    /** The Constant conceptId. */
    private static final String conceptId = "22298006";

    /** The concept. */
    private SnomedConcept concept;

    /** The test snomed expression dao. */
    private SnomedExpressionDAO testSnomedExpressionDAO;

    /** The Constant uuid. */
    private static final UUID uuid = UUID.randomUUID();

    /** The close to user expression. */
    private CloseToUserExpression closeToUserExpression;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        concept = (SnomedConcept) testTerminologyDAO.getTerminologyConcept(conceptId);
        closeToUserExpression = new CloseToUserExpressionImpl(concept);
        closeToUserExpression.setUuid(uuid);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Test concept creation.
     *
     * @throws Exception the exception
     */
    public void testConceptCreation() throws Exception{
        assertNotNull("Concept must not be null", concept);
        assertTrue("Concept Id must be 22298006", conceptId.equalsIgnoreCase(concept.getConceptID()));
        assertNotNull("Close to user expression must not be null", closeToUserExpression);
        assertTrue("UUIDs must be equal", uuid.equals(closeToUserExpression.getUuid()));
    }

    /**
     * Test save expression.
     *
     * @throws Exception the exception
     */
    public void testSaveExpression() throws Exception {
        testSnomedExpressionDAO.saveExpression(closeToUserExpression);
        // check count is not 0
        assertTrue("Repository must have at least one entry", testSnomedExpressionDAO.findAll().size() >0);
    }

    /**
     * Test find.
     *
     * @throws Exception the exception
     */
    public void testFind() throws Exception {
        Expression returnedExpression = testSnomedExpressionDAO.find(uuid);
        assertNotNull("Returned expression must not be null", returnedExpression);
        assertTrue("UUIDs must match", uuid.equals(returnedExpression.getUuid()));
    }

    /**
     * Sets the test snomed expression dao.
     *
     * @param testSnomedExpressionDAO the new test snomed expression dao
     */
    public void setTestSnomedExpressionDAO(SnomedExpressionDAO testSnomedExpressionDAO) {
        this.testSnomedExpressionDAO = testSnomedExpressionDAO;
    }

    /**
     * Sets the test terminology dao.
     *
     * @param testTerminologyDAO the new test terminology dao
     */
    public void setTestTerminologyDAO(SnomedConceptDatabaseDAO testTerminologyDAO) {
        this.testTerminologyDAO = testTerminologyDAO;
    }
}
