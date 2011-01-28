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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXHyperlink;
import uk.nhs.cfh.dsp.snomed.converters.human.readable.HumanReadableRender;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// TODO: Auto-generated Javadoc
/**
 * A {@link javax.swing.JPanel} that renders a {@link uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 5, 2010 at 1:26:20 PM
 */
public class ConceptPanel extends JPanel{

    /** The render concept id. */
    private boolean renderConceptId = false;
    
    /** The concept. */
    private SnomedConcept concept;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ConceptPanel.class);
    
    /** The concept label. */
    private JXHyperlink conceptLabel;
    
    /** The human readable render. */
    private HumanReadableRender humanReadableRender;

    /**
     * Instantiates a new concept panel.
     *
     * @param humanReadableRender the human readable render
     */
    public ConceptPanel(HumanReadableRender humanReadableRender) {
        this.humanReadableRender = humanReadableRender;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){
        conceptLabel = new JXHyperlink();
        conceptLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2)
                {
                    logger.debug("Mouse double clicked on concept expression label");
                }
            }
        });

        // set layout to box layout
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(conceptLabel);
    }

    /**
     * Populate fields.
     *
     * @param snomedConcept the snomed concept
     */
    private void populateFields(SnomedConcept snomedConcept){
        // set text in conceptLabel
        conceptLabel.setText(humanReadableRender.getHumanReadableLabel(snomedConcept));
        // refresh UI
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Gets the concept.
     *
     * @return the concept
     */
    public SnomedConcept getConcept() {
        return concept;
    }

    /**
     * Sets the concept.
     *
     * @param concept the new concept
     */
    public void setConcept(SnomedConcept concept) {
        if (concept != null)
        {
            this.concept = concept;
            populateFields(getConcept());
        }
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
        humanReadableRender.setRenderConceptIds(renderConceptId);
        this.renderConceptId = renderConceptId;
    }
}
