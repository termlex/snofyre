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
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalFeatureEntity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link ClinicalFeatureEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 7:21:53 PM
 */

@Embeddable
@Entity(name = "Clinical_Feature")
@DiscriminatorValue("Clinical_Feature")
public class ClinicalFeatureEntityImpl extends AbstractClinicalObservationEntity implements ClinicalFeatureEntity{
	
	/** The value. */
	private Double value;
	
	/**
	 * Instantiates a new clinical feature entity impl.
	 */
	public ClinicalFeatureEntityImpl() {
		// empty constructor for persistence
	}

	/**
	 * Instantiates a new clinical feature entity impl.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param value the value
	 */
	public ClinicalFeatureEntityImpl(String name, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Double value) {
		this(name, Calendar.getInstance(), type, UUID.randomUUID(), expression, expressionType, value);
	}

	/**
	 * Instantiates a new clinical feature entity impl.
	 * 
	 * @param name the name
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param value the value
	 */
	public ClinicalFeatureEntityImpl(String name,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Double value) {
		this(name, Calendar.getInstance(), clinicallyRelevantTime, type, uuid, expression,
				expressionType, value);
	}

	/**
	 * Instantiates a new clinical feature entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param value the value
	 */
	public ClinicalFeatureEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Double value) {
		this(name, instantiationTime, clinicallyRelevantTime, type, UUID.randomUUID(),
				expression, expressionType, value);
	}

	/**
	 * Instantiates a new clinical feature entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param value the value
	 */
	public ClinicalFeatureEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Double value) {
		super(name, instantiationTime, clinicallyRelevantTime, type, uuid,
				expression, expressionType);
		this.value = value;
	}
	
    /**
     * Gets the value.
     * 
     * @return the value
     */
    @Column(name = "value")
	public Double getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 * 
	 * @param value the new value
	 */
	public void setValue(Double value) {
		this.value = value;
	}
}
