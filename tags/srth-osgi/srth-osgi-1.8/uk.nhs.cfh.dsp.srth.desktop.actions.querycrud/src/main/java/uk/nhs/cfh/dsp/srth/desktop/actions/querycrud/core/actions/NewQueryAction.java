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
package uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * An action that creates a new {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 28, 2009 at 10:49:31 AM
 */
public class NewQueryAction extends AbstractAction{

    /** The query expression factory. */
    private QueryExpressionFactory queryExpressionFactory;
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(NewQueryAction.class);
    /** The query service. */
    private QueryService queryService;

    /**
     * Instantiates a new new query action.
     *
     * @param applicationService the application service
     * @param queryService the query service
     * @param queryExpressionFactory the query expression factory
     */
    public NewQueryAction(ApplicationService applicationService, QueryService queryService,
                          QueryExpressionFactory queryExpressionFactory) {
        super("New Query", new ImageIcon(NewQueryAction.class.getResource("resources/filenew.png")));
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.queryExpressionFactory = queryExpressionFactory;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(SHORT_DESCRIPTION, "Create new query");
        putValue(LONG_DESCRIPTION, "Creates a new query and sets it to active query");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        class QueryCreationTask extends Task<Void, Void> {

            public QueryCreationTask(Application arg0) {
                super(arg0);
            }

            @Override
            protected Void doInBackground() throws Exception {
                // create new query object
                QueryStatement newQuery = queryExpressionFactory.getQueryStatement();

                // register query with query service
                queryService.registerQuery(newQuery);
                // notify query service that query has changed
                queryService.queryChanged(queryService.getActiveQuery(),this);

                return null;
            }

            @Override
            protected void succeeded(Void arg0) {
                setMessage("New query successfully created");
            }

            @Override
            protected void failed(Throwable e) {
                String simpleMessage = "Error creating new query.";
                setMessage(simpleMessage);
                applicationService.notifyError(simpleMessage, e, Level.WARNING);
                logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
            }
        }

        QueryCreationTask task = new QueryCreationTask(applicationService.getActiveApplication());
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }
}
