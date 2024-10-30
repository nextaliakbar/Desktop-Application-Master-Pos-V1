/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;

import action.TableAction;
import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import control.Report;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import model.ModelDetailPemeriksaan;
import model.ModelDetailPemesanan;
import model.ModelDetailPengeluaran;
import model.ModelDetailPenjualan;
import util.ModelHeaderTable;
import model.ModelKaryawan;
import model.ModelPasien;
import model.ModelPemeriksaan;
import model.ModelPemesanan;
import model.ModelPengeluaran;
import model.ModelPengguna;
import model.ModelPenjualan;
import util.ModelRenderTable;
import model.ModelReservasi;
import model.ModelSupplier;
import net.sf.jasperreports.engine.JRException;
import service.ServiceLaporan;
import swing.TableCellActionRender;
import swing.TableCellEditor;

/**
 *
 * @author usER
 */
public class FiturLaporan extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private DefaultTableModel tabmodel;
    private DefaultTableModel model;
    private TableAction action;
    private DateChooser dateChooser;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private TableRowSorter<DefaultTableModel> sorter;
    private final LocalDate dateNow = LocalDate.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
    private final DecimalFormat df = new DecimalFormat("#,##0.##");
    private ServiceLaporan serviceLaporan = new ServiceLaporan();
    private JFrame parent;
    public FiturLaporan(JFrame parent) {
        initComponents();
        this.parent = parent;
        initation();
        txtTgl.setText(dateNow.format(formatter));
        styleTable(scrollPemeriksaan, tablePemeriksaan, 14);
        styleTable(scrollPenjualan, tablePenjualan, 9);
        styleTable(scrollPengeluaran, tablePengeluaran, 7);
        tablePemesanan.scrollPane(scrollPemesanan);
        tablePemesanan.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        actionRenderTable(tablePemeriksaan, 14);
        actionRenderTable(tablePenjualan, 9);
        actionRenderTable(tablePemesanan, 13);
        actionRenderTable(tablePengeluaran, 7);
        model = (DefaultTableModel) tablePengeluaran.getModel();
        sorter = new TableRowSorter<>(model);
        tablePengeluaran.setRowSorter(sorter);
        cariData();
    }
    
    private void instanceReport(String slide) {
        try {
            Report.getInstance().compileReport(slide);
        } catch(JRException ex) {
            ex.printStackTrace();
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
    
    //  Update,Delete,Detail
    private void actionRenderTable(JTable table, int columnIndex) {
        action = new TableAction() {
        @Override
        public void edit(int row) {
            
        }

        @Override
        public void delete(int row) {
            if(table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
        }

        @Override
        public void view(int row) {
            int index = cbxJenisLaporan.getSelectedIndex();
            switch(index) {
                case 0:
                    detailPemeriksaan(row);
                    break;
                case 1:
                    detailPenjualan(row);
                    break;
                case 2:
                    detailPemesanan(row);
                    break;
                case 3:
                    detailPengeluaran(row);
                    break;
            }
        }
    };        
        table.getColumnModel().getColumn(columnIndex).setCellRenderer(new TableCellActionRender(false, false, true));
        table.getColumnModel().getColumn(columnIndex).setCellEditor(new TableCellEditor(action, false, false, true));
    }
    
    //    Detail Pemeriksaan
    private void detailPemeriksaan(int row) {
        ModelPemeriksaan modelPemeriksaan = new ModelPemeriksaan();
        ModelPasien modelPasien = new ModelPasien();
        ModelKaryawan modelKaryawan = new ModelKaryawan();
        ModelReservasi modelReservasi = new ModelReservasi();
        ModelPengguna modelPengguna = new ModelPengguna();
        
        modelPemeriksaan.setNoPemeriksaan((String) tablePemeriksaan.getValueAt(row, 1));
        modelReservasi.setNoReservasi((String) tablePemeriksaan.getValueAt(row, 2));
        modelPemeriksaan.setModelReservasi(modelReservasi);
        modelPasien.setIdPasien((String) tablePemeriksaan.getValueAt(row, 3));
        modelPasien.setNama((String) tablePemeriksaan.getValueAt(row, 4));
        modelPemeriksaan.setModelPasien(modelPasien);
        modelKaryawan.setIdKaryawan((String) tablePemeriksaan.getValueAt(row, 5));
        modelPemeriksaan.setModelKaryawan(modelKaryawan);
        modelPemeriksaan.setTglPemeriksaan((String) tablePemeriksaan.getValueAt(row, 6));
        modelPemeriksaan.setTotal((String) tablePemeriksaan.getValueAt(row, 7));
        modelPemeriksaan.setDeskripsi((String) tablePemeriksaan.getValueAt(row, 8));
        modelPemeriksaan.setBayar((double) tablePemeriksaan.getValueAt(row, 9));
        modelPemeriksaan.setKembali((double) tablePemeriksaan.getValueAt(row, 10));
        modelPemeriksaan.setJenisPembayaran((String) tablePemeriksaan.getValueAt(row, 11));
        modelPengguna.setIdpengguna((String) tablePemeriksaan.getValueAt(row, 12));
        modelPengguna.setNama((String) tablePemeriksaan.getValueAt(row, 13));
        modelPemeriksaan.setModelPengguna(modelPengguna);
        
        ModelDetailPemeriksaan modelDetail = new ModelDetailPemeriksaan();
        modelDetail.setModelPemeriksaan(modelPemeriksaan);

        DialogDetail dialog = new DialogDetail(parent, true, "Slide-1", modelDetail, null, null, null);
        dialog.setVisible(true);
    }
    
    //  Detail Penjualan
    private void detailPenjualan(int row) {
        ModelPengguna modelPengguna = new ModelPengguna();
        String noPenjualan = (String) tablePenjualan.getValueAt(row, 1);
        String idPengguna = (String) tablePenjualan.getValueAt(row, 2);
        String namaPengguna = (String) tablePenjualan.getValueAt(row, 3);
        modelPengguna.setIdpengguna(idPengguna);
        modelPengguna.setNama(namaPengguna);
        String tglPenjualan = (String) tablePenjualan.getValueAt(row, 4);
        String totalPenjualan = (String) tablePenjualan.getValueAt(row, 5);
        double bayar = (double) tablePenjualan.getValueAt(row, 6);
        double kembali = (double) tablePenjualan.getValueAt(row, 7);
        String jenisPembayaran = (String) tablePenjualan.getValueAt(row, 8);
        ModelPenjualan modelPenjualan = new ModelPenjualan(noPenjualan, tglPenjualan, totalPenjualan, bayar, kembali, jenisPembayaran, modelPengguna);
        ModelDetailPenjualan modelDetail = new ModelDetailPenjualan();
        modelDetail.setModelPenjualan(modelPenjualan);
        DialogDetail detail = new DialogDetail(parent, true, "Slide-6", null, modelDetail, null, null);
        detail.setVisible(true);
    }
    
    //    Detail Pemesanan
    private void detailPemesanan(int row) {
        ModelDetailPemesanan modelDetail = new ModelDetailPemesanan();
        ModelPemesanan modelPemesanan = new ModelPemesanan();
        ModelSupplier modelSupplier = new ModelSupplier();
        ModelPengguna modelPengguna = new ModelPengguna();

        modelPemesanan.setNoPemesanan((String) tablePemesanan.getValueAt(row, 1));
        modelSupplier.setIdSupplier((String) tablePemesanan.getValueAt(row, 2));
        modelSupplier.setNamaSupplier((String) tablePemesanan.getValueAt(row, 3));
        modelPemesanan.setModelSupplier(modelSupplier);
        modelPemesanan.setTglPemesanan((String) tablePemesanan.getValueAt(row, 4));
        modelPemesanan.setTotalPemesanan((String) tablePemesanan.getValueAt(row, 5));
        modelPemesanan.setBayar((double) tablePemesanan.getValueAt(row, 6));
        modelPemesanan.setKembali((double) tablePemesanan.getValueAt(row, 7));
        modelPemesanan.setJenisPembayaran((String) tablePemesanan.getValueAt(row, 8));
        modelPemesanan.setStatusPemesanan((String) tablePemesanan.getValueAt(row, 10));
        modelPengguna.setIdpengguna((String) tablePemesanan.getValueAt(row, 11));
        modelPengguna.setNama((String) tablePemesanan.getValueAt(row, 12));
        modelPemesanan.setModelPengguna(modelPengguna);
        modelDetail.setModelPemesanan(modelPemesanan);

        DialogDetail dialog = new DialogDetail(parent, true, "Slide-5", null, null, modelDetail, null);
        dialog.setVisible(true);
    }
    
    //    Detail Pengeluaran
    private void detailPengeluaran(int row) {
        ModelDetailPengeluaran detailPengeluaran = new ModelDetailPengeluaran();
        ModelPengeluaran pengeluaran = new ModelPengeluaran();
        ModelPengguna modelPengguna = new ModelPengguna();
        pengeluaran.setNoPengeluaran((String) tablePengeluaran.getValueAt(row, 1));
        modelPengguna.setIdpengguna((String) tablePengeluaran.getValueAt(row, 2));
        modelPengguna.setNama((String) tablePengeluaran.getValueAt(row, 3));
        pengeluaran.setModelPengguna(modelPengguna);
        pengeluaran.setTglPengeluaran((String) tablePengeluaran.getValueAt(row, 4));
        pengeluaran.setTotal((String) tablePengeluaran.getValueAt(row, 5));
        pengeluaran.setDeskripsi((String) tablePengeluaran.getValueAt(row, 6));
        detailPengeluaran.setModelPengeluaran(pengeluaran);
        DialogDetail dialog = new DialogDetail(parent, true, "Slide-3", null, null, null,detailPengeluaran);
        dialog.setVisible(true);
    }
    
    private void initiationSearch() {
        String text = txtCari.getText();
            if(text.length() == 0) {
                rowSorter.setRowFilter(null);
            } else if(text.equals("Cari Berdasarkan No Pemeriksaan atau Nama Pasien")) {
                rowSorter.setRowFilter(null);
            } else if(text.equals("Cari Berdasarkan No Penjualan atau Kasir")){
                rowSorter.setRowFilter(null);                 
            } else if(text.equals("Cari Berdasarkan No Pemesanan atau Nama Supplier")) {
                rowSorter.setRowFilter(null);
            } else if(text.equals("Cari Berdasarkan No Pengeluaran atau Pengguna")) {
                sorter.setRowFilter(null);
            } else {
                int orders = cbxJenisLaporan.getSelectedIndex();
                switch(orders) {
                  case 0:
                      rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1 ,4));   
                      break;
                  case 1:
                      rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1, 3));
                      break;
                  case 2:
                      rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1, 3));   
                      break;
                  case 3:
                      sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1, 4));
                      break;
              }
        }
    }
            
    private void cariData() {
        txtCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                initiationSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                initiationSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              
            }
        });
    }
    
    private void changePanelTable(JPanel panel) {
        panel3.removeAll();
        panel3.add(panel);
        panel3.repaint();
        panel3.revalidate();
    }
    
    private void initation() {
        cbxJenisLaporan.setBackground(new Color(255,255,255));
        cbxJenisLaporan.setForeground(new Color(0,0,0));
        cbxJenisLaporan.setFont(new Font("sansserif",0,20));
        String[] types = new String[]{"Laporan Pemeriksaan","Laporan Penjualan","Laporan Pemesanan","Laporan Pengeluaran"};        
        for(String type : types) {
            cbxJenisLaporan.addItem(type);
        }        
        dateChooser = new DateChooser();
        dateChooser.setTextField(txtTgl);
        dateChooser.setBetweenCharacter(" Sampai ");
        dateChooser.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        dateChooser.setLabelCurrentDayVisible(false);
        dateChooser.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        dateChooser.setSelectedDate(new Date());
        dateChooser.addActionDateChooserListener(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fromDate = sdf.format(date.getFromDate());
                String toDate = sdf.format(date.getToDate());
                tabmodel.setRowCount(0);
                model.setRowCount(0);
                int index = cbxJenisLaporan.getSelectedIndex();
                switch(index) {
                    case 0:
                        serviceLaporan.loadBetween(fromDate, toDate, tabmodel, "Pemeriksaan");
                        lbTotal.setText(total(tablePemeriksaan, 7));
                        break;
                    case 1:
                        serviceLaporan.loadBetween(fromDate, toDate, tabmodel, "Penjualan");
                        lbTotal.setText(total(tablePenjualan, 5));
                        break;
                    case 2:
                        serviceLaporan.loadBetween(fromDate, toDate, tabmodel, "Pemesanan");
                        lbTotal.setText(total(tablePemesanan, 5));
                        break;
                    case 3:
                        serviceLaporan.loadBetween(fromDate, toDate, model,"Pengeluaran");
                        lbTotal.setText(total(tablePengeluaran, 5));
                        break;
                }
            }
           
        });
    }
    
    private String total(JTable table, int columnIndex) {
        int total = 0;
        int subtotal = 0;
        if(table.getRowCount() != 0) {
            for(int a = 0; a < table.getRowCount(); a++) {
                try {
                    Number number = df.parse(table.getValueAt(a, columnIndex).toString());
                    subtotal = number.intValue();
                } catch(ParseException ex) {
                    ex.printStackTrace();
                }
                total += subtotal;
            }   
        }
        DecimalFormat df = new DecimalFormat("#,##0.##");
        return df.format(total);
    }
    
    private void changeType() {
       int index = cbxJenisLaporan.getSelectedIndex();
       switch(index) {
           case 0:
               changePanelTable(panelPemeriksaan);
               instanceReport("Pemeriksaan");
               tabmodel = (DefaultTableModel) tablePemeriksaan.getModel();
               tabmodel.setRowCount(0);
               serviceLaporan.loadAll(tabmodel, "Pemeriksaan");
               lbTotal.setText(total(tablePemeriksaan, 7));
               txtTgl.setText(dateNow.format(formatter));
               rowSorter = new TableRowSorter<>(tabmodel);
               tablePemeriksaan.setRowSorter(rowSorter);
               txtCari.setForeground(new Color(185,185,185));
               txtCari.setFont(new Font("sansserif",Font.ITALIC,14));
               txtCari.setText("Cari Berdasarkan No Pemeriksaan atau Nama Pasien");
               break;
           case 1:
               changePanelTable(panelPenjualan);
               instanceReport("Penjualan");
               tabmodel = (DefaultTableModel) tablePenjualan.getModel();
               tabmodel.setRowCount(0);
               serviceLaporan.loadAll(tabmodel, "Penjualan");
               lbTotal.setText(total(tablePenjualan, 5));
               txtTgl.setText(dateNow.format(formatter));
               rowSorter = new TableRowSorter<>(tabmodel);
               tablePenjualan.setRowSorter(rowSorter);
               txtCari.setForeground(new Color(185,185,185));
               txtCari.setFont(new Font("sansserif",Font.ITALIC,14));
               txtCari.setText("Cari Berdasarkan No Penjualan atau Kasir");
               break;
           case 2:
               changePanelTable(panelPemesanan);
               instanceReport("Pemesanan");
               tabmodel = (DefaultTableModel) tablePemesanan.getModel();
               tabmodel.setRowCount(0);
               serviceLaporan.loadAll(tabmodel, "Pemesanan");
               txtTgl.setText(dateNow.format(formatter));
               lbTotal.setText(total(tablePemesanan, 5));
               rowSorter = new TableRowSorter<>(tabmodel);
               tablePemesanan.setRowSorter(rowSorter);
               txtCari.setForeground(new Color(185,185,185));
               txtCari.setFont(new Font("sansserif",Font.ITALIC,14));
               txtCari.setText("Cari Berdasarkan No Pemesanan atau Nama Supplier");
               break;
           case 3:
               changePanelTable(panelPengeluaran);
               instanceReport("Pengeluaran");
               model.setRowCount(0);
               serviceLaporan.loadAll(model, "Pengeluaran");
               txtTgl.setText(dateNow.format(formatter));
               lbTotal.setText(total(tablePengeluaran, 5));
               txtCari.setForeground(new Color(185,185,185));
               txtCari.setFont(new Font("sansserif",Font.ITALIC,14));
               txtCari.setText("Cari Berdasarkan No Pengeluaran atau Pengguna");
               break;
       }
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
        mainPanel = new javax.swing.JPanel();
        label = new javax.swing.JLabel();
        panel1 = new javax.swing.JPanel();
        cbxJenisLaporan = new javax.swing.JComboBox<>();
        txtTgl = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        lbRp = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        panel3 = new javax.swing.JPanel();
        panelPemeriksaan = new javax.swing.JPanel();
        scrollPemeriksaan = new javax.swing.JScrollPane();
        tablePemeriksaan = new javax.swing.JTable();
        panelPenjualan = new javax.swing.JPanel();
        scrollPenjualan = new javax.swing.JScrollPane();
        tablePenjualan = new javax.swing.JTable();
        panelPemesanan = new javax.swing.JPanel();
        scrollPemesanan = new javax.swing.JScrollPane();
        tablePemesanan = new swing.Table();
        panelPengeluaran = new javax.swing.JPanel();
        scrollPengeluaran = new javax.swing.JScrollPane();
        tablePengeluaran = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        panelData.setBackground(new java.awt.Color(153, 153, 153));
        panelData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        mainPanel.setBackground(new java.awt.Color(185, 185, 185));
        mainPanel.setForeground(new java.awt.Color(255, 255, 255));

        label.setBackground(new java.awt.Color(135, 15, 50));
        label.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("LAPORAN");
        label.setOpaque(true);

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        panel1.setForeground(new java.awt.Color(255, 255, 255));

        cbxJenisLaporan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        cbxJenisLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxJenisLaporanActionPerformed(evt);
            }
        });

        txtTgl.setBackground(new java.awt.Color(255, 255, 255));
        txtTgl.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtTgl.setForeground(new java.awt.Color(0, 0, 0));
        txtTgl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTgl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));

        jLabel1.setBackground(new java.awt.Color(135, 15, 50));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FILTER");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxJenisLaporan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTgl))
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cbxJenisLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(185, 185, 185));
        txtCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari.setText("Cari Berdasarkan No Pemeriksaan atau Nama Pasien");
        txtCari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(185, 185, 185)));
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        btnPrint.setBackground(new java.awt.Color(255, 255, 255));
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/printer.png"))); // NOI18N
        btnPrint.setBorder(null);
        btnPrint.setOpaque(false);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        lbRp.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lbRp.setForeground(new java.awt.Color(135, 15, 50));
        lbRp.setText("Rp");

        lbTotal.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(135, 15, 50));
        lbTotal.setText("0");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lbRp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(74, 74, 74))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbRp)
                        .addComponent(lbTotal))
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel3.setBackground(new java.awt.Color(255, 255, 255));
        panel3.setLayout(new java.awt.CardLayout());

        panelPemeriksaan.setBackground(new java.awt.Color(255, 255, 255));

        scrollPemeriksaan.setBackground(new java.awt.Color(255, 255, 255));
        scrollPemeriksaan.setBorder(null);
        scrollPemeriksaan.setOpaque(false);

        tablePemeriksaan.setBackground(new java.awt.Color(255, 255, 255));
        tablePemeriksaan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tablePemeriksaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No Pemeriksaan", "No Reservasi", "ID Pasien", "Nama Pasien", "ID Karyawan", "Tanggal Pemeriksaan", "Total", "Deskripsi", "Bayar", "Kembalian", "Jenis Pembayaran", "ID Pengguna", "Nama Pengguna", "       Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePemeriksaan.setGridColor(new java.awt.Color(185, 185, 185));
        tablePemeriksaan.setOpaque(false);
        tablePemeriksaan.setSelectionBackground(new java.awt.Color(255, 255, 255));
        scrollPemeriksaan.setViewportView(tablePemeriksaan);
        if (tablePemeriksaan.getColumnModel().getColumnCount() > 0) {
            tablePemeriksaan.getColumnModel().getColumn(0).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(3).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(3).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(5).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(5).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(8).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(8).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(9).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(9).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(9).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(10).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(10).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(10).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(11).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(11).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(11).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(12).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(12).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(12).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(13).setMinWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(13).setPreferredWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(13).setMaxWidth(0);
            tablePemeriksaan.getColumnModel().getColumn(14).setMinWidth(100);
            tablePemeriksaan.getColumnModel().getColumn(14).setPreferredWidth(100);
            tablePemeriksaan.getColumnModel().getColumn(14).setMaxWidth(100);
        }

        javax.swing.GroupLayout panelPemeriksaanLayout = new javax.swing.GroupLayout(panelPemeriksaan);
        panelPemeriksaan.setLayout(panelPemeriksaanLayout);
        panelPemeriksaanLayout.setHorizontalGroup(
            panelPemeriksaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(panelPemeriksaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE))
        );
        panelPemeriksaanLayout.setVerticalGroup(
            panelPemeriksaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
            .addGroup(panelPemeriksaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPemeriksaan, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
        );

        panel3.add(panelPemeriksaan, "card2");

        panelPenjualan.setBackground(new java.awt.Color(255, 255, 255));

        scrollPenjualan.setBackground(new java.awt.Color(255, 255, 255));
        scrollPenjualan.setBorder(null);
        scrollPenjualan.setOpaque(false);

        tablePenjualan.setBackground(new java.awt.Color(255, 255, 255));
        tablePenjualan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tablePenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No Penjualan", "ID Pengguna", "Kasir", "Tanggal", "Total", "Bayar", "Kembali", "Jenis Pembayaran", "       Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePenjualan.setGridColor(new java.awt.Color(185, 185, 185));
        tablePenjualan.setOpaque(false);
        tablePenjualan.setSelectionBackground(new java.awt.Color(255, 255, 255));
        scrollPenjualan.setViewportView(tablePenjualan);
        if (tablePenjualan.getColumnModel().getColumnCount() > 0) {
            tablePenjualan.getColumnModel().getColumn(0).setMinWidth(0);
            tablePenjualan.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablePenjualan.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePenjualan.getColumnModel().getColumn(2).setMinWidth(0);
            tablePenjualan.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablePenjualan.getColumnModel().getColumn(2).setMaxWidth(0);
            tablePenjualan.getColumnModel().getColumn(6).setMinWidth(0);
            tablePenjualan.getColumnModel().getColumn(6).setPreferredWidth(0);
            tablePenjualan.getColumnModel().getColumn(6).setMaxWidth(0);
            tablePenjualan.getColumnModel().getColumn(7).setMinWidth(0);
            tablePenjualan.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablePenjualan.getColumnModel().getColumn(7).setMaxWidth(0);
            tablePenjualan.getColumnModel().getColumn(8).setMinWidth(0);
            tablePenjualan.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablePenjualan.getColumnModel().getColumn(8).setMaxWidth(0);
            tablePenjualan.getColumnModel().getColumn(9).setMinWidth(100);
            tablePenjualan.getColumnModel().getColumn(9).setPreferredWidth(100);
            tablePenjualan.getColumnModel().getColumn(9).setMaxWidth(100);
        }

        javax.swing.GroupLayout panelPenjualanLayout = new javax.swing.GroupLayout(panelPenjualan);
        panelPenjualan.setLayout(panelPenjualanLayout);
        panelPenjualanLayout.setHorizontalGroup(
            panelPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(panelPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE))
        );
        panelPenjualanLayout.setVerticalGroup(
            panelPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
            .addGroup(panelPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
        );

        panel3.add(panelPenjualan, "card2");

        panelPemesanan.setBackground(new java.awt.Color(255, 255, 255));

        tablePemesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No Pemesanan", "ID Supplier", "Supplier", "Tanggal Pemesanan", "Total", "Bayar", "Kembali", "Jenis Pembayaran", "Status", "", "ID Pengguna", "Pengguna", "       Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePemesanan.setSelectionBackground(new java.awt.Color(245, 245, 245));
        tablePemesanan.setSelectionForeground(new java.awt.Color(0, 0, 0));
        scrollPemesanan.setViewportView(tablePemesanan);
        if (tablePemesanan.getColumnModel().getColumnCount() > 0) {
            tablePemesanan.getColumnModel().getColumn(0).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(2).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(2).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(6).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(6).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(6).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(7).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(7).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(8).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(8).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(9).setMinWidth(100);
            tablePemesanan.getColumnModel().getColumn(9).setPreferredWidth(100);
            tablePemesanan.getColumnModel().getColumn(9).setMaxWidth(100);
            tablePemesanan.getColumnModel().getColumn(10).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(10).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(10).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(11).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(11).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(11).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(12).setMinWidth(0);
            tablePemesanan.getColumnModel().getColumn(12).setPreferredWidth(0);
            tablePemesanan.getColumnModel().getColumn(12).setMaxWidth(0);
            tablePemesanan.getColumnModel().getColumn(13).setMinWidth(100);
            tablePemesanan.getColumnModel().getColumn(13).setPreferredWidth(100);
            tablePemesanan.getColumnModel().getColumn(13).setMaxWidth(100);
        }

        javax.swing.GroupLayout panelPemesananLayout = new javax.swing.GroupLayout(panelPemesanan);
        panelPemesanan.setLayout(panelPemesananLayout);
        panelPemesananLayout.setHorizontalGroup(
            panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPemesanan, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE))
        );
        panelPemesananLayout.setVerticalGroup(
            panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
            .addGroup(panelPemesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPemesanan, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
        );

        panel3.add(panelPemesanan, "card2");

        panelPengeluaran.setBackground(new java.awt.Color(255, 255, 255));

        scrollPengeluaran.setBackground(new java.awt.Color(255, 255, 255));
        scrollPengeluaran.setBorder(null);
        scrollPengeluaran.setOpaque(false);

        tablePengeluaran.setBackground(new java.awt.Color(255, 255, 255));
        tablePengeluaran.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tablePengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No Pengeluaran", "ID Pengguna", "Pengguna", "Tanggal Pengeluaran", "Total Pengeluaran", "Deskripsi", "       Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePengeluaran.setGridColor(new java.awt.Color(185, 185, 185));
        tablePengeluaran.setOpaque(false);
        tablePengeluaran.setSelectionBackground(new java.awt.Color(255, 255, 255));
        scrollPengeluaran.setViewportView(tablePengeluaran);
        if (tablePengeluaran.getColumnModel().getColumnCount() > 0) {
            tablePengeluaran.getColumnModel().getColumn(0).setMinWidth(0);
            tablePengeluaran.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablePengeluaran.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePengeluaran.getColumnModel().getColumn(2).setMinWidth(0);
            tablePengeluaran.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablePengeluaran.getColumnModel().getColumn(2).setMaxWidth(0);
            tablePengeluaran.getColumnModel().getColumn(6).setMinWidth(0);
            tablePengeluaran.getColumnModel().getColumn(6).setPreferredWidth(0);
            tablePengeluaran.getColumnModel().getColumn(6).setMaxWidth(0);
            tablePengeluaran.getColumnModel().getColumn(7).setMinWidth(100);
            tablePengeluaran.getColumnModel().getColumn(7).setPreferredWidth(100);
            tablePengeluaran.getColumnModel().getColumn(7).setMaxWidth(100);
        }

        javax.swing.GroupLayout panelPengeluaranLayout = new javax.swing.GroupLayout(panelPengeluaran);
        panelPengeluaran.setLayout(panelPengeluaranLayout);
        panelPengeluaranLayout.setHorizontalGroup(
            panelPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(panelPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE))
        );
        panelPengeluaranLayout.setVerticalGroup(
            panelPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
            .addGroup(panelPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
        );

        panel3.add(panelPengeluaran, "card2");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, 1221, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(869, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout panelDataLayout = new javax.swing.GroupLayout(panelData);
        panelData.setLayout(panelDataLayout);
        panelDataLayout.setHorizontalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDataLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        panelDataLayout.setVerticalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
        );

        add(panelData, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        txtCari.setText(null);
        txtCari.setForeground(new Color(0,0,0));
        txtCari.setFont(new Font("sansserif",0,14));
    }//GEN-LAST:event_txtCariFocusGained

    private void cbxJenisLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxJenisLaporanActionPerformed
       changeType();
    }//GEN-LAST:event_cbxJenisLaporanActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        
        if(lbTotal.getText().equals("0")) {
            JOptionPane.showMessageDialog(parent, "Tidak Ada Transaksi Di Rentang\n Ini Silahkan Pilih Rentang Lain");
            return;
        }
        
        switch (cbxJenisLaporan.getSelectedIndex()) {
            case 0:
                serviceLaporan.printReportPemeriksaan(parent, tablePemeriksaan, txtTgl, lbTotal);
                break;
            case 1:
                serviceLaporan.printReportPenjualan(parent, tablePenjualan, txtTgl, lbTotal);
                break;
            case 2:
                serviceLaporan.printReportPemesanan(parent, tablePemesanan, txtTgl, lbTotal);
                break;
            default:
                serviceLaporan.printReportPengeluaran(parent, tablePengeluaran, txtTgl, lbTotal);                    
        }
    }//GEN-LAST:event_btnPrintActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JComboBox<String> cbxJenisLaporan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel label;
    private javax.swing.JLabel lbRp;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panelData;
    private javax.swing.JPanel panelPemeriksaan;
    private javax.swing.JPanel panelPemesanan;
    private javax.swing.JPanel panelPengeluaran;
    private javax.swing.JPanel panelPenjualan;
    private javax.swing.JScrollPane scrollPemeriksaan;
    private javax.swing.JScrollPane scrollPemesanan;
    private javax.swing.JScrollPane scrollPengeluaran;
    private javax.swing.JScrollPane scrollPenjualan;
    private javax.swing.JTable tablePemeriksaan;
    private swing.Table tablePemesanan;
    private javax.swing.JTable tablePengeluaran;
    private javax.swing.JTable tablePenjualan;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtTgl;
    // End of variables declaration//GEN-END:variables
}
