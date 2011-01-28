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

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An interface that represents any action that is carried out or ordered
 * by a healthcare professional. It includes investigations, procedures and
 * medication administrations.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 4, 2009 at 8:04:38 PM
 */

public interface ClinicalActivityEntity extends BoundClinicalEvent{

    /**
     * Gets the indications.
     *
     * @return the indications
     */
    Set<BoundClinicalEntity> getIndications();

    /**
     * Sets the indications.
     *
     * @param indications the new indications
     */
    void setIndications(Set<BoundClinicalEntity> indications);

    /**
     * Adds the indication.
     *
     * @param indication the indication
     */
    void addIndication(BoundClinicalEntity indication);

    /**
     * Removes the indication.
     *
     * @param indication the indication
     */
    void removeIndication(BoundClinicalEntity indication);
}
