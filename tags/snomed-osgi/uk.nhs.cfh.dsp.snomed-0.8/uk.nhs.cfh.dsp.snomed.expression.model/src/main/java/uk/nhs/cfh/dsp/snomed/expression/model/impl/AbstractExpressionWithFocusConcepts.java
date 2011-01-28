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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRelationshipImpl;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRoleGroupImpl;

import javax.persistence.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 7, 2010 at 4:58:08 PM
 */
@Embeddable
@MappedSuperclass
@Entity(name = "ExpressionWithFocusConcepts")
public abstract class AbstractExpressionWithFocusConcepts extends AbstractExpressionImpl implements ExpressionWithFocusConcepts {

    /** The focus concepts. */
    protected Collection<SnomedConcept> focusConcepts = new HashSet<SnomedConcept>();
    
    /** The relationship expressions. */
    protected Collection<SnomedRelationshipPropertyExpression> relationshipExpressions = new HashSet<SnomedRelationshipPropertyExpression>();
    
    /** The role group expressions. */
    protected Collection<SnomedRoleGroupExpression>roleGroupExpressions = new HashSet<SnomedRoleGroupExpression>();
    
    /** The relationships. */
    protected Collection<SnomedRelationship> relationships = new HashSet<SnomedRelationship>();
    
    /** The role groups. */
    protected Collection<SnomedRoleGroup> roleGroups = new HashSet<SnomedRoleGroup>();
    
    /** The property expressions. */
    protected Collection<PropertyExpression> propertyExpressions = new HashSet<PropertyExpression>();
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(AbstractExpressionWithFocusConcepts.class);

    /**
     * Instantiates a new abstract expression with focus concepts.
     *
     * @param concept the concept
     */
    public AbstractExpressionWithFocusConcepts(SnomedConcept concept) {
        this(UUID.randomUUID(), Collections.singleton(concept));
    }

    /**
     * Instantiates a new abstract expression with focus concepts.
     */
    public AbstractExpressionWithFocusConcepts() {
        this(new HashSet<SnomedConcept>());
    }

    /**
     * Instantiates a new abstract expression with focus concepts.
     *
     * @param focusConcepts the focus concepts
     */
    public AbstractExpressionWithFocusConcepts(Collection<SnomedConcept> focusConcepts) {
        this(UUID.randomUUID(),focusConcepts);
    }

    /**
     * Instantiates a new abstract expression with focus concepts.
     *
     * @param uuid the uuid
     * @param focusConcepts the focus concepts
     */
    public AbstractExpressionWithFocusConcepts(UUID uuid, Collection<SnomedConcept> focusConcepts) {
        super(uuid);
        this.focusConcepts = focusConcepts;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts#getFocusConcepts()
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SnomedConceptImpl.class)
//    @JoinTable(
//            name="FOCUS_CONCEPTS",
//            joinColumns = @JoinColumn( name="uuid"),
//            inverseJoinColumns = @JoinColumn( name="focus_concept_id"))
    public Collection<SnomedConcept> getFocusConcepts() {
        return focusConcepts;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts#setFocusConcepts(java.util.Collection)
     */
    public void setFocusConcepts(Collection<SnomedConcept> focusConcepts) {
        this.focusConcepts = focusConcepts;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts#addFocusConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    @Transient
    public void addFocusConcept(SnomedConcept concept){
        if(concept != null)
        {
            getFocusConcepts().add(concept);
        }
        else
        {
            throw new IllegalArgumentException("Focus concept passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts#removeFocusConcept(uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept)
     */
    @Transient
    public void removeFocusConcept(SnomedConcept concept){
        if(concept != null)
        {
            getFocusConcepts().remove(concept);
        }
        else
        {
            throw new IllegalArgumentException("Focus concept passed can not be null.");
        }
    }

    /**
     * Gets the relationships.
     *
     * @return the relationships
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedRelationshipImpl.class)
    @JoinTable(
            name="EFC_RELATIONSHIPS",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="relationship_id"))
    public Collection<SnomedRelationship> getRelationships() {
        return relationships;
    }

    /**
     * Sets the relationships.
     *
     * @param relationships the new relationships
     */
    public void setRelationships(Collection<SnomedRelationship> relationships) {
        this.relationships = relationships;
    }

    /**
     * Gets the role groups.
     *
     * @return the role groups
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedRoleGroupImpl.class)
    @JoinTable(
            name="EFC_ROLEGROUPS",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="rolegroup_id"))
    public Collection<SnomedRoleGroup> getRoleGroups() {
        return roleGroups;
    }

    /**
     * Sets the role groups.
     *
     * @param roleGroups the new role groups
     */
    public void setRoleGroups(Collection<SnomedRoleGroup> roleGroups) {
        this.roleGroups = roleGroups;
    }

    /**
     * Gets the relationship expressions.
     *
     * @return the relationship expressions
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedRoleGroupImpl.class)
    @JoinTable(
            name="EFC_RELATIONSHIP_EXPRESSIONS",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="rel_exp_uuid"))
    public Collection<SnomedRelationshipPropertyExpression> getRelationshipExpressions() {
        return relationshipExpressions;
    }

    /**
     * Sets the relationship expressions.
     *
     * @param relationshipExpressions the new relationship expressions
     */
    public void setRelationshipExpressions(Collection<SnomedRelationshipPropertyExpression> relationshipExpressions) {
        this.relationshipExpressions = relationshipExpressions;
    }

    /**
     * Gets the role group expressions.
     *
     * @return the role group expressions
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedRoleGroupImpl.class)
    @JoinTable(
            name="EFC_ROLEGROUP_EXPRESSIONS",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="rg_exp_uuid"))
    public Collection<SnomedRoleGroupExpression> getRoleGroupExpressions() {
        return roleGroupExpressions;
    }

    /**
     * Sets the role group expressions.
     *
     * @param roleGroupExpressions the new role group expressions
     */
    public void setRoleGroupExpressions(Collection<SnomedRoleGroupExpression> roleGroupExpressions) {
        this.roleGroupExpressions = roleGroupExpressions;
    }

    /**
     * Gets the property expressions.
     *
     * @return the property expressions
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity= SnomedRoleGroupImpl.class)
    @JoinTable(
            name="EFC_PROPERTY_EXPRESSIONS",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="prop_exp_uuid"))
    public Collection<PropertyExpression> getPropertyExpressions() {
        return propertyExpressions;
    }

    /**
     * Sets the property expressions.
     *
     * @param propertyExpressions the new property expressions
     */
    public void setPropertyExpressions(Collection<PropertyExpression> propertyExpressions) {
        for(PropertyExpression propertyExpression : propertyExpressions)
        {
            addPropertyExpression(propertyExpression);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#setChildExpressions(java.util.Collection)
     */
    @Override
    public void setChildExpressions(Collection<Expression> childExpressions) {
        for(Expression expression : childExpressions)
        {
            if(expression instanceof PropertyExpression)
            {
                addPropertyExpression((PropertyExpression) expression);
            }
        }
    }

    /**
     * Adds the relationship.
     *
     * @param relationship the relationship
     */
    @Transient
    public void addRelationship(SnomedRelationship relationship){
        if(relationship != null)
        {
            relationships.add(relationship);
        }
        else
        {
            throw new IllegalArgumentException("Relationship passed can not be null.");
        }
    }

    /**
     * Removes the relationship.
     *
     * @param relationship the relationship
     */
    @Transient
    public void removeRelationship(SnomedRelationship relationship){
        if(relationship != null)
        {
            relationships.remove(relationship);
        }
        else
        {
            throw new IllegalArgumentException("Relationship passed can not be null.");
        }
    }

    /**
     * Adds the role group.
     *
     * @param roleGroup the role group
     */
    @Transient
    public void addRoleGroup(SnomedRoleGroup roleGroup){
        if(roleGroup != null)
        {
            roleGroups.add(roleGroup);
        }
        else
        {
            throw new IllegalArgumentException("Role group passed can not be null.");
        }
    }

    /**
     * Removes the role group.
     *
     * @param roleGroup the role group
     */
    @Transient
    public void removeRoleGroup(SnomedRoleGroup roleGroup){
        if(roleGroup != null)
        {
            roleGroups.remove(roleGroup);
        }
        else
        {
            throw new IllegalArgumentException("Role group passed can not be null.");
        }
    }

    /**
     * Adds the property expression.
     *
     * @param expression the expression
     */
    @Transient
    public void addPropertyExpression(PropertyExpression expression){
        if(expression != null)
        {
            propertyExpressions.add(expression);
            if(expression instanceof SnomedRelationshipPropertyExpression)
            {
                super.addChildExpression(expression);
                SnomedRelationshipPropertyExpression snomedRelationshipPropertyExpression =
                        (SnomedRelationshipPropertyExpression) expression;
                relationshipExpressions.add(snomedRelationshipPropertyExpression);

                SnomedRelationship relationship = snomedRelationshipPropertyExpression.getRelationship();
                if(relationship != null)
                {
                    relationships.add(relationship);
                }
                else
                {
                    throw new IllegalArgumentException("Relationship contained in expression passed can not be null.");
                }
            }
            else if(expression instanceof SnomedRoleGroupExpression)
            {
                super.addChildExpression(expression);
                SnomedRoleGroupExpression roleGroupExpression = (SnomedRoleGroupExpression) expression;
                roleGroupExpressions.add(roleGroupExpression);
                SnomedRoleGroup roleGroup = roleGroupExpression.getRoleGroup();
                if(roleGroup != null)
                {
                    roleGroups.add(roleGroup);
                }
                else
                {
                    throw new IllegalArgumentException("Role Group contained in expression passed can not be null.");
                }
            }
            else
            {
                throw new IllegalArgumentException("PropertyExpression passed not recognised : "+expression);
            }
        }
        else
        {
            throw new IllegalArgumentException("PropertyExpression passed can not be null.");
        }
    }

    /**
     * Removes the property expression.
     *
     * @param expression the expression
     */
    @Transient
    public void removePropertyExpression(PropertyExpression expression){
        if(expression != null)
        {
            propertyExpressions.remove(expression);
            if(expression instanceof SnomedRelationshipPropertyExpression)
            {
                super.removeChildExpression(expression);
                SnomedRelationshipPropertyExpression snomedRelationshipPropertyExpression =
                        (SnomedRelationshipPropertyExpression) expression;
                relationshipExpressions.remove(snomedRelationshipPropertyExpression);
                SnomedRelationship relationship = snomedRelationshipPropertyExpression.getRelationship();
                if(relationship != null)
                {
                    relationships.remove(relationship);
                }
                else
                {
                    throw new IllegalArgumentException("Relationship contained in expression passed can not be null.");
                }
            }
            else if(expression instanceof SnomedRoleGroupExpression)
            {
                super.removeChildExpression(expression);
                SnomedRoleGroupExpression roleGroupExpression = (SnomedRoleGroupExpression) expression;
                roleGroupExpressions.remove(roleGroupExpression);
                SnomedRoleGroup roleGroup = roleGroupExpression.getRoleGroup();
                if(roleGroup != null)
                {
                    roleGroups.remove(roleGroup);
                }
                else
                {
                    throw new IllegalArgumentException("Role Group contained in expression passed can not be null.");
                }
            }
            else
            {
                throw new IllegalArgumentException("PropertyExpression passed not recognised : "+expression);
            }
        }
        else
        {
            throw new IllegalArgumentException("PropertyExpression passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts#getCompositionalGrammarForm()
     */
    @Transient
    public String getCompositionalGrammarForm(){
        return getRenderedStringForm(true);
    }

    /**
     * Gets the rendered string form.
     *
     * @param useSpacing the use spacing
     * @return the rendered string form
     */
    @Transient
    private String getRenderedStringForm(boolean useSpacing) {

        String spacing = "";
        String newLine = "";
        if(useSpacing)
        {
            // if compositional grammar is to be returned, we use spacing and newline character.
            spacing = " ";
            newLine = "\n";
        }
        String canonicalForm = "";
        int counter1 = 0;
        List<SnomedConcept> sortedConcepts = new ArrayList<SnomedConcept>(getFocusConcepts());
        Collections.sort(sortedConcepts);
        List<SnomedRelationshipPropertyExpression> sortedRelationshipExpressions =
                new ArrayList<SnomedRelationshipPropertyExpression>(getRelationshipExpressions());
        Collections.sort(sortedRelationshipExpressions);
        List<SnomedRoleGroupExpression> sortedRoleGroupExpressions =
                new ArrayList<SnomedRoleGroupExpression>(getRoleGroupExpressions());
        Collections.sort(sortedRoleGroupExpressions);

        // add proximal primitive concepts
        for(SnomedConcept c : sortedConcepts)
        {
            if(counter1 ==0)
            {
                canonicalForm = c.getCanonicalStringForm();
            }
            else
            {
                canonicalForm = canonicalForm+spacing+"+"+spacing+c.getCanonicalStringForm();
            }

            // increment counter
            counter1++;
        }

        // check if there are any property expressions and add a refinement symbol if present
        if(this.getChildExpressions().size() >0)
        {
            canonicalForm= canonicalForm+spacing+":"+spacing;
        }

        // process relationships
        int counter2 =0;
        for(SnomedRelationshipPropertyExpression rpe : sortedRelationshipExpressions)
        {
            String cf = rpe.getCanonicalStringForm();
            if(counter2 == 0)
            {
                canonicalForm = canonicalForm+spacing+newLine+cf;
            }
            else
            {
                canonicalForm = canonicalForm+spacing+","+newLine+spacing+cf;
            }

            // increment counter 2
            counter2++;
        }

        int counter3 = 0;
        // process property expressions
        for(SnomedRoleGroupExpression rge : sortedRoleGroupExpressions)
        {
            String cf = rge.getCanonicalStringForm();
            if(counter3 == 0)
            {
                canonicalForm = canonicalForm+spacing+newLine+cf;
            }
            else
            {
                canonicalForm = canonicalForm+spacing+","+newLine+spacing+cf;
            }

            // increment counter3
            counter3++;
        }

        return canonicalForm;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#getCanonicalStringForm()
     */
    @Transient
    public String getCanonicalStringForm() {
        return getRenderedStringForm(false);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#hashCode()
     */
    @Override
    @Transient
    public int hashCode() {
        return getCanonicalStringForm().hashCode();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#equals(java.lang.Object)
     */
    @Override
    @Transient
    public boolean equals(Object obj) {

        if(obj instanceof ExpressionWithFocusConcepts)
        {
            ExpressionWithFocusConcepts expressionWithFocusConcepts = (ExpressionWithFocusConcepts) obj;
            return this.getCanonicalStringForm().equalsIgnoreCase(expressionWithFocusConcepts.getCanonicalStringForm());
        }
        else
        {
            return super.equals(obj);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts#getSingletonFocusConceptType()
     */
    @Transient
    public ConceptType getSingletonFocusConceptType(){
        return getSingletonFocusConcept().getType();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.ExpressionWithFocusConcepts#getSingletonFocusConcept()
     */
    @Transient
    public SnomedConcept getSingletonFocusConcept() {
        List<SnomedConcept> focusConcepts = new ArrayList<SnomedConcept>(getFocusConcepts());
        if(focusConcepts.size() == 1)
        {
            return focusConcepts.get(0);
        }
        else if(focusConcepts.size() > 1)
        {
            logger.warn("Found more than one focus concept. Using the first one found");
            return focusConcepts.get(0);
        }
        else
        {
            throw new IllegalArgumentException("Expression passed has no focus concepts.");
        }
    }
}
