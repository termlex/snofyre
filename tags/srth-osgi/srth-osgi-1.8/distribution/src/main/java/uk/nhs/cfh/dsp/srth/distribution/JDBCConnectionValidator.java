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
import com.izforge.izpack.installer.DataValidator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * A custom {@link com.izforge.izpack.installer.DataValidator} that validates connection to the
 * MySQL database configured by the user.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 15, 2010 at 5:02:41 PM
 */
 public class JDBCConnectionValidator implements DataValidator {

    private DBConnectionVerifier verifier;
    private AppPathFormatter formatter;
    private static Logger logger = Logger.getLogger(JDBCConnectionValidator.class.getName());
    private final String SEPARATOR = System.getProperty("file.separator");

    public JDBCConnectionValidator() {
        verifier = new DBConnectionVerifier();
        formatter = new AppPathFormatter();
        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+SEPARATOR+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    public boolean getDefaultAnswer() {
         return false;  
     }  
   
     public String getErrorMessageId() {
         return "Can not connect to MySQL using given parameters! \n" +
                 "Please verify that MySQL server is online and the parameters are correct.";  
     }  
   
     public String getWarningMessageId() {
         return "Error connecting to MySQL Server. ";
     }  
   
     public Status validateData(AutomatedInstallData arg) {

         String portNum = arg.getVariable("sappheiros.db.port.number");
         String hostName = arg.getVariable("sappheiros.db.host.name");
         String schemaName = arg.getVariable("sappheiros.db.schema.name");
         String userName = arg.getVariable("sappheiros.db.user.name");
         char[] pwd = arg.getVariable("sappheiros.db.password").toCharArray();
         verifier.setPortNumber(portNum);
         verifier.setHostName(hostName);
         verifier.setSchemaName(schemaName);
         boolean result = verifier.verifyConnection(userName, pwd);
         // save variables in a map to pass to AppPathFormatter
         Map<String, String> varMap = new HashMap<String, String>();
         varMap.put("sappheiros.db.port.number", portNum);
         varMap.put("sappheiros.db.host.name", hostName);
         varMap.put("sappheiros.db.schema.name", schemaName);
         varMap.put("sappheiros.db.user.name", userName);
         varMap.put("sappheiros.db.password", String.valueOf(pwd));

         String installPath = arg.getVariable("INSTALL_PATH");
         logger.info("installPath = " + installPath);
         logger.info("varMap = " + varMap);
         // replace spaces with %20 if needed
         List<String> files = new ArrayList<String>();
         files.add("bin"+SEPARATOR+"settings"+SEPARATOR+"fakedata-db.properties");
        files.add("bin"+SEPARATOR+"settings"+SEPARATOR+"snomed-db.properties");
        files.add("bin"+SEPARATOR+"settings"+SEPARATOR+"query-engine.properties");

         if(result)
         {
             // parse settings files
             formatter.setVarMap(varMap);
             for(String file : files)
             {
                 File f = new File(installPath, file);
                 if(f.exists())
                 {
                     formatter.processFile(f);
                 }
             }
             
             return Status.OK;
         }
         else
         {
             return  Status.ERROR;
         }   
     }
 }  
