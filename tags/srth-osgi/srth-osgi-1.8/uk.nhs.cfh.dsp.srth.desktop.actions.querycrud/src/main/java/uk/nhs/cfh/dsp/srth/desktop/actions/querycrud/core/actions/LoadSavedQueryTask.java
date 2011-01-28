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
/**
 *
 */
package uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Task;
import org.jdom.Document;
import uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.utils.XMLLoader;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import java.awt.*;
import java.io.File;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.application.Task} sub class that
 * generates a {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}
 * from a saved file.
 *
 * <br> Version : @#VersionNumber#@
 * <br> Written by @author Jay Kola
 * <br> Created on Jul 20, 2009 at 3:50:01 PM
 * <br> Modified on Saturday; November 28, 2009 at 12:50 PM
 */
public  class LoadSavedQueryTask extends Task<Void, Void>{

    /** The query service. */
    private QueryService queryService;

    /** The application service. */
    private ApplicationService applicationService;

    /** The query. */
    private QueryStatement query;

    /** The file selected. */
    private String fileSelected;

    /** The logger. */
    private static Log logger = LogFactory.getLog(LoadSavedQueryTask.class);
    
    /** The query expression xml converter. */
    private QueryExpressionXMLConverter queryExpressionXMLConverter;

    /**
     * Instantiates a new load saved query task.
     *
     * @param applicationService the application service
     * @param queryService the query service
     * @param queryExpressionXMLConverter the query expression xml converter
     */
    public LoadSavedQueryTask(ApplicationService applicationService, QueryService queryService,
                              QueryExpressionXMLConverter queryExpressionXMLConverter) {
        super(applicationService.getActiveApplication());
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.queryExpressionXMLConverter = queryExpressionXMLConverter;
        setDescription("A task that loads a query from a saved file");
        setTitle("Load Saved Query Task");
    }

    /**
     * Do in background.
     *
     * @return the query statement
     *
     * @throws Exception the exception
     */
    @Override
    protected Void doInBackground() throws Exception {

        // open file dialog
        FileDialog fd = new FileDialog(applicationService.getFrameView().getActiveFrame(),
                "Select file to load", FileDialog.LOAD);
        fd.setVisible(true);

        fileSelected = fd.getFile();
        String directorySelected = fd.getDirectory();
        if(fileSelected != null && directorySelected != null)
        {
            File physicalFile = new  File(directorySelected , fileSelected);
            // use file name load file from
            XMLLoader loader = new XMLLoader();
            Document doc = loader.getXMLDoc(physicalFile);

            query = queryExpressionXMLConverter.getQueryStatementFromElement(doc.getRootElement());
            if (query != null)
            {
                // assign physical location to query
                query.setPhysicalLocation(physicalFile.toURI());

                // register query and set to active query
                queryService.registerQuery(query);
                queryService.queryChanged(query, this);
            }
        }

        return null;
    }

    /**
     * Succeeded.
     *
     * @param v the v
     */
    @Override
    protected void succeeded(Void v) {
        if (query != null) {
            setMessage("Finished loading query : "+ query.getFileName());
        }
    }

    /**
     * Cancelled.
     */
    @Override
    protected void cancelled() {
        setMessage("User cancelled loading from saved query "+fileSelected);
    }

    /**
     * Failed.
     *
     * @param e the e
     */
    @Override
    protected void failed(Throwable e) {
        String simpleMessage ="Error loading query from file "+fileSelected+
                    " Check file exists and is readable.";
        setMessage(simpleMessage);
        applicationService.notifyError(simpleMessage, e, Level.WARNING);
        logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
    }
}