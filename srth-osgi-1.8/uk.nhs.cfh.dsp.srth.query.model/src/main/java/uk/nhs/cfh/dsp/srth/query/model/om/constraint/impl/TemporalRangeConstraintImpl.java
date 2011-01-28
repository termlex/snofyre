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
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.RangeFacetVocabulary;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TemporalRangeConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TemporalRangeFacetConstraint;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TemporalRangeConstraint}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 9, 2009 at 2:18:36 PM
 */

@Embeddable
@Entity(name = "TemporalRangeConstraint")
@DiscriminatorValue(value = "TemporalRangeConstraint")
public class TemporalRangeConstraintImpl extends AbstractTemporalConstraint implements TemporalRangeConstraint {

    /** The facets. */
    private Set<TemporalRangeFacetConstraint> facets = new HashSet<TemporalRangeFacetConstraint>();

    /**
     * Instantiates a new temporal range constraint impl.
     * 
     * @param facets the facets
     * @param dimensionVocabulary the dimension vocabulary
     */
    public TemporalRangeConstraintImpl(Collection<TemporalRangeFacetConstraint> facets,
                                       ConstraintDimensionVocabulary dimensionVocabulary) {
        super(dimensionVocabulary);
        this.facets = new HashSet<TemporalRangeFacetConstraint>(facets);
    }


    /**
     * Instantiates a new temporal range constraint impl.
     * 
     * @param facets the facets
     */
    public TemporalRangeConstraintImpl(Collection<TemporalRangeFacetConstraint> facets) {
        this(facets, ConstraintDimensionVocabulary.NULL);
    }

    /**
     * Instantiates a new temporal range constraint impl.
     */
    public TemporalRangeConstraintImpl() {
    }

    /**
     * Gets the facets.
     * 
     * @return the facets
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = TemporalRangeFacetConstraintImpl.class)
    public Set<TemporalRangeFacetConstraint> getFacets() {
        return facets;
    }

    /**
     * Sets the facets.
     * 
     * @param facets the new facets
     */
    public void setFacets(Set<TemporalRangeFacetConstraint> facets) {
        this.facets = facets;
    }

    /**
     * Gets the lower bound facet.
     * 
     * @return the lower bound facet
     */
    @Transient
    public TemporalRangeFacetConstraint getLowerBoundFacet(){

        TemporalRangeFacetConstraint lowerBoundFacet = null;
        for(TemporalRangeFacetConstraint fc : getFacets())
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
    public TemporalRangeFacetConstraint getUpperBoundFacet(){

        TemporalRangeFacetConstraint upperBoundFacet = null;
        for(TemporalRangeFacetConstraint fc : getFacets())
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

    /**
     * Gets the lower bound time.
     * 
     * @return the lower bound time
     */
    @Transient
    public Calendar getLowerBoundTime(){
        return getLowerBoundFacet().getTime();
    }

    /**
     * Gets the upper bound time.
     * 
     * @return the upper bound time
     */
    @Transient
    public Calendar getUpperBoundTime(){
        return getUpperBoundFacet().getTime();
    }
}
