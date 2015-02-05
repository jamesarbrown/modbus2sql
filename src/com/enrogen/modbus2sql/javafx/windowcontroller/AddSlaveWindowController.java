package com.enrogen.modbus2sql.javafx.windowcontroller;

import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerSlaves;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerAlarmFlag;
import com.enrogen.modbus2sql.components.NumberSpinner;
import com.enrogen.modbus2sql.sql.SlaveDetail;
import com.enrogen.modbus2sql.sql.SlaveDetailSQLController;
import com.enrogen.modbus2sql.sql.sqlConnection;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddSlaveWindowController implements Initializable {

    //These are set if the window was opened in edit mode
    //called from mainWindow
    private int rowid;
    private boolean editMode = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //The page no spinner
        SlaveIDNoSpinner.setMaxValueProperty(BigDecimal.valueOf(999));
        SlaveIDNoSpinner.setMinValueProperty(BigDecimal.valueOf(1));

        //Get the slaves available and add to choice box
        String sqlcmd = "SELECT controllertype FROM controllertypes";
        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        for (int z = 0; z < resultList.size(); z++) {
            List resultValues = (List) resultList.get(z);
            ChoiceBoxDevice.getItems().add((String) resultValues.get(0));
        }

        ChoiceBoxDevice.getSelectionModel().selectFirst();
    }

    @FXML
    private NumberSpinner SlaveIDNoSpinner;

    @FXML
    private TextField TextDescription;

    @FXML
    private ChoiceBox ChoiceBoxDevice;
    
    @FXML
    private CheckBox CheckBoxRS485;
    
    @FXML
    private TextField TextFieldIpAddress;

    @FXML
    private Button ButtonInsert;

    @FXML
    private void ButtonInsert_click(ActionEvent ae) {
        boolean returnValue;
        
        //try to insert the values
        String DeviceType = (String) ChoiceBoxDevice.getSelectionModel().getSelectedItem();
        Integer ModbusSlaveID = SlaveIDNoSpinner.getNumber().intValue();
        String Description = TextDescription.getText();
        Boolean useRS485 = CheckBoxRS485.isSelected();
        String IpAddress = TextFieldIpAddress.getText();

        if (!isEditMode() ) {
            SlaveDetail sd = new SlaveDetail(null, DeviceType, ModbusSlaveID, Description,
                useRS485, IpAddress);
            returnValue = new SlaveDetailSQLController().Insert(sd);
        } else {
            SlaveDetail sd = new SlaveDetail(rowid, DeviceType, ModbusSlaveID, Description,
                useRS485, IpAddress);
            returnValue = new SlaveDetailSQLController().update(sd);
        }
        
        //Push the Table Controller
        TableControllerSlaves.getInstance().refreshSlaveDetailTable();

        if (returnValue) {
            Stage stage = (Stage) ButtonInsert.getScene().getWindow();
            stage.close();
        }
        
        //Push the table
        TableControllerAlarmFlag.getInstance().refreshAlarmFlagTable();
    }

    @FXML
    private Button ButtonCancel;

    @FXML
    private void ButtonCancel_click(ActionEvent ae) {
        Stage stage = (Stage) ButtonCancel.getScene().getWindow();
        stage.close();

    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        if (editMode) {
            ButtonInsert.setText("Apply");
        }
    }
    
    //getters and setters
    public void setRowid(Integer ID) {
        this.rowid = ID;
    }
    
    public void setDescription(String Description) {
        this.TextDescription.setText(Description);
    }
    
    public void setModbusID(Integer ID) {
        this.SlaveIDNoSpinner.setNumber(BigDecimal.valueOf(ID));
    }
    
    public void setUseRS485(Boolean useRS485) {
        this.CheckBoxRS485.setSelected(useRS485);
    }
    
    public void setIpAddress(String IpAddress) {
        this.TextFieldIpAddress.setText(IpAddress);
    }
}
