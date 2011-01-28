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
package uk.nhs.cfh.dsp.srth.query.repository.viewcomponent.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.Action} that handles the logic for saving a
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement} to a repository
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 22-Mar-2010 at 14:19:28
 */
public class SaveQueryToRepositoryAction extends AbstractAction{

    /** The application service. */
    private ApplicationService applicationService;

    /** The query service. */
    private QueryService queryService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SaveQueryToRepositoryAction.class);
    
    /** The query repository service. */
    private QueryRepositoryService queryRepositoryService;

    /**
     * Instantiates a new save query to repository action.
     *
     * @param applicationService the application service
     * @param queryService the query service
     * @param queryRepositoryService the query repository service
     */
    public SaveQueryToRepositoryAction(ApplicationService applicationService, QueryService queryService,
                                       QueryRepositoryService queryRepositoryService) {
        super("Save query to repository", new ImageIcon(SaveQueryToRepositoryAction.class.getResource("resources/savetorepo.png")));
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.queryRepositoryService = queryRepositoryService;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
        		(java.awt.event.InputEvent.ALT_MASK |
        				(Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()))));
        putValue(SHORT_DESCRIPTION, "Save query to repository");
        putValue(LONG_DESCRIPTION, "Saves the query to the query repository");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        class SaveQueryToRepoTask extends Task<Void, Void> {

            public SaveQueryToRepoTask(Application arg0) {
                super(arg0);
            }

            @Override
            protected Void doInBackground() throws Exception {

                // get active query and check if a physical location has been assigned
                QueryStatement query = queryService.getActiveQuery();
                String title = query.getMetaData().getTitle();
                if(title != null)
                {
                    queryRepositoryService.saveQuery(query);
                }
                else
                {
                    title = JOptionPane.showInputDialog(applicationService.getFrameView().getActiveFrame(), "Specify label for query",
                            "Specify title", JOptionPane.INFORMATION_MESSAGE);

                    if(title != null)
                    {
                        query.getMetaData().setTitle(title);
                        queryRepositoryService.saveQuery(query);
                    }
                }

                return null;
            }

            @Override
            protected void succeeded(Void arg0) {
                setMessage("Finished saving query");
            }

            @Override
            protected void failed(Throwable e) {
                String simpleMessage ="Error saving query to repository";
                setMessage(simpleMessage);
                applicationService.notifyError(simpleMessage, e, Level.WARNING);
                logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
            }
        }

        SaveQueryToRepoTask task = new SaveQueryToRepoTask(applicationService.getActiveApplication());
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);

    }
}
