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

import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a listener that listens to errors in
 * an {@link uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 13, 2010 at 1:33:42 PM
 *
 * @see ErrorLoggerServiceEvent
 */
public interface ErrorLoggerServiceListener {

    /**
     * Error thrown.
     *
     * @param source the source
     * @param message the message
     * @param cause the cause
     * @param level the level of error, defaults to Level.WARNING
     */
    void errorThrown(Object source, String message, Throwable cause, Level level);

    /**
     * Error thrown.
     *
     * @param errorInfo the error info
     */
    void errorThrown(ErrorInfo errorInfo);
}
