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
package uk.nhs.cfh.dsp.srth.desktop.appservice;

import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXStatusBar;
import org.noos.xing.mydoggy.ContentManager;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 9, 2010 at 9:42:20 AM.
 */
public interface MDockingView {

//    boolean isTaskRunning();
//
//    void setTaskRunning(boolean taskRunning);

    /**
 * Gets the tool window manager.
 *
 * @return the tool window manager
 */
MyDoggyToolWindowManager getToolWindowManager();

    /**
     * Gets the content manager.
     *
     * @return the content manager
     */
    ContentManager getContentManager();

    /**
     * Gets the view component service.
     *
     * @return the view component service
     */
    ViewComponentService getViewComponentService();

    /**
     * Sets the view component service.
     *
     * @param viewComponentService the new view component service
     */
    void setViewComponentService(ViewComponentService viewComponentService);

    /**
     * Gets the action component service.
     *
     * @return the action component service
     */
    ActionComponentService getActionComponentService();

    /**
     * Sets the action component service.
     *
     * @param actionComponentService the new action component service
     */
    void setActionComponentService(ActionComponentService actionComponentService);
//
//    void saveViewLayout();
//
//    void restoreViewLayout();
//
//    void quitApp();
//
//    void cancelCurrentTask();

    /**
 * Gets the active frame.
 *
 * @return the active frame
 */
JFrame getActiveFrame();

    /**
     * Gets the active component.
     *
     * @return the active component
     */
    JComponent getActiveComponent();

    /**
     * Gets the m menu bar.
     *
     * @return the m menu bar
     */
    JMenuBar getMMenuBar();

    /**
     * Gets the m tool bar.
     *
     * @return the m tool bar
     */
    JToolBar getMToolBar();

    /**
     * Gets the m status bar.
     *
     * @return the m status bar
     */
    JXStatusBar getMStatusBar();

    /**
     * Gets the action map.
     *
     * @return the action map
     */
    ApplicationActionMap getActionMap();

    /**
     * Gets the resource map.
     *
     * @return the resource map
     */
    ResourceMap getResourceMap();
}
