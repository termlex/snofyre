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
package uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel;

import com.jidesoft.swing.JideButton;
import org.jdesktop.swingx.JXCollapsiblePane;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionServiceListener;
import uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.actions.PopulateHierarchyTreeModelTask;
import uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.model.SnomedConceptHierarchyTreeModel;
import uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.renderer.SnomedConceptHierarchyTreeCellRenderer;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that renders the hierarchy of a
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2010 at 1:52:23 PM
 */
public class SnomedConceptHierarchyPanel extends JPanel implements SelectionServiceListener{

    /** The concept. */
    private SnomedConcept concept;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The snomed concept hierarchy tree. */
    private JTree snomedConceptHierarchyTree;
    
    /** The tree model. */
    private SnomedConceptHierarchyTreeModel treeModel;
    
    /** The render concept ids box. */
    private JCheckBox renderConceptIdsBox;
    
    /** The prefer fsn over ptj check box. */
    private JCheckBox preferFSNOverPTJCheckBox;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The renderer. */
    private SnomedConceptHierarchyTreeCellRenderer renderer;
    
    /** The controls pane. */
    private JXCollapsiblePane controlsPane;
    
    /** The border offset. */
    private static final int borderOffset = 5;
    
    /** The sort hierarchy alphabetically box. */
    private JCheckBox sortHierarchyAlphabeticallyBox;

    /**
     * Instantiates a new snomed concept hierarchy panel.
     *
     * @param terminologyConceptDAO the terminology concept dao
     * @param selectionService the selection service
     * @param applicationService the application service
     */
    public SnomedConceptHierarchyPanel(TerminologyConceptDAO terminologyConceptDAO,
                                       SelectionService selectionService,
                                       ApplicationService applicationService) {
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.selectionService = selectionService;
        this.applicationService = applicationService;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        //set name for this panel for UI persistence
        setName(getClass().getCanonicalName());

        // initialise tree model and tree
        treeModel = new SnomedConceptHierarchyTreeModel(terminologyConceptDAO);
        snomedConceptHierarchyTree = new JTree(treeModel);
        snomedConceptHierarchyTree.setName("snomedConceptHierarchyTree");
        // set renderer
        renderer = new SnomedConceptHierarchyTreeCellRenderer();
        snomedConceptHierarchyTree.setCellRenderer(renderer);
        // add selection listener to tree
        snomedConceptHierarchyTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // get object a location
                int row = snomedConceptHierarchyTree.getRowForLocation(e.getX(), e.getY());
                if(row > -1)
                {
                    TreePath selectionPath = snomedConceptHierarchyTree.getPathForRow(row);
                    Object node = selectionPath.getLastPathComponent();
                    if(node instanceof SnomedConcept && e.getClickCount() == 2)
                    {
                        // set as selectedConcept
                        SnomedConcept selectedConcept = (SnomedConcept) node;
                        // publish as selection using selectionService
                        selectionService.objectSelected(selectedConcept, SnomedConceptHierarchyPanel.this);
                    }
                }
            }
        });

        // create a scrollpane and add snomedConceptHierarchyTree to it
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setName("ConceptHierarchyScrollPane");
        scrollPane.setViewportView(snomedConceptHierarchyTree);

        // set controls for rendering concept ids and labels in a collapsible pane
        controlsPane = new JXCollapsiblePane(new GridLayout(0, 1));
        controlsPane.setName("controlsPane");
        renderConceptIdsBox = new JCheckBox(new AbstractAction("Render Concept IDs"){

            public void actionPerformed(ActionEvent e) {
                // toggle status in tree cell renderer
                renderer.setRenderConceptId(renderConceptIdsBox.isSelected());
                // refresh tree
                SwingUtilities.updateComponentTreeUI(snomedConceptHierarchyTree);
            }
        });
        renderConceptIdsBox.setName("preferFSNOverPTJCheckBox");

        preferFSNOverPTJCheckBox = new JCheckBox(new AbstractAction("Prefer FSN over PT") {
            public void actionPerformed(ActionEvent e) {
                // toggle preference in renderer
                renderer.setPreferFSNOverPT(preferFSNOverPTJCheckBox.isSelected());
                // refresh tree
                SwingUtilities.updateComponentTreeUI(snomedConceptHierarchyTree);
            }
        });
        preferFSNOverPTJCheckBox.setName("preferFSNOverPTJCheckBox");

        sortHierarchyAlphabeticallyBox = new JCheckBox(new AbstractAction("Sort Hierarchy in alphabetic order") {
            public void actionPerformed(ActionEvent e) {
                // toggle preference in treeModel 
                treeModel.setSortHierarchyByAlphabeticalOrder(sortHierarchyAlphabeticallyBox.isSelected());
                populateHierarchy(getConcept());
            }
        });
        sortHierarchyAlphabeticallyBox.setName("sortHierarchyAlphabeticallyBox");

        // add checkboxes to controlsPane
        controlsPane.add(renderConceptIdsBox);
        controlsPane.add(preferFSNOverPTJCheckBox);
        controlsPane.add(sortHierarchyAlphabeticallyBox);
        controlsPane.setCollapsed(true);

        //TODO create and add search field which should allow the user to quick search through the hierarchy
        JTextField searchField = new JTextField();
        if(org.jdesktop.swingx.util.OS.isMacOSX())
        {
            searchField.putClientProperty("JTextField.variant", "search");
        }
        searchField.setName("conceptHierarchySearchField");
        // add action to search field
        searchField.addActionListener(new AbstractAction(""){

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Not implemented yet!");
            }
        });

        // create a button that shows and hides collapsible pane with check boxes
        JideButton controlsButton = new JideButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(controlsPane.isCollapsed())
                {
                    controlsPane.setCollapsed(false);
                }
                else
                {
                    controlsPane.setCollapsed(true);
                }
            }
        });
        controlsButton.setIcon(new ImageIcon(SnomedConceptHierarchyPanel.class.getResource("resources/configure.png")));
        controlsButton.setToolTipText("Click to display or hide configuration panel");

        // add search field and a control buttons in NORTH position
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        p1.add(new JLabel("Search Hierarchy"));
        p1.add(searchField);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
        p2.add(Box.createHorizontalGlue());
        p2.add(new JLabel("Change display preferences "));
        p2.add(controlsButton);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
        northPanel.add(p1);
        northPanel.add(p2);

        // set container layout to border layout and add components
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(borderOffset, borderOffset, borderOffset, borderOffset));
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(controlsPane, BorderLayout.SOUTH);
    }

    /**
     * Gets the concept.
     *
     * @return the concept
     */
    public SnomedConcept getConcept() {
        return concept;
    }

    /**
     * Sets the concept.
     *
     * @param concept the new concept
     */
    public void setConcept(SnomedConcept concept) {
        if(concept != null && concept != getConcept())
        {
            this.concept = concept;
            // populate tree model
            populateHierarchy(getConcept());
        }
    }

    /**
     * Sets the concept.
     *
     * @param conceptId the new concept
     */
    public void setConcept(String conceptId) {
        if(getConcept() != null && conceptId != null &&
                getConcept().getConceptID() != null && ! getConcept().getConceptID().equals(conceptId))
        {
            // convert to concept and populate model
            SnomedConcept localConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, conceptId);
            if(localConcept != null)
            {
               setConcept(localConcept);               
            }
        }
    }

    /**
     * Gets the snomed concept hierarchy tree.
     *
     * @return the snomed concept hierarchy tree
     */
    public JTree getSnomedConceptHierarchyTree() {
        return snomedConceptHierarchyTree;
    }

    /**
     * Sets the snomed concept hierarchy tree.
     *
     * @param snomedConceptHierarchyTree the new snomed concept hierarchy tree
     */
    public void setSnomedConceptHierarchyTree(JTree snomedConceptHierarchyTree) {
        this.snomedConceptHierarchyTree = snomedConceptHierarchyTree;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionServiceListener#selectionChanged(uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService, java.lang.Object, java.lang.Object)
     */
    public void selectionChanged(SelectionService selectionService, Object selection, Object source) {
        // ensure selection is not from self
        if(source != this && !(source instanceof SnomedConceptHierarchyPanel) && selection instanceof SnomedConcept)
        {
            setConcept((SnomedConcept) selection);
        }
    }

    /**
     * Populate hierarchy.
     *
     * @param concept the concept
     */
    private void populateHierarchy(SnomedConcept concept) {
        // create a new task instance and execute it
        PopulateHierarchyTreeModelTask task =
                new PopulateHierarchyTreeModelTask(applicationService.getActiveApplication(), concept, treeModel);
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }
}
