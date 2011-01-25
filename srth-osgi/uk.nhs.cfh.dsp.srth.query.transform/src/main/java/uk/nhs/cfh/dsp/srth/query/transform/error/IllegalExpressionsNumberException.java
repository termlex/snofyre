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
package uk.nhs.cfh.dsp.srth.query.transform.error;

/**
 * A custom exception to handle illegal number of expressions in a
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on May 25, 2009 at 9:29:15 PM
 * <br>
 */
@SuppressWarnings("serial")
public class IllegalExpressionsNumberException extends RuntimeException {

	/** The message. */
	private String message = "Illegal Number of expression in query expression passed.";
	
	/**
	 * Instantiates a new illegal expressions number exception.
	 */
	public IllegalExpressionsNumberException() {
		super("Illegal Number of expression in query expression passed.");
	}

	/**
	 * Instantiates a new illegal expressions number exception.
	 * 
	 * @param message the message
	 */
	public IllegalExpressionsNumberException(String message) {
		super("Illegal Number of arguments passed."+message);
		// save local copy of error message
		this.message = this.message+message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
