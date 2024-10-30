/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import action.BlankPlotChatRender;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author usER
 */
public class BlankPlotChart extends JComponent{
    private final DecimalFormat format = new DecimalFormat("#,##0.##");
    private NiceScale niceScale;
    private double maxValue;
    private double minValue;
    private int labelCount;
    private String valuesFormat = "#,##0.##";
    private BlankPlotChatRender blankPlotChatRender;

    public NiceScale getNiceScale() {
        return niceScale;
    }

    public void setNiceScale(NiceScale niceScale) {
        this.niceScale = niceScale;
    }

    public double getMaxValues() {
        return maxValue;
    }

    public void setMaxValues(double maxValues) {
        this.maxValue = maxValues;
        niceScale.setMax(maxValues);
        repaint();
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public int getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(int labelCount) {
        this.labelCount = labelCount;
    }

    public String getValuesFormat() {
        return valuesFormat;
    }

    public void setValuesFormat(String valuesFormat) {
        this.valuesFormat = valuesFormat;
        format.applyPattern(valuesFormat);
    }

    public BlankPlotChatRender getBlankPlotChatRender() {
        return blankPlotChatRender;
    }

    public void setBlankPlotChatRender(BlankPlotChatRender blankPlotChatRender) {
        this.blankPlotChatRender = blankPlotChatRender;
    }

    public BlankPlotChart() {
        setBackground(new Color(255, 255, 255));
        setOpaque(false);
        setFont(new Font("sansserif", 0, 12));
        setForeground(new Color(102, 102, 102));
        setBorder(new EmptyBorder(20, 10, 20, 10));
        initiation();
    }
    
    private void initiation() {
        initValues(0, 10);
    }
    
    public void initValues(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        niceScale = new NiceScale(minValue, maxValue);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        if(niceScale != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            createLine(g2);
            createValues(g2);
            createLine(g2);
            createLabelText(g2);
            renderSeries(g2);
        }
    }
    
    private void createLine(Graphics2D g2) {
        g2.setColor(new Color(220, 220, 220));
        Insets insets = getInsets();
        double textHeight = getLabelTextHeight(g2);
        double height = getHeight() - (insets.top + insets.bottom) - textHeight;
        double space = height / niceScale.getMaxTicks();
        double locationY = insets.bottom + textHeight;
        double textWidth = getMaxValuesTextWidth(g2);
        double spaceText = 5;
        for(int i = 0; i < niceScale.getMaxTicks(); i++) {
            int y = (int) (getHeight() - locationY);
            g2.drawLine((int)(insets.left + textWidth + spaceText), y, (int) getWidth() - insets.right, y);
            locationY += space;
        }
    }
    
    private void createValues(Graphics2D g2) {
        g2.setColor(getForeground());
        Insets insets = getInsets();
        double textHeight = getLabelTextHeight(g2);
        double height = getHeight() - (insets.top + insets.bottom) - textHeight;
        double space = height / niceScale.getMaxTicks();
        double valueCount = niceScale.getNiceMin();
        double locationY = insets.bottom + textHeight;
        FontMetrics ft = g2.getFontMetrics();
        for(int i = 0; i < niceScale.getMaxTicks(); i++) {
//            String text = "Rp "+ format.format(valueCount);
            String text = format.format(valueCount);
            Rectangle2D r2 = ft.getStringBounds(text, g2);
            double StringY = r2.getCenterY() * -1;
            double y = getHeight() - locationY + StringY;
            g2.drawString(text, insets.left, (int)y);
            locationY += space;
            valueCount += niceScale.getTickSpacing();
        }
    }
    
    private void createLabelText(Graphics2D g2) {
        if(labelCount > 0) {
            Insets insets = getInsets();
            double textWidth = getMaxValuesTextWidth(g2);
            double spaceText = 5;
            double width = getWidth() - insets.left - insets.right - textWidth - spaceText;
            double space = width / labelCount;
            double locationX = insets.left + textWidth + spaceText;
            double locationText = getHeight() - insets.bottom;
            FontMetrics ft = g2.getFontMetrics();
            for(int i = 0; i < labelCount; i++) {
                double centerX = ((locationX + space / 2));
                g2.setColor(getForeground());
                String text = getCharText(i);
                Rectangle2D r2 = ft.getStringBounds(text, g2);
                double textX = centerX - r2.getWidth();
                g2.drawString(text, (int) textX, (int) locationText);
                locationX += space;
            }
        }
    }
    
    private void renderSeries(Graphics2D g2) {
        if(blankPlotChatRender != null) {
            Insets insets = getInsets();
            double textWidth = getMaxValuesTextWidth(g2);
            double textHeight = getLabelTextHeight(g2);
            double spaceText = 5;
            double width = getWidth() - insets.left - insets.right - textWidth - spaceText;
            double height = getHeight() - insets.top - insets.bottom - textHeight;
            double space = width / labelCount;
            double locationX = insets.left + textWidth + spaceText;
            for(int i = 0; i < labelCount; i++) {
                blankPlotChatRender.renderSeries(this, g2, getRectangle(i, height, space, locationX, insets.top), i);
            }
        }
    }
    
    private double getMaxValuesTextWidth(Graphics2D g2) {
        double width = 0;
        FontMetrics ft = g2.getFontMetrics();
        double valueCount = niceScale.getNiceMin();
        for(int i = 0; i <= niceScale.getMaxTicks(); i++) {
            String text = format.format(valueCount);
            Rectangle2D r2 = ft.getStringBounds(text, g2);
            double w = r2.getWidth();
            if(w > width) {
                width = w;
            }
            
            valueCount += niceScale.getTickSpacing();
        }
        
        return width;
    }
    
    private int getLabelTextHeight(Graphics2D g2) {
        FontMetrics ft = g2.getFontMetrics();
        return ft.getHeight();
    }
    
    private String getCharText(int index) {
        if(blankPlotChatRender != null) {
            return blankPlotChatRender.getLabelText(index);
        } else {
            return "Label";
        }
    }
    
    public SeriesSize getRectangle(int index, double height, double space, double startX, double startY) {
        double x = startX + space * index;
        SeriesSize size = new SeriesSize(x, startY+1, space, height);
        return size;
    }
    
    public double getSeriesValuesOf(double value, double height) {
        double max = niceScale.getTickSpacing() * niceScale.getMaxTicks();
        double percentValue = value * 100d / max;
        return height * percentValue / 100d;
    }
}
