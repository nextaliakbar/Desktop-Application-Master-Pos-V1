/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import javax.swing.JLayeredPane;

/**
 *
 * @author usER
 */
public class Background extends JLayeredPane{

    public Background() {
        setOpaque(false);
    }
    
    private float animate;
    private int header = 50;
    private boolean showPaint;

    public float getAnimate() {
        return animate;
    }

    public void setAnimate(float animate) {
        this.animate = animate;
        repaint();
    }

    public boolean isShowPaint() {
        return showPaint;
    }

    public void setShowPaint(boolean showPaint) {
        this.showPaint = showPaint;
    }
    
    private float easeOutQuint(float x) { 
        return (float) (1 - Math.pow(1 - x, 5));
    }
    
    private float easeInOutCirc(float x) {
        double v = x < 0.5 ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2 : (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2;
        return (float) v;
    }
    
    private Shape createShape(int location , int statrInit, int... points) {
        int width = getWidth();
        int height = getHeight();
        int ss = width / points.length;
        int size = location;
        
        GeneralPath gnp = new GeneralPath();
        int space = 0;
        int xs = 0;
        int ys = location - statrInit;
        for(int point : points) {
            point = size - point;
            int s = space + ss / 2;
            gnp.append(new CubicCurve2D.Double(xs, ys, s, ys, s, point, xs + ss, point), true);
            space += ss;
            xs += ss;
            ys = point;
        }
        gnp.lineTo(width, ys);
        gnp.lineTo(width, height);
        gnp.lineTo(0, height);
        
        return gnp;
    }

    @Override
    public void paint(Graphics g) {
        if(!showPaint) {
        super.paint(g);
        }
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int height = (int) (getHeight() * (1f - easeOutQuint(animate)));
        
        g2.setColor(new Color(255, 140, 173));
        g2.fill(createShape(height, 50, 70, 50, 100));
        
        g2.setColor(new Color(255, 84, 133));
        g2.fill(createShape(height, 80, 50, 100, 50, 100, 50));
        
        g2.setColor(new Color(204, 2, 60));
        g2.fill(createShape(height, 70, 20, 60, 20, 70));
        
        int bgHeight = (int) (getHeight() * (1f - easeInOutCirc(animate)));
        bgHeight += header;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, bgHeight, getWidth(), getHeight());
        g2.dispose();
        if(showPaint) {
            super.paint(g);
        }
        
    }
    
    
}
