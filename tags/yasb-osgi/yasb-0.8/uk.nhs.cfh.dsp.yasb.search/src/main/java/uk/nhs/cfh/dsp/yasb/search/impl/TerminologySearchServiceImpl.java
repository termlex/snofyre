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
package uk.nhs.cfh.dsp.yasb.search.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceListener;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * An implementation of a {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchService}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 23, 2009 at 9:31:06 PM
 */
public class TerminologySearchServiceImpl implements TerminologySearchService{

    /** The providers. */
    private List<TerminologySearchServiceProvider> providers;
    
    /** The listeners. */
    private Collection<TerminologySearchServiceListener> listeners;
    
    /** The default search service provider. */
    private TerminologySearchServiceProvider defaultSearchServiceProvider;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(TerminologySearchServiceImpl.class);
    private static final String PROVIDER_ERROR_MESSGE = "Provider passed is not legal : ";
    private static final String LISTENER_ERROR_MESSGE = "Listener passed is not legal : ";
    private static final String ARGUMENT_ERROR_MESSGE = "Argument passed is not legal : ";
    private static final String DEFAULT_PROVIDER_ERROR_MESSGE = "No default Search Provider set in the service. Please register " +
                    "atleast one provider and set it to default provider.";

    /**
     * Instantiates a new terminology search service impl.
     */
    public TerminologySearchServiceImpl() {
        providers = new ArrayList<TerminologySearchServiceProvider>();
        listeners = new ArrayList<TerminologySearchServiceListener>();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchService#getProviders()
     */
    public List<TerminologySearchServiceProvider> getProviders() {
        return providers;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchService#registerProvider(uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider)
     */
    public synchronized void registerProvider(TerminologySearchServiceProvider provider){
        if(provider != null)
        {
            providers.add(provider);
            // set and check if default service provider
            if(provider.isDefaultServiceProvider())
            {
                this.defaultSearchServiceProvider = provider;
            }
            // notify listeners
            for(TerminologySearchServiceListener listener : listeners)
            {
                listener.providerRegistered(this, provider);
            }
        }
        else
        {
            logger.warn(PROVIDER_ERROR_MESSGE+provider);
            throw new IllegalArgumentException(PROVIDER_ERROR_MESSGE+provider);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchService#unregisterProvider(uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider)
     */
    public synchronized void unregisterProvider(TerminologySearchServiceProvider provider){
        if(provider != null)
        {
            providers.remove(provider);
            if(defaultSearchServiceProvider.equals(provider))
            {
                // reset default provider
                this.defaultSearchServiceProvider = null;
            }
            // notify listeners
            for(TerminologySearchServiceListener listener : listeners)
            {
                listener.providerUnregistered(this, provider);
            }
        }
        else
        {
            logger.warn(PROVIDER_ERROR_MESSGE+provider);
            throw new IllegalArgumentException(PROVIDER_ERROR_MESSGE+provider);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchService#addListener(uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceListener)
     */
    public synchronized void addListener(TerminologySearchServiceListener listener){
        if(listener != null)
        {
            listeners.add(listener);
        }
        else
        {
            logger.warn(LISTENER_ERROR_MESSGE+listener);
            throw new IllegalArgumentException(LISTENER_ERROR_MESSGE+listener);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchService#removeistener(uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceListener)
     */
    public synchronized void removeistener(TerminologySearchServiceListener listener){
        if(listener != null)
        {
            listeners.remove(listener);
        }
        else
        {
            logger.warn(LISTENER_ERROR_MESSGE+listener);
            throw new IllegalArgumentException(LISTENER_ERROR_MESSGE+listener);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.yasb.search.TerminologySearchService#searchResultsAvailable(java.util.Collection)
     */
    @SuppressWarnings("unchecked")
    public synchronized void searchResultsAvailable(Collection<? extends Object> searchResults) {
        if(searchResults != null)
        {
            for(TerminologySearchServiceListener listener : listeners)
            {
                listener.searchResultsChanged(searchResults);
            }
        }
    }

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
    public List<? extends Object> getMatchesForTerm(TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                                    String searchTerm, ComponentStatus componentStatus, ConceptType conceptType) {

        if(defaultSearchServiceProvider != null)
        {
            List<? extends Object> result = defaultSearchServiceProvider.getMatchesForTerm(algorithm, searchTerm,
                    componentStatus, conceptType);
            // publish results
            searchResultsAvailable(result);
            return result;
        }
        else
        {
            logger.warn(DEFAULT_PROVIDER_ERROR_MESSGE);
            throw new UnsupportedOperationException(DEFAULT_PROVIDER_ERROR_MESSGE);
        }
    }

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
    public List<? extends Object> getMatchesForID(TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                                  String conceptId, ComponentStatus componentStatus, ConceptType conceptType) {
        if(defaultSearchServiceProvider != null)
        {
            List<? extends Object> result = defaultSearchServiceProvider.getMatchesForID(algorithm,
                    conceptId, componentStatus, conceptType);
            // publish results
            searchResultsAvailable(result);
            return result;
        }
        else
        {
            logger.warn(DEFAULT_PROVIDER_ERROR_MESSGE);
            throw new UnsupportedOperationException(DEFAULT_PROVIDER_ERROR_MESSGE);
        }
    }

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
    public List<? extends Object> getMatchesForTerm(TerminologySearchServiceProvider provider,
                                                    TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                                    String searchTerm, ComponentStatus componentStatus, ConceptType conceptType) {
        if(provider != null)
        {
            List<? extends Object> result = defaultSearchServiceProvider.getMatchesForTerm(algorithm, searchTerm,
                    componentStatus, conceptType);
            // publish results
            searchResultsAvailable(result);
            return result;
        }
        else
        {
            throw new IllegalArgumentException(ARGUMENT_ERROR_MESSGE+provider);
        }
    }


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
    public List<? extends Object> getMatchesForID(TerminologySearchServiceProvider provider,
                                                  TerminologySearchServiceProvider.SearchAlgorithm algorithm,
                                                  String conceptId, ComponentStatus componentStatus, ConceptType conceptType) {
        if(defaultSearchServiceProvider != null)
        {
            List<? extends Object> result = defaultSearchServiceProvider.getMatchesForID(algorithm, conceptId,
                    componentStatus, conceptType);
            // publish results
            searchResultsAvailable(result);
            return result;
        }
        else
        {
            throw new IllegalArgumentException(ARGUMENT_ERROR_MESSGE+provider);
        }
    }
}
