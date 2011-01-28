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
package uk.nhs.cfh.dsp.srth.distribution;

import com.izforge.izpack.gui.IzPanelLayout;
import com.izforge.izpack.installer.InstallData;
import com.izforge.izpack.installer.InstallerFrame;
import com.izforge.izpack.installer.IzPanel;
import com.izforge.izpack.installer.ResourceManager;
import com.izforge.izpack.util.HyperlinkHandler;
import com.izforge.izpack.util.VariableSubstitutor;

import javax.swing.*;
import java.net.URL;

/**
 * A custom extension of {@link com.izforge.izpack.installer.IzPanel}. Most of the base code is from the
 * {@link com.izforge.izpack.panels.HTMLInfoPanel} and {@link com.izforge.izpack.panels.XInfoPanel}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 18, 2010 at 2:25:42 PM
 */
public class XHTMLInfoPanel extends IzPanel{

    /** Resource prefix for panel. */
    protected String panelResourcePrefixStr;

    /** Resource name for panel content. */
    protected String panelResourceNameStr;

    /**
     * The text area.
     */
    protected JEditorPane textArea;
    private String info;

    /**
     * The constructor.
     *
     * @param parent The parent.
     * @param idata  The installation data.
     */
    public XHTMLInfoPanel(InstallerFrame parent, InstallData idata)
    {
        this(parent,idata,"XHTMLInfoPanel");
    }

    /**
     * Alternate constructor with additional parameters.  For use with
     * subclasses.
     *
     * @param parent The parent.
     * @param idata  The installation data.
     * @param resPrefixStr prefix string for content resource name.
     * above content.
     */
    public XHTMLInfoPanel(InstallerFrame parent, InstallData idata,
                             String resPrefixStr)
    {
        super(parent, idata, new IzPanelLayout());
                   //setup given resource prefix and name:
        panelResourcePrefixStr = resPrefixStr;
        panelResourceNameStr = resPrefixStr + ".info";

        // We add the components
        try
        {
            textArea = new JEditorPane();
            textArea.setContentType("text/html; charset=utf-8");
            textArea.setEditable(false);
            textArea.addHyperlinkListener(new HyperlinkHandler());
            JScrollPane scroller = new JScrollPane(textArea);
            textArea.setPage(loadHTMLInfoContent());
                   //set caret so beginning of file is displayed:
            textArea.setCaretPosition(0);
            add(scroller, NEXT_LINE);
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
        getLayoutHelper().completeLayout();
    }

    /*
    * loads the content of the info resource as text so that it can be parsed afterwards
    */
    private URL loadHTMLInfoContent()
    {
        if (getMetadata() != null && getMetadata().getPanelid() != null)
        {
            try
            {
                String panelSpecificResName = panelResourcePrefixStr + '.' + this.getMetadata().getPanelid();
                String panelspecificResContent = ResourceManager.getInstance().getTextResource(panelSpecificResName);
                if (panelspecificResContent != null)
                {
                    panelResourceNameStr = panelSpecificResName;
                }
            }
            catch (Exception e)
            {
                // Those ones can be skipped
            }
        }

        try
        {
            return ResourceManager.getInstance().getURL(panelResourceNameStr);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Indicates wether the panel has been validated or not.
     *
     * @return Always true.
     */
    public boolean isValidated()
    {
        return true;
    }

        /**
     * Loads the info text.
     */
    private void loadInfo()
    {
        try
        {
            // We read it
            info = ResourceManager.getInstance().getTextResource("XHTMLInfoPanel.info");
        }
        catch (Exception err)
        {
            info = "Error : could not load the info text !";
        }
    }

        /**
     * Parses the text for special variables.
     */
    private void parseText()
    {
        try
        {
            // Initialize the variable substitutor
            VariableSubstitutor vs = new VariableSubstitutor(idata.getVariables());

            // Parses the info text
            info = vs.substitute(info, null);
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    public void panelActivate()
    {
        // load and parse text
        loadInfo();
        parseText();
        textArea.setText(info);
        //set caret so beginning of file is displayed:
        textArea.setCaretPosition(0);
    }

}
