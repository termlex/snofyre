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
package uk.nhs.cfh.dsp.srth.query.model.om.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryIntersectionExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryUnionExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintFactory;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 6:58:21 PM.
 */
public class QueryExpressionFactoryImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The constraint factory. */
    private ConstraintFactory constraintFactory;
    
    /** The query expression factory. */
    private QueryExpressionFactory queryExpressionFactory;
    
    /** The expressions. */
    private List<QueryExpression> expressions;

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        QueryExpression exp1 = new QueryComponentExpressionImpl();
        QueryExpression exp2 = new QueryComponentExpressionImpl();
        expressions = new ArrayList<QueryExpression>();
        expressions.add(exp1);
        expressions.add(exp2);
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context.xml"};
    }

    /**
     * Sets the constraint factory.
     *
     * @param constraintFactory the new constraint factory
     */
    public void setConstraintFactory(ConstraintFactory constraintFactory) {
        this.constraintFactory = constraintFactory;
    }

    /**
     * Sets the query expression factory.
     *
     * @param queryExpressionFactory the new query expression factory
     */
    public void setQueryExpressionFactory(QueryExpressionFactory queryExpressionFactory) {
        this.queryExpressionFactory = queryExpressionFactory;
    }

    /**
     * Test get constraint factory.
     *
     * @throws Exception the exception
     */
    public void testGetConstraintFactory() throws Exception {
        // spring should have autowired constraintFactory for us... :)
        assertNotNull(queryExpressionFactory.getConstraintFactory());
    }

    /**
     * Test query expression.
     *
     * @throws Exception the exception
     */
    public void testQueryExpression() throws Exception {
        assertFalse("Expressions should be different", expressions.get(0).equals(expressions.get(1)));
    }

    /**
     * Test get query intersection expression.
     *
     * @throws Exception the exception
     */
    public void testGetQueryIntersectionExpression() throws Exception {
        QueryIntersectionExpression intersectionExpression1 = queryExpressionFactory.getQueryIntersectionExpression();
        assertNotNull("Intersection object must not be null", intersectionExpression1);

        QueryIntersectionExpression intersectionExpression2 = queryExpressionFactory.getQueryIntersectionExpression(expressions);
        assertNotNull("Intersection object must not be null", intersectionExpression2);
        assertEquals("Intersection object must have two children", 2, intersectionExpression2.getContainedExpressions().size());
    }

    /**
     * Test get query union expression.
     *
     * @throws Exception the exception
     */
    public void testGetQueryUnionExpression() throws Exception {

        QueryUnionExpression unionExpression1 = queryExpressionFactory.getQueryUnionExpression();
        assertNotNull("Intersection object must not be null", unionExpression1);

        QueryUnionExpression unionExpression2 = queryExpressionFactory.getQueryUnionExpression(expressions);
        assertNotNull("Intersection object must not be null", unionExpression2);
        assertEquals("Intersection object must have two children", 2, unionExpression2.getContainedExpressions().size());
    }
}
