/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.List;

/**
 *
 * @author usER
 */
public class FieldsLaporanPemeriksaan {

    public FieldsLaporanPemeriksaan(int no, String noPemeriksaan, String noReservasi, String pasien, String terapis, String tgl, 
        String total, String bayar, String kembali, List<FieldsPemeriksaan> detail) {
        this.no = no;
        this.noPemeriksaan = noPemeriksaan;
        this.noReservasi = noReservasi;
        this.pasien = pasien;
        this.terapis = terapis;
        this.tgl = tgl;
        this.total = total;
        this.bayar = bayar;
        this.kembali = kembali;
        this.detail = detail;
    }

    public FieldsLaporanPemeriksaan() {
    }
    
    private int no;
    private String noPemeriksaan;
    private String noReservasi;
    private String pasien;
    private String terapis;
    private String tgl;
    private String total;
    private String bayar;
    private String kembali;
    private List<FieldsPemeriksaan> detail;

    public int getNo() {
        return no;
    }

    public String getNoPemeriksaan() {
        return noPemeriksaan;
    }

    public String getNoReservasi() {
        return noReservasi;
    }

    public String getPasien() {
        return pasien;
    }

    public String getTerapis() {
        return terapis;
    }

    public String getTgl() {
        return tgl;
    }

    public String getTotal() {
        return total;
    }

    public String getBayar() {
        return bayar;
    }

    public String getKembali() {
        return kembali;
    }

    public List<FieldsPemeriksaan> getDetail() {
        return detail;
    }

}
