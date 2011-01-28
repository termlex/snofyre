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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A {@link org.jdesktop.swingx.treetable.TreeTableModel} that renders a given set of
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.PropertyExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 9:14:02 PM
 */
public class SnomedPropertyExpressionTreeTableModel extends AbstractTreeTableModel {

    /** The close to user expression. */
    private CloseToUserExpression closeToUserExpression;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SnomedPropertyExpressionTreeTableModel.class);

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.TreeTableModel#getColumnCount()
     */
    public int getColumnCount() {
        return 3;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.TreeTableModel#getValueAt(java.lang.Object, int)
     */
    public Object getValueAt(Object o, int column) {
        if(o instanceof SnomedRoleGroupExpression)
        {
            if(column == 0)
            {
                return "Group Refinement";
            }
            else
            {
                return "";
            }
        }
        else if(o instanceof SnomedRelationshipPropertyExpression)
        {
            SnomedRelationshipPropertyExpression relationshipPropertyExpression = (SnomedRelationshipPropertyExpression) o;
            SnomedRelationship relationship = relationshipPropertyExpression.getRelationship();
            if(column == 0)
            {
                return "Refinement";
            }
            else if(column == 1)
            {
                return relationship.getName();
            }
            else
            {
                return relationship.getTargetConcept().getPreferredLabel();
            }
        }
        else if(o instanceof CloseToUserExpression)
        {
            CloseToUserExpression expression = (CloseToUserExpression) o;
            List<SnomedConcept> list = new ArrayList<SnomedConcept>(expression.getFocusConcepts());

            return list.get(0).getPreferredLabel();
        }
        else
        {
            throw new IllegalArgumentException("Object passed is not recognised. Object : "+o.getClass());
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        if(parent instanceof SnomedRoleGroupExpression)
        {
            SnomedRoleGroupExpression roleGroupExpression = (SnomedRoleGroupExpression) parent;
            List<Expression> children = new ArrayList<Expression>(roleGroupExpression.getChildExpressions());

            return children.get(index);
        }
        else if(parent instanceof CloseToUserExpression)
        {
            CloseToUserExpression expression = (CloseToUserExpression) parent;
            return expression.getChildExpressions();
        }
        else if(parent instanceof SnomedRelationshipPropertyExpression)
        {
            return 0;
        }
        else
        {
            throw new IllegalArgumentException("Parent passed not recognised. Type : "+parent.getClass());
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        if(parent instanceof SnomedRelationshipPropertyExpression)
        {
            return 0;
        }
        else if(parent instanceof Expression)
        {
            Expression expression = (Expression) parent;
            return expression.getChildExpressions().size();
        }
        else if(parent instanceof Collection)
        {
            Collection collection = (Collection) parent;
            return collection.size();
        }
        else
        {
            throw new IllegalArgumentException("Unknown parent type passed. Type : "+parent.getClass());
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        if(parent instanceof SnomedRelationshipPropertyExpression)
        {
            SnomedRoleGroupExpression roleGroupExpression = (SnomedRoleGroupExpression) parent;
            List<Expression> children = new ArrayList<Expression>(roleGroupExpression.getChildExpressions());
            return children.indexOf(child);
        }
        else if(parent instanceof SnomedRelationshipPropertyExpression)
        {
            return 0;
        }
        else if(parent instanceof CloseToUserExpression)
        {
            CloseToUserExpression expression = (CloseToUserExpression) parent;
            List<PropertyExpression> children = new ArrayList<PropertyExpression>(expression.getRefinementExpressions());
            return children.indexOf(child);
        }
        else
        {
            throw new IllegalArgumentException("Unknown parent type passed. Type : "+parent.getClass());
        }
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
        if(column == 0)
        {
            return "Refinement";
        }
        else if(column == 1)
        {
            return "Name";
        }
        else
        {
            return "Value";
        }
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int column) {
        return String.class;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.treetable.AbstractTreeTableModel#getRoot()
     */
    @Override
    public Object getRoot() {
        return closeToUserExpression;
    }

    /**
     * Gets the expression.
     *
     * @return the expression
     */
    public CloseToUserExpression getExpression() {
        return closeToUserExpression;
    }

    /**
     * Sets the expression.
     *
     * @param expression the new expression
     */
    public void setExpression(CloseToUserExpression expression) {
        if (expression != null)
        {
            logger.debug("Setting new root");   
            this.closeToUserExpression = expression;
            modelSupport.fireNewRoot();
            logger.debug("Model support fired");
        }
    }
}

