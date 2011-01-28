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

package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.Property;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.Property}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Friday; October 2, 2009 2:29:54 PM
 * <br> Modified on Wednesday; December 9, 2009 at 12:38 PM to enable OSGI
 * <br> Modified on Wednesday; December 16, 2009 at 4:19 PM to enable a generic expression class
 */

@Embeddable
@Entity(name = "Property")
@Table(name = "PROPERTIES")
public class PropertyImpl extends AbstractPropertyImpl implements Property {

    /**
     * Instantiates a new property impl.
     *
     * @param propertyName the property name
     * @param uuid the uuid
     */
    public PropertyImpl(String propertyName, UUID uuid) {
        super(propertyName, uuid);
    }

    /**
     * Instantiates a new property impl.
     *
     * @param propertyName the property name
     */
    public PropertyImpl(String propertyName) {
        super(propertyName);
    }
}