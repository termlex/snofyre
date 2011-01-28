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
package uk.nhs.cfh.dsp.srth.desktop.appservice.error;

import org.jdesktop.swingx.error.ErrorInfo;

import java.util.List;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that listens to and logs errors in an.
 *
 * {@link uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 13, 2010 at 1:31:30 PM
 */
public interface ErrorLoggerService {

    /**
     * Gets the listeners.
     *
     * @return the listeners
     */
    List<ErrorLoggerServiceListener> getListeners();

    /**
     * Sets the listeners.
     *
     * @param listeners the new listeners
     */
    void setListeners(List<ErrorLoggerServiceListener> listeners);

    /**
     * Gets the error logs.
     *
     * @return the error logs
     */
    List<ErrorLog> getErrorLogs();

    /**
     * Sets the error logs.
     *
     * @param errorLogs the new error logs
     */
    void setErrorLogs(List<ErrorLog> errorLogs);

    /**
     * Register listener.
     *
     * @param listener the listener
     */
    void registerListener(ErrorLoggerServiceListener listener);

    /**
     * Unregister listener.
     *
     * @param listener the listener
     */
    void unregisterListener(ErrorLoggerServiceListener listener);

    /**
     * Notify error.
     *
     * @param source the source
     * @param message the message
     * @param cause the cause
     * @param level
     */
    void notifyError(Object source, String message, Throwable cause, Level level);

    /**
     * Notify error.
     *
     * @param errorInfo the error info
     */
    void notifyError(ErrorInfo errorInfo);
}
