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
package uk.nhs.cfh.dsp.srth.simulator;

import uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine;

/**
 * An interface for a factory object that returns a {@link uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine}
 * based on the {@link uk.nhs.cfh.dsp.srth.simulator.engine.DataGenerationEngine.DataGenerationSource} specified.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 5, 2010 at 2:51:43 PM
 */
public interface DataGenerationEngineFactory {
    DataGenerationEngine getDataGenerationEngine(DataGenerationEngine.DataGenerationSource source);

    void recreateDatabase();
}
