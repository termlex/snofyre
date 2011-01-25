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
/**
 *
 */
package uk.nhs.cfh.dsp.snomed.converters.xml;

import org.jdom.Document;
import uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept;

import java.io.File;


// TODO: Auto-generated Javadoc
/**
 * A generic interface to convert a {@link uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept} to JDOM {@link org.jdom.Document} object.
 *
 * @deprecated Use {@link uk.nhs.cfh.dsp.snomed.converters.xml.SnomedXMLConverter} instead
 * 
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 22, 2008 at 11:43:00 PM
 */
public interface TerminologyConceptToXMLConverter {

    /**
     * Convert concept to xml document.
     *
     * @param concept the concept
     *
     * @return the document
     */
    Document convertConceptToXMLDocument(TerminologyConcept concept);

    /**
     * Convert xml to concept.
     *
     * @param document the document
     *
     * @return the terminology concept
     */
    TerminologyConcept convertXMLToConcept(Document document);

    /**
     * Export concept to xml document.
     *
     * @param concept the concept
     * @param file the file to export to
     */
    void exportConceptToXMLDocument(TerminologyConcept concept, File file);

}