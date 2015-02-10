package com.enrogen.modbus2sql.sql;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.logger.EgLogger;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SlaveDetailSQLController {

    private final ObservableList<SlaveDetail> SlaveList = FXCollections.observableArrayList();

    public SlaveDetailSQLController() {
    }

    public ObservableList getSlaves() {
        refresh();
        return SlaveList;
    }

    public void refresh() {
        //Clear the list
        SlaveList.clear();

        String sqlcmd = "SELECT rowid, controllertype, modbusslaveid, longname,"
                + "useRS485, IpAddress FROM slaves";
        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        for (int rows = 0; rows < resultList.size(); rows++) {
            //Get the row data
            List resultValues = (List) resultList.get(rows);
            Integer ID = (Integer) resultValues.get(0);
            String Type = (String) resultValues.get(1);
            Integer ModbusSlaveID = (Integer) resultValues.get(2);
            String Desc = (String) resultValues.get(3);
            Boolean useRS485 = (Boolean) resultValues.get(4);
            String IpAddress = (String) resultValues.get(5);

            //Put it into the class
            SlaveDetail slave = new SlaveDetail(ID, Type, ModbusSlaveID, Desc, useRS485, IpAddress);

            //Now add to the Observable List
            SlaveList.add(slave);
        }
    }

    //This is called from AddDeviceWindowController
    public boolean Insert(SlaveDetail slave) {
        String DeviceType = slave.getDeviceType();
        String Description = slave.getDescription();
        Integer ModbusSlaveID = slave.getSlaveID();
        Boolean useRS485 = slave.isUseRS485();
        String IpAddress = slave.getIpAddress();

        //Check Slave ID is not already used
        String sqlcmd = "SELECT * FROM slaves WHERE modbusslaveid="
                + ModbusSlaveID + ";";
        Long checkcount = null;

        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        if (resultList.size() > 0) {
            new InfoPopup("That slave ID already exists, please change");
            return false;
        } else {
            //Ok so create a table for the slave based on controllertype
            String tablename = "slave" + String.valueOf(ModbusSlaveID);

            //Now we can create it
            String sqlCreateTable = " CREATE TABLE `" + tablename + "` ("
                    + "`rowid` int(11) NOT NULL AUTO_INCREMENT,"
                    + "`register` int(11) DEFAULT NULL,"
                    + "`description` varchar(50) DEFAULT NULL,"
                    + "`timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," //DO NOT AUTO UPDATE TIMESTAMP!!
                    + "`livedata` tinyint(1) NOT NULL DEFAULT '0',"
                    + "`value` int(11) NOT NULL DEFAULT '0',"
                    + "`registervalve` int(4) UNSIGNED NOT NULL DEFAULT '0',"
                    + "`binaryvalue` varchar(32) DEFAULT '',"
                    + "`is32bit` tinyint(1) NOT NULL DEFAULT '0',"
                    + "`isSigned` tinyint(1) NOT NULL DEFAULT '0',"
                    + "`lowbyteregister` int(11) DEFAULT '0',"
                    + "`scalingfactor` double DEFAULT '1',"
                    + "`minimum` double DEFAULT NULL,"
                    + "`maximum` double DEFAULT NULL,"
                    + "`units` varchar(50) DEFAULT NULL,"
                    + "`changeflag` tinyint(4) NOT NULL DEFAULT '0',"
                    + "`writedata` int(11) NOT NULL DEFAULT '0',"
                    + "PRIMARY KEY (`rowid`)"
                    + ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";
            sqlConnection.getInstance().SQLUpdateCommand(sqlCreateTable);

            //These are convienence fields for EnrogenHMI to be deprecated
            sqlcmd = "ALTER TABLE `modbus2sql`.`"+ tablename + "`"
                 + "ADD COLUMN `16binary` VARCHAR(16) NOT NULL DEFAULT '0000000000000000' AFTER `writedata`, "
                 + "ADD COLUMN `16hex` char(10) NOT NULL DEFAULT '0x0000',"
                 + "ADD COLUMN `16integer` bigint(20) NOT NULL DEFAULT '0',"
                 + "ADD COLUMN `32binary` varchar(32) DEFAULT NULL,"
                 + "ADD COLUMN `32hex` char(20) DEFAULT NULL,"
                 + "ADD COLUMN `32integer` bigint(20) DEFAULT NULL;";
            sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
            
            //Update entry into master slaves table
            String sqlSlaveEntry = "INSERT INTO slaves SET modbusslaveid=" + ModbusSlaveID
                    + ", controllertype='" + DeviceType + "', "
                    + "longname='" + Description + "', "
                    + "useRS485=" + useRS485 + ", "
                    + "IpAddress='" + IpAddress + "';";
            sqlConnection.getInstance().SQLUpdateCommand(sqlSlaveEntry);

            //Get the register blocks by slave ID
            ObservableList<RegisterBlock> registerBlockList
                    = new RegisterBlockSQLController().getRegisterBlocksBySlaveID(slave);

            //Itterate
            for (int z = 0; z < registerBlockList.size(); z++) {
                RegisterBlock registerBlock = registerBlockList.get(z);

                for (int register = registerBlock.getRegisterStart(); register <= registerBlock.getRegisterEnd(); register++) {
                    //Get the Register Detail by Register No
                    ObservableList<RegisterDetail> RegisterDetailList
                            = new RegisterDetailSQLController().getRegisterDetail(slave, register);
                    RegisterDetail registerdetail = RegisterDetailList.get(0);

                    //Is it 32 bit?
                    Integer is32bit = 0;
                    if (registerdetail.getBits() == 32) {
                        is32bit = 1;
                    }

                    String sqlInsertRegister = "INSERT INTO " + tablename + " SET "
                            + "register=" + register + ", "
                            + "description='" + registerdetail.getDescription() + "', "
                            + "is32bit=" + is32bit + ", "
                            + "isSigned=" + tinyIntBoolean(registerdetail.isSigned()) + ","
                            + "lowbyteregister=" + registerdetail.getLowByteRegister() + ";";

                    sqlConnection.getInstance().SQLUpdateCommand(sqlInsertRegister);
                }
            }
            return true;
        }
    }

    //Convienence to turn boolean into an integer for storage in Mysql tinyint column
    public int tinyIntBoolean(Boolean value) {
        if (value) {
            return 1;
        } else {
            return 0;
        }
    }

    public void delete(SlaveDetail sd) {
        //Open SQL
        String sqlcmd = "DELETE FROM slaves WHERE rowid=" + sd.getRowID() + ";";
        String sqlcmd2 = "DROP TABLE slave" + sd.getSlaveID() + ";";
        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd2);
    }

    public boolean update(SlaveDetail sd) {
        int ID = sd.getRowID();
        String DeviceType = sd.getDeviceType();
        String Description = sd.getDescription();
        Integer ModbusSlaveID = sd.getSlaveID();
        Boolean useRS485 = sd.isUseRS485();
        String IpAddress = sd.getIpAddress();

        try {
            String sqlcmd = "UPDATE slaves SET modbusslaveid=" + ModbusSlaveID + ", "
                    + "longname='" + Description + "', "
                    + "controllertype='" + DeviceType + "', "
                    + "useRS485=" + useRS485 + ", "
                    + "IpAddress='" + IpAddress + "' "
                    + "WHERE rowid=" + ID + ";";
            sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
