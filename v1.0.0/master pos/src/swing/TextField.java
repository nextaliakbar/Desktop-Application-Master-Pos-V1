/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author usER
 */
public class TextField extends JTextField{

    public TextField() {
        setFont(new Font("sansserif", 0, 14));
        setBorder(new EmptyBorder(20, 3, 23, 3));
        setSelectionColor(new Color (245, 34, 94));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
                repaint();
            }  
        });
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                showing(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                showing(true);
            }
        });
        
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                animateHitText = getText().equals("");
            }

            @Override
            public void timingEvent(float fraction) {
                location = fraction;
                repaint();
            }
        };
        
        animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }
    
    private final Animator animator;
    private boolean animateHitText;
    private float location;
    private boolean show;
    private boolean mouseOver = false;
    private String labelText = "Label";
    private String helperText = "";
    private int spaceHelperText = 15;
    private Color lineColor = new Color(245, 34, 94);

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        repaint();
    }

    public String getHelperText() {
        return helperText;
    }

    public void setHelperText(String helperText) {
        this.helperText = helperText;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }
    
    
    private void showing(boolean action) {
        if(animator.isRunning()) {
            animator.stop();
        } else {
            location = 1;
        }
        animator.setStartFraction(1f - location);
        show = action;
        location = 1f - location;
        animator.start();
    }

    @Override 
    public void paint(Graphics g) {
        super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        int width = getWidth();
        int height = getHeight();
        if(mouseOver) {
            g2.setColor(lineColor);
        } else {
            g2.setColor(new Color(150, 150, 150));
        }
        
        g2.fillRect(2, height - spaceHelperText - 1, width - 4, 1);
        createHintText(g2);
        createLineStyle(g2);
        createHelperText(g2);
        g2.dispose();
    }
    
    private void createHintText(Graphics2D g2) {
        Insets insets = getInsets();
        g2.setColor(new Color(150, 150, 150));
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D r2 = fm.getStringBounds(labelText, g2);
        double height = getHeight() - insets.top - insets.bottom;
        double textY = (height - r2.getHeight()) / 2;
        double size;
        if(animateHitText) {
            if(show) {
                size = 18 * (1 - location);
            } else {
                size = 18 * location;
            }
        } else {
            size = 18;
        }
        g2.drawString(labelText, insets.right, (int)(insets.top + textY + fm.getAscent() - size));
    }
    
    private void createLineStyle(Graphics2D g2) {
        if(isFocusOwner()) {
            double width = getWidth() - 4;
            int height = getHeight() - spaceHelperText;
            g2.setColor(lineColor);
            double size;
            if(show) {
                size = width * (1 - location);
            } else {
                size = width * location;
            }
            double x = (width - size) / 2;
            g2.fillRect((int)(x + 2), height - 2, (int) size, 2);
        }
    }
    
    private void createHelperText(Graphics2D g2) {
        if(helperText != null && !helperText.equals("")) {
            Insets insets = getInsets();
            int height = getHeight() - 17;
            g2.setColor(new Color(255, 76, 76));
            Font font = getFont();
            g2.setFont(font.deriveFont(0, font.getSize() - 1));
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r2 = fm.getStringBounds(labelText, g2);
            double textY = (15 - r2.getHeight()) / 3f;
            g2.drawString(helperText, insets.right, (int)(height + fm.getAscent() - textY));
        }
    }
    
    @Override
    public void setText(String strText) {
        if(!getText().equals(strText)) {
            showing(strText.equals(""));
        }
        super.setText(strText);
    }
}
