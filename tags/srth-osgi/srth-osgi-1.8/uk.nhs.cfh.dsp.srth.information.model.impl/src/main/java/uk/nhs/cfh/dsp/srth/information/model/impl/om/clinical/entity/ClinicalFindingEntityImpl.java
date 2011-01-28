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
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalFindingEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.Laterality;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalFindingEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 8:40:54 PM
 */

@Embeddable
@Entity(name = "Clinical_Finding")
@DiscriminatorValue("Clinical_Finding")
public class ClinicalFindingEntityImpl extends AbstractClinicalObservationEntity implements ClinicalFindingEntity{

	/** The laterality. */
	private Laterality laterality;
	
	/** The finding sites. */
	private Set<AnatomicalLocationEntity> findingSites = new HashSet<AnatomicalLocationEntity>();
	
	/**
	 * Instantiates a new clinical finding entity impl.
	 */
	public ClinicalFindingEntityImpl() {
		// empty constructor for persistence
	}

	/**
	 * Instantiates a new clinical finding entity impl.
	 * 
	 * @param name the name
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public ClinicalFindingEntityImpl(String name,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality) {
        this(name, Type.FINDING, expression, expressionType, laterality);
	}

	/**
	 * Instantiates a new clinical finding entity impl.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public ClinicalFindingEntityImpl(String name, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality) {
        this(name, Calendar.getInstance(), type, expression, expressionType, laterality);
	}

	/**
	 * Instantiates a new clinical finding entity impl.
	 * 
	 * @param name the name
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public ClinicalFindingEntityImpl(String name,
			Calendar clinicallyRelevantTime, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality) {
        this(name, Calendar.getInstance(), clinicallyRelevantTime, type, expression,  expressionType, laterality);
	}

	/**
	 * Instantiates a new clinical finding entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public ClinicalFindingEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality) {
        this(name, instantiationTime, clinicallyRelevantTime, type, UUID.randomUUID(), expression,
                expressionType, laterality);
	}

	/**
	 * Instantiates a new clinical finding entity impl.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param expression the expression
	 * @param expressionType the expression type
	 * @param laterality the laterality
	 */
	public ClinicalFindingEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			ClinicalExpression expression, ClinicalExpressionType expressionType,
			Laterality laterality) {
		super(name, instantiationTime, clinicallyRelevantTime, type, uuid,
				expression, expressionType);
		this.laterality = laterality;
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
     * Gets the finding sites.
     * 
     * @return the finding sites
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AnatomicalLocationEntityImpl.class)
    @ForeignKey(name = "FK_FIN_SITE", inverseName = "FK_SITE_FIN")
	public Set<AnatomicalLocationEntity> getFindingSites() {
		return findingSites;
	}
	
	/**
	 * Sets the finding sites.
	 * 
	 * @param findingSites the new finding sites
	 */
	public void setFindingSites(Set<AnatomicalLocationEntity> findingSites) {
		this.findingSites = findingSites;
	}

    /**
     * Adds the finding site.
     * 
     * @param site the site
     */
    @Transient
	public void addFindingSite(AnatomicalLocationEntity site){
		if(site == null)
		{
			throw new IllegalArgumentException("Site can not be null");
		}
		else
		{
			getFindingSites().add(site);
		}
	}

    /**
     * Removes the finding site.
     * 
     * @param site the site
     */
    @Transient
	public void removeFindingSite(AnatomicalLocationEntity site){
		if(site == null)
		{
			throw new IllegalArgumentException("Site can not be null");
		}
		else
		{
			getFindingSites().remove(site);
		}
	}
}
