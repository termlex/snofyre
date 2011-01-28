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
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractViewComponent;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent} that handles
 * data generation.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 13, 2010 at 12:55:27 AM
 */
public class DataGenerationViewComponent extends AbstractViewComponent{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The data generation engine. */
    private DataGenerationEngine dataGenerationEngine;
    
    /** The view component service. */
    private ViewComponentService viewComponentService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(DataGenerationViewComponent.class);
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;

    /**
     * Instantiates a new data generation view component.
     */
    public DataGenerationViewComponent() {
        super("Data Generation", Alignment.BOTTOM, null);
    }


    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent#init()
     */
    public synchronized void init() {
        logger.info("Starting module"+getName());
        DataGenerationPanel dataGenerationPanel =
                new DataGenerationPanel(applicationService, queryService, dataGenerationEngine, propertyChangeTrackerService);
        dataGenerationPanel.initComponents();
        // set as component and register with listeners
        setComponent(dataGenerationPanel);
        viewComponentService.registerViewComponent(this);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent#dispose()
     */
    public synchronized void dispose() {
        viewComponentService.unregisterViewComponent(this);
        logger.info("Stopped module :"+getName());        
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
     * Sets the view component service.
     *
     * @param viewComponentService the new view component service
     */
    public synchronized void setViewComponentService(ViewComponentService viewComponentService) {
        this.viewComponentService = viewComponentService;
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
