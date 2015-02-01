package com.enrogen.modbus2sql.javafx.tablecontroller;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.mainWindow;
import com.enrogen.modbus2sql.sql.RegisterBlock;
import com.enrogen.modbus2sql.sql.RegisterBlockSQLController;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableControllerRegisterBlocks {

    private static TableControllerRegisterBlocks instance;
    private static TableView tv;
    
    //Singleton
    public static TableControllerRegisterBlocks getInstance() {
        if (instance == null) {
            instance = new TableControllerRegisterBlocks();
    }
        return instance;
    }
    
    public TableControllerRegisterBlocks() {
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();
        tv = mwc.getTable_registerblocks();
        tv.setEditable(false);

        //Update the columns to link to the data
        TableColumn ta = (TableColumn) tv.getColumns().get(0);
        ta.setCellValueFactory(
                new PropertyValueFactory<>("RowID")
        );

        TableColumn tb = (TableColumn) tv.getColumns().get(1);
        tb.setCellValueFactory(
                new PropertyValueFactory<>("DeviceType")
        );

        TableColumn tc = (TableColumn) tv.getColumns().get(2);
        tc.setCellValueFactory(
                new PropertyValueFactory<>("PageNo")
        );

        TableColumn td = (TableColumn) tv.getColumns().get(3);
        td.setCellValueFactory(
                new PropertyValueFactory<>("RegisterStart")
        );
        
        TableColumn te = (TableColumn) tv.getColumns().get(4);
        te.setCellValueFactory(
                new PropertyValueFactory<>("RegisterEnd")
        );
        
        TableColumn tf = (TableColumn) tv.getColumns().get(5);
        tf.setCellValueFactory(
                new PropertyValueFactory<>("Description")
        );
    }
    
    public void refreshRegisterBlocksTable() {
        //Get the data
        RegisterBlockSQLController dtc = new RegisterBlockSQLController();
        dtc.refresh();
        ObservableList<RegisterBlock> registerList = dtc.getRegisterBlocks();
        tv.getItems().clear();
        tv.setItems(registerList);
    }

    public RegisterBlock getSelectedRegisterBlock() {
        //Reference the table
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();
        TableView tv = mwc.getTable_registerblocks();

        //Get the device
        return (RegisterBlock) tv.getSelectionModel().getSelectedItem();
    }

    public void deleteRegisterBlock() {
        try {
            RegisterBlock rb = getSelectedRegisterBlock();
            new RegisterBlockSQLController().delete(rb);
        } catch (Exception e) {
            new InfoPopup("You must select a block to delete it");
        }
         refreshRegisterBlocksTable();
    }
}
