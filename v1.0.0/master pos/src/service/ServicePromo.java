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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelPromo;
/**
 *
 * @author usER
 */
public class ServicePromo {
    
    private Connection connection;
    
    public ServicePromo() {
        connection = Koneksi.getConnection();
    }
    
    //    service atur promo
    public void addPromo(JFrame parent, ModelPromo modelPromo) {
        String query = "INSERT INTO promo (No_Promo, Nama_Promo, Tanggal_Awal, Tanggal_Akhir, Banyak_Promo, Jenis_Promo, Keterangan) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, modelPromo.getNoPromo());
            pst.setString(2, modelPromo.getNamaPromo());
            pst.setString(3, modelPromo.getTglAwal());
            pst.setString(4, modelPromo.getTglAkhir());
            pst.setInt(5, modelPromo.getBanyakPromo());
            pst.setString(6, modelPromo.getJenisPromo());
            pst.setString(7, modelPromo.getKeterangan());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Berhasil menambahkan promo");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createNo() {
        String noPromosi = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        Date date = new Date();
        String format = sdf.format(date);
        String query = "SELECT RIGHT(No_Promo, 3) AS Nomor FROM promo WHERE No_Promo LIKE 'P-"+format+"%' ORDER BY Nomor DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int no = Integer.parseInt(rst.getString("Nomor"));
                no++;
                noPromosi = "P-" + format + "-"+String.format("%03d", no);
            } else {
                noPromosi = "P-" + format + "-001";
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return noPromosi;
    }
    
    
//  loadPromo
    public void loadPromo(DefaultTableModel tabmodel, String query) {
        DecimalFormat df = new DecimalFormat("#,##0.##");
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noPromo = rst.getString("No_Promo");
                String nama = rst.getString("Nama_Promo");
                String tglAwal = rst.getString("Tanggal_Awal");
                String tglAkhir = rst.getString("Tanggal_Akhir");
                String rentang = tglAwal + " Sampai " + tglAkhir;
                int lots = rst.getInt("Banyak_Promo");
                String banyak;
                if(lots <= 100) {
                    banyak = rst.getString("Banyak_Promo").concat(" %");
                } else {
                    banyak  = df.format(Integer.parseInt(rst.getString("Banyak_Promo")));
                }
                String jenis = rst.getString("Jenis_Promo");
                String keterangan = rst.getString("Keterangan");
                tabmodel.addRow(new String[]{noPromo,nama, rentang, banyak, jenis, keterangan});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
//    end promo
    public void endPromo(JFrame parent, ModelPromo promo) {
        String query = "UPDATE promo SET Keterangan='Berakhir' WHERE No_Promo='"+promo.getNoPromo()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(parent, "Promo berhasil di akhiri");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String lastDatePromo(ModelPromo promo) {
        String lastDay = null;
        String query = "SELECT Tanggal_Akhir FROM promo WHERE No_Promo='"+promo.getNoPromo()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                lastDay = rst.getString("Tanggal_Akhir");
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return lastDay;
    }
    
//    Atur Promo Otomatis Berkhir
    private void setAutoEndPromo(ModelPromo modelPromo) {
        String query = "UPDATE promo SET Keterangan='Berakhir' WHERE No_Promo='"+modelPromo.getNoPromo()+"' AND Keterangan='Berjalan'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
//    Atur Promo Otomatis Berjalan
    private void setAutoRunPromo(ModelPromo modelPromo) {
        String query = "UPDATE promo SET Keterangan='Berjalan' WHERE No_Promo='"+modelPromo.getNoPromo()+"' AND Keterangan='Akan Datang'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void autoChangeKeteranganPromo() {
        String query = "SELECT No_Promo, Tanggal_Awal, Tanggal_Akhir FROM promo";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateNow = LocalDate.now();
        int yearNow = dateNow.getYear();
        int monthNow = dateNow.getMonthValue();
        int dayNow = dateNow.getDayOfMonth();
        String strDateNow = dateNow.format(formatter);
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String strFirstDate = rst.getString("Tanggal_Awal");  
                LocalDate firstDate = LocalDate.parse(strFirstDate, formatter);
                int yearFisrt = firstDate.getYear();
                int monthFirst = firstDate.getMonthValue();
                int dayFirst = firstDate.getDayOfMonth();
                
                String strLastDate = rst.getString("Tanggal_Akhir");
                LocalDate lastDate = LocalDate.parse(strLastDate, formatter);
                int yearLast = lastDate.getYear();
                int monthLast = lastDate.getMonthValue();
                int dayLast = lastDate.getDayOfMonth();
                LocalDate plusOneDay = lastDate.plusDays(1);
                String strPlusOneDay = plusOneDay.format(formatter);
                
                if(strFirstDate.equals(strDateNow) || yearNow == yearFisrt && monthNow == monthFirst && dayNow >= dayFirst ) {
                    String noPromo = rst.getString("No_Promo");
                    ModelPromo modelPromo = new ModelPromo();
                    modelPromo.setNoPromo(noPromo);
                    setAutoRunPromo(modelPromo);
                }
                
                if(strDateNow.equals(strPlusOneDay)) {
                    String noPromo = rst.getString("No_Promo");
                    ModelPromo modelPromo = new ModelPromo();
                    modelPromo.setNoPromo(noPromo);
                    setAutoEndPromo(modelPromo);
                } else if(yearNow > yearLast || monthNow >= monthLast && dayNow > dayLast || monthNow > monthLast) {
                    String noPromo = rst.getString("No_Promo");
                    ModelPromo modelPromo = new ModelPromo();
                    modelPromo.setNoPromo(noPromo);
                    setAutoEndPromo(modelPromo);
                }   
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String getKeterangan(ModelPromo modelPromo) {
        String keterangan = "Akan Datang";
        String query = "SELECT Keterangan FROM promo WHERE Keterangan='"+modelPromo.getKeterangan()+"'";
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
