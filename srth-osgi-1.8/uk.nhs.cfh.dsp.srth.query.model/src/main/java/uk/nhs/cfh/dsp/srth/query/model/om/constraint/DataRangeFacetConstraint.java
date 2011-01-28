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

// TODO: Auto-generated Javadoc
/**
 * A {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint} object that uses a
 * data range facet for its value set.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 12:58:19 PM
 */
public interface DataRangeFacetConstraint extends DataConstraint {
    
    /**
     * Gets the facet.
     * 
     * @return the facet
     */
    RangeFacetVocabulary getFacet();

    /**
     * Sets the facet.
     * 
     * @param facet the new facet
     */
    void setFacet(RangeFacetVocabulary facet);

    /**
     * As double.
     * 
     * @return the double
     */
    double asDouble();

    /**
     * As int.
     * 
     * @return the int
     */
    int asInt();
}
