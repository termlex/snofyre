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
package uk.nhs.cfh.dsp.srth.desktop.uiframework.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingworker.SwingWorker;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.action.OpenBrowserAction;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jdesktop.swingx.error.ErrorReporter;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerServiceListener;
import uk.nhs.cfh.dsp.srth.desktop.appservice.utils.LookAndFeelUtils;
import uk.nhs.cfh.dsp.srth.desktop.dependencies.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc

/**
 * A custom {@link org.jdesktop.swingx.error.ErrorReporter} that sends an error report
 * vie email.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 22, 2010 at 12:17:58 PM
 */
public class GoogleFormErrorReporter implements ErrorReporter, ErrorLoggerServiceListener {

    /** The logger. */
    private static Log logger = LogFactory.getLog(GoogleFormErrorReporter.class);

    /** The application service. */
    private ApplicationService applicationService;

    /** The error pane. */
    private JXErrorPane errorPane;

    /** The error dialog. */
    private JDialog errorDialog;
    private URL errorReportingURL ;
//    private String appVersionMajor;
//    private String buildId;
//    private String buildNumber;
    private static final String NEW_LINE_CHAR = System.getProperty("line.separator");    

    /**
     * Instantiates a new mail error reporter.
     */
    public GoogleFormErrorReporter() {

    }

    /**
     * Initialise.
     */
    public synchronized void initialise(){

        // wait until application is properly initialised
        while(applicationService == null)
        {
            // idle in this thread
            try
            {
                this.wait(6000);
            } catch (InterruptedException e) {
                logger.warn("e.getMessage() = " + e.getMessage());
            }

            if(applicationService != null && applicationService.getErrorLoggerService() != null)
            {
                this.applicationService.getErrorLoggerService().registerListener(this);

            }
        }

        // set LNF first to avoid component UI errors
        LookAndFeelUtils.setDefaultLNF();

        errorPane = new JXErrorPane();
        errorPane.setPreferredSize(new Dimension(400, 300));
        errorPane.setIcon(ImageUtils.getIcon(ImageUtils.IconName.WARNING_ICON_48));
        errorPane.setErrorReporter(this);
        errorDialog = JXErrorPane.createDialog(null, errorPane);
        errorDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        errorDialog.pack();
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingx.error.ErrorReporter#reportError(org.jdesktop.swingx.error.ErrorInfo)
     */
    public void reportError(final ErrorInfo errorInfo){

        SubmitErrorReportTask task = new SubmitErrorReportTask(errorInfo);
        task.execute();
        // dispose errorPanel
        errorDialog.setVisible(false);
    }

    /**
     * The Class SubmitErrorReportTask.
     */
    class SubmitErrorReportTask extends SwingWorker<Void, Void>{

        /** The error info. */
        private ErrorInfo errorInfo;

        /**
         * Instantiates a new send mail task.
         *
         * @param errorInfo the error info
         */
        SubmitErrorReportTask(ErrorInfo errorInfo) {
            this.errorInfo = errorInfo;
        }

        /* (non-Javadoc)
         * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
         */
        @Override
        protected Void doInBackground() throws Exception {

            if (errorInfo != null)
            {
                Throwable t = errorInfo.getErrorException();
                String osMessage = "An error occurred on "+System.getProperty("os.name")+
                        " version "+System.getProperty("os.version");
                StringBuffer message = new StringBuffer();
                message.append("System Info : ").append(osMessage).append(NEW_LINE_CHAR);
                message.append("Message : ").append(t.toString()).append(NEW_LINE_CHAR);
                message.append("Level : ").append(errorInfo.getErrorLevel()).append(NEW_LINE_CHAR);
                message.append("Stack Trace : ").append(NEW_LINE_CHAR);
                message.append(stackTraceToString(t));

                // copy error message to system clipboard
                StringSelection stringSelection = new StringSelection(message.toString());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // open errorReportingURL
                OpenBrowserAction openBrowserAction = new OpenBrowserAction(errorReportingURL);
                openBrowserAction.actionPerformed(null);
            }

            return null;
        }
    }


    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerServiceListener#errorThrown(java.lang.Object, java.lang.String, java.lang.Throwable)
     */
    public void errorThrown(Object source, String errorMessage, Throwable cause, Level level) {

        // set LNF first to avoid component UI errors
        LookAndFeelUtils.setDefaultLNF();
        errorPane.setErrorInfo(new ErrorInfo("Error", errorMessage, cause.fillInStackTrace().getMessage(), "",
                cause, level, new HashMap<String, String>(0)));
        errorDialog.setVisible(true);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.error.ErrorLoggerServiceListener#errorThrown(org.jdesktop.swingx.error.ErrorInfo)
     */
    public void errorThrown(ErrorInfo errorInfo) {
        // set LNF first to avoid component UI errors
        LookAndFeelUtils.setDefaultLNF();
        
        errorPane.setErrorInfo(errorInfo);
        errorDialog.setVisible(true);
    }

    private String stackTraceToString(Throwable t){

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();

        return NEW_LINE_CHAR +sw.toString()+ NEW_LINE_CHAR;
    }

    /**
     * Sets the application service.
     *
     * @param applicationService the new application service
     */
    public synchronized void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
        this.applicationService.getErrorLoggerService().registerListener(this);
    }

    public synchronized void dispose(){
        if(applicationService != null && applicationService.getErrorLoggerService() != null)
        {
            this.applicationService.getErrorLoggerService().unregisterListener(this);
        }
    }

    public synchronized void setErrorReportingURL(URL errorReportingURL) {
        this.errorReportingURL = errorReportingURL;
    }

//    public synchronized void setAppVersionMajor(String appVersionMajor) {
//        this.appVersionMajor = appVersionMajor;
//    }
//
//    public synchronized void setBuildId(String buildId) {
//        this.buildId = buildId;
//    }
//
//    public synchronized void setBuildNumber(String buildNumber) {
//        this.buildNumber = buildNumber;
//    }
//
//    public String getAppVersionMajor() {
//        return appVersionMajor;
//    }
//
//    public String getBuildId() {
//        return buildId;
//    }
//
//    public String getBuildNumber() {
//        return buildNumber;
//    }
}
