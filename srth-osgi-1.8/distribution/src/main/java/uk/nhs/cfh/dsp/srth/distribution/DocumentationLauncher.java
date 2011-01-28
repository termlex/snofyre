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

import com.izforge.izpack.util.AbstractUIProcessHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * A custom class that launches the documentation for Sappheiros.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 22, 2010 at 3:04:42 PM
 */
public class DocumentationLauncher {

    private static Logger logger = Logger.getLogger(DocumentationLauncher.class.getName());

    public DocumentationLauncher() {

        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+
                    System.getProperty("file.separator")+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    public void launchDocumentation(File file){

        if(Desktop.isDesktopSupported())
        {
            Desktop desktop = Desktop.getDesktop();
            // open the file
            try
            {
                if (file.exists() && file.canRead()) {
                    desktop.open(file);
                }
            }
            catch (IOException e) {
                logger.warning("Error launching document. Nested exception is : " + e.fillInStackTrace().getMessage());
            }
        }
    }

    public void run(AbstractUIProcessHandler handler, String[] args) {

        DocumentationLauncher documentationLauncher = new DocumentationLauncher();
        logger.info("args = " + args.length);
        // check there are exactly four arguments
        if(args.length == 2)
        {
            String pdfDocUrl = args[0];
            String installPath = args[1];
            File f = new File(installPath, pdfDocUrl);
            logger.info("pdf file getAbsolutePath() = " + f.getAbsolutePath());
            if(f.exists())
            {
                documentationLauncher.launchDocumentation(f);
            }

            logger.info("Finished launching "+pdfDocUrl);
        }
    }

    public static void main(String[] args) {

        logger.info("Displaying documentation.. please wait");
        DocumentationLauncher launcher = new DocumentationLauncher();
        // iterate through arguments and launch all files
        for(String arg : args)
        {
            logger.info("arg = " + arg);
            File f = new File(arg);
            if(f.exists() && f.canRead())
            {
                launcher.launchDocumentation(f);
            }
        }

        logger.info("Finished displaying documentation");
    }
}
