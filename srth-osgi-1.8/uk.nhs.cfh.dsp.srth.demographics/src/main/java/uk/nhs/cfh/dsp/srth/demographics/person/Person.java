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
package uk.nhs.cfh.dsp.srth.demographics.person;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * An interface representing a person.
 *
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2009 at 10:08:07 PM
 */
public interface Person {

    /**
     * Gets the id.
     *
     * @return the id
     */
    long getId();

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    void setId(long id);

    public enum Gender{ MALE, FEMALE, UNKNOWN}

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    String getFirstName();

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    void setFirstName(String firstName);

    /**
     * Gets the middle name.
     *
     * @return the middle name
     */
    String getMiddleName();

    /**
     * Sets the middle name.
     *
     * @param middleName the new middle name
     */
    void setMiddleName(String middleName);

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    String getLastName();

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    void setLastName(String lastName);

    /**
     * Gets the dob.
     *
     * @return the dob
     */
    Calendar getDob();

    /**
     * Sets the dob.
     *
     * @param dob the new dob
     */
    void setDob(Calendar dob);

    /**
     * Gets the gender.
     *
     * @return the gender
     */
    Gender getGender();

    /**
     * Sets the gender.
     *
     * @param gender the new gender
     */
    void setGender(Gender gender);

    /**
     * Gets the age.
     *
     * @return the age
     */
    long getAge();

    /**
     * Sets the age.
     *
     * @param age the new age
     */
    void setAge(long age);

    /**
     * Gets the age years.
     *
     * @return the age years
     */
    int getAgeYears();

    /**
     * Sets the age years.
     *
     * @param ageYears the new age years
     */
    void setAgeYears(int ageYears);
}