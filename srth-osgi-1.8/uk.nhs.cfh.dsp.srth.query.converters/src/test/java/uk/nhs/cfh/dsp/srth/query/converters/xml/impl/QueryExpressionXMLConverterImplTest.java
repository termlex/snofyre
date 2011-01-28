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
package uk.nhs.cfh.dsp.srth.query.converters.xml.impl;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.ConstraintFactoryImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryComponentExpressionImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryExpressionFactoryImpl;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A test case for {@link uk.nhs.cfh.dsp.srth.query.converters.xml.impl.QueryExpressionXMLConverterImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 3, 2010 at 3:23:25 PM
 */
public class QueryExpressionXMLConverterImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test query expression xml converter. */
    private QueryExpressionXMLConverter testQueryExpressionXMLConverter;
    
    /** The intersection expression. */
    private QueryIntersectionExpression intersectionExpression;
    
    /** The query statement. */
    private QueryStatement queryStatement;
    
    /** The query expression factory. */
    private QueryExpressionFactory queryExpressionFactory;
    
    /** The expressions. */
    private Collection<QueryExpression> expressions;
    
    /** The component expression. */
    private QueryComponentExpression componentExpression;
    
    /** The terminology constraint. */
    private TerminologyConstraint terminologyConstraint;
    
    /** The outputter. */
    private XMLOutputter outputter;
    
    /** The builder. */
    private SAXBuilder builder;

    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;
    
    /** The concept. */
    private SnomedConcept concept;
    
    /** The file location. */
    private static String fileLocation = "target/test-classes/";

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
        assertNotNull("Query converter must not be null", testQueryExpressionXMLConverter);
        concept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "22298006");
        assertNotNull("Concept can not be null", concept);
        // create converter
        outputter = new XMLOutputter(Format.getPrettyFormat());
        builder = new SAXBuilder(false);

        // create query objects
        queryExpressionFactory = new QueryExpressionFactoryImpl(new ConstraintFactoryImpl());
        QueryExpression exp1 = new QueryComponentExpressionImpl();
        QueryExpression exp2 = new QueryComponentExpressionImpl();
        expressions = new ArrayList<QueryExpression>();
        expressions.add(exp1);
        expressions.add(exp2);

        componentExpression = queryExpressionFactory.getQueryComponentExpression(concept, "Exp");
        terminologyConstraint = componentExpression.getIncludedConstraint();

        assertNotNull(outputter);
        assertNotNull(builder);

        // verify query expressions
        assertNotNull(queryExpressionFactory);
        assertNotNull(expressions);
        assertNotNull(componentExpression);
        assertNotNull(terminologyConstraint);
    }

    /**
     * Test get element for query expression.
     *
     * @throws Exception the exception
     */
    public void testGetElementForQueryExpression() throws Exception {
        intersectionExpression = queryExpressionFactory.getQueryIntersectionExpression(expressions);
        Element element = testQueryExpressionXMLConverter.getElementForQueryExpression(intersectionExpression);
        assertNotNull(element);
        FileWriter writer = new FileWriter(fileLocation+"intersection-expression.xml");
        outputter.output(element, writer);

        // verify file exists
        File file = new File(fileLocation+"intersection-expression.xml");
        assertTrue(file.exists());
    }

    /**
     * Test get element for query statement.
     *
     * @throws Exception the exception
     */
    public void testGetElementForQueryStatement() throws Exception {
        queryStatement = queryExpressionFactory.getQueryStatement();
        queryStatement.addChildExpression(componentExpression);
        Element element = testQueryExpressionXMLConverter.getElementForQueryStatement(queryStatement);
        assertNotNull(element);
        FileWriter writer = new FileWriter(fileLocation+"query-statement.xml");
        outputter.output(element, writer);

        // verify file exists
        File file = new File(fileLocation+"query-statement.xml");
        assertTrue(file.exists());
    }

    /**
     * Test get element for constraint.
     *
     * @throws Exception the exception
     */
    public void testGetElementForConstraint() throws Exception {
        Element element = testQueryExpressionXMLConverter.getElementForConstraint(terminologyConstraint);
        assertNotNull(element);
        FileWriter writer = new FileWriter(fileLocation+"constraint.xml");
        outputter.output(element, writer);

        // verify file exists
        File file = new File(fileLocation+"constraint.xml");
        assertTrue(file.exists());
    }

    /**
     * Test get expression from element.
     *
     * @throws Exception the exception
     */
    public void testGetExpressionFromElement() throws Exception {
        File file = new File(fileLocation+"intersection-expression.xml");
        Document doc = builder.build(file);
        assertNotNull(doc);
        Element rootElement = doc.getRootElement();
        QueryExpression unmarshalledExpression = testQueryExpressionXMLConverter.getExpressionFromElement(rootElement);
        assertNotNull(unmarshalledExpression);

        // compare with intersectionExpression
        assertEquals("Unmarshalled expression must have 2 children", 2, unmarshalledExpression.getContainedExpressions().size());
    }

    /**
     * Test get query statement from element.
     *
     * @throws Exception the exception
     */
    public void testGetQueryStatementFromElement() throws Exception {
        File file = new File(fileLocation+"query-statement.xml");
        Document doc = builder.build(file);
        assertNotNull(doc);
        Element rootElement = doc.getRootElement();
        QueryExpression unmarshalledExpression = testQueryExpressionXMLConverter.getQueryStatementFromElement(rootElement);
        assertNotNull(unmarshalledExpression);

        assertEquals("Query statement must contain 1 child", 1, unmarshalledExpression.getContainedExpressions().size());
    }

    /**
     * Test get constraint from element.
     *
     * @throws Exception the exception
     */
    public void testGetConstraintFromElement() throws Exception {
        File file = new File(fileLocation+"constraint.xml");
        Document doc = builder.build(file);
        assertNotNull(doc);
        Element rootElement = doc.getRootElement();
        Constraint constraint = testQueryExpressionXMLConverter.getConstraintFromElement(rootElement);
        assertNotNull(constraint);
        assertTrue("Constraint must be a TerminologyConstraint", constraint instanceof TerminologyConstraint);
        TerminologyConstraint terminologyConstraint = (TerminologyConstraint) constraint;
        Expression expression = terminologyConstraint.getExpression();
        assertTrue("Expression must be a Close to user expression", expression instanceof CloseToUserExpression);
        CloseToUserExpression closeToUserExpression = (CloseToUserExpression) expression;
        List<SnomedConcept> focusConcepts = new ArrayList<SnomedConcept>(closeToUserExpression.getFocusConcepts());

        // check contained concept is the same
        assertTrue("Concepts must be the same", focusConcepts.get(0).equals(concept));
    }

    /**
     * Sets the test query expression xml converter.
     *
     * @param testQueryExpressionXMLConverter the new test query expression xml converter
     */
    public void setTestQueryExpressionXMLConverter(QueryExpressionXMLConverter testQueryExpressionXMLConverter) {
        this.testQueryExpressionXMLConverter = testQueryExpressionXMLConverter;
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
