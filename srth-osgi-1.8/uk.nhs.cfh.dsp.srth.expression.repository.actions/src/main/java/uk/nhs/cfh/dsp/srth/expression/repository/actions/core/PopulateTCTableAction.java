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
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionTransitiveClosureTableGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;


// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.application.Task} for computing the transitive closure table
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 3:17:12 AM.
 */
public class PopulateTCTableAction extends AbstractAction{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The expression transitive closure table generator. */
    private ExpressionTransitiveClosureTableGenerator expressionTransitiveClosureTableGenerator;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(PopulateTCTableAction.class);

    /**
     * Instantiates a new populate tc table action.
     *
     * @param applicationService the application service
     * @param expressionTransitiveClosureTableGenerator the expression transitive closure table generator
     */
    public PopulateTCTableAction(ApplicationService applicationService,
                                 ExpressionTransitiveClosureTableGenerator expressionTransitiveClosureTableGenerator) {
        super("Generate TC table", new ImageIcon(PopulateTCTableAction.class.getResource("resources/kdesvn.png")));
        this.applicationService = applicationService;
        this.expressionTransitiveClosureTableGenerator = expressionTransitiveClosureTableGenerator;
        putValue(SHORT_DESCRIPTION, "Generates a transitive closure table");
        putValue(LONG_DESCRIPTION, "Generates a transitive closure table for the expressions in the database");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        JXLabel label = new JXLabel("Computing the transitive closure table is a computationally intensive and " +
                "time consuming process, generally lasting an hour or longer. " +
                "Do you really want to continue?");
        label.setLineWrap(true);
        label.setMaxLineSpan(300);

        int choice = JOptionPane.showConfirmDialog(applicationService.getFrameView().getActiveFrame(), label, "Confirm action",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION)
        {
            class TCGenerateTask extends Task<Void, Void> {

                public TCGenerateTask(Application arg0) {
                    super(arg0);
                    setTitle(TCGenerateTask.class.getName());
                }

                @Override
                protected Void doInBackground() throws Exception {
                    expressionTransitiveClosureTableGenerator.generateTCTable(true);
                    return null;
                }

                @Override
                protected void succeeded(Void arg0) {
                    setMessage("Successfully generated TC table");
                }

                @Override
                protected void failed(Throwable e) {
                    String simpleMessage ="Error generating TC table";
                    setMessage(simpleMessage);
                    applicationService.notifyError(simpleMessage, e, Level.WARNING);
                    logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
                }
            }

            TCGenerateTask task = new TCGenerateTask(applicationService.getActiveApplication());
            applicationService.getActiveApplication().getContext().getTaskService().execute(task);
        }
    }
}
