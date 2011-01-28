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
package uk.nhs.cfh.dsp.srth.demographics.person.impl;

import org.hibernate.annotations.Index;
import uk.nhs.cfh.dsp.srth.demographics.person.Patient;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.EHRImpl;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;


// TODO: Auto-generated Javadoc
/**
 * An representation of a patient.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2009 at at 9:37:43 AM
 * <br> Modified on Monday; November 30, 2009 at 12:29 PM to create an interface class for Patient
 */

@Entity(name="Patient")
@Table(name="PATIENT_TABLE")
@DiscriminatorValue(value = "Patient")
public class PatientImpl extends AbstractPerson implements Patient {
    
    /** The ehr. */
    private EHR ehr;

    /**
	 * empty no args constructor for persistence.
	 */
	public PatientImpl(){
	}

    /**
     * Instantiates a new patient impl.
     *
     * @param id the patient id
     */
    public PatientImpl(long id){
        super(id);
    }

	/**
	 * Instantiates a new patient.
	 * 
	 * @param firstName the first name
	 * @param middleName the middle name
	 * @param lastName the last name
	 * @param dob the dob
	 * @param gender the gender
	 * @param age the age
	 * @param id the patient id
	 */
	public PatientImpl(String firstName, String middleName, String lastName,
			Calendar dob, Gender gender, long age, long id) {
		super(firstName, middleName, lastName, dob, gender, age);
        setId(id);
	}

    /**
     * Gets the ehr.
     * 
     * @return the ehr
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = EHRImpl.class )
    public EHR getEhr() {
        return ehr;
    }

    /**
     * Sets the ehr.
     * 
     * @param ehr the new ehr
     */
    public void setEhr(EHR ehr) {
        this.ehr = ehr;
    }
}
