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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.Expression}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 3:13:25 PM
 */

@Embeddable
@MappedSuperclass
@Entity(name = "Expression")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@TypeDef(name = "uuid", typeClass = uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType.class)
public abstract class AbstractExpressionImpl implements Expression, Comparable<Expression> {

    /** The uuid. */
    private UUID uuid;
    
    /** The parent expressions. */
    private Collection<Expression> parentExpressions = new HashSet<Expression>();
    
    /** The child expressions. */
    private Collection<Expression> childExpressions = new HashSet<Expression>();
    
    /** The equivalent expressions. */
    private Collection<Expression> equivalentExpressions = new HashSet<Expression>();

    /**
     * Instantiates a new abstract expression impl.
     */
    public AbstractExpressionImpl() {
        this(UUID.randomUUID());
    }

    /**
     * Instantiates a new abstract expression impl.
     *
     * @param uuid the uuid
     */
    public AbstractExpressionImpl(UUID uuid) {
        this(uuid, new HashSet<Expression>());
    }

    /**
     * Instantiates a new abstract expression impl.
     *
     * @param uuid the uuid
     * @param childExpressions the child expressions
     */
    public AbstractExpressionImpl(UUID uuid, Collection<Expression> childExpressions) {
        if (uuid != null)
        {
            this.uuid = uuid;
            this.childExpressions = childExpressions;
        }
        else {
            this.uuid = UUID.randomUUID();
            this.childExpressions = childExpressions;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#getUuid()
     */
    @Id
//    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    @Column(name = "uuid", nullable = false, columnDefinition = "VARBINARY(16)")
    @Type(type = "uk.nhs.cfh.dsp.snomed.persistence.orm.HexBasedUUIDUserType")
    public UUID getUuid() {
        return uuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#setUuid(java.lang.String)
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#getParentExpressions()
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AbstractExpressionImpl.class)
    @JoinTable(
            name="PARENT_EXPRESSIONS",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="parent_expression_id"))
    public Collection<Expression> getParentExpressions() {
        return parentExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#setParentExpressions(java.util.Collection)
     */
    public void setParentExpressions(Collection<Expression> parentExpressions) {
        this.parentExpressions = parentExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#getChildExpressions()
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AbstractExpressionImpl.class)
    @JoinTable(
            name="CHILD_EXPRESSIONS",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="child_expression_id"))
    public Collection<Expression> getChildExpressions() {
        return childExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#setChildExpressions(java.util.Collection)
     */
    public void setChildExpressions(Collection<Expression> childExpressions) {
        this.childExpressions = childExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#addChildExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Transient
    public void addChildExpression(Expression childExpression){

        if(childExpression != null)
        {
            getChildExpressions().add(childExpression);
            // as this as parent of childExpression
//            childExpression.addParentExpression(this);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#removeChildExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Transient
    public void removeChildExpression(Expression expression){
        if(expression != null)
        {
            getChildExpressions().remove(expression);
            // remove this expression as parent
//            expression.removeParentExpression(this);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#removeParentExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Transient
    public void removeParentExpression(Expression expression){
        if(expression != null)
        {
            getParentExpressions().remove(expression);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#addParentExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Transient
    public void addParentExpression(Expression parentExpression){
        if(parentExpression != null)
        {
            getParentExpressions().add(parentExpression);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#getEquivalentExpressions()
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AbstractExpressionImpl.class)
    @JoinTable(
            name="EQUIVALENT_EXPRESSIONS",
            joinColumns = @JoinColumn( name="uuid"),
            inverseJoinColumns = @JoinColumn( name="equivalent_expression_id"))
    public Collection<Expression> getEquivalentExpressions() {
        return equivalentExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#setEquivalentExpressions(java.util.Collection)
     */
    public void setEquivalentExpressions(Collection<Expression> equivalentExpressions) {
        this.equivalentExpressions = equivalentExpressions;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#addEquivalentExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Transient
    public void addEquivalentExpression(Expression expression){
        if(expression != null)
        {
            equivalentExpressions.add(expression);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.Expression#removeEquivalentExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Transient
    public void removeEquivalentExpression(Expression expression){
        if(expression != null)
        {
            equivalentExpressions.remove(expression);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    @Transient
    public int hashCode() {
        return getUuid().hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @Transient
    public boolean equals(Object obj) {
        if(obj == this)
        {
            return true;
        }
        else if(obj instanceof Expression)
        {
            Expression objExpression = (Expression) obj;
            return this.getUuid().equals(objExpression.getUuid());
        }
        else
        {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.CanonicalRepresentableElement#getCanonicalStringForm()
     */
    @Transient
    public String getCanonicalStringForm() {
        throw new UnsupportedOperationException("This method has not been implemented");
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(T)
     */
    @Transient
    public int compareTo(Expression o) {
        throw new UnsupportedOperationException("This method has not been implemented");
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    @Transient
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}