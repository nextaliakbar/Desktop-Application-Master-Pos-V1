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
public class TableCellActionRender extends DefaultTableCellRenderer{

    private boolean showEdit;
    private boolean showDelete;
    private boolean showView;
    public TableCellActionRender(boolean showEdit, boolean showDelete, boolean showView) {
        this.showEdit = showEdit;
        this.showDelete = showDelete;
        this.showView = showView;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        component.setBackground(new Color(255, 255, 255));
        PanelActionTable actionTable = new PanelActionTable(showEdit, showDelete, showView);
        
        if(isSelected) {
            actionTable.setBackground(component.getBackground());
        }
        
        return actionTable;
    }
    
    
}
