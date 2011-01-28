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
package uk.nhs.cfh.dsp.srth.desktop.modules.simulator.viewcomponent.actions;

import org.jdesktop.swingx.JXLabel;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.simulator.DataGenerationEngineFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;

// TODO: Auto-generated Javadoc

/**
 * A custom {@link javax.swing.Action} that deletes all patient records from the database.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 09-Mar-2010 at 16:31:58
 */
public class RecreateDatabaseAction extends AbstractAction{

            /** The application service. */
    private ApplicationService applicationService;

    /** The patient dao. */
    private DataGenerationEngineFactory dataGenerationEngineFactory;

    /**
     * Instantiates a new load query from file action.
     *
     * @param dataGenerationEngineFactory
     * @param applicationService the application service
     */
    public RecreateDatabaseAction(DataGenerationEngineFactory dataGenerationEngineFactory, 
                                  ApplicationService applicationService) {
        super("Recreate Database",
                new ImageIcon(RecreateDatabaseAction.class.getResource("resources/database_refresh.png")));
        this.applicationService = applicationService;
        this.dataGenerationEngineFactory = dataGenerationEngineFactory;
        putValue(SHORT_DESCRIPTION, "Recreates the database");
        putValue(LONG_DESCRIPTION, "Recreates the database, deleting all records in held in the process.");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        JXLabel label = new JXLabel("This action will recreate the database." +
                "This will result in the loss of all existing records." +
                "Are you sure you want to continue?");
        label.setLineWrap(true);
        label.setMaxLineSpan(300);

        int choice = JOptionPane.showConfirmDialog(applicationService.getFrameView().getActiveFrame(),
                label, "Confirm deletion",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION)
        {
            RecreateDatabaseTask task = new RecreateDatabaseTask(dataGenerationEngineFactory, applicationService);
            applicationService.getActiveApplication().getContext().getTaskService().execute(task);
        }
    }
}
