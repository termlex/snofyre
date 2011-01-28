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
package uk.nhs.cfh.dsp.srth.desktop.modules.resultexplanationpanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.swingx.JXTreeTable;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modules.resultexplanationpanel.model.ReportingEngineExplanationTreeTableModel;
import uk.nhs.cfh.dsp.srth.desktop.modules.resultexplanationpanel.renderer.SQLResultsExplanationTreeCellRenderer;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;
import uk.nhs.cfh.dsp.srth.query.converters.html.QueryExpressionHTMLRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.transform.QueryEngineServiceListener;
import uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService;
import uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * A class that generates explanations for results returned by the {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService
 * for a given query.
 * To obtain explanations, debug mode needs to be enabled on the {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService}.
 * See {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService.EngineStatus}
 * 
 * <br> Version : @#VersionNumber#@<br>Written by @author Jay Kola
 * <br> Created on Apr 3, 2009 at 5:12:37 PM
 * <br> Modified on Tuesday; December 1, 2009 at 10:16 AM to include modular services
 */
@SuppressWarnings({"serial", "unused"})
public class ResultsExplanationPanel extends JPanel implements QueryEngineServiceListener{

    /** The logger. */
    private static Log logger = LogFactory.getLog(ResultsExplanationPanel.class);
    
    /** The stats map. */
    private Map<QueryExpression, QueryStatisticsCollection> statsMap = new HashMap<QueryExpression, QueryStatisticsCollection>();
    
    /** The table model. */
    private ReportingEngineExplanationTreeTableModel tableModel;
    
    /** The tree table. */
    private JXTreeTable treeTable;
    
    /** The collection panel. */
    private QueryStatisticsCollectionPanel collectionPanel;
    
    /** The collection dialog. */
    private JDialog collectionDialog;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The sql query engine service. */
    private SQLQueryEngineService sqlQueryEngineService;
    
    /** The query expression html renderer. */
    private QueryExpressionHTMLRenderer queryExpressionHTMLRenderer;
    
    /** The active frame. */
    private JFrame activeFrame;

    /**
     * Instantiates a new results explanation panel.
     * 
     * @param applicationService the application service
     * @param queryService the query service
     * @param sqlQueryEngineService the sql query engine service
     * @param queryExpressionHTMLRenderer the query expression html renderer
     */
    public ResultsExplanationPanel(ApplicationService applicationService, QueryService queryService,
                                   SQLQueryEngineService sqlQueryEngineService,
                                   QueryExpressionHTMLRenderer queryExpressionHTMLRenderer) {
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.sqlQueryEngineService = sqlQueryEngineService;
        this.queryExpressionHTMLRenderer = queryExpressionHTMLRenderer;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        while(activeFrame == null)
        {
            if(applicationService != null && applicationService.getFrameView() != null)
            {
                activeFrame = applicationService.getFrameView().getActiveFrame();
            }
        }
        // create collections dialog
        createCollectionsDialog();
        tableModel = new ReportingEngineExplanationTreeTableModel(queryService.getActiveQuery(), statsMap);
        treeTable = new JXTreeTable(tableModel);
        treeTable.setTreeCellRenderer(new SQLResultsExplanationTreeCellRenderer(queryExpressionHTMLRenderer));
        // add mouse listeners
        treeTable.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)
                {
                    // get value at selection
                    int row = treeTable.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        TreePath path = treeTable.getPathForLocation(e.getX(), e.getY());
                        // get node
                        Object node = path.getLastPathComponent();
                        if (node instanceof QueryExpression) {
                            QueryExpression expression = (QueryExpression) node;
                            // get collection for selected expression
                            QueryStatisticsCollection collection = statsMap.get(expression);
                            // set expression in collection panel
                            collectionPanel.setCollection(collection);
                            collectionPanel.revalidate();
                            // show collections dialog
                            collectionDialog.setVisible(true);
                        }
                    }
                }
            }
        });

        // add components to this object
        setLayout(new BorderLayout());
//		add(label, BorderLayout.NORTH);
        add(new JScrollPane(treeTable), BorderLayout.CENTER);

    }

    /**
     * Creates the collections dialog.
     */
    private synchronized void createCollectionsDialog() {

        // create collectionPanel  and initialise components
        collectionPanel = new QueryStatisticsCollectionPanel(applicationService, queryExpressionHTMLRenderer);
        collectionPanel.initComponents();

        collectionDialog = new JDialog(activeFrame, "Explanations", Dialog.ModalityType.DOCUMENT_MODAL);
        collectionDialog.setPreferredSize(new Dimension(400, 550));
        collectionDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        collectionDialog.getContentPane().add(collectionPanel);
        collectionDialog.pack();
        collectionDialog.setLocationRelativeTo(activeFrame);
    }

    /**
     * Gets the stats map.
     * 
     * @return the stats map
     */
    public Map<QueryExpression, QueryStatisticsCollection> getStatsMap() {
        return statsMap;
    }

    /**
     * Inits the.
     */
    public synchronized void init(){
        // register self with sqlQueryEngineService
        sqlQueryEngineService.addListener(this);
    }

    /**
     * Dispose.
     */
    public synchronized void dispose(){
        // unregister self from sqlQueryEngineService
        sqlQueryEngineService.removeListener(this);
    }

    /**
     * Result set changed.
     * 
     * @param resultSet the result set
     */
    public void resultSetChanged(ResultSet resultSet) {
        // do nothing
    }

    /**
     * Query time changed.
     * 
     * @param queryTime the query time
     */
    public void queryTimeChanged(long queryTime) {
        // do nothing
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.query.transform.QueryEngineServiceListener#queryResultsAvailable(java.util.Collection)
     */
    public void queryResultsAvailable(Collection<ClinicalEntry> entries) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Query statistics collection changed.
     * 
     * @param statisticsCollectionMap the statistics collection map
     */
    public void queryStatisticsCollectionChanged(Map<QueryExpression, QueryStatisticsCollection>
            statisticsCollectionMap) {

        // get map and populate tree model in a separate task
        class PopulateExplanationTask extends Task<Void, Void>{

            PopulateExplanationTask(Application application) {
                super(application);
            }

            @Override
            protected Void doInBackground() throws Exception {

                // clear existing stats
                statsMap.clear();

                // get tables map and generate stats
                Map<QueryExpression, QueryStatisticsCollection> tablesMap =
                        sqlQueryEngineService.getQueryStatisticsCollectionMap();
                for(QueryExpression expression : tablesMap.keySet())
                {
                    // update result set count
                    QueryStatisticsCollection coll = tablesMap.get(expression);
//                    coll.setResultSetSize(coll.getResultSetSize(sqlQueryEngineService.getConnection()));
                    // save object
                    statsMap.put(expression, coll);
                }

                // refresh tree table model
                tableModel.updateStats(queryService.getActiveQuery(), statsMap);
                // resize all columns
                treeTable.packAll();

                return null;
            }

            @Override
            protected void succeeded(Void aVoid) {
                setMessage("Updated explanations in Results Explanation Panel");
            }

            @Override
            protected void failed(Throwable throwable) {
                setMessage("Failed to update explanations in Results Explanation Panel.");
                logger.warn("The nested exception is "+throwable.fillInStackTrace());
            }
        }

        // run update task only if the sqlQueryEngineService status is set to debug. it wont collect statistics otherwise.
        if (SQLQueryEngineService.EngineStatus.DEBUG == sqlQueryEngineService.getStatus())
        {
            // create and execute task
            PopulateExplanationTask task = new PopulateExplanationTask(applicationService.getActiveApplication());
            applicationService.getActiveApplication().getContext().getTaskService().execute(task);
        }
    }
}