/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import control.FieldsCard;
import control.ParamCard;
import control.Report;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;

/**
 *
 * @author usER
 */
public class FiturCetakKartu extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private JFrame parent;
    public FiturCetakKartu(JFrame parent) {
        initComponents();
        this.parent = parent;
        style();
        changeType();
        instance("Kartu Karyawan");
    }
    
    private void style() {
        cbxJenisKartu.setBorder(new MatteBorder(1, 1, 1, 1, new Color(185, 185, 185)));
        cbxJenisKartu.setFont(new Font("sansserif",0,20));
        cbxJenisKartu.setBackground(new Color(255,255,255));
        cbxJenisKartu.setForeground(new Color(0,0,0));
        cbxJenisKartu.removeAllItems();
        String[] jenis = new String[]{"Kartu Karyawan","Kartu Membership"};
        for(String jns : jenis) {
            cbxJenisKartu.addItem(jns);
        }
    }
    
    private void type(String lbId, String lbNama, String lbJabatan, String slide) {
        lb_id.setText(lbId);
        lb_nama.setText(lbNama);
        lb_jabatan.setText(lbJabatan);
        clearField();
        instance(slide);
    }
    
    private void changeType() {
        cbxJenisKartu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = cbxJenisKartu.getSelectedIndex();
                switch(index) {
                    case 0: 
                        type("ID Karyawan", "Nama", "Jabatan", "Kartu Karyawan");
                    break;
                    
                    case 1:
                        type("ID Pasien", "Nama", "Level", "Kartu Membership");                        
                    break;
                        
                }
            }
        });
    }
    
//    Instance
    private void instance(String slide) {
        try {
            Report.getInstance().compileReport(slide);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
//    Print karyawn
    private void print() {
        try {
        List<FieldsCard> fields = new ArrayList<>();
        
        String id = txtId.getText();
        String name = txtNama.getText();
        String postion = txtJabatan.getText();
       
        fields.add(new FieldsCard(id, name, postion));
        ImageIcon image1 = new ImageIcon(getClass().getResource("/image/Master Pos Logo 4.png"));
        ImageIcon image2;
        switch (cbxJenisKartu.getSelectedIndex()) {
            case 0:
                image2 = new ImageIcon(getClass().getResource("/image/Master Pos Logo 5.png"));
                break;
            default :
                image2 = new ImageIcon(getClass().getResource("/image/Master Pos Logo 6.png"));
        }
        ParamCard dataPrint = new ParamCard(fields, generateQRCode(), ImageToInpuStream(image1),ImageToInpuStream(image2));
        Report.getInstance().printCard(dataPrint);
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Peringatan", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private InputStream ImageToInpuStream(ImageIcon imageIcon) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(imageIcon.getImage().getWidth(null), imageIcon.getImage().getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(imageIcon.getImage(), 0, 0, null);
        g2.dispose();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    private InputStream generateQRCode() throws WriterException, IOException{
        String id = txtId.getText();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(id, BarcodeFormat.QR_CODE, 75, 75, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    private void clearField() {
        txtId.setText(null);
        txtNama.setText(null);
        txtJabatan.setText(null);
    }
    
    private boolean validation() {
        boolean valid = false;
        
        if(txtId.getText().isEmpty()) {
            String index = (String) cbxJenisKartu.getSelectedItem();
            switch(index) {
                case "Kartu Karyawan": 
                    JOptionPane.showMessageDialog(parent, "Silahkan pilih karyawan");
                break;
                
                case "Kartu Membership":
                    JOptionPane.showMessageDialog(parent, "Silahkan pilih pasien");
                break;
            }
        } else {
            valid = true;
        }
        
        return valid;
    }    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTambah = new javax.swing.JPanel();
        panel2 = new javax.swing.JPanel();
        btnCetak = new swing.Button();
        lb_id = new javax.swing.JLabel();
        lb_jabatan = new javax.swing.JLabel();
        lb_nama = new javax.swing.JLabel();
        txtJabatan = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        cbxJenisKartu = new javax.swing.JComboBox<>();
        btnPilih = new swing.Button();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        panelTambah.setBackground(new java.awt.Color(153, 153, 153));
        panelTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        btnCetak.setForeground(new java.awt.Color(135, 15, 50));
        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/printer.png"))); // NOI18N
        btnCetak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        lb_id.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb_id.setForeground(new java.awt.Color(0, 0, 0));
        lb_id.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_id.setText("ID Karyawan");

        lb_jabatan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb_jabatan.setForeground(new java.awt.Color(0, 0, 0));
        lb_jabatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_jabatan.setText("Jabatan");

        lb_nama.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lb_nama.setForeground(new java.awt.Color(0, 0, 0));
        lb_nama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_nama.setText("Nama");

        txtJabatan.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtJabatan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        txtId.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtId.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        txtNama.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        txtNama.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(185, 185, 185)));

        cbxJenisKartu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnPilih.setBackground(new java.awt.Color(135, 15, 50));
        btnPilih.setForeground(new java.awt.Color(255, 255, 255));
        btnPilih.setText("PILIH");
        btnPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_nama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_id)
                            .addComponent(lb_jabatan))
                        .addGap(43, 43, 43)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJabatan)
                            .addComponent(txtNama)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPilih, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(cbxJenisKartu, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbxJenisKartu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_id, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPilih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(135, 15, 50));

        label1.setBackground(new java.awt.Color(135, 15, 50));
        label1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("CETAK KARTU");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTambahLayout.createSequentialGroup()
                        .addGap(0, 102, Short.MAX_VALUE)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTambahLayout.setVerticalGroup(
            panelTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(267, Short.MAX_VALUE))
        );

        add(panelTambah, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        if(validation()) {
        print();   
        }
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihActionPerformed
        String slide = (String) cbxJenisKartu.getSelectedItem();
        PilihCetakKartu pilih = new PilihCetakKartu(null, true, slide);
        pilih.setVisible(true);
        switch(slide) {
            case "Kartu Karyawan":
                txtId.setText(pilih.modelKaryawan.getIdKaryawan());
                txtNama.setText(pilih.modelKaryawan.getNama());
                txtJabatan.setText(pilih.modelKaryawan.getJabatan());
                break;
            case  "Kartu Membership":
                txtId.setText(pilih.modelPasien.getIdPasien());
                txtNama.setText(pilih.modelPasien.getNama());
                txtJabatan.setText(pilih.modelPasien.getLevel());
                break;
        }
    }//GEN-LAST:event_btnPilihActionPerformed

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnCetak;
    private swing.Button btnPilih;
    private javax.swing.JComboBox<String> cbxJenisKartu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lb_id;
    private javax.swing.JLabel lb_jabatan;
    private javax.swing.JLabel lb_nama;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panelTambah;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtJabatan;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
