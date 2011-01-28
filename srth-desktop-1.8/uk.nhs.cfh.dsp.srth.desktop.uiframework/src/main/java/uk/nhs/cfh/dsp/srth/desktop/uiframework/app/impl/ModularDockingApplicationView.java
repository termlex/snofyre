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
package uk.nhs.cfh.dsp.srth.desktop.uiframework.app.impl;

import com.jidesoft.swing.JideButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Action;
import org.jdesktop.application.*;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.action.OpenBrowserAction;
import org.jdesktop.swingx.icon.EmptyIcon;
import org.jdesktop.swingx.painter.BusyPainter;
import org.noos.xing.mydoggy.ContentManager;
import org.noos.xing.mydoggy.ContentManagerUIListener;
import org.noos.xing.mydoggy.MultiSplitContentManagerUI;
import org.noos.xing.mydoggy.TabbedContentManagerUI;
import org.noos.xing.mydoggy.event.ContentManagerUIEvent;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;
import org.noos.xing.mydoggy.plaf.ui.content.MyDoggyMultiSplitContentManagerUI;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingView;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.dependencies.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.util.EventObject;

// TODO: Change persistence file location to URL
/**
 * A docking enabled {@link org.jdesktop.application.FrameView}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 12, 2010 at 4:41:03 PM
 */
public class ModularDockingApplicationView extends FrameView implements MDockingView {

    /** The progress bar. */
    private JProgressBar progressBar;

    /** The task monitor. */
    private TaskMonitor taskMonitor;

    /** The busy label. */
    private JXBusyLabel busyLabel;

    /** The status message label. */
    private JLabel statusMessageLabel;

    /** The logger. */
    private static Log logger = LogFactory.getLog(ModularDockingApplication.class);

    /** The task running. */
    private boolean taskRunning;

    /** The tool window manager. */
    private MyDoggyToolWindowManager toolWindowManager;

    // spring enabled services
    /** The view component service. */
    private ViewComponentService viewComponentService;

    /** The action component service. */
    private ActionComponentService actionComponentService;

    /** The application service. */
    private ApplicationService applicationService;

    /** The menu bar. */
    private JMenuBar menuBar;

    /** The tool bar. */
    private JToolBar toolBar;

    /** The jx status bar. */
    private JXStatusBar jxStatusBar;

    /** The cancel current task button. */
    private JideButton cancelCurrentTaskButton;

    /** The main panel. */
    private JPanel mainPanel;

    /** The content manager. */
    private ContentManager contentManager;

    /** The action map. */
    private ApplicationActionMap actionMap;

    /** The resource map. */
    private ResourceMap resourceMap;
    private ModularApplicationAboutDialog aboutDialog;
    private boolean workspaceFileEnabled = false;
    private File workspaceFile;
    private static final int buffer = 5;
    private static final int waitTime = 1000;

    /**
     * Instantiates a new modular docking application view.
     *
     * @param app the app
     * @param viewComponentService the view component service
     * @param actionComponentService the action component service
     * @param applicationService the application service
     */
    public ModularDockingApplicationView(ModularDockingApplication app, ViewComponentService viewComponentService,
                                         ActionComponentService actionComponentService,
                                         ApplicationService applicationService, ModularApplicationAboutDialog aboutDialog){
        super(app);
        this.viewComponentService = viewComponentService;
        this.actionComponentService = actionComponentService;
        this.applicationService = applicationService;
        this.aboutDialog = aboutDialog;

        resourceMap = Application.getInstance(ModularDockingApplication.class).getContext().getResourceMap(ModularDockingApplicationView.class);
        actionMap = Application.getInstance(ModularDockingApplication.class).getContext().getActionMap(ModularDockingApplicationView.class, this);

        // init components -- bad call from constructor!
        initComponents(this);
    }

    /**
     * Inits the components.
     * @param view the ModularDockingApplicationView to register with the ApplicationService
     */
    public synchronized void initComponents(ModularDockingApplicationView view) {

        // register self with applicationService and errorLogService within
        logger.debug("applicationService = " + applicationService);
        while (applicationService == null)
        {
            // idle in this thread
            try
            {
                this.wait(waitTime);
            } catch (InterruptedException e) {
                logger.warn("e.getMessage() = " + e.getMessage());
            }
        }
        // register this modular view with applicationService
        applicationService.registerFrameView(view);

        mainPanel = new JPanel(new BorderLayout());

        statusMessageLabel = new JLabel("Application started");
        statusMessageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusMessageLabel.setVerticalAlignment(SwingConstants.CENTER);
        busyLabel = new JXBusyLabel(new Dimension(18,19));
        BusyPainter painter = new BusyPainter(
                new RoundRectangle2D.Float(0, 0,8.0f,1.9f,10.0f,10.0f),
                new Ellipse2D.Float(2.5f,2.5f,14.0f,14.0f));
        painter.setTrailLength(4);
        painter.setPoints(16);
        painter.setFrame(4);
        busyLabel.setPreferredSize(new Dimension(18,19));
        busyLabel.setIcon(new EmptyIcon(18,19));
        busyLabel.setBusyPainter(painter);
        busyLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        // create progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setVisible(false);
        progressBar.setName("progressBar"); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N
        statusMessageLabel.setName("statusMessageLabel"); // NOI18N
        busyLabel.setName("statusAnimationLabel"); // NOI18N

        // check workspace file exists
        createWorkspacePersistenceFile();

        // create menuBar
        createMenuBar();
        // create toolbar
        createToolBar();
        // create status bar
        createStatusBar();

        // set menubar
        setMenuBar(menuBar);
        // set toolbar
        setToolBar(toolBar);
        // set status bar
        setStatusBar(jxStatusBar);

        // create tool window
        initToolWindows();

        // set tool window to main content
        setComponent(toolWindowManager);

        // create task monitor and exit listeners
        createTasMonitorAndExitListeners();
    }

    /**
     * Creates the tas monitor and exit listeners.
     */
    private synchronized void createTasMonitorAndExitListeners() {

        // connecting action tasks to status bar via TaskMonitor
        taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName))
                {
                    busyLabel.setBusy(true);
                    setTaskRunning(true);
                }
                else if ("done".equals(propertyName))
                {
                    busyLabel.setBusy(false);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                    setTaskRunning(false);
                }
                else if ("message".equals(propertyName))
                {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                }
                else if ("progress".equals(propertyName))
                {
                    int value = (Integer)(evt.getNewValue());
                    busyLabel.setBusy(false);
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
                else if("cancel".equals(propertyName))
                {
                    statusMessageLabel.setText("Task cancelled");
                }
            }
        });

        // add exit listener
        Application.ExitListener exitListener = new Application.ExitListener() {

            private boolean canExit = false;

            public boolean canExit(EventObject e) {

                canExit = checkCanExit();
                logger.info("Value of canExit : " + canExit);
                return canExit;
            }

            public void willExit(EventObject e) {
                // clean up before exiting
                cleanUpBeforeExit();

                // save to log
                logger.info("Shutting down application");
            }
        };
        getApplication().addExitListener(exitListener);

        // add window listener
        getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                // call exit listener
                getApplication().exit();
            }
        });
    }

    /**
     * Inits the tool windows.
     */
    private synchronized void initToolWindows(){

        toolWindowManager = new MyDoggyToolWindowManager();
        toolWindowManager.setName("toolWindowManager");

        contentManager = toolWindowManager.getContentManager();
        MultiSplitContentManagerUI multiSplitContentManagerUI = new MyDoggyMultiSplitContentManagerUI();
        contentManager.setContentManagerUI(multiSplitContentManagerUI);
        // specify default settings for multiSplitContentManagerUI
        multiSplitContentManagerUI.setTabPlacement(TabbedContentManagerUI.TabPlacement.TOP);
        multiSplitContentManagerUI.setShowAlwaysTab(false);
        multiSplitContentManagerUI.addContentManagerUIListener(new ContentManagerUIListener() {
            public boolean contentUIRemoving(ContentManagerUIEvent event) {
                return JOptionPane.showConfirmDialog(getActiveFrame(), "Are you sure?") == JOptionPane.OK_OPTION;
            }

            public void contentUIDetached(ContentManagerUIEvent event) {
            }
        });
    }

    /**
     * Creates the tool bar.
     */
    protected synchronized void createToolBar(){

        toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setName("toolBar");
        toolBar.setFloatable(false);
        toolBar.setBorderPainted(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder(buffer, buffer, buffer, buffer));
    }

    /**
     * Creates the status bar.
     */
    protected synchronized void createStatusBar() {

        // create and add status bar to view
        jxStatusBar = new JXStatusBar();
        jxStatusBar.setName("jxStatusBar");
        jxStatusBar.add(Box.createHorizontalStrut(buffer), new JXStatusBar.Constraint(buffer));
        JXStatusBar.Constraint c1 = new JXStatusBar.Constraint(JXStatusBar.Constraint.ResizeBehavior.FILL);
        jxStatusBar.add(statusMessageLabel, c1);

        JXStatusBar.Constraint c2 = new JXStatusBar.Constraint(25);
        jxStatusBar.add(progressBar, new JXStatusBar.Constraint(150));
        jxStatusBar.add(busyLabel, c2);
        // create cancel current task button
        cancelCurrentTaskButton = new JideButton(actionMap.get("cancelCurrentTask"));
        cancelCurrentTaskButton.setText("");
        cancelCurrentTaskButton.setName("cancelCurrentTaskButton");

        jxStatusBar.add(cancelCurrentTaskButton , new JXStatusBar.Constraint(25));
    }

    /**
     * Creates the menu bar.
     */
    protected synchronized void createMenuBar(){

        menuBar = new JMenuBar();
        menuBar.setName("menuBar"); // NOI18N
        JMenu fileMenu = new JMenu();
        JMenuItem exitMenuItem = new JMenuItem();
        JSeparator statusPanelSeparator = new JSeparator();
        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        fileMenu.setText("File"); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N
        exitMenuItem.setAction(actionMap.get("quitApp")); // NOI18N
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // query menu
        JMenu queryMenu = new JMenu();
        queryMenu.setText("Query");
        queryMenu.setName("queryMenu");
        menuBar.add(queryMenu);

        // dataMenu
        JMenu dataMenu = new JMenu();
        dataMenu.setText("Data");
        dataMenu.setName("dataMenu");
        menuBar.add(dataMenu);

        // toolsMenu
        JMenu toolsMenu = new JMenu();
        toolsMenu.setText("Tools");
        toolsMenu.setName("toolsMenu");
        menuBar.add(toolsMenu);

        // view menu
        JMenu viewMenu = new JMenu();
        viewMenu.setText(resourceMap.getString("viewMenu.text"));
        viewMenu.setName("viewMenu");
        viewMenu.add(actionMap.get("saveViewLayout"));
        viewMenu.add(actionMap.get("restoreViewLayout"));
        menuBar.add(viewMenu);

        // help menu
        JMenu helpMenu = new JMenu();
        helpMenu.setText("Help"); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N
        helpMenu.add(actionMap.get("showErrorReportingURL"));
        helpMenu.add(actionMap.get("showFeatureRequestURL"));        
        helpMenu.add(actionMap.get("showAboutDialog"));
        menuBar.add(helpMenu);
    }

    /**
     * Checks if is task running.
     *
     * @return true, if is task running
     */
    public boolean isTaskRunning() {
        return taskRunning;
    }

    /**
     * Sets the task running.
     *
     * @param taskRunning the new task running
     */
    public void setTaskRunning(boolean taskRunning) {
        boolean oldValue = this.taskRunning;
        this.taskRunning = taskRunning;
        firePropertyChange("taskRunning", oldValue, this.taskRunning);
    }

    /**
     * Gets the tool window manager.
     *
     * @return the tool window manager
     */
    public synchronized MyDoggyToolWindowManager getToolWindowManager() {
        return toolWindowManager;
    }

    /**
     * Gets the content manager.
     *
     * @return the content manager
     */
    public synchronized ContentManager getContentManager() {
        return contentManager;
    }

    /**
     * Gets the view component service.
     *
     * @return the view component service
     */
    public synchronized ViewComponentService getViewComponentService() {
        return viewComponentService;
    }

    /**
     * Sets the view component service.
     *
     * @param viewComponentService the new view component service
     */
    public synchronized void setViewComponentService(ViewComponentService viewComponentService) {
        this.viewComponentService = viewComponentService;
    }

    /**
     * Gets the action component service.
     *
     * @return the action component service
     */
    public synchronized ActionComponentService getActionComponentService() {
        return actionComponentService;
    }

    /**
     * Sets the action component service.
     *
     * @param actionComponentService the new action component service
     */
    public synchronized void setActionComponentService(ActionComponentService actionComponentService) {
        this.actionComponentService = actionComponentService;
    }

    /**
     * Check can exit.
     *
     * @return true, if successful
     */
    protected boolean checkCanExit() {
        int option = JOptionPane.showConfirmDialog(getActiveFrame(), "Are you sure you want to exit?",
                "Exit Application?", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE,
                ImageUtils.getIcon(ImageUtils.IconName.TERMINAL_48));

        return option == JOptionPane.YES_OPTION;
    }

    private void cleanUpBeforeExit(){
        // close all actively running tasks
        for(Task task : taskMonitor.getTasks())
        {
            task.cancel(true);
            logger.info("Cancelled task : "+task.getClass().getName());
        }
        // save layout
        saveViewLayout();
        logger.info("Saved Layout");
    }

    /**
     * Methods the active frame for the application.
     *
     * @return the active frame
     */
    public JFrame getActiveFrame(){
        return getFrame();
    }

    /**
     * Quit app.
     */
    @Action(block = Task.BlockingScope.APPLICATION)
    public void quitApp(){
        // call exit listener
        getApplication().exit();
    }

    /**
     * Cancel current task.
     */
    @Action(enabledProperty="taskRunning")
    public void cancelCurrentTask(){
        // get current task and cancel it
        Task<Void, Void> currentTask = taskMonitor.getForegroundTask();
        if(currentTask != null && currentTask.getUserCanCancel())
        {
            currentTask.cancel(true);
            logger.info("User cancelled task "+currentTask.getTitle());
        }
    }

    public boolean isWorkspaceFileEnabled() {
        return workspaceFileEnabled;
    }

    public void setWorkspaceFileEnabled(boolean workspaceFileEnabled) {
        boolean oldValue = this.workspaceFileEnabled;
        this.workspaceFileEnabled = workspaceFileEnabled;
        firePropertyChange("workspaceFileEnabled", oldValue, this.taskRunning);
    }

    private synchronized void createWorkspacePersistenceFile(){
        try
        {
            String workSpaceFileName = applicationService.getApplicationProperty("view.persistence.file.location");
            logger.debug("workSpaceFileName = " + workSpaceFileName);
            logger.debug("applicationService.getApplicationProperty(\"installation.folder\") = " + applicationService.getApplicationProperty("installation.folder"));
            workspaceFile = new File(applicationService.getApplicationProperty("installation.folder"), workSpaceFileName);
            // create file if it doesn't exist
            if(!workspaceFile.exists())
            {
                boolean result = workspaceFile.createNewFile();
                if(! result)
                {
                    workspaceFileEnabled = false;
                    logger.warn("Unable to create workspace file!");
                }
                else
                {
                    workspaceFileEnabled = true;
                }
            }
            else if (workspaceFile.canRead() && workspaceFile.canWrite())
            {
                workspaceFileEnabled = true;
            }
        }
        catch (IOException e) {
            logger.warn("IO exception. Nested exception is : " + e.fillInStackTrace());
            // set property workspaceFileEnabled to false
            workspaceFileEnabled = false;
        }
    }

    @Action(block= Task.BlockingScope.ACTION, enabledProperty = "workspaceFileEnabled")
    public void saveViewLayout(){
    	// get persistence delegate and save settings
		try
		{
            FileOutputStream output = new FileOutputStream(workspaceFile);
            toolWindowManager.getPersistenceDelegate().save(output);
            output.close();
            statusMessageLabel.setText("Saved workspace layout");
            logger.info("Saved workspace layout");

        } catch (FileNotFoundException e) {
			logger.warn("File not found. Nested exception is : " + e.fillInStackTrace());
		} catch (IOException e) {
			logger.warn("IO exception. Nested exception is : " + e.fillInStackTrace());
		}
    }

    @Action(block= Task.BlockingScope.ACTION, enabledProperty = "workspaceFileEnabled")
    public void restoreViewLayout(){
        // restore view layout from file
        try
        {
            InputStream is = new FileInputStream(workspaceFile);
            toolWindowManager.getPersistenceDelegate().apply(is);
            is.close();
            statusMessageLabel.setText("Restored workspace layout");
            logger.info("Restored workspace layout");

        } catch (FileNotFoundException e) {
            logger.warn("File not found. Nested exception is : " + e.fillInStackTrace());
        } catch (IOException e) {
            logger.warn("File not found. Nested exception is : " + e.fillInStackTrace());
        }
    }

    /**
     * Show about box.
     */
    @Action(block= Task.BlockingScope.ACTION)
    public void showAboutDialog() {
        if(aboutDialog != null)
        {
            JFrame frame = ModularDockingApplication.getApplication().getMainFrame();
            aboutDialog.pack();
            aboutDialog.setLocationRelativeTo(frame);
            ModularDockingApplication.getApplication().show(aboutDialog);            
        }
    }

    @Action(block = Task.BlockingScope.ACTION)
    public void showErrorReportingURL(){
        String errorReportingURL = applicationService.getApplicationProperty("error.reporting.url");
        if (errorReportingURL != null)
        {
            OpenBrowserAction openBrowserAction = new OpenBrowserAction(errorReportingURL);
            openBrowserAction.actionPerformed(null);
            logger.debug("errorReportingURL = " + errorReportingURL);
        }
    }

    @Action(block = Task.BlockingScope.ACTION)
    public void showFeatureRequestURL(){
        String featureRequestURL = applicationService.getApplicationProperty("feature.request.url");
        if (featureRequestURL != null)
        {
            OpenBrowserAction openBrowserAction = new OpenBrowserAction(featureRequestURL);
            openBrowserAction.actionPerformed(null);
            logger.debug("featureRequestURL = " + featureRequestURL);
        }
    }

    /**
     * returns the active component for the application frame.
     *
     * @return the active component
     */
    public synchronized JComponent getActiveComponent(){
        return super.getComponent();
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingView#getMMenuBar()
     */
    public synchronized JMenuBar getMMenuBar() {
        return menuBar;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingView#getMToolBar()
     */
    public synchronized JToolBar getMToolBar() {
        return toolBar;
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingView#getMStatusBar()
     */
    public synchronized JXStatusBar getMStatusBar() {
        return jxStatusBar;
    }

    public ApplicationActionMap getActionMap() {
        return actionMap;
    }
}
