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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry;

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent;

// TODO: Auto-generated Javadoc
/**
 * An interface that corresponds to an {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry} of type {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent}.
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 4, 2009 at 10:01:00 AM
 */

public interface ClinicalEventEntry extends ClinicalEntry {

    /**
     * Gets the event.
     *
     * @return the event
     */
    ClinicalEvent getEvent();

    /**
     * Sets the event.
     *
     * @param event the new event
     */
    void setEvent (ClinicalEvent event);

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    void setStatus(ClinicalEvent.Status status);

    /**
     * Gets the status.
     *
     * @return the status
     */
    ClinicalEvent.Status getStatus();
}
