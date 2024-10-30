/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import model.ModelTindakan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ServiceTindakan {
    private Connection connection;
    
    public ServiceTindakan() {
        connection = Koneksi.getConnection();
    }
    
    public void loadData(DefaultTableModel model) {
        String query = "SELECT * FROM tindakan";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kodeTindakan = rst.getString("Kode_Tindakan");
                String namaTindakan = rst.getString("Nama_Tindakan");
                int biaya = rst.getInt("Biaya_Tindakan");
                model.addRow(new Object[]{kodeTindakan, namaTindakan, biaya});
            }
            rst.close();
            pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addData(JFrame parent, ModelTindakan modelTindakan) {
        String query = "INSERT INTO tindakan (Kode_Tindakan, Nama_Tindakan, Biaya_Tindakan) VALUES (?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelTindakan.getKodeTindakan());
            pst.setString(2, modelTindakan.getNamaTindakan());
            pst.setInt(3, modelTindakan.getBiaya());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Data Tindakan berhasil ditambahkan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateData(JFrame parent, ModelTindakan modelTindakan) {
        String query = "UPDATE tindakan SET Nama_Tindakan=?, Biaya_Tindakan=? WHERE Kode_Tindakan=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelTindakan.getNamaTindakan());
            pst.setInt(2, modelTindakan.getBiaya());
            pst.setString(3, modelTindakan.getKodeTindakan());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Data Tindakan berhasil diperbarui");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deletaData(JFrame parent, ModelTindakan modelTindakan) {
        String query = "DELETE FROM tindakan WHERE Kode_Tindakan=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelTindakan.getKodeTindakan());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Data Tindakan berhasil dihapus");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createKodeTindakan() {
        String kodeTindakan = null;
        String query = "SELECT RIGHT(Kode_Tindakan, 3) AS Kode FROM Tindakan ORDER BY Kode_Tindakan DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("Kode"));
                number++;
                kodeTindakan = "TDKN-" + String.format("%03d", number);
            } else {
                kodeTindakan = "TDKN-001";
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return kodeTindakan;
    }
    
    public boolean validationDelete(JFrame parent, ModelTindakan modelTindakan) {
        boolean valid = false;
        String query = "SELECT Kode_Tindakan FROM detail_pemeriksaan WHERE Kode_Tindakan='"+modelTindakan.getKodeTindakan()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                JOptionPane.showMessageDialog(parent, "Tidak dapat menghapus tindakan ini\n"
               + "Tindakan ini pernah digunakan di transaksi", "Peringatan", JOptionPane.WARNING_MESSAGE);
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
       
