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
public class FieldsLaporanganPengeluaran {

    public FieldsLaporanganPengeluaran(int no, String noPengeluaran, String pengguna, String tgl, String totalPengeluaran, List<FieldsPengeluaran> detail) {
        this.no = no;
        this.noPengeluaran = noPengeluaran;
        this.pengguna = pengguna;
        this.tgl = tgl;
        this.totalPengeluaran = totalPengeluaran;
        this.detail = detail;
    }

    public FieldsLaporanganPengeluaran() {
    }
    
    private int no;
    private String noPengeluaran;
    private String pengguna;
    private String tgl;
    private String totalPengeluaran;
    private List<FieldsPengeluaran> detail;

    public int getNo() {
        return no;
    }

    public String getNoPengeluaran() {
        return noPengeluaran;
    }

    public String getPengguna() {
        return pengguna;
    }

    public String getTgl() {
        return tgl;
    }

    public String getTotalPengeluaran() {
        return totalPengeluaran;
    }

    public List<FieldsPengeluaran> getDetail() {
        return detail;
    }
}
