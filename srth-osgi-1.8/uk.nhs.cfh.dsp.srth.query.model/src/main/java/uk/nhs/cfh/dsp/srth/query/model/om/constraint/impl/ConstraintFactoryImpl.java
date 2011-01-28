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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintFactory}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 1:42:02 PM
 */
public class ConstraintFactoryImpl implements ConstraintFactory {

    /**
     * Gets the data constant constraint.
     * 
     * @param number the number
     * @param name the name
     * 
     * @return the data constant constraint
     */
    public DataConstantConstraint getDataConstantConstraint(Number number , String name){
        DataConstantConstraint constraint = new DataConstantConstraintImpl(number, ConstraintDimensionVocabulary.NULL);
        constraint.setName(name);
        
        return constraint;
    }

    /**
     * Gets the data range facet constraint.
     * 
     * @param number the number
     * @param facet the facet
     * @param name the name
     * 
     * @return the data range facet constraint
     */
    public DataRangeFacetConstraint getDataRangeFacetConstraint(Number number,
                                                                RangeFacetVocabulary facet,
                                                                String name){
        DataRangeFacetConstraint constraint = new DataRangeFacetConstraintImpl(number, facet);
        constraint.setName(name);

        return constraint;
    }

    /**
     * Gets the data range constraint.
     * 
     * @param facets the facets
     * @param name the name
     * 
     * @return the data range constraint
     */
    public DataRangeConstraint getDataRangeConstraint(Collection<DataRangeFacetConstraint> facets, String name){
        DataRangeConstraint constraint = new DataRangeConstraintImpl(facets);
        constraint.setName(name);

        return constraint;
    }

    /**
     * Gets the data range constraint.
     * 
     * @param lowerBound the lower bound
     * @param lowerFacet the lower facet
     * @param upperBound the upper bound
     * @param upperFacet the upper facet
     * @param name the name
     * 
     * @return the data range constraint
     */
    public DataRangeConstraint getDataRangeConstraint(Number lowerBound, RangeFacetVocabulary lowerFacet,
                                                      Number upperBound, RangeFacetVocabulary upperFacet,
                                                      String name){
        Collection<DataRangeFacetConstraint> facets = new ArrayList<DataRangeFacetConstraint>();
        facets.add(getDataRangeFacetConstraint(lowerBound, lowerFacet, name));
        facets.add(getDataRangeFacetConstraint(upperBound, upperFacet, name));

        return getDataRangeConstraint(facets, name);
    }

    /**
     * Gets the temporal constant constraint.
     * 
     * @param calendar the calendar
     * @param name the name
     * 
     * @return the temporal constant constraint
     */
    public TemporalConstantConstraint getTemporalConstantConstraint(Calendar calendar, String name){

        TemporalConstantConstraint constraint = new TemporalConstantConstraintImpl(calendar,
                            ConstraintDimensionVocabulary.NULL);
        constraint.setName(name);

        return constraint;
    }

    /**
     * Gets the temporal range facet constraint.
     * 
     * @param time the time
     * @param event the event
     * @param facet the facet
     * @param name the name
     * 
     * @return the temporal range facet constraint
     */
    public TemporalRangeFacetConstraint getTemporalRangeFacetConstraint(Calendar time, AnchorEvent event,
                                                                        RangeFacetVocabulary facet, String name){
        TemporalRangeFacetConstraint constraint =
                new TemporalRangeFacetConstraintImpl(time, event, ConstraintDimensionVocabulary.NULL, facet);
        constraint.setName(name);

        return constraint;
    }

    /**
     * Gets the temporal range constraint.
     * 
     * @param facets the facets
     * @param name the name
     * 
     * @return the temporal range constraint
     */
    public TemporalRangeConstraint getTemporalRangeConstraint(Collection<TemporalRangeFacetConstraint> facets, String name){
        TemporalRangeConstraint constraint = new TemporalRangeConstraintImpl(facets);
        constraint.setName(name);

        return constraint;
    }

    /**
     * Gets the temporal range constraint.
     * 
     * @param lowerBound the lower bound
     * @param lowerFacet the lower facet
     * @param lowerBoundEvent the lower bound event
     * @param upperBound the upper bound
     * @param upperFacet the upper facet
     * @param upperBoundEvent the upper bound event
     * @param name the name
     * 
     * @return the temporal range constraint
     */
    public TemporalRangeConstraint getTemporalRangeConstraint(Calendar lowerBound, RangeFacetVocabulary lowerFacet,
                                                              AnchorEvent lowerBoundEvent,
                                                              Calendar upperBound, RangeFacetVocabulary upperFacet,
                                                              AnchorEvent upperBoundEvent,
                                                              String name){
        Collection<TemporalRangeFacetConstraint> facets = new ArrayList<TemporalRangeFacetConstraint>();
        facets.add(getTemporalRangeFacetConstraint(lowerBound, lowerBoundEvent, lowerFacet, name));
        facets.add(getTemporalRangeFacetConstraint(upperBound, upperBoundEvent, upperFacet, name));

        return getTemporalRangeConstraint(facets, name);
    }

    /**
     * Gets the terminology constraint.
     * 
     * @param expression the expression
     * @param name the name
     * 
     * @return the terminology constraint
     */
    public TerminologyConstraint getTerminologyConstraint(CloseToUserExpression expression, String name){
        TerminologyConstraint constraint = new TerminologyConstraintImpl(expression);
        constraint.setName(name);

        return constraint;
    }

    /**
     * Gets the terminology constraint.
     * 
     * @param concept the concept
     * @param name the name
     * 
     * @return the terminology constraint
     */
    public TerminologyConstraint getTerminologyConstraint(SnomedConcept concept, String name){
        return getTerminologyConstraint(new CloseToUserExpressionImpl(concept), name);
    }
}
