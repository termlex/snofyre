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
package uk.nhs.cfh.dsp.srth.information.model.impl.internal;

import uk.nhs.cfh.dsp.srth.information.model.impl.EHRFactory;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.EHRImpl;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link uk.nhs.cfh.dsp.srth.information.model.impl.EHRFactory}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 4:41:17 PM
 */
public class EHRFactoryImpl implements EHRFactory {

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.EHRFactory#getEHR(java.lang.String)
     */
    public EHR getEHR(long patientID){
        return new EHRImpl(patientID);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.srth.information.model.impl.EHRFactory#getEHR()
     */
    public EHR getEHR(){
        return new EHRImpl();
    }
}
