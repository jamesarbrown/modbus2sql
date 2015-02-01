package com.enrogen.modbus2sql.javafx.tablecontroller;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.mainWindow;
import com.enrogen.modbus2sql.sql.DeviceType;
import com.enrogen.modbus2sql.sql.DeviceTypeSQLController;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableControllerDeviceTypes {

    private static TableControllerDeviceTypes instance;
    private static TableView tv;
    
    //Singleton
    public static TableControllerDeviceTypes getInstance() {
        if (instance == null) {
            instance = new TableControllerDeviceTypes();
    }
        return instance;
    }
    
    public TableControllerDeviceTypes() {
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();
        tv = mwc.getTable_controllertypes();
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
                new PropertyValueFactory<>("LongDesc")
        );
    }
    
    public void refreshDeviceTypesTable() {
        //Get the data
        DeviceTypeSQLController dtc = new DeviceTypeSQLController();
        dtc.refresh();
        ObservableList<DeviceType> DeviceList = dtc.getDevices();

        tv.getItems().clear();
        tv.setItems(DeviceList);
    }

    public DeviceType getSelectedDevice() {
        //Get the device
        return (DeviceType) tv.getSelectionModel().getSelectedItem();
    }

    public void deleteDeviceType() {
        try {
            DeviceType dt = getSelectedDevice();
            new DeviceTypeSQLController().delete(dt);
        } catch (Exception e) {
            new InfoPopup("You must select a device to delete it");
        }
        refreshDeviceTypesTable();
    }
    
}
