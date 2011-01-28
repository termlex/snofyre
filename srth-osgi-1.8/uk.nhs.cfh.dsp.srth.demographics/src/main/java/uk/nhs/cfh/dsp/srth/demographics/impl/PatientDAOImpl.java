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
package uk.nhs.cfh.dsp.srth.demographics.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.demographics.person.Patient;
import uk.nhs.cfh.dsp.srth.demographics.person.impl.PatientImpl;

import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.demographics.PatientDAO}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2010 at 3:31:32 PM
 */
public class PatientDAOImpl implements PatientDAO {

    /** The hibernate template. */
    private HibernateTemplate hibernateTemplate;
    
    /** The session factory. */
    private SessionFactory sessionFactory;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(PatientDAOImpl.class);

    /**
     * Instantiates a new patient dao impl.
     *
     * @param sessionFactory the session factory
     */
    public PatientDAOImpl(SessionFactory sessionFactory) {
        if (sessionFactory != null)
        {
            this.sessionFactory = sessionFactory;
            hibernateTemplate = new HibernateTemplate(sessionFactory);
        }
        else {
            throw new IllegalArgumentException("Session factory passed can not be null.");
        }
    }

    /**
     * empty no args constructor for IOC.
     */
    public PatientDAOImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientDAO#savePatient(uk.nhs.cfh.dsp.srth.demographics.person.Patient)
     */
    public void savePatient(final Patient patient){
        hibernateTemplate.saveOrUpdate(patient);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientDAO#deletePatient(uk.nhs.cfh.dsp.srth.demographics.person.Patient)
     */
    public void deletePatient(final Patient patient){
        hibernateTemplate.delete("Patient", patient);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientDAO#findAllPatients()
     */
    public Collection<Patient> findAllPatients(){
        return hibernateTemplate.loadAll(PatientImpl.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientDAO#findPatient(java.lang.String)
     */
    public Patient findPatient(String patientId){
        try
        {
            Long id = Long.parseLong(patientId);
            return findPatient(id);
        }
        catch (NumberFormatException e) {
            logger.warn("Nested exception is : " + e.fillInStackTrace().getMessage());
            return null;
        }
    }

    public void flushCache(){
        hibernateTemplate.flush();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientDAO#findPatient(long)
     */
    public Patient findPatient(final long id){
        List<Patient> results = hibernateTemplate.find("from Patient p where p.id = ?", id);
        return checkAndReturnResult(results);
    }

    /**
     * Check and return result.
     *
     * @param results the results
     * @return the patient
     */
    private Patient checkAndReturnResult(List<Patient> results) {
        if(results.size() > 1)
        {
            logger.info("More than one matching patient records found for object");
            return results.get(0);
        }
        else if(results.size() == 1)
        {
            return results.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    public synchronized void setSessionFactory(SessionFactory sessionFactory) {
        if (sessionFactory != null)
        {
            this.sessionFactory = sessionFactory;
            hibernateTemplate = new HibernateTemplate(sessionFactory);
        }
        else {
            throw new IllegalArgumentException("Session factory passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientDAO#getTotalPatientCountInDatabase()
     */
    public long getTotalPatientCountInDatabase(){
        List result = hibernateTemplate.find("select count(*) from Patient p");
        return Long.parseLong(result.get(0).toString());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientDAO#getNextPatientId()
     */
    public long getNextPatientId(){
        long totalCount = getTotalPatientCountInDatabase();
        if(totalCount == 0)
        {
            return 1;
        }
        else
        {
            List result = hibernateTemplate.find("select max(id) from Patient p");
            long maxCount = Long.parseLong(result.get(0).toString());
            return maxCount+1;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientDAO#deleteAllRecords()
     */
    public void deleteAllRecords(){
        this.hibernateTemplate.deleteAll(this.findAllPatients());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.demographics.PatientDAO#getFirstPatientId()
     */
    public long getFirstPatientId(){
        List result = hibernateTemplate.find("select min(id) from Patient p");
        return Long.parseLong(result.get(0).toString());
    }
}
