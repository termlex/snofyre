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

package uk.nhs.cfh.dsp.srth.demographics.person.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.demographics.PersonUtilsService;
import uk.nhs.cfh.dsp.srth.demographics.person.Person;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.demographics.PersonUtilsService}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 1:49:35 PM.
 */

public class PersonUtilsServiceImpl implements PersonUtilsService {

    /** The logger. */
    private static Log logger = LogFactory.getLog(PersonUtilsServiceImpl.class);

    /**
     * Gets the age.
     *
     * @param person the person
     *
     * @return the age
     */
    public synchronized long getAge(Person person){

        Calendar nowCal = Calendar.getInstance();
        Calendar dob = person.getDob();
        // return age relative to now
        return getAgeRelativeToReferencePoint(dob, nowCal);
    }

    /**
     * Gets the age relative to reference point.
     *
     * @param referenceTime the reference time
     * @param testTime the test time
     *
     * @return the age relative to reference point
     */
    public synchronized long getAgeRelativeToReferencePoint(Calendar referenceTime, Calendar testTime){

        if(referenceTime.getTime().after(testTime.getTime()))
        {
            logger.warn("Reference time is after test time");
        }

        return testTime.getTimeInMillis() - referenceTime.getTimeInMillis();
    }


    /**
     * Gets the age years relative to reference point.
     *
     * @param anchorTime the anchor time
     * @param testTime the test time
     *
     * @return the age years relative to reference point
     */
    public synchronized int getAgeYearsRelativeToReferencePoint(Calendar anchorTime, Calendar testTime){

        Date refDate = anchorTime.getTime();
        Date testDate = testTime.getTime();
        int ageYears = 0;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(testDate);
        // lopp through years incrementing age if test time is before reference time
        while(testDate.before(refDate))
        {
            // increment gc
            gc.add(Calendar.YEAR, 1);
            // reset test date
            testDate = gc.getTime();
            // increment age
            ageYears++;
        }

        return ageYears;
    }

    /**
     * Gets the age in years.
     *
     * @param p the p
     *
     * @return the age in years
     */
    public synchronized int getAgeInYears(Person p){

        Calendar dob = p.getDob();

        return getAgeYearsRelativeToReferencePoint(Calendar.getInstance(), dob);
    }
}
