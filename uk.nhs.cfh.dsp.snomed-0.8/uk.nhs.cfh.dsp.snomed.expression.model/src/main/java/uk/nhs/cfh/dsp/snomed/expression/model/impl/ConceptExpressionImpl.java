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

import uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.IntersectionExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 3:17:13 PM
 */

@Embeddable
@Entity(name = "ConceptExpression")
@Table(name = "CONCEPT_EXPRESSIONS")
public class ConceptExpressionImpl extends  AbstractExpressionImpl implements ConceptExpression {

    /** The concept. */
    private SnomedConcept concept;
    
    /** The concept id. */
    private String conceptId;

    /**
     * Instantiates a new concept expression impl.
     */
    public ConceptExpressionImpl() {
    }

    /**
     * Instantiates a new concept expression impl.
     *
     * @param concept the concept
     */
    public ConceptExpressionImpl(SnomedConcept concept) {
        this(UUID.randomUUID(), concept);
    }

    /**
     * Instantiates a new concept expression impl.
     *
     * @param uuid the uuid
     * @param concept the concept
     */
    public ConceptExpressionImpl(UUID uuid, SnomedConcept concept) {
        super(uuid);
        if (concept != null)
        {
            this.concept = concept;
            conceptId = concept.getConceptID();
        }
        else
        {
            throw new IllegalArgumentException("Concept passed can not be null.");
        }
    }

    /**
     * Populate relationships.
     *
     * @param concept the concept
     */
    @Transient
    protected void populateRelationships(SnomedConcept concept) {
        // get parents and add as parent expressions
        for(SnomedConcept parent : concept.getParents())
        {
            ConceptExpression parentExpression = new ConceptExpressionImpl(parent);
            parentExpression.addChildExpression(this);
        }

        // get children and add as child expressions
        for(SnomedConcept child : concept.getChildren())
        {
            addChildExpression(new ConceptExpressionImpl(child));
        }

        /*
            process relationships; we know that all defining relationships and role groups associated with them
            need to go into an equivalent expression composed on an intersection expression
          */


        Collection<Expression> expressions = new HashSet<Expression>();
        for(SnomedRelationship relationship : concept.getDefiningRelationships())
        {
            expressions.add(new SnomedRelationshipPropertyExpression(relationship));
        }
        // process role groups
        for(SnomedRoleGroup roleGroup : concept.getRoleGroups())
        {
            expressions.add(new SnomedRoleGroupExpression(roleGroup));
        }
        // create an intersection expression and add as equivalent expression
        IntersectionExpression intersectionExpression = new IntersectionExpressionImpl(expressions);
        intersectionExpression.addChildExpression(this);

        // process refining relationships
        for(SnomedRelationship relationship : concept.getRefiningRelationships())
        {
            SnomedRelationshipPropertyExpression relationshipPropertyExpression =
                    new SnomedRelationshipPropertyExpression(relationship);
            relationshipPropertyExpression.addChildExpression(this);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression#getConcept()
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SnomedConceptImpl.class)
    public SnomedConcept getConcept() {
        return concept;
    }

    
    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression#setConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public void setConcept(SnomedConcept concept) {
        this.concept = concept;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression#getConceptId()
     */
    @Column(name = "concept_id")
    public String getConceptId() {
        return conceptId;
    }

    
    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression#setConceptId(java.lang.String)
     */
    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#getCanonicalStringForm()
     */
    @Override
    @Transient
    public String getCanonicalStringForm() {
        return getConcept().getCanonicalStringForm();
    }
}