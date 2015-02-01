package com.enrogen.modbus2sql.sql;

public class RegisterDetailSQLController {

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
                        + "signed=" + rd.getSigned() + ", "
                        + "register=" + rd.getRegisterNo() + ", "
                        + "units='" + rd.getUnits() + "', "
                        + "writeable=" + rd.getWriteable() + ", "
                        + "lowbyteregister=" + rd.getLowByteRegister() + ";";
            sqlConnection.getInstance().SQLUpdateCommand(SqlCmd);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Delete can only be enacted by RegisterBlockSQLController
    //No method here.
}
