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

import java.net.URI;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a composite query object that contains many
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression}s and has some human readable and 
 * logical statements attached to it.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 21, 2010 at 8:28:20 PM
 */
public interface QueryStatement extends QueryExpression{
    
    /**
     * Gets the human readable statement.
     * 
     * @return the human readable statement
     */
    String getHumanReadableStatement();

    /**
     * Sets the human readable statement.
     * 
     * @param humanReadableStatement the new human readable statement
     */
    void setHumanReadableStatement(String humanReadableStatement);

    /**
     * Gets the logical statement.
     * 
     * @return the logical statement
     */
    String getLogicalStatement();

    /**
     * Sets the logical statement.
     * 
     * @param logicalStatement the new logical statement
     */
    void setLogicalStatement(String logicalStatement);

    /**
     * Gets the physical location.
     * 
     * @return the physical location
     */
    URI getPhysicalLocation();

    /**
     * Sets the physical location.
     * 
     * @param physicalLocation the new physical location
     */
    void setPhysicalLocation(URI physicalLocation);

    /**
     * Gets the file name.
     * 
     * @return the file name
     */
    String getFileName();
}
