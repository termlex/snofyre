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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.tree.TreeModelSupport;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom tree model for rendering a {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl} object
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Mar 9, 2009 at 12:03:26 PM
 */

@SuppressWarnings("unused")
public class ReportingQueryStatementTreeModel implements TreeModel {

	/** The logger. */
	private static Log logger = LogFactory.getLog(ReportingQueryStatementTreeModel.class);
	
	/** The root. */
	private QueryExpression root;
	
	/** The model support. */
	private TreeModelSupport modelSupport;

	/**
	 * Instantiates a new reporting query statement tree model.
	 * 
	 * @param root the root
	 */
	public ReportingQueryStatementTreeModel(QueryExpression root) {
		this.root = root;
		modelSupport = new TreeModelSupport(this);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void addTreeModelListener(TreeModelListener listener) {
		modelSupport.addTreeModelListener(listener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object node, int index) {
		QueryExpression element = (QueryExpression) node;
		List<QueryExpression> children = new ArrayList<QueryExpression>(element.getContainedExpressions());
		return children.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object node) {
		QueryExpression element = (QueryExpression) node;
		return element.getContainedExpressions().size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
	 */
	public int getIndexOfChild(Object parent, Object child) {
		QueryExpression parentEle = (QueryExpression) parent;
		QueryExpression childEle = (QueryExpression) child;
		List<QueryExpression> children = new ArrayList<QueryExpression>(parentEle.getContainedExpressions());
		return children.indexOf(childEle);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#getRoot()
	 */
	public Object getRoot() {
		return root;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
	 */
	public boolean isLeaf(Object node) {
		QueryExpression element = (QueryExpression) node;
		return (element.getContainedExpressions().size() == 0);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void removeTreeModelListener(TreeModelListener listener) {
		modelSupport.removeTreeModelListener(listener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
	 */
	public void valueForPathChanged(TreePath path, Object node) {
		// do nothing
	}

    /**
     * Insert node into.
     * 
     * @param childElement the child element
     * @param parentElement the parent element
     * @param insertPosition the insert position
     */
    public void insertNodeInto(QueryExpression childElement, QueryExpression parentElement, int insertPosition){

        parentElement.addChildExpression(childElement);
        TreePath path = new TreePath(getPathToRoot(parentElement));
        modelSupport.fireChildAdded(path, insertPosition, childElement);
    }

    /**
     * Adds the child to parent.
     * 
     * @param childElement the child element
     * @param parentElement the parent element
     */
    public void addChildToParent(QueryExpression childElement, QueryExpression parentElement){

        parentElement.addChildExpression(childElement);
        TreePath path = new TreePath(getPathToRoot(parentElement));
        // get index of child in parent's children
        int insertPosition = getIndexOfChild(parentElement, childElement);
        modelSupport.fireChildAdded(path, insertPosition, childElement);
    }

    /**
     * Adds the node.
     * 
     * @param element the element
     */
    public void addNode(QueryExpression element){
        insertNodeInto(element, root, root.getContainedExpressions().size());
    }

    /**
     * Removes the node from parent.
     * 
     * @param element the element
     */
    public void removeNodeFromParent(QueryExpression element){

        QueryExpression parent = element.getParentExpression();
        int index = 0;
        List<QueryExpression> children = new ArrayList<QueryExpression>(parent.getContainedExpressions());
        for(int i=0; i<children.size(); i++)
        {
            QueryExpression child = children.get(i);
            if(child.equals(element))
            {
                index = i;
                modelSupport.fireChildRemoved(new TreePath(getPathToRoot(parent)), index, element);
                break;
            }
        }
    }

    /**
     * Gets the path to root.
     * 
     * @param expression the expression
     * 
     * @return the path to root
     */
    public Object[] getPathToRoot(QueryExpression expression) {

        logger.debug("Getting path to root");
        List<QueryExpression> path = new ArrayList<QueryExpression>();
        while (! expression.equals(root))
        {
            path.add(0, expression);
            expression =  expression.getParentExpression();
        }

        if (expression.equals(root))
        {
            path.add(0, expression);
        }

        logger.debug("path = " + path);
        return path.toArray(new QueryExpression[0]);
    }

    /**
     * Populate model.
     * 
     * @param newRoot the new root
     */
    public void populateModel(QueryExpression newRoot){
    	QueryExpression oldRoot = this.root;
    	if(newRoot != null)
    	{
    		this.root = newRoot;
    		modelSupport.fireNewRoot();
    	}
    }

    /**
     * Gets the tree model listeners.
     * 
     * @return the tree model listeners
     */
    public TreeModelListener[] getTreeModelListeners(){
    	return modelSupport.getTreeModelListeners();
    }

    /**
     * Sets the value at.
     * 
     * @param value the value
     * @param node the node
     */
    public void setValueAt(Object value, Object node) {

        if(node instanceof QueryExpression)
        {
            QueryExpression element = (QueryExpression) node;
            modelSupport.firePathChanged(new TreePath(getPathToRoot(element)));
        }
    }
}