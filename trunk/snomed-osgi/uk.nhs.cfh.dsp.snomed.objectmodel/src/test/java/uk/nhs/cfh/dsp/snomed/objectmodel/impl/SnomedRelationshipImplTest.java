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
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;

// TODO: Auto-generated Javadoc
/**
 * A test class for {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 6, 2010 at 6:47:20 PM
 */
public class SnomedRelationshipImplTest extends TestCase {

    /** The relationship1. */
    private SnomedRelationship relationship1;
    
    /** The relationship2. */
    private SnomedRelationship relationship2;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();
        relationship1 = new SnomedRelationshipImpl(null, null, "1", "targetID", SnomedRelationship.RelationshipType.DEFINING,
                SnomedRelationship.Refinability.NOT_REFINABLE, "0", "CORE");
        relationship2 = new SnomedRelationshipImpl(null, null, "2", "targetID", SnomedRelationship.RelationshipType.DEFINING,
                SnomedRelationship.Refinability.OPTIONAL, "0", "CORE");
    }

    /**
     * Test is refinable.
     *
     * @throws Exception the exception
     */
    public void testIsRefinable() throws Exception {
        assertFalse("Relationship 1 should not be refinable", relationship1.isRefinable());
        assertTrue("Relationship 2 should be refinable", relationship2.isRefinable());
    }

    /**
     * Test get canonical string form.
     *
     * @throws Exception the exception
     */
    public void testGetCanonicalStringForm() throws Exception {
        assertEquals("Relationship 1's canonical form should be '1=targetID'", 
                                    "1=targetID", relationship1.getCanonicalStringForm());
    }

    /**
     * Test compare to.
     *
     * @throws Exception the exception
     */
    public void testCompareTo() throws Exception {
        assertEquals("Relationship 1 should not be greater than 2 on comparision", 1, 
                                    relationship2.compareTo(relationship1));
    }
}
