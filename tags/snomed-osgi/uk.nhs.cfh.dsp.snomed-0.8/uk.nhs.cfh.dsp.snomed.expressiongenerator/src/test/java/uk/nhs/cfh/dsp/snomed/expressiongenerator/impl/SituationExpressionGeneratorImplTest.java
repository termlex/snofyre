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
package uk.nhs.cfh.dsp.snomed.expressiongenerator.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.SituationExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.expressiongenerator.SituationExpressionGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2010 at 1:18:56 PM.
 */
public class SituationExpressionGeneratorImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;
    
    /** The Constant miConceptId. */
    private static final String miConceptId = "22298006";
    
    /** The Constant mastectomyConceptId. */
    private static final String mastectomyConceptId = "69031006";
    
    /** The test situation expression generator. */
    private SituationExpressionGenerator testSituationExpressionGenerator;
    
    /** The mi concept. */
    private SnomedConcept miConcept;
    
    /** The mastectomy concept. */
    private SnomedConcept mastectomyConcept;
    
    /** The situation we concept. */
    private SnomedConcept situationWEConcept;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        assertNotNull("Expression generator must not be null", testSituationExpressionGenerator);
        miConcept = (SnomedConcept) testTerminologyDAO.getTerminologyConcept(miConceptId);
        assertNotNull("MI concept must not be null", miConcept);
        mastectomyConcept = (SnomedConcept) testTerminologyDAO.getTerminologyConcept(mastectomyConceptId);
        assertNotNull("Mastectomy concept must not be null", mastectomyConcept);
        situationWEConcept = (SnomedConcept) testTerminologyDAO.getTerminologyConcept("243796009");
        assertNotNull("Situation WEC concept must not be null", situationWEConcept);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Test get assertable planned procedure expression.
     *
     * @throws Exception the exception
     */
    public void testGetAssertablePlannedProcedureExpression() throws Exception {
        SituationExpression expression = testSituationExpressionGenerator.getAssertablePlannedProcedureExpression(mastectomyConcept);
        assertNotNull("Expression must not be null", expression);
        assertTrue("Focus concept must be equal", mastectomyConcept.equals(expression.getFocusConcept()));
        SnomedConcept procedureContext = expression.getAssociatedContext();
        assertNotNull("Procedure context must not be null", procedureContext);
        assertTrue("Subject relationship context value must be 397943006", "397943006".equalsIgnoreCase(procedureContext.getConceptID()));
    }

    /**
     * Test get assertable procedure expression.
     *
     * @throws Exception the exception
     */
    public void testGetAssertableProcedureExpression() throws Exception {
        SituationExpression expression = testSituationExpressionGenerator.getAssertableProcedureExpression(mastectomyConcept);
        assertNotNull("Expression must not be null", expression);
        assertTrue("Focus concept must be equal", mastectomyConcept.equals(expression.getFocusConcept()));
        SnomedConcept procedureContext = expression.getAssociatedContext();
        assertNotNull("Procedure context must not be null", procedureContext);
        assertTrue("Subject relationship context value must be 398166005", "398166005".equalsIgnoreCase(procedureContext.getConceptID()));
    }

    /**
     * Test get assertable finding expression.
     *
     * @throws Exception the exception
     */
    public void testGetAssertableFindingExpression() throws Exception {
        SituationExpression expression = testSituationExpressionGenerator.getAssertableFindingExpression(miConcept);
        assertNotNull("Expression must not be null", expression);
        assertTrue("Focus concept must be equal", miConcept.equals(expression.getFocusConcept()));

        SnomedConcept tempContext = expression.getTemporalContext();
        assertNotNull("Temporal context must not be null", tempContext);
        assertTrue("Temporal context value must be 410512000", "410512000".equalsIgnoreCase(tempContext.getConceptID()));

        SnomedConcept sbContext = expression.getSubjectRelationship();
        assertNotNull("Subject relationship context must not be null", sbContext);
        assertTrue("Subject relationship context value must be 410604004", "410604004".equalsIgnoreCase(sbContext.getConceptID()));

        SnomedConcept findingContext = expression.getAssociatedContext();
        assertNotNull("Finding context must not be null", findingContext);
        assertTrue("Finding context value must be 410515003", "410515003".equalsIgnoreCase(findingContext.getConceptID()));
    }

    /**
     * Test get assertable past finding expression.
     *
     * @throws Exception the exception
     */
    public void testGetAssertablePastFindingExpression() throws Exception {
        SituationExpression expression = testSituationExpressionGenerator.getAssertablePastFindingExpression(miConcept);
        assertNotNull("Expression must not be null", expression);
        assertTrue("Focus concept must be equal", miConcept.equals(expression.getFocusConcept()));
        SnomedConcept tempContext = expression.getTemporalContext();
        assertNotNull("Temporal context must not be null", tempContext);
        assertTrue("Temporal context value must be 410513005", "410513005".equalsIgnoreCase(tempContext.getConceptID()));
    }

    /**
     * Test get assertable family history finding expression.
     *
     * @throws Exception the exception
     */
    public void testGetAssertableFamilyHistoryFindingExpression() throws Exception {
        SituationExpression expression = testSituationExpressionGenerator.getAssertableFamilyHistoryFindingExpression(miConcept);
        assertNotNull("Expression must not be null", expression);
        assertTrue("Focus concept must be equal", miConcept.equals(expression.getFocusConcept()));
        SnomedConcept sbContext = expression.getSubjectRelationship();
        assertNotNull("Subject relationship context must not be null", sbContext);
        assertTrue("Subject relationship context value must be 125677006", "125677006".equalsIgnoreCase(sbContext.getConceptID()));
    }

    /**
     * Test get default context situation expression.
     *
     * @throws Exception the exception
     */
    public void testGetDefaultContextSituationExpression() throws Exception {
        SituationExpression expression = testSituationExpressionGenerator.getDefaultContextSituationExpression(miConcept);
        assertNotNull("Expression must not be null", expression);
        assertTrue("Focus concept must be equal", miConcept.equals(expression.getFocusConcept()));

        SnomedConcept tempContext = expression.getTemporalContext();
        assertNotNull("Temporal context must not be null", tempContext);
        assertTrue("Temporal context value must be 410510008", "410510008".equalsIgnoreCase(tempContext.getConceptID()));

        SnomedConcept sbContext = expression.getSubjectRelationship();
        assertNotNull("Subject relationship context must not be null", sbContext);
        assertTrue("Subject relationship context value must be 125676002", "125676002".equalsIgnoreCase(sbContext.getConceptID()));

        SnomedConcept findingContext = expression.getAssociatedContext();
        assertNotNull("Finding context must not be null", findingContext);
        assertTrue("Finding context value must be 410514004", "410514004".equalsIgnoreCase(findingContext.getConceptID()));
    }

    /**
     * Test assertable situation expression from close to user form.
     *
     * @throws Exception the exception
     */
    public void testAssertableSituationExpressionFromCloseToUserForm() throws Exception {
        CloseToUserExpression input = new CloseToUserExpressionImpl(miConcept);
        assertNotNull("Input expression can not be null", input);
        CloseToUserExpression output = testSituationExpressionGenerator.getSituationWithAssertableContext(input);
        assertNotNull("Output expression can not be null", output);
        assertTrue("Output must have a focus concept of miConcept", output.getFocusConcepts().contains(situationWEConcept));
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
     * Sets the test situation expression generator.
     *
     * @param testSituationExpressionGenerator the new test situation expression generator
     */
    public void setTestSituationExpressionGenerator(SituationExpressionGenerator testSituationExpressionGenerator) {
        this.testSituationExpressionGenerator = testSituationExpressionGenerator;
    }
}
