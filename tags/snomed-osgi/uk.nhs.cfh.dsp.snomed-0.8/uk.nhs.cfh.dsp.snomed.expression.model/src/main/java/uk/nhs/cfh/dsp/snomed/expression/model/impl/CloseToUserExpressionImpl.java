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
package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import javax.persistence.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 8, 2010 at 4:13:14 PM
 */

@Embeddable
@Entity(name = "CloseToUserExpression")
@Table(name = "CLOSE_TO_USER_EXPRESSIONS")
public class CloseToUserExpressionImpl extends AbstractExpressionWithFocusConcepts implements CloseToUserExpression {

    /**
     * Instantiates a new close to user expression impl.
     */
    public CloseToUserExpressionImpl() {
        this(new HashSet<SnomedConcept>());
    }

    /**
     * Instantiates a new close to user expression impl.
     *
     * @param concept the concept
     */
    public CloseToUserExpressionImpl(SnomedConcept concept) {
        this(Collections.singleton(concept));
    }

    /**
     * Instantiates a new close to user expression impl.
     *
     * @param focusConcepts the focus concepts
     */
    public CloseToUserExpressionImpl(Set<SnomedConcept> focusConcepts) {
        this(focusConcepts, UUID.randomUUID());
    }

    /**
     * Instantiates a new close to user expression impl.
     *
     * @param focusConcepts the focus concepts
     * @param uuid the uuid
     */
    public CloseToUserExpressionImpl(Set<SnomedConcept> focusConcepts, UUID uuid) {
        super(uuid, focusConcepts);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression#getRefinementExpressions()
     */
    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY, targetEntity= AbstractExpressionImpl.class)
    @JoinTable(name="REFINEMENT_EXPRESSIONS",
            joinColumns = @JoinColumn(name = "uuid"),
            inverseJoinColumns = @JoinColumn( name="ref_exp_uuid"))
    public Collection<PropertyExpression> getRefinementExpressions() {
        return super.getPropertyExpressions();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression#setRefinementExpressions(java.util.Collection)
     */
    public void setRefinementExpressions(Collection<PropertyExpression> refinementExpressions) {
        super.setPropertyExpressions(refinementExpressions);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression#addRefinement(uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression)
     */
    @Transient
    public void addRefinement(PropertyExpression refinement){
        super.addPropertyExpression(refinement);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression#removeRefinement(uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression)
     */
    @Transient
    public void removeRefinement(PropertyExpression refinement){
        super.removePropertyExpression(refinement);
    }

}
