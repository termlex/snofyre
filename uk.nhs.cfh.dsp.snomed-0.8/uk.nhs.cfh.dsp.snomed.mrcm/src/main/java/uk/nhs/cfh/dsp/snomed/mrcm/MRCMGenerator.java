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
package uk.nhs.cfh.dsp.snomed.mrcm;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * An interface that generates the MRCM constraints from a given source.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 16, 2010 at 10:56:33 AM
 */
public interface MRCMGenerator {

    /**
     * Generate mrcm table.
     *
     * @param file the file
     */
    void generateMRCMTable(File file);
}
