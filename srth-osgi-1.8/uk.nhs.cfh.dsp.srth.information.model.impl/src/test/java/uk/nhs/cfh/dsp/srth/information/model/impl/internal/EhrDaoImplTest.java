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
import uk.nhs.cfh.dsp.srth.information.model.impl.EhrDao;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.EHRImpl;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;

// TODO: Auto-generated Javadoc
/**
 * A test case for {@link uk.nhs.cfh.dsp.srth.information.model.impl.internal.EhrDaoImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2010 at 12:00:03 PM
 */
public class EhrDaoImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The ehr dao. */
    private EhrDao ehrDAO;
    
    /** The ehr. */
    private EHR ehr;

    private static long id = 1;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        // create a test ehr
        ehr = new EHRImpl(id);
        assertNotNull("EHR created can not be null", ehr);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    protected String[] getConfigLocations()
    {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Sets the ehr dao.
     *
     * @param ehrDAO the new ehr dao
     */
    public void setEhrDAO(EhrDao ehrDAO) {
        this.ehrDAO = ehrDAO;
    }

    /**
     * Test initialisation.
     *
     * @throws Exception the exception
     */
    public void testInitialisation() throws Exception{
        assertNotNull("EHR must have been instantiated", ehr);
    }

    /**
     * Test save ehr.
     *
     * @throws Exception the exception
     */
    public void testSaveEHR() throws Exception {
        ehrDAO.saveEHR(ehr);
        assertEquals("Number of ehrs must be 1", 1, ehrDAO.findAll().size());
    }

    /**
     * Test find by id.
     *
     * @throws Exception the exception
     */
    public void testFindById() throws Exception {
        EHR localEHR = ehrDAO.findById(id);
        assertNotNull("An EHR must be returned", localEHR);
        assertEquals("Patient Ids must be the same", ehr.getPatientId(), localEHR.getPatientId());
    }

    public void testDeleteEHR() throws Exception {
        ehrDAO.deleteEHR(ehrDAO.findById(id));
        assertEquals("Number of ehrs must be 0", 0, ehrDAO.findAll().size());
    }
}
