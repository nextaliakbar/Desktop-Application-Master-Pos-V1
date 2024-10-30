/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelDetailPemesanan {

    public ModelDetailPemesanan(ModelPemesanan modelPemesanan, ModelBarang modelBarang, int jumlah, double subtotal) {
        this.modelPemesanan = modelPemesanan;
        this.modelBarang = modelBarang;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public ModelDetailPemesanan() {
    
    }
    
    private ModelPemesanan modelPemesanan;
    private ModelBarang modelBarang;
    private int jumlah;
    private double subtotal;

    public ModelPemesanan getModelPemesanan() {
        return modelPemesanan;
    }

    public void setModelPemesanan(ModelPemesanan modelPemesanan) {
        this.modelPemesanan = modelPemesanan;
    }

    public ModelBarang getModelBarang() {
        return modelBarang;
    }

    public void setModelBarang(ModelBarang modelBarang) {
        this.modelBarang = modelBarang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
