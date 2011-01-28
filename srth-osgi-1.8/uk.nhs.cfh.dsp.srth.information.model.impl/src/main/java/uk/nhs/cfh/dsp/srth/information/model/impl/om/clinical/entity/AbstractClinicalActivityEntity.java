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

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpressionType;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.BoundClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalActivityEntity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link ClinicalActivityEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 8:52:45 PM
 */
@MappedSuperclass
@DiscriminatorValue("Clinical_Activity_Event")
public abstract class AbstractClinicalActivityEntity extends AbstractBoundClinicalEvent implements ClinicalActivityEntity{

	/** The indications. */
	private Set<BoundClinicalEntity> indications = new HashSet<BoundClinicalEntity>();
	
	/**
	 * Instantiates a new abstract clinical activity entity.
	 */
	public AbstractClinicalActivityEntity() {
		// empty constructor for persistence
	}

	/**
	 * Instantiates a new abstract clinical activity entity.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 */
	public AbstractClinicalActivityEntity(String name,
			Calendar instantiationTime, Calendar clinicallyRelevantTime,
			Type type, UUID uuid, Status status, ClinicalExpression expression,
			ClinicalExpressionType expressionType) {
		super(name, instantiationTime, clinicallyRelevantTime, type, uuid, status, expression, expressionType);
	}

    /**
     * Gets the indications.
     * 
     * @return the indications
     */
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER, targetEntity = AbstractClinicalEntity.class)
	public Set<BoundClinicalEntity> getIndications() {
		return indications;
	}

	/**
	 * Sets the indications.
	 * 
	 * @param indications the new indications
	 */
	public void setIndications(Set<BoundClinicalEntity> indications) {
		this.indications = indications;
	}

    /**
     * Adds the indication.
     * 
     * @param indication the indication
     */
    @Transient
	public void addIndication(BoundClinicalEntity indication){
		if(indication == null)
		{
			throw new IllegalArgumentException("Indication passed cant be null");
		}
		else
		{
			getIndications().add(indication);
		}
	}

    /**
     * Removes the indication.
     * 
     * @param indication the indication
     */
    @Transient
	public void removeIndication(BoundClinicalEntity indication){
		if(indication == null)
		{
			throw new IllegalArgumentException("Indication passed cant be null");
		}
		else
		{
			getIndications().remove(indication);
		}
	}
}
