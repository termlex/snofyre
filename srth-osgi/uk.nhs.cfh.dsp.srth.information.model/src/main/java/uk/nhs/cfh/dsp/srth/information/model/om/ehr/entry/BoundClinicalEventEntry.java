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

/**
 * A type of {@link BoundClinicalEntry} that takes a.
 *
 * {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.BoundClinicalEvent}
 * as the type of  {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity} it holds.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 6, 2009 at 2:33:10 PM
 */

public interface BoundClinicalEventEntry extends ClinicalEventEntry, BoundClinicalEntry{

    /*
     * an empty interface. The abstract implementation will check if
     * the clinical entry passed is bound.
     */
}
