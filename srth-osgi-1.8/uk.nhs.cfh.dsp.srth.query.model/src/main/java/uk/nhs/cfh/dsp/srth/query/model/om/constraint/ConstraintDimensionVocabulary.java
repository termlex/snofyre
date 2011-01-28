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
/**
 * 
 */
package uk.nhs.cfh.dsp.srth.query.model.om.constraint;


// TODO: Auto-generated Javadoc
/**
 * An enumeration of allowable dimensions/units for a {@link Constraint} object.
 * This should really implemented in conjunction with error check to ensure the
 * dimension is restricted by {@link ConstraintType}. Ideally we should be able
 * to populate this enumeration from an external knowledge source.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 7, 2009 at 2:53:30 PM
 * <br>
 */
public enum ConstraintDimensionVocabulary {

	/** The SECOND. */
	SECOND, 
 
 /** The HOUR. */
 HOUR, 
 
 /** The DAY. */
 DAY, 
 
 /** The WEEK. */
 WEEK, 
 
 /** The MONTH. */
 MONTH, 
 
 /** The YEAR. */
 YEAR,
	
	/** The NULL. */
	NULL,  // allows us to ignore any associated units and dimensions
	/** The DATE. */
  DATE  // allows us to specify that the constraint value is a date
}
