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
package uk.nhs.cfh.dsp.srth.demographics.person.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.demographics.person.Person;

import javax.persistence.*;
import java.util.Calendar;


// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link Person}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Jan 7, 2009 at 10:08:07 PM
 */

@MappedSuperclass
public abstract class AbstractPerson implements Person {

    /** The first name. */
    private String firstName;

    /** The middle name. */
    private String middleName;

    /** The last name. */
    private String lastName;

    /** The dob. */
    private Calendar dob;

    /** The gender. */
    private Gender gender;

    /** The age. */
    private long age;

    /** The age years. */
    private int ageYears;

    /** The logger. */
    private static Log logger = LogFactory.getLog(AbstractPerson.class);

    /** The id. */
    private long id;

    /**
     * empty no args constructor for persistence.
     */
    protected AbstractPerson(){
    }

    protected AbstractPerson(long id) {
        this.id = id;
    }

    /**
     * Instantiates a new abstract person.
     *
     * @param firstName the first name
     * @param middleName the middle name
     * @param lastName the last name
     * @param dob the dob
     * @param gender the gender
     * @param age the age
     */
    protected AbstractPerson(String firstName, String middleName, String lastName,
                             Calendar dob, Gender gender, long age) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.age = age;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#getFirstName()
      */
    /**
     * Gets the first name.
     *
     * @return the first name
     */
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#setFirstName(java.lang.String)
      */
    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#getMiddleName()
      */
    /**
     * Gets the middle name.
     *
     * @return the middle name
     */
    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#setMiddleName(java.lang.String)
      */
    /**
     * Sets the middle name.
     *
     * @param middleName the new middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#getLastName()
      */
    /**
     * Gets the last name.
     *
     * @return the last name
     */
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#setLastName(java.lang.String)
      */
    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /* (non-Javadoc)
    * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#getDob()
    */
    /**
     * Gets the dob.
     *
     * @return the dob
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getDob() {
        return dob;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#setDob(java.util.Calendar)
      */
    /**
     * Sets the dob.
     *
     * @param dob the new dob
     */
    public void setDob(Calendar dob) {
        this.dob = dob;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#getGender()
      */
    /**
     * Gets the gender.
     *
     * @return the gender
     */
    @Enumerated(value= EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#setGender(uk.nhs.cfh.dsp.srth.demographics.person.Person.Gender)
      */
    /**
     * Sets the gender.
     *
     * @param gender the new gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#getAge()
      */
    /**
     * Gets the age.
     *
     * @return the age
     */
    @Column(name = "age")
    public long getAge() {
        return age;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#setAge(long)
      */
    /**
     * Sets the age.
     *
     * @param age the new age
     */
    public void setAge(long age) {
        this.age = age;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#getAgeYears()
      */
    /**
     * Gets the age years.
     *
     * @return the age years
     */
    @Column(name = "age_in_years")
    public int getAgeYears() {
        return ageYears;
    }

    /* (non-Javadoc)
      * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#setAgeYears(int)
      */
    /**
     * Sets the age years.
     *
     * @param ageYears the new age years
     */
    public void setAgeYears(int ageYears) {
        this.ageYears = ageYears;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#getId()
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.person.Person#setId(long)
     */
    public void setId(long id) {
        this.id = id;
    }
}
