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
package uk.nhs.cfh.dsp.snomed.expression.comparator.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2010 at 7:47:07 PM.
 */
public class UnidirectionalExpressionComparatorImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test expression comparator. */
    private ExpressionComparator testExpressionComparator;

    /**
     * Sets the expression comparator.
     *
     * @param testExpressionComparator the new expression comparator
     */
    public void setExpressionComparator(ExpressionComparator testExpressionComparator) {
        this.testExpressionComparator = testExpressionComparator;
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[] {"META-INF/spring/bundle-test-context.xml" };
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        assertNotNull("Expression comparator must not be null", testExpressionComparator);
    }

    /**
     * Test get subsumption relation.
     *
     * @throws Exception the exception
     */
    public void testGetSubsumptionRelation() throws Exception {
        assertTrue(ExpressionComparator.Subsumption_Relation.SUBSUMES ==
                testExpressionComparator.getSubsumptionRelation("106063007", "22298006"));
    }

    /**
     * Test is subsumed by.
     *
     * @throws Exception the exception
     */
    public void testIsSubsumedBy() throws Exception {

        assertTrue(testExpressionComparator.isSubsumedBy("22298006", "106063007"));
    }
}
