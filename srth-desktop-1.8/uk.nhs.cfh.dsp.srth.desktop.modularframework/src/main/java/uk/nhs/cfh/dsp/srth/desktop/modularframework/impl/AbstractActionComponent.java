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

import uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponent;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of an {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponent}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 11:02:49 AM
 */
public abstract class AbstractActionComponent implements ActionComponent{

    /** The action. */
    private Action action;
    
    /** The menu name. */
    private String menuName;
    
    /** The menu location index. */
    private int menuLocationIndex = 0;
    
    /** The tool bar index. */
    private int toolBarIndex =0;
    
    /** The visible on toolbar. */
    private boolean visibleOnToolbar = false;
    
    /** The actions. */
    private List<Action> actions;
    
    /** The show in separate toolbar. */
    private boolean showInSeparateToolbar = false;

    /**
     * Instantiates a new abstract action component.
     * 
     * @param action the action
     * @param menuName the menu name
     * @param menuLocationIndex the menu location index
     * @param toolBarIndex the tool bar index
     */
    public AbstractActionComponent(Action action, String menuName, int menuLocationIndex, int toolBarIndex) {
        this.action = action;
        this.menuName = menuName;
        this.menuLocationIndex = menuLocationIndex;
        this.toolBarIndex = toolBarIndex;
        actions = new ArrayList<Action>();
    }

    /**
     * Instantiates a new abstract action component.
     * 
     * @param action the action
     */
    public AbstractActionComponent(Action action) {
        this.action = action;
        this.menuName = "Views";
        actions = new ArrayList<Action>();
    }

    /**
     * Instantiates a new abstract action component.
     * 
     * @param menuName the menu name
     * @param menuLocationIndex the menu location index
     * @param toolBarIndex the tool bar index
     * @param visibleOnToolbar the visible on toolbar
     */
    public AbstractActionComponent(String menuName, int menuLocationIndex, int toolBarIndex, boolean visibleOnToolbar) {
        this.menuName = menuName;
        this.menuLocationIndex = menuLocationIndex;
        this.toolBarIndex = toolBarIndex;
        this.visibleOnToolbar = visibleOnToolbar;
        actions = new ArrayList<Action>();
    }

    /**
     * Gets the action.
     * 
     * @return the action
     */
    public Action getAction() {
        if (action != null)
        {
            return action;
        }
        else
        {
            throw new UnsupportedOperationException("This method was called before the action was set." +
                    "This method must be called after setting the action with setAction() method");
        }
    }

    /**
     * Inits the.
     */
    public abstract void init();

    /**
     * Dispose.
     */
    public abstract void dispose();

    /**
     * Sets the action.
     * 
     * @param action the new action
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * Gets the actions.
     * 
     * @return the actions
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Sets the actions.
     * 
     * @param actions the new actions
     */
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    /**
     * Gets the menu name.
     * 
     * @return the menu name
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * Sets the menu name.
     * 
     * @param menuName the new menu name
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * Gets the menu location index.
     * 
     * @return the menu location index
     */
    public int getMenuLocationIndex() {
        return menuLocationIndex;
    }

    /**
     * Sets the menu location index.
     * 
     * @param menuLocationIndex the new menu location index
     */
    public void setMenuLocationIndex(int menuLocationIndex) {
        this.menuLocationIndex = menuLocationIndex;
    }

    /**
     * Gets the tool bar index.
     * 
     * @return the tool bar index
     */
    public int getToolBarIndex() {
        return toolBarIndex;
    }

    /**
     * Sets the tool bar index.
     * 
     * @param toolBarIndex the new tool bar index
     */
    public void setToolBarIndex(int toolBarIndex) {
        this.toolBarIndex = toolBarIndex;
    }

    /**
     * Checks if is visible on toolbar.
     * 
     * @return true, if is visible on toolbar
     */
    public boolean isVisibleOnToolbar() {
        return visibleOnToolbar;
    }

    /**
     * Sets the visible on toolbar.
     * 
     * @param visibleOnToolbar the new visible on toolbar
     */
    public void setVisibleOnToolbar(boolean visibleOnToolbar) {
        this.visibleOnToolbar = visibleOnToolbar;
    }

    /**
     * Checks if is show in separate toolbar.
     * 
     * @return true, if is show in separate toolbar
     */
    public boolean isShowInSeparateToolbar() {
        return showInSeparateToolbar;
    }

    /**
     * Sets the show in separate toolbar.
     * 
     * @param showInSeparateToolbar the new show in separate toolbar
     */
    public void setShowInSeparateToolbar(boolean showInSeparateToolbar) {
        this.showInSeparateToolbar = showInSeparateToolbar;
    }
}
