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

import groovy.ui.Console;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.desktop.modules.scripting.GroovyConsoleService;

/**
 * A concrete implementation of a {@link uk.nhs.cfh.dsp.srth.desktop.modules.scripting.GroovyConsoleService}
 *
 * Based on an example by Bruce Fancher
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 6, 2011 at 6:19:01 PM
 */
public class GroovyConsoleServiceImpl extends AbstractGroovyService implements GroovyConsoleService {

    private Thread thread;
    private Console console;
    private boolean autoClearOutput;
    private static Log logger = LogFactory.getLog(GroovyConsoleServiceImpl.class);

    public GroovyConsoleServiceImpl() {
        super();
    }

    public void launch() {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    if (console == null) {
                        console = new Console(createBinding());
                    }
                    // set autoclear property
                    console.setAutoClearOutput(isAutoClearOutput());
                    console.run();
                }
                catch (Exception e) {
                    logger.warn("Nested exception is : " + e.fillInStackTrace());
                }
            }
        };

        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void destroy() {
        if (console != null) {
            console.exit();
        }
        super.destroy();
    }

    public boolean isAutoClearOutput() {
        return autoClearOutput;
    }

    public void setAutoClearOutput(boolean autoClearOutput) {
        this.autoClearOutput = autoClearOutput;
    }
}
