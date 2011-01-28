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
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

// TODO: Auto-generated Javadoc
/**
 * A test case for {@link uk.nhs.cfh.dsp.snomed.dao.impl.SpringEnabledSnomedConceptDAO}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 19-Mar-2010 at 11:30:56
 */
public class SpringEnabledSnomedConceptDAOTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test terminology dao. */
    private SnomedConceptDatabaseDAO testTerminologyDAO;
    
    /** The test spring enabled terminology dao. */
    private SpringEnabledSnomedConceptDAO testSpringEnabledTerminologyDAO;
    
    /** The Constant conceptId. */
    private static final String conceptId = "22298006";
    
    /** The concept. */
    private SnomedConcept concept;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        concept = (SnomedConcept) testTerminologyDAO.getTerminologyConcept(conceptId);
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
     * Test save terminology concept.
     *
     * @throws Exception the exception
     */
    public void testSaveTerminologyConcept() throws Exception {
        // save concpet using testSpringEnabledTerminologyDAO
        testSpringEnabledTerminologyDAO.saveTerminologyConcept(concept);
        // get count of concepts
        assertTrue("Concept count must be more than 0", testSpringEnabledTerminologyDAO.getConceptCount() > 0);
    }

    /**
     * Test get terminology concept.
     *
     * @throws Exception the exception
     */
    public void testGetTerminologyConcept() throws Exception {
        SnomedConcept returnedConcept = (SnomedConcept) testSpringEnabledTerminologyDAO.getTerminologyConcept(conceptId);
        assertNotNull("Returned concept must not be null", returnedConcept);
        assertTrue("Returned concept must be the same as saved concept", concept.equals(returnedConcept));
    }

    /**
     * Sets the test terminology dao.
     *
     * @param testTerminologyDAO the new test terminology dao
     */
    public void setTestTerminologyDAO(SnomedConceptDatabaseDAO testTerminologyDAO) {
        this.testTerminologyDAO = testTerminologyDAO;
    }

    /**
     * Sets the test spring enabled terminology dao.
     *
     * @param testSpringEnabledTerminologyDAO the new test spring enabled terminology dao
     */
    public void setTestSpringEnabledTerminologyDAO(SpringEnabledSnomedConceptDAO testSpringEnabledTerminologyDAO) {
        this.testSpringEnabledTerminologyDAO = testSpringEnabledTerminologyDAO;
    }
}
