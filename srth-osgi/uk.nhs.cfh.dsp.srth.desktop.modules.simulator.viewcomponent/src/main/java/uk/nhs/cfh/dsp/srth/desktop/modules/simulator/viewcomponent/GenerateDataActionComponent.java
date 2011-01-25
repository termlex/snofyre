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
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;
import uk.nhs.cfh.dsp.srth.desktop.modules.simulator.viewcomponent.actions.GenerateDataAction;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;


// TODO: Auto-generated Javadoc
/**
 * An action component that displays the actions for generating data in the menu and toolbar.
 * {@link }.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 3:13:35 AM
 */
public class GenerateDataActionComponent extends AbstractActionComponent{

    /** The application service. */
    private ApplicationService applicationService;

    /** The action component service. */
    private ActionComponentService actionComponentService;
    /** The query service. */
    private QueryService queryService;
    
    /** The data generation engine. */
    private DataGenerationEngine dataGenerationEngine;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(GenerateDataActionComponent.class);
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;


    /**
     * Instantiates a new new query action component.
     *
     * @param applicationService the application service
     * @param actionComponentService the action component service
     * @param queryService the query service
     * @param dataGenerationEngine the data generation engine
     * @param propertyChangeTrackerService the property change tracker service
     */
    public GenerateDataActionComponent(ApplicationService applicationService,
                                       ActionComponentService actionComponentService,
                                       QueryService queryService,
                                       DataGenerationEngine dataGenerationEngine,
                                       PropertyChangeTrackerService propertyChangeTrackerService) {
        super("Data", 0, 0, false);
        this.applicationService = applicationService;
        this.actionComponentService = actionComponentService;
        this.dataGenerationEngine = dataGenerationEngine;
        this.queryService = queryService;
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }

    /**
     * Instantiates a new generate data action component.
     */
    public GenerateDataActionComponent() {
        super("Data", 0, 0, false);
    }

    /**
     * Inits the.
     */
    @Override
    public synchronized void init(){

        logger.info("Starting action component : "+getClass());
        // create action and set action
        setAction(new GenerateDataAction(applicationService, queryService, dataGenerationEngine,
                propertyChangeTrackerService));

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

    /**
     * Sets the query service.
     *
     * @param queryService the new query service
     */
    public synchronized void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Sets the data generation engine.
     *
     * @param dataGenerationEngine the new data generation engine
     */
    public synchronized void setDataGenerationEngine(DataGenerationEngine dataGenerationEngine) {
        this.dataGenerationEngine = dataGenerationEngine;
    }

    /**
     * Sets the property change tracker service.
     *
     * @param propertyChangeTrackerService the new property change tracker service
     */
    public synchronized void setPropertyChangeTrackerService(PropertyChangeTrackerService propertyChangeTrackerService) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }
}