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
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;
import uk.nhs.cfh.dsp.srth.desktop.modules.scripting.GroovyConsoleService;

/**
 * A custom {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponent} that launches the
 * scripting console
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 18, 2011 at 2:25:56 PM
 */
public class LaunchScriptingConsoleActionComponent extends AbstractActionComponent{

    private GroovyConsoleService groovyConsoleService;
    private ApplicationService applicationService;
    private ActionComponentService actionComponentService;
    private static Log logger = LogFactory.getLog(LaunchScriptingConsoleActionComponent.class);

    public LaunchScriptingConsoleActionComponent(String menuName, int menuLocationIndex, int toolBarIndex,
                                                 boolean visibleOnToolbar, GroovyConsoleService groovyConsoleService,
                                                 ApplicationService applicationService,
                                                 ActionComponentService actionComponentService) {
        super(menuName, menuLocationIndex, toolBarIndex, visibleOnToolbar);
        this.groovyConsoleService = groovyConsoleService;
        this.applicationService = applicationService;
        this.actionComponentService = actionComponentService;
    }

    /**
     * public no args constructor for IOC
     */
    public LaunchScriptingConsoleActionComponent() {
        super("Tools", 0, 0, false);
    }

    /**
     * Inits the.
     */
    @Override
    public synchronized void init(){

        logger.info("Starting action component : "+getClass());
        // create action and set action
        setAction(new LaunchGroovyConsoleAction(applicationService, groovyConsoleService));
        // register self with actionComponentService
        this.actionComponentService.registerActionComponent(this);
    }

    /**
     * Dispose.
     */
    @Override
    public synchronized void dispose(){
        // unregister from actionComponentService
        actionComponentService.unregisterActionComponent(this);
        logger.info("Stopped action component : "+getClass());
    }

    public synchronized void setGroovyConsoleService(GroovyConsoleService groovyConsoleService) {
        this.groovyConsoleService = groovyConsoleService;
    }

    /**
     * Sets the application service.
     *
     * @param applicationService the new application service
     */
    public synchronized void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Sets the action component service.
     *
     * @param actionComponentService the new action component service
     */
    public synchronized void setActionComponentService(ActionComponentService actionComponentService) {
        this.actionComponentService = actionComponentService;
    }
}
