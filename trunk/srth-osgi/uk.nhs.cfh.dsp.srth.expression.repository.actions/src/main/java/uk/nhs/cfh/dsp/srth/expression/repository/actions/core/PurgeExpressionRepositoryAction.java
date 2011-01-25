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
package uk.nhs.cfh.dsp.srth.expression.repository.actions.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.swingx.JXLabel;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * An {@link javax.swing.Action} that deletes all entries in the expression repository
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 17-Mar-2010 at 13:56:51
 */
public class PurgeExpressionRepositoryAction extends AbstractAction{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The expression mapping object dao. */
    private ExpressionMappingObjectDAO expressionMappingObjectDAO;
    
    /** The expression subsumption relationship dao. */
    private ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(PopulateTCTableAction.class);

    /**
     * Instantiates a new purge expression repository action.
     *
     * @param applicationService the application service
     * @param expressionMappingObjectDAO the expression mapping object dao
     * @param expressionSubsumptionRelationshipDAO the expression subsumption relationship dao
     */
    public PurgeExpressionRepositoryAction(ApplicationService applicationService,
                                           ExpressionMappingObjectDAO expressionMappingObjectDAO,
                                           ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO) {
        super("Purge Expression Repository");
        this.applicationService = applicationService;
        this.expressionMappingObjectDAO = expressionMappingObjectDAO;
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
        putValue(SHORT_DESCRIPTION, "Purges expressions stored from the database");
        putValue(LONG_DESCRIPTION, "Delete all stored unique expressions from the database.");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        JPanel innerPanel = new JPanel(new BorderLayout());
        JXLabel label = new JXLabel("This action will delete all unique expressions stored in the database. " +
                "This usually renders the transitive closure table useless. " +
                "Do you really want to continue?");
        label.setLineWrap(true);
        label.setMaxLineSpan(300);

        innerPanel.add(label, BorderLayout.CENTER);
        // add a checkbox that controls if transitive closure table is purged
        JCheckBox purgeTCTableCheckBox = new JCheckBox("Purge Transitive Closure table");
        innerPanel.add(purgeTCTableCheckBox, BorderLayout.SOUTH);
        
        int choice = JOptionPane.showConfirmDialog(applicationService.getFrameView().getActiveFrame(), innerPanel, "Confirm deletion",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) 
        {
            PurgeExpressionRepositoryTask task = new PurgeExpressionRepositoryTask(applicationService.getActiveApplication());
            applicationService.getActiveApplication().getContext().getTaskService().execute(task);

            // purge tc table if purgeTCTable is true
            if(purgeTCTableCheckBox.isSelected())
            {
                PurgeTCTableTask tcTableTask = new PurgeTCTableTask(applicationService, expressionSubsumptionRelationshipDAO);
                applicationService.getActiveApplication().getContext().getTaskService().execute(tcTableTask);
            }
        }
    }

    /**
     * The Class PurgeExpressionRepositoryTask.
     */
    class PurgeExpressionRepositoryTask extends Task<Void, Void> {

        /**
         * Instantiates a new purge expression repository task.
         *
         * @param arg0 the arg0
         */
        public PurgeExpressionRepositoryTask(Application arg0) {
            super(arg0);
        }

        /* (non-Javadoc)
         * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
         */
        @Override
        protected Void doInBackground() throws Exception {
            expressionMappingObjectDAO.deleteAll();
            return null;
        }

        /* (non-Javadoc)
         * @see org.jdesktop.application.Task#succeeded(T)
         */
        @Override
        protected void succeeded(Void arg0) {
            setMessage("Successfully purged expression repository");
        }

        /* (non-Javadoc)
         * @see org.jdesktop.application.Task#failed(java.lang.Throwable)
         */
        @Override
        protected void failed(Throwable e) {
            String simpleMessage ="Error purging expression repository";
            setMessage(simpleMessage);
            applicationService.notifyError(simpleMessage, e, Level.WARNING);
            logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }
}
