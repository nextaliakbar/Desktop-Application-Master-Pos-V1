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
public class ParamPemeriksaan {

    public ParamPemeriksaan(String noPemeriksaan, String tglPemeriksaan, String jamPemeriksaan, String pasien, 
    String karyawan, String admin, String total, String potongan, String bayar, String kembalian, String jenis, 
    List<FieldsPemeriksaan> fields) {
        this.noPemeriksaan = noPemeriksaan;
        this.tglPemeriksaan = tglPemeriksaan;
        this.jamPemeriksaan = jamPemeriksaan;
        this.pasien = pasien;
        this.karyawan = karyawan;
        this.admin = admin;
        this.total = total;
        this.potongan = potongan;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.jenis = jenis;
        this.fields = fields;
    }
    
    public ParamPemeriksaan() {
    }

    private String noPemeriksaan;
    private String tglPemeriksaan;
    private String jamPemeriksaan;
    private String pasien;
    private String karyawan;
    private String admin;
    private String total;
    private String potongan;
    private String bayar;
    private String kembalian;
    private String jenis;
    private List<FieldsPemeriksaan> fields;    

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

    public String getJamPemeriksaan() {
        return jamPemeriksaan;
    }

    public void setJamPemeriksaan(String jamPemeriksaan) {
        this.jamPemeriksaan = jamPemeriksaan;
    }

    public String getPasien() {
        return pasien;
    }

    public void setPasien(String pasien) {
        this.pasien = pasien;
    }

    public String getKaryawan() {
        return karyawan;
    }

    public void setKaryawan(String karyawan) {
        this.karyawan = karyawan;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    public String getPotongan() {
        return potongan;
    }

    public void setPotongan(String potongan) {
        this.potongan = potongan;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getKembalian() {
        return kembalian;
    }

    public void setKembalian(String kembalian) {
        this.kembalian = kembalian;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public List<FieldsPemeriksaan> getFields() {
        return fields;
    }

    public void setFields(List<FieldsPemeriksaan> fields) {
        this.fields = fields;
    }
}
