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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.srth.demographics.PatientDAO;
import uk.nhs.cfh.dsp.srth.demographics.person.Patient;

import javax.swing.*;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * A graphical view of a {@link uk.nhs.cfh.dsp.srth.demographics.person.impl.PatientImpl} object.
 * <br>Version : @#VersionNumber#@<br>Written by @author Jay Kola
 * <br>Created on May 13, 2009 at 10:24:39 PM
 * <br> Modified on Monday; November 30, 2009 at 11:26 AM to include modular services
 */
@SuppressWarnings("serial")
public class PatientPanel extends JPanel {

    /** The logger. */
    private static Log logger = LogFactory.getLog(PatientPanel.class);

    /** The patient. */
    private Patient patient;

    /** The first name label. */
    private JLabel firstNameLabel;

    /** The gender label. */
    private JLabel genderLabel;

    /** The last name label. */
    private JLabel lastNameLabel;

    /** The dob label. */
    private JLabel dobLabel;

    /** The middle name label. */
    private JLabel middleNameLabel;

    /** The age years label. */
    private JLabel ageYearsLabel;

    /** The ehr panel. */
    private EHRViewPanel ehrPanel;

    /** The id label. */
    private JLabel idLabel;
    
    /** The patient dao. */
    private PatientDAO patientDAO;

    /**
     * Instantiates a new patient panel.
     *
     * @param patientDAO the patient dao
     */
    public PatientPanel(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    /**
     * Instantiates a new patient panel.
     */
    public PatientPanel() {
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents() {

        JPanel personalDetailsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        p1.add(new JLabel("<html><b>First Name : </b></html>"));
        firstNameLabel = new JLabel();
        p1.add(firstNameLabel);
        personalDetailsPanel.add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
        p2.add(new JLabel("<html><b>Gender : </b></html>"));
        genderLabel = new JLabel();
        p2.add(genderLabel);
        personalDetailsPanel.add(p2);

        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.LINE_AXIS));
        p3.add(new JLabel("<html><b>Last Name : </b></html>"));
        lastNameLabel = new JLabel();
        p3.add(lastNameLabel);
        personalDetailsPanel.add(p3);

        JPanel p4 = new JPanel();
        p4.setLayout(new BoxLayout(p4, BoxLayout.LINE_AXIS));
        p4.add(new JLabel("<html><b>Dob : </b></html>"));
        dobLabel = new JLabel();
        p4.add(dobLabel);
        personalDetailsPanel.add(p4);

        JPanel p5 = new JPanel();
        p5.setLayout(new BoxLayout(p5, BoxLayout.LINE_AXIS));
        p5.add(new JLabel("<html><b>Middle Name : </b></html>"));
        middleNameLabel = new JLabel();
        p5.add(middleNameLabel);
        personalDetailsPanel.add(p5);

        JPanel p6 = new JPanel();
        p6.setLayout(new BoxLayout(p6, BoxLayout.LINE_AXIS));
        p6.add(new JLabel("<html><b>Age in years : </b></html>"));
        ageYearsLabel = new JLabel();
        p6.add(ageYearsLabel);
        personalDetailsPanel.add(p6);

        JPanel p7 = new JPanel();
        p7.setLayout(new BoxLayout(p7, BoxLayout.LINE_AXIS));
        p7.add(new JLabel("<html><b>PatientId  : </b></html>"));
        idLabel = new JLabel();
        p7.add(idLabel);
        personalDetailsPanel.add(p7);

        ehrPanel = new EHRViewPanel();
        ehrPanel.initComponents();
        // set layout and add components
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Patient Details "));
        add(personalDetailsPanel, BorderLayout.NORTH);
        add(ehrPanel, BorderLayout.CENTER);
    }

    /**
     * Gets the patient.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets the patient.
     *
     * @param patient the new patient
     */
    public void setPatient(Patient patient) {
        if (patient != null)
        {
            this.patient = patient;
            populateFields(patient);
        }
    }

    /**
     * Sets the patient.
     *
     * @param patientID the new patient
     */
    public void setPatient(String patientID) {
        // use patientDAO to get matching patient
        Patient patient = patientDAO.findPatient(patientID);
        if(patient != null)
        {
            setPatient(patient);
        }
        else
        {
            logger.warn("No matching patient found for Id : "+patientID);
        }
    }

    /**
     * Populate fields.
     *
     * @param patient the patient
     */
    private void populateFields(Patient patient){

        // update fields
        firstNameLabel.setText(patient.getFirstName());
        lastNameLabel.setText(patient.getLastName());
        middleNameLabel.setText(patient.getMiddleName());
        dobLabel.setText(patient.getDob().getTime().toString());
        genderLabel.setText(patient.getGender().name());
        ageYearsLabel.setText(String.valueOf(patient.getAgeYears()));
        idLabel.setText(String.valueOf(patient.getId()));

        // update ehr view
        ehrPanel.setEhr(patient.getEhr());

//        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Sets the patient dao.
     *
     * @param patientDAO the new patient dao
     */
    public void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }
}