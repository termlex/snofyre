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
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionTransitiveClosureTableGenerator;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionMappingObjectImpl;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntityFactory;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;

// TODO: Auto-generated Javadoc
/**
 * A test case for {@link uk.nhs.cfh.dsp.srth.expression.repository.impl.ExpressionTransitiveClosureTableGeneratorImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 2:08:30 AM.
 */
public class ExpressionTransitiveClosureTableGeneratorImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test expression transitive closure table generator. */
    private ExpressionTransitiveClosureTableGenerator testExpressionTransitiveClosureTableGenerator;
    
    /** The test expression subsumption relationship dao. */
    private ExpressionSubsumptionRelationshipDAO testExpressionSubsumptionRelationshipDAO;
    
    /** The test expression mapping object dao. */
    private ExpressionMappingObjectDAO testExpressionMappingObjectDAO;
    
    /** The test entity factory service. */
    private ClinicalEntityFactory testEntityFactoryService;
    
    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    public void onSetUp() throws Exception {

        assertNotNull("Clinical Entity factory must not be null", testEntityFactoryService);
        // create a few mapping objects and save them
        createMappingObject("22298006");
        createMappingObject("428196007");
        createMappingObject("194810002");

        assertNotNull("Expression Relationship DAO can not be null", testExpressionSubsumptionRelationshipDAO);
        assertNotNull("TC Generator can not be null", testExpressionTransitiveClosureTableGenerator);
    }

    /**
     * Creates the mapping object.
     *
     * @param conceptId the concept id
     */
    private void createMappingObject(String conceptId){
        
        SnomedConcept concept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, conceptId);
        assertNotNull("Concept must not be null", concept);
        // create expression object.
        ClinicalExpression clinicalExpression = testEntityFactoryService.getExpression(new CloseToUserExpressionImpl(concept));
        assertNotNull("Clinical Expression can not be null", clinicalExpression);
        ExpressionMappingObject expressionMappingObject = new ExpressionMappingObjectImpl();
        expressionMappingObject.setCloseToUserCGForm(clinicalExpression.getCompositionalGrammarForm());
        expressionMappingObject.setNormalFormCGForm(clinicalExpression.getCompositionalGrammarNormalForm());
        expressionMappingObject.setSituationNormalFormCGForm(clinicalExpression.getCompositionalSituationNormalForm());
        expressionMappingObject.setCloseToUserExpressionUuid(clinicalExpression.getUuid());
        expressionMappingObject.setNormalFormExpressionUuid(clinicalExpression.getNormalFormUuid());
        expressionMappingObject.setSituationNormalFormExpressionUuid(clinicalExpression.getSituationNormalFormUuid());
        assertNotNull("Expression Mapping Object can not be null", expressionMappingObject);
        assertNotNull("Expression Mapping object DAO can not be null", testExpressionMappingObjectDAO);

        // save object
        testExpressionMappingObjectDAO.save(expressionMappingObject);
        assertTrue("Repo DB must contain at least 1 record.",
                testExpressionMappingObjectDAO.findAll().size() > 0);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Test generate tc table.
     *
     * @throws Exception the exception
     */
    public void testGenerateTCTable() throws Exception {
        // use testExpressionSubsumptionRelationshipDAO to remove all entries in TC table
        testExpressionSubsumptionRelationshipDAO.deleteAll();
        assertEquals("Database must contain 0 relationships after delete... ", 0,
                testExpressionSubsumptionRelationshipDAO.findAllRelationships().size());
        // now populate TC table
        testExpressionTransitiveClosureTableGenerator.generateTCTable(true);
        assertTrue("Database must contain more than one entry after populating...",
                testExpressionSubsumptionRelationshipDAO.findAllRelationships().size() > 0);
        // remove all entries in mapping objects table
        testExpressionMappingObjectDAO.deleteAll();
    }

    /**
     * Sets the test expression transitive closure table generator.
     *
     * @param testExpressionTransitiveClosureTableGenerator the new test expression transitive closure table generator
     */
    public void setTestExpressionTransitiveClosureTableGenerator(ExpressionTransitiveClosureTableGenerator
            testExpressionTransitiveClosureTableGenerator) {
        this.testExpressionTransitiveClosureTableGenerator = testExpressionTransitiveClosureTableGenerator;
    }

    /**
     * Sets the test expression subsumption relationship dao.
     *
     * @param testExpressionSubsumptionRelationshipDAO the new test expression subsumption relationship dao
     */
    public void setTestExpressionSubsumptionRelationshipDAO(ExpressionSubsumptionRelationshipDAO
            testExpressionSubsumptionRelationshipDAO) {
        this.testExpressionSubsumptionRelationshipDAO = testExpressionSubsumptionRelationshipDAO;
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
