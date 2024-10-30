/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.ModelKaryawan;

/**
 *
 * @author usER
 */
public class ModelAbsensi {

    public ModelAbsensi(String tanggal, String absenMasuk, String absenKeluar, String keterangan, ModelKaryawan modelKaryawan) {
        this.tanggal = tanggal;
        this.absenMasuk = absenMasuk;
        this.absenKeluar = absenKeluar;
        this.keterangan = keterangan;
        this.modelKaryawan = modelKaryawan;
    }

    public ModelAbsensi() {
    }
    
    private String tanggal;
    private String absenMasuk;
    private String absenKeluar;
    private String keterangan;
    private ModelKaryawan modelKaryawan;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAbsenMasuk() {
        return absenMasuk;
    }

    public void setAbsenMasuk(String absenMasuk) {
        this.absenMasuk = absenMasuk;
    }

    public String getAbsenKeluar() {
        return absenKeluar;
    }

    public void setAbsenKeluar(String absenKeluar) {
        this.absenKeluar = absenKeluar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public ModelKaryawan getModelKaryawan() {
        return modelKaryawan;
    }

    public void setModelKaryawan(ModelKaryawan modelKaryawan) {
        this.modelKaryawan = modelKaryawan;
    }
}
