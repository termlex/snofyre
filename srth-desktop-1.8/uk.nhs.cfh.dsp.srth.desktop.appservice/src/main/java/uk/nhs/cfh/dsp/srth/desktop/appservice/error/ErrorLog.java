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

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a log that contains all application related
 * error messages. The error messages are generally {@link Exception}s with text if any.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 13, 2010 at 1:36:15 PM
 */
public interface ErrorLog {

    /**
     * Gets the errors.
     *
     * @return the errors
     */
    List<Error> getErrors();

    /**
     * Sets the errors.
     *
     * @param errors the new errors
     */
    void setErrors(List<Error> errors);

    /**
     * Log error.
     *
     * @param source the source
     * @param error the error
     */
    void logError(Object source, Error error);

    /**
     * Delete error.
     *
     * @param source the source
     * @param error the error
     */
    void deleteError(Object source, Error error);

    /**
     * Log error.
     *
     * @param source the source
     * @param exception the exception
     */
    void logError(Object source, Exception exception);

    /**
     * Delete error.
     *
     * @param source the source
     * @param exception the exception
     */
    void deleteError(Object source, Exception exception);
}
