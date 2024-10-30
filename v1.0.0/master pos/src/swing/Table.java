/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import component.Profile;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.ModelProfile;

/**
 *
 * @author usER
 */
public class Table extends JTable{    
    public Table() {
        setShowHorizontalLines(true);
        setGridColor(new Color(185, 185, 185));
        setRowHeight(40);
        setFont(new Font("sansserif", 0, 14));
        getTableHeader().setReorderingAllowed(false);
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(value instanceof ModelProfile) {
                    ModelProfile data = (ModelProfile) value;
                    Profile cell = new Profile(data);
                        if(isSelected) {
                            cell.setBackground(new Color(245, 245, 245));
                        } else {
                            cell.setBackground(new Color(255, 255, 255));
                        }
                    return cell;
                
                } else if(value instanceof StatusType) {
                    StatusType type = (StatusType) value;
                    PanelStatus status = new PanelStatus(type);
                    if(isSelected) {
                        status.setBackground(new Color(245, 245, 245));
                    } else {
                        status.setBackground(new Color(255, 255, 255));
                    }
                    return status;
                } else {
                    component.setBackground(new Color(255, 255, 255));
                    setBorder(noFocusBorder);
                    if(isSelected) {
                        component.setBackground(new Color(245, 245, 245));
                        component.setForeground(new Color(0, 0, 0));
                    } else {
                        component.setForeground(new Color(0, 0, 0));
                    } 
                    return component;
                }
            }
            
        });
    }
    
    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
    
    public void scrollPane(JScrollPane scroll) {
        scroll.getViewport().setBackground(new Color(255, 255, 255));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.setBorder(new EmptyBorder(5, 10, 5, 10));
    }
}
