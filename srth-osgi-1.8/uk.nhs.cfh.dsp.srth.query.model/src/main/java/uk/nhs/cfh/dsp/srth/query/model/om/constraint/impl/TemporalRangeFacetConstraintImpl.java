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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl;

import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;

import javax.persistence.*;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TemporalRangeFacetConstraint}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 9, 2009 at 10:15:47 AM
 * <br>
 */

@Embeddable
@Entity(name = "TemporalRangeFacetConstraint")
@DiscriminatorValue(value = "TemporalRangeFacetConstraint")
public class TemporalRangeFacetConstraintImpl extends AbstractTemporalConstraint implements TemporalRangeFacetConstraint {

	/** The facet. */
	private RangeFacetVocabulary facet;
	
	/** The anchor event. */
	private AnchorEvent anchorEvent;

	/**
	 * Instantiates a new temporal range facet constraint impl.
	 * 
	 * @param value the value
	 * @param anchorEvent the anchor event
	 * @param dimensionVocabulary the dimension vocabulary
	 * @param facet the facet
	 */
	public TemporalRangeFacetConstraintImpl(ConstraintValue<?> value, AnchorEvent anchorEvent,
			ConstraintDimensionVocabulary dimensionVocabulary, RangeFacetVocabulary facet) {
		super(value, dimensionVocabulary);
		this.facet = facet;
		this.anchorEvent = anchorEvent;
	}

	/**
	 * Instantiates a new temporal range facet constraint impl.
	 * 
	 * @param date the date
	 * @param anchorEvent the anchor event
	 * @param dimensionVocabulary the dimension vocabulary
	 * @param facet the facet
	 */
	public TemporalRangeFacetConstraintImpl(Calendar date, AnchorEvent anchorEvent,
			ConstraintDimensionVocabulary dimensionVocabulary, RangeFacetVocabulary facet) {
		super(new ConstraintValue<Calendar>(date), dimensionVocabulary);
		this.facet = facet;
		this.anchorEvent = anchorEvent;
	}
	
	/**
	 * Instantiates a new temporal range facet constraint impl.
	 * 
	 * @param number the number
	 * @param anchorEvent the anchor event
	 * @param dimensionVocabulary the dimension vocabulary
	 * @param facet the facet
	 */
	public TemporalRangeFacetConstraintImpl(Number number, AnchorEvent anchorEvent,
			ConstraintDimensionVocabulary dimensionVocabulary, RangeFacetVocabulary facet) {
		super(new ConstraintValue<Number>(number), dimensionVocabulary);
		this.facet = facet;
		this.anchorEvent = anchorEvent;
	}
	
	/**
	 * Instantiates a new temporal range facet constraint impl.
	 * 
	 * @param number the number
	 * @param date the date
	 * @param dimensionVocabulary the dimension vocabulary
	 * @param facet the facet
	 */
	public TemporalRangeFacetConstraintImpl(Number number, Calendar date,
			ConstraintDimensionVocabulary dimensionVocabulary, RangeFacetVocabulary facet) {
		super(new ConstraintValue<Number>(number), dimensionVocabulary);
		this.facet = facet;
		this.anchorEvent = new AnchorEvent(date);
	}
	
	/**
	 * Instantiates a new temporal range facet constraint impl.
	 * 
	 * @param number the number
	 * @param dimensionVocabulary the dimension vocabulary
	 * @param facet the facet
	 */
	public TemporalRangeFacetConstraintImpl(Number number,
			ConstraintDimensionVocabulary dimensionVocabulary, RangeFacetVocabulary facet) {
		super(new ConstraintValue<Number>(number), dimensionVocabulary);
		this.facet = facet;
		this.anchorEvent = new AnchorEvent();
	}

    /**
     * Instantiates a new temporal range facet constraint impl.
     */
    public TemporalRangeFacetConstraintImpl() {
    }

    /**
     * Gets the facet.
     * 
     * @return the facet
     */
    @Enumerated(EnumType.STRING)
    public RangeFacetVocabulary getFacet() {
		return facet;
	}

	/**
	 * Sets the facet.
	 * 
	 * @param facet the new facet
	 */
	public void setFacet(RangeFacetVocabulary facet) {
		this.facet = facet;
	}

	/**
	 * Gets the anchor event.
	 * 
	 * @return the anchor event
	 */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AnchorEvent.class)
	public AnchorEvent getAnchorEvent() {
		return anchorEvent;
	}

	/**
	 * Sets the anchor event.
	 * 
	 * @param anchorEvent the new anchor event
	 */
	public void setAnchorEvent(AnchorEvent anchorEvent) {
		this.anchorEvent = anchorEvent;
	}
}
