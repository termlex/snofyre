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
package uk.nhs.cfh.dsp.snomed.normaliser.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2010 at 8:21:39 PM.
 */
public class NormalFormGeneratorImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test expression comparator. */
    private ExpressionComparator testExpressionComparator;
    
    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;
    
    /** The test normal form hierarchy provider. */
    private NormalFormHierarchyProvider testNormalFormHierarchyProvider;
    
    /** The test normal form generator. */
    private NormalFormGenerator testNormalFormGenerator;
    
    /** The Constant conceptId. */
    private static final String conceptId = "22298006";
    
    /** The concept. */
    private SnomedConcept concept;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(NormalFormGeneratorImplTest.class);

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[] {"META-INF/spring/bundle-context-test.xml" };
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("Expression comparator must not be null", testExpressionComparator);
        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        assertNotNull("Hierarchy Provider must not be null", testNormalFormHierarchyProvider);
        assertNotNull("Normal form generator must not be null", testNormalFormGenerator);
        concept = (SnomedConcept) testTerminologyDAO.getTerminologyConcept(conceptId);
        assertNotNull("Concept must not be null", concept);
    }

    /**
     * Test get short normal form expression.
     *
     * @throws Exception the exception
     */
    public void testGetShortNormalFormExpression() throws Exception {
        NormalFormExpression normalFormExpression = testNormalFormGenerator.getShortNormalFormExpression(concept);
        assertNotNull("Normal form must not be null", normalFormExpression);
    }

    /**
     * Test get long normal form expression for rendering.
     *
     * @throws Exception the exception
     */
    public void testGetLongNormalFormExpressionForRendering() throws Exception {
        NormalFormExpression normalFormExpression = testNormalFormGenerator.getLongNormalFormExpressionForRendering(concept);
        assertNotNull("Normal form must not be null", normalFormExpression);
    }

    /**
     * Test get long normal form expression.
     *
     * @throws Exception the exception
     */
    public void testGetLongNormalFormExpression() throws Exception {
        NormalFormExpression normalFormExpression = testNormalFormGenerator.getLongNormalFormExpression(concept);
        assertNotNull("Normal form must not be null", normalFormExpression);

        CloseToUserExpression closeToUserExpression = new CloseToUserExpressionImpl(concept);
        assertNotNull("Close to user expression must not be null", closeToUserExpression);
        NormalFormExpression normalFormExpression2 = testNormalFormGenerator.getLongNormalFormExpression(concept);
        assertNotNull("Normal form must not be null", normalFormExpression2);

        assertTrue("Normal forms returned must be the same",
                normalFormExpression.getCanonicalStringForm().equals(normalFormExpression2.getCanonicalStringForm()));
    }

    /**
     * Test expression comparision.
     *
     * @throws Exception the exception
     */
    public void testExpressionComparision() throws Exception {
        NormalFormExpression normalFormExpression = testNormalFormGenerator.getLongNormalFormExpression(concept);
        assertNotNull("Normal form must not be null", normalFormExpression);

        CloseToUserExpression closeToUserExpression = new CloseToUserExpressionImpl(concept);
        assertNotNull("Close to user expression must not be null", closeToUserExpression);
        NormalFormExpression normalFormExpression2 = testNormalFormGenerator.getLongNormalFormExpression(closeToUserExpression);
        assertNotNull("Normal form must not be null", normalFormExpression2);
        assertTrue("NFE2 must be Equivalent to NFE1", ExpressionComparator.Subsumption_Relation.SAME ==
                testExpressionComparator.getSubsumptionRelation(normalFormExpression2, normalFormExpression));

        // compare with asthma concept - 195967001
        SnomedConcept asthmaConcept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "195967001");
        NormalFormExpression nfe3 = testNormalFormGenerator.getLongNormalFormExpression(asthmaConcept);
        assertNotNull("Normal form for asthma must not be null", nfe3);
        assertEquals("NFE3 must not be related to NFE1", ExpressionComparator.Subsumption_Relation.NO_RELATION, 
                testExpressionComparator.getSubsumptionRelation(nfe3, normalFormExpression));
    }

    /**
     * Test expression comparision with refinements.
     *
     * @throws Exception the exception
     */
    public void testExpressionComparisionWithRefinements() throws Exception {

        NormalFormExpression normalFormExpression = testNormalFormGenerator.getLongNormalFormExpression(concept);
        assertNotNull("Normal form must not be null", normalFormExpression);

        // add a refinement and test again
        CloseToUserExpression closeToUserExpression2 = new CloseToUserExpressionImpl(concept);
        SnomedRelationshipPropertyExpression pe = new SnomedRelationshipPropertyExpression();
        pe.setRelationshipFromConcept(TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "263502005"));
        pe.getRelationship().setTargetConcept(TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "19939008"));
        assertNotNull("Property expression 1 can not be null", pe);
        SnomedRelationshipPropertyExpression pe2 = new SnomedRelationshipPropertyExpression();
        pe2.setRelationshipFromConcept(TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "246112005"));
        pe2.getRelationship().setTargetConcept(TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "371924009"));
        assertNotNull("Property expression 2 can not be null", pe2);
        // add property expressions to close to user form
        closeToUserExpression2.addRefinement(pe);
        closeToUserExpression2.addRefinement(pe2);
        assertNotNull("Close to user expression must not be null", closeToUserExpression2);
        NormalFormExpression normalFormExpression3 = testNormalFormGenerator.getLongNormalFormExpression(closeToUserExpression2);
        assertNotNull("Normal form must not be null", normalFormExpression3);
        logger.debug("normalFormExpression3.getCompositionalGrammarForm() = " + normalFormExpression3.getCompositionalGrammarForm());

        // get short normal form
        NormalFormExpression shortNFE = testNormalFormGenerator.getShortNormalFormExpression(normalFormExpression);
        assertNotNull("Short normal form can not be null", shortNFE);
        logger.debug("shortNFE.getCompositionalGrammarForm() = " + shortNFE.getCompositionalGrammarForm());
        assertTrue("NFE3 must be subsumed by NFE1", ExpressionComparator.Subsumption_Relation.SUBSUMED_BY ==
                testExpressionComparator.getSubsumptionRelation(normalFormExpression3, shortNFE));

                // compare with asthma concept - 195967001
        SnomedConcept asthmaConcept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "195967001");
        NormalFormExpression nfe4 = testNormalFormGenerator.getLongNormalFormExpression(asthmaConcept);
        assertNotNull("Normal form for asthma must not be null", nfe4);
        NormalFormExpression shortNFE4 = testNormalFormGenerator.getShortNormalFormExpression(nfe4);
        assertNotNull("Short Normal form for asthma must not be null", shortNFE4);
        logger.debug("shortNFE4.getCompositionalGrammarForm() = " + shortNFE4.getCompositionalGrammarForm());
        logger.debug("shortNFE4.getFocusConcepts() = " + shortNFE4.getFocusConcepts().size());
        assertEquals("Short form NFE4 must not be related to NFE3", ExpressionComparator.Subsumption_Relation.NO_RELATION,
                testExpressionComparator.getSubsumptionRelation(shortNFE4, normalFormExpression3));
    }

    /**
     * Sets the test expression comparator.
     *
     * @param testExpressionComparator the new test expression comparator
     */
    public void setTestExpressionComparator(ExpressionComparator testExpressionComparator) {
        this.testExpressionComparator = testExpressionComparator;
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
     * Sets the test normal form hierarchy provider.
     *
     * @param testNormalFormHierarchyProvider the new test normal form hierarchy provider
     */
    public void setTestNormalFormHierarchyProvider(NormalFormHierarchyProvider testNormalFormHierarchyProvider) {
        this.testNormalFormHierarchyProvider = testNormalFormHierarchyProvider;
    }

    /**
     * Sets the test normal form generator.
     *
     * @param testNormalFormGenerator the new test normal form generator
     */
    public void setTestNormalFormGenerator(NormalFormGenerator testNormalFormGenerator) {
        this.testNormalFormGenerator = testNormalFormGenerator;
    }
}
