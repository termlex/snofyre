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
import uk.nhs.cfh.dsp.srth.simulator.DataGenerationEngineFactory;

import javax.swing.*;
import java.util.logging.Level;

/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 4, 2010 at 3:32:57 PM
 */
class RecreateDatabaseTask extends Task<Void, Void> {

    private DataGenerationEngineFactory dataGenerationEngineFactory;
    private ApplicationService applicationService;
    private static Log logger = LogFactory.getLog(RecreateDatabaseTask.class);

    public RecreateDatabaseTask(DataGenerationEngineFactory dataGenerationEngineFactory,
                                ApplicationService applicationService) {
        super(applicationService.getActiveApplication());
        this.applicationService = applicationService;
        this.dataGenerationEngineFactory = dataGenerationEngineFactory;
    }

    @Override
    protected Void doInBackground() throws Exception {

//        dataGenerationEngineFactory.recreateDatabase();
        JOptionPane.showMessageDialog(null, "This feature is not currently supported!");
        return null;
    }

    @Override
    protected void succeeded(Void arg0) {
        if(!isCancelled())
        {
            setMessage("Successfully recreated database");
        }
    }

    /* (non-Javadoc)
    * @see org.jdesktop.application.Task#failed(java.lang.Throwable)
    */
    @Override
    protected void failed(Throwable e) {
        String simpleMessage = "Error recreating database."+e.getMessage();
        setMessage(simpleMessage);
        applicationService.notifyError(simpleMessage, e, Level.WARNING);
        logger.warn(simpleMessage+"Nested exception is : " + e.fillInStackTrace().getMessage());
    }
}