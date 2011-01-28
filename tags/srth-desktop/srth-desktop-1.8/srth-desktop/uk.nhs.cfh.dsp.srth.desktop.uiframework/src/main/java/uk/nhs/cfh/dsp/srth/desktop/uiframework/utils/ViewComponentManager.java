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
import org.noos.xing.mydoggy.AggregationPosition;
import org.noos.xing.mydoggy.ContentManager;
import org.noos.xing.mydoggy.MultiSplitConstraint;
import org.noos.xing.mydoggy.ToolWindowAnchor;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.appservice.MDockingView;
import uk.nhs.cfh.dsp.srth.desktop.appservice.utils.LookAndFeelUtils;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentServiceListener;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * An utility class that manages view components that are added and removed
 * dynamically by modules. This classes uses the
 * {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponentService} to dynamically manage views.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 3:54:33 PM
 */
public class ViewComponentManager implements ViewComponentServiceListener{

    /** The application view. */
    private MDockingView applicationView;

    /** The view component service. */
    private ViewComponentService viewComponentService;

    /** The alignment map. */
    private Map<ViewComponent.Alignment, ToolWindowAnchor> alignmentMap;

    /** The component names. */
    private Set<String> componentNames;

    /** The logger. */
    private static Log logger = LogFactory.getLog(ViewComponentManager.class);

    /** The tool window manager. */
    private MyDoggyToolWindowManager toolWindowManager;

    /** The content manager. */
    private ContentManager contentManager;

    /** The application service. */
    private ApplicationService applicationService;

    /**
     * Instantiates a new view component manager.
     *
     * @param applicationService the application service
     * @param viewComponentService the view component service
     */
    public ViewComponentManager(ApplicationService applicationService, ViewComponentService viewComponentService) {
        this.applicationService = applicationService;
        this.viewComponentService = viewComponentService;
        alignmentMap = new HashMap<ViewComponent.Alignment, ToolWindowAnchor>();
        componentNames = new HashSet<String>();
    }

    /**
     * Empty constructor for DI.
     */
    public ViewComponentManager() {
        alignmentMap = new HashMap<ViewComponent.Alignment, ToolWindowAnchor>();
        componentNames = new HashSet<String>();
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents() {

        // create alignment map
        alignmentMap.put(ViewComponent.Alignment.TOP , ToolWindowAnchor.TOP);
        alignmentMap.put(ViewComponent.Alignment.BOTTOM , ToolWindowAnchor.BOTTOM);
        alignmentMap.put(ViewComponent.Alignment.LEFT , ToolWindowAnchor.LEFT);
        alignmentMap.put(ViewComponent.Alignment.RIGHT , ToolWindowAnchor.RIGHT);

        if(applicationService != null && applicationService.getFrameView() != null)
        {
            applicationView = applicationService.getFrameView();
            toolWindowManager = applicationView.getToolWindowManager();
            contentManager = applicationView.getContentManager();
        }

        // wait until application is properly initialised
        while(viewComponentService == null || toolWindowManager == null || contentManager == null)
        {
            // idle in this thread
            try
            {
                this.wait(6000);
            } catch (InterruptedException e) {
                logger.warn("e.getMessage() = " + e.getMessage());
            }

            if(applicationService != null && applicationService.getFrameView() != null)
            {
                applicationView = applicationService.getFrameView();
                toolWindowManager = applicationView.getToolWindowManager();
                contentManager = applicationView.getContentManager();
            }
        }

        logger.info("Number of view components registered with viewComponentService = "
                + viewComponentService.getComponents().size());
        // get all viewComponents registered with viewComponentService and add them
        for(ViewComponent component: viewComponentService.getComponents())
        {
            logger.info("Adding view component  : "+component.getName());
            addViewComponent(component);
        }

        // register listener with service
        viewComponentService.addListener(this);
    }

//    /**
//     * Checks if is initialised.
//     *
//     * @return true, if is initialised
//     */
//    private synchronized boolean isInitialised(){
//
//        logger.info("contentManager = " + contentManager);
//        logger.info( "toolWindowManager = " + toolWindowManager);
//        logger.info( "viewComponentService = " + viewComponentService);
//        return (viewComponentService != null && toolWindowManager != null
//                && contentManager != null);
//    }

    /**
     * Adds the view component.
     *
     * @param component the component
     */
    private synchronized void addViewComponent(final ViewComponent component){
        if(component != null)
        {
            // ensure thread safe UI update
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    // add componentToPosition
                    addComponentToPosition(component);
                    
                    applicationView.getActiveComponent().revalidate();
                    SwingUtilities.updateComponentTreeUI(toolWindowManager);
                    SwingUtilities.updateComponentTreeUI(applicationView.getActiveComponent());
                }
            };

            SwingUtilities.invokeLater(runnable);
        }
    }

    private void addComponentToPosition(ViewComponent component) {

        // set LNF first to avoid component UI errors
        LookAndFeelUtils.setDefaultLNF();
        String name = component.getName();
        String title = component.getTitle();
        Icon icon = component.getIcon();
        ViewComponent.Alignment alignment = component.getAlignment();
        JComponent innerComponent = component.getComponent();
        SwingUtilities.updateComponentTreeUI(innerComponent);
        innerComponent.setName(name);

        /*
       Replace all previous implementations with a simple placement. In the newer, simpler model,
       the following alignments are added as tool windows : LEFT, RIGHT, BOTTOM
       the following alignments are added as tabbed windows : TOP, CENTRE
        */

        if(ViewComponent.Alignment.CENTRE == alignment && !componentNames.contains(name))
        {
            // get component and add as tabbed panel
            contentManager.addContent(title, title, icon, innerComponent, null,
                    new MultiSplitConstraint(AggregationPosition.BOTTOM));
            logger.info("Added view component : "+name+" to centre window");
            // add to component names
            componentNames.add(component.getName());
        }
        else if(ViewComponent.Alignment.TOP == alignment && !componentNames.contains(name))
        {
            // get component and add as tabbed panel
            contentManager.addContent(title, title, icon, innerComponent, null,
                    new MultiSplitConstraint(AggregationPosition.TOP));
            logger.info("Added view component : "+name+" to top window");
            // add to component names
            componentNames.add(component.getName());
        }
        else if(ViewComponent.Alignment.LEFT == alignment && !componentNames.contains(name))
        {
            // create tool window and make it available
            toolWindowManager.registerToolWindow(title, title, icon,
                    innerComponent, ToolWindowAnchor.LEFT);
            toolWindowManager.getToolWindow(title).setAvailable(true);
            // set active if set to show on active
            if(component.isActiveOnInitialise())
            {
                toolWindowManager.getToolWindow(title).setActive(true);
            }
            logger.info( "Added view component : "+name+" as tool in LEFT position.");
            // add to component names
            componentNames.add(component.getName());
        }
        else if(ViewComponent.Alignment.RIGHT == alignment && !componentNames.contains(name))
        {
            // create tool window and make it available
            toolWindowManager.registerToolWindow(title, title, icon,
                    innerComponent, ToolWindowAnchor.RIGHT);
            toolWindowManager.getToolWindow(title).setAvailable(true);
            // set active if set to show on active
            if(component.isActiveOnInitialise())
            {
                toolWindowManager.getToolWindow(title).setActive(true);
            }
            logger.info( "Added view component : "+name+" as tool in RIGHT position.");
            // add to component names
            componentNames.add(component.getName());
        }
        else if(ViewComponent.Alignment.BOTTOM == alignment && !componentNames.contains(name))
        {
            // create tool window and make it available
            toolWindowManager.registerToolWindow(title, title, icon,
                    innerComponent, ToolWindowAnchor.BOTTOM);
            toolWindowManager.getToolWindow(title).setAvailable(true);
            // set active if set to show on active
            if(component.isActiveOnInitialise())
            {
                toolWindowManager.getToolWindow(title).setActive(true);
            }
            logger.info( "Added view component : "+name+" as tool in BOTTOM position.");
            // add to component names
            componentNames.add(component.getName());
        }
        else if(alignment == null && !componentNames.contains(name))
        {
            // warn user that unknown alignment found
            logger.warn("Unknown alignment found. Alignment = " + alignment);
            // add to bottom tool window
            applicationView.getToolWindowManager().registerToolWindow(title, title, icon,
                    innerComponent, ToolWindowAnchor.BOTTOM);
            toolWindowManager.getToolWindow(title).setAvailable(true);
            // set active if set to show on active
            if(component.isActiveOnInitialise())
            {
                toolWindowManager.getToolWindow(title).setActive(true);
            }
            logger.info( "Added view component : "+name+" as bottom tool window");
            // add to component names
            componentNames.add(component.getName());
        }
        else if(componentNames.contains(name))
        {
            logger.warn("Component has already been added to application.");
            throw new IllegalArgumentException("Component has already been added to application.");
        }
    }

    /**
     * Removes the view component.
     *
     * @param component the component
     */
    private synchronized void removeViewComponent(final ViewComponent component){
        if(component != null)
        {
            // ensure thread safe UI update
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    // set LNF first to avoid component UI errors
                    LookAndFeelUtils.setDefaultLNF();

                    String name = component.getName();
                    String title = component.getTitle();
                    ViewComponent.Alignment alignment = component.getAlignment();
                    JComponent innerComponent = component.getComponent();
                    // remove component from alignment map
                    alignmentMap.remove(component);

                    if(ViewComponent.Alignment.CENTRE == alignment || ViewComponent.Alignment.TOP == alignment)
                    {
                        // remove from contentManager
                        contentManager.removeContent(contentManager.getContentByComponent(innerComponent));
                        componentNames.remove(name);
                    }
                    else
                    {
                        // unregister toolwindow from toolWindowManager
                        applicationView.getToolWindowManager().unregisterToolWindow(title);
                        logger.info( "Removed component : "+name+" from "+alignment.name()+" position.");
                        // remove from component names
                        componentNames.remove(name);
                    }

                    applicationView.getActiveComponent().revalidate();
                    SwingUtilities.updateComponentTreeUI(applicationView.getActiveComponent());
                }
            };

            SwingUtilities.invokeLater(runnable);
        }
    }

    /**
     * View component registered.
     *
     * @param service the service
     * @param component the component
     */
    public synchronized void viewComponentRegistered(ViewComponentService service, ViewComponent component) {

        addViewComponent(component);
    }

    /**
     * View component unregistered.
     *
     * @param service the service
     * @param component the component
     */
    public synchronized void viewComponentUnregistered(ViewComponentService service, ViewComponent component) {

        removeViewComponent(component);
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
     * Sets the application service.
     *
     * @param applicationService the new application service
     */
    public synchronized void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Gets the application view.
     *
     * @return the application view
     */
    public synchronized MDockingView getApplicationView() {
        return applicationView;
    }

    /**
     * Sets the application view.
     *
     * @param applicationView the new application view
     */
    public synchronized void setApplicationView(MDockingView applicationView) {
        this.applicationView = applicationView;
    }

    /**
     * Dipose.
     */
    public synchronized void dipose(){
        // unregister self from viewComponentService
        viewComponentService.removeListener(this);
    }
}
