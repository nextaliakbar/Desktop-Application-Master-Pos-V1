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
public class ParamPenjualan {

    public ParamPenjualan(String tglJam, String noPenjualan, String admin, String total, String bayar, String kembalian, String jenis, List<FieldsPenjualan> fields) {
        this.tglJam = tglJam;
        this.noPenjualan = noPenjualan;
        this.admin = admin;
        this.total = total;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.jenis = jenis;
        this.fields = fields;
    }

    public ParamPenjualan() {
    }
    
    
    private String tglJam;
    private String noPenjualan;
    private String admin;
    private String total;
    private String bayar;
    private String kembalian;
    private String jenis;
    private List<FieldsPenjualan> fields;

    public String getTglJam() {
        return tglJam;
    }

    public void setTglJam(String tglJam) {
        this.tglJam = tglJam;
    }

    public String getNoPenjualan() {
        return noPenjualan;
    }

    public void setNoPenjualan(String noPenjualan) {
        this.noPenjualan = noPenjualan;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
    
    public List<FieldsPenjualan> getFields() {
        return fields;
    }
    
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public void setFields(List<FieldsPenjualan> fields) {
        this.fields = fields;
    }
}
