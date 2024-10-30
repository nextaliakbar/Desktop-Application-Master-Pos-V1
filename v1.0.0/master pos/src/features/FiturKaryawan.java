/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;

import action.TableAction;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import util.ModelHeaderTable;
import model.ModelKaryawan;
import util.ModelRenderTable;
import service.ServiceKaryawan;
import swing.TableCellActionRender;
import swing.TableCellEditor;

/**
 *
 * @author usER
 */
public class FiturKaryawan extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private TableRowSorter<DefaultTableModel> rowSorter;
    private DefaultTableModel tabmodel;
    private TableAction action;
    private ServiceKaryawan serviceKaryawan = new ServiceKaryawan();
    private JFrame parent;
    public FiturKaryawan(JFrame parent) {
        initComponents();
        this.parent = parent;
        scrollPane.getViewport().setBackground(new Color(255,255,255));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255,255,255));
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scrollPane.setBorder(new EmptyBorder(5,10,5,10));
        table.setRowHeight(40);        
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        table.setDefaultRenderer(Object.class, new ModelRenderTable(7));
        tabmodel = (DefaultTableModel) table.getModel();
        rowSorter = new TableRowSorter<>(tabmodel);
        table.setRowSorter(rowSorter);
        pagination.setVisible(false);
        serviceKaryawan.loadData(tabmodel);
        tampilJabatanStatus();
        actionRenderTable();
        cariData();
    }
    
//  Update,Delete,Detail
    private void actionRenderTable() {
        action = new TableAction() {
        @Override
        public void edit(int row) {
            changePanel(panelTambah);
            setComponentUpdate(row);
            btnSimpan.setText("PERBARUI");
        }

        @Override
        public void delete(int row) {
            hapusData(row);  
        }

        @Override
        public void view(int row) {
            
        }
    };        
        table.getColumnModel().getColumn(7).setCellRenderer(new TableCellActionRender(true, true, false));
        table.getColumnModel().getColumn(7).setCellEditor(new TableCellEditor(action, true, true, false));
    }
    
    private void tambahData(){
    String IdKaryawan = TFIdKaryawan.getText();   
    String NamaKaryawan = TFNamaKaryawan.getText();   
    String TeleponKaryawan = TFTeleponKaryawan.getText();   
    String EmailKaryawan = TFEmailKaryawan.getText();   
    String AlamatKaryawan = TFAlamatKaryawan.getText();   
    String JabatanKaryawan = (String) cbxJabatanKaryawan.getSelectedItem();
    String StatusKaryawan = (String) cbxStatusKaryawan.getSelectedItem();
    ModelKaryawan modelKaryawan = new ModelKaryawan(IdKaryawan, NamaKaryawan, TeleponKaryawan, 
    EmailKaryawan, AlamatKaryawan, JabatanKaryawan, StatusKaryawan);
    serviceKaryawan.addData(parent, modelKaryawan);
    
    }
    
    private void setComponentUpdate(int row){
    TFIdKaryawan.setText((String) table.getValueAt(row, 0));
    TFNamaKaryawan.setText((String) table.getValueAt(row, 1));
    TFTeleponKaryawan.setText((String) table.getValueAt(row, 2));
    TFEmailKaryawan.setText((String) table.getValueAt(row, 3));
    TFAlamatKaryawan.setText((String) table.getValueAt(row, 4));
    cbxJabatanKaryawan.setSelectedItem((String)table.getValueAt(row, 5));
    cbxStatusKaryawan.setSelectedItem((String) table.getValueAt(row, 6));
    
   }
    
    private void perbaruiData(){
        String IdKaryawan = TFIdKaryawan.getText();
        String NamaKaryawan = TFNamaKaryawan.getText();
        String TeleponKaryawan = TFTeleponKaryawan.getText();
        String EmailKaryawan = TFEmailKaryawan.getText();
        String AlamatKaryawan = TFAlamatKaryawan.getText();
        String JabatanKaryawan = (String) cbxJabatanKaryawan.getSelectedItem();
        String StatusKaryawan = (String) cbxStatusKaryawan.getSelectedItem();
        ModelKaryawan modelKaryawan = new ModelKaryawan(IdKaryawan, NamaKaryawan, TeleponKaryawan, 
        EmailKaryawan, AlamatKaryawan, JabatanKaryawan, StatusKaryawan);
        serviceKaryawan.updateData(parent, modelKaryawan);
        
    }
     private void hapusData(int row){
        String IdKaryawan = (String) table.getValueAt(row, 0);
        ModelKaryawan modelKaryawan = new ModelKaryawan();
        modelKaryawan.setIdKaryawan(IdKaryawan);
        if(serviceKaryawan.validationDelete(parent, modelKaryawan)) {
            int confirm = JOptionPane.showConfirmDialog(parent, "Yakin ingin menghapus karyawan ini?", 
        "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                if(table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                serviceKaryawan.deleteData(parent, modelKaryawan);
                tabmodel.removeRow(row);   
                }
        } 
    }
     
     private boolean validationFormatEmail() {
          String REGEX_EMAIL = "[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
          Pattern pattern = Pattern.compile(REGEX_EMAIL);
          Matcher matcher = pattern.matcher(TFEmailKaryawan.getText());
          if(matcher.matches()) {
              return true;
          }
          JOptionPane.showMessageDialog(parent, "Format email harus dalam bentuk email\n"
                  + "Contoh : email@gmail.com");
          return false;
      }
     
     private boolean validation(){
        boolean valid = false;
        if(TFIdKaryawan.getText().isEmpty()){
            JOptionPane.showMessageDialog(parent, "ID Karyawan Tidak Boleh Kosong");
        }else if (TFNamaKaryawan.getText().isEmpty()){
            JOptionPane.showMessageDialog(parent, "Nama Karyawan Tidak Boleh Kosong");
        }else if (TFTeleponKaryawan.getText().isEmpty()){
            JOptionPane.showMessageDialog(parent, "No Telepon Karyawan Tidak Boleh Kosong");
        }else if (TFEmailKaryawan.getText().isEmpty()){
            JOptionPane.showMessageDialog(parent, "Email Karyawan Tidak Boleh Kosong");
        }else if (TFAlamatKaryawan.getText().isEmpty()){
            JOptionPane.showMessageDialog(parent, "Alamat Karyawan Tiddak Boleh Kosong");   
        }else{
            valid = true;
        }
        
        return valid;
    }
     private void clearField(){
        TFIdKaryawan.setText(null);
        TFNamaKaryawan.setText(null);
        TFTeleponKaryawan.setText(null);
        TFEmailKaryawan.setText(null);
        TFAlamatKaryawan.setText(null);
    }
         
    private void tampilJabatanStatus() {
        String[] positions = new String[]{"Admin","Terapis"};
        String[] statuss = new String[]{"Aktif","Nonaktif"};
        for(int a = 0; a < 2; a++) {
            cbxJabatanKaryawan.addItem(positions[a]);
            cbxStatusKaryawan.addItem(statuss[a]);
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
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtCari.getText();
                if(text.length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
    }
    
//    private void tampilLevelKa
   
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
        panel2 = new javax.swing.JPanel();
        btnSimpan = new swing.Button();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnSimpan1 = new swing.Button();
        TFIdKaryawan = new javax.swing.JTextField();
        TFEmailKaryawan = new javax.swing.JTextField();
        TFNamaKaryawan = new javax.swing.JTextField();
        TFTeleponKaryawan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TFAlamatKaryawan = new javax.swing.JTextArea();
        cbxJabatanKaryawan = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbxStatusKaryawan = new javax.swing.JComboBox<>();
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

        table.setBackground(new java.awt.Color(255, 255, 255));
        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Karyawan", "Nama", "No Telepon", "Email", "Alamat", "Jabatan", "Status", "           Aksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
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
            table.getColumnModel().getColumn(0).setMinWidth(100);
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(0).setMaxWidth(100);
            table.getColumnModel().getColumn(2).setMinWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setMaxWidth(150);
            table.getColumnModel().getColumn(4).setPreferredWidth(300);
            table.getColumnModel().getColumn(5).setMinWidth(125);
            table.getColumnModel().getColumn(5).setPreferredWidth(125);
            table.getColumnModel().getColumn(5).setMaxWidth(125);
            table.getColumnModel().getColumn(6).setMinWidth(125);
            table.getColumnModel().getColumn(6).setPreferredWidth(125);
            table.getColumnModel().getColumn(6).setMaxWidth(125);
            table.getColumnModel().getColumn(7).setMinWidth(125);
            table.getColumnModel().getColumn(7).setPreferredWidth(125);
            table.getColumnModel().getColumn(7).setMaxWidth(125);
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
        label.setText("KARYAWAN");

        pagination.setBackground(new java.awt.Color(135, 15, 50));
        pagination.setForeground(new java.awt.Color(255, 255, 255));
        pagination.setOpaque(false);

        txtCari.setBackground(new java.awt.Color(255, 255, 255));
        txtCari.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(185, 185, 185));
        txtCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari.setText("Cari Berdasarkan ID Karyawan Atau Nama");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 522, Short.MAX_VALUE)
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
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Nama");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("ID Karyawan");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Email");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("No Telepon");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Status");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Alamat");

        btnSimpan1.setBackground(new java.awt.Color(0, 153, 0));
        btnSimpan1.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan1.setText("BATAL");
        btnSimpan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan1ActionPerformed(evt);
            }
        });

        TFIdKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFIdKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        TFEmailKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFEmailKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        TFNamaKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFNamaKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        TFTeleponKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFTeleponKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));
        TFTeleponKaryawan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TFTeleponKaryawanKeyTyped(evt);
            }
        });

        TFAlamatKaryawan.setColumns(20);
        TFAlamatKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        TFAlamatKaryawan.setRows(5);
        jScrollPane1.setViewportView(TFAlamatKaryawan);

        cbxJabatanKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        cbxJabatanKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Jabatan");

        cbxStatusKaryawan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        cbxStatusKaryawan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TFIdKaryawan)
                    .addComponent(TFEmailKaryawan)
                    .addComponent(TFNamaKaryawan)
                    .addComponent(TFTeleponKaryawan)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxStatusKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(cbxJabatanKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFIdKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFNamaKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFTeleponKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFEmailKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxJabatanKaryawan)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxStatusKaryawan)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        label1.setBackground(new java.awt.Color(135, 15, 50));
        label1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Tambah Karyawan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(477, Short.MAX_VALUE)
                .addComponent(label1)
                .addContainerGap(477, Short.MAX_VALUE))
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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTambahLayout.setVerticalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        add(panelTambah, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        clearField();
        changePanel(panelTambah);
        btnSimpan.setText("SIMPAN");
        TFIdKaryawan.setText(serviceKaryawan.createId());
        TFIdKaryawan.setEnabled(false);
        cbxJabatanKaryawan.setSelectedItem("Admin");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(btnSimpan.getText().equals("SIMPAN")) {
        if(validation()) {
            if(validationFormatEmail()) {
                tambahData();          
                changePanel(panelData);   
            }
        }   
        } else {
        if(validation()) {
            if(validationFormatEmail()) {
                perbaruiData();    
                changePanel(panelData);  
            }
        }
        }
        tabmodel.setRowCount(0);
        serviceKaryawan.loadData(tabmodel);
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        txtCari.setText(null);
        txtCari.setForeground(new Color(0,0,0));
        txtCari.setFont(new Font("sansserif",0,14));
    }//GEN-LAST:event_txtCariFocusGained

    private void TFTeleponKaryawanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFTeleponKaryawanKeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_TFTeleponKaryawanKeyTyped

    private void btnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan1ActionPerformed
        clearField();
        changePanel(panelData);
        tabmodel.setRowCount(0);
        serviceKaryawan.loadData(tabmodel);
    }//GEN-LAST:event_btnSimpan1ActionPerformed

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
    private javax.swing.JTextArea TFAlamatKaryawan;
    private javax.swing.JTextField TFEmailKaryawan;
    private javax.swing.JTextField TFIdKaryawan;
    private javax.swing.JTextField TFNamaKaryawan;
    private javax.swing.JTextField TFTeleponKaryawan;
    private swing.Button btnSimpan;
    private swing.Button btnSimpan1;
    private swing.Button btnTambah;
    private javax.swing.JComboBox<String> cbxJabatanKaryawan;
    private javax.swing.JComboBox<String> cbxStatusKaryawan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private swing.Pagination pagination;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panelData;
    private javax.swing.JPanel panelTambah;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
