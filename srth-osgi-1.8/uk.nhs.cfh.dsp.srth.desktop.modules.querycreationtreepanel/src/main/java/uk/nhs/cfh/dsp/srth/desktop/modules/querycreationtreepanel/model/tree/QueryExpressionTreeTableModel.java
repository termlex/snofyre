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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.model.tree;

import org.jdesktop.swingx.tree.TreeModelSupport;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;

import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.swingx.treetable.AbstractTreeTableModel} that renders a
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Mar 29, 2010 at 6:52:29 PM
 */
public class QueryExpressionTreeTableModel extends AbstractTreeTableModel{

    /** The expression. */
    private QueryExpression expression;
    
    /** The child expressions. */
    private List<QueryExpression> childExpressions = new ArrayList<QueryExpression>();

    /**
     * Instantiates a new query expression tree table model.
     *
     * @param expression the expression
     */
    public QueryExpressionTreeTableModel(QueryExpression expression) {
        this.expression = expression;
        modelSupport = new TreeModelSupport(this);
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.TreeTableModel#getColumnCount()
     */
    public int getColumnCount() {
        return 1;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.TreeTableModel#getValueAt(java.lang.Object, int)
     */
    public Object getValueAt(Object o, int column) {
        if(column == 0)
        {
            return o;
        }
        else
        {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        return getChildrenAsExpressions(parent).get(index);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        return getChildrenAsExpressions(parent).size();
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        return getChildrenAsExpressions(parent).indexOf(child);
    }

    /**
     * Gets the children as expressions.
     *
     * @param parent the parent
     * @return the children as expressions
     */
    private List<QueryExpression> getChildrenAsExpressions(Object parent){

        // cast parent to QueryExpression
        QueryExpression parentExpression = (QueryExpression) parent;
        return new ArrayList<QueryExpression>(parentExpression.getContainedExpressions());
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getRoot()
     */
    @Override
    public Object getRoot() {
        return expression;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#isLeaf(java.lang.Object)
     */
    @Override
    public boolean isLeaf(Object node) {
        return expression.getContainedExpressions().size() == 0;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#setValueAt(java.lang.Object, java.lang.Object, int)
     */
    @Override
    public void setValueAt(Object value, Object node, int column) {
        modelSupport.fireChildChanged(new TreePath(value), column, node);
    }

    /**
     * Adds the child to parent.
     *
     * @param childExpression the child expression
     * @param parentExpression the parent expression
     */
    public void addChildToParent(QueryExpression childExpression, QueryExpression parentExpression){
        parentExpression.addChildExpression(childExpression);
        List<QueryExpression> children = getChildrenAsExpressions(parentExpression);
        modelSupport.fireChildAdded(new TreePath(parentExpression), children.indexOf(childExpression), childExpression );
    }

    /**
     * Removes the node from parent.
     *
     * @param expression the expression
     */
    public void removeNodeFromParent(QueryExpression expression){

    }

    /**
     * Populate model.
     *
     * @param expression the expression
     */
    public void populateModel(QueryExpression expression){
        if(expression != null && ! expression.equals(this.expression))
        {
            this.expression = expression;
            modelSupport.fireNewRoot();
        }
    }

    /**
     * Gets the path to root.
     *
     * @param e the e
     * @return the path to root
     */
    public Object[] getPathToRoot(QueryExpression e) {

        List<QueryExpression> path = new ArrayList<QueryExpression>();
        while (! e.equals(root))
        {
            path.add(0, e);
            e =  e.getParentExpression();
        }

        if (e.equals(root))
        {
            path.add(0, e);
        }

        return path.toArray(new QueryExpression[0]);
    }
}
