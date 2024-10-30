/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;

import action.ActionPagination;
import action.TableAction;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.ModelBarang;
import model.ModelDetailPemesanan;
import model.ModelNotifikasi;
import util.ModelHeaderTable;
import model.ModelPemesanan;
import model.ModelPengguna;
import util.ModelRenderTable;
import model.ModelSupplier;
import model.Sementara;
import service.ServiceDetailPemesanan;
import service.ServiceNotifikasi;
import service.ServicePemesanan;
import swing.TableCellActionRender;
import swing.TableCellEditor;

/**
 *
 * @author usER
 */
public class FiturPemesanan extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private DefaultTableModel tabmodel1;
    private DefaultTableModel tabmodel2;
    private TableAction action;
    private JFrame parent;
    private JButton btnNotif;
    private ModelPengguna modelPengguna;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private ServicePemesanan servicePemesanan = new ServicePemesanan();
    private ServiceDetailPemesanan serviceDetail = new ServiceDetailPemesanan();
    private ServiceNotifikasi serviceNotifikasi = new ServiceNotifikasi();
    public FiturPemesanan(JFrame parent, ModelPengguna modelPengguna, JButton btnNotif) {
        initComponents();
        this.parent = parent;
        this.modelPengguna = modelPengguna;
        this.btnNotif = btnNotif;
        table.scrollPane(scrollPane);
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        tabmodel1 = (DefaultTableModel) table.getModel();
        rowSorter = new TableRowSorter<>(tabmodel1);
        table.setRowSorter(rowSorter);
        styleTable(scrollPanePasien, tableDetail, 7);
        tabmodel2 = (DefaultTableModel) tableDetail.getModel();
        
        tampilData();
        cariData();
        actionTableData();
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
    
//  Update,Delete,Detail Main
    private void actionTableData() {
        action = new TableAction() {
        @Override
        public void edit(int row) {
        }

        @Override
        public void delete(int row) {
            
        }

        @Override
        public void view(int row) {
            tampilDataDetail(row);
            txtCari.setText("");
            if(txtCari.getText().length() == 0) {
                tabmodel1.setRowCount(0);
                tampilData();
            }
        }
    };        
        table.getColumnModel().getColumn(12).setCellRenderer(new TableCellActionRender(false, false, true));
        table.getColumnModel().getColumn(12).setCellEditor(new TableCellEditor(action, false, false, true));
    }
    
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
                DecimalFormat df = new DecimalFormat("#,##0.##");
                lbTotal.setText(df.format(total()));
                double kembali = Double.parseDouble(txtBayar.getText()) - total();
                lbKembalian.setText(df.format(kembali));
            }

            @Override
            public void view(int row) {
                
            }
        };
        
        tableDetail.getColumnModel().getColumn(7).setCellRenderer(new TableCellActionRender(false, true, false));
        tableDetail.getColumnModel().getColumn(7).setCellEditor(new TableCellEditor(action, false, true, false));
    }
    
//    Tampil Data
    private void tampilData() {
        servicePemesanan.loadData(1, tabmodel1, pagination);
        pagination.addActionPagination(new ActionPagination() {
            @Override
            public void pageChanged(int page) {
                tabmodel1.setRowCount(0);
                servicePemesanan.loadData(page, tabmodel1, pagination);
            }
        });
    }
    
//    Tammpil Data Detail
    private void tampilDataDetail(int row) {
        ModelDetailPemesanan modelDetail = new ModelDetailPemesanan();
        ModelPemesanan modelPemesanan = new ModelPemesanan();
        ModelSupplier modelSupplier = new ModelSupplier();
        ModelPengguna modelPengguna = new ModelPengguna();

        modelPemesanan.setNoPemesanan((String) table.getValueAt(row, 0));
        modelSupplier.setIdSupplier((String) table.getValueAt(row, 1));
        modelSupplier.setNamaSupplier((String) table.getValueAt(row, 2));
        modelPemesanan.setModelSupplier(modelSupplier);
        modelPemesanan.setTglPemesanan((String) table.getValueAt(row, 3));
        modelPemesanan.setTotalPemesanan((String) table.getValueAt(row, 4));
        modelPemesanan.setBayar((double) table.getValueAt(row, 5));
        modelPemesanan.setKembali((double) table.getValueAt(row, 6));
        modelPemesanan.setJenisPembayaran((String) table.getValueAt(row, 7));
        modelPemesanan.setStatusPemesanan((String) table.getValueAt(row, 9));
        modelPengguna.setIdpengguna((String) table.getValueAt(row, 10));
        modelPengguna.setNama((String) table.getValueAt(row, 11));
        modelPemesanan.setModelPengguna(modelPengguna);
        modelDetail.setModelPemesanan(modelPemesanan);

        DialogDetail dialog = new DialogDetail(parent, true, "Slide-5", null, null, modelDetail, null);
        dialog.setVisible(true);
    }
    
//    Tambah Data
    private void tambahData() {
        ModelDetailPemesanan detail = new ModelDetailPemesanan();
        ModelSupplier modelSupplier = new ModelSupplier();
        String noPemesanan = lbNoPemesanan.getText();
        Date date = new Date();
        String tglPemesanan = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String totalPemesanan = String.valueOf(total());
        double bayar = Double.parseDouble(txtBayar.getText());
        double kembalian = 0;
        String jenisPembayaran = (String) cbx_jenisPembayaran.getSelectedItem();
        String strKembalian = lbKembalian.getText();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        try {
            Number formatNumber = df.parse(strKembalian);
            kembalian = Double.parseDouble(formatNumber.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        String idSupplier = lbIdSupplier.getText();
        modelSupplier.setIdSupplier(idSupplier);
        
        ModelPemesanan modelPemesanan = new ModelPemesanan(
        noPemesanan, tglPemesanan, null, null, totalPemesanan,
        bayar, kembalian, jenisPembayaran, modelSupplier, modelPengguna);
        
        servicePemesanan.addData(parent, modelPemesanan);
        
    //        Tambah Detail
        List<String> kodeBrg = new ArrayList<>();
        List<Integer> hargaBeli = new ArrayList<>();
        List<Integer> jumlah = new ArrayList<>();
        List<Integer> subtotal = new ArrayList<>();
        detail.setModelPemesanan(modelPemesanan);
        for(int a = 0; a < tableDetail.getRowCount(); a++) {
            kodeBrg.add((String) tableDetail.getValueAt(a, 0));
            hargaBeli.add((Integer)tableDetail.getValueAt(a, 3));
            jumlah.add((Integer) tableDetail.getValueAt(a, 5));
            subtotal.add((Integer)tableDetail.getValueAt(a, 6));
            Sementara ps = new Sementara(kodeBrg, hargaBeli,jumlah, subtotal);
            serviceDetail.addData(detail, ps);
        }
    }
    
//    Tambah Data Sementara
    private void tambahDataSementara() {
        String kodeBrg = lbKodeBrg.getText();
        String namaBrg = lbNamaBrg.getText();
        String satuan = lbSatuan.getText();
        int hargaSkrg = Integer.valueOf(txtHrgBeliSkrg.getText());
        int hargaSblm = Integer.valueOf(lbHrgBeliSblm.getText());
        int jumlah = (int) spnJumlah.getValue();
        int subtotal = Integer.valueOf(lbSubtotal.getText());
        tabmodel2.addRow(new Object[]{kodeBrg, namaBrg, hargaSblm, hargaSkrg, satuan, jumlah, subtotal});
        DecimalFormat df = new DecimalFormat("#,##0.##");
        
        lbTotal.setText(df.format(total()));
    }
       
    private int total() {
        int total = 0;
        for(int a = 0; a < tableDetail.getRowCount(); a++) {
            int subtotal = (int) tableDetail.getValueAt(a, 6);
            total += subtotal;
        }
        return total;
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
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 2, 3));
                    pagination.setVisible(false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtCari.getText();
                if(text.length() == 0) {
                    rowSorter.setRowFilter(null);
                    pagination.setVisible(true);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 2, 3));
                    pagination.setVisible(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
    }
    
    private boolean validationAddTemporary() {
        boolean valid = true;
        int jumlah = (int) spnJumlah.getValue();
        int rowCount = tableDetail.getRowCount();
        try {
            if(lbKodeBrg.getText().trim().length() == 0 ) {
                valid = false;
                JOptionPane.showMessageDialog(parent, "Silahkan Pilih Barang");
            } else if(txtHrgBeliSkrg.getText().trim().length() == 0) {
                valid = false;
                JOptionPane.showMessageDialog(parent, "Silahkan Masukkan Harga Beli");
            } else if(jumlah <= 0) {
                valid = false;
                JOptionPane.showMessageDialog(parent, "Silahkan Masukkan Jumlah Pemesanan");
            } else {
             for(int a = 0; a < rowCount; a++) {
                String kodeBrgInTable = (String) tableDetail.getValueAt(a, 0);
                String kodeBrg = lbKodeBrg.getText();            
                    if(kodeBrg.equals(kodeBrgInTable)) {
                        valid = false;
                        JOptionPane.showMessageDialog(parent, "Barang ini sudah ditambahkan");
                        break;
                    } else {
                        valid = true;
                    }
                }            
            }
        } catch(NullPointerException ex) {
            valid = false;
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Barang");
        }
        
        return valid;
    }
    
    private boolean validation() {
        boolean valid = false;
        try {
            if(lbIdSupplier.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(parent, "Silahkan Pilih Supplier");
            } else if(tableDetail.getRowCount() == 0) {
                JOptionPane.showMessageDialog(parent, "Silahkan Pilih Barang");       
            } else if(txtBayar.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(parent, "Silahkan masukkan jumlah pembayaran");
            } else if(Double.parseDouble(txtBayar.getText()) < total()) {
                JOptionPane.showMessageDialog(parent, "Jumlah Pembayaran Kurang dari Total Pemesanan");    
            } else {
                valid = true;
            }
        } catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Supplier");    
        }
        
        return valid;
    }

    private boolean validationUpdatePrice() {
        boolean valid = false;
        for(int a = 0; a < tableDetail.getRowCount(); a++) {
            int hrgSblm = (int) tableDetail.getValueAt(a, 2);
            int hrgSkrg = (int) tableDetail.getValueAt(a, 3);
            if(hrgSblm != hrgSkrg) {
                valid = true;
                break;
            }
        }
        return valid;
    }
    
    private void perbaruiHargaBeli() {
        for(int a = 0; a < tableDetail.getRowCount(); a++) {
            String kodeBrg = tableDetail.getValueAt(a, 0).toString();
            String namaBrg = tableDetail.getValueAt(a, 1).toString();
            int hrgSblm = (int) tableDetail.getValueAt(a, 2);
            int hrgSkrg = (int) tableDetail.getValueAt(a, 3);
            if(hrgSblm != hrgSkrg) {
                ModelBarang modelBarang = new ModelBarang();
                modelBarang.setKode_Barang(kodeBrg);
                modelBarang.setHarga_Beli(hrgSkrg);
                servicePemesanan.updatePriceBuy(modelBarang);
                cekPerubahanHarga(kodeBrg, namaBrg, hrgSblm, hrgSkrg); 
            }
        }
        
    }
    
    private void cekPerubahanHarga(String kodeBarang, String namaBarang, int hrgBeliSebelum, int hargaBeli) {
        String idNotifikasi = String.valueOf(new Random().nextInt(10000));
        StringBuilder deskripsi = new StringBuilder();
        tambahPerubahanHargaBeli(idNotifikasi, kodeBarang, deskripsi, namaBarang, hrgBeliSebelum, hargaBeli);        
    }
    
     private void tambahPerubahanHargaBeli(String idNotifikasi, String kodeBarang, StringBuilder deskripsi, 
            String namaBarang, int hrgBeliSebelum, int hargaBeli) {
        
        String namaNotifikasi1 = "Perubahan Harga Beli Barang";
         deskripsi.append("Harga Beli " + namaBarang + " berhasil dirubah dari "+hrgBeliSebelum+" menjadi " + hargaBeli);
         ModelNotifikasi modelNotifikasi1 = new ModelNotifikasi(idNotifikasi, namaNotifikasi1.toString(), 
             deskripsi.toString(), kodeBarang, false);
         serviceNotifikasi.addNotification(modelNotifikasi1);
    }
    
    private void clearAllField() {
        lbIdSupplier.setText(null);
        lbNamaSupplier.setText(null);
        lbKodeBrg.setText(null);
        lbNamaBrg.setText(null);
        txtHrgBeliSkrg.setText(null);
        spnJumlah.setValue((int) 0);
        lbSubtotal.setText(null);
        lbTotal.setText("0");
        txtBayar.setText("");
        lbKembalian.setText("0");
        tabmodel2.setRowCount(0);
    }
    
    private void clearFieldBrg() {
        lbKodeBrg.setText(null);
        lbNamaBrg.setText(null);
        lbSatuan.setText(null);
        txtHrgBeliSkrg.setText(null);
        lbHrgBeliSblm.setText(null);
        spnJumlah.setValue((int) 0);
        lbSubtotal.setText(null);
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
        btnTambah = new swing.Button();
        label = new javax.swing.JLabel();
        pagination = new swing.Pagination();
        txtCari = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        table = new swing.Table();
        panelTambah = new javax.swing.JPanel();
        panel3 = new javax.swing.JPanel();
        scrollPanePasien = new javax.swing.JScrollPane();
        tableDetail = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        cbx_jenisPembayaran = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        lbKembalian = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        btnTambahSementara = new swing.Button();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnPilihSuplr = new swing.Button();
        lbNoPemesanan = new javax.swing.JLabel();
        lbTgl = new javax.swing.JLabel();
        lbIdSupplier = new javax.swing.JLabel();
        lbNamaSupplier = new javax.swing.JLabel();
        lbNamaBrg = new javax.swing.JLabel();
        spnJumlah = new javax.swing.JSpinner();
        jLabel25 = new javax.swing.JLabel();
        lbSubtotal = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        btnPilihBrg = new swing.Button();
        txtHrgBeliSkrg = new javax.swing.JTextField();
        lbKodeBrg = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbHrgBeliSblm = new javax.swing.JLabel();
        lbSatuan = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        btnBatal = new swing.Button();
        btnSimpan = new swing.Button();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        panelData.setBackground(new java.awt.Color(153, 153, 153));
        panelData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

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
        label.setText("PEMESANAN");

        pagination.setBackground(new java.awt.Color(135, 15, 50));
        pagination.setForeground(new java.awt.Color(255, 255, 255));
        pagination.setOpaque(false);

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(185, 185, 185));
        txtCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari.setText("Cari Berdasarkan No Pemesanan, Supplier atau Tanggal");
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

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Pemesanan", "ID Supplier", "Supplier", "Tanggal Pemesanan", "Total", "Bayar", "Kembali", "Jenis Pembayaran", "Status", "", "ID Pengguna", "Pengguna", "        Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setSelectionBackground(new java.awt.Color(245, 245, 245));
        table.setSelectionForeground(new java.awt.Color(0, 0, 0));
        scrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(1).setMinWidth(0);
            table.getColumnModel().getColumn(1).setPreferredWidth(0);
            table.getColumnModel().getColumn(1).setMaxWidth(0);
            table.getColumnModel().getColumn(5).setMinWidth(0);
            table.getColumnModel().getColumn(5).setPreferredWidth(0);
            table.getColumnModel().getColumn(5).setMaxWidth(0);
            table.getColumnModel().getColumn(6).setMinWidth(0);
            table.getColumnModel().getColumn(6).setPreferredWidth(0);
            table.getColumnModel().getColumn(6).setMaxWidth(0);
            table.getColumnModel().getColumn(7).setMinWidth(0);
            table.getColumnModel().getColumn(7).setPreferredWidth(0);
            table.getColumnModel().getColumn(7).setMaxWidth(0);
            table.getColumnModel().getColumn(8).setMinWidth(100);
            table.getColumnModel().getColumn(8).setPreferredWidth(100);
            table.getColumnModel().getColumn(8).setMaxWidth(100);
            table.getColumnModel().getColumn(9).setMinWidth(0);
            table.getColumnModel().getColumn(9).setPreferredWidth(0);
            table.getColumnModel().getColumn(9).setMaxWidth(0);
            table.getColumnModel().getColumn(10).setMinWidth(0);
            table.getColumnModel().getColumn(10).setPreferredWidth(0);
            table.getColumnModel().getColumn(10).setMaxWidth(0);
            table.getColumnModel().getColumn(11).setMinWidth(0);
            table.getColumnModel().getColumn(11).setPreferredWidth(0);
            table.getColumnModel().getColumn(11).setMaxWidth(0);
            table.getColumnModel().getColumn(12).setMinWidth(100);
            table.getColumnModel().getColumn(12).setPreferredWidth(100);
            table.getColumnModel().getColumn(12).setMaxWidth(100);
        }

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 698, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPane))
                .addContainerGap())
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label)
                    .addContainerGap(980, Short.MAX_VALUE)))
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
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label)
                    .addContainerGap(843, Short.MAX_VALUE)))
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
                "Kode Barang", "Nama Barang", "Harga Beli Sebelum", "Harga Beli Sekarang", "Satuan", "Jumlah", "Subtotal", "         Aksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
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
            tableDetail.getColumnModel().getColumn(1).setMinWidth(300);
            tableDetail.getColumnModel().getColumn(1).setPreferredWidth(300);
            tableDetail.getColumnModel().getColumn(1).setMaxWidth(300);
            tableDetail.getColumnModel().getColumn(2).setMinWidth(150);
            tableDetail.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableDetail.getColumnModel().getColumn(2).setMaxWidth(150);
            tableDetail.getColumnModel().getColumn(3).setMinWidth(150);
            tableDetail.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableDetail.getColumnModel().getColumn(3).setMaxWidth(150);
            tableDetail.getColumnModel().getColumn(4).setMinWidth(75);
            tableDetail.getColumnModel().getColumn(4).setPreferredWidth(75);
            tableDetail.getColumnModel().getColumn(4).setMaxWidth(75);
            tableDetail.getColumnModel().getColumn(7).setMinWidth(100);
            tableDetail.getColumnModel().getColumn(7).setPreferredWidth(100);
            tableDetail.getColumnModel().getColumn(7).setMaxWidth(100);
        }

        jPanel2.setBackground(new java.awt.Color(135, 15, 50));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("TOTAL : ");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Rp");

        lbTotal.setBackground(new java.awt.Color(255, 255, 255));
        lbTotal.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(255, 255, 255));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTotal.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("BAYAR");

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("KEMBALI");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Rp");

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Rp");

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

        cbx_jenisPembayaran.setBackground(new java.awt.Color(255, 255, 255));
        cbx_jenisPembayaran.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        cbx_jenisPembayaran.setForeground(new java.awt.Color(0, 0, 0));
        cbx_jenisPembayaran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tunai", "Non Tunai" }));
        cbx_jenisPembayaran.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        jPanel6.setOpaque(false);

        lbKembalian.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lbKembalian.setForeground(new java.awt.Color(0, 0, 0));
        lbKembalian.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(lbKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbKembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPanePasien, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBayar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbx_jenisPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_jenisPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanePasien, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jLabel1.setText("Tanggal");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("No Pemesanan");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Nama Barang");

        jLabel10.setBackground(new java.awt.Color(134, 15, 50));
        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Pilih Supplier");
        jLabel10.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(134, 15, 50));
        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Pilih Barang");
        jLabel11.setOpaque(true);

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("ID Supplier");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Nama Supplier");

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Satuan");

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Harga Beli");

        btnPilihSuplr.setBackground(new java.awt.Color(135, 15, 50));
        btnPilihSuplr.setForeground(new java.awt.Color(255, 255, 255));
        btnPilihSuplr.setText("PILIH");
        btnPilihSuplr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihSuplrActionPerformed(evt);
            }
        });

        lbNoPemesanan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNoPemesanan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbTgl.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbTgl.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbIdSupplier.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbIdSupplier.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNamaSupplier.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaSupplier.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbNamaBrg.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbNamaBrg.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        spnJumlah.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        spnJumlah.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        spnJumlah.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnJumlahStateChanged(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Subtotal");

        lbSubtotal.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbSubtotal.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        jLabel27.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Jumlah");

        btnPilihBrg.setBackground(new java.awt.Color(135, 15, 50));
        btnPilihBrg.setForeground(new java.awt.Color(255, 255, 255));
        btnPilihBrg.setText("PILIH");
        btnPilihBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihBrgActionPerformed(evt);
            }
        });

        txtHrgBeliSkrg.setBackground(new java.awt.Color(255, 255, 255));
        txtHrgBeliSkrg.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtHrgBeliSkrg.setForeground(new java.awt.Color(0, 0, 0));
        txtHrgBeliSkrg.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        txtHrgBeliSkrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHrgBeliSkrgKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHrgBeliSkrgKeyTyped(evt);
            }
        });

        lbKodeBrg.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbKodeBrg.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Kode Barang");

        lbHrgBeliSblm.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbHrgBeliSblm.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        lbSatuan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lbSatuan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNoPemesanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(lbIdSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPilihSuplr, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbNamaSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTambahSementara, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(spnJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtHrgBeliSkrg)))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lbSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(lbKodeBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPilihBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lbNamaBrg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lbHrgBeliSblm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbNoPemesanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbTgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPilihSuplr, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(lbIdSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbNamaSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnPilihBrg, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(lbKodeBrg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNamaBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtHrgBeliSkrg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnTambahSementara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbHrgBeliSblm, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        label1.setBackground(new java.awt.Color(135, 15, 50));
        label1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Tambah Pemesanan");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(392, Short.MAX_VALUE)
                .addComponent(label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 392, Short.MAX_VALUE)
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
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTambahLayout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(panelTambah, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        actionTableDetail();
        clearAllField();
        changePanel(panelTambah);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy - MM - dd");
        String dateNow = sdf.format(date);
        lbNoPemesanan.setText(servicePemesanan.createNo());
        lbTgl.setText(dateNow);
        lbSatuan.setText("");
        lbHrgBeliSblm.setVisible(false);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        txtCari.setText(null);
        txtCari.setForeground(new Color(0,0,0));
        txtCari.setFont(new Font("sansserif",0,14));
        pagination.setVisible(false);
        tabmodel1.setRowCount(0);
        servicePemesanan.loadAll(tabmodel1);
    }//GEN-LAST:event_txtCariFocusGained

    private void btnTambahSementaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSementaraActionPerformed
        if(validationAddTemporary()) {
            tambahDataSementara();
            clearFieldBrg();
            String bayar = txtBayar.getText();
            double kembalian = 0;
            if(bayar.length() > 0) {
                kembalian = Double.parseDouble(bayar) - total();
            }
            DecimalFormat df = new DecimalFormat("#,##0.##");
            lbKembalian.setText(df.format(kembalian));
        }
    }//GEN-LAST:event_btnTambahSementaraActionPerformed

    private void btnPilihSuplrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihSuplrActionPerformed
        TambahPemesanan pilihSupplier = new TambahPemesanan(parent, true, "Slide-1");
        pilihSupplier.setVisible(true);
        lbIdSupplier.setText(pilihSupplier.modelSupplier.getIdSupplier());
        lbNamaSupplier.setText(pilihSupplier.modelSupplier.getNamaSupplier());
    }//GEN-LAST:event_btnPilihSuplrActionPerformed

    private void btnPilihBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihBrgActionPerformed
        TambahPemesanan pilihBarang = new TambahPemesanan(parent, true, "Slide-2");
        pilihBarang.setVisible(true);
        lbKodeBrg.setText(pilihBarang.modelBarang.getKode_Barang());
        lbNamaBrg.setText(pilihBarang.modelBarang.getNama_Barang());
        lbSatuan.setText(pilihBarang.modelBarang.getSatuan());
        txtHrgBeliSkrg.setText(String.valueOf(pilihBarang.modelBarang.getHarga_Beli()));
        lbHrgBeliSblm.setText(String.valueOf(pilihBarang.modelBarang.getHarga_Beli()));
        spnJumlah.setValue((int) 1);
        int jumlah = (int) spnJumlah.getValue();
        int hargaBeli = Integer.valueOf(txtHrgBeliSkrg.getText());
        int subtotal = hargaBeli * jumlah;
        lbSubtotal.setText(String.valueOf(subtotal));
    }//GEN-LAST:event_btnPilihBrgActionPerformed

    private void txtBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyReleased
        String strBayar = txtBayar.getText();
        double total = (double) total();
        double bayar = 0;
        double kembalian = 0;
        if(strBayar.length() > 0) {
            bayar = Double.parseDouble(strBayar);
        }
        kembalian = bayar - total;
        DecimalFormat df = new DecimalFormat("#,##0");
        lbKembalian.setText(df.format(kembalian));
    }//GEN-LAST:event_txtBayarKeyReleased

    private void txtBayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_txtBayarKeyTyped

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(validation()) {
            if(validationUpdatePrice()) {
                JOptionPane.showMessageDialog(parent, "Harga beli beberapa barang telah dirubah", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                perbaruiHargaBeli();
                btnNotif.setText(serviceNotifikasi.getCountNotification() + "");
            }
                tambahData();
                clearAllField();
                tabmodel1.setRowCount(0);
                changePanel(panelData);
                tampilData();    
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        if(tableDetail.getRowCount() != 0) {
            int confirm = JOptionPane.showConfirmDialog(parent, "Data yang telah diinput akan dihapus", "Konfirmasi", JOptionPane.OK_OPTION);
            if(confirm == 0) {
            clearAllField();
            changePanel(panelData);
            }   
        } else {
            clearAllField();
            changePanel(panelData); 
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    private void spnJumlahStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnJumlahStateChanged
        int jumlah = (int) spnJumlah.getValue();
        String hargaBeli = txtHrgBeliSkrg.getText();
        int subtotal;
        if(hargaBeli.length() != 0) {
            if(jumlah > 0) {
                subtotal = Integer.valueOf(hargaBeli) * jumlah;    
            } else if(jumlah == 0) {
                subtotal = Integer.valueOf(hargaBeli) + jumlah;         
            } else {
                subtotal = Integer.valueOf(hargaBeli) + 0;             
            }
        } else {
            subtotal = 0;
        }
        lbSubtotal.setText(String.valueOf(subtotal));
    }//GEN-LAST:event_spnJumlahStateChanged

    private void txtHrgBeliSkrgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHrgBeliSkrgKeyReleased
        int jumlah = (int) spnJumlah.getValue();
        String hargaBeli = txtHrgBeliSkrg.getText();
        int subtotal;
        try {
            if(jumlah > 0) {
                subtotal = Integer.valueOf(hargaBeli) * jumlah;
            } else if(jumlah == 0) {
                subtotal = Integer.valueOf(hargaBeli) + jumlah;
            } else {
                subtotal = Integer.valueOf(hargaBeli) + 0;
            }   
        } catch(NumberFormatException ex) {
           subtotal = 0; 
        }
        lbSubtotal.setText(String.valueOf(subtotal));
    }//GEN-LAST:event_txtHrgBeliSkrgKeyReleased

    private void txtHrgBeliSkrgKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHrgBeliSkrgKeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_txtHrgBeliSkrgKeyTyped

    private void txtCariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusLost
        pagination.setVisible(true);
    }//GEN-LAST:event_txtCariFocusLost

    private void changePanel(JPanel panel) {
        removeAll();
        add(panel);
        repaint();
        revalidate();
    }
    
    private void characterDigit(KeyEvent evt) {
        char typed = evt.getKeyChar();
        if(!Character.isDigit(typed)) {
            evt.consume();
        }
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnBatal;
    private swing.Button btnPilihBrg;
    private swing.Button btnPilihSuplr;
    private swing.Button btnSimpan;
    private swing.Button btnTambah;
    private swing.Button btnTambahSementara;
    private javax.swing.JComboBox<String> cbx_jenisPembayaran;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
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
    private javax.swing.JLabel lbHrgBeliSblm;
    private javax.swing.JLabel lbIdSupplier;
    private javax.swing.JLabel lbKembalian;
    private javax.swing.JLabel lbKodeBrg;
    private javax.swing.JLabel lbNamaBrg;
    private javax.swing.JLabel lbNamaSupplier;
    private javax.swing.JLabel lbNoPemesanan;
    private javax.swing.JLabel lbSatuan;
    private javax.swing.JLabel lbSubtotal;
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
    private javax.swing.JSpinner spnJumlah;
    private swing.Table table;
    private javax.swing.JTable tableDetail;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHrgBeliSkrg;
    // End of variables declaration//GEN-END:variables
}
