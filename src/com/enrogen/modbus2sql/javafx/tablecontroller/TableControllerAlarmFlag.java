package com.enrogen.modbus2sql.javafx.tablecontroller;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.mainWindow;
import com.enrogen.modbus2sql.sql.AlarmFlag;
import com.enrogen.modbus2sql.sql.AlarmFlagSQLController;
import com.enrogen.modbus2sql.sql.DeviceType;
import com.enrogen.modbus2sql.sql.DeviceTypeSQLController;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableControllerAlarmFlag {

    private static TableControllerAlarmFlag instance;
    private static TableView tv;

    //Singleton
    public static TableControllerAlarmFlag getInstance() {
        if (instance == null) {
            instance = new TableControllerAlarmFlag();
        }
        return instance;
    }

    public TableControllerAlarmFlag() {
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();
        tv = mwc.getAlarmFlagTable();
        tv.setEditable(false);

        //Update the columns to link to the data
        TableColumn ta = (TableColumn) tv.getColumns().get(0);
        ta.setCellValueFactory(
                new PropertyValueFactory<>("UniqueID")
        );

        TableColumn tb = (TableColumn) tv.getColumns().get(1);
        tb.setCellValueFactory(
                new PropertyValueFactory<>("DeviceType")
        );

        TableColumn tc = (TableColumn) tv.getColumns().get(2);
        tc.setCellValueFactory(
                new PropertyValueFactory<>("WarningRegister")
        );

        TableColumn td = (TableColumn) tv.getColumns().get(3);
        td.setCellValueFactory(
                new PropertyValueFactory<>("WarningBit")
        );

        TableColumn te = (TableColumn) tv.getColumns().get(4);
        te.setCellValueFactory(
                new PropertyValueFactory<>("ShutdownRegister")
        );

        TableColumn tf = (TableColumn) tv.getColumns().get(5);
        tf.setCellValueFactory(
                new PropertyValueFactory<>("ShutdownBit")
        );

        TableColumn tg = (TableColumn) tv.getColumns().get(6);
        tg.setCellValueFactory(
                new PropertyValueFactory<>("TripRegister")
        );

        TableColumn th = (TableColumn) tv.getColumns().get(7);
        th.setCellValueFactory(
                new PropertyValueFactory<>("TripBit")
        );

    }

    public void refreshAlarmFlagTable() {
        //Get the data
        AlarmFlagSQLController afc = new AlarmFlagSQLController();
        ObservableList<AlarmFlag> AlarmFlagList = afc.getAlarmFlags();

        tv.getItems().clear();
        tv.setItems(AlarmFlagList);
    }

    public AlarmFlag getSelectedAlarmFlag() {
        //Get the device
        return (AlarmFlag) tv.getSelectionModel().getSelectedItem();
    }

    public void deleteAlarmFlag() {
        try {
            AlarmFlag af = getSelectedAlarmFlag();
            new AlarmFlagSQLController().delete(af);
        } catch (Exception e) {
            new InfoPopup("You must select a device to delete it");
        }
        refreshAlarmFlagTable();
    }

}
