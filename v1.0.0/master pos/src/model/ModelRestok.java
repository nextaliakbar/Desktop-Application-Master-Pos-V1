/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelRestok {

    public ModelRestok(ModelPemesanan modelPemesanan, String tglTiba, int total, ModelPengguna modelPengguna) {
        this.modelPemesanan = modelPemesanan;
        this.tglTiba = tglTiba;
        this.total = total;
        this.modelPengguna = modelPengguna;
    }

    public ModelRestok() {
    }
    
    
    private ModelPemesanan modelPemesanan;
    private String tglTiba;
    private int total;
    private ModelPengguna modelPengguna;

    public ModelPemesanan getModelPemesanan() {
        return modelPemesanan;
    }

    public void setModelPemesanan(ModelPemesanan modelPemesanan) {
        this.modelPemesanan = modelPemesanan;
    }

    public String getTglTiba() {
        return tglTiba;
    }

    public void setTglTiba(String tglTiba) {
        this.tglTiba = tglTiba;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ModelPengguna getModelPengguna() {
        return modelPengguna;
    }

    public void setModelPengguna(ModelPengguna modelPengguna) {
        this.modelPengguna = modelPengguna;
    }
    
}
