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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.renderer;

import com.jidesoft.swing.StyleRange;
import com.jidesoft.swing.StyledLabel;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.util.Collection;

/**
 * A custom {@link javax.swing.tree.TreeCellRenderer} that uses a {@link com.jidesoft.tree.StyledTreeCellRenderer}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 21, 2010 at 12:02:56 PM
 */
public class StyledQueryStatementTreeCellRenderer implements TreeCellRenderer{

    /** The icon. */
    private Icon icon ;

    /** The human readable render. */
    private HumanReadableRender humanReadableRender;
    private StyleRange strikeThroughStyle;
    private StyleRange boldStyle;
    private StyleRange plainStyle;
    private StyledLabel label;

    public StyledQueryStatementTreeCellRenderer(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
        icon = new ImageIcon(StyledQueryStatementTreeCellRenderer.class.getResource("icons/flag.png"));
        strikeThroughStyle = new StyleRange(Font.PLAIN, Color.BLACK, StyleRange.STYLE_STRIKE_THROUGH, Color.BLACK);
        boldStyle = new StyleRange(Font.BOLD);
        plainStyle = new StyleRange(Font.PLAIN);
        label = new StyledLabel();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {

        if (value instanceof QueryStatement)
        {
            label.setText("Get patients who have");
        }
        else if (value instanceof QueryIntersectionExpression)
        {
            QueryIntersectionExpression intersectionObject = (QueryIntersectionExpression) value;
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
        else if (value instanceof QueryUnionExpression)
        {
            label.setText("ANY of the following");
        }
        else if (value instanceof QueryComponentExpression)
        {
            QueryComponentExpression componentExpression = (QueryComponentExpression) value;
            TerminologyConstraint includedConstraint = componentExpression.getIncludedConstraint();
            if (includedConstraint != null)
            {
                String text = humanReadableRender.getHumanReadableLabel(includedConstraint.getExpression());
                label.setText(text);
            }

            final Collection<TerminologyConstraint> excludedTerms = componentExpression.getExcludedConstraints();
            if(excludedTerms.size()>0)
            {
                label.setIcon(icon);
                label.setIconTextGap(3);
            }

//            Runnable runnable = new Runnable() {
//                public void run() {
//                    SwingUtilities.updateComponentTreeUI(label);
////                    revalidate();
//                }
//            };
//
//            SwingUtilities.invokeLater(runnable);

            SwingUtilities.updateComponentTreeUI(label);
            label.revalidate();
        }

        // set text style
        setStyleForText(value);
        // set label text color based on run time status
        setStyleForRunTimeStatus(value);

        return label;
    }


    private void setStyleForText(Object value){
        if (value instanceof QueryStatement || value instanceof QueryUnionExpression
                || value instanceof QueryIntersectionExpression)
        {
            label.setStyleRanges(new StyleRange[]{boldStyle});
        }
        else
        {
            label.setStyleRanges(new StyleRange[]{plainStyle});
        }
    }

    private void setStyleForRunTimeStatus(Object value){
        if (value instanceof QueryExpression)
        {
            QueryExpression expression = (QueryExpression) value;
            if(QueryExpression.QueryRunTimeStatus.SKIP == expression.getRunTimeStatus())
            {
                label.setStyleRanges(new StyleRange[]{strikeThroughStyle});
            }
        }
    }
}
