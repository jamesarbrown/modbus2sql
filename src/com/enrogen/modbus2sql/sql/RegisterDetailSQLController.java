package com.enrogen.modbus2sql.sql;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegisterDetailSQLController {

    private final ObservableList<RegisterDetail> RegisterDetailList = FXCollections.observableArrayList();

    public void RegisterDetailSQLController() {
    }

    public boolean insert(RegisterDetail rd) {
        try {
            String SqlCmd = "INSERT INTO registerdetail SET controllertype='"
                    + rd.getDeviceType() + "', "
                    + "page=" + rd.getPageNo() + ", "
                    + "description='" + rd.getDescription() + "', "
                    + "minimum='" + rd.getMinimumValue() + "',"
                    + "maximum='" + rd.getMaximumValue() + "',"
                    + "scalingfactor='" + rd.getScalingFactor() + "', "
                    + "bits=" + rd.getBits() + ", "
                    + "signed=" + tinyIntBoolean(rd.isSigned()) + ", "
                    + "register=" + rd.getRegisterNo() + ", "
                    + "units='" + rd.getUnits() + "', "
                    + "writeable=" + tinyIntBoolean(rd.isWriteable()) + ", "
                    + "lowbyteregister=" + rd.getLowByteRegister() + ";";
            sqlConnection.getInstance().SQLUpdateCommand(SqlCmd);
            return true;
        } catch (Exception e) {
            return false;
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

    public ObservableList getRegisterDetail(SlaveDetail sd, Integer Register) {
        //Clear the list is already init
        RegisterDetailList.clear();

        //Now fill the table
        String sqlcmd = "SELECT rowid, register, controllertype, page, description, "
                + "bits, signed, lowbyteregister,  scalingfactor, "
                + "minimum, maximum, units, writeable, lowbyteregister "
                + "FROM registerdetail WHERE controllertype='"
                + sd.getDeviceType() + "' AND register='" + Register + "';";
        getFillRegisters(sqlcmd);

        return RegisterDetailList;
    }

    private void getFillRegisters(String sqlcmd) {
        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        for (int rows = 0; rows < resultList.size(); rows++) {
            //Get the row data
            List resultValues = (List) resultList.get(rows);
            Integer rowid = (Integer) resultValues.get(0);
            Integer RegisterNo = (Integer) resultValues.get(1);
            String DeviceType = (String) resultValues.get(2);
            Integer PageNo = (Integer) resultValues.get(3);
            String Description = (String) resultValues.get(4);
            Integer Bits = (Integer) resultValues.get(5);
            Boolean Signed = (Boolean) resultValues.get(6);
            Integer LowByteRegister = (Integer) resultValues.get(7);
            Double ScalingFactor = (Double) resultValues.get(8);
            Double MinimumValue = (Double) resultValues.get(9);
            Double MaximumValue = (Double) resultValues.get(10);
            String Units = (String) resultValues.get(11);
            Boolean Writeable = (Boolean) resultValues.get(12);
            Integer UnimplementedValue = (Integer) resultValues.get(13);

            //Put it into the class
            RegisterDetail registerDetail = new RegisterDetail(
                    rowid,
                    RegisterNo,
                    DeviceType,
                    PageNo,
                    Description,
                    Bits,
                    Signed,
                    LowByteRegister,
                    ScalingFactor,
                    MinimumValue,
                    MaximumValue,
                    Units,
                    Writeable,
                    UnimplementedValue
            );
            
            //Now add to the Observable List
            RegisterDetailList.add(registerDetail);
        }
    }

    //Delete can only be enacted by RegisterBlockSQLController
    //No method here.
}
