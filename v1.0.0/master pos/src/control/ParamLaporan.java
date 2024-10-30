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
public class ParamLaporan {

    public ParamLaporan() {
        
    }
    
    private String rentang;
    private String totalQty;
    private String totalKeseluruhan;
    private List<FieldsLaporanPemeriksaan> fieldsPemeriksaan;
    private List<FieldsLaporanPenjualan> fieldsPenjualan;
    private List<FieldsLaporanPemesanan> fieldsPemesanan;
    private List<FieldsLaporanganPengeluaran> fieldsPengeluaran;

    public String getRentang() {
        return rentang;
    }

    public void setRentang(String rentang) {
        this.rentang = rentang;
    }

    public String getTotalKeseluruhan() {
        return totalKeseluruhan;
    }

    public void setTotalKeseluruhan(String totalKeseluruhan) {
        this.totalKeseluruhan = totalKeseluruhan;
    }
    
    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public List<FieldsLaporanPemeriksaan> getFieldsPemeriksaan() {
        return fieldsPemeriksaan;
    }

    public void setFieldsPemeriksaan(List<FieldsLaporanPemeriksaan> fieldsPemeriksaan) {
        this.fieldsPemeriksaan = fieldsPemeriksaan;
    }

    public List<FieldsLaporanPenjualan> getFieldsPenjualan() {
        return fieldsPenjualan;
    }

    public void setFieldsPenjualan(List<FieldsLaporanPenjualan> fieldsPenjualan) {
        this.fieldsPenjualan = fieldsPenjualan;
    }

    public List<FieldsLaporanPemesanan> getFieldsPemesanan() {
        return fieldsPemesanan;
    }

    public void setFieldsPemesanan(List<FieldsLaporanPemesanan> fieldsPemesanan) {
        this.fieldsPemesanan = fieldsPemesanan;
    }
    
    public List<FieldsLaporanganPengeluaran> getFieldsPengeluaran() {
        return fieldsPengeluaran;
    }

    public void setFieldsPengeluaran(List<FieldsLaporanganPengeluaran> fieldsPengeluaran) {
        this.fieldsPengeluaran = fieldsPengeluaran;
    }

}
