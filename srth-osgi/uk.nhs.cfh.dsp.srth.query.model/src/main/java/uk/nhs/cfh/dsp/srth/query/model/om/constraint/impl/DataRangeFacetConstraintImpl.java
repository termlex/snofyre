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

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataRangeFacetConstraint}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 7, 2009 at 3:56:46 PM
 * <br>
 */

@Embeddable
@Entity(name = "DataFacetConstraint")
@DiscriminatorValue(value = "DataFacetConstraint")
public class DataRangeFacetConstraintImpl extends AbstractDataConstraint implements DataRangeFacetConstraint {

    /** The facet. */
    private RangeFacetVocabulary facet;
    
    /** The value. */
    private Number value;

    /**
     * Instantiates a new data range facet constraint impl.
     * 
     * @param value the value
     * @param dimensionVocabulary the dimension vocabulary
     * @param facet the facet
     */
    public DataRangeFacetConstraintImpl(Number value,
                                        ConstraintDimensionVocabulary dimensionVocabulary,
                                        RangeFacetVocabulary facet) {
        super(new ConstraintValue<Number>(value), dimensionVocabulary);
        this.facet = facet;
        this.value = value;
    }

    /**
     * Instantiates a new data range facet constraint impl.
     * 
     * @param value the value
     * @param facet the facet
     */
    public DataRangeFacetConstraintImpl(Number value,
                                        RangeFacetVocabulary facet) {
        super(new ConstraintValue<Number>(value));
        this.facet = facet;
        this.value = value;
    }

    /**
     * Instantiates a new data range facet constraint impl.
     */
    public DataRangeFacetConstraintImpl() {
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
     * As double.
     * 
     * @return the double
     */
    @Transient
    public double asDouble(){
        return (Double) value.doubleValue();
    }

    /**
     * As int.
     * 
     * @return the int
     */
    @Transient
    public int asInt(){
        return (Integer) value.intValue();
    }
}
