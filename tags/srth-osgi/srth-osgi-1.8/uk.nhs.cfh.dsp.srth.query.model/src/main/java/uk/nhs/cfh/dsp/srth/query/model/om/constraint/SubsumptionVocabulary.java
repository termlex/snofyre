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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint;

// TODO: Auto-generated Javadoc
/**
 * An enumeration of permissible subsumption flavours that can be used by a
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl.TerminologyConstraintImpl}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 20, 2010 at 10:06:19 PM
 */
public enum SubsumptionVocabulary {
    
    /** The SEL f_ o r_ an y_ typ e_ of. */
    SELF_OR_ANY_TYPE_OF, 
 
 /** The SEL f_ only. */
 SELF_ONLY, 
 
 /** The AN y_ typ e_ o f_ bu t_ no t_ self. */
 ANY_TYPE_OF_BUT_NOT_SELF
}
