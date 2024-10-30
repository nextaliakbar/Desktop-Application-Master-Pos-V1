/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author usER
 */
public class ModelRenderTable extends DefaultTableCellRenderer{
    
    private int columnTable;
    public ModelRenderTable(int columnTable) {
        setHorizontalAlignment(JLabel.LEFT);
        this.columnTable = columnTable;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(column != columnTable) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setBackground(new Color(255,255,255));
            setBorder(noFocusBorder);
            
            if(isSelected) {
                component.setForeground(new Color(135, 15, 50));
            } else {
                component.setForeground(new Color(0,0,0));
            }
            
            return component;
        } else {
            return null;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(185,185,185));
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
    
}
