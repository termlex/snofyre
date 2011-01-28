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

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * A class that handles all UI events related to downloading from TRUD.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 3, 2011 at 4:46:05 AM
 */
public class ProcessExecutor {

    private static Logger logger = Logger.getLogger(ProcessExecutor.class.getName());
    private final static String SEPARATOR = System.getProperty("file.separator");

    public ProcessExecutor() {
        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+
                    SEPARATOR+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    public void run(AbstractUIProcessHandler handler, String[] args) {

        FileDownloader fileDownloader = new FileDownloader();
        logger.info("args = " + args.length);
        // check there are exactly four arguments
        if(args.length == 8)
        {
            String packPath = args[0];
            logger.info("packPath = " + packPath);
            handler.logOutput("PackPath = " + packPath, true);
            String packName = args[1];
            logger.info("packName = " + packName);
            handler.logOutput("PackName = " + packName, true);
            String userName = args[2];
            logger.info("userName = " + userName);
            handler.logOutput("Skipping username = " + userName, true);
            String pwd = args[3];
            handler.logOutput("Skipping password...", true);
            String installType = args[5];
            logger.info("installType = " + installType);
            handler.logOutput("Install Type selected = " + installType, true);

            String installPath = args[6];
            logger.info("installPath = " + installPath);
            handler.logOutput("Install Path = " + installPath, true);
            String downloadURL = args[7];
            logger.info("downloadURL = " + downloadURL);
            handler.logOutput("Opening connection to "+downloadURL, true);

            TRUDLoginService trudLoginService = new TRUDLoginService();
            try
            {
                File outputDirectory = new File(installPath);

                if(outputDirectory.exists() && outputDirectory.isDirectory() && outputDirectory.canWrite())
                {
                    trudLoginService.authenticate(userName, pwd.toCharArray(), downloadURL);
                    logger.info("Finished authenticating to "+downloadURL);
                    handler.logOutput("Finished authenticating to "+downloadURL, true);

                    fileDownloader.setPackPath(packPath);
                    logger.info("fd.getPackPath() = " + fileDownloader.getPackPath());
                    // dont have to validate again, because we checked in previous installation step
                    fileDownloader.setFtpClient(trudLoginService.getFtpClient());
                    logger.info("Got ftp client from login service...");
                    handler.logOutput("Got ftp client from login service...", true);

                    // set ftp client in fileDownloader
                    fileDownloader.setFtpClient(trudLoginService.getFtpClient());

                    handler.logOutput("Getting Snofyre DATA", true);
                    fileDownloader.getFileFromTRUDArchive(packName, "snofyre-data.zip",
                            installPath);

                    if("full".equals(installType))
                    {
                        handler.logOutput("Getting SNOMED CT DATA", true);
                        fileDownloader.getFileFromTRUDArchive(packName, "snomed-data.zip",
                                installPath);
                    }

                    handler.logOutput("Finished downloading all packs from TRUD", true);
                }
                else
                {
                    handler.emitError("Error downloading data to "+installPath, "Directory might not exist or may be unwritable!");
                }
            }
            catch (Exception e) {
                logger.warning("Nested exception is : " + e.fillInStackTrace());
                handler.emitError("Error downloading data", "Nested exception is : " + e.fillInStackTrace());
            }
        }
        else
        {
            logger.warning("Illegal number of arguments passed. Expected = 7. Passed = "+args.length);
        }
    }

}
