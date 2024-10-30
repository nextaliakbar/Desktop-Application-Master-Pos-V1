/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author usER
 */
public class TableCellStatusRender extends DefaultTableCellRenderer{
    private StatusType type;
    public TableCellStatusRender(StatusType type) {
        this.type = type;
    }
    

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        component.setBackground(Color.WHITE);
        PanelStatus status = new PanelStatus(this.type);
        
        if(isSelected == false) {
            status.setBackground(new Color(255, 255, 255));
        } else {
            status.setBackground(component.getBackground());
        }
        
        return status;
    }
}
