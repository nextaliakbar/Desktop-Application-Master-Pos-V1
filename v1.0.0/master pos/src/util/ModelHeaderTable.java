/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author usER
 */
public class ModelHeaderTable extends DefaultTableCellRenderer{

    public ModelHeaderTable() {
        setOpaque(true);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setHorizontalAlignment(JLabel.LEFT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(185,185,185));
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
    
    
}
