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
package uk.nhs.cfh.dsp.srth.demographics;

import uk.nhs.cfh.dsp.srth.demographics.person.Person;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a service that implements some utility methods.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 5, 2010 at 12:47:05 PM
 */
public interface PersonUtilsService {

    /**
     * Gets the age.
     *
     * @param person the person
     * @return the age
     */
    long getAge(Person person);

    /**
     * Gets the age relative to reference point.
     *
     * @param referenceTime the reference time
     * @param testTime the test time
     * @return the age relative to reference point
     */
    long getAgeRelativeToReferencePoint(Calendar referenceTime, Calendar testTime);

    /**
     * Gets the age years relative to reference point.
     *
     * @param anchorTime the anchor time
     * @param testTime the test time
     * @return the age years relative to reference point
     */
    int getAgeYearsRelativeToReferencePoint(Calendar anchorTime, Calendar testTime);

    /**
     * Gets the age in years.
     *
     * @param p the p
     * @return the age in years
     */
    int getAgeInYears(Person p);
}
