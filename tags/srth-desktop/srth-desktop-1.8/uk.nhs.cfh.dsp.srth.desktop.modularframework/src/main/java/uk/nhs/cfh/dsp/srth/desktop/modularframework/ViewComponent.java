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

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a view component that can
 * be added to the desktop version of the SNOMED CT Reporting Test Harness.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 24, 2009 at 10:44:10 AM
 */
public interface ViewComponent {

    /**
     * Checks if is active on initialise.
     *
     * @return true, if is active on initialise
     */
    boolean isActiveOnInitialise();

    /**
     * Sets the active on initialise.
     *
     * @param activeOnInitialise the new active on initialise
     */
    void setActiveOnInitialise(boolean activeOnInitialise);

    /**
     * The Enum Alignment.
     */
     enum Alignment{/** The LEFT. */
    LEFT, /** The RIGHT. */
    RIGHT, /** The BOTTOM. */
    BOTTOM, /** The TOP. */
    TOP, /** The CENTRE. */
    CENTRE};

    /**
     * Checks if is adds the as tool window.
     *
     * @return true, if is adds the as tool window
     */
     boolean isAddAsToolWindow();

    /**
     * Sets the adds the as tool window.
     *
     * @param addAsToolWindow the new adds the as tool window
     */
     void setAddAsToolWindow(boolean addAsToolWindow);

    /**
     * Inits the.
     */
     void init();

    /**
     * Dispose.
     */
     void dispose();

    /**
     * Sets the component.
     *
     * @param component the new component
     */
     void setComponent(JComponent component);

    /**
     * Gets the component.
     *
     * @return the component
     */
     JComponent getComponent();

    /**
     * Gets the name.
     *
     * @return the name
     */
     String getName();

    /**
     * Sets the name.
     *
     * @param name the new name
     */
     void setName(String name);

    /**
     * Gets the title.
     *
     * @return the title
     */
     String getTitle();

    /**
     * Sets the title.
     *
     * @param title the new title
     */
     void setTitle(String title);

    /**
     * Gets the alignment.
     *
     * @return the alignment
     */
     Alignment getAlignment();

    /**
     * Sets the alignment.
     *
     * @param alignment the new alignment
     */
     void setAlignment(Alignment alignment);

    /**
     * Gets the icon.
     *
     * @return the icon
     */
     Icon getIcon();

    /**
     * Sets the icon.
     *
     * @param icon the new icon
     */
     void setIcon(Icon icon);

    /**
     * Checks if is preferences visible.
     *
     * @return true, if is preferences visible
     */
     boolean isPreferencesVisible();

    /**
     * Sets the preferences visible.
     *
     * @param preferencesVisible the new preferences visible
     */
     void setPreferencesVisible(boolean preferencesVisible);
}
