/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.swing.Icon;

/**
 *
 * @author usER
 */
public class ModelCard {
    public ModelCard(String title, double values, Icon icon, String textButton) {
        this.title = title;
        this.values = values;
        this.icon = icon;
        this.textButton = textButton;
    }
  
    public ModelCard() {
    
    }
    
    private String title;
    private double values;
    private Icon icon;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getValues() {
        return values;
    }

    public void setValues(double values) {
        this.values = values;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    
    public String getTextButton() {
        return textButton;
    }

    public void setTextButton(String textButton) {
        this.textButton = textButton;
    }
    private String textButton;
}
