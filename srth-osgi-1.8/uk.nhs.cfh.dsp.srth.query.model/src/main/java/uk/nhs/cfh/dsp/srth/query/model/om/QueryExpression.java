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
package uk.nhs.cfh.dsp.srth.query.model.om;

import uk.nhs.cfh.dsp.srth.query.model.om.annotation.MetaData;

import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface that describes an query expression/criterion.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2009 at 11:38:03 AM
 */

public interface QueryExpression {

    /**
     * The Enum QueryOperatorType.
     */
    public enum QueryOperatorType {

        AND,
        OR,
        BEFORE,
        AFTER,
        WITHIN
    };

    /**
     * The Enum QueryRunTimeStatus.
     */
    public enum QueryRunTimeStatus {

        SKIP,
        EXECUTE
    }

    /**
     * Gets the contained expressions.
     *
     * @return the contained expressions
     */
    Set<QueryExpression> getContainedExpressions();

    /**
     * Sets the contained expressions.
     *
     * @param expressions the new contained expressions
     */
    void setContainedExpressions(Set<QueryExpression> expressions);

    /**
     * Gets the parent expression.
     *
     * @return the parent expression
     */
    QueryExpression getParentExpression();

    /**
     * Sets the parent expression.
     *
     * @param expression the new parent expression
     */
    void setParentExpression(QueryExpression expression);

    /**
     * Adds the child expression.
     *
     * @param expression the expression
     */
    void addChildExpression(QueryExpression expression);

    /**
     * Removes the child expression.
     *
     * @param expression the expression
     */
    void removeChildExpression(QueryExpression expression);

    /**
     * Gets the run time status.
     *
     * @return the run time status
     */
    QueryRunTimeStatus getRunTimeStatus();

    /**
     * Sets the run time status.
     *
     * @param runStatus the new run time status
     */
    void setRunTimeStatus(QueryRunTimeStatus runStatus);

    /**
     * Gets the meta data.
     *
     * @return the meta data
     */
    MetaData getMetaData();

    /**
     * Sets the meta data.
     *
     * @param metaData the new meta data
     */
    void setMetaData(MetaData metaData);

    /**
     * Sets the uUID.
     *
     * @param uuid the new uUID
     */
    void setUUID(String uuid);

    /**
     * Gets the uUID.
     *
     * @return the uUID
     */
    String getUUID();
}
