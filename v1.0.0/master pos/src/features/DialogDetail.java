/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package features;

import util.ModelHeaderTable;
import util.ModelRenderTable;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.*;
import service.*;

/**
 *
 * @author usER
 */
public class DialogDetail extends java.awt.Dialog {

    /**
     * Creates new form DialogDetail
     */
    private ServiceDetailPemeriksaan serviceDetailPemeriksaan = new ServiceDetailPemeriksaan();
    private ServiceDetailPenjualan serviceDetailPenjualan = new ServiceDetailPenjualan();
    private ServiceDetailPemesanan serviceDetailPemesanan = new ServiceDetailPemesanan();
    private ServiceRiwayatPasien serviceRiwayat = new ServiceRiwayatPasien();
    private ServiceReservasi serviceReservasi = new ServiceReservasi();
    private ServicePengeluaran servicePengeluaran = new ServicePengeluaran();
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    private DefaultTableModel tabModel1;
    private DefaultTableModel tabModel2;
    private DefaultTableModel tabModel3;
    private DefaultTableModel tabmodel4;
    private DefaultTableModel tabmodel5;
    private ModelDetailPemeriksaan detailPemeriksaan;
    private ModelDetailPenjualan detailPenjualan;
    private ModelDetailPemesanan detailPemesanan;
    private ModelDetailPengeluaran detailPengeluaran;
    private JFrame parent;
    public DialogDetail(java.awt.Frame parent, boolean modal, String slide, 
    ModelDetailPemeriksaan detailPemeriksaan, ModelDetailPenjualan detailPenjualan, 
    ModelDetailPemesanan detailPemesanan, ModelDetailPengeluaran detailPengeluaran) {
        super(parent, modal);
        initComponents();
        this.parent = (JFrame) parent;
        setIconImage(new ImageIcon(getClass().getResource("/image/Master Pos Logo 3.png")).getImage());
        this.detailPemeriksaan = detailPemeriksaan;
        this.detailPenjualan = detailPenjualan;
        this.detailPemesanan = detailPemesanan;
        this.detailPengeluaran = detailPengeluaran;
        
        styleTable(scroll1, tablePemeriksaan, 5);
        styleTable(scroll2, tableRiwayat, 3);
        styleTable(scroll3, tableDetailPengeluaran, 4);
        styleTable(scroll4, tableDetailPemesanan, 6);
        styleTable(scroll5, tableDetailPenjualan, 6);
        tabModel1 = (DefaultTableModel) tablePemeriksaan.getModel();
        tabModel2 = (DefaultTableModel) tableRiwayat.getModel();
        tabModel3 = (DefaultTableModel) tableDetailPengeluaran.getModel();
        tabmodel4 = (DefaultTableModel) tableDetailPemesanan.getModel();
        tabmodel5 = (DefaultTableModel) tableDetailPenjualan.getModel();

        changePanel(slide);
        styleTextArea(txtDesc, false);
        styleTextArea(txtDescPengeluaran, false);
    }
    
    private void styleTextArea(JTextArea txtArea, boolean enable) {
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        txtArea.setEnabled(enable);
    }
    
    //  Tampil Data Detail Pemeriksaan
    private void tampilDataPemeriksaan() {
        
        ModelDetailPemeriksaan detail = new ModelDetailPemeriksaan();
        ModelPemeriksaan pemeriksaan = new ModelPemeriksaan();
        String noPemeriksaan = detailPemeriksaan.getModelPemeriksaan().getNoPemeriksaan();
        String noReservasi = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getNoReservasi();
        String idPasien = detailPemeriksaan.getModelPemeriksaan().getModelPasien().getIdPasien();
        String nama = detailPemeriksaan.getModelPemeriksaan().getModelPasien().getNama();
        String idKaryawan = detailPemeriksaan.getModelPemeriksaan().getModelKaryawan().getIdKaryawan();
        String tgl = detailPemeriksaan.getModelPemeriksaan().getTglPemeriksaan();
        String total = detailPemeriksaan.getModelPemeriksaan().getTotal();
        double bayar = detailPemeriksaan.getModelPemeriksaan().getBayar();
        double kembalian = detailPemeriksaan.getModelPemeriksaan().getKembali();
        String jenisPembayaran = detailPemeriksaan.getModelPemeriksaan().getJenisPembayaran();
        String deskripsi = detailPemeriksaan.getModelPemeriksaan().getDeskripsi();
        String idPengguna = detailPemeriksaan.getModelPemeriksaan().getModelPengguna().getIdpengguna();
        String namaPengguna = detailPemeriksaan.getModelPemeriksaan().getModelPengguna().getNama();
        pemeriksaan.setNoPemeriksaan(noPemeriksaan);
        detail.setModelPemeriksaan(pemeriksaan);
        
        lbNoPemeriksaan.setText(noPemeriksaan);
        lbNoReservasi.setText(noReservasi);
        lbTgl.setText(tgl);
        lbIdPasien.setText(idPasien);
        lbNama.setText(nama);
        lbIdKaryawan.setText(idKaryawan);
        lbTotal.setText("Rp " + total);
        txtDesc.setText(deskripsi);
        lbBayar.setText("Rp " + df.format(bayar) + " / " + jenisPembayaran);
        lbKembalian.setText("Rp " + df.format(kembalian));
        lbKasir.setText(idPengguna + " / " + namaPengguna);
        serviceDetailPemeriksaan.loadData(tabModel1, detail);
    }
    
    //    Tampil Data Detail Penjualan
    private void tampilDataPenjualan() {
        ModelDetailPenjualan modelDetailPenjualan = new ModelDetailPenjualan();
        ModelPenjualan modelPenjualan = new ModelPenjualan();
        String noPenjualan = detailPenjualan.getModelPenjualan().getNoPenjualan();
        String tglPenjualan = detailPenjualan.getModelPenjualan().getTglPenjualan();
        String idPengguna = detailPenjualan.getModelPenjualan().getModelPengguna().getIdpengguna();
        String namaPengguna = detailPenjualan.getModelPenjualan().getModelPengguna().getNama();
        String total = detailPenjualan.getModelPenjualan().getTotalPenjualan();
        double bayar = detailPenjualan.getModelPenjualan().getBayar();
        double kembali = detailPenjualan.getModelPenjualan().getKembali();
        String jenisPembayaran = detailPenjualan.getModelPenjualan().getJenisPembayaran();
        
        lbNoPenjualan.setText(noPenjualan);
        lbTglPenjualan.setText(tglPenjualan);
        lbKasir2.setText(idPengguna+" / "+namaPengguna);
        lbTotalPenjualan.setText("Rp "+total);
        lbBayarPenjualan.setText("Rp "+df.format(bayar)+" / "+jenisPembayaran);
        lbKembaliPenjualan.setText("Rp "+df.format(kembali));
        
        modelPenjualan.setNoPenjualan(noPenjualan);
        modelDetailPenjualan.setModelPenjualan(modelPenjualan);
        serviceDetailPenjualan.loadData(modelDetailPenjualan, tabmodel5);
    }
    
    //    Tampil Data Detail Pemesanan
    private void tampilDataPemesanan() {
        ModelDetailPemesanan modelDetailPemesanan = new ModelDetailPemesanan();
        ModelPemesanan modelPemesanan = new ModelPemesanan();
        String noPemesanan = detailPemesanan.getModelPemesanan().getNoPemesanan();
        String idSupplier = detailPemesanan.getModelPemesanan().getModelSupplier().getIdSupplier();
        String namaSupplier = detailPemesanan.getModelPemesanan().getModelSupplier().getNamaSupplier();
        String tanggal = detailPemesanan.getModelPemesanan().getTglPemesanan();
        String status = detailPemesanan.getModelPemesanan().getStatusPemesanan();
        String total = detailPemesanan.getModelPemesanan().getTotalPemesanan();
        double bayar = detailPemesanan.getModelPemesanan().getBayar();
        double kembali = detailPemesanan.getModelPemesanan().getKembali();
        String jenisPembayaran = detailPemesanan.getModelPemesanan().getJenisPembayaran();
        String idPengguna = detailPemesanan.getModelPemesanan().getModelPengguna().getIdpengguna();
        String namaPengguna = detailPemesanan.getModelPemesanan().getModelPengguna().getNama();

        lbNoPemesanan.setText(noPemesanan);
        lbTglPemesanan.setText(tanggal);
        lbStatusPemesanan.setText(status);
        lbIdSuplr.setText(idSupplier);
        lbNamaSuplr.setText(namaSupplier);
        lbPemesan.setText(idPengguna+" / "+namaPengguna);
        lbTotalPemesanan.setText("Rp " + total);
        lbBayarPemesanan.setText("Rp " + df.format(bayar) + " / " + jenisPembayaran);
        lbKembaliPemesanan.setText("Rp " + df.format(kembali));

        modelPemesanan.setNoPemesanan(noPemesanan);
        modelDetailPemesanan.setModelPemesanan(modelPemesanan);
        serviceDetailPemesanan.loadData(modelDetailPemesanan, tabmodel4);
    }
    
    //    Tampil data riwayat pasien
    private void tampilDataRiwayat() {
        ModelPemeriksaan pemeriksaan = new ModelPemeriksaan();
        ModelPasien pasien = new ModelPasien();
        String idPasien = detailPemeriksaan.getModelPemeriksaan().getModelPasien().getIdPasien();
        String namaPasien = detailPemeriksaan.getModelPemeriksaan().getModelPasien().getNama();
        String tgl = detailPemeriksaan.getModelPemeriksaan().getTglPemeriksaan();
        String noPemeriksaan = detailPemeriksaan.getModelPemeriksaan().getNoPemeriksaan();
        
        lbIdPasien2.setText(idPasien);
        lbNamaPasien.setText(namaPasien);
        lbTerakhirPemeriksaan.setText(tgl);
        lbNoPemeriksaan2.setText(noPemeriksaan);
        
        pasien.setIdPasien(idPasien);
        pemeriksaan.setModelPasien(pasien);
        serviceRiwayat.loadDataDetail(pemeriksaan, tabModel2);
    }
    
//    Tampil data pengeluaran
    private void tampilDataPengeluaran() {
        String noPengeluaran = detailPengeluaran.getModelPengeluaran().getNoPengeluaran();
        String idPengguna = detailPengeluaran.getModelPengeluaran().getModelPengguna().getIdpengguna();
        String namaPengguna = detailPengeluaran.getModelPengeluaran().getModelPengguna().getNama();
        String user = idPengguna.concat("/" + namaPengguna);
        String tanggal = detailPengeluaran.getModelPengeluaran().getTglPengeluaran();
        String desc = detailPengeluaran.getModelPengeluaran().getDeskripsi();
        String total = detailPengeluaran.getModelPengeluaran().getTotal();
        
        lbNoPengeluaran.setText(noPengeluaran);
        lbPengguna.setText(user);
        lbTglPengeluaran.setText(tanggal);
        lbTotalPengeluaran.setText("Rp " + total);
        txtDescPengeluaran.setText(desc);
        ModelPengeluaran modelPengeluaran = new ModelPengeluaran();
        modelPengeluaran.setNoPengeluaran(noPengeluaran);
        ModelDetailPengeluaran modelDetail = new ModelDetailPengeluaran();
        modelDetail.setModelPengeluaran(modelPengeluaran);
        servicePengeluaran.loadDataDetail(modelDetail, tabModel3);
    }
    
//    Tampil data reservasi
    private void tampilDataReservasi() {
        String noReservasi = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getNoReservasi();
        String tglKedatangan = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getTglKedatangan();
        String jamKedatangan = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getJamKedatangan();
        String idPengguna = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getModelPengguna().getIdpengguna();
        String namaPengguna = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getModelPengguna().getNama();
        String status = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getStatusReservasi();
        String idPasien = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getModelPasien().getIdPasien();
        String namaPasien = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getModelPasien().getNama();
        String jenisKelamin = detailPemeriksaan.getModelPemeriksaan().getModelReservasi().getModelPasien().getJenisKelamin();
        
        lbNoReservasi2.setText(noReservasi);
        lbTglKedatangan.setText(tglKedatangan);
        lbJamKedatangan.setText(jamKedatangan);
        lbIdPengguna.setText(idPengguna);
        lbNamaPengguna.setText(namaPengguna);
        lbStatus.setText(status);
        lbIdPasien3.setText(idPasien);
        lbNamaPasien3.setText(namaPasien);
        lbJenisKelamin.setText(jenisKelamin);
    }
    
    private void perbaruiStatus() {
        String status = lbStatus.getText();
        if(status.equals("Selesai")) {
            JOptionPane.showMessageDialog(parent, "Reservasi ini sudah selesai");
        } else if(status.equals("Batal")){
            JOptionPane.showMessageDialog(parent, "Reservasi ini sudah dibatalkan");
        } else {
            int confirm = JOptionPane.showConfirmDialog(parent, "Yakin ingin membatalkan reservasi?", "Konfirmasi", JOptionPane.OK_OPTION);
            if(confirm == 0) {
                ModelReservasi modelReservasi = new ModelReservasi();
                String noReservasi = lbNoReservasi2.getText();
                modelReservasi.setNoReservasi(noReservasi);
                serviceReservasi.updateStatus(parent, modelReservasi);
                dispose();
            }
        }
    }
    
//  Style Table
    private void styleTable(JScrollPane scroll, JTable table, int columnTable) {
        scroll.getViewport().setBackground(new Color(255,255,255));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255,255,255));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.setBorder(new EmptyBorder(5,10,5,10));
        table.setRowHeight(40);        
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        table.setDefaultRenderer(Object.class, new ModelRenderTable(columnTable));
    }
    
    private void changePanel(String slide) {
        if(slide.equals("Slide-1")) {
            showPanel(panel1);
            tampilDataPemeriksaan();
        } else if(slide.equals("Slide-2")) {
            showPanel(panel2);
            tampilDataRiwayat();
        } else if(slide.equals("Slide-3")) {
            showPanel(panel3);
            tampilDataPengeluaran();
        } else if(slide.equals("Slide-4")) {
            showPanel(panel4);
            tampilDataReservasi();
        } else if(slide.equals("Slide-5")) {
            showPanel(panel5);
            tampilDataPemesanan();
        } else if(slide.equals("Slide-6")) {
            showPanel(panel6);
            tampilDataPenjualan();
        }
    }
    
    private void showPanel(JPanel pn) {
        panel.removeAll();
        panel.add(pn);
        panel.repaint();
        panel.revalidate();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    @Override
    public void paintComponents(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(50, 50, 50));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponents(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        lb1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbNoReservasi = new javax.swing.JLabel();
        lb2 = new javax.swing.JLabel();
        lbNoPemeriksaan = new javax.swing.JLabel();
        lb3 = new javax.swing.JLabel();
        lbTgl = new javax.swing.JLabel();
        lb4 = new javax.swing.JLabel();
        lb5 = new javax.swing.JLabel();
        lb6 = new javax.swing.JLabel();
        lbIdPasien = new javax.swing.JLabel();
        lbNama = new javax.swing.JLabel();
        lbIdKaryawan = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextArea();
        scroll1 = new javax.swing.JScrollPane();
        tablePemeriksaan = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        lbBayar = new javax.swing.JLabel();
        lb7 = new javax.swing.JLabel();
        lb16 = new javax.swing.JLabel();
        lbKembalian = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        lb17 = new javax.swing.JLabel();
        lbKasir = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        lb27 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbNamaPasien = new javax.swing.JLabel();
        lb8 = new javax.swing.JLabel();
        lbIdPasien2 = new javax.swing.JLabel();
        lb10 = new javax.swing.JLabel();
        lb11 = new javax.swing.JLabel();
        lbTerakhirPemeriksaan = new javax.swing.JLabel();
        lbNoPemeriksaan2 = new javax.swing.JLabel();
        scroll2 = new javax.swing.JScrollPane();
        tableRiwayat = new javax.swing.JTable();
        lb13 = new javax.swing.JLabel();
        panel3 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbPengguna = new javax.swing.JLabel();
        lb9 = new javax.swing.JLabel();
        lbNoPengeluaran = new javax.swing.JLabel();
        lb12 = new javax.swing.JLabel();
        lb14 = new javax.swing.JLabel();
        lbTglPengeluaran = new javax.swing.JLabel();
        lbTotalPengeluaran = new javax.swing.JLabel();
        scroll3 = new javax.swing.JScrollPane();
        tableDetailPengeluaran = new javax.swing.JTable();
        lb15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescPengeluaran = new javax.swing.JTextArea();
        panel4 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbTglKedatangan = new javax.swing.JLabel();
        lb18 = new javax.swing.JLabel();
        lbNoReservasi2 = new javax.swing.JLabel();
        lb19 = new javax.swing.JLabel();
        lb20 = new javax.swing.JLabel();
        lbJamKedatangan = new javax.swing.JLabel();
        lbIdPengguna = new javax.swing.JLabel();
        lb21 = new javax.swing.JLabel();
        lbNamaPengguna = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        lb22 = new javax.swing.JLabel();
        lb23 = new javax.swing.JLabel();
        lbJenisKelamin = new javax.swing.JLabel();
        lbNamaPasien3 = new javax.swing.JLabel();
        lb24 = new javax.swing.JLabel();
        lb25 = new javax.swing.JLabel();
        lbIdPasien3 = new javax.swing.JLabel();
        lb26 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btnBatalReservasi = new swing.Button();
        panel5 = new javax.swing.JPanel();
        lb28 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbTglPemesanan = new javax.swing.JLabel();
        lb29 = new javax.swing.JLabel();
        lbNoPemesanan = new javax.swing.JLabel();
        lb30 = new javax.swing.JLabel();
        lbStatusPemesanan = new javax.swing.JLabel();
        lb31 = new javax.swing.JLabel();
        lb33 = new javax.swing.JLabel();
        lbIdSuplr = new javax.swing.JLabel();
        lbNamaSuplr = new javax.swing.JLabel();
        scroll4 = new javax.swing.JScrollPane();
        tableDetailPemesanan = new javax.swing.JTable();
        jSeparator5 = new javax.swing.JSeparator();
        lbBayarPemesanan = new javax.swing.JLabel();
        lb34 = new javax.swing.JLabel();
        lb35 = new javax.swing.JLabel();
        lbKembaliPemesanan = new javax.swing.JLabel();
        lbTotalPemesanan = new javax.swing.JLabel();
        lb36 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        lb37 = new javax.swing.JLabel();
        lbPemesan = new javax.swing.JLabel();
        panel6 = new javax.swing.JPanel();
        lb32 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbTglPenjualan = new javax.swing.JLabel();
        lbNoPenjualan = new javax.swing.JLabel();
        lb41 = new javax.swing.JLabel();
        scroll5 = new javax.swing.JScrollPane();
        tableDetailPenjualan = new javax.swing.JTable();
        jSeparator7 = new javax.swing.JSeparator();
        lbBayarPenjualan = new javax.swing.JLabel();
        lb42 = new javax.swing.JLabel();
        lb43 = new javax.swing.JLabel();
        lbKembaliPenjualan = new javax.swing.JLabel();
        lbTotalPenjualan = new javax.swing.JLabel();
        lb44 = new javax.swing.JLabel();
        lb45 = new javax.swing.JLabel();
        lbKasir2 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        panel.setLayout(new java.awt.CardLayout());

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        lb1.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb1.setForeground(new java.awt.Color(0, 0, 0));
        lb1.setText("No Pemeriksaan");

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        jLabel1.setBackground(new java.awt.Color(135, 15, 50));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DETAIL PEMERIKSAAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        lbNoReservasi.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoReservasi.setForeground(new java.awt.Color(0, 0, 0));
        lbNoReservasi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb2.setForeground(new java.awt.Color(0, 0, 0));
        lb2.setText("ID Pasien");

        lbNoPemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPemeriksaan.setForeground(new java.awt.Color(0, 0, 0));
        lbNoPemeriksaan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb3.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb3.setForeground(new java.awt.Color(0, 0, 0));
        lb3.setText("Nama Pasien");

        lbTgl.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTgl.setForeground(new java.awt.Color(0, 0, 0));
        lbTgl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb4.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb4.setForeground(new java.awt.Color(0, 0, 0));
        lb4.setText("Tanggal");

        lb5.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb5.setForeground(new java.awt.Color(0, 0, 0));
        lb5.setText("ID Terapis");

        lb6.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb6.setForeground(new java.awt.Color(0, 0, 0));
        lb6.setText("Total");

        lbIdPasien.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdPasien.setForeground(new java.awt.Color(0, 0, 0));
        lbIdPasien.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbNama.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNama.setForeground(new java.awt.Color(0, 0, 0));
        lbNama.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbIdKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdKaryawan.setForeground(new java.awt.Color(0, 0, 0));
        lbIdKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        txtDesc.setBackground(new java.awt.Color(255, 255, 255));
        txtDesc.setColumns(20);
        txtDesc.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        txtDesc.setForeground(new java.awt.Color(0, 0, 0));
        txtDesc.setRows(5);
        txtDesc.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        txtDesc.setCaretColor(new java.awt.Color(255, 255, 255));

        tablePemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        tablePemeriksaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Tindakan", "Nama Tindakan", "Harga", "Potongan", "Total Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll1.setViewportView(tablePemeriksaan);
        if (tablePemeriksaan.getColumnModel().getColumnCount() > 0) {
            tablePemeriksaan.getColumnModel().getColumn(1).setPreferredWidth(200);
            tablePemeriksaan.getColumnModel().getColumn(2).setPreferredWidth(210);
        }

        jSeparator1.setBackground(new java.awt.Color(185, 185, 185));

        lbBayar.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbBayar.setForeground(new java.awt.Color(0, 0, 0));
        lbBayar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbBayar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb7.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb7.setForeground(new java.awt.Color(0, 0, 0));
        lb7.setText("Bayar");

        lb16.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb16.setForeground(new java.awt.Color(0, 0, 0));
        lb16.setText("Kembali");

        lbKembalian.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKembalian.setForeground(new java.awt.Color(0, 0, 0));
        lbKembalian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbKembalian.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbTotal.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(0, 0, 0));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTotal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb17.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb17.setForeground(new java.awt.Color(0, 0, 0));
        lb17.setText("No Reservasi");

        lbKasir.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKasir.setForeground(new java.awt.Color(0, 0, 0));
        lbKasir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbKasir.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        jSeparator4.setBackground(new java.awt.Color(185, 185, 185));

        lb27.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb27.setForeground(new java.awt.Color(0, 0, 0));
        lb27.setText("Kasir");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(scroll1)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lb4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lb17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lb1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbNoPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbNoReservasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbTgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lb3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lb2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lb5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbIdKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lbIdPasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(lbNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(182, 182, 182))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(lb6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lb27))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(lb7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lb16))
                                    .addComponent(lbKasir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(80, 80, 80))
                    .addComponent(txtDesc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbNoPemeriksaan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbNoReservasi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lb17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lb4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(txtDesc))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(lb2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(lbIdPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(lbNama, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbIdKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb27, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel.add(panel1, "card2");

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(135, 15, 50));

        jLabel2.setBackground(new java.awt.Color(135, 15, 50));
        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("DETAIL RIWAYAT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
                .addGap(100, 100, 100))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        );

        lbNamaPasien.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaPasien.setForeground(new java.awt.Color(0, 0, 0));
        lbNamaPasien.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb8.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb8.setForeground(new java.awt.Color(0, 0, 0));
        lb8.setText("Nama");

        lbIdPasien2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdPasien2.setForeground(new java.awt.Color(0, 0, 0));
        lbIdPasien2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb10.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb10.setForeground(new java.awt.Color(0, 0, 0));
        lb10.setText("Terakhir Pemeriksaan");

        lb11.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb11.setForeground(new java.awt.Color(0, 0, 0));
        lb11.setText("No Pemeriksaan");

        lbTerakhirPemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTerakhirPemeriksaan.setForeground(new java.awt.Color(0, 0, 0));
        lbTerakhirPemeriksaan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbNoPemeriksaan2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPemeriksaan2.setForeground(new java.awt.Color(0, 0, 0));
        lbNoPemeriksaan2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        tableRiwayat.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        tableRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Pemeriksaan", "Tanggal Pemeriksaan", "Estimasi Pemeriksaan Kembali"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll2.setViewportView(tableRiwayat);
        if (tableRiwayat.getColumnModel().getColumnCount() > 0) {
            tableRiwayat.getColumnModel().getColumn(0).setMinWidth(0);
            tableRiwayat.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableRiwayat.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        lb13.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb13.setForeground(new java.awt.Color(0, 0, 0));
        lb13.setText("ID Pasien");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addComponent(lb11, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbNoPemeriksaan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lb8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbTerakhirPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbNamaPasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbIdPasien2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbIdPasien2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNamaPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTerakhirPemeriksaan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNoPemeriksaan2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel.add(panel2, "card2");

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(135, 15, 50));

        jLabel3.setBackground(new java.awt.Color(135, 15, 50));
        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DETAIL PENGELUARAN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        );

        lbPengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbPengguna.setForeground(new java.awt.Color(0, 0, 0));
        lbPengguna.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb9.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb9.setForeground(new java.awt.Color(0, 0, 0));
        lb9.setText("Pengguna");

        lbNoPengeluaran.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        lbNoPengeluaran.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb12.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb12.setForeground(new java.awt.Color(0, 0, 0));
        lb12.setText("Tanggal Pengeluaran");

        lb14.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb14.setForeground(new java.awt.Color(0, 0, 0));
        lb14.setText("Total Pengeluaran");

        lbTglPengeluaran.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTglPengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        lbTglPengeluaran.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbTotalPengeluaran.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTotalPengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        lbTotalPengeluaran.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        tableDetailPengeluaran.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        tableDetailPengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Jenis", "Jenis Pengeluaran", "Detail Jenis", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll3.setViewportView(tableDetailPengeluaran);
        if (tableDetailPengeluaran.getColumnModel().getColumnCount() > 0) {
            tableDetailPengeluaran.getColumnModel().getColumn(0).setMinWidth(0);
            tableDetailPengeluaran.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableDetailPengeluaran.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        lb15.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb15.setForeground(new java.awt.Color(0, 0, 0));
        lb15.setText("No Pengeluaran");

        jScrollPane2.setBorder(null);

        txtDescPengeluaran.setBackground(new java.awt.Color(255, 255, 255));
        txtDescPengeluaran.setColumns(20);
        txtDescPengeluaran.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        txtDescPengeluaran.setRows(5);
        txtDescPengeluaran.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        jScrollPane2.setViewportView(txtDescPengeluaran);

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scroll3, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTglPengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbPengguna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNoPengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTotalPengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNoPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTglPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTotalPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scroll3, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel.add(panel3, "card2");

        panel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(135, 15, 50));

        jLabel4.setBackground(new java.awt.Color(135, 15, 50));
        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("DETAIL RESERVASI");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        );

        lbTglKedatangan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTglKedatangan.setForeground(new java.awt.Color(0, 0, 0));
        lbTglKedatangan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb18.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb18.setForeground(new java.awt.Color(0, 0, 0));
        lb18.setText("Tanggal Kedatangan");

        lbNoReservasi2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoReservasi2.setForeground(new java.awt.Color(0, 0, 0));
        lbNoReservasi2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb19.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb19.setForeground(new java.awt.Color(0, 0, 0));
        lb19.setText("Jam Kedatangan");

        lb20.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb20.setForeground(new java.awt.Color(0, 0, 0));
        lb20.setText("ID Pengguna");

        lbJamKedatangan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbJamKedatangan.setForeground(new java.awt.Color(0, 0, 0));
        lbJamKedatangan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbIdPengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdPengguna.setForeground(new java.awt.Color(0, 0, 0));
        lbIdPengguna.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb21.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb21.setForeground(new java.awt.Color(0, 0, 0));
        lb21.setText("No Reservasi");

        lbNamaPengguna.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaPengguna.setForeground(new java.awt.Color(0, 0, 0));
        lbNamaPengguna.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbStatus.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbStatus.setForeground(new java.awt.Color(0, 0, 0));
        lbStatus.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb22.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb22.setForeground(new java.awt.Color(0, 0, 0));
        lb22.setText("Nama Pengguna");

        lb23.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb23.setForeground(new java.awt.Color(0, 0, 0));
        lb23.setText("Status");

        lbJenisKelamin.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbJenisKelamin.setForeground(new java.awt.Color(0, 0, 0));
        lbJenisKelamin.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbNamaPasien3.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaPasien3.setForeground(new java.awt.Color(0, 0, 0));
        lbNamaPasien3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb24.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb24.setForeground(new java.awt.Color(0, 0, 0));
        lb24.setText("Jenis Kelamin");

        lb25.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb25.setForeground(new java.awt.Color(0, 0, 0));
        lb25.setText("Nama Pasien");

        lbIdPasien3.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdPasien3.setForeground(new java.awt.Color(0, 0, 0));
        lbIdPasien3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb26.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb26.setForeground(new java.awt.Color(0, 0, 0));
        lb26.setText("ID Pasien");

        jSeparator2.setBackground(new java.awt.Color(185, 185, 185));
        jSeparator2.setForeground(new java.awt.Color(185, 185, 185));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setToolTipText("");

        jSeparator3.setBackground(new java.awt.Color(185, 185, 185));
        jSeparator3.setForeground(new java.awt.Color(185, 185, 185));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setToolTipText("");

        btnBatalReservasi.setBackground(new java.awt.Color(135, 15, 50));
        btnBatalReservasi.setForeground(new java.awt.Color(255, 255, 255));
        btnBatalReservasi.setText("BATALKAN RESERVASI");
        btnBatalReservasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalReservasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbNoReservasi2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lbTglKedatangan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lb18, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                                        .addComponent(lbJamKedatangan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lb19))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb22, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(lb23, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNamaPengguna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbIdPengguna, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lb26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb24, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbNamaPasien3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbIdPasien3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbJenisKelamin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(69, 69, 69))
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBatalReservasi, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addComponent(lb21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbNoReservasi2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(lb18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbTglKedatangan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(lb19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbJamKedatangan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addComponent(lb26, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbIdPasien3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(lb25, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(lb24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbNamaPasien3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel4Layout.createSequentialGroup()
                        .addComponent(lb20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbIdPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(lb22, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(lb23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbNamaPengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3))
                .addGap(18, 18, 18)
                .addComponent(btnBatalReservasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(436, Short.MAX_VALUE))
        );

        panel.add(panel4, "card2");

        panel5.setBackground(new java.awt.Color(255, 255, 255));

        lb28.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb28.setForeground(new java.awt.Color(0, 0, 0));
        lb28.setText("No Pemesanan");

        jPanel5.setBackground(new java.awt.Color(135, 15, 50));

        jLabel5.setBackground(new java.awt.Color(135, 15, 50));
        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("DETAIL PEMESANAN");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        lbTglPemesanan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTglPemesanan.setForeground(new java.awt.Color(0, 0, 0));
        lbTglPemesanan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb29.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb29.setForeground(new java.awt.Color(0, 0, 0));
        lb29.setText("ID Supplier");

        lbNoPemesanan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPemesanan.setForeground(new java.awt.Color(0, 0, 0));
        lbNoPemesanan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb30.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb30.setForeground(new java.awt.Color(0, 0, 0));
        lb30.setText("Nama Supplier");

        lbStatusPemesanan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbStatusPemesanan.setForeground(new java.awt.Color(0, 0, 0));
        lbStatusPemesanan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb31.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb31.setForeground(new java.awt.Color(0, 0, 0));
        lb31.setText("Status");

        lb33.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb33.setForeground(new java.awt.Color(0, 0, 0));
        lb33.setText("Total");

        lbIdSuplr.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdSuplr.setForeground(new java.awt.Color(0, 0, 0));
        lbIdSuplr.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbNamaSuplr.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaSuplr.setForeground(new java.awt.Color(0, 0, 0));
        lbNamaSuplr.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        tableDetailPemesanan.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        tableDetailPemesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Satuan", "Harga Beli", "Jumlah", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll4.setViewportView(tableDetailPemesanan);
        if (tableDetailPemesanan.getColumnModel().getColumnCount() > 0) {
            tableDetailPemesanan.getColumnModel().getColumn(1).setPreferredWidth(200);
            tableDetailPemesanan.getColumnModel().getColumn(3).setPreferredWidth(210);
        }

        jSeparator5.setBackground(new java.awt.Color(185, 185, 185));

        lbBayarPemesanan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbBayarPemesanan.setForeground(new java.awt.Color(0, 0, 0));
        lbBayarPemesanan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbBayarPemesanan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb34.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb34.setForeground(new java.awt.Color(0, 0, 0));
        lb34.setText("Bayar");

        lb35.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb35.setForeground(new java.awt.Color(0, 0, 0));
        lb35.setText("Kembali");

        lbKembaliPemesanan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKembaliPemesanan.setForeground(new java.awt.Color(0, 0, 0));
        lbKembaliPemesanan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbKembaliPemesanan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbTotalPemesanan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTotalPemesanan.setForeground(new java.awt.Color(0, 0, 0));
        lbTotalPemesanan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTotalPemesanan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb36.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb36.setForeground(new java.awt.Color(0, 0, 0));
        lb36.setText("Tanggal");

        jSeparator6.setBackground(new java.awt.Color(185, 185, 185));

        lb37.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb37.setForeground(new java.awt.Color(0, 0, 0));
        lb37.setText("Pemesan");

        lbPemesan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbPemesan.setForeground(new java.awt.Color(0, 0, 0));
        lbPemesan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        javax.swing.GroupLayout panel5Layout = new javax.swing.GroupLayout(panel5);
        panel5.setLayout(panel5Layout);
        panel5Layout.setHorizontalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel5Layout.createSequentialGroup()
                        .addComponent(lb33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTotalPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lb34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbBayarPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lb35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbKembaliPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 80, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel5Layout.createSequentialGroup()
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbNoPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbTglPemesanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbStatusPemesanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPemesan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbIdSuplr, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                                .addComponent(lbNamaSuplr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(34, 34, 34)))
                .addContainerGap())
        );
        panel5Layout.setVerticalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbNoPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb28, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTglPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb36, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbStatusPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb31, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addComponent(lb29, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lb30, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lb37, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addComponent(lbIdSuplr, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(11, 11, 11)
                            .addComponent(lbNamaSuplr, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lbPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbBayarPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb34, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbKembaliPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb35, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTotalPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb33, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll4, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel.add(panel5, "card2");

        panel6.setBackground(new java.awt.Color(255, 255, 255));

        lb32.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb32.setForeground(new java.awt.Color(0, 0, 0));
        lb32.setText("No Penjualan");

        jPanel6.setBackground(new java.awt.Color(135, 15, 50));

        jLabel6.setBackground(new java.awt.Color(135, 15, 50));
        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("DETAIL PENJUALAN");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        lbTglPenjualan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTglPenjualan.setForeground(new java.awt.Color(0, 0, 0));
        lbTglPenjualan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbNoPenjualan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPenjualan.setForeground(new java.awt.Color(0, 0, 0));
        lbNoPenjualan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb41.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb41.setForeground(new java.awt.Color(0, 0, 0));
        lb41.setText("Total");

        tableDetailPenjualan.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        tableDetailPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Satuan", "Harga Jual", "Jumlah", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll5.setViewportView(tableDetailPenjualan);
        if (tableDetailPenjualan.getColumnModel().getColumnCount() > 0) {
            tableDetailPenjualan.getColumnModel().getColumn(1).setPreferredWidth(200);
            tableDetailPenjualan.getColumnModel().getColumn(3).setPreferredWidth(210);
        }

        jSeparator7.setBackground(new java.awt.Color(185, 185, 185));

        lbBayarPenjualan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbBayarPenjualan.setForeground(new java.awt.Color(0, 0, 0));
        lbBayarPenjualan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb42.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb42.setForeground(new java.awt.Color(0, 0, 0));
        lb42.setText("Bayar");

        lb43.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb43.setForeground(new java.awt.Color(0, 0, 0));
        lb43.setText("Kembali");

        lbKembaliPenjualan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKembaliPenjualan.setForeground(new java.awt.Color(0, 0, 0));
        lbKembaliPenjualan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lbTotalPenjualan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTotalPenjualan.setForeground(new java.awt.Color(0, 0, 0));
        lbTotalPenjualan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        lb44.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb44.setForeground(new java.awt.Color(0, 0, 0));
        lb44.setText("Tanggal");

        lb45.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb45.setForeground(new java.awt.Color(0, 0, 0));
        lb45.setText("Kasir");

        lbKasir2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKasir2.setForeground(new java.awt.Color(0, 0, 0));
        lbKasir2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        javax.swing.GroupLayout panel6Layout = new javax.swing.GroupLayout(panel6);
        panel6.setLayout(panel6Layout);
        panel6Layout.setHorizontalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator7)
                    .addComponent(scroll5, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbNoPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbTglPenjualan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbKasir2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb42, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb43, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbBayarPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbKembaliPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTotalPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel6Layout.setVerticalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbNoPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb32, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTglPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb44, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb45, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbKasir2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbTotalPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb41, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbBayarPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb42, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbKembaliPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb43, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(7, 7, 7)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll5, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel.add(panel6, "card2");

        add(panel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    
    
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnBatalReservasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalReservasiActionPerformed
        perbaruiStatus();
    }//GEN-LAST:event_btnBatalReservasiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogDetail dialog = new DialogDetail(new java.awt.Frame(), true, "", null, null, null, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnBatalReservasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel lb1;
    private javax.swing.JLabel lb10;
    private javax.swing.JLabel lb11;
    private javax.swing.JLabel lb12;
    private javax.swing.JLabel lb13;
    private javax.swing.JLabel lb14;
    private javax.swing.JLabel lb15;
    private javax.swing.JLabel lb16;
    private javax.swing.JLabel lb17;
    private javax.swing.JLabel lb18;
    private javax.swing.JLabel lb19;
    private javax.swing.JLabel lb2;
    private javax.swing.JLabel lb20;
    private javax.swing.JLabel lb21;
    private javax.swing.JLabel lb22;
    private javax.swing.JLabel lb23;
    private javax.swing.JLabel lb24;
    private javax.swing.JLabel lb25;
    private javax.swing.JLabel lb26;
    private javax.swing.JLabel lb27;
    private javax.swing.JLabel lb28;
    private javax.swing.JLabel lb29;
    private javax.swing.JLabel lb3;
    private javax.swing.JLabel lb30;
    private javax.swing.JLabel lb31;
    private javax.swing.JLabel lb32;
    private javax.swing.JLabel lb33;
    private javax.swing.JLabel lb34;
    private javax.swing.JLabel lb35;
    private javax.swing.JLabel lb36;
    private javax.swing.JLabel lb37;
    private javax.swing.JLabel lb4;
    private javax.swing.JLabel lb41;
    private javax.swing.JLabel lb42;
    private javax.swing.JLabel lb43;
    private javax.swing.JLabel lb44;
    private javax.swing.JLabel lb45;
    private javax.swing.JLabel lb5;
    private javax.swing.JLabel lb6;
    private javax.swing.JLabel lb7;
    private javax.swing.JLabel lb8;
    private javax.swing.JLabel lb9;
    private javax.swing.JLabel lbBayar;
    private javax.swing.JLabel lbBayarPemesanan;
    private javax.swing.JLabel lbBayarPenjualan;
    private javax.swing.JLabel lbIdKaryawan;
    private javax.swing.JLabel lbIdPasien;
    private javax.swing.JLabel lbIdPasien2;
    private javax.swing.JLabel lbIdPasien3;
    private javax.swing.JLabel lbIdPengguna;
    private javax.swing.JLabel lbIdSuplr;
    private javax.swing.JLabel lbJamKedatangan;
    private javax.swing.JLabel lbJenisKelamin;
    private javax.swing.JLabel lbKasir;
    private javax.swing.JLabel lbKasir2;
    private javax.swing.JLabel lbKembaliPemesanan;
    private javax.swing.JLabel lbKembaliPenjualan;
    private javax.swing.JLabel lbKembalian;
    private javax.swing.JLabel lbNama;
    private javax.swing.JLabel lbNamaPasien;
    private javax.swing.JLabel lbNamaPasien3;
    private javax.swing.JLabel lbNamaPengguna;
    private javax.swing.JLabel lbNamaSuplr;
    private javax.swing.JLabel lbNoPemeriksaan;
    private javax.swing.JLabel lbNoPemeriksaan2;
    private javax.swing.JLabel lbNoPemesanan;
    private javax.swing.JLabel lbNoPengeluaran;
    private javax.swing.JLabel lbNoPenjualan;
    private javax.swing.JLabel lbNoReservasi;
    private javax.swing.JLabel lbNoReservasi2;
    private javax.swing.JLabel lbPemesan;
    private javax.swing.JLabel lbPengguna;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbStatusPemesanan;
    private javax.swing.JLabel lbTerakhirPemeriksaan;
    private javax.swing.JLabel lbTgl;
    private javax.swing.JLabel lbTglKedatangan;
    private javax.swing.JLabel lbTglPemesanan;
    private javax.swing.JLabel lbTglPengeluaran;
    private javax.swing.JLabel lbTglPenjualan;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lbTotalPemesanan;
    private javax.swing.JLabel lbTotalPengeluaran;
    private javax.swing.JLabel lbTotalPenjualan;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel panel5;
    private javax.swing.JPanel panel6;
    private javax.swing.JScrollPane scroll1;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JScrollPane scroll3;
    private javax.swing.JScrollPane scroll4;
    private javax.swing.JScrollPane scroll5;
    private javax.swing.JTable tableDetailPemesanan;
    private javax.swing.JTable tableDetailPengeluaran;
    private javax.swing.JTable tableDetailPenjualan;
    private javax.swing.JTable tablePemeriksaan;
    private javax.swing.JTable tableRiwayat;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextArea txtDescPengeluaran;
    // End of variables declaration//GEN-END:variables
    
}
