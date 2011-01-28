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
package uk.nhs.cfh.dsp.snomed.objectmodel.impl;

import junit.framework.TestCase;
import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

// TODO: Auto-generated Javadoc
/**
 * A test class for {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 6, 2010 at 6:28:23 PM
 */
public class SnomedConceptImplTest extends TestCase {

    /** The concept1. */
    private SnomedConcept concept1;
    
    /** The concept2. */
    private SnomedConcept concept2;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {
        concept1 = new SnomedConceptImpl("1", "A");
        concept1.setStatus(ComponentStatus.CURRENT);
        concept2 = new SnomedConceptImpl("2", "B");
        concept2.setStatus(ComponentStatus.RETIRED);
    }

    /**
     * Test get canonical string form.
     *
     * @throws Exception the exception
     */
    public void testGetCanonicalStringForm() throws Exception {
        assertEquals("Canonical form is not 1", "1", concept1.getCanonicalStringForm());
        assertTrue("Canonical forms of concepts 1 and 2 are the same",
                 !(concept1.getCanonicalStringForm().equalsIgnoreCase(concept2.getCanonicalStringForm())));
    }

    /**
     * Test is active concept.
     *
     * @throws Exception the exception
     */
    public void testIsActiveConcept() throws Exception {
        assertTrue("Concept1 should be have status ACTIVE", concept1.isActiveConcept());
        assertFalse("Concept 2 must have a status of RETIRED", ComponentStatus.AMBIGUOUS == concept2.getStatus());
    }

    /**
     * Test equals.
     *
     * @throws Exception the exception
     */
    public void testEquals() throws Exception {
        assertFalse("Concepts 1 and 2 should not be equal", concept1.equals(concept2));
    }

    /**
     * Test compare to.
     *
     * @throws Exception the exception
     */
    public void testCompareTo() throws Exception {
        assertEquals("Concept 2 must be greater than concept1 for sorting", 1, concept2.compareTo(concept1));
    }
}
