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
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.AnatomicalLocationEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.Laterality;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link AnatomicalLocationEntity}
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 7:05:11 PM.
 */

@Embeddable
@Entity(name = "Anatomical_Location")
@DiscriminatorValue("Anatomical_Location")
public class AnatomicalLocationEntityImpl extends AbstractBoundClinicalEntity implements AnatomicalLocationEntity{

	/** The laterality. */
	private Laterality laterality;
	
	/** The parity. */
	private AnatomicalLocationEntity.Parity parity;
	
	/**
	 * Instantiates a new anatomical location entity impl.
	 */
	public AnatomicalLocationEntityImpl() {
		// empty constructor for persistence
	}

	/**
	 * Instantiates a new anatomical location entity impl.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 * @param parity the parity
	 */
	public AnatomicalLocationEntityImpl(String name, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality, Parity parity) {
		this(name, Calendar.getInstance(), Calendar.getInstance(), type, expression, expressionType, laterality, parity);
	}

	/**
	 * Instantiates a new anatomical location entity impl.
	 * 
	 * @param name the name
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 * @param parity the parity
	 */
	public AnatomicalLocationEntityImpl(String name,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality, Parity parity) {
		this(name, Calendar.getInstance(), clinicallyRelevantTime, type, uuid, expression,
				expressionType, laterality, parity);
	}

	/**
	 * Instantiates a new anatomical location entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 * @param parity the parity
	 */
	public AnatomicalLocationEntityImpl(String name,
			Calendar instantiationTime, Calendar clinicallyRelevantTime,
			Type type, ClinicalExpression expression,
			ClinicalExpressionType expressionType, Laterality laterality, Parity parity) {
		this(name, instantiationTime, clinicallyRelevantTime, type, UUID.randomUUID(),
				expression, expressionType, laterality, parity);
	}

	/**
	 * Instantiates a new anatomical location entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 * @param parity the parity
	 */
	public AnatomicalLocationEntityImpl(String name,
			Calendar instantiationTime, Calendar clinicallyRelevantTime,
			Type type, UUID uuid, ClinicalExpression expression,
			ClinicalExpressionType expressionType, Laterality laterality, Parity parity) {
		super(name, instantiationTime, clinicallyRelevantTime, type, uuid,
				expression, expressionType);
		this.laterality = laterality;
		this.parity = parity;
	}

    /**
     * Gets the laterality.
     * 
     * @return the laterality
     */
    @Enumerated(EnumType.ORDINAL)
	public Laterality getLaterality() {
		return laterality;
	}
	
	/**
	 * Sets the laterality.
	 * 
	 * @param laterality the new laterality
	 */
	public void setLaterality(Laterality laterality) {
		this.laterality = laterality;
	}

    /**
     * Gets the parity.
     * 
     * @return the parity
     */
    @Enumerated(EnumType.ORDINAL)
	public AnatomicalLocationEntity.Parity getParity() {
		return parity;
	}
	
	/**
	 * Sets the parity.
	 * 
	 * @param parity the new parity
	 */
	public void setParity(AnatomicalLocationEntity.Parity parity) {
		this.parity = parity;
	}
	
	
}
