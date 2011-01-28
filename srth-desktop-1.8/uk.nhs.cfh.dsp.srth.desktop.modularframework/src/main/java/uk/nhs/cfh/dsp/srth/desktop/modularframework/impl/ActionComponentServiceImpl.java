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

import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponent;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentServiceListener;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 2:31:22 PM
 */
public class ActionComponentServiceImpl implements ActionComponentService{

    /** The components. */
    private List<ActionComponent> components;
    
    /** The Constant errorMessage. */
    private static final String errorMessage = "Argument passed can not be null : ";
    
    /** The listeners. */
    private List<ActionComponentServiceListener> listeners;

    /**
     * Instantiates a new action component service impl.
     */
    public ActionComponentServiceImpl() {
        components = new ArrayList<ActionComponent>();
        listeners = new ArrayList<ActionComponentServiceListener>();
    }

    /**
     * Register action component.
     * 
     * @param actionComponent the action component
     */
    public synchronized void registerActionComponent(ActionComponent actionComponent){
        if(actionComponent != null)
        {
            components.add(actionComponent);
            for(ActionComponentServiceListener listener : listeners)
            {
                listener.componentRegistered(this, actionComponent);
            }
        }
        else
        {
            throw new IllegalArgumentException(errorMessage+actionComponent);
        }
    }

    /**
     * Unregister action component.
     * 
     * @param actionComponent the action component
     */
    public synchronized void unregisterActionComponent(ActionComponent actionComponent){
        if(actionComponent != null)
        {
            components.remove(actionComponent);
            for(ActionComponentServiceListener listener : listeners)
            {
                listener.componentUnregistered(this, actionComponent);
            }
        }
        else
        {
            throw new IllegalArgumentException(errorMessage+actionComponent);
        }
    }

    /**
     * Gets the components.
     * 
     * @return the components
     */
    public List<ActionComponent> getComponents() {
        return components;
    }

    /**
     * Sets the components.
     * 
     * @param components the new components
     */
    public void setComponents(List<ActionComponent> components) {
        this.components = components;
    }

    /**
     * Gets the listeners.
     * 
     * @return the listeners
     */
    public List<ActionComponentServiceListener> getListeners() {
        return listeners;
    }

    /**
     * Sets the listeners.
     * 
     * @param listeners the new listeners
     */
    public void setListeners(List<ActionComponentServiceListener> listeners) {
        this.listeners = listeners;
    }

    /**
     * Adds the listener.
     * 
     * @param listener the listener
     */
    public void addListener(ActionComponentServiceListener listener){
        if(listener != null)
        {
            listeners.add(listener);
        }
        else
        {
            throw new IllegalArgumentException(errorMessage+listener);
        }
    }

    /**
     * Removes the listener.
     * 
     * @param listener the listener
     */
    public void removeListener(ActionComponentServiceListener listener){
        if(listener != null)
        {
            listeners.remove(listener);
        }
        else
        {
            throw new IllegalArgumentException(errorMessage+listener);
        }
    }
}
