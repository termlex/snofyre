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

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * A custom {@link com.izforge.izpack.installer.DataValidator} that validates connection to the
 * TRUD for the NHSSAPPHEIROSDATA pack.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 15, 2010 at 5:02:41 PM
 */
 public class TRUDConnectionValidator implements DataValidator {

    private TRUDLoginService trudLoginService;
    private static Logger logger = Logger.getLogger(TRUDConnectionValidator.class.getName());
    private final static String SEPARATOR = System.getProperty("file.separator");

    public TRUDConnectionValidator() {
        trudLoginService = new TRUDLoginService();
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
         return "Can not connect to TRUD using given parameters! \n" +
                 "Please verify that you are online and the parameters are correct.";  
     }  
   
     public String getWarningMessageId() {
         return "Error connecting to TRUD. ";
     }  
   
     public Status validateData(AutomatedInstallData arg) {

         String userName = arg.getVariable("trud.user.name");
         String serverURL = arg.getVariable("trud.url");
         char[] pwd = arg.getVariable("trud.password").toCharArray();

         boolean result = false;
         try
         {
             result = trudLoginService.authenticate(userName, pwd, serverURL);
         }
         catch (Exception e) {
             logger.warning("Nested exception is : " + e.fillInStackTrace());
         }

         String installPath = arg.getVariable("INSTALL_PATH");
         logger.info("installPath = " + installPath);

         if(result)
         {            
             return Status.OK;
         }
         else
         {
             return  Status.ERROR;
         }   
     }
 }  
