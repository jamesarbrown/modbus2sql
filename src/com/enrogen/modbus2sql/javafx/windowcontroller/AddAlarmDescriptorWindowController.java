package com.enrogen.modbus2sql.javafx.windowcontroller;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.sql.DeviceType;
import com.enrogen.modbus2sql.sql.DeviceTypeSQLController;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAlarmDescriptorWindowController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Init the combo boxes
        ObservableList<DeviceType> DeviceList = new DeviceTypeSQLController().getDevices();
        for (int i = 0; i < DeviceList.size(); i++) {
            DeviceTypeComboBox.getItems().add(DeviceList.get(i).getDeviceType());
        }
        DeviceTypeComboBox.getSelectionModel().selectFirst();
    }

    //Components
    @FXML
    private ComboBox DeviceTypeComboBox;

    @FXML
    private TextField TextFieldFilePath;

    @FXML
    private Button ButtonInsert;

    @FXML
    private Button ButtonCancel;

    //Methods
    @FXML
    private void ButtonInsert_click(ActionEvent ae) {
        if (TextFieldFilePath.getText().isEmpty()) {
            new InfoPopup("You have not selected a file");
            return;
        } else {
            parseCSV();
        }
    }

    @FXML
    private void ButtonCancel_click(ActionEvent ae) {
        Stage stage = (Stage) ButtonCancel.getScene().getWindow();
        stage.close();
    }

    public void parseCSV() {
        List CSVEntries = null;
        
        //Read the file
        try {
            CSVReader reader = new CSVReader(new FileReader(TextFieldFilePath.getText()));
            CSVEntries = reader.readAll();
        } catch (Exception e) {
            new InfoPopup("There was an issue trying to open the CSV file");
            return;
        }

        //Read them into the list
        for (int i = 0; i < CSVEntries.size(); i++) {
            String[] row = (String[]) CSVEntries.get(i);
            int entryQty = row.length;

            String alarmstring = row[0];
            String modbusregister = row[1];
            String startbit = row[2];
            String bitqty = row[3];
            String modbusregisterdescription = row[4];
            String registerqtydescription = row[5];
            String valuedisabled = row[6];
            String valuehealthy = row[7];
            String valuewarning = row[8];
            String valueshutdown = row[9];
            String valuetrip = row[10];
            String valueunimplemented = row[11];
        
    }
                String controllertype = (String) combo_type.getSelectedItem();

}
