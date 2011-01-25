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
package uk.nhs.cfh.dsp.srth.desktop.appservice.impl;

import org.jdesktop.application.Application;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationServiceListener;
import uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingApplication;
import uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingView;
import uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService;
import uk.nhs.cfh.dsp.srth.desktop.appservice.error.impl.ErrorLoggerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;


// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService}.
 * 
 * <br> Version : @#VersionNumber#@
 * <br> Written by @author jay
 * <br> Created on Nov 27, 2009 at 5:44:00 PM
 */
public class ApplicationServiceImpl implements ApplicationService {

    private MDockingApplication mApplication;
    
    /** The Constant errorMessage. */
    private static final String errorMessage = "Argument passed can not be null : ";
    
    /** The listeners. */
    private List<ApplicationServiceListener> listeners;
    
    /** The frame view. */
    private MDockingView frameView;
    
    /** The error logger service. */
    private ErrorLoggerService errorLoggerService;
    private Properties applicationProperties;

    /**
     * Instantiates a new application service impl.
     */
    public ApplicationServiceImpl() {
        listeners = new ArrayList<ApplicationServiceListener>(5);
        errorLoggerService = new ErrorLoggerServiceImpl();
    }

    /**
     * Gets the active application.
     * 
     * @return the active application
     */
    public Application getActiveApplication() {
        return mApplication.getActiveApplication();
    }

    /**
     * Gets the frame view.
     * 
     * @return the frame view
     */
    public MDockingView getFrameView() {
        return frameView;
    }

    /**
     * Gets the listeners.
     * 
     * @return the listeners
     */
    public List<ApplicationServiceListener> getListeners() {
        return listeners;
    }

    /**
     * Sets the listeners.
     * 
     * @param listeners the new listeners
     */
    public void setListeners(List<ApplicationServiceListener> listeners) {
        this.listeners = listeners;
    }

    /**
     * Register frame view.
     * 
     * @param frameView the frame view
     */
    public void registerFrameView(MDockingView frameView){
        if(frameView != null)
        {
            this.frameView = frameView;
            // notify listeners
            for(ApplicationServiceListener listener : listeners)
            {
                listener.frameViewRegistered(frameView);
            }
        }
        else
        {
            throw new IllegalArgumentException(errorMessage+frameView);
        }
    }

    /**
     * Unregister frame view.
     * 
     * @param frameView the frame view
     */
    public void unregisterFrameView(MDockingView frameView){
        if(frameView != null)
        {
            this.frameView = null;
            // notify listeners
            for(ApplicationServiceListener listener : listeners)
            {
                listener.frameViewUnregistered(frameView);
            }
        }
        else
        {
            throw new IllegalArgumentException(errorMessage+frameView);
        }
    }

    /**
     * Register application.
     * 
     * @param application the application
     */
    public void registerApplication(MDockingApplication application){
        if(application != null)
        {
            this.mApplication = application;
            // notify listeners
            for(ApplicationServiceListener listener : listeners)
            {
                listener.applicationRegistered(application);
            }
        }
        else
        {
            throw new IllegalArgumentException(errorMessage+application);
        }
    }

    /**
     * Unregister application.
     * 
     * @param application the application
     */
    public void unregisterApplication(MDockingApplication application){
        if(application != null)
        {
            this.mApplication = null;
            // notify listeners
            for(ApplicationServiceListener listener : listeners)
            {
                listener.applicationUnregistered(application);
            }
        }
        else
        {
            throw new IllegalArgumentException(errorMessage+application);
        }
    }

    /**
     * Adds the listener.
     * 
     * @param listener the listener
     */
    public void addListener(ApplicationServiceListener listener){
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
    public void removeListener(ApplicationServiceListener listener){
        if(listener != null)
        {
            listeners.remove(listener);
        }
        else
        {
            throw new IllegalArgumentException(errorMessage+listener);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService#getErrorLoggerService()
     */
    public synchronized ErrorLoggerService getErrorLoggerService() {
        return errorLoggerService;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService#setErrorLoggerService(uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService)
     */
    public synchronized void setErrorLoggerService(ErrorLoggerService errorLoggerService) {
        this.errorLoggerService = errorLoggerService;
    }

    public Properties getApplicationProperties() {
        return applicationProperties;
    }

    public void setApplicationProperties(Properties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void setApplicationProperty(String propertyName, String propertyValue){
        if (applicationProperties != null) {
            applicationProperties.setProperty(propertyName, propertyValue);
        }
    }

    public String getApplicationProperty(String propertyName){
        if(applicationProperties != null && applicationProperties.containsKey(propertyName))
        {
            return applicationProperties.getProperty(propertyName);
        }
        else
        {
            return null;
        }
    }

    public void notifyError(String message, Throwable cause, Level level){
        if(errorLoggerService != null)
        {
            errorLoggerService.notifyError(null, message, cause, level);
        }
    }
}