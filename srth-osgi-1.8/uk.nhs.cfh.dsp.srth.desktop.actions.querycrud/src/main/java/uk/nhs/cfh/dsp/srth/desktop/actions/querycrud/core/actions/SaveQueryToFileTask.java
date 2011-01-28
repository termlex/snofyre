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
package uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.query.converters.file.QueryExpressionFileOutputter;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import java.awt.*;
import java.io.File;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * A custom subclass of {@link org.jdesktop.application.Task} that handles saving a given
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement} to a {@link java.io.File}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 28, 2009 at 1:46:04 PM
 */
public class SaveQueryToFileTask extends Task<Boolean, Void> {

    /** The query service. */
    private QueryService queryService;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The file selected. */
    private String fileSelected;

    /** The query expression file outputter. */
    private QueryExpressionFileOutputter queryExpressionFileOutputter;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SaveQueryToFileTask.class);      

    /**
     * Instantiates a new save query to file task.
     * 
     * @param applicationService the application service
     * @param queryService the query service
     * @param queryExpressionFileOutputter the query expression file outputter
     */
    public SaveQueryToFileTask(ApplicationService applicationService, QueryService queryService,
                               QueryExpressionFileOutputter queryExpressionFileOutputter) {
        super(applicationService.getActiveApplication());
        this.applicationService =applicationService;
        this.queryService = queryService;
        this.queryExpressionFileOutputter = queryExpressionFileOutputter;
    }

    /**
     * Do in background.
     * 
     * @return the void
     * 
     * @throws Exception the exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {

        boolean result = false;
        QueryStatement query = queryService.getActiveQuery();
        // open file dialog to get save location
        FileDialog fd = new FileDialog(applicationService.getFrameView().getActiveFrame(),
                "Select location to save", FileDialog.SAVE);
        fd.setVisible(true);

        String selectedFile = fd.getFile();
        String selectedDirectory = fd.getDirectory();
        fileSelected = fd.getFile();
        if(selectedDirectory != null && selectedFile != null)
        {
            // save to selected location
            File physicalLocation = new File(selectedDirectory, selectedFile);
            query.setPhysicalLocation(physicalLocation.toURI());
            queryExpressionFileOutputter.save(query, physicalLocation.toURI());

            // notify query has changed
            queryService.queryChanged(query, this);
            // change result to true
            result = true;
        }

        return result;
    }

    /**
     * Succeeded.
     * 
     * @param arg0 the arg0
     */
    @Override
    protected void succeeded(Boolean arg0) {
        if (arg0) {
            setMessage("Finished saving query to location : "
                    +queryService.getActiveQuery().getPhysicalLocation());
        }
    }

    /**
     * Cancelled.
     */
    @Override
    protected void cancelled() {
        setMessage("User cancelled saving query "+fileSelected);
    }

    /**
     * Failed.
     * 
     * @param e the e
     */
    @Override
    protected void failed(Throwable e) {
        String simpleMessage ="Error saving query to file "+fileSelected+
                "Check file exists and is readable.";
        setMessage(simpleMessage);
        applicationService.notifyError(simpleMessage, e, Level.WARNING);
        logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
    }
}