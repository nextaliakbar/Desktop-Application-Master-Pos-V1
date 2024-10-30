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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelBarang;
import model.ModelPenjualan;
import swing.Pagination;
/**
 *
 * @author usER
 */
public class ServicePenjualan {
    private Connection connection;
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd - MMMM - yyyy", new Locale("id", "ID"));
    public ServicePenjualan() {
        connection = Koneksi.getConnection();
    }
    
        public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(No_Penjualan) AS Jumlah FROM penjualan";
        int limit = 16;
        int count = 0;
        
        String query = "SELECT pjl.No_Penjualan, pjl.Tanggal, "
                + "pjl.Total_Penjualan, pjl.Bayar, pjl.Kembali, pjl.Jenis_Pembayaran, pjl.ID_Pengguna, "
                + "pgn.Nama FROM penjualan pjl INNER JOIN pengguna pgn ON pjl.ID_Pengguna=pgn.ID_Pengguna "
                + "ORDER BY No_Penjualan DESC LIMIT "+(page-1) * limit+","+limit+"";
        
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
            while(rst.next()) {
                String noPenjualan = rst.getString("No_Penjualan");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                LocalDate dateTglPenjualan = LocalDate.parse(rst.getString("Tanggal"), 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tglPenjualan = formatter.format(dateTglPenjualan);
                int total = rst.getInt("Total_Penjualan");
                double bayar = rst.getDouble("Bayar");
                double kembali = rst.getDouble("Kembali");
                String jenisPembayaran = rst.getString("Jenis_Pembayaran");
                tabmodel.addRow(new Object[]{noPenjualan, idPengguna, namaPengguna, tglPenjualan, df.format(total), bayar, kembali, jenisPembayaran});
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
        String query = "SELECT pjl.No_Penjualan,pjl.Tanggal, "
                + "pjl.Total_Penjualan, pjl.Bayar, pjl.Kembali, pjl.Jenis_Pembayaran, pjl.ID_Pengguna, "
                + "pgn.Nama FROM penjualan pjl INNER JOIN pengguna pgn ON pjl.ID_Pengguna=pgn.ID_Pengguna "
                + "ORDER BY No_Penjualan DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPenjualan = rst.getString("No_Penjualan");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                LocalDate dateTglPenjualan = LocalDate.parse(rst.getString("Tanggal"), 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tglPenjualan = formatter.format(dateTglPenjualan);
                int total = rst.getInt("Total_Penjualan");
                double bayar = rst.getDouble("Bayar");
                double kembali = rst.getDouble("Kembali");
                String jenisPembayaran = rst.getString("Jenis_Pembayaran");
                tabmodel.addRow(new Object[]{noPenjualan, idPengguna, namaPengguna, tglPenjualan, df.format(total), bayar, kembali, jenisPembayaran});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(JFrame parent, ModelPenjualan modelPenjualan) {
        String query = "INSERT INTO penjualan (No_Penjualan, Tanggal, Total_Penjualan, Total_Keuntungan, Bayar, Kembali, Jenis_Pembayaran, ID_Pengguna) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPenjualan.getNoPenjualan());
            pst.setString(2, modelPenjualan.getTglPenjualan());
            pst.setString(3, modelPenjualan.getTotalPenjualan());
            pst.setInt(4, modelPenjualan.getTotalKeuntungan());
            pst.setDouble(5, modelPenjualan.getBayar());
            pst.setDouble(6, modelPenjualan.getKembali());
            pst.setString(7, modelPenjualan.getJenisPembayaran());
            pst.setString(8, modelPenjualan.getModelPengguna().getIdpengguna());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Berhasil");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createNo() {
        String noPenjualan = null;
        Date date = new Date();
        String format = new SimpleDateFormat("yyMM").format(date);
        String query = "SELECT RIGHT(No_Penjualan, 3) AS Nomor FROM penjualan WHERE No_Penjualan LIKE 'PJLN-"+format+"%' ORDER BY No_Penjualan DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("Nomor"));
                number++;
                noPenjualan = "PJLN-" + format + "-" + String.format("%03d", number);
            } else {
                noPenjualan = "PJLN-"+format+"-001";
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return noPenjualan;
    }
    
    public List<ModelBarang> setFieldBrg(ModelBarang modelBarang) {
        List<ModelBarang> listDetail = new ArrayList<>();
        String query = "SELECT Kode_Barang, Nama_Barang, Satuan, Harga_Beli, Harga_Jual, Stok FROM barang WHERE Nomor_Barcode='"+modelBarang.getNomor_Barcode()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                ModelBarang barang = new ModelBarang();
                barang.setKode_Barang(rst.getString("Kode_Barang"));
                barang.setNama_Barang(rst.getString("Nama_Barang"));
                barang.setSatuan(rst.getString("Satuan"));
                barang.setHarga_Beli(rst.getInt("Harga_Beli"));
                barang.setHarga_Jual(rst.getInt("Harga_Jual"));
                barang.setStok(rst.getInt("Stok"));
                listDetail.add(barang);
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return listDetail;
    }
}
