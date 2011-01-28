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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryrenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractViewComponent;
import uk.nhs.cfh.dsp.srth.query.converters.html.QueryExpressionHTMLRenderer;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

// TODO: Auto-generated Javadoc
/**
 * A view component that displays a human readable text rendering of a.
 *
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl} using HTML. It contains a
 * {@link uk.nhs.cfh.dsp.srth.desktop.modules.queryrenderer.QueryHTMLRendererPanel} for its component.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Dec 2, 2009 at 2:55:29 PM
 */
public class QueryHTMLRendererViewComponent extends AbstractViewComponent{

    /** The query service. */
    private QueryService queryService;
    
    /** The view component service. */
    private ViewComponentService viewComponentService;
    
    /** The query html renderer panel. */
    private QueryHTMLRendererPanel queryHTMLRendererPanel;
    
    /** The query expression html renderer. */
    private QueryExpressionHTMLRenderer queryExpressionHTMLRenderer;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryHTMLRendererViewComponent.class);

    /**
     * Instantiates a new query html renderer view component.
     * 
     * @param queryService the query service
     * @param viewComponentService the view component service
     * @param queryExpressionHTMLRenderer the query expression html renderer
     */
    public QueryHTMLRendererViewComponent(QueryService queryService, ViewComponentService viewComponentService,
                                          QueryExpressionHTMLRenderer queryExpressionHTMLRenderer) {
        super("Query Expression", Alignment.TOP, null);
        this.queryService = queryService;
        this.viewComponentService = viewComponentService;
        this.queryExpressionHTMLRenderer = queryExpressionHTMLRenderer;
    }

    /**
     * No args constructor for IOC.
     */
    public QueryHTMLRendererViewComponent() {
        super("Query Expression", Alignment.TOP, null);
    }

    /**
     * Inits the.
     */
    public synchronized void init() {

        logger.info("Starting module  : "+getName());
        queryHTMLRendererPanel = new QueryHTMLRendererPanel(queryExpressionHTMLRenderer);
        // set queryHTMLRendererPanel to component
        setComponent(queryHTMLRendererPanel);
        // register queryHTMLRendererPanel  with queryservice
        queryService.addListener(queryHTMLRendererPanel);
        // register self with viewComponentService
        viewComponentService.registerViewComponent(this);
    }

    /**
     * Dispose.
     */
    public synchronized void dispose() {
        // unregister from services
        queryService.removeListener(queryHTMLRendererPanel);
        viewComponentService.unregisterViewComponent(this);
        logger.info("Shutdown module : "+getName());
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
     * Sets the view component service.
     * 
     * @param viewComponentService the new view component service
     */
    public void setViewComponentService(ViewComponentService viewComponentService) {
        this.viewComponentService = viewComponentService;
    }

    /**
     * Sets the query html renderer panel.
     * 
     * @param queryHTMLRendererPanel the new query html renderer panel
     */
    public void setQueryHTMLRendererPanel(QueryHTMLRendererPanel queryHTMLRendererPanel) {
        this.queryHTMLRendererPanel = queryHTMLRendererPanel;
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
