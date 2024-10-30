/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
/**
 *
 * @author usER
 */
public class ModelNotifikasi {

    public ModelNotifikasi(String idNotifkasi, String namaNotifikasi, String deskripsi, String jenisNotifikasi, boolean statusSudahDibaca) {
        this.idNotifkasi = idNotifkasi;
        this.namaNotifikasi = namaNotifikasi;
        this.deskripsi = deskripsi;
        this.jenisNotifikasi = jenisNotifikasi;
        this.statusSudahDibaca = statusSudahDibaca;
    }    
    
    public ModelNotifikasi() {
    }
    
    private String idNotifkasi;
    private LocalDateTime tanggalNotifikasi = LocalDateTime.now();
    private String namaNotifikasi;
    private String deskripsi;
    private String jenisNotifikasi = null;
    private boolean statusSudahDibaca;

    public String getIdNotifkasi() {
        return idNotifkasi;
    }

    public void setIdNotifkasi(String idNotifkasi) {
        this.idNotifkasi = idNotifkasi;
    }

    public LocalDateTime getTanggalNotifikasi() {
        return tanggalNotifikasi;
    }
    
    public void setTanggalNotifikasi(LocalDateTime tanggalNotifikasi) {
        this.tanggalNotifikasi = tanggalNotifikasi;
    }
    
    public String getNamaNotifikasi() {
        return namaNotifikasi;
    }

    public void setNamaNotifikasi(String namaNotifikasi) {
        this.namaNotifikasi = namaNotifikasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJenisNotifikasi() {
        return jenisNotifikasi;
    }

    public void setJenisNotifikasi(String jenisNotifikasi) {
        this.jenisNotifikasi = jenisNotifikasi;
    }
    
    public boolean isStatusSudahDibaca() {
        return statusSudahDibaca;
    }

    public void setStatusSudahDibaca(boolean statusSudahDibaca) {
        this.statusSudahDibaca = statusSudahDibaca;
    }

}
