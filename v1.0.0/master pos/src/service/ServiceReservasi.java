/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelPasien;
import model.ModelPengguna;
import model.ModelReservasi;
import swing.Pagination;
import swing.StatusType;
/**
 *
 * @author usER
 */
public class ServiceReservasi {
    private Connection connection;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd - MMMM - yyyy", new Locale("id", "ID"));
    public ServiceReservasi() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(int page, DefaultTableModel tabmodel, Pagination pagination) {
        String sqlCount = "SELECT COUNT(No_Reservasi) AS Jumlah FROM reservasi";
        int limit = 16;
        int count = 0;
        
        String query = "SELECT rsv.No_Reservasi, rsv.Tanggal_Reservasi, "
                + "rsv.ID_Pasien, psn.Nama, psn.Jenis_Kelamin, rsv.Tanggal_Kedatangan, "
                + "TIME_FORMAT(rsv.Jam_Kedatangan, '%H.%i WIB') AS Jam_Kedatangan, rsv.Status_Reservasi, rsv.ID_Pengguna, pgn.Nama FROM reservasi rsv "
                + "JOIN pasien psn ON rsv.ID_Pasien=psn.ID_Pasien JOIN pengguna pgn "
                + "ON rsv.ID_Pengguna=pgn.ID_Pengguna ORDER BY No_Reservasi DESC LIMIT "+(page-1) * limit+","+limit+"";
        ModelPengguna modelPengguna = new ModelPengguna();
        ModelPasien modelPasien = new ModelPasien();
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
                String noReservasi = rst.getString("No_Reservasi");
                LocalDate dateTglReservasi = LocalDate.parse(rst.getString("Tanggal_Reservasi"), 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tglReservasi = formatter.format(dateTglReservasi);
                String idPasien = rst.getString("ID_Pasien");
                String namaPasien = rst.getString("psn.Nama");
                String jenisKelamin = rst.getString("Jenis_Kelamin");
                LocalDate dateTglKedatangan = LocalDate.parse(rst.getString("Tanggal_Kedatangan"), 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tglKedatangan = formatter.format(dateTglKedatangan);
                String jamKedatangan = rst.getString("Jam_Kedatangan");
                String status = rst.getString("Status_Reservasi");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                
                StatusType type;
                modelPasien.setIdPasien(idPasien);
                modelPasien.setNama(namaPasien);
                modelPasien.setJenisKelamin(jenisKelamin);
                modelPengguna.setIdpengguna(idPengguna);
                modelPengguna.setNama(namaPengguna);
                
                if(status.equals("Menunggu")) {
                    type = StatusType.Wait;
                } else if(status.equals("Selesai")) {
                    type = StatusType.Finish;
                } else {
                    type = StatusType.Cancel;
                }
                tabmodel.addRow(new ModelReservasi
                (noReservasi, tglReservasi, tglKedatangan, 
                jamKedatangan, type, status, modelPengguna, 
                modelPasien).toRowTable());
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
        String query = "SELECT rsv.No_Reservasi, rsv.Tanggal_Reservasi, "
        + "rsv.ID_Pasien, psn.Nama, psn.Jenis_Kelamin, rsv.Tanggal_Kedatangan, "
        + "TIME_FORMAT(rsv.Jam_Kedatangan, '%H.%i WIB') AS Jam_Kedatangan, rsv.Status_Reservasi, rsv.ID_Pengguna, pgn.Nama FROM reservasi rsv "
        + "JOIN pasien psn ON rsv.ID_Pasien=psn.ID_Pasien JOIN pengguna pgn "
        + "ON rsv.ID_Pengguna=pgn.ID_Pengguna ORDER BY No_Reservasi DESC ";
        ModelPengguna modelPengguna = new ModelPengguna();
        ModelPasien modelPasien = new ModelPasien();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()){
                String noReservasi = rst.getString("No_Reservasi");
                LocalDate dateTglReservasi = LocalDate.parse(rst.getString("Tanggal_Reservasi"), 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tglReservasi = formatter.format(dateTglReservasi);
                String idPasien = rst.getString("ID_Pasien");
                String namaPasien = rst.getString("psn.Nama");
                String jenisKelamin = rst.getString("Jenis_Kelamin");
                LocalDate dateTglKedatangan = LocalDate.parse(rst.getString("Tanggal_Kedatangan"), 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String tglKedatangan = formatter.format(dateTglKedatangan);
                String jamKedatangan = rst.getString("Jam_Kedatangan");
                String status = rst.getString("Status_Reservasi");
                String idPengguna = rst.getString("ID_Pengguna");
                String namaPengguna = rst.getString("pgn.Nama");
                
                StatusType type;
                modelPasien.setIdPasien(idPasien);
                modelPasien.setNama(namaPasien);
                modelPasien.setJenisKelamin(jenisKelamin);
                modelPengguna.setIdpengguna(idPengguna);
                modelPengguna.setNama(namaPengguna);
                
                if(status.equals("Menunggu")) {
                    type = StatusType.Wait;
                } else if(status.equals("Selesai")) {
                    type = StatusType.Finish;
                } else {
                    type = StatusType.Cancel;
                }
                tabmodel.addRow(new ModelReservasi
                (noReservasi, tglReservasi, tglKedatangan, 
                jamKedatangan, type, status, modelPengguna, 
                modelPasien).toRowTable());
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(JFrame parent, ModelReservasi modelReservasi) {
        String query = "INSERT INTO reservasi (No_Reservasi, Tanggal_Reservasi, Tanggal_Kedatangan, Jam_Kedatangan, Status_Reservasi, ID_Pasien, ID_Pengguna) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelReservasi.getNoReservasi());
            pst.setString(2, modelReservasi.getTglReservasi());
            pst.setString(3, modelReservasi.getTglKedatangan());
            pst.setString(4, modelReservasi.getJamKedatangan());
            pst.setString(5, "Menunggu");
            pst.setString(6, modelReservasi.getModelPasien().getIdPasien());
            pst.setString(7, modelReservasi.getModelPengguna().getIdpengguna());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Reservasi baru berhasil ditambahkan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateStatus(JFrame parent, ModelReservasi modelReservasi) {
        String query = "UPDATE reservasi SET Status_Reservasi='Batal' WHERE No_Reservasi=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelReservasi.getNoReservasi());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Reservasi berhasil dibatalkan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createNo() {
        String noReservasi = null;
        LocalDate date = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyMM");
        String strDate = date.format(format);
        String query = "SELECT RIGHT (No_Reservasi, 3) AS Nomor FROM reservasi WHERE No_Reservasi LIKE 'RSV-"+strDate+"%' ORDER BY Nomor DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int nomor = Integer.parseInt(rst.getString("Nomor"));
                nomor++;
                noReservasi = "RSV-" + strDate + "-" + String.format("%03d", nomor);
            } else {
                noReservasi = "RSV-" + strDate + "-001";
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return noReservasi;
    }
    
    public void loadDataPasien(DefaultTableModel tabmodel) {
        String query = "SELECT ID_Pasien, Nama, Jenis_Kelamin, Alamat, No_Telp FROM pasien";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String idPasien = rst.getString("ID_Pasien");
                String nama = rst.getString("Nama");
                String jenisKelamin = rst.getString("Jenis_Kelamin");
                String alamat = rst.getString("Alamat");
                String noTelp = rst.getString("No_Telp");
                
                tabmodel.addRow(new String[] {idPasien, nama, jenisKelamin, alamat, noTelp});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
