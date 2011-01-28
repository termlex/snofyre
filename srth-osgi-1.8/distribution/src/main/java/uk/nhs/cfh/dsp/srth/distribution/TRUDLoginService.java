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

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.jdesktop.swingx.auth.LoginService;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * A custom {@link org.jdesktop.swingx.auth.LoginService} that is used to access a download site
 * (in this case TRUD)
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 2, 2011 at 6:13:27 PM
 */
public class TRUDLoginService extends LoginService{

    private static Logger logger = Logger.getLogger(TRUDLoginService.class.getName());
    private FTPClient ftpClient;

    public TRUDLoginService() {
        this(new FTPClient());
    }

    public TRUDLoginService(FTPClient ftpClient) {

        this.ftpClient = ftpClient;
        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+
                    System.getProperty("file.separator")+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    @Override
    public boolean authenticate(String userName, char[] pwd, String server) throws Exception {
        boolean result = false;
        try
        {
            int reply;
            ftpClient.connect(server);
            logger.info("Connected to " + server + ".");
            ftpClient.login(userName, String.valueOf(pwd));
            logger.info(ftpClient.getReplyString());

            // After connection attempt, you should check the reply code to verify success.
            reply = ftpClient.getReplyCode();
            logger.info("reply = " + reply);
            logger.info("FTPReply.isPositiveCompletion(reply) = " + FTPReply.isPositiveCompletion(reply));

            if(!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                logger.warning("FTP server refused connection.");
            }
            else
            {
                result = true;
            }

        } catch(IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace());
        }

        return result;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
