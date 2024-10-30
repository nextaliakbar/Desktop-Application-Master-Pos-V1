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
public class PemeriksaanSementara {

    public PemeriksaanSementara(List<String> kodeTindakan, List<Integer> biayaTindakanFinal, 
            List<Integer> potongan, List<Integer> subtotal) {
        this.kodeTindakan = kodeTindakan;
        this.biayaTindakanFinal = biayaTindakanFinal;
        this.potongan = potongan;
        this.subtotal = subtotal;
    }

    public PemeriksaanSementara() {
    
    }
    
    private List<String> kodeTindakan;
    private List<Integer> biayaTindakanFinal;
    private List<Integer> potongan;
    private List<Integer> subtotal;
    
    
    public List<String> getKodeTindakan() {
        return kodeTindakan;
    }

    public void setKodeTindakan(List<String> kodeTindakan) {
        this.kodeTindakan = kodeTindakan;
    }

    public List<Integer> getBiayaTindakanFinal() {
        return biayaTindakanFinal;
    }

    public void setBiayaTindakanFinal(List<Integer> biayaTindakanFinal) {
        this.biayaTindakanFinal = biayaTindakanFinal;
    }

    public List<Integer> getPotongan() {
        return potongan;
    }

    public void setPotongan(List<Integer> potongan) {
        this.potongan = potongan;
    }

    public List<Integer> getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(List<Integer> subtotal) {
        this.subtotal = subtotal;
    }  
}
