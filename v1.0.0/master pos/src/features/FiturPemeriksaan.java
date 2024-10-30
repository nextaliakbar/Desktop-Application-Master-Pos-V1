/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;

import action.ActionPagination;
import action.TableAction;
import control.FieldsPemeriksaan;
import control.ParamPemeriksaan;
import control.Report;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.ModelDetailPemeriksaan;
import util.ModelHeaderTable;
import model.ModelKaryawan;
import model.ModelPemeriksaan;
import util.ModelRenderTable;
import model.ModelPasien;
import model.ModelPengguna;
import model.ModelReservasi;
import model.ModelTindakan;
import model.PemeriksaanSementara;
import service.ServiceDetailPemeriksaan;
import swing.TableCellActionRender;
import swing.TableCellEditor;
import service.ServicePemeriksaan;

/**
 *
 * @author usER
 */
public class FiturPemeriksaan extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private DefaultTableModel tabmodel1;
    private DefaultTableModel tabmodel2;
    private JFrame parent;
    private TableAction action;
    private ModelPengguna modelPengguna;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private ServicePemeriksaan servicPemeriksaan = new ServicePemeriksaan();
    private ServiceDetailPemeriksaan serviceDetail = new ServiceDetailPemeriksaan();
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    public FiturPemeriksaan(JFrame parent, ModelPengguna modelPengguna) {
        initComponents();
        this.parent = parent;
        this.modelPengguna = modelPengguna;
        styleTable(scrollPane, table, 13);
        tabmodel1 = (DefaultTableModel) table.getModel();
        rowSorter = new TableRowSorter<>(tabmodel1);
        table.setRowSorter(rowSorter);
        
        styleTable(scrollPanePasien, tableDetail, 6);
        tabmodel2 = (DefaultTableModel) tableDetail.getModel();
        txtDeskripsi.setLineWrap(true);
        
        tampilData();
        cariData();
        actionTableMain();
        instanceReport();
        actionBtnPrint();
    }
    
//  Tampil data
    private void tampilData() {
        servicPemeriksaan.loadData(1, tabmodel1, pagination);
        pagination.addActionPagination(new ActionPagination() {
            @Override
            public void pageChanged(int page) {
                tabmodel1.setRowCount(0);
                servicPemeriksaan.loadData(page, tabmodel1, pagination);
            }
        });
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
    
//  Tambah Data
    private void tambahData() {
        ModelPasien pasien = new ModelPasien();
        ModelKaryawan karyawan = new ModelKaryawan();
        ModelDetailPemeriksaan detail = new ModelDetailPemeriksaan();
        ModelReservasi modelReservasi = new ModelReservasi();
        modelReservasi.setNoReservasi((String) cbxNoReservasi.getSelectedItem());
        
        String noPemeriksaan = lbNoPemeriksaan.getText();
        String tgl = lbTgl.getText();
        String deskripsi = txtDeskripsi.getText();
        if(deskripsi.equals("Catatan (Opsional)")) {
            deskripsi = null;
        }
        double bayar = Double.parseDouble(txtBayar.getText());
        double kembali = 0;
        String jenisPembayaran = (String) cbx_jenisPembayaran.getSelectedItem();
        String strKembali = lbKembali.getText();
        try {
            Number formatNumber = df.parse(strKembali);
            kembali = Double.parseDouble(formatNumber.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        pasien.setIdPasien(lbIdPasien1.getText());
        pasien.setNama(lbNamaPasien.getText());
        karyawan.setIdKaryawan(lbIdKaryawan.getText());
        karyawan.setNama(lbNamaKaryawan.getText());
        
        
        ModelPemeriksaan pemeriksaan = new ModelPemeriksaan(
        noPemeriksaan, modelReservasi, tgl, deskripsi, 
        String.valueOf(total()), bayar, kembali, jenisPembayaran, pasien, 
        karyawan, modelPengguna);
        servicPemeriksaan.addData(parent, pemeriksaan);
        
//      Tambah Detail
        List<String> kodeTindakan = new ArrayList<>();
        List<Integer> biayaTindakanFinal = new ArrayList<>();
        List<Integer> potongan = new ArrayList<>();
        List<Integer> totalHarga = new ArrayList<>();
        
        detail.setModelPemeriksaan(pemeriksaan);
        for(int a = 0; a < tabmodel2.getRowCount(); a++) {
            kodeTindakan.add((String) tableDetail.getValueAt(a, 1));
            biayaTindakanFinal.add((Integer) tableDetail.getValueAt(a, 3));
            potongan.add((Integer) tableDetail.getValueAt(a, 4));
            totalHarga.add((Integer) tableDetail.getValueAt(a, 5));
            
            PemeriksaanSementara
                    ps = new PemeriksaanSementara(kodeTindakan, biayaTindakanFinal, 
                            potongan, totalHarga);
            serviceDetail.addData(detail, ps);
        }
        
        tabmodel2.setRowCount(0);
    }
    
    
    private int total() {
        
        int total = 0;
         for(int a = 0; a < tabmodel2.getRowCount(); a++) {
             int subtotal = (int) tableDetail.getValueAt(a, 5);
             total += subtotal;
         }
        return total; 
    }
    
    private int totalPotongan() {
        int totalPotongan = 0;
        int rowCount = tableDetail.getRowCount();
        for(int a = 0; a < rowCount; a++) {
            int subtotalPotongan = (int) tableDetail.getValueAt(a, 4);
            totalPotongan += subtotalPotongan;
        }
        return totalPotongan;
    }
    
    private int potongan() {
        int harga = Integer.parseInt(lbHarga.getText());
        String disc = txtPotongan.getText();
        int potongan = 0;
        int percent = 0;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(disc);

        if(matcher.find()) {
            percent = Integer.parseInt(matcher.group());
            if(percent <= 100) {
                float decimal = (float) percent / 100;
                potongan = (int) (harga * decimal);
            } else {
                potongan = percent;
            }
        }
        return potongan;
    }
    
//  Tambah Sementara
    private void tambahDataSementara() {
         String kodeTindakan = lbKodeTindakan.getText();
         String namaTindakan = lbNamaTindakan.getText();
         int harga = Integer.parseInt(lbHarga.getText());         
         ModelTindakan modelTindakan = new ModelTindakan(kodeTindakan, namaTindakan, harga);
         int totalHarga = harga - potongan();
         tabmodel2.addRow(new ModelDetailPemeriksaan(null, modelTindakan, totalHarga, potongan()).toRowTable());
         lbTotal.setText(df.format(total()));
    }
    
//    Detail Pemeriksaan
    private void detailPemeriksaan(int row) {
        ModelPemeriksaan modelPemeriksaan = new ModelPemeriksaan();
        ModelPasien modelPasien = new ModelPasien();
        ModelKaryawan modelKaryawan = new ModelKaryawan();
        ModelReservasi modelReservasi = new ModelReservasi();
        ModelPengguna modelPengguna = new ModelPengguna();
        
        modelPemeriksaan.setNoPemeriksaan((String) table.getValueAt(row, 0));
        modelReservasi.setNoReservasi((String) table.getValueAt(row, 1));
        modelPemeriksaan.setModelReservasi(modelReservasi);
        modelPasien.setIdPasien((String) table.getValueAt(row, 2));
        modelPasien.setNama((String) table.getValueAt(row, 3));
        modelPemeriksaan.setModelPasien(modelPasien);
        modelKaryawan.setIdKaryawan((String) table.getValueAt(row, 4));
        modelPemeriksaan.setModelKaryawan(modelKaryawan);
        modelPemeriksaan.setTglPemeriksaan((String) table.getValueAt(row, 5));
        modelPemeriksaan.setTotal((String) table.getValueAt(row, 6));
        modelPemeriksaan.setDeskripsi((String) table.getValueAt(row, 7));
        modelPemeriksaan.setBayar((double) table.getValueAt(row, 8));
        modelPemeriksaan.setKembali((double) table.getValueAt(row, 9));
        modelPemeriksaan.setJenisPembayaran((String) table.getValueAt(row, 10));
        modelPengguna.setIdpengguna((String) table.getValueAt(row, 11));
        modelPengguna.setNama((String) table.getValueAt(row, 12));
        modelPemeriksaan.setModelPengguna(modelPengguna);
        
        ModelDetailPemeriksaan modelDetail = new ModelDetailPemeriksaan();
        modelDetail.setModelPemeriksaan(modelPemeriksaan);

        DialogDetail dialog = new DialogDetail(parent, true, "Slide-1", modelDetail, 
                null, null, null);
        dialog.setVisible(true);
    }
    
//      Instance report
    private void instanceReport() {
        try {
            Report.getInstance().compileReport("Pemeriksaan");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
//      Print pemeriksaan
    private void printPemeriksaan() {
        try {
        List<FieldsPemeriksaan> fields = new ArrayList<>();
        for(int a = 0; a < tableDetail.getRowCount(); a++) {
            ModelDetailPemeriksaan pemeriksaan = (ModelDetailPemeriksaan) tableDetail.getValueAt(a, 0);
            fields.add(new FieldsPemeriksaan(pemeriksaan.getModelTindakan().getKodeTindakan(), 
            df.format(pemeriksaan.getModelTindakan().getBiaya()), df.format(pemeriksaan.getPotongan()), 
            df.format(pemeriksaan.getSubtotal())));
        }
            String noPemeriksaan = lbNoPemeriksaan.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(lbTgl.getText(), formatter);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MMMM - yyyy");
            String tglPemeriksaan = format.format(date);
            Date dateNow = new Date();
            String jamPemeriksaan = new SimpleDateFormat("HH:mm").format(dateNow) + " WIB";
            String pasien = lbNamaPasien.getText();
            String karyawan = lbIdKaryawan.getText();
            String admin = modelPengguna.getIdpengguna();
            String total = lbTotal.getText();
            String totalPotongan = String.valueOf(totalPotongan());
            String bayar = df.format(Double.parseDouble(txtBayar.getText()));
            String kembali = lbKembali.getText();
            String jenisPembayaran = (String) cbx_jenisPembayaran.getSelectedItem();
            ParamPemeriksaan parameter = new ParamPemeriksaan(noPemeriksaan, tglPemeriksaan, jamPemeriksaan, pasien, karyawan, admin, 
            total, totalPotongan, bayar, kembali, jenisPembayaran, fields);
            Report.getInstance().printReportPemeriksaan(parameter);

        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Peringatan", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
//  Update,Delete,Detail Table Main
    private void actionTableMain() {
        action = new TableAction() {
        @Override
        public void edit(int row) {
        }

        @Override
        public void delete(int row) {
        }

        @Override
        public void view(int row) {
            detailPemeriksaan(row);
            txtCari.setText("");
            if(txtCari.getText().length() == 0) {
                tabmodel1.setRowCount(0);
                tampilData();
            }
        }
    };        
        table.getColumnModel().getColumn(13).setCellRenderer(new TableCellActionRender(false, false, true));
        table.getColumnModel().getColumn(13).setCellEditor(new TableCellEditor(action, false, false, true));
    }
    
//    Update,Delete,Detail Table Detail
    private void actionTableDetail() {
        action = new TableAction() {
            @Override
            public void edit(int row) {
            }

            @Override
            public void delete(int row) {
                if(tableDetail.isEditing()) {
                    tableDetail.getCellEditor().stopCellEditing();
                }
                tabmodel2.removeRow(row);
                lbTotal.setText(df.format(total()));
                double kembali = Double.parseDouble(txtBayar.getText()) - total();
                lbKembali.setText(df.format(kembali));
            }

            @Override
            public void view(int row) {
                
            }
        };
        
        tableDetail.getColumnModel().getColumn(6).setCellRenderer(new TableCellActionRender(false, true, false));
        tableDetail.getColumnModel().getColumn(6).setCellEditor(new TableCellEditor(action, false, true, false));
    }
    
    private void viewCheck(String text, JTextField field, int size) {
        field.setText(text);
        field.setFont(new Font("sansserif", Font.ITALIC, size));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setForeground(new Color(185, 185, 185));
    }
    
//    check promo
    private void checkPromo() {
        String idMember = txtPotongan.getText();
        if(idMember.length() > 0) {
            if(servicPemeriksaan.checkMember(idMember)) {
                int potongan = servicPemeriksaan.promo();
                txtPotongan.setHorizontalAlignment(JTextField.LEADING);
                if(potongan == 0) {
                    viewCheck("Tidak ada promo", txtPotongan, 20);
                    txtPotongan.setHorizontalAlignment(JTextField.CENTER);
                } else if(potongan <= 100) {
                    txtPotongan.setText(String.valueOf(potongan).concat(" " + "%"));   
                } else {
                    txtPotongan.setText(String.valueOf(potongan));       
                }
            } else {
                    viewCheck("Pasien bukan member", txtPotongan, 20);
            }
                tabEnter.setText("Scan Ulang");
                txtPotongan.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(parent, "Silahkan scan kartu member");
        }
    }
    
    private void validationCheckPromo() {
        String idMember = txtPotongan.getText();
        if(idMember.equals(lbIdPasien1.getText())) {
            checkPromo();
        } else {
            txtPotongan.setHorizontalAlignment(JTextField.CENTER);
            viewCheck("Klik disini dan Scan Kartu Member", txtPotongan, 14);
            JOptionPane.showMessageDialog(parent, "Pasien tidak terdaftar"); 
        }
    }
        
    private void cariData() {
        txtCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txtCari.getText();
                if(text.length() == 0) {
                   rowSorter.setRowFilter(null);
                   pagination.setVisible(true);
                } else {
                   pagination.setVisible(false);
                   rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text, 0, 3, 5));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtCari.getText();
                if(text.length() == 0) {
                   rowSorter.setRowFilter(null);
                   pagination.setVisible(true);
                } else {
                   pagination.setVisible(false);
                   rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text, 0, 3, 5));
                }
           }

            @Override
            public void changedUpdate(DocumentEvent e) {
               
           }
        });
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelData = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnTambah = new swing.Button();
        label = new javax.swing.JLabel();
        pagination = new swing.Pagination();
        txtCari = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        panelTambah = new javax.swing.JPanel();
        panel3 = new javax.swing.JPanel();
        scrollPanePasien = new javax.swing.JScrollPane();
        tableDetail = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbRp = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        lbKembali = new javax.swing.JLabel();
        cbx_jenisPembayaran = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        btnTambahSementara = new swing.Button();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbxNoReservasi = new javax.swing.JComboBox<>();
        btnPilih1 = new swing.Button();
        btnPilih2 = new swing.Button();
        txtDeskripsi = new javax.swing.JTextArea();
        lbNoPemeriksaan = new javax.swing.JLabel();
        lbTgl = new javax.swing.JLabel();
        lbNamaPasien = new javax.swing.JLabel();
        lbIdKaryawan = new javax.swing.JLabel();
        lbNamaKaryawan = new javax.swing.JLabel();
        lbKodeTindakan = new javax.swing.JLabel();
        lbNamaTindakan = new javax.swing.JLabel();
        lbHarga = new javax.swing.JLabel();
        txtPotongan = new javax.swing.JTextField();
        tabEnter = new javax.swing.JLabel();
        lbIdPasien2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        btnBatal = new swing.Button();
        btnSimpan = new swing.Button();
        lbIdPasien1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        panelData.setBackground(new java.awt.Color(153, 153, 153));
        panelData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);

        table.setBackground(new java.awt.Color(255, 255, 255));
        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Pemeriksaan", "No Reservasi", "ID Pasien", "Nama Pasien", "ID Karyawan", "Tanggal Pemeriksaan", "Total", "Deskripsi", "Bayar", "Kembali", "Jenis Pembayaran", "ID Pengguna", "Nama Pengguna", "        Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setGridColor(new java.awt.Color(185, 185, 185));
        table.setOpaque(false);
        table.setSelectionBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(2).setMinWidth(0);
            table.getColumnModel().getColumn(2).setPreferredWidth(0);
            table.getColumnModel().getColumn(2).setMaxWidth(0);
            table.getColumnModel().getColumn(4).setMinWidth(0);
            table.getColumnModel().getColumn(4).setPreferredWidth(0);
            table.getColumnModel().getColumn(4).setMaxWidth(0);
            table.getColumnModel().getColumn(7).setMinWidth(0);
            table.getColumnModel().getColumn(7).setPreferredWidth(0);
            table.getColumnModel().getColumn(7).setMaxWidth(0);
            table.getColumnModel().getColumn(8).setMinWidth(0);
            table.getColumnModel().getColumn(8).setPreferredWidth(0);
            table.getColumnModel().getColumn(8).setMaxWidth(0);
            table.getColumnModel().getColumn(9).setMinWidth(0);
            table.getColumnModel().getColumn(9).setPreferredWidth(0);
            table.getColumnModel().getColumn(9).setMaxWidth(0);
            table.getColumnModel().getColumn(10).setMinWidth(0);
            table.getColumnModel().getColumn(10).setPreferredWidth(0);
            table.getColumnModel().getColumn(10).setMaxWidth(0);
            table.getColumnModel().getColumn(11).setMinWidth(0);
            table.getColumnModel().getColumn(11).setPreferredWidth(0);
            table.getColumnModel().getColumn(11).setMaxWidth(0);
            table.getColumnModel().getColumn(12).setMinWidth(0);
            table.getColumnModel().getColumn(12).setPreferredWidth(0);
            table.getColumnModel().getColumn(12).setMaxWidth(0);
            table.getColumnModel().getColumn(13).setMinWidth(100);
            table.getColumnModel().getColumn(13).setPreferredWidth(100);
            table.getColumnModel().getColumn(13).setMaxWidth(100);
        }

        btnTambah.setBackground(new java.awt.Color(135, 15, 50));
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("TAMBAH");
        btnTambah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        label.setBackground(new java.awt.Color(135, 15, 50));
        label.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label.setForeground(new java.awt.Color(135, 15, 50));
        label.setText("PEMERIKSAAN");

        pagination.setBackground(new java.awt.Color(135, 15, 50));
        pagination.setForeground(new java.awt.Color(255, 255, 255));
        pagination.setOpaque(false);

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(185, 185, 185));
        txtCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari.setText("Cari Berdasarkan No Pemeriksaan, Nama Pasien atau Tanggal");
        txtCari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(185, 185, 185)));
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCariFocusLost(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 585, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addComponent(label)
                            .addGap(0, 933, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 765, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label)
                    .addGap(63, 63, 63)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGap(53, 53, 53)))
        );

        javax.swing.GroupLayout panelDataLayout = new javax.swing.GroupLayout(panelData);
        panelData.setLayout(panelDataLayout);
        panelDataLayout.setHorizontalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDataLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        panelDataLayout.setVerticalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        add(panelData, "card2");

        panelTambah.setBackground(new java.awt.Color(153, 153, 153));
        panelTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        scrollPanePasien.setBorder(null);
        scrollPanePasien.setOpaque(false);

        tableDetail.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tableDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Kode Tindakan", "Nama Tindakan", "Harga", "Potongan", "Total Harga", "         Aksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDetail.setOpaque(false);
        tableDetail.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tableDetail.setSelectionForeground(new java.awt.Color(255, 255, 255));
        scrollPanePasien.setViewportView(tableDetail);
        if (tableDetail.getColumnModel().getColumnCount() > 0) {
            tableDetail.getColumnModel().getColumn(0).setMinWidth(0);
            tableDetail.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableDetail.getColumnModel().getColumn(0).setMaxWidth(0);
            tableDetail.getColumnModel().getColumn(6).setMinWidth(100);
            tableDetail.getColumnModel().getColumn(6).setPreferredWidth(100);
            tableDetail.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        jPanel2.setBackground(new java.awt.Color(135, 15, 50));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("TOTAL : ");

        lbRp.setBackground(new java.awt.Color(255, 255, 255));
        lbRp.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbRp.setForeground(new java.awt.Color(255, 255, 255));
        lbRp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbRp.setText("Rp");

        lbTotal.setBackground(new java.awt.Color(255, 255, 255));
        lbTotal.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(255, 255, 255));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTotal.setText("0");

        btnPrint.setBackground(new java.awt.Color(135, 15, 50));
        btnPrint.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/printerwhite.png"))); // NOI18N
        btnPrint.setText("F1");
        btnPrint.setBorder(null);
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbRp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("BAYAR");

        txtBayar.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txtBayar.setForeground(new java.awt.Color(0, 0, 0));
        txtBayar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        txtBayar.setOpaque(false);
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBayarKeyTyped(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        jPanel6.setOpaque(false);

        lbKembali.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lbKembali.setForeground(new java.awt.Color(0, 0, 0));
        lbKembali.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(lbKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbKembali, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        cbx_jenisPembayaran.setBackground(new java.awt.Color(255, 255, 255));
        cbx_jenisPembayaran.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        cbx_jenisPembayaran.setForeground(new java.awt.Color(0, 0, 0));
        cbx_jenisPembayaran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tunai", "Non Tunai" }));
        cbx_jenisPembayaran.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("KEMBALI");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Rp");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Rp");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPanePasien)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addComponent(txtBayar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbx_jenisPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBayar)
                                .addComponent(cbx_jenisPembayaran))
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanePasien)
                .addContainerGap())
        );

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        btnTambahSementara.setBackground(new java.awt.Color(135, 15, 50));
        btnTambahSementara.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahSementara.setText("Tambah");
        btnTambahSementara.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTambahSementara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSementaraActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("No Reservasi");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("No Pemeriksaan");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Kode Tindakan");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Nama Pasien");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Nama Tindakan");

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Tanggal");

        jLabel10.setBackground(new java.awt.Color(134, 15, 50));
        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Pilih Terapis");
        jLabel10.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(134, 15, 50));
        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Pilih Tindakan");
        jLabel11.setOpaque(true);

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("ID Terapis");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Nama Terapis");

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Harga");

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Potongan");

        cbxNoReservasi.setBackground(new java.awt.Color(255, 255, 255));
        cbxNoReservasi.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        cbxNoReservasi.setForeground(new java.awt.Color(0, 0, 0));
        cbxNoReservasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih No Reservasi" }));
        cbxNoReservasi.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        cbxNoReservasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNoReservasiActionPerformed(evt);
            }
        });

        btnPilih1.setBackground(new java.awt.Color(135, 15, 50));
        btnPilih1.setForeground(new java.awt.Color(255, 255, 255));
        btnPilih1.setText("PILIH");
        btnPilih1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilih1ActionPerformed(evt);
            }
        });

        btnPilih2.setBackground(new java.awt.Color(135, 15, 50));
        btnPilih2.setForeground(new java.awt.Color(255, 255, 255));
        btnPilih2.setText("PILIH");
        btnPilih2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilih2ActionPerformed(evt);
            }
        });

        txtDeskripsi.setColumns(20);
        txtDeskripsi.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDeskripsi.setForeground(new java.awt.Color(185, 185, 185));
        txtDeskripsi.setRows(5);
        txtDeskripsi.setText("Catatan (Opsional)");
        txtDeskripsi.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        txtDeskripsi.setOpaque(false);
        txtDeskripsi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDeskripsiFocusGained(evt);
            }
        });

        lbNoPemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPemeriksaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbNoPemeriksaan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbTgl.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTgl.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNamaPasien.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaPasien.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbIdKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdKaryawan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbIdKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNamaKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaKaryawan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbNamaKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbKodeTindakan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKodeTindakan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNamaTindakan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaTindakan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbHarga.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbHarga.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        txtPotongan.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtPotongan.setForeground(new java.awt.Color(185, 185, 195));
        txtPotongan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPotongan.setText("Klik disini dan Scan Kartu Member");
        txtPotongan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        txtPotongan.setOpaque(false);
        txtPotongan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPotonganFocusGained(evt);
            }
        });
        txtPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPotonganActionPerformed(evt);
            }
        });
        txtPotongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPotonganKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPotonganKeyReleased(evt);
            }
        });

        tabEnter.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tabEnter.setForeground(new java.awt.Color(135, 15, 50));
        tabEnter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tabEnter.setText("Tab Enter");
        tabEnter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabEnterMouseClicked(evt);
            }
        });

        lbIdPasien2.setBackground(new java.awt.Color(255, 255, 255));
        lbIdPasien2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lbIdPasien2.setForeground(new java.awt.Color(0, 0, 0));
        lbIdPasien2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxNoReservasi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNoPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNamaPasien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTambahSementara, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbIdPasien2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDeskripsi)
                            .addComponent(lbNamaKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNamaTindakan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbHarga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPotongan)
                            .addComponent(tabEnter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(lbIdKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPilih1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(lbKodeTindakan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPilih2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbNoPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxNoReservasi, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbTgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbNamaPasien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(btnPilih1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbIdKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbNamaKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnPilih2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbKodeTindakan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbNamaTindakan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtPotongan))
                .addGap(5, 5, 5)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(tabEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDeskripsi, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                            .addComponent(lbIdPasien2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(btnTambahSementara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        label1.setBackground(new java.awt.Color(135, 15, 50));
        label1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label1.setText("Tambah Pemeriksaan");

        btnBatal.setForeground(new java.awt.Color(135, 15, 50));
        btnBatal.setText("BATAL");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnSimpan.setForeground(new java.awt.Color(135, 15, 50));
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        lbIdPasien1.setBackground(new java.awt.Color(135, 15, 50));
        lbIdPasien1.setForeground(new java.awt.Color(135, 15, 50));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbIdPasien1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label1)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbIdPasien1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelTambahLayout = new javax.swing.GroupLayout(panelTambah);
        panelTambah.setLayout(panelTambahLayout);
        panelTambahLayout.setHorizontalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTambahLayout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTambahLayout.setVerticalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(panelTambah, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        changePanel(panelTambah);
        lbIdPasien1.setVisible(false);
        lbIdPasien2.setVisible(false);
        lbNoPemeriksaan.setText(servicPemeriksaan.createNo());
        actionTableDetail();
        cbxNoReservasi.removeAllItems();
        cbxNoReservasi.addItem("Pilih No Reservasi");
        servicPemeriksaan.readReservasi(cbxNoReservasi);
        tabmodel2.setRowCount(0);
        tabEnter.setVisible(false);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        txtCari.setText(null);
        txtCari.setForeground(new Color(0,0,0));
        txtCari.setFont(new Font("sansserif",0,14));
        pagination.setVisible(false);
        tabmodel1.setRowCount(0);
        servicPemeriksaan.loadAll(tabmodel1);
    }//GEN-LAST:event_txtCariFocusGained

    private void btnTambahSementaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSementaraActionPerformed
        if(validationAddTemporary()) {
            tambahDataSementara();
            clearFieldTindakan();
            String bayar = txtBayar.getText();
            double kembali = 0;
            if(bayar.length() > 0) {
                kembali = Double.parseDouble(bayar) - total();
            }
            lbKembali.setText(df.format(kembali));    
        }
    }//GEN-LAST:event_btnTambahSementaraActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(validation()) {
        tambahData();
        tabmodel1.setRowCount(0);
        clearField();
        changePanel(panelData);
        tampilData();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        if(tableDetail.getRowCount() != 0) {
            int confirm = JOptionPane.showConfirmDialog(parent, "Data yang telah diinput akan dihapus", "Konfirmasi", JOptionPane.OK_OPTION);
            if(confirm == 0) {
            clearField();
            changePanel(panelData);
            }   
        } else {
            clearField();
            changePanel(panelData); 
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnPilih1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilih1ActionPerformed
        PilihPemeriksaan pilih = new PilihPemeriksaan(parent, true, "Slide-1");
        pilih.setVisible(true);
        lbIdKaryawan.setText(pilih.modelKaryawan.getIdKaryawan());
        lbNamaKaryawan.setText(pilih.modelKaryawan.getNama());   
    }//GEN-LAST:event_btnPilih1ActionPerformed

    private void btnPilih2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilih2ActionPerformed
        PilihPemeriksaan pilih = new PilihPemeriksaan(parent, true, "Slide-2");
        pilih.setVisible(true);
        lbKodeTindakan.setText(pilih.modelTindakan.getKodeTindakan());
        lbNamaTindakan.setText(pilih.modelTindakan.getNamaTindakan());
        lbHarga.setText(String.valueOf(pilih.modelTindakan.getBiaya()));
    }//GEN-LAST:event_btnPilih2ActionPerformed

    private void cbxNoReservasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNoReservasiActionPerformed
        int selectIndex = cbxNoReservasi.getSelectedIndex();
        
        if(selectIndex == selectIndex) {
            String noReservasi = (String) cbxNoReservasi.getSelectedItem();
            servicPemeriksaan.readLabel(noReservasi, lbTgl, lbIdPasien1,lbNamaPasien);
        }
    }//GEN-LAST:event_cbxNoReservasiActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        if(validation()) {
            printPemeriksaan();    
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void txtPotonganKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPotonganKeyReleased
        String member = txtPotongan.getText();
        if(member.trim().length() > 0) {
            tabEnter.setVisible(true);
        } else {
            tabEnter.setVisible(false);
        }
    }//GEN-LAST:event_txtPotonganKeyReleased

    private void txtPotonganFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPotonganFocusGained
        txtPotongan.setText(null);
        txtPotongan.setFont(new Font("sansserif", Font.PLAIN, 20));
        txtPotongan.setHorizontalAlignment(JTextField.CENTER);
        txtPotongan.setForeground(new Color(0, 0, 0));
        lbIdPasien2.setText("");
    }//GEN-LAST:event_txtPotonganFocusGained

    private void tabEnterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabEnterMouseClicked
        if(tabEnter.getText().equals("Scan Ulang")) {
            txtPotongan.setEnabled(true);
            tabEnter.setText("Tab Enter");
            txtPotongan.setHorizontalAlignment(JTextField.CENTER);
            txtPotongan.requestFocus();
        }
    }//GEN-LAST:event_tabEnterMouseClicked

    private void txtPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPotonganActionPerformed
        validationCheckPromo();
    }//GEN-LAST:event_txtPotonganActionPerformed

    private void txtBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyReleased
        String strBayar = txtBayar.getText();
        double total = (double) total();
        double bayar = 0;
        double kembali = 0;
        if(strBayar.length() > 0) {
            bayar = Double.parseDouble(strBayar);
        }
        kembali = bayar - total;
        lbKembali.setText(df.format(kembali));        
    }//GEN-LAST:event_txtBayarKeyReleased

    private void txtBayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_txtBayarKeyTyped

    private void txtCariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusLost
        pagination.setVisible(true);
    }//GEN-LAST:event_txtCariFocusLost

    private void txtDeskripsiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDeskripsiFocusGained
        txtDeskripsi.setText("");
        txtDeskripsi.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_txtDeskripsiFocusGained

    private void txtPotonganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPotonganKeyPressed
        lbIdPasien2.setText(txtPotongan.getText());    
    }//GEN-LAST:event_txtPotonganKeyPressed
    
    private void actionBtnPrint() {
        btnPrint.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"), "F1Pressed");
        btnPrint.getActionMap().put("F1Pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validation()) {
                    printPemeriksaan();    
                }
            }
        });
    }
    
    private void changePanel(JPanel panel) {
        removeAll();
        add(panel);
        repaint();
        revalidate();
    }
    
    private void clearField() {
        lbTgl.setText(null);
        lbNamaPasien.setText(null);
        lbIdKaryawan.setText(null);
        lbNamaKaryawan.setText(null);
        lbTotal.setText(String.valueOf(0));
        txtBayar.setText("");
        lbKembali.setText("0");
        tabmodel2.setRowCount(0);
        clearFieldTindakan();
    }
    
    private void clearFieldTindakan() {
        lbKodeTindakan.setText(null);
        lbNamaTindakan.setText(null);
        lbHarga.setText(null);
        viewCheck("Klik disini dan Scan Kartu Member", txtPotongan, 14);
        txtPotongan.setEnabled(true);
        tabEnter.setText("Tab Enter");
        tabEnter.setVisible(false);
        txtDeskripsi.setText("Catatan (Opsional)");
        txtDeskripsi.setForeground(new Color(185, 185, 185));
    }
    
    private boolean validation() {
        boolean valid = false;
        
        if(lbTgl.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Nomor Reservasi");
        } else if(lbIdKaryawan.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Karyawan");  
        } else if(txtBayar.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(parent, "Silahkan Masukkan Jumlah Pembayaran");
        } else if(tableDetail.getRowCount() == 0) {
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Tindakan");    
        } else if(Double.parseDouble(txtBayar.getText()) < total()) {
            JOptionPane.showMessageDialog(parent, "Jumlah Pembayaran Kurang dari Total Pemeriksaan");    
        } else {
            valid = true;
        }
        
        return valid;
    }
    
    private boolean validationAddTemporary() {
        boolean valid = true;
        int rowCount = tableDetail.getRowCount();
        String disc = txtPotongan.getText();
        String kodeTdkn = lbKodeTindakan.getText();
        ModelPasien modelPasien = new ModelPasien();
        modelPasien.setIdPasien(lbIdPasien2.getText());
        try {
            if(kodeTdkn.length() == 0) {
                valid = false;
                JOptionPane.showMessageDialog(parent, "Silahkan Pilih Tindakan");
            } else if(servicPemeriksaan.validastionAddData(modelPasien) && lbIdPasien2.getText().length() != 0) {
                valid = false;
                JOptionPane.showMessageDialog(parent, "Pasien tidak terdaftar");
            } else if(!disc.equals("Klik disini dan Scan Kartu Member")) {
                if(potongan() == 0 || totalPotongan() == 0) {
                    valid = true;
                } else if(potongan() <= totalPotongan() || potongan() >= totalPotongan()) {
                    valid = false;
                    JOptionPane.showMessageDialog(parent, "Potongan hanya berlaku 1x");
                }
            } else {
                for(int a = 0; a < rowCount; a++) {
                    String kodeTdknSmntr = (String) tableDetail.getValueAt(a, 1);
                    if(kodeTdkn.equals(kodeTdknSmntr)) {
                        valid = false;
                        JOptionPane.showMessageDialog(parent, "Tindakan ini sudah di tambahkan");
                        break;
                    } else {
                        valid = true;
                        
                    }
                }
            }
        } catch(NullPointerException ex) {
            valid = false;
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Tindakan");
        }
        
        return valid;
    }
    
    private void characterDigit(KeyEvent evt) {
        char digit = evt.getKeyChar();
        if(!Character.isDigit(digit)) {
            evt.consume();
        }
    }
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnBatal;
    private swing.Button btnPilih1;
    private swing.Button btnPilih2;
    private javax.swing.JButton btnPrint;
    private swing.Button btnSimpan;
    private swing.Button btnTambah;
    private swing.Button btnTambahSementara;
    private javax.swing.JComboBox<String> cbxNoReservasi;
    private javax.swing.JComboBox<String> cbx_jenisPembayaran;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lbHarga;
    private javax.swing.JLabel lbIdKaryawan;
    private javax.swing.JLabel lbIdPasien1;
    private javax.swing.JLabel lbIdPasien2;
    private javax.swing.JLabel lbKembali;
    private javax.swing.JLabel lbKodeTindakan;
    private javax.swing.JLabel lbNamaKaryawan;
    private javax.swing.JLabel lbNamaPasien;
    private javax.swing.JLabel lbNamaTindakan;
    private javax.swing.JLabel lbNoPemeriksaan;
    private javax.swing.JLabel lbRp;
    private javax.swing.JLabel lbTgl;
    private javax.swing.JLabel lbTotal;
    private swing.Pagination pagination;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panelData;
    private javax.swing.JPanel panelTambah;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane scrollPanePasien;
    private javax.swing.JLabel tabEnter;
    private javax.swing.JTable table;
    private javax.swing.JTable tableDetail;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextArea txtDeskripsi;
    private javax.swing.JTextField txtPotongan;
    // End of variables declaration//GEN-END:variables
}
