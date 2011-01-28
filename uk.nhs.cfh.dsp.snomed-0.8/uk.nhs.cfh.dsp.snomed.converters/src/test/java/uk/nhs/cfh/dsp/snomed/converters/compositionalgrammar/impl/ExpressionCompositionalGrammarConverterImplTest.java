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
package uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.ExpressionCompositionalGrammarConverter;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 19, 2010 at 3:35:44 PM.
 */
public class ExpressionCompositionalGrammarConverterImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;
    
    /** The concept. */
    private SnomedConcept concept;
    
    /** The test expression compositional grammar converter. */
    private ExpressionCompositionalGrammarConverter testExpressionCompositionalGrammarConverter;
    
    /** The file location. */
    private static String fileLocation = "target/test-classes/cg-out-file.txt";

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

        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        assertNotNull("Expression converter must not be null", testExpressionCompositionalGrammarConverter);
        concept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "22298006");
        assertNotNull("Concept can not be null", concept);
    }


    /**
     * Test get compositional form.
     *
     * @throws Exception the exception
     */
    public void testGetCompositionalForm() throws Exception {
        CloseToUserExpression closeToUserExpression = new CloseToUserExpressionImpl(concept);
        assertNotNull("Close to user form can not be null", closeToUserExpression);
        String compositionalForm = testExpressionCompositionalGrammarConverter.getCompositionalForm(closeToUserExpression);
        assertNotNull("Compositional form can not be null", compositionalForm);
        // save to file
        File outFile =  new File(fileLocation);
        FileWriter fw = new FileWriter(outFile);
        fw.flush();
        fw.write(compositionalForm);
        fw.close();
        // check file exists
        assertTrue("Output file must exist and must be readable", (outFile.exists() && outFile.canRead()));
    }
    
    /**
     * Test get close to user form.
     *
     * @throws Exception the exception
     */
    public void testGetCloseToUserForm() throws Exception {
        String input = "";
        Scanner scanner = new Scanner(new File(fileLocation));
        while(scanner.hasNextLine())
        {
          input = input+scanner.nextLine();
        }
        assertNotNull("Input String must not be null", input);
        CloseToUserExpression outputExpression = testExpressionCompositionalGrammarConverter.getCloseToUserForm(input);
        assertNotNull("Output expression must not be null", outputExpression);
        // focus concpet must be the same as concept
        SnomedConcept focusConcept = outputExpression.getSingletonFocusConcept();
        assertTrue("Focus concepts must be 22298006", concept.equals(focusConcept));
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
     * Sets the test expression compositional grammar converter.
     *
     * @param testExpressionCompositionalGrammarConverter the new test expression compositional grammar converter
     */
    public void setTestExpressionCompositionalGrammarConverter(ExpressionCompositionalGrammarConverter testExpressionCompositionalGrammarConverter) {
        this.testExpressionCompositionalGrammarConverter = testExpressionCompositionalGrammarConverter;
    }
}
