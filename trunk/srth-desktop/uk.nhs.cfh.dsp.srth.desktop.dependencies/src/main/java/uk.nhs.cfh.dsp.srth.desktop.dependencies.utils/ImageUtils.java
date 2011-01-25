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
package uk.nhs.cfh.dsp.srth.desktop.dependencies.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * An utility class for accessing application specific icons
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 19, 2010 at 9:59:46 PM
 */
public class ImageUtils {

    public enum IconName{
        WARNING_ICON_16, WARNING_ICON_48, ERROR_ICON_16, ERROR_ICON_48, LOGO_WHITE,
        HELP_16, TERMINAL_48
    }

    public static BufferedImage getImage(String imageFileName) {
        try
        {
            return ImageIO.read(ImageUtils.class.getResourceAsStream(imageFileName));
        }
        catch (IOException e) {
            throw new NullPointerException();
        }
    }

    public static Icon getIcon(String imageFileName) {
        return new ImageIcon(getImage(imageFileName));
    }

    public static Icon getIcon(IconName iconName){
        if(iconName == IconName.WARNING_ICON_16)
        {
            return getIcon("resources/messagebox_warning_16x16.png");
        }
        else if(iconName == IconName.WARNING_ICON_48)
        {
            return getIcon("resources/messagebox_warning_big.png");
        }
        else if(iconName == IconName.ERROR_ICON_16)
        {
            return getIcon("resources/messagebox_critical_16x16.png");
        }
        else if(iconName == IconName.ERROR_ICON_48)
        {
            return getIcon("resources/messagebox_critical_big.png");
        }
        else if(iconName == IconName.LOGO_WHITE)
        {
            return getIcon("resources/logo-white.png");
        }
        else if(iconName == IconName.HELP_16)
        {
            return getIcon("resources/help.png");
        }
        else if(iconName == IconName.TERMINAL_48)
        {
            return getIcon("resources/xterm.png");
        }
        else
        {
            throw new EnumConstantNotPresentException(IconName.class, iconName.name());
        }

    }
}
