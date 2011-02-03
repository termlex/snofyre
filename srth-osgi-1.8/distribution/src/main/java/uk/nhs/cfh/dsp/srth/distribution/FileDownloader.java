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

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.jdesktop.swingx.JXLoginPane;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URL;
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
    private long maxBytes = 0;

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

    public void getFileFromStreamWithProgress(InputStream inputStream, String outputURL){

        try
        {
            File outputFile = new File(outputURL);
            boolean exists = outputFile.exists();
            logger.info("exists = " + exists);
            if(!exists)
            {
                exists = outputFile.createNewFile();
                logger.info("exists after new file create = " + exists);
            }

            if(exists)
            {
                FileOutputStream fos = new FileOutputStream(outputFile);
                logger.info("fos = " + fos);
                fos.flush();
                ProgressMonitorInputStream pmis =
                        new ProgressMonitorInputStream(null, "Getting file... "+outputFile.getName(), inputStream);
                pmis.getProgressMonitor().setMillisToDecideToPopup(50);
                pmis.getProgressMonitor().setMaximum(Math.round(maxBytes));
                logger.info("Math.round(maxBytes) = " + Math.round(maxBytes));

                BufferedOutputStream bout = new BufferedOutputStream(fos);
                bout.flush();
                logger.info("Getting ready to write bytes...");

                byte[] buffer = new byte[BUFFER];
                int count = 0;
                int n = 0;

                while ((n = inputStream.read(buffer)) != -1) {
                    bout.write(buffer, 0, n);
                    count += n;
                    Object[] byteCounts = {count, maxBytes};
                    String message = String.format("Downloaded %d of %d bytes.\n", byteCounts);
                    pmis.getProgressMonitor().setNote(message);
                }
                logger.info("count = " + count);
                fos.flush();
                bout.flush();

//                byte[] data = new byte[BUFFER];
//                while (true) {
//                    int count = pmis.read(data);
//                    if (count < 0) break;
//                    bout.write(data, 0, count);
//                }

//                byte[] b = new byte[BUFFER];
//                int read;
//                while ((read = pmis.read(b)) != -1) {
//                    bout.write(b, 0, read);
//                }

//                int res;
//                while ((res = pmis.read()) != -1) {
//                    bout.write(res);
//                }

//                int i = 0;
//                byte[] data = new byte[BUFFER];
//                // write data
//                while((i = inputStream.read(data,0,BUFFER)) >=0)
//                {
//                    bout.write(data, 0, i);
//                }

//                long bytesCopied = IOUtils.copy(pmis, fos, BUFFER);
//                logger.info("Finished writing bytes : "+bytesCopied);

                // close streams
                bout.close();
                fos.close();                
                pmis.close();
            }

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

    public void getFileFromTRUD(String fileName, String outputURL){

        logger.info("Now getting file... ");
        if (ftpClient.isConnected())
        {
            logger.info("ftp client is connected... ");
            // now get file specified by inputURL
            try
            {
                ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
//                ftpClient.setBufferSize(BUFFER);
                logger.info("Sending NOOP command... ");
                // send NOOP command to see if connection is still active
                if(ftpClient.sendNoOp())
                {
                    logger.info("ftpClient.getReplyString() = " + ftpClient.getReplyString());
                    logger.info("Now listing directory... ");
                    FTPFile[] files = ftpClient.listFiles(getPackPath());
                    for(FTPFile file : files)
                    {
                        logger.info("file.getName() = " + file.getName());
                        logger.info("file.getSize() = " + file.getSize());
                        if(file.getName().equals(fileName) && file.getSize() > 0)
                        {
//                            File destinationFile = new File(outputURL);
//                            OutputStream out = new FileOutputStream(destinationFile);
//                            ftpClient.retrieveFile(getPackPath()+fileName, out);
//                            out.flush();
//                            out.close();
//                            logger.info("Done retrieving file from server");

                            InputStream is = ftpClient.retrieveFileStream(getPackPath() +fileName);
                            logger.info("is = " + is);
                            // set maxbytes
                            maxBytes = file.getSize();
                            if (is != null) {

                                logger.info("is.available() = " + is.available());
//                                File destinationFile = new File(outputURL);
//                                OutputStream out = new FileOutputStream(destinationFile);
//                                IOUtils.copy(is, out);
//                                out.close();

                                getFileFromStreamWithProgress(is, outputURL);
                                // close stream
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
