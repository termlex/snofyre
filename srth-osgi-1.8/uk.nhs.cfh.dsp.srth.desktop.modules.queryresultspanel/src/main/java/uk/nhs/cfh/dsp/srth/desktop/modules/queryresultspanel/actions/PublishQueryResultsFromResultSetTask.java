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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.application.Task} that publishes the results of executing a
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}.
 * The results are incrementally published from the {@link java.sql.ResultSet}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 20, 2009 at 9:45:28 PM
 * <br> Modified on Monday; November 30, 2009 at 1:42 PM to include modular services.
 *
 */
public class PublishQueryResultsFromResultSetTask extends Task<Void, List<Object>> {

	/** The result set. */
	private ResultSet resultSet;
	
	/** The logger. */
	private static Log logger = LogFactory.getLog(PublishQueryResultsFromResultSetTask.class);


	/**
	 * Instantiates a new publish query results task.
	 * 
	 * @param applicationService the application service
	 * @param resultSet the result set
	 */
	public PublishQueryResultsFromResultSetTask(ApplicationService applicationService, ResultSet resultSet) {
		super(applicationService.getActiveApplication());
		this.resultSet = resultSet;
		setDescription("A task that populates a table model with the results of a query");
		setTitle("Populate Table Model Task");
	}

    /**
	 * Do in background.
	 * 
	 * @return the void
	 * 
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground() throws Exception {

		// loop through result set and publish all columns
		if(!isCancelled())
		{
			if(resultSet != null)
			{
				try
				{
					int colNum = resultSet.getMetaData().getColumnCount();
					while(resultSet.next())
					{
						List<Object> localList = new ArrayList<Object>();
						for(int i=0; i< colNum; i++)
						{
							// add to local list
							localList.add(resultSet.getObject(i+1));
						}

						// publish list
						publish(localList);
					}
				} catch (SQLException e) {
					logger.warn(e.fillInStackTrace().getMessage());
				}
			}
			else
			{
                logger.warn("Null result set passed. Please check you've executed a valid query.");
			}
		}
		return null;
	}

	/**
	 * Cancelled.
	 */
	@Override
	protected void cancelled() {
		setMessage("User cancelled task "+getTitle());
	}

	/**
	 * Succeeded.
	 * 
	 * @param arg0 the arg0
	 */
	@Override
	protected void succeeded(Void arg0) {
		setMessage("Finished displaying results");
	}
}