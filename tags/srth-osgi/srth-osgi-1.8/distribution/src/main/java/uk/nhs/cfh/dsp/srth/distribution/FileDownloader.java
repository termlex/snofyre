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

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.jdesktop.swingx.JXLoginPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * A custom class for downloading resources from a given {@link java.net.URL}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 2, 2011 at 5:10:01 PM
 */
public class FileDownloader {

    private static Logger logger = Logger.getLogger(FileDownloader.class.getName());
    private String packPath;
    private static final String SEPARATOR = System.getProperty("file.separator");
    private FTPClient ftpClient;
    private final static int BUFFER = 1024;
    private static final String ERR_MESSAGE = "Nested exception message is : ";

    public FileDownloader() {
        this(new FTPClient());
    }

    public FileDownloader(FTPClient ftpClient) {

        this.ftpClient = ftpClient;
        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+
                    SEPARATOR+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning(ERR_MESSAGE+ e.fillInStackTrace().getMessage());
        }
    }

    public void getFileFromStream(InputStream inputStream, String outputURL){

        try
        {
            FileOutputStream fos = new FileOutputStream(outputURL);
            logger.info("fos = " + fos);
            fos.flush();

            BufferedInputStream in = new BufferedInputStream(inputStream);
            BufferedOutputStream bout = new BufferedOutputStream(fos,BUFFER);
            bout.flush();

            int i = 0;
            byte[] data = new byte[BUFFER];
            // write data
            while((i = in.read(data,0,BUFFER)) >=0)
            {
                bout.write(data, 0, i);
            }

            // close streams
            bout.close();
            in.close();
            fos.close();

            // log message
            logger.info("Successfully downloaded file to : "+outputURL);
        }
        catch (FileNotFoundException e) {
            logger.warning(ERR_MESSAGE+ e.fillInStackTrace());
        }
        catch (IOException e) {
            logger.warning(ERR_MESSAGE+ e.fillInStackTrace());
        }
    }

    public void getFileFromURL(URL inputURL, String outputURL) throws IOException {
        getFileFromStream(inputURL.openStream(), outputURL);
    }

    public void getFileFromURL(String inputURL, String outputURL) throws IOException {
        getFileFromURL(new URL(inputURL), outputURL);
    }

    public void getFileFromTRUDArchive(String trudArchiveName, String fileName, String outputURL){

        logger.info("Now getting file... ");
        if (ftpClient.isConnected())
        {
            logger.info("ftp client is connected... ");
            // now get file specified by inputURL
            try
            {
                ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                logger.info("Sending NOOP command... ");
                // send NOOP command to see if connection is still active
                if(ftpClient.sendNoOp())
                {
                    logger.info("ftpClient.getReplyString() = " + ftpClient.getReplyString());
                    FTPFile[] files = ftpClient.listFiles(getPackPath());
                    for(FTPFile file : files)
                    {
                        logger.info("file.getName() = " + file.getName());
                        logger.info("file.getSize() = " + file.getSize());
                        if(file.getName().equals(trudArchiveName) && file.getSize() > 0)
                        {
                            InputStream is = ftpClient.retrieveFileStream(getPackPath() +trudArchiveName);

                            if (is != null)
                            {
                                ZipArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(is);
                                ZipArchiveEntry zipArchiveEntry = zipArchiveInputStream.getNextZipEntry();
                                while(zipArchiveEntry != null)
                                {
                                    String zippedArchiveEntryName = zipArchiveEntry.getName();
                                    logger.info("zippedArchiveEntryName = " + zippedArchiveEntryName);
                                    logger.info("fileName = " + fileName);
                                    if(zippedArchiveEntryName.equals(fileName))
                                    {
                                        logger.info("Extracting: " +zippedArchiveEntryName);
                                        File outputLocation = new File(outputURL);
                                        boolean canWrite = outputLocation.exists();
                                        logger.info("canWrite = " + canWrite);
                                        if(! canWrite)
                                        {
                                            canWrite = outputLocation.mkdirs();
                                            logger.info("canWrite after mkdirs = " + canWrite);
                                        }

                                        if (canWrite && outputLocation.canWrite())
                                        {
                                            logger.info("outputLocation.getPath() = " + outputLocation.getPath());
                                            File destinationFile = new File(outputLocation.getAbsolutePath(), zippedArchiveEntryName);
                                            OutputStream out = new FileOutputStream(destinationFile);
                                            IOUtils.copy(zipArchiveInputStream, out);
                                            out.close();

                                            if (zippedArchiveEntryName.indexOf(".zip") > -1)
                                            {
                                                // unpackZip file
                                                extractZipFileContents(destinationFile);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        logger.info("Resetting zipArchiveEntry");
                                        zipArchiveEntry = zipArchiveInputStream.getNextZipEntry();
                                        if (zipArchiveEntry != null) {
                                            logger.info("zipArchiveEntry.getName() = " + zipArchiveEntry.getName());
                                        }
                                    }

                                }

                                zipArchiveInputStream.close();
                                is.close();
                            }
                            break;
                        }
                    }
                    
                    // complete pending commands; needed after opening and closing streams
                    ftpClient.completePendingCommand();
                }
                else
                {
                    logger.warning("FTP connection might have timed out.");
                    ftpClient.disconnect();
                }
            }
            catch (IOException e) {
                logger.warning("FTP connection might have timed out. "+ERR_MESSAGE + e.fillInStackTrace());
            }
        }
        else
        {
            logger.warning("No connection to TRUD is available. Ensure FTP connection is open");
        }
    }

    private void copyInputStream(InputStream in, OutputStream out)
            throws IOException
    {
        byte[] buffer = new byte[1024];
        int len;

        while((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);

        in.close();
        out.close();
        logger.info("Finished copying inputstream.");
    }

    private void extractZipFileContents(File file) {
        Enumeration entries;

        try {
            logger.info("Extracting zip file contents...");
            ZipFile zipFile = new ZipFile(file);

            entries = zipFile.getEntries();

            while(entries.hasMoreElements()) {
                ZipArchiveEntry entry = (ZipArchiveEntry)entries.nextElement();

                if(entry.isDirectory()) {

                    logger.info("Extracting directory: " + entry.getName());
                    File dir = new File(file.getParent(), entry.getName());
                    boolean canWrite = dir.exists();
                    if(! canWrite)
                    {
                        canWrite = dir.mkdir();
                    }

                    if(canWrite){
                        continue;
                    }
                    else{
                        logger.warning("Error creating directory during extraction");
                    }
                }

                logger.info("Extracting file: " + entry.getName());
                copyInputStream(zipFile.getInputStream(entry),
                        new BufferedOutputStream(new FileOutputStream(new File(file.getParent(), entry.getName()))));
            }

            zipFile.close();
            logger.info("Closed zip file.");
        } catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace());
        }
    }

    public void getFileFromTRUD(String fileName, String outputURL){

        logger.info("Now getting file... ");
        if (ftpClient.isConnected())
        {
            // now get file specified by inputURL
            try
            {
                // send NOOP command to see if connection is still active
                if(ftpClient.sendNoOp())
                {
                    logger.info("Now listing directory... ");
                    FTPFile[] files = ftpClient.listFiles(getPackPath());
                    for(FTPFile file : files)
                    {
                        logger.info("file.getName() = " + file.getName());
                        logger.info("file.getSize() = " + file.getSize());
                        if(file.getName().equals(fileName) && file.getSize() > 0)
                        {
                            InputStream is = ftpClient.retrieveFileStream(getPackPath() +fileName);
                            if (is != null) {
                                getFileFromStream(is, outputURL);
                            }
                            break;
                        }
                    }
                }
                else
                {
                    ftpClient.disconnect();
                    checkAndOpenFTPConnection();
                }
            }
            catch (IOException e) {
                logger.warning("FTP connection might have timed out. Nested exception is : " + e.fillInStackTrace());
            }
        }
        else
        {
            logger.warning("No connection to TRUD is available. Ensure FTP connection is open");
        }
    }

    private void checkAndOpenFTPConnection(){

        if (! ftpClient.isConnected() ||
                ! FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            logger.info("Opening login dialog");
            final TRUDLoginService trudLoginService = new TRUDLoginService(ftpClient);
            final JXLoginPane.JXLoginFrame frame = JXLoginPane.showLoginFrame(trudLoginService);
            logger.info("Showed login dialog");
            frame.addWindowStateListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    logger.info("Login frame no longer visible");

                    // get result from login
                    if(JXLoginPane.Status.SUCCEEDED == frame.getStatus())
                    {
                        logger.info("frame.getStatus() = " + frame.getStatus());
                        // get ftpclient in trudLoginService
                        ftpClient = trudLoginService.getFtpClient();
                    }
                }
            });
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
            while(JXLoginPane.Status.SUCCEEDED != frame.getStatus() &&
                    frame.isVisible())
            {
                // idle thread
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    logger.warning(ERR_MESSAGE+ e.fillInStackTrace());
                }
            }
            logger.info("frame.getStatus() = " + frame.getStatus());
        }
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public String getPackPath() {
        return packPath;
    }

    public void setPackPath(String packPath) {
        this.packPath = packPath;
    }

    public static void main(String[] args) {
        FileDownloader fd = new FileDownloader();
        // check there are exactly four arguments
        if(args.length == 4)
        {
            String packPath = args[0];
            String packName = args[1];
            String userName = args[2];
            String pwd = args[3];

            TRUDLoginService trudLoginService = new TRUDLoginService();
            fd.setPackPath(packPath);
            try
            {
                trudLoginService.authenticate(userName, pwd.toCharArray(), null);
                // dont have to validate again, because we checked in previous installation step
                fd.setFtpClient(trudLoginService.getFtpClient());
                // get file from trud
                fd.getFileFromTRUD(packName, System.getProperty("user.dir")+fd.SEPARATOR+"packs");
            }
            catch (Exception e) {
                logger.warning(ERR_MESSAGE+ e.fillInStackTrace());
            }
        }
        else
        {
            logger.warning("Illegal number of arguments passed. Expected = 4. Passed = "+args.length);
        }
    }
}
