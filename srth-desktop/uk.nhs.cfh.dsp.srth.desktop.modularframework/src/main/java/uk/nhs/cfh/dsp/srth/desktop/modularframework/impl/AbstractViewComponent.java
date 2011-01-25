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
package uk.nhs.cfh.dsp.srth.desktop.modularframework.impl;

import uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 10:44:57 AM
 */
public abstract class AbstractViewComponent implements ViewComponent{

    /** The name. */
    private String name;
    
    /** The title. */
    private String title;
    
    /** The alignment. */
    private ViewComponent.Alignment alignment;
    
    /** The icon. */
    private Icon icon;
    
    /** The preferences visible. */
    private boolean preferencesVisible = false;
    
    /** The add as tool window. */
    private boolean addAsToolWindow = true;
    
    /** The component. */
    private JComponent component;
    
    /** The active on initialise. */
    private boolean activeOnInitialise = false;

    /**
     * Instantiates a new abstract view component.
     * 
     * @param name the name
     * @param title the title
     * @param alignment the alignment
     * @param icon the icon
     * @param preferencesVisible the preferences visible
     */
    public AbstractViewComponent(String name, String title, ViewComponent.Alignment alignment, Icon icon,
                                 boolean preferencesVisible) {
        this.name = name;
        this.title = title;
        this.alignment = alignment;
        this.icon = icon;
        this.preferencesVisible = preferencesVisible;
    }

    /**
     * Instantiates a new abstract view component.
     * 
     * @param title the title
     * @param alignment the alignment
     * @param icon the icon
     * @param preferencesVisible the preferences visible
     */
    public AbstractViewComponent(String title, ViewComponent.Alignment alignment, Icon icon, boolean preferencesVisible) {
        this.title = title;
        this.alignment = alignment;
        this.icon = icon;
        this.preferencesVisible = preferencesVisible;
        this.name = getClass().getName();
    }

    /**
     * Instantiates a new abstract view component.
     * 
     * @param title the title
     * @param alignment the alignment
     * @param icon the icon
     */
    public AbstractViewComponent(String title, ViewComponent.Alignment alignment, Icon icon) {
        this.title = title;
        this.alignment = alignment;
        this.icon = icon;
        this.name = getClass().getName();
        this.preferencesVisible = false;
    }

    /**
     * Gets the component.
     * 
     * @return the component
     */
    public JComponent getComponent() {
        if (component != null)
        {
            return component;
        }
        else
        {
            throw new UnsupportedOperationException("This method was called before the component was set." +
                    "This method must be called after setting the component with setComponent() method");
        }
    }

    /**
     * Sets the component.
     * 
     * @param component the new component
     */
    public void setComponent(JComponent component) {
        this.component = component;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * 
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the alignment.
     * 
     * @return the alignment
     */
    public ViewComponent.Alignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the alignment.
     * 
     * @param alignment the new alignment
     */
    public void setAlignment(ViewComponent.Alignment alignment) {
        this.alignment = alignment;
    }

    /**
     * Gets the icon.
     * 
     * @return the icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * Sets the icon.
     * 
     * @param icon the new icon
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     * Checks if is preferences visible.
     * 
     * @return true, if is preferences visible
     */
    public boolean isPreferencesVisible() {
        return preferencesVisible;
    }

    /**
     * Sets the preferences visible.
     * 
     * @param preferencesVisible the new preferences visible
     */
    public void setPreferencesVisible(boolean preferencesVisible) {
        this.preferencesVisible = preferencesVisible;
    }

    /**
     * Checks if is adds the as tool window.
     * 
     * @return true, if is adds the as tool window
     */
    public boolean isAddAsToolWindow() {
        return addAsToolWindow;
    }

    /**
     * Sets the adds the as tool window.
     * 
     * @param addAsToolWindow the new adds the as tool window
     */
    public void setAddAsToolWindow(boolean addAsToolWindow) {
        this.addAsToolWindow = addAsToolWindow;
    }

    /**
     * Checks if is active on initialise.
     * 
     * @return true, if is active on initialise
     */
    public boolean isActiveOnInitialise() {
        return activeOnInitialise;
    }

    /**
     * Sets the active on initialise.
     * 
     * @param activeOnInitialise the new active on initialise
     */
    public void setActiveOnInitialise(boolean activeOnInitialise) {
        this.activeOnInitialise = activeOnInitialise;
    }
}
