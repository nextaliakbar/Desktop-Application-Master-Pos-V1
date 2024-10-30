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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelPasien;
import model.ModelPemeriksaan;
import swing.Pagination;
/**
 *
 * @author usER
 */
public class ServicePemeriksaan {
    private Connection connection;
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd - MMMM - yyyy", new Locale("id", "ID"));
    public ServicePemeriksaan() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(No_Pemeriksaan) AS Jumlah FROM pemeriksaan";
        int limit = 16;
        int count = 0;
        
        String query = "SELECT pmn.No_Pemeriksaan,pmn.No_Reservasi, pmn.Tanggal_Pemeriksaan, "
                + "pmn.Deskripsi, pmn.Total, pmn.Bayar, pmn.Kembali, pmn.Jenis_Pembayaran, pmn.ID_Pasien, "
                + "psn.Nama, pmn.ID_Karyawan, krn.Nama, pmn.ID_Pengguna, pgn.Nama FROM pemeriksaan pmn "
                + "INNER JOIN pasien psn ON pmn.ID_Pasien=psn.ID_Pasien "
                + "INNER JOIN karyawan krn ON pmn.ID_Karyawan=krn.ID_Karyawan "
                + "INNER JOIN pengguna pgn ON pmn.ID_Pengguna=pgn.ID_Pengguna "
                + "ORDER BY No_Pemeriksaan DESC LIMIT "+(page-1) * limit+","+limit+"";
        
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
                String noPemeriksaan = rst.getString("No_Pemeriksaan");
                String noReservasi = rst.getString("No_Reservasi");
                String idPasien = rst.getString("ID_Pasien");
                String namaPasien = rst.getString("psn.Nama");
                String idKaryawan = rst.getString("ID_Karyawan");
                LocalDate dateTglPemeriksaan = LocalDate.parse(rst.getString("Tanggal_Pemeriksaan"), 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tgl = formatter.format(dateTglPemeriksaan);
                int total = rst.getInt("Total");
                String deskripsi = rst.getString("Deskripsi");
                double bayar = rst.getDouble("Bayar");
                double kembalian = rst.getDouble("Kembali");
                String jenisPembayaran = rst.getString("Jenis_Pembayaran");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                tabmodel.addRow(
                new Object[]{noPemeriksaan, noReservasi, idPasien, namaPasien, 
                idKaryawan, tgl, df.format(total), deskripsi, bayar, kembalian, 
                jenisPembayaran, idPengguna, namaPengguna});
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
        String query = "SELECT pmn.No_Pemeriksaan,pmn.No_Reservasi, pmn.Tanggal_Pemeriksaan, "
        + "pmn.Deskripsi, pmn.Total, pmn.Bayar, pmn.Kembali, pmn.Jenis_Pembayaran, pmn.ID_Pasien, "
        + "psn.Nama, pmn.ID_Karyawan, krn.Nama, pmn.ID_Pengguna, pgn.Nama FROM pemeriksaan pmn "
        + "INNER JOIN pasien psn ON pmn.ID_Pasien=psn.ID_Pasien "
        + "INNER JOIN karyawan krn ON pmn.ID_Karyawan=krn.ID_Karyawan "
        + "INNER JOIN pengguna pgn ON pmn.ID_Pengguna=pgn.ID_Pengguna "
        + "ORDER BY No_Pemeriksaan DESC";
        
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPemeriksaan = rst.getString("No_Pemeriksaan");
                String noReservasi = rst.getString("No_Reservasi");
                String idPasien = rst.getString("ID_Pasien");
                String namaPasien = rst.getString("psn.Nama");
                String idKaryawan = rst.getString("ID_Karyawan");
                LocalDate dateTglPemeriksaan = LocalDate.parse(rst.getString("Tanggal_Pemeriksaan"), 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tgl = formatter.format(dateTglPemeriksaan);
                int total = rst.getInt("Total");
                String deskripsi = rst.getString("Deskripsi");
                double bayar = rst.getDouble("Bayar");
                double kembalian = rst.getDouble("Kembali");
                String jenisPembayaran = rst.getString("Jenis_Pembayaran");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                tabmodel.addRow(
                new Object[]{noPemeriksaan, noReservasi, idPasien, namaPasien, 
                idKaryawan, tgl, df.format(total), deskripsi, bayar, kembalian, 
                jenisPembayaran, idPengguna, namaPengguna});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
        
    public void addData(JFrame parent, ModelPemeriksaan modelPemeriksaan) {
        String query = "INSERT INTO pemeriksaan (No_Pemeriksaan, No_Reservasi, Tanggal_Pemeriksaan, "
                + "Deskripsi, Status_Pemeriksaan, Total, Bayar, Kembali, Jenis_Pembayaran, "
                + "ID_Pasien, ID_Karyawan, ID_Pengguna) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPemeriksaan.getNoPemeriksaan());
            pst.setString(2, modelPemeriksaan.getModelReservasi().getNoReservasi());
            pst.setString(3, modelPemeriksaan.getTglPemeriksaan());
            pst.setString(4, modelPemeriksaan.getDeskripsi());
            pst.setString(5, "Selesai");
            pst.setString(6, modelPemeriksaan.getTotal());
            pst.setDouble(7, modelPemeriksaan.getBayar());
            pst.setDouble(8, modelPemeriksaan.getKembali());
            pst.setString(9, modelPemeriksaan.getJenisPembayaran());
            pst.setString(10, modelPemeriksaan.getModelPasien().getIdPasien());
            pst.setString(11, modelPemeriksaan.getModelKaryawan().getIdKaryawan());
            pst.setString(12, modelPemeriksaan.getModelPengguna().getIdpengguna());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(parent, "Data Berhasil Ditambahkan");
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createNo() {
        String noPemeriksaan = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        Date date = new Date();
        String format = sdf.format(date);
        String query = "SELECT RIGHT(No_Pemeriksaan, 3) AS Nomor FROM pemeriksaan WHERE No_Pemeriksaan LIKE 'PMRN-"+format+"%' ORDER BY Nomor DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int no = Integer.parseInt(rst.getString("Nomor"));
                no++;
                noPemeriksaan = "PMRN-" + format + "-" +String.format("%03d", no);
            } else {
                noPemeriksaan = "PMRN-" + format + "-001";
            }
            rst.close();
            pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return noPemeriksaan;
    }

    public void readReservasi(JComboBox<String> comboBox) {
        String query = "SELECT No_Reservasi FROM reservasi WHERE Status_Reservasi='Menunggu'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noReservasi = rst.getString("No_Reservasi");
                comboBox.addItem(noReservasi);
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void readLabel(String noReservasi, JLabel lbTgl, JLabel lbId, JLabel lbNama) {
        String query = "SELECT rsv.Tanggal_Kedatangan, rsv.ID_Pasien, psn.Nama FROM reservasi rsv "
                + "INNER JOIN pasien psn ON rsv.ID_Pasien=psn.ID_Pasien WHERE No_Reservasi='"+noReservasi+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                String tgl = rst.getString("Tanggal_Kedatangan");
                String id = rst.getString("ID_Pasien");
                String nama = rst.getString("Nama");
                lbTgl.setText(tgl);
                lbId.setText(id);
                lbNama.setText(nama);
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int promo() {
        int banyakPromo = 0;
        String query = "SELECT Banyak_Promo FROM promo WHERE Keterangan='Berjalan'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                banyakPromo = rst.getInt("Banyak_Promo");
            } else {
                banyakPromo = 0;
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return banyakPromo;
    }
    
    public boolean checkMember(String idMember) {
        boolean member = false;
        String query = "SELECT Level FROM pasien WHERE ID_Pasien='"+idMember+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
               String level = rst.getString("Level");
               if(level.equals("Member")) {
                   member = true;
               }
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return member;
    }
    
    public boolean validastionAddData(ModelPasien modelPasien) {
        boolean valid = true;
        String query = "SELECT ID_Pasien FROM pasien WHERE ID_Pasien='"+modelPasien.getIdPasien()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                valid = false;
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }
}
