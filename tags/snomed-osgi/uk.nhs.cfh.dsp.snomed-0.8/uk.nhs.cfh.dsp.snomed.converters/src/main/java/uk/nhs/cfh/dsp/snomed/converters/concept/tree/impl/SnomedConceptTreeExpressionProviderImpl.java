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
package uk.nhs.cfh.dsp.snomed.converters.concept.tree.impl;

import uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.ConceptExpressionImpl;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 5, 2010 at 10:07:20 AM
 */
public class SnomedConceptTreeExpressionProviderImpl implements SnomedConceptTreeExpressionProvider {

    /** The concept. */
    private SnomedConcept concept;
    
    /** The defining relationship property expressions. */
    private Collection<SnomedRelationshipPropertyExpression> definingRelationshipPropertyExpressions =
                                            new ArrayList<SnomedRelationshipPropertyExpression>();
    
    /** The qualifier relationship property expressions. */
    private Collection<SnomedRelationshipPropertyExpression> qualifierRelationshipPropertyExpressions =
                                            new ArrayList<SnomedRelationshipPropertyExpression>();
    
    /** The role group expressions. */
    private Collection<SnomedRoleGroupExpression> roleGroupExpressions = new ArrayList<SnomedRoleGroupExpression>();
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The concept expression. */
    private ConceptExpression conceptExpression;

    /**
     * Instantiates a new snomed concept tree expression provider impl.
     *
     * @param terminologyConceptDAO the terminology concept dao
     */
    public SnomedConceptTreeExpressionProviderImpl(TerminologyConceptDAO terminologyConceptDAO) {
        if (terminologyConceptDAO != null)
        {
            this.terminologyConceptDAO = terminologyConceptDAO;
        }
        else
        {
            throw new IllegalArgumentException("Terminology DAO passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#getConcept()
     */
    public SnomedConcept getConcept() {
        return concept;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#setConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public void setConcept(SnomedConcept concept) {
        this.concept = concept;
        // generate expression for concept
        generateExpression(getConcept());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#getDefiningRelationshipPropertyExpressions()
     */
    public Collection<SnomedRelationshipPropertyExpression> getDefiningRelationshipPropertyExpressions() {
        return definingRelationshipPropertyExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#setDefiningRelationshipPropertyExpressions(java.util.Collection)
     */
    public void setDefiningRelationshipPropertyExpressions(Collection<SnomedRelationshipPropertyExpression> definingRelationshipPropertyExpressions) {
        this.definingRelationshipPropertyExpressions = definingRelationshipPropertyExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#getQualifierRelationshipPropertyExpressions()
     */
    public Collection<SnomedRelationshipPropertyExpression> getQualifierRelationshipPropertyExpressions() {
        return qualifierRelationshipPropertyExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#setQualifierRelationshipPropertyExpressions(java.util.Collection)
     */
    public void setQualifierRelationshipPropertyExpressions(Collection<SnomedRelationshipPropertyExpression> qualifierRelationshipPropertyExpressions) {
        this.qualifierRelationshipPropertyExpressions = qualifierRelationshipPropertyExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#getRoleGroupExpressions()
     */
    public Collection<SnomedRoleGroupExpression> getRoleGroupExpressions() {
        return roleGroupExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#setRoleGroupExpressions(java.util.Set)
     */
    public void setRoleGroupExpressions(Set<SnomedRoleGroupExpression> roleGroupExpressions) {
        this.roleGroupExpressions = roleGroupExpressions;
    }

    /**
     * Gets the terminology concept dao.
     *
     * @return the terminology concept dao
     */
    public TerminologyConceptDAO getTerminologyConceptDAO() {
        return terminologyConceptDAO;
    }

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#getConceptExpression()
     */
    public ConceptExpression getConceptExpression() {
        return conceptExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#setConceptExpression(uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression)
     */
    public void setConceptExpression(ConceptExpression conceptExpression) {
        this.conceptExpression = conceptExpression;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.concept.tree.SnomedConceptTreeExpressionProvider#generateExpression(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    public ConceptExpression generateExpression(SnomedConcept concept) {

        if(concept != null)
        {
            // set concept as global concept
            this.concept = concept;
            
            ConceptExpression conceptExpression = new ConceptExpressionImpl(getConcept());

            // get non subsumption relationships and qualifier relationships and sort them
            List<SnomedRelationship> nonSubSumptionDefiningRelationships =
                            new ArrayList<SnomedRelationship>(concept.getNonSubSumptionDefiningRelationships());
            Collections.sort(nonSubSumptionDefiningRelationships);

            List<SnomedRelationship> qualifierRelationships =
                                    new ArrayList<SnomedRelationship>(concept.getRefiningRelationships());
            Collections.sort(qualifierRelationships);

            for(SnomedRelationship relationship : nonSubSumptionDefiningRelationships)
            {
                SnomedRelationshipPropertyExpression propertyExpression =
                                                new SnomedRelationshipPropertyExpression(relationship);
                // add to nonSubSumptionDefiningRelationships
                definingRelationshipPropertyExpressions.add(propertyExpression);
                // add as child expression of conceptExpression
                conceptExpression.addChildExpression(propertyExpression);
            }

            // add qualifier relationships as property expressions
            for(SnomedRelationship relationship : qualifierRelationships)
            {
                SnomedRelationshipPropertyExpression propertyExpression = getRelationshipExpression(relationship);
                // add to qualifierRelationshipPropertyExpressions
                qualifierRelationshipPropertyExpressions.add(propertyExpression);
                // add as child expression of this conceptExpression
                conceptExpression.addChildExpression(propertyExpression);
            }

            // get role groups and sort them
            List<SnomedRoleGroup> roleGroups = new ArrayList<SnomedRoleGroup>(concept.getRoleGroups());
            Collections.sort(roleGroups);

            // process and add role groups
            for(SnomedRoleGroup roleGroup : roleGroups)
            {
                SnomedRoleGroupExpression roleGroupExpression = getRoleGroupExpression(roleGroup);
                // add to roleGroupExpressions
                roleGroupExpressions.add(roleGroupExpression);
                // add as child of conceptExpression
                conceptExpression.addChildExpression(roleGroupExpression);
            }

            // set local conceptExpression as global conceptExpression
            setConceptExpression(conceptExpression);

            return getConceptExpression();
        }
        else
        {
            throw new IllegalArgumentException("Concept passed can not be null.");
        }
    }

    /**
     * Gets the role group expression.
     *
     * @param roleGroup the role group
     * @return the role group expression
     */
    private SnomedRoleGroupExpression getRoleGroupExpression(SnomedRoleGroup roleGroup){

        if(roleGroup != null)
        {
            SnomedRoleGroupExpression roleGroupExpression = new SnomedRoleGroupExpression(roleGroup);
            // get relationships in role group and add as children
            for(SnomedRelationship relationship : roleGroup.getRelationships())
            {
                roleGroupExpression.addChildExpression(getRelationshipExpression(relationship));
            }

            return roleGroupExpression;
        }
        else
        {
            throw new IllegalArgumentException("Role group passed can not be null.");
        }
    }

    /**
     * Gets the relationship expression.
     *
     * @param relationship the relationship
     * @return the relationship expression
     */
    private SnomedRelationshipPropertyExpression getRelationshipExpression(SnomedRelationship relationship){

        if(relationship != null)
        {
            SnomedRelationshipPropertyExpression propertyExpression =
                                            new SnomedRelationshipPropertyExpression(relationship);
            // get target concept for relationship and process it
            SnomedConcept targetConcept = relationship.getTargetConcept();
            if(targetConcept == null)
            {
                targetConcept = checkAndGetConcept(relationship.getTargetConceptID());
            }

            // ensure concept is no longer null
            if(targetConcept != null)
            {
                relationship.setTargetConcept(targetConcept);
                propertyExpression.setExpression(new ConceptExpressionImpl(targetConcept));
            }

            return propertyExpression;            
        }
        else
        {
            throw new IllegalArgumentException("Relationship passed can not be null.");
        }
    }

    /**
     * Check and get concept.
     *
     * @param conceptId the concept id
     * @return the snomed concept
     */
    private SnomedConcept checkAndGetConcept(String conceptId){

        if(conceptId != null)
        {
            SnomedConcept concept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, conceptId);
            if(concept != null)
            {
                return concept;
            }
            else
            {
                throw new IllegalArgumentException("Concept with given ID does not exist. Concept ID : "+conceptId);
            }
        }
        else
        {
            throw new IllegalArgumentException("Concept ID passed can not be null.");
        }
    }
}
