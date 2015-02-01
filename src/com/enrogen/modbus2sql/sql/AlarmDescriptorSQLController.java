package com.enrogen.modbus2sql.sql;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AlarmDescriptorSQLController {

    private final ObservableList<AlarmDescriptor> AlarmDescriptorList = FXCollections.observableArrayList();

    public ObservableList getAlarmDescriptors() {
        refresh();
        return AlarmDescriptorList;
    }

    public void refresh() {
        String sqlcmd = "SELECT rowid, controllertype, alarmstring, modbusregister, startbit, bitqty, valuedisabled, valuewarning,"
                + "valueshutdown, valueunimplemented, valuetrip FROM alarmtypes";

        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        for (int rows = 0; rows < resultList.size(); rows++) {
            //Get the row data
            AlarmDescriptor ad = new AlarmDescriptor();
            List resultValues = (List) resultList.get(rows);
            ad.setUniqueID((Integer) resultValues.get(0));
            ad.setDeviceType((String) resultValues.get(1));
            ad.setAlarmString((String) resultValues.get(2));
            ad.setModbusRegister((Integer) resultValues.get(3));
            ad.setStartBit((Integer) resultValues.get(4));
            ad.setBitQty((Integer) resultValues.get(5));
            ad.setValueDisabled((Integer) resultValues.get(6));
            ad.setValueWarning((Integer) resultValues.get(7));
            ad.setValueShutdown((Integer) resultValues.get(8));
            ad.setValueUnimplemented((Integer) resultValues.get(9));
            ad.setValueTripped((Integer) resultValues.get(10));

            AlarmDescriptorList.add(ad);
        }
    }

    public void insert(AlarmDescriptor ad) {
        //Check any empty data
        if (ad.getBitQty() == 0) {
            ad.setBitQty(16);
        }

        String sqlcmd = "INSERT INTO alarmtypes SET controllertype='"
                + ad.getDeviceType() + "', "
                + "alarmstring='" + ad.getAlarmString() + "', "
                + "modbusregister=" + ad.getModbusRegister() + ", "
                + "startbit=" + ad.getStartBit() + ","
                + "bitqty=" + ad.getBitQty() + ","
                + "valuedisabled=" + ad.getValueDisabled() + ", "
                + "valuewarning=" + ad.getValueWarning() + ", "
                + "valueshutdown=" + ad.getValueShutdown() + ", "
                + "valuetrip=" + ad.getValueTripped() + ", "
                + "valueunimplemented=" + ad.getValueUnimplemented() + ";";

        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
    }

    public void update(AlarmDescriptor ad) {
//        String sqlcmd = "UPDATE alarmflags SET warningregister=" + af.getWarningRegister() + ", "
//                + "warningbit=" + af.getWarningBit() + ", "
//                + "shutdownregister=" + af.getShutdownRegister() + ", "
//                + "shutdownbit=" + af.getShutdownBit() + ", "
//                + "tripregister=" + af.getTripRegister() + ", "
//                + "tripbit=" + af.getTripBit() + ", "
//                + "controllertype='" + af.getDeviceType() + "' "
//                + "WHERE rowid='" + af.getUniqueID() + "';";

//        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
    }
}
