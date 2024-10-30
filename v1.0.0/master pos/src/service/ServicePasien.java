/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import model.ModelPasien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ServicePasien {
    private Connection connection;
    
    public ServicePasien() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(DefaultTableModel model) {
        String query = "SELECT * FROM pasien";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            pst = connection.prepareStatement(query);
            rst = pst.executeQuery();
            while(rst.next()) {
                String idPasien = rst.getString("Id_Pasien");
                String nama = rst.getString("Nama");
                String jenisKelamin = rst.getString("Jenis_Kelamin");
                String no_Telp = rst.getString("No_Telp");
                String alamat = rst.getString("Alamat");
                String email = rst.getString("Email");
                String level = rst.getString("Level");
                model.addRow(new Object[]{idPasien, nama, jenisKelamin, no_Telp, email, alamat, level});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(JFrame parent, ModelPasien modelPasien) {
        String query = "INSERT INTO pasien (Id_Pasien, Nama, Jenis_Kelamin, No_Telp, Alamat, Email, Level) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPasien.getIdPasien());
            pst.setString(2, modelPasien.getNama());
            pst.setString(3, modelPasien.getJenisKelamin());
            pst.setString(4, modelPasien.getNoTelp());
            pst.setString(5, modelPasien.getAlamat());
            pst.setString(6, modelPasien.getEmail());
            pst.setString(7, modelPasien.getLevel());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Data Pasien berhasil ditambahkan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateData(JFrame parent, ModelPasien modelPasien) {
        String query = "UPDATE pasien SET Nama=?, Jenis_Kelamin=?, No_Telp=?, Alamat=?, Email=?, Level=? WHERE Id_Pasien=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPasien.getNama());
            pst.setString(2, modelPasien.getJenisKelamin());
            pst.setString(3, modelPasien.getNoTelp());
            pst.setString(4, modelPasien.getAlamat());
            pst.setString(5, modelPasien.getEmail());
            pst.setString(6, modelPasien.getLevel());
            pst.setString(7, modelPasien.getIdPasien());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Data Pasien berhasil diperbarui");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteData(JFrame parent, ModelPasien modelPasien) {
        String query = "DELETE FROM pasien WHERE Id_Pasien=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPasien.getIdPasien());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Data Pasien berhasil dihapus");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createId() {
        String idPasien = null;
        String query = "SELECT RIGHT(ID_Pasien, 3) AS ID FROM pasien ORDER BY ID_Pasien DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("ID"));
                number++;
                idPasien = "PASIEN" + String.format("%03d", number);
            } else {
                idPasien = "PASIEN001";
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return idPasien;
    }
    
    public boolean validationDelete(JFrame parent, ModelPasien modelPasien) {
        boolean valid = false;
        String query1 = "SELECT ID_Pasien FROM reservasi WHERE ID_Pasien='"+modelPasien.getIdPasien()+"' ";
        String query2 = "SELECT ID_Pasien FROM pemeriksaan WHERE ID_Pasien='"+modelPasien.getIdPasien()+"' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(query1);
            ResultSet rst1 = pst1.executeQuery();
            PreparedStatement pst2 = connection.prepareStatement(query2);
            ResultSet rst2 = pst2.executeQuery();
            if(rst1.next() || rst2.next()) {
                JOptionPane.showMessageDialog(parent, "Tidak dapat menghapus pasien ini\n"
               + "Pasien ini pernah melakukan transaksi", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                valid = true;
            }
            rst1.close();
            rst2.close();
            pst1.close();
            pst2.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }
    
}
