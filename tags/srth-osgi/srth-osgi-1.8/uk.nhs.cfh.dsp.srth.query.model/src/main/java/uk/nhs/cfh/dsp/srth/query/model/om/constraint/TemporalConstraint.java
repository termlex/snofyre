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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint;

import uk.nhs.cfh.dsp.srth.query.model.om.error.InvalidConstraintValueException;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint} whose value set is
 * restricted to time.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 1:23:46 PM
 */
public interface TemporalConstraint extends Constraint {


    /**
     * Gets the time.
     * 
     * @return the time
     */
    Calendar getTime();

    /**
     * Sets the time.
     * 
     * @param time the new time
     * 
     * @throws InvalidConstraintValueException the invalid constraint value exception
     */
    void setTime(Calendar time) throws InvalidConstraintValueException;
}
