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
package uk.nhs.cfh.dsp.yasb.expression.builder;

import com.jidesoft.swing.JideButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.yasb.expression.renderer.ExpressionRenderingLabel;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that renders a simple form of a
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression} as a simple label and a single edit button.
 *
 * @deprecated This class is redundant and will be removed in future versions. The equivalent class is included in the
 * appropriate package of the query building interface.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 25, 2010 at 2:26:29 PM
 */
public class SimpleCloseToUserExpressionPanel extends JPanel implements PropertyChangeTrackerServiceListener{

    /** The expression. */
    private CloseToUserExpression expression;
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;
    
    /** The terminology search service. */
    private TerminologySearchService terminologySearchService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;
    
    /** The active frame. */
    private JFrame activeFrame;

//    private ExpressionBuilderPanel expressionBuilderPanel;
    /** The expression builder panel. */
private RefinedExpressionBuilderPanel expressionBuilderPanel;
    
    /** The expression builder dialog. */
    private JDialog expressionBuilderDialog;
    
    /** The rendering label. */
    private ExpressionRenderingLabel renderingLabel;
    
    /** The Constant EXPRESSION_CHANGED. */
    private static final String EXPRESSION_CHANGED = "EXPRESSION_CHANGED";
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SimpleCloseToUserExpressionPanel.class);


    /**
     * Instantiates a new simple close to user expression panel.
     *
     * @param propertyChangeTrackerService the property change tracker service
     * @param terminologySearchService the terminology search service
     * @param applicationService the application service
     * @param selectionService the selection service
     * @param terminologyConceptDAO the terminology concept dao
     * @param humanReadableRender the human readable render
     * @param normalFormGenerator the normal form generator
     */
    public SimpleCloseToUserExpressionPanel(PropertyChangeTrackerService propertyChangeTrackerService,
                                            TerminologySearchService terminologySearchService,
                                            ApplicationService applicationService, SelectionService selectionService,
                                            TerminologyConceptDAO terminologyConceptDAO,
                                            HumanReadableRender humanReadableRender,
                                            NormalFormGenerator normalFormGenerator) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
        this.terminologySearchService = terminologySearchService;
        this.applicationService = applicationService;
        this.selectionService = selectionService;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.humanReadableRender = humanReadableRender;
        this.normalFormGenerator = normalFormGenerator;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        renderingLabel = new ExpressionRenderingLabel(humanReadableRender);
        renderingLabel.initComponents();

        // create expressionBuilderDialog
        createExpressionBuilderDialog();

        JideButton editButton = new JideButton(new AbstractAction("Edit") {
            public void actionPerformed(ActionEvent e) {
                // display expression builder dialog
                expressionBuilderDialog.setVisible(true);
            }
        });

        // create  panel that displays editing options and button
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        p1.add((new JLabel("Edit this expression")));
        p1.add(Box.createHorizontalGlue());
        p1.add(editButton);

        // set title and add panels
        setBorder(BorderFactory.createTitledBorder("Contained Expression"));
        setLayout(new BorderLayout());
        add(renderingLabel, BorderLayout.CENTER);
        add(p1, BorderLayout.SOUTH);
    }

    /**
     * Creates the expression builder dialog.
     */
    private synchronized void createExpressionBuilderDialog() {

        // wait for applicationService  to make a frame available
        while( applicationService == null || activeFrame == null)
        {
            // check applicationService
            if (applicationService != null && applicationService.getFrameView() != null)
            {
                activeFrame = applicationService.getFrameView().getActiveFrame();
            }
        }

//        // create expressionBuilderPanel  and register with services
//        expressionBuilderPanel = new ExpressionBuilderPanel(terminologySearchService, applicationService,
//                selectionService, terminologyConceptDAO,
//                propertyChangeTrackerService, humanReadableRender, normalFormGenerator);
//        // initialise and register expressionBuilderPanel  with services
//        expressionBuilderPanel.initComponents();
//        expressionBuilderPanel.register();

        // create expressionBuilderPanel  and register with services
        expressionBuilderPanel = new RefinedExpressionBuilderPanel(terminologySearchService, applicationService,
                selectionService, terminologyConceptDAO,
                propertyChangeTrackerService, humanReadableRender, normalFormGenerator);
        // initialise and register expressionBuilderPanel  with services
        expressionBuilderPanel.initComponents();

        // wrap conceptHierarchyPanel in a dialog
        expressionBuilderDialog = new JDialog(activeFrame, "Author expression", Dialog.ModalityType.DOCUMENT_MODAL);
        expressionBuilderDialog.getContentPane().add(expressionBuilderPanel);
        expressionBuilderDialog.setLocationRelativeTo(activeFrame);
        expressionBuilderDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        expressionBuilderDialog.pack();
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
            renderingLabel.setExpression(getExpression());
            expressionBuilderPanel.setCloseToUserExpression(getExpression());
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener#propertyChanged(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public synchronized void propertyChanged(String property, Object oldValue, Object newValue, Object source) {
        /*
            we check if expression has changed while the expressionBuilderDialog  is visible, which means that this
            panel's expression is being actively edited. This is a proxy for the source of the property change being
            expressionBuilderDialog or any of its contents
         */
        if(EXPRESSION_CHANGED.equalsIgnoreCase(property) && expressionBuilderDialog.isVisible()
                && newValue instanceof CloseToUserExpression && ! (source instanceof SimpleCloseToUserExpressionPanel))
        {
                // propagate event to other listeners (used by terminology constraint panel)
                propertyChangeTrackerService.firePropertyChanged(EXPRESSION_CHANGED, getExpression(), newValue, this);
                logger.info("Propagated event from simple close to user expression panel");
                this.expression = (CloseToUserExpression) newValue;
                renderingLabel.setExpression(getExpression());
                SwingUtilities.updateComponentTreeUI(this);
        }
    }

}
