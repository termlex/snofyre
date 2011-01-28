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
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.ClinicalObservationEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.InvestigationEntity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.InvestigationEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 8:51:02 PM
 */

@Embeddable
@Entity(name = "Investigation_Entity")
@DiscriminatorValue("Investigation_Entity")
public class InvestigationEntityImpl extends AbstractClinicalActivityEntity implements InvestigationEntity{

	/** The target sites. */
	private Set<AnatomicalLocationEntity> targetSites = new HashSet<AnatomicalLocationEntity>();
	
	/** The findings. */
	private Set<ClinicalObservationEntity> findings = new HashSet<ClinicalObservationEntity>();

	
	/**
	 * Instantiates a new investigation entity impl.
	 */
	public InvestigationEntityImpl() {
		// empty constructor for persistence
	}

	/**
	 * Instantiates a new investigation entity impl.
	 *
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 */
	public InvestigationEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, Status status,
			ClinicalExpression expression, ClinicalExpressionType expressionType) {
		this(name, instantiationTime, clinicallyRelevantTime, type, UUID.randomUUID(), status, expression, expressionType);
	}

	/**
	 * Instantiates a new investigation entity impl.
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
	public InvestigationEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			Status status, ClinicalExpression expression,
			ClinicalExpressionType expressionType) {
		super(name, instantiationTime, clinicallyRelevantTime, type, uuid, status, expression, expressionType);
	}

	/**
	 * Instantiates a new investigation entity impl.
	 *
	 * @param name the name
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param expression the expression
	 * @param expressionType the expression type
	 */
	public InvestigationEntityImpl(String name,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			ClinicalExpression expression, ClinicalExpressionType expressionType) {
		this(name, clinicallyRelevantTime, type, uuid, Status.COMPLETED, expression, expressionType);

	}

	/**
	 * Instantiates a new investigation entity impl.
	 *
	 * @param name the name
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 */
	public InvestigationEntityImpl(String name,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			Status status, ClinicalExpression expression,
			ClinicalExpressionType expressionType) {
		this(name, Calendar.getInstance(), clinicallyRelevantTime, type, uuid, status, expression,	expressionType);
	}

	/**
	 * Instantiates a new investigation entity impl.
	 *
	 * @param name the name
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 */
	public InvestigationEntityImpl(String name, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType) {
		this(name, type, Status.COMPLETED, expression, expressionType);
	}

	/**
	 * Instantiates a new investigation entity impl.
	 *
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param expression the expression
	 * @param expressionType the expression type
	 */
	public InvestigationEntityImpl(String name, Type type, Status status,
			ClinicalExpression expression, ClinicalExpressionType expressionType) {
		this(name, Calendar.getInstance(), Calendar.getInstance(), type, status, expression, expressionType);
	}

	/**
	 * Instantiates a new investigation entity impl.
	 *
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param expression the expression
	 * @param expressionType the expression type
	 */
	public InvestigationEntityImpl(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type,
			ClinicalExpression expression, ClinicalExpressionType expressionType) {
		this(name, instantiationTime, clinicallyRelevantTime, type, Status.COMPLETED,
				expression, expressionType);
	}

    /**
     * Gets the target sites.
     * 
     * @return the target sites
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AnatomicalLocationEntityImpl.class)
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
     * Gets the findings.
     * 
     * @return the findings
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractClinicalObservationEntity.class)
	public Set<ClinicalObservationEntity> getFindings() {
		return findings;
	}
	
	/**
	 * Sets the findings.
	 * 
	 * @param findings the new findings
	 */
	public void setFindings(Set<ClinicalObservationEntity> findings) {
		this.findings = findings;
	}

    /**
     * Adds the finding.
     * 
     * @param finding the finding
     */
    @Transient
	public void addFinding(ClinicalObservationEntity finding) {
		if(finding == null)
		{
			throw new IllegalArgumentException("Finding can not be null");
		}
		else
		{
			getFindings().add(finding);
		}
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
     * Removes the finding.
     * 
     * @param finding the finding
     */
    @Transient
	public void removeFinding(ClinicalObservationEntity finding) {
		if(finding == null)
		{
			throw new IllegalArgumentException("Finding can not be null");
		}
		else
		{
			getFindings().remove(finding);
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
