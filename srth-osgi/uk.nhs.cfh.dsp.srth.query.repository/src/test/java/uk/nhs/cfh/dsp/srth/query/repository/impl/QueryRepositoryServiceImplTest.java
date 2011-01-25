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
package uk.nhs.cfh.dsp.srth.query.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryIntersectionExpressionImpl;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl;
import uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService;

import java.util.List;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 21, 2010 at 12:28:31 AM.
 */
public class QueryRepositoryServiceImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test query repository service. */
    private QueryRepositoryService testQueryRepositoryService;
    
    /** The query statement. */
    private QueryStatement queryStatement;
    
    /** The Constant uuid. */
    private static final String uuid = UUID.randomUUID().toString();
    private static Log logger = LogFactory.getLog(QueryRepositoryServiceImplTest.class);

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("Query Repository Service can not be null", testQueryRepositoryService);
        queryStatement = new QueryStatementImpl(uuid);
        logger.debug("uuid = " + uuid);
        queryStatement.getMetaData().setDescription("Test query with asthma");
        queryStatement.getMetaData().setTitle("Asthma Query");
        queryStatement.addChildExpression(new QueryIntersectionExpressionImpl());
        assertNotNull("Query Statement can not be null", queryStatement);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context-test.xml"};
    }

    /**
     * Test save query.
     *
     * @throws Exception the exception
     */
    public void testSaveQuery() throws Exception {
        testQueryRepositoryService.saveQuery(queryStatement);
        assertTrue("Repository must contain one entry", testQueryRepositoryService.findAllQueries().size() >0);
    }

//    public void testDeleteQuery() throws Exception {
//    }

    /**
 * Test find query expression.
 *
 * @throws Exception the exception
 */
public void testFindQueryExpression() throws Exception {
        QueryExpression statement = testQueryRepositoryService.findQueryExpression(uuid);
        assertNotNull("Query Expression returned must not be null", statement);
        assertTrue("Meta data must have asthma in description", statement.getMetaData().getDescription().indexOf("asthma") > -1);
        assertTrue("Returned expressions uuid must match set uuid", uuid.equals(statement.getUUID()));
    }
    
    /**
     * Test find using search term.
     *
     * @throws Exception the exception
     */
    public void testFindUsingSearchTerm() throws Exception {
        testQueryRepositoryService.saveQuery(queryStatement);
        testQueryRepositoryService.indexAllQueries();
        List<QueryExpression> results = testQueryRepositoryService.findUsingSearchTerm("asthma");
        assertTrue("At least one result with asthma must be returned", results.size() > 0);
    }

    /**
     * Test delete all queries.
     *
     * @throws Exception the exception
     */
    public void testDeleteAllQueries() throws Exception {
        testQueryRepositoryService.deleteAllQueries();
        assertTrue("Repository must contain one entry", testQueryRepositoryService.findAllQueries().size()  == 0);
    }

    /**
     * Sets the test query repository service.
     *
     * @param testQueryRepositoryService the new test query repository service
     */
    public void setTestQueryRepositoryService(QueryRepositoryService testQueryRepositoryService) {
        this.testQueryRepositoryService = testQueryRepositoryService;
    }
}
