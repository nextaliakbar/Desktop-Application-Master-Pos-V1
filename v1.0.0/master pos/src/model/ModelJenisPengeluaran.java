/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelJenisPengeluaran {

    public ModelJenisPengeluaran(String noJenis, String jenis) {
        this.noJenis = noJenis;
        this.jenis = jenis;
    }

    public ModelJenisPengeluaran() {
    }
    
    
    private String noJenis;
    private String jenis;

    public String getNoJenis() {
        return noJenis;
    }

    public void setNoJenis(String noJenis) {
        this.noJenis = noJenis;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
