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
package uk.nhs.cfh.dsp.srth.query.model.om;

import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A functional unit of a query that contains a given set of.
 *
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint}s. It always has one included constraint
 * that is guaranteed to be a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint}.
 * It might have excluded terms that are types of {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint}s
 * and additional constraints that might be of types
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataConstraint} or
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TemporalConstraint}
 * 
 * <p/>
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 8:03:53 PM
 */
public interface QueryComponentExpression extends QueryExpression {

    /**
     * Gets the included constraint.
     * 
     * @return the included constraint
     */
    TerminologyConstraint getIncludedConstraint();

    /**
     * Sets the included constraint.
     * 
     * @param includedConstraint the new included constraint
     */
    void setIncludedConstraint(TerminologyConstraint includedConstraint);

    /**
     * Gets the excluded constraints.
     * 
     * @return the excluded constraints
     */
    Set<TerminologyConstraint> getExcludedConstraints();

    /**
     * Sets the excluded constraints.
     * 
     * @param excludedConstraints the new excluded constraints
     */
    void setExcludedConstraints(Set<TerminologyConstraint> excludedConstraints);

    /**
     * Gets the additional constraints.
     * 
     * @return the additional constraints
     */
    Set<Constraint> getAdditionalConstraints();

    /**
     * Sets the additional constraints.
     * 
     * @param additionalConstraints the new additional constraints
     */
    void setAdditionalConstraints(Set<Constraint> additionalConstraints);

    /**
     * Adds the excluded constraint.
     * 
     * @param constraint the constraint
     */
    void addExcludedConstraint(TerminologyConstraint constraint);

    /**
     * Removes the excluded constraint.
     * 
     * @param constraint the constraint
     */
    void removeExcludedConstraint(TerminologyConstraint constraint);

    /**
     * Adds the additional constraint.
     * 
     * @param constraint the constraint
     */
    void addAdditionalConstraint(Constraint constraint);

    /**
     * Removes the additional constraint.
     * 
     * @param constraint the constraint
     */
    void removeAdditionalConstraint(Constraint constraint);
}
