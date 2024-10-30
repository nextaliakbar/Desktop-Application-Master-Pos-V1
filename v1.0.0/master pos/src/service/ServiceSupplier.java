
package service;
import model.ModelSupplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class ServiceSupplier {
    private Connection connection;

    public ServiceSupplier() {
        connection = Koneksi.getConnection();
    }
    public void loadData(DefaultTableModel tabmodel) {
        String query = "SELECT * FROM supplier";
        
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String IdSupplier = rst.getString("ID_Supplier");
                String NamaSupplier= rst.getString("Nama");
                String TeleponSupplier = rst.getString("No_Telp");
                String EmailSupplier = rst.getString("Email");
                String AlamatSupplier = rst.getString("Alamat");
               
                tabmodel.addRow(new Object[]{IdSupplier, NamaSupplier, TeleponSupplier, EmailSupplier, AlamatSupplier});
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }    
    }
                
                
    public void addData(JFrame parent, ModelSupplier modelSupplier) {
        String query = "INSERT INTO supplier (ID_Supplier, Nama, No_Telp, Email, Alamat) VALUES (?,?,?,?,?)";
        try {
           PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelSupplier.getIdSupplier());
           pst.setString(2, modelSupplier.getNamaSupplier());
           pst.setString(3, modelSupplier.getTeleponSupplier());
           pst.setString(4, modelSupplier.getEmailSupplier());
           pst.setString(5, modelSupplier.getAlamatSupplier());
           pst.executeUpdate();
           pst.close();
           JOptionPane.showMessageDialog(parent, "Data Supplier Berhasil Ditambahkan");
           
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateData(JFrame parent, ModelSupplier modelSupplier){
     String query = "UPDATE supplier SET Nama=?, No_Telp=?, Email=?, Alamat=? WHERE ID_Supplier=?";
     try {
          PreparedStatement pst = connection.prepareStatement(query);
           pst.setString(1, modelSupplier.getNamaSupplier());
           pst.setString(2, modelSupplier.getTeleponSupplier());
           pst.setString(3, modelSupplier.getEmailSupplier());
           pst.setString(4, modelSupplier.getAlamatSupplier());
           pst.setString(5, modelSupplier.getIdSupplier());
           pst.executeUpdate();
           pst.close();
           JOptionPane.showMessageDialog(parent, "Data Supplier Berhasil Diperbarui");
           
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }
    
    
    public void deleteData(JFrame parent, ModelSupplier modelSupplier){
    String query = "DELETE FROM supplier WHERE ID_Supplier=?";
    try{
        PreparedStatement pst = connection.prepareCall(query);
        pst.setString(1, modelSupplier.getIdSupplier());
        pst.executeUpdate();
        pst.close();
         JOptionPane.showMessageDialog(parent, "Data Supplier Berhasil Di Hapus");
    } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String createId() {
        String idSupplier = null;
        String query = "SELECT RIGHT(ID_Supplier, 3) AS ID FROM supplier ORDER BY ID_Supplier DESC";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("ID"));
                number++;
                idSupplier = "SLR-" + String.format("%03d", number);
            } else {
                idSupplier = "SLR-001";
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return idSupplier;
    }
    
    public boolean validationDelete(JFrame parent, ModelSupplier modelSupplier) {
        boolean valid = false;
        String query = "SELECT ID_Supplier FROM pemesanan WHERE ID_Supplier='"+modelSupplier.getIdSupplier()+"' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                JOptionPane.showMessageDialog(parent, "Tidak dapat menghapus supplier ini\n"
               + "Toko pernah melakukan transaksi\n"
               + "Pemesanan dengan supplier ini", "Peringatan", JOptionPane.WARNING_MESSAGE);
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


