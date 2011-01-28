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

import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.Action} that handles data generation using a
 * {@link uk.nhs.cfh.dsp.srth.desktop.modules.simulator.viewcomponent.actions.DataGeneratorTask}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 13, 2010 at 12:32:29 AM
 */
public class GenerateDataAction extends AbstractAction{
        /** The application service. */
    private ApplicationService applicationService;

    /** The query service. */
    private QueryService queryService;
    
    /** The data generation engine. */
    private DataGenerationEngine dataGenerationEngine;
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;

    /**
     * Instantiates a new load query from file action.
     *
     * @param applicationService the application service
     * @param queryService the query service
     * @param dataGenerationEngine the data generation engine
     * @param propertyChangeTrackerService the property change tracker service
     */
    public GenerateDataAction(ApplicationService applicationService, QueryService queryService,
                                   DataGenerationEngine dataGenerationEngine,
                                   PropertyChangeTrackerService propertyChangeTrackerService) {
        super("Generate data",
                new ImageIcon(GenerateDataAction.class.getResource("resources/kcmsystem.png")));
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.dataGenerationEngine = dataGenerationEngine;
        this.propertyChangeTrackerService = propertyChangeTrackerService;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D,
        	    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(SHORT_DESCRIPTION, "Generates data from a given query");
        putValue(LONG_DESCRIPTION, "Uses the current query or a saved query to generate data.");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

    	DataGeneratorTask task = new DataGeneratorTask(applicationService, queryService.getActiveQuery(),
                dataGenerationEngine);
        // register task with propertyChangeTrackerService
        propertyChangeTrackerService.registerListener(task);
    	applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }
}
