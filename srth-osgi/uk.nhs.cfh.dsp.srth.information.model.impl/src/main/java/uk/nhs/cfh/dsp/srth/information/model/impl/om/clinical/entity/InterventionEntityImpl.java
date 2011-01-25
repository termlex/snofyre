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

import org.hibernate.annotations.ForeignKey;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpressionType;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.AnatomicalLocationEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.InterventionEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.Laterality;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link InterventionEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 9:06:33 PM
 */

@Embeddable
@Entity(name = "Intervention_Entity")
@DiscriminatorValue("Intervention_Entity")
public class InterventionEntityImpl extends AbstractClinicalActivityEntity implements InterventionEntity{

	/** The target sites. */
	private Set<AnatomicalLocationEntity> targetSites = new HashSet<AnatomicalLocationEntity>();
	
	/** The laterality. */
	private Laterality laterality;
	
	/**
	 * Instantiates a new intervention entity impl.
	 */
	public InterventionEntityImpl() {
		// empty constructor for persistence
	}

	/**
	 * Instantiates a new intervention entity impl.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public InterventionEntityImpl(String name, Type type, Status status,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality) {
		this(name, Calendar.getInstance(), Calendar.getInstance(), type, status, expression, expressionType, laterality);
	}

	/**
	 * Instantiates a new intervention entity impl.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public InterventionEntityImpl(String name, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality) {
		this(name, type, Status.COMPLETED, expression, expressionType, laterality);
	}

	/**
	 * Instantiates a new intervention entity impl.
	 * 
	 * @param name the name
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public InterventionEntityImpl(String name, Calendar clinicallyRelevantTime,
			Type type, UUID uuid, Status status, ClinicalExpression expression,
			ClinicalExpressionType expressionType, Laterality laterality) {
		this(name, Calendar.getInstance(), clinicallyRelevantTime, type, uuid, status, expression,
				expressionType, laterality);
	}

	/**
	 * Instantiates a new intervention entity impl.
	 * 
	 * @param name the name
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public InterventionEntityImpl(String name, Calendar clinicallyRelevantTime,
			Type type, UUID uuid, ClinicalExpression expression,
			ClinicalExpressionType expressionType, Laterality laterality) {
		this(name, clinicallyRelevantTime, type, uuid, Status.COMPLETED, expression,
				expressionType, laterality);
	}

	/**
	 * Instantiates a new intervention entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public InterventionEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			Status status, ClinicalExpression expression,
			ClinicalExpressionType expressionType, Laterality laterality) {
		super(name, instantiationTime, clinicallyRelevantTime, type, uuid,
				status, expression, expressionType);
		this.laterality = laterality;
	}

	/**
	 * Instantiates a new intervention entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public InterventionEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, Status status,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality) {
		this(name, instantiationTime, clinicallyRelevantTime, type, UUID.randomUUID(), status,
				expression, expressionType, laterality);
	}

	/**
	 * Instantiates a new intervention entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public InterventionEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality) {
		this(name, instantiationTime, clinicallyRelevantTime, type, Status.COMPLETED,
				expression, expressionType, laterality);
	}

    /**
     * Gets the target sites.
     * 
     * @return the target sites
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AnatomicalLocationEntityImpl.class)
    @ForeignKey(name = "FK_INTV_SITE", inverseName = "FK_SITE_INTV")    
	public Set<AnatomicalLocationEntity> getTargetSites() {
		return targetSites;
	}
	
	/**
	 * Sets the target sites.
	 * 
	 * @param targetSites the new target sites
	 */
	public void setTargetSites(Set<AnatomicalLocationEntity> targetSites) {
		this.targetSites = targetSites;
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
     * Adds the target site.
     * 
     * @param targetSite the target site
     */
    @Transient
	public void addTargetSite(AnatomicalLocationEntity targetSite) {
		if(targetSite == null)
		{
			throw new IllegalArgumentException("Target site can not be null");
		}
		else
		{
			getTargetSites().add(targetSite);
		}
	}

    /**
     * Removes the target site.
     * 
     * @param targetSite the target site
     */
    @Transient
	public void removeTargetSite(AnatomicalLocationEntity targetSite) {
		if(targetSite == null)
		{
			throw new IllegalArgumentException("Target site can not be null");
		}
		else
		{
			getTargetSites().remove(targetSite);
		}
	}
}
