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
package uk.nhs.cfh.dsp.srth.query.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.AbstractQueryExpression;
import uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO;

import java.sql.SQLException;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 30, 2009 at 9:22:02 PM
 */
public class QueryExpressionDAOImpl implements QueryExpressionDAO{

    /** The hibernate template. */
    private HibernateTemplate hibernateTemplate;
    
    /** The session factory. */
    private SessionFactory sessionFactory;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryExpressionDAOImpl.class);
    
    /** The Constant FIELD_NAMES. */
    private static final String[] FIELD_NAMES = new String[]{"title", "description",
            "subject"};

    /**
     * Instantiates a new query expression dao impl.
     *
     * @param sessionFactory the session factory
     */
    public QueryExpressionDAOImpl(SessionFactory sessionFactory) {
        if (sessionFactory != null)
        {
            hibernateTemplate = new HibernateTemplate(sessionFactory);
        }
        else {
            throw new IllegalArgumentException("Session factory passed can not be null.");
        }
    }

    /**
     * Instantiates a new query expression dao impl.
     */
    public QueryExpressionDAOImpl() {

    }

    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    public synchronized void setSessionFactory(SessionFactory sessionFactory){
        if (sessionFactory != null)
        {
            hibernateTemplate = new HibernateTemplate(sessionFactory);
        }
        else {
            throw new IllegalArgumentException("Session factory passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO#saveQuery(uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression)
     */
    public void saveQuery(QueryExpression expression) {
        hibernateTemplate.saveOrUpdate(expression);
//        hibernateTemplate.executeWithNewSession(new HibernateCallback() {
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                hibernateTemplate.flush();
//                hibernateTemplate.merge(expression);
//                return null;
//            }
//        });
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO#deleteQuery(uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression)
     */
    public void deleteQuery(QueryExpression expression) {
        hibernateTemplate.delete(expression);
//        hibernateTemplate.flush();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO#findQueryExpression(java.lang.String)
     */
    public QueryExpression findQueryExpression(String uuid) {
        List<QueryExpression> results = hibernateTemplate.find("from QueryExpression q where q.UUID = ?", uuid);
        return checkAndReturnResult(results);
    }

    /**
     * Check and return result.
     *
     * @param result the result
     * @return the query expression
     */
    private QueryExpression checkAndReturnResult(List<QueryExpression> result) {
        if(result.size() == 1)
        {
            return result.get(0);
        }
        else if(result.size() >1)
        {
            logger.warn("More than one match found. Returning first match found");
            return result.get(0);
        }
        else
        {
            logger.warn("No matches found!");
            return null;
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO#findAllQueries()
     */
    public List<QueryExpression> findAllQueries() {
        return hibernateTemplate.loadAll(AbstractQueryExpression.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO#deleteAllQueries()
     */
    public void deleteAllQueries() {
        hibernateTemplate.deleteAll(this.findAllQueries());
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO#indexAllQueries()
     */
    public synchronized void indexAllQueries(){

        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                FullTextSession fullTextSession = Search.getFullTextSession(session);
                Transaction tx = fullTextSession.beginTransaction();
                List<QueryExpression> queries = session.createQuery("from QueryExpression as query").list();
                for(QueryExpression query : queries)
                {
                    fullTextSession.index(query);
                }
                tx.commit(); //index is written at commit time
                return null;
            }
        });
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO#findUsingSearchTerm(java.lang.String)
     */
    public List<QueryExpression> findUsingSearchTerm( final String searchTerm) {
        List searchResults = hibernateTemplate.executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(org.hibernate.Session session) {
                        FullTextSession fullTextSession = Search.getFullTextSession(session);
                        QueryParser parser = new MultiFieldQueryParser( FIELD_NAMES, new StandardAnalyzer());
                        org.apache.lucene.search.Query query = null;
                        try
                        {
                            query = parser.parse(searchTerm);
                        }
                        catch (ParseException e) {
                            logger.warn("Error parsing search term. Nested exception is : " + e.fillInStackTrace().getMessage());
                        }
                        org.hibernate.Query hibernateQuery = fullTextSession.createFullTextQuery(query);

                        return hibernateQuery.list();
                    }
                });

        return searchResults;
    }
}
