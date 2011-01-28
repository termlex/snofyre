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
package uk.nhs.cfh.dsp.srth.query.model.om;

// TODO: Auto-generated Javadoc
/**
 * A interface that represents two or more {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression} objects joined
 * using a Logical OR operator.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 8:33:17 PM.
 */
public interface QueryUnionExpression extends QueryExpression {
    
    /**
     * Gets the operator.
     * 
     * @return the operator
     */
    QueryOperatorType getOperator();

    /**
     * Sets the operator.
     * 
     * @param operator the new operator
     */
    void setOperator(QueryOperatorType operator);
}
