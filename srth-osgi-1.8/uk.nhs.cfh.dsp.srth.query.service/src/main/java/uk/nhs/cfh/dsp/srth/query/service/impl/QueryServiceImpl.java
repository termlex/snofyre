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
package uk.nhs.cfh.dsp.srth.query.service.impl;

import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.service.QueryServiceListener;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link QueryService}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 16, 2009 at 5:39:04 PM
 * <br> Modified on Wednesday; December 2, 2009 at 4:09 PM to include the source of the event
 */

public class QueryServiceImpl implements QueryService {

	/** The queries. */
	private List<QueryStatement> queries;
	
	/** The listeners. */
	private ArrayList<QueryServiceListener> listeners ;
    
    /** The active query. */
    private QueryStatement activeQuery;
	
	/**
	 * Instantiates a new query service impl.
	 */
	public QueryServiceImpl() {
		queries = new ArrayList<QueryStatement>();
		listeners = new ArrayList<QueryServiceListener>();
	}

	/**
	 * Gets the queries.
	 * 
	 * @return the queries
	 */
	public List<QueryStatement> getQueries() {
		return queries;
	}

	/**
	 * Adds the listener.
	 * 
	 * @param listener the listener
	 */
	public void addListener(QueryServiceListener listener) {
		if(listener != null)
		{
			listeners.add(listener);
		}
		else
		{
			throw new IllegalArgumentException("Listener passed can not be null");
		}
	}

	/**
	 * Removes the listener.
	 * 
	 * @param listener the listener
	 */
	public void removeListener(QueryServiceListener listener) {
		if(listener != null)
		{
			listeners.remove(listener);
		}
		else
		{
			throw new IllegalArgumentException("Listener passed can not be null");
		}
	}
	
	/**
	 * Register query.
	 * 
	 * @param query the query
	 */
	@SuppressWarnings("unchecked")
	public synchronized void registerQuery(QueryStatement query) {
		if(query != null)
		{
            // set query to active query
            activeQuery = query;
			queries.add(query);
		}
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null : "+query);
        }
	}

	/**
	 * Unregister query.
	 * 
	 * @param query the query
	 */
	@SuppressWarnings("unchecked")
	public synchronized void unregisterQuery(QueryStatement query) {
		if(query != null)
		{
            // set active query to null
            activeQuery = null;
            // remove query from list of queries
			queries.remove(query);
		}
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null : "+query);
        }
	}
	
	/**
	 * Query changed.
	 * 
	 * @param query the query
	 * @param source the source
	 */
	@SuppressWarnings("unchecked")
	public synchronized void queryChanged(QueryStatement query, Object source){
		if(query != null)
		{
			// notify listeners
			for(QueryServiceListener listener : (ArrayList<QueryServiceListener>)listeners.clone())
			{
				listener.queryChanged(this, query, source);
			}
		}
	}

    /**
     * Gets the active query.
     * 
     * @return the active query
     */
    public synchronized QueryStatement getActiveQuery() {
        return activeQuery;
    }

    /**
     * Gets the listeners.
     * 
     * @return the listeners
     */
    public ArrayList<QueryServiceListener> getListeners() {
        return listeners;
    }

    /**
     * Sets the listeners.
     * 
     * @param listeners the new listeners
     */
    public void setListeners(ArrayList<QueryServiceListener> listeners) {
        this.listeners = listeners;
    }
}
