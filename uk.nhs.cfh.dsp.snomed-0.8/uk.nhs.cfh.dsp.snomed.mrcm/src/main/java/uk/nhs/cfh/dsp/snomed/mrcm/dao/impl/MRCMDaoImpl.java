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
package uk.nhs.cfh.dsp.snomed.mrcm.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao;
import uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint;
import uk.nhs.cfh.dsp.snomed.mrcm.om.NameIdEntity;
import uk.nhs.cfh.dsp.snomed.mrcm.om.impl.MRCMConstraintImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 11:06:36 AM
 */
public class MRCMDaoImpl implements MRCMDao {

    /** The hibernate template. */
    private HibernateTemplate hibernateTemplate;
    
    /** The session factory. */
    private SessionFactory sessionFactory;

    /**
     * Instantiates a new mRCM dao impl.
     *
     * @param sessionFactory the session factory
     */
    public MRCMDaoImpl(SessionFactory sessionFactory) {
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
     * Instantiates a new mRCM dao impl.
     */
    public MRCMDaoImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao#saveConstraint(uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint)
     */
    public synchronized void saveConstraint(MRCMConstraint constraint){
        hibernateTemplate.saveOrUpdate(constraint);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao#deleteConstraint(uk.nhs.cfh.dsp.snomed.mrcm.om.MRCMConstraint)
     */
    public synchronized void deleteConstraint(MRCMConstraint constraint){
        hibernateTemplate.delete(constraint);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao#deleteAllConstraints()
     */
    public synchronized void deleteAllConstraints(){
        hibernateTemplate.deleteAll(this.findAllConstraints());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao#findAllConstraints()
     */
    public synchronized List<MRCMConstraint> findAllConstraints(){
        return hibernateTemplate.loadAll(MRCMConstraintImpl.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao#findConstraint(java.lang.String, java.lang.String)
     */
    public synchronized List<MRCMConstraint> findConstraint(String sourceId, String attributeId){
        return hibernateTemplate.findByNamedParam("from MRCMConstraint m where m.sourceId = :scId " +
                "and m.attributeId = :aId",
                new String[]{"scId", "aId"},
                new Object[] {sourceId, attributeId});
    };

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao#getSanctionedValues(java.lang.String, java.lang.String)
     */
    public synchronized Set<String> getSanctionedValues(String sourceId, String attributeId){
        Set<String> valueIds = new HashSet<String>();
        for(MRCMConstraint constraint : findConstraint(sourceId, attributeId))
        {
            valueIds.add(constraint.getValueId());
        }

        return valueIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao#getSanctionedAttributesOnConcept(java.lang.String)
     */
    public synchronized Set<String> getSanctionedAttributesOnConcept(String sourceId){
        Set<String> attributes = new HashSet<String>();
        List<MRCMConstraint> result = hibernateTemplate.find("from MRCMConstraint m where m.sourceId = ?", sourceId);
        for(MRCMConstraint constraint : result)
        {
            attributes.add(constraint.getAttributeId());
        }

        return attributes;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.mrcm.dao.MRCMDao#getSanctionedValuesOnConcept(java.lang.String)
     */
    public synchronized Set<String> getSanctionedValuesOnConcept(String sourceId){
        Set<String> valueIds = new HashSet<String>();
        List<MRCMConstraint> result = hibernateTemplate.find("from MRCMConstraint m where m.sourceId = ?", sourceId);
        for(MRCMConstraint constraint : result)
        {
            valueIds.add(constraint.getValueId());
        }

        return valueIds;
    }

    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
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
