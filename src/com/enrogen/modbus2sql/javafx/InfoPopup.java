package com.enrogen.modbus2sql.javafx;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.javafx.windowcontroller.InfoPopupController;
import com.enrogen.modbus2sql.logger.EgLogger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InfoPopup implements appInterface {

    private final String mainWindowResource = JAVAFX_FXML_LOCATION + "InfoPopup.fxml";

    public InfoPopup(String message) {
        try {
            //Show the window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));
            Parent root = fxmlLoader.load();
            InfoPopupController wc = fxmlLoader.getController();

            //Prepare the dialog
            final Stage dialog = new Stage();
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.centerOnScreen();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Error");

            //Set the parameters
            wc.setMessage(message);

            dialog.show();
        } catch (Exception e) {
            EgLogger.logInfo("Could not create InfoPopup message box");
        }
    }
}
