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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.model.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom table model for displaying a {@link java.sql.ResultSet} object in a table
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Apr 3, 2009 at 12:00:49 PM
 */
@SuppressWarnings("serial")
public class ResultSetTableModel extends AbstractTableModel {

	/** The logger. */
	private static Log logger = LogFactory.getLog(ResultSetTableModel.class);
	
	/** The data list. */
	private List<List<Object>> dataList = new ArrayList<List<Object>>();
	
	/** The column names. */
	private String[] columnNames = {"Patient Id", "Concept Id", "Entry time", "Term Text"};

	/**
	 * Instantiates a new result set table model.
	 * 
	 * @param resultSet the result set
	 */
	public ResultSetTableModel(ResultSet resultSet) {

		// populate model
		populateModel(resultSet);
	}

	/**
	 * Instantiates a new result set table model.
	 */
	public ResultSetTableModel() {

	}

	/**
	 * Sets the column names from result set.
	 * 
	 * @param resultSet the new column names from result set
	 */
	public void setColumnNamesFromResultSet(ResultSet resultSet) {

		try
		{
			ResultSetMetaData metaData = resultSet.getMetaData();
			int colCount = metaData.getColumnCount();
			// reset column names array
			columnNames = new String[colCount];

			for(int i = 0; i<colCount; i++)
			{
				String colName = metaData.getColumnName(i+1);
				// convert colname to human readable form
				colName = convertDBColumnNameToJTableColumnName(colName);
				columnNames[i] = colName;
			}

			fireTableStructureChanged();
		} catch (SQLException e) {
            logger.warn("Error generating table structure from result set." +
                    "\nNested exception is : " + e.fillInStackTrace().getMessage());
		}
	}

	/**
	 * converts the table column name from the database style with underscores
	 * to a more human readable form.
	 * 
	 * @param dbColName the db col name
	 * 
	 * @return the string
	 */
	private String convertDBColumnNameToJTableColumnName(String dbColName){

		String jTableColumnName = "";
		/*
		 * we know the columns name style in the db looks like ENTRY_TIME_TABLE_NAME_SUB_QUERY_NUMBER
		 * So to convert this, we replace all underscores and create a human readable version as
		 * Entry Time # where # is the sub query id.
		 */
		// get sub query number
		String subQueryId= dbColName.substring(dbColName.lastIndexOf("_")+1);

		int subQueryNumber =0;
		try
		{
			if (! "ID".equalsIgnoreCase(subQueryId)) {
				subQueryNumber = Integer.parseInt(subQueryId);
			}
		} catch (NumberFormatException e) {
			logger.warn(e.fillInStackTrace());
		}
		//increment since sub query ids start from 0 and not 1
		subQueryNumber = subQueryNumber+1;


		if(dbColName.indexOf("Concept") >-1)
		{
			jTableColumnName = "Concept ID "+subQueryNumber;
		}
		else if(dbColName.indexOf("Entry_Time") >-1)
		{
			jTableColumnName = "Entry Time "+subQueryNumber;
		}
		else if(dbColName.indexOf("Free_Text_Entry") >-1)
		{
			jTableColumnName = "Term Text "+subQueryNumber;
		}
		else
		{
			jTableColumnName = dbColName.replaceAll("_", " ");
		}

		return jTableColumnName;
	}

	/**
	 * Populate model.
	 * 
	 * @param resultSet the result set
	 */
	public void populateModel(ResultSet resultSet){

		if(resultSet != null)
		{
			// get column names from result set
			setColumnNamesFromResultSet(resultSet);

			// clear data
			dataList.clear();

			try
			{
				while(resultSet.next())
				{
					List<Object> localList = new ArrayList<Object>();
					for(int i=0; i< columnNames.length; i++)
					{
						// add to local list
						localList.add(resultSet.getObject(i+1));
					}

					// add local list to data list
					dataList.add(localList);
				}
			} catch (SQLException e) {
				logger.warn(e.fillInStackTrace());
			}

			// notify listeners
			fireTableStructureChanged();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return dataList.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {
		return dataList.get(row).get(col);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	/**
	 * Gets the data list.
	 * 
	 * @return the data list
	 */
	public List<List<Object>> getDataList() {
		return dataList;
	}

	/**
	 * Gets the column names.
	 * 
	 * @return the column names
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * Clear.
	 */
	public void clear() {
		if(dataList.size() >0)
		{
			int lastIndex = dataList.size() -1;
			dataList.clear();
			fireTableRowsDeleted(0, lastIndex);
		}
	}

	/**
	 * Adds the all.
	 * 
	 * @param list the list
	 */
	public void addAll(List<List<Object>> list){
		if(list.size() > 0)
		{
			dataList.addAll(list);
			int lastIndex = dataList.size() -1;
			int firstIndex = lastIndex - (list.size() -1);
			fireTableRowsInserted(firstIndex, lastIndex);
		}
	}
}