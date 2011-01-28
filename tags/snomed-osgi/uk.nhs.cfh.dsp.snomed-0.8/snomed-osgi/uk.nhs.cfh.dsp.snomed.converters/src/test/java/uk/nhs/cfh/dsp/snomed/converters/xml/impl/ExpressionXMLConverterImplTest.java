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
package uk.nhs.cfh.dsp.snomed.converters.xml.impl;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.io.File;
import java.io.FileWriter;

// TODO: Auto-generated Javadoc
/**
 * A test case for {@link uk.nhs.cfh.dsp.snomed.converters.xml.ExpressionXMLConverter}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 3, 2010 at 2:10:57 PM
 */
public class ExpressionXMLConverterImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;
    
    /** The concept. */
    private SnomedConcept concept;
    
    /** The test snomed xml converter. */
    private ExpressionXMLConverter testSnomedXMLConverter;
    
    /** The out file location. */
    private static String outFileLocation = "target/test-classes/";
    
    /** The test normal form generator. */
    private NormalFormGenerator testNormalFormGenerator;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    protected void onSetUp() throws Exception {
        assertNotNull("Terminology DAO not be null", testTerminologyDAO);
        concept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "22298006");
        assertNotNull("Concept can not be null", concept);
        assertNotNull("Expression converter can not be null", testSnomedXMLConverter);
        assertNotNull("Normal form generator can not be null",testNormalFormGenerator);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Test get element for close to user form.
     *
     * @throws Exception the exception
     */
    public void testGetElementForCloseToUserForm() throws Exception {
        CloseToUserExpression closeToUserExpression = new CloseToUserExpressionImpl(concept);
        assertNotNull(closeToUserExpression);
        Element element = testSnomedXMLConverter.getElementForCloseToUserForm(closeToUserExpression);
        assertNotNull(element);

        // save element
        FileWriter writer = new FileWriter(outFileLocation+"close-to-user-expression.xml");
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(element, writer);

        // check file exists
        File file = new File(outFileLocation+"close-to-user-expression.xml");
        assertTrue("Outfile must exist", file.exists());
    }

    /**
     * Test get close to user expression.
     *
     * @throws Exception the exception
     */
    public void testGetCloseToUserExpression() throws Exception {

        // get file from previous step
        File file = new File(outFileLocation+"close-to-user-expression.xml");
        assertTrue("Input file must exist", file.exists());
        SAXBuilder builder = new SAXBuilder(false);
        Document document = builder.build(file);
        assertNotNull("XML document must have been parsed", document);
        // get root element of document which should contain the expression
        Element element = document.getRootElement();
        assertNotNull("Document must have a root element", element);

        CloseToUserExpression unmarshalledExpression = testSnomedXMLConverter.getCloseToUserExpression(element);
        assertNotNull("Close to user expression must have been unmarshalled", unmarshalledExpression);
        assertTrue("Focus concepts should be the same", concept.equals(unmarshalledExpression.getSingletonFocusConcept()));
    }

    /**
     * Test get element for normal form.
     *
     * @throws Exception the exception
     */
    public void testGetElementForNormalForm() throws Exception {
        NormalFormExpression nfe = testNormalFormGenerator.getLongNormalFormExpression(concept);
        assertNotNull(nfe);
        Element element = testSnomedXMLConverter.getElementForNormalForm(nfe);
        assertNotNull(element);

        // save element
        FileWriter writer = new FileWriter(outFileLocation+"nfe.xml");
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(element, writer);

        // check file exists
        File file = new File(outFileLocation+"nfe.xml");
        assertTrue("Outfile must exist", file.exists());
    }

    /**
     * Test normal form expression.
     *
     * @throws Exception the exception
     */
    public void testNormalFormExpression() throws Exception {

        // get file from previous step
        File file = new File(outFileLocation+"nfe.xml");
        assertTrue("Input file must exist", file.exists());
        SAXBuilder builder = new SAXBuilder(false);
        Document document = builder.build(file);
        assertNotNull("XML document must have been parsed", document);
        // get root element of document which should contain the expression
        Element element = document.getRootElement();
        assertNotNull("Document must have a root element", element);

        NormalFormExpression unmarshalledExpression = testSnomedXMLConverter.getNormalFormExpressionFromXML(element);
        assertNotNull("Normal form expression must have been unmarshalled", unmarshalledExpression);
        assertTrue("Focus concept should be 64572001", "64572001".equals(unmarshalledExpression.getSingletonFocusConcept().getConceptID()));
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
     * Sets the test snomed xml converter.
     *
     * @param testSnomedXMLConverter the new test snomed xml converter
     */
    public void setTestSnomedXMLConverter(ExpressionXMLConverter testSnomedXMLConverter) {
        this.testSnomedXMLConverter = testSnomedXMLConverter;
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
