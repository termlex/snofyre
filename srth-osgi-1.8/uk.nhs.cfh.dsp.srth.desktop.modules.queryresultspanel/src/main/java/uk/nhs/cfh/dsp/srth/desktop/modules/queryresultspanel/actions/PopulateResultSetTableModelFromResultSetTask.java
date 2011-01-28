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

import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.QueryResultsPanel;
import uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.model.table.ResultSetTableModel;

import java.sql.ResultSet;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A {@link org.jdesktop.application.Task} that populate a {@link ResultSetTableModel} based on the
 * results of a query. This task extends {@link PublishQueryResultsFromResultSetTask} to
 * update the {@link ResultSetTableModel} itself.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 20, 2009 at 10:07:03 PM
 * <br> Modified on Monday; November 30, 2009 at 1:47 PM to include modular services
 *
 */
public class PopulateResultSetTableModelFromResultSetTask extends PublishQueryResultsFromResultSetTask {

	/** The table model. */
	private ResultSetTableModel tableModel;
	
	/** The result set view. */
	private QueryResultsPanel resultSetView;

	/**
	 * Instantiates a new populate result set table model task.
	 * 
	 * @param applicationService the application service
	 * @param resultSet the result set
	 * @param model the table model
	 * @param resultsPanel the results panel
	 */
	public PopulateResultSetTableModelFromResultSetTask(ApplicationService applicationService, ResultSet resultSet,
                                           ResultSetTableModel model, QueryResultsPanel resultsPanel) {
		super(applicationService, resultSet);
        this.tableModel = model;
        this.resultSetView = resultsPanel;

		// set column names from result set
		tableModel.setColumnNamesFromResultSet(resultSet);
		// clear existing model contents
		tableModel.clear();
	}

	/**
	 * Process.
	 * 
	 * @param list the list
	 */
	@Override
	protected void process(List<List<Object>> list) {
		if (! isCancelled()) {
			// get table model and update
			tableModel.addAll(list);
		}
	}

	/* (non-Javadoc)
	 * @see main.java.uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.actions.PublishQueryResultsFromResultSetTask#succeeded(java.lang.Void)
	 */
	@Override
	protected void succeeded(Void arg0) {

		resultSetView.renderColumns();
		setMessage("Successfully displayed all results");
	}
}