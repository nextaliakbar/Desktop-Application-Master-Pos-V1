/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author usER
 */
public class Report {
    
    private static Report instance;
    private JasperReport report1;
    private JasperReport report2;
    
    public static Report getInstance() {
        if(instance == null) {
            instance = new Report();
        }
        
        return instance;
    }
    
    private Report() {
        
    }
    
    public void compileReport(String slide) throws JRException{
        switch (slide) {
            case "Pemeriksaan":
                  report1 = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/ReportPemeriksaan.jrxml"));
                break;
            case "Penjualan":
                  report1 = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/ReportPenjualan.jrxml"));       
                break;
            default:
                switch(slide) {
                case "Kartu Karyawan": 
                    report1 = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/KartuKaryawan.jrxml"));
                    break;
                case "Kartu Membership": 
                    report1 = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/KartuMember.jrxml"));
                    break;
            }
        }
        switch (slide) {
            case "Pemeriksaan":
                report2 = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/LaporanPemeriksaan.jrxml"));
                break;
            case "Penjualan":
                report2 = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/LaporanPenjualan.jrxml"));
                break;
            case "Pemesanan":
                report2 = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/LaporanPemesanan.jrxml"));
                break;
            default:
                report2 = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/LaporanPengeluaran.jrxml"));
        }
    }
    
    public void printReportPemeriksaan(ParamPemeriksaan data) throws JRException {
        Map parameter = new HashMap();
        parameter.put("noPmrksn", data.getNoPemeriksaan());
        parameter.put("tanggal", data.getTglPemeriksaan());
        parameter.put("jam", data.getJamPemeriksaan());
        parameter.put("pasien", data.getPasien());
        parameter.put("staff", data.getKaryawan());
        parameter.put("admin", data.getAdmin());
        parameter.put("total", data.getTotal());
        parameter.put("potongan", data.getPotongan());
        parameter.put("bayar", data.getBayar());
        parameter.put("kembalian", data.getKembalian());
        parameter.put("jenis", data.getJenis());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
        JasperPrint print = JasperFillManager.fillReport(report1, parameter,  dataSource);
        viewReport(print);
    }
    
    public void printReportPenjualan(ParamPenjualan data) throws JRException{
        Map parameter = new HashMap();
        parameter.put("tgljam", data.getTglJam());
        parameter.put("noPenjualan", data.getNoPenjualan());
        parameter.put("admin", data.getAdmin());
        parameter.put("total", data.getTotal());
        parameter.put("bayar", data.getBayar());
        parameter.put("kembalian", data.getKembalian());
        parameter.put("jenis", data.getJenis());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
        JasperPrint print = JasperFillManager.fillReport(report1, parameter, dataSource);
        viewReport(print);
    }
    
    public void printCard(ParamCard data) throws JRException{
        Map paramater = new HashMap();
        paramater.put("qrcode", data.getQrcode());
        paramater.put("image1", data.getImage1());
        paramater.put("image2", data.getImage2());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFields());
        JasperPrint print = JasperFillManager.fillReport(report1, paramater, dataSource);
        viewReport(print);
    }
    
    public void printLaporanPemeriksaan(ParamLaporan data) throws JRException{
        Map paramater = new HashMap();
        paramater.put("rentang", data.getRentang());
        paramater.put("total", data.getTotalKeseluruhan());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFieldsPemeriksaan());
        JasperPrint print = JasperFillManager.fillReport(report2, paramater, dataSource);
        viewReport(print);
    }
    
    public void printLaporanPenjualan(ParamLaporan data) throws JRException{
        Map paramater = new HashMap();
        paramater.put("rentang", data.getRentang());
        paramater.put("totalQty", data.getTotalQty());
        paramater.put("total", data.getTotalKeseluruhan());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFieldsPenjualan());
        JasperPrint print = JasperFillManager.fillReport(report2, paramater, dataSource);
        viewReport(print);
    }
    
    public void printLaporanPemesanan(ParamLaporan data) throws JRException {
        Map paramater = new HashMap();
        paramater.put("rentang", data.getRentang());
        paramater.put("totalQty", data.getTotalQty());
        paramater.put("total", data.getTotalKeseluruhan());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFieldsPemesanan());
        JasperPrint print = JasperFillManager.fillReport(report2, paramater, dataSource);
        viewReport(print);
    }
    
    public void printLaporanPengeluaran(ParamLaporan data) throws JRException {
        Map paramater = new HashMap();
        paramater.put("rentang", data.getRentang());
        paramater.put("totalQty", data.getTotalQty());
        paramater.put("total", data.getTotalKeseluruhan());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data.getFieldsPengeluaran());
        JasperPrint print = JasperFillManager.fillReport(report2, paramater, dataSource);
        viewReport(print);
    }
    
    private void viewReport(JasperPrint print) throws JRException {
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setTitle("Print Preview");
        viewer.setIconImage(null);
        viewer.setVisible(true);
    }
}
