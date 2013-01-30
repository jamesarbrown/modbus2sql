//////////////////////////////////////////////////////////////////////////
//com.enrogen.modbus2sql.NewAlarmFlagWindow
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.modbus2sql;

import au.com.bytecode.opencsv.CSVReader;
import com.enrogen.sql.SQLCommand;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;

public class NewAlarmWindow extends javax.swing.JDialog {

    public NewAlarmWindow(java.awt.Frame parent, SQLCommand SQLConnection) {
        super(parent);
        this.setTitle("Add Alarm Block");
        initComponents();
        SetupForm(SQLConnection);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        combo_type = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        btn_cancel = new javax.swing.JButton();
        btn_insert = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        text_filename = new javax.swing.JTextField();
        btn_browser = new javax.swing.JButton();

        jFrame1.setName("jFrame1"); // NOI18N

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setName("jPanel1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.enrogen.modbus2sql.Modbus2SQLApp.class).getContext().getResourceMap(NewAlarmWindow.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        combo_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_type.setName("combo_type"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.enrogen.modbus2sql.Modbus2SQLApp.class).getContext().getActionMap(NewAlarmWindow.class, this);
        btn_cancel.setAction(actionMap.get("closeDiag")); // NOI18N
        btn_cancel.setText(resourceMap.getString("btn_cancel.text")); // NOI18N
        btn_cancel.setName("btn_cancel"); // NOI18N

        btn_insert.setAction(actionMap.get("Insert")); // NOI18N
        btn_insert.setText(resourceMap.getString("btn_insert.text")); // NOI18N
        btn_insert.setName("btn_insert"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        text_filename.setText(resourceMap.getString("text_filename.text")); // NOI18N
        text_filename.setName("text_filename"); // NOI18N

        btn_browser.setText(resourceMap.getString("btn_browser.text")); // NOI18N
        btn_browser.setName("btn_browser"); // NOI18N
        btn_browser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_insert, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 320, Short.MAX_VALUE))
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(combo_type, 0, 257, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(120, 120, 120)
                                .addComponent(text_filename, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_browser)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_filename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btn_browser))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel)
                    .addComponent(btn_insert))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    File selFile = null;
    private void btn_browserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browserActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(jFrame1);
        selFile = fc.getSelectedFile();
        try {
            text_filename.setText(selFile.getAbsolutePath().toString());
        } catch (NullPointerException npe) {
        }
}//GEN-LAST:event_btn_browserActionPerformed

    ////////////////////////////////////////////////////////////////////////////
    //Initialise form and SQL connection
    ////////////////////////////////////////////////////////////////////////////

    private SQLCommand LocalSQLConnection = null;

    public void parseCSV() {
        List list = readCSV(selFile);
        CSVItterator(list);
    }

    public List readCSV(File filename) {
        List myEntries = null;
        try {
            CSVReader reader = new CSVReader(new FileReader(filename));
            myEntries = reader.readAll();
        } catch (Exception e) {
        }
        return myEntries;
    }

    public void CSVItterator(List CSVEntries) {
        int rows = CSVEntries.size();
        String controllertype = (String) combo_type.getSelectedItem();

        for (int i = 0; i < rows; i++) {
            String[] row = (String[]) CSVEntries.get(i);
            int entryQty = row.length;

            String alarmstring = row[0];
            String modbusregister = row[1];
            String startbit = row[2];
            String bitqty = row[3];
            String modbusregisterdescription = row[4];
            String registerqtydescription = row[5];
            String valuedisabled = row[6];
            String valuehealthy = row[7];
            String valuewarning = row[8];
            String valueshutdown = row[9];
            String valuetrip = row[10];
            String valueunimplemented = row[11];

            //Check any empty data
            if (bitqty.isEmpty()) {
                bitqty = "16";
            }

            String SqlCmd = "INSERT INTO alarmtypes SET controllertype='" +
                    controllertype + "', " +
                    "alarmstring='" + alarmstring + "', " +
                    "modbusregister=" + modbusregister + ", " +
                    "startbit=" + startbit + "," +
                    "bitqty=" + bitqty + "," +
                    "modbusregisterdescription=" + modbusregisterdescription + "," +
                    "registerqtydescription=" + registerqtydescription + ", " +
                    "valuedisabled=" + valuedisabled + ", " +
                    "valuehealthy=" + valuehealthy + ", " +
                    "valuewarning=" + valuewarning + ", " +
                    "valueshutdown=" + valueshutdown + ", " +
                    "valuetrip=" + valuetrip + ", " +
                    "valueunimplemented=" + valueunimplemented + ";" ;

            LocalSQLConnection.SQLUpdateCommand(SqlCmd);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NewAlarmWindow dialog = new NewAlarmWindow(new javax.swing.JFrame(), null);
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
    private javax.swing.JButton btn_browser;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_insert;
    private javax.swing.JComboBox combo_type;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField text_filename;
    // End of variables declaration//GEN-END:variables

    public void SetupForm(SQLCommand SqlConnect) {
        LocalSQLConnection = SqlConnect;

        //build the combo model
        String sqlcmd = "SELECT controllertype FROM controllertypes";
        combo_type.removeAllItems();
        List resultList = LocalSQLConnection.SQLSelectCommand(sqlcmd);
        for (int y=0; y<resultList.size(); y++) {
            List resultValues = (List) resultList.get(y);
            String value = (String) resultValues.get(0);
            combo_type.addItem(value);
        }
    }

    @Action
    public void Insert() {
        String message = "";
        boolean testEntry = true;

        if (text_filename.getText().isEmpty()) {
            testEntry = false;
            message = "You have not selected a file";
        }

        // Modal dialog with OK button
        if (testEntry = false) {
            String title = "Frame Title";
            JFrame frame = jFrame1;
            JOptionPane.showMessageDialog(frame, message);
        } else {
            parseCSV();
            this.dispose();
        }
        return;
    }

    @Action
    public void closeDiag() {
        this.dispose();
    }
}
