package com.enrogen.modbus2sql.javafx.windowcontroller;

import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerDeviceTypes;
import com.enrogen.modbus2sql.sql.DeviceType;
import com.enrogen.modbus2sql.sql.DeviceTypeSQLController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddDeviceWindowController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Default value for Insert Button
        btn_insert.setText("Insert");
    }

    //These are set if the window was opened in edit mode
    //called from mainWindow
    private int rowid;
    private boolean editMode = false;

    @FXML
    private TextField txt_Type;

    @FXML
    private TextField txt_Desc;

    @FXML
    private Button btn_insert;

    @FXML
    private void btn_insert_click(ActionEvent ae) {
        boolean returnValue;

        if (!isEditMode()) {
            //Push into the SQL Controller
            DeviceType dt = new DeviceType(null, txt_Type.getText(), txt_Desc.getText());
            returnValue = new DeviceTypeSQLController().insert(dt);
        } else {
            //The rowid was set when the Dialog was generated in edit mode only
            DeviceType dt = new DeviceType(rowid, txt_Type.getText(), txt_Desc.getText());
            returnValue = new DeviceTypeSQLController().update(dt);
        }

        //Push the Table Controller
        TableControllerDeviceTypes.getInstance().refreshDeviceTypesTable();

        if (returnValue) {
            //Get this window and close it
            Stage stage = (Stage) btn_insert.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private Button btn_cancel;

    @FXML
    private void btn_cancel_click(ActionEvent ae) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        if (editMode) {
            btn_insert.setText("Apply");
        }
    }

    //Getters and Setters
    public void setType(String type) {
        txt_Type.setText(type);
    }

    public void setDesc(String desc) {
        txt_Desc.setText(desc);
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

}
