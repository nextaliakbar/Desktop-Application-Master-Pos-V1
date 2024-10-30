/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import component.PanelLoading;
import component.PanelVerifyCode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import model.ModelPengguna;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import service.Koneksi;
import service.ServiceLogin;

/**
 *
 * @author usER
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    private Animator animatorLogin;
    private Animator animatorForgot;
    private boolean login;
    private PanelVerifyCode panelVerify;
    private PanelLoading panelLoading;
    private Koneksi connoection;
    private ServiceLogin serviceLogin;
    private JFrame parent;
    public Login() {
        initComponents();
        this.parent = this;
        connoection = new Koneksi();
//        connoection.createDatabase();
        serviceLogin = new ServiceLogin();
        setIconImage(new ImageIcon(getClass().getResource("/image/Master Pos Logo 3.png")).getImage());
        initiation();
    }
    
    private void initiation() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        TimingTarget targetLogin = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if(login) {
                    background.setAnimate(fraction);
                } else {
                    background.setAnimate(1f - fraction);
                }
            }

            @Override
            public void end() {
                if(login) {
                    panelLogin.setVisible(false);
                    background.setShowPaint(true);
                    panelForgot.setAlpha(0);
                    panelForgot.setVisible(true);
                    animatorForgot.start();
                } else {
                    enableLogin(true);
                    txtUserOrEmail.grabFocus();
                }
            }
          
        };
        
        TimingTarget targetForgot = new TimingTargetAdapter(){
            @Override
            public void timingEvent(float fraction) {
                if(login) {
                    panelForgot.setAlpha(fraction);
                } else {
                    panelForgot.setAlpha(1f - fraction);
                }
            }

            @Override
            public void end() {
                if(login == false) {
                    panelForgot.setVisible(false);
                    background.setShowPaint(false);
                    background.setAnimate(1);
                    panelLogin.setVisible(true);
                    animatorLogin.start();
                }
            }
           
        };
        
        animatorLogin = new Animator(1500, targetLogin);
        animatorForgot = new Animator(500, targetForgot);
        animatorLogin.setResolution(0);
        animatorForgot.setResolution(0);
        
        panelVerify = new PanelVerifyCode();
        panelLoading = new PanelLoading();
        background.add(panelLoading);
        background.setComponentZOrder(panelLoading, 0);
        background.setLayer(panelVerify, JLayeredPane.POPUP_LAYER);
        background.add(panelVerify);
        actionVerify();
    }
    
    private void masuk() {
        ModelPengguna modelPengguna = new ModelPengguna();
        String userOrEmail = txtUserOrEmail.getText();
        String password = txtPassword.getText();
        modelPengguna.setUsername(userOrEmail);
        modelPengguna.setEmail(userOrEmail);
        modelPengguna.setPassword(password);
        
        boolean action = true;
        if(userOrEmail.equals("")) {
            txtUserOrEmail.setHelperText("Bidang ini harus diisi");
            txtUserOrEmail.grabFocus();
            action = false;
        }

        if(password.equals("")) {
            txtPassword.setHelperText("Bidang ini harus diisi");
            txtPassword.grabFocus();
            action = false;
        }

        if(action) {
            serviceLogin.login(this, modelPengguna,panelLoading, this);
        }
    }
    
    private void lupaPassword() {
        ModelPengguna modelPengguna = new ModelPengguna();
        String newPass = txtNewPass.getText();
        String confir = txtConfirPass.getText();
        String email = txtEmail.getText();  
        modelPengguna.setPassword(newPass);
        modelPengguna.setConfirPass(confir);
        modelPengguna.setEmail(email);
        boolean action = true;
        
        if(newPass.equals("")) {
            txtNewPass.setHelperText("Bidang ini harus diisi");
            txtNewPass.grabFocus();
            action = false;
        }
        
        if(confir.equals("")) {
            txtConfirPass.setHelperText("Bidang ini harus diisi");
            txtConfirPass.grabFocus();
            action = false;
        }
        
        if(email.equals("")) {
           txtEmail.setHelperText("Bidang ini harus diisi");
           txtEmail.grabFocus();
           action = false; 
        }
        
        if(action) {
            background.setComponentZOrder(panelLoading, 1);
            if(serviceLogin.checkEmail(this, modelPengguna)) {
            serviceLogin.getVerifyEmail(this, modelPengguna, panelVerify, panelLoading);
            }
        }
    }
    
    private void actionVerify() {
        panelVerify.okEventButto(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelPengguna modelPengguna = new ModelPengguna();
                String verifyCode = panelVerify.getInputCode();
                String password = txtNewPass.getText();
                String email = txtEmail.getText();
                modelPengguna.setKode_verifikasi(verifyCode);
                modelPengguna.setPassword(password);
                modelPengguna.setEmail(email);
                if(serviceLogin.cekVerifyCode(parent,modelPengguna)) {
                    serviceLogin.doneVerify(parent, modelPengguna);
                    panelVerify.setVisible(false);
                    login = false;
                    clearField();
                    animatorForgot.start();
                }
            }
        });
        
        panelVerify.cancelEventButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelPengguna modelPengguna = new ModelPengguna();
                String email = txtEmail.getText();
                modelPengguna.setEmail(email);
                serviceLogin.cancelVerify(modelPengguna);
                panelVerify.setVisible(false);
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

        background = new swing.Background();
        panelLogin = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        txtPassword = new swing.PasswordField();
        btnMasuk = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lbForgot = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUserOrEmail = new swing.TextField();
        panelForgot = new swing.PanelTransparant();
        panel2 = new javax.swing.JPanel();
        btnVerify = new javax.swing.JButton();
        lbBack = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new swing.TextField();
        txtNewPass = new swing.TextField();
        txtConfirPass = new swing.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setLayout(new java.awt.CardLayout());

        panelLogin.setBackground(new java.awt.Color(255, 255, 255));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        btnMasuk.setBackground(new java.awt.Color(245, 34, 94));
        btnMasuk.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnMasuk.setForeground(new java.awt.Color(255, 255, 255));
        btnMasuk.setText("Masuk");
        btnMasuk.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasukActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(150, 150, 150));
        jLabel1.setText("Lupa Password?");

        lbForgot.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbForgot.setForeground(new java.awt.Color(245, 34, 94));
        lbForgot.setText("Klik disini");
        lbForgot.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbForgot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbForgotMouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Master Pos Logo 2.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(150, 150, 150));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Halo Selamat Datang");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(150, 150, 150));
        jLabel5.setText("Silahkan Masuk");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        txtUserOrEmail.setLabelText("Username atau Email");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUserOrEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbForgot)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(txtUserOrEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbForgot))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap(631, Short.MAX_VALUE)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(632, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                .addContainerGap(128, Short.MAX_VALUE)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(232, Short.MAX_VALUE))
        );

        background.add(panelLogin, "card2");

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        btnVerify.setBackground(new java.awt.Color(245, 34, 94));
        btnVerify.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnVerify.setForeground(new java.awt.Color(255, 255, 255));
        btnVerify.setText("Verifikasi");
        btnVerify.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnVerify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerifyActionPerformed(evt);
            }
        });

        lbBack.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbBack.setForeground(new java.awt.Color(245, 34, 94));
        lbBack.setText("Kembali Masuk");
        lbBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbBackMouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Master Pos Logo 2.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(150, 150, 150));
        jLabel8.setText("Lupa Password");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(150, 150, 150));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Silahkan lengkapi form dibawah ini");

        txtEmail.setLabelText("Email");

        txtNewPass.setLabelText("Password Baru");

        txtConfirPass.setLabelText("Konfrimasi Password");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVerify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(lbBack)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNewPass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtConfirPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(txtNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(txtConfirPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnVerify, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbBack)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelForgotLayout = new javax.swing.GroupLayout(panelForgot);
        panelForgot.setLayout(panelForgotLayout);
        panelForgotLayout.setHorizontalGroup(
            panelForgotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelForgotLayout.createSequentialGroup()
                .addContainerGap(575, Short.MAX_VALUE)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(574, Short.MAX_VALUE))
        );
        panelForgotLayout.setVerticalGroup(
            panelForgotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelForgotLayout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );

        background.add(panelForgot, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerifyActionPerformed
        lupaPassword();
    }//GEN-LAST:event_btnVerifyActionPerformed

    private void lbBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbBackMouseClicked
        login = false;
        clearField();
        animatorForgot.start();
    }//GEN-LAST:event_lbBackMouseClicked

    private void lbForgotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbForgotMouseClicked
        if(!animatorLogin.isRunning()) {
            login = true;
            animatorLogin.start();
            enableLogin(false);
            clearField();
        }
    }//GEN-LAST:event_lbForgotMouseClicked

    private void btnMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasukActionPerformed
        masuk();
    }//GEN-LAST:event_btnMasukActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void enableLogin(boolean action) {
        txtUserOrEmail.setEditable(action);
        txtPassword.setEditable(action);
        btnMasuk.setEnabled(action);
    }
    
    private void clearField() {
        txtUserOrEmail.setText("");
        txtPassword.setText("");
        txtUserOrEmail.setHelperText("");
        txtPassword.setHelperText("");
        txtNewPass.setText("");
        txtNewPass.setHelperText("");
        txtConfirPass.setText("");
        txtConfirPass.setHelperText("");
        txtEmail.setText("");
        txtEmail.setHelperText("");
        panelLoading.setVisible(false);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Background background;
    private javax.swing.JButton btnMasuk;
    private javax.swing.JButton btnVerify;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lbBack;
    private javax.swing.JLabel lbForgot;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private swing.PanelTransparant panelForgot;
    private javax.swing.JPanel panelLogin;
    private swing.TextField txtConfirPass;
    private swing.TextField txtEmail;
    private swing.TextField txtNewPass;
    private swing.PasswordField txtPassword;
    private swing.TextField txtUserOrEmail;
    // End of variables declaration//GEN-END:variables
}
