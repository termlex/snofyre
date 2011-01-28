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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.model.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 16, 2009 at 1:05:59 PM
 * <br>.
 */
@SuppressWarnings("serial")
public class ConstraintTableModel extends AbstractTableModel {

	/** The constraints. */
	private List<Constraint> constraints = new ArrayList<Constraint>();
	
	/** The column names. */
	private String[] columnNames = {"Field", "Operator", "Value"};
	
	/** The logger. */
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(ConstraintTableModel.class);
	
	/**
	 * The Enum Operator.
	 */
	private enum Operator{
/** The EQUA l_ to. */
EQUAL_TO , 
 /** The LES s_ tha n_ exclusive. */
 LESS_THAN_EXCLUSIVE, 
 /** The GREATE r_ tha n_ exclusive. */
 GREATER_THAN_EXCLUSIVE,
		
		/** The LES s_ tha n_ inclusive. */
		LESS_THAN_INCLUSIVE, 
 /** The GREATE r_ tha n_ inclusive. */
 GREATER_THAN_INCLUSIVE, 
 /** The BETWEEN. */
 BETWEEN};

	/**
	 * Instantiates a new constraint table model.
	 * 
	 * @param constraints the constraints
	 */
	public ConstraintTableModel(List<Constraint> constraints) {
		this.constraints = new ArrayList<Constraint>(constraints);
		populateConstraints(constraints);
	}

	/**
	 * Populate constraints.
	 * 
	 * @param cons the cons
	 */
	public void populateConstraints(List<Constraint> cons) {

		if(cons != null)
		{
			// clear contents
			constraints.clear();
			//add to constraints
			for(Constraint c : cons)
			{
				constraints.add(c);
			}

			// notify listeners
			fireTableDataChanged();
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
		return constraints.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {

		Constraint constraint = constraints.get(row);
		String name = constraint.getName();
		String operator = "";
		String value = "";

		// return operator
		if(constraint instanceof DataConstantConstraint)
		{
			DataConstantConstraint dcc = (DataConstantConstraint) constraint;
			operator = Operator.EQUAL_TO.name();
			value = dcc.value().toString();
		}
		else if(constraint instanceof DataRangeConstraint)
		{
			DataRangeConstraint drc = (DataRangeConstraint) constraint;
			operator = Operator.BETWEEN.name();
			value = drc.getLowerBound()+" , "+drc.getUpperBound();
		}
		else if(constraint instanceof DataRangeFacetConstraint)
		{
			DataRangeFacetConstraint dfc = (DataRangeFacetConstraint) constraint;
			value = dfc.value().toString();

			// get facet and use its value
			RangeFacetVocabulary facet = dfc.getFacet();
			if(facet == RangeFacetVocabulary.MAX_EXCLUSIVE)
			{
				operator = Operator.LESS_THAN_EXCLUSIVE.name();
			}
			else if(facet == RangeFacetVocabulary.MAX_INCLUSIVE)
			{
				operator = Operator.LESS_THAN_INCLUSIVE.name();
			}
			else if(facet == RangeFacetVocabulary.MIN_EXCLUSIVE)
			{
				operator = Operator.GREATER_THAN_EXCLUSIVE.name();
			}
			else if(facet == RangeFacetVocabulary.MIN_INCLUSIVE)
			{
				operator = Operator.GREATER_THAN_INCLUSIVE.name();
			}
		}

		if(col == 0)
		{
			// return mapped column name
			return name;
		}
		else if(col == 1)
		{
			return operator;
		}
		else
		{
			return value;
		}
	}

	/**
	 * Adds the row.
	 * 
	 * @param constraint the constraint
	 */
	public void addRow(Constraint constraint){

		if(constraint != null)
		{
			constraints.add(constraint);

			// notify listeners
			fireTableRowsInserted(constraints.size() -1, constraints.size());
		}
	}

	/**
	 * Delete row.
	 * 
	 * @param constraint the constraint
	 */
	public void deleteRow(Constraint constraint){
		if(constraint != null)
		{
			constraints.remove(constraint);
			// notify listeners
			fireTableRowsDeleted(constraints.size()-1, constraints.size());
		}
	}

	/**
	 * Delete row.
	 * 
	 * @param row the row
	 */
	public void deleteRow(int row){
		deleteRow(getConstraintAtRow(row));
	}

	/**
	 * Gets the constraint at row.
	 * 
	 * @param row the row
	 * 
	 * @return the constraint at row
	 */
	public Constraint getConstraintAtRow(int row){
		return constraints.get(row);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}