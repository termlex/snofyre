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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A test class for {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.ConstraintFactoryImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 6:16:25 PM
 */
public class ConstraintFactoryImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The constraint factory. */
    private ConstraintFactory constraintFactory;

    /**
     * Sets the constraint factory.
     *
     * @param constraintFactory the new constraint factory
     */
    public void setConstraintFactory(ConstraintFactory constraintFactory) {
        this.constraintFactory = constraintFactory;
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"META-INF/spring/bundle-context.xml"};
    }

    /**
     * Test get data constant constraint.
     *
     * @throws Exception the exception
     */
    public void testGetDataConstantConstraint() throws Exception {
        DataConstantConstraint constraint = constraintFactory.getDataConstantConstraint(10, "value");
        assertNotNull("Constraint should have been created", constraint);
        assertEquals("Constraint value must be 10", 10, constraint.asInt());
        assertTrue("Name must be 'value'", "value".equalsIgnoreCase(constraint.getName()));
    }

    /**
     * Test get data range facet constraint.
     *
     * @throws Exception the exception
     */
    public void testGetDataRangeFacetConstraint() throws Exception {
        RangeFacetVocabulary facet = RangeFacetVocabulary.MAX_EXCLUSIVE;
        DataRangeFacetConstraint constraint = constraintFactory.getDataRangeFacetConstraint(5, facet, "value");
        assertNotNull("Constraint should have been created", constraint);
        assertEquals("Constraint value must be 10", 5, constraint.asInt());
        assertTrue("Name must be 'value'", "value".equalsIgnoreCase(constraint.getName()));
        assertTrue("Facet must be max_exclusive", RangeFacetVocabulary.MAX_EXCLUSIVE == constraint.getFacet());
    }

    /**
     * Test get data range constraint.
     *
     * @throws Exception the exception
     */
    public void testGetDataRangeConstraint() throws Exception {

        RangeFacetVocabulary lowerFacet = RangeFacetVocabulary.MIN_INCLUSIVE;
        DataRangeConstraint constraint = constraintFactory.getDataRangeConstraint(5, lowerFacet, 10,
                RangeFacetVocabulary.MAX_INCLUSIVE, "value");
        assertNotNull("Constraint should have been created", constraint);
        assertEquals("Constraint lower bound must be 5", 5.0, constraint.getLowerBound());
        assertTrue("Constraint's upper bound must be 10", (11.0 > constraint.getUpperBound()));
        assertTrue("Name must be 'value'", "value".equalsIgnoreCase(constraint.getName()));
        assertTrue("Facet must be max_exclusive", RangeFacetVocabulary.MIN_INCLUSIVE == constraint.getLowerBoundFacet().getFacet());
    }

    /**
     * Test get temporal constant constraint.
     *
     * @throws Exception the exception
     */
    public void testGetTemporalConstantConstraint() throws Exception {

        Calendar time = Calendar.getInstance();
        TemporalConstantConstraint constraint = constraintFactory.getTemporalConstantConstraint(time, "entry_time");
        assertNotNull("Constraint should have been created", constraint);
        assertEquals("Constraint value must be same as time stamp", time, constraint.getTime());
        assertTrue("Name must be 'entry_time'", "entry_time".equalsIgnoreCase(constraint.getName()));
    }

    /**
     * Test get temporal range facet constraint.
     *
     * @throws Exception the exception
     */
    public void testGetTemporalRangeFacetConstraint() throws Exception {
        Calendar time = Calendar.getInstance();
        TemporalRangeFacetConstraint constraint = constraintFactory.getTemporalRangeFacetConstraint(time, null, RangeFacetVocabulary.MAX_INCLUSIVE, "entry_time");
        assertNotNull("Constraint should have been created", constraint);
        assertEquals("Constraint value must be same as time stamp", time, constraint.getTime());
        assertTrue("Name must be 'entry_time'", "entry_time".equalsIgnoreCase(constraint.getName()));
        assertTrue("Facet must be max_inclusive", RangeFacetVocabulary.MAX_INCLUSIVE == constraint.getFacet());

    }

    /**
     * Test get temporal range constraint.
     *
     * @throws Exception the exception
     */
    public void testGetTemporalRangeConstraint() throws Exception {

        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        TemporalRangeConstraint constraint = constraintFactory.getTemporalRangeConstraint(time1, RangeFacetVocabulary.MIN_INCLUSIVE, null,
                time2, RangeFacetVocabulary.MAX_INCLUSIVE, null, "entry_time");
        assertNotNull("Constraint should have been created", constraint);
        assertEquals("Constraint value must be same as time stamp", time1, constraint.getLowerBoundTime());
        assertEquals("Constraint value must be same as time stamp", time2, constraint.getUpperBoundTime());
        assertTrue("Name must be 'entry_time'", "entry_time".equalsIgnoreCase(constraint.getName()));
        assertTrue("Facet must be max_inclusive", RangeFacetVocabulary.MAX_INCLUSIVE == constraint.getUpperBoundFacet().getFacet());
    }
}
