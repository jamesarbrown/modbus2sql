package com.enrogen.modbus2sql.javafx.tablecontroller;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.mainWindow;
import com.enrogen.modbus2sql.sql.AlarmDescriptor;
import com.enrogen.modbus2sql.sql.AlarmDescriptorSQLController;
import com.enrogen.modbus2sql.sql.AlarmFlag;
import com.enrogen.modbus2sql.sql.AlarmFlagSQLController;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableControllerAlarmDescriptor {

    private static TableControllerAlarmDescriptor instance;
    private static TableView tv;

    //Singleton
    public static TableControllerAlarmDescriptor getInstance() {
        if (instance == null) {
            instance = new TableControllerAlarmDescriptor();
        }
        return instance;
    }

    public TableControllerAlarmDescriptor() {
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();
        tv = mwc.getAlarmDescriptorTable();
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
                new PropertyValueFactory<>("AlarmString")
        );

        TableColumn td = (TableColumn) tv.getColumns().get(3);
        td.setCellValueFactory(
                new PropertyValueFactory<>("ModbusRegister")
        );

        TableColumn te = (TableColumn) tv.getColumns().get(4);
        te.setCellValueFactory(
                new PropertyValueFactory<>("StartBit")
        );

        TableColumn tf = (TableColumn) tv.getColumns().get(5);
        tf.setCellValueFactory(
                new PropertyValueFactory<>("BitQty")
        );

        TableColumn tg = (TableColumn) tv.getColumns().get(6);
        tg.setCellValueFactory(
                new PropertyValueFactory<>("ValueWarning")
        );

        TableColumn th = (TableColumn) tv.getColumns().get(7);
        th.setCellValueFactory(
                new PropertyValueFactory<>("ValueShutdown")
        );

        TableColumn ti = (TableColumn) tv.getColumns().get(8);
        ti.setCellValueFactory(
                new PropertyValueFactory<>("ValueTripped")
        );

        TableColumn tj = (TableColumn) tv.getColumns().get(9);
        tj.setCellValueFactory(
                new PropertyValueFactory<>("ValueUnimplemented")
        );

    }

    public void refreshAlarmDescriptorTable() {
        //Get the data
        AlarmDescriptorSQLController adc = new AlarmDescriptorSQLController();
        ObservableList<AlarmDescriptor> AlarmDescriptorList = adc.getAlarmDescriptors();

        tv.getItems().clear();
        tv.setItems(AlarmDescriptorList);
    }

    public AlarmDescriptor getSelectedAlarmDescriptor() {
        //Get the device
        return (AlarmDescriptor) tv.getSelectionModel().getSelectedItem();
    }

    public void deleteAlarmDescriptors() {
        try {
            AlarmDescriptor ad = getSelectedAlarmDescriptor();
            new AlarmDescriptorSQLController().delete(ad);
        } catch (Exception e) {
            new InfoPopup("You must select a device to delete it");
        }
        refreshAlarmDescriptorTable();
    }

    public void deleteAllAlarmDescriptors() {
        try {
            AlarmDescriptor ad = getSelectedAlarmDescriptor();
            new AlarmDescriptorSQLController().deleteAll(ad.getDeviceType());
        } catch (Exception e) {
            new InfoPopup("You must select a device to delete it");
        }
        refreshAlarmDescriptorTable();
    }

}
