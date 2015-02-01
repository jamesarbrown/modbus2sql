package com.enrogen.modbus2sql.sql;

import com.enrogen.modbus2sql.logger.EgLogger;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AlarmFlagSQLController {

    private final ObservableList<AlarmFlag> AlarmFlagList = FXCollections.observableArrayList();

    public ObservableList getAlarmFlags() {
        refresh();
        return AlarmFlagList;
    }

    public void refresh() {
        String sqlcmd = "SELECT rowid, controllertype, warningregister, warningbit, "
                + "shutdownregister, shutdownbit, tripregister, tripbit "
                + "FROM alarmflags";

        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);
                
        for (int rows = 0; rows < resultList.size(); rows++) {
            //Get the row data
            AlarmFlag af = new AlarmFlag();
            List resultValues = (List) resultList.get(rows);
            af.setUniqueID((Integer) resultValues.get(0));
            af.setDeviceType((String) resultValues.get(1));
            af.setWarningRegister((Integer) resultValues.get(2));
            af.setWarningBit((Integer) resultValues.get(3));
            af.setShutdownRegister((Integer) resultValues.get(4));
            af.setShutdownBit((Integer) resultValues.get(5));
            af.setTripRegister((Integer) resultValues.get(6));
            af.setTripBit((Integer) resultValues.get(7));

            AlarmFlagList.add(af);
        }
    }

    public void insert(AlarmFlag af) {
        String sqlcmd = "INSERT INTO alarmflags SET warningregister=" + af.getWarningRegister() + ", "
                + "warningbit=" + af.getWarningBit() + ", "
                + "shutdownregister=" + af.getShutdownRegister() + ", "
                + "shutdownbit=" + af.getShutdownBit() + ", "
                + "tripregister=" + af.getTripRegister() + ", "
                + "tripbit=" + af.getTripBit() + ", "
                + "controllertype='" + af.getDeviceType() + "';";

        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
    }

    public void update(AlarmFlag af) {
        String sqlcmd = "UPDATE alarmflags SET warningregister=" + af.getWarningRegister() + ", "
                + "warningbit=" + af.getWarningBit() + ", "
                + "shutdownregister=" + af.getShutdownRegister() + ", "
                + "shutdownbit=" + af.getShutdownBit() + ", "
                + "tripregister=" + af.getTripRegister() + ", "
                + "tripbit=" + af.getTripBit() + ", "
                + "controllertype='" + af.getDeviceType() + "' "
                + "WHERE rowid='" + af.getUniqueID() + "';";

        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
    }
    
    public void delete(AlarmFlag af) {
        //Run SQL Command
        String sqlcmd = "DELETE FROM alarmflags WHERE rowid=" + af.getUniqueID() + ";";
        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
    }
}
