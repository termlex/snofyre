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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject;
import uk.nhs.cfh.dsp.srth.expression.repository.om.impl.ExpressionMappingObjectImpl;

import java.sql.SQLException;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO}
 *
 * @scr.component immediate=“true”
 * @scr.service
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 11, 2010 at 11:02:32 AM
 */
public class ExpressionMappingObjectDAOImpl implements ExpressionMappingObjectDAO {

    /** The session factory. */
    private SessionFactory sessionFactory;
    
    /** The hibernate template. */
    private HibernateTemplate hibernateTemplate;
    
    /** The Constant NFE_UUID. */
    private static final String NFE_UUID = "normalFormExpressionUuid";
    
    /** The Constant CTU_UUID. */
    private static final String CTU_UUID = "closeToUserExpressionUuid";
    
    /** The Constant NFE_CGF. */
    private static final String NFE_CGF = "normalFormCGForm";
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ExpressionMappingObjectDAOImpl.class);

    /**
     * Instantiates a new expression mapping object dao impl.
     *
     * @param sessionFactory the session factory
     */
    public ExpressionMappingObjectDAOImpl(SessionFactory sessionFactory) {
        if (sessionFactory != null)
        {
            this.sessionFactory = sessionFactory;
            this.hibernateTemplate = new HibernateTemplate(sessionFactory);
        }
        else {
            throw new IllegalArgumentException("Session Factory passed can not be null.");
        }
    }

    /**
     * empty constructor for IOC.
     */
    public ExpressionMappingObjectDAOImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#save(uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject)
     */
    public void save(ExpressionMappingObject object){
        hibernateTemplate.saveOrUpdate(object);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#delete(uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject)
     */
    public void delete(ExpressionMappingObject object){
        hibernateTemplate.delete(object);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#deleteAll()
     */
    public void deleteAll(){
        hibernateTemplate.deleteAll(this.findAll());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#findAll()
     */
    public List<ExpressionMappingObject> findAll(){
        return hibernateTemplate.loadAll(ExpressionMappingObjectImpl.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#findUsingCTUId(java.lang.String)
     */
    public ExpressionMappingObject findUsingCTUId(UUID uuid){
        return checkAndReturnConcept("from ExpressionMappingObject e where e.closeToUserExpressionUuid = ? ", uuid);
    }

    /**
     * This method returns all contained {@link uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject}s,
     * in the repository, but only returns their UUIDs and compositional grammar form of their NFE.
     *
     * @return the list
     */
    public List<ExpressionMappingObject> returnAllAsLiteObjects(){

        List list = this.hibernateTemplate.executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(ExpressionMappingObjectImpl.class);
                ProjectionList projectionList = Projections.projectionList();
                projectionList.add(Projections.property(NFE_UUID));
                projectionList.add(Projections.property(NFE_CGF));
                criteria.setProjection(projectionList);

                return criteria.list();
            }
        });

        return returnLiteObjects(list);
    }

    /**
     * This method returns all distinct contained {@link uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject}s,
     * in the repository, but only returns their UUIDs and compositional grammar form of their NFE.
     *
     * @return the list
     */
    public List<ExpressionMappingObject> returnAllDistinctLiteObjects(){

        List list = this.hibernateTemplate.executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(ExpressionMappingObjectImpl.class);
                ProjectionList projectionList = Projections.projectionList();
                projectionList.add(Projections.distinct(Projections.property(NFE_UUID)));
                projectionList.add(Projections.property(NFE_CGF));
                criteria.setProjection(projectionList);

                return criteria.list();
            }
        });

        return returnLiteObjects(list);
    }

    /**
     * This method returns all distinct {@link uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject}s,
     * in the repository which match the given proximal primitive using SQL like operator,
     * but only returns their UUIDs and compositional grammar form of their NFE.
     *
     * @param proximalPrimitive the proximal primitive
     * @return the list
     */
    public List<ExpressionMappingObject> returnAllMatchingDistinctLiteObjects(final String proximalPrimitive){

        List list = this.hibernateTemplate.executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(ExpressionMappingObjectImpl.class);
                ProjectionList projectionList = Projections.projectionList();
                projectionList.add(Projections.distinct(Projections.property(NFE_UUID)));
                projectionList.add(Projections.property(NFE_CGF));
                criteria.setProjection(projectionList);
                criteria.add(Restrictions.like(NFE_CGF, proximalPrimitive+"%"));

                return criteria.list();
            }
        });

        return returnLiteObjects(list);
    }

    /**
     * Return lite objects.
     *
     * @param list the list
     * @return the list
     */
    private List<ExpressionMappingObject> returnLiteObjects(List list){

        List<ExpressionMappingObject> emos = new ArrayList<ExpressionMappingObject>(list.size());
        Iterator it = list.iterator();
        while(it.hasNext())
        {
            Object[] row = (Object[]) it.next();
            // set values of emo from row values
            ExpressionMappingObject emo = new ExpressionMappingObjectImpl();
//            emo.setNormalFormExpressionUuid(String.valueOf(row[0]));
            emo.setNormalFormExpressionUuid((UUID) row[0]);
            emo.setNormalFormCGForm(String.valueOf(row[1]));
            // add emo to emos
            emos.add(emo);
        }

        return emos;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#getCTUIdsUsingNFEFIds(java.util.Collection)
     */
    public List<UUID> getCTUIdsUsingNFEFIds(final Collection<UUID> nfeUUIDs){

        if(nfeUUIDs.size() <1)
        {
            return Collections.emptyList();
        }
        else
        {
            List list = this.hibernateTemplate.executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Criteria criteria = session.createCriteria(ExpressionMappingObjectImpl.class);
                    ProjectionList projectionList = Projections.projectionList();
                    projectionList.add(Projections.distinct(Projections.property(CTU_UUID)));
                    criteria.setProjection(projectionList);
                    criteria.add(Restrictions.in(NFE_UUID, nfeUUIDs.toArray()));

                    return criteria.list();
                }
            });

            return list;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#findUsingNFEId(java.lang.String)
     */
    public ExpressionMappingObject findUsingNFEId(UUID uuid){
        return checkAndReturnConcept("from ExpressionMappingObject e where e.normalFormExpressionUuid = ? ", uuid);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#findUsingSNFEId(java.lang.String)
     */
    public ExpressionMappingObject findUsingSNFEId(UUID uuid){
        return checkAndReturnConcept("from ExpressionMappingObject e where e.situationNormalFormExpressionUuid = ? ", uuid);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#findUsingCTUCompositionalGrammarForm(java.lang.String)
     */
    public ExpressionMappingObject findUsingCTUCompositionalGrammarForm(String cgForm){
        return checkAndReturnConcept("from ExpressionMappingObject e where e.closeToUserCGForm = ? ", cgForm);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#findUsingNFECompositionalGrammarForm(java.lang.String)
     */
    public ExpressionMappingObject findUsingNFECompositionalGrammarForm(String cgForm){
        return checkAndReturnConcept("from ExpressionMappingObject e where e.normalFormCGForm = ? ", cgForm);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO#findUsingSNFECompositionalGrammarForm(java.lang.String)
     */
    public ExpressionMappingObject findUsingSNFECompositionalGrammarForm(String cgForm){
        return checkAndReturnConcept("from ExpressionMappingObject e where e.situationNormalFormCGForm = ? ", cgForm);
    }

    /**
     * Check and return concept.
     *
     * @param criteria the criteria
     * @param value the value
     * @return the expression mapping object
     */
    private ExpressionMappingObject checkAndReturnConcept(String criteria, Object value){
        List<ExpressionMappingObject> result = hibernateTemplate.find(criteria, value);
        if(result.size() == 1)
        {
            return result.get(0);
        }
        else if(result.size() >1)
        {
            logger.warn("Found more than one match. Returning the first match found");
            return result.get(0);
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
    public void setSessionFactory(SessionFactory sessionFactory) {
        if (sessionFactory != null)
        {
            this.sessionFactory = sessionFactory;
            this.hibernateTemplate = new HibernateTemplate(sessionFactory);
        }
        else {
            throw new IllegalArgumentException("Session Factory passed can not be null.");
        }
    }

    /**
     * Gets the session factory.
     *
     * @return the session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
