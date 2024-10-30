/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelKaryawan {

    public ModelKaryawan() {
    
    }

    public ModelKaryawan(String idKaryawan, String nama, String noTelp, String email, String alamat, String jabatan, String status) {
        this.idKaryawan = idKaryawan;
        this.nama = nama;
        this.noTelp = noTelp;
        this.email = email;
        this.alamat = alamat;
        this.jabatan = jabatan;
        this.status = status;
    }

    
    private String idKaryawan;
    private String nama;
    private String noTelp;
    private String email;
    private String alamat;
    private String jabatan;
    private String status;
    
    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
        public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
