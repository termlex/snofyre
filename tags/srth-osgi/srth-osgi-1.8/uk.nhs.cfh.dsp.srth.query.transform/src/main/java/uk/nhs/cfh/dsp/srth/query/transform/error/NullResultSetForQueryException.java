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
 * Custom Exception class for handling no result returned by executing
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on May 25, 2009 at 10:15:45 PM
 * <br>
 */
@SuppressWarnings("serial")
public class NullResultSetForQueryException extends RuntimeException {

	/** The message. */
	private String message = "Null result returned for query. " +
			"Please check that you have passed a legal query";

	/**
	 * Instantiates a new null result set for query exception.
	 */
	public NullResultSetForQueryException() {
		super("Null result returned for query. Please check that you have passed a legal query");
	}

	/**
	 * Instantiates a new null result set for query exception.
	 * 
	 * @param message the message
	 */
	public NullResultSetForQueryException(String message) {
		super("Null result returned for query. Please check that you have passed a legal query. "+message);
		// save local copy of message
		this.message = this.message+message;
	}


	/**
	 * Instantiates a new null result set for query exception.
	 * 
	 * @param message the message
	 * @param throwable the throwable
	 */
	public NullResultSetForQueryException(String message, Throwable throwable) {
		super("Null result returned for query. " +
				"Please check that you have passed a legal query. "+message, throwable);
		this.message = this.message+message+throwable.getMessage();
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
