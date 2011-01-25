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
package uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.ClinicalFindingEntry;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A test class for {@link uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.EHRImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2010 at 9:37:39 AM
 */
public class EHRImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;
    
    /** The entry factory service. */
    private ClinicalEntryFactory entryFactoryService;
    
//    /** The uuid. */
//    private static UUID uuid = UUID.randomUUID();
    private static long id = 1;
    
    /** The ehr. */
    private EHRImpl ehr;
    
    /** The concept. */
    private SnomedConcept concept;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    public void onSetUp() throws Exception{
        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        concept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "22298006");
        assertNotNull("Concept must not be null", concept);
        assertNotNull("Entry factory can not be null", entryFactoryService);
        // create an EHR using uuid
        ehr = new EHRImpl(id);
        assertNotNull("EHR must not be null", ehr);
    }

    /**
     * Test get patient id.
     *
     * @throws Exception the exception
     */
    public void testGetPatientId() throws Exception {
        assertEquals("Patient ID must be the same as uuid", id, ehr.getPatientId());
    }

    /**
     * Test add finding.
     *
     * @throws Exception the exception
     */
    public void testAddFinding() throws Exception {
        CloseToUserExpression closeToUserExpression = new CloseToUserExpressionImpl(concept);
        assertNotNull("Close to user expression can not be null", closeToUserExpression);
        // create findingEntry and add to ehr
        ClinicalFindingEntry findingEntry = entryFactoryService.getClinicalFindingEntry(ehr.getPatientId(),
                closeToUserExpression, Calendar.getInstance());
        assertNotNull("Finding entry can not be null", findingEntry);
        ehr.addFinding(findingEntry);
        assertEquals("Must contain one entry", 1, ehr.getClinicalFindings().size());
        assertTrue("EHR must contain entry for finding", ehr.containsFinding(findingEntry));
    }

    /**
     * Sets the test terminology dao.
     *
     * @param testTerminologyDAO the new test terminology dao
     */
    public void setTestTerminologyDAO(TerminologyConceptDAO testTerminologyDAO) {
        this.testTerminologyDAO = testTerminologyDAO;
    }

    /**
     * Sets the entry factory service.
     *
     * @param entryFactoryService the new entry factory service
     */
    public void setEntryFactoryService(ClinicalEntryFactory entryFactoryService) {
        this.entryFactoryService = entryFactoryService;
    }
}
