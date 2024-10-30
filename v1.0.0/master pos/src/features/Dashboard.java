/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package features;
import com.raven.datechooser.DateBetween;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import component.Chart;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import util.ModelCard;
import model.ModelDashboard;
import util.ModelHeaderTable;
import model.ModelPengguna;
import service.ServiceDashboard;

/**
 *
 * @author usER
 */
public class Dashboard extends javax.swing.JPanel {

    /**
     * Creates new form FiturBarang
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private ServiceDashboard serviceDashboard = new ServiceDashboard();
    private ModelPengguna modelPengguna;
    private JFrame parent;
    public Dashboard(JFrame parent, ModelPengguna modelPengguna) {
        initComponents();
        this.parent = parent;
        this.modelPengguna = modelPengguna;
        initiationCard();
        viewChart(0);
        serviceDashboard.lastReseravsi(table2);
        table2.scrollPane(scroll2);
        styleTable( table2);
        actionRange();
        actionDetail();
    }
    
    private ModelCard modelCard(String title, double values, Icon icon, String textButton) {
        return new ModelCard(title, values, icon, textButton);
    }
    
    private void initiationCard() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        String tglSekarang = now.format(format);
        ModelDashboard modelDashboard = new ModelDashboard(tglSekarang, tglSekarang);
        
        double revenuePemeriksaan = serviceDashboard.pendapatanPemeriksaan(modelDashboard);
        double totalPenjualan = serviceDashboard.totalPenjualan(modelDashboard);
        double keuntunganPenjualan = serviceDashboard.keuntunganPenjualan(modelDashboard);
        double pengeluaran = serviceDashboard.pengeluaran(modelDashboard);
        
        this.card11.setData(modelCard("Pendapatan Pemeriksaan", revenuePemeriksaan, new ImageIcon(getClass().getResource("/image/coins.png")),"Lihat Detail"));
        this.card12.setData(modelCard("Total Penjualan", totalPenjualan, new ImageIcon(getClass().getResource("/image/sales.png")),"Lihat Detail"));
        this.card13.setData(modelCard("Keuntungan Penjualan", keuntunganPenjualan, new ImageIcon(getClass().getResource("/image/financial-profit.png")), "Informasi"));
        this.card14.setData(modelCard("Pengeluaran", pengeluaran, new ImageIcon(getClass().getResource("/image/expenses.png")), "Lihat Detail"));
    }
    
    private void actionRange() {
        this.card11.setRange(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                String fromDate = sdf.format(date.getFromDate());
                String toDate = sdf.format(date.getToDate());
                ModelDashboard modelDashboard = new ModelDashboard(fromDate, toDate);
                double value = serviceDashboard.pendapatanPemeriksaan(modelDashboard);
                card11.setData(modelCard("Pendapatan Pemeriksaan", value, new ImageIcon(getClass().getResource("/image/coins.png")),"Lihat Detail"));
            }    
        });
        
        this.card12.setRange(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                String fromDate = sdf.format(date.getFromDate());
                String toDate = sdf.format(date.getToDate());
                ModelDashboard modelDashboard = new ModelDashboard(fromDate, toDate);
                double value = serviceDashboard.totalPenjualan(modelDashboard);
                card12.setData(modelCard("Total Penjualan", value, new ImageIcon(getClass().getResource("/image/sales.png")),"Lihat Detail"));
            }
        });
        
        this.card13.setRange(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                String fromDate = sdf.format(date.getFromDate());
                String toDate = sdf.format(date.getToDate());
                ModelDashboard modelDashboard = new ModelDashboard(fromDate, toDate);
                double value = serviceDashboard.keuntunganPenjualan(modelDashboard);
                card13.setData(modelCard("Keuntungan Penjualan", value, new ImageIcon(getClass().getResource("/image/financial-profit.png")), "Informasi"));
            }
        });
        
        this.card14.setRange(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                String fromDate = sdf.format(date.getFromDate());
                String toDate = sdf.format(date.getToDate());
                ModelDashboard modelDashboard = new ModelDashboard(fromDate, toDate);
                double value = serviceDashboard.pengeluaran(modelDashboard);
                card14.setData(modelCard("Pengeluaran", value, new ImageIcon(getClass().getResource("/image/expenses.png")), "Lihat Detail"));
            }
            
        });
    }
    
    private void actionDetail() {
        this.card11.viewDetail(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detail(new FiturPemeriksaan(parent, modelPengguna));
            }
        });
        
        this.card12.viewDetail(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detail(new FiturPenjualan(parent, modelPengguna));
            }
        });
        
        this.card13.viewDetail(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parent, "Keuntungan penjualan merupakan total perhitungan\n"
                + "Dari selisih harga jual dengan harga beli barang\nPada transaksi penjualan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        this.card14.viewDetail(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detail(new FiturPengeluaran(parent,modelPengguna));
            }
        });
    }
    
    private void viewChart(int index) {
        LocalDate now = LocalDate.now();
        txtYear.setText(String.valueOf(now.getYear()));
        String year = txtYear.getText();
        switch(index) {
            case 0:
                chart1.clearLegends();
                chart1.clearLegends();
                chart1.clearLegends();
                chart1.addLegends("Pendapatan Pemeriksaan", new Color(255, 193, 7));
                chart1.addLegends("Total Penjualan", new Color(76, 175, 80));
                chart1.addLegends("Pengeluaran", new Color(135, 15, 50));
                getChart(year, chart1, index);
                break;
            case 1:
                chart2.clearLegends();
                chart2.addLegends("Keuntungan Penjualan", new Color(33, 150, 243));
                getChart(year, chart2, index);
                break;
        }   
    }
    
    private void styleTable(JTable table) {
        table.getTableHeader().setDefaultRenderer(new ModelHeaderTable());  
    }
    
    private void detail(JPanel panel) {
        removeAll();
        add(panel);
        repaint();
        revalidate();
    }
    
    private void getChart(String year, Chart chart, int index) {
        chart.clear();
        serviceDashboard.chartDiagram(chart, year, index);
        chart.start();
    }
    
    private void changeChart(Chart chart) {
        panelChart.removeAll();
        panelChart.add(chart);
        panelChart.repaint();
        panelChart.revalidate();
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
        card11 = new component.Card();
        card12 = new component.Card();
        card13 = new component.Card();
        panel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        cbxChart = new javax.swing.JComboBox<>();
        panelChart = new javax.swing.JPanel();
        chart1 = new component.Chart();
        chart2 = new component.Chart();
        panel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        scroll2 = new javax.swing.JScrollPane();
        table2 = new swing.Table();
        card14 = new component.Card();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        panelData.setBackground(new java.awt.Color(153, 153, 153));
        panelData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        card11.setBackground(new java.awt.Color(255, 255, 255));

        card12.setBackground(new java.awt.Color(255, 255, 255));

        card13.setBackground(new java.awt.Color(255, 255, 255));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Aktivitas Usaha Dalam Satu (1) Tahun");

        txtYear.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        txtYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtYearActionPerformed(evt);
            }
        });

        cbxChart.setBackground(new java.awt.Color(255, 255, 255));
        cbxChart.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        cbxChart.setForeground(new java.awt.Color(102, 102, 102));
        cbxChart.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grafik Utama", "Grafik Keuntungan" }));
        cbxChart.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(185, 185, 185)));
        cbxChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxChartActionPerformed(evt);
            }
        });

        panelChart.setBackground(new java.awt.Color(255, 255, 255));
        panelChart.setForeground(new java.awt.Color(255, 255, 255));
        panelChart.setLayout(new java.awt.CardLayout());
        panelChart.add(chart1, "card2");
        panelChart.add(chart2, "card2");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxChart, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxChart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Reservasi Terakhir");

        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Reservasi", "Nama Pasien", "Tanggal / Jam", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll2.setViewportView(table2);
        if (table2.getColumnModel().getColumnCount() > 0) {
            table2.getColumnModel().getColumn(0).setMinWidth(0);
            table2.getColumnModel().getColumn(0).setPreferredWidth(0);
            table2.getColumnModel().getColumn(0).setMaxWidth(0);
            table2.getColumnModel().getColumn(1).setPreferredWidth(185);
            table2.getColumnModel().getColumn(2).setPreferredWidth(125);
        }

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );

        card14.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelDataLayout = new javax.swing.GroupLayout(panelData);
        panelData.setLayout(panelDataLayout);
        panelDataLayout.setHorizontalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addComponent(card11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(card12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(card13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(card14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelDataLayout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDataLayout.setVerticalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addGroup(panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(panelData, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtYearActionPerformed
        String year = txtYear.getText();
        int index = cbxChart.getSelectedIndex();
        switch(index) {
            case 0:
                getChart(year, chart1, index);
                break;
            case 1 :
                getChart(year, chart2, index);
                break;
        }
    }//GEN-LAST:event_txtYearActionPerformed

    private void cbxChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxChartActionPerformed
        LocalDate now = LocalDate.now();
        txtYear.setText(String.valueOf(now.getYear()));
        String year = txtYear.getText();
        int index = cbxChart.getSelectedIndex();
        switch(index) {
            case 0:
                changeChart(chart1);
                viewChart(index);
                getChart(year, chart1, index);
                break;
            case 1 :
                changeChart(chart2);
                viewChart(index);
                getChart(year, chart2, index);
                break;
        }
    }//GEN-LAST:event_cbxChartActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.Card card11;
    private component.Card card12;
    private component.Card card13;
    private component.Card card14;
    private javax.swing.JComboBox<String> cbxChart;
    private component.Chart chart1;
    private component.Chart chart2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panelChart;
    private javax.swing.JPanel panelData;
    private javax.swing.JScrollPane scroll2;
    private swing.Table table2;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
