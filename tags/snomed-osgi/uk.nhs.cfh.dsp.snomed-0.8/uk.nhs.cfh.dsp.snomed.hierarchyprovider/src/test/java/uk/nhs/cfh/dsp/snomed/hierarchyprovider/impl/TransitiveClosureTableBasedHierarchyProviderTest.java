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
import uk.nhs.cfh.dsp.snomed.hierarchyprovider.HierarchyProvider;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A test class for {@link uk.nhs.cfh.dsp.snomed.hierarchyprovider.impl.TransitiveClosureTableBasedHierarchyProvider}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2010 at 1:08:42 AM
 */
public class TransitiveClosureTableBasedHierarchyProviderTest extends AbstractDependencyInjectionSpringContextTests {

    /** The tc hierarchy provider. */
    private HierarchyProvider tcHierarchyProvider;
    
    /** The Constant conceptId. */
    private static final String conceptId = "22298006";

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
        /*
         * work around for autowiring used by AbstractDependencyInjectionSpringContextTests.
         * we need to explictly set the bean to inject
         * */
        tcHierarchyProvider = (HierarchyProvider) getApplicationContext().getBean("tcHierarchyProvider");
        assertNotNull("Hierarchy Provider must not be null", tcHierarchyProvider);
        assertTrue("Concept Id must be 22298006", "22298006".equalsIgnoreCase(conceptId));
    }

    /**
     * Test get parents.
     *
     * @throws Exception the exception
     */
    public void testGetParents() throws Exception {
        Collection parents = tcHierarchyProvider.getParents(conceptId);
        assertTrue("Parents must be more than 1", parents.size() > 1);
    }

    /**
     * Test get children.
     *
     * @throws Exception the exception
     */
    public void testGetChildren() throws Exception {
        Collection children = tcHierarchyProvider.getChildren(conceptId);
        assertTrue("Concept must have more than one child", children.size() > 1);
    }

    /**
     * Test get descendants.
     *
     * @throws Exception the exception
     */
    public void testGetDescendants() throws Exception {
        Collection descendants = tcHierarchyProvider.getDescendants(conceptId);
        assertTrue("Concept must have more than one descendant", descendants.size() > 1);
    }

    /**
     * Test get ancestors.
     *
     * @throws Exception the exception
     */
    public void testGetAncestors() throws Exception {
        Collection<String> ancestors = tcHierarchyProvider.getAncestors(conceptId);
        assertTrue("Ancestor set must contain id 404684003", ancestors.contains("404684003"));
    }
}
