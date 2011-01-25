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
package uk.nhs.cfh.dsp.srth.desktop.uiframework.app.impl;

import com.jidesoft.swing.JideButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.ResourceMap;
import uk.nhs.cfh.dsp.srth.desktop.dependencies.utils.ImageUtils;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

// TODO: Auto-generated Javadoc

/**
 * A custom {@link javax.swing.JDialog} for displaying application information.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author jay
 * <br>Created on Nov 22, 2009 at 7:57:33 PM.
 */
public class ModularApplicationAboutDialog extends JDialog{

    /** The main panel. */
    private JPanel mainPanel;

    /** The info pane. */
    private JTextPane infoPane;

    /** The resource map. */
    private ResourceMap resourceMap;
    private URL featuresHtmlURL;
    private URL dependenciesHtmlURL;

    /** The logger. */
    private static Log logger = LogFactory.getLog(ModularApplicationAboutDialog.class);
    private String appTitle;
    private String appDesc;
    private String appVersionMajor;
    private String buildId;
    private String buildNumber;
    private String authorName;
    private String authorContact;
    private String vendor;
    private String propertiesFilePath;

    /**
     * Instantiates a new sRTH desktop application about.
     *
     */
    public ModularApplicationAboutDialog() {
        setModalityType(ModalityType.APPLICATION_MODAL);
        resourceMap = org.jdesktop.application.Application.getInstance(ModularDockingApplication.class)
                .getContext().getResourceMap(ModularDockingApplication.class);
    }

    /**
     * Inits the components.
     */
    public void initComponents() {

        // initialise gui
        mainPanel = new JPanel();
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(ImageUtils.getIcon(ImageUtils.IconName.LOGO_WHITE));
        logoLabel.setBackground(Color.WHITE);
        logoLabel.setOpaque(true);
        infoPane = new JTextPane();
        infoPane.setEditorKit(new HTMLEditorKit());
        infoPane.setText(createHTMLText());
        infoPane.setEditable(false);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(logoLabel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(infoPane), BorderLayout.CENTER);
        createButtonsPanel();

        getContentPane().add(mainPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 500));
        setResizable(true);
    }

    /**
     * Creates the html text.
     *
     * @return the string
     */
    private String createHTMLText(){

        String htmlText ="<font name=\"Verdana\"><b>"+ appTitle +"</b>"
                +"<p><b>Version : </b>"+ appVersionMajor +"."
                +"<br><b>Build : </b>"+ buildId +"-"+ buildNumber +"</p>"
                +"<p><b>Author : </b>"+ authorName
                +"<br><b>Contact : </b>"+ authorContact
                +"<p><b>Copyright</b>(c) : "+ vendor
                +"<br><b>Decription</b>" +"<br><i>"+ appDesc +"</i>"
                +"</font";

        htmlText = "<html>"
                +"<table border=\"0\" cellspacing=\"5\" cellpadding=\"5\">"
                +"<tr>"+htmlText+"</tr>"
                +"</table>"
                +"</html>";

        return htmlText;
    }

    /**
     * Creates the buttons panel.
     */
    private void createButtonsPanel(){

        JideButton aboutButton = new JideButton(new AbstractAction("About"){

            public void actionPerformed(ActionEvent arg0) {
                // show features in info panel
                infoPane.setText(createHTMLText());
            }
        });

        JideButton featuresButton = new JideButton(new AbstractAction("Features"){

            public void actionPerformed(ActionEvent arg0) {
                // show features in info panel
                if (featuresHtmlURL == null)
                {
                    featuresHtmlURL = ModularApplicationAboutDialog.class.getResource("resources/features.html");
                }

                try
                {
                    infoPane.setEditorKit(new HTMLEditorKit());
                    infoPane.setPage(featuresHtmlURL);
                } catch (IOException e) {
                    logger.warn("Nested exception is : " + e.fillInStackTrace());
                }
            }
        });

        JButton libsButton = new JideButton(new AbstractAction("Libraries"){

            public void actionPerformed(ActionEvent arg0) {
                // show dependencies in info panel
                if (dependenciesHtmlURL == null)
                {
                    dependenciesHtmlURL = ModularApplicationAboutDialog.class.getResource("resources/dependencies.html");
                }

                try
                {
                    infoPane.setEditorKit(new HTMLEditorKit());
                    infoPane.setPage(dependenciesHtmlURL);
                } catch (IOException e) {
                    logger.warn("Nested exception is : " + e.fillInStackTrace());
                }
            }
        });

        JButton closeButton = new JButton(new AbstractAction("Close"){

            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 0));
        buttonsPanel.add(aboutButton);
        buttonsPanel.add(featuresButton);
        buttonsPanel.add(libsButton);
        buttonsPanel.add(closeButton);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void setPropertiesFileLocation(String propertiesFilePath) throws MalformedURLException {

        this.propertiesFilePath = propertiesFilePath;
        
        File propsFile = new File(propertiesFilePath);
        Properties props = new Properties();
        if(propsFile.exists() && propsFile.canRead())
        {
            try
            {
                props.load(new FileInputStream(propsFile));
            }
            catch (IOException e) {
                logger.warn("Error reading properties file. Nested exception is : " + e.fillInStackTrace());
            }
        }

        if(props.keySet().size() < 1)
        {
            appTitle = resourceMap.getString("Application.title");
            appDesc = resourceMap.getString("Application.description");
            appVersionMajor = resourceMap.getString("Application.version.major");
            buildId = resourceMap.getString("Application.build.id");
            buildNumber = resourceMap.getString("Application.build.no");
            authorName = resourceMap.getString("Application.author");
            authorContact = resourceMap.getString("Application.author.contact");
            vendor = resourceMap.getString("Application.vendor");
        }
        else
        {
            appTitle = props.getProperty("Application.title");
            appDesc = props.getProperty("Application.description");
            appVersionMajor = props.getProperty("Application.version.major");
            buildId = props.getProperty("Application.build.id");
            buildNumber = props.getProperty("Application.build.no");
            authorName = props.getProperty("Application.author");
            authorContact = props.getProperty("Application.author.contact");
            vendor = props.getProperty("Application.vendor");
            File dependenciesHTMLFile = new File(props.getProperty("application.features.html.path"));
            dependenciesHtmlURL = dependenciesHTMLFile.toURI().toURL();
            File featuresHTMLFile = new File(props.getProperty("application.dependencies.html.path"));
            featuresHtmlURL = featuresHTMLFile.toURI().toURL();
        }
    }
}