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
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.Action} that deletes all patient records from the database.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 09-Mar-2010 at 16:31:58
 */
public class DeleteAllRecordsAction extends AbstractAction{

            /** The application service. */
    private ApplicationService applicationService;
    
    /** The patient dao. */
    private PatientDAO patientDAO;

    /**
     * Instantiates a new load query from file action.
     *
     * @param patientDAO the patient dao
     * @param applicationService the application service
     */
    public DeleteAllRecordsAction(PatientDAO patientDAO, ApplicationService applicationService) {
        super("Delete All Records",
                new ImageIcon(DeleteAllRecordsAction.class.getResource("resources/emptytrash.png")));
        this.applicationService = applicationService;
        this.patientDAO = patientDAO;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X,
        	    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(SHORT_DESCRIPTION, "Delete all records from the database");
        putValue(LONG_DESCRIPTION, "Delete all existing patient records from the active database.");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        JXLabel label = new JXLabel("This action will purge all simulated records." +
                "This will result in the loss of all existing records." +
                "Do you really want to continue?");
        label.setLineWrap(true);
        label.setMaxLineSpan(300);

        int choice = JOptionPane.showConfirmDialog(applicationService.getFrameView().getActiveFrame(), label,
                "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION)
        {
            DeleteAllRecordsTask task = new DeleteAllRecordsTask(patientDAO, applicationService);
            applicationService.getActiveApplication().getContext().getTaskService().execute(task);
        }
    }
}
