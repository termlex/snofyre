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
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ViewComponent}
 * that has {@link uk.nhs.cfh.dsp.srth.desktop.modularframework.ActionComponent}s attached to it.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 11:10:06 AM
 */
public abstract class AbstractActionEnabledViewComponent extends AbstractViewComponent{

    /** The actions. */
    private List<ActionComponent> actions;
    
    /** The Constant errorMessage. */
    private static final String errorMessage = "Action passed can not be null";
    
    /** The inner actions. */
    private List<Action> innerActions;

    /**
     * Instantiates a new abstract action enabled view component.
     * 
     * @param name the name
     * @param title the title
     * @param alignment the alignment
     * @param icon the icon
     * @param preferencesVisible the preferences visible
     */
    public AbstractActionEnabledViewComponent(String name, String title, Alignment alignment,
                                              Icon icon, boolean preferencesVisible) {
        super(name, title, alignment, icon, preferencesVisible);
        actions = new ArrayList<ActionComponent>();
        innerActions = new ArrayList<Action>();
    }

    /**
     * Instantiates a new abstract action enabled view component.
     * 
     * @param title the title
     * @param alignment the alignment
     * @param icon the icon
     * @param preferencesVisible the preferences visible
     */
    public AbstractActionEnabledViewComponent(String title, Alignment alignment, Icon icon,
                                              boolean preferencesVisible) {
        super(title, alignment, icon, preferencesVisible);
        actions = new ArrayList<ActionComponent>();
        innerActions = new ArrayList<Action>();
    }

    /**
     * Instantiates a new abstract action enabled view component.
     * 
     * @param title the title
     * @param alignment the alignment
     * @param icon the icon
     */
    public AbstractActionEnabledViewComponent(String title, Alignment alignment, Icon icon) {
        super(title, alignment, icon);
        actions = new ArrayList<ActionComponent>();
        innerActions = new ArrayList<Action>();
    }

    /**
     * Gets the actions.
     * 
     * @return the actions
     */
    public List<ActionComponent> getActions() {
        return actions;
    }

    /**
     * Sets the actions.
     * 
     * @param actions the new actions
     */
    public void setActions(List<ActionComponent> actions) {
        this.actions = actions;
    }

    /**
     * Adds the action.
     * 
     * @param action the action
     */
    public void addAction(ActionComponent action){
        if(action != null)
        {
            actions.add(action);
            innerActions.add(action.getAction());
        }
        else
        {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Removes the action.
     * 
     * @param action the action
     */
    public void removeAction(ActionComponent action){
        if(action != null)
        {
            actions.remove(action);
            innerActions.remove(action.getAction());
        }
        else
        {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Adds the action.
     * 
     * @param action the action
     */
    public void addAction(Action action){
        if(action != null)
        {
            // create inner action component implementation
            ActionComponent actionComponent = new ActionComponentImpl(action);
            // add to actions and inner actions
            addAction(actionComponent);
        }
        else
        {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Removes the action.
     * 
     * @param action the action
     */
    public void removeAction(Action action){
        if(action != null)
        {
            // create inner action component implementation
            ActionComponent actionComponent = new ActionComponentImpl(action);
            // remove from actions and inner actions
            removeAction(actionComponent);
        }
        else
        {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * The Class ActionComponentImpl.
     */
    class ActionComponentImpl extends AbstractActionComponent{
        
        /**
         * Instantiates a new action component impl.
         * 
         * @param action the action
         */
        ActionComponentImpl(Action action) {
            super(action);
        }

        /* (non-Javadoc)
         * @see nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent#init()
         */
        @Override
        public void init() {

        }

        /* (non-Javadoc)
         * @see nhs.cfh.dsp.srth.desktop.modularframework.impl.AbstractActionComponent#dispose()
         */
        @Override
        public void dispose() {
           
        }
    }
}
