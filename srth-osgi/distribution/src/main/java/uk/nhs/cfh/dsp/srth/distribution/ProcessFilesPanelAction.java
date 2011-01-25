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
package uk.nhs.cfh.dsp.srth.distribution;

import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.installer.PanelAction;
import com.izforge.izpack.installer.PanelActionConfiguration;
import com.izforge.izpack.util.AbstractUIHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * A custom {@link com.izforge.izpack.installer.PanelAction} that processes files to set JDBC connection settings
 * after the {@link uk.nhs.cfh.dsp.srth.distribution.JDBCConnectionValidator} validates the connection.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 18, 2010 at 9:58:16 AM
 */
public class ProcessFilesPanelAction implements PanelAction{

    private AppPathFormatter formatter;
    private static Logger logger = Logger.getLogger(ProcessFilesPanelAction.class.getName());
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String SETTINGS_FOLDER_PATH = SEPARATOR+"bin"+SEPARATOR+"settings"+SEPARATOR;

    public ProcessFilesPanelAction() {
        formatter = new AppPathFormatter();
        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+
                    SEPARATOR+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    public void executeAction(AutomatedInstallData arg, AbstractUIHandler abstractUIHandler) {

        String portNum = arg.getVariable("sappheiros.db.port.number");
        String hostName = arg.getVariable("sappheiros.db.host.name");
        String schemaName = arg.getVariable("sappheiros.db.schema.name");
        String userName = arg.getVariable("sappheiros.db.user.name");
        char[] pwd = arg.getVariable("sappheiros.db.password").toCharArray();

        // save variables in a map to pass to AppPathFormatter
        Map<String, String> varMap = new HashMap<String, String>();
        varMap.put("sappheiros.db.port.number", portNum);
        varMap.put("sappheiros.db.host.name", hostName);
        varMap.put("sappheiros.db.schema.name", schemaName);
        varMap.put("sappheiros.db.user.name", userName);
        varMap.put("sappheiros.db.password", String.valueOf(pwd));

        String installPath = arg.getVariable("INSTALL_PATH");
        logger.info("varMap = " + varMap);
        logger.info("installPath = " + installPath);
        
        List<String> files = new ArrayList<String>();
        files.add(installPath+SETTINGS_FOLDER_PATH+"fakedata-db.properties");
        files.add(installPath+SETTINGS_FOLDER_PATH+"snomed-db.properties");
        files.add(installPath+SETTINGS_FOLDER_PATH+"query-engine.properties");
        files.add(installPath+SETTINGS_FOLDER_PATH+"aboutDialog.properties");

        // parse settings files
        formatter.setVarMap(varMap);
        for(String file : files)
        {
            File f = new File(file);
            logger.info("f.getAbsolutePath() = " + f.getAbsolutePath());
            if(f.exists())
            {
                formatter.processFile(f);
            }
        }

        logger.info("Finished processing files");        
    }

    public void initialize(PanelActionConfiguration panelActionConfiguration) {

    }
}
