/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelPengeluaran {

    public ModelPengeluaran(String noPengeluaran, String tglPengeluaran, String total, String deskripsi, ModelPengguna modelPengguna) {
        this.noPengeluaran = noPengeluaran;
        this.tglPengeluaran = tglPengeluaran;
        this.total = total;
        this.deskripsi = deskripsi;
        this.modelPengguna = modelPengguna;
    }

    public ModelPengeluaran() {
    
    }    
    
    private String noPengeluaran;
    private String tglPengeluaran;
    private String total;
    private String deskripsi;
    private ModelPengguna modelPengguna;

    public String getNoPengeluaran() {
        return noPengeluaran;
    }

    public void setNoPengeluaran(String noPengeluaran) {
        this.noPengeluaran = noPengeluaran;
    }

    public String getTglPengeluaran() {
        return tglPengeluaran;
    }

    public void setTglPengeluaran(String tglPengeluaran) {
        this.tglPengeluaran = tglPengeluaran;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    public ModelPengguna getModelPengguna() {
        return modelPengguna;
    }

    public void setModelPengguna(ModelPengguna modelPengguna) {
        this.modelPengguna = modelPengguna;
    }
    
    public Object[] toRowTable() {
        return new Object[]{this, noPengeluaran, modelPengguna.getIdpengguna(), modelPengguna.getNama(), tglPengeluaran, total, deskripsi};
    }
}
