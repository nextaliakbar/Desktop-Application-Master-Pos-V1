/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;
import model.ModelDetailPemeriksaan;
import model.PemeriksaanSementara;
/**
 *
 * @author usER
 */
public class ServiceDetailPemeriksaan {
    
    private Connection conncetion;
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    public ServiceDetailPemeriksaan() {
        conncetion = Koneksi.getConnection();
    }
    
    public void loadData(DefaultTableModel tabModel, ModelDetailPemeriksaan detail) {
        String query = "SELECT detPem.Kode_Tindakan, tdk.Nama_Tindakan, tdk.Biaya_Tindakan, detPem.Biaya_Tindakan_Final, "
                + "detPem.Potongan, detPem.Subtotal FROM detail_pemeriksaan detPem INNER JOIN "
                + "tindakan tdk ON detPem.Kode_Tindakan=tdk.Kode_Tindakan WHERE No_Pemeriksaan='"+detail.getModelPemeriksaan().getNoPemeriksaan()+"'";
        try {
            PreparedStatement pst = conncetion.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kode = rst.getString("Kode_Tindakan");
                String nama = rst.getString("Nama_Tindakan");
                int hargaFinal = rst.getInt("Biaya_Tindakan_Final");
                int harga = rst.getInt("Biaya_Tindakan");
                StringBuilder stringBuilder = new StringBuilder();  
                
                if(hargaFinal != harga) {
                    stringBuilder.append("  ( Harga Sebelum = ")
                            .append(df.format(hargaFinal))
                            .append(" )");
                }
                String info = stringBuilder.toString();
                int potongan = rst.getInt("Potongan");
                int totalHarga = rst.getInt("Subtotal");
                
                tabModel.addRow(new Object[]{kode, nama, df.format(harga).concat(info), df.format(potongan), df.format(totalHarga)});
            }
            
            pst.close();
            rst.close();
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelDetailPemeriksaan detail, PemeriksaanSementara ps) {
        String query = "INSERT INTO detail_pemeriksaan (No_Pemeriksaan, Kode_Tindakan, Biaya_Tindakan_Final, Potongan, Subtotal) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pst = conncetion.prepareStatement(query);

            pst.setString(1, detail.getModelPemeriksaan().getNoPemeriksaan());
            
            for(var kodeTindakan : ps.getKodeTindakan()) {
                pst.setString(2, kodeTindakan);
            }
            
            for(var biaya : ps.getBiayaTindakanFinal()) {
                pst.setInt(3, biaya);
            }
            
            for(var potongan : ps.getPotongan()) {
                pst.setInt(4, potongan);
            }
            
            for(var subtotal : ps.getSubtotal()) {
                pst.setInt(5, subtotal);
            }
            
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
