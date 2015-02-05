package com.enrogen.modbus2sql.javafx.windowcontroller;

import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerRegisterBlocks;
import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.components.NumberSpinner;
import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.sql.*;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddRegisterBlockWindowController implements Initializable, appInterface {

    final FileChooser fileChooser = new FileChooser();
    private File selFile = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Init the combo boxes
        ObservableList<DeviceType> DeviceList = new DeviceTypeSQLController().getDevices();
        for (int i = 0; i < DeviceList.size(); i++) {
            ChoiceBoxDeviceType.getItems().add(DeviceList.get(i).getDeviceType());
        }
        ChoiceBoxDeviceType.getSelectionModel().selectFirst();

        //The page no spinner
        PageNoSpinner.setMaxValueProperty(BigDecimal.valueOf(9999));
        PageNoSpinner.setMinValueProperty(BigDecimal.valueOf(0));

        //Setup the Modbus read type
        //Some are commented out as unsupported
        ChoiceBoxModbusReadType.getItems().add(MODBUS_FUNC_CODE_READ_MULTIPLE_STRING);
        //ChoiceBoxModbusReadType.getItems().add(MODBUS_FUNC_CODE_READ_COILS_STRING);
        //ChoiceBoxModbusReadType.getItems().add(MODBUS_FUNC_CODE_READ_INP_DIS_STRING);
        //ChoiceBoxModbusReadType.getItems().add(MODBUS_FUNC_CODE_READ_INP_REG_STRING);
        ChoiceBoxModbusReadType.getSelectionModel().selectFirst();

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

    @FXML
    private ChoiceBox ChoiceBoxDeviceType;

    @FXML
    private NumberSpinner PageNoSpinner;

    @FXML
    private ChoiceBox ChoiceBoxModbusReadType;

    @FXML
    private TextArea TextAreaDescription;

    @FXML
    private TextField TextFieldFileName;

    @FXML
    private Button ButtonFileChooser;

    @FXML
    private void ButtonFileChooser_click(ActionEvent ae) {
        selFile = fileChooser.showOpenDialog((Stage) ButtonFileChooser.getScene().getWindow());
        TextFieldFileName.setText(selFile.getName());
    }

    @FXML
    private Button ButtonInsert;

    @FXML
    private void ButtonInsert_click(ActionEvent ae) {
        boolean FormCheck = true;
        String message = "";

        Integer page = (Integer) PageNoSpinner.getNumber().intValue();
        if (page == 0) {
            FormCheck = false;
            message = "Please specify a page number";
        }

        if (TextAreaDescription.getText().isEmpty()) {
            FormCheck = false;
            message = "You must give a description";
        }

        if (TextFieldFileName.getText().isEmpty()) {
            FormCheck = false;
            message = "You have not selected a file";
        }

        //Main routine
        if (!FormCheck) {
            new InfoPopup(message);
            return;
        }

        //Read in the CSV File
        List list = null;
        try {
            list = readCSV(selFile);
            CSVItterator(list);
        } catch (Exception E) {
            new InfoPopup("There was an issue opening the CSV file");
            return;
        }
        
        //Push the Table Controller
        TableControllerRegisterBlocks.getInstance().refreshRegisterBlocksTable();
        
        //Now close the window
        Stage stage = (Stage) ButtonInsert.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button ButtonCancel;

    @FXML
    private void ButtonCancel_click(ActionEvent ae) {
        Stage stage = (Stage) ButtonCancel.getScene().getWindow();
        stage.close();
    }

    public List readCSV(File filename) throws Exception {
        List myEntries;
        CSVReader reader = new CSVReader(new FileReader(filename));
        myEntries = reader.readAll();
        return myEntries;
    }

    public boolean CSVItterator(List CSVEntries) {
        try {
            //Prepeare the data to be entered
            int rows = CSVEntries.size();
            String DeviceType = (String) ChoiceBoxDeviceType.getValue();
            Integer PageNo = (Integer) PageNoSpinner.getNumber().intValue();
            Integer StartReg = 0;
            Integer EndReg = 0;

            //Get the combo function to a Modbus Function Integer
            String selectedFunction = ChoiceBoxModbusReadType.getValue().toString();
            int ModbusFuncCode = 0;
            if (selectedFunction.compareTo(MODBUS_FUNC_CODE_READ_COILS_STRING) == 0) {
                ModbusFuncCode = MODBUS_FUNC_CODE_READ_COILS;
            }
            if (selectedFunction.compareTo(MODBUS_FUNC_CODE_READ_INP_DIS_STRING) == 0) {
                ModbusFuncCode = MODBUS_FUNC_CODE_READ_INP_DIS;
            }
            if (selectedFunction.compareTo(MODBUS_FUNC_CODE_READ_MULTIPLE_STRING) == 0) {
                ModbusFuncCode = MODBUS_FUNC_CODE_READ_MULTIPLE;
            }
            if (selectedFunction.compareTo(MODBUS_FUNC_CODE_READ_INP_REG_STRING) == 0) {
                ModbusFuncCode = MODBUS_FUNC_CODE_READ_INP_REG;
            }

            for (int i = 0; i < rows; i++) {
                String[] row = (String[]) CSVEntries.get(i);
                int entryQty = row.length;

                Integer RegisterNo = tryParseInt(row[0]);
                String Description = row[1];
                Double MinimumValue = tryParseDouble(row[2]);
                Double MaximumValue = tryParseDouble(row[3]);
                Double ScalingFactor = tryParseDouble(row[4]);
                String Units = row[5];
                String bitsString = row[6];
                Boolean Writeable = Boolean.valueOf(row[7]);
                Integer LowByteRegister = tryParseInt(row[8]);
                Integer UnimplementedValue = tryParseInt(row[9]);

                //We keep a look out for the first and last row
                //and whether its signed etc
                Integer bitsInt;
                Boolean signed;

                //Record first register
                if (i == 0) {
                    StartReg = RegisterNo;
                }

                //Record last register
                if (i == rows - 1) {
                    EndReg = RegisterNo;
                }

                //Check the bits and signing
                if (bitsString.contains("32")) {
                    bitsInt = 32;
                } else {
                    bitsInt = 16;
                }

                if (bitsString.contains("S")) {
                    signed = true;
                } else {
                    signed = false;
                }

                //Insert the register
                RegisterDetail rd = new RegisterDetail(null, RegisterNo, DeviceType, PageNo,
                        Description, bitsInt, signed, LowByteRegister, ScalingFactor, MinimumValue,
                        MaximumValue, Units, Writeable, UnimplementedValue);
                new RegisterDetailSQLController().insert(rd);

                //End for
            }

            //Insert reference to the register block
            String RegisterBlockDescription = TextAreaDescription.getText();
            RegisterBlock rb = new RegisterBlock(null, DeviceType, PageNo, StartReg, EndReg,
                    RegisterBlockDescription, ModbusFuncCode);
            new RegisterBlockSQLController().insert(rb);
            return true;

        } catch (Exception e) {
            EgLogger.logSevere("Issue trying to parse CSV");
            e.printStackTrace();
            return false;
        }
    }

    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            // Log exception.
            return 0;
        }
    }

    private Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            // Log exception.
            return 0.0;
        }
    }

}
