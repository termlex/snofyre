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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A custom tree cell renderer for a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Apr 2, 2009 at 9:30:58 PM
 * <br> Modified on Sunday; December 6, 2009 at 5:44 PM to fix rendering bug
 */

public class ReportingQueryStatementTreeCellRenderer implements TreeCellRenderer{

    /** The logger. */
    private static Log logger = LogFactory.getLog(ReportingQueryStatementTreeCellRenderer.class);
    
    /** The icon. */
    private Icon icon ;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;

    /**
     * Instantiates a new reporting query statement tree cell renderer.
     *
     * @param humanReadableRender the human readable render
     */
    public ReportingQueryStatementTreeCellRenderer(HumanReadableRender humanReadableRender) {
        icon = new ImageIcon(ReportingQueryStatementTreeCellRenderer.class.getResource("icons/flag.png"));
        this.humanReadableRender = humanReadableRender;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {

        final JLabel label = new JLabel();
        if (value instanceof QueryExpression)
        {
            QueryExpression expression = (QueryExpression) value;

            if (expression instanceof QueryStatement) {
                label.setText("Get patients who have");
            }
            else if (expression instanceof QueryIntersectionExpression)
            {
                QueryIntersectionExpression intersectionObject = (QueryIntersectionExpression) expression;
                QueryExpression.QueryOperatorType operatorType = intersectionObject.getOperator();
                if(operatorType != QueryExpression.QueryOperatorType.AND)
                {
                    label.setText("ALL of the following ("+operatorType.toString()+")");
                }
                else
                {
                    label.setText("ALL of the following");
                }
            }
            else if (expression instanceof QueryUnionExpression)
            {
                label.setText("ANY of the following");
            }
            else if (expression instanceof QueryComponentExpression)
            {
                QueryComponentExpression componentExpression = (QueryComponentExpression) value;
                TerminologyConstraint includedConstraint = componentExpression.getIncludedConstraint();
                if (includedConstraint != null)
                {
                    String text = humanReadableRender.getHumanReadableLabel((CloseToUserExpression) includedConstraint.getExpression());
                    label.setText(text);
                }

                final Collection<TerminologyConstraint> excludedTerms = componentExpression.getExcludedConstraints();
                if(excludedTerms.size()>0)
                {
                    label.setIcon(icon);
                }

                SwingUtilities.updateComponentTreeUI(label);
            }

            // set label text color based on run time status
            setFontColor(expression, label);
        }

        if (hasFocus)
        {
            Color lineColor = UIManager.getColor("Tree.selectionBorderColor");
            label.setBorder(BorderFactory.createLineBorder(lineColor));
            label.setBackground(UIManager.getColor("Tree.selectionBackground"));
        } else
        {
            label.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            label.setBackground(UIManager.getColor("Tree.background"));
        }

        return label;
    }

    /**
     * Sets the font color.
     * 
     * @param expression the expression
     * @param label the label
     */
    private void setFontColor(QueryExpression expression, JLabel label){
        if(expression.getRunTimeStatus() == QueryExpression.QueryRunTimeStatus.SKIP)
        {
            label.setForeground(Color.RED);
        }
        else
        {
            label.setForeground(Color.BLACK);
        }
    }
}