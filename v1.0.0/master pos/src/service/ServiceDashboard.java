/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import component.Chart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import util.ModelChart;
import model.ModelDashboard;
import model.ModelLastReservasi;
import swing.StatusType;
import swing.Table;
/**
 *
 * @author usER
 */
public class ServiceDashboard {
    private Connection connection;
    
    public ServiceDashboard() {
        connection = Koneksi.getConnection();
    }
    
    public double pendapatanPemeriksaan(ModelDashboard modelDashboard) {
        double revenue = 0;
        String query= "SELECT SUM(total) AS Total FROM pemeriksaan WHERE Tanggal_Pemeriksaan BETWEEN '"+modelDashboard.getFromDate()+"' AND '"+modelDashboard.getToDate()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                revenue = rst.getInt("Total");
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return revenue;
                        
    }
    
    public double totalPenjualan(ModelDashboard modelDashboard) {
        double total = 0;
        String query= "SELECT SUM(Total_Penjualan) AS Total FROM penjualan WHERE Tanggal BETWEEN '"+modelDashboard.getFromDate()+"' AND '"+modelDashboard.getToDate()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                total = rst.getInt("Total");
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return total;
    }
    
    public double pengeluaran(ModelDashboard modelDashboard) {
        double pengeluaran = 0;
        String query = "SELECT SUM(Total_Pengeluaran) AS Total FROM pengeluaran WHERE Tanggal_Pengeluaran BETWEEN '"+modelDashboard.getFromDate()+"' AND '"+modelDashboard.getToDate()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                pengeluaran = rst.getDouble("Total");
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return pengeluaran;
    }
    
    public double keuntunganPenjualan(ModelDashboard modelDashboard) {
        double keuntungan = 0;
        String query = "SELECT SUM(Total_Keuntungan) AS Total FROM penjualan WHERE Tanggal BETWEEN '"+modelDashboard.getFromDate()+"' AND '"+modelDashboard.getToDate()+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                keuntungan = rst.getDouble("Total");
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            
        }
        return keuntungan;
    }
    
    public void lastReseravsi(Table table) {
        String query = "SELECT rsv.No_Reservasi, rsv.ID_Pasien, psn.Nama, psn.Jenis_Kelamin, rsv.Tanggal_Kedatangan, "
                + "rsv.Jam_Kedatangan, rsv.Status_Reservasi FROM reservasi rsv INNER JOIN pasien psn "
                + "ON rsv.ID_Pasien=psn.ID_Pasien ORDER BY CONCAT(Tanggal_Kedatangan, ' ', Jam_Kedatangan) DESC";
        
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String noReservasi = rst.getString("No_Reservasi");
                String nama = rst.getString("Nama");
                String jenisKelamin = rst.getString("Jenis_Kelamin");
                String tglKedatangan = rst.getString("Tanggal_Kedatangan");
                String jamKedatangan = rst.getString("Jam_Kedatangan");
                String status = rst.getString("Status_Reservasi");
                String sourceImage = "/image/businesswoman.png";
                
                if(jenisKelamin.equals("Laki-laki")) {
                    sourceImage = "/image/office-worker.png";
                }
                
                StatusType type;
                if(status.equals("Menunggu")) {
                    type = StatusType.Wait;
                } else if(status.equals("Selesai")) {
                    type = StatusType.Finish;
                } else {
                    type = StatusType.Cancel;
                }
                
                table.addRow(new ModelLastReservasi(noReservasi,new ImageIcon(getClass().getResource(sourceImage)), nama, tglKedatangan.concat(" / " + jamKedatangan),type).toRowTable());
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private List<Double> getRevenues(List<Integer> months, String year) {
        List<Double> revenues = new ArrayList<>();
        for(int month : months) {
        String query = "SELECT SUM(Total) AS Revenues FROM pemeriksaan WHERE MONTH(Tanggal_Pemeriksaan)='"+month+"' AND YEAR(Tanggal_Pemeriksaan)='"+year+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                revenues.add(rst.getDouble("Revenues"));
            } else {
                revenues.add((double)0);
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
      }
      
        return revenues;
    }
    
    private List<Double> getTotalSells(List<Integer> months, String year) {
        List<Double> totalSells = new ArrayList<>();
        for(int month : months) {
        String query = "SELECT SUM(Total_Penjualan) AS TotalSells FROM penjualan WHERE MONTH(Tanggal)='"+month+"' AND YEAR(Tanggal)='"+year+"'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                totalSells.add(rst.getDouble("TotalSells")); 
            } else {
                totalSells.add((double)0);
            }
            rst.close();
            pst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
      }
      
        return totalSells;
    }
    
    private List<Double> getExpenditures(List<Integer> months, String year) {
        List<Double> expenditures = new ArrayList<>();
        for(int month : months) {
            String query = "SELECT SUM(Total_Pengeluaran) AS Expenditures FROM pengeluaran WHERE MONTH(Tanggal_Pengeluaran)='"+month+"' AND YEAR(Tanggal_Pengeluaran)='"+year+"'";
            try {
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rst = pst.executeQuery();
                if(rst.next()) {
                    expenditures.add(rst.getDouble("Expenditures"));
                } else {
                    expenditures.add((double)0);
                }
                rst.close();
                pst.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return expenditures;
    }
        
    private List<Double> getProfits(List<Integer> months, String year) {
        List<Double> profits = new ArrayList<>();
        for(int month : months) {
            String query = "SELECT YEAR(pjn.Tanggal) AS Tahun, MONTH(pjn.Tanggal) AS Bulan, \n" +
            "SUM(dtl.Subtotal) - SUM(brg.Harga_Beli * dtl.Jumlah) AS Keuntungan \n" +
            "FROM detail_penjualan dtl INNER JOIN penjualan pjn \n" +
            "ON dtl.No_Penjualan = pjn.No_Penjualan INNER JOIN barang brg \n" +
            "ON dtl.Kode_Barang = brg.Kode_Barang WHERE MONTH(pjn.Tanggal)='"+month+"' \n" +
            "AND YEAR(pjn.Tanggal) = '"+year+"' GROUP BY YEAR (pjn.Tanggal), MONTH(pjn.Tanggal)";
            try {
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rst = pst.executeQuery();
                boolean isNext = rst.next();
                if(isNext) {
                    profits.add(rst.getDouble("Keuntungan"));
                } else {
                    profits.add((double)0);
                }
                rst.close();
                pst.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return profits;
    }
    
    
    
    public void chartDiagram(Chart chart, String year, int index) {
        List<Integer> months = new ArrayList<>();
        
        for(int a = 1; a <= 12; a++) {
            months.add(a);
        }
        
        List<Double> revenues = getRevenues(months, year);
        List<Double> totalSells = getTotalSells(months, year);
        List<Double> expenditures = getExpenditures(months, year);
        List<Double> profits = getProfits(months, year);
        for(int a = 0; a < 12; a++) {
            Month month = Month.of(a+1);
            String bulan = styleString(month.toString());
            double[] values1 = {revenues.get(a), totalSells.get(a), expenditures.get(a)};
            double[] values2 = {profits.get(a)};
            if(index == 0) {
                chart.addData(new ModelChart(bulan, values1));
            } else {
                chart.addData(new ModelChart(bulan, values2));
            }
        }
    }
    
    private String styleString(String str) {
        String firstStr = str.substring(0, 1);
        String otherStr =  str.substring(1);
        return firstStr + otherStr.toLowerCase();
    }
}
