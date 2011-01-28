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

import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintDimensionVocabulary;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataRangeConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataRangeFacetConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.RangeFacetVocabulary;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataRangeConstraint}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 7, 2009 at 4:02:48 PM
 * <br>
 */

@Embeddable
@Entity(name = "DataRangeConstraint")
@DiscriminatorValue(value = "DataRangeConstraint")
public class DataRangeConstraintImpl extends AbstractDataConstraint implements DataRangeConstraint {

	/** The facets. */
	private Set<DataRangeFacetConstraint> facets = new HashSet<DataRangeFacetConstraint>();

    /**
     * Instantiates a new data range constraint impl.
     * 
     * @param facets the facets
     * @param dimensionVocabulary the dimension vocabulary
     */
    public DataRangeConstraintImpl(Collection<DataRangeFacetConstraint> facets,
			ConstraintDimensionVocabulary dimensionVocabulary) {
        super(dimensionVocabulary);
        this.facets = new HashSet<DataRangeFacetConstraint>(facets);
	}

	/**
	 * Instantiates a new data range constraint impl.
	 * 
	 * @param facets the facets
	 */
	public DataRangeConstraintImpl(Collection<DataRangeFacetConstraint> facets) {
        this(facets, ConstraintDimensionVocabulary.NULL);
	}

    /**
     * Instantiates a new data range constraint impl.
     */
    public DataRangeConstraintImpl() {
    }

    /**
     * Gets the facets.
     * 
     * @return the facets
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = DataRangeFacetConstraintImpl.class)
    public Set<DataRangeFacetConstraint> getFacets() {
		return facets;
	}

	/**
	 * Sets the facets.
	 * 
	 * @param facets the new facets
	 */
	public void setFacets(Set<DataRangeFacetConstraint> facets) {
		this.facets = facets;
	}
	
	/**
	 * Gets the upper bound.
	 * 
	 * @return the upper bound
	 */
    @Transient
	public double getUpperBound(){
		return getUpperBoundFacet().asDouble();
	}
	
	/**
	 * Gets the lower bound.
	 * 
	 * @return the lower bound
	 */
    @Transient
	public double getLowerBound(){
		return getLowerBoundFacet().asDouble();
	}
	
	/**
	 * Gets the lower bound facet.
	 * 
	 * @return the lower bound facet
	 */
    @Transient
	public DataRangeFacetConstraint getLowerBoundFacet(){
		
		DataRangeFacetConstraint lowerBoundFacet = null;
		for(DataRangeFacetConstraint fc : getFacets())
		{
			if(fc.getFacet() == RangeFacetVocabulary.MIN_EXCLUSIVE ||
					fc.getFacet() == RangeFacetVocabulary.MIN_INCLUSIVE)
			{
				
				lowerBoundFacet = fc;
				break;
			}
		}
		
		return lowerBoundFacet;
	}
	
	/**
	 * Gets the upper bound facet.
	 * 
	 * @return the upper bound facet
	 */
    @Transient
	public DataRangeFacetConstraint getUpperBoundFacet(){
		
		DataRangeFacetConstraint upperBoundFacet = null;
		for(DataRangeFacetConstraint fc : getFacets())
		{
			if(fc.getFacet() == RangeFacetVocabulary.MAX_EXCLUSIVE ||
					fc.getFacet() == RangeFacetVocabulary.MAX_INCLUSIVE)
			{
				upperBoundFacet = fc;
				break;
			}
		}
		
		return upperBoundFacet;
	}
}
