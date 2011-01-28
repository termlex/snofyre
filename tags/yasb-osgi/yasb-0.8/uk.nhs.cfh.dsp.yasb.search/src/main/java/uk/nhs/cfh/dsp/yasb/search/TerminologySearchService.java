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
package uk.nhs.cfh.dsp.yasb.search;

import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;

import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a search service.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 23, 2009 at 9:29:22 PM
 */
public interface TerminologySearchService {


    /**
     * Gets the providers.
     *
     * @return the providers
     */
    List<TerminologySearchServiceProvider> getProviders();

    /**
     * Register provider.
     *
     * @param provider the provider
     */
    void registerProvider(TerminologySearchServiceProvider provider);

    /**
     * Unregister provider.
     *
     * @param provider the provider
     */
    void unregisterProvider(TerminologySearchServiceProvider provider);

    /**
     * Adds the listener.
     *
     * @param listener the listener
     */
    void addListener(TerminologySearchServiceListener listener);

    /**
     * Removeistener.
     *
     * @param listener the listener
     */
    void removeistener(TerminologySearchServiceListener listener);

    /**
     * Search results available.
     *
     * @param searchResults the search results
     */
    void searchResultsAvailable(Collection<? extends Object> searchResults);

    /**
     * This is a utility method that calls the method by the same name in the default {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider}.
     * This method returns a {@link java.util.List} of {@link org.apache.lucene.document.Document} objects
     * generally. This might be over ridden when the entire API is moved to use the SNOMED CT object model.
     * Then a collection of {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} will be returned.
     *
     * @param algorithm the search algorithm to use
     * @param searchTerm the search term
     * @param componentStatus the status of the {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent}
     * @param conceptType the type of the concept
     * @return a list of {@link org.apache.lucene.document.Document} objects. See note above
     */
    List<? extends Object> getMatchesForTerm(TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                             String searchTerm, ComponentStatus componentStatus, ConceptType conceptType);
    /**
     * This is a utility method that calls the method by the same name in the default {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider}.
     *
     * This method returns a {@link java.util.List} of {@link org.apache.lucene.document.Document} objects
     * generally. This might be over ridden when the entire API is moved to use the SNOMED CT object model.
     * Then a collection of {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} will be returned.
     *
     * @param algorithm the search algorithm to use
     * @param conceptId the concept id to search for
     * @param componentStatus the status of the {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent}
     * @param conceptType the type of the concept
     * @return a list of {@link org.apache.lucene.document.Document} objects. See note above
     */
    List<? extends Object> getMatchesForID(TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                           String conceptId, ComponentStatus componentStatus, ConceptType conceptType);

    /**
     * This is a utility method that calls the method by the same name in the default {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider}.
     * This method returns a {@link java.util.List} of {@link org.apache.lucene.document.Document} objects
     * generally. This might be over ridden when the entire API is moved to use the SNOMED CT object model.
     * Then a collection of {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} will be returned.
     *
     * @param provider the terminology search provider to use. Throws an error if this is missing!
     * @param algorithm the search algorithm to use
     * @param searchTerm the search term
     * @param componentStatus the status of the {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent}
     * @param conceptType the type of the concept
     * @return a list of {@link org.apache.lucene.document.Document} objects. See note above
     */
    List<? extends Object> getMatchesForTerm(TerminologySearchServiceProvider provider, TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                             String searchTerm, ComponentStatus componentStatus, ConceptType conceptType);

    /**
     * This is a utility method that calls the method by the same name in the default {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider}.
     *
     * This method returns a {@link java.util.List} of {@link org.apache.lucene.document.Document} objects
     * generally. This might be over ridden when the entire API is moved to use the SNOMED CT object model.
     * Then a collection of {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} will be returned.
     *
     * @param provider the terminology search provider to use. Throws an error if this is missing!
     * @param algorithm the search algorithm to use
     * @param conceptId the concept id to search for
     * @param componentStatus the status of the {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent}
     * @param conceptType the type of the concept
     * @return a list of {@link org.apache.lucene.document.Document} objects. See note above
     */
    List<? extends Object> getMatchesForID(TerminologySearchServiceProvider provider, TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                           String conceptId, ComponentStatus componentStatus, ConceptType conceptType);
}
