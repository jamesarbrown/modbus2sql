package com.enrogen.modbus2sql.threads;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.jamod.ModbusDataController;
import com.enrogen.modbus2sql.jamod.ModbusRegister;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.sql.ModbusDataSQLController;
import com.enrogen.modbus2sql.sql.SlaveDetail;
import com.enrogen.modbus2sql.sql.SlaveDetailSQLController;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ModbusThread implements appInterface {

    private static ModbusThread instance;
    private Timeline ModbusLoopTimer = null;
    private boolean ModbusLoopComplete = true;

    //Create singleton instance
    public static ModbusThread getInstance() {
        if (instance == null) {
            instance = new ModbusThread();
        }
        return instance;
    }

    public ModbusThread() {
        //Init the thread
        if (ModbusLoopTimer == null) {
            //Create a thread to flash the lamps
            EgLogger.logInfo("Starting Modbus Polling Ticker at : " + MODBUS_POLL_TICKER + "mSec");

            ModbusLoopTimer = new Timeline(new KeyFrame(
                    Duration.millis(MODBUS_POLL_TICKER),
                    ae -> MainLoop()));
            ModbusLoopTimer.setCycleCount(Animation.INDEFINITE);
        }

    }

    public void StartModbusThread() {
        EgLogger.logInfo("Starting Modbus Loop Thread: " + SYSTEM_ALIVE_POLL_TICKER + "msec");
        ModbusLoopTimer.play();
    }

    public void StopModbusThread() {
        EgLogger.logInfo("Starting Modbus Loop Thread");
        ModbusLoopTimer.stop();
    }

    public void MainLoop() {
        List<SlaveDetail> slaveList = new SlaveDetailSQLController().getSlaves();

        for (int i = 0; i < slaveList.size(); i++) {
                //As the loop is called on a ticker we must ensure the last loop
            //completed before we call it again
            if (ModbusLoopComplete) {
                ModbusLoopComplete = false;

                //Get the slave
                SlaveDetail slaveDetail = slaveList.get(i);

                //Get its data 
                List<ModbusRegister> response = ModbusDataController.getInstance().readSlave(slaveDetail);

                //Send Data to SQL
                ModbusDataSQLController mdsc = new ModbusDataSQLController();
                mdsc.writeModbusData(response, slaveDetail);
                
                //Calculate the 32 bin and signed values etc
             //   mdsc.fillTableData(slaveDetail);
                
                                //            writeDataToSQL(response, SlaveID, registers[0]);
                //           writeModbusData(SlaveID);
                  //  CheckAlarmPresent();
                //  AnnunciatorStatus();
                ModbusLoopComplete = true;
                i++;
            }
        }
    }


}
