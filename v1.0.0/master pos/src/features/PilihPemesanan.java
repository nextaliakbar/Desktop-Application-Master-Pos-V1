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
import java.util.ArrayList;
import java.util.List;
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
import model.ModelPemesanan;
import model.ModelPengguna;
import util.ModelRenderTable;
import model.ModelSupplier;
import service.Koneksi;

/**
 *
 * @author usER
 */
public class PilihPemesanan extends java.awt.Dialog {

    /**
     * Creates new form DialogDetail
     */
    private Connection connection;
    private DefaultTableModel tabemodel2;
    public ModelPemesanan modelPemesanan;
    private JFrame parent;
    private final String query = "SELECT pmsn.No_Pemesanan, DATE_FORMAT(pmsn.Tanggal_Pemesanan, '%d - %M - %Y') AS Tanggal, "
            + "pmsn.ID_Supplier, spl.Nama, pgn.ID_Pengguna, pgn.Nama, pmsn.Total_Pemesanan FROM pemesanan pmsn JOIN supplier spl "
            + "ON pmsn.ID_Supplier=spl.ID_Supplier JOIN pengguna pgn ON pmsn.ID_Pengguna=pgn.ID_Pengguna ";
    public PilihPemesanan(java.awt.Frame parent, boolean modal, String slide) {
        super(parent, modal);
        initComponents();
        this.parent = (JFrame) parent;
        setIconImage(new ImageIcon(getClass().getResource("/image/Master Pos Logo 3.png")).getImage());
        connection = Koneksi.getConnection();
                
        styleTable(scroll2, table2, 7);
        tabemodel2 = (DefaultTableModel) table2.getModel();
        modelPemesanan = new ModelPemesanan();
        tampilData(slide);
        TableRowSorter rowSorter = new TableRowSorter<>(tabemodel2);
        table2.setRowSorter(rowSorter);
        
        search(txtCari2, rowSorter);
    }
    
    private void tampilData(String slide) {
        if(slide.equals("Pengeluaran")) {
            tampilData1(slide);
        } else {
            tampilData2(slide);
        }
    }
    
//  Style Table
    private void styleTable(JScrollPane scroll, JTable table, int columnTable) {
        scroll.getViewport().setBackground(new Color(255, 255, 255));
        JPanel pn = new JPanel();
        pn.setBackground(new Color(255, 255, 255));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, pn);
        scroll.setBorder(new EmptyBorder(5, 10, 5, 10));
        table.setRowHeight(40);
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());
        table.setDefaultRenderer(Object.class, new ModelRenderTable(columnTable));
    }
    

    private List<String> getDetailJenis() {
        List<String> listDetail = new ArrayList<>();
        String query = "SELECT Detail_Jenis FROM detail_pengeluaran";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                listDetail.add(rst.getString("Detail_Jenis"));
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return listDetail;
    }
    
    private void addRowTable(ResultSet rst, DefaultTableModel tabmodel, String slide) throws Exception{
        String noPemesanan = rst.getString("No_Pemesanan");
        String tglPemesanan = rst.getString("Tanggal");
        String idSupplier = rst.getString("ID_Supplier");
        String namaSupplier = rst.getString("spl.Nama");
        String idPengguna = rst.getString("ID_Pengguna");
        String namaPengguna = rst.getString("pgn.Nama");
        int total = rst.getInt("Total_Pemesanan");
        if(slide.equals("Pengeluaran")) {
            boolean valid = true;
                for(String listDetail : getDetailJenis()) {
                    if(listDetail.equals(noPemesanan)) {
                        valid = false;
                    }
                }
            if(valid) {
                tabmodel.addRow(new Object[]{noPemesanan, tglPemesanan, idSupplier, namaSupplier, idPengguna, namaPengguna, total});
            }
        } else {
            tabmodel.addRow(new Object[]{noPemesanan, tglPemesanan, idSupplier, namaSupplier, idPengguna, namaPengguna, total});
        }
    }
                
//  Pengeluaran Pemesanan
    private void tampilData1(String slide) {
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                addRowTable(rst, tabemodel2, slide);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    //  Restok Pemesanan
    private void tampilData2(String slide) {
        try {
            PreparedStatement pst = connection.prepareStatement(query.concat("WHERE Status_Pemesanan='Dikirim'"));
            ResultSet rst = pst.executeQuery();
            while(rst.next()) {
                addRowTable(rst, tabemodel2, slide);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
//  Pilih Pemesanan
    private void pilihPemesanan() {
        ModelSupplier modelSupplier = new ModelSupplier();
        ModelPengguna modelPengguna = new ModelPengguna();
        int selectRow = table2.getSelectedRow();
        if(selectRow != -1) {
            modelPemesanan.setNoPemesanan((String) table2.getValueAt(selectRow, 0));
            modelPemesanan.setTglPemesanan((String) table2.getValueAt(selectRow, 1));
            modelSupplier.setIdSupplier((String) table2.getValueAt(selectRow, 2));
            modelSupplier.setNamaSupplier((String) table2.getValueAt(selectRow, 3));
            modelPemesanan.setModelSupplier(modelSupplier);
            modelPengguna.setIdpengguna((String) table2.getValueAt(selectRow, 4));
            modelPengguna.setNama((String) table2.getValueAt(selectRow, 5));
            modelPemesanan.setModelPengguna(modelPengguna);
            modelPemesanan.setTotalPemesanan(String.valueOf(Integer.parseInt(table2.getValueAt(selectRow, 6).toString())));
            dispose();
        } else {
            JOptionPane.showMessageDialog(parent, "Silahkan Pilih Pemesanan Terlebih Dahulu");
        }
    }
    
//    Cari Pemesanan
    private void search(JTextField field, TableRowSorter rowSorter) {
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
        panel2 = new javax.swing.JPanel();
        scroll2 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        label2 = new javax.swing.JLabel();
        button2 = new swing.Button();
        txtCari2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        panel.setLayout(new java.awt.CardLayout());

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        table2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Pemesanan", "Tanggal Pemesanan", "ID Supplier", "Supplier", "ID Pengguna", "Pemesan", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll2.setViewportView(table2);
        if (table2.getColumnModel().getColumnCount() > 0) {
            table2.getColumnModel().getColumn(2).setMinWidth(0);
            table2.getColumnModel().getColumn(2).setPreferredWidth(0);
            table2.getColumnModel().getColumn(2).setMaxWidth(0);
            table2.getColumnModel().getColumn(4).setMinWidth(0);
            table2.getColumnModel().getColumn(4).setPreferredWidth(0);
            table2.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        label2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        label2.setForeground(new java.awt.Color(135, 15, 50));
        label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label2.setText("Data Pemesanan Barang");

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
        txtCari2.setText("Cari Berdasarkan No Pemesanan atau Tanggal");
        txtCari2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        txtCari2.setOpaque(false);
        txtCari2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCari2FocusGained(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-2.png"))); // NOI18N

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll2)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCari2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
            .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCari2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(3, 3, 3)))
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

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        pilihPemesanan();
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
                PilihPemesanan dialog = new PilihPemesanan(new java.awt.Frame(), true, "");
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
    private swing.Button button2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel label2;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel2;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JTable table2;
    private javax.swing.JTextField txtCari2;
    // End of variables declaration//GEN-END:variables

}
