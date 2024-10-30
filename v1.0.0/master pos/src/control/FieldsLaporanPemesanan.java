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
public class FieldsLaporanPemesanan {

    public FieldsLaporanPemesanan(int no, String noPemesanan, String supplier, String tgl, int totalJumlah, String totalPemesanan, String bayar, String kembali, List<FieldsPenjualan> detail) {
        this.no = no;
        this.noPemesanan = noPemesanan;
        this.supplier = supplier;
        this.tgl = tgl;
        this.totalJumlah = totalJumlah;
        this.totalPemesanan = totalPemesanan;
        this.bayar = bayar;
        this.kembali = kembali;
        this.detail = detail;
    }

    public FieldsLaporanPemesanan() {
    }
    
    private int no;
    private String noPemesanan;
    private String supplier;
    private String tgl;
    private int totalJumlah;
    private String totalPemesanan;
    private String bayar;
    private String kembali;
    private List<FieldsPenjualan> detail;

    public int getNo() {
        return no;
    }

    public String getNoPemesanan() {
        return noPemesanan;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getTgl() {
        return tgl;
    }

    public int getTotalJumlah() {
        return totalJumlah;
    }

    public String getTotalPemesanan() {
        return totalPemesanan;
    }

    public String getBayar() {
        return bayar;
    }

    public String getKembali() {
        return kembali;
    }

    public List<FieldsPenjualan> getDetail() {
        return detail;
    }
}
