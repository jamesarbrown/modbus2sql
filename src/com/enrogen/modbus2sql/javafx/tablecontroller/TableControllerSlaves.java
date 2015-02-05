package com.enrogen.modbus2sql.javafx.tablecontroller;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.mainWindow;
import com.enrogen.modbus2sql.sql.SlaveDetail;
import com.enrogen.modbus2sql.sql.SlaveDetailSQLController;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableControllerSlaves {

    private static TableControllerSlaves instance;
    private static TableView tv;

    //Singleton
    public static TableControllerSlaves getInstance() {
        if (instance == null) {
            instance = new TableControllerSlaves();
        }
        return instance;
    }

    public TableControllerSlaves() {
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();
        tv = mwc.getTable_SlaveDetail();
        tv.setEditable(false);

        //Update the columns to link to the data
        TableColumn ta = (TableColumn) tv.getColumns().get(0);
        ta.setCellValueFactory(
                new PropertyValueFactory<>("RowID")
        );

        TableColumn tb = (TableColumn) tv.getColumns().get(1);
        tb.setCellValueFactory(
                new PropertyValueFactory<>("SlaveID")
        );

        TableColumn tc = (TableColumn) tv.getColumns().get(2);
        tc.setCellValueFactory(
                new PropertyValueFactory<>("Description")
        );

        TableColumn td = (TableColumn) tv.getColumns().get(3);
        td.setCellValueFactory(
                new PropertyValueFactory<>("DeviceType")
        );

        TableColumn te = (TableColumn) tv.getColumns().get(4);
        te.setCellValueFactory(
                new PropertyValueFactory<>("UseRS485")
        );

        TableColumn tf = (TableColumn) tv.getColumns().get(5);
        tf.setCellValueFactory(
                new PropertyValueFactory<>("IpAddress")
        );

        refreshSlaveDetailTable();
    }

    public void refreshSlaveDetailTable() {
        //Get the data
        SlaveDetailSQLController dtc = new SlaveDetailSQLController();
        dtc.refresh();
        ObservableList<SlaveDetail> SlaveList = dtc.getSlaves();

        tv.getItems().clear();
        tv.setItems(SlaveList);
    }

    public SlaveDetail getSelectedSlave() {
        //Get the device
        SlaveDetail sd = (SlaveDetail) tv.getSelectionModel().getSelectedItem();
        return sd;
    }

    public void deleteSlave() {
        try {
            SlaveDetail sd = getSelectedSlave();
            new SlaveDetailSQLController().delete(sd);
        } catch (Exception e) {
            new InfoPopup("You must select a slave to delete it");
        }
        refreshSlaveDetailTable();
    }

}
