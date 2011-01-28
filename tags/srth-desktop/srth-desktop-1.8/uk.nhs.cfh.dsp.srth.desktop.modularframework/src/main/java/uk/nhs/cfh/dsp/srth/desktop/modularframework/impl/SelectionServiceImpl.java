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
package uk.nhs.cfh.dsp.srth.desktop.modularframework.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionServiceListener;

import java.util.ArrayList;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 3:04:55 PM
 */
public class SelectionServiceImpl implements SelectionService {

    /** The listeners. */
    private Collection<SelectionServiceListener> listeners;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SelectionServiceImpl.class);    


    /**
     * Instantiates a new selection service impl.
     */
    public SelectionServiceImpl() {
        listeners = new ArrayList<SelectionServiceListener>();
    }

    /**
     * Register listener.
     * 
     * @param listener the listener
     */
    public synchronized void registerListener(SelectionServiceListener listener){
        if(listener != null)
        {
            listeners.add(listener);
        }
        else
        {
            logger.warn("listener = " + listener);
            throw new IllegalArgumentException("Argument passed can not be null : "+listener);
        }
    }

    /**
     * Unregister listener.
     * 
     * @param listener the listener
     */
    public synchronized void unregisterListener(SelectionServiceListener listener){
        if(listener != null)
        {
            listeners.remove(listener);
        }
        else
        {
            logger.warn("listener = " + listener);
            throw new IllegalArgumentException("Argument passed can not be null : "+listener);
        }
    }

    /**
     * Object selected.
     * 
     * @param selection the selection
     * @param source the source
     */
    public synchronized void objectSelected(Object selection, Object source){
        for(SelectionServiceListener listener : listeners)
        {
            // notify listeners
            listener.selectionChanged(this, selection, source);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService#getListeners()
     */
    public Collection<SelectionServiceListener> getListeners() {
        return listeners;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService#setListeners(java.util.Collection)
     */
    public void setListeners(Collection<SelectionServiceListener> listeners) {
        this.listeners = listeners;
    }
}
