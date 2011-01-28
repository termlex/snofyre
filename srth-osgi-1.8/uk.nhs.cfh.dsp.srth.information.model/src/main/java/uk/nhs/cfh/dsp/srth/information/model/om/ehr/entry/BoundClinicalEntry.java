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

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.BoundClinicalEntity;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A type of {@link ClinicalEntry} that takes a {@link BoundClinicalEntity}
 * as the type of {@link ClinicalEntity} it holds.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 6, 2009 at 2:29:35 PM
 */

public interface BoundClinicalEntry extends ClinicalEntry{

    /**
     * Gets the canonical form.
     * 
     * @return the canonical form
     */
    String getCanonicalForm();

    /**
     * Sets the canonical form.
     * 
     * @param canonicalForm the new canonical form
     */
    void setCanonicalForm(String canonicalForm);

    /**
     * Gets the uuid.
     *
     * @return the uuid
     */
    UUID getUuid();

    void setUuid(UUID uuid);
}
