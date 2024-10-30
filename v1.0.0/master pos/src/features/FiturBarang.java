/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;
import action.TableAction;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.ModelBarang;
import util.ModelHeaderTable;
import util.ModelRenderTable;
import model.ModelJenisBarang;
import model.ModelNotifikasi;
import service.ServiceBarang;
import service.ServiceNotifikasi;
import swing.TableCellActionRender;
import swing.TableCellEditor;

/**
 *
 * @author usER
 */
public class FiturBarang extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private TableRowSorter<DefaultTableModel> rowSorter;
    private DefaultTableModel tabmodel;
    private TableAction action;
    private JFrame parent;
    private JButton btnNotif;
    private ServiceBarang serviceBarang = new ServiceBarang();
    private ServiceNotifikasi serviceNotifikasi = new ServiceNotifikasi();
    public FiturBarang(JFrame parent, JButton btnNotif) {
        initComponents();
        this.parent = parent;
        this.btnNotif = btnNotif;
        scrollPane.getViewport().setBackground(new Color(255,255,255));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255,255,255));
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scrollPane.setBorder(new EmptyBorder(5,10,5,10));
        table.setRowHeight(40);        
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        table.setDefaultRenderer(Object.class, new ModelRenderTable(9));
        tabmodel = (DefaultTableModel) table.getModel();
        rowSorter = new TableRowSorter<>(tabmodel);
        table.setRowSorter(rowSorter);
        t_Dir.setVisible(false);
        lbKodeJenis.setVisible(false);
        pagination.setVisible(false);
        lbHrgBeliSblm.setVisible(false);
        lbHrgJualSebelum.setVisible(false);
        serviceBarang.loadData(tabmodel);
        cariData();
        actionRenderTable();
//        tampilSatuan();
    }
        
//  Update,Delete,Detail
    private void actionRenderTable() {
        action = new TableAction() {
        @Override
        public void edit(int row) {
            changePanel(panelTambah);
            cbx_jenisBarang.setVisible(false);
            btnHapusJenis.setVisible(false);
            t_kodeBarang.setEnabled(false);
            setShowFieldAddJenis(false, false, false);
            btnSimpan.setText("PERBARUI");
            String kodeBarang = (String) table.getValueAt(row, 0);
            String nomorBarcode = (String) table.getValueAt(row, 1);
            if(nomorBarcode.equals("")) {
                nomorBarcode = "Klik disini untuk scan nomor barcode barang (Opsional)";
                t_noBarcode.setFont(new Font("sansserif", Font.ITALIC, 20));
                t_noBarcode.setForeground(new Color(185,185,185));
                t_noBarcode.setEnabled(true);
            } else {
                t_noBarcode.setFont(new Font("sansserif", 0, 20));
                t_noBarcode.setForeground(new Color(0,0,0));
                t_noBarcode.setEnabled(false);
            }
            String kodeJenis = (String) table.getValueAt(row, 2);
            String namaBarang = (String) table.getValueAt(row, 4);
            String satuan = (String) table.getValueAt(row, 5);
            int hargaBeli = (int) table.getValueAt(row,6);
            int hargaJual = (int) table.getValueAt(row, 7);
            int stok = (int) table.getValueAt(row, 8);
            
            t_kodeBarang.setText(kodeBarang);
            t_noBarcode.setText(nomorBarcode);
            lbKodeJenis.setText(kodeJenis);
            t_namaBarang.setText(namaBarang);
            if(setShowFieldSatuan(satuan)) {
               cbx_satuan.setSelectedItem(satuan);
               cbx_satuan.setVisible(true);
               t_satuan.setVisible(false);
            } else {
               t_satuan.setText(satuan);
               cbx_satuan.setVisible(false);
               t_satuan.setVisible(true);
               t_satuan.setFont(new Font("sansserif", 0, 20));
               t_satuan.setForeground(new Color(0,0,0));
            }
            t_hargaBeli.setText(String.valueOf(hargaBeli));
            lbHrgBeliSblm.setText(String.valueOf(hargaBeli));
            t_hargaJual.setText(String.valueOf(hargaJual));
            lbHrgJualSebelum.setText(String.valueOf(hargaJual));
            spn_stok.setValue(stok);
        }

        @Override
        public void delete(int row) {
            hapusData(row);
        }

        @Override
        public void view(int row) {
        }
    };        
        table.getColumnModel().getColumn(9).setCellRenderer(new TableCellActionRender(true, true, false));
        table.getColumnModel().getColumn(9).setCellEditor(new TableCellEditor(action, true, true, false));
    }
    
    private boolean setShowFieldSatuan(String satuan) {
        for(int a = 0; a < cbx_satuan.getItemCount(); a++) {
            String jenisSatuan = cbx_satuan.getItemAt(a);
            if(jenisSatuan.equals(satuan)) {
                return true;
            }
        }
        
        return false;
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
        btnPilih = new swing.Button();
        btnHapus = new swing.Button();
        t_Dir = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        btnSimpan = new swing.Button();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnBatal = new swing.Button();
        jLabel8 = new javax.swing.JLabel();
        t_kodeBarang = new javax.swing.JTextField();
        t_hargaBeli = new javax.swing.JTextField();
        t_hargaJual = new javax.swing.JTextField();
        t_namaBarang = new javax.swing.JTextField();
        cbx_satuan = new javax.swing.JComboBox<>();
        spn_stok = new javax.swing.JSpinner();
        cbx_jenisBarang = new javax.swing.JComboBox<>();
        t_noBarcode = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        t_tambahJenisBarang = new javax.swing.JTextField();
        btnTambahJenis = new swing.Button();
        btnBatalJenis = new swing.Button();
        lbKodeJenis = new javax.swing.JLabel();
        lbHrgBeliSblm = new javax.swing.JLabel();
        lbHrgJualSebelum = new javax.swing.JLabel();
        btnHapusJenis = new swing.Button();
        t_satuan = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        panelData.setBackground(new java.awt.Color(153, 153, 153));
        panelData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);

        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nomor Barcode", "Kode Jenis", "Jenis Barang", "Nama Barang", "Satuan", "Harga Beli", "Harga Jual", "Stok", "           Aksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
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
            table.getColumnModel().getColumn(0).setMinWidth(150);
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.getColumnModel().getColumn(0).setMaxWidth(150);
            table.getColumnModel().getColumn(1).setMinWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setMaxWidth(150);
            table.getColumnModel().getColumn(2).setMinWidth(0);
            table.getColumnModel().getColumn(2).setPreferredWidth(0);
            table.getColumnModel().getColumn(2).setMaxWidth(0);
            table.getColumnModel().getColumn(3).setMinWidth(125);
            table.getColumnModel().getColumn(3).setPreferredWidth(125);
            table.getColumnModel().getColumn(3).setMaxWidth(125);
            table.getColumnModel().getColumn(4).setMinWidth(300);
            table.getColumnModel().getColumn(4).setPreferredWidth(300);
            table.getColumnModel().getColumn(4).setMaxWidth(300);
            table.getColumnModel().getColumn(5).setMinWidth(100);
            table.getColumnModel().getColumn(5).setPreferredWidth(100);
            table.getColumnModel().getColumn(5).setMaxWidth(100);
            table.getColumnModel().getColumn(8).setMinWidth(100);
            table.getColumnModel().getColumn(8).setPreferredWidth(100);
            table.getColumnModel().getColumn(8).setMaxWidth(100);
            table.getColumnModel().getColumn(9).setMinWidth(125);
            table.getColumnModel().getColumn(9).setPreferredWidth(125);
            table.getColumnModel().getColumn(9).setMaxWidth(125);
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
        label.setText("BARANG");

        pagination.setBackground(new java.awt.Color(135, 15, 50));
        pagination.setForeground(new java.awt.Color(255, 255, 255));
        pagination.setOpaque(false);

        txtCari.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(185, 185, 185));
        txtCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari.setText("Cari Berdasarkan Kode Barang Atau Nama Barang");
        txtCari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(185, 185, 185)));
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
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
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 654, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 519, Short.MAX_VALUE)
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

        btnPilih.setBackground(new java.awt.Color(135, 15, 50));
        btnPilih.setForeground(new java.awt.Color(255, 255, 255));
        btnPilih.setText("PILIH");
        btnPilih.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(0, 153, 0));
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setText("HAPUS");
        btnHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        t_Dir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/insert-picture-icon.png"))); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(175, 175, 175)));

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGap(0, 28, Short.MAX_VALUE)
                        .addComponent(btnPilih, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addComponent(t_Dir, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(t_Dir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPilih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        btnSimpan.setBackground(new java.awt.Color(135, 15, 50));
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("SIMPAN");
        btnSimpan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Nama Barang");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Kode Barang");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Harga Beli");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Satuan");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Stok");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Harga Jual");

        btnBatal.setBackground(new java.awt.Color(0, 153, 0));
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("BATAL");
        btnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(135, 15, 50));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/barcode.png"))); // NOI18N
        jLabel8.setOpaque(true);

        t_kodeBarang.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        t_kodeBarang.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        t_hargaBeli.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        t_hargaBeli.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        t_hargaBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_hargaBeliKeyTyped(evt);
            }
        });

        t_hargaJual.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        t_hargaJual.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        t_hargaJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t_hargaJualKeyTyped(evt);
            }
        });

        t_namaBarang.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        t_namaBarang.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        cbx_satuan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        cbx_satuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pcs", "Gram", "Butir", "Kaleng", "Isi Sendiri" }));
        cbx_satuan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        cbx_satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_satuanActionPerformed(evt);
            }
        });

        spn_stok.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        spn_stok.setModel(new javax.swing.SpinnerNumberModel());

        cbx_jenisBarang.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        cbx_jenisBarang.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        cbx_jenisBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_jenisBarangActionPerformed(evt);
            }
        });

        t_noBarcode.setFont(new java.awt.Font("SansSerif", 2, 20)); // NOI18N
        t_noBarcode.setForeground(new java.awt.Color(185, 185, 185));
        t_noBarcode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_noBarcode.setText("Klik disini untuk scan nomor barcode barang (Opsional)");
        t_noBarcode.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        t_noBarcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_noBarcodeFocusGained(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Nomor Barcode");

        t_tambahJenisBarang.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        t_tambahJenisBarang.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        t_tambahJenisBarang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_tambahJenisBarangFocusGained(evt);
            }
        });

        btnTambahJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add.png"))); // NOI18N
        btnTambahJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahJenisActionPerformed(evt);
            }
        });

        btnBatalJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus.png"))); // NOI18N
        btnBatalJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalJenisActionPerformed(evt);
            }
        });

        lbKodeJenis.setBackground(new java.awt.Color(255, 255, 255));
        lbKodeJenis.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        lbKodeJenis.setForeground(new java.awt.Color(0, 0, 0));

        lbHrgBeliSblm.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lbHrgBeliSblm.setForeground(new java.awt.Color(255, 255, 255));
        lbHrgBeliSblm.setText("Harga Beli Sebelum");

        lbHrgJualSebelum.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lbHrgJualSebelum.setForeground(new java.awt.Color(255, 255, 255));
        lbHrgJualSebelum.setText("Harga Jual Sebelum");

        btnHapusJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close.png"))); // NOI18N
        btnHapusJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusJenisActionPerformed(evt);
            }
        });

        t_satuan.setBackground(new java.awt.Color(255, 255, 255));
        t_satuan.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        t_satuan.setForeground(new java.awt.Color(0, 0, 0));
        t_satuan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_satuan.setText("Isi Satuan Sendiri");
        t_satuan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        t_satuan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_satuanFocusGained(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbKodeJenis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(t_kodeBarang)
                    .addComponent(t_hargaBeli)
                    .addComponent(t_hargaJual)
                    .addComponent(t_namaBarang)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(spn_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(lbHrgBeliSblm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbHrgJualSebelum)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(cbx_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t_satuan))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(t_tambahJenisBarang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatalJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(t_noBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_jenisBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(64, 64, 64))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cbx_jenisBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(t_noBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnHapusJenis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(t_tambahJenisBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnTambahJenis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBatalJenis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbKodeJenis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_kodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(cbx_satuan, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(t_satuan, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_hargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t_hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(spn_stok, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(lbHrgBeliSblm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbHrgJualSebelum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        label1.setBackground(new java.awt.Color(135, 15, 50));
        label1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Tambah Barang");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1)
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
                        .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelTambahLayout.setVerticalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(107, Short.MAX_VALUE))
        );

        add(panelTambah, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        changePanel(panelTambah);
        clearField();
        t_noBarcode.setText("Klik disini untuk scan nomor barcode barang (Opsional)");
        t_noBarcode.setFont(new Font("sansserif", Font.ITALIC, 20));
        t_noBarcode.setForeground(new Color(185,185,185));
        t_noBarcode.setEnabled(true);
        cbx_jenisBarang.setVisible(true);
        cbx_satuan.setVisible(true);
        cbx_satuan.setSelectedIndex(0);
        t_satuan.setVisible(false);
        t_satuan.setFont(new Font("sansserif", Font.ITALIC, 20));
        t_satuan.setForeground(new Color(185,185,185));
        btnHapusJenis.setVisible(true);
        t_kodeBarang.setEnabled(false);
        tampilJenisBarang();
        setShowFieldAddJenis(false, false, false);
        btnSimpan.setText("SIMPAN");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(validation()) {
            if(btnSimpan.getText().equals("SIMPAN")) {
                tambahData();
            } else {
                perbaruiData();
                btnNotif.setText(serviceNotifikasi.getCountNotification() + "");
            }
        
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        txtCari.setText(null);
        txtCari.setForeground(new Color(0,0,0));
        txtCari.setFont(new Font("sansserif",0,20));
    }//GEN-LAST:event_txtCariFocusGained

    private void t_hargaBeliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_hargaBeliKeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_t_hargaBeliKeyTyped

    private void t_hargaJualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_hargaJualKeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_t_hargaJualKeyTyped

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        clearField();
        changePanel(panelData);
        tabmodel.setRowCount(0);
        serviceBarang.loadData(tabmodel);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void cbx_jenisBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_jenisBarangActionPerformed
        String index = (String) cbx_jenisBarang.getSelectedItem(); 
        if(!index.equals("Tambah Jenis Barang")) {
            setShowFieldAddJenis(false, false, false);
            ModelJenisBarang modelJenis = new ModelJenisBarang();
            modelJenis.setNamaJenis(index);
            String kodeBarang = serviceBarang.createKodeBarang(modelJenis);
            String kodeJenis = serviceBarang.selectKodeJenis(modelJenis);
            lbKodeJenis.setText(kodeJenis);
            t_kodeBarang.setText(kodeBarang);
        } else {
            setShowFieldAddJenis(true, true, true);
            t_tambahJenisBarang.setText("Tambah Jenis Barang");
            t_tambahJenisBarang.setForeground(new Color(185, 185, 185));
            t_kodeBarang.setText(null);
        }
    }//GEN-LAST:event_cbx_jenisBarangActionPerformed

    private void btnTambahJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahJenisActionPerformed
        if(t_tambahJenisBarang.getText().equals("Tambah Jenis Barang")) {
            JOptionPane.showMessageDialog(parent, "Silahkan masukkan jenis barang baru");
            return;
        }
        
        
        if(serviceBarang.loadDataJenisBarang().size() == 0) {
            tambahJenisBarang();
            setShowFieldAddJenis(false, false, false);
            List<String> listJenisBarang = serviceBarang.loadDataJenisBarang();
            for(String list : listJenisBarang) {
            cbx_jenisBarang.removeItem(list);
            cbx_jenisBarang.addItem(list);
            }
        } else {
            String newJenisBarang = t_tambahJenisBarang.getText();
            ModelJenisBarang modelJenisBarang = new ModelJenisBarang();
            modelJenisBarang.setNamaJenis(newJenisBarang);
            if(serviceBarang.validationAddJenisBarang(parent, modelJenisBarang)) {
                tambahJenisBarang();
                tampilJenisBarang();
                setShowFieldAddJenis(false, false, false);      
            } else {
                setShowFieldAddJenis(false, false, false);      
                
            }     
        }
    }//GEN-LAST:event_btnTambahJenisActionPerformed

    private void btnBatalJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalJenisActionPerformed
        setShowFieldAddJenis(false, false, false);
    }//GEN-LAST:event_btnBatalJenisActionPerformed

    private void t_tambahJenisBarangFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_tambahJenisBarangFocusGained
        t_tambahJenisBarang.setText("");
        t_tambahJenisBarang.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_t_tambahJenisBarangFocusGained

    private void t_noBarcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_noBarcodeFocusGained
        if(t_noBarcode.getText().equals("Klik disini untuk scan nomor barcode barang (Opsional)")) {
            t_noBarcode.setText("");
            t_noBarcode.setForeground(new Color(0, 0, 0));
            t_noBarcode.setFont(new Font("sansserif", 0, 20));
        }
    }//GEN-LAST:event_t_noBarcodeFocusGained

    private void btnHapusJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusJenisActionPerformed
        hapusJenisBarang();
    }//GEN-LAST:event_btnHapusJenisActionPerformed

    private void btnPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihActionPerformed
        JOptionPane.showMessageDialog(parent, "Fitur ini masih belum bisa digunakan");
    }//GEN-LAST:event_btnPilihActionPerformed

    private void t_satuanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_satuanFocusGained
        t_satuan.setText("");
        t_satuan.setFont(new Font("sansserif", 0, 20));
        t_satuan.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_t_satuanFocusGained

    private void cbx_satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_satuanActionPerformed
        if(cbx_satuan.getSelectedIndex() == 4) {
            t_satuan.setText("Isi Satuan Sendiri");
            t_satuan.setVisible(true);
            cbx_satuan.setVisible(false);
        }
    }//GEN-LAST:event_cbx_satuanActionPerformed

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
    private swing.Button btnBatalJenis;
    private swing.Button btnHapus;
    private swing.Button btnHapusJenis;
    private swing.Button btnPilih;
    private swing.Button btnSimpan;
    private swing.Button btnTambah;
    private swing.Button btnTambahJenis;
    private javax.swing.JComboBox<String> cbx_jenisBarang;
    private javax.swing.JComboBox<String> cbx_satuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lbHrgBeliSblm;
    private javax.swing.JLabel lbHrgJualSebelum;
    private javax.swing.JLabel lbKodeJenis;
    private swing.Pagination pagination;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panelData;
    private javax.swing.JPanel panelTambah;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSpinner spn_stok;
    private javax.swing.JTextField t_Dir;
    private javax.swing.JTextField t_hargaBeli;
    private javax.swing.JTextField t_hargaJual;
    private javax.swing.JTextField t_kodeBarang;
    private javax.swing.JTextField t_namaBarang;
    private javax.swing.JTextField t_noBarcode;
    private javax.swing.JTextField t_satuan;
    private javax.swing.JTextField t_tambahJenisBarang;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables

// Tambah data barang
    private void tambahData() {
        String kodeBarang = t_kodeBarang.getText();
        String nomorBarcode = t_noBarcode.getText();
        if(nomorBarcode.equals("Klik disini untuk scan nomor barcode barang (Opsional)")) {
            nomorBarcode = "";
        }
        String kodeJenis = lbKodeJenis.getText();
        String namaBarang = t_namaBarang.getText();
        String satuan = (String) cbx_satuan.getSelectedItem();
        if(t_satuan.getText().length() > 0 && !t_satuan.getText().equals("Isi Satuan Sendiri")) {
            satuan = t_satuan.getText();
        }
        
        int hargaBeli = Integer.parseInt(t_hargaBeli.getText());
        int hargaJual = Integer.parseInt(t_hargaJual.getText());
        int stok = (int) spn_stok.getValue();
        ModelBarang modelBarang = new ModelBarang(kodeBarang, nomorBarcode, kodeJenis, namaBarang, satuan, hargaBeli, hargaJual, stok);
        if(!serviceBarang.cekNomorBarcode(modelBarang)) {
            JOptionPane.showMessageDialog(parent, "Nomor barcode sudah tersedia\nMohon cek kembali nomor\nbarcode barang");
            return;
        }
        serviceBarang.addData(parent, modelBarang);                
        clearField();
        changePanel(panelData);
        tabmodel.setRowCount(0);
        serviceBarang.loadData(tabmodel);
    }
    
    private void perbaruiData() {
        String kodeBarang = t_kodeBarang.getText();
        String nomorBarcode = t_noBarcode.getText();
        if(nomorBarcode.equals("Klik disini untuk scan nomor barcode barang (Opsional)")) {
            nomorBarcode = "";
        }
        String kodeJenis = lbKodeJenis.getText();
        String namaBarang = t_namaBarang.getText();
        String satuan = (String) cbx_satuan.getSelectedItem();
        if(t_satuan.getText().length() > 0 && !t_satuan.getText().equals("Isi Satuan Sendiri")) {
            satuan = t_satuan.getText();
        }
        int hargaBeli = Integer.parseInt(t_hargaBeli.getText());
        int hargaJual = Integer.parseInt(t_hargaJual.getText());
        int stok = (int) spn_stok.getValue();
        ModelBarang modelBarang = new ModelBarang(kodeBarang, nomorBarcode, kodeJenis, namaBarang, satuan, hargaBeli, hargaJual, stok);
        serviceBarang.updateData(parent, modelBarang);
        int hrgBeliSebelum = Integer.valueOf(lbHrgBeliSblm.getText());
        int hrgJualSebelum = Integer.valueOf(lbHrgJualSebelum.getText());
        cekPerubahanHarga(kodeBarang, namaBarang, hrgJualSebelum, hargaJual, hrgBeliSebelum, hargaBeli);
        clearField();
        changePanel(panelData);
        tabmodel.setRowCount(0);
        serviceBarang.loadData(tabmodel);
    }
    
    private void hapusData(int row) {
        String kodeBarang = (String) table.getValueAt(row, 0);
        ModelBarang modelBarang = new ModelBarang();
        modelBarang.setKode_Barang(kodeBarang);
        if(serviceBarang.validationDelete(parent, modelBarang)) {
            int confirm = JOptionPane.showConfirmDialog(parent, "Yakin ingin menghapus barang ini?", 
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if(confirm == JOptionPane.YES_OPTION) {
                    
                    if(table.isEditing()) {
                        table.getCellEditor().stopCellEditing();
                    }
                    serviceBarang.deleteData(parent, modelBarang);   
                    tabmodel.removeRow(row);   
            }
        }
    }
    
    private void cekPerubahanHarga(String kodeBarang, String namaBarang, 
    int hrgJualSebelum, int hargaJual, int hrgBeliSebelum, int hargaBeli) {
        String idNotifikasi1 = String.valueOf(new Random().nextInt(10000));
        String idNotifikasi2 = String.valueOf(new Random().nextInt(10000));
        StringBuilder deskripsi1 = new StringBuilder();
        StringBuilder deskripsi2 = new StringBuilder();
        
        if(!t_hargaBeli.getText().equals(lbHrgBeliSblm.getText()) && 
            !t_hargaJual.getText().equals(lbHrgJualSebelum.getText())) {
            
            tambahPerubahanHargaBeli(idNotifikasi1, kodeBarang, deskripsi1, namaBarang, hrgBeliSebelum, hargaBeli);
            
            tambahPerubahanHargaJual(idNotifikasi2, kodeBarang, deskripsi2, namaBarang, hrgJualSebelum, hargaJual);
            
            return;
        }
        
        if(!t_hargaBeli.getText().equals(lbHrgBeliSblm.getText())) {
            tambahPerubahanHargaBeli(idNotifikasi1, kodeBarang, deskripsi1, namaBarang, hrgBeliSebelum, hargaBeli);
         } 

        if(!t_hargaJual.getText().equals(lbHrgJualSebelum.getText())) {
            tambahPerubahanHargaJual(idNotifikasi2, kodeBarang, deskripsi2, namaBarang, hrgJualSebelum, hargaJual);
         } 
        
    }
    
    private void tambahPerubahanHargaBeli(String idNotifikasi, String kodeBarang, StringBuilder deskripsi, 
            String namaBarang, int hrgBeliSebelum, int hargaBeli) {
        
        String namaNotifikasi = "Perubahan Harga Beli Barang";
         deskripsi.append("Harga Beli " + namaBarang + " berhasil dirubah dari "+hrgBeliSebelum+" menjadi " + hargaBeli);
         ModelNotifikasi modelNotifikasi1 = new ModelNotifikasi(idNotifikasi, namaNotifikasi.toString(), 
             deskripsi.toString(), kodeBarang, false);
         serviceNotifikasi.addNotification(modelNotifikasi1);
    }
    
    private void tambahPerubahanHargaJual(String idNotifikasi, String kodeBarang, StringBuilder deskripsi, 
            String namaBarang, int hrgJualSebelum, int hargaJual) {
        String namaNotifikasi = "Perubahan Harga Jual Barang";
         deskripsi.append("Harga Jual " + namaBarang + " berhasil dirubah dari "+hrgJualSebelum+" menjadi " + hargaJual);
         ModelNotifikasi modelNotifikasi = new ModelNotifikasi(idNotifikasi, namaNotifikasi.toString(), 
             deskripsi.toString(), kodeBarang, false);
         serviceNotifikasi.addNotification(modelNotifikasi);
    }
    
    private void tambahJenisBarang() {
        String kodeJenis = serviceBarang.createKodeJenis();
        String namaJenis = t_tambahJenisBarang.getText();
        ModelJenisBarang modelJenisBarang = new ModelJenisBarang(kodeJenis, namaJenis);
        serviceBarang.addJenisBarang(modelJenisBarang);
    }
    
    private void tampilJenisBarang() {
        cbx_jenisBarang.removeItem("Tambah Jenis Barang");
        cbx_jenisBarang.addItem("Tambah Jenis Barang");
        List<String> listJenisBarang = serviceBarang.loadDataJenisBarang();
        for(String list : listJenisBarang) {
            cbx_jenisBarang.removeItem(list);
            cbx_jenisBarang.addItem(list);
        }
    }
    
    private void hapusJenisBarang() {
        if(cbx_jenisBarang.getSelectedItem().equals("Tambah Jenis Barang")) {
            JOptionPane.showMessageDialog(parent, "Silahkan pilih jenis barang yang ingin dihapus");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(parent, "Yakin ingin menghapus jenis barang ini?", 
        "Konfirmasi",JOptionPane.YES_OPTION);
        
        ModelJenisBarang modelJenis = new ModelJenisBarang();
        modelJenis.setNamaJenis(cbx_jenisBarang.getSelectedItem().toString());
        
        if(confirm == JOptionPane.YES_OPTION) {
            if(!serviceBarang.validationDeleteJenisBarang(modelJenis)) {
                JOptionPane.showMessageDialog(parent, "Tidak dapat menghapus jenis barang ini\n"
                + "Jenis barang ini sedang digunakan pada\nmenu barang", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            serviceBarang.deleteJenisBarang(modelJenis);
            cbx_jenisBarang.removeItem(cbx_jenisBarang.getSelectedItem());
            cbx_jenisBarang.setSelectedItem("Tambah Jenis Barang");
            t_kodeBarang.setText("");
        }
    }
    
    private void tampilSatuan() {
        String[] jenisSatuan = {"Pcs","Gram","Butir","Kaleng"};
        for(String satuan : jenisSatuan) {
            cbx_satuan.addItem(satuan);
        }
    }
        
    private void cariData() {
        txtCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txtCari.getText();
                if(text.length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 4));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtCari.getText();
                if(text.length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 4));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
    }
        
    private boolean validation() {
        boolean valid = false;
        int stok = (int) spn_stok.getValue();
        if(t_kodeBarang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Kode Barang tidak boleh kosong");
        } else if(t_namaBarang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Nama Barang tidak boleh kosong");   
        } else if(t_hargaBeli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Harga Beli tidak boleh kosong");             
        } else if(t_hargaJual.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Harga Jual tidak boleh kosong");   
        } else if(stok == 0) {
            JOptionPane.showMessageDialog(parent, "Stok harus di isi");   
        } else {
            valid = true;
        }
        
        return valid;
    }
    
    private void clearField() {
        t_noBarcode.setText(null);
        t_kodeBarang.setText(null);
        t_namaBarang.setText(null);
        t_hargaBeli.setText(null);
        t_hargaJual.setText(null);
        spn_stok.setValue(0);
    }
        
    private void setShowFieldAddJenis(boolean tambahJenis, boolean btnTambah, boolean btnBatal) {
        t_tambahJenisBarang.setVisible(tambahJenis);
        btnTambahJenis.setVisible(btnTambah);
        btnBatalJenis.setVisible(btnBatal);
    }
}