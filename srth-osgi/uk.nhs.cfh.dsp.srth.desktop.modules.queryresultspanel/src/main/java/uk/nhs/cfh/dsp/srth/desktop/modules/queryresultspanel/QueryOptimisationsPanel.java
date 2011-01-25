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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel;

import uk.nhs.cfh.dsp.srth.query.transform.SubtypeGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} that displays the optimisations used by the current
 * {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService}. These parameters are passed
 * to the {@link uk.nhs.cfh.dsp.srth.query.transform.SubtypeGetter} that is used by the engine.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Aug 9, 2010 at 6:35:23 PM
 */
public class QueryOptimisationsPanel extends JPanel implements ActionListener{

    /** The subtype getter. */
    private SubtypeGetter subtypeGetter;
    
    /** The ignore tc table check box. */
    private JCheckBox ignoreTCTableCheckBox;

    /**
     * Instantiates a new query optimisations panel.
     *
     * @param subtypeGetter the subtype getter
     */
    public QueryOptimisationsPanel(SubtypeGetter subtypeGetter) {
        this.subtypeGetter = subtypeGetter;
    }

    /**
     * Empty constructor for IOC.
     */
    public QueryOptimisationsPanel() {
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){
        ignoreTCTableCheckBox = new JCheckBox(new AbstractAction("Ignore Transitive Closure Table") {
            public void actionPerformed(ActionEvent e) {
                subtypeGetter.setIgnoreTransitiveClosureTable(ignoreTCTableCheckBox.isSelected());   
            }
        });

        // create radio buttons for choosing uuid based optimisations
		JRadioButton allUUIDsButton = new JRadioButton("All UUIDs");
		allUUIDsButton.addActionListener(this);
		allUUIDsButton.setActionCommand("allUUIDS");

		JRadioButton distinctUUIDsButton = new JRadioButton("Distinct UUIDs");
        distinctUUIDsButton.setSelected(true);
        distinctUUIDsButton.addActionListener(this);
        distinctUUIDsButton.setActionCommand("distinctUUIDs");

		JRadioButton uuidsAndProxPrimitivesButton = new JRadioButton("UUIDs & Proximal Primitives");
		uuidsAndProxPrimitivesButton.addActionListener(this);
		uuidsAndProxPrimitivesButton.setActionCommand("uuidsAndProxPrim");

		JRadioButton focusConceptButton = new JRadioButton("Focus concepts");
		focusConceptButton.addActionListener(this);
		focusConceptButton.setActionCommand("focusConcepts");

		ButtonGroup bg = new ButtonGroup();
		bg.add(allUUIDsButton);
		bg.add(distinctUUIDsButton);
		bg.add(uuidsAndProxPrimitivesButton);
		bg.add(focusConceptButton);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));
		buttonsPanel.setBorder(BorderFactory.createTitledBorder("UUID Optimisation"));
		buttonsPanel.add(allUUIDsButton);
		buttonsPanel.add(distinctUUIDsButton);
		buttonsPanel.add(uuidsAndProxPrimitivesButton);
//		buttonsPanel.add(focusConceptButton);

        setLayout(new BorderLayout());
        setName("QueryOptionsPanel");
        add(ignoreTCTableCheckBox, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
    }

    /**
     * Sets the subtype getter.
     *
     * @param subtypeGetter the new subtype getter
     */
    public synchronized void setSubtypeGetter(SubtypeGetter subtypeGetter) {
        this.subtypeGetter = subtypeGetter;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String actionCommand = actionEvent.getActionCommand();

        if("allUUIDS".equals(actionCommand))
        {
            subtypeGetter.setUseDistinctUUIDs(false);
            subtypeGetter.setUseProximalPrimitives(true);
        }
        else if("distinctUUIDs".equals(actionCommand))
        {
            subtypeGetter.setUseDistinctUUIDs(true);
            subtypeGetter.setUseProximalPrimitives(false);
        }
        else if("uuidsAndProxPrim".equals(actionCommand))
        {
            subtypeGetter.setUseDistinctUUIDs(false);
            subtypeGetter.setUseProximalPrimitives(false);
        }
    }
}
