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
 * A custom {@link com.izforge.izpack.installer.DataValidator} that nags the user to confirm that
 * they followed all the steps specified in the configuration manual.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 24, 2010 at 9:49:40 PM
 */
public class ConfigurationCheckListValidator implements DataValidator {

    private static Logger logger = Logger.getLogger(ConfigurationCheckListValidator.class.getName());
    private static final String VALID_CHECK_RESPONSE = "yes";

    public ConfigurationCheckListValidator() {
        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+
                    System.getProperty("file.separator")+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    public Status validateData(AutomatedInstallData automatedInstallData) {

        // get values from the nag screen
        String serverInstallCheck = automatedInstallData.getVariable("mysql.server.installed.check");
        String userAccountCheck = automatedInstallData.getVariable("mysql.server.user.account.check");
        String snomedImportCheck = automatedInstallData.getVariable("mysql.snomed.data.import.check");
        String sappheirosImportCheck = automatedInstallData.getVariable("mysql.sappheiros.data.import.check");
        String snomedPermissionsCheck = automatedInstallData.getVariable("mysql.server.snomed.db.permissions.check");
        String sappheirosPermissionsCheck = automatedInstallData.getVariable("mysql.server.sappheiros.db.permissions.check");

        if(VALID_CHECK_RESPONSE.equalsIgnoreCase(serverInstallCheck)
                && VALID_CHECK_RESPONSE.equalsIgnoreCase(userAccountCheck)
                && VALID_CHECK_RESPONSE.equalsIgnoreCase(snomedImportCheck)
                && VALID_CHECK_RESPONSE.equalsIgnoreCase(sappheirosImportCheck)
                && VALID_CHECK_RESPONSE.equalsIgnoreCase(snomedPermissionsCheck)
                && VALID_CHECK_RESPONSE.equalsIgnoreCase(sappheirosPermissionsCheck))
        {
            return Status.OK;
        }
        else
        {
            return Status.ERROR;
        }
    }

    public boolean getDefaultAnswer() {
         return true;
     }

     public String getErrorMessageId() {
         return "Please confirm that you have followed all the steps in the \n" +
                 "Configuration Manual by checking all the boxes.";
     }

     public String getWarningMessageId() {
         return "Can not progress to next screen, without checking all boxes. ";
     }
}
