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
package uk.nhs.cfh.dsp.srth.desktop.actions.querycrud.core.actions;

import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.query.converters.xml.QueryExpressionXMLConverter;
import uk.nhs.cfh.dsp.srth.query.service.QueryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

// TODO: Auto-generated Javadoc
/**
 * An action that loads a {@link uk.nhs.cfh.dsp.srth.query.model.om.impl.QueryStatementImpl}
 * from an XML file.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 28, 2009 at 12:45:31 PM
 */
public class LoadQueryFromFileAction extends AbstractAction{

    /** The application service. */
    private ApplicationService applicationService;
    
    /** The query service. */
    private QueryService queryService;
    
    /** The query expression xml converter. */
    private QueryExpressionXMLConverter queryExpressionXMLConverter;

    /**
     * Instantiates a new load query from file action.
     *
     * @param applicationService the application service
     * @param queryService the query service
     * @param queryExpressionXMLConverter the query expression xml converter
     */
    public LoadQueryFromFileAction(ApplicationService applicationService, QueryService queryService,
                                   QueryExpressionXMLConverter queryExpressionXMLConverter) {
        super("Load Query from file", new ImageIcon(LoadQueryFromFileAction.class.getResource("resources/fileopen.png")));
        this.applicationService = applicationService;
        this.queryService = queryService;
        this.queryExpressionXMLConverter = queryExpressionXMLConverter;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O,
        	    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(SHORT_DESCRIPTION, "Loads a query from a file");
        putValue(LONG_DESCRIPTION, "Loads an existing query from a file and sets it to active query");
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

    	LoadSavedQueryTask task = new LoadSavedQueryTask(applicationService, queryService, queryExpressionXMLConverter);
    	applicationService.getActiveApplication().getContext().getTaskService().execute(task);
    }
}