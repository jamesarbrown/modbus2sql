package com.enrogen.modbus2sql;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.configuration.config;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.threads.MainThread;
import com.enrogen.modbus2sql.threads.SQLThread;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class appStart extends Application implements appInterface {

    private mainWindow mw;
    private Timeline systemWatchdogTimer = null;

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("MODBUS2SQL - Entering Startup");

        //Makesure .modbus2sql is created
        config c = new config();
        if (!c.checkHomeDirExists()) {
            System.out.print("FATAL : No home directory and could not create one...exiting...");
            Platform.exit();
        }

        //Show the main Window
        mainWindow.getInstance().start(stage);

        //This will trigger the logging startup, it is created in home_dir/.modbus2sql/logs/
        EgLogger.logInfo("Application Startup");

        //Read the XML Settings
        boolean startupOK = c.firstStart();

        //Setup debuging of the modules
        new config().setupDebugging();

        //If startup start the modules
        if (startupOK) {
        } else {
            Platform.exit();
        }

        //Start the SQL Keep Alive Thread
        SQLThread.getInstance().StartSQLThread();

        //We can start the main infinate loop of the mainthread
        //This thread will start and stop the Modbus loop based on whether
        //The SQL Server is up or down.
        try {
            MainThread.getInstance().StartMainThread();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
