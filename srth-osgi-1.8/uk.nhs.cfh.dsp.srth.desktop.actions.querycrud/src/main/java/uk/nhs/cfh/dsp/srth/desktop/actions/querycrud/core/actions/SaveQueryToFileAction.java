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

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.query.converters.file.QueryExpressionFileOutputter;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URI;

// TODO: Auto-generated Javadoc
/**
 * An action that allows the user to save a {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl}
 * to a {@link java.io.File}. This action checks if the specified
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl} has an assigned physical {@link java.net.URL},
 * otherwise it calls the {@link uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions.SaveQueryToFileTask}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 28, 2009 at 1:42:06 PM
 */
public class SaveQueryToFileAction extends AbstractAction{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The query expression file outputter. */
    private QueryExpressionFileOutputter queryExpressionFileOutputter;

    /**
     * Instantiates a new save query to file action.
     * 
     * @param applicationService the application service
     * @param queryService the query service
     * @param queryExpressionFileOutputter the query expression file outputter
     */
    public SaveQueryToFileAction(ApplicationService applicationService, QueryService queryService,
                                 QueryExpressionFileOutputter queryExpressionFileOutputter) {
        super("Save query", new ImageIcon(LoadQueryFromFileAction.class.getResource("resources/filesave.png")));
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.queryExpressionFileOutputter = queryExpressionFileOutputter;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(SHORT_DESCRIPTION, "Save query to file");
        putValue(LONG_DESCRIPTION, "Saves the query to the location it was loaded from, otherwise " +
                "it opens a dialog to save the query as a file to an user specified location");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        class SaveQueryTask extends Task<Void, Void> {

            public SaveQueryTask(Application arg0) {
                super(arg0);
            }

            @Override
            protected Void doInBackground() throws Exception {

                // get active query and check if a physical location has been assigned
                QueryStatement query = queryService.getActiveQuery();
                URI physicalLocation = query.getPhysicalLocation();
                if(physicalLocation != null)
                {
                    queryExpressionFileOutputter.save(query);
                }
                else
                {
                    // trigger save as task
                    SaveQueryToFileTask task = new SaveQueryToFileTask(applicationService, queryService, queryExpressionFileOutputter);
                    applicationService.getActiveApplication().getContext().getTaskService().execute(task);
                }

                return null;
            }

            @Override
            protected void succeeded(Void arg0) {
                setMessage("Finished saving query");
            }
        }

        SaveQueryTask task = new SaveQueryTask(applicationService.getActiveApplication());
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);

    }
}
