/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package action;

import javax.swing.JButton;

/**
 *
 * @author usER
 */
public interface PaginationItemRender {
    JButton createPaginationItem(Object value, boolean isPreviouse, boolean isNext, boolean enable);
    JButton createButton(Object value, boolean isPreviouse, boolean isNext, boolean enable);
    Object createPreviouseIcon();
    Object createNextIcon();
}
