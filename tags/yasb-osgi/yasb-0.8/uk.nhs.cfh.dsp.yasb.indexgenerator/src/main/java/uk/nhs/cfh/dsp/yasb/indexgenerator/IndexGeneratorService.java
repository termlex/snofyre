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
package uk.nhs.cfh.dsp.yasb.indexgenerator;

import org.apache.lucene.store.Directory;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;

import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that generates an index of.
 *
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}s or
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription}s
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 6, 2010 at 11:22:57 AM
 */
public interface IndexGeneratorService {

    /**
     * the method that generates an index in the specified index location and returns
     * the {@link org.apache.lucene.store.Directory} for the index
     *
     * @param indexFlavour the {@link uk.nhs.cfh.dsp.yasb.indexgenerator.IndexGeneratorService.Index_Flavour}
     * @return the {@link org.apache.lucene.store.Directory} object for the index.
     */
    Directory getIndexDirectory(Index_Flavour indexFlavour);

    /**
     * Gets the index location.
     *
     * @return the index location
     */
    URL getIndexLocation();

    /**
     * Sets the index location.
     *
     * @param indexLocation the new index location
     */
    void setIndexLocation(URL indexLocation);

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO);

    /**
     * An enumeration that determines if an index is generated over a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}
     * or a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedDescription}
     */
    public enum Index_Flavour{CONCEPT, DESCRIPTION}
}
