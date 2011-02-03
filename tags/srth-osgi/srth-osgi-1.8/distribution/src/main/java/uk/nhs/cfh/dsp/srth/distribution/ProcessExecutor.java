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
    private final static String TEMP_FOLDER = System.getProperty("java.io.tmpdir");

    public ProcessExecutor() {
        try
        {
            logger.addHandler(new FileHandler(TEMP_FOLDER+
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
            String userName = args[2].trim();
            logger.info("userName = " + userName);
            handler.logOutput("Using username = " + userName, true);
            String pwd = args[3].trim();
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

//            TRUDLoginService trudLoginService = new TRUDLoginService();
            try
            {
                File outputDirectory = new File(installPath);

                if(outputDirectory.exists() && outputDirectory.isDirectory() && outputDirectory.canWrite())
                {
//                    trudLoginService.authenticate(userName, pwd.toCharArray(), downloadURL);
//                    logger.info("Finished authenticating to "+downloadURL);
//                    handler.logOutput("Finished authenticating to "+downloadURL, true);
//
//                    fileDownloader.setPackPath(packPath);
//                    logger.info("fd.getPackPath() = " + fileDownloader.getPackPath());
//                    // dont have to validate again, because we checked in previous installation step
//                    fileDownloader.setFtpClient(trudLoginService.getFtpClient());
//                    logger.info("Got ftp client from login service...");
//                    handler.logOutput("Got ftp client from login service...", true);
//
//                    // set ftp client in fileDownloader
//                    fileDownloader.setFtpClient(trudLoginService.getFtpClient());
//
//                    handler.logOutput("Getting "+packName, true);
////                    fileDownloader.getFileFromTRUD(packName, installPath+SEPARATOR+packName);
//                    String downloadedPackPath = installPath+SEPARATOR+packName;
//                    fileDownloader.getFileFromTRUD(packName, downloadedPackPath);
                    handler.logOutput("Finished downloading all packs from TRUD", true);

                    // check downloaded pack exists
                    String downloadedPackPath = installPath+SEPARATOR+packName;
                    handler.logOutput("Verifying downloaded pack and starting extraction", true);                    
                    File downloadedPack = new File(downloadedPackPath);
                    if(downloadedPack.exists() && downloadedPack.canRead())
                    {
                        // extract pack into temp folder
                        ZipArchiveUtils.extractZipFileContents(downloadedPack);
//                        fileDownloader.extractZipFileContents(downloadedPack);

                        // extract snofyre data to installation folder
                        handler.logOutput("Extracting SNOFYRE Pack", true);
//                        fileDownloader.extractZipFileContents(new File(TEMP_FOLDER, "snofyre-data.zip"), installPath);
//                        if("full".equals(installType))
//                        {
//                            handler.logOutput("Extracting SNOMED CT DATA", true);
//                            fileDownloader.extractZipFileContents(new File(TEMP_FOLDER, "snomed-data.zip"), installPath);
//                        }
//                        fileDownloader.extractZipFileContents(downloadedPack);
                        ZipArchiveUtils.extractZipFileContents(downloadedPack);

                        File snofyrePack = new File(installPath, "snofyre-data.zip");
                        if(snofyrePack.exists() && snofyrePack.canRead())
                        {
                            handler.logOutput("Extracting SNOFYRE CT DATA", true);
//                            fileDownloader.extractZipFileContents(snofyrePack);
                            ZipArchiveUtils.extractZipFileContents(snofyrePack);
                        }

                        if("full".equals(installType))
                        {
                            File snomedPack = new File(installPath, "snomed-data.zip");
                            if(snomedPack.exists() && snomedPack.canRead())
                            {
                                handler.logOutput("Extracting SNOMED CT DATA", true);                                
//                                fileDownloader.extractZipFileContents(snomedPack);
                                ZipArchiveUtils.extractZipFileContents(snomedPack);
                            }
                        }
                        
                        handler.logOutput("Finished downloading all packs from TRUD", true);
                    }
                    else
                    {
                        handler.logOutput("Error unpacking downloaded pack. Pack has not been downloaded or is corrupt!", false);
                    }
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
