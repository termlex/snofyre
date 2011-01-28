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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.nhs.cfh.dsp.snomed.objectmodel;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a relationship between concepts in a terminology
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on December 16, 2008 at 2:34:08 PM 
 */

public interface TerminologyRelationship {

    /**
     * Gets the relationship id.
     *
     * @return the relationship id
     */
    String getRelationshipID();

    /**
     * Gets the source concept id.
     *
     * @return the source concept id
     */
    String getSourceConceptID();

    /**
     * Gets the target concept id.
     *
     * @return the target concept id
     */
    String getTargetConceptID();

    /**
     * Sets the relationship id.
     *
     * @param relationshipID the new relationship id
     */
    void setRelationshipID(String relationshipID);

    /**
     * Sets the source concept id.
     *
     * @param sourceConceptID the new source concept id
     */
    void setSourceConceptID(String sourceConceptID);

    /**
     * Sets the target concept id.
     *
     * @param targetConceptID the new target concept id
     */
    void setTargetConceptID(String targetConceptID);
}
