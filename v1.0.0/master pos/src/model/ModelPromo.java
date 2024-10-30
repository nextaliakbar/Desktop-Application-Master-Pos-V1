/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelPromo {

    public ModelPromo(String noPromo, String namaPromo, String tglAwal, String tglAkhir, int banyakPromo, String jenisPromo, String keterangan) {
        this.noPromo = noPromo;
        this.namaPromo = namaPromo;
        this.tglAwal = tglAwal;
        this.tglAkhir = tglAkhir;
        this.banyakPromo = banyakPromo;
        this.jenisPromo = jenisPromo;
        this.keterangan = keterangan;
    }

    public ModelPromo() {
    }
    
    private String noPromo;
    private String namaPromo;
    private String tglAwal;
    private String tglAkhir;
    private int banyakPromo;
    private String jenisPromo;
    private String keterangan;

    public String getNoPromo() {
        return noPromo;
    }

    public void setNoPromo(String noPromo) {
        this.noPromo = noPromo;
    }

    public String getNamaPromo() {
        return namaPromo;
    }

    public void setNamaPromo(String namaPromo) {
        this.namaPromo = namaPromo;
    }
    
    public String getTglAwal() {
        return tglAwal;
    }

    public void setTglAwal(String tglAwal) {
        this.tglAwal = tglAwal;
    }

    public String getTglAkhir() {
        return tglAkhir;
    }

    public void setTglAkhir(String tglAkhir) {
        this.tglAkhir = tglAkhir;
    }

    public int getBanyakPromo() {
        return banyakPromo;
    }

    public void setBanyakPromo(int banyakPromo) {
        this.banyakPromo = banyakPromo;
    }

    public String getJenisPromo() {
        return jenisPromo;
    }

    public void setJenisPromo(String jenisPromo) {
        this.jenisPromo = jenisPromo;
    }
    
    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
}
