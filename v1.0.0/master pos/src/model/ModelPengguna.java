/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Alfito Dwi
 */
public class ModelPengguna {
    public ModelPengguna(String idpengguna, String nama, String username, String password, String email, String level, String status){
         this.idpengguna = idpengguna;
         this.nama = nama;
         this.username = username;
         this.password = password;
         this.email = email;
         this.level = level;
         this.status = status;
    }
    public ModelPengguna (){
        
    }
                  
    private String idpengguna;
    private String nama;
    private String username; 
    private String password;
    private String confirPass;
    private String email;
    private String level; 
    private String status;
    private String kode_verifikasi; 

    public String getIdpengguna() {
        return idpengguna;
    }

    public void setIdpengguna(String idpengguna) {
        this.idpengguna = idpengguna;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirPass() {
        return confirPass;
    }

    public void setConfirPass(String confirPass) {
        this.confirPass = confirPass;
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
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKode_verifikasi() {
        return kode_verifikasi;
    }

    public void setKode_verifikasi(String kode_verifikasi) {
        this.kode_verifikasi = kode_verifikasi;
    }
}

   

