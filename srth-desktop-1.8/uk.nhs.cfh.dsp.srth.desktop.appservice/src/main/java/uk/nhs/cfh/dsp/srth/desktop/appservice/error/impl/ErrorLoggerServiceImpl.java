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
package uk.nhs.cfh.dsp.srth.desktop.appservice.error.impl;

import org.jdesktop.swingx.error.ErrorInfo;
import uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLog;
import uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService;
import uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerServiceListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 13, 2010 at 1:32:30 PM
 */
public class ErrorLoggerServiceImpl implements ErrorLoggerService {

    /** The listeners. */
    private List<ErrorLoggerServiceListener> listeners;
    
    /** The error logs. */
    private List<ErrorLog> errorLogs;
    
    /** The initial size. */
    private static int initialSize = 10;

    /**
     * Instantiates a new error logger service impl.
     */
    public ErrorLoggerServiceImpl() {
        listeners = new ArrayList<ErrorLoggerServiceListener>(initialSize);
        errorLogs = new ArrayList<ErrorLog>(initialSize);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService#getListeners()
     */
    public synchronized List<ErrorLoggerServiceListener> getListeners() {
        return listeners;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService#setListeners(java.util.List)
     */
    public synchronized void setListeners(List<ErrorLoggerServiceListener> listeners) {
        this.listeners = listeners;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService#getErrorLogs()
     */
    public synchronized List<ErrorLog> getErrorLogs() {
        return errorLogs;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService#registerListener(uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerServiceListener)
     */
    public synchronized void registerListener(ErrorLoggerServiceListener listener){
        getListeners().add(listener);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService#unregisterListener(uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerServiceListener)
     */
    public synchronized void unregisterListener(ErrorLoggerServiceListener listener){
        getListeners().remove(listener);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService#setErrorLogs(java.util.List)
     */
    public synchronized void setErrorLogs(List<ErrorLog> errorLogs) {
        this.errorLogs = errorLogs;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService#notifyError(java.lang.Object, java.lang.String, java.lang.Throwable)
     */
    public synchronized void notifyError(Object source, String message, Throwable cause, Level level){
        if(cause != null)
        {
            for(ErrorLoggerServiceListener listener : listeners)
            {
                listener.errorThrown(source, message, cause, level);
            }
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerService#notifyError(org.jdesktop.swingx.error.ErrorInfo)
     */
    public synchronized void notifyError(ErrorInfo errorInfo){
        if(errorInfo != null)
        {
            for(ErrorLoggerServiceListener listener : listeners)
            {
                listener.errorThrown(errorInfo);
            }
        }
    }
}
