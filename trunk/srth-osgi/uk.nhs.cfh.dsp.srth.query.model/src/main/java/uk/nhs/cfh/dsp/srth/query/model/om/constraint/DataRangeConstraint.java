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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataConstraint} that uses a range of numbers
 * as its value set.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 1:01:48 PM
 */
public interface DataRangeConstraint extends DataConstraint {

    /**
     * Gets the facets.
     * 
     * @return the facets
     */
    Set<DataRangeFacetConstraint> getFacets();

    /**
     * Sets the facets.
     * 
     * @param facets the new facets
     */
    void setFacets(Set<DataRangeFacetConstraint> facets);

    /**
     * Gets the upper bound.
     * 
     * @return the upper bound
     */
    double getUpperBound();

    /**
     * Gets the lower bound.
     * 
     * @return the lower bound
     */
    double getLowerBound();

    /**
     * Gets the lower bound facet.
     * 
     * @return the lower bound facet
     */
    DataRangeFacetConstraint getLowerBoundFacet();

    /**
     * Gets the upper bound facet.
     * 
     * @return the upper bound facet
     */
    DataRangeFacetConstraint getUpperBoundFacet();
}
