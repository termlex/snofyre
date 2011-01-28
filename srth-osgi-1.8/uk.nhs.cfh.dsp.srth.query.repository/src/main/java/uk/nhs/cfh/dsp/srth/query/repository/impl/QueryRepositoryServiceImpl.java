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

import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.repository.QueryExpressionDAO;
import uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService}
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 30, 2009 at 9:17:56 PM
 */
public class QueryRepositoryServiceImpl implements QueryRepositoryService {

    /** The query expression dao. */
    private QueryExpressionDAO queryExpressionDAO;

    /**
     * Instantiates a new query repository service impl.
     *
     * @param queryExpressionDAO the query expression dao
     */
    public QueryRepositoryServiceImpl(QueryExpressionDAO queryExpressionDAO) {
        this.queryExpressionDAO = queryExpressionDAO;
    }

    /**
     * Instantiates a new query repository service impl.
     */
    public QueryRepositoryServiceImpl() {
    }

    /**
     * Sets the query expression dao.
     *
     * @param queryExpressionDAO the new query expression dao
     */
    public synchronized void setQueryExpressionDAO(QueryExpressionDAO queryExpressionDAO) {
        this.queryExpressionDAO = queryExpressionDAO;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService#saveQuery(uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression)
     */
    public void saveQuery(QueryExpression expression) {
        queryExpressionDAO.saveQuery(expression);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService#deleteQuery(uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression)
     */
    public void deleteQuery(QueryExpression expression) {
        queryExpressionDAO.deleteQuery(expression);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService#findQueryExpression(java.lang.String)
     */
    public QueryExpression findQueryExpression(String uuid) {
        return queryExpressionDAO.findQueryExpression(uuid);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService#findAllQueries()
     */
    public List<QueryExpression> findAllQueries() {
        return queryExpressionDAO.findAllQueries();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService#deleteAllQueries()
     */
    public void deleteAllQueries() {
        queryExpressionDAO.deleteAllQueries();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService#indexAllQueries()
     */
    public void indexAllQueries() {
        queryExpressionDAO.indexAllQueries();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService#findUsingSearchTerm(java.lang.String)
     */
    public List<QueryExpression> findUsingSearchTerm(String searchTerm){
        if(searchTerm != null)
        {
            List<QueryExpression> result = queryExpressionDAO.findUsingSearchTerm(searchTerm);

            return result;
        }
        else
        {
            throw new IllegalArgumentException("Search term passed can not be null.");
        }
    }
}
