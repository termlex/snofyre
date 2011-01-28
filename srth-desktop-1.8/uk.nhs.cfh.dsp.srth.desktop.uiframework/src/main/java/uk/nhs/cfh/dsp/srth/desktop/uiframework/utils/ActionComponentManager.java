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

import com.jidesoft.swing.JideButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.appservice.utils.LookAndFeelUtils;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponent;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponentServiceListener;

import javax.swing.*;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * An utility class that registers and handles actions with the.
 *
 * {@link uk.nhs.cfh.dsp.srth.desktop.uiframework.app.impl.ModularDockingApplication}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 25, 2009 at 10:17:26 PM
 */
public class ActionComponentManager implements ActionComponentServiceListener{

    /** The menu bar. */
    private JMenuBar menuBar;

    /** The tool bar. */
    private JToolBar toolBar;

    /** The action component service. */
    private ActionComponentService actionComponentService;

    /** The logger. */
    private static Log logger = LogFactory.getLog(ActionComponentManager.class);

    /** The application service. */
    private ApplicationService applicationService;
    private static final int waitTime = 6000;

    /**
     * Instantiates a new action component manager.
     *
     * @param applicationService the application service
     * @param actionComponentService the action component service
     */
    public ActionComponentManager(ApplicationService applicationService, ActionComponentService actionComponentService) {
        this.applicationService = applicationService;
        this.actionComponentService = actionComponentService;
    }

    /**
     * Empty constrctor for DI.
     */
    public ActionComponentManager() {
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents() {

        if(applicationService != null && applicationService.getFrameView() != null)
        {
            menuBar = applicationService.getFrameView().getMMenuBar();
            toolBar = applicationService.getFrameView().getMToolBar();
        }

        while(menuBar == null || toolBar == null)
        {
            // idle this thread for a little while
            try {
                this.wait(waitTime);
            } catch (InterruptedException e) {
                logger.warn(e.fillInStackTrace());
            }

            if(applicationService != null && applicationService.getFrameView() != null)
            {
                menuBar = applicationService.getFrameView().getMMenuBar();
                toolBar = applicationService.getFrameView().getMToolBar();
            }
        }

        logger.info("Number of actions registered with actionComponentService = "
                + actionComponentService.getComponents().size());
        // loop through registered action components and add them to menu bar
        for(ActionComponent actionComponent : actionComponentService.getComponents())
        {
            logger.info("Adding action component : "+actionComponent.getClass().getName());
            addActionComponent(actionComponent);
        }

        // register this listener with service
        actionComponentService.addListener(this);
    }
    
    /**
     * Adds the action component.
     *
     * @param actionComponent the action component
     */
    public synchronized void addActionComponent(final ActionComponent actionComponent){

        if(actionComponent != null)
        {
            Runnable runnable = new Runnable() {
                public void run()
                {
                    // set LNF first to avoid component UI errors
                    LookAndFeelUtils.setDefaultLNF();

                    String menuName = actionComponent.getMenuName();
                    int menuLocationIndex = actionComponent.getMenuLocationIndex();
                    int toolBarIndex = actionComponent.getToolBarIndex();
                    logger.info("Added to menuName = " + menuName);

                    // get menu for menu name
                    JMenu menu = getMenuWithName(menuName);
                    // create menu item with action;
                    JMenuItem menuItem = new JMenuItem(actionComponent.getAction());
                    SwingUtilities.updateComponentTreeUI(menuItem);
                    checkAndAddMenuItem(menu, menuItem, menuLocationIndex);

                    // refresh menu
                    menu.revalidate();
                    SwingUtilities.updateComponentTreeUI(menu);

                    // check if set to visible on toolbar
                    if(actionComponent.isVisibleOnToolbar())
                    {
                        JideButton b = new JideButton(actionComponent.getAction());
                        // set text to be empty
                        b.setText("");
                        SwingUtilities.updateComponentTreeUI(b);
                        // check toolbar count and add button
                        checkAndAddToolbarButton(toolBar, b, toolBarIndex);
                    }

                    // refresh toolbar and menubar
                    menuBar.revalidate();
                    toolBar.revalidate();
                    SwingUtilities.updateComponentTreeUI(toolBar);
                    SwingUtilities.updateComponentTreeUI(menuBar);
                }
            };

            SwingUtilities.invokeLater(runnable);
        }
        else
        {
            throw new IllegalArgumentException("Argument passed can not be null : "+actionComponent);
        }
    }

    /**
     * Check and add toolbar button.
     *
     * @param toolBar the tool bar
     * @param button the button
     * @param toolBarIndex the tool bar index
     */
    private synchronized void checkAndAddToolbarButton(JToolBar toolBar, JideButton button, int toolBarIndex) {
        // check component count of toolBar before adding button at toolBarIndex
        int componentCount = toolBar.getComponentCount();
        if(componentCount > toolBarIndex)
        {
            // add horizontal space if component count > 0
            if(componentCount >0)
            {
                toolBar.add(Box.createHorizontalStrut(5));
            }
            toolBar.add(button, toolBarIndex);
        }
        else
        {
            toolBar.add(button, componentCount);
        }
    }

    /**
     * Check and add menu item.
     *
     * @param menu the menu
     * @param menuItem the menu item
     * @param menuLocationIndex the menu location index
     */
    private synchronized void checkAndAddMenuItem(JMenu menu, JMenuItem menuItem, int menuLocationIndex) {
        // check if menu has item count greater than menuLocationIndex
        int menuItemCount = menu.getItemCount();
        if(menuItemCount > menuLocationIndex)
        {
            menu.insert(menuItem, menuLocationIndex);
        }
        else
        {
            menu.insert(menuItem, menuItemCount);
        }

        //reset  quit menu item
        resetQuitMenuMenuItem();
    }

    /**
     * Reset quit menu menu item.
     */
    private synchronized void resetQuitMenuMenuItem(){
        // get file menu
        JMenu fileMenu = getMenuWithName("File");
        int itemCount = fileMenu.getItemCount();
        for(int i=0; i<itemCount; i++)
        {
            JMenuItem item = fileMenu.getItem(i);
            if("exitMenuItem".equals(item.getName()))
            {
                // reset item to last item
                fileMenu.insert(item, itemCount);
            }
        }
    }

    /**
     * Removes the action component.
     *
     * @param actionComponent the action component
     */
    public synchronized void removeActionComponent(final ActionComponent actionComponent) {

        Runnable uiUpdater = new Runnable() {
            public void run() {

                // set LNF first to avoid component UI errors
                LookAndFeelUtils.setDefaultLNF();
                removeComponent(actionComponent);

                toolBar.revalidate();
                menuBar.revalidate();
                SwingUtilities.updateComponentTreeUI(toolBar);
                SwingUtilities.updateComponentTreeUI(menuBar);
            }
        };

        SwingUtilities.invokeLater(uiUpdater);
    }

    private void removeComponent(ActionComponent actionComponent) {

        JMenu actionsMenu = getMenuWithName(actionComponent.getMenuName());
        Action action = actionComponent.getAction();

        Component[] components = toolBar.getComponents();
        for (Component component : components)
        {
            if (component instanceof JideButton
                    && action == ((JideButton) component).getAction()) {
                toolBar.remove(component);
                break;
            }

            components = actionsMenu.getMenuComponents();
            for (Component c : components)
            {
                if (c instanceof JMenuItem
                        && action == ((JMenuItem) c)
                        .getAction()) {
                    actionsMenu.remove(c);
                    break;
                }
            }
        }

        // Cleanup toolbar separators
        components = toolBar.getComponents();
        for (int i = 0; i < components.length; i++)
        {
            if (components[i] instanceof JToolBar.Separator
                    && (i == 0 || i == components.length - 1 ||
                    (i < components.length - 1 && components[i + 1] instanceof JToolBar.Separator))) {
                toolBar.remove(components[i]);
            }
        }

        // Cleanup menu separators
        components = actionsMenu.getMenuComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JPopupMenu.Separator
                    && (i == 0 || i == components.length - 1 ||
                    (i < components.length - 1 && components[i + 1] instanceof JPopupMenu.Separator))) {
                actionsMenu.remove(components[i]);
            }
        }
    }

    /**
     * Gets the menu with name.
     *
     * @param name the name
     *
     * @return the menu with name
     */
    private synchronized JMenu getMenuWithName(String name){

        // get index of matching menu if exists
        int menuIndex = getMenuIndex(name);
        if(menuIndex > -1)
        {
            return menuBar.getMenu(menuIndex);
        }
        else
        {
            logger.info("Menu "+name+" does not exist. Adding to menu bar");
            JMenu menu = new JMenu(name);
            menu.setName(name+"Menu");
            menuBar.add(menu);

            return menu;
        }
    }

    /**
     * Gets the menu index.
     *
     * @param name the name
     *
     * @return the menu index
     */
    private synchronized int getMenuIndex(String name){

        /*
       check if menu already exists in menu bar, but the name would have been added as 'fileMenu',
       instead of 'file'
        */
        int menuIndex = -1;
        for(int i=0; i<menuBar.getMenuCount(); i++)
        {
            JMenu m = menuBar.getMenu(i);
            if(m.getName().equalsIgnoreCase(name+"Menu"))
            {
                menuIndex = i;
                break;
            }
        }

        return menuIndex;
    }

    /**
     * Component registered.
     *
     * @param service the service
     * @param component the component
     */
    public synchronized void componentRegistered(ActionComponentService service, ActionComponent component) {
        addActionComponent(component);
    }

    /**
     * Component unregistered.
     *
     * @param service the service
     * @param component the component
     */
    public synchronized void componentUnregistered(ActionComponentService service, ActionComponent component) {
        removeActionComponent(component);
    }

    /**
     * Shut down.
     */
    public synchronized void shutDown(){
        actionComponentService.removeListener(this);
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
     * Sets the application service.
     *
     * @param applicationService the new application service
     */
    public synchronized void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
}
