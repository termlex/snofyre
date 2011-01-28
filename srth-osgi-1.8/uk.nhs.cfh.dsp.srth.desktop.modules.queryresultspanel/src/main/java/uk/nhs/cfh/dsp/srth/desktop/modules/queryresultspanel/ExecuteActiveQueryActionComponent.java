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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;
import uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.actions.ExecuteActiveQueryAction;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService;

// TODO: Auto-generated Javadoc
/**
 * An action component that executes the active {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}
 * against the database. Its action is set to
 * {@link uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.actions.ExecuteActiveQueryAction}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 30, 2009 at 3:32:32 PM
 */
public class ExecuteActiveQueryActionComponent extends AbstractActionComponent{

    /** The query service. */
    private QueryService queryService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The action component service. */
    private ActionComponentService actionComponentService;
    
    /** The sql query engine service. */
    private SQLQueryEngineService sqlQueryEngineService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ExecuteActiveQueryActionComponent.class);
    

    /**
     * Instantiates a new execute active query action component.
     * 
     * @param applicationService the application service
     * @param actionComponentService the action component service
     * @param queryService the query service
     * @param sqlQueryEngineService the sql query engine service
     */
    public ExecuteActiveQueryActionComponent(ApplicationService applicationService,
                                           ActionComponentService actionComponentService,
                                           QueryService queryService,
                                           SQLQueryEngineService sqlQueryEngineService) {

        super("Query", 0, 4, true);
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.actionComponentService = actionComponentService;
        this.sqlQueryEngineService = sqlQueryEngineService;
    }

    /**
     * No args constructor for IOC.
     */
    public ExecuteActiveQueryActionComponent() {
        super("Query", 0, 4, true);
    }

    /**
     * Inits the.
     */
    @Override
    public synchronized void init() {
        logger.info("Starting module : "+getClass().getName());
        // set action
        setAction(new ExecuteActiveQueryAction(applicationService, queryService, sqlQueryEngineService));
        // set visible on toolbar
        setVisibleOnToolbar(true);
        // register self with actionComponentService
        actionComponentService.registerActionComponent(this);
    }

    /**
     * Dispose.
     */
    @Override
    public synchronized void dispose() {
        // unregister self from actionComponentService
        actionComponentService.unregisterActionComponent(this);
        logger.info("Stopped module : "+getClass().getName());
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
     * Sets the sql query engine service.
     *
     * @param sqlQueryEngineService the new sql query engine service
     */
    public synchronized void setSqlQueryEngineService(SQLQueryEngineService sqlQueryEngineService) {
        this.sqlQueryEngineService = sqlQueryEngineService;
    }
}
