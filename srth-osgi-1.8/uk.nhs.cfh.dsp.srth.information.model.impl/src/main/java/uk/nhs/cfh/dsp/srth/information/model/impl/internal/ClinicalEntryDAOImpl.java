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
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;
import uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.AbstractBoundClinicalEntry;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.ClinicalFeatureEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalEntity;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 2:29:15 PM
 */
public class ClinicalEntryDAOImpl implements ClinicalEntryDAO {

    /** The hibernate template. */
    private HibernateTemplate hibernateTemplate;
    
    /** The session factory. */
    private SessionFactory sessionFactory;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ClinicalEntryDAOImpl.class);
    private static final String UUIDS = "uuids";
    private static final String BOUND_VALUE = "boundValue";
    private static final String TYPE = "type";

    /**
     * Instantiates a new clinical entry dao impl.
     *
     * @param sessionFactory the session factory
     */
    public ClinicalEntryDAOImpl(SessionFactory sessionFactory) {
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
     * Empty no args constructor for IOC.
     */
    public ClinicalEntryDAOImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#saveEntry(uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry)
     */
    public synchronized void saveEntry(ClinicalEntry entry){
        hibernateTemplate.saveOrUpdate(entry);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#deleteEntry(uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry)
     */
    public synchronized void deleteEntry(ClinicalEntry entry){
        hibernateTemplate.delete(entry);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#deleteAllEntries()
     */
    public synchronized void deleteAllEntries(){
        hibernateTemplate.deleteAll(this.findAllEntries());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findAllEntries()
     */
    public synchronized List<ClinicalEntry> findAllEntries(){
        return hibernateTemplate.loadAll(AbstractBoundClinicalEntry.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findEntryWithCloseToUserUuId(java.lang.String)
     */
    public synchronized ClinicalEntry findEntryWithCloseToUserUuId(String ctuId){
        logger.debug("ctuId = " + ctuId);
        return findEntryWithCloseToUserUuId(UUID.fromString(ctuId));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findEntryWithCloseToUserUuId(java.lang.String)
     */
    public synchronized ClinicalEntry findEntryWithCloseToUserUuId(UUID uuid){
        logger.debug("uuid = " + uuid);
        List<ClinicalEntry> result = hibernateTemplate.find("from Clinical_Entry e where e.uuid = ?", uuid);
        if(result.size() == 1)
        {
            return result.get(0);
        }
        else if(result.size() > 1)
        {
            logger.warn("Found more than one match with uid : "+uuid+".\n" +
                    "Returning the first match found.");
            return result.get(0);
        }
        else
        {
            logger.warn("No entries found with uuid : "+uuid+". Returning null");
            return null;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findEntriesUsingCriteria(org.hibernate.criterion.DetachedCriteria)
     */
    public synchronized List<ClinicalEntry> findEntriesUsingCriteria(DetachedCriteria detachedCriteria){
        return hibernateTemplate.findByCriteria(detachedCriteria);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findEntriesWithCTUuuids(java.util.Collection)
     */
    public synchronized List<ClinicalEntry> findEntriesWithCTUuuids(Collection<UUID> ctuUUIDs){

        String uuids = "";
        int counter = 0;
        for(UUID ctuUUID : ctuUUIDs)
        {
            if(counter ==0)
            {
                uuids = "'"+ctuUUID.toString()+"'";
            }
            else
            {
                uuids = uuids+" , "+"'"+ctuUUID.toString()+"'";
            }

            counter ++;
        }

       return hibernateTemplate.find("from Clinical_Entry e where e.uuid in ( "+ uuids+ ")");
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findFeaturesWithValueLessThanExclusive(java.lang.String[], double)
     */
    public synchronized List<ClinicalFeatureEntry> findFeaturesWithValueLessThanExclusive(String[] uuids, double value){
       return hibernateTemplate.findByNamedParam("from Feature_Entry e where e.value < :boundValue and e.uuid = :uuids",
               new String[] {TYPE, BOUND_VALUE, UUIDS},
               new Object[] {ClinicalEntity.Type.FEATURE.name(), value, uuids});
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findFeaturesWithValueGreaterThanExclusive(java.lang.String[], double)
     */
    public synchronized List<ClinicalFeatureEntry> findFeaturesWithValueGreaterThanExclusive(String[] uuids, double value){
       return hibernateTemplate.findByNamedParam("from Feature_Entry e where e.value > :boundValue and e.uuid = :uuids",
               new String[] {TYPE, BOUND_VALUE, UUIDS},
               new Object[] {ClinicalEntity.Type.FEATURE.name(), value, uuids});
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findFeaturesWithValueLessThanInclusive(java.lang.String, java.lang.String[], double)
     */
    public synchronized List<ClinicalFeatureEntry> findFeaturesWithValueLessThanInclusive(String entryType, String[] uuids, double value){
       return hibernateTemplate.findByNamedParam("from Feature_Entry e where e.value <= :boundValue and e.uuid = :uuids",
               new String[] {TYPE, BOUND_VALUE, UUIDS},
               new Object[] {ClinicalEntity.Type.FEATURE.name(), value, uuids});
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findFeaturesWithValueGreaterThanInclusive(java.lang.String[], double)
     */
    public synchronized List<ClinicalFeatureEntry> findFeaturesWithValueGreaterThanInclusive(String[] uuids, double value){
       return hibernateTemplate.findByNamedParam("from Feature_Entry e where  e.value >= :boundValue and e.uuid = :uuids",
               new String[] {TYPE, BOUND_VALUE, UUIDS},
               new Object[] {ClinicalEntity.Type.FEATURE.name(), value, uuids});
    }
    
    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.ClinicalEntryDAO#findFeaturesWithValuesBetween(java.lang.String[], double, double)
     */
    public synchronized List<ClinicalFeatureEntry> findFeaturesWithValuesBetween(String[] uuids, double upperBound, double lowerBound){
       return hibernateTemplate.findByNamedParam("from Feature_Entry e where  e.value between :lBound and :uBound" +
                        " and e.uuid = :uuids",
               new String[] {TYPE, "lBound", "uBound", UUIDS},
               new Object[] {ClinicalEntity.Type.FEATURE.name(), lowerBound, upperBound, uuids});
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
}
