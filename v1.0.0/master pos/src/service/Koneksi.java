/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author usER
 */
public class Koneksi {
    
    private static Connection connection;
    
    public static Connection getConnection() {
    String database = "master_pos";
    String username = "root";
    String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/".concat(database), username, password);
        } catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
        return connection;
    }
    
    
    /**
    public static Connection getConnection() {
        String jdbcUrl = "jdbc:mariadb://localhost:3306/master_pos";
        String username = "root";
        String password = "12345678";
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return connection;
    }
    
    
        public void createDatabase() {
        String jdbcURL = "jdbc:mariadb://localhost:3306/";
        String username = "root";
        String password = "12345678";
        try {
            File file = new File(new File("src/others").getAbsoluteFile(), "master_pos.sql");
            String sqlScript = new String(Files.readAllBytes(file.toPath()));
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            
            Statement stmt = connection.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT SCHEMA_NAME FROM information_schema.schemata WHERE SCHEMA_NAME = 'master_pos'");
            if(rst.next()) {
                return;
            }

            String[] queries = sqlScript.split(";");


            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    statement.execute(query.trim() + ";");
                }
            }
            
            createTrigger();
            
            statement.close();
            connection.close();
        } catch(Exception exception) {
            throw new IllegalArgumentException(exception);
        }
    }
    
    private void createTrigger() {
        String jdbcURL = "jdbc:mariadb://localhost:3306/master_pos";
        String username = "root";
        String password = "12345678";
        
        String createTriggerSQLpemeriksaan = "CREATE TRIGGER update_status_reservasi_after_insert_pemeriksaan "
                + "AFTER INSERT ON pemeriksaan "
                + "FOR EACH ROW "
                + "BEGIN "
                + "     UPDATE reservasi "
                + "     SET Status_Reservasi = NEW.Status_Pemeriksaan "
                + "     WHERE No_Reservasi = NEW.No_Reservasi; "
                + "END;";
        
        String createTriggerSQLDetailPenjualan = "CREATE TRIGGER update_stok_after_insert_detail_penjualan "
                                + "AFTER INSERT ON detail_penjualan "
                                + "FOR EACH ROW "
                                + "BEGIN "
                                + "    UPDATE barang "
                                + "    SET Stok = Stok - NEW.Jumlah "
                                + "    WHERE Kode_Barang = NEW.Kode_Barang; "
                                + "END;";
        
        String createTriggerSQLDetailRestok = "CREATE TRIGGER update_stok_after_insert_detail_restok "
                + "AFTER INSERT ON detail_restok "
                + "FOR EACH ROW "
                + "BEGIN "
                + "     UPDATE barang "
                + "     SET STOK = Stok + New.Jumlah "
                + "     WHERE Kode_Barang = NEW.Kode_Barang; "
                + "END;";
        
        String createTriggerSQLRestok = "CREATE TRIGGER update_status_pemesanan_after_insert_restok "
                + "AFTER INSERT ON restok "
                + "FOR EACH ROW "
                + "BEGIN "
                + "     UPDATE pemesanan "
                + "     SET Status_Pemesanan = NEW.Status_Restok "
                + "     WHERE No_Pemesanan = NEW.No_Restok; "
                + "END;";
        
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            
            Statement statement = connection.createStatement();
            statement.execute(createTriggerSQLpemeriksaan);
            statement.execute(createTriggerSQLDetailPenjualan);
            statement.execute(createTriggerSQLDetailRestok);
            statement.execute(createTriggerSQLRestok);
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}
