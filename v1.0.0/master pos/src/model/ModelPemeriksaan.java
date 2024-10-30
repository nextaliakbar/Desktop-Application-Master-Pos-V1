/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.DecimalFormat;

/**
 *
 * @author usER
 */
public class ModelPemeriksaan {

    public ModelPemeriksaan() {
        
    }

    public ModelPemeriksaan(
    String noPemeriksaan, ModelReservasi modelReservasi, String tglPemeriksaan, String deskripsi, 
    String total, double bayar, double kembali, String jenisPembayaran, ModelPasien modelPasien, 
    ModelKaryawan modelKaryawan, ModelPengguna modelPengguna
    ) {
        this.noPemeriksaan = noPemeriksaan;
        this.modelReservasi = modelReservasi;
        this.tglPemeriksaan = tglPemeriksaan;
        this.deskripsi = deskripsi;
        this.total = total;
        this.bayar = bayar;
        this.kembali = kembali;
        this.jenisPembayaran = jenisPembayaran;
        this.modelPasien = modelPasien;
        this.modelKaryawan = modelKaryawan;
        this.modelPengguna = modelPengguna;
    }
    
    private String noPemeriksaan;
    private String tglPemeriksaan;
    private String deskripsi;
    private String total;
    private double bayar;
    private double kembali;
    private String jenisPembayaran;
    private ModelPasien modelPasien;
    private ModelKaryawan modelKaryawan;
    private ModelReservasi modelReservasi;
    private ModelPengguna modelPengguna;
    
    public String getNoPemeriksaan() {
        return noPemeriksaan;
    }

    public void setNoPemeriksaan(String noPemeriksaan) {
        this.noPemeriksaan = noPemeriksaan;
    }
    
    public String getTglPemeriksaan() {
        return tglPemeriksaan;
    }

    public void setTglPemeriksaan(String tglPemeriksaan) {
        this.tglPemeriksaan = tglPemeriksaan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public ModelPasien getModelPasien() {
        return modelPasien;
    }

    public void setModelPasien(ModelPasien modelPasien) {
        this.modelPasien = modelPasien;
    }

    public ModelKaryawan getModelKaryawan() {
        return modelKaryawan;
    }

    public void setModelKaryawan(ModelKaryawan modelKaryawan) {
        this.modelKaryawan = modelKaryawan;
    }
    
    public ModelReservasi getModelReservasi() {
        return modelReservasi;
    }

    public void setModelReservasi(ModelReservasi modelReservasi) {
        this.modelReservasi = modelReservasi;
    }
    
    public ModelPengguna getModelPengguna() {
        return modelPengguna;
    }

    public void setModelPengguna(ModelPengguna modelPengguna) {
        this.modelPengguna = modelPengguna;
    }
    
    public Object[] toRowTable() {
        return new Object[]{this, noPemeriksaan, modelReservasi.getNoReservasi(), modelPasien.getIdPasien(),
        modelPasien.getNama(), modelKaryawan.getIdKaryawan(), tglPemeriksaan, total, deskripsi, bayar, 
        kembali, jenisPembayaran, modelPengguna.getIdpengguna(), modelPengguna.getNama()};
    }
}
