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
package uk.nhs.cfh.dsp.srth.desktop.modules.simulator.viewcomponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;
import uk.nhs.cfh.dsp.srth.desktop.modules.simulator.viewcomponent.actions.RecreateDatabaseAction;
import uk.nhs.cfh.dsp.srth.simulator.DataGenerationEngineFactory;

import javax.swing.*;

// TODO: Auto-generated Javadoc

/**
 * An {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponent} that recreates the database
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 10-Mar-2010 at 10:47:22
 */
public class RecreateDatabaseActionComponent extends AbstractActionComponent{

    private DataGenerationEngineFactory dataGenerationEngineFactory;
    /** The application service. */
    private ApplicationService applicationService;

    /** The action component service. */
    private ActionComponentService actionComponentService;

    /** The logger. */
    private static Log logger = LogFactory.getLog(RecreateDatabaseActionComponent.class);

    /**
     * Instantiates a new delete all records action component.
     *
     * @param action the action
     * @param dataGenerationEngineFactory
     * @param applicationService the application service
     * @param actionComponentService the action component service
     */
    public RecreateDatabaseActionComponent(Action action, DataGenerationEngineFactory dataGenerationEngineFactory,
                                           ApplicationService applicationService,
                                           ActionComponentService actionComponentService) {
        super("Data", 0, 0, false);
        this.dataGenerationEngineFactory = dataGenerationEngineFactory;
        this.applicationService = applicationService;
        this.actionComponentService = actionComponentService;
    }

    /**
     * Instantiates a new delete all records action component.
     */
    public RecreateDatabaseActionComponent() {
        super("Data", 0, 0, false);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent#init()
     */
    @Override
    public void init() {
        logger.info("Starting action component : "+getClass().getName());
        setAction(new RecreateDatabaseAction(dataGenerationEngineFactory, applicationService));
        // register with actionComponentService
        actionComponentService.registerActionComponent(this);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent#dispose()
     */
    @Override
    public void dispose() {
        actionComponentService.unregisterActionComponent(this);
        logger.info("Stopped action component : "+getClass().getName());
    }

    public synchronized void setDataGenerationEngineFactory(DataGenerationEngineFactory dataGenerationEngineFactory) {
        this.dataGenerationEngineFactory = dataGenerationEngineFactory;
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
