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
package uk.nhs.cfh.dsp.yasb.searchpanel;

import com.jidesoft.swing.JideButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.icon.EmptyIcon;
import org.jdesktop.swingx.painter.BusyPainter;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.objectmodel.ComponentStatus;
import uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider;
import uk.nhs.cfh.dsp.yasb.searchpanel.actions.PopulateListModelTask;
import uk.nhs.cfh.dsp.yasb.searchpanel.model.SearchResultsListModel;
import uk.nhs.cfh.dsp.yasb.searchpanel.renderer.SearchResultListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

// TODO: Auto-generated Javadoc
/**
 * A panel that allows the user to search for concepts. The actual search itself is carried out
 * in a {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchServiceProvider}, which is linked to
 * a {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchService}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Dec 4, 2009 at 11:34:39 AM
 */

public class SearchPanel extends JPanel{

    /** The terminology search service. */
    private TerminologySearchService terminologySearchService;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The search field. */
    private JTextField searchField;
    
    /** The results list. */
    private JList resultsList;
    
    /** The list model. */
    private SearchResultsListModel listModel;
    
    /** The busy status label. */
    private JXBusyLabel busyStatusLabel;
    
    /** The buttons panel. */
    private JPanel buttonsPanel;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SearchPanel.class);
    
    /** The selected concept status. */
    private ComponentStatus selectedConceptStatus = ComponentStatus.CURRENT;
    
    /** The search concept id. */
    private boolean searchConceptId = false;
    
    /** The selected concept type. */
    private ConceptType selectedConceptType = ConceptType.UNKNOWN;
    
    /** The selected analyzer. */
    private Analyzer selectedAnalyzer;
    
    /** The populate list model task. */
    private PopulateListModelTask populateListModelTask;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The render concept ids box. */
    private JCheckBox renderConceptIdsBox;
    
    /** The prefer fsn over ptj check box. */
    private JCheckBox preferFSNOverPTJCheckBox;
    
    /** The controls pane. */
    private JXCollapsiblePane controlsPane;
    
    /** The renderer. */
    private SearchResultListCellRenderer renderer;
    private static final TerminologySearchServiceProvider.SearchAlgorithm DEFAULT_ALGORITHM = TerminologySearchServiceProvider.SearchAlgorithm.WILD_CARDS;

    /**
     * A constructor for the class that sets up all the services. Always call the initComponents() method
     * after instantiation.
     *
     * @param terminologySearchService the terminology search service to use
     * @param selectionService  the selection search to use, (ignored in this version)
     * @param applicationService the application service that handles asychronous event handling
     * @param terminologyConceptDAO the DAO for SNOMED CT content.
     */
    public SearchPanel(TerminologySearchService terminologySearchService, SelectionService selectionService,
                       ApplicationService applicationService, TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologySearchService = terminologySearchService;
        this.selectionService = selectionService;
        this.applicationService = applicationService;
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Empty no args constructor for IOC
     */
    public SearchPanel() {
    }

    /**
     * a method that initalises the components.
     */
    public synchronized void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Search"));
        mainPanel.setName("searchPanelMainPanel");

        searchField = new JTextField();
        if(org.jdesktop.swingx.util.OS.isMacOSX())
        {
            searchField.putClientProperty("JTextField.variant", "search");
        }
        searchField.setName("searchField");
        searchField.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent arg0) {
                doSearch();

            }
        });

        // create controls panel
        createControlPanel();

        // create button panel
        createButtonPanel();

        // create busy label
        createBusyLabel();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(busyStatusLabel, BorderLayout.EAST);
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(panel, BorderLayout.NORTH);
        searchPanel.add(buttonsPanel, BorderLayout.CENTER);
        searchPanel.add(controlsPane, BorderLayout.SOUTH);

        listModel = new SearchResultsListModel();
        resultsList = new JList(listModel);
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        renderer = new SearchResultListCellRenderer();
        resultsList.setCellRenderer(renderer);
        // add mouse listener to resultsList
        resultsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // check if double click and get selected object
                if(e.getClickCount() == 2)
                {
                    Object selectedObject = resultsList.getSelectedValue();
                    if(selectedObject instanceof Document)
                    {
                        Document document = (Document) selectedObject;
                        String conceptId = document.get("CONCEPTID");
                        SnomedConcept concept = checkAndReturnConceptForID(conceptId);
                        // set focus concept if concept is not null
                        if(concept != null)
                        {
                            // publish as selection
                            selectionService.objectSelected(concept, SearchPanel.this);
                        }
                    }
                }
            }
        });

        // add components to main panel
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(resultsList), BorderLayout.CENTER);

        // add main panel to search panel
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the control panel.
     */
    private synchronized void createControlPanel(){
        // set controls for rendering concept ids and labels in a collapsible pane
        controlsPane = new JXCollapsiblePane(new GridLayout(0, 1));
        controlsPane.setName("controlsPane");


        final JComboBox conceptTypeBox = new JComboBox(ConceptType.values());
        // set default value to UNKNOWN, which will return all types
        conceptTypeBox.setSelectedItem(ConceptType.UNKNOWN);

        conceptTypeBox.setAction(new AbstractAction(){

            public void actionPerformed(ActionEvent e) {
                // get selected value
                Object selection = conceptTypeBox.getSelectedItem();
                if(selection instanceof ConceptType)
                {
                    selectedConceptType = (ConceptType) conceptTypeBox.getSelectedItem();
                    doSearch();
                }

            }});
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.LINE_AXIS));
        panel1.add(new JLabel("Concept Type"));
        panel1.add(Box.createHorizontalStrut(10));
        panel1.add(conceptTypeBox);

        final JComboBox statusBox = new JComboBox(ComponentStatus.values());
        statusBox.setSelectedItem(ComponentStatus.CURRENT);
        statusBox.setAction(new AbstractAction(){

            public void actionPerformed(ActionEvent arg0) {

                Object selection = statusBox.getSelectedItem();
                if(selection instanceof ComponentStatus)
                {
                    selectedConceptStatus = (ComponentStatus) statusBox.getSelectedItem();
                    doSearch();
                }
            }
        });
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
        panel2.add(new JLabel("Concept Status"));
        panel2.add(Box.createHorizontalStrut(10));
        panel2.add(statusBox);

        // create stemming enabling checkbox
        final JCheckBox enableStemmingCheckBox = new JCheckBox();
        enableStemmingCheckBox.setAction(new AbstractAction("Enable Stemming"){
            public void actionPerformed(ActionEvent arg0) {
                if(enableStemmingCheckBox.isSelected())
                {
                    // change analyser
                    selectedAnalyzer = new SnowballAnalyzer("English");
                    logger.debug("Enabled Stemming");
                    doSearch();
                }
                else
                {
                    selectedAnalyzer = new StandardAnalyzer();
                    logger.debug("Disabled Stemming");
                    doSearch();
                }
            }

        });

        renderConceptIdsBox = new JCheckBox(new AbstractAction("Render Concept IDs"){

            public void actionPerformed(ActionEvent e) {
                // toggle status in tree cell renderer
                renderer.setRenderConceptId(renderConceptIdsBox.isSelected());
                // refresh tree
                SwingUtilities.updateComponentTreeUI(resultsList);
            }
        });
        renderConceptIdsBox.setName("preferFSNOverPTJCheckBox");

        preferFSNOverPTJCheckBox = new JCheckBox(new AbstractAction("Prefer FSN over PT") {
            public void actionPerformed(ActionEvent e) {
                // toggle preference in renderer
                renderer.setPreferFSNOverPT(preferFSNOverPTJCheckBox.isSelected());
                // refresh tree
                SwingUtilities.updateComponentTreeUI(resultsList);
            }
        });
        preferFSNOverPTJCheckBox.setName("preferFSNOverPTJCheckBox");

        // add panels to controlsPane
        controlsPane.add(panel1);
        controlsPane.add(panel2);
        controlsPane.add(enableStemmingCheckBox);
        controlsPane.add(renderConceptIdsBox);
        controlsPane.add(preferFSNOverPTJCheckBox);
        controlsPane.setCollapsed(true);
    }

    /**
     * Creates the busy label.
     */
    private void createBusyLabel() {

        busyStatusLabel = new JXBusyLabel(new Dimension(18,19));
        BusyPainter painter = new BusyPainter(
                new RoundRectangle2D.Float(0, 0,8.0f,1.9f,10.0f,10.0f),
                new Ellipse2D.Float(2.5f,2.5f,14.0f,14.0f));
        painter.setTrailLength(4);
        painter.setPoints(16);
        painter.setFrame(4);
        busyStatusLabel.setPreferredSize(new Dimension(18,19));
        busyStatusLabel.setIcon(new EmptyIcon(18,19));
        busyStatusLabel.setBusyPainter(painter);
    }

    /**
     * Creates the button panel.
     */
    private void createButtonPanel() {

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.add(new JLabel("Search using "));
        fieldsPanel.add(Box.createHorizontalStrut(5));
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.LINE_AXIS));
        // create button group for term selection
        JRadioButton allRadioButton = new JRadioButton(new AbstractAction("Term"){

            public void actionPerformed(ActionEvent event) {
                // set search on term
                searchConceptId = false;
                doSearch();
            }
        });

        JRadioButton idRadioButton = new JRadioButton(new AbstractAction("ID"){

            public void actionPerformed(ActionEvent event) {
                // set search on id
                searchConceptId = true;
                doSearch();
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(allRadioButton);
        buttonGroup.add(idRadioButton);
        buttonGroup.setSelected(allRadioButton.getModel(), true);

        // add search field terms to fields panel
        fieldsPanel.add(allRadioButton);
        fieldsPanel.add(idRadioButton);
        fieldsPanel.add(Box.createHorizontalGlue());

        // create panel that contains button that toggles display of controlsPane
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
        controlsButton.setIcon(new ImageIcon(SearchPanel.class.getResource("resources/configure.png")));
        controlsButton.setToolTipText("Click to display or hide configuration panel");
        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.LINE_AXIS));
        panel4.add(Box.createHorizontalGlue());
        panel4.add(new JLabel("Change search and display preferences "));
        panel4.add(controlsButton);

        // add panels to button panel
        buttonsPanel.add(fieldsPanel);
        buttonsPanel.add(panel4);
    }

    /**
     * an utility method that returns a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} for a given
     * concept id.
     * @param conceptID the concept id to lookup
     * @return the concept for the given ID.
     */
    private SnomedConcept checkAndReturnConceptForID(String conceptID){

        SnomedConcept concept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, conceptID);
        if(concept != null)
        {
            return concept;
        }
        else
        {
            return null;
        }
    }

    /**
     * the method that makes the call to a {@link uk.nhs.cfh.dsp.yasb.searchpanel.actions.PopulateListModelTask} which
     * then executes the search and handles display of results
     */
    private void doSearch(){

        String query = searchField.getText().trim();
        logger.debug("Value of query : " + query);
        if (query != null)
        {
            if(populateListModelTask != null && !populateListModelTask.isDone())
            {
                populateListModelTask.cancel(true);
            }

            populateListModelTask = new PopulateListModelTask(applicationService.getActiveApplication(),
                    terminologySearchService, listModel,
                    DEFAULT_ALGORITHM,
                    query, selectedConceptStatus, selectedConceptType, searchConceptId);
            applicationService.getActiveApplication().getContext().getTaskService().execute(populateListModelTask);
        }

    }

    /**
     * Gets the results list.
     *
     * @return the results list
     */
    public JList getResultsList() {
        return resultsList;
    }

    public synchronized void setTerminologySearchService(TerminologySearchService terminologySearchService) {
        this.terminologySearchService = terminologySearchService;
    }

    public synchronized void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    public synchronized void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }
}
