/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usER
 */
public class ModelDetailPemeriksaan {

    public ModelDetailPemeriksaan(ModelPemeriksaan modelPemeriksaan, ModelTindakan modelTindakan, int subtotal, int potongan) {
        this.modelPemeriksaan = modelPemeriksaan;
        this.modelTindakan = modelTindakan;
        this.subtotal = subtotal;
        this.potongan = potongan;
    }

    public ModelDetailPemeriksaan() {
    
    }
    
    
    private ModelPemeriksaan modelPemeriksaan;
    private ModelTindakan modelTindakan;
    private int subtotal;
    private int potongan;

    public ModelPemeriksaan getModelPemeriksaan() {
        return modelPemeriksaan;
    }

    public void setModelPemeriksaan(ModelPemeriksaan modelPemeriksaan) {
        this.modelPemeriksaan = modelPemeriksaan;
    }

    public ModelTindakan getModelTindakan() {
        return modelTindakan;
    }

    public void setModelTindakan(ModelTindakan modelTindakan) {
        this.modelTindakan = modelTindakan;
    }
    
    public int getSubtotal() {
        return this.subtotal;
    }
    
    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
    
    public int getPotongan() {
        return this.potongan;
    }
    
    public void setPotongan(int potongan) {
        this.potongan = potongan;
    }
    
    public Object[] toRowTable() {
        return new Object[]{this, modelTindakan.getKodeTindakan(), modelTindakan.getNamaTindakan(), 
        modelTindakan.getBiaya(), potongan, subtotal};
    }
}
