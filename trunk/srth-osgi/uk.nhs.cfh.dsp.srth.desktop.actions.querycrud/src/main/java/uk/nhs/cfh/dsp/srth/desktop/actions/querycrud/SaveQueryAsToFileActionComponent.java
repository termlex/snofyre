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
import uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions.SaveQueryAsToFileAction;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;
import uk.nhs.cfh.dsp.srth.query.converters.file.QueryExpressionFileOutputter;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

// TODO: Auto-generated Javadoc
/**
 * An action component that allows the user to save a {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl}
 * to a {@link java.io.File}. It encapsulates a
 * {@link uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions.SaveQueryAsToFileAction}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 28, 2009 at 2:18:02 PM
 */
public class SaveQueryAsToFileActionComponent extends AbstractActionComponent{

    /** The query service. */
    private QueryService queryService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The action component service. */
    private ActionComponentService actionComponentService;
    
    /** The query expression file outputter. */
    private QueryExpressionFileOutputter queryExpressionFileOutputter;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SaveQueryAsToFileActionComponent.class);



    /**
     * Instantiates a new save query as to file action component.
     * 
     * @param applicationService the application service
     * @param actionComponentService the action component service
     * @param queryService the query service
     * @param queryExpressionFileOutputter the query expression file outputter
     */
    public SaveQueryAsToFileActionComponent(ApplicationService applicationService,
                                           ActionComponentService actionComponentService,
                                           QueryService queryService,
                                           QueryExpressionFileOutputter queryExpressionFileOutputter) {

        super("File", 4, 4, true);
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.actionComponentService = actionComponentService;
        this.queryExpressionFileOutputter = queryExpressionFileOutputter;
    }

    /**
     * Empty constructor for IOC.
     */
    public SaveQueryAsToFileActionComponent() {
        super("File", 4, 4, true);
    }

    /**
     * Inits the.
     */
    @Override
    public synchronized void init(){
        logger.info("Starting action component : "+getClass());
        // create new SaveQueryToFileAction and set as action
        setAction(new SaveQueryAsToFileAction(applicationService, queryService, queryExpressionFileOutputter));

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
     * Sets the query expression file outputter.
     *
     * @param queryExpressionFileOutputter the new query expression file outputter
     */
    public synchronized void setQueryExpressionFileOutputter(QueryExpressionFileOutputter queryExpressionFileOutputter) {
        this.queryExpressionFileOutputter = queryExpressionFileOutputter;
    }
}