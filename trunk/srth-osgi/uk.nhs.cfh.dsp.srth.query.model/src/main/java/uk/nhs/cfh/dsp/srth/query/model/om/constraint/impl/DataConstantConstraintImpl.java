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

import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;

import javax.persistence.*;


// TODO: Auto-generated Javadoc
/**
 * A {@link Constraint} object that takes a single numerical value as a constraint.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 7, 2009 at 3:40:13 PM
 * <br>
 */

@Embeddable
@Entity(name = "DataConstantConstraint")
@DiscriminatorValue(value = "DataConstantConstraint")
public class DataConstantConstraintImpl extends AbstractDataConstraint implements DataConstantConstraint {

    /** The value. */
    private Number value;

    /**
     * Instantiates a new data constant constraint impl.
     * 
     * @param value the value
     * @param dimensionVocabulary the dimension vocabulary
     */
    public DataConstantConstraintImpl(Number value,
                                      ConstraintDimensionVocabulary dimensionVocabulary) {
        super(new ConstraintValue<Number>(value), dimensionVocabulary);
        this.value = value;
    }

    /**
     * Instantiates a new data constant constraint impl.
     * 
     * @param value the value
     */
    public DataConstantConstraintImpl(Number value) {
        super(new ConstraintValue<Number>(value));
        this.value = value;
    }

    /**
     * Instantiates a new data constant constraint impl.
     */
    public DataConstantConstraintImpl() {
    }

    /**
     * As double.
     * 
     * @return the double
     */
    @Transient
    public double asDouble(){
        return (Double) value.doubleValue();
    }

    /**
     * As int.
     * 
     * @return the int
     */
    @Transient
    public int asInt(){
        return (Integer) value.intValue();
    }
}
