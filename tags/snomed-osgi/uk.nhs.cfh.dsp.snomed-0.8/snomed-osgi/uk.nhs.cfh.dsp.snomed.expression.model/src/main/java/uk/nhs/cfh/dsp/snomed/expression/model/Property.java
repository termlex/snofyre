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

package uk.nhs.cfh.dsp.snomed.expression.model;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An object representation of an Attribute in an Attribute-value pair
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Friday; October 2, 2009 2:22:43 PM
 * <br> Modified on Wednesday; December 9, 2009 at 12:32 PM to enable OSGI
 * <br> Modified on Wednesday; December 16, 2009 at 4:18 PM to enable a generic expression class.
 */

public interface Property {


    /**
     * Gets the property name.
     *
     * @return the property name
     */
    String getPropertyName();

    /**
     * Sets the property name.
     *
     * @param propertyName the new property name
     */
    void setPropertyName(String propertyName);

    /**
     * Gets the uuid.
     *
     * @return the uuid
     */
    UUID getUuid();

    /**
     * Sets the uuid.
     *
     * @param uuid the new uuid
     */
    void setUuid(UUID uuid);
}