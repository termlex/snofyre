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
import uk.nhs.cfh.dsp.srth.information.model.impl.EHRFactory;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;

// TODO: Auto-generated Javadoc
/**
 * A test case for dependency injection and methods of a {@link uk.nhs.cfh.dsp.srth.information.model.impl.EHRFactory}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 5:19:26 PM
 */
public class EHRFactoryImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The ehr factory service. */
    private EHRFactory ehrFactoryService;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Sets the ehr factory service.
     *
     * @param ehrFactoryService the new ehr factory service
     */
    public void setEhrFactoryService(EHRFactory ehrFactoryService) {
        this.ehrFactoryService = ehrFactoryService;
    }

    /**
     * Test get ehr.
     *
     * @throws Exception the exception
     */
    public void testGetEHR() throws Exception {
        assertNotNull("EHR must not be null", ehrFactoryService.getEHR());
        long patientId = 1234;
        EHR ehr = ehrFactoryService.getEHR(patientId);
        assertEquals("The patient ID must be 1234", 1234, ehr.getPatientId());
    }
}
