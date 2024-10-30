/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.ModelPengguna;
/**
 *
 * @author usER
 */
public class ServicePengaturan {
    private Connection connection;
    public ServicePengaturan() {
        connection = Koneksi.getConnection();
    }
    
//    Account
    public List<String> loadAccount(ModelPengguna modelPengguna) {
        List<String> listData = new ArrayList<>();
        String query = "SELECT Nama, Username, Email FROM pengguna WHERE ID_Pengguna='"+modelPengguna.getIdpengguna()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                listData.add(rst.getString("Nama"));
                listData.add(rst.getString("Username"));
                listData.add(rst.getString("Email"));
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return listData;
    }
    
    public void setAccount(JFrame parent, ModelPengguna modelPengguna) {
        String query = "UPDATE pengguna SET Nama=?, Username=?, Email=? WHERE ID_Pengguna=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPengguna.getNama());
            pst.setString(2, modelPengguna.getUsername());
            pst.setString(3, modelPengguna.getEmail());
            pst.setString(4, modelPengguna.getIdpengguna());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Akun berhasil dirubah");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
//    Change Password
    public void setPassword(JFrame parent, ModelPengguna modelPengguna) {
        String query = "UPDATE pengguna SET Password=? WHERE ID_Pengguna=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPengguna.getPassword());
            pst.setString(2, modelPengguna.getIdpengguna());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Password berhasil dirubah");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
