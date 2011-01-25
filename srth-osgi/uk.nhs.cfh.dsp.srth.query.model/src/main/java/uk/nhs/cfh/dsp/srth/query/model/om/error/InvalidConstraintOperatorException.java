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
package uk.nhs.cfh.dsp.srth.query.model.om.error;

// TODO: Auto-generated Javadoc
/**
 * A custom exception thrown when invalid {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint}s
 * are created.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jun 19, 2009 at 10:43:29 AM
 */

@SuppressWarnings("serial")
public class InvalidConstraintOperatorException extends RuntimeException{

	/** The message. */
	private String message = "Invalid Constraint Operator passed";

	/**
	 * Instantiates a new invalid constraint operator exception.
	 *
	 */
	public InvalidConstraintOperatorException() {
		super("Invalid Constraint Operator passed");
	}

	/**
	 * Instantiates a new invalid constraint operator exception.
	 * 
	 * @param msg the msg
	 */
	public InvalidConstraintOperatorException(String msg) {
		super("Invalid Constraint Operator passed"+msg);
		this.message = msg;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
}