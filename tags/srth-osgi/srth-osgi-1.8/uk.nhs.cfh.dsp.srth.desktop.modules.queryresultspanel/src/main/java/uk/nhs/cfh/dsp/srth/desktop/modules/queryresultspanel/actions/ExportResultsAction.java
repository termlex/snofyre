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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.actions;

import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.transform.QueryEngineServiceListener;
import uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;

// TODO: Auto-generated Javadoc

/**
 * A custom {@link javax.swing.Action} to export results
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Dec 19, 2010 at 11:49:34 PM
 */
public class ExportResultsAction extends AbstractAction implements QueryEngineServiceListener{

    /** The application service. */
    private ApplicationService applicationService;

    /** The query service. */
    private QueryService queryService;
    private ResultSet resultSet;
    private int resultSetCount;

    /**
     * Instantiates a new execute active query action.
     *
     * @param applicationService the application service
     * @param queryService the query service
     *
     */
    public ExportResultsAction(ApplicationService applicationService,
                               QueryService queryService) {
        super("Export Query Results",
                new ImageIcon(ExportResultsAction.class.getResource("resources/table_go.png")));
        this.applicationService = applicationService;
        this.queryService = queryService;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(SHORT_DESCRIPTION, "Exports the results of query");
        putValue(LONG_DESCRIPTION, "Exports the results of query executed." +
                "the Results Panel");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        ExportResultTask task = new ExportResultTask(applicationService, resultSet, resultSetCount);
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }


    /**
     * Result set changed.
     *
     * @param resultSet the result set
     */
    public void resultSetChanged(ResultSet resultSet) {
        if (resultSet != null) {
            this.resultSet = resultSet;
            setEnabled(true);
        }
        else
        {
            setEnabled(false);
        }
    }

    /**
     * Query time changed.
     *
     * @param queryTime the query time
     */
    public void queryTimeChanged(long queryTime) {
        // do nothing
    }

    /**
     * Query statistics collection changed.
     *
     * @param statisticsCollectionMap the statistics collection map
     */
    public void queryStatisticsCollectionChanged(Map<QueryExpression, QueryStatisticsCollection> statisticsCollectionMap) {
        // get activey query from queryService and use it to retrieve collection from map
        if(statisticsCollectionMap != null && statisticsCollectionMap.containsKey(queryService.getActiveQuery()))
        {
            // get value collection
            QueryStatisticsCollection collection = statisticsCollectionMap.get(queryService.getActiveQuery());
            resultSetCount = collection.getResultSetSize();
        }
    }

    public void queryResultsAvailable(Collection<ClinicalEntry> entries) {
        // do nothing
    }
}
