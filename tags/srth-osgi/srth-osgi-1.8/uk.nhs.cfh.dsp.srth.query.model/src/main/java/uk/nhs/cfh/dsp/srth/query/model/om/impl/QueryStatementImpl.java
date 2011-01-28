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
/**
 *
 */
package uk.nhs.cfh.dsp.srth.query.model.om.impl;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.net.URI;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2009 at 11:44:41 AM
 */

@Indexed
@Entity(name = "QueryStatement")
@DiscriminatorValue(value = "QueryStatement")
public class QueryStatementImpl extends AbstractQueryExpression implements QueryStatement {

    /** The logical statement. */
    private String logicalStatement;

    /** The human readable statement. */
    private String humanReadableStatement;

    /** The physical location. */
    private URI physicalLocation;

    // explicit no args constructor
    /**
     * Instantiates a new query statement impl.
     */
    public QueryStatementImpl() {
        this(UUID.randomUUID().toString());
    }

    /**
     * Instantiates a new query statement impl.
     *
     * @param uuid the uuid
     */
    public QueryStatementImpl(String uuid) {
        super(uuid);
    }

    /**
     * Instantiates a new query statement impl.
     *
     * @param uuid the uuid
     * @param logicalStatement the logical statement
     * @param humanReadableStatement the human readable statement
     * @param physicalLocation the physical location
     */
    public QueryStatementImpl(String uuid, String logicalStatement, String humanReadableStatement, URI physicalLocation) {
        super(uuid);
        this.logicalStatement = logicalStatement;
        this.humanReadableStatement = humanReadableStatement;
        this.physicalLocation = physicalLocation;
    }

    /**
     * Gets the human readable statement.
     *
     * @return the human readable statement
     */
    @Column(name = "HUMAN_READABLE_STATEMENT")
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public String getHumanReadableStatement() {
        return humanReadableStatement;
    }

    /**
     * Sets the human readable statement.
     *
     * @param humanReadableStatement the new human readable statement
     */
    public void setHumanReadableStatement(String humanReadableStatement) {
        this.humanReadableStatement = humanReadableStatement;
    }

    /**
     * Gets the logical statement.
     *
     * @return the logical statement
     */
    @Column(name = "LOGICAL_STATEMENT")
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public String getLogicalStatement() {
        return logicalStatement;
    }

    /**
     * Sets the logical statement.
     *
     * @param logicalStatement the new logical statement
     */
    public void setLogicalStatement(String logicalStatement) {
        this.logicalStatement = logicalStatement;
    }

    /**
     * Gets the physical location.
     *
     * @return the physical location
     */
    @Column(name = "PHYSICAL_LOCATION")
    @Field(index = Index.TOKENIZED, store = Store.NO)
    public URI getPhysicalLocation() {
        return physicalLocation;
    }

    /**
     * Sets the physical location.
     *
     * @param physicalLocation the new physical location
     */
    public void setPhysicalLocation(URI physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    /**
     * Gets the file name.
     *
     * @return the file name
     */
    @Transient
    public String getFileName(){
        String fileName ="";
        // everything after the last index of system file separator will give file name
        String fileSeparator = System.getProperty("file.separator");
        if (getPhysicalLocation() != null) {
            // chop at last index of separator
            String path = getPhysicalLocation().getPath();
            fileName = path.substring(path.lastIndexOf(fileSeparator) + 1);
        }
        return fileName;
    }
}
