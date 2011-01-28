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
package uk.nhs.cfh.dsp.snomed.objectmodel;

// TODO: Auto-generated Javadoc
/**
 * A generic SNOMED CT component
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 19-Mar-2010 at 16:19:17.
 */
public interface SnomedComponent {

    /**
     * Gets the status.
     *
     * @return the status
     */
    ComponentStatus getStatus();

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    void setStatus(ComponentStatus status);
}
