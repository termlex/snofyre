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
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.RandomSubtypeHierarchyProvider;

import java.util.Collection;

/**
 * A test case for {@link uk.nhs.cfh.dsp.snomed.hierarchyprovider.impl.RandomSubtypeHierarchyProviderImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 18, 2011 at 8:56:18 PM
 */
public class RandomSubtypeHierarchyProviderImplTest extends AbstractDependencyInjectionSpringContextTests {

    /** The test normal form hierarchy provider. */
    private RandomSubtypeHierarchyProvider testRandomSubtypeHierarchyProvider;

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
        testRandomSubtypeHierarchyProvider = (RandomSubtypeHierarchyProvider) getApplicationContext().getBean("testRandomSubtypeHierarchyProvider");
        assertNotNull("Hierarchy Provider must not be null", testRandomSubtypeHierarchyProvider);
    }

    public void testGetRandomSetOfSubTypes() throws Exception {
        // we get 10 subtypes
        Collection<String> subtypes = testRandomSubtypeHierarchyProvider.getRandomSetOfSubTypes(conceptId, 10);
        assertEquals("10 subtypes must have been returned", 10, subtypes.size());
    }

    public void testGetRandomSubtype() throws Exception {
        // we get 1 subtype
        String subType = testRandomSubtypeHierarchyProvider.getRandomSubtype(conceptId);
        assertNotNull("Subtype returned must not be null", subType);
    }

    public void setTestRandomSubtypeHierarchyProvider(RandomSubtypeHierarchyProvider testRandomSubtypeHierarchyProvider) {
        this.testRandomSubtypeHierarchyProvider = testRandomSubtypeHierarchyProvider;
    }
}
