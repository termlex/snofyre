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
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintType;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintValue;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TemporalConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.error.InvalidConstraintValueException;

import javax.persistence.*;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TemporalConstraint}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 9, 2009 at 9:45:39 AM
 * <br>
 */

@Embeddable
@Entity(name = "AbstractTerminologyConstraint")
@MappedSuperclass
public abstract class AbstractTemporalConstraint extends AbstractConstraint implements TemporalConstraint {


    /**
     * Instantiates a new abstract temporal constraint.
     */
    public AbstractTemporalConstraint() {
        // empty constructor for persistence
    }

    /**
     * Instantiates a new abstract temporal constraint.
     * 
     * @param dimensionVocabulary the dimension vocabulary
     */
    protected AbstractTemporalConstraint(ConstraintDimensionVocabulary dimensionVocabulary) {
        super(ConstraintType.TEMPORAL, dimensionVocabulary);
    }

    /**
     * Instantiates a new abstract temporal constraint.
     * 
     * @param value the value
     * @param dimensionVocabulary the dimension vocabulary
     */
    protected AbstractTemporalConstraint(ConstraintValue<?> value,
                                      ConstraintDimensionVocabulary dimensionVocabulary) {
        super(ConstraintType.TEMPORAL, value, dimensionVocabulary);
    }

    /**
     * Gets the time.
     * 
     * @return the time
     */

    @Column(name = "time", columnDefinition = "timestamp")
    public Calendar getTime() {
        return (Calendar) super.getValue().getValue();
    }

    /**
     * Sets the time.
     * 
     * @param time the new time
     * 
     * @throws InvalidConstraintValueException the invalid constraint value exception
     */
    public void setTime(Calendar time) throws InvalidConstraintValueException {
        super.setValue(new ConstraintValue<Calendar> (time));    //To change body of overridden methods use File | Settings | File Templates.
    }

    /* (non-Javadoc)
     * @see nhs.cfh.dsp.srth.query.model.om.constraint.impl.AbstractConstraint#permittedValue(ConstraintValue)
     */
    @Transient
    protected boolean permittedValue(ConstraintValue<?> value) {
        return (value.getValue() instanceof Calendar);
    }
}
