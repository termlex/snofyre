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
package uk.nhs.cfh.dsp.yasb.expression.renderer.model;

import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.table.TableModel} that displays a collection of
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}s.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 23, 2010 at 12:10:07 AM
 */
public class SnomedConceptsTableModel extends AbstractTableModel{

    /** The concepts. */
    private List<SnomedConcept> concepts = new ArrayList<SnomedConcept>();

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return concepts.size();
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
        return concepts.get(rowIndex);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
        return "Concept Name";
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return SnomedConcept.class;
    }

    /**
     * Populate model.
     *
     * @param concepts the concepts
     */
    public void populateModel(Collection<SnomedConcept> concepts){
        // clear existing list
        this.concepts.clear();

        // add new collection and notify listeners
        this.concepts.addAll(concepts);
        fireTableDataChanged();
    }
}
