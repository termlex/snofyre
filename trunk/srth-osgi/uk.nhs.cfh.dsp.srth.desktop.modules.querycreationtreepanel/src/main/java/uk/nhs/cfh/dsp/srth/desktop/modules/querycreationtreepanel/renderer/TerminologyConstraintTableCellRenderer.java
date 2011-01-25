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

import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.table.TableCellRenderer} that renders a
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 23, 2010 at 5:17:02 PM
 */
public class TerminologyConstraintTableCellRenderer implements TableCellRenderer{

    /** The human readable render. */
    private HumanReadableRender humanReadableRender;

    /**
     * Instantiates a new terminology constraint table cell renderer.
     *
     * @param humanReadableRender the human readable render
     */
    public TerminologyConstraintTableCellRenderer(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }

    /* (non-Javadoc)
    * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
    */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        JLabel label = new JLabel();
        if(value instanceof TerminologyConstraint)
        {
            TerminologyConstraint constraint = (TerminologyConstraint) value;
            Expression includedExpression = constraint.getExpression();
            if(includedExpression != null)
            {
                label.setText(humanReadableRender.getHumanReadableLabel(includedExpression));
            }
        }
        else
        {
            label.setText("Unknown value");
        }

        if(isSelected || hasFocus)
        {
            label.setBackground(UIManager.getColor("table.selectionBackground"));
            label.setForeground(UIManager.getColor("table.selectionForeground"));
        }
        else
        {
            label.setBackground(UIManager.getColor("table.background"));
            label.setForeground(UIManager.getColor("table.foreground"));
        }

        return label;
    }
}
