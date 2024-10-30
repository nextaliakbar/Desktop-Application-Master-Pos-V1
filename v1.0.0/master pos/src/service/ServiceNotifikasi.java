/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.ModelNotifikasi;
/**
 *
 * @author usER
 */
public class ServiceNotifikasi {
    
    private Connection connection;
    
    public ServiceNotifikasi() {
        connection = Koneksi.getConnection();
    }
    
    public void addNotification(ModelNotifikasi modelNotifikasi) {
        String query = "INSERT INTO notifikasi (ID_Notifikasi, Tanggal_Notifikasi, Nama_Notifikasi, Deskripsi, Jenis_Notifikasi, Status_Sudah_Dibaca) "
                + "VALUES (?,?,?,?,?,?)";
        try(PreparedStatement pst = connection.prepareStatement(query)) {
           if(checkIdNotification(modelNotifikasi)) {
               pst.setString(1, String.valueOf(new Random().nextInt(10000)));
           } else {
              pst.setString(1, modelNotifikasi.getIdNotifkasi());   
           }
           pst.setTimestamp(2, Timestamp.valueOf(modelNotifikasi.getTanggalNotifikasi()));
           pst.setString(3, modelNotifikasi.getNamaNotifikasi());
           pst.setString(4, modelNotifikasi.getDeskripsi());
           pst.setString(5, modelNotifikasi.getJenisNotifikasi());
           pst.setBoolean(6, modelNotifikasi.isStatusSudahDibaca());
           pst.executeUpdate();
        } catch(SQLException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
    
    public void updateStatusNotification() {
        String query = "UPDATE notifikasi SET Status_Sudah_Dibaca=? WHERE Status_Sudah_Dibaca=?";
        try(PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setBoolean(1, true);
            pst.setBoolean(2, false);
            pst.executeUpdate();
        } catch(SQLException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
    
    public Integer getCountNotification() {
        String query = "SELECT COUNT(Status_Sudah_Dibaca) AS Total_Notifikasi FROM notifikasi WHERE Status_Sudah_Dibaca="+false+"";
        try(PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rst = pst.executeQuery()) {
            if(rst.next()) {
              return rst.getInt("Total_Notifikasi");
            }
            return 0;
        } catch(SQLException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
    
    public List<ModelNotifikasi> getAllByFilter(ModelNotifikasi modelNotifikasi) {
        List<ModelNotifikasi> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifikasi WHERE Nama_Notifikasi=? ORDER BY Tanggal_Notifikasi DESC";
        try(PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, modelNotifikasi.getNamaNotifikasi());
            try(ResultSet rst = pst.executeQuery()) {
                    while(rst.next()) {
                    ModelNotifikasi notifikasi = new ModelNotifikasi();
                    notifikasi.setIdNotifkasi(rst.getString("ID_Notifikasi"));
                    notifikasi.setTanggalNotifikasi(rst.getTimestamp("Tanggal_Notifikasi").toLocalDateTime());
                    notifikasi.setNamaNotifikasi(rst.getString("Nama_Notifikasi"));
                    notifikasi.setDeskripsi(rst.getString("Deskripsi"));
                    notifikasi.setJenisNotifikasi(rst.getString("Jenis_Notifikasi"));
                    notifikasi.setStatusSudahDibaca(rst.getBoolean("Status_Sudah_Dibaca"));
                    notifications.add(notifikasi);
                }
            }
            return notifications;
        } catch(SQLException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
    
    public List<ModelNotifikasi> getAll() {
        List<ModelNotifikasi> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifikasi ORDER BY Tanggal_Notifikasi DESC";
        try(PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rst = pst.executeQuery()) {
            while(rst.next()) {
                ModelNotifikasi modelNotifikasi = new ModelNotifikasi();
                modelNotifikasi.setIdNotifkasi(rst.getString("ID_Notifikasi"));
                modelNotifikasi.setTanggalNotifikasi(rst.getTimestamp("Tanggal_Notifikasi").toLocalDateTime());
                modelNotifikasi.setNamaNotifikasi(rst.getString("Nama_Notifikasi"));
                modelNotifikasi.setDeskripsi(rst.getString("Deskripsi"));
                modelNotifikasi.setJenisNotifikasi(rst.getString("Jenis_Notifikasi"));
                modelNotifikasi.setStatusSudahDibaca(rst.getBoolean("Status_Sudah_Dibaca"));
                notifications.add(modelNotifikasi);
            }
            return notifications;
        } catch(SQLException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
    
    private boolean checkIdNotification(ModelNotifikasi modelNotifikasi) {
        String query = "SELECT ID_Notifikasi FROM notifikasi WHERE ID_Notifikasi='"+modelNotifikasi.getIdNotifkasi()+"' ";
        try(PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rst = pst.executeQuery()) {
            if(rst.next()) {
                return true;
            }
            
            return false;
            
        } catch(SQLException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
        
}
