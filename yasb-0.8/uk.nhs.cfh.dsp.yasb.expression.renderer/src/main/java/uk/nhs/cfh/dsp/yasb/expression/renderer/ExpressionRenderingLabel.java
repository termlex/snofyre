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
package uk.nhs.cfh.dsp.yasb.expression.renderer;

import org.jdesktop.swingx.JXLabel;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.swingx.JXLabel} that renders the human readable label for a given
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 25, 2010 at 2:12:05 PM
 */
public class ExpressionRenderingLabel extends JXLabel{

    /** The expression. */
    private CloseToUserExpression expression;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;
    
    /** The use bold font. */
    private boolean useBoldFont = true;

    /**
     * Instantiates a new expression rendering label.
     *
     * @param humanReadableRender the human readable render
     */
    public ExpressionRenderingLabel(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){
        setLineWrap(true);
        setMaxLineSpan(400);
        setText("No expression set");
        setHorizontalAlignment(SwingConstants.LEFT);
        setHorizontalTextPosition(SwingConstants.LEFT);
    }

    /**
     * Update label.
     *
     * @param closeToUserExpression the close to user expression
     */
    private void updateLabel(CloseToUserExpression closeToUserExpression) {
        String text = humanReadableRender.getHumanReadableLabel(closeToUserExpression);

        // set text in this label
        if(text != null && !("".equalsIgnoreCase(text)))
        {
            // set to bold if isUseBoldFont
            if(isUseBoldFont())
            {
                text = "<html><b>"+text+"</b></html>";
            }
            setText(text);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // repaint self in a thread safe manner
                SwingUtilities.updateComponentTreeUI(ExpressionRenderingLabel.this);
                ExpressionRenderingLabel.this.revalidate();
            }
        });
    }

    /**
     * Gets the expression.
     *
     * @return the expression
     */
    public CloseToUserExpression getExpression() {
        return expression;
    }

    /**
     * Sets the expression.
     *
     * @param expression the new expression
     */
    public void setExpression(CloseToUserExpression expression) {
        if (expression != null)
        {
            this.expression = expression;
            updateLabel(getExpression());
        }
    }

    /**
     * Checks if is use bold font.
     *
     * @return true, if is use bold font
     */
    public boolean isUseBoldFont() {
        return useBoldFont;
    }

    /**
     * Sets the use bold font.
     *
     * @param useBoldFont the new use bold font
     */
    public void setUseBoldFont(boolean useBoldFont) {
        this.useBoldFont = useBoldFont;
    }
}
