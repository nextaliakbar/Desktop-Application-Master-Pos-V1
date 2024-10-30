/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author usER
 */
public class FieldsPemeriksaan {

    public FieldsPemeriksaan(String action, String price, String disc, String subtotal) {
        this.action = action;
        this.price = price;
        this.disc = disc;
        this.subtotal = subtotal;
    }

    public FieldsPemeriksaan() {
    }
    
    
    
    private String action;
    private String price;
    private String disc;
    private String subtotal;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
