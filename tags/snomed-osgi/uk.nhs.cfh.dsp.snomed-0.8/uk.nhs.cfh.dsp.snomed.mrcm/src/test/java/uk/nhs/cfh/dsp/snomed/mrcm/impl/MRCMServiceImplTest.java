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
package uk.nhs.cfh.dsp.snomed.mrcm.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.mrcm.MRCMGenerator;
import uk.nhs.cfh.dsp.snomed.mrcm.MRCMService;
import uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao;
import uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint;
import uk.nhs.cfh.dsp.snomed.mrcm.om.impl.MRCMConstraintImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;

import java.util.List;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 9:23:04 PM.
 */
public class MRCMServiceImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test mrcm generator. */
    private MRCMGenerator testMrcmGenerator;
    
    /** The test mrcm dao. */
    private MRCMDao testMrcmDAO;
    
    /** The test mrcm service. */
    private MRCMService testMrcmService;
    
    /** The constraint. */
    private MRCMConstraint constraint;

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
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("MRCM Generator can not be null", testMrcmGenerator);
        assertNotNull("MRCM DAO can not be null", testMrcmDAO);
        assertNotNull("MRCM Service can not be null", testMrcmService);

        // create a constraint
        constraint = new MRCMConstraintImpl();
        constraint.setSourceId("404684003");
        constraint.setSourceName("<<Clinical finding (finding)");
        constraint.setAttributeId("246075003");
        constraint.setAttributeName("0:* M:ALL Causative agent (attribute)");
        constraint.setValueId("78621006");
        constraint.setValueName("<<Physical force (physical force)");
        assertNotNull("Constraint can not be null", constraint);
    }

    /**
     * Test check and intialise database.
     *
     * @throws Exception the exception
     */
    public void testCheckAndIntialiseDatabase() throws Exception {
        assertTrue("Repo must contain at least one constraint", testMrcmService.findAllConstraints().size() > 0);
    }


    /**
     * Test delete all constraints.
     *
     * @throws Exception the exception
     */
    public void testDeleteAllConstraints() throws Exception {
        testMrcmService.deleteAllConstraints();
        assertEquals("Repo must contain no constrains", 0, testMrcmService.findAllConstraints().size());
    }

    /**
     * Test save constraint.
     *
     * @throws Exception the exception
     */
    public void testSaveConstraint() throws Exception {
        testMrcmService.deleteAllConstraints();
        testMrcmService.saveConstraint(constraint);
        assertEquals("Repo must hold exactly one constraint", 1, testMrcmService.findAllConstraints().size());
    }

    /**
     * Test find constraint.
     *
     * @throws Exception the exception
     */
    public void testFindConstraint() throws Exception {
        List<MRCMConstraint> result = testMrcmService.findConstraint("404684003", "246075003");
        assertTrue("Must return at least one constraint", result.size() > 0);
    }

    /**
     * Test get sanctioned values.
     *
     * @throws Exception the exception
     */
    public void testGetSanctionedValues() throws Exception {
        Set<String> values = testMrcmService.getSanctionedValues("404684003", "246075003");
        assertTrue("At least one value entity must have been returned", values.size() > 0);
        assertTrue("Value Ids must contain : 78621006", values.contains("78621006"));
    }
    
    /**
     * Test get sanctioned attributes.
     *
     * @throws Exception the exception
     */
    public void testGetSanctionedAttributes() throws Exception {
        Set<String> values = testMrcmService.getSanctionedAttributes(ConceptType.FINDING);
        System.out.println("values = " + values);        
        assertTrue("At least one value entity must have been returned", values.size() > 0);
        assertTrue("Value Ids must contain : 246075003", values.contains("246075003"));
    }

    /**
     * Sets the test mrcm generator.
     *
     * @param testMrcmGenerator the new test mrcm generator
     */
    public void setTestMrcmGenerator(MRCMGenerator testMrcmGenerator) {
        this.testMrcmGenerator = testMrcmGenerator;
    }

    /**
     * Sets the test mrcm dao.
     *
     * @param testMrcmDAO the new test mrcm dao
     */
    public void setTestMrcmDAO(MRCMDao testMrcmDAO) {
        this.testMrcmDAO = testMrcmDAO;
    }

    /**
     * Sets the test mrcm service.
     *
     * @param testMrcmService the new test mrcm service
     */
    public void setTestMrcmService(MRCMService testMrcmService) {
        this.testMrcmService = testMrcmService;
    }
}
