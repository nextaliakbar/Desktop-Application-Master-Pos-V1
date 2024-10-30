/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelPasien {

    public ModelPasien() {
        
    }

    public ModelPasien(String idPasien, String nama, String jenisKelamin, String noTelp, String alamat, String email, String level) {
        this.idPasien = idPasien;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.noTelp = noTelp;
        this.alamat = alamat;
        this.email = email;
        this.level = level;
    }
    
    private String idPasien;
    private String nama;
    private String jenisKelamin;
    private String noTelp;
    private String alamat;
    private String email;
    private String level;
            

    public String getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(String idPasien) {
        this.idPasien = idPasien;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
