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
package uk.nhs.cfh.dsp.srth.desktop.modularframework;

import javax.swing.*;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for an action component for the
 * desktop version of SNOMED CT Reporting Test Harness.
 * The action component declares the menu location for the action and toolbar if any.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 11:00:43 AM
 */
public interface ActionComponent {


   /**
    * Gets the action.
    * 
    * @return the action
    */
    Action getAction();

   /**
    * Sets the action.
    * 
    * @param action the new action
    */
    void setAction(Action action);

   /**
    * Gets the menu name.
    * 
    * @return the menu name
    */
    String getMenuName();

   /**
    * Sets the menu name.
    * 
    * @param menuName the new menu name
    */
    void setMenuName(String menuName);

   /**
    * Gets the menu location index.
    * 
    * @return the menu location index
    */
    int getMenuLocationIndex();

   /**
    * Sets the menu location index.
    * 
    * @param menuLocationIndex the new menu location index
    */
    void setMenuLocationIndex(int menuLocationIndex);

   /**
    * Gets the tool bar index.
    * 
    * @return the tool bar index
    */
    int getToolBarIndex();

   /**
    * Sets the tool bar index.
    * 
    * @param toolBarIndex the new tool bar index
    */
    void setToolBarIndex(int toolBarIndex);

   /**
    * Checks if is visible on toolbar.
    * 
    * @return true, if is visible on toolbar
    */
    boolean isVisibleOnToolbar();

   /**
    * Sets the visible on toolbar.
    * 
    * @param visibleOnToolbar the new visible on toolbar
    */
    void setVisibleOnToolbar(boolean visibleOnToolbar);

   /**
    * Inits the.
    */
    void init();

   /**
    * Dispose.
    */
    void dispose();

   /**
    * Gets the actions.
    * 
    * @return the actions
    */
    List<Action> getActions();

   /**
    * Sets the actions.
    * 
    * @param actions the new actions
    */
    void setActions(List<Action> actions);

   /**
    * Checks if is show in separate toolbar.
    * 
    * @return true, if is show in separate toolbar
    */
    boolean isShowInSeparateToolbar();

   /**
    * Sets the show in separate toolbar.
    * 
    * @param showInSeparateToolbar the new show in separate toolbar
    */
    void setShowInSeparateToolbar(boolean showInSeparateToolbar);
}
