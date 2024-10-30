/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import action.TableAction;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;


/**
 *
 * @author usER
 */
public class TableCellEditor extends DefaultCellEditor{
    private TableAction action;
    private boolean showEdit;
    private boolean showDelete;
    private boolean showView;
    public TableCellEditor(TableAction action, boolean showEdit, boolean showDelete, boolean showView) {
        super(new JCheckBox());
        this.action = action;
        this.showEdit = showEdit;
        this.showDelete = showDelete;
        this.showView = showView;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        PanelActionTable actionTable = new PanelActionTable(showEdit, showDelete, showView);
        actionTable.initiationAction(action, row);
        actionTable.setBackground(table.getSelectionBackground());
        return actionTable;
    }
    

    
    
    
}
