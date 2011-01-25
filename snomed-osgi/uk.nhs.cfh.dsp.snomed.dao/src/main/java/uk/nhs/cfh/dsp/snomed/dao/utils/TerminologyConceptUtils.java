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
package uk.nhs.cfh.dsp.snomed.dao.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.error.ConceptNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * An utility class that implements static methods for returning a.
 *
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} for a given conceptID.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 19, 2009 at 2:38:49 PM
 */
public class TerminologyConceptUtils {

    /** The logger. */
    private static Log logger = LogFactory.getLog(TerminologyConceptUtils.class);

    /**
     * Gets the concept for id.
     *
     * @param dao the dao
     * @param conceptID the concept id
     * @return the concept for id
     */
    public synchronized static SnomedConcept getConceptForID(TerminologyConceptDAO dao, String conceptID){
        if(conceptID != null && conceptID.length() >0)
        {
            try
            {
                return (SnomedConcept) dao.getTerminologyConcept(conceptID);
            }
            catch (ConceptNotFoundException e) {
                logger.warn("Concept not found. Nested exception is : " + e.fillInStackTrace());
                return null;
            }
        }
        else
        {
            throw new IllegalArgumentException("Concept ID passed can not be null or empty.");
        }
    }
}
