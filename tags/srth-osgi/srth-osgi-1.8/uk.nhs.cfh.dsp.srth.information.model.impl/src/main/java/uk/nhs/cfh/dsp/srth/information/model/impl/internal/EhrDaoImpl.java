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
package uk.nhs.cfh.dsp.srth.information.model.impl.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import uk.nhs.cfh.dsp.srth.information.model.impl.EhrDao;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.EHRImpl;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;

import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.information.model.impl.EhrDao}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2010 at 11:32:58 AM
 */
public class EhrDaoImpl implements EhrDao {

    /** The hibernate template. */
    private HibernateTemplate hibernateTemplate;
    
    /** The session factory. */
    private SessionFactory sessionFactory;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(EhrDaoImpl.class);

    /**
     * Instantiates a new ehr dao impl.
     *
     * @param sessionFactory the session factory
     */
    public EhrDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * Instantiates a new ehr dao impl.
     */
    public EhrDaoImpl() {
    }

    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    public synchronized void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.EhrDao#saveEHR(uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR)
     */
    public synchronized void saveEHR(EHR ehr){
        hibernateTemplate.saveOrUpdate(ehr);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.EhrDao#deleteEHR(uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR)
     */
    public synchronized void deleteEHR(EHR ehr){
        hibernateTemplate.delete(ehr);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.EhrDao#findAll()
     */
    public synchronized Collection<EHR> findAll(){
        return hibernateTemplate.loadAll(EHRImpl.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.EhrDao#findById(java.lang.String)
     */
    public synchronized EHR findById(long patientId){
        List<EHR> result =hibernateTemplate.find("from EHR e where e.patientId = ?", patientId);
        return checkAndReturnResult(result);
    }

    /**
     * Check and return result.
     *
     * @param result the result
     * @return the eHR
     */
    private synchronized EHR checkAndReturnResult(List<EHR> result){
        if(result.size() == 1)
        {
            return result.get(0);
        }
        else if(result.size() > 1)
        {
            logger.warn("Found more than one match. Returning first match found.");
            return result.get(0);
        }
        else
        {
            logger.warn("No entries found. Returning null");
            return null;
        }
    }
}
