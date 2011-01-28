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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel;

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
import uk.nhs.cfh.dsp.srth.query.converters.text.QueryExpressionPlainTextRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpressionFactory;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;

// TODO: Auto-generated Javadoc
/**
 * A {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent} that contains a
 * {@link uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.QueryAuthoringTreePanel}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 26, 2009 at 12:35:24 PM
 * <br> Modified on Saturday; December 5, 2009 at 10:28 AM to include a
 * {@link uk.nhs.cfh.dsp.yasb.search.TerminologySearchService}
 */
public class QueryAuthoringTreeViewComponent extends AbstractViewComponent{

    /** The view component service. */
    private ViewComponentService viewComponentService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryAuthoringTreeViewComponent.class);
    
    /** The query authoring tree panel. */
    private QueryAuthoringTreePanel queryAuthoringTreePanel;
    
    /** The query expression plain text renderer. */
    private QueryExpressionPlainTextRenderer queryExpressionPlainTextRenderer;
        
        /** The terminology search service. */
        private TerminologySearchService terminologySearchService;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;

    /** The query expression factory. */
    private QueryExpressionFactory queryExpressionFactory;

    /**
     * Instantiates a new query authoring tree view component.
     *
     * @param queryService the query service
     * @param viewComponentService the view component service
     * @param applicationService the application service
     * @param terminologySearchService the terminology search service
     * @param terminologyConceptDAO the terminology concept dao
     * @param selectionService the selection service
     * @param queryExpressionPlainTextRenderer the query expression plain text renderer
     * @param humanReadableRender the human readable render
     * @param propertyChangeTrackerService the property change tracker service
     * @param normalFormGenerator the normal form generator
     * @param queryExpressionFactory the query expression factory
     */
    public QueryAuthoringTreeViewComponent(QueryService queryService,
                                           ViewComponentService viewComponentService,
                                           ApplicationService applicationService,
                                           TerminologySearchService terminologySearchService,
                                           TerminologyConceptDAO terminologyConceptDAO,
                                           SelectionService selectionService,
                                           QueryExpressionPlainTextRenderer queryExpressionPlainTextRenderer,
                                           HumanReadableRender humanReadableRender,
                                           PropertyChangeTrackerService propertyChangeTrackerService,
                                           NormalFormGenerator normalFormGenerator,
                                           QueryExpressionFactory queryExpressionFactory) {
        super("Query Authoring", Alignment.LEFT, null);
        this.queryService = queryService;
        this.viewComponentService = viewComponentService;
        this.applicationService = applicationService;
        this.terminologySearchService = terminologySearchService;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.selectionService = selectionService;
        this.queryExpressionPlainTextRenderer = queryExpressionPlainTextRenderer;
        this.humanReadableRender = humanReadableRender;
        this.propertyChangeTrackerService =propertyChangeTrackerService;
        this.normalFormGenerator = normalFormGenerator;
        this.queryExpressionFactory = queryExpressionFactory;
    }

    /**
     * No args constructor for IOC.
     */
    public QueryAuthoringTreeViewComponent() {
        super("Query Authoring", Alignment.LEFT, null);
    }

    /**
     * Inits the.
     */
    public synchronized void init() {

        logger.info("Starting module : "+getClass().getName());
        queryAuthoringTreePanel = new QueryAuthoringTreePanel(applicationService,
                                        queryService, terminologySearchService, terminologyConceptDAO,
                                        selectionService, queryExpressionPlainTextRenderer ,
                propertyChangeTrackerService, humanReadableRender, normalFormGenerator, queryExpressionFactory);
        queryAuthoringTreePanel.initComponents();
        setComponent(queryAuthoringTreePanel);
        setActiveOnInitialise(true);
        // register querycreationtreepanel  with query service and selectionService
        queryService.addListener(queryAuthoringTreePanel);
        selectionService.registerListener(queryAuthoringTreePanel);
        // register self to viewComponentService
        viewComponentService.registerViewComponent(this);
    }

    /**
     * Dispose.
     */
    public synchronized void dispose() {
        // unregister from services
        queryService.removeListener(queryAuthoringTreePanel);
        selectionService.unregisterListener(queryAuthoringTreePanel);
        viewComponentService.unregisterViewComponent(this);
        logger.info("Stopped module : "+getClass().getName());
    }

    /**
     * Sets the query service.
     * 
     * @param queryService the new query service
     */
    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Sets the application service.
     * 
     * @param applicationService the new application service
     */
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Sets the terminology search service.
     * 
     * @param terminologySearchService the new terminology search service
     */
    public void setTerminologySearchService(TerminologySearchService terminologySearchService) {
        this.terminologySearchService = terminologySearchService;
    }

    /**
     * Sets the terminology concept dao.
     * 
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Sets the view component service.
     * 
     * @param viewComponentService the new view component service
     */
    public void setViewComponentService(ViewComponentService viewComponentService) {
        this.viewComponentService = viewComponentService;
    }

    /**
     * Sets the selection service.
     * 
     * @param selectionService the new selection service
     */
    public void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    /**
     * Sets the query expression plain text renderer.
     * 
     * @param queryExpressionPlainTextRenderer the new query expression plain text renderer
     */
    public void setQueryExpressionPlainTextRenderer(QueryExpressionPlainTextRenderer queryExpressionPlainTextRenderer) {
        this.queryExpressionPlainTextRenderer = queryExpressionPlainTextRenderer;
    }

    /**
     * Sets the human readable render.
     * 
     * @param humanReadableRender the new human readable render
     */
    public void setHumanReadableRender(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Sets the property change tracker service.
     * 
     * @param propertyChangeTrackerService the new property change tracker service
     */
    public void setPropertyChangeTrackerService(PropertyChangeTrackerService propertyChangeTrackerService) {
        this.propertyChangeTrackerService = propertyChangeTrackerService;
    }

    /**
     * Sets the normal form generator.
     * 
     * @param normalFormGenerator the new normal form generator
     */
    public void setNormalFormGenerator(NormalFormGenerator normalFormGenerator) {
        this.normalFormGenerator = normalFormGenerator;
    }

    /**
     * Sets the query expression factory.
     *
     * @param queryExpressionFactory the new query expression factory
     */
    public void setQueryExpressionFactory(QueryExpressionFactory queryExpressionFactory) {
        this.queryExpressionFactory = queryExpressionFactory;
    }
}
