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

import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener;

import java.util.Collection;
import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 12, 2010 at 2:52:31 PM
 */
public class PropertyChangeTrackerServiceImpl implements PropertyChangeTrackerService {

    /** The listeners. */
    private Collection<PropertyChangeTrackerServiceListener> listeners;

    /**
     * Instantiates a new property change tracker service impl.
     */
    public PropertyChangeTrackerServiceImpl() {
        listeners = new HashSet<PropertyChangeTrackerServiceListener>();
    }

    /**
     * Register listener.
     * 
     * @param listener the listener
     */
    public synchronized void registerListener(PropertyChangeTrackerServiceListener listener){
        if(listener != null)
        {
            listeners.add(listener);
        }
        else
        {
            throw new IllegalArgumentException("Listener passed can not be null.");
        }
    }

    /**
     * Unregister listener.
     * 
     * @param listener the listener
     */
    public synchronized void unregisterListener(PropertyChangeTrackerServiceListener listener){
        if(listener != null)
        {
            listeners.remove(listener);
        }
        else
        {
            throw new IllegalArgumentException("Listener passed can not be null.");
        }
    }

    /**
     * Fire property changed.
     * 
     * @param propertyName the property name
     * @param oldValue the old value
     * @param newValue the new value
     * @param source the source
     */
    public synchronized void firePropertyChanged(String propertyName, Object oldValue, Object newValue, Object source){
        // loop through listeners and notify them
        for(PropertyChangeTrackerServiceListener listener : getListeners())
        {
            listener.propertyChanged(propertyName, oldValue, newValue, source);
        }
    }

    /**
     * Gets the listeners.
     * 
     * @return the listeners
     */
    public Collection<PropertyChangeTrackerServiceListener> getListeners() {
        return listeners;
    }
}
