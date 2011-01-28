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

import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 19, 2009 at 4:40:46 PM
 */

@Embeddable
@Entity(name = "NormalFormExpression")
@Table(name = "NORMAL_FORM_EXPRESSIONS")
public class NormalFormExpressionImpl extends AbstractExpressionWithFocusConcepts implements NormalFormExpression {

    /** The proximal primitives. */
    private Collection<SnomedConcept> proximalPrimitives = new HashSet<SnomedConcept>();

    /**
     * Instantiates a new normal form expression impl.
     *
     * @param concept the concept
     */
    public NormalFormExpressionImpl(SnomedConcept concept) {
        this(UUID.randomUUID(), Collections.singleton(concept));
    }

    /**
     * Instantiates a new normal form expression impl.
     */
    public NormalFormExpressionImpl() {
        this(new HashSet<SnomedConcept>());
    }

    /**
     * Instantiates a new normal form expression impl.
     *
     * @param focusConcepts the focus concepts
     */
    public NormalFormExpressionImpl(Collection<SnomedConcept> focusConcepts) {
        this(UUID.randomUUID(),focusConcepts);
    }

    /**
     * Instantiates a new normal form expression impl.
     *
     * @param uuid the uuid
     * @param focusConcepts the focus concepts
     */
    public NormalFormExpressionImpl(UUID uuid, Collection<SnomedConcept> focusConcepts) {
        super(uuid, focusConcepts);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression#getProximalPrimitives()
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SnomedConceptImpl.class)
    @JoinTable(
            name="PROXIMAL_PRIMITIVES",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="concept_id"))
    public Collection<SnomedConcept> getProximalPrimitives() {
        return proximalPrimitives;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression#setProximalPrimitives(java.util.Collection)
     */
    public void setProximalPrimitives(Collection<SnomedConcept> proximalPrimitives) {
        this.proximalPrimitives = proximalPrimitives;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression#addRelationshipExpression(uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression)
     */
    @Transient
    public void addRelationshipExpression(PropertyExpression expression){
        super.addPropertyExpression(expression);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression#removeRelationshipExpression(uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression)
     */
    @Transient
    public void removeRelationshipExpression(PropertyExpression expression){
        super.removePropertyExpression(expression);
    }
}