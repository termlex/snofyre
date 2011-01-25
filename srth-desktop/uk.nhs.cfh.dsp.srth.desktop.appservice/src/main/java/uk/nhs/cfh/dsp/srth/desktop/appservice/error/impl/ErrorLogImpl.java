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

import uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLog;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLog}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 13, 2010 at 1:37:36 PM
 */
public class ErrorLogImpl implements ErrorLog {

    /** The errors. */
    private List<Error> errors;

    /**
     * Instantiates a new error log impl.
     */
    public ErrorLogImpl() {
        errors = new ArrayList<Error>(20);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLog#getErrors()
     */
    public synchronized List<Error> getErrors() {
        return errors;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLog#setErrors(java.util.List)
     */
    public synchronized void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLog#logError(java.lang.Object, java.lang.Error)
     */
    public synchronized void logError(Object source, Error error){
        errors.add(error);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLog#deleteError(java.lang.Object, java.lang.Error)
     */
    public synchronized void deleteError(Object source, Error error){
        errors.remove(error);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLog#logError(java.lang.Object, java.lang.Exception)
     */
    public synchronized void logError(Object source, Exception exception){
        errors.add(new Error(exception));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLog#deleteError(java.lang.Object, java.lang.Exception)
     */
    public synchronized void deleteError(Object source, Exception exception){
        errors.remove(new Error(exception));
    }
}
