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
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractViewComponent;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService;

// TODO: Auto-generated Javadoc
/**
 * A view component that displays the results of a query execution in a
 * table. It contains a {@link uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.QueryResultsPanel}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 30, 2009 at 11:29:09 AM
 */
public class QueryResultsViewComponent extends AbstractViewComponent{

    /** The view component service. */
    private ViewComponentService viewComponentService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The sql query engine service. */
    private SQLQueryEngineService sqlQueryEngineService;
    
    /** The query results panel. */
    private QueryResultsPanel queryResultsPanel;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryResultsViewComponent.class);
    
    /** The patient dao. */
    private PatientDAO patientDAO;
    

    /**
     * Instantiates a new query results view component.
     *
     * @param viewComponentService the view component service
     * @param applicationService the application service
     * @param queryService the query service
     * @param patientDAO the patient dao
     * @param sqlQueryEngineService the sql query engine service
     */
    public QueryResultsViewComponent(ViewComponentService viewComponentService, ApplicationService applicationService,
                                     QueryService queryService,
                                     PatientDAO patientDAO ,
                                     SQLQueryEngineService sqlQueryEngineService) {
        super("Results Panel", Alignment.CENTRE, null);
        this.viewComponentService = viewComponentService;
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.patientDAO = patientDAO;
        this.sqlQueryEngineService = sqlQueryEngineService;
    }

    /**
     * no args constructor for IOC.
     */
    public QueryResultsViewComponent() {
        super("Results Panel", Alignment.CENTRE, null);
    }

    /**
     * Inits the.
     */
    public synchronized void init() {

        logger.info("Starting module : "+getName());
        // create queryresultspanel
        queryResultsPanel = new QueryResultsPanel(queryService, sqlQueryEngineService,
                applicationService, patientDAO);
        queryResultsPanel.initComponents();
        // register queryResultsPanel with sqlQueryEngineService
        sqlQueryEngineService.addListener(queryResultsPanel);
        // set queryresultspanel as component
        setComponent(queryResultsPanel);
        // set view component to be not added as a tool
        setAddAsToolWindow(false);
        // register self with viewComponentService
        viewComponentService.registerViewComponent(this);
    }

    /**
     * Dispose.
     */
    public synchronized void dispose() {
        // unregister queryResultsPanel from sqlQueryEngineService
        sqlQueryEngineService.removeListener(queryResultsPanel);
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
     * Sets the application service.
     * 
     * @param applicationService the new application service
     */
    public synchronized void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
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
     * Sets the sql query engine service.
     * 
     * @param sqlQueryEngineService the new sql query engine service
     */
    public synchronized void setSqlQueryEngineService(SQLQueryEngineService sqlQueryEngineService) {
        this.sqlQueryEngineService = sqlQueryEngineService;
    }

    /**
     * Sets the patient dao.
     *
     * @param patientDAO the new patient dao
     */
    public synchronized void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }
}
