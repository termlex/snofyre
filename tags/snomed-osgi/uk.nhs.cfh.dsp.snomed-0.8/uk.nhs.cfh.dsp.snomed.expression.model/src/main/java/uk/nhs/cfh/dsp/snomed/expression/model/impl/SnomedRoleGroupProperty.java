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
package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import uk.nhs.cfh.dsp.snomed.expression.model.Property;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.expression.model.Property}
 * that handles a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 16, 2009 at 1:06:03 PM
 * <br> Modified on Wednesday; December 16, 2009 at 4:24 PM to support generic expression class
 */

@Embeddable
@Entity(name = "SnomedRoleGroupProperty")
@Table(name = "SNOMED_ROLE_GROUP_PROPERTIES")
public class SnomedRoleGroupProperty extends AbstractPropertyImpl implements Property {

    /**
     * Instantiates a new snomed role group property.
     */
    public SnomedRoleGroupProperty() {
        super("ROLE_GROUP");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractPropertyImpl#setPropertyName(java.lang.String)
     */
    @Override
    @Transient
    public void setPropertyName(String propertyName) {
        throw new UnsupportedOperationException("This operation is not permitted");
    }
}