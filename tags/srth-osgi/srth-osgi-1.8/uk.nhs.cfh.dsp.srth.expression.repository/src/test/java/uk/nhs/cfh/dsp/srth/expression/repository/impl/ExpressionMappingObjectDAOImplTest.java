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
package uk.nhs.cfh.dsp.srth.expression.repository.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionMappingObjectImpl;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A test case for {@link uk.nhs.cfh.dsp.srth.expression.repository.impl.ExpressionMappingObjectDAOImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 11, 2010 at 12:20:27 PM.
 */
public class ExpressionMappingObjectDAOImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test expression mapping object dao. */
    private ExpressionMappingObjectDAO testExpressionMappingObjectDAO;
    
    /** The test entity factory service. */
    private ClinicalEntityFactory testEntityFactoryService;
    
    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;
    
    /** The expression mapping object. */
    private ExpressionMappingObject expressionMappingObject;
    
    /** The clinical expression. */
    private ClinicalExpression clinicalExpression;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    public void onSetUp() throws Exception {

        assertNotNull("Clinical Entity factory must not be null", testEntityFactoryService);
        SnomedConcept concept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "22298006");
        assertNotNull("Concept must not be null", concept);
        // create expression object.
        clinicalExpression = testEntityFactoryService.getExpression(new CloseToUserExpressionImpl(concept));
        assertNotNull("Clinical Expression can not be null", clinicalExpression);
        expressionMappingObject = new ExpressionMappingObjectImpl();
        expressionMappingObject.setCloseToUserCGForm(clinicalExpression.getCompositionalGrammarForm());
        expressionMappingObject.setNormalFormCGForm(clinicalExpression.getCompositionalGrammarNormalForm());
        expressionMappingObject.setSituationNormalFormCGForm(clinicalExpression.getCompositionalSituationNormalForm());
        expressionMappingObject.setCloseToUserExpressionUuid(clinicalExpression.getUuid());
        expressionMappingObject.setNormalFormExpressionUuid(clinicalExpression.getNormalFormUuid());
        expressionMappingObject.setSituationNormalFormExpressionUuid(clinicalExpression.getSituationNormalFormUuid());
        assertNotNull("Expression Mapping Object can not be null", expressionMappingObject);
        System.out.println("expressionMappingObject = " + expressionMappingObject);
        assertNotNull("Expression Mapping object DAO can not be null", testExpressionMappingObjectDAO);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Test save.
     *
     * @throws Exception the exception
     */
    public void testSave() throws Exception {
        testExpressionMappingObjectDAO.save(expressionMappingObject);
        assertEquals("Repo DB must contain one record.", 1, testExpressionMappingObjectDAO.findAll().size());
    }

    /**
     * Test find using ctu id.
     *
     * @throws Exception the exception
     */
    public void testFindUsingCTUId() throws Exception {
        testExpressionMappingObjectDAO.save(expressionMappingObject);
        ExpressionMappingObject returnedObject = testExpressionMappingObjectDAO.findUsingCTUId(clinicalExpression.getUuid());
        assertNotNull("Returned object must not be null", returnedObject);
        assertTrue("UUIDs must match", returnedObject.getCloseToUserExpressionUuid().equals(clinicalExpression.getUuid()));
    }

    /**
     * Test find using nfe id.
     *
     * @throws Exception the exception
     */
    public void testFindUsingNFEId() throws Exception {
        testExpressionMappingObjectDAO.save(expressionMappingObject);
        ExpressionMappingObject returnedObject = testExpressionMappingObjectDAO.findUsingNFEId(clinicalExpression.getNormalFormUuid());
        assertNotNull("Returned object must not be null", returnedObject);
        assertTrue("UUIDs must match", returnedObject.getNormalFormExpressionUuid().equals(clinicalExpression.getNormalFormUuid()));
    }

    /**
     * Test find using snfe id.
     *
     * @throws Exception the exception
     */
    public void testFindUsingSNFEId() throws Exception {
        testExpressionMappingObjectDAO.save(expressionMappingObject);
        ExpressionMappingObject returnedObject = testExpressionMappingObjectDAO.findUsingSNFEId(clinicalExpression.getSituationNormalFormUuid());
        assertNotNull("Returned object must not be null", returnedObject);
        assertTrue("UUIDs must match", returnedObject.getSituationNormalFormExpressionUuid().equals(clinicalExpression.getSituationNormalFormUuid()));
    }

    /**
     * Test find using ctu compositional grammar form.
     *
     * @throws Exception the exception
     */
    public void testFindUsingCTUCompositionalGrammarForm() throws Exception {
        ExpressionMappingObject returnedObject = testExpressionMappingObjectDAO.findUsingCTUCompositionalGrammarForm(clinicalExpression.getCompositionalGrammarForm());
        assertNotNull("Returned object must not be null", returnedObject);
        assertTrue("Compositional grammar must match", returnedObject.getCloseToUserCGForm().equals(clinicalExpression.getCompositionalGrammarForm()));
    }

    /**
     * Test find using nfe compositional grammar form.
     *
     * @throws Exception the exception
     */
    public void testFindUsingNFECompositionalGrammarForm() throws Exception {
        ExpressionMappingObject returnedObject = testExpressionMappingObjectDAO.findUsingNFECompositionalGrammarForm(clinicalExpression.getCompositionalGrammarNormalForm());
        assertNotNull("Returned object must not be null", returnedObject);
        assertTrue("Compositional grammar must match", returnedObject.getNormalFormCGForm().equals(clinicalExpression.getCompositionalGrammarNormalForm()));
    }

    /**
     * Test find using snfe compositional grammar form.
     *
     * @throws Exception the exception
     */
    public void testFindUsingSNFECompositionalGrammarForm() throws Exception {
        ExpressionMappingObject returnedObject = testExpressionMappingObjectDAO.findUsingSNFECompositionalGrammarForm(clinicalExpression.getCompositionalSituationNormalForm());
        assertNotNull("Returned object must not be null", returnedObject);
        assertTrue("Compositional grammar must match", returnedObject.getSituationNormalFormCGForm().equals(clinicalExpression.getCompositionalSituationNormalForm()));
    }

    /**
     * Test return all as lite objects.
     *
     * @throws Exception the exception
     */
    public void testReturnAllAsLiteObjects() throws Exception {
        List<ExpressionMappingObject> list = testExpressionMappingObjectDAO.returnAllAsLiteObjects();
        assertTrue("Returned list size must be greater than 0", list.size() > 0);
        ExpressionMappingObject returnedObject = list.get(0);
        assertNotNull("Expression mapping object returned must not be null", returnedObject);
        assertTrue("Compositional grammar must match", returnedObject.getNormalFormCGForm().equals(clinicalExpression.getCompositionalGrammarNormalForm()));
        // don't try to check UUIDs since we do not know which object is returned by index 0
    }

    /**
     * Test delete all.
     *
     * @throws Exception the exception
     */
    public void testDeleteAll() throws Exception {
        testExpressionMappingObjectDAO.deleteAll();
        assertEquals("Database must contain 0 objects after delete... ", 0,
                testExpressionMappingObjectDAO.findAll().size());
    }

    /**
     * Sets the test expression mapping object dao.
     *
     * @param testExpressionMappingObjectDAO the new test expression mapping object dao
     */
    public void setTestExpressionMappingObjectDAO(ExpressionMappingObjectDAO testExpressionMappingObjectDAO) {
        this.testExpressionMappingObjectDAO = testExpressionMappingObjectDAO;
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
     * Sets the test terminology dao.
     *
     * @param testTerminologyDAO the new test terminology dao
     */
    public void setTestTerminologyDAO(TerminologyConceptDAO testTerminologyDAO) {
        this.testTerminologyDAO = testTerminologyDAO;
    }
}
