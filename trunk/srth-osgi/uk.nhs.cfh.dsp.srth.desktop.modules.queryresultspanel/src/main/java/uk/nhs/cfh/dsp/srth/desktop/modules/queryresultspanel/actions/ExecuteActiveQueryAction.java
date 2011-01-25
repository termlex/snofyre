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
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 30, 2009 at 1:54:33 PM.
 */
public class ExecuteActiveQueryAction extends AbstractAction{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The sql query engine service. */
    private SQLQueryEngineService sqlQueryEngineService;

    /**
     * Instantiates a new execute active query action.
     * 
     * @param applicationService the application service
     * @param queryService the query service
     * @param sqlQueryEngineService the sql query engine service
     */
    public ExecuteActiveQueryAction(ApplicationService applicationService, QueryService queryService,
                                    SQLQueryEngineService sqlQueryEngineService) {
        super("Execute Query", new ImageIcon(ExecuteActiveQueryAction.class.getResource("resources/runquery.png")));
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.sqlQueryEngineService = sqlQueryEngineService;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(SHORT_DESCRIPTION, "Execute the active query");
        putValue(LONG_DESCRIPTION, "Executes the currently active query in the application and displays results in " +
                "the Results Panel");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        ExecuteQueryTask task = new ExecuteQueryTask(applicationService, queryService, sqlQueryEngineService);
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }
}
