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
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.MedicationEntity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.MedicationEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 9:13:12 PM
 */

@Embeddable
@Entity(name = "Medication_Entity")
@DiscriminatorValue("Medication_Entity")
public class MedicationEntityImpl extends AbstractClinicalActivityEntity implements MedicationEntity{

	/** The dose. */
	private Double dose;
	
	/** The dose units. */
	private String doseUnits;
	
	/**
	 * Instantiates a new medication entity impl.
	 */
	public MedicationEntityImpl() {
		// empty constructor for persistence
	}

	/**
	 * Instantiates a new medication entity impl.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param dose the dose
	 * @param doseUnits the dose units
	 */
	public MedicationEntityImpl(String name, Type type, Status status,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Double dose, String doseUnits) {
		this(name, Calendar.getInstance(), Calendar.getInstance(), type, status, expression, expressionType, dose, doseUnits);
	}

	/**
	 * Instantiates a new medication entity impl.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param dose the dose
	 * @param doseUnits the dose units
	 */
	public MedicationEntityImpl(String name, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Double dose, String doseUnits) {
		this(name, type, Status.COMPLETED, expression, expressionType, dose, doseUnits);
	}

	/**
	 * Instantiates a new medication entity impl.
	 * 
	 * @param name the name
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param dose the dose
	 * @param doseUnits the dose units
	 */
	public MedicationEntityImpl(String name, Calendar clinicallyRelevantTime,
			Type type, UUID uuid, Status status, ClinicalExpression expression,
			ClinicalExpressionType expressionType, Double dose, String doseUnits) {
		this(name, Calendar.getInstance(), clinicallyRelevantTime, type, uuid, status, expression,
				expressionType, dose, doseUnits);
	}

	/**
	 * Instantiates a new medication entity impl.
	 * 
	 * @param name the name
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param dose the dose
	 * @param doseUnits the dose units
	 */
	public MedicationEntityImpl(String name, Calendar clinicallyRelevantTime,
			Type type, UUID uuid, ClinicalExpression expression,
			ClinicalExpressionType expressionType, Double dose, String doseUnits) {
		this(name, clinicallyRelevantTime, type, uuid, Status.COMPLETED, expression,
				expressionType, dose, doseUnits);
	}

	/**
	 * Instantiates a new medication entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param dose the dose
	 * @param doseUnits the dose units
	 */
	public MedicationEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			Status status, ClinicalExpression expression,
			ClinicalExpressionType expressionType, Double dose, String doseUnits) {
		super(name, instantiationTime, clinicallyRelevantTime, type, uuid,
				status, expression, expressionType);
		this.dose = dose;
		this.doseUnits = doseUnits;
	}

	/**
	 * Instantiates a new medication entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param dose the dose
	 * @param doseUnits the dose units
	 */
	public MedicationEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, Status status,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Double dose, String doseUnits) {
		this(name, instantiationTime, clinicallyRelevantTime, type, UUID.randomUUID(), status,
				expression, expressionType, dose, doseUnits);
	}

	/**
	 * Instantiates a new medication entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param dose the dose
	 * @param doseUnits the dose units
	 */
	public MedicationEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Double dose, String doseUnits) {
		this(name, instantiationTime, clinicallyRelevantTime, type, Status.COMPLETED,
				expression, expressionType, dose, doseUnits);
	}

    /**
     * Gets the dose.
     * 
     * @return the dose
     */
    @Column(name = "dose")
	public Double getDose() {
		return dose;
	}
	
	/**
	 * Sets the dose.
	 * 
	 * @param dose the new dose
	 */
	public void setDose(Double dose) {
		this.dose = dose;
	}

    /**
     * Gets the dose units.
     * 
     * @return the dose units
     */
    @Column(name = "dose_units")
	public String getDoseUnits() {
		return doseUnits;
	}
	
	/**
	 * Sets the dose units.
	 * 
	 * @param doseUnits the new dose units
	 */
	public void setDoseUnits(String doseUnits) {
		this.doseUnits = doseUnits;
	}
}
