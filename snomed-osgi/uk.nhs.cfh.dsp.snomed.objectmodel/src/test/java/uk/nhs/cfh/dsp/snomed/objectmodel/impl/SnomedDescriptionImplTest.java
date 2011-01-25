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
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription;

// TODO: Auto-generated Javadoc
/**
 * A test class for {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription}. Note we do not need to
 * test the other methods because the class is a bean!
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 6, 2010 at 7:13:08 PM
 */
public class SnomedDescriptionImplTest extends TestCase {

    /** The description. */
    private SnomedDescription description;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        description = new SnomedDescriptionImpl(null, ComponentStatus.CURRENT, null, "Term", true,
                null, null, null);
    }

    /**
     * Test get term.
     *
     * @throws Exception the exception
     */
    public void testGetTerm() throws Exception {
        assertEquals("Term in description must be 'Term", "Term", description.getTerm());
    }
}
