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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel;

import com.jidesoft.swing.JideButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXTable;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.model.table.ConstraintTableModel;
import uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.model.table.TerminologyConstraintTableModel;
import uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.renderer.TerminologyConstraintTableCellRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryComponentExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataRangeConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.DataRangeFacetConstraint;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that renders a
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryComponentExpression}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 23, 2010 at 2:30:07 PM
 */
public class QueryComponentExpressionPanel extends JPanel implements PropertyChangeTrackerServiceListener {

    /** The component expression. */
    private QueryComponentExpression componentExpression;

    /** The terminology search service. */
    private TerminologySearchService terminologySearchService;

    /** The human readable render. */
    private HumanReadableRender humanReadableRender;

    /** The selection service. */
    private SelectionService selectionService;

    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;

    /** The application service. */
    private ApplicationService applicationService;

    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;

    /** The query service. */
    private QueryService queryService;

    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;

    /** The included constraint panel. */
    private TerminologyConstraintPanel activeConstraintPanel;

    /** The excluded constraint table model. */
    private TerminologyConstraintTableModel excludedConstraintTableModel;

    /** The excluded constraints table. */
    private JTable excludedConstraintsTable;

    /** The add exclusion icon. */
    private Icon addExclusionIcon;

    /** The delete exclusion icon. */
    private Icon deleteExclusionIcon;

    /** The add constraint icon. */
    private Icon addConstraintIcon;

    /** The delete constraint icon. */
    private Icon deleteConstraintIcon;

    /** The Constant ACTIVE_QUERY_CHANGED. */
    private final static String ACTIVE_QUERY_CHANGED = "ACTIVE_QUERY_CHANGED";
    
    /** The Constant CONSTRAINT_CHANGED. */
    private final static String CONSTRAINT_CHANGED = "CONSTRAINT_CHANGED";

    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryComponentExpressionPanel.class);
    
    /** The active frame. */
    private JFrame activeFrame;
    
    /** The active constraints dialog. */
    private JDialog activeConstraintsDialog;
    
    /** The query expression factory. */
    private QueryExpressionFactory  queryExpressionFactory;
    
    /** The active terminology constraint. */
    private TerminologyConstraint activeTerminologyConstraint;
    
    /** The excluded constraints panel. */
    private JPanel excludedConstraintsPanel;
    
    /** The constraint panel. */
    private ConstraintPanel constraintPanel;
//    private boolean addingNew = false;
    /** The editing existing. */
private boolean editingExisting = false;
    
    /** The active additional constraint. */
    private Constraint activeAdditionalConstraint;
    
    /** The constraints table model. */
    private ConstraintTableModel constraintsTableModel;
    
    /** The constraints table. */
    private JXTable constraintsTable;
    
    /** The constraints. */
    private java.util.List<Constraint> constraints = new ArrayList<Constraint>();    /** The constraint collapsible pane. */
    private JXCollapsiblePane constraintCollapsiblePane;
    
    /** The included constraint panel. */
    private TerminologyConstraintPanel includedConstraintPanel;
    
    /** The additional constraints panel. */
    private JPanel additionalConstraintsPanel;


    /**
     * Instantiates a new query component expression panel.
     *
     * @param terminologySearchService the terminology search service
     * @param humanReadableRender the human readable render
     * @param selectionService the selection service
     * @param terminologyConceptDAO the terminology concept dao
     * @param applicationService the application service
     * @param propertyChangeTrackerService the property change tracker service
     * @param normalFormGenerator the normal form generator
     * @param queryService the query service
     * @param queryExpressionFactory the query expression factory
     */
    public QueryComponentExpressionPanel(TerminologySearchService terminologySearchService,
                                         HumanReadableRender humanReadableRender, SelectionService selectionService,
                                         TerminologyConceptDAO terminologyConceptDAO,
                                         ApplicationService applicationService,
                                         PropertyChangeTrackerService propertyChangeTrackerService,
                                         NormalFormGenerator normalFormGenerator,
                                         QueryService queryService,
                                         QueryExpressionFactory queryExpressionFactory) {
        this.terminologySearchService = terminologySearchService;
        this.humanReadableRender = humanReadableRender;
        this.selectionService = selectionService;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.applicationService = applicationService;
        this.propertyChangeTrackerService = propertyChangeTrackerService;
        this.normalFormGenerator = normalFormGenerator;
        this.queryService = queryService;
        this.queryExpressionFactory = queryExpressionFactory;

        // create icons
        addExclusionIcon = new ImageIcon(QueryComponentExpressionPanel.class.getResource("icons/add.png"));
        deleteExclusionIcon = new ImageIcon(QueryComponentExpressionPanel.class.getResource("icons/delete.png"));
        addConstraintIcon = new ImageIcon(QueryComponentExpressionPanel.class.getResource("icons/add.png"));
        deleteConstraintIcon = new ImageIcon(QueryComponentExpressionPanel.class.getResource("icons/delete.png"));

        // create the initial componentExpression
        componentExpression = this.queryExpressionFactory.getQueryComponentExpression();
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        setName(getClass().getCanonicalName());

        // wait for application serive to register an active frame
        while(activeFrame == null)
        {
            if(applicationService != null && applicationService.getFrameView() != null)
            {
                activeFrame = applicationService.getFrameView().getActiveFrame();
            }
        }

        /*
        the general layout should be :
         -- A TerminologyConstraintPanel for the included constraint in the NORTH
         -- A TerminologyConstraintTable for the excluded constraints in the CENTRE.
         -- A constraints editing panel for data and temporal constraints in the SOUTH.
         */

        includedConstraintPanel = new TerminologyConstraintPanel(humanReadableRender, selectionService,
                terminologyConceptDAO, applicationService, propertyChangeTrackerService, normalFormGenerator,
                terminologySearchService,queryExpressionFactory.getConstraintFactory());
        includedConstraintPanel.initComponents();
        includedConstraintPanel.setName("includedConstraintPanel");
        propertyChangeTrackerService.registerListener(includedConstraintPanel);

        // create an dialog for creating and editing constraints
        createAdditionalConstraintPanel();
        createAdditionalConstraintsTablePanel();
        createExcludedTermsPanel();
        createActiveConstraintsDialog();

        // set layout and add components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(includedConstraintPanel, BorderLayout.NORTH);
        mainPanel.add(excludedConstraintsPanel, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(additionalConstraintsPanel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(450, 500));
    }

    /**
     * Creates the excluded terms panel.
     */
    private synchronized void createExcludedTermsPanel() {

        // create table for displaying excluded constraints
        excludedConstraintTableModel = new TerminologyConstraintTableModel();
        excludedConstraintsTable = new JTable(excludedConstraintTableModel);
        excludedConstraintsTable.setDefaultRenderer(TerminologyConstraint.class,
                new TerminologyConstraintTableCellRenderer(humanReadableRender));
        excludedConstraintsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // get row a click
                int row = excludedConstraintsTable.rowAtPoint(e.getPoint());
                if(row > -1 && e.getClickCount() == 2)
                {
//                    addingNew = false;
                    editingExisting = true;
                    // get constraint at row
                    activeTerminologyConstraint = excludedConstraintTableModel.getConstraintAtRow(row);
                    // set constraint in activeConstraintPanel and display activeConstraintsDialog
                    activeConstraintPanel.setConstraint(activeTerminologyConstraint);
                    activeConstraintsDialog.setVisible(true);
                }
            }
        });

        // create a label and control buttons for editing excluded constraints
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.LINE_AXIS));
        p3.add(new JLabel("Edit excluded constraints"));
        p3.add(Box.createHorizontalGlue());

        // add panel for editing excluded terms
        JideButton addExclusionTermButton = new JideButton(new AbstractAction("",
                addExclusionIcon){

            public void actionPerformed(ActionEvent e) {
                // show activeConstraintsDialog
//                addingNew = true;
                activeConstraintsDialog.setVisible(true);
            }
        });
        addExclusionTermButton.setName("addExclusionTermButton");

        JideButton deleteExclusionTermButton = new JideButton(new AbstractAction("",
                deleteExclusionIcon){

            public void actionPerformed(ActionEvent e) {
                final int row = excludedConstraintsTable.getSelectedRow();
                if(row > -1)
                {
                    QueryStatement oldValue = queryService.getActiveQuery();
                    // get term at row
                    TerminologyConstraint term = (TerminologyConstraint) excludedConstraintTableModel.getValueAt(row, 1);
                    // remove term from sub query
                    componentExpression.removeExcludedConstraint(term);
                    Runnable runnable = new Runnable() {
                        public void run() {
                            excludedConstraintTableModel.deleteRow(row);
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                    // notify listeners
                    propertyChangeTrackerService.firePropertyChanged(ACTIVE_QUERY_CHANGED, oldValue,
                            queryService.getActiveQuery(), QueryComponentExpressionPanel.this);
                }
            }
        });
        deleteExclusionTermButton.setName("deleteExclusionTermButton");

        // add buttons to p3
        p3.add(addExclusionTermButton);
        p3.add(deleteExclusionTermButton);

        // create panel that contains excluded constraints
        excludedConstraintsPanel = new JPanel(new BorderLayout());
        excludedConstraintsPanel.setBorder(BorderFactory.createTitledBorder("Excluded constraints"));
        excludedConstraintsPanel.add(p3, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(excludedConstraintsTable);
        excludedConstraintsPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates the active constraints dialog.
     */
    private synchronized void createActiveConstraintsDialog() {

        activeConstraintPanel = new TerminologyConstraintPanel(humanReadableRender, selectionService, terminologyConceptDAO,
                applicationService, propertyChangeTrackerService, normalFormGenerator,
                terminologySearchService,queryExpressionFactory.getConstraintFactory());
        activeConstraintPanel.initComponents();
        activeConstraintPanel.setName("activeConstraintPanel");
        propertyChangeTrackerService.registerListener(activeConstraintPanel);

        // create a dialog for displaying the excludedConstraintPanel
        activeConstraintsDialog = new JDialog(activeFrame, "Edit Constraint", Dialog.ModalityType.DOCUMENT_MODAL);
        activeConstraintsDialog.getContentPane().add(activeConstraintPanel);
        activeConstraintsDialog.setLocationRelativeTo(activeFrame);
        activeConstraintsDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        activeConstraintsDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                activeConstraintsDialog.setVisible(false);
                // set adding new to false
//                addingNew = false;
                editingExisting = false;
            }
        });
        activeConstraintsDialog.pack();
    }


    /**
     * Creates the additional constraints table panel.
     */
    private synchronized void createAdditionalConstraintsTablePanel(){

        constraintsTableModel = new ConstraintTableModel(constraints);
        constraintsTable = new JXTable(constraintsTableModel);
        constraintsTable.setPreferredScrollableViewportSize(new Dimension(400, 80));
        constraintsTable.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                // get row at click point
                int row = constraintsTable.rowAtPoint(e.getPoint());
                if(row > -1)
                {
                    // check if double click
                    if(e.getClickCount() == 2)
                    {
                        // get term at row
                        Constraint constraint =  constraintsTableModel.getConstraintAtRow(row);
                        // set as active constraint
                        activeAdditionalConstraint = constraint;
                        // show in active constraint dialog
                        constraintPanel.setConstraint(activeAdditionalConstraint);
                        // set editing tag to true
                        editingExisting = true;
                        constraintCollapsiblePane.setCollapsed(false);
                    }
                }
            }
        });

        // add panel for editing excluded terms
        JideButton addConstraintButton = new JideButton(new AbstractAction("",
                addConstraintIcon){

            public void actionPerformed(ActionEvent e) {
                constraintCollapsiblePane.setCollapsed(false);
                // disable editing tag
                editingExisting = false;
            }
        });
        addConstraintButton.setName("addConstraintButton");
        addConstraintButton.setBorderPainted(false);

        JideButton deleteConstraintButton = new JideButton(new AbstractAction("",
                deleteConstraintIcon){

            public void actionPerformed(ActionEvent e) {
                final int row = constraintsTable.getSelectedRow();
                if(row > -1)
                {
                    // get constraint at row
                    Constraint cons = constraintsTableModel.getConstraintAtRow(row);
                    // remove from subquery
                    componentExpression.removeAdditionalConstraint(cons);
                    Runnable runnable = new Runnable() {
                        public void run() {
                            // delete constraint at row
                            constraintsTableModel.deleteRow(row);
                            constraintsTable.revalidate();
                            QueryComponentExpressionPanel.this.revalidate();
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                    // notify listeners of query change
                    queryService.queryChanged(queryService.getActiveQuery(), QueryComponentExpressionPanel.this);
                }
            }
        });
        deleteConstraintButton.setName("deleteConstraintButton");
        deleteConstraintButton.setBorderPainted(false);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(new JLabel("Edit constraints"));
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(addConstraintButton);
        buttonsPanel.add(deleteConstraintButton);

        additionalConstraintsPanel = new JPanel(new BorderLayout());
        additionalConstraintsPanel.setBorder(BorderFactory.createTitledBorder("Additional Constraints"));
        additionalConstraintsPanel.add(buttonsPanel , BorderLayout.NORTH);
        additionalConstraintsPanel.add(new JScrollPane(constraintsTable), BorderLayout.CENTER);
        additionalConstraintsPanel.add(constraintCollapsiblePane, BorderLayout.SOUTH);

    }

    /**
     * Creates the additional constraint panel.
     */
    private synchronized void createAdditionalConstraintPanel(){

        constraintPanel = new ConstraintPanel(queryService);
        JButton saveButton = new JButton(new AbstractAction("Save"){
            public void actionPerformed(ActionEvent arg0) {

                if (!editingExisting)
                {
                    // get constraint from panel and add to model
                    activeAdditionalConstraint = constraintPanel.getConstraint();
                    // add constraint to sub query
                    componentExpression.addAdditionalConstraint(activeAdditionalConstraint);
                    // update model
                    constraintsTableModel.populateConstraints(new ArrayList<Constraint>(componentExpression.getAdditionalConstraints()));
                    // notify listeners of query change
                    queryService.queryChanged(queryService.getActiveQuery(), QueryComponentExpressionPanel.this);
                }
                else
                {
                    Constraint oldConstraint = activeAdditionalConstraint;
                    Constraint modifiedConstraint = constraintPanel.getConstraint();
                    if(modifiedConstraint instanceof DataRangeConstraint)
                    {
                        DataRangeConstraint drc = (DataRangeConstraint) modifiedConstraint;
                        DataRangeFacetConstraint lfc = drc.getLowerBoundFacet();
                        DataRangeFacetConstraint ufc = drc.getUpperBoundFacet();
                    }
                    // remove old constraint from sub query
                    componentExpression.removeAdditionalConstraint(oldConstraint);
                    // add new constraint to sub query
                    componentExpression.addAdditionalConstraint(modifiedConstraint);
                    // update model
                    constraintsTableModel.populateConstraints(new ArrayList<Constraint>(componentExpression.getAdditionalConstraints()));
                    // notify listeners of query change
                    queryService.queryChanged(queryService.getActiveQuery(), QueryComponentExpressionPanel.this);
                }

                // collapse panel
                constraintCollapsiblePane.setCollapsed(true);
            }
        });

        JButton cancelButton = new JButton(new AbstractAction("Cancel"){

            public void actionPerformed(ActionEvent arg0) {
                // collapse panel
                constraintCollapsiblePane.setCollapsed(true);
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        constraintCollapsiblePane = new JXCollapsiblePane();
        constraintCollapsiblePane.setLayout(new BorderLayout());
        constraintCollapsiblePane.add(constraintPanel, BorderLayout.CENTER);
        constraintCollapsiblePane.add(buttonsPanel, BorderLayout.SOUTH);
        constraintCollapsiblePane.setCollapsed(true);
    }
    /**
     * Gets the component expression.
     *
     * @return the component expression
     */
    public QueryComponentExpression getComponentExpression() {
        return componentExpression;
    }

    /**
     * Sets the component expression.
     *
     * @param componentExpression the new component expression
     */
    public void setComponentExpression(QueryComponentExpression componentExpression) {
        this.componentExpression = componentExpression;
        // populate fields
        populateFields(getComponentExpression());
    }

    /**
     * Populate fields.
     *
     * @param componentExpression the component expression
     */
    private void populateFields(QueryComponentExpression componentExpression) {
        if(componentExpression != null)
        {
            if (componentExpression.getIncludedConstraint() != null)
            {
                includedConstraintPanel.setConstraint(componentExpression.getIncludedConstraint());
            }

            if (componentExpression.getExcludedConstraints() != null)
            {
                excludedConstraintTableModel.populateModel(componentExpression.getExcludedConstraints());
            }
        }
    }

    /**
     * Property changed.
     *
     * @param propertyName the property name
     * @param oldValue the old value
     * @param newValue the new value
     * @param source the source
     */
    public void propertyChanged(String propertyName, Object oldValue, Object newValue, Object source) {
        // check if property is CONSTRAINT_CHANGED and handle accordingly
        if(CONSTRAINT_CHANGED.equalsIgnoreCase(propertyName) && newValue instanceof TerminologyConstraint)
        {
            QueryStatement oldQueryStatement = queryService.getActiveQuery();
            if(source == includedConstraintPanel)
            {
                getComponentExpression().setIncludedConstraint((TerminologyConstraint) newValue);
                logger.debug("Set included constraint.");
            }
            else if(source == activeConstraintPanel)
            {
                if(editingExisting)
                {
                    // remove existing constraint
                    getComponentExpression().removeExcludedConstraint(activeTerminologyConstraint);
                    // add new value
                    getComponentExpression().addExcludedConstraint((TerminologyConstraint) newValue);
                    logger.debug("Edited excluded constraint.");
                }
                else
                {
                    getComponentExpression().addExcludedConstraint((TerminologyConstraint) newValue);
                    logger.debug("Added excluded constraint.");
                }
                
                excludedConstraintTableModel.populateModel(getComponentExpression().getExcludedConstraints());
            }

            // update query in listeners
            queryService.queryChanged(queryService.getActiveQuery(), this);
            propertyChangeTrackerService.firePropertyChanged(ACTIVE_QUERY_CHANGED, oldQueryStatement, queryService.getActiveQuery(), this);
        }
    }
}
