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
import uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions.LoadQueryFromFileAction;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * An action component that allows the user to load a {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl}
 * from a {@link java.io.File}. It encapsulates a
 * {@link uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions.LoadQueryFromFileAction}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 28, 2009 at 2:22:34 PM
 */
public class LoadQueryFromFileActionComponent extends AbstractActionComponent{

    /** The query service. */
    private QueryService queryService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The action component service. */
    private ActionComponentService actionComponentService;
    
    /** The query expression xml converter. */
    private QueryExpressionXMLConverter queryExpressionXMLConverter;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(LoadQueryFromFileActionComponent.class);



    /**
     * Instantiates a new load query from file action component.
     *
     * @param applicationService the application service
     * @param actionComponentService the action component service
     * @param queryService the query service
     * @param queryExpressionXMLConverter the query expression xml converter
     */
    public LoadQueryFromFileActionComponent(ApplicationService applicationService,
                                           ActionComponentService actionComponentService,
                                           QueryService queryService,
                                           QueryExpressionXMLConverter queryExpressionXMLConverter) {

        super("File", 1, 1, true);
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.actionComponentService = actionComponentService;
        this.queryExpressionXMLConverter = queryExpressionXMLConverter;
    }

    /**
     * Empty constructor for IOC.
     */
    public LoadQueryFromFileActionComponent() {
        super("File", 1, 1, true);
    }

    /**
     * Inits the.
     */
    @Override
    public synchronized void init(){
        logger.info("Starting action component : "+getClass());
        // create new SaveQueryToFileAction and set as action
        setAction(new LoadQueryFromFileAction(applicationService, queryService, queryExpressionXMLConverter));

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
     * Sets the query expression xml converter.
     *
     * @param queryExpressionXMLConverter the new query expression xml converter
     */
    public synchronized void setQueryExpressionXMLConverter(QueryExpressionXMLConverter queryExpressionXMLConverter) {
        this.queryExpressionXMLConverter = queryExpressionXMLConverter;
    }
}