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
package uk.nhs.cfh.dsp.srth.desktop.modules.querycreationtreepanel;

import com.jidesoft.swing.JideButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXCollapsiblePane;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.normaliser.NormalFormGenerator;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerService;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.PropertyChangeTrackerServiceListener;
import uk.nhs.cfh.dsp.srth.desktop.modularframework.SelectionService;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.ConstraintFactory;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.SubsumptionVocabulary;
import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;
import uk.nhs.cfh.dsp.yasb.search.TerminologySearchService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that renders a
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 22, 2010 at 11:12:22 PM
 */
public class TerminologyConstraintPanel extends JPanel implements PropertyChangeTrackerServiceListener{

    /** The constraint. */
    private TerminologyConstraint constraint;

    /** The human readable render. */
    private HumanReadableRender humanReadableRender;

    /** The selection service. */
    private SelectionService selectionService;

    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;

    /** The application service. */
    private ApplicationService applicationService;

    /** The property change tracker service. */
    private PropertyChangeTrackerService propertyChangeTrackerService;

    /** The normal form generator. */
    private NormalFormGenerator normalFormGenerator;

    /** The terminology search service. */
    private TerminologySearchService terminologySearchService;

    /** The subsumption box. */
    private JComboBox subsumptionBox;

    /** The Constant CONSTRAINT_CHANGED. */
    private final static String CONSTRAINT_CHANGED = "CONSTRAINT_CHANGED";
    
    /** The Constant EXPRESSION_CHANGED. */
    private final static String EXPRESSION_CHANGED = "EXPRESSION_CHANGED";

    /** The simple close to user expression panel. */
    private SimpleCloseToUserExpressionPanel simpleCloseToUserExpressionPanel;
    
    /** The constraint factory. */
    private ConstraintFactory constraintFactory;

    /** The subsume all types check box. */
    private JCheckBox subsumeAllTypesCheckBox;

    /**
     * Instantiates a new terminology constraint panel.
     *
     * @param humanReadableRender the human readable render
     * @param selectionService the selection service
     * @param terminologyConceptDAO the terminology concept dao
     * @param applicationService the application service
     * @param propertyChangeTrackerService the property change tracker service
     * @param normalFormGenerator the normal form generator
     * @param terminologySearchService the terminology search service
     * @param constraintFactory the constraint factory
     */
    public TerminologyConstraintPanel(HumanReadableRender humanReadableRender, SelectionService selectionService,
                                      TerminologyConceptDAO terminologyConceptDAO,
                                      ApplicationService applicationService,
                                      PropertyChangeTrackerService propertyChangeTrackerService,
                                      NormalFormGenerator normalFormGenerator,
                                      TerminologySearchService terminologySearchService,
                                      ConstraintFactory constraintFactory) {
        this.humanReadableRender = humanReadableRender;
        this.selectionService = selectionService;
        this.terminologyConceptDAO = terminologyConceptDAO;
        this.applicationService = applicationService;
        this.propertyChangeTrackerService = propertyChangeTrackerService;
        this.normalFormGenerator = normalFormGenerator;
        this.terminologySearchService = terminologySearchService;
        this.constraintFactory = constraintFactory;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        setName(getClass().getCanonicalName());

        simpleCloseToUserExpressionPanel = new SimpleCloseToUserExpressionPanel(propertyChangeTrackerService, terminologySearchService,
                applicationService, selectionService, terminologyConceptDAO, humanReadableRender,
                normalFormGenerator);
        simpleCloseToUserExpressionPanel.initComponents();
        propertyChangeTrackerService.registerListener(simpleCloseToUserExpressionPanel);

        // add check box that allows user to toggle subsume all types
        subsumeAllTypesCheckBox = new JCheckBox(new AbstractAction("Include all types of") {
            public void actionPerformed(ActionEvent e) {
                if(subsumeAllTypesCheckBox.isSelected())
                {
                    setAndNotify(SubsumptionVocabulary.SELF_OR_ANY_TYPE_OF);
                }
                else
                {
                    setAndNotify(SubsumptionVocabulary.SELF_ONLY);
                }
            }
        });

        // add a combo box that allows user to specify subsumption
        SubsumptionVocabulary[] vocabularies = SubsumptionVocabulary.values();
        subsumptionBox = new JComboBox(vocabularies);
        // add listener to subsumptionBox
        subsumptionBox.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                Object selectedItem = subsumptionBox.getSelectedItem();
                if(selectedItem instanceof SubsumptionVocabulary)
                {
                    setAndNotify((SubsumptionVocabulary) selectedItem);
                }
            }
        });

        final JXCollapsiblePane collapsiblePane = new JXCollapsiblePane(new BorderLayout());
        collapsiblePane.add(new JLabel("Choose subsumption"), BorderLayout.WEST);
        collapsiblePane.add(subsumptionBox, BorderLayout.CENTER);
        collapsiblePane.setCollapsed(true);

        // set advanced button that displays the collapsible panel with more options
        JideButton advancedButton = new JideButton(new AbstractAction("V") {
            public void actionPerformed(ActionEvent e) {
                if (collapsiblePane.isCollapsed())
                {
                    collapsiblePane.setCollapsed(false);
                }
                else
                {
                    collapsiblePane.setCollapsed(true);
                }
            }
        });

        // add panel for subsumption options
        JPanel subsumptionPanel = new JPanel(new BorderLayout());
        subsumptionPanel.add(subsumeAllTypesCheckBox, BorderLayout.CENTER);
        subsumptionPanel.add(advancedButton, BorderLayout.EAST);
        subsumptionPanel.add(collapsiblePane, BorderLayout.SOUTH);
        // set layout and add closeToUserExpressionPanel and subsumptionBox with a label on top
        setLayout(new BorderLayout());
        add(simpleCloseToUserExpressionPanel, BorderLayout.CENTER);
        add(subsumptionPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the and notify.
     *
     * @param vocabulary the new and notify
     */
    private void setAndNotify(SubsumptionVocabulary vocabulary){

        TerminologyConstraint oldValue = getConstraint();
        if (oldValue != null && vocabulary != null)
        {
            // set subsumption value in constraint
            getConstraint().setSubsumption(vocabulary);
            // update subsumptionBox
            subsumptionBox.setSelectedItem(vocabulary);
            // update subsumeAllTypesCheckBox
            subsumeAllTypesCheckBox.setSelected(SubsumptionVocabulary.SELF_OR_ANY_TYPE_OF == vocabulary);
            // notify listeners
            propertyChangeTrackerService.firePropertyChanged(CONSTRAINT_CHANGED, oldValue, getConstraint(),
                    TerminologyConstraintPanel.this);
        }
    }

    /**
     * Gets the constraint.
     *
     * @return the constraint
     */
    public TerminologyConstraint getConstraint() {
        return constraint;
    }

    /**
     * Sets the constraint.
     *
     * @param constraint the new constraint
     */
    public void setConstraint(TerminologyConstraint constraint) {
        if (constraint != null)
        {
            this.constraint = constraint;
            // update simpleCloseToUserExpressionPanel
            simpleCloseToUserExpressionPanel.setExpression((CloseToUserExpression) constraint.getExpression());
            SubsumptionVocabulary subsumption = constraint.getSubsumption();

            if (subsumption != null)
            {
                // update subsumptionBox
                subsumptionBox.setSelectedItem(getConstraint().getSubsumption());
                // update subsumeAllTypesCheckBox
                subsumeAllTypesCheckBox.setSelected(SubsumptionVocabulary.SELF_OR_ANY_TYPE_OF == subsumption);
            }
        }
    }

    /**
     * Property changed.
     *
     * @param propertyName the property name
     * @param oldValue the old value
     * @param newValue the new value
     * @param source the source
     */
    public void propertyChanged(String propertyName, Object oldValue, Object newValue, Object source) {

        if(EXPRESSION_CHANGED.equalsIgnoreCase(propertyName) && newValue instanceof CloseToUserExpression
                && source == this.simpleCloseToUserExpressionPanel)
        {
            TerminologyConstraint terminologyConstraint = constraintFactory.getTerminologyConstraint((CloseToUserExpression) newValue, "Expression");
            propertyChangeTrackerService.firePropertyChanged(CONSTRAINT_CHANGED, getConstraint(), terminologyConstraint, this);
        }
    }
}
