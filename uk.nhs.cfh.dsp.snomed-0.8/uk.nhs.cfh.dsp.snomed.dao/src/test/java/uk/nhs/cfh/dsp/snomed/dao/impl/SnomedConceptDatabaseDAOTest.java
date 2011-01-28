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
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

// TODO: Auto-generated Javadoc
/**
 * A test case for {@link uk.nhs.cfh.dsp.snomed.dao.impl.SnomedConceptDatabaseDAO}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 25, 2010 at 8:08:36 PM
 */
public class SnomedConceptDatabaseDAOTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test terminology dao. */
    private SnomedConceptDatabaseDAO testTerminologyDAO;
    
    /** The Constant conceptId. */
    private static final String conceptId = "22298006";
    private static final String corruptConceptId = "{2229:8006*";

    /** The concept. */
    private SnomedConcept concept;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        // instantiate concept with corruptConceptId, we want to verify we can strip all non digits
        concept = (SnomedConcept) testTerminologyDAO.getTerminologyConcept(corruptConceptId);
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
    }

    /**
     * Test concept relationships.
     *
     * @throws Exception the exception
     */
    public void testConceptRelationships() throws Exception{
        assertTrue("Concept must have at least one relationship", concept.getRelationships().size() > 0);
    }

    /**
     * Test preferred term.
     *
     * @throws Exception the exception
     */
    public void testPreferredTerm() throws Exception{
        assertEquals("Preferred Term must be Myocardial Infarction", "Myocardial infarction", concept.getPreferredLabel());
    }

    /**
     * Test concept status.
     *
     * @throws Exception the exception
     */
    public void testConceptStatus() throws Exception{
        assertTrue("Concept status must be current", ComponentStatus.CURRENT == concept.getStatus());
    }

    /**
     * Test terminology concept utils.
     *
     * @throws Exception the exception
     */
    public void testTerminologyConceptUtils() throws Exception {
        SnomedConcept localConcept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "22298006");
        assertNotNull("Concept returned can not be null", localConcept);
        assertEquals("Both concepts must be equal" , concept, localConcept);
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
