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
package uk.nhs.cfh.dsp.srth.desktop.modules.plugin.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;

import java.net.URI;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponent} that displays
 * a plugin management console.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 20, 2010 at 8:23:29 PM
 */
public class ShowPluginManagerActionComponent extends AbstractActionComponent{

    /** The action component service. */
    private ActionComponentService actionComponentService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ShowPluginManagerActionComponent.class);
    
//    /** The user name. */
//    private String userName;
//
//    /** The pass word. */
//    private String passWord;
    
    /** The console system path. */
    private String consoleSystemPath;
    
    /** The console url. */
    private String consoleURL;

    /**
     * Instantiates a new show plugin manager action component.
     */
    public ShowPluginManagerActionComponent() {
        super("View", 0, 0, false);
    }
    
    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent#init()
     */
    @Override
    public synchronized void init(){

        logger.info("Starting action component : "+getClass());
        URI pluginConsoleURI = URI.create(consoleURL+consoleSystemPath);
        // create action and set action
        setAction(new ShowPluginManagerAction(pluginConsoleURI));

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

    /**
     * Sets the action component service.
     *
     * @param actionComponentService the new action component service
     */
    public synchronized void setActionComponentService(ActionComponentService actionComponentService) {
        this.actionComponentService = actionComponentService;
    }

//    /**
//     * Sets the user name.
//     *
//     * @param userName the new user name
//     */
//    public synchronized void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    /**
//     * Sets the pass word.
//     *
//     * @param passWord the new pass word
//     */
//    public void setPassWord(String passWord) {
//        this.passWord = passWord;
//    }

    /**
     * Sets the console system path.
     *
     * @param consoleSystemPath the new console system path
     */
    public synchronized void setConsoleSystemPath(String consoleSystemPath) {
        this.consoleSystemPath = consoleSystemPath;
    }

    /**
     * Sets the console url.
     *
     * @param consoleURL the new console url
     */
    public synchronized void setConsoleURL(String consoleURL) {
        this.consoleURL = consoleURL;
    }
}
