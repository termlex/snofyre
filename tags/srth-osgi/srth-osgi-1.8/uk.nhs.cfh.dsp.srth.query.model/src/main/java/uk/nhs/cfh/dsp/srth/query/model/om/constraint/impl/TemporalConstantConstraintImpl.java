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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl;

import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintDimensionVocabulary;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintValue;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TemporalConstantConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.error.InvalidConstraintValueException;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TemporalConstantConstraint}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 9, 2009 at 2:12:08 PM
 * <br>
 */

@Embeddable
@Entity(name = "TemporalConstantConstraint")
@DiscriminatorValue(value = "TemporalConstantConstraint")
public class TemporalConstantConstraintImpl extends AbstractTemporalConstraint implements TemporalConstantConstraint {

	/**
	 * Instantiates a new temporal constant constraint impl.
	 * 
	 * @param value the value
	 * @param dimensionVocabulary the dimension vocabulary
	 */
	public TemporalConstantConstraintImpl(ConstraintValue<Calendar> value,
			ConstraintDimensionVocabulary dimensionVocabulary) {
		super(value, dimensionVocabulary);
	}
	
	/**
	 * Instantiates a new temporal constant constraint impl.
	 * 
	 * @param date the date
	 * @param dimensionVocabulary the dimension vocabulary
	 */
	public TemporalConstantConstraintImpl(Calendar date,
			ConstraintDimensionVocabulary dimensionVocabulary) {
		super(new ConstraintValue<Calendar>(date), dimensionVocabulary);
	}
	
	/**
	 * Instantiates a new temporal constant constraint impl.
	 * 
	 * @param dimensionVocabulary the dimension vocabulary
	 */
	public TemporalConstantConstraintImpl(ConstraintDimensionVocabulary dimensionVocabulary) {
		super(new ConstraintValue<Calendar>(Calendar.getInstance()), dimensionVocabulary);
	}

    /**
     * Instantiates a new temporal constant constraint impl.
     */
    public TemporalConstantConstraintImpl() {
    }

    /**
     * Date.
     * 
     * @return the date
     */
    @Column(name = "date", columnDefinition = "date")
    public Date date() {
		return super.getTime().getTime();
	}

    /**
     * Sets the time.
     * 
     * @param date the new time
     * 
     * @throws InvalidConstraintValueException the invalid constraint value exception
     */
    public void setTime(Date date) throws InvalidConstraintValueException {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        super.setTime(gc);
    }

    /**
     * Sets the time.
     * 
     * @param millis the new time
     * 
     * @throws InvalidConstraintValueException the invalid constraint value exception
     */
    @Transient
    public void setTime(long millis) throws InvalidConstraintValueException {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(millis);

        super.setTime(cal);
    }
}
