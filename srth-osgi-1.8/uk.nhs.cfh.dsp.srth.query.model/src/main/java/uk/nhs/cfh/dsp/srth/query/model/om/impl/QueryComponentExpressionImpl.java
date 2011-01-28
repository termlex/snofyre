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
/*
 * 
 */
package uk.nhs.cfh.dsp.srth.query.model.om.impl;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryComponentExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.AbstractConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.TerminologyConstraintImpl;

import javax.persistence.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryComponentExpression}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 8:03:07 PM
 */

@Indexed
@Entity(name = "QueryComponentExpression")
@DiscriminatorValue(value = "QueryComponentExpression")
@Embeddable
public class QueryComponentExpressionImpl extends AbstractQueryExpression implements QueryComponentExpression {

    /** The included constraint. */
    private TerminologyConstraint includedConstraint ;
    
    /** The excluded constraints. */
    private Set<TerminologyConstraint> excludedConstraints = new HashSet<TerminologyConstraint>();
    
    /** The additional constraints. */
    private Set<Constraint> additionalConstraints = new HashSet<Constraint>();

    /**
     * empty no args constructor for persistence.
     */
    public QueryComponentExpressionImpl() {
        super(UUID.randomUUID().toString());
    }

    /**
     * Instantiates a new query component expression impl.
     * 
     * @param expression the expression
     */
    public QueryComponentExpressionImpl(CloseToUserExpression expression) {
        this(new TerminologyConstraintImpl(expression));
    }

    /**
     * Instantiates a new query component expression impl.
     * 
     * @param includedConstraint the included constraint
     */
    public QueryComponentExpressionImpl(TerminologyConstraint includedConstraint) {
        this(includedConstraint, new HashSet<TerminologyConstraint>(), new HashSet<Constraint>() );
    }

    /**
     * Instantiates a new query component expression impl.
     * 
     * @param includedConstraint the included constraint
     * @param excludedConstraints the excluded constraints
     * @param additionalConstraints the additional constraints
     */
    public QueryComponentExpressionImpl(TerminologyConstraint includedConstraint,
                                        Set<TerminologyConstraint> excludedConstraints,
                                        Set<Constraint> additionalConstraints) {
        this(includedConstraint, excludedConstraints, additionalConstraints, UUID.randomUUID().toString());
    }

    /**
     * Instantiates a new query component expression impl.
     *
     * @param includedConstraint the included constraint
     * @param excludedConstraints the excluded constraints
     * @param additionalConstraints the additional constraints
     * @param uuid the uuid
     */
    public QueryComponentExpressionImpl(TerminologyConstraint includedConstraint,
                                        Set<TerminologyConstraint> excludedConstraints,
                                        Set<Constraint> additionalConstraints,
                                        String uuid) {
        super(uuid);
        this.includedConstraint = includedConstraint;
        this.excludedConstraints = excludedConstraints;
        this.additionalConstraints = additionalConstraints;
    }

    /**
     * Gets the included constraint.
     * 
     * @return the included constraint
     */
    @IndexedEmbedded
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = TerminologyConstraintImpl.class)
    public TerminologyConstraint getIncludedConstraint() {
        return includedConstraint;
    }

    /**
     * Sets the included constraint.
     * 
     * @param includedConstraint the new included constraint
     */
    public void setIncludedConstraint(TerminologyConstraint includedConstraint) {
        this.includedConstraint = includedConstraint;
    }

    /**
     * Gets the excluded constraints.
     * 
     * @return the excluded constraints
     */
    @IndexedEmbedded
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = TerminologyConstraintImpl.class)
    public Set<TerminologyConstraint> getExcludedConstraints() {
        return excludedConstraints;
    }

    /**
     * Sets the excluded constraints.
     * 
     * @param excludedConstraints the new excluded constraints
     */
    public void setExcludedConstraints(Set<TerminologyConstraint> excludedConstraints) {
        this.excludedConstraints = excludedConstraints;
    }

    /**
     * Gets the additional constraints.
     * 
     * @return the additional constraints
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractConstraint.class)
    public Set<Constraint> getAdditionalConstraints() {
        return additionalConstraints;
    }

    /**
     * Sets the additional constraints.
     * 
     * @param additionalConstraints the new additional constraints
     */
    public void setAdditionalConstraints(Set<Constraint> additionalConstraints) {
        this.additionalConstraints = additionalConstraints;
    }

    /**
     * Adds the excluded constraint.
     * 
     * @param constraint the constraint
     */
    @Transient
    public void addExcludedConstraint(TerminologyConstraint constraint){
        if(constraint != null)
        {
            getExcludedConstraints().add(constraint);
        }
    }

    /**
     * Removes the excluded constraint.
     * 
     * @param constraint the constraint
     */
    @Transient
    public void removeExcludedConstraint(TerminologyConstraint constraint){
        if(constraint != null)
        {
            getExcludedConstraints().remove(constraint);
        }
    }

    /**
     * Adds the additional constraint.
     * 
     * @param constraint the constraint
     */
    @Transient
    public void addAdditionalConstraint(Constraint constraint){
        if(constraint != null)
        {
            getAdditionalConstraints().add(constraint);
        }
    }

    /**
     * Removes the additional constraint.
     * 
     * @param constraint the constraint
     */
    @Transient
    public void removeAdditionalConstraint(Constraint constraint){
        if(constraint != null)
        {
            getAdditionalConstraints().remove(constraint);
        }
    }
}
