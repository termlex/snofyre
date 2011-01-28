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
import org.jdesktop.swingx.JXLabel;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.Action} that purges the transitive closure table for expressions.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Apr 10, 2010 at 3:34:25 PM
 */
public class PurgeTCTableAction extends AbstractAction{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The expression subsumption relationship dao. */
    private ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(PurgeTCTableAction.class);

    /**
     * Instantiates a new purge tc table action.
     *
     * @param applicationService the application service
     * @param expressionSubsumptionRelationshipDAO the expression subsumption relationship dao
     */
    public PurgeTCTableAction(ApplicationService applicationService,
                              ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO) {
        super("Purge TC table");
        this.applicationService = applicationService;
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
        putValue(SHORT_DESCRIPTION, "Purges the transitive closure table");
        putValue(LONG_DESCRIPTION, "Delete all stored subsumption relationships in the transitive closure table.");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        JXLabel label = new JXLabel("This action will delete all entries in the transitive closure table. " +
                "Regenerating the TC table is a lengthy and computationally intensive process. " +
                "Do you really want to continue?");
        label.setLineWrap(true);
        label.setMaxLineSpan(300);

        int choice = JOptionPane.showConfirmDialog(applicationService.getFrameView().getActiveFrame(), label, "Confirm deletion",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION)
        {
            PurgeTCTableTask task = new PurgeTCTableTask(applicationService, expressionSubsumptionRelationshipDAO);
            applicationService.getActiveApplication().getContext().getTaskService().execute(task);
        }
    }
}
