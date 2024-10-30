/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.ModelNotifikasi;

/**
 *
 * @author usER
 */
public class TableNotifikasi extends JTable{    
    public TableNotifikasi() {
        setShowHorizontalLines(true);
        setGridColor(new Color(185, 185, 185));
        setRowHeight(125);
        setFont(new Font("sansserif", 0, 14));
        getTableHeader().setReorderingAllowed(false);
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if(value instanceof ModelNotifikasi) {
                    ModelNotifikasi data = (ModelNotifikasi) value;
                    Notifications cell = new Notifications(data);
                    if(!data.isStatusSudahDibaca()) {
                        cell.setBackground(new Color(252, 232, 232));
                    }
                    
                    return cell;              
                } 
                
                return null;
            }
            
        });
    }
    
    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
    
    public void setRowCount(int count) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.setRowCount(count);
    }
    
    public void scrollPane(JScrollPane scroll) {
        scroll.getViewport().setBackground(new Color(255, 255, 255));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.setBorder(new EmptyBorder(5, 10, 5, 10));
    }
}
