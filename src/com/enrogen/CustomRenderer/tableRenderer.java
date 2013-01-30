//////////////////////////////////////////////////////////////////////////
//com.enrogen.CustomRenderer.tableRenderer
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.CustomRenderer;

import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.ToolTipManager;

public class tableRenderer extends DefaultTableCellRenderer {
    private boolean highlightingON = false;
    private List highlightRowsList = null;
    private List toolTipList = null;

    public void setTableHighlightON() {
        highlightingON = true;
    }

    public void setHighLightRowsList(List highlightList) {
        highlightRowsList = highlightList;
    }

    public void setToolTipList(List TTL) {
        toolTipList = TTL;
    }

    public void setToolTipDelays(int showDelay, int dismissDelay) {
        ToolTipManager.sharedInstance().setInitialDelay(showDelay);
        ToolTipManager.sharedInstance().setDismissDelay(dismissDelay);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {

        Component comp = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,rowIndex,vColIndex);

        if (highlightingON) {
            if(highlightRowsList.contains(rowIndex)) {
                comp.setForeground(CustomRenderer.TABLE_HIGHLIGHT_COLOR);
            } else {
                comp.setForeground(CustomRenderer.TABLE_FOREGROUND_DEFAULT_COLOR);
            }
        }

        setToolTipText(toolTipList.get(rowIndex).toString());

        if (isSelected) {
        }

        // cell (and perhaps other cells) are selected
        if (hasFocus) {
            // this cell is the anchor and the table has the focus
        }
        return comp;
    }
}
