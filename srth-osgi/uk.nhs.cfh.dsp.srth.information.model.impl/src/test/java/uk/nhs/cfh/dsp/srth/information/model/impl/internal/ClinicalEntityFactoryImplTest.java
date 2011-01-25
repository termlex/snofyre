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
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.*;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 4:58:56 PM.
 */
public class ClinicalEntityFactoryImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test normal form generator. */
    private NormalFormGenerator testNormalFormGenerator;
    
    /** The test human readable renderer. */
    private HumanReadableRender testHumanReadableRenderer;
    
    /** The test situation expression generator. */
    private SituationExpressionGenerator testSituationExpressionGenerator;
    
    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;
    
    /** The test entity factory service. */
    private ClinicalEntityFactory testEntityFactoryService;
    
    /** The Constant conceptId. */
    private static final String conceptId = "22298006";
    
    /** The concept. */
    private SnomedConcept concept;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    protected String[] getConfigLocations()
    {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("Normal form generator must not be null", testNormalFormGenerator);
        assertNotNull("Human readable renderer must not be null", testHumanReadableRenderer);
        assertNotNull("Situation generator must not be null", testSituationExpressionGenerator);
        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        concept = (SnomedConcept) testTerminologyDAO.getTerminologyConcept(conceptId);
        assertNotNull("Concept must not be null", concept);
    }

    /**
     * Sets the test entity factory service.
     *
     * @param testEntityFactoryService the new test entity factory service
     */
    public void setTestEntityFactoryService(ClinicalEntityFactory testEntityFactoryService) {
        this.testEntityFactoryService = testEntityFactoryService;
    }

    /**
     * Test get clinical feature entity.
     *
     * @throws Exception the exception
     */
    public void testGetClinicalFeatureEntity() throws Exception {
        CloseToUserExpression expression = new CloseToUserExpressionImpl(concept);
        assertNotNull("Close to user expression must not be null", expression);
        ClinicalFindingEntity entity = testEntityFactoryService.getClinicalFindingEntity(expression);
        assertNotNull("Feature entity must not be null", entity);
        assertNotNull("Expression in entity must not be null", entity.getExpression());
        assertEquals("Expression in entity must be the ctu", expression.getUuid(), entity.getExpression().getUuid());
    }

    /**
     * Test get clinical finding entity.
     *
     * @throws Exception the exception
     */
    public void testGetClinicalFindingEntity() throws Exception {
        assertNotNull("Finding entity must not be null", testEntityFactoryService.getClinicalFindingEntity());
    }

    /**
     * Test get investigation entity.
     *
     * @throws Exception the exception
     */
    public void testGetInvestigationEntity() throws Exception {
        assertNotNull("Investigation entity must not be null", testEntityFactoryService.getInvestigationEntity());
    }

    /**
     * Test get intervention entity.
     *
     * @throws Exception the exception
     */
    public void testGetInterventionEntity() throws Exception {
        assertNotNull("Intervention entity must not be null", testEntityFactoryService.getInterventionEntity());
    }

    /**
     * Test get medication entity.
     *
     * @throws Exception the exception
     */
    public void testGetMedicationEntity() throws Exception {
        assertNotNull("Medication entity must not be null", testEntityFactoryService.getMedicationEntity());
    }

    /**
     * Test get anatomical location entity.
     *
     * @throws Exception the exception
     */
    public void testGetAnatomicalLocationEntity() throws Exception {
        assertNotNull("Anatomical entity must not be null", testEntityFactoryService.getAnatomicalLocationEntity());
    }

    /**
     * Test get entity.
     *
     * @throws Exception the exception
     */
    public void testGetEntity() throws Exception {
        assertTrue("Must return Feature Entity", testEntityFactoryService.getEntity(ClinicalEntity.Type.FEATURE) instanceof ClinicalFeatureEntity);
        assertTrue("Must return Finding Entity", testEntityFactoryService.getEntity(ClinicalEntity.Type.FINDING) instanceof ClinicalFindingEntity);
        assertTrue("Must return Intervention Entity", testEntityFactoryService.getEntity(ClinicalEntity.Type.INTERVENTION) instanceof InterventionEntity);
        assertTrue("Must return Investigation Entity", testEntityFactoryService.getEntity(ClinicalEntity.Type.INVESTIGATION) instanceof InvestigationEntity);
        assertTrue("Must return Medication Entity", testEntityFactoryService.getEntity(ClinicalEntity.Type.MEDICATION) instanceof MedicationEntity);
        assertTrue("Must return Anatomical Entity", testEntityFactoryService.getEntity(ClinicalEntity.Type.ANATOMICAL_LOCATION) instanceof AnatomicalLocationEntity);
    }

    /**
     * Sets the test normal form generator.
     *
     * @param testNormalFormGenerator the new test normal form generator
     */
    public void setTestNormalFormGenerator(NormalFormGenerator testNormalFormGenerator) {
        this.testNormalFormGenerator = testNormalFormGenerator;
    }

    /**
     * Sets the test human readable renderer.
     *
     * @param testHumanReadableRenderer the new test human readable renderer
     */
    public void setTestHumanReadableRenderer(HumanReadableRender testHumanReadableRenderer) {
        this.testHumanReadableRenderer = testHumanReadableRenderer;
    }

    /**
     * Sets the test situation expression generator.
     *
     * @param testSituationExpressionGenerator the new test situation expression generator
     */
    public void setTestSituationExpressionGenerator(SituationExpressionGenerator testSituationExpressionGenerator) {
        this.testSituationExpressionGenerator = testSituationExpressionGenerator;
    }

    /**
     * Sets the test terminology dao.
     *
     * @param testTerminologyDAO the new test terminology dao
     */
    public void setTestTerminologyDAO(TerminologyConceptDAO testTerminologyDAO) {
        this.testTerminologyDAO = testTerminologyDAO;
    }
}
