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
package uk.nhs.cfh.dsp.srth.demographics.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.demographics.person.Patient;
import uk.nhs.cfh.dsp.srth.demographics.person.impl.PatientImpl;

// TODO: Auto-generated Javadoc
/**
 * A test class for {@link uk.nhs.cfh.dsp.srth.demographics.impl.PatientDAOImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2010 at 3:58:50 PM
 */
public class PatientDAOImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test patient dao. */
    private PatientDAO testPatientDAO;
    
    /** The patient. */
    private Patient patient;
    
    /** The patient id. */
    private static final long id = 1;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull(testPatientDAO);
        // create a dummy patient
        patient = new PatientImpl();
        patient.setFirstName("Test");
        patient.setLastName("Head");
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Test save patient.
     *
     * @throws Exception the exception
     */
    public void testSavePatient() throws Exception {
        assertNotNull("Patient can not be null", patient);
        assertNotNull("Patient ID can not be null", patient.getId());
        testPatientDAO.savePatient(patient);
        assertEquals("Patient id must be 1", id, patient.getId());
        assertEquals("Database must now contain one record", 1, testPatientDAO.findAllPatients().size());
    }

    /**
     * Test find patient.
     *
     * @throws Exception the exception
     */
    public void testFindPatient() throws Exception {
        Patient localPatient = testPatientDAO.findPatient(String.valueOf(id));
        assertNotNull("Patient must have been returned", localPatient);
        assertEquals("Patient id must be the same as the static id", id,  localPatient.getId());
        assertTrue("First name must be Test", "Test".equalsIgnoreCase(localPatient.getFirstName()));
        assertTrue("Last name must be Head", "Head".equalsIgnoreCase(localPatient.getLastName()));
    }

    /**
     * Test find patient using long id.
     *
     * @throws Exception the exception
     */
    public void testFindPatientUsingLongId() throws Exception {
        Patient localPatient = testPatientDAO.findPatient(id);
        assertNotNull("Patient must have been returned", localPatient);
        assertEquals("Patient id must be the same as the static id", id,  localPatient.getId());
        assertTrue("First name must be Test", "Test".equalsIgnoreCase(localPatient.getFirstName()));
        assertTrue("Last name must be Head", "Head".equalsIgnoreCase(localPatient.getLastName()));
    }

    /**
     * Test total patient count.
     *
     * @throws Exception the exception
     */
    public void testTotalPatientCount() throws Exception {
        long count = testPatientDAO.getTotalPatientCountInDatabase();
        assertNotNull("Count must not be null", count);
        assertEquals("Count must be 1", 1, count);
    }

    /**
     * Test get next patient id.
     *
     * @throws Exception the exception
     */
    public void testGetNextPatientId() throws Exception {
        long id = testPatientDAO.getNextPatientId();
        assertNotNull("Id must not be null", id);
        assertEquals("Next patient must be 2", 2, id);
    }

    /**
     * Test get first patient id.
     *
     * @throws Exception the exception
     */
    public void testGetFirstPatientId() throws Exception {
        long id = testPatientDAO.getFirstPatientId();
        assertNotNull("Id must not be null", id);
        assertEquals("Next patient must be 1", 1, id);
    }

    public void testDeletePatient() throws Exception {
        testPatientDAO.deletePatient(testPatientDAO.findPatient(id));
        assertEquals("Database must now contain no records", 0, testPatientDAO.findAllPatients().size());
    }

    /**
 * Sets the test patient dao.
 *
 * @param testPatientDAO the new test patient dao
 */
public void setTestPatientDAO(PatientDAO testPatientDAO) {
        this.testPatientDAO = testPatientDAO;
    }
}
