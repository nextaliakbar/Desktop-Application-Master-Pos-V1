/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import action.PaginationItemRender;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author usER
 */
public class ModelDefaultPaginationItemRender implements PaginationItemRender{

    @Override
    public JButton createPaginationItem(Object value, boolean isPreviouse, boolean isNext, boolean enable) {
        JButton button = createButton(value, isPreviouse, isNext, enable);
        button.setFont(new Font("sansserif",0,14));
        if(isPreviouse) {
            Object icon = createPreviouseIcon();
            if(icon != null) {
                if(icon instanceof Icon) {
                button.setIcon((Icon) icon); 
                } else {
                button.setText(icon.toString());
                }
            }
        } else if(isNext) {
            Object icon = createNextIcon();
            if(icon != null) {
                if(icon instanceof Icon) {
                    button.setIcon((Icon) icon);
                } else {
                    button.setText(icon.toString());
                }
            }
        } else {
            button.setText(value.toString());
        }
        
        if(!enable) {
            button.setFocusable(false);
        }
        
        return button;
    }

    @Override
    public JButton createButton(Object value, boolean isPreviouse, boolean isNext, boolean enable) {
        return new JButton();
    }

    @Override
    public Object createPreviouseIcon() {
        return "Kembali";
    }

    @Override
    public Object createNextIcon() {
        return "Selanjutnya";
    }
    
}
