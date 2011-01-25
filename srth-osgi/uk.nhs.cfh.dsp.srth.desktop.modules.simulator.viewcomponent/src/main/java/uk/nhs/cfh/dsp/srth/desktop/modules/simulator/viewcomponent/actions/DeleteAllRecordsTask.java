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
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;

import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * A {@link org.jdesktop.application.Task} that drops all existing records from the database.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 09-Mar-2010 at 16:25:50
 */
public class DeleteAllRecordsTask extends Task<Void, Void>{

    /** The patient dao. */
    private PatientDAO patientDAO;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(DeleteAllRecordsTask.class);

    /**
     * Instantiates a new delete all records task.
     *
     * @param patientDAO the patient dao
     * @param applicationService the application service
     */
    public DeleteAllRecordsTask(PatientDAO patientDAO, ApplicationService applicationService) {
        super(applicationService.getActiveApplication());
        this.patientDAO = patientDAO;
        this.applicationService = applicationService;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
     */
    @Override
    protected Void doInBackground() throws Exception {
        patientDAO.deleteAllRecords();
        return null;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.application.Task#succeeded(T)
     */
    @Override
    protected void succeeded(Void result) {
        setMessage("Successfully deleted all patient records from database");
    }

    /* (non-Javadoc)
     * @see org.jdesktop.application.Task#failed(java.lang.Throwable)
     */
    @Override
    protected void failed(Throwable e) {
        String simpleMessage = "Error deleting records from database."+e.getMessage();
        setMessage(simpleMessage);
        applicationService.notifyError(simpleMessage, e, Level.WARNING);
        logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
    }
}
