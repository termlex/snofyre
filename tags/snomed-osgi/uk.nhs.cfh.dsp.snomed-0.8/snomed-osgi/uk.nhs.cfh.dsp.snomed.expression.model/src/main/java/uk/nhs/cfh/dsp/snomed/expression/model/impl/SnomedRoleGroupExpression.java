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
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRoleGroupImpl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression}
 * that wraps a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 5:41:53 PM
 */

@Embeddable
@Entity(name = "SnomedRoleGroupExpression")
@Table(name = "SNOMED_ROLE_GROUP_PROPERTY_EXPRESSIONS")
public class SnomedRoleGroupExpression extends AbstractPropertyExpressionImpl
        implements PropertyExpression, Comparable<Expression> {

    /** The role group. */
    private SnomedRoleGroup roleGroup;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SnomedRoleGroupExpression.class);

    /**
     * Instantiates a new snomed role group expression.
     *
     * @param uuid the uuid
     * @param roleGroup the role group
     */
    public SnomedRoleGroupExpression(UUID uuid, SnomedRoleGroup roleGroup) {
        super(uuid, new SnomedRoleGroupProperty());
        this.roleGroup = roleGroup;
    }

    /**
     * Instantiates a new snomed role group expression.
     *
     * @param roleGroup the role group
     */
    public SnomedRoleGroupExpression(SnomedRoleGroup roleGroup) {
        this(UUID.randomUUID(), roleGroup);
    }

    /**
     * Instantiates a new snomed role group expression.
     */
    public SnomedRoleGroupExpression() {
    }

    /**
     * Gets the role group.
     *
     * @return the role group
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SnomedRoleGroupImpl.class)    
    public SnomedRoleGroup getRoleGroup() {
        return roleGroup;
    }

    /**
     * Sets the role group.
     *
     * @param roleGroup the new role group
     */
    public void setRoleGroup(SnomedRoleGroup roleGroup) {
        this.roleGroup = roleGroup;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#getCanonicalStringForm()
     */
    @Override
    @Transient
    public String getCanonicalStringForm() {
        int counter = 0;
        String canonicalForm = "";
        // sort contained property expressions
        List<SnomedRelationshipPropertyExpression> sortedRelationshipPropertyExpressionList =
                new ArrayList<SnomedRelationshipPropertyExpression>();

        for(Expression e : getChildExpressions())
        {
            if(e instanceof SnomedRelationshipPropertyExpression)
            {
                sortedRelationshipPropertyExpressionList.add((SnomedRelationshipPropertyExpression) e);
            }
        }
        Collections.sort(sortedRelationshipPropertyExpressionList);

        // we get child expressions of rge
        for(SnomedRelationshipPropertyExpression childExpression : sortedRelationshipPropertyExpressionList)
        {
            String cf = childExpression.getCanonicalStringForm();
            if(counter == 0)
            {
                canonicalForm = cf;
            }
            else
            {
                canonicalForm = canonicalForm+",\n"+cf;
            }

            // increment counter
            counter++;
        }

        return "{"+canonicalForm+"}";
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

        if(obj instanceof SnomedRoleGroupExpression)
        {
            SnomedRoleGroupExpression roleGroupExpression = (SnomedRoleGroupExpression) obj;
            return roleGroupExpression.getCanonicalStringForm().equalsIgnoreCase(this.getCanonicalStringForm());
        }
        else
        {
            return super.equals(obj);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl#compareTo(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    @Override
    @Transient
    public int compareTo(Expression o) {
        if(o instanceof SnomedRoleGroupExpression)
        {
            return (this.getCanonicalStringForm().compareTo(o.getCanonicalStringForm()));
        }
        else
        {
            throw new IllegalArgumentException("Object being compared must be a type of "+getClass().getName());
        }
    }
}