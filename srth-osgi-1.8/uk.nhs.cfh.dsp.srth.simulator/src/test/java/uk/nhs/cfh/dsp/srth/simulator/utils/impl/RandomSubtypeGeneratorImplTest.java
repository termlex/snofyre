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
package uk.nhs.cfh.dsp.srth.simulator.utils.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.RandomSubtypeHierarchyProvider;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.simulator.utils.RandomSubtypeGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

/**
 * A test class for {@link uk.nhs.cfh.dsp.srth.simulator.utils.impl.RandomSubtypeGeneratorImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 8, 2010 at 9:36:57 PM
 */
public class RandomSubtypeGeneratorImplTest extends AbstractDependencyInjectionSpringContextTests {

    private TerminologyConceptDAO testTerminologyDAO;
    private RandomSubtypeHierarchyProvider testRandomSubtypeHierarchyProvider;
    private SnomedConcept concept;
    private RandomSubtypeGenerator testRandomSubtypeGenerator;
    private ExpressionComparator testExpressionComparator;
    private NormalFormGenerator testNormalFormGenerator;
    private ExpressionXMLConverter testExpressionXMLConverter;
    private static Log logger = LogFactory.getLog(RandomSubtypeGeneratorImplTest.class);

    @Override
    public void onSetUp() throws Exception {

        concept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "22298006");
        testRandomSubtypeHierarchyProvider = (RandomSubtypeHierarchyProvider) getApplicationContext().getBean("testRandomSubtypeHierarchyProvider");
        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        assertNotNull("Hierarchy Provider must not be null", testRandomSubtypeHierarchyProvider);
        assertNotNull("Concept must not be null", concept);
        assertNotNull("Expression comparator must not be null ", testExpressionComparator);
        assertNotNull("Expression converter must not be null ", testExpressionXMLConverter);
    }

        @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    public void testGetSubTypeConcept() throws Exception {
        SnomedConcept subtypeConcept = testRandomSubtypeGenerator.getSubTypeConcept(concept);
        // verify subtypeConcept is a type of concept using hierarchyprovider
        Collection<String> descendants = testRandomSubtypeHierarchyProvider.getDescendants(concept.getConceptID());
        assertTrue("Sub type must be contained in descendants returned by hierarchy provider",
                descendants.contains(subtypeConcept.getConceptID()));
    }

    public void testGetSubTypeExpression() throws Exception {

        CloseToUserExpression expression = new CloseToUserExpressionImpl(concept);
        assertNotNull("Expression from concept must not be null", expression);
        logger.debug("expression.getCompositionalGrammarForm() = " + expression.getCompositionalGrammarForm());
        // save expression to file
        saveElement(testExpressionXMLConverter.getElementForCloseToUserForm(expression), "target/expression-before.xml");
        CloseToUserExpression subtypeExpression = testRandomSubtypeGenerator.getSubTypeExpression(expression);
        assertNotNull("Subtype expression must not be null", subtypeExpression);        
        // save subtype expression to file
        saveElement(testExpressionXMLConverter.getElementForCloseToUserForm(subtypeExpression), "target/subtype-expression.xml");
        // save original expression to file after getting subtype -- done to check modification via references
        saveElement(testExpressionXMLConverter.getElementForCloseToUserForm(expression), "target/expression-after.xml");

        logger.debug("subtypeExpression.getCompositionalGrammarForm() = " + subtypeExpression.getCompositionalGrammarForm());
        NormalFormExpression lhsNormalFormExpression = testNormalFormGenerator.getShortNormalFormExpression(subtypeExpression);
        logger.debug("lhsNormalFormExpression.getCompositionalGrammarForm() = " + lhsNormalFormExpression.getCompositionalGrammarForm());
        assertNotNull("LHS Normal form must not be null", lhsNormalFormExpression);
        logger.debug("testNormalFormGenerator.getLongNormalFormExpression(\"22298006\") = " + testNormalFormGenerator.getLongNormalFormExpression("22298006").getCompositionalGrammarForm());
        NormalFormExpression rhsNormalFormExpression = testNormalFormGenerator.getLongNormalFormExpression(expression);
        logger.debug("rhsNormalFormExpression.getCompositionalGrammarForm() = " + rhsNormalFormExpression.getCompositionalGrammarForm());
        assertNotNull("RHS Normal form must not be null", rhsNormalFormExpression);
        assertTrue("Generated expression must be a subtype of input expression",
                testExpressionComparator.isSubsumedBy(lhsNormalFormExpression, rhsNormalFormExpression));
    }

    private void saveElement(Element element, String fileName) throws IOException {
        Writer fw = new FileWriter(fileName);
        fw.flush();
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(element, fw);
        fw.close();
        logger.info("Finished saving element to file : "+fileName);
    }

    public void setTestTerminologyDAO(TerminologyConceptDAO testTerminologyDAO) {
        this.testTerminologyDAO = testTerminologyDAO;
    }

    public void setTestRandomSubtypeGenerator(RandomSubtypeGenerator testRandomSubtypeGenerator) {
        this.testRandomSubtypeGenerator = testRandomSubtypeGenerator;
    }

    public void setTestExpressionComparator(ExpressionComparator testExpressionComparator) {
        this.testExpressionComparator = testExpressionComparator;
    }

    public void setTestNormalFormGenerator(NormalFormGenerator testNormalFormGenerator) {
        this.testNormalFormGenerator = testNormalFormGenerator;
    }

    public void setTestExpressionXMLConverter(ExpressionXMLConverter testExpressionXMLConverter) {
        this.testExpressionXMLConverter = testExpressionXMLConverter;
    }
}
