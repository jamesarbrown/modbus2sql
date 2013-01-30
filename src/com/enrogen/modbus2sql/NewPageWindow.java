//////////////////////////////////////////////////////////////////////////
//com.enrogen.modbus2sql.NewPageWindow
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

public class NewPageWindow extends javax.swing.JDialog {

    public NewPageWindow(java.awt.Frame parent, SQLCommand SQLConnection) {
        super(parent);
        this.setTitle("Add a Modbus Data Block");
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
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_description = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        text_filename = new javax.swing.JTextField();
        btn_browser = new javax.swing.JButton();
        spinner_page = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        combo_function = new javax.swing.JComboBox();

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

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.enrogen.modbus2sql.Modbus2SQLApp.class).getContext().getResourceMap(NewPageWindow.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        combo_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_type.setName("combo_type"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.enrogen.modbus2sql.Modbus2SQLApp.class).getContext().getActionMap(NewPageWindow.class, this);
        btn_cancel.setAction(actionMap.get("closeDiag")); // NOI18N
        btn_cancel.setText(resourceMap.getString("btn_cancel.text")); // NOI18N
        btn_cancel.setName("btn_cancel"); // NOI18N

        btn_insert.setAction(actionMap.get("Insert")); // NOI18N
        btn_insert.setText(resourceMap.getString("btn_insert.text")); // NOI18N
        btn_insert.setName("btn_insert"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(87, 87));

        text_description.setColumns(20);
        text_description.setRows(5);
        text_description.setName("text_description"); // NOI18N
        jScrollPane1.setViewportView(text_description);

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

        spinner_page.setName("spinner_page"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        combo_function.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_function.setName("combo_function"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(307, 307, 307)
                        .addComponent(btn_insert, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE))
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2))
                                .addGap(64, 64, 64)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(text_filename, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_browser))
                                    .addComponent(spinner_page, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                    .addComponent(combo_type, javax.swing.GroupLayout.Alignment.TRAILING, 0, 257, Short.MAX_VALUE)
                                    .addComponent(combo_function, 0, 257, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinner_page, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(combo_function, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_filename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btn_browser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel)
                    .addComponent(btn_insert)))
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
        try {
            int rows = CSVEntries.size();
            String controllertype = (String) combo_type.getSelectedItem();
            Integer page = (Integer) spinner_page.getValue();
            String StartReg = "";
            String EndReg = "";
            String BlockDescription = text_description.getText().toString();

            //Get the combo function to a Modbus Function Integer
            String selectedFunction = combo_function.getSelectedItem().toString();
            int ModbusFuncCode = 0;
            if (selectedFunction.compareTo("Read Multiple Registers (3)") == 0) {
                ModbusFuncCode = 3;
            }
            if (selectedFunction.compareTo("Read Coils (1)") == 0) {
                ModbusFuncCode = 1;
            }
            if (selectedFunction.compareTo("Read Input Discretes (2)") == 0) {
                ModbusFuncCode = 2;
            }
            if (selectedFunction.compareTo("Read Input Registers (4)") == 0) {
                ModbusFuncCode = 4;
            }

            for (int i = 0; i < rows; i++) {
                String[] row = (String[]) CSVEntries.get(i);
                int entryQty = row.length;

                String register = row[0];
                String description = row[1];
                String minimum = row[2];
                String maximum = row[3];
                String scalingfactor = row[4];
                String units = row[5];
                String bits = row[6];
                Integer bitsInt = 16;
                Integer signed = 0;
                String writeable = row[7];
                String lowByteRegister = row[8];

                //Record first register
                if (i == 0) {
                    StartReg = register;
                }

                //Record last register
                if (i == rows - 1) {
                    EndReg = register;
                }

                //Check the bits and signing
                if (bits.contains("32")) {
                    bitsInt = 32;
                } else {
                    bitsInt = 16;
                }
                if (bits.contains("S")) {
                    signed = 1;
                } else {
                    signed = 0;
                }

                //Check any empty data
                if (minimum.isEmpty()) {
                    minimum = "0.0";
                }
                if (maximum.isEmpty()) {
                    minimum = "0.0";
                }
                if (scalingfactor.isEmpty()) {
                    scalingfactor = "0.0";
                }
                if (lowByteRegister.isEmpty()) {
                    lowByteRegister = "0";
                }

                String SqlCmd = "INSERT INTO registerdetail SET controllertype='"
                        + controllertype + "', "
                        + "page=" + page + ", "
                        + "description='" + description + "', "
                        + "minimum='" + minimum + "',"
                        + "maximum='" + maximum + "',"
                        + "scalingfactor='" + scalingfactor + "', "
                        + "bits=" + bitsInt + ", "
                        + "signed=" + signed + ", "
                        + "register=" + register + ", "
                        + "units='" + units + "', "
                        + "writeable=" + writeable + ", "
                        + "lowbyteregister=" + lowByteRegister + ";";

                LocalSQLConnection.SQLUpdateCommand(SqlCmd);
            }

            String SqlCmd2 = "INSERT INTO registerblocks SET controllertype='"
                    + controllertype + "', "
                    + "page=" + page + ", "
                    + "registerstart=" + StartReg + ", "
                    + "registerend=" + EndReg + ", "
                    + "description='" + BlockDescription + "', "
                    + "modbusFunctionType=" + ModbusFuncCode
                    + ";";

            LocalSQLConnection.SQLUpdateCommand(SqlCmd2);
        } catch (Exception e) {
            System.out.println("Issue trying to parse CSV");
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NewPageWindow dialog = new NewPageWindow(new javax.swing.JFrame(), null);
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
    private javax.swing.JComboBox combo_function;
    private javax.swing.JComboBox combo_type;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spinner_page;
    private javax.swing.JTextArea text_description;
    private javax.swing.JTextField text_filename;
    // End of variables declaration//GEN-END:variables

    public void SetupForm(SQLCommand SqlConnect) {
        LocalSQLConnection = SqlConnect;

        //build the combo model
        String sqlcmd = "SELECT controllertype FROM controllertypes";
        combo_type.removeAllItems();
        List resultList = LocalSQLConnection.SQLSelectCommand(sqlcmd);
        for (int y = 0; y < resultList.size(); y++) {
            List resultValues = (List) resultList.get(y);
            String value = (String) resultValues.get(0);
            combo_type.addItem(value);
        }

        //build the combo function
        combo_function.removeAllItems();
        combo_function.addItem("Read Multiple Registers (3)");
        combo_function.addItem("Read Coils (1)");
        combo_function.addItem("Read Input Discretes (2)");
        combo_function.addItem("Read Input Registers (4)");
    }

    @Action
    public void Insert() {
        String message = "";
        boolean testEntry = true;

        if (text_description.getText().isEmpty()) {
            testEntry = false;
            message = "You must give a description";
        }

        Integer page = (Integer) spinner_page.getValue();
        if (page == 0) {
            testEntry = false;
            message = "Please specify a page number";
        }

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
