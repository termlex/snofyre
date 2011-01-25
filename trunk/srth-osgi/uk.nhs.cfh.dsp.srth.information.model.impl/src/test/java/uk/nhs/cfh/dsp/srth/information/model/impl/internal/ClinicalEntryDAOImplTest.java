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
package uk.nhs.cfh.dsp.srth.information.model.impl.internal;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;

import java.util.Calendar;
import java.util.UUID;

/**
 * A test class for {@link uk.nhs.cfh.dsp.srth.information.model.impl.internal.ClinicalEntryDAOImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 1, 2010 at 10:04:26 PM
 */
public class ClinicalEntryDAOImplTest extends AbstractDependencyInjectionSpringContextTests {

    private ClinicalEntryFactory entryFactoryService;
    private ClinicalEntryDAO testClinicalEntryDAO;
    private BoundClinicalEntry entry ;
    private TerminologyConceptDAO testTerminologyDAO;
    private static final String conceptId = "22298006";
    private static final UUID uuid = UUID.randomUUID();
    private static final long id = 1;

    @Override
    public void onSetUp() throws Exception {

        assertNotNull("Clinical Entry Factory must not be null", entryFactoryService);
        assertNotNull("DAO must not be null", testClinicalEntryDAO);
        assertNotNull("Terminology Concept DAO must not be null", testTerminologyDAO);
        SnomedConcept concept = (SnomedConcept) testTerminologyDAO.getTerminologyConcept(conceptId);
        assertNotNull("Concept must not be null", concept);
        CloseToUserExpression expression = new CloseToUserExpressionImpl(concept);
        assertNotNull("Expression must not be null", expression);
        entry = entryFactoryService.getClinicalFindingEntry(id, expression, Calendar.getInstance());
        entry.setUuid(uuid);
        assertNotNull("Clinical Entry must not be null", entry);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    public void testSaveEntry() throws Exception {
        // save entry
        testClinicalEntryDAO.saveEntry(entry);
        // test if number of entries is at least 1
        assertEquals("Database must now contain one record", 1, testClinicalEntryDAO.findAllEntries().size());
    }

    public void testFindEntryWithCloseToUserUuId() throws Exception {
        // get entry based on saved uuid
        BoundClinicalEntry be = (BoundClinicalEntry) testClinicalEntryDAO.findEntryWithCloseToUserUuId(uuid.toString());
        assertNotNull("Clinical Entry returned must not be null", be);
        assertEquals("Returned entry must have uuid = "+uuid, uuid, be.getUuid());
    }

    public void testDeleteAllEntries() throws Exception {
        // purge all entries
        testClinicalEntryDAO.deleteAllEntries();
        // test if number of entries is 0
        assertEquals("Database must now contain no records", 0, testClinicalEntryDAO.findAllEntries().size());
    }

    public void setTestClinicalEntryFactory(ClinicalEntryFactory entryFactoryService) {
        this.entryFactoryService = entryFactoryService;
    }

    public void setTestClinicalEntryDAO(ClinicalEntryDAO testClinicalEntryDAO) {
        this.testClinicalEntryDAO = testClinicalEntryDAO;
    }

    public void setTestTerminologyDAO(TerminologyConceptDAO testTerminologyDAO) {
        this.testTerminologyDAO = testTerminologyDAO;
    }
}
