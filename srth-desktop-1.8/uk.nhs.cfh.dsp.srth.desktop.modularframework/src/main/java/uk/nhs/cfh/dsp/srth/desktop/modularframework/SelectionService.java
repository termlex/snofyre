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
 * An interface specification for a service that tracks user selection in the
 * desktop version of SNOMED CT Reporting Test Harness.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 3:02:19 PM
 */
public interface SelectionService {

    /**
     * Register listener.
     * 
     * @param listener the listener
     */
    void registerListener(SelectionServiceListener listener);

    /**
     * Unregister listener.
     * 
     * @param listener the listener
     */
    void unregisterListener(SelectionServiceListener listener);

    /**
     * Object selected.
     * 
     * @param selection the selection
     * @param source the source
     */
    void objectSelected(Object selection, Object source);

    /**
     * Gets the listeners.
     *
     * @return the listeners
     */
    Collection<SelectionServiceListener> getListeners();

    /**
     * Sets the listeners.
     *
     * @param listeners the new listeners
     */
    void setListeners(Collection<SelectionServiceListener> listeners);
}
