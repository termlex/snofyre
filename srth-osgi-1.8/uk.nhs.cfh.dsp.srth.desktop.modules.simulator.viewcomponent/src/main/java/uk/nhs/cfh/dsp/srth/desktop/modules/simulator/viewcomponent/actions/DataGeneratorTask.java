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
package uk.nhs.cfh.dsp.srth.desktop.modules.simulator.viewcomponent.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;

import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.application.Task} for generating data using a
 * {@link uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 13, 2010 at 12:27:35 AM
 */
public class DataGeneratorTask extends Task<Void, Void> implements PropertyChangeTrackerServiceListener{

    /** The active query. */
    private QueryStatement activeQuery;
    
    /** The data generation engine. */
    private DataGenerationEngine dataGenerationEngine;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(DataGeneratorTask.class);
    
    /** The RECOR d_ create d_ property. */
    private static String RECORD_CREATED_PROPERTY = "RECORDS_CREATED";
    
    /** The total records. */
    private	double totalRecords = 0.0;
    
    /** The percent. */
    private double percent = 0.0;
    
    /** The completed. */
    private double completed = 0.0;

    /**
     * Instantiates a new data generator task.
     *
     * @param applicationService the application service
     * @param queryStatement the query statement
     * @param dataGenerationEngine the data generation engine
     */
    public DataGeneratorTask(ApplicationService applicationService, QueryStatement queryStatement,
                             DataGenerationEngine dataGenerationEngine) {
        super(applicationService.getActiveApplication());
        this.activeQuery = queryStatement;
        this.dataGenerationEngine = dataGenerationEngine;
        this.applicationService = applicationService;
        setDescription("A task for generating data from a given set of parameters");
        setTitle("Data Generation Task");
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
     */
    @Override
    protected Void doInBackground() throws Exception {

        // reset previous counters
        percent = 0.0;
        completed = 0.0;

        setMessage("Starting data generation. Please wait...");
        // stop engine in  case there is an existing generation cycle
        dataGenerationEngine.setStopEngine(true);
        // now restart engine
        dataGenerationEngine.setStopEngine(false);
        dataGenerationEngine.generateDataForQuery(activeQuery);
        return null;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.application.Task#succeeded(T)
     */
    @Override
    protected void succeeded(Void result) {
        setMessage("Successfully generated data for query.");
        setProgress(0);
    }

    @Override
    protected void cancelled() {
        dataGenerationEngine.setStopEngine(true);
        setMessage("User cancelled data generation");
    }

    /* (non-Javadoc)
    * @see org.jdesktop.application.Task#failed(java.lang.Throwable)
    */
    @Override
    protected void failed(Throwable e) {
        String simpleMessage = "Error creating data. Nested message is "+e.getMessage();
        setMessage(simpleMessage);
        applicationService.notifyError(simpleMessage, e, Level.WARNING);
        logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
        setProgress(0);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener#propertyChanged(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public void propertyChanged(String propertyName, Object oldValue, Object newValue, Object source) {
        if(RECORD_CREATED_PROPERTY.equals(propertyName) && dataGenerationEngine.isTrackProgress())
        {
                    // set total records
            totalRecords = dataGenerationEngine.getMaxPatientNumber();
            completed = Double.valueOf(newValue.toString());
            percent = (completed/totalRecords)*100;
            setProgress((int) percent);
            setMessage("Generated "+((int)completed)+" of "+((int)totalRecords)+" records ");
        }
    }
}
