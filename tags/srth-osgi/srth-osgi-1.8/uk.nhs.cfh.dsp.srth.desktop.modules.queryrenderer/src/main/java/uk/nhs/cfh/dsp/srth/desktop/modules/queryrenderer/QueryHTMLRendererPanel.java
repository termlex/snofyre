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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryrenderer;

import org.jdesktop.swingx.JXLabel;
import uk.nhs.cfh.dsp.srth.query.converters.html.QueryExpressionHTMLRenderer;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;
import uk.nhs.cfh.dsp.srth.query.service.QueryServiceListener;

import javax.swing.*;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * A custom panel that renders a human readable version of a.
 *
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryStatement}
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Dec 2, 2009 at 2:38:59 PM
 */
public class QueryHTMLRendererPanel extends JPanel implements QueryServiceListener{

    /** The label. */
    private JXLabel label;
    
    /** The query expression html renderer. */
    private QueryExpressionHTMLRenderer queryExpressionHTMLRenderer;

    /**
     * Instantiates a new query html renderer panel.
     * 
     * @param queryExpressionHTMLRenderer the query expression html renderer
     */
    public QueryHTMLRendererPanel(QueryExpressionHTMLRenderer queryExpressionHTMLRenderer) {

        this.queryExpressionHTMLRenderer = queryExpressionHTMLRenderer;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Active Query"));
        label = new JXLabel("No query available to render yet.");
        label.setLineWrap(true);
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setVerticalTextPosition(SwingConstants.TOP);
        add(label);

        // set names to allow for gui persistence
        label.setName(getClass().getName()+"Label");
        this.setName(getClass().getName());
    }

    /**
     * Query changed.
     * 
     * @param service the service
     * @param query the query
     * @param source the source
     */
    public void queryChanged(QueryService service, final QueryStatement query, Object source) {
        Runnable runnable = new Runnable() {
            public void run() {
                label.setText("<html>"+queryExpressionHTMLRenderer.getHTMLHumanReadableLabelForExpression(query)+"</html");
                label.revalidate();
                revalidate();
            }
        };

        SwingUtilities.invokeLater(runnable);
    }
}
