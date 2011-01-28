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
import uk.nhs.cfh.dsp.snomed.dao.impl.SnomedConceptDatabaseDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

// TODO: Auto-generated Javadoc
/**
 * A test case for {@link uk.nhs.cfh.dsp.snomed.converters.xml.impl.XSBasedSnomedXMLConverter}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 3, 2010 at 10:59:03 AM
 */
public class XSBasedSnomedXMLConverterTest extends AbstractDependencyInjectionSpringContextTests {


    /** The test terminology dao. */
    private SnomedConceptDatabaseDAO testTerminologyDAO;
    
    /** The concept. */
    private SnomedConcept concept;
    
    /** The test xsb snomed xml converter. */
    private XSBasedSnomedXMLConverter testXsbSnomedXMLConverter;
    
    /** The out file path. */
    private static String outFilePath = "target/test-classes/concept-export.xml";

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    protected void onSetUp() throws Exception {
        assertNotNull("Terminology DAO not be null", testTerminologyDAO);
        concept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "22298006");        
        assertNotNull("Concept can not be null", concept);
        assertNotNull("Expression converter can not be null",testXsbSnomedXMLConverter);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Test get element for concept.
     *
     * @throws Exception the exception
     */
    public void testGetElementForConcept() throws Exception {
        Element element = testXsbSnomedXMLConverter.getElementForConcept(concept);
        assertNotNull(element);

        Writer writer = new FileWriter(outFilePath);
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(element, writer);

        File outFile = new File(outFilePath);
        assertTrue(outFile.exists());
    }

    /**
     * Test get concept from xml.
     *
     * @throws Exception the exception
     */
    public void testGetConceptFromXML() throws Exception {
        SAXBuilder builder = new SAXBuilder(false);
        assertNotNull(builder);
        Document doc = builder.build(new File(outFilePath));
        assertNotNull(doc);
        // get root element which should be the concept
        Element conceptElement = doc.getRootElement();
        assertNotNull(conceptElement);
        SnomedConcept unmarshalledConcept = testXsbSnomedXMLConverter.getConceptFromXML(conceptElement);
        assertNotNull(unmarshalledConcept);
        // check attributes on unmarshalledConcept
        assertTrue("Concept Id must be "+concept.getConceptID(), concept.getConceptID().equalsIgnoreCase(unmarshalledConcept.getConceptID()));
        assertTrue("Concept FSN must be "+concept.getFullySpecifiedName(), concept.getFullySpecifiedName().equalsIgnoreCase(unmarshalledConcept.getFullySpecifiedName()));
        assertTrue("Concept Preferred Term must be "+concept.getPreferredLabel(), concept.getPreferredLabel().equalsIgnoreCase(unmarshalledConcept.getPreferredLabel()));
        assertTrue("Concept synonyms must be "+concept.getSynonyms().size(), concept.getSynonyms().size() == unmarshalledConcept.getSynonyms().size());
    }

    /**
     * Sets the test terminology dao.
     *
     * @param testTerminologyDAO the new test terminology dao
     */
    public void setTestTerminologyDAO(SnomedConceptDatabaseDAO testTerminologyDAO) {
        this.testTerminologyDAO = testTerminologyDAO;
    }

    /**
     * Sets the test xsb snomed xml converter.
     *
     * @param testXsbSnomedXMLConverter the new test xsb snomed xml converter
     */
    public void setTestXsbSnomedXMLConverter(XSBasedSnomedXMLConverter testXsbSnomedXMLConverter) {
        this.testXsbSnomedXMLConverter = testXsbSnomedXMLConverter;
    }
}
