package com.enrogen.modbus2sql.javafx.windowcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InfoPopupController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private Label text_message;

    public void setMessage(String text) {
        text_message.setText(text);
    }

    @FXML
    private Button ok;

    public void ok_clicked(ActionEvent ae) {
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

}
