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

        String sqlcmd = "SELECT rowid, controllertype, modbusslaveid, longname  FROM slaves";
        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        for (int rows = 0; rows < resultList.size(); rows++) {
            //Get the row data
            List resultValues = (List) resultList.get(rows);
            Integer ID = (Integer) resultValues.get(0);
            String Type = (String) resultValues.get(1);
            Integer ModbusSlaveID = (Integer) resultValues.get(2);
            String Desc = (String) resultValues.get(3);

            //Put it into the class
            SlaveDetail slave = new SlaveDetail(ID, Type, ModbusSlaveID, Desc);

            //Now add to the Observable List
            SlaveList.add(slave);
        }
    }

    //This is called from AddDeviceWindowController
    public boolean Insert(SlaveDetail slave) {
        String DeviceType = slave.getDeviceType();
        String Description = slave.getDescription();
        Integer ModbusSlaveID = slave.getSlaveID();

        //Check Slave ID is not already used
        String sqlcmd = "SELECT * FROM slaves WHERE modbusslaveid="
                + ModbusSlaveID + ";";
        Long checkcount = null;

        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        if (resultList.size() > 0) {
            try {
                new InfoPopup("That slave ID already exists, please change");
            } catch (Exception e) {
                EgLogger.logInfo("Popup Failure");
            }
            return false;
        } else {
            //Ok so create a table for the slave based on controllertype
            String tablename = "slave" + String.valueOf(ModbusSlaveID);
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
                    + "WHERE controllertype='" + DeviceType + "';";

            //Update entry into slaves table
            String sqlSlaveEntry = "INSERT INTO slaves SET modbusslaveid=" + ModbusSlaveID
                    + ", controllertype='" + DeviceType + "', "
                    + "longname='" + Description + "';";

            sqlConnection.getInstance().SQLUpdateCommand(sqlCreateTable);
            sqlConnection.getInstance().SQLUpdateCommand(sqlSlaveEntry);

            //Insert registers
            resultList = sqlConnection.getInstance().SQLSelectCommand(sqlRegisterBlocks);
            for (int z = 0; z < resultList.size(); z++) {
                List resultValues = (List) resultList.get(z);
                Integer registerstart = (Integer) resultValues.get(0);
                Integer registerend = (Integer) resultValues.get(1);
                int start = registerstart.intValue();
                int end = registerend.intValue();

                for (int register = start; register <= end; register++) {

                    //Get the long description
                    String sqllongdesc = "SELECT description, bits, signed, lowbyteregister FROM registerdetail WHERE "
                            + "controllertype='" + DeviceType + "' AND register="
                            + String.valueOf(register) + ";";

                    List resultList2 = sqlConnection.getInstance().SQLSelectCommand(sqllongdesc);

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

                        sqlConnection.getInstance().SQLUpdateCommand(sqlInsertRegister);
                    } catch (Exception e) {
                        EgLogger.logSevere("There was something wrong with the CSV Files");
                        EgLogger.logSevere(sqllongdesc);
                    }
                }
            }
            return true;
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

        try {
            String sqlcmd = "UPDATE slaves SET modbusslaveid=" + ModbusSlaveID
                    + ", " + "longname='" + Description + "', "
                    + "controllertype='" + DeviceType + "' "
                    + "WHERE rowid=" + ID + ";";

            sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
