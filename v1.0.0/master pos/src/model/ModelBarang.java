/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public class ModelBarang {
    
    public ModelBarang() {
        
    }

    public ModelBarang(String Kode_Barang, String Nomor_Barcode, String Kode_Jenis, String Nama_Barang, String Satuan, Integer Harga_Beli, Integer Harga_Jual, Integer Stok) {
        this.Kode_Barang = Kode_Barang;
        this.Nomor_Barcode = Nomor_Barcode;
        this.Kode_Jenis = Kode_Jenis;
        this.Nama_Barang = Nama_Barang;
        this.Satuan = Satuan;
        this.Harga_Beli = Harga_Beli;
        this.Harga_Jual = Harga_Jual;
        this.Stok = Stok;
    }
    
    

    private String Kode_Barang;
    private String Nomor_Barcode;
    private String Kode_Jenis;
    private String Jenis_Barang;
    private String Nama_Barang;
    private String Satuan;
    private Integer Harga_Beli;
    private Integer Harga_Jual;
    private Integer Stok;

    public String getKode_Barang() {
        return Kode_Barang;
    }

    public void setKode_Barang(String Kode_Barang) {
        this.Kode_Barang = Kode_Barang;
    }
    
    public String getNomor_Barcode() {
        return Nomor_Barcode;
    }

    public void setNomor_Barcode(String Nomor_Barcode) {
        this.Nomor_Barcode = Nomor_Barcode;
    }
    
    public String getKode_Jenis() {
        return Kode_Jenis;
    }

    public void setKode_Jenis(String Kode_Jenis) {
        this.Kode_Jenis = Kode_Jenis;
    }
    
    public String getJenis_Barang() {
        return Jenis_Barang;
    }

    public void setJenis_Barang(String Jenis_Barang) {
        this.Jenis_Barang = Jenis_Barang;
    }
    
    public String getNama_Barang() {
        return Nama_Barang;
    }

    public void setNama_Barang(String Nama_Barang) {
        this.Nama_Barang = Nama_Barang;
    }

    public String getSatuan() {
        return Satuan;
    }

    public void setSatuan(String Satuan) {
        this.Satuan = Satuan;
    }

    public Integer getHarga_Beli() {
        return Harga_Beli;
    }

    public void setHarga_Beli(Integer Harga_Beli) {
        this.Harga_Beli = Harga_Beli;
    }

    public Integer getHarga_Jual() {
        return Harga_Jual;
    }

    public void setHarga_Jual(Integer Harga_Jual) {
        this.Harga_Jual = Harga_Jual;
    }

    public Integer getStok() {
        return Stok;
    }

    public void setStok(Integer Stok) {
        this.Stok = Stok;
    }
}
