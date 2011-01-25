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
/**
 *
 */
package uk.nhs.cfh.dsp.srth.desktop.modules.resultexplanationpanel.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


// TODO: Auto-generated Javadoc
/**
 * A custom tree table model for browsing explanations of a result set returned by
 * a {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService}.
 *
 * <br>Version : @#VersionNumber#@<br>Written by @author Jay Kola
 * <br>Created on Apr 3, 2009 at 5:37:50 PM
 * <br>
 */

public class ReportingEngineExplanationTreeTableModel extends AbstractTreeTableModel {

    /** The root. */
    private QueryStatement root;
//	private Map<QueryExpression, Integer> statsMap;
    /** The statistics map. */
    private Map<QueryExpression, QueryStatisticsCollection> statisticsMap;
    /** The logger. */
    private static Log logger = LogFactory.getLog(ReportingEngineExplanationTreeTableModel.class);


    /**
     * Instantiates a new reporting engine explanation tree table model.
     *
     * @param root the root
     * @param statisticsMap the statistics map
     */
    public ReportingEngineExplanationTreeTableModel(
            QueryStatement root,
            Map<QueryExpression, QueryStatisticsCollection> statisticsMap) {
        this.root = root;
        this.statisticsMap = statisticsMap;
    }

    /* (non-Javadoc)
      * @see org.jdesktop.swingx.treetable.TreeTableModel#getColumnCount()
      */
    /**
     * Gets the column count.
     *
     * @return the column count
     */
    public int getColumnCount() {
        return 3;
    }

    /* (non-Javadoc)
      * @see org.jdesktop.swingx.treetable.TreeTableModel#getValueAt(java.lang.Object, int)
      */
    /**
     * Gets the value at.
     *
     * @param obj the obj
     * @param col the col
     *
     * @return the value at
     */
    public Object getValueAt(Object obj, int col) {

        if(col ==0)
        {
            return obj;
        }
        else
        {
            // cast objec to reporting expression
            if(obj instanceof QueryExpression)
            {
                QueryExpression expression = (QueryExpression) obj;
                // get statistics from map
                if(statisticsMap.containsKey(expression))
                {
                    QueryStatisticsCollection coll = statisticsMap.get(expression);
                    if(col == 1)
                    {
                        return coll.getResultSetSize();
                    }
                    else
                    {
                        return coll.getQueryTime();
                    }
                }
                else
                {
                    logger.warn("No match found for expression : "+expression);
                    return 0;
                }

            }
            else
            {
                return 0;
            }
        }
    }

    /* (non-Javadoc)
      * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
      */
    /**
     * Gets the child.
     *
     * @param arg0 the arg0
     * @param arg1 the arg1
     *
     * @return the child
     */
    public Object getChild(Object arg0, int arg1) {
        QueryExpression expression = (QueryExpression) arg0;
        List<QueryExpression> children = new ArrayList<QueryExpression>(expression.getContainedExpressions());
        return children.get(arg1);
    }

    /* (non-Javadoc)
      * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
      */
    /**
     * Gets the child count.
     *
     * @param arg0 the arg0
     *
     * @return the child count
     */
    public int getChildCount(Object arg0) {
        QueryExpression expression = (QueryExpression) arg0;
        return expression.getContainedExpressions().size();
    }

    /* (non-Javadoc)
      * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
      */
    /**
     * Gets the index of child.
     *
     * @param arg0 the arg0
     * @param arg1 the arg1
     *
     * @return the index of child
     */
    public int getIndexOfChild(Object arg0, Object arg1) {
        QueryExpression parent = (QueryExpression) arg0;
        QueryExpression child = (QueryExpression) arg1;
        List<QueryExpression> children = new ArrayList<QueryExpression>(parent.getContainedExpressions());

        return children.indexOf(child);
    }

    /**
     * Checks if is leaf.
     *
     * @param node the node
     *
     * @return true, if is leaf
     */
    @Override
    public boolean isLeaf(Object node) {
        QueryExpression expression = (QueryExpression) node;
        return (expression.getContainedExpressions().size() == 0);
    }

    /**
     * Gets the column name.
     *
     * @param column the column
     *
     * @return the column name
     */
    @Override
    public String getColumnName(int column) {
        if(column == 0)
        {
            return "Expression";
        }
        else if(column == 1)
        {
            return "Matches";
        }
        else
        {
            return "Query Time";
        }
    }

    /**
     * Gets the column class.
     *
     * @param column the column
     *
     * @return the column class
     */
    @Override
    public Class<?> getColumnClass(int column) {
        if(column>0)
        {
            return Integer.class;
        }
        else
        {
            return super.getColumnClass(column);
        }
    }

    /**
     * Gets the root.
     *
     * @return the root
     */
    @Override
    public Object getRoot() {
        return root;
    }

    /**
     * Update stats.
     *
     * @param newRoot the new root
     * @param statsMap the stats map
     */
    public void updateStats(QueryStatement newRoot,
                            Map<QueryExpression, QueryStatisticsCollection> statsMap){
        if (newRoot != null)
        {
            this.statisticsMap = statsMap;
            this.root = newRoot;
            modelSupport.fireNewRoot();
        }
    }
}