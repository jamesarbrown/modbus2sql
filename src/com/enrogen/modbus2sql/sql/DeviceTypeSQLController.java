package com.enrogen.modbus2sql.sql;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.logger.EgLogger;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeviceTypeSQLController {

    private final ObservableList<DeviceType> DeviceList = FXCollections.observableArrayList();

    public DeviceTypeSQLController() {
    }

    public ObservableList getDevices() {
        refresh();
        return DeviceList;
    }

    public void refresh() {
        //Clear the list
        DeviceList.clear();

        String sqlcmd = "SELECT rowid, controllertype, longname FROM controllertypes";

        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        for (int rows = 0; rows < resultList.size(); rows++) {
            //Get the row data
            List resultValues = (List) resultList.get(rows);
            Integer ID = (Integer) resultValues.get(0);
            String Type = (String) resultValues.get(1);
            String Desc = (String) resultValues.get(2);

            //Put it into the class
            DeviceType device = new DeviceType(ID, Type, Desc);

            //Now add to the Observable List
            DeviceList.add(device);
        }
    }

    //This is called from AddDeviceWindowController
    public boolean insert(DeviceType dt) {
        String DeviceType = dt.getDeviceType();
        String Description = dt.getLongDesc();

        //We will check the DeviceType does not already exist
        String sqlcmd = "SELECT * FROM controllertypes WHERE "
                + "controllertype='" + DeviceType + "';";
        List resultset = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        if (resultset.size() < 1) {

            //Now we can insert
            sqlcmd = "INSERT INTO controllertypes SET controllertype='"
                    + DeviceType + "', longname='" + Description + "';";

            //Execute it
            sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);

            return true;
        } else {
            try {
                new InfoPopup("That device type already exists, please change");
            } catch (Exception e) {
                EgLogger.logInfo("Popup Failure");
            }
            return false;
        }
    }

    public void delete(DeviceType dt) {
        //Run SQL Command
        String sqlcmd = "DELETE FROM controllertypes WHERE rowid=" + dt.getUniqueID() + ";";
        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
    }

    public boolean update(DeviceType dt) {
        int ID = dt.getUniqueID();
        String DeviceType = dt.getDeviceType();
        String Description = dt.getLongDesc();
        
        try {
            //Get previous value
            String sqlPreviousValue = "SELECT controllertype FROM controllertypes WHERE rowid=" + ID + ";";
            String previousValue = "";
            List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlPreviousValue);
            List resultValues = (List) resultList.get(0);
            previousValue = (String) resultValues.get(0);

            //Now update tables
            String sqlcmd = "UPDATE controllertypes SET controllertype='" + DeviceType
                    + "', " + "longname='" + Description + "' "
                    + "WHERE rowid=" + ID + ";";

            String sqlcmd2 = "UPDATE slaves SET controllertype='" + DeviceType
                    + "' WHERE controllertype='" + previousValue + "';";

            String sqlcmd3 = "UPDATE registerblocks SET controllertype='" + DeviceType
                    + "' WHERE controllertype='" + previousValue + "';";

            String sqlcmd4 = "UPDATE registerdetail SET controllertype='" + DeviceType
                    + "' WHERE controllertype='" + previousValue + "';";

            sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
            sqlConnection.getInstance().SQLUpdateCommand(sqlcmd2);
            sqlConnection.getInstance().SQLUpdateCommand(sqlcmd3);
            sqlConnection.getInstance().SQLUpdateCommand(sqlcmd4);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
