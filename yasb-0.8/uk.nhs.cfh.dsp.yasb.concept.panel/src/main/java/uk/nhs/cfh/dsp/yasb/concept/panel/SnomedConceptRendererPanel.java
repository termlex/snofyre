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
package uk.nhs.cfh.dsp.yasb.concept.panel;

import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipProperty;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionServiceListener;
import uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.SnomedConceptHierarchyPanel;
import uk.nhs.cfh.dsp.yasb.concept.panel.actions.GenerateExpressionTask;
import uk.nhs.cfh.dsp.yasb.concept.panel.model.SnomedConceptTreeModel;
import uk.nhs.cfh.dsp.yasb.concept.panel.renderer.SnomedConceptExpressionTreeCellRenderer;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// TODO: Auto-generated Javadoc
/**
 * A {@link javax.swing.JPanel} that renders a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} in
 * a {@link javax.swing.JTree}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 5, 2010 at 4:14:45 PM
 */
public class SnomedConceptRendererPanel extends JScrollPane implements SelectionServiceListener,
        PropertyChangeTrackerServiceListener{

    /** The close to user expression. */
    private CloseToUserExpression closeToUserExpression;
    
    /** The active relationship. */
    private SnomedRelationship activeRelationship;
    
    /** The selected property expression. */
    private SnomedRelationshipPropertyExpression selectedPropertyExpression;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;
    
    /** The tree model. */
    private SnomedConceptTreeModel treeModel;
    
    /** The concept hierarchy panel. */
    private SnomedConceptHierarchyPanel conceptHierarchyPanel;
    
    /** The concept tree. */
    private JTree conceptTree;
    
    /** The concept hierarchy dialog. */
    private JDialog conceptHierarchyDialog;
    
    /** The active frame. */
    private JFrame activeFrame;
    
    /** The Constant EXPRESSION_CHANGED. */
    private static final String EXPRESSION_CHANGED = "EXPRESSION_CHANGED";
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;

    /**
     * Instantiates a new snomed concept renderer panel.
     *
     * @param terminologyConceptDAO the terminology concept dao
     * @param applicationService the application service
     * @param selectionService the selection service
     * @param propertyChangeTrackerService the property change tracker service
     * @param normalFormGenerator the normal form generator
     */
    public SnomedConceptRendererPanel(TerminologyConceptDAO terminologyConceptDAO,
                                      ApplicationService applicationService,
                                      SelectionService selectionService,
                                      PropertyChangeTrackerService propertyChangeTrackerService,
                                      NormalFormGenerator normalFormGenerator){
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.applicationService = applicationService;
        this.selectionService = selectionService;
        this.propertyChangeTrackerService = propertyChangeTrackerService;
        this.normalFormGenerator = normalFormGenerator;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        // create conceptHierarchyPanel
        createConceptHierarchyDialog();

        // create tree model
        treeModel = new SnomedConceptTreeModel();
        conceptTree = new JTree(treeModel);
        conceptTree.setCellRenderer(new SnomedConceptExpressionTreeCellRenderer());

        // add mouse listeners to tree
        conceptTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int row = conceptTree.getRowForLocation(e.getX(), e.getY());
                if(row > -1 && e.getClickCount() == 2)
                {
                    // get path for location selected
                    TreePath selectionPath = conceptTree.getPathForRow(row);
                    // get selection
                    Object selection = selectionPath.getLastPathComponent();

                    // evaluate selection and set behaviour
                    if(selection != null && selection instanceof SnomedRelationshipPropertyExpression)
                    {
                        SnomedRelationshipPropertyExpression propertyExpression =
                                (SnomedRelationshipPropertyExpression) selection;
                        // get contained relationship
                        SnomedRelationship relationship = propertyExpression.getRelationship();
                        if(relationship != null)
                        {
                            // set as selected propertyExpression
                            selectedPropertyExpression = propertyExpression;
                            // set as active relationship
                            activeRelationship = relationship;
                            SnomedConcept targetConcept = relationship.getTargetConcept();
                            if(targetConcept != null)
                            {
                                conceptHierarchyPanel.setConcept(targetConcept);
                            }

                            // show conceptHierarchyDialog
                            conceptHierarchyDialog.setVisible(true);
                        }
                    }
                }
            }
        });

        setViewportView(conceptTree);
    }

    /**
     * Creates the concept hierarchy dialog.
     */
    private synchronized void createConceptHierarchyDialog() {

        // wait for applicationService  to make a frame available
        while(activeFrame == null)
        {
            // check applicationService and set active frame         
            if (applicationService != null && applicationService.getFrameView() != null)
            {
                activeFrame = applicationService.getFrameView().getActiveFrame();
            }
        }

        // wrap conceptHierarchyPanel in a dialog
        conceptHierarchyPanel = new SnomedConceptHierarchyPanel(terminologyConceptDAO, selectionService, applicationService);
        conceptHierarchyPanel.initComponents();

        conceptHierarchyDialog = new JDialog(activeFrame, "Concept Hierarchy", Dialog.ModalityType.DOCUMENT_MODAL);
        conceptHierarchyDialog.getContentPane().add(conceptHierarchyPanel);
        conceptHierarchyDialog.setPreferredSize(new Dimension(300, 400));
        conceptHierarchyDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        conceptHierarchyDialog.setLocationRelativeTo(activeFrame);
        conceptHierarchyDialog.pack();
    }

    /**
     * Populate fields.
     */
    private void populateFields(){
        // generate expression in a new task and execute it
        GenerateExpressionTask task = new GenerateExpressionTask(applicationService.getActiveApplication(),
                getCloseToUserExpression(), normalFormGenerator, treeModel);
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionServiceListener#selectionChanged(uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService, java.lang.Object, java.lang.Object)
     */
    public void selectionChanged(SelectionService selectionService, Object selection, Object source) {
        // ensure source is not self and handle appropriately
        if(selection instanceof SnomedConcept && source.equals(conceptHierarchyPanel) && activeRelationship != null)
        {
            // save existing value
            CloseToUserExpression oldValue = getCloseToUserExpression();

            // set concept in activeRelationship  and selectedPropertyExpression
            SnomedConcept concept = (SnomedConcept) selection;
            activeRelationship.setTargetConcept(concept);
            activeRelationship.setTargetConceptID(concept.getConceptID());

            // set as relationship in selectedPropertyExpression
            selectedPropertyExpression.setProperty(new SnomedRelationshipProperty(activeRelationship));

            // notify listeners
            if (oldValue != null)
            {
                CloseToUserExpression newExpression = oldValue;
                // add activeRelationship to closeToUserExpression
                newExpression.addRefinement(selectedPropertyExpression);
                // fire property change event
                propertyChangeTrackerService.firePropertyChanged(EXPRESSION_CHANGED, oldValue, newExpression, this);
            }

            // hide conceptHierarchyDialog 
            conceptHierarchyDialog.setVisible(false);
        }
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
            populateFields();
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener#propertyChanged(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public void propertyChanged(String propertyName, Object oldValue, Object newValue, Object source) {

        if(EXPRESSION_CHANGED.equalsIgnoreCase(propertyName) && newValue instanceof CloseToUserExpression)
        {
            setCloseToUserExpression((CloseToUserExpression) newValue);
        }
    }
}
