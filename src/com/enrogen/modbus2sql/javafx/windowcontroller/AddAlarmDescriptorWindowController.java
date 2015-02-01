package com.enrogen.modbus2sql.javafx.windowcontroller;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerAlarmDescriptor;
import com.enrogen.modbus2sql.sql.AlarmDescriptor;
import com.enrogen.modbus2sql.sql.AlarmDescriptorSQLController;
import com.enrogen.modbus2sql.sql.DeviceType;
import com.enrogen.modbus2sql.sql.DeviceTypeSQLController;
import com.opencsv.CSVReader;
import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddAlarmDescriptorWindowController implements Initializable {

    final FileChooser fileChooser = new FileChooser();
    private File selFile = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Init the combo boxes
        ObservableList<DeviceType> DeviceList = new DeviceTypeSQLController().getDevices();
        for (int i = 0; i < DeviceList.size(); i++) {
            DeviceTypeComboBox.getItems().add(DeviceList.get(i).getDeviceType());
        }
        DeviceTypeComboBox.getSelectionModel().selectFirst();

        //Setup the CSV file chooser
        fileChooser.setTitle("Select CSV");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
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

    @FXML
    private Button ButtonFileChooser;

    //Methods
    @FXML
    private void ButtonInsert_click(ActionEvent ae) {
        if (TextFieldFilePath.getText().isEmpty()) {
            new InfoPopup("You have not selected a file");
            return;
        } else {
            //Read the CSV
            parseCSV();

            //Push the table controller and exit
            TableControllerAlarmDescriptor.getInstance().refreshAlarmDescriptorTable();
            Stage stage = (Stage) ButtonInsert.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void ButtonCancel_click(ActionEvent ae) {
        Stage stage = (Stage) ButtonCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void ButtonFileChooser_click(ActionEvent ae) {
        selFile = fileChooser.showOpenDialog((Stage) ButtonFileChooser.getScene().getWindow());
        TextFieldFilePath.setText(selFile.getAbsolutePath());
    }

    public void parseCSV() {
        List CSVEntries = null;

        //Read the file
        try {
            CSVReader reader = new CSVReader(new FileReader(selFile.getAbsolutePath()));
            CSVEntries = reader.readAll();
        } catch (Exception e) {
            new InfoPopup("There was an issue trying to open the CSV file");
            e.printStackTrace();
            return;
        }

        String DeviceType = (String) DeviceTypeComboBox.getSelectionModel().getSelectedItem();

        //Read them into the list
        for (int i = 0; i < CSVEntries.size(); i++) {
            String[] row = (String[]) CSVEntries.get(i);
            int entryQty = row.length;

            AlarmDescriptor ad = new AlarmDescriptor();

            //16 Bits on MSB0 (ie Bit 0=Left Bit, 15=right bit)
            ad.setDeviceType(DeviceType);
            ad.setAlarmString((String) row[0]);
            ad.setModbusRegister(Integer.valueOf(row[1]));
            ad.setStartBit(Integer.valueOf(row[2]));
            ad.setBitQty(Integer.valueOf(row[3]));
            ad.setValueDisabled(Integer.valueOf(row[4]));
            ad.setValueWarning(Integer.valueOf(row[5]));
            ad.setValueShutdown(Integer.valueOf(row[6]));
            ad.setValueTripped(Integer.valueOf(row[7]));
            ad.setValueUnimplemented(Integer.valueOf(row[8]));

            //Now insert the alarm into the database
            new AlarmDescriptorSQLController().insert(ad);

        }
    }
}
