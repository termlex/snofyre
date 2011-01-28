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
package uk.nhs.cfh.dsp.srth.simulator.utils;

import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

/**
 * An interface specification for a service that returns a random type of a given
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} or a
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 8, 2010 at 3:23:42 PM
 */
public interface RandomSubtypeGenerator {

    SnomedConcept getSubTypeConcept(SnomedConcept parentConcept);

    CloseToUserExpression getSubTypeExpression(CloseToUserExpression expression);

    CloseToUserExpression getSubTypeExpression(SnomedConcept parentConcept);

    boolean isUseNormalFormForRendering();

    void setUseNormalFormForRendering(boolean useNormalFormForRendering);
}
