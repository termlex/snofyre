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
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractViewComponent;
import uk.nhs.cfh.dsp.srth.query.transform.SubtypeGetter;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent} that displays the various
 * optimisations that can be applied to a {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Aug 9, 2010 at 6:55:22 PM
 */
public class QueryOptimisationsViewComponent extends AbstractViewComponent {

    /** The view component service. */
    private ViewComponentService viewComponentService;
    
    /** The subtype getter. */
    private SubtypeGetter subtypeGetter;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryOptimisationsViewComponent.class);

    /**
     * Instantiates a new query optimisations view component.
     *
     * @param viewComponentService the view component service
     * @param subtypeGetter the subtype getter
     */
    public QueryOptimisationsViewComponent(ViewComponentService viewComponentService, SubtypeGetter subtypeGetter) {
        super("Query Execution Optimisations", Alignment.LEFT, null);
        this.viewComponentService = viewComponentService;
        this.subtypeGetter = subtypeGetter;
    }

    /**
     * Empty constructor for IOC.
     */
    public QueryOptimisationsViewComponent() {
        super("Query Execution Optimisations", Alignment.LEFT, null);
    }

        /**
     * Inits the.
     */
    public synchronized void init() {

        logger.info("Starting module : "+getName());
        QueryOptimisationsPanel queryOptimisationsPanel = new QueryOptimisationsPanel(subtypeGetter);
        queryOptimisationsPanel.initComponents();

        // set queryOptimisationsPanel as component
        setComponent(queryOptimisationsPanel);
        // set view component to be not added as a tool
        setAddAsToolWindow(false);
        // register self with viewComponentService
        viewComponentService.registerViewComponent(this);
    }

    /**
     * Dispose.
     */
    public synchronized void dispose() {
        // unregister from viewComponentService
        viewComponentService.unregisterViewComponent(this);
        logger.info("Stopped module : "+getName());
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
     * Sets the subtype getter.
     *
     * @param subtypeGetter the new subtype getter
     */
    public synchronized void setSubtypeGetter(SubtypeGetter subtypeGetter) {
        this.subtypeGetter = subtypeGetter;
    }
}
