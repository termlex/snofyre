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
package uk.nhs.cfh.dsp.srth.desktop.modules.resultexplanationpanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractViewComponent;
import uk.nhs.cfh.dsp.srth.query.converters.html.QueryExpressionHTMLRenderer;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService;

// TODO: Auto-generated Javadoc
/**
 * A view component that displays explanations for the results of an execution of a.
 *
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}. It usses a
 * {@link uk.nhs.cfh.dsp.srth.desktop.modules.resultexplanationpanel.ResultsExplanationPanel} for its component.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Dec 1, 2009 at 10:05:50 AM
 */
public class ResultExplanationViewComponent extends AbstractViewComponent{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The sql query engine service. */
    private SQLQueryEngineService sqlQueryEngineService;
    
    /** The view component service. */
    private ViewComponentService viewComponentService;
    
    /** The results explanation panel. */
    private ResultsExplanationPanel resultsExplanationPanel;
    
    /** The query expression html renderer. */
    private QueryExpressionHTMLRenderer queryExpressionHTMLRenderer;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ResultExplanationViewComponent.class);

    /**
     * Instantiates a new result explanation view component.
     * 
     * @param applicationService the application service
     * @param queryService the query service
     * @param sqlQueryEngineService the sql query engine service
     * @param viewComponentService the view component service
     * @param queryExpressionHTMLRenderer the query expression html renderer
     */
    public ResultExplanationViewComponent(ApplicationService applicationService,
                                          QueryService queryService,
                                          SQLQueryEngineService sqlQueryEngineService,
                                          ViewComponentService viewComponentService,
                                          QueryExpressionHTMLRenderer queryExpressionHTMLRenderer) {
        super("Results Explanation", Alignment.BOTTOM, null);
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.sqlQueryEngineService = sqlQueryEngineService;
        this.viewComponentService = viewComponentService;
        this.queryExpressionHTMLRenderer = queryExpressionHTMLRenderer;
    }

    /**
     * empty no args constructor for IOC.
     */
    public ResultExplanationViewComponent() {
        super("Results Explanation", Alignment.BOTTOM, null);
    }

    /**
     * Inits the.
     */
    public synchronized void init() {

        logger.info("Starting module : "+getName());
        // create resultsExplanationPanel
        resultsExplanationPanel = new ResultsExplanationPanel(applicationService, queryService,
                sqlQueryEngineService, queryExpressionHTMLRenderer);
        resultsExplanationPanel.initComponents();
        // register resultsExplanationPanel with services
        resultsExplanationPanel.init();
        // set resultsExplanationPanel  as component
        setComponent(resultsExplanationPanel);
        // set show as tool
        setAddAsToolWindow(true);

        // regiser self with viewComponentService
        viewComponentService.registerViewComponent(this);
    }

    /**
     * Dispose.
     */
    public synchronized void dispose() {
        // dispose resultsExplanationPanel which unregisters it from sqlQueryEngineService
        resultsExplanationPanel.dispose();
        // unregister from viewComponentService
        viewComponentService.unregisterViewComponent(this);
        logger.info("Stopped module : "+getName());
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
     * Sets the query service.
     * 
     * @param queryService the new query service
     */
    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Sets the sql query engine service.
     * 
     * @param sqlQueryEngineService the new sql query engine service
     */
    public void setSqlQueryEngineService(SQLQueryEngineService sqlQueryEngineService) {
        this.sqlQueryEngineService = sqlQueryEngineService;
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
     * Sets the query expression html renderer.
     * 
     * @param queryExpressionHTMLRenderer the new query expression html renderer
     */
    public void setQueryExpressionHTMLRenderer(QueryExpressionHTMLRenderer queryExpressionHTMLRenderer) {
        this.queryExpressionHTMLRenderer = queryExpressionHTMLRenderer;
    }
}
