/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import swing.StatusType;

/**
 *
 * @author usER
 */
public class ModelReservasi {

    public ModelReservasi(String noReservasi, String tglReservasi, String tglKedatangan, String jamKedatangan, StatusType type, String statusReservasi, ModelPengguna modelPengguna, ModelPasien modelPasien) {
        this.noReservasi = noReservasi;
        this.tglReservasi = tglReservasi;
        this.tglKedatangan = tglKedatangan;
        this.jamKedatangan = jamKedatangan;
        this.type = type;
        this.statusReservasi = statusReservasi;
        this.modelPengguna = modelPengguna;
        this.modelPasien = modelPasien;
    }

    public ModelReservasi() {
    }
    
    
    private String noReservasi;
    private String tglReservasi;
    private String tglKedatangan;
    private String jamKedatangan;
    private StatusType type;
    private String statusReservasi;
    private ModelPengguna modelPengguna;
    private ModelPasien modelPasien;

    public String getNoReservasi() {
        return noReservasi;
    }

    public void setNoReservasi(String noReservasi) {
        this.noReservasi = noReservasi;
    }
    
    public String getTglReservasi() {
        return tglReservasi;
    }

    public void setTglReservasi(String tglReservasi) {
        this.tglReservasi = tglReservasi;
    }

    public String getTglKedatangan() {
        return tglKedatangan;
    }

    public void setTglKedatangan(String tglKedatangan) {
        this.tglKedatangan = tglKedatangan;
    }

    public String getJamKedatangan() {
        return jamKedatangan;
    }

    public void setJamKedatangan(String jamKedatangan) {
        this.jamKedatangan = jamKedatangan;
    }

    public String getStatusReservasi() {
        return statusReservasi;
    }

    public void setStatusReservasi(String statusReservasi) {
        this.statusReservasi = statusReservasi;
    }
    
    public StatusType getType() {
        return type;
    }

    public void setType(StatusType type) {
        this.type = type;
    }

    public ModelPengguna getModelPengguna() {
        return modelPengguna;
    }

    public void setModelPengguna(ModelPengguna modelPengguna) {
        this.modelPengguna = modelPengguna;
    }

    public ModelPasien getModelPasien() {
        return modelPasien;
    }

    public void setModelPasien(ModelPasien modelPasien) {
        this.modelPasien = modelPasien;
    }
    
    public Object[] toRowTable() {
        return new Object[]{noReservasi, tglReservasi, modelPasien.getIdPasien(), modelPasien.getNama(), modelPasien.getJenisKelamin(), tglKedatangan, jamKedatangan, type, statusReservasi, modelPengguna.getIdpengguna(), modelPengguna.getNama()};
    }
}
