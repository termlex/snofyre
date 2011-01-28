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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import uk.nhs.cfh.dsp.snomed.expression.comparator.ExpressionComparator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.profile;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 20, 2009 at 10:15:30 PM.
 */

@RunWith(JUnit4TestRunner.class)
public class ExpressionComparatorImplTest2{

    /** The bundle context. */
    @Inject
    private BundleContext bundleContext;
    
    /** The expression comparator. */
    private ExpressionComparator expressionComparator;

    /**
     * Setup.
     *
     * @throws Exception the exception
     */
    @Before
    public void setup() throws Exception {
        expressionComparator = retrieveExpressionComparatorService();
    }


    /**
     * Gets the test bundles names.
     *
     * @return the test bundles names
     */
    @Configuration
    protected String[] getTestBundlesNames() {
        return new String[]{
                "uk.nhs.cfh.dsp.snomed, uk.nhs.cfh.dsp.snomed.expression.comparator, 0.6-SNAPSHOT",
                "uk.nhs.cfh.dsp.snomed, uk.nhs.cfh.dsp.snomed.dao, 0.6-SNAPSHOT",
                "uk.nhs.cfh.dsp.srth-osgi, uk.nhs.cfh.dsp.srth.dcp, 1.0-SNAPSHOT"
        };
    }

    /**
     * Configuration.
     *
     * @return the option[]
     */
    @Configuration
    public static Option[] configuration()
    {
        return options(equinox(), profile("spring.dm"), provision(
                mavenBundle().groupId("uk.nhs.cfh.dsp.snomed").artifactId("uk.nhs.cfh.dsp.snomed.expression.comparator"),
                mavenBundle().groupId("uk.nhs.cfh.dsp.snomed").artifactId("uk.nhs.cfh.dsp.snomed.dao"),
                mavenBundle().groupId("uk.nhs.cfh.dsp.srth-osgi").artifactId("uk.nhs.cfh.dsp.srth.dcp")
        ));
    }

    /**
     * Retrieve expression comparator service.
     *
     * @return the expression comparator
     * @throws InterruptedException the interrupted exception
     */
    private ExpressionComparator retrieveExpressionComparatorService() throws InterruptedException {

        ServiceTracker tracker = new ServiceTracker(bundleContext,
                ExpressionComparator.class.getName(), null);
        tracker.open();
        ExpressionComparator expressionComparator = (ExpressionComparator) tracker.waitForService(5000);
        tracker.close();
        assertNotNull(expressionComparator);
        return expressionComparator;
    }

    /**
     * Bundle context should not be null.
     *
     * @throws Exception the exception
     */
    @Test
    public void bundleContextShouldNotBeNull() throws Exception {
        assertNotNull(bundleContext);
    }

    /**
     * Test service reference exists.
     */
    @Test
    public void testServiceReferenceExists() {
        ServiceReference serviceReference =
                bundleContext.getServiceReference(ExpressionComparator.class.getName());
        assertNotNull(serviceReference);
    }

    /**
     * Test get subsumption relation.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetSubsumptionRelation() throws Exception {
        assertTrue(ExpressionComparator.Subsumption_Relation.SUBSUMES ==
                expressionComparator.getSubsumptionRelation("106063007", "22298006"));
    }

    /**
     * Test is subsumed by.
     *
     * @throws Exception the exception
     */
    @Test
    public void testIsSubsumedBy() throws Exception {

        assertTrue(expressionComparator.isSubsumedBy("22298006", "106063007"));
    }
}