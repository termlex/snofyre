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
package uk.nhs.cfh.dsp.snomed.objectmodel;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an Attribute
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 9, 2009 at 12:34:23 PM.
 */
public interface SnomedAttribute {

    /**
     * Gets the iD.
     *
     * @return the iD
     */
    String getID();

    /**
     * Sets the iD.
     *
     * @param attributeID the new iD
     */
    void setID(String attributeID);

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the name.
     *
     * @param attributeName the new name
     */
    void setName(String attributeName);

    /**
     * Gets the allowed attribute value i ds.
     *
     * @return the allowed attribute value i ds
     */
    Set<String> getAllowedAttributeValueIDs();

    /**
     * Sets the allowed attribute value i ds.
     *
     * @param allowedAttributeValueIDs the new allowed attribute value i ds
     */
    void setAllowedAttributeValueIDs(Set<String> allowedAttributeValueIDs);

    /**
     * Gets the allowed attribute values.
     *
     * @return the allowed attribute values
     */
    Set<String> getAllowedAttributeValues();

    /**
     * Sets the allowed attribute values.
     *
     * @param allowedAttributeValues the new allowed attribute values
     */
    void setAllowedAttributeValues(Set<String> allowedAttributeValues);

    /**
     * Gets the concept type.
     *
     * @return the concept type
     */
    String getConceptType();

    /**
     * Sets the concept type.
     *
     * @param conceptType the new concept type
     */
    void setConceptType(String conceptType);
}
