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
package uk.nhs.cfh.dsp.srth.information.model.impl.om.clinical.entity;

import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEvent;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link ClinicalEvent}.
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 6:52:47 PM
 */

//@MappedSuperclass
@Entity(name = "Clinical_Event")
@DiscriminatorValue("Clinical_Event")
public abstract class AbstractClinicalEvent extends AbstractClinicalEntity implements ClinicalEvent{

	/** The status. */
	private ClinicalEvent.Status status;

	/**
	 * Instantiates a new abstract clinical event.
	 */
	public AbstractClinicalEvent() {
		// empty constructor for persistence
	}
	
	/**
	 * Instantiates a new abstract clinical event.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param status the status
	 */
	public AbstractClinicalEvent(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, UUID uuid, Status status) {
		super(name, instantiationTime, clinicallyRelevantTime, type, uuid);
		this.status = status;
	}

    /**
     * Gets the status.
     * 
     * @return the status
     */
    @Enumerated(EnumType.STRING)
	public ClinicalEvent.Status getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the new status
	 */
	public void setStatus(ClinicalEvent.Status status) {
		this.status = status;
	}	
}
