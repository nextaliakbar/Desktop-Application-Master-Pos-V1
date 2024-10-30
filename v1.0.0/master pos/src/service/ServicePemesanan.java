/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelBarang;
import model.ModelPemesanan;
import model.ModelPengguna;
import model.ModelSupplier;
import swing.Pagination;
import swing.StatusType;
/**
 *
 * @author usER
 */
public class ServicePemesanan {
    private Connection connection;
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd - MMMM - yyyy", new Locale("id", "ID"));
    public ServicePemesanan() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(No_Pemesanan) AS Jumlah FROM pemesanan";
        int limit = 16;
        int count = 0;
        
        String query = "SELECT pmsn.No_Pemesanan, pmsn.Tanggal_Pemesanan, "
                + "pmsn.Status_Pemesanan, pmsn.Total_Pemesanan, pmsn.Bayar, pmsn.Kembali, pmsn.ID_Supplier, "
                + "slr.Nama, pmsn.ID_Pengguna, pgn.Nama, pmsn.Jenis_Pembayaran FROM pemesanan pmsn "
                + "INNER JOIN supplier slr ON pmsn.ID_Supplier=slr.ID_Supplier "
                + "INNER JOIN pengguna pgn ON pmsn.ID_Pengguna=pgn.ID_Pengguna "
                + "ORDER BY No_Pemesanan DESC LIMIT "+(page-1) * limit+","+limit+"";
        
        try {
            PreparedStatement pst = connection.prepareStatement(sqlCount);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                count = rst.getInt("Jumlah");
            }
            
            rst.close();
            pst.close();
            
            pst = connection.prepareStatement(query);
            rst = pst.executeQuery();
            ModelSupplier modelSupplier = new ModelSupplier();
            ModelPengguna modelPengguna = new ModelPengguna();
            while(rst.next()) {
                String noPemeriksaan = rst.getString("No_Pemesanan");
                String idSupplier = rst.getString("ID_Supplier");
                String namaSupplier = rst.getString("slr.Nama");
                modelSupplier.setIdSupplier(idSupplier);
                modelSupplier.setNamaSupplier(namaSupplier);
                LocalDate dateTglPemesanan = LocalDate.parse(rst.getString("Tanggal_Pemesanan"), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tgl = formatter.format(dateTglPemesanan);
                int total = rst.getInt("Total_Pemesanan");
                double bayar = rst.getDouble("Bayar");
                double kembali = rst.getDouble("Kembali");
                String jenisPembayaran = rst.getString("Jenis_Pembayaran");
                String status = rst.getString("Status_Pemesanan");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                modelPengguna.setIdpengguna(idPengguna);
                modelPengguna.setNama(namaPengguna);
                
                StatusType type = StatusType.Send;
                if(status.equals("Diterima")) {
                    type = StatusType.Finish;
                }
                
                tabmodel.addRow(new ModelPemesanan(
                        noPemeriksaan, tgl, type, status, 
                        df.format(total), bayar, kembali, jenisPembayaran, modelSupplier, 
                        modelPengguna).toRowTable1());
            }
            rst.close();
            pst.close();
            
            int totalPage = (int) Math.ceil((double)count / limit);
            pagination.setPagination(page, totalPage);
        } catch(Exception ex) {
            ex.printStackTrace();
        }    
    }
    
    public void loadAll(DefaultTableModel tabmodel) {
        String query = "SELECT pmsn.No_Pemesanan, pmsn.Tanggal_Pemesanan, "
                + "pmsn.Status_Pemesanan, pmsn.Total_Pemesanan, pmsn.Bayar, pmsn.Kembali, pmsn.Jenis_Pembayaran, "
                + "pmsn.ID_Supplier, slr.Nama, pmsn.ID_Pengguna, pgn.Nama FROM pemesanan pmsn "
                + "INNER JOIN supplier slr ON pmsn.ID_Supplier=slr.ID_Supplier "
                + "INNER JOIN pengguna pgn ON pmsn.ID_Pengguna=pgn.ID_Pengguna ORDER BY No_Pemesanan DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            ModelSupplier modelSupplier = new ModelSupplier();
            ModelPengguna modelPengguna = new ModelPengguna();
            while(rst.next()) {
                String noPemeriksaan = rst.getString("No_Pemesanan");
                String idSupplier = rst.getString("ID_Supplier");
                String namaSupplier = rst.getString("slr.Nama");
                modelSupplier.setIdSupplier(idSupplier);
                modelSupplier.setNamaSupplier(namaSupplier);
                LocalDate dateTglPemesanan = LocalDate.parse(rst.getString("Tanggal_Pemesanan"), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tgl = formatter.format(dateTglPemesanan);
                int total = rst.getInt("Total_Pemesanan");
                double bayar = rst.getDouble("Bayar");
                double kembali = rst.getDouble("Kembali");
                String jenisPembayaran = rst.getString("Jenis_Pembayaran");
                String status = rst.getString("Status_Pemesanan");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                modelPengguna.setIdpengguna(idPengguna);
                modelPengguna.setNama(namaPengguna);
                
                StatusType type = StatusType.Send;
                if(status.equals("Diterima")) {
                    type = StatusType.Finish;
                }
                
                tabmodel.addRow(new ModelPemesanan(
                        noPemeriksaan, tgl, type, status, 
                        df.format(total), bayar, kembali, jenisPembayaran, modelSupplier, 
                        modelPengguna).toRowTable1());
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(JFrame parent, ModelPemesanan modelPemesanan) {
        String query = "INSERT INTO pemesanan (No_Pemesanan, Tanggal_Pemesanan, Status_Pemesanan, Total_Pemesanan, Bayar, Kembali, Jenis_Pembayaran, ID_Supplier, ID_Pengguna) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPemesanan.getNoPemesanan());
            pst.setString(2, modelPemesanan.getTglPemesanan());
            pst.setString(3, "Dikirim");
            pst.setString(4, modelPemesanan.getTotalPemesanan());
            pst.setDouble(5, modelPemesanan.getBayar());
            pst.setDouble(6, modelPemesanan.getKembali());
            pst.setString(7, modelPemesanan.getJenisPembayaran());
            pst.setString(8, modelPemesanan.getModelSupplier().getIdSupplier());
            pst.setString(9, modelPemesanan.getModelPengguna().getIdpengguna());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Pesanan baru telah ditambahkan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createNo() {
        String noPemesanan = null;
        Date date = new Date();
        String format = new SimpleDateFormat("yyMM").format(date);
        String query = "SELECT RIGHT(No_Pemesanan, 3) AS Nomor FROM pemesanan WHERE No_Pemesanan LIKE 'PMSN-"+format+"%' ORDER BY No_Pemesanan DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("Nomor"));
                number++;
                noPemesanan = "PMSN-" + format + "-" + String.format("%03d", number);
            } else {
                noPemesanan = "PMSN-"+ format +"-001";
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return noPemesanan;
    }
    
    public void updatePriceBuy(ModelBarang modelBarang) {
        String query = "UPDATE barang SET Harga_Beli="+modelBarang.getHarga_Beli()+" WHERE Kode_Barang='"+modelBarang.getKode_Barang()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
