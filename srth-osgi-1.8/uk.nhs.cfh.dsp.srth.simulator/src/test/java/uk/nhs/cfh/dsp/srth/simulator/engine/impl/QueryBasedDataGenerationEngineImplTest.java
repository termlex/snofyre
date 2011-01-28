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
package uk.nhs.cfh.dsp.srth.simulator.engine.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryExpressionFactoryImpl;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;

import java.util.Calendar;

/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2010 at 12:17:08 AM
 */
public class QueryBasedDataGenerationEngineImplTest extends AbstractDependencyInjectionSpringContextTests {

    private TerminologyConceptDAO testTerminologyDAO;
    private SnomedConcept concept;
    private DataGenerationEngine testDataGenerationEngine;
    private QueryExpressionFactory queryExpressionFactory;
    private PatientDAO testPatientDAO;
    private static final int maxNumber = 10;
    private static Log logger = LogFactory.getLog(QueryBasedDataGenerationEngineImplTest.class);

    @Override
    public void onSetUp() throws Exception {

        assertNotNull("Data generation engine must not be null", testDataGenerationEngine);
        // set max number of patients to just 2
        testDataGenerationEngine.setMaxPatientNumber(maxNumber);
        testDataGenerationEngine.setMinPatientAgeInYears(40);
        concept = TerminologyConceptUtils.getConceptForID(testTerminologyDAO, "22298006");
        assertNotNull("Concept must not be null", concept);

        queryExpressionFactory = new QueryExpressionFactoryImpl();
        assertNotNull("Query expression factory can not be null", queryExpressionFactory);
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    public void testGenerateDataForQuery() throws Exception {

        QueryStatement statement = queryExpressionFactory.getQueryStatement();
        assertNotNull("Statement must not be null", statement);
        statement.addChildExpression(queryExpressionFactory.getQueryComponentExpression(concept));

        // generate data for statement
        logger.info("Started data generation");
        long startTime = Calendar.getInstance().getTimeInMillis();
        testDataGenerationEngine.generateDataForQuery(statement);
        long endTime = Calendar.getInstance().getTimeInMillis();
        logger.info("Finished data generation in "+(endTime - startTime)+" ms.");

        // test that we have two records in the patient database
        assertEquals("Number of records in database must be "+maxNumber, maxNumber, testPatientDAO.getTotalPatientCountInDatabase());
    }

    public void testGetMinPatientAgeInYears() throws Exception {
        assertEquals("Min patient age must be 40", 40, testDataGenerationEngine.getMinPatientAgeInYears());
    }

    public void testGetMaxPatientNumber() throws Exception {
        assertEquals("Max patient number must be "+maxNumber, maxNumber, testDataGenerationEngine.getMaxPatientNumber());
    }

    public void setTestTerminologyDAO(TerminologyConceptDAO testTerminologyDAO) {
        this.testTerminologyDAO = testTerminologyDAO;
    }

    public void setTestDataGenerationEngine(DataGenerationEngine testDataGenerationEngine) {
        this.testDataGenerationEngine = testDataGenerationEngine;
    }

    public void setTestPatientDAO(PatientDAO testPatientDAO) {
        this.testPatientDAO = testPatientDAO;
    }
}
