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
package uk.nhs.cfh.dsp.srth.query.transform.sql;

import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a provider that returns the database column that corresponds to a
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint}. This allows the SQL generated for a
 * given {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression} to be cleanly decoupled from the
 * abstract query specification.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 2:44:52 PM
 */
public interface ConstraintColumnNameProvider {

    /**
     * Gets the column name.
     * 
     * @param constraint the constraint
     * 
     * @return the column name
     */
    String getColumnName(Constraint constraint);

    /**
     * Gets the column name.
     * 
     * @param constraintName the constraint name
     * 
     * @return the column name
     */
    String getColumnName(String constraintName);

    /**
     * Gets the column map.
     * 
     * @return the column map
     */
    Map<String, String> getColumnMap();

    /**
     * Sets the column map.
     * 
     * @param columnMap the column map
     */
    void setColumnMap(Map<String, String> columnMap);
}
