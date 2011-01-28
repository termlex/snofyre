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
package uk.nhs.cfh.dsp.snomed.hierarchyprovider.impl;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A test class for {@link uk.nhs.cfh.dsp.snomed.hierarchyprovider.NormalFormHierarchyProvider}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2010 at 8:09:19 PM.
 */
public class NormalFormHierarchyProviderImplTest extends AbstractDependencyInjectionSpringContextTests {
    
    /** The test terminology dao. */
    private TerminologyConceptDAO testTerminologyDAO;
    
    /** The test normal form hierarchy provider. */
    private NormalFormHierarchyProvider testNormalFormHierarchyProvider;
    
    /** The Constant conceptId. */
    private static final String conceptId = "22298006";

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[] {"META-INF/spring/bundle-context-test.xml" };
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        /*
         * work around for autowiring used by AbstractDependencyInjectionSpringContextTests.
         * we need to explictly set the bean to inject
         * */
        testNormalFormHierarchyProvider = (NormalFormHierarchyProvider) getApplicationContext().getBean("testNormalFormHierarchyProvider");
        assertNotNull("Data Access Object must not be null", testTerminologyDAO);
        assertNotNull("Hierarchy Provider must not be null", testNormalFormHierarchyProvider);
    }

    /**
     * Test get primitive parents.
     *
     * @throws Exception the exception
     */
    public void testGetPrimitiveParents() throws Exception {
        Collection<String> primitiveParents = testNormalFormHierarchyProvider.getPrimitiveParents(conceptId);
        assertTrue("Must not have primitive parents", primitiveParents.size() == 0);
    }

    /**
     * Test get primitive ancestors.
     *
     * @throws Exception the exception
     */
    public void testGetPrimitiveAncestors() throws Exception {
        Collection<String> primtiveAncestors = testNormalFormHierarchyProvider.getPrimitiveAncestors(conceptId);
        assertTrue("Must have primitive ancestors", primtiveAncestors.size() > 0);
    }

    /**
     * Test get fully defined ancestors.
     *
     * @throws Exception the exception
     */
    public void testGetFullyDefinedAncestors() throws Exception {
        Collection<String> definedAncestors = testNormalFormHierarchyProvider.getFullyDefinedAncestors(conceptId);
        assertTrue("Must have defined ancestors", definedAncestors.size() > 0);
    }

    /**
     * Test get fully defined parents.
     *
     * @throws Exception the exception
     */
    public void testGetFullyDefinedParents() throws Exception {
        Collection<String> definedParents = testNormalFormHierarchyProvider.getFullyDefinedParents(conceptId);
        assertTrue("Must have primitive parents", definedParents.size() > 0);
    }

    /**
     * Test get proximal primitive parents.
     *
     * @throws Exception the exception
     */
    public void testGetProximalPrimitiveParents() throws Exception {
        Collection<String> proximalPrimitives = testNormalFormHierarchyProvider.getProximalPrimitiveParents(conceptId);
        assertTrue("Must have one proximal primitive", proximalPrimitives.size() == 1);
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
     * Sets the test normal form hierarchy provider.
     *
     * @param testNormalFormHierarchyProvider the new test normal form hierarchy provider
     */
    public void setTestNormalFormHierarchyProvider(NormalFormHierarchyProvider testNormalFormHierarchyProvider) {
        this.testNormalFormHierarchyProvider = testNormalFormHierarchyProvider;
    }
}
