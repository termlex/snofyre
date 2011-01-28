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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.table.TableColumnExt;
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.actions.PopulateResultSetTableModelFromResultSetTask;
import uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.model.table.ResultSetTableModel;
import uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.renderer.QueryResultsTableCellRenderer;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.transform.QueryEngineServiceListener;
import uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService;
import uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * A custom view for browsing the results of a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}.
 * The results of a query are returned as a {@link java.sql.ResultSet}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Apr 3, 2009 at 12:29:28 PM
 * <br> Modified on Monday; November 30, 2009 at 11:22 AM to include modular services
 */
@SuppressWarnings({"serial", "unused"})
public class QueryResultsPanel extends JPanel implements QueryEngineServiceListener{

    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryResultsPanel.class);
    /** The result set table model. */
    private ResultSetTableModel resultSetTableModel;

    /** The result count label. */
    private JXLabel resultCountLabel;

    /** The query time label. */
    private JXLabel queryTimeLabel;

    /** The display distinct results check box. */
    private JCheckBox displayDistinctResultsCheckBox;

    /** The results table. */
    private JXTable resultsTable;

    /** The patient dialog. */
    private JDialog patientDialog;

    /** The patient panel. */
    private PatientPanel patientPanel;

    /** The search field. */
    private JTextField searchField;

    /** The distinct results only. */
    private boolean distinctResultsOnly;

    /** The unique patient count. */
    private int uniquePatientCount = 0;

    /** The result set count. */
    private int resultSetCount = 0;
    
    /** The border size. */
    private static int borderSize = 5;
    /** The query service. */
    private QueryService queryService;

    /** The Constant QUERY_TIME_PREFIX. */
    private static final String QUERY_TIME_PREFIX = "<html><b>Time taken to return results for query : <font color =\"eee\"> ";
    
    /** The Constant QUERY_TIME_SUFFIX. */
    private static final String QUERY_TIME_SUFFIX = " seconds</font></b></html>";
    
    /** The Constant QUERY_COUNT_PREFIX. */
    private static final String QUERY_COUNT_PREFIX = "<html><b>Number of record entries satisfying criteria in the query : <font color =\"eee\"> ";
    
    /** The Constant UNIQUE_QUERY_COUNT_PREFIX. */
    private static final String UNIQUE_QUERY_COUNT_PREFIX = "<html><b>Number of unique patients satisfying criteria in the query : <font color =\"eee\"> ";
    
    /** The Constant QUERY_COUNT_SUFFIX. */
    private static final String QUERY_COUNT_SUFFIX = " </font></b></html>";

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The active frame. */
    private JFrame activeFrame;
    
    /** The patient dao. */
    private PatientDAO patientDAO;

    /**
     * Instantiates a new query results panel.
     *
     * @param queryService the query service
     * @param sqlQueryEngineService the sql query engine service
     * @param applicationService the application service
     * @param patientDAO the patient dao
     */
    public QueryResultsPanel(QueryService queryService, SQLQueryEngineService sqlQueryEngineService,
                             ApplicationService applicationService,
                             PatientDAO patientDAO) {
        this.queryService = queryService;
        this.applicationService = applicationService;
        this.patientDAO = patientDAO;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents() {

        while(activeFrame != null)
        {
            if(applicationService != null && applicationService.getFrameView() != null)
            {
                activeFrame = applicationService.getFrameView().getActiveFrame();
            }
        }

        resultCountLabel = new JXLabel(QUERY_COUNT_PREFIX+resultSetCount+QUERY_COUNT_SUFFIX);
        resultCountLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        queryTimeLabel = new JXLabel(QUERY_TIME_PREFIX+0+QUERY_TIME_SUFFIX);
        queryTimeLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        displayDistinctResultsCheckBox = new JCheckBox(new AbstractAction("" +
                "Display only unique patient count") {

            public void actionPerformed(ActionEvent e) {
                renderQueryCountLabels();
            }
        });
        displayDistinctResultsCheckBox.setHorizontalAlignment(SwingConstants.LEFT);

        // create panels and add labels
        JPanel countPanel = new JPanel();
        countPanel.setLayout(new BoxLayout(countPanel, BoxLayout.LINE_AXIS));
        countPanel.add(resultCountLabel);
        countPanel.add(Box.createHorizontalGlue());
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.LINE_AXIS));
        timePanel.add(Box.createHorizontalGlue());
        timePanel.add(queryTimeLabel);
        timePanel.add(Box.createHorizontalGlue());
        timePanel.add(displayDistinctResultsCheckBox);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.PAGE_AXIS));
        labelsPanel.add(countPanel);
        labelsPanel.add(timePanel);

        resultSetTableModel = new ResultSetTableModel();
        resultsTable = new JXTable(resultSetTableModel);

        // set cell renderer for columns in table
        setCellRenderer();
        resultsTable.setColumnControlVisible(true);
        resultsTable.setHighlighters(HighlighterFactory.createAlternateStriping());
        resultsTable.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                // get row at click point
                int row = resultsTable.rowAtPoint(e.getPoint());
                if(row > -1)
                {
                    // check if double click
                    if(e.getClickCount() == 2)
                    {
                        // get id at row
                        String patientId = resultSetTableModel.getValueAt(row, 0).toString();
                        logger.debug("Value of patientId : " + patientId);
                        // set as active constraint
                        patientPanel.setPatient(patientId);
                        patientDialog.setTitle("Patient - "+patientId);
                        patientDialog.setVisible(true);
                    }
                }
            }
        });

        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.setBorder(BorderFactory.createTitledBorder("Query Results"));
        lowerPanel.add(labelsPanel, BorderLayout.NORTH);
        lowerPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        // set components for view
        setLayout(new BorderLayout());
        add(lowerPanel, BorderLayout.CENTER);

        // create patient panel
        createPatientDialog();
    }

    /**
     * Render query count labels.
     */
    private void renderQueryCountLabels() {
        if(displayDistinctResultsCheckBox.isSelected()){
            resultCountLabel.setText(UNIQUE_QUERY_COUNT_PREFIX+uniquePatientCount+QUERY_COUNT_SUFFIX);
        }else{
            resultCountLabel.setText(QUERY_COUNT_PREFIX+resultSetCount+QUERY_COUNT_SUFFIX);
        }
    }

    /**
     * Sets the cell renderer.
     */
    private void setCellRenderer() {
        for(int i=0; i<resultsTable.getColumnCount(true); i++)
        {
            resultsTable.getColumnExt(i).setCellRenderer(new QueryResultsTableCellRenderer());
        }
    }


    /**
     * Hide concept Id and Entry time columns.
     * 
     * We know that column's follow the generic pattern : Concept_ID, Entry_Time, Free_Text_Entry
     * So we use this pattern to hide columns, 1 and 2 of the series. Note this patterns will change if
     * the table model is changed to use clinical entries.
     */
    public void renderColumns() {

        java.util.List<TableColumnExt> cols = new ArrayList<TableColumnExt>();
		for(int i=0; i<resultsTable.getColumnCount(true); i++)
		{
			cols.add(resultsTable.getColumnExt(i));
		}

		for(int i=0; i<cols.size() ; i++)
		{
            TableColumnExt col = cols.get(i);
            col.setCellRenderer(new QueryResultsTableCellRenderer());
			if(i % 3 == 0)
			{
				// set column visible
				col.setVisible(true);
			}
			else
			{
				col.setVisible(false);
			}
		}
    }

    /**
     * Creates the patient dialog.
     */
    private void createPatientDialog(){
        // create patientPanel  and initialise components
        patientPanel = new PatientPanel();
        patientPanel.setPatientDAO(patientDAO);
        patientPanel.initComponents();

        patientDialog = new JDialog(activeFrame, "Patient details", Dialog.ModalityType.DOCUMENT_MODAL);
        patientDialog.setPreferredSize(new Dimension(500, 520));
        patientDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        patientDialog.add(patientPanel);
        patientDialog.pack();
        patientDialog.setLocationRelativeTo(activeFrame);
    }

    /**
     * Result set changed.
     *
     * @param resultSet the result set
     */
    public synchronized void resultSetChanged(ResultSet resultSet) {

        // use result set to populate result set view
        PopulateResultSetTableModelFromResultSetTask populateTask = new PopulateResultSetTableModelFromResultSetTask(applicationService,
                resultSet, resultSetTableModel, this);
        applicationService.getActiveApplication().getContext().getTaskService().execute(populateTask);
    }

    /**
     * Query time changed.
     *
     * @param queryTime the query time
     */
    public synchronized void queryTimeChanged(long queryTime) {
        queryTime/=1000;
        queryTime= Math.round((double)queryTime );
        queryTimeLabel.setText(QUERY_TIME_PREFIX+queryTime+QUERY_TIME_SUFFIX);
    }

    /**
     * Query statistics collection changed.
     *
     * @param statisticsCollectionMap the statistics collection map
     */
    public synchronized void queryStatisticsCollectionChanged(Map<QueryExpression, QueryStatisticsCollection> statisticsCollectionMap) {
        // get activey query from queryService and use it to retrieve collection from map
        if(statisticsCollectionMap != null && statisticsCollectionMap.containsKey(queryService.getActiveQuery()))
        {
            // get value collection
            QueryStatisticsCollection collection = statisticsCollectionMap.get(queryService.getActiveQuery());
            resultSetCount = collection.getResultSetSize();
            uniquePatientCount = collection.getDistinctPatientsCount();
            if (logger.isDebugEnabled()) {
                logger.debug("resultSetCount = " + resultSetCount);
                logger.debug("uniquePatientCount = " + uniquePatientCount);
            }
            // set count in label
            renderQueryCountLabels();
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.transform.QueryEngineServiceListener#queryResultsAvailable(java.util.Collection)
     */
    public synchronized void queryResultsAvailable(Collection<ClinicalEntry> entries) {
        /*
            use this commented out method if using hibernate based query engine that returns a collection of
            ClinicalEntry objects.
         */
    }
}