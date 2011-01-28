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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint;

import uk.nhs.cfh.dsp.srth.query.model.om.error.InvalidConstraintValueException;

// TODO: Auto-generated Javadoc
/**
 * An interface object that represents a criterion that can be used to refine a.
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression}.
 * on a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryComponentExpression}
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 7, 2009 at 2:36:29 PM
 * <br>
 */
public interface Constraint {

    /**
     * Gets the type.
     *
     * @return the type
     */
    ConstraintType getType();

    /**
     * Sets the type.
     *
     * @param constraintType the new type
     */
    void setType(ConstraintType constraintType);

    /**
     * Sets the value.
     *
     * @param value the new value
     *
     * @throws InvalidConstraintValueException the invalid constraint value exception
     */
    void setValue(ConstraintValue<?> value) throws InvalidConstraintValueException;

    /**
     * Gets the value.
     *
     * @return the value
     */
    ConstraintValue<?> getValue();

    /**
     * Gets the dimension vocabulary.
     *
     * @return the dimension vocabulary
     */
    ConstraintDimensionVocabulary getDimensionVocabulary();

    /**
     * Sets the dimension vocabulary.
     *
     * @param dimensionVocabulary the new dimension vocabulary
     */
    void setDimensionVocabulary(ConstraintDimensionVocabulary dimensionVocabulary);

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    void setName(String name);
}
