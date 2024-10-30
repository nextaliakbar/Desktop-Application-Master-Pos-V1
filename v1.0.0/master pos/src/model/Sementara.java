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
public class Sementara {

    public Sementara(List<String> kodeBrg, List<Integer> jumlah, List<Integer> subtotal) {
        this.kodeBrg = kodeBrg;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public Sementara(List<String> kodeBrg, List<Integer> hargaFinal, List<Integer> jumlah, List<Integer> subtotal) {
        this.kodeBrg = kodeBrg;
        this.hargaFinal = hargaFinal;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }
    

    public Sementara() {
    
    }
    
    private List<String> kodeBrg;
    private List<Integer> hargaFinal;
    private List<Integer> jumlah;
    private List<Integer> subtotal;

    public List<String> getKodeBrg() {
        return kodeBrg;
    }

    public void setKodeBrg(List<String> kodeBrg) {
        this.kodeBrg = kodeBrg;
    }

    public List<Integer> getHargaFinal() {
        return hargaFinal;
    }

    public void setHargaFinal(List<Integer> hargaBeli) {
        this.hargaFinal = hargaBeli;
    }
    
    public List<Integer> getJumlah() {
        return jumlah;
    }

    public void setJumlah(List<Integer> jumlah) {
        this.jumlah = jumlah;
    }

    public List<Integer> getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(List<Integer> subtotal) {
        this.subtotal = subtotal;
    }

    
}
