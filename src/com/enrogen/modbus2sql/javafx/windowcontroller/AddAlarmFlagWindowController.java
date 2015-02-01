package com.enrogen.modbus2sql.javafx.windowcontroller;

import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerAlarmFlag;
import com.enrogen.modbus2sql.components.NumberSpinner;
import com.enrogen.modbus2sql.mainWindow;
import com.enrogen.modbus2sql.sql.AlarmFlag;
import com.enrogen.modbus2sql.sql.AlarmFlagSQLController;
import com.enrogen.modbus2sql.sql.DeviceType;
import com.enrogen.modbus2sql.sql.DeviceTypeSQLController;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class AddAlarmFlagWindowController implements Initializable {

    private Boolean isEditMode = false;
    private Integer rowID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Init the combo boxes
        ObservableList<DeviceType> DeviceList = new DeviceTypeSQLController().getDevices();
        for (int i = 0; i < DeviceList.size(); i++) {
            DeviceTypeComboBox.getItems().add(DeviceList.get(i).getDeviceType());
        }
        DeviceTypeComboBox.getSelectionModel().selectFirst();

        //Setup the number spinners
        WarningRegisterNumberSpinner.setMinValueProperty(BigDecimal.ZERO);
        WarningBitNumberSpinner.setMinValueProperty(BigDecimal.ZERO);
        WarningBitNumberSpinner.setMaxValueProperty(BigDecimal.valueOf(8));

        ShutdownRegisterNumberSpinner.setMinValueProperty(BigDecimal.ZERO);
        ShutdownBitNumberSpinner.setMinValueProperty(BigDecimal.ZERO);
        ShutdownBitNumberSpinner.setMaxValueProperty(BigDecimal.valueOf(8));

        TrippedRegisterNumberSpinner.setMinValueProperty(BigDecimal.ZERO);
        TrippedBitNumberSpinner.setMinValueProperty(BigDecimal.ZERO);
        TrippedBitNumberSpinner.setMaxValueProperty(BigDecimal.valueOf(8));
    }

    //The components
    @FXML
    private ComboBox DeviceTypeComboBox;

    @FXML
    private NumberSpinner WarningRegisterNumberSpinner;

    @FXML
    private NumberSpinner WarningBitNumberSpinner;

    @FXML
    private NumberSpinner ShutdownRegisterNumberSpinner;

    @FXML
    private NumberSpinner ShutdownBitNumberSpinner;

    @FXML
    private NumberSpinner TrippedRegisterNumberSpinner;

    @FXML
    private NumberSpinner TrippedBitNumberSpinner;

    @FXML
    private Button InsertButton;

    @FXML
    private Button CancelButton;

    //The Methods
    @FXML
    private void InsertButton_click(ActionEvent ae) {
        //Insert the values
        AlarmFlag af = new AlarmFlag();
        af.setDeviceType((String) DeviceTypeComboBox.getValue());
        af.setWarningRegister((Integer) WarningRegisterNumberSpinner.getNumber().intValue());
        af.setWarningBit((Integer) WarningBitNumberSpinner.getNumber().intValue());
        af.setShutdownRegister((Integer) ShutdownRegisterNumberSpinner.getNumber().intValue());
        af.setShutdownBit((Integer) ShutdownBitNumberSpinner.getNumber().intValue());
        af.setTripRegister((Integer) TrippedRegisterNumberSpinner.getNumber().intValue());
        af.setTripBit((Integer) TrippedBitNumberSpinner.getNumber().intValue());

        if (isEditMode) {
            af.setUniqueID(rowID);
            new AlarmFlagSQLController().update(af);
        } else {
            new AlarmFlagSQLController().insert(af);
        }

        //Push the table
        TableControllerAlarmFlag.getInstance().refreshAlarmFlagTable();
        
        Stage stage = (Stage) InsertButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void CancelButton_click(ActionEvent ae) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    //Getters+Setters
    public void setEditMode(Boolean isEditMode) {
        this.isEditMode = isEditMode;
        InsertButton.setText("Update");
    }

    public void setRowID(Integer rowID) {
        this.rowID = rowID;
    }

    public void setWarningRegister(Integer register) {
        WarningRegisterNumberSpinner.setNumber(BigDecimal.valueOf(register));
    }

    public void setWarningBit(Integer bit) {
        WarningBitNumberSpinner.setNumber(BigDecimal.valueOf(bit));
    }

    public void setShutdownRegister(Integer register) {
        ShutdownRegisterNumberSpinner.setNumber(BigDecimal.valueOf(register));
    }

    public void setShutdownBit(Integer bit) {
        ShutdownBitNumberSpinner.setNumber(BigDecimal.valueOf(bit));
    }

    public void setTrippedRegister(Integer register) {
        TrippedRegisterNumberSpinner.setNumber(BigDecimal.valueOf(register));
    }
    
    public void setTrippedBit(Integer bit) {
        TrippedBitNumberSpinner.setNumber(BigDecimal.valueOf(bit));
    }
}
