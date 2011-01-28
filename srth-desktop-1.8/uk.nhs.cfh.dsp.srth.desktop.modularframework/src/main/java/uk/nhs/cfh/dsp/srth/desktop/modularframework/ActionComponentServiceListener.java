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

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a listener that listens to registration events from a.
 *
 * {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 2:04:56 PM
 */
public interface ActionComponentServiceListener {

    /**
     * Component registered.
     * 
     * @param service the service
     * @param component the component
     */
     void componentRegistered(ActionComponentService service, ActionComponent component);

    /**
     * Component unregistered.
     * 
     * @param service the service
     * @param component the component
     */
     void componentUnregistered(ActionComponentService service, ActionComponent component);
}
