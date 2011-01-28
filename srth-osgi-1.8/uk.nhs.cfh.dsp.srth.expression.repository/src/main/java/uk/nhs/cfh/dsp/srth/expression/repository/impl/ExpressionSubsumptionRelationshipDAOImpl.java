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
package uk.nhs.cfh.dsp.srth.expression.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.hql.ast.HqlToken;
import org.springframework.orm.hibernate3.HibernateTemplate;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionSubsumptionRelationshipImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 14, 2010 at 11:56:32 PM
 */
public class ExpressionSubsumptionRelationshipDAOImpl implements ExpressionSubsumptionRelationshipDAO {

    /** The hql token. */
    private HqlToken hqlToken;
    
    /** The hibernate template. */
    private HibernateTemplate hibernateTemplate;
    
    /** The session factory. */
    private SessionFactory sessionFactory;
    
    /** The initial size. */
    private static int initialSize = 20;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ExpressionSubsumptionRelationshipDAOImpl.class);

    /**
     * no args constructor for IOC.
     */
    public ExpressionSubsumptionRelationshipDAOImpl() {
    }

    /**
     * Instantiates a new expression subsumption relationship dao impl.
     *
     * @param sessionFactory the session factory
     */
    public ExpressionSubsumptionRelationshipDAOImpl(SessionFactory sessionFactory) {
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
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO#saveRelationship(uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship)
     */
    public synchronized void saveRelationship(ExpressionSubsumptionRelationship relationship){
        hibernateTemplate.saveOrUpdate(relationship);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO#deleteRelationship(uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionSubsumptionRelationship)
     */
    public synchronized void deleteRelationship(ExpressionSubsumptionRelationship relationship){
        hibernateTemplate.delete(relationship);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO#deleteAll()
     */
    public synchronized void deleteAll(){
        hibernateTemplate.deleteAll(this.findAllRelationships());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO#findAllRelationships()
     */
    public synchronized List<ExpressionSubsumptionRelationship> findAllRelationships(){
        return hibernateTemplate.loadAll(ExpressionSubsumptionRelationshipImpl.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO#findRelationship(java.lang.String, java.lang.String)
     */
    public synchronized ExpressionSubsumptionRelationship findRelationship(UUID superTypeUuid, UUID subtypeUuid){
        List<ExpressionSubsumptionRelationship> result =
                hibernateTemplate.findByNamedParam("from ExpressionSubsumptionRelationship e " +
                        "where e.superTypeExpressionUUuid = :supId and e.subTypeExpressionUuid = :subId",
                        new String[] {"supId", "subId"},
                        new Object[] {superTypeUuid, subtypeUuid});
        if(result.size() == 1)
        {
            return result.get(0);
        }
        else if(result.size() > 1)
        {
            logger.warn("Found more than one matches for relationship with suptype id : "+superTypeUuid+" and " +
                    "subtypeid : "+subtypeUuid+". Returning first one found.");
            return result.get(0);
        }
        else
        {
            logger.warn("No matches for relationship with suptype id : "+superTypeUuid+" and " +
                    "subtypeid : "+subtypeUuid+". Returning null.");
            return null;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO#getRelationshipsCount()
     */
    public synchronized int getRelationshipsCount(){
        List result = hibernateTemplate.find("select count(*) from ExpressionSubsumptionRelationship e ");
        return Integer.parseInt(String.valueOf(result.get(0)));
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO#getAllSubTypes(java.lang.String)
     */
    public synchronized Set<UUID> getAllSubTypes(UUID superTypeUuid){
        List<ExpressionSubsumptionRelationship> result = hibernateTemplate.find("from ExpressionSubsumptionRelationship e " +
                "where e.superTypeExpressionUUuid = ?", superTypeUuid);

        // create set with ids of relationships in result
        Set<UUID> subTypeIds = new HashSet<UUID>(initialSize);
        for(ExpressionSubsumptionRelationship r : result)
        {
            subTypeIds.add(r.getSubTypeExpressionUuid());
        }
        return subTypeIds;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO#getAllSuperTypes(java.lang.String)
     */
    public synchronized Set<UUID> getAllSuperTypes(UUID subTypeUuid){
        List<ExpressionSubsumptionRelationship> result = hibernateTemplate.find("from ExpressionSubsumptionRelationship e " +
                "where e.subTypeExpressionUuid = ?", subTypeUuid);

        // create set with ids of relationships in result
        Set<UUID> superTypeIds = new HashSet<UUID>(initialSize);
        for(ExpressionSubsumptionRelationship r : result)
        {
            superTypeIds.add(r.getSuperTypeExpressionUUuid());
        }
        return superTypeIds;
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
