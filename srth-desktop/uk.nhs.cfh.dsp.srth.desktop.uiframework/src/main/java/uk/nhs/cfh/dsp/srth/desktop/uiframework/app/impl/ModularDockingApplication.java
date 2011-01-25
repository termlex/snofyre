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
package uk.nhs.cfh.dsp.srth.desktop.uiframework.app.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.SingleFrameApplication;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingApplication;
import uk.nhs.cfh.dsp.srth.desktop.appservice.utils.LookAndFeelUtils;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;


// TODO: Auto-generated Javadoc
/**
 * A modular docking enabled {@link org.jdesktop.application.SingleFrameApplication}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 12, 2010 at 4:39:51 PM
 */
public class ModularDockingApplication extends SingleFrameApplication implements MDockingApplication {

    /** The logger. */
    private static Log logger = LogFactory.getLog(ModularDockingApplication.class);

    /** The view component service. */
    private ViewComponentService viewComponentService;

    /** The action component service. */
    private ActionComponentService actionComponentService;

    /** The application service. */
    private ApplicationService applicationService;

    private ModularApplicationAboutDialog aboutDialog;
    private String applicationPropertiesFileLocation;

    /**
     * Prints the system properties.
     * @param properties the properties to print out
     */
    protected void printProperties(Properties properties){

        Enumeration keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String value = (String)properties.get(key);
            logger.info(key + ": " + value);
        }
    }

     /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {

         // set application properties
         setApplicationProperties(applicationPropertiesFileLocation);
         // print all system variables for debugging
         logger.info("\n =============== Starting application "+getClass().getName()+"===============\n");
         printProperties(applicationService.getApplicationProperties());
         printProperties(System.getProperties());
         logger.info("\n =============== End of Properties =============== \n");

        /* Initialize the ApplicationContext application properties
         */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ApplicationContext ctx = getContext();
                ctx.setApplicationClass(ModularDockingApplication.class);

                // set the LNF to system default
                LookAndFeelUtils.setDefaultLNF();
                // register self with application service
                getApplicationService().registerApplication(ModularDockingApplication.this);

                show(new ModularDockingApplicationView(ModularDockingApplication.this, getViewComponentService(),
                        getActionComponentService(),  getApplicationService(), aboutDialog));
            }
        });
    }

    private void setApplicationProperties(String propertiesFileLocation){
        Properties properties = new Properties();
        if(propertiesFileLocation != null)
        {
            try
            {
                properties.load(new FileReader(propertiesFileLocation));
                while(applicationService == null || applicationService.getApplicationProperties() == null)
                {
                    // set properties
                    if(applicationService != null){
                        applicationService.setApplicationProperties(properties);
                    }
                }
            }
            catch (IOException e) {
                logger.warn("Error load properties from file specified. " +
                        "Nested exception is : " + e.fillInStackTrace());
            }
        }
    }

    /**
     * A convenient static getter for the application instance.
     *
     * @return the instance of ModularDockingApplication
     */
    public static ModularDockingApplication getApplication() {
        return Application.getInstance(ModularDockingApplication.class);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingApplication#getActiveApplication()
     */
    public Application getActiveApplication() {
        return this;
    }

    /**
     * Sets the view component service.
     *
     * @param viewComponentService the new view component service
     */
    public synchronized void setViewComponentService(ViewComponentService viewComponentService) {
        this.viewComponentService = viewComponentService;
    }

    /**
     * Sets the action component service.
     *
     * @param actionComponentService the new action component service
     */
    public synchronized void setActionComponentService(ActionComponentService actionComponentService) {
        this.actionComponentService = actionComponentService;
    }

    /**
     * Sets the application service.
     *
     * @param applicationService the new application service
     */
    public synchronized void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingApplication#getViewComponentService()
     */
    public ViewComponentService getViewComponentService() {
        return viewComponentService;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingApplication#getActionComponentService()
     */
    public ActionComponentService getActionComponentService() {
        return actionComponentService;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingApplication#getApplicationService()
     */
    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public synchronized void setAboutDialog(ModularApplicationAboutDialog aboutDialog) {
        this.aboutDialog = aboutDialog;
    }

    public synchronized void setApplicationPropertiesFileLocation(String applicationPropertiesFileLocation) {
        this.applicationPropertiesFileLocation = applicationPropertiesFileLocation;
    }
}
