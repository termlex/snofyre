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
import uk.nhs.cfh.dsp.snomed.dao.SnomedExpressionDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionImpl;

import java.util.List;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.snomed.dao.SnomedExpressionDAO}.
 *
 * @scr.component immediate=“true”
 * @scr.service 
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 20-Mar-2010 at 21:40:32
 */
public class SnomedExpressionDAOImpl implements SnomedExpressionDAO {

    /** The session factory. */
    private SessionFactory sessionFactory;
    
    /** The hibernate template. */
    private HibernateTemplate hibernateTemplate;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SnomedExpressionDAOImpl.class);

    /**
     * Instantiates a new snomed expression dao impl.
     *
     * @param sessionFactory the session factory
     */
    public SnomedExpressionDAOImpl(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            this.sessionFactory = sessionFactory;
            hibernateTemplate = new HibernateTemplate(this.sessionFactory);
        } else {
            throw new IllegalArgumentException("Session factory passed can not be null.");
        }
    }

    /**
     * Instantiates a new snomed expression dao impl.
     */
    public SnomedExpressionDAOImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.SnomedExpressionDAO#saveExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public void saveExpression(Expression expression){
        hibernateTemplate.saveOrUpdate(expression);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.SnomedExpressionDAO#deleteExpression(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public void deleteExpression(Expression expression){
        hibernateTemplate.delete(expression);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.SnomedExpressionDAO#findAll()
     */
    public List<Expression> findAll(){
        return hibernateTemplate.loadAll(AbstractExpressionImpl.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.dao.SnomedExpressionDAO#find(java.lang.String)
     */
    public Expression find(UUID uuid){
        List<Expression> results = hibernateTemplate.find("from Expression e where e.uuid = ?", uuid);
        return checkAndReturnResult(results);
    }

    /**
     * Check and return result.
     *
     * @param results the results
     * @return the expression
     */
    private Expression checkAndReturnResult(List<Expression> results) {
        if(results.size() == 1)
        {
            return results.get(0);
        }
        else if(results.size() > 1)
        {
            logger.warn("Found more than one matching result. Returning the first one found");
            return results.get(0);
        }
        else
        {
            logger.warn("No matching result found. Returning null");
            return null;
        }
    }

    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    public synchronized void setSessionFactory(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            this.sessionFactory = sessionFactory;
            hibernateTemplate = new HibernateTemplate(this.sessionFactory);
        } else {
            throw new IllegalArgumentException("Session factory passed can not be null.");
        }
    }
}
