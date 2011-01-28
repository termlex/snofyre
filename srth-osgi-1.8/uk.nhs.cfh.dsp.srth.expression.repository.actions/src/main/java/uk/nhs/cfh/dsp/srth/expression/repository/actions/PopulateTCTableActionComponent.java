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
package uk.nhs.cfh.dsp.srth.expression.repository.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionTransitiveClosureTableGenerator;
import uk.nhs.cfh.dsp.srth.expression.repository.actions.core.PopulateTCTableAction;


// TODO: Auto-generated Javadoc
/**
 * An action component that populates the Transitive closure table using an.
 *
 * {@link uk.nhs.cfh.dsp.srth.expression.repository.ExpressionTransitiveClosureTableGenerator}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 15, 2010 at 3:13:35 AM
 */
public class PopulateTCTableActionComponent extends AbstractActionComponent{

    /** The application service. */
    private ApplicationService applicationService;

    /** The action component service. */
    private ActionComponentService actionComponentService;
    
    /** The expression transitive closure table generator. */
    private ExpressionTransitiveClosureTableGenerator expressionTransitiveClosureTableGenerator;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(PopulateTCTableActionComponent.class);


    /**
     * Instantiates a new new query action component.
     *
     * @param applicationService the application service
     * @param actionComponentService the action component service
     * @param expressionTransitiveClosureTableGenerator the expression transitive closure table generator
     */
    public PopulateTCTableActionComponent(ApplicationService applicationService,
                                   ActionComponentService actionComponentService,
                                   ExpressionTransitiveClosureTableGenerator expressionTransitiveClosureTableGenerator) {
        super("Tools", 0, 0, false);
        this.applicationService = applicationService;
        this.actionComponentService = actionComponentService;
        this.expressionTransitiveClosureTableGenerator = expressionTransitiveClosureTableGenerator;
    }

    /**
     * Instantiates a new populate tc table action component.
     */
    public PopulateTCTableActionComponent() {
        super("Tools", 0, 0, false);
    }

    /**
     * Inits the.
     */
    @Override
    public synchronized void init(){

        logger.info("Starting action component : "+getClass());
        // create action and set action
        setAction(new PopulateTCTableAction(applicationService, expressionTransitiveClosureTableGenerator));

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
     * Sets the expression transitive closure table generator.
     *
     * @param expressionTransitiveClosureTableGenerator the new expression transitive closure table generator
     */
    public synchronized void setExpressionTransitiveClosureTableGenerator(ExpressionTransitiveClosureTableGenerator expressionTransitiveClosureTableGenerator) {
        this.expressionTransitiveClosureTableGenerator = expressionTransitiveClosureTableGenerator;
    }
}