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
package uk.nhs.cfh.dsp.srth.desktop.modules.resultexplanationpanel.renderer;

import org.jdesktop.swingx.JXLabel;
import uk.nhs.cfh.dsp.srth.query.converters.html.QueryExpressionHTMLRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * A tree cell renderer that generates the explanation for the results from the.
 *
 * {@link uk.nhs.cfh.dsp.srth.query.transform.sql.SQLQueryEngineService}.
 * <br>Version : @#VersionNumber#@<br>Written by @author Jay Kola
 * <br>Created on Apr 4, 2009 at 8:33:20 AM
 * <br> Modified on Tuesday; December 1, 2009 at 10:18 AM to include modular services.
 */
public class SQLResultsExplanationTreeCellRenderer implements TreeCellRenderer{

    /** The query expression html renderer. */
    private QueryExpressionHTMLRenderer queryExpressionHTMLRenderer;

    /**
     * Instantiates a new sQL results explanation tree cell renderer.
     * 
     * @param queryExpressionHTMLRenderer the query expression html renderer
     */
    public SQLResultsExplanationTreeCellRenderer(QueryExpressionHTMLRenderer queryExpressionHTMLRenderer) {
        this.queryExpressionHTMLRenderer = queryExpressionHTMLRenderer;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		JXLabel label = new JXLabel();
		label.setLineWrap(true);
		label.setMaxLineSpan(100);

		if(value instanceof QueryExpression)
		{
			QueryExpression expression = (QueryExpression) value;
			String htmlText = getHtmlRenderingForExpression(expression);
			label.setText(htmlText);
			String toolTipText = htmlText.replaceAll("AND", "<br>AND<br>");
			toolTipText = toolTipText.replaceAll("OR", "<br>OR<br>");
			label.setToolTipText(toolTipText);
//			tree.setRowHeight(label.getHeight()+50);
		}

		return label;
	}

	/**
	 * Gets the html rendering for expression.
	 * 
	 * @param expression the expression
	 * 
	 * @return the html rendering for expression
	 */
	private String getHtmlRenderingForExpression(QueryExpression expression){

		String html = "<html><b>Number of patients with </b>";
		html = html+queryExpressionHTMLRenderer.getHTMLHumanReadableLabelForExpression(expression)+"</html";

		return html;
	}
}