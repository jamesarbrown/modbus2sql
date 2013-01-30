//////////////////////////////////////////////////////////////////////////
//com.enrogen.modbus2sql.NewSlaveWindow
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////

package com.enrogen.modbus2sql;

import com.enrogen.sql.SQLCommand;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import org.jdesktop.application.Action;

public class NewSlaveWindow extends javax.swing.JDialog {

    /** Creates new form EnrogenAddNewSlave */
    public NewSlaveWindow(java.awt.Frame parent, SQLCommand SQLConnection) {
        super(parent);
        initComponents();
        SetupForm(SQLConnection);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        spinnerModbusID = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        combo_type = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        btn_cancel = new javax.swing.JButton();
        btn_insert = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        text_name = new javax.swing.JTextField();

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
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.enrogen.modbus2sql.Modbus2SQLApp.class).getContext().getResourceMap(NewSlaveWindow.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        spinnerModbusID.setName("spinnerModbusID"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        combo_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_type.setName("combo_type"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.enrogen.modbus2sql.Modbus2SQLApp.class).getContext().getActionMap(NewSlaveWindow.class, this);
        btn_cancel.setAction(actionMap.get("btn_close_action")); // NOI18N
        btn_cancel.setText(resourceMap.getString("btn_cancel.text")); // NOI18N
        btn_cancel.setName("btn_cancel"); // NOI18N

        btn_insert.setAction(actionMap.get("addNewSlave")); // NOI18N
        btn_insert.setText(resourceMap.getString("btn_insert.text")); // NOI18N
        btn_insert.setName("btn_insert"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        text_name.setText(resourceMap.getString("text_name.text")); // NOI18N
        text_name.setName("text_name"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerModbusID, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                            .addComponent(combo_type, 0, 159, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(text_name, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_insert, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerModbusID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel)
                    .addComponent(btn_insert))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NewSlaveWindow dialog = new NewSlaveWindow(new javax.swing.JFrame(), null);
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
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_insert;
    private javax.swing.JComboBox combo_type;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSpinner spinnerModbusID;
    private javax.swing.JTextField text_name;
    // End of variables declaration//GEN-END:variables

    ////////////////////////////////////////////////////////////////////////////
    //Initialise form and SQL connection
    ////////////////////////////////////////////////////////////////////////////
    SQLCommand LocalSQLConnection = null;

    public void SetupForm(SQLCommand SqlConnect) {
        LocalSQLConnection = SqlConnect;

        //Build the spinner model
        SpinnerModel Spinnermodel = new SpinnerNumberModel(1,
                1, //min
                16, //max
                1);                //step
        spinnerModbusID.setModel(Spinnermodel);

        //build the combo model
        String sqlcmd = "SELECT controllertype FROM controllertypes";
        combo_type.removeAllItems();
        List resultList = LocalSQLConnection.SQLSelectCommand(sqlcmd);

        for (int z = 0; z < resultList.size(); z++) {
            List resultValues = (List) resultList.get(z);
            String value = (String) resultValues.get(0);
            combo_type.addItem(value);
        }
    }

    @Action
    public void addNewSlave() {
        //Get the modbus ID
        Integer modbusID = (Integer) spinnerModbusID.getValue();
        String controllertype = (String) combo_type.getSelectedItem();
        String longname = text_name.getText().toString();

        //Check Slave ID is not already used
        String sqlcmd = "SELECT count(rowid) FROM slaves WHERE modbusslaveid="
                + modbusID + ";";
        Long checkcount = null;

        List resultList = LocalSQLConnection.SQLSelectCommand(sqlcmd);
        for (int z = 0; z < resultList.size(); z++) {
            List resultValues = (List) resultList.get(0);
            checkcount = (Long) resultValues.get(0);
            if (checkcount > 0) {
                String title = "Error";
                JFrame frame = jFrame1;
                JOptionPane.showMessageDialog(frame, "The Modbus Slave ID is Used");
                return;
            }
        }

        //Ok so create a table for the slave based on controllertype
        String tablename = "slave" + String.valueOf(modbusID);

        String sqlCreateTable = " CREATE TABLE `" + tablename + "` ("
                + "`rowid` int(11) NOT NULL AUTO_INCREMENT,"
                + "`register` int(11) DEFAULT NULL,"
                + "`description` varchar(50) DEFAULT NULL,"
                + "`timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," + //DO NOT AUTO UPDATE TIMESTAMP!!
                "`livedata` tinyint(1) NOT NULL DEFAULT '0',"
                + "`16binary` varchar(16) NOT NULL DEFAULT '0000000000000000',"
                + "`16hex` char(10) NOT NULL DEFAULT '0x0000',"
                + "`16integer` bigint(20) NOT NULL DEFAULT '0',"
                + "`32binary` varchar(32) DEFAULT NULL,"
                + "`32hex` char(20) DEFAULT NULL,"
                + "`32integer` bigint(20) DEFAULT NULL,"
                + "`lowbyteregister` int(11) DEFAULT '0',"
                + "`is32bit` tinyint(1) NOT NULL DEFAULT '0',"
                + "`isSigned` tinyint(1) NOT NULL DEFAULT '0',"
                + "`SignedResult` int(11) NOT NULL DEFAULT '0',"
                + "`changeflag` tinyint(4) NOT NULL DEFAULT '0',"
                + "`writedata` int(11) NOT NULL DEFAULT '0',"
                + "PRIMARY KEY (`rowid`)"
                + ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";

        //Fill table with the relevant registers
        String sqlRegisterBlocks = "SELECT registerstart, registerend FROM registerblocks "
                + "WHERE controllertype='" + controllertype + "';";

        //Update entry into slaves table
        String sqlSlaveEntry = "INSERT INTO slaves SET modbusslaveid=" + modbusID
                + ", controllertype='" + controllertype + "', "
                + "longname='" + longname + "';";

        LocalSQLConnection.SQLUpdateCommand(sqlCreateTable);
        LocalSQLConnection.SQLUpdateCommand(sqlSlaveEntry);

        //Insert registers
        resultList = LocalSQLConnection.SQLSelectCommand(sqlRegisterBlocks);
        for (int z = 0; z < resultList.size(); z++) {
            List resultValues = (List) resultList.get(z);
            Integer registerstart = (Integer) resultValues.get(0);
            Integer registerend = (Integer) resultValues.get(1);
            int start = registerstart.intValue();
            int end = registerend.intValue();

            for (int register = start; register <= end; register++) {

                //Get the long description
                String sqllongdesc = "SELECT description, bits, signed, lowbyteregister FROM registerdetail WHERE "
                        + "controllertype='" + controllertype + "' AND register="
                        + String.valueOf(register) + ";";
                
                List resultList2 = LocalSQLConnection.SQLSelectCommand(sqllongdesc);

                try {
                List resultValues2 = (List) resultList2.get(0);
                String longdesc = (String) resultValues2.get(0);
                Integer bits = (Integer) resultValues2.get(1);
                Integer is32bit = 0;
                if (bits == 32) {
                    is32bit = 1;
                }
                Boolean boolSigned = (Boolean) resultValues2.get(2);
                Integer signed = 0;
                if (boolSigned) {
                    signed = 1;
                }
                Integer lowbyteregister = (Integer) resultValues2.get(3);

                String sqlInsertRegister = "INSERT INTO " + tablename + " SET "
                        + "register=" + register + ", description='" + longdesc + "', "
                        + "is32bit=" + is32bit + ", isSigned=" + signed + ", lowbyteregister=" + lowbyteregister + ";";

                LocalSQLConnection.SQLUpdateCommand(sqlInsertRegister);}
                catch (Exception e) {
                    System.out.println("There was something wrong with the CSV Files");
                    System.out.println(sqllongdesc);
                }
            }
        }

        this.dispose();
    }

    @Action
    public void btn_close_action() {
        this.dispose();
    }
}
