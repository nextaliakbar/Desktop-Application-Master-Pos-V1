/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.swing.JButton;
import model.ModelNotifikasi;
import model.ModelPemeriksaan;
/**
 *
 * @author usER
 */
public class ServiceRiwayatPasien {
    private Connection connection;
    private ServiceNotifikasi serviceNotifikasi = new ServiceNotifikasi();
    public ServiceRiwayatPasien() {
        connection = Koneksi.getConnection();
    }
    
    public List<String> getIdPasien(){
        List<String> idPasiens = new ArrayList<>();
        String query = "SELECT DISTINCT ID_Pasien FROM pemeriksaan";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String idPasien = rst.getString("ID_Pasien");
                idPasiens.add(idPasien);
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return idPasiens;
    }
    
    public void loadData(ModelPemeriksaan modelPemeriksaan, DefaultTableModel model, JButton btnNotif) {
        String query = "SELECT pmrn.No_Pemeriksaan, pmrn.ID_Pasien, psn.Nama, pmrn.Tanggal_Pemeriksaan FROM pemeriksaan pmrn "
                + "INNER JOIN pasien psn ON psn.ID_Pasien=pmrn.ID_Pasien WHERE pmrn.ID_Pasien='"+modelPemeriksaan.getModelPasien().getIdPasien()+"' "
                + "ORDER BY Tanggal_Pemeriksaan DESC LIMIT 1";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");        
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPemeriksaan = rst.getString("No_Pemeriksaan");
                String idPasien = rst.getString("ID_Pasien");
                String nama = rst.getString("Nama");
                String tglPemeriksaan = rst.getString("Tanggal_Pemeriksaan");
                LocalDate lastCheckDate = LocalDate.parse(tglPemeriksaan, formatter);
                LocalDate dateNow = LocalDate.now();
                String tglSekarang = dateNow.format(formatter);
                LocalDate estimateCheck = lastCheckDate.plusDays(30);
                String estimasiPemeriksaan = estimateCheck.format(formatter);
                
                String status = "";
                if(tglSekarang.equals(estimasiPemeriksaan)) {
                    ModelNotifikasi modelNotifikasi = new ModelNotifikasi();
                    modelNotifikasi.setIdNotifkasi(String.valueOf(new Random().nextInt(10000)));
                    modelNotifikasi.setNamaNotifikasi("Follow Up Pasien");
                    modelNotifikasi.setDeskripsi("pemberitahuan untuk menindak lanjuti pasien kepada ".concat(nama));
                    modelNotifikasi.setJenisNotifikasi(noPemeriksaan);
                    modelNotifikasi.setStatusSudahDibaca(false);
                    
                    if(validationAddNotificationFollowUp(modelNotifikasi)) {
                        serviceNotifikasi.addNotification(modelNotifikasi);
                        if(btnNotif != null) {
                            btnNotif.setText(serviceNotifikasi.getCountNotification() + "");
                        }
                    }
                    
                    status = "Tindak Lanjuti";
                }
                if(model != null) {
                    model.addRow(new String[]{noPemeriksaan, idPasien, nama, lastCheckDate.format(DateTimeFormatter.ofPattern("dd - MMMM - yyyy", 
                        new Locale("id", "ID"))), status});
                }
                
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void loadDataDetail(ModelPemeriksaan modelPemeriksaan, DefaultTableModel model) {
        String query = "SELECT No_Pemeriksaan, Tanggal_Pemeriksaan FROM pemeriksaan WHERE ID_Pasien='"+modelPemeriksaan.getModelPasien().getIdPasien()+"' ORDER BY Tanggal_Pemeriksaan DESC";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MMMM - yyyy", new Locale("id", "ID"));
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPemeriksaan = rst.getString("No_Pemeriksaan");
                String tglPemeriksaan = rst.getString("Tanggal_Pemeriksaan");
                LocalDate lastDate = LocalDate.parse(tglPemeriksaan, formatter);
                LocalDate nextDate = lastDate.plusDays(30);
                String strLastDate = lastDate.format(format);
                String estimate = nextDate.format(format);
                model.addRow(new String[]{noPemeriksaan, strLastDate, estimate});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean validationAddNotificationFollowUp(ModelNotifikasi modelNotifikasi) {
        String query = "SELECT Jenis_Notifikasi FROM notifikasi WHERE Jenis_Notifikasi='"+modelNotifikasi.getJenisNotifikasi()+"' ";
        try(PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rst = pst.executeQuery()) {
            
            if(rst.next()) {
                return false;
            }
            
            return true;
            
        } catch(SQLException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
