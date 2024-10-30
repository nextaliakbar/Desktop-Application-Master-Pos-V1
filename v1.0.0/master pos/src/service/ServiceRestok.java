/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelBarang;
import model.ModelDetailRestok;
import model.ModelRestok;
import model.Sementara;
import swing.StatusType;
/**
 *
 * @author usER
 */
public class ServiceRestok {
    private Connection connection;
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd - MMMM - yyyy", new Locale("id", "ID"));
    public ServiceRestok() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(DefaultTableModel tabmodel) {
        String query = "SELECT rst.No_Restok, rst.Tanggal, "
                + "rst.ID_Pengguna, pgn.Nama, rst.Total_Biaya FROM restok rst JOIN pengguna pgn "
                + "ON rst.ID_Pengguna=pgn.ID_Pengguna ORDER BY No_Restok DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noRestok = rst.getString("No_Restok");
                LocalDate dateTglTiba = LocalDate.parse(rst.getString("Tanggal"), 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tglTiba = formatter.format(dateTglTiba);
                String idPengguna = rst.getString("ID_Pengguna");
                String nama = rst.getString("Nama");
                int total = rst.getInt("Total_Biaya");
                tabmodel.addRow(new Object[]{noRestok, tglTiba, idPengguna, nama, df.format(total)});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } 
    }
    
    public void getData(ModelRestok modelRestok, DefaultTableModel tabmodel) {
        String query = "SELECT dtl.Kode_Barang, brg.Nama_Barang, brg.Harga_Beli, dtl.Jumlah, dtl.SubTotal "
                + "FROM detail_pemesanan dtl JOIN barang brg ON dtl.Kode_Barang=brg.Kode_Barang "
                + "WHERE No_Pemesanan='"+modelRestok.getModelPemesanan().getNoPemesanan()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kodeBarang = rst.getString("Kode_Barang");
                String namaBarang = rst.getString("Nama_Barang");
                int hargaBeli = rst.getInt("Harga_Beli");
                int jumlah = rst.getInt("Jumlah");
                int subtotal = rst.getInt("SubTotal");
                tabmodel.addRow(new Object[]{kodeBarang, namaBarang, hargaBeli, jumlah, subtotal});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(JFrame parent, ModelRestok modelRestok) {
        String query = "INSERT INTO restok (No_Restok, Tanggal, Total_Biaya, Status_Restok, ID_Pengguna) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelRestok.getModelPemesanan().getNoPemesanan());
            pst.setString(2, modelRestok.getTglTiba());
            pst.setInt(3, modelRestok.getTotal());
            pst.setString(4, "Diterima");
            pst.setString(5, modelRestok.getModelPengguna().getIdpengguna());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Berhasil Menambah Stok Baru");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addDataDetail(ModelDetailRestok modelDetail, Sementara rs) {
        String query = "INSERT INTO detail_restok (No_Restok, Kode_Barang, Jumlah, SubTotal) VALUES (?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelDetail.getModelRestok().getModelPemesanan().getNoPemesanan());
            
            for(String kodeBrg : rs.getKodeBrg()) {
                pst.setString(2, kodeBrg);
            }
            
            for(int jumlah : rs.getJumlah()) {
                pst.setInt(3, jumlah);
            }
            
            for(double subtotal : rs.getSubtotal()) {
                pst.setDouble(4, subtotal);
            }
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadDataStok(DefaultTableModel model) {
        String query = "SELECT brg.Kode_Barang, brg.Kode_Jenis, jb.Nama_Jenis, brg.Nama_Barang, brg.Satuan, brg.Stok FROM barang brg "
                + "JOIN jenis_barang jb ON brg.Kode_Jenis=jb.Kode_Jenis ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kodeBarang = rst.getString("Kode_Barang");
                String kodeJenis = rst.getString("Kode_Jenis");
                String jenisBarang = rst.getString("Nama_Jenis");
                String namaBarang = rst.getString("Nama_Barang");
                String satuan = rst.getString("Satuan");
                int stok = rst.getInt("Stok");
                
                StatusType type = null;
                if(stok == 0) {
                    type = StatusType.Habis;
                } else if(stok <= 10) {
                    type = StatusType.Tambah;
                }
                
                ModelBarang modelBarang = new ModelBarang();
                modelBarang.setKode_Barang(kodeBarang);
                modelBarang.setKode_Jenis(kodeJenis);
                modelBarang.setJenis_Barang(jenisBarang);
                modelBarang.setNama_Barang(namaBarang);
                modelBarang.setSatuan(satuan);
                modelBarang.setStok(stok);
                
                ModelDetailRestok modelDetail = new ModelDetailRestok();
                modelDetail.setModelBarang(modelBarang);
                modelDetail.setType(type);
                
                model.addRow(modelDetail.toRowTable());
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Integer> getStok() {
        List<Integer> listStok = new ArrayList<>();
        String query = "SELECT Stok FROM barang";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                listStok.add(rst.getInt("Stok"));
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return listStok;
    }
}
