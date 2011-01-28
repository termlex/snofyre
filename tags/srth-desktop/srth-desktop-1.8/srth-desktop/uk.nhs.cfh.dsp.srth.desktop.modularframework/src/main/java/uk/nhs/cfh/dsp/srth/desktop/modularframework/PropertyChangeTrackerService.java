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
package uk.nhs.cfh.dsp.srth.desktop.modularframework;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A service that tracks changes within the framework based on bean properties. This class should allow property
 * change events to be tracked dynamically in a thread safe manner in an OSGi framework.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 12, 2010 at 2:47:00 PM
 */
public interface PropertyChangeTrackerService {

    /**
     * Register listener.
     * 
     * @param listener the listener
     */
    void registerListener(PropertyChangeTrackerServiceListener listener);

    /**
     * Unregister listener.
     * 
     * @param listener the listener
     */
    void unregisterListener(PropertyChangeTrackerServiceListener listener);

    /**
     * Fire property changed.
     * 
     * @param propertyName the property name
     * @param oldValue the old value
     * @param newValue the new value
     * @param source the source
     */
    void firePropertyChanged(String propertyName, Object oldValue, Object newValue, Object source);

    /**
     * Gets the listeners.
     * 
     * @return the listeners
     */
    Collection<PropertyChangeTrackerServiceListener> getListeners();
}
