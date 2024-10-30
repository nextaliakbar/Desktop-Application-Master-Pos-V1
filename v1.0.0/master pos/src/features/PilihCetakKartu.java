/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package features;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import util.ModelHeaderTable;
import model.ModelKaryawan;
import model.ModelPasien;
import util.ModelRenderTable;
import service.Koneksi;

/**
 *
 * @author usER
 */
public class PilihCetakKartu extends java.awt.Dialog {

    /**
     * Creates new form DialogDetail
     */
    private Connection connection;
    private String slide;
    private DefaultTableModel tabemodel1;
    private DefaultTableModel tabemodel2;
    public ModelKaryawan modelKaryawan;
    public ModelPasien modelPasien;
    private JFrame parent;
    public PilihCetakKartu(java.awt.Frame parent, boolean modal, String slide) {
        super(parent, modal);
        initComponents();
        this.parent = (JFrame) parent;
        setIconImage(new ImageIcon(getClass().getResource("/image/Master Pos Logo 3.png")).getImage());
        connection = Koneksi.getConnection();
        
        this.slide = slide;
        changePanel();
        
        styleTable(scroll1, table1, 4);
        tabemodel1 = (DefaultTableModel) table1.getModel();
        tabemodel1.setRowCount(0);
        tampilData1();
        modelKaryawan = new ModelKaryawan();
        TableRowSorter rowSorter1 = new TableRowSorter<>(tabemodel1);
        table1.setRowSorter(rowSorter1);
        
        styleTable(scroll2, table2, 4);
        tabemodel2 = (DefaultTableModel) table2.getModel();
        tabemodel2.setRowCount(0);
        tampilData2();
        modelPasien = new ModelPasien();
        TableRowSorter rowSorter2 = new TableRowSorter<>(tabemodel2);
        table2.setRowSorter(rowSorter2);
        
        
        searchEmployee(txtCari1, rowSorter1);
        searchPasien(txtCari2, rowSorter2);
    }
    
//  Style Table
    private void styleTable(JScrollPane scroll, JTable table, int columnTable) {
        scroll.getViewport().setBackground(new Color(255, 255, 255));
        JPanel pn = new JPanel();
        pn.setBackground(new Color(255, 255, 255));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, pn);
        scroll.setBorder(new EmptyBorder(5, 10, 5, 10));
        table.setRowHeight(35);
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        table.setDefaultRenderer(Object.class, new ModelRenderTable(columnTable));
    }
    
//  Tampil Data Karyawan
    private void tampilData1() {
        String query = "SELECT ID_Karyawan, Nama, Jabatan FROM karyawan";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String idKaryawan = rst.getString("ID_Karyawan");
                String nama = rst.getString("Nama");
                String jabatan = rst.getString("Jabatan");
                tabemodel1.addRow(new String[]{idKaryawan, nama, jabatan});
            }
            pst.close();
            rst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
//  Tampil Data Tindakan
    private void tampilData2() {
        String query = "SELECT ID_Pasien, Nama, Level FROM pasien WHERE Level='Member'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kode = rst.getString("ID_Pasien");
                String nama = rst.getString("Nama");
                String level = rst.getString("Level");
                
                tabemodel2.addRow(new Object[]{kode, nama, level});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void changePanel() {
        if(slide.equals("Kartu Karyawan")) {
            showPanel(panel1);
        } else if(slide.equals("Kartu Membership")) {
            showPanel(panel2);   
        }
    }
    
    private void showPanel(JPanel pn) {
        panel.removeAll();
        panel.add(pn);
        panel.repaint();
        panel.revalidate();
    }
    
//  Pilih Karyawan
    private void pilihKaryawan() {
        int selectRow = table1.getSelectedRow();
        if(selectRow != -1) {
        String id = (String) table1.getValueAt(selectRow, 0);
        String nama = (String) table1.getValueAt(selectRow, 1);
        String jabatan = (String) table1.getValueAt(selectRow, 2);
        
        modelKaryawan.setIdKaryawan(id);
        modelKaryawan.setNama(nama);
        modelKaryawan.setJabatan(jabatan);
        dispose();
        } else {
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Karyawan Terlebih Dahulu");
        }
    }
    
//  Pilih Tindakan
    private void pilihPasien() {
        int selectRow = table2.getSelectedRow();
        if(selectRow != -1) {
            String id = (String) table2.getValueAt(selectRow, 0);
            String nama = (String) table2.getValueAt(selectRow, 1);
            String level = (String) table2.getValueAt(selectRow, 2);
            
            modelPasien.setIdPasien(id);
            modelPasien.setNama(nama);
            modelPasien.setLevel(level);
            dispose();
        } else {
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Pasien Terlebih Dahulu");
        }
    }
    
//    Cari karyawan
    private void searchEmployee(JTextField field, TableRowSorter rowSorter) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = field.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = field.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                } 
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }
//    Cari Pasien
    private void searchPasien(JTextField field, TableRowSorter rowSorter) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = field.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = field.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                } 
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
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
        scroll1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        label1 = new javax.swing.JLabel();
        button1 = new swing.Button();
        txtCari1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        scroll2 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        label3 = new javax.swing.JLabel();
        button2 = new swing.Button();
        txtCari2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        panel.setLayout(new java.awt.CardLayout());

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        table1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Karyawan", "Nama", "Jabatan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll1.setViewportView(table1);

        label1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        label1.setForeground(new java.awt.Color(135, 15, 50));
        label1.setText("Data Karyawan");

        button1.setBackground(new java.awt.Color(135, 15, 50));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Pilih");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        txtCari1.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari1.setForeground(new java.awt.Color(185, 185, 185));
        txtCari1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari1.setText("Cari Berdasarkan ID Karyawan Atau Nama Karyawan");
        txtCari1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        txtCari1.setOpaque(false);
        txtCari1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCari1FocusGained(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll1)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(label1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel.add(panel1, "card2");

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        table2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Pasien", "Nama", "Level"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll2.setViewportView(table2);

        label3.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        label3.setForeground(new java.awt.Color(135, 15, 50));
        label3.setText("Data Pasien");

        button2.setBackground(new java.awt.Color(135, 15, 50));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Pilih");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        txtCari2.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCari2.setForeground(new java.awt.Color(185, 185, 185));
        txtCari2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCari2.setText("Cari Berdasarkan ID Pasien Atau Nama Pasien");
        txtCari2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        txtCari2.setOpaque(false);
        txtCari2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCari2FocusGained(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll2)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(label3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel.add(panel2, "card2");

        add(panel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    
    private void focusGained(JTextField txtField) {
        txtField.setText(null);
        txtField.setFont(new Font("sansserif", 0, 14));
        txtField.setForeground(new Color(0, 0,0));
    }
    
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        pilihKaryawan();
    }//GEN-LAST:event_button1ActionPerformed

    private void txtCari1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCari1FocusGained
        focusGained(txtCari1);
    }//GEN-LAST:event_txtCari1FocusGained

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        pilihPasien();
    }//GEN-LAST:event_button2ActionPerformed

    private void txtCari2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCari2FocusGained
        focusGained(txtCari2);
    }//GEN-LAST:event_txtCari2FocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PilihCetakKartu dialog = new PilihCetakKartu(new java.awt.Frame(), true, "");
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
    private swing.Button button1;
    private swing.Button button2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label3;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JScrollPane scroll1;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JTable table1;
    private javax.swing.JTable table2;
    private javax.swing.JTextField txtCari1;
    private javax.swing.JTextField txtCari2;
    // End of variables declaration//GEN-END:variables

}
