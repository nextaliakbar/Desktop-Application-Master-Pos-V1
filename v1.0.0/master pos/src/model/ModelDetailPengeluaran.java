/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelDetailPengeluaran {

    public ModelDetailPengeluaran(ModelPengeluaran modelPengeluaran, ModelJenisPengeluaran modelJenis, String detailJenis, int subtotal) {
        this.modelPengeluaran = modelPengeluaran;
        this.modelJenis = modelJenis;
        this.detailJenis = detailJenis;
        this.subtotal = subtotal;
    }

    public ModelDetailPengeluaran() {
    }
    
    
    private ModelPengeluaran modelPengeluaran;
    private ModelJenisPengeluaran modelJenis;
    private String detailJenis;
    private int subtotal;

    public ModelPengeluaran getModelPengeluaran() {
        return modelPengeluaran;
    }

    public void setModelPengeluaran(ModelPengeluaran modelPengeluaran) {
        this.modelPengeluaran = modelPengeluaran;
    }

    public ModelJenisPengeluaran getModelJenis() {
        return modelJenis;
    }

    public void setModelJenis(ModelJenisPengeluaran modelJenis) {
        this.modelJenis = modelJenis;
    }

    public String getDetailJenis() {
        return detailJenis;
    }

    public void setDetailJenis(String detailJenis) {
        this.detailJenis = detailJenis;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
