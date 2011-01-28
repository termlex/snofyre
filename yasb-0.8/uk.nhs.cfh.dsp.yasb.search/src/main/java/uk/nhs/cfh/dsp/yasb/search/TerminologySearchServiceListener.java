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

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a listener for a {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchService}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 23, 2009 at 9:41:46 PM
 *
 * @see TerminologySearchServiceEvent
 */
public interface TerminologySearchServiceListener {

    /**
     * Provider registered.
     *
     * @param service the service
     * @param provider the provider
     */
    void providerRegistered(TerminologySearchService service, TerminologySearchServiceProvider provider);

    /**
     * Provider unregistered.
     *
     * @param service the service
     * @param provider the provider
     */
    void providerUnregistered(TerminologySearchService service, TerminologySearchServiceProvider provider);

    /**
     * Search results changed.
     *
     * @param searchResults the search results
     */
    void searchResultsChanged(Collection<? extends Object> searchResults);
}
