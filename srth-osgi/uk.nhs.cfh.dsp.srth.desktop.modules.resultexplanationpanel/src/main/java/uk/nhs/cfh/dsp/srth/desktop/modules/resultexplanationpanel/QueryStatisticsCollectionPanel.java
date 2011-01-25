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
package uk.nhs.cfh.dsp.srth.desktop.modules.resultexplanationpanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXLabel;
import uk.nhs.cfh.dsp.srth.desktop.appservice.ApplicationService;
import uk.nhs.cfh.dsp.srth.query.converters.html.QueryExpressionHTMLRenderer;
import uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * A custom panel that displays the contents of a {@link uk.nhs.cfh.dsp.srth.query.transform.utils.QueryStatisticsCollection}
 * 
 * <br>Version : @#VersionNumber#@<br>Written by @author Jay Kola
 * <br>Created on Apr 6, 2009 at 6:01:28 PM
 * <br> Modified on Tuesday; December 1, 2009 at 10:15 AM to include modular services
 */
@SuppressWarnings({"serial"})
public class QueryStatisticsCollectionPanel extends JPanel implements ClipboardOwner{

    /** The collection. */
    private QueryStatisticsCollection collection;
    
    /** The human readable label. */
    private JXLabel humanReadableLabel;
    
    /** The commented steps text area. */
    private JTextArea commentedStepsTextArea;
    
    /** The query time label. */
    private JXLabel queryTimeLabel;
    
    /** The result count label. */
    private JXLabel resultCountLabel;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(QueryStatisticsCollectionPanel.class);
    
    /** The sql statements text area. */
    private JTextArea sqlStatementsTextArea;
    
    /** The application service. */
    private ApplicationService applicationService;
    
    /** The query expression html renderer. */
    private QueryExpressionHTMLRenderer queryExpressionHTMLRenderer;

    /**
     * Instantiates a new query statistics collection panel.
     * 
     * @param applicationService the application service
     * @param queryExpressionHTMLRenderer the query expression html renderer
     */
    public QueryStatisticsCollectionPanel(ApplicationService applicationService,
                                          QueryExpressionHTMLRenderer queryExpressionHTMLRenderer) {

        this.applicationService = applicationService;
        this.queryExpressionHTMLRenderer = queryExpressionHTMLRenderer;
    }

    /**
     * Inits the components.
     */
    public synchronized void initComponents() {

        resultCountLabel = new JXLabel("<html><b>Number of patients satisfying criteria in the query : </b></html>");
        resultCountLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        queryTimeLabel = new JXLabel("<html><b>Time taken to return results for query : </b></html>");
        queryTimeLabel.setHorizontalTextPosition(SwingConstants.LEFT);

        // create panels and add labels
        JPanel countPanel = new JPanel();
        countPanel.setLayout(new BoxLayout(countPanel, BoxLayout.LINE_AXIS));
        countPanel.add(resultCountLabel);
        countPanel.add(Box.createHorizontalGlue());
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.LINE_AXIS));
        timePanel.add(Box.createHorizontalGlue());
        timePanel.add(queryTimeLabel);
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.PAGE_AXIS));
        labelsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        labelsPanel.add(countPanel);
        labelsPanel.add(timePanel);
        // add empty panel for spacing
        labelsPanel.add(new JXLabel(" "));

        JPanel humanReadableLabelPanel = new JPanel(new BorderLayout());
        humanReadableLabelPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(),
                BorderFactory.createTitledBorder("Query Text")));
        humanReadableLabel = new JXLabel();
        humanReadableLabel.setLineWrap(true);
        humanReadableLabelPanel.add(humanReadableLabel, BorderLayout.CENTER);

        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(humanReadableLabelPanel, BorderLayout.CENTER);
        upperPanel.add(labelsPanel, BorderLayout.SOUTH);

        commentedStepsTextArea = new JTextArea();
        commentedStepsTextArea.setLineWrap(true);
        commentedStepsTextArea.setEditable(false);
        commentedStepsTextArea.setWrapStyleWord(true);
        // create panel for comments text area
        JPanel commentsPanel = createStatementsPanel(commentedStepsTextArea, "SQL Steps");

        sqlStatementsTextArea = new JTextArea();
        sqlStatementsTextArea.setLineWrap(true);
        sqlStatementsTextArea.setEditable(false);
        sqlStatementsTextArea.setWrapStyleWord(true);
        // create panel for sql statements
        JPanel sqlPanel = createStatementsPanel(sqlStatementsTextArea, "SQL Statements");

        // create a tabbed pane to contain the statements panels
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Steps", null, commentsPanel, "Commented steps executed in generating the result");
        tabbedPane.addTab("SQL", null, sqlPanel, "SQL Statements used in generating the result");

        // add components to this panel
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(550, 450));
        add(upperPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Creates the statements panel.
     * 
     * @param textArea the text area
     * @param title the title
     * 
     * @return the j panel
     */
    private synchronized JPanel createStatementsPanel(final JTextArea textArea, String title){

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(),
                BorderFactory.createTitledBorder(title)));

        // create buttons for copy and export
        JButton copyButton = new JButton(new AbstractAction("Copy"){

            public void actionPerformed(ActionEvent event) {
                StringSelection selection = new StringSelection(textArea.getSelectedText());
                // get contents of text area and copy to system clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, QueryStatisticsCollectionPanel.this);
            }
        });

        JButton exportButton = new JButton(new AbstractAction("Export"){

            public void actionPerformed(ActionEvent event) {
                // open a file dialog and export the contents
                FileDialog fd = new FileDialog(applicationService.getFrameView().getActiveFrame(),
                        "Select file to export to", FileDialog.SAVE);
                fd.setVisible(true);

                String fileName = fd.getFile();
                String fileDirectory = fd.getDirectory();

                if(fileDirectory != null && fileName!= null)
                {
                    File file = new File(fileDirectory, fileName);
                    try
                    {
                        String contents = textArea.getText();
                        FileWriter fw = new FileWriter(file);
                        fw.flush();
                        fw.write(contents);
                        fw.close();
                        // inform user
//						manager.getStatusMessageLabel().setText("Successfully saved contents to file "+fileName);
                        logger.info("Successfully saved contents to file "+fileName);

                    } catch (IOException e) {
                        logger.warn(e.fillInStackTrace());
//						manager.getStatusMessageLabel().setText("Errors saving contents to file "+fileName);
                    }
                }
            }
        });
        // create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(Box.createHorizontalStrut(200));
        buttonsPanel.add(copyButton);
        buttonsPanel.add(exportButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Gets the collection.
     * 
     * @return the collection
     */
    public QueryStatisticsCollection getCollection() {
        return collection;
    }

    /**
     * Sets the collection.
     * 
     * @param collection the new collection
     */
    public void setCollection(QueryStatisticsCollection collection) {
        this.collection = collection;
        populateFields(this.collection);
    }

    /**
     * Populate fields.
     * 
     * @param collection the collection
     */
    private void populateFields(final QueryStatisticsCollection collection) {

        // update fields in a separate thread to avoid refresh issues.
        Runnable runnable = new Runnable() {
            public void run() {
                int resultSetCount = collection.getResultSetSize();
                long queryTime = collection.getQueryTime();
                // update labels
                resultCountLabel.setText("<html><b>Number of patients satisfying criteria in the query : <font color =\"eee\">"+resultSetCount+"</font></b></html>");
                queryTimeLabel.setText("<html><b>Time taken to return results for query : <font color =\"eee\">"+queryTime+" milliseconds</font></b></html>");
                humanReadableLabel.setText("<html>"+queryExpressionHTMLRenderer.getHTMLHumanReadableLabelForExpression(collection.getExpression())+"</html");

                // update commented steps
                String commentedSteps = "";
                int counter = 0;
                for(String sql : collection.getCommentedSteps())
                {
                    if (counter >0)
                    {
                        commentedSteps = commentedSteps + "\n" + sql;
                    }
                    else
                    {
                        commentedSteps = sql;
                    }

                    counter++;
                }
                commentedStepsTextArea.setText(commentedSteps);

                // update sql statements
                String sqlStatements = "";
                int counter2 = 0;
                for(String sql : collection.getSqlSteps())
                {
                    if (counter2 >0)
                    {
                        sqlStatements = sqlStatements + "\n" + sql;
                    }
                    else
                    {
                        sqlStatements = sql;
                    }

                    counter2++;
                }
                sqlStatementsTextArea.setText(sqlStatements);
            }
        };

        SwingUtilities.invokeLater(runnable);
    }

    /* (non-Javadoc)
     * @see java.awt.datatransfer.ClipboardOwner#lostOwnership(java.awt.datatransfer.Clipboard, java.awt.datatransfer.Transferable)
     */
    public void lostOwnership(Clipboard arg0, Transferable arg1) {
        // do nothing
    }
}