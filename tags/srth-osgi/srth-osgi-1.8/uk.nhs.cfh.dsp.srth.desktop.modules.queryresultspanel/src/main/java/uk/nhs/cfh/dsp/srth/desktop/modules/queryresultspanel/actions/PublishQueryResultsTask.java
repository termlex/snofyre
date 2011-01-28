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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Task;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.ClinicalFeatureEntry;
import uk.nhs.cfh.dsp.srth.information.model.impl.om.ehr.entry.MedicationEventEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.BoundClinicalEntry;
import uk.nhs.cfh.dsp.srth.information.model.om.ehr.entry.ClinicalEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link org.jdesktop.application.Task} that publishes the result of a query execution
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 26-Mar-2010 at 10:22:59
 */
public class PublishQueryResultsTask extends Task<Void, List<Object>>{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The entries. */
    private Collection<ClinicalEntry> entries;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(PublishQueryResultsTask.class);
    
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

    /**
     * Instantiates a new publish query results task.
     *
     * @param applicationService the application service
     * @param entries the entries
     */
    public PublishQueryResultsTask(ApplicationService applicationService, Collection<ClinicalEntry> entries) {
        super(applicationService.getActiveApplication());
        this.entries = entries;
    }

    /* (non-Javadoc)
     * @see org.jdesktop.swingworker.SwingWorker#doInBackground()
     */
    @Override
    protected Void doInBackground() throws Exception {
        // loop through entries and populate column contents from attributes
        for(ClinicalEntry e : entries)
        {
            // cast every entry to BoundClinicalEntry
            BoundClinicalEntry entry = (BoundClinicalEntry) e;
            List<Object> rowObject = new ArrayList<Object>(columnNames.length);

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

            // publish rowObject
            publish(rowObject);
        }

        return null;
    }
}
