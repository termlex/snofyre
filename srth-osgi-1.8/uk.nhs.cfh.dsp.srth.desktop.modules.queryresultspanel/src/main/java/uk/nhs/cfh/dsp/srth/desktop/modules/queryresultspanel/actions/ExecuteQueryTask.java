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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService;

import java.util.logging.Level;


// TODO: Auto-generated Javadoc
/**
 * A custom class for executing {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement} against a test database.
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 20, 2009 at 9:27:48 PM
 * <br> Modified on Monday; November 30, 2009 at 1:38 PM to include modular services
 */
public class ExecuteQueryTask extends Task<Void, Void> {

    /** The query service. */
    private QueryService queryService;

    /** The sql query engine service. */
    private SQLQueryEngineService sqlQueryEngineService;

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ExecuteQueryTask.class);

    /**
     * Instantiates a new execute query task.
     *
     * @param applicationService the application service
     * @param queryService the query service
     * @param sqlQueryEngineService the sql query engine service
     */
    public ExecuteQueryTask(ApplicationService applicationService,
                            QueryService queryService, SQLQueryEngineService sqlQueryEngineService) {
        super(applicationService.getActiveApplication());
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.sqlQueryEngineService = sqlQueryEngineService;
        setDescription("A task for executing a query against a test database");
        setTitle("Query Execution Task");
    }

    /**
     * Do in background.
     *
     * @return the void
     *
     * @throws Exception the exception
     */
    @Override
    protected Void doInBackground() throws Exception {

        if (! isCancelled())
        {
            // set status message label
            setMessage("Executing query... please wait...");
            // get active query and execute it
            QueryStatement query = queryService.getActiveQuery();
            sqlQueryEngineService.getResultSetForReportinQuery(query);
        }

        return null;
    }

    /**
     * Succeeded.
     *
     * @param v the v
     */
    @Override
    protected void succeeded(Void v) {
        setMessage("Finished executing query. Rendering results... ");
    }

    /**
     * Cancelled.
     */
    @Override
    protected void cancelled() {
        setMessage("User cancelled query execution");
    }


    /* (non-Javadoc)
     * @see org.jdesktop.application.Task#failed(java.lang.Throwable)
     */
    @Override
    protected void failed(Throwable e) {
        String simpleMessage = "Error running query.";
        setMessage(simpleMessage);
        applicationService.notifyError(simpleMessage, e, Level.WARNING);
        logger.warn(simpleMessage+" Nested exception is : " + e.fillInStackTrace().getMessage());
    }
}