/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author usER
 */
public class PengeluaranSementara {

    public PengeluaranSementara(List<String> noJenis, List<String> detailJenis, List<Integer> subtotal) {
        this.noJenis = noJenis;
        this.detailJenis = detailJenis;
        this.subtotal = subtotal;
    }


    public PengeluaranSementara() {
    }
    
    private List<String> noJenis;
    private List<String> detailJenis;
    private List<Integer> subtotal;

    public List<String> getNoJenis() {
        return noJenis;
    }

    public void setNoJenis(List<String> noJenis) {
        this.noJenis = noJenis;
    }

    public List<String> getDetailJenis() {
        return detailJenis;
    }

    public void setDetailJenis(List<String> detailJenis) {
        this.detailJenis = detailJenis;
    }

    public List<Integer> getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(List<Integer> subtotal) {
        this.subtotal = subtotal;
    }
    
    
    
}
