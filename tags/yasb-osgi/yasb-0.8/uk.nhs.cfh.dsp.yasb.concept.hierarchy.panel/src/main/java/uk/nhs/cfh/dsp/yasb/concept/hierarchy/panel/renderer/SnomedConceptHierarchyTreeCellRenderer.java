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
package uk.nhs.cfh.dsp.yasb.concept.hierarchy.panel.renderer;

import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.tree.TreeCellRenderer} that is used to render the
 * hierarchy of a {@link uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 7, 2010 at 2:42:08 PM
 */
public class SnomedConceptHierarchyTreeCellRenderer implements TreeCellRenderer{

    /** The component. */
    private Component component;
    
    /** The render concept id. */
    private boolean renderConceptId = false;
    
    /** The prefer fsn over pt. */
    private boolean preferFSNOverPT = false;

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {

        // set component based on value
        if(value instanceof SnomedConcept)
        {
            String text = "";
            SnomedConcept concept = (SnomedConcept) value;
            String fullySpecifiedName = concept.getFullySpecifiedName();
            String preferredTerm = concept.getPreferredLabel();

            // if preferFSNOverPT  is true or if preferred term is null, we use the FSN
            if(isPreferFSNOverPT() || preferredTerm == null)
            {
                if (fullySpecifiedName != null)
                {
                    text = fullySpecifiedName;
                }
            }
            else
            {
                text = concept.getPreferredLabel();
            }

            // render concept ID if renderConceptIds is set to true
            if(isRenderConceptId())
            {
                text = text+" ("+concept.getConceptID()+")";
            }

            /*
                TODO -- see below set a very basic tool tip that renders the FSN/PT + concept. Need to replace this
                with a better HTML based tooltip that renders
                1. subsumption relationships
                2. synonyms
                3. concept id

              */

            component = new JLabel(text);
            //set tooltip
//            component.setToolTipText(getHTMLRendering(concept));
        }

        // set background selection color
        if(selected)
        {
            // set background color
            component.setBackground(UIManager.getColor("Tree.selectionBackground"));
        }
        else
        {
          component.setBackground(Color.WHITE);
        }
        
        return component;
    }

    /**
     * Gets the hTML rendering.
     *
     * @param concept the concept
     * @return the hTML rendering
     */
    private String getHTMLRendering(SnomedConcept concept) {

        String htmlText = "";
        String conceptLabel = "";
        if(isPreferFSNOverPT())
        {
            conceptLabel = concept.getPreferredLabel();
        }
        else
        {
            conceptLabel = concept.getFullySpecifiedName();
        }

        // add conceptLabel
        htmlText = "<b>Concept Label : </b>"+conceptLabel;
        // add concept id in italics
        htmlText = htmlText+"<br>"+"<b>Concept ID : </b><i>"+concept.getConceptID()+"</i>";

        return "<html>"+htmlText+"</html>";
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
     * Checks if is prefer fsn over pt.
     *
     * @return true, if is prefer fsn over pt
     */
    public boolean isPreferFSNOverPT() {
        return preferFSNOverPT;
    }

    /**
     * Sets the prefer fsn over pt.
     *
     * @param preferFSNOverPT the new prefer fsn over pt
     */
    public void setPreferFSNOverPT(boolean preferFSNOverPT) {
        this.preferFSNOverPT = preferFSNOverPT;
    }
}
