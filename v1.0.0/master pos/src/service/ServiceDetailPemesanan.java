/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import model.ModelDetailPemesanan;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import model.Sementara;
/**
 *
 * @author usER
 */
public class ServiceDetailPemesanan {
    private Connection connection;
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    
    public ServiceDetailPemesanan() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(ModelDetailPemesanan modelDetail, DefaultTableModel tabmodel) {
        String query = "SELECT dtl.Kode_Barang, dtl.Harga_Beli_Final, brg.Nama_Barang, " +
                "brg.Satuan, brg.Harga_Beli, dtl.Jumlah, dtl.Subtotal FROM detail_pemesanan dtl " +
                "INNER JOIN barang brg ON dtl.Kode_Barang=brg.Kode_Barang " +
                "WHERE No_Pemesanan='"+modelDetail.getModelPemesanan().getNoPemesanan()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kodeBarang = rst.getString("Kode_Barang");
                int hrgBeliFinal = rst.getInt("Harga_Beli_Final");
                String namaBarang = rst.getString("Nama_Barang");
                String satuan = rst.getString("Satuan");
                int hrgBeli = rst.getInt("Harga_Beli");
                int jumlah = rst.getInt("Jumlah");
                int subtotal = rst.getInt("Subtotal");
                
                StringBuilder builder = new StringBuilder();  
                
                if(hrgBeliFinal != hrgBeli) {
                    builder.append(" (Harga Sebelum = ")
                            .append(df.format(hrgBeliFinal))
                            .append(")");
                }
                
                String info = builder.toString();
                
                tabmodel.addRow(new Object[]{kodeBarang, namaBarang, satuan, df.format(hrgBeli).concat(info), jumlah, df.format(subtotal)});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelDetailPemesanan modelDetail, Sementara ps) {
        String query = "INSERT INTO detail_pemesanan (No_Pemesanan, Kode_Barang, Harga_Beli_Final, Jumlah, Subtotal) VALUES (?,?,?,?,?) ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelDetail.getModelPemesanan().getNoPemesanan());
            
            for(String kodeBrg : ps.getKodeBrg()) {
                pst.setString(2, kodeBrg);
            }
            
            for(int hargaBeli : ps.getHargaFinal()) {
                pst.setInt(3, hargaBeli);
            }
            
            for(int jumlah : ps.getJumlah()) {
                pst.setInt(4, jumlah);
            }
            
            for(int subtotal : ps.getSubtotal()) {
                pst.setInt(5, subtotal);
            }
            
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
