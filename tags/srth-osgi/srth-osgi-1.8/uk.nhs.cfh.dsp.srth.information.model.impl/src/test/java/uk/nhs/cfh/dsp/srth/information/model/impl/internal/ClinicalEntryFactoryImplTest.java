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
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryFactory;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 5:13:21 PM.
 */
public class ClinicalEntryFactoryImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The entry factory service. */
    private ClinicalEntryFactory entryFactoryService;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    protected String[] getConfigLocations()
    {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Sets the entry factory service.
     *
     * @param entryFactoryService the new entry factory service
     */
    public void setEntryFactoryService(ClinicalEntryFactory entryFactoryService) {
        this.entryFactoryService = entryFactoryService;
    }

    /**
     * Test get clinical finding entry.
     *
     * @throws Exception the exception
     */
    public void testGetClinicalFindingEntry() throws Exception {
        assertNotNull("Finding entry should not be null", entryFactoryService.getClinicalFindingEntry());
    }

    /**
     * Test get clinical activity entry.
     *
     * @throws Exception the exception
     */
    public void testGetClinicalActivityEntry() throws Exception {
        assertNotNull("Activity entry should not be null", entryFactoryService.getClinicalActivityEntry());
    }

    /**
     * Test get clinical feature entry.
     *
     * @throws Exception the exception
     */
    public void testGetClinicalFeatureEntry() throws Exception {
        assertNotNull("Feature entry should not be null", entryFactoryService.getClinicalFeatureEntry());
    }

    /**
     * Test get investigation entry.
     *
     * @throws Exception the exception
     */
    public void testGetInvestigationEntry() throws Exception {
        assertNotNull("Investigation entry should not be null", entryFactoryService.getInvestigationEntry());
    }

    /**
     * Test get intervention entry.
     *
     * @throws Exception the exception
     */
    public void testGetInterventionEntry() throws Exception {
        assertNotNull("Intervention entry should not be null", entryFactoryService.getInterventionEntry());
    }

    /**
     * Test get medication entry.
     *
     * @throws Exception the exception
     */
    public void testGetMedicationEntry() throws Exception {
        assertNotNull("Medication entry should not be null", entryFactoryService.getMedicationEntry());
    }
}
