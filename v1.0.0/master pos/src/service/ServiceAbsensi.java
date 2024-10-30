/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import model.ModelAbsensi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author usER
 */
public class ServiceAbsensi {
    
    private Connection connection;
    
    public ServiceAbsensi() {
        connection = Koneksi.getConnection();
    }
    
    private void addAttendenceIn(ModelAbsensi modelAbsensi) {
        String query = "INSERT INTO absensi (ID_Karyawan, Tanggal, Absensi_Masuk) VALUES(?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelAbsensi.getModelKaryawan().getIdKaryawan());
            pst.setString(2, modelAbsensi.getTanggal());
            pst.setString(3, modelAbsensi.getAbsenMasuk());
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void updateAttendenceIn(ModelAbsensi modelAbsensi) {
        String query = "UPDATE absensi SET Absensi_Masuk=? WHERE ID_Karyawan=? AND Tanggal=? ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelAbsensi.getAbsenKeluar());
            pst.setString(2, modelAbsensi.getModelKaryawan().getIdKaryawan());
            pst.setString(3, modelAbsensi.getTanggal());
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void addAttendenceOut(ModelAbsensi modelAbsensi) {
        String query = "INSERT INTO absensi (ID_Karyawan, Tanggal, Absensi_Keluar) VALUES(?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelAbsensi.getModelKaryawan().getIdKaryawan());
            pst.setString(2, modelAbsensi.getTanggal());
            pst.setString(3, modelAbsensi.getAbsenMasuk());
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void updateAttendenceOut(ModelAbsensi modelAbsensi) {
        String query = "UPDATE absensi SET Absensi_Keluar=? WHERE ID_Karyawan=? AND Tanggal=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelAbsensi.getAbsenKeluar());
            pst.setString(2, modelAbsensi.getModelKaryawan().getIdKaryawan());
            pst.setString(3, modelAbsensi.getTanggal());
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void validationSetAttendenceIn(ModelAbsensi modelAbsensi) {
        String query = "SELECT Absensi_Masuk FROM absensi WHERE ID_Karyawan='"+modelAbsensi.getModelKaryawan().getIdKaryawan()+"' "
                + "AND Tanggal='"+modelAbsensi.getTanggal()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                updateAttendenceIn(modelAbsensi);
            } else {
                addAttendenceIn(modelAbsensi);
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void validationSetAttendenceOut(ModelAbsensi modelAbsensi) {
        String query = "SELECT Absensi_Keluar FROM absensi WHERE ID_Karyawan='"+modelAbsensi.getModelKaryawan().getIdKaryawan()+"' "
                + "AND Tanggal='"+modelAbsensi.getTanggal()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                updateAttendenceOut(modelAbsensi);
            } else {
                addAttendenceOut(modelAbsensi);
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    private boolean cekInformation(ModelAbsensi modelAbsensi) {
        boolean valid = false;
        String query = "SELECT Absensi_Masuk, Absensi_Keluar FROM absensi WHERE ID_Karyawan='"+modelAbsensi.getModelKaryawan().getIdKaryawan()+"' "
                + "AND Tanggal='"+modelAbsensi.getTanggal()+"'";
        try {
            String attendenceIn = null;
            String attendenceOut = null;
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                attendenceIn = rst.getString("Absensi_Masuk");
                attendenceOut = rst.getString("Absensi_Keluar");
            }
            
            if(!attendenceIn.isEmpty() && !attendenceOut.isEmpty()) {
                valid = true;
            }
            rst.close();
            pst.close();
            
        } catch(Exception ex) {
        }
        return valid;
    }
    
    public void setInformation(ModelAbsensi modelAbsensi) {
        String query = "UPDATE absensi SET Keterangan=? WHERE ID_Karyawan='"+modelAbsensi.getModelKaryawan().getIdKaryawan()+"' "
                + "AND Tanggal='"+modelAbsensi.getTanggal()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            if(cekInformation(modelAbsensi)) {
                pst.setString(1, "Sudah Absen");
            } else {
                pst.setString(1, " - ");
            }
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String getNamaKaryawan(ModelAbsensi modelAbsensi) {
        String namaKaryawan = null;
        String query = "SELECT ID_Karyawan, Nama FROM karyawan WHERE ID_Karyawan='"+modelAbsensi.getModelKaryawan().getIdKaryawan()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                namaKaryawan = rst.getString("Nama");
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return namaKaryawan;
    }
    
    public String getKeteranganAbsen(ModelAbsensi modelAbsensi) {
        String keterangan = " - ";
        String query = "SELECT Keterangan FROM absensi WHERE ID_Karyawan='"+modelAbsensi.getModelKaryawan().getIdKaryawan()+"' "
                + "AND Tanggal='"+modelAbsensi.getTanggal()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                keterangan = rst.getString("Keterangan");
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return keterangan;
    }
}
