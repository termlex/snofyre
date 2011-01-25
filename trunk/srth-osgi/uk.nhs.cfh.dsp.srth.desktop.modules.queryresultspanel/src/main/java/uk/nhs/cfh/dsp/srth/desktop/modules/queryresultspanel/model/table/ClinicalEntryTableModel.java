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
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.ClinicalFeatureEntry;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.MedicationEventEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.table.TableModel} that handles data that is a 
 * {@link java.util.Collection} of {@link uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry} objects.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 26-Mar-2010 at 09:22:05
 */
public class ClinicalEntryTableModel extends AbstractTableModel{

    /** The Constant initialListSize. */
    private static final int initialListSize = 10;
    
    /** The data list. */
    private List<List<Object>> dataList = new ArrayList<List<Object>>(initialListSize);
    
    /** The Constant PATIENT_ID. */
    private static final String PATIENT_ID = "Patient Id";
    
    /** The Constant ATTESTATION_TIME. */
    private static final String ATTESTATION_TIME = "Attestation Time";
    
    /** The Constant FREE_TEXT_ENTRY. */
    private static final String FREE_TEXT_ENTRY = "Free text entry";
    
    /** The Constant UUID. */
    private static final String UUID = "UUID";
    
    /** The Constant CTU_FORM. */
    private static final String CTU_FORM = "CTU Form";
    
    /** The Constant VALUE. */
    private static final String VALUE = "Value";
    
    /** The column names. */
    private String[] columnNames = {PATIENT_ID, ATTESTATION_TIME, FREE_TEXT_ENTRY, UUID, CTU_FORM, VALUE};
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ClinicalEntryTableModel.class);

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return dataList.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return dataList.get(rowIndex).get(columnIndex);
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
     * Sets the data list.
     *
     * @param dataList the new data list
     */
    public void setDataList(List<List<Object>> dataList) {
        this.dataList = dataList;
    }

    /**
     * Populate data list.
     *
     * @param entries the entries
     */
    public void populateDataList(Collection<? extends BoundClinicalEntry> entries){
        // clear existing dataList
        dataList.clear();

        // loop through entries and populate column contents from attributes
        for(BoundClinicalEntry entry : entries)
        {
            List<Object> rowObject = new ArrayList<Object>(initialListSize);

            // add entries based on column name
            for(String columnName : columnNames)
            {
                if(PATIENT_ID.equals(columnName)){
                    rowObject.add(entry.getPatientId());
                }
                else if(ATTESTATION_TIME.equals(columnName)){
                    rowObject.add(entry.getAttestationTime());
                }
                else if(FREE_TEXT_ENTRY.equals(columnName)){
                    rowObject.add(entry.getFreeTextEntry());
                }
                else if(UUID.equals(columnName)){
                    rowObject.add(entry.getUuid());
                }
                else if(CTU_FORM.equals(columnName)){
                    rowObject.add(entry.getCanonicalForm());
                }
                else if(VALUE.equals(columnName)){
                    // add more entries based on entryType
                    if(entry instanceof MedicationEventEntry)
                    {
                        MedicationEventEntry medicationEventEntry = (MedicationEventEntry) entry;
                        rowObject.add(medicationEventEntry.getDose());
                    }
                    else if(entry instanceof ClinicalFeatureEntry)
                    {
                        ClinicalFeatureEntry clinicalFeatureEntry = (ClinicalFeatureEntry) entry;
                        rowObject.add(clinicalFeatureEntry.getValue());
                    }
                    else
                    {
                        // add empty string
                        rowObject.add("");
                    }
                }
                else
                {
                    logger.warn("Unknown column name found : "+columnName);
                }
            }

            // add rowObject to dataList
            dataList.add(rowObject);
        }

        // notify listeners
        fireTableDataChanged();
    }

    /**
     * Clears the contents of the table model.
     */
    public void clear() {
        if(dataList.size() >0)
        {
            int lastIndex = dataList.size() -1;
            dataList.clear();
            fireTableRowsDeleted(0, lastIndex);
        }
        else
        {
            dataList.clear();
            fireTableDataChanged();
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
