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
package uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.renderer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * A custom {@link javax.swing.table.TableCellRenderer} that renders the cells in
 * {@link uk.nhs.cfh.dsp.srth.desktop.modules.queryresultspanel.QueryResultsPanel}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 29-Mar-2010 at 16:04:01
 */
public class QueryResultsTableCellRenderer implements TableCellRenderer{

    /** The sdf. */
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    /* (non-Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        if(value instanceof Calendar)
        {
            Calendar cal = (Calendar) value;
            label.setText(sdf.format(cal.getTime()));
        }
        else if(value instanceof Date)
        {
            Date date = (Date) value;
            label.setText(sdf.format(date));
        }
        else if(value instanceof byte[])
        {
            byte[] bytes = (byte[]) value;
            label.setText(UUID.nameUUIDFromBytes(bytes).toString());
        }
        else
        {
            label.setText(value.toString());
        }

        return label;
    }
}
