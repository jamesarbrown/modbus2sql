package com.enrogen.modbus2sql;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainWindow implements appInterface {
    //Declare the variables
    private static mainWindow instance;
    private static final String mainWindowResource = "/com/enrogen/modbus2sql/javafx/fxml/mainWindow.fxml";
    private mainWindowController mwc;

    private mainWindow() {
    }
    
    //We want to create a singleton to access mwc
    public static mainWindow getInstance() {
        if (instance == null) {
            instance = new mainWindow();
    }
        return instance;
    }
    
    public void start(Stage stage) throws Exception {
        //Show the window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));  
        Parent root = fxmlLoader.load();  
        mwc = fxmlLoader.getController();   
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modbus2SQL");
        stage.setMinWidth(MINIMUM_H_WINDOW_SIZE);
        stage.setMinHeight(MINIMUM_V_WINDOW_SIZE);
        stage.show();
    }

    public mainWindowController getMainWindowController() {
        return mwc;
    }
}
