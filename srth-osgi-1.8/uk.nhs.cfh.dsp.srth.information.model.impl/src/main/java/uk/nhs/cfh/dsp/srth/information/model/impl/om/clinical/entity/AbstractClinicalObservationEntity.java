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
/**
 * 
 */
package uk.nhs.cfh.dsp.srth.information.model.impl.om.clinical.entity;

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpressionType;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalObservationEntity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link ClinicalObservationEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 7:08:51 PM
 */

@MappedSuperclass
@Entity(name = "Clinical_Observation")
@DiscriminatorValue("Clinical_Observation")
public class AbstractClinicalObservationEntity extends AbstractBoundClinicalEntity implements ClinicalObservationEntity{

    /** The indications. */
    private Collection<ClinicalEntity> indications = new HashSet<ClinicalEntity>();
    /**
     * Instantiates a new clinical observation entity impl.
     */
    public AbstractClinicalObservationEntity() {
        // empty constructor that just calls super
    }

    /**
     * Instantiates a new abstract clinical observation entity.
     *
     * @param name the name
     * @param instantiationTime the instantiation time
     * @param clinicallyRelevantTime the clinically relevant time
     * @param type the type
     * @param uuid the uuid
     * @param expression the expression
     * @param expressionType the expression type
     */
    public AbstractClinicalObservationEntity(String name, Calendar instantiationTime, Calendar clinicallyRelevantTime,
                                             Type type, UUID uuid, ClinicalExpression expression,
                                             ClinicalExpressionType expressionType) {
        super(name, instantiationTime, clinicallyRelevantTime, type, uuid, expression, expressionType);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalObservationEntity#getIndications()
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractClinicalEntity.class)
    public Collection<ClinicalEntity> getIndications() {
        return indications;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalObservationEntity#setIndications(java.util.Collection)
     */
    public void setIndications(Collection<ClinicalEntity> indications) {
        this.indications = indications;
    }
}
