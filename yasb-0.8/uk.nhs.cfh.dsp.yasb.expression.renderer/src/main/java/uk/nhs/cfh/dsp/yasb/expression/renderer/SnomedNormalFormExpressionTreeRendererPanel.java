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

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener;
import uk.nhs.cfh.dsp.yasb.expression.renderer.actions.GenerateExpressionTask;
import uk.nhs.cfh.dsp.yasb.expression.renderer.model.SnomedNormalFormExpressionTreeModel;
import uk.nhs.cfh.dsp.yasb.expression.renderer.renderer.SnomedNormalFormExpressionTreeCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that renders a given SNOMED CT expression in a tree.
 * The SNOMED CT expression needs to be represented as a {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression}
 * or a {@link }
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 12, 2010 at 1:17:39 PM
 */
public class SnomedNormalFormExpressionTreeRendererPanel extends JPanel implements PropertyChangeTrackerServiceListener{

    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;
    
    /** The tree model. */
    private SnomedNormalFormExpressionTreeModel treeModel;
    
    /** The Constant EXPRESSION_CHANGED. */
    private static final String EXPRESSION_CHANGED = "EXPRESSION_CHANGED";
    
    /** The tree. */
    private JTree tree;
    
    /** The close to user expression. */
    private CloseToUserExpression closeToUserExpression;
    
    /** The render short normal form. */
    private boolean renderShortNormalForm = false;
    
    /** The render short normal form check box. */
    private JCheckBox renderShortNormalFormCheckBox;
    
    /** The application service. */
    private ApplicationService applicationService;

    /**
     * Instantiates a new snomed normal form expression tree renderer panel.
     *
     * @param normalFormGenerator the normal form generator
     * @param applicationService the application service
     */
    public SnomedNormalFormExpressionTreeRendererPanel(NormalFormGenerator normalFormGenerator,
                                                       ApplicationService applicationService) {
        this.normalFormGenerator = normalFormGenerator;
        this.applicationService = applicationService;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        // add check box for specifying if rendering is for editing
        renderShortNormalFormCheckBox = new JCheckBox(new AbstractAction("Render Short Normal Form") {
            public void actionPerformed(ActionEvent e) {
                setRenderShortNormalForm(renderShortNormalFormCheckBox.isSelected());
                populateModel();
            }
        });

        // create tree and model
        treeModel = new SnomedNormalFormExpressionTreeModel(normalFormGenerator);
        tree = new JTree(treeModel);
        tree.setCellRenderer(new SnomedNormalFormExpressionTreeCellRenderer());
//        tree.setPreferredSize(new Dimension(300, 300));

        // set tree as viewport view
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tree);

        // set layout and add components
        setLayout(new BorderLayout());
        add(renderShortNormalFormCheckBox, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener#propertyChanged(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public void propertyChanged(String propertyName, Object oldValue, Object newValue, Object source) {
        // get event name and handle appropriately
        if(EXPRESSION_CHANGED.equalsIgnoreCase(propertyName))
        {
            // get new value and update treeModel
            if(newValue instanceof CloseToUserExpression)
            {
                setCloseToUserExpression((CloseToUserExpression) newValue);
            }
            else if(newValue instanceof NormalFormExpression)
            {
                treeModel.setNormalFormExpression((NormalFormExpression) newValue);
            }
        }
    }

    /**
     * Populate model.
     */
    private void populateModel(){

        GenerateExpressionTask task = new GenerateExpressionTask(applicationService.getActiveApplication(),
                getCloseToUserExpression(), normalFormGenerator, treeModel, isRenderShortNormalForm());
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }

    /**
     * Checks if is render short normal form.
     *
     * @return true, if is render short normal form
     */
    public boolean isRenderShortNormalForm() {
        return renderShortNormalForm;
    }

    /**
     * Sets the render short normal form.
     *
     * @param renderShortNormalForm the new render short normal form
     */
    public void setRenderShortNormalForm(boolean renderShortNormalForm) {
        this.renderShortNormalForm = renderShortNormalForm;
    }

    /**
     * Gets the close to user expression.
     *
     * @return the close to user expression
     */
    public CloseToUserExpression getCloseToUserExpression() {
        return closeToUserExpression;
    }

    /**
     * Sets the close to user expression.
     *
     * @param closeToUserExpression the new close to user expression
     */
    public void setCloseToUserExpression(CloseToUserExpression closeToUserExpression) {
        if (closeToUserExpression != null)
        {
            this.closeToUserExpression = closeToUserExpression;
            populateModel();
        }
    }
}
