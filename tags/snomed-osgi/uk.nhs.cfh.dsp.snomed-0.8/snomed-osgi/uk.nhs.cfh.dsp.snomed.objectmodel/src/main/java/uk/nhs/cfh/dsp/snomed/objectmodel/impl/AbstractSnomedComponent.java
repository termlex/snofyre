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
package uk.nhs.cfh.dsp.snomed.objectmodel.impl;

import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 19-Mar-2010 at 16:19:57
 */
public abstract class AbstractSnomedComponent implements SnomedComponent {

    /** The status. */
    private ComponentStatus status = ComponentStatus.UNKNOWN;

    /**
     * Instantiates a new abstract snomed component.
     */
    public AbstractSnomedComponent() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent#getStatus()
     */
    @org.hibernate.annotations.Index(name="IDX_STATUS")
    @Enumerated(value= EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(18)")
    public ComponentStatus getStatus() {
        return status;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.objectmodel.SnomedComponent#setStatus(ComponentStatus)
     */
    public void setStatus(ComponentStatus status) {
        this.status = status;
    }
}
