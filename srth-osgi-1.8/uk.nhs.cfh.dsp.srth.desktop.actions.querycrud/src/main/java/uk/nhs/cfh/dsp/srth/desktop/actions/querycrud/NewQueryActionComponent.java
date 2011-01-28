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
package uk.nhs.cfh.dsp.srth.desktop.actions.querycrud;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions.NewQueryAction;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * An action component that creates a new {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl}.
 * It encapsulates a {@link uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions.NewQueryAction}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 27, 2009 at 5:13:35 PM
 */
public class NewQueryActionComponent extends AbstractActionComponent{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The action component service. */
    private ActionComponentService actionComponentService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The query expression factory. */
    private QueryExpressionFactory queryExpressionFactory;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(NewQueryActionComponent.class);

    /**
     * Instantiates a new new query action component.
     *
     * @param applicationService the application service
     * @param actionComponentService the action component service
     * @param queryService the query service
     * @param queryExpressionFactory the query expression factory
     */
    public NewQueryActionComponent(ApplicationService applicationService,
                                   ActionComponentService actionComponentService,
                                   QueryService queryService, QueryExpressionFactory queryExpressionFactory) {
        super("File", 0, 0, true);
        this.applicationService = applicationService;
        this.actionComponentService = actionComponentService;
        this.queryService = queryService;
        this.queryExpressionFactory = queryExpressionFactory;
    }

    /**
     * Empty constructor for IOC.
     */
    public NewQueryActionComponent() {
        super("File", 0, 0, true);
    }

    /**
     * Inits the.
     */
    @Override
    public synchronized void init(){

        logger.info("Starting action component : "+getClass());
        // create action and set action
        setAction(new NewQueryAction(applicationService, queryService, queryExpressionFactory));

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
     * Sets the query expression factory.
     *
     * @param queryExpressionFactory the new query expression factory
     */
    public synchronized void setQueryExpressionFactory(QueryExpressionFactory queryExpressionFactory) {
        this.queryExpressionFactory = queryExpressionFactory;
    }
}
