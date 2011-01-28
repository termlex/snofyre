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
package uk.nhs.cfh.dsp.srth.desktop.modules.scripting.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modules.scripting.GroovyConsoleService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

/**
 * A custom {@link javax.swing.Action} that launches the Scripting Console
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 18, 2011 at 2:31:00 PM
 */
public class LaunchGroovyConsoleAction extends AbstractAction{

        /** The application service. */
    private ApplicationService applicationService;

    /** The groovy console service */
    private GroovyConsoleService groovyConsoleService;

    /** The logger. */
    private static Log logger = LogFactory.getLog(LaunchGroovyConsoleAction.class);

    /**
     * Instantiates a new purge tc table action.
     *
     * @param applicationService the application service
     * @param groovyConsoleService the scripting console service
     */
    public LaunchGroovyConsoleAction(ApplicationService applicationService,
                              GroovyConsoleService groovyConsoleService) {
        super("Launch Scripting Console", new ImageIcon(LaunchGroovyConsoleAction.class.getResource("resources/xterm.png")));
        this.applicationService = applicationService;
        this.groovyConsoleService = groovyConsoleService;
        putValue(SHORT_DESCRIPTION, "Launches the scripting console");
        putValue(LONG_DESCRIPTION, "Launches the scripting console for executing commands using Snofyre's API");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent event) {
        try
        {
            if(groovyConsoleService != null)
            {
                logger.info("Launching Scripting console...");
                groovyConsoleService.launch();
            }
        }
        catch (Exception e) {
            String simpleMessage ="Error transitive closure table";
            applicationService.notifyError(simpleMessage, e, Level.WARNING);
            logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }
}
