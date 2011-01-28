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
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataConstraint;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataConstraint}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 7, 2009 at 3:19:32 PM
 * <br>
 */

@Embeddable
@Entity(name = "AbstractDataConstraint")
@MappedSuperclass
public abstract class AbstractDataConstraint extends AbstractConstraint implements DataConstraint {

	/** The value. */
	private Number value;
	
	/**
	 * Instantiates a new abstract data constraint.
	 */
	public AbstractDataConstraint() {

	}

    /**
     * Instantiates a new abstract data constraint.
     * 
     * @param dimensionVocabulary the dimension vocabulary
     */
    protected AbstractDataConstraint(ConstraintDimensionVocabulary dimensionVocabulary) {
        super(ConstraintType.DATA, dimensionVocabulary);
    }

    /**
     * Instantiates a new abstract data constraint.
     * 
     * @param value the value
     * @param dimensionVocabulary the dimension vocabulary
     */
    protected AbstractDataConstraint(ConstraintValue<Number> value,
			ConstraintDimensionVocabulary dimensionVocabulary) {
		super(ConstraintType.DATA, value, dimensionVocabulary);
		this.value = value.getValue();
	}

	/**
	 * Instantiates a new abstract data constraint.
	 * 
	 * @param value the value
	 */
	protected AbstractDataConstraint(ConstraintValue<Number> value) {
		this(value, ConstraintDimensionVocabulary.NULL);
		this.value = value.getValue();
	}

    /**
     * Value.
     * 
     * @return the number
     */
    @Column(name = "value")
    public Number value(){
		return value;
	}

    /* (non-Javadoc)
     * @see nhs.cfh.dsp.srth.query.model.om.constraint.impl.AbstractConstraint#permittedValue(ConstraintValue)
     */
    @Transient
    protected boolean permittedValue(ConstraintValue<?> value) {
        return (value.getValue() instanceof Number);
    }
}
