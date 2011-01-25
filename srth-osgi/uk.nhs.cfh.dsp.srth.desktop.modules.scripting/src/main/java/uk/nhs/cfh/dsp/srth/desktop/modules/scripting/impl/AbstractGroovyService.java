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
package uk.nhs.cfh.dsp.srth.desktop.modules.scripting.impl;

import groovy.lang.Binding;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.modules.scripting.GroovyConsoleService;

import java.util.Map;

/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.srth.desktop.modules.scripting.GroovyConsoleService}
 *
 * Based on the example by Bruce Fancher
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 6, 2011 at 6:12:12 PM
 */
public abstract class AbstractGroovyService implements GroovyConsoleService{

    private static Log logger = LogFactory.getLog(AbstractGroovyService.class);

    private Map<String, Object> bindings;
    private boolean launchAtStart;
    private Thread serverThread;

    public AbstractGroovyService() {
        super();
    }

    public AbstractGroovyService(Map<String, Object> bindings) {
        this();
        this.bindings = bindings;
    }

    public void launchInBackground() {
        logger.info("Launching GroovyService in background");
        serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    launch();
                }
                catch (Exception e) {
                    logger.warn("Nested exception is : " + e.fillInStackTrace());
                }
            }
        };

        serverThread.setDaemon(true);
        serverThread.start();
    }

    public abstract void launch();

    protected Binding createBinding() {
        Binding binding = new Binding();

        if (bindings != null)  {
            for (Map.Entry<String, Object> nextBinding : bindings.entrySet()) {
                binding.setVariable(nextBinding.getKey(), nextBinding.getValue());
            }
        }

        return binding;
    }

    public void initialize() {
        if (launchAtStart) {
            launchInBackground();
        }
    }

    public void destroy() {
        logger.info("Destroyed Groovy Service");
    }

    public void setBindings(final Map<String, Object> bindings) {
        this.bindings = bindings;
    }

    public boolean isLaunchAtStart() {
        return launchAtStart;
    }

    public void setLaunchAtStart(boolean launchAtStart) {
        this.launchAtStart = launchAtStart;
    }
}
