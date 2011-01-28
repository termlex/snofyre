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
package uk.nhs.cfh.dsp.srth.desktop.appservice;

import org.jdesktop.application.Application;
import uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * A service that monitors the availability of an {@link org.jdesktop.application.Application}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 27, 2009 at 5:43:21 PM
 */
public interface ApplicationService {

   /**
    * Gets the active application.
    * 
    * @return the active application
    */
    Application getActiveApplication();

   /**
    * Gets the listeners.
    * 
    * @return the listeners
    */
    List<ApplicationServiceListener> getListeners();

   /**
    * Sets the listeners.
    * 
    * @param listeners the new listeners
    */
    void setListeners(List<ApplicationServiceListener> listeners);

   /**
    * Register application.
    * 
    * @param application the application
    */
    void registerApplication(MDockingApplication application);

   /**
    * Unregister application.
    * 
    * @param application the application
    */
    void unregisterApplication(MDockingApplication application);

   /**
    * Adds the listener.
    * 
    * @param listener the listener
    */
    void addListener(ApplicationServiceListener listener);

   /**
    * Removes the listener.
    * 
    * @param listener the listener
    */
    void removeListener(ApplicationServiceListener listener);

   /**
    * Gets the frame view.
    * 
    * @return the frame view
    */
    MDockingView getFrameView();

   /**
    * Register frame view.
    * 
    * @param frameView the frame view
    */
    void registerFrameView(MDockingView frameView);

   /**
    * Unregister frame view.
    * 
    * @param frameView the frame view
    */
    void unregisterFrameView(MDockingView frameView);

    /**
     * Gets the error logger service.
     *
     * @return the error logger service
     */
    ErrorLoggerService getErrorLoggerService();

    /**
     * Sets the error logger service.
     *
     * @param errorLoggerService the new error logger service
     */
    void setErrorLoggerService(ErrorLoggerService errorLoggerService);

    void setApplicationProperties(Properties applicationProperties);

    void setApplicationProperty(String propertyName, String propertyValue);

    String getApplicationProperty(String propertyName);

    Properties getApplicationProperties();

    void notifyError(String message, Throwable cause, Level level);
}