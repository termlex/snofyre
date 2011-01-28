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
package uk.nhs.cfh.dsp.yasb.expression.builder.viewcomponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractViewComponent;
import uk.nhs.cfh.dsp.yasb.expression.builder.ExpressionBuilderPanel;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;

// TODO: Auto-generated Javadoc
/**
 * A {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent} that contains a 
 * {@link uk.nhs.cfh.dsp.yasb.expression.builder.ExpressionBuilderPanel}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 11, 2010 at 5:11:49 PM
 */
public class ExpressionBuilderViewComponent extends AbstractViewComponent{

    /** The logger. */
    private static Log logger = LogFactory.getLog(ExpressionBuilderViewComponent.class);
    
    /** The expression builder panel. */
    private ExpressionBuilderPanel expressionBuilderPanel;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The view component service. */
    private ViewComponentService viewComponentService;
    
    /** The terminology search service. */
    private TerminologySearchService terminologySearchService;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;

    /**
     * Empty constructor for IOC.
     */
    public ExpressionBuilderViewComponent() {
        super("Expression Builder", Alignment.RIGHT, null);
    }

    /**
     * Instantiates a new expression builder view component.
     *
     * @param applicationService the application service
     * @param selectionService the selection service
     * @param viewComponentService the view component service
     * @param terminologySearchService the terminology search service
     * @param terminologyConceptDAO the terminology concept dao
     * @param propertyChangeTrackerService the property change tracker service
     * @param humanReadableRender the human readable render
     * @param normalFormGenerator the normal form generator
     */
    public ExpressionBuilderViewComponent(ApplicationService applicationService, SelectionService selectionService,
                                          ViewComponentService viewComponentService,
                                          TerminologySearchService terminologySearchService,
                                          TerminologyConceptDAO terminologyConceptDAO,
                                          PropertyChangeTrackerService propertyChangeTrackerService,
                                          HumanReadableRender humanReadableRender,
                                          NormalFormGenerator normalFormGenerator) {
        super("Expression Builder", Alignment.RIGHT, null);
        this.applicationService = applicationService;
        this.selectionService = selectionService;
        this.viewComponentService = viewComponentService;
        this.terminologySearchService = terminologySearchService;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.propertyChangeTrackerService = propertyChangeTrackerService;
        this.humanReadableRender = humanReadableRender;
        this.normalFormGenerator = normalFormGenerator;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent#init()
     */
    public synchronized void init() {

        logger.info("Starting module  : "+getName());
        // create expressionBuilderPanel  and register with services
        expressionBuilderPanel = new ExpressionBuilderPanel(terminologySearchService, applicationService,
                selectionService, terminologyConceptDAO,
                propertyChangeTrackerService, humanReadableRender, normalFormGenerator);
        // initialise and register expressionBuilderPanel  with services
        expressionBuilderPanel.initComponents();
        expressionBuilderPanel.register();
        // set as component
        setComponent(expressionBuilderPanel);
        // register self with viewComponentService
        viewComponentService.registerViewComponent(this);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent#dispose()
     */
    public synchronized void dispose() {
        // unregister expressionBuilderPanel
        expressionBuilderPanel.unRegister();
        // unregister self from viewComponentService
        viewComponentService.unregisterViewComponent(this);
        logger.info("Stopped module : "+getName());
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
     * Sets the selection service.
     *
     * @param selectionService the new selection service
     */
    public synchronized void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
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
     * Sets the terminology search service.
     *
     * @param terminologySearchService the new terminology search service
     */
    public synchronized void setTerminologySearchService(TerminologySearchService terminologySearchService) {
        this.terminologySearchService = terminologySearchService;
    }

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Sets the property change tracker service.
     *
     * @param propertyChangeTrackerService the new property change tracker service
     */
    public  synchronized void setPropertyChangeTrackerService(PropertyChangeTrackerService propertyChangeTrackerService) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }

    /**
     * Sets the human readable render.
     *
     * @param humanReadableRender the new human readable render
     */
    public synchronized void setHumanReadableRender(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Sets the normal form generator.
     *
     * @param normalFormGenerator the new normal form generator
     */
    public synchronized void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }
}
