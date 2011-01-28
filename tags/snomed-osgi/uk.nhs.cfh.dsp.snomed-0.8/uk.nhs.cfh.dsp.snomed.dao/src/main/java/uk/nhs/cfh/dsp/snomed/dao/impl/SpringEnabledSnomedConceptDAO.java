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
package uk.nhs.cfh.dsp.snomed.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.error.ConceptNotFoundException;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl;

import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO} that
 * uses Spring to handle {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}s.
 *
 * @scr.component immediate=“true”
 * @scr.service 
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 9, 2010 at 11:47:44 PM
 */
public class SpringEnabledSnomedConceptDAO implements TerminologyConceptDAO{

    /** The hibernate template. */
    private HibernateTemplate hibernateTemplate;
    
    /** The session factory. */
    private SessionFactory sessionFactory;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SpringEnabledSnomedConceptDAO.class);

    /**
     * Instantiates a new spring enabled snomed concept dao.
     *
     * @param sessionFactory the session factory
     */
    public SpringEnabledSnomedConceptDAO(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            this.sessionFactory = sessionFactory;
            hibernateTemplate = new HibernateTemplate(sessionFactory);
        }
        else {
            throw new IllegalArgumentException("Session factory passed can not be null.");
        }
    }

    /**
     * Empty constructor for IOC.
     */
    public SpringEnabledSnomedConceptDAO() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#saveTerminologyConcept(uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept)
     */
    public void saveTerminologyConcept(final TerminologyConcept concept) {
        hibernateTemplate.saveOrUpdate(concept);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#deleteConcept(uk.nhs.cfh.dsp.snomed.objectmodel.TerminologyConcept)
     */
    public void deleteConcept(TerminologyConcept concept){
        hibernateTemplate.delete(concept);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getAllConceptsInTerminology()
     */
    public Collection<TerminologyConcept> getAllConceptsInTerminology(){
        return hibernateTemplate.loadAll(SnomedConceptImpl.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getConceptCount()
     */
    public int getConceptCount(){
        List result = hibernateTemplate.find("select count(*) from Concept");
        return Integer.parseInt(result.get(0).toString());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getTerminologyConcept(java.lang.String)
     */
    public TerminologyConcept getTerminologyConcept(final String conceptID) throws ConceptNotFoundException {
        List<TerminologyConcept> concepts =
                hibernateTemplate.find("from Concept c where c.conceptID = ? ", conceptID);
        return checkAndReturnConcept(concepts);
    }

    /**
     * Check and return concept.
     *
     * @param concepts the concepts
     * @return the terminology concept
     */
    private TerminologyConcept checkAndReturnConcept(List<TerminologyConcept> concepts) {
        if(concepts.size() == 1)
        {
            return concepts.get(0);
        }
        else if(concepts.size() >1)
        {
            logger.info("Found more than one concept with concept id. Returning the first one.");
            return concepts.get(0);
        }
        else
        {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getMatchingConcepts(java.lang.String)
     */
    public Collection<TerminologyConcept> getMatchingConcepts(final String label) {
        return hibernateTemplate.find("from Concept c where c.preferredLabel like ?", label);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO#getCurrentConceptForConcept(java.lang.String)
     */
    public TerminologyConcept getCurrentConceptForConcept(String conceptId) throws ConceptNotFoundException {
        throw new UnsupportedOperationException("This method has not been implemented in : "+getClass());
    }

    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    public synchronized void setSessionFactory(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            this.sessionFactory = sessionFactory;
            hibernateTemplate = new HibernateTemplate(sessionFactory);
        }
        else {
            throw new IllegalArgumentException("Session factory passed can not be null.");
        }
    }
}
