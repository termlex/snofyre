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
package uk.nhs.cfh.dsp.srth.desktop.modules.plugin.manager;

import org.jdesktop.swingx.action.OpenBrowserAction;

import java.net.URI;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.Action} that displays the Felix web console plugin in
 * a new browser window.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 20, 2010 at 8:13:10 PM
 */
public class ShowPluginManagerAction extends OpenBrowserAction{

    /**
     * Instantiates a new show plugin manager action.
     *
     * @param uri the uri
     */
    public ShowPluginManagerAction(URI uri) {
        super(uri);
        putValue(NAME, "Show Plugin Manager");
        putValue(SHORT_DESCRIPTION, "Show Plugin Manager");
        putValue(LONG_DESCRIPTION, "Displays the plugin management console in a new browser window.");
    }
}
