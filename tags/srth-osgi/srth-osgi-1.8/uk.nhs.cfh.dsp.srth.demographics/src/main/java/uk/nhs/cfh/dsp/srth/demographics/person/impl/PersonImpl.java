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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.nhs.cfh.dsp.srth.demographics.person.impl;

import org.hibernate.hql.ast.HqlToken;
import uk.nhs.cfh.dsp.srth.demographics.person.Person;

import javax.persistence.*;
import java.util.Calendar;


// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link Person}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 2:07:47 PM
 */

@Entity(name="Person")
@Table(name="PERSON_TABLE")
@DiscriminatorValue(value = "Person")
public class PersonImpl extends AbstractPerson{
    
    /**
     * Instantiates a new person impl.
     */
    public PersonImpl() {
    }

    /**
     * Instantiates a new person impl.
     *
     * @param firstName the first name
     * @param middleName the middle name
     * @param lastName the last name
     * @param dob the dob
     * @param gender the gender
     * @param age the age
     */
    public PersonImpl(String firstName, String middleName, String lastName, Calendar dob, Gender gender, long age) {
        super(firstName, middleName, lastName, dob, gender, age);
    }

    /**
     * Instantiates a new person impl.
     *
     * @param firstName the first name
     * @param lastName the last name
     */
    public PersonImpl(String firstName, String lastName) {
        super(firstName, null, lastName, null, Gender.UNKNOWN, 0);
    }
}
