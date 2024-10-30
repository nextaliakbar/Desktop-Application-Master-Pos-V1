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
import model.ModelDetailPenjualan;
import model.Sementara;
/**
 *
 * @author usER
 */
public class ServiceDetailPenjualan {
    private Connection connection;
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    
    public ServiceDetailPenjualan() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(ModelDetailPenjualan modelDetail, DefaultTableModel tabmodel) {
        String query = "SELECT dtl.Kode_Barang, dtl.Harga_Jual_Final, brg.Nama_Barang, brg.Satuan, brg.Harga_Jual, "
                + "dtl.Jumlah, dtl.Subtotal FROM detail_penjualan dtl JOIN barang brg "
                + "ON dtl.Kode_Barang=brg.Kode_Barang WHERE No_Penjualan='"+modelDetail.getModelPenjualan().getNoPenjualan()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kodeBrg = rst.getString("Kode_Barang");
                String namaBrg = rst.getString("Nama_Barang");
                String satuan = rst.getString("Satuan");
                int hrgJualFinal = rst.getInt("Harga_Jual_Final");
                int hrgJual = rst.getInt("Harga_Jual");
                int jumlah = rst.getInt("Jumlah");
                int subtotal = rst.getInt("Subtotal");
                
                StringBuilder builder = new StringBuilder();
                if(hrgJualFinal != hrgJual) {
                    builder.append(" (Harga Sebelum = ")
                            .append(hrgJualFinal)
                            .append(")");
                }
                
                tabmodel.addRow(new Object[]{kodeBrg, namaBrg, satuan, df.format(hrgJual).concat(builder.toString()), jumlah, df.format(subtotal)});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(ModelDetailPenjualan modelDetail, Sementara ps) {
        String query = "INSERT INTO detail_penjualan (No_Penjualan, Kode_Barang, Harga_Jual_Final, Jumlah, Subtotal) VALUES (?,?,?,?,?) ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelDetail.getModelPenjualan().getNoPenjualan());

            for(String kodeBrg : ps.getKodeBrg()) {
                pst.setString(2, kodeBrg);
            }
            
            for(int hargaJual : ps.getHargaFinal()) {
                pst.setInt(3, hargaJual);
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
