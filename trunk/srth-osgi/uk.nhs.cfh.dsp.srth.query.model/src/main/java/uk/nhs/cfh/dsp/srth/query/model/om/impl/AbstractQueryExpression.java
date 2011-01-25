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

import org.hibernate.search.annotations.*;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.annotation.MetaData;
import uk.nhs.cfh.dsp.srth.query.model.om.annotation.impl.MetaDataImpl;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An implementation of the {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression} interface.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * Created on Feb 9, 2009 at 11:46:20 AM
 */

@Indexed
@Embeddable
@Table(name = "QUERY_EXPRESSIONS")
@Entity(name = "QueryExpression")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Q_E_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractQueryExpression implements QueryExpression {

    /** The contained expressions. */
    private Set<QueryExpression> containedExpressions = new LinkedHashSet<QueryExpression>();

    /** The parent expression. */
    private QueryExpression parentExpression;

    /** The run time status. */
    private QueryRunTimeStatus runTimeStatus = QueryRunTimeStatus.EXECUTE;

    /** The meta data. */
    private MetaData metaData;

    /** The uuid. */
    private String uuid = UUID.randomUUID().toString();

    /**
     * Instantiates a new abstract query expression.
     */
    public AbstractQueryExpression() {
        this(UUID.randomUUID().toString());
    }

    /**
     * Instantiates a new abstract query expression.
     *
     * @param uuid the uuid
     */
    protected AbstractQueryExpression(String uuid) {
        if (uuid != null)
        {
            this.uuid = uuid;
            this.metaData = new MetaDataImpl();
        } else {
            this.uuid = UUID.randomUUID().toString();
            this.metaData = new MetaDataImpl();
        }
    }

    /**
     * Gets the contained expressions.
     * 
     * @return the contained expressions
     */
    @IndexedEmbedded
    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.EAGER, targetEntity= AbstractQueryExpression.class)
//    @JoinTable(name="CHILD_EXPRESSIONS")
//    @JoinTable(name="CHILD_EXPRESSIONS",
//            joinColumns = @JoinColumn(name = "uuid"),
//            inverseJoinColumns = @JoinColumn( name="child_expression_id"))
    public Set<QueryExpression> getContainedExpressions() {
        return containedExpressions;
    }

    /**
     * Sets the contained expressions.
     *
     * @param containedExpressions the new contained expressions
     */
    public void setContainedExpressions(Set<QueryExpression> containedExpressions) {
        this.containedExpressions = containedExpressions;
    }

    /**
     * Adds the child expression.
     *
     * @param expression the expression
     */
    @Transient
    public void addChildExpression(QueryExpression expression) {
        if (expression != null)
        {
            //add this as parent of expression
            getContainedExpressions().add(expression);
            // add this expression as parent
            expression.setParentExpression(this);
        }
    }

    /**
     * Removes the child expression.
     *
     * @param expression the expression
     */
    @Transient
    public void removeChildExpression(QueryExpression expression) {
        if (expression != null)
        {
            //remove expression
            getContainedExpressions().remove(expression);
            // remove as parent
            expression.setParentExpression(null);
        }
    }

    /**
     * Gets the parent expression.
     *
     * @return the parent expression
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractQueryExpression.class)
    public QueryExpression getParentExpression() {
        return parentExpression;
    }

    /**
     * Sets the parent expression.
     *
     * @param parentExpression the new parent expression
     */
    public void setParentExpression(QueryExpression parentExpression) {
        this.parentExpression = parentExpression;
    }

    /**
     * Gets the run time status.
     *
     * @return the run time status
     */
    @Field(index = Index.TOKENIZED, store = Store.NO)
    @Enumerated(EnumType.STRING)
    @Column(name = "run_time_status")
    public QueryRunTimeStatus getRunTimeStatus() {
        return runTimeStatus;
    }

    /**
     * Sets the run time status.
     *
     * @param runTimeStatus the new run time status
     */
    public void setRunTimeStatus(QueryRunTimeStatus runTimeStatus) {
        this.runTimeStatus = runTimeStatus;
    }

    /**
     * Gets the meta data.
     *
     * @return the meta data
     */
    @IndexedEmbedded
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = MetaDataImpl.class)
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     * Sets the meta data.
     *
     * @param metaData the new meta data
     */
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * Gets the uUID.
     *
     * @return the uUID
     */
    @Id
    @Column(name = "UUID", nullable = false, columnDefinition = "VARCHAR(36)")
    public String getUUID() {
        return uuid;
    }

    /**
     * Sets the uUID.
     *
     * @param uuid the new uUID
     */
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    @Transient
    public int hashCode() {
        return getUUID().hashCode();
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
        else if(obj instanceof QueryExpression)
        {
            QueryExpression expression = (QueryExpression) obj;
            String expUUID = expression.getUUID();

            return expUUID != null && getUUID() != null && expUUID.equals(getUUID());
        }
        else
        {
            return false;
        }
    }
}
