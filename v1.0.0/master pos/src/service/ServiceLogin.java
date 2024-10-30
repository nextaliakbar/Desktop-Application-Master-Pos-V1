/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import component.PanelLoading;
import component.PanelVerifyCode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Main;
import util.ModelMessage;
import model.ModelPengguna;
/**
 *
 * @author usER
 */
public class ServiceLogin {
    private Connection connection;
    
    public ServiceLogin() {
        connection = Koneksi.getConnection();
    }
    
//    Login
    public void login(JFrame parent, ModelPengguna modelPengguna, PanelLoading panelLoading, JFrame frameLogin) {
        String query = "SELECT * FROM pengguna WHERE (Username=? OR Email=?) AND Password=?";
            new Thread(()-> {
                panelLoading.setVisible(true);
                    try {
                        PreparedStatement pst = connection.prepareStatement(query);
                        pst.setString(1, modelPengguna.getUsername());
                        pst.setString(2, modelPengguna.getEmail());
                        pst.setString(3, modelPengguna.getPassword());
                        ResultSet rst = pst.executeQuery();
                        if(rst.next()) {
                            String idPenguna = rst.getString("ID_Pengguna");
                            String namaPengguna = rst.getString("Nama");
                            String username = rst.getString("Username");
                            String email = rst.getString("Email");
                            String level = rst.getString("Level");
                            String password = rst.getString("Password");
                            String status = rst.getString("Status_Pengguna");
                            if(status.equals("Aktif")) {
                                ModelPengguna pengguna = new ModelPengguna(idPenguna, namaPengguna, username, password, email, level,"");
                                Main main = new Main(pengguna);
                                panelLoading.setVisible(false);
                                main.setVisible(true);
                                frameLogin.dispose();   
                            } else {
                                panelLoading.setVisible(false);
                                JOptionPane.showMessageDialog(parent, "Pengguna ini sudah tidak aktif");
                            }
                        } else {
                            try {
                                panelLoading.setVisible(true);
                                Thread.sleep(Duration.ofSeconds(2));
                                panelLoading.setVisible(false);
                                JOptionPane.showMessageDialog(parent, "Username atau Email\ndan Password Salah");
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ServiceLogin.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        rst.close();
                        pst.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ServiceLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }).start();
        
    }
    
    private String generateVerifyCode() throws SQLException{
        DecimalFormat df = new DecimalFormat("000000");
        Random random = new Random();
        String verifyCode = df.format(random.nextInt(1000000));
        while(checkDuplicateVerifyCode(verifyCode)) {
            verifyCode = df.format(random.nextInt(1000000));
        }
        return verifyCode;
    }
    
    private boolean checkDuplicateVerifyCode(String verifyCode) throws SQLException{
        boolean check = false;
        String query = "SELECT ID_Pengguna FROM pengguna WHERE Kode_Verifikasi='"+verifyCode+"'";
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rst = pst.executeQuery();
        if(rst.next()) {
            check = true;
        }
        rst.close();
        pst.close();
        return check;
    }
    
//    Forgot Password
    public boolean checkEmail(JFrame parent, ModelPengguna modelPengguna) {
        boolean check = false;
        String query = "SELECT Email FROM pengguna WHERE Email='"+modelPengguna.getEmail()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                check = true;
            } else {
                JOptionPane.showMessageDialog(parent, "Email tidak tedaftar");
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return check;
    }
    
    public boolean cekVerifyCode(JFrame parent, ModelPengguna modelPengguna) {
        boolean check = false;
        String query = "SELECT Kode_Verifikasi FROM pengguna WHERE Email='"+modelPengguna.getEmail()+"' AND "
                + "Kode_Verifikasi='"+modelPengguna.getKode_verifikasi()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                check = true;
            } else {
                JOptionPane.showMessageDialog(parent, "Kode Verifikasi Salah");
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return check;
    }
    
    public void doneVerify(JFrame parent, ModelPengguna modelPengguna) {
        String query = "UPDATE pengguna SET Password='"+modelPengguna.getPassword()+"', Kode_Verifikasi='' "
                + "WHERE Email='"+modelPengguna.getEmail()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Password berhasil diubah");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void cancelVerify(ModelPengguna modelPengguna) {
        String query = "UPDATE pengguna SET Kode_Verifikasi='' WHERE Email='"+modelPengguna.getEmail()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void getVerifyEmail(JFrame parent, ModelPengguna modelPengguna, PanelVerifyCode verifyCode, PanelLoading panelLoading) {
        String query = "UPDATE pengguna SET Kode_Verifikasi=? WHERE Email=?";
        try {
            modelPengguna.setKode_verifikasi(generateVerifyCode());
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPengguna.getKode_verifikasi());
            pst.setString(2, modelPengguna.getEmail());
            if(modelPengguna.getConfirPass().equals(modelPengguna.getPassword())) {
                pst.executeUpdate();
                sendEmail(modelPengguna, verifyCode, panelLoading);
            } else {
                JOptionPane.showMessageDialog(parent, "Konfirmasi Password Salah");
            }
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void sendEmail(ModelPengguna modelPengguna, PanelVerifyCode verifyCode, PanelLoading panelLoading) {
        new Thread(()-> {
            panelLoading.setVisible(true);
            ModelMessage modelMessage = new ServiceMail().sendMail(modelPengguna.getEmail(), modelPengguna.getKode_verifikasi());
            if(modelMessage.isSucces()) {
                panelLoading.setVisible(false);
                verifyCode.setVisible(true);
            } else{
                panelLoading.setVisible(false);
            }
        }).start();
    }
    
    
}
