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
package uk.nhs.cfh.dsp.srth.query.service;

import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A modular service that registers and tracks {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}s.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 16, 2009 at 5:29:57 PM
 * <br> Modified on Wednesday; December 2, 2009 at 4:09 PM to include the source of the event
 */

public interface QueryService {

	/**
	 * Register query.
	 * 
	 * @param query the query
	 */
	 void registerQuery(QueryStatement query);
	
	/**
	 * Unregister query.
	 * 
	 * @param query the query
	 */
	 void unregisterQuery(QueryStatement query);
	
	/**
	 * Gets the queries.
	 * 
	 * @return the queries
	 */
	 List<QueryStatement> getQueries();
	
	/**
	 * Adds the listener.
	 * 
	 * @param listener the listener
	 */
	 void addListener(QueryServiceListener listener);
	
	/**
	 * Removes the listener.
	 * 
	 * @param listener the listener
	 */
	 void removeListener(QueryServiceListener listener);

	/**
	 * Query changed.
	 * 
	 * @param query the query
	 * @param source the source
	 */
	 void queryChanged(QueryStatement query, Object source);

    /**
     * Gets the active query.
     * 
     * @return the active query
     */
     QueryStatement getActiveQuery();

    /**
     * Gets the listeners.
     * 
     * @return the listeners
     */
     ArrayList<QueryServiceListener> getListeners();

    /**
     * Sets the listeners.
     * 
     * @param listeners the new listeners
     */
     void setListeners(ArrayList<QueryServiceListener> listeners);
}
