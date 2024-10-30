/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

/**
 *
 * @author usER
 */
public class Status extends JLabel{
    private StatusType type;

    public StatusType getType() {
        return type;
    }

    public void setType(StatusType type) {
        this.type = type;
        setText(type.toString());
        repaint();
    }
    
    public Status() {
        setFont(new Font("sansserif",1, 14));
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(type != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp;
            if(type == StatusType.Wait) {
                gp = new GradientPaint(0, 0, new Color(76, 175, 80), 0, getHeight(), new Color(76, 175, 80));
            } else if(type == StatusType.Finish) {
                gp = new GradientPaint(0, 0, new Color(33, 150, 243), 0, getHeight(), new Color(33, 150, 243)); 
            } else if(type == StatusType.Cancel){
                gp = new GradientPaint(0, 0, new Color(135, 15, 50), 0, getHeight(), new Color(135, 15, 50));
            } else if(type == StatusType.Send) {
                gp = new GradientPaint(0, 0, new Color(190, 4, 196), 0, getHeight(), new Color(190, 4, 196));
            } else if(type == StatusType.Accept) {
                gp = new GradientPaint(0, 0, new Color(0, 153, 0), 0, getHeight(), new Color(0, 153, 0));    
            } else if(type == StatusType.Habis) {
                gp = new GradientPaint(0, 0, new Color(135, 15, 50), 0, getHeight(), new Color(135, 15, 50));      
            } else {
                gp = new GradientPaint(0, 0, new Color(194, 146, 4), 0, getHeight(), new Color(194, 146, 4));           
            }
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 1, 1);
        }
        super.paintComponent(g);
    }
}
