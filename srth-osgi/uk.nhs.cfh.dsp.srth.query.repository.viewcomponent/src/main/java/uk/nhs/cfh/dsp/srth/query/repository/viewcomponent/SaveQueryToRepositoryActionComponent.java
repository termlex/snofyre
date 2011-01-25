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
package uk.nhs.cfh.dsp.srth.query.repository.viewcomponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;
import uk.nhs.cfh.dsp.srth.query.repository.QueryRepositoryService;
import uk.nhs.cfh.dsp.srth.query.repository.viewcomponent.actions.SaveQueryToRepositoryAction;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 22-Mar-2010 at 14:51:04.
 */
public class SaveQueryToRepositoryActionComponent extends AbstractActionComponent{

        /** The query service. */
    private QueryService queryService;

    /** The application service. */
    private ApplicationService applicationService;

    /** The action component service. */
    private ActionComponentService actionComponentService;

    /** The query repository service. */
    private QueryRepositoryService queryRepositoryService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SaveQueryToRepositoryActionComponent.class);

    /**
     * Instantiates a new save query to repository action component.
     *
     * @param applicationService the application service
     * @param actionComponentService the action component service
     * @param queryService the query service
     * @param queryRepositoryService the query repository service
     */
    public SaveQueryToRepositoryActionComponent(ApplicationService applicationService,
                                           ActionComponentService actionComponentService,
                                           QueryService queryService,
                                           QueryRepositoryService queryRepositoryService) {

        super("File", 2, 2, true);
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.actionComponentService = actionComponentService;
        this.queryRepositoryService = queryRepositoryService;
    }

    /**
     * Empty constructor for IOC.
     */
    public SaveQueryToRepositoryActionComponent() {
        super("File", 2, 2, true);
    }

    /**
     * Inits the.
     */
    @Override
    public synchronized void init(){
        logger.info("Starting action component : "+getClass());
        // create new SaveQueryToFileAction and set as action
        setAction(new SaveQueryToRepositoryAction(applicationService, queryService, queryRepositoryService));

        // register self with actionComponentService
        actionComponentService.registerActionComponent(this);
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
     * Sets the query service.
     *
     * @param queryService the new query service
     */
    public synchronized void setQueryService(QueryService queryService) {
        this.queryService = queryService;
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
     * Sets the query repository service.
     *
     * @param queryRepositoryService the new query repository service
     */
    public synchronized void setQueryRepositoryService(QueryRepositoryService queryRepositoryService) {
        this.queryRepositoryService = queryRepositoryService;
    }
}
