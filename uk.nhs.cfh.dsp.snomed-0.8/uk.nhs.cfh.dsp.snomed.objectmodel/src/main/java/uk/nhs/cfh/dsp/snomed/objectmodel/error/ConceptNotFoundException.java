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
package uk.nhs.cfh.dsp.snomed.objectmodel.error;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link RuntimeException} thrown when a concept with given Id is not found!
 *
 * <br> Written by @author jay
 * <br> Created on Dec 15, 2008 at 8:26:58 PM.
 * <br> Modified on Tuesday; December 8, 2009 at 6:51 PM for OSGI services
 */

@SuppressWarnings("serial")
public class ConceptNotFoundException extends RuntimeException {

    private String message = "No matching concept found for concept ID - ";
	/**
	 * Instantiates a new concept not found exception.
	 * 
	 * @param conceptID the id of the concept that was not found
	 */
	public ConceptNotFoundException(String conceptID) {
		super("No matching concept found for concept ID - "+conceptID);
        this.message = message+conceptID;
	}

	/**
	 * The Constructor.
	 * 
	 * @param conceptId the id of concept that was not found
     * @param t the throwable
	 */
	public ConceptNotFoundException(String conceptId, Throwable t) {
		super("No matching concept found for concept ID - "+conceptId, t);
        this.message = message+conceptId;
	}

    @Override
    public String getMessage() {
        return message;
    }
}
