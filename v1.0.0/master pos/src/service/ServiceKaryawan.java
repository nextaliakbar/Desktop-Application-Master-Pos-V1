/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelKaryawan;

/**
 *
 * @author Alfito Dwi
 */
public class ServiceKaryawan {
    private Connection connection;

    public ServiceKaryawan() {
        connection = Koneksi.getConnection();
    }
     public void loadData(DefaultTableModel tabmodel) {
        String query ="SELECT * FROM karyawan";            
        try {
           PreparedStatement pst = connection.prepareStatement(query);
           ResultSet rst = pst.executeQuery();
           while(rst.next()){
               String IdKaryawan = rst.getString("ID_Karyawan");
               String NamaKaryawan = rst.getString("Nama");
               String TeleponKaryawan = rst.getString("No_Telp");
               String EmailKaryawan = rst.getString("Email");
               String AlamatKaryawan = rst.getString("Alamat");
               String JabatanKaryawan = rst.getString("Jabatan");
               String StatusKaryawan = rst.getString("Status_Karyawan");
               tabmodel.addRow(new Object[]{IdKaryawan, NamaKaryawan, TeleponKaryawan, 
               EmailKaryawan, AlamatKaryawan, JabatanKaryawan, StatusKaryawan});
           }
           rst.close();
           pst.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void addData(JFrame parent, ModelKaryawan modelKaryawan) {
        String query = "INSERT INTO karyawan (ID_Karyawan, Nama, No_Telp, Email, Alamat, Jabatan, Status_Karyawan) VALUES (?,?,?,?,?,?,?)";
        try {
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelKaryawan.getIdKaryawan());
           pst.setString(2, modelKaryawan.getNama());
           pst.setString(3, modelKaryawan.getNoTelp());
           pst.setString(4, modelKaryawan.getEmail());
           pst.setString(5, modelKaryawan.getAlamat());
           pst.setString(6, modelKaryawan.getJabatan());
           pst.setString(7, modelKaryawan.getStatus());
           pst.executeUpdate();
           pst.close();
           JOptionPane.showMessageDialog(parent, "Data Karyawan Berhasil Ditambahkan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    
    }
    public void updateData(JFrame parent, ModelKaryawan modelKaryawan){
     String query = "UPDATE karyawan SET Nama=?, No_Telp=?, Email=?, Jabatan=?, Alamat=?, Status_Karyawan=? WHERE ID_Karyawan=?";
     try {
          PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelKaryawan.getNama());
           pst.setString(2, modelKaryawan.getNoTelp());
           pst.setString(3, modelKaryawan.getEmail());
           pst.setString(4, modelKaryawan.getJabatan());
           pst.setString(5, modelKaryawan.getAlamat());
           pst.setString(6, modelKaryawan.getStatus());
           pst.setString(7, modelKaryawan.getIdKaryawan());
           pst.executeUpdate();
           JOptionPane.showMessageDialog(parent, "Data Karyawan  Berhasil Diperbarui");
           pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }
    public void deleteData(JFrame parent, ModelKaryawan modelKaryawan){
    String query = "DELETE FROM karyawan WHERE ID_Karyawan=?";
    try{
        PreparedStatement pst = connection.prepareCall(query);
        pst.setString(1, modelKaryawan.getIdKaryawan());
        pst.executeUpdate();
        pst.close();
        JOptionPane.showMessageDialog(parent, "Data Karyawan Berhasil Di Hapus");
    } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createId() {
        String idPasien = null;
        String query = "SELECT RIGHT(ID_Karyawan, 3) AS ID FROM karyawan ORDER BY ID_Karyawan DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("ID"));
                number++;
                idPasien = "STAFF-" + String.format("%03d", number);
            } else {
                idPasien = "STAFF-001";
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return idPasien;
    }
    
    public boolean validationDelete(JFrame parent, ModelKaryawan modelKaryawan) {
        boolean valid = false;
        String query = "SELECT ID_Karyawan FROM pemeriksaan WHERE ID_Karyawan='"+modelKaryawan.getIdKaryawan()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                JOptionPane.showMessageDialog(parent, "Tidak dapat menghapus karyawan ini\n"
               + "Karyawan ini pernah melakukan\n"
               + "Transaksi silahkan ubah status\n"
               + "Karyawan ini menjadi nonaktif", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                valid = true;
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }
}
                
