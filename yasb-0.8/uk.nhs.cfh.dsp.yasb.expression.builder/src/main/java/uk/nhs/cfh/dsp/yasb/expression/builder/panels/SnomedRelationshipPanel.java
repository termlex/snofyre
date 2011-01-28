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
package uk.nhs.cfh.dsp.yasb.expression.builder.panels;

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.hyperlink.AbstractHyperlinkAction;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.dao.utils.TerminologyConceptUtils;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.SnomedConceptHierarchyPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that represents the rendering for a
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 5, 2010 at 12:02:06 PM
 */
public class SnomedRelationshipPanel extends JPanel{

    /** The relationship. */
    private SnomedRelationship relationship;
    
    /** The render concept id. */
    private boolean renderConceptId = false;
    
    /** The attribute button. */
    private JXHyperlink attributeButton;
    
    /** The operator label. */
    private JXHyperlink operatorLabel;
    
    /** The value button. */
    private JXHyperlink valueButton;
    
    /** The attribute operator map. */
    private Map<String, String> attributeOperatorMap = new HashMap<String, String>();
    
    /** The concept hierarchy panel. */
    private SnomedConceptHierarchyPanel conceptHierarchyPanel;
    
    /** The concept hierarchy dialog. */
    private JDialog conceptHierarchyDialog;
    
    /** The active frame. */
    private JFrame activeFrame;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;

    /**
     * Instantiates a new snomed relationship panel.
     *
     * @param selectionService the selection service
     * @param terminologyConceptDAO the terminology concept dao
     * @param applicationService the application service
     * @param humanReadableRender the human readable render
     */
    public SnomedRelationshipPanel(SelectionService selectionService, TerminologyConceptDAO terminologyConceptDAO,
                                   ApplicationService applicationService,
                                   HumanReadableRender humanReadableRender) {
        this.selectionService = selectionService;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.applicationService = applicationService;
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Instantiates a new snomed relationship panel.
     */
    public SnomedRelationshipPanel() {
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        // create attributeOperatorMap
        createAttributeOperatorMap();

        // create conceptHierarchyDialog
        createConceptHierarchyDialog();

        /*
            create three main panels that contain the attribute, operator and the value of the property.
            Note the Attribute and Value panels will contain labels which when clicked will open up a dialog
            for refining their contained values.
          */

        // create attributePanel action
        attributeButton = new JXHyperlink(new AbstractHyperlinkAction<SnomedConcept>() {
            public void actionPerformed(ActionEvent e) {
                conceptHierarchyPanel.setConcept(relationship.getRelationshipType());
                conceptHierarchyDialog.setVisible(true);
            }
        });

        // create operator label
        operatorLabel = new JXHyperlink();

        // create value label and add mouse action
        valueButton = new JXHyperlink(new AbstractHyperlinkAction<SnomedConcept>() {
            public void actionPerformed(ActionEvent e) {
                conceptHierarchyPanel.setConcept(relationship.getTargetConcept());
                conceptHierarchyDialog.setVisible(true);
            }
        });

        // set box layout and add panels
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(attributeButton);
        add(operatorLabel);
        add(valueButton);
    }

    /**
     * Creates the attribute operator map.
     */
    private void createAttributeOperatorMap() {
        // not yet implemented
    }

    /**
     * Creates the concept hierarchy dialog.
     */
    private synchronized void createConceptHierarchyDialog() {

        // wait for applicationService  to make a frame available
        while(activeFrame == null)
        {
            // check applicationService and set active frame
            if (applicationService != null && applicationService.getFrameView() != null)
            {
                activeFrame = applicationService.getFrameView().getActiveFrame();
            }
        }

        // wrap conceptHierarchyPanel in a dialog
        conceptHierarchyPanel = new SnomedConceptHierarchyPanel(terminologyConceptDAO,
                selectionService, applicationService);
        conceptHierarchyPanel.initComponents();

        conceptHierarchyDialog = new JDialog(activeFrame, "Concept Hierarchy", Dialog.ModalityType.DOCUMENT_MODAL);
        conceptHierarchyDialog.getContentPane().add(conceptHierarchyPanel);
        conceptHierarchyDialog.setPreferredSize(new Dimension(300, 400));
        conceptHierarchyDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        conceptHierarchyDialog.setLocationRelativeTo(activeFrame);
        conceptHierarchyDialog.pack();
    }


    /**
     * Populate fields.
     *
     * @param relationship the relationship
     */
    public void populateFields(SnomedRelationship relationship){

        String relationshipType = relationship.getRelationshipType();
        SnomedConcept attributeConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, relationshipType);
        //set attributeButton  text to relationship type
        if (attributeConcept == null)
        {
            attributeButton.setText(relationshipType);
        }
        else {
            attributeButton.setText(humanReadableRender.getHumanReadableLabel(attributeConcept));
        }
        
        // set operator value for attribute
        if(attributeOperatorMap.containsKey(relationshipType))
        {
            operatorLabel.setText(attributeOperatorMap.get(relationshipType));
        }
        else
        {
            operatorLabel.setText(" = ");
        }

        // set value
        String targetConceptId = relationship.getTargetConceptID();
        SnomedConcept targetConcept = TerminologyConceptUtils.getConceptForID(terminologyConceptDAO, targetConceptId);
        if (targetConcept == null)
        {
            valueButton.setText(targetConceptId);
        }
        else {
            valueButton.setText(humanReadableRender.getHumanReadableLabel(targetConcept));
        }

    }

    /**
     * Gets the relationship.
     *
     * @return the relationship
     */
    public SnomedRelationship getRelationship() {
        return relationship;
    }

    /**
     * Sets the relationship.
     *
     * @param relationship the new relationship
     */
    public void setRelationship(SnomedRelationship relationship) {
        this.relationship = relationship;
        // populate fields
        populateFields(getRelationship());
    }

    /**
     * Checks if is render concept id.
     *
     * @return true, if is render concept id
     */
    public boolean isRenderConceptId() {
        return renderConceptId;
    }

    /**
     * Sets the render concept id.
     *
     * @param renderConceptId the new render concept id
     */
    public void setRenderConceptId(boolean renderConceptId) {
        this.renderConceptId = renderConceptId;
    }

    /**
     * Sets the selection service.
     *
     * @param selectionService the new selection service
     */
    public synchronized void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * Sets the application service.
     *
     * @param applicationService the new application service
     */
    public synchronized void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
}
