/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelTindakan {

    public ModelTindakan(String kodeTindakan, String namaTindakan, int biaya) {
        this.kodeTindakan = kodeTindakan;
        this.namaTindakan = namaTindakan;
        this.biaya = biaya;
    }

    public ModelTindakan() {
    
    }
    
    private String kodeTindakan;
    private String namaTindakan;
    private int biaya;

    public String getKodeTindakan() {
        return kodeTindakan;
    }

    public void setKodeTindakan(String kodeTindakan) {
        this.kodeTindakan = kodeTindakan;
    }

    public String getNamaTindakan() {
        return namaTindakan;
    }

    public void setNamaTindakan(String namaTindakan) {
        this.namaTindakan = namaTindakan;
    }

    public int getBiaya() {
        return biaya;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }
}
