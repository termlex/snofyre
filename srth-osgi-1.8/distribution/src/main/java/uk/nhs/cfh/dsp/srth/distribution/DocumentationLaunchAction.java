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
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * A custom {@link com.izforge.izpack.installer.PanelAction} that launches documentation after installation
 * and downloading the relevant docs
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 24, 2011 at 10:26:58 AM
 */
public class DocumentationLaunchAction implements PanelAction {

    private DocumentationLauncher documentationLauncher;
    private static Logger logger = Logger.getLogger(DocumentationLaunchAction.class.getName());
    private static final String SEPARATOR = System.getProperty("file.separator");

    public DocumentationLaunchAction() {
        documentationLauncher = new DocumentationLauncher();
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

        String installPath = arg.getVariable("INSTALL_PATH");
        logger.info("installPath = " + installPath);
        String pdfDocUrl = arg.getVariable("pdf.doc.url");
        File f = new File(installPath, pdfDocUrl);
        logger.info("pdf file getAbsolutePath() = " + f.getAbsolutePath());
        if(f.exists())
        {
            documentationLauncher.launchDocumentation(f);
        }

        logger.info("Finished launching "+pdfDocUrl);
    }

    public void initialize(PanelActionConfiguration panelActionConfiguration) {

    }
}
