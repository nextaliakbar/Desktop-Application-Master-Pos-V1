/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Alfito Dwi
 */
public class ModelSupplier {

    public ModelSupplier(String idSupplier, String namaSupplier, String teleponSupplier, String emailSupplier, String alamatSupplier) {
        this.idSupplier = idSupplier;
        this.namaSupplier = namaSupplier;
        this.teleponSupplier = teleponSupplier;
        this.emailSupplier = emailSupplier;
        this.alamatSupplier = alamatSupplier;
    }

    

    public ModelSupplier() {
    }
    
    
    
    private String idSupplier;
    private String namaSupplier;
    private String teleponSupplier;
    private String emailSupplier;
    private String alamatSupplier;

    public String getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public String getTeleponSupplier() {
        return teleponSupplier;
    }

    public void setTeleponSupplier(String teleponSupplier) {
        this.teleponSupplier = teleponSupplier;
    }

    public String getEmailSupplier() {
        return emailSupplier;
    }

    public void setEmailSupplier(String emailSupplier) {
        this.emailSupplier = emailSupplier;
    }

    public String getAlamatSupplier() {
        return alamatSupplier;
    }

    public void setAlamatSupplier(String alamatSupplier) {
        this.alamatSupplier = alamatSupplier;
    }

   
}
