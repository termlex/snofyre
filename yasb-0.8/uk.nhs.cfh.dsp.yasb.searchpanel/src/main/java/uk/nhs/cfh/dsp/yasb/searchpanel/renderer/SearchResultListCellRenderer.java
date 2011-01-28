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
package uk.nhs.cfh.dsp.yasb.searchpanel.renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;

import javax.swing.*;
import java.awt.*;


// TODO: Auto-generated Javadoc
/**
 * <br>A custom {@link javax.swing.ListCellRenderer} for the displays results. This uses a
 * Lucene {@link org.apache.lucene.document.Document} object to render the label.
 * 
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 23, 2008 at 4:26:13 PM
 * <br> Modified on Friday; December 4, 2009 at 11:41 AM to include modular services
 */
public class SearchResultListCellRenderer implements ListCellRenderer {

	/** The logger. */
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(SearchResultListCellRenderer.class);
	
	/** The current status icon. */
	private Icon currentStatusIcon = new ImageIcon(SearchResultListCellRenderer.class.getResource("resources/tag_green.png"));
	
	/** The limited status icon. */
	private Icon limitedStatusIcon = new ImageIcon(SearchResultListCellRenderer.class.getResource("resources/tag_red.png"));
	
	/** The ambiguous status icon. */
	private Icon ambiguousStatusIcon = new ImageIcon(SearchResultListCellRenderer.class.getResource("resources/tag_blue.png"));
	
	/** The duplicate status icon. */
	private Icon duplicateStatusIcon = new ImageIcon(SearchResultListCellRenderer.class.getResource("resources/tag_yellow.png"));
    
    /** The render concept id. */
    private boolean renderConceptId = false;
    
    /** The prefer fsn over pt. */
    private boolean preferFSNOverPT = false;

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {

		JLabel label = new JLabel();
		label.setOpaque(true);
		if(value instanceof Document)
		{
			// get field that has value of the fully specified name
			Document doc = (Document) value;
			if(isRenderConceptId())
			{
				label.setText("<html><b>"+doc.get("TERM")+"</b><font color=\"eee\">|"+doc.get("CONCEPTID")+"|</font></html>");
			}
			else
			{
				label.setText("<html><b>"+doc.get("TERM")+"</b></html>");
			}

			// set icon based on status
			String status = doc.get("STATUS");
			if("limited".equalsIgnoreCase(status))
			{
				label.setIcon(limitedStatusIcon);
			}
			else if("duplicate".equalsIgnoreCase(status))
			{
				label.setIcon(duplicateStatusIcon);
			}
			else if("ambiguous".equalsIgnoreCase(status))
			{
				label.setIcon(ambiguousStatusIcon);
			}
			else
			{
				label.setIcon(currentStatusIcon);
			}

            // always set tooltip to FSN + concept id
            label.setToolTipText("<html><b>"+doc.get("TERM")+"</b><font color=\"eee\">|"+doc.get("CONCEPTID")+"|</font></html>");
		}

		if(isSelected){
			label.setBackground(UIManager.getColor("Tree.selectionbackground"));
		}
		else{
			label.setBackground(UIManager.getColor("Tree.background"));
		}

		return label;
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