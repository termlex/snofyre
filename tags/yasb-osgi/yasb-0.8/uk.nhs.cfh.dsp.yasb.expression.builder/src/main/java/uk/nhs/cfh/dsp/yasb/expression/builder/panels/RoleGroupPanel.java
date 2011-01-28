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

import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;

import javax.swing.*;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that renders a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRoleGroup}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 23, 2010 at 12:43:10 PM
 */
public class RoleGroupPanel extends JScrollPane{

    /** The role group. */
    private SnomedRoleGroup roleGroup;
    
    /** The selection service. */
    private SelectionService selectionService;
    
    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;
    
    /** The render concept id. */
    private boolean renderConceptId = false;

    /**
     * Instantiates a new role group panel.
     *
     * @param selectionService the selection service
     * @param terminologyConceptDAO the terminology concept dao
     * @param applicationService the application service
     * @param humanReadableRender the human readable render
     */
    public RoleGroupPanel(SelectionService selectionService, TerminologyConceptDAO terminologyConceptDAO,
                                   ApplicationService applicationService, HumanReadableRender humanReadableRender) {
        this.selectionService = selectionService;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.applicationService = applicationService;
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Instantiates a new role group panel.
     */
    public RoleGroupPanel() {
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){
        setBorder(BorderFactory.createTitledBorder("Related properties"));
        setVerticalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Gets the role group.
     *
     * @return the role group
     */
    public SnomedRoleGroup getRoleGroup() {
        return roleGroup;
    }

    /**
     * Sets the role group.
     *
     * @param roleGroup the new role group
     */
    public void setRoleGroup(SnomedRoleGroup roleGroup) {
        if (roleGroup != null)
        {
            this.roleGroup = roleGroup;
            populateFields(getRoleGroup());
        }
    }

    /**
     * Populate fields.
     *
     * @param rg the rg
     */
    private void populateFields(SnomedRoleGroup rg){

        JPanel panel = new JPanel(new GridLayout(0, 1));
        for(SnomedRelationship r : rg.getRelationships())
        {
            // create new snomed relationshipPanel using r
            SnomedRelationshipPanel relationshipPanel = new SnomedRelationshipPanel(selectionService,
                    terminologyConceptDAO, applicationService, humanReadableRender);
            relationshipPanel.initComponents();
            relationshipPanel.setRelationship(r);
            // add to this panel
            panel.add(relationshipPanel);
        }

        // add panel to this scrollPane
        setViewportView(panel);
        revalidate();
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

    /**
     * Sets the human readable render.
     *
     * @param humanReadableRender the new human readable render
     */
    public synchronized void setHumanReadableRender(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }
}
