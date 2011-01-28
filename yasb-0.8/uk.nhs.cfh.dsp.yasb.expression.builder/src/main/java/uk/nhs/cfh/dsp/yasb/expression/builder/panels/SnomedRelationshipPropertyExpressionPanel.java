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
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.JPanel} type that displays a
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression}
 *
 * br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 5, 2010 at 1:47:53 PM
 */
public class SnomedRelationshipPropertyExpressionPanel extends JPanel{

    /** The property expression. */
    private SnomedRelationshipPropertyExpression propertyExpression;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(SnomedRelationshipPropertyExpressionPanel.class);
    
    /** The attribute label. */
    private JLabel attributeLabel;
    
    /** The operator label. */
    private JLabel operatorLabel;
    
    /** The value panel. */
    private JPanel valuePanel;
    
    /** The render concept id. */
    private boolean renderConceptId = false;
    
    /** The attribute operator map. */
    private Map<String, String> attributeOperatorMap = new HashMap<String, String>();

    /**
     * Inits the components.
     */
    public synchronized void initComponents(){

        // create attributeOperatorMap
        createAttributeOperatorMap();

        attributeLabel = new JLabel();
        attributeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2)
                {
                    logger.debug("Mouse double clicked on attribute label");
                }
            }
        });

        // create and add operator label
        operatorLabel = new JLabel();
        operatorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2)
                {
                    logger.debug("Mouse double clicked on operator label");
                }
            }
        });

        // create value panel
        valuePanel = new JPanel();

        // set main layout to box layout
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(attributeLabel);
        add(operatorLabel);
        add(valuePanel);
    }

    /**
     * Populate fields.
     *
     * @param propertyExpression the property expression
     */
    public void populateFields(SnomedRelationshipPropertyExpression propertyExpression){

        if(propertyExpression != null)
        {
            SnomedRelationship relationship = propertyExpression.getRelationship();
            String relationshipType = relationship.getRelationshipType();
            // get relationship name and set as text
            String attributeText = relationship.getName();
            if(isRenderConceptId())
            {
                attributeText = attributeText+"("+relationshipType+")";
            }

            // set text in attributeLabel
            attributeLabel.setText(attributeText);

            // get operator and set as operatorLabel text
            if(attributeOperatorMap.containsKey(relationshipType))
            {
                operatorLabel.setText(attributeOperatorMap.get(relationshipType));
            }
            else
            {
                operatorLabel.setText(" = ");
            }

            // update UI in a thread safe manner
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    /**
     * Creates the attribute operator map.
     */
    private void createAttributeOperatorMap() {
        // not yet implemented
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
     * Gets the property expression.
     *
     * @return the property expression
     */
    public SnomedRelationshipPropertyExpression getPropertyExpression() {
        return propertyExpression;
    }

    /**
     * Sets the property expression.
     *
     * @param propertyExpression the new property expression
     */
    public void setPropertyExpression(SnomedRelationshipPropertyExpression propertyExpression) {
        this.propertyExpression = propertyExpression;
        // populate fields
        populateFields(getPropertyExpression());
    }
}
