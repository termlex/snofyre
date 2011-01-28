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

import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;

import javax.swing.*;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that displays a {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 11:57:24 PM
 */
public class DetailedCloseToUserExpressionPanel extends JPanel implements PropertyChangeTrackerServiceListener{

    /** The expression. */
    private CloseToUserExpression expression;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;
    
    /** The Constant EXPRESSION_CHANGED. */
    private static final String EXPRESSION_CHANGED = "EXPRESSION_CHANGED";
    
    /** The rendering label. */
    private ExpressionRenderingLabel renderingLabel;
    
    /** The views tabbed pane. */
    private JTabbedPane viewsTabbedPane;
    
    /** The application service. */
    private ApplicationService applicationService;

    /**
     * Instantiates a new detailed close to user expression panel.
     *
     * @param humanReadableRender the human readable render
     * @param propertyChangeTrackerService the property change tracker service
     * @param selectionService the selection service
     * @param normalFormGenerator the normal form generator
     * @param applicationService the application service
     */
    public DetailedCloseToUserExpressionPanel(HumanReadableRender humanReadableRender,
                                              PropertyChangeTrackerService propertyChangeTrackerService,
                                              SelectionService selectionService,
                                              NormalFormGenerator normalFormGenerator,
                                              ApplicationService applicationService) {
        this.humanReadableRender = humanReadableRender;
        this.propertyChangeTrackerService = propertyChangeTrackerService;
        this.selectionService = selectionService;
        this.normalFormGenerator = normalFormGenerator;
        this.applicationService = applicationService;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        renderingLabel = new ExpressionRenderingLabel(humanReadableRender);
        renderingLabel.initComponents();
        JPanel p = new JPanel(new BorderLayout());
        p.add(renderingLabel, BorderLayout.NORTH);

        // create snomedNormalFormExpressionTreeRendererPanel and register with services
        SnomedNormalFormExpressionTreeRendererPanel snomedNormalFormExpressionTreeRendererPanel =
                        new SnomedNormalFormExpressionTreeRendererPanel(normalFormGenerator, applicationService);
        snomedNormalFormExpressionTreeRendererPanel.initComponents();
        propertyChangeTrackerService.registerListener(snomedNormalFormExpressionTreeRendererPanel);

        viewsTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        viewsTabbedPane.addTab("Expression View", snomedNormalFormExpressionTreeRendererPanel);

        // set layout for main panel and add components to it
        setLayout(new BorderLayout());
        add(p, BorderLayout.NORTH);
        add(viewsTabbedPane, BorderLayout.CENTER);
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
            populateFields(getExpression());
        }
    }

    /**
     * Populate fields.
     *
     * @param closeToUserExpression the close to user expression
     */
    private void populateFields(CloseToUserExpression closeToUserExpression){
        if(closeToUserExpression != null)
        {
            renderingLabel.setExpression(closeToUserExpression);
        }
    }

    /**
     * Adds the view.
     *
     * @param viewName the view name
     * @param component the component
     */
    public void addView(String viewName , JComponent component){
        if(component != null)
        {
            viewsTabbedPane.addTab(viewName, component);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener#propertyChanged(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public void propertyChanged(String property, Object oldValue, Object newValue, Object source) {
        if(EXPRESSION_CHANGED.equalsIgnoreCase(property) && newValue instanceof CloseToUserExpression)
        {
            CloseToUserExpression closeToUserExpression = (CloseToUserExpression) newValue;
            setExpression(closeToUserExpression);
        }
    }

    /**
     * Sets the human readable render.
     *
     * @param humanReadableRender the new human readable render
     */
    public void setHumanReadableRender(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Sets the selection service.
     *
     * @param selectionService the new selection service
     */
    public void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    /**
     * Sets the property change tracker service.
     *
     * @param propertyChangeTrackerService the new property change tracker service
     */
    public void setPropertyChangeTrackerService(PropertyChangeTrackerService propertyChangeTrackerService) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }

    /**
     * Sets the normal form generator.
     *
     * @param normalFormGenerator the new normal form generator
     */
    public void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }
}
