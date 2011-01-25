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
package uk.nhs.cfh.dsp.yasb.concept.panel.renderer;

import uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRelationshipPropertyExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.SnomedRoleGroupExpression;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedRelationship;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.tree.TreeCellRenderer} that renders a
 * {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept} that is wrapped inside a 
 * {@link uk.nhs.cfh.dsp.snomed.expression.model.ConceptExpression}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 5, 2010 at 11:50:27 AM
 */
public class SnomedConceptExpressionTreeCellRenderer implements TreeCellRenderer{

    /** The render concept ids. */
    private boolean renderConceptIds = false;
    
    /** The component. */
    private JComponent component;

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {

        // set component based on value passed
        if(value instanceof NormalFormExpression)
        {
            NormalFormExpression nfe = (NormalFormExpression) value;
            // get proximal primitives in nfe and create text
            String text = "";
            int counter = 0;
            for(SnomedConcept focusConcept : nfe.getFocusConcepts())
            {
                if(counter == 0)
                {
                    text = getStringForConcept(focusConcept);
                }
                else
                {
                    text = text+" + "+getStringForConcept(focusConcept);
                }

                // increment counter
                counter++;
            }

            component = new JLabel(text);
        }
        else if(value instanceof ConceptExpression)
        {
            ConceptExpression conceptExpression = (ConceptExpression) value;
            String text = getStringForConcept(conceptExpression.getConcept());

            component = new JLabel(text);
        }
        else if(value instanceof SnomedRelationshipPropertyExpression)
        {
            SnomedRelationshipPropertyExpression propertyExpression = (SnomedRelationshipPropertyExpression) value;
            // use contained relationship to generate text
            SnomedRelationship relationship = propertyExpression.getRelationship();
            if(relationship != null)
            {
                // add relationship name
                String text = relationship.getName();
                if(renderConceptIds)
                {
                    text = text+" ("+relationship.getRelationshipType()+")";
                }

                // add "=" symbol
                text = text+" = ";

                // get contained target concept and add to relationship if not set
                SnomedConcept targetConcept = relationship.getTargetConcept();
                if (targetConcept != null)
                {
                    // get text for targetConcept
                    text = text+getStringForConcept(targetConcept);
                }
                else {
                    text = text+relationship.getRelationshipType();
                }

                component = new JLabel(text);
            }
            else
            {
                component = new JLabel("Error! Relationship missing.");
            }
        }
        else if(value instanceof SnomedRoleGroupExpression)
        {
            component = new JLabel("Role Group");
        }

        // update UI
        SwingUtilities.updateComponentTreeUI(component);

        return component;
    }

    /**
     * Checks if is render concept ids.
     *
     * @return true, if is render concept ids
     */
    public boolean isRenderConceptIds() {
        return renderConceptIds;
    }

    /**
     * Sets the render concept ids.
     *
     * @param renderConceptIds the new render concept ids
     */
    public void setRenderConceptIds(boolean renderConceptIds) {
        this.renderConceptIds = renderConceptIds;
    }

    /**
     * Gets the string for concept.
     *
     * @param concept the concept
     * @return the string for concept
     */
    private String getStringForConcept(SnomedConcept concept){

        // get preferred term  of concept
        String text = concept.getPreferredLabel();

        // add id if set to renderConceptIds
        if(isRenderConceptIds())
        {
            text = text+" ("+concept.getConceptID()+")";
        }

        return text;
    }
}
