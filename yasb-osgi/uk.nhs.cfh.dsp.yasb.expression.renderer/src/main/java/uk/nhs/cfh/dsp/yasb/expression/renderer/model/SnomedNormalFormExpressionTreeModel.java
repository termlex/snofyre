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
package uk.nhs.cfh.dsp.yasb.expression.renderer.model;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.tree.TreeModel} that renders a
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 12, 2010 at 1:22:40 PM
 */
public class SnomedNormalFormExpressionTreeModel implements TreeModel{

    /** The normal form expression. */
    private NormalFormExpression normalFormExpression;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;
    
    /** The listeners. */
    private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();

    /**
     * Instantiates a new snomed normal form expression tree model.
     *
     * @param normalFormGenerator the normal form generator
     */
    public SnomedNormalFormExpressionTreeModel(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getRoot()
     */
    public Object getRoot() {
        return normalFormExpression;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        Expression expression = (Expression) parent;
        return new ArrayList<Expression>(expression.getChildExpressions()).get(index);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        return ((Expression)parent).getChildExpressions().size();
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object node) {
        return ((Expression)node).getChildExpressions().size() == 0;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        // not implemented
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        Expression parentExpression = (Expression) parent;
        Expression chiExpression = (Expression) child;

        return new ArrayList<Expression>(parentExpression.getChildExpressions()).indexOf(chiExpression);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void addTreeModelListener(TreeModelListener l) {
        if(l != null)
        {
            listeners.add(l);
        }
        else
        {
            throw new IllegalArgumentException("Listener passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void removeTreeModelListener(TreeModelListener l) {
        if(l != null)
        {
            listeners.remove(l);
        }
        else
        {
            throw new IllegalArgumentException("Listener passed can not be null.");
        }
    }

    /**
     * Fire tree structure changed.
     *
     * @param normalFormExpression the normal form expression
     */
    public void fireTreeStructureChanged(NormalFormExpression normalFormExpression){
        if(normalFormExpression != null && normalFormExpression.equals(this.normalFormExpression))
        {
            // notify listeners
            TreeModelEvent event = new TreeModelEvent(this, new Object[] {normalFormExpression});
            for(TreeModelListener listener : listeners)
            {
                listener.treeStructureChanged(event);
            }
        }
    }

    /**
     * Gets the normal form expression.
     *
     * @return the normal form expression
     */
    public NormalFormExpression getNormalFormExpression() {
        return normalFormExpression;
    }

    /**
     * Sets the normal form expression.
     *
     * @param normalFormExpression the new normal form expression
     */
    public void setNormalFormExpression(NormalFormExpression normalFormExpression) {
        if (normalFormExpression != null)
        {
            this.normalFormExpression = normalFormExpression;
            // notify listeners
            fireTreeStructureChanged(getNormalFormExpression());
        }
    }

    /**
     * Sets the normal form expression.
     *
     * @param closeToUserExpression the close to user expression
     * @param renderShortNormalForm the render short normal form
     */
    public void setNormalFormExpression(CloseToUserExpression closeToUserExpression, boolean renderShortNormalForm){
        if(closeToUserExpression != null)
        {
            NormalFormExpression nfe;
            if (! renderShortNormalForm)
            {
                nfe = normalFormGenerator.getLongNormalFormExpression(closeToUserExpression);
            }
            else
            {
                nfe = normalFormGenerator.getShortNormalFormExpression(closeToUserExpression);
            }

            // set as root
            setNormalFormExpression(nfe);
        }
    }

    /**
     * Sets the short normal form expression.
     *
     * @param closeToUserExpression the new short normal form expression
     */
    public void setShortNormalFormExpression(CloseToUserExpression closeToUserExpression){
        if(closeToUserExpression != null)
        {
            // get normalFormExpression  for closeToUserExpression using normalFormGenerator
            NormalFormExpression nfe = normalFormGenerator.getShortNormalFormExpression(closeToUserExpression);
            // set as root
            setNormalFormExpression(nfe);
        }
    }
}
