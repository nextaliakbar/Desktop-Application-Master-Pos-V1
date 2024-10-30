/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import swing.StatusType;

/**
 *
 * @author usER
 */
public class ModelDetailRestok {

    public ModelDetailRestok(ModelRestok modelRestok, ModelBarang modelBarang, int jumlah, int subtotal, StatusType type) {
        this.modelRestok = modelRestok;
        this.modelBarang = modelBarang;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
        this.type = type;
    }

    public ModelDetailRestok() {
    }
    
    
    private ModelRestok modelRestok;
    private ModelBarang modelBarang;
    private int jumlah;
    private int subtotal;
    private StatusType type;
    
    public ModelRestok getModelRestok() {
        return modelRestok;
    }

    public void setModelRestok(ModelRestok modelRestok) {
        this.modelRestok = modelRestok;
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
    
    public StatusType getType() {
        return type;
    }

    public void setType(StatusType type) {
        this.type = type;
    }
    
    public Object[] toRowTable() {
        return new Object[]{modelBarang.getKode_Barang(), modelBarang.getKode_Jenis(), modelBarang.getJenis_Barang(), modelBarang.getNama_Barang(), modelBarang.getSatuan(), modelBarang.getStok(), type};
    }
}
