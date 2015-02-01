package com.enrogen.modbus2sql;

import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerDeviceTypes;
import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.configuration.config;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerAlarmFlag;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerRegisterBlocks;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerSlaves;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.sql.sqlConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;

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

        //Now startup the SQL
        EgLogger.logInfo("Starting SQL sub-system");
        sqlConnection.getInstance().StartSQL();

        //Start the watchdog
        SystemWatchdogTimer();
        
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void SystemWatchdog() {
        //Get the window controller
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();
        
        //Get sqlConnection Instance
        sqlConnection sc = sqlConnection.getInstance();

        if (sc.isAlive()) {
            //Start Things
            mwc.red_lamp_off();
            mwc.unlockTabs();

            //Flash Lamps
            mwc.blink_green_lamp();

            //if sql was down and is now up
            if (sc.isRestarted()) {
            //    StartModbusTicker();
                //    StartWatchDogTicker();
                sc.resetRestartedFlag();
                refreshSetupTab();

                //Initial startup flag
                // if (startModbusFlag = true) {
                //     startModbusFlag = false;
                //     btnStart();
                //  }
            }
        } else {
            //Stop Things
            mwc.green_lamp_off();
            mwc.lockTabs();

            try {
              //  modbusTimer.stop();
                //  watchdogTimer.stop();
            } catch (Exception e) {
            }

            //Flash lamps
            mwc.blink_red_lamp();
        }
    }

    //The timer to keep alive
    private void SystemWatchdogTimer() {
        if (systemWatchdogTimer == null) {
            //Create a thread to flash the lamps
            EgLogger.logInfo("Starting System Watchdog Ticker at : " + SYSTEM_ALIVE_POLL_TICKER + "mSec");

            systemWatchdogTimer = new Timeline(new KeyFrame(
                    Duration.millis(SYSTEM_ALIVE_POLL_TICKER),
                    ae -> SystemWatchdog()));
            systemWatchdogTimer.setCycleCount(Animation.INDEFINITE);
        }
        systemWatchdogTimer.play();
    }

    public void refreshSetupTab() {
        if (sqlConnection.getInstance().isAlive()) {
            EgLogger.logInfo("Refreshing GUI Setup Tab");
        
            //This inits the various tables in the app via their respective controllers
            new TableControllerDeviceTypes().refreshDeviceTypesTable();
            new TableControllerRegisterBlocks().refreshRegisterBlocksTable();
            new TableControllerSlaves().refreshSlaveDetailTable();
            new TableControllerAlarmFlag().refreshAlarmFlagTable();
            
            //This is the balance of standard form components
            mainWindowController mwc = mainWindow.getInstance().getMainWindowController();
            
            mwc.RS485TabRefresh();
            //fillViewerComboBox();
            //refreshAlarmsMonitoredTable();
            //refreshAlarmAnnunciatortable();
        }
        return;
    }
}
