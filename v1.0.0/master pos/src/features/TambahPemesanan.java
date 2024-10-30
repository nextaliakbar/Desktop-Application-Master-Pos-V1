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
import model.ModelBarang;
import util.ModelHeaderTable;
import util.ModelRenderTable;
import model.ModelSupplier;
import service.Koneksi;

/**
 *
 * @author usER
 */
public class TambahPemesanan extends java.awt.Dialog {

    /**
     * Creates new form DialogDetail
     */
    private Connection connection;
    private String slide;
    private DefaultTableModel tabemodel1;
    private DefaultTableModel tabemodel2;
    public  ModelSupplier modelSupplier;
    public  ModelBarang modelBarang;
    private JFrame parent;
    public TambahPemesanan(java.awt.Frame parent, boolean modal, String slide) {
        super(parent, modal);
        initComponents();
        this.parent = (JFrame) parent;
        setIconImage(new ImageIcon(getClass().getResource("/image/Master Pos Logo 3.png")).getImage());
        connection = Koneksi.getConnection();
        
        this.slide = slide;
        changePanel();
        
        styleTable(scrollSuplr, tableSplr, 3);
        tabemodel1 = (DefaultTableModel) tableSplr.getModel();
        modelSupplier = new ModelSupplier();
        TableRowSorter rowSorter1 = new TableRowSorter<>(tabemodel1);
        tableSplr.setRowSorter(rowSorter1);
        tampilData1();
        
        styleTable(scrollBrg, tableBrg, 6);
        tabemodel2 = (DefaultTableModel) tableBrg.getModel();
        modelBarang = new ModelBarang();
        TableRowSorter rowSorter2 = new TableRowSorter<>(tabemodel2);
        tableBrg.setRowSorter(rowSorter2);
        tampilData2();
        
        searchAction(txtCariSupplier, rowSorter1);
        searchEmployee(txtCariBrg, rowSorter2);
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
    
//  Tampil Data Supplier
    private void tampilData1() {
        String query = "SELECT ID_Supplier, Nama, Alamat FROM supplier";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String idSupplier = rst.getString("ID_Supplier");
                String nama = rst.getString("Nama");
                String alamat = rst.getString("Alamat");
                tabemodel1.addRow(new String[]{idSupplier, nama, alamat});
            }
            pst.close();
            rst.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
//  Tampil Data Barang
    private void tampilData2() {
        String query = "SELECT Kode_Barang, Nama_Barang, Satuan, Harga_Beli, Harga_Jual, Stok FROM barang ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                String kodeBrg = rst.getString("Kode_Barang");
                String namaBrg = rst.getString("Nama_Barang");
                String satuan = rst.getString("Satuan");
                int hargaBeli = rst.getInt("Harga_Beli");
                int hargaJual = rst.getInt("Harga_Jual");
                int stok = rst.getInt("Stok");
                
                tabemodel2.addRow(new Object[]{kodeBrg, namaBrg, satuan, hargaBeli, hargaJual, stok});
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void changePanel() {
        if(slide.equals("Slide-1")) {
            showPanel(panelSupplier);
        } else if(slide.equals("Slide-2")) {
            showPanel(panelBrg);   
        }
    }
    
    private void showPanel(JPanel pn) {
        panel.removeAll();
        panel.add(pn);
        panel.repaint();
        panel.revalidate();
    }
    
//  Pilih Supplier
    private void pilihSupplier() {
        int selectRow = tableSplr.getSelectedRow();
        if(selectRow != -1) {
            String idSupplier = (String) tableSplr.getValueAt(selectRow, 0);
            String namaSupplier = (String) tableSplr.getValueAt(selectRow, 1);
            String alamat = (String) tableSplr.getValueAt(selectRow, 2);
            modelSupplier.setIdSupplier(idSupplier);
            modelSupplier.setNamaSupplier(namaSupplier);
            modelSupplier.setAlamatSupplier(alamat);
            dispose();
        } else {
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Supplier Terlebih Dahulu");
        }
    }
    
//  Pilih Barang
    private void pilihBarang() {
        int selectRow = tableBrg.getSelectedRow();
        if(selectRow != -1) {
        String kodeBrg = (String) tableBrg.getValueAt(selectRow, 0);
        String namaBrg = (String) tableBrg.getValueAt(selectRow, 1);
        String satuan = (String) tableBrg.getValueAt(selectRow, 2);
        int hargaBeli = (int) tableBrg.getValueAt(selectRow, 3);
        int hargaJual = (int) tableBrg.getValueAt(selectRow, 4);
        int stok = (int) tableBrg.getValueAt(selectRow, 5);
        
        modelBarang.setKode_Barang(kodeBrg);
        modelBarang.setNama_Barang(namaBrg);
        modelBarang.setSatuan(satuan);
        modelBarang.setHarga_Beli(hargaBeli);
        modelBarang.setHarga_Jual(hargaJual);
        modelBarang.setStok(stok);
        dispose();
        } else {
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Barang Terlebih Dahulu");
        }
    }
    
    
//    Cari Supplier
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
                
            }
        });
    }
//    Cari Barang
    private void searchAction(JTextField field, TableRowSorter rowSorter) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = field.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = field.getText();
                if(text.trim().length() == 0) {
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
        panelSupplier = new javax.swing.JPanel();
        scrollSuplr = new javax.swing.JScrollPane();
        tableSplr = new javax.swing.JTable();
        label2 = new javax.swing.JLabel();
        btnPilihSupplier = new swing.Button();
        txtCariSupplier = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        panelBrg = new javax.swing.JPanel();
        scrollBrg = new javax.swing.JScrollPane();
        tableBrg = new javax.swing.JTable();
        label1 = new javax.swing.JLabel();
        btnPilihBrg = new swing.Button();
        txtCariBrg = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        panel.setLayout(new java.awt.CardLayout());

        panelSupplier.setBackground(new java.awt.Color(255, 255, 255));

        tableSplr.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tableSplr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Supplier", "Nama Supplier", "Alamat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollSuplr.setViewportView(tableSplr);
        if (tableSplr.getColumnModel().getColumnCount() > 0) {
            tableSplr.getColumnModel().getColumn(0).setMinWidth(100);
            tableSplr.getColumnModel().getColumn(0).setPreferredWidth(100);
            tableSplr.getColumnModel().getColumn(0).setMaxWidth(100);
            tableSplr.getColumnModel().getColumn(1).setMinWidth(200);
            tableSplr.getColumnModel().getColumn(1).setPreferredWidth(200);
            tableSplr.getColumnModel().getColumn(1).setMaxWidth(200);
        }

        label2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        label2.setForeground(new java.awt.Color(135, 15, 50));
        label2.setText("Data Supplier");

        btnPilihSupplier.setBackground(new java.awt.Color(135, 15, 50));
        btnPilihSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnPilihSupplier.setText("Pilih");
        btnPilihSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihSupplierActionPerformed(evt);
            }
        });

        txtCariSupplier.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCariSupplier.setForeground(new java.awt.Color(185, 185, 185));
        txtCariSupplier.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCariSupplier.setText("Cari Berdasarkan ID Supplier atau Nama Supplier");
        txtCariSupplier.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        txtCariSupplier.setOpaque(false);
        txtCariSupplier.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariSupplierFocusGained(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panelSupplierLayout = new javax.swing.GroupLayout(panelSupplier);
        panelSupplier.setLayout(panelSupplierLayout);
        panelSupplierLayout.setHorizontalGroup(
            panelSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollSuplr)
            .addGroup(panelSupplierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSupplierLayout.createSequentialGroup()
                        .addComponent(btnPilihSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCariSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSupplierLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(label2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelSupplierLayout.setVerticalGroup(
            panelSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSupplierLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPilihSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCariSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scrollSuplr, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel.add(panelSupplier, "card2");

        panelBrg.setBackground(new java.awt.Color(255, 255, 255));

        tableBrg.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tableBrg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Satuan", "Harga Beli", "Harga Jual", "Stok"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollBrg.setViewportView(tableBrg);
        if (tableBrg.getColumnModel().getColumnCount() > 0) {
            tableBrg.getColumnModel().getColumn(0).setMinWidth(125);
            tableBrg.getColumnModel().getColumn(0).setPreferredWidth(125);
            tableBrg.getColumnModel().getColumn(0).setMaxWidth(125);
            tableBrg.getColumnModel().getColumn(1).setPreferredWidth(175);
        }

        label1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        label1.setForeground(new java.awt.Color(135, 15, 50));
        label1.setText("Data Barang");

        btnPilihBrg.setBackground(new java.awt.Color(135, 15, 50));
        btnPilihBrg.setForeground(new java.awt.Color(255, 255, 255));
        btnPilihBrg.setText("Pilih");
        btnPilihBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihBrgActionPerformed(evt);
            }
        });

        txtCariBrg.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        txtCariBrg.setForeground(new java.awt.Color(185, 185, 185));
        txtCariBrg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCariBrg.setText("Cari Berdasarkan Kode Barang atau Nama Barang");
        txtCariBrg.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        txtCariBrg.setOpaque(false);
        txtCariBrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariBrgFocusGained(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panelBrgLayout = new javax.swing.GroupLayout(panelBrg);
        panelBrg.setLayout(panelBrgLayout);
        panelBrgLayout.setHorizontalGroup(
            panelBrgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollBrg)
            .addGroup(panelBrgLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBrgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBrgLayout.createSequentialGroup()
                        .addComponent(btnPilihBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCariBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBrgLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(label1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBrgLayout.setVerticalGroup(
            panelBrgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBrgLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBrgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPilihBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCariBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scrollBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel.add(panelBrg, "card2");

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

    private void btnPilihBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihBrgActionPerformed
        pilihBarang();
    }//GEN-LAST:event_btnPilihBrgActionPerformed

    private void btnPilihSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihSupplierActionPerformed
        pilihSupplier();
    }//GEN-LAST:event_btnPilihSupplierActionPerformed

    private void txtCariBrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariBrgFocusGained
        focusGained(txtCariBrg);
    }//GEN-LAST:event_txtCariBrgFocusGained

    private void txtCariSupplierFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariSupplierFocusGained
        focusGained(txtCariSupplier);
    }//GEN-LAST:event_txtCariSupplierFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TambahPemesanan dialog = new TambahPemesanan(new java.awt.Frame(), true, "");
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
    private swing.Button btnPilihBrg;
    private swing.Button btnPilihSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelBrg;
    private javax.swing.JPanel panelSupplier;
    private javax.swing.JScrollPane scrollBrg;
    private javax.swing.JScrollPane scrollSuplr;
    private javax.swing.JTable tableBrg;
    private javax.swing.JTable tableSplr;
    private javax.swing.JTextField txtCariBrg;
    private javax.swing.JTextField txtCariSupplier;
    // End of variables declaration//GEN-END:variables

}
