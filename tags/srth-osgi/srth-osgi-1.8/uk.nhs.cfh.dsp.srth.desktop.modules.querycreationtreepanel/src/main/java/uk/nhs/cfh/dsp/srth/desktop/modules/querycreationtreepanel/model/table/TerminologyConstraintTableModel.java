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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel.model.table;

import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A {@link javax.swing.table.TableModel} that renders a collection of
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 10:13:46 PM
 */
public class TerminologyConstraintTableModel extends AbstractTableModel{

    /** The constraints. */
    private List<TerminologyConstraint> constraints = new ArrayList<TerminologyConstraint>();

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return constraints.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return 1;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return constraints.get(rowIndex);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
        return "Excluded Terms";
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return TerminologyConstraint.class;
    }

    /**
     * Adds the row.
     * 
     * @param constraint the constraint
     */
    public void addRow(TerminologyConstraint constraint){

		if(constraint != null)
		{
			// add to existing constraints
			constraints.add(constraint);
			// notify listeners
			fireTableRowsInserted(constraints.size() -1, constraints.size());
		}
	}

	/**
	 * Delete row.
	 * 
	 * @param row the row
	 */
	public void deleteRow(int row){

		// remove from constraints
		constraints.remove(row);
		// notify listeners
		fireTableRowsDeleted(constraints.size() -1, constraints.size());
	}

    /**
     * Populate model.
     * 
     * @param constraints the constraints
     */
    public void populateModel(Collection<TerminologyConstraint> constraints){
        // clear existing constriants
        this.constraints.clear();

        this.constraints.addAll(constraints);
        // notify listeners
        fireTableDataChanged();
    }

    /**
     * Gets the constraint at row.
     *
     * @param row the row
     * @return the constraint at row
     */
    public TerminologyConstraint getConstraintAtRow(int row){
        return constraints.get(row);
    }
}
