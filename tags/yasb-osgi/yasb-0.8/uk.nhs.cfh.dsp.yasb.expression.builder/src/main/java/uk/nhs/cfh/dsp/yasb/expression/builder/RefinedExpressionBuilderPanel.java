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
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXList;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.CloseToUserExpressionImpl;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedRelationshipImpl;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionServiceListener;
import uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.SnomedConceptHierarchyPanel;
import uk.nhs.cfh.dsp.yasb.expression.builder.model.PropertyExpressionListModel;
import uk.nhs.cfh.dsp.yasb.expression.builder.panels.ConceptPanel;
import uk.nhs.cfh.dsp.yasb.expression.builder.renderer.ExpressionListCellRenderer;
import uk.nhs.cfh.dsp.yasb.expression.renderer.ExpressionRenderingLabel;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;
import uk.nhs.cfh.dsp.yasb.searchpanel.SearchPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that allows the user to generate a post-coordinated expression
 * as a {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 23, 2010 at 12:26:28 PM
 */
public class RefinedExpressionBuilderPanel extends JPanel implements SelectionServiceListener,
        PropertyChangeTrackerServiceListener {

    /** The close to user expression. */
    private CloseToUserExpression closeToUserExpression;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ExpressionBuilderPanel.class);
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The search panel. */
    private SearchPanel searchPanel;
    
    /** The terminology search service. */
    private TerminologySearchService terminologySearchService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;

    /** The collapsible pane. */
    private JXCollapsiblePane collapsiblePane;
    
    /** The laterality label. */
    private JLabel lateralityLabel;
    
    /** The active frame. */
    private JFrame activeFrame;
    
    /** The concept hierarchy dialog. */
    private JDialog conceptHierarchyDialog;
    
    /** The laterality hierarchy panel. */
    private SnomedConceptHierarchyPanel lateralityHierarchyPanel;
    
    /** The laterality relationship. */
    private SnomedRelationship lateralityRelationship;
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;
    
    /** The Constant EXPRESSION_CHANGED. */
    private static final String EXPRESSION_CHANGED = "EXPRESSION_CHANGED";
    
    /** The laterality parent concept. */
    private SnomedConcept lateralityParentConcept;
    
    /** The border offset. */
    private static int borderOffset = 5;
    
    /** The rendering label. */
    private ExpressionRenderingLabel renderingLabel;
    
    /** The focus concept panel. */
    private ConceptPanel focusConceptPanel;
    
    /** The properties list. */
    private JXList propertiesList;
    
    /** The properties scroll pane. */
    private JScrollPane propertiesScrollPane;
    
    /** The selected property expression. */
    private SnomedRelationshipPropertyExpression selectedPropertyExpression;
    
    /** The active relationship. */
    private SnomedRelationship activeRelationship;
    
    /** The property expression list model. */
    private PropertyExpressionListModel propertyExpressionListModel;


    /**
     * Instantiates a new refined expression builder panel.
     *
     * @param terminologySearchService the terminology search service
     * @param applicationService the application service
     * @param selectionService the selection service
     * @param terminologyConceptDAO the terminology concept dao
     * @param propertyChangeTrackerService the property change tracker service
     * @param humanReadableRender the human readable render
     * @param normalFormGenerator the normal form generator
     */
    public RefinedExpressionBuilderPanel(TerminologySearchService terminologySearchService,
                                         ApplicationService applicationService, SelectionService selectionService,
                                         TerminologyConceptDAO terminologyConceptDAO,
                                         PropertyChangeTrackerService propertyChangeTrackerService,
                                         HumanReadableRender humanReadableRender,
                                         NormalFormGenerator normalFormGenerator) {
        this.terminologySearchService = terminologySearchService;
        this.applicationService = applicationService;
        this.selectionService = selectionService;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.propertyChangeTrackerService = propertyChangeTrackerService;
        this.humanReadableRender = humanReadableRender;
        this.normalFormGenerator = normalFormGenerator;
    }

    /**
     * empty constructor for IOC.
     */
    public RefinedExpressionBuilderPanel() {
        // empty constructor for IOC
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        // generate and store concept corresponding to Side |182353008|
        if (terminologyConceptDAO != null)
        {
            lateralityParentConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, "182353008");
        }

        // add a collapsible panel in NORTH position with a concept search panel
        searchPanel = new SearchPanel(terminologySearchService, selectionService,
                applicationService, terminologyConceptDAO);
        searchPanel.initComponents();
        searchPanel.setPreferredSize(new Dimension(350, 450));
        collapsiblePane = new JXCollapsiblePane(new BorderLayout());
        collapsiblePane.add(searchPanel, BorderLayout.CENTER);
        collapsiblePane.setCollapsed(true);

        // create panel for rendering label
        renderingLabel = new ExpressionRenderingLabel(humanReadableRender);
        renderingLabel.initComponents();

        focusConceptPanel = new ConceptPanel(humanReadableRender);
        focusConceptPanel.initComponents();

        // create top panel with some instructions
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        p1.add(focusConceptPanel);

        // add button for editing focus concept
        JideButton editFocusConceptButton = new JideButton(new AbstractAction("Edit") {
            public void actionPerformed(ActionEvent e) {
                if(collapsiblePane.isCollapsed())
                {
                    collapsiblePane.setCollapsed(false);
                }
                else
                {
                    collapsiblePane.setCollapsed(true);
                }
            }
        });
        p1.add(Box.createHorizontalGlue());
        p1.add(editFocusConceptButton);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(renderingLabel);
        topPanel.add(p1);

        // create scrollPane that contains the properties
        propertyExpressionListModel = new PropertyExpressionListModel(normalFormGenerator);
        propertiesList = new JXList(propertyExpressionListModel);
//        propertiesList.setCellRenderer(new PropertyExpressionListCellRenderer(selectionService,
//                terminologyConceptDAO, applicationService, humanReadableRender));
        propertiesList.setCellRenderer(new ExpressionListCellRenderer(selectionService,
                terminologyConceptDAO, applicationService, humanReadableRender));
        propertiesScrollPane = new JScrollPane();
        propertiesScrollPane.setViewportView(propertiesList);

//        // add mouse listeners to tree
//        propertiesList.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int row = propertiesList.getSelectedIndex();
//                if(row > -1 && e.getClickCount() == 2)
//                {
//                    // get selection
//                    Object selection = propertiesList.getSelectedValue();
//
//                    // evaluate selection and set behaviour
//                    if(selection instanceof SnomedRelationshipPropertyExpression)
//                    {
//                        SnomedRelationshipPropertyExpression propertyExpression =
//                                (SnomedRelationshipPropertyExpression) selection;
//                        // get contained relationship
//                        SnomedRelationship relationship = propertyExpression.getRelationship();
//                        if(relationship != null)
//                        {
//                            // set as selected propertyExpression
//                            selectedPropertyExpression = propertyExpression;
//                            // set as active relationship
//                            activeRelationship = relationship;
//                        }
//                    }
//                }
//            }
//        });

        // create laterality hierarchy panel
        createLateralityConceptHierarchyDialog();

        // add panel for adding laterality directly
        JPanel bottomPanel = new JPanel(new BorderLayout());
        // add label and button for laterilty value
        lateralityLabel = new JLabel();
        JButton editLateralityButton = new JButton(new AbstractAction("Edit") {
            public void actionPerformed(ActionEvent e) {
                /*
                 get lateralityHierarchyPanel and set parent to
                 Side |182353008|
                */

                lateralityHierarchyPanel.setConcept(lateralityParentConcept);
                // show conceptHierarchyDialog
                conceptHierarchyDialog.setLocationRelativeTo(activeFrame);
                conceptHierarchyDialog.setVisible(true);
            }
        });
        bottomPanel.add(new JLabel("<html><b>Laterality of concept </b></html>"), BorderLayout.WEST);
        bottomPanel.add(lateralityLabel, BorderLayout.CENTER);
        bottomPanel.add(editLateralityButton, BorderLayout.EAST);

        // add panels to this panel and main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(propertiesScrollPane, BorderLayout.CENTER);

        // add to this panel
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(borderOffset, borderOffset, borderOffset, borderOffset));
        add(collapsiblePane, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the laterality concept hierarchy dialog.
     */
    private synchronized void createLateralityConceptHierarchyDialog() {

        // wait for applicationService  to make a frame available
        while(activeFrame == null)
        {
            // check applicationService and set active frame
            if (applicationService != null)
            {
                activeFrame = applicationService.getFrameView().getActiveFrame();
            }
        }
        // create a laterality relationship that will be reused
        lateralityRelationship = new SnomedRelationshipImpl();
        lateralityRelationship.setRelationshipType("272741003");
        lateralityRelationship.setName("Laterality");

        // create lateralityHierarchyPanel panel
        lateralityHierarchyPanel = new SnomedConceptHierarchyPanel(terminologyConceptDAO, selectionService, applicationService);
        lateralityHierarchyPanel.initComponents();

        // wrap lateralityHierarchyPanel in a dialog
        conceptHierarchyDialog = new JDialog(activeFrame, "Concept Hierarchy", Dialog.ModalityType.DOCUMENT_MODAL);
        conceptHierarchyDialog.getContentPane().add(lateralityHierarchyPanel);
        conceptHierarchyDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        conceptHierarchyDialog.pack();
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
        if ( closeToUserExpression != null)
        {
//            propertyChangeTrackerService.firePropertyChanged(EXPRESSION_CHANGED, getCloseToUserExpression(),
//                    closeToUserExpression, this);
            this.closeToUserExpression = closeToUserExpression;
            renderingLabel.setExpression(this.closeToUserExpression);
            focusConceptPanel.setConcept(this.closeToUserExpression.getSingletonFocusConcept());
            populatePropertiesList();
        }
    }

    /**
     * Populate properties list.
     */
    private void populatePropertiesList() {

        class PopulateModelTask extends Task<Void, Void>{
            private CloseToUserExpression cte;

            PopulateModelTask(Application application, CloseToUserExpression closeToUserExpression) {
                super(application);
                this.cte = closeToUserExpression;
            }

            @Override
            protected Void doInBackground() throws Exception {
                propertyExpressionListModel.setCloseToUserExpression(cte);
                return null;
            }
        }

        PopulateModelTask task = new PopulateModelTask(applicationService.getActiveApplication(), getCloseToUserExpression());
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionServiceListener#selectionChanged(uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService, java.lang.Object, java.lang.Object)
     */
    public void selectionChanged(SelectionService selectionService, Object selection, Object source) {
        // check source is not self and handle appropriately
        if(selection instanceof SnomedConcept)
        {
            SnomedConcept concept = (SnomedConcept) selection;
            if(source.equals(searchPanel))
            {
                // create new close to use expression using concept and set as expression in snomedConceptRendererPanel
                CloseToUserExpression localExpression = new CloseToUserExpressionImpl(concept);
                // notify listeners of closeToUserExpression  change
                propertyChangeTrackerService.firePropertyChanged(EXPRESSION_CHANGED, getCloseToUserExpression(),
                        localExpression, this);
                // collapse searchPanel
                collapsiblePane.setCollapsed(true);
            }
            else if(source.equals(lateralityHierarchyPanel))
            {
                if (getCloseToUserExpression() != null)
                {
                    // set lateralityLabel
                    lateralityLabel.setText(concept.getPreferredLabel());
                    lateralityLabel.setToolTipText(concept.getConceptID());
                    // set values for lateralityRelationship
                    lateralityRelationship.setTargetConcept(concept);
                    lateralityRelationship.setTargetConceptID(concept.getConceptID());
                    // notify listeners
                    notifyListeners();
                }

                // close conceptHierarchyDialog
                conceptHierarchyDialog.setVisible(false);
            }
        }
    }

    /**
     * Notify listeners.
     */
    private void notifyListeners() {
        CloseToUserExpression oldValue = getCloseToUserExpression();
        // add laterality to closeToUserExpression
        getCloseToUserExpression().addRefinement(new SnomedRelationshipPropertyExpression(lateralityRelationship));
        // notify listeners of closeToUserExpression  change
        propertyChangeTrackerService.firePropertyChanged(EXPRESSION_CHANGED, oldValue, getCloseToUserExpression(), this);
    }

    /**
     * Register.
     */
    public synchronized void register(){
        selectionService.registerListener(this);
        propertyChangeTrackerService.registerListener(this);
    }

    /**
     * Un register.
     */
    public synchronized void unRegister(){
        selectionService.unregisterListener(this);
        propertyChangeTrackerService.unregisterListener(this);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener#propertyChanged(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public void propertyChanged(String propertyName, Object oldValue, Object newValue, Object source){
        if(EXPRESSION_CHANGED.equalsIgnoreCase(propertyName) && newValue instanceof CloseToUserExpression)
        {
            // set closeToUserExpression to newValue
            setCloseToUserExpression((CloseToUserExpression) newValue);
        }
    }

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Sets the terminology search service.
     *
     * @param terminologySearchService the new terminology search service
     */
    public synchronized void setTerminologySearchService(TerminologySearchService terminologySearchService) {
        this.terminologySearchService = terminologySearchService;
    }

    /**
     * Sets the application service.
     *
     * @param applicationService the new application service
     */
    public synchronized void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Sets the selection service.
     *
     * @param selectionService the new selection service
     */
    public synchronized void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    /**
     * Sets the human readable render.
     *
     * @param humanReadableRender the new human readable render
     */
    public synchronized void setHumanReadableRender(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Sets the property change tracker service.
     *
     * @param propertyChangeTrackerService the new property change tracker service
     */
    public synchronized void setPropertyChangeTrackerService(PropertyChangeTrackerService propertyChangeTrackerService) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }

    /**
     * Sets the normal form generator.
     *
     * @param normalFormGenerator the new normal form generator
     */
    public synchronized void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }
}
