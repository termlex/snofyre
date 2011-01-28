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

import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.query.converters.file.QueryExpressionFileOutputter;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

// TODO: Auto-generated Javadoc
/**
 * An action that allows the user to save a {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl}
 * to a {@link java.io.File}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 28, 2009 at 1:32:06 PM
 * <br> Modified on Saturday; November 28, 2009 at 1:38 PM to include
 * service listeners.
 */
public class SaveQueryAsToFileAction extends AbstractAction{


    /** The application service. */
    private ApplicationService applicationService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The query expression file outputter. */
    private QueryExpressionFileOutputter queryExpressionFileOutputter;

    /**
     * Instantiates a new save query as to file action.
     * 
     * @param applicationService the application service
     * @param queryService the query service
     * @param queryExpressionFileOutputter the query expression file outputter
     */
    public SaveQueryAsToFileAction(ApplicationService applicationService, QueryService queryService,
                                   QueryExpressionFileOutputter queryExpressionFileOutputter) {
        super("Save query to file as", new ImageIcon(LoadQueryFromFileAction.class.getResource("resources/filesaveas.png")));
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.queryExpressionFileOutputter = queryExpressionFileOutputter;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
        		(java.awt.event.InputEvent.SHIFT_MASK |
        				(Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()))));
        putValue(SHORT_DESCRIPTION, "Save query as");
        putValue(LONG_DESCRIPTION, "Opens a dialog to save the query as a file to an user specified location");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        SaveQueryToFileTask task = new SaveQueryToFileTask(applicationService, queryService, queryExpressionFileOutputter);
        applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }
}
