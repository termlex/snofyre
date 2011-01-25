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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

/**
 * A custom {@link org.jdesktop.application.Task} to export results of a query.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 19, 2010 at 11:42:51 PM
 */
public class ExportResultTask extends Task<Void, Void> {

    private static Log logger = LogFactory.getLog(ExportResultTask.class);
    private ApplicationService applicationService;
    private ResultSet resultSet;
    private int resultSetCount = 0;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public ExportResultTask(ApplicationService applicationService, ResultSet resultSet, int resultSetCount) {
        super(applicationService.getActiveApplication());
        this.applicationService = applicationService;
        this.resultSet = resultSet;
        this.resultSetCount = resultSetCount;
    }

    @Override
    protected Void doInBackground() throws Exception {

        // open file dialog to get save location
        FileDialog fd = new FileDialog(applicationService.getFrameView().getActiveFrame(),
                "Select location to save", FileDialog.SAVE);
        fd.setVisible(true);

        String selectedFile = fd.getFile();
        String selectedDirectory = fd.getDirectory();
        if(selectedDirectory != null && selectedFile != null && resultSet != null)
        {
            try
            {
                // save to selected location
                File file = new File(selectedDirectory, selectedFile);
                // export to flat file
                FileWriter fw = new FileWriter(file);
                fw.flush();
                
                // add column names to data
                String cols = "";
                int colNum = resultSet.getMetaData().getColumnCount();
                for(int c=0; c <colNum; c++)
                {
                    cols = cols+"\t"+resultSet.getMetaData().getColumnName(c+1);
                }

                // trim line and add new line character
                cols = cols.trim();
                cols = cols+"\n";
                // write to file
                fw.write(cols);

                float totalRows = resultSetCount;
                float percent = 0;
                int counter = 0;

                // reset resultSet to first row in case its been iterated over before
                resultSet.beforeFirst();
                while(resultSet.next())
                {
                    String line = "";
                    for(int l=0; l<colNum; l++)
                    {
                        Object o = resultSet.getObject(l+1);
                        if(o instanceof Date)
                        {
                            o = sdf.format((Date)o);
                        }
                        else if(o instanceof byte[])
                        {
                            byte[] bytes = (byte[]) o;
                            o = UUID.nameUUIDFromBytes(bytes).toString();
                        }
                        
                        line = line+"\t"+o.toString();
                    }

                    // trim line
                    line = line.trim();
                    // append new line character to line
                    line = line+"\n";
                    // write to file
                    fw.write(line);

                    // update progress bar
                    percent = (counter/totalRows)*100;
                    setProgress((int)percent);
                    setMessage("Exported "+counter+" of "+totalRows+" rows");

                    counter++;
                }

                // close file writer
                fw.close();

            } catch (IOException e) {
                logger.warn("Nested exception is : " + e.fillInStackTrace());
            }
        }

        return null;
    }

    @Override
    protected void succeeded(Void arg0) {
        setMessage("Finished exporting results");
    }

    /* (non-Javadoc)
    * @see org.jdesktop.application.Task#failed(java.lang.Throwable)
    */
    @Override
    protected void failed(Throwable e) {
        String simpleMessage = "Error exporting results. "+e.getMessage();
        setMessage(simpleMessage);
        applicationService.notifyError(simpleMessage, e, Level.WARNING);
        logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
    }
}
