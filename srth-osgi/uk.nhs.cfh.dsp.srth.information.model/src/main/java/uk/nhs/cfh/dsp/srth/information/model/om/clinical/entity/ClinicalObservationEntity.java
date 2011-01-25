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

package uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity;

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * An interface that represents a generic observation with the domain.
 * This can either be a {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalFeatureEntity} or
 * a {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalFindingEntity}.
 * It can also have indications which are types of {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity}
 * objects.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 4, 2009 at 7:41:57 PM
 */

public interface ClinicalObservationEntity extends BoundClinicalEntity{
    
    /**
     * Gets the indications.
     *
     * @return the indications
     */
    Collection<ClinicalEntity> getIndications();

    /**
     * Sets the indications.
     *
     * @param indications the new indications
     */
    void setIndications(Collection<ClinicalEntity> indications);
}
