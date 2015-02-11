package com.enrogen.modbus2sql.sql;

import com.enrogen.modbus2sql.jamod.ModbusRegister;
import com.enrogen.modbus2sql.logger.EgLogger;
import java.util.Iterator;
import java.util.List;

public class ModbusDataSQLController {

    public void ModbusDataSQLController() {

    }

    public void writeModbusData(List<ModbusRegister> response, SlaveDetail slave) {
        //Ensure the batch function is empty
        sqlConnection.getInstance().ClearBatch();

        //Write the response to the SQL Table and timestamp it.
        Iterator<ModbusRegister> it = response.listIterator();

        while (it.hasNext()) {
            String sqlcmd = "UPDATE slave" + slave.getSlaveID()
                    + " SET registervalue='" + it.next().getValue() + "' ,"
                    + "timestamp=NOW() WHERE register=" + it.next().getRegisterNo() + ";";
            sqlConnection.getInstance().AddToBatch(sqlcmd);
        }
        sqlConnection.getInstance().SQLExecuteBatch();

        //Now bring that lot back into a ModbusRegister so we can update
        String sqlcmd = "SELECT rowid, register, registervalue, isSigned, is32bit, lowbyteregister FROM slave"
                + slave.getSlaveID() + ";";
        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        //clear the batch command ready for next phase
        sqlConnection.getInstance().ClearBatch();

        try {
            for (int i = 0; i < resultList.size(); i++) {
                //The register we are dealing with
                List resultValues = (List) resultList.get(i);

                //The next register (which we may need for 32bit data)
                List resultValuesNext = (List) resultList.get(i + 1);

                //it would seem mysql tinyint can only be cast as boolean
                Integer rowid = (Integer) resultValues.get(0);
                Integer regsiterNo = (Integer) resultValues.get(1);
                Boolean isSigned = (Boolean) resultValues.get(3);
                Boolean is32Bit = (Boolean) resultValues.get(4);
                Integer lowByteRegister = (Integer) resultValues.get(5);

                //And the values
                Integer registerValue = (Integer) resultValues.get(2);
                Integer nextRegisterValue = (Integer) resultValuesNext.get(2);

                //We need to insert all that into a ModbusRegister class
                ModbusRegister mr = new ModbusRegister();
                mr.setRegisterNo(regsiterNo);
                mr.setIs32Bit(is32Bit);
                mr.setIsSigned(isSigned);

                //If is 16bit we simply insert the current value
                //with 0 for the high word
                mr.setValue(0, registerValue);

                //If is32Bit and LowByte we are looking at the high byte
                //We are not interested in it as its only half the data
                if (is32Bit && lowByteRegister > 0) {
                    mr.setValue(65535L);  //Unimplemented
                } else {
                    //We need to add code here incase H word and L word
                    //are reversed, as it can differ manufacturer to manufacturer.
                    mr.setValue(registerValue, nextRegisterValue);
                }

                //Now we can update the SQL
                sqlcmd = "UPDATE slave" + slave.getSlaveID() + " "
                        + "SET value=" + mr.getValue() + ", "
                        + "registerValue='" + registerValue + "', "
                        + "timestamp=NOW(), "
                        + "binaryvalue='" + mr.getBinaryValue() + "' "
                        + "WHERE register=" + mr.getRegisterNo() + ";";
                sqlConnection.getInstance().AddToBatch(sqlcmd);

                //Now update the deprecated columns TO REMOVE
                sqlConnection.getInstance().AddToBatch(updateDeprecatedColumns(slave, mr));
            }

            //Execute the batch
            sqlConnection.getInstance().SQLExecuteBatch();

            //Now update the deprecated columns still in use by EnrogenHMI
        } catch (Exception e) {
            EgLogger.logInfo("Error with SQL Command Listing Batch Out : ");
            for (int i = 0; i < sqlConnection.getInstance().BatchSQLCommands.size(); i++) {
                EgLogger.logInfo(sqlConnection.getInstance().BatchSQLCommands.get(i).toString());
            }
            e.printStackTrace();
            return;
        }
    }

    //This is to update the deprecated columns for EnrogenHMI
    //It will be removed in future.
    public String updateDeprecatedColumns(SlaveDetail sd, ModbusRegister mr) {
        if (!mr.is32Bit()) {
            String hex16 = "0x" + Long.toHexString(mr.getValue());
            String sqlcmd = "UPDATE slave" + sd.getSlaveID() + " "
                    + "SET 16integer=" + mr.getValue() + ", "
                    + "16binary='" + mr.getBinaryValue().substring(16) + "' "
                    + "16hex='" + hex16 + "' WHERE register=" + mr.getRegisterNo() + ";";
            return sqlcmd;
        } else {
            //Fill the 32bit data
            String hex32 = "0x" + Long.toHexString(mr.getValue());
            String sqlcmd = "UPDATE slave" + sd.getSlaveID() + " "
                    +"SET 32integer=" + mr.getValue() + ", "
                    + "32hex='" + hex32 + "', "
                    + "32binary='" + mr.getBinaryValue() + "' "
                    + "WHERE register=" + mr.getRegisterNo() + ";";
            return sqlcmd;
        }
    }

}
