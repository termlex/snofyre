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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionServiceListener;
import uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.model.ReportingQueryStatementTreeModel;
import uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.renderer.StyledQueryStatementTreeCellRenderer;
import uk.nhs.cfh.dsp.srth.query.converters.text.QueryExpressionPlainTextRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.*;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.service.QueryServiceListener;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


// TODO: Auto-generated Javadoc
/**
 * A custom panel that allows creation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl} via a
 * tree interface.
 *
 * <br> Version : @#VersionNumber#@
 * <br> Written by @author Jay Kola
 * <br> Created on Apr 5, 2009 at 11:19:13 PM
 * <br> Modified on Thursday; November 26, 2009 at 12:03 PM
 * <br> Modified on Sunday; December 6, 2009 at 4:21 PM to create user friendly labels
 */
@SuppressWarnings({"serial", "unused"})
public class QueryAuthoringTreePanel extends JPanel implements ActionListener, QueryServiceListener, SelectionServiceListener{

    /** The active query. */
    private QueryStatement activeQuery;
    
    /** The selected expression. */
    private QueryExpression selectedExpression;
    
    /** The reporting query tree model. */
    private ReportingQueryStatementTreeModel reportingQueryTreeModel;

    /** The query actions menu. */
    private JPopupMenu queryActionsMenu;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryAuthoringTreePanel.class);
    
    /** The query interface tree. */
    private JXTree queryInterfaceTree;
    
    /** The toggle status menu. */
    private JPopupMenu toggleStatusMenu;
    
    /** The query expression plain text renderer. */
    private QueryExpressionPlainTextRenderer queryExpressionPlainTextRenderer;

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
    
    /** The active frame. */
    private JFrame activeFrame;
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;
    
    /** The query expression factory. */
    private QueryExpressionFactory queryExpressionFactory;

    /** The query component expression panel. */
    private QueryComponentExpressionPanel queryComponentExpressionPanel;
    
    /** The query component expression dialog. */
    private JDialog queryComponentExpressionDialog;

    /**
     * Instantiates a new query authoring tree panel.
     *
     * @param applicationService the application service
     * @param queryService the query service
     * @param terminologySearchService the terminology search service
     * @param terminologyConceptDAO the terminology concept dao
     * @param selectionService the selection service
     * @param queryExpressionPlainTextRenderer the query expression plain text renderer
     * @param propertyChangeTrackerService the property change tracker service
     * @param humanReadableRender the human readable render
     * @param normalFormGenerator the normal form generator
     * @param queryExpressionFactory the query expression factory
     */
    public QueryAuthoringTreePanel(ApplicationService applicationService,
                                   QueryService queryService, TerminologySearchService terminologySearchService,
                                   TerminologyConceptDAO terminologyConceptDAO, SelectionService selectionService,
                                   QueryExpressionPlainTextRenderer queryExpressionPlainTextRenderer,
                                   PropertyChangeTrackerService propertyChangeTrackerService,
                                   HumanReadableRender humanReadableRender,
                                   NormalFormGenerator normalFormGenerator,
                                   QueryExpressionFactory queryExpressionFactory) {
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.terminologySearchService = terminologySearchService;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.selectionService = selectionService;
        this.queryExpressionPlainTextRenderer = queryExpressionPlainTextRenderer;
        this.propertyChangeTrackerService = propertyChangeTrackerService;
        this.humanReadableRender = humanReadableRender;
        this.normalFormGenerator = normalFormGenerator;
        this.queryExpressionFactory = queryExpressionFactory;

        activeQuery = queryService.getActiveQuery();
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents()
    {
        setName(getClass().getCanonicalName());

        createQueryComponentExpressionDialog();
        // create pop up menu
        createPopupMenus();
        //create tree
        createTreeInterface();

        // set layout and add tree
        setLayout(new BorderLayout());
        add(new JScrollPane(queryInterfaceTree), BorderLayout.CENTER);
    }
    
    /**
     * Creates the tree interface.
     */
    private void createTreeInterface(){

        reportingQueryTreeModel = new ReportingQueryStatementTreeModel(activeQuery);
        queryInterfaceTree = new JXTree(reportingQueryTreeModel);
//        queryInterfaceTree.setCellRenderer(new ReportingQueryStatementTreeCellRenderer(humanReadableRender));
        queryInterfaceTree.setCellRenderer(new StyledQueryStatementTreeCellRenderer(humanReadableRender));
        queryInterfaceTree.addHighlighter(HighlighterFactory.createAlternateStriping());
        // add listener for mouse events
        queryInterfaceTree.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                evaluatePopupAction(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                evaluatePopupAction(e);
            }

            private void evaluatePopupAction(MouseEvent e) {
                // get node selected
                int row = queryInterfaceTree.getRowForLocation(e.getX(), e.getY());

                if (row > -1)
                {
                    TreePath selectedPath = queryInterfaceTree.getPathForLocation(e.getX(), e.getY());
                    Object node = selectedPath.getLastPathComponent();
                    if (node != null && node instanceof QueryExpression)
                    {
                        QueryExpression expression = (QueryExpression) node;
                        // set selected expression
                        selectedExpression = expression;
                        // check if right click
                        if(expression instanceof QueryIntersectionExpression
                                || expression instanceof QueryUnionExpression
                                || expression instanceof QueryStatement)
                        {
                            if (e.isPopupTrigger())
                            {
                                queryActionsMenu.show(e.getComponent(), e.getX(), e.getY());
                            }
                        }
                        else if(expression instanceof QueryComponentExpression)
                        {
                            if(e.getClickCount() == 2)
                            {
                                QueryComponentExpression componentExpression = (QueryComponentExpression) expression;
                                // get selected sub query and display it in a dialog
                                queryComponentExpressionPanel.setComponentExpression(componentExpression);
//                                queryComponentExpressionDialog.setTitle("Author Component Query");
                                queryComponentExpressionDialog.setVisible(true);
                            }
                            else if (e.isPopupTrigger())
                            {
                                toggleStatusMenu.show(e.getComponent(), e.getX(), e.getY());
                            }
                        }
                    }
                }
                else
                {
                    if (queryActionsMenu.isVisible()) {
                        queryActionsMenu.setVisible(false);
                    }

                    if(toggleStatusMenu.isVisible())
                    {
                        toggleStatusMenu.setVisible(false);
                    }
                }
            }

        });
    }

    /**
     * Creates the popup menus.
     */
    private void createPopupMenus(){

        queryActionsMenu = new JPopupMenu("Query Actions");
        JMenuItem orMenuItem = new JMenuItem("ANY of the following");
        orMenuItem.setActionCommand("Add OR");
        orMenuItem.addActionListener(this);

        JMenuItem andMenuItem = new JMenuItem("ALL of the following");
        andMenuItem.setActionCommand("Add And");
        andMenuItem.addActionListener(this);
        // add submenu to group logical relationships
        JMenu logicalSubMenu = new JMenu("Add Logical query");
        logicalSubMenu.add(andMenuItem);
        logicalSubMenu.add(orMenuItem);

        JMenuItem afterMenuItem = new JMenuItem("After");
        afterMenuItem.setActionCommand("After");
        afterMenuItem.addActionListener(this);
        JMenuItem beforeMenuItem = new JMenuItem("Before");
        beforeMenuItem.setActionCommand("Before");
        beforeMenuItem.addActionListener(this);
        // add submenu to group temporal relationships
        JMenu temporalSubMenu = new JMenu("Add Temporal query");
        temporalSubMenu.add(beforeMenuItem);
        temporalSubMenu.add(afterMenuItem);

        JMenuItem addCriterionMenuItem = new JMenuItem("Add Query criterion");
        addCriterionMenuItem.setActionCommand("Add Query criterion");
        addCriterionMenuItem.addActionListener(this);

        JMenuItem removeMenuItem1 = new JMenuItem("Remove criterion");
        removeMenuItem1.setActionCommand("Remove Expression");
        removeMenuItem1.addActionListener(this);

        queryActionsMenu.add(logicalSubMenu);
        queryActionsMenu.add(temporalSubMenu);
        queryActionsMenu.add(addCriterionMenuItem);
        queryActionsMenu.add(removeMenuItem1);

        queryActionsMenu.add(new AbstractAction("Toggle execution"){

            public void actionPerformed(ActionEvent arg0) {

                // get currently selected expression and toggle run time status between SKIP and EXECUTE
                QueryExpression.QueryRunTimeStatus status = selectedExpression.getRunTimeStatus();
                if (status == QueryExpression.QueryRunTimeStatus.EXECUTE) {
                    selectedExpression.setRunTimeStatus(QueryExpression.QueryRunTimeStatus.SKIP);
                }
                else{
                    selectedExpression.setRunTimeStatus(QueryExpression.QueryRunTimeStatus.EXECUTE);
                }
                // hide queryActionsMenu
                queryActionsMenu.setVisible(false);
                // revalidate tree
                queryInterfaceTree.revalidate();
            }
        });

        toggleStatusMenu = new JPopupMenu();
        JMenuItem removeMenuItem2 = new JMenuItem("Remove criterion");
        removeMenuItem2.setActionCommand("Remove Expression");
        removeMenuItem2.addActionListener(this);
        toggleStatusMenu.add(removeMenuItem2);
        toggleStatusMenu.add(new AbstractAction("Toggle execution"){

            public void actionPerformed(ActionEvent arg0) {

                // get currently selected expresion and toggle run time status between SKIP and EXECUTE
                QueryExpression.QueryRunTimeStatus status = selectedExpression.getRunTimeStatus();
                if (status == QueryExpression.QueryRunTimeStatus.EXECUTE) {
                    selectedExpression.setRunTimeStatus(QueryExpression.QueryRunTimeStatus.SKIP);
                }
                else{
                    selectedExpression.setRunTimeStatus(QueryExpression.QueryRunTimeStatus.EXECUTE);
                }

                // hide toggleStatusMenu
                toggleStatusMenu.setVisible(false);
                // revalidate tree
                queryInterfaceTree.revalidate();
                // notify listeners
                queryService.queryChanged(activeQuery, QueryAuthoringTreePanel.this);
            }
        });
    }

    /**
     * Creates the query component expression dialog.
     */
    private void createQueryComponentExpressionDialog(){

        // wait for applicationService  to make a frame available
        while( applicationService == null || activeFrame == null)
        {
            // check applicationService
            if (applicationService != null && applicationService.getFrameView() != null)
            {
                activeFrame = applicationService.getFrameView().getActiveFrame();
            }
        }
        // make dialog modal
        queryComponentExpressionPanel = new QueryComponentExpressionPanel(terminologySearchService, humanReadableRender, selectionService,
                terminologyConceptDAO, applicationService, propertyChangeTrackerService,
                normalFormGenerator, queryService, queryExpressionFactory);
        // initialise components
        queryComponentExpressionPanel.initComponents();
        propertyChangeTrackerService.registerListener(queryComponentExpressionPanel);

        queryComponentExpressionDialog = new JDialog(activeFrame, "Component Expression",
                Dialog.ModalityType.DOCUMENT_MODAL);
        queryComponentExpressionDialog.setPreferredSize(new Dimension(350, 450));
        queryComponentExpressionDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        queryComponentExpressionDialog.getContentPane().add(queryComponentExpressionPanel);
        queryComponentExpressionDialog.pack();
        queryComponentExpressionDialog.setLocationRelativeTo(activeFrame);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if("Add And".equals(actionCommand))
        {
            addExpressionAndNotify(queryExpressionFactory.getQueryIntersectionExpression());
        }
        else if("Before".equals(actionCommand))
        {
            QueryIntersectionExpression intersectionObject = queryExpressionFactory.getQueryIntersectionExpression();
            intersectionObject.setOperator(QueryExpression.QueryOperatorType.BEFORE);
            addExpressionAndNotify(intersectionObject);
        }
        else if("After".equals(actionCommand))
        {
            QueryIntersectionExpression intersectionObject = queryExpressionFactory.getQueryIntersectionExpression();
            intersectionObject.setOperator(QueryExpression.QueryOperatorType.AFTER);
            addExpressionAndNotify(intersectionObject);
        }
        else if("Add OR".equals(actionCommand))
        {
            addExpressionAndNotify(queryExpressionFactory.getQueryUnionExpression());
        }
        else if("Remove Expression".equals(actionCommand))
        {
            // get parent expression of selected expression
            QueryExpression parentExpression = selectedExpression.getParentExpression();
            if(parentExpression != null)
            {
                // update models
                reportingQueryTreeModel.removeNodeFromParent(selectedExpression);
                // remove actual relationship
                parentExpression.removeChildExpression(selectedExpression);
                // set selected expression to parent expression
                selectedExpression = parentExpression;
                // hide queryActionsMenu
                queryActionsMenu.setVisible(false);
                toggleStatusMenu.setVisible(false);
                // notify listeners
                queryService.queryChanged(activeQuery, this);
            }
        }
        else if("Add Query criterion".equals(actionCommand))
        {
            // hide queryActionsMenu
            queryActionsMenu.setVisible(false);
            // add a new query component expression to query
            QueryComponentExpression componentExpression = queryExpressionFactory.getQueryComponentExpression();
            // add componentExpression to selectedExpression
            selectedExpression.addChildExpression(componentExpression);
            // update models
            reportingQueryTreeModel.addChildToParent(componentExpression, selectedExpression);
            queryComponentExpressionPanel.setComponentExpression(componentExpression);
            if (queryComponentExpressionDialog != null)
            {
                queryComponentExpressionDialog.setVisible(true);
            }
        }
    }

    /**
     * Adds the expression and notify.
     *
     * @param queryExpression the query expression
     */
    private void addExpressionAndNotify(QueryExpression queryExpression){
        // add queryExpression to selectedExpression
        selectedExpression.addChildExpression(queryExpression);
        // update models
        reportingQueryTreeModel.addChildToParent(queryExpression, selectedExpression);
        // hide queryActionsMenu
        queryActionsMenu.setVisible(false);
        // set selected expression to newly added expression
        selectedExpression = queryExpression;
        // notify listeners
        queryService.queryChanged(activeQuery, this);
        Runnable runnable = new Runnable() {
            public void run() {
                queryInterfaceTree.revalidate();
            }
        };
        
        SwingUtilities.invokeLater(runnable);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.service.QueryServiceListener#queryChanged(uk.nhs.cfh.dsp.srth.query.service.QueryService, uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement, java.lang.Object)
     */
    public void queryChanged(QueryService service, QueryStatement query, Object source) {

        if (query != null)
        {
            // reset active query
            activeQuery = query;
            logger.debug("Notified model of query change");
            reportingQueryTreeModel.populateModel(activeQuery);
//            // scroll to active node
//            if (selectedExpression != null && !(selectedExpression instanceof QueryStatement))
//            {
//                TreePath path = new TreePath(reportingQueryTreeModel.getPathToRoot(selectedExpression));
//                queryInterfaceTree.scrollPathToVisible(path);
//            }

            // update UI
            Runnable runnable = new Runnable() {
                public void run() {
                    queryInterfaceTree.revalidate();
                    SwingUtilities.updateComponentTreeUI(queryInterfaceTree);
                }
            };

            SwingUtilities.invokeLater(runnable);
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionServiceListener#selectionChanged(uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService, java.lang.Object, java.lang.Object)
     */
    public void selectionChanged(SelectionService service, Object selection, Object source) {

    }
}