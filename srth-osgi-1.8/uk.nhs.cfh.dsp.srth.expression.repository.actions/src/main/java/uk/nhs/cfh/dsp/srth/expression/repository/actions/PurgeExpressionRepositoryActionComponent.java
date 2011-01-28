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
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionMappingObjectDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.ExpressionSubsumptionRelationshipDAO;
import uk.nhs.cfh.dsp.srth.expression.repository.actions.core.PurgeExpressionRepositoryAction;

// TODO: Auto-generated Javadoc
/**
 * An {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent} that deletes
 * all entries in the expression repository.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 17-Mar-2010 at 14:05:22
 */
public class PurgeExpressionRepositoryActionComponent extends AbstractActionComponent{

    /** The application service. */
    private ApplicationService applicationService;

    /** The action component service. */
    private ActionComponentService actionComponentService;
    
    /** The expression mapping object dao. */
    private ExpressionMappingObjectDAO expressionMappingObjectDAO;
    
    /** The expression subsumption relationship dao. */
    private ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(PopulateTCTableActionComponent.class);


    /**
     * Instantiates a new new query action component.
     *
     * @param applicationService the application service
     * @param actionComponentService the action component service
     * @param expressionMappingObjectDAO the expression mapping object dao
     * @param expressionSubsumptionRelationshipDAO the expression subsumption relationship dao
     */
    public PurgeExpressionRepositoryActionComponent(ApplicationService applicationService,
                                                    ActionComponentService actionComponentService,
                                                    ExpressionMappingObjectDAO expressionMappingObjectDAO,
                                                    ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO) {
        super("Tools", 0, 0, false);
        this.applicationService = applicationService;
        this.actionComponentService = actionComponentService;
        this.expressionMappingObjectDAO = expressionMappingObjectDAO;
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
    }

    /**
     * Instantiates a new purge expression repository action component.
     */
    public PurgeExpressionRepositoryActionComponent() {
        super("Tools", 0, 0, false);
    }

    /**
     * Inits the.
     */
    @Override
    public synchronized void init(){

        logger.info("Starting action component : "+getClass());
        // create action and set action
        setAction(new PurgeExpressionRepositoryAction(applicationService, expressionMappingObjectDAO,
                expressionSubsumptionRelationshipDAO));
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
     * Sets the expression mapping object dao.
     *
     * @param expressionMappingObjectDAO the new expression mapping object dao
     */
    public synchronized void setExpressionMappingObjectDAO(ExpressionMappingObjectDAO expressionMappingObjectDAO) {
        this.expressionMappingObjectDAO = expressionMappingObjectDAO;
    }

    /**
     * Sets the expression subsumption relationship dao.
     *
     * @param expressionSubsumptionRelationshipDAO the new expression subsumption relationship dao
     */
    public synchronized void setExpressionSubsumptionRelationshipDAO(ExpressionSubsumptionRelationshipDAO expressionSubsumptionRelationshipDAO) {
        this.expressionSubsumptionRelationshipDAO = expressionSubsumptionRelationshipDAO;
    }
}
