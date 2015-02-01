package com.enrogen.modbus2sql.threads;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerAlarmDescriptor;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerAlarmFlag;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerDeviceTypes;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerRegisterBlocks;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerSlaves;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.mainWindow;
import com.enrogen.modbus2sql.sql.sqlConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MainThread implements appInterface {

    private static MainThread instance;
    private Timeline DisplayTimer = null;

    //Create singleton instance
    public static MainThread getInstance() {
        if (instance == null) {
            instance = new MainThread();
        }
        return instance;
    }

    public MainThread() {
        if (DisplayTimer == null) {
            //Create a thread to flash the lamps
            EgLogger.logInfo("Starting System Watchdog Ticker at : " + SYSTEM_ALIVE_POLL_TICKER + "mSec");

            DisplayTimer = new Timeline(new KeyFrame(
                    Duration.millis(SYSTEM_ALIVE_POLL_TICKER),
                    ae -> MainLoop()));
            DisplayTimer.setCycleCount(Animation.INDEFINITE);
        }
    }

    public void StartMainThread() {
        EgLogger.logInfo("Starting Display Lamps Thread: " + SYSTEM_ALIVE_POLL_TICKER + "msec");
        DisplayTimer.play();
    }

    public void StopMainThread() {
        EgLogger.logInfo("Stopping Display Lamps Thread");
        DisplayTimer.stop();
    }

    public void MainLoop() {
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

    public void refreshSetupTab() {
        if (sqlConnection.getInstance().isAlive()) {
            EgLogger.logInfo("Refreshing GUI Setup Tab");

            //This inits the various tables in the app via their respective controllers
            new TableControllerDeviceTypes().refreshDeviceTypesTable();
            new TableControllerRegisterBlocks().refreshRegisterBlocksTable();
            new TableControllerSlaves().refreshSlaveDetailTable();
            new TableControllerAlarmFlag().refreshAlarmFlagTable();
            new TableControllerAlarmDescriptor().refreshAlarmDescriptorTable();

            //This is the balance of standard form components
            mainWindowController mwc = mainWindow.getInstance().getMainWindowController();

            mwc.RS485TabRefresh();
            //fillViewerComboBox();
            //refreshAlarmAnnunciatortable();
        }
    }
}
