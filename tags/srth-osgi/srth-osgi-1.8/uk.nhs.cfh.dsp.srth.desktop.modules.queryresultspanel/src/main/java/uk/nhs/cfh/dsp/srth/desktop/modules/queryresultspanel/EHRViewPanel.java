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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.model.table.ClinicalEntryTableModel;
import uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.renderer.QueryResultsTableCellRenderer;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.EHR;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEventEntry;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * A graphical view of an {@link EHR} object.
 * <br>Version : @#VersionNumber#@<br>Written by @author Jay Kola
 * <br>Created on May 13, 2009 at 9:50:50 PM
 * <br> Modified on Monday; November 30, 2009 at 11:27 AM to include modular services
 */
@SuppressWarnings("serial")
public class EHRViewPanel extends JPanel {

    /** The ehr. */
    private EHR ehr;

    /** The table models. */
    private List<ClinicalEntryTableModel> tableModels = new ArrayList<ClinicalEntryTableModel>();

    /** The all entries table model. */
    private ClinicalEntryTableModel allEntriesTableModel;

    /** The all findings table model. */
    private ClinicalEntryTableModel allFindingsTableModel;

    /** The all procedures table model. */
    private ClinicalEntryTableModel allProceduresTableModel;

    /** The all medications table model. */
    private ClinicalEntryTableModel allMedicationsTableModel;


    /**
     * Instantiates a new eHR view panel.
     */
    public EHRViewPanel() {
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents() {

        allEntriesTableModel = new ClinicalEntryTableModel();
        allFindingsTableModel = new ClinicalEntryTableModel();
        allProceduresTableModel = new ClinicalEntryTableModel();
        allMedicationsTableModel = new ClinicalEntryTableModel();
        tableModels.add(allEntriesTableModel);
        tableModels.add(allFindingsTableModel);
        tableModels.add(allProceduresTableModel);
        tableModels.add(allMedicationsTableModel);

        String[] names = {"All Entries", "Findings", "Procedures", "Medications"};

        // create tables for each model and add as tabs to a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        for(int i=0 ; i<tableModels.size(); i++)
        {
            JXTable table = new JXTable(tableModels.get(i));
            table.setColumnControlVisible(true);
            table.setHighlighters(HighlighterFactory.createAlternateStriping());

            // add to tabbed pane
            tabbedPane.addTab(names[i], new JScrollPane(table));
        }

        // add tabbed panel to this
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Sets the ehr.
     *
     * @param ehr the new ehr
     */
    public void setEhr(EHR ehr) {
        if (ehr != null)
        {
            this.ehr = ehr;
            populateFields(ehr);
        }
    }

    /**
     * Populate fields.
     *
     * @param ehr the ehr
     *
     */
    private void populateFields(EHR ehr){

        if(ehr != null)
        {
            allEntriesTableModel.populateDataList(ehr.getEntries());
            allFindingsTableModel.populateDataList(ehr.getClinicalFindings());
            allMedicationsTableModel.populateDataList(ehr.getMedications());
            // add both investigations and interventions to the procedures table
            Collection<BoundClinicalEventEntry> procedures = new ArrayList<BoundClinicalEventEntry>();
            procedures.addAll(ehr.getInterventions());
            procedures.addAll(ehr.getInvestigations());
            allProceduresTableModel.populateDataList(procedures);
            // set cell renderers
            setCellRenderers(this);
        }
    }

    /**
     * Sets the cell renderers.
     */
    private void setCellRenderers(Container container) {
        // loop through all components of this panel and if component is a JXTable, then set custom cell renderer
        for(int c=0; c<container.getComponentCount(); c++)

        //TODO TJI need to to get the TabPanel component and iterate over each of its tables and set the cellRenderer
            //
        {
            Component comp = container.getComponent(c);
            if(comp instanceof JXTable)
            {
                JXTable jxTable = (JXTable) comp;
                for(int i=0; i<jxTable.getColumnCount(true); i++)
                {
                    jxTable.getColumnExt(i).setCellRenderer(new QueryResultsTableCellRenderer());
                }

                jxTable.setHighlighters(HighlighterFactory.createAlternateStriping());
            }
            else if(comp instanceof Container)
            {
                setCellRenderers((Container)comp);
            }
        }
    }
}