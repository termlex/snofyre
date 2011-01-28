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
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentServiceListener;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 2:05:51 PM
 */
public class ViewComponentServiceImpl implements ViewComponentService{

    /** The components. */
    private List<ViewComponent> components;
    
    /** The listeners. */
    private List<ViewComponentServiceListener> listeners;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ViewComponentServiceImpl.class);


    /**
     * Instantiates a new view component service impl.
     */
    public ViewComponentServiceImpl() {
        components = new ArrayList<ViewComponent>();
        listeners = new ArrayList<ViewComponentServiceListener>();
    }

    /**
     * Register view component.
     * 
     * @param viewComponent the view component
     */
    public synchronized void registerViewComponent(ViewComponent viewComponent){
        if(viewComponent != null)
        {
            components.add(viewComponent);
            for(ViewComponentServiceListener listener : listeners)
            {
                listener.viewComponentRegistered(this, viewComponent);
            }
        }
        else
        {
            logger.warn("viewComponent = " + viewComponent);
            throw new IllegalArgumentException("View component passed can not be null : "+viewComponent);
        }
    }

    /**
     * Unregister view component.
     * 
     * @param viewComponent the view component
     */
    public synchronized void unregisterViewComponent(ViewComponent viewComponent){
        if(viewComponent != null)
        {
            components.remove(viewComponent);
            for(ViewComponentServiceListener listener : listeners)
            {
                listener.viewComponentUnregistered(this, viewComponent);
            }
        }
        else
        {
            logger.warn("viewComponent = " + viewComponent);
            throw new IllegalArgumentException("View component passed can not be null : "+viewComponent);
        }
    }

    /**
     * Gets the components.
     * 
     * @return the components
     */
    public List<ViewComponent> getComponents() {
        return components;
    }

    /**
     * Sets the components.
     * 
     * @param components the new components
     */
    public void setComponents(List<ViewComponent> components) {
        this.components = components;
    }

    /**
     * Gets the listeners.
     * 
     * @return the listeners
     */
    public List<ViewComponentServiceListener> getListeners() {
        return listeners;
    }

    /**
     * Sets the listeners.
     * 
     * @param listeners the new listeners
     */
    public void setListeners(List<ViewComponentServiceListener> listeners) {
        this.listeners = listeners;
    }

    /**
     * Adds the listener.
     * 
     * @param listener the listener
     */
    public void addListener(ViewComponentServiceListener listener){
        if(listener != null)
        {
            listeners.add(listener);
        }
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null : "+listener);
        }
    }

    /**
     * Removes the listener.
     * 
     * @param listener the listener
     */
    public void removeListener(ViewComponentServiceListener listener){
        if(listener != null)
        {
            listeners.remove(listener);
        }
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null : "+listener);
        }
    }
}
