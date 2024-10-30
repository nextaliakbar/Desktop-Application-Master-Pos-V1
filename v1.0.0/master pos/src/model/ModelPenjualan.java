/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelPenjualan {

    public ModelPenjualan(String noPenjualan, String tglPenjualan, String totalPenjualan, double bayar, double kembali, String jenisPembayaran, ModelPengguna modelPengguna) {
        this.noPenjualan = noPenjualan;
        this.tglPenjualan = tglPenjualan;
        this.totalPenjualan = totalPenjualan;
        this.bayar = bayar;
        this.kembali = kembali;
        this.jenisPembayaran = jenisPembayaran;
        this.modelPengguna = modelPengguna;
    }

    public ModelPenjualan() {
    }
    
    
    private String noPenjualan;
    private String tglPenjualan;
    private String totalPenjualan;
    private int totalKeuntungan;
    private double bayar;
    private double kembali;
    private String jenisPembayaran;
    private ModelPengguna modelPengguna;

    public String getNoPenjualan() {
        return noPenjualan;
    }

    public void setNoPenjualan(String noPenjualan) {
        this.noPenjualan = noPenjualan;
    }

    public String getTglPenjualan() {
        return tglPenjualan;
    }

    public void setTglPenjualan(String tglPenjualan) {
        this.tglPenjualan = tglPenjualan;
    }

    public String getTotalPenjualan() {
        return totalPenjualan;
    }

    public void setTotalPenjualan(String totalPenjualan) {
        this.totalPenjualan = totalPenjualan;
    }

    public int getTotalKeuntungan() {
        return totalKeuntungan;
    }

    public void setTotalKeuntungan(int totalKeuntungan) {
        this.totalKeuntungan = totalKeuntungan;
    }
    
    public double getBayar() {
        return bayar;
    }

    public void setBayar(double bayar) {
        this.bayar = bayar;
    }

    public double getKembali() {
        return kembali;
    }

    public void setKembali(double kembali) {
        this.kembali = kembali;
    }

    public String getJenisPembayaran() {
        return jenisPembayaran;
    }

    public void setJenisPembayaran(String jenisPembayaran) {
        this.jenisPembayaran = jenisPembayaran;
    }

    public ModelPengguna getModelPengguna() {
        return modelPengguna;
    }

    public void setModelPengguna(ModelPengguna modelPengguna) {
        this.modelPengguna = modelPengguna;
    }
    
    public Object[] toRowTable() {
        return new Object[]{this, noPenjualan, modelPengguna.getIdpengguna(), modelPengguna.getNama(), tglPenjualan, 
        totalPenjualan, bayar, kembali, jenisPembayaran};
    }
}
