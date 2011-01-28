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
import uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService;
import uk.nhs.cfh.dsp.srth.demographics.person.Patient;
import uk.nhs.cfh.dsp.srth.demographics.person.Person;
import uk.nhs.cfh.dsp.srth.demographics.person.impl.PatientImpl;

import java.util.*;


// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created onn Feb 23, 2009 at 10:14:11 AM
 */
public class PatientUtilsServiceImpl extends PersonUtilsServiceImpl implements PatientUtilsService {

    /** The logger. */
    private static Log logger = LogFactory.getLog(PatientUtilsServiceImpl.class);
    
    /** The random. */
    private static Random random = new Random();

    /**
     * Instantiates a new patient utils service impl.
     */
    public PatientUtilsServiceImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#createPatient(String, Person.Gender, Calendar)
     */
    public Patient createPatient(Person.Gender gender, Calendar dob){

        Patient patient = new PatientImpl();
        patient.setDob(dob);
        patient.setGender(gender);

        return patient;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#createRandomPatient(java.lang.String, java.util.Calendar)
     */
    public Patient createRandomPatient(Calendar dob){

        // use a random value for gender
        int nextNum = random.nextInt();
        if(nextNum >=0)
        {
            return createPatient(Person.Gender.FEMALE, dob);
        }
        else
        {
            return createPatient(Person.Gender.MALE, dob);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#createRandomPatient(java.lang.String, int)
     */
    public Patient createRandomPatient(int minAge){

        GregorianCalendar gc = new GregorianCalendar(1900, 0, 1);
        long centuryStart = gc.getTimeInMillis();
        // rewind time to now - 1
        GregorianCalendar gc2 = new GregorianCalendar();
        gc2.setTime(Calendar.getInstance().getTime());
        gc2.add(Calendar.YEAR, -minAge);
        long todayMinusMinYears = gc2.getTimeInMillis();

        // use a random value to generate values between century start and today
        long randomTime = (long) (Math.random()*(todayMinusMinYears-centuryStart)) + centuryStart;
        // use random time to set calendar
        gc.setTimeInMillis(randomTime);

        // create and return patient with dob as random time
        return createRandomPatient(gc);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#getDateRelativeToPatientAge(uk.nhs.cfh.dsp.srth.demographics.person.Patient, int, int, int)
     */
    public Calendar getDateRelativeToPatientAge(Patient p, int ageYears, int ageMonths, int ageDays){

        GregorianCalendar gc = new GregorianCalendar();
        // get dob of patient
        gc.setTime(p.getDob().getTime());
        // forward time using given parameters
        gc.add(Calendar.YEAR, ageYears);
        gc.add(Calendar.MONTH, ageMonths);
        gc.add(Calendar.DATE, ageDays);

        return gc;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#getRandomDateInTimeRange(java.util.Calendar, int, int, int, int, int, int)
     */
    public Calendar getRandomDateInTimeRange(Calendar anchor,
                                                          int yearUpperBound, int yearLowerBound,
                                                          int monthUpperBound, int monthLowerBound,
                                                          int dayUpperBound, int dayLowerBound){

        int randomYear = (int)(Math.random()*(yearUpperBound-yearLowerBound)) + yearLowerBound;
        int randomMonth = (int)(Math.random()*(monthUpperBound-monthLowerBound)) + monthLowerBound;
        int randomDay = (int)(Math.random()*(dayUpperBound-dayLowerBound)) + dayLowerBound;

        // debugging output
        if(logger.isDebugEnabled())
        {
            logger.debug("Value of anchor year: " + anchor.get(Calendar.YEAR));
            logger.debug("Value of anchor month: " + anchor.get(Calendar.MONTH));
            logger.debug("Value of anchor date: " + anchor.get(Calendar.DATE));
            logger.debug("Value of randomYear : " + randomYear);
            logger.debug("Value of randomMonth : " + randomMonth);
            logger.debug("Value of randomDay : " + randomDay);
        }

        // forward calendar by year and month
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(anchor.getTime());
        calendar.add(Calendar.YEAR, -randomYear);
        calendar.add(Calendar.MONTH, -randomMonth);
        calendar.add(Calendar.DATE, -randomDay);
        if (logger.isDebugEnabled()) {
            logger.debug("Value of calendar.get(year) : "
                    + calendar.get(Calendar.YEAR));
        }
        return calendar;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#getRandomDateInTimeRangeInFuture(java.util.Calendar, int, int, int, int, int, int)
     */
    public Calendar getRandomDateInTimeRangeInFuture(Calendar anchor,
                                                                  int yearUpperBound, int yearLowerBound,
                                                                  int monthUpperBound, int monthLowerBound,
                                                                  int dayUpperBound, int dayLowerBound){

        int randomYear = (int)(Math.random()*(yearUpperBound-yearLowerBound)) + yearLowerBound;
        int randomMonth = (int)(Math.random()*(monthUpperBound-monthLowerBound)) + monthLowerBound;
        int randomDay = (int)(Math.random()*(dayUpperBound-dayLowerBound)) + dayLowerBound;

        // forward calendar by year and month
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(anchor.getTime());
        calendar.add(Calendar.YEAR, randomYear);
        calendar.add(Calendar.MONTH, randomMonth);
        calendar.add(Calendar.DATE, randomDay);
        logger.debug("Value of calendar.get(year) : " + calendar.get(Calendar.YEAR));

        return calendar;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#getSensibleRandomDateInTimeRange(java.util.Calendar, int, int, int, int, int, int)
     */
    public Calendar getSensibleRandomDateInTimeRange(
            Calendar anchor,
            int yearUpperBound, int yearLowerBound,
            int monthUpperBound, int monthLowerBound,
            int dayUpperBound, int dayLowerBound){

        Calendar randomCal = getRandomDateInTimeRange(anchor, yearUpperBound, yearLowerBound, monthUpperBound, monthLowerBound, dayUpperBound, dayLowerBound);
        // check if this random cal is before today, if it is not then it is a future event n not realistic
        Date randomTime = randomCal.getTime();
        Date anchorTime = anchor.getTime();
        while(randomTime.after(anchorTime))
        {
            randomCal = getRandomDateInTimeRange(anchor, yearUpperBound, yearLowerBound, monthUpperBound, monthLowerBound, dayUpperBound, dayLowerBound);
            // reset random time
            randomTime = randomCal.getTime();
        }

        return randomCal;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#getSensibleRandomDateInTimeRangeRelativeToPatient(uk.nhs.cfh.dsp.srth.demographics.person.Patient, int, int, int, int, int, int)
     */
    public Calendar getSensibleRandomDateInTimeRangeRelativeToPatient(Patient patient,
                                                                                   int yearUpperBound, int yearLowerBound,
                                                                                   int monthUpperBound, int monthLowerBound,
                                                                                   int dayUpperBound, int dayLowerBound){

        Calendar anchor = Calendar.getInstance();
        /*
           * use time now as anchor point and get a random sensible date
           */
        Calendar randomCal = getSensibleRandomDateInTimeRange(anchor, yearUpperBound, yearLowerBound, monthUpperBound, monthLowerBound, dayUpperBound, dayLowerBound);
        Date randomDate = randomCal.getTime();
        // we set time now to NOW - 1 day
        Date dob = patient.getDob().getTime();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dob);
        // reset time by 1 day
        gc.add(Calendar.DATE, 1);
        dob = gc.getTime();
        while(randomDate.before(dob))
        {
            randomCal = getSensibleRandomDateInTimeRange(anchor, yearUpperBound, yearLowerBound, monthUpperBound, monthLowerBound, dayUpperBound, dayLowerBound);
            // reset random date
            randomDate = randomCal.getTime();
        }

        return randomCal;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#getSensibleRandomDateInTimeRangeRelativeToPatient(uk.nhs.cfh.dsp.srth.demographics.person.Patient, int, int, int, int)
     */
    public Calendar getSensibleRandomDateInTimeRangeRelativeToPatient(Patient patient,
                                                                                   int yearUpperBound, int yearLowerBound,
                                                                                   int monthUpperBound, int monthLowerBound){

        return getSensibleRandomDateInTimeRangeRelativeToPatient(patient, yearUpperBound, yearLowerBound, monthUpperBound, monthLowerBound, 0, 30);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientUtilsService#getSensibleRandomDateInTimeRangeRelativeToPatient(uk.nhs.cfh.dsp.srth.demographics.person.Patient, int, int)
     */
    public Calendar getSensibleRandomDateInTimeRangeRelativeToPatient(Patient patient,
                                                                                   int yearUpperBound, int yearLowerBound){

        return getSensibleRandomDateInTimeRangeRelativeToPatient(patient, yearUpperBound, yearLowerBound, 0, 11, 0, 30);
    }
}
