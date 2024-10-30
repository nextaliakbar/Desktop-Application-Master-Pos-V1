/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelDetailPenjualan {

    public ModelDetailPenjualan(ModelPenjualan modelPenjualan, ModelBarang modelBarang, int jumlah, int subtotal, int subtotalHrgBeli) {
        this.modelPenjualan = modelPenjualan;
        this.modelBarang = modelBarang;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
        this.subtotalHrgBeli = subtotalHrgBeli;
    }

    public ModelDetailPenjualan() {
    
    }
    
    private ModelPenjualan modelPenjualan;
    private ModelBarang modelBarang;
    private int jumlah;
    private int subtotal;
    private int subtotalHrgBeli;

    public ModelPenjualan getModelPenjualan() {
        return modelPenjualan;
    }

    public void setModelPenjualan(ModelPenjualan modelPenjualan) {
        this.modelPenjualan = modelPenjualan;
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

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getSubtotalHrgBeli() {
        return subtotalHrgBeli;
    }

    public void setSubtotalHrgBeli(int subtotalHrgBeli) {
        this.subtotalHrgBeli = subtotalHrgBeli;
    }
    
    public Object[] toRowTable() {
        return new Object[]{this, modelBarang.getKode_Barang(), modelBarang.getNama_Barang(), 
        modelBarang.getSatuan(), modelBarang.getHarga_Beli(), modelBarang.getHarga_Jual(), jumlah, subtotal, subtotalHrgBeli};
    }
}
