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
package uk.nhs.cfh.dsp.srth.desktop.appservice.utils;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.jidesoft.plaf.LookAndFeelFactory;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

/**
 * An utility class that implements a static method to set Look and Feel to System default value.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 23, 2010 at 12:20:48 PM
 */
public class LookAndFeelUtils {

    private static Logger logger = Logger.getLogger(LookAndFeelUtils.class.getName());
    private static final int dim = 18;

    /**
     * Sets the default lnf.
     */
    public static void setDefaultLNF(){

        UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
        Options.setDefaultIconSize(new Dimension(dim, dim));

        String lafName = Options.getCrossPlatformLookAndFeelClassName();
        // set look and feel
        if(LookUtils.IS_OS_WINDOWS || LookUtils.IS_OS_MAC)
        {
            lafName = UIManager.getSystemLookAndFeelClassName();
        }

        logger.info("Setting look and feel to : " + lafName);
        // set the look n feel
        try
        {
            UIManager.setLookAndFeel(lafName);
            LookAndFeelFactory.installDefaultLookAndFeel();
            LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        } catch (Exception e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace());
        }
    }
}
