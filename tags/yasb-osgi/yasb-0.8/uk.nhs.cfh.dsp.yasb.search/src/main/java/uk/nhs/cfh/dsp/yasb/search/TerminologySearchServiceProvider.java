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

import java.util.List;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a search service provider.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 23, 2009 at 9:47:33 PM
 */
public interface TerminologySearchServiceProvider {

    /**
     * An enumeration of different search algorithms that can be supported.
     */
    public enum SearchAlgorithm {EXACT_MATCH, EXACT_MATCH_IGNORE_CASE, STARTS_WITH, STARTS_WITH_IGNORE_CASE,
        ENDS_WITH, ENDS_WITH_IGNORE_MATCH, PHRASE_MATCH, PHRASE_MATCH_IGNORE_CASE, WILD_CARDS,
        WILD_CARDS_IGNORE_CASE, PHRASE_UNORDERED, PHRASE_UNORDERED_IGNORE_CASE, REGEX
    };

    /**
     * Checks if is default service provider.
     *
     * @return checks if this provider is the default provider
     */
    boolean isDefaultServiceProvider();

    /**
     * Sets the default service provider.
     *
     * @param defaultServiceProvider the new default service provider
     */
    void setDefaultServiceProvider(boolean defaultServiceProvider);

    /**
     * Gets the max results size.
     *
     * @return the max results size
     */
    int getMaxResultsSize();

    /**
     * Sets the max results size.
     *
     * @param maxResultsSize the new max results size
     */
    void setMaxResultsSize(int maxResultsSize);

    /**
     * Gets the index location.
     *
     * @return the index location
     */
    String getIndexLocation();

    /**
     * Sets the index location.
     *
     * @param indexLocationURL the new index location
     */
    void setIndexLocation(String indexLocationURL);

    /**
     * Gets the service name.
     *
     * @return the service name
     */
    String getServiceName();

    /**
     * Sets the service name.
     *
     * @param serviceName the new service name
     */
    void setServiceName(String serviceName);

    /**
     * Gets the service version.
     *
     * @return the service version
     */
    String getServiceVersion();

    /**
     * Sets the service version.
     *
     * @param serviceVersion the new service version
     */
    void setServiceVersion(String serviceVersion);

    /**
     * Gets the terminology name.
     *
     * @return the terminology name
     */
    String getTerminologyName();

    /**
     * Sets the terminology name.
     *
     * @param terminologyName the new terminology name
     */
    void setTerminologyName(String terminologyName);

    /**
     * Gets the terminology version.
     *
     * @return the terminology version
     */
    String getTerminologyVersion();

    /**
     * Sets the terminology version.
     *
     * @param terminologyVersion the new terminology version
     */
    void setTerminologyVersion(String terminologyVersion);

    /**
     * Gets the supported algorithms.
     *
     * @return the supported algorithms
     */
    Set<SearchAlgorithm> getSupportedAlgorithms();

    /**
     * Sets the supported algorithms.
     *
     * @param supportedAlgorithms the new supported algorithms
     */
    void setSupportedAlgorithms(Set<SearchAlgorithm> supportedAlgorithms);

    /**
     * Adds the supported algorithm.
     *
     * @param searchAlgorithm the search algorithm
     */
    void addSupportedAlgorithm(SearchAlgorithm searchAlgorithm);

    /**
     * Removes the supported algorithm.
     *
     * @param searchAlgorithm the search algorithm
     */
    void removeSupportedAlgorithm(SearchAlgorithm searchAlgorithm);

    /**
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
    List<? extends Object> getMatchesForTerm(SearchAlgorithm algorithm,
                                             String searchTerm, ComponentStatus componentStatus, ConceptType conceptType);

     /**
     * This method returns a {@link java.util.List} of {@link org.apache.lucene.document.Document} objects
     * generally. This might be over ridden when the entire API is moved to use the SNOMED CT object model.
     * Then a collection of {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} will be returned.
     *
     * @param algorithm the search algorithm to use
     * @param conceptId the concept id to search for
     * @param conceptStatus the status of the {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent}
     * @param conceptType the type of the concept
     * @return a list of {@link org.apache.lucene.document.Document} objects. See note above
     */
    List<? extends Object> getMatchesForID(SearchAlgorithm algorithm,
                                           String conceptId, ComponentStatus conceptStatus, ConceptType conceptType);

}
