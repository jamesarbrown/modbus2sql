package com.enrogen.modbus2sql.threads;

import static com.enrogen.modbus2sql.appInterface.appInterface.SQL_KEEP_ALIVE_INTERVAL;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.sql.sqlConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SQLThread {

    private static SQLThread instance;
    private Timeline sqlWatchdogTimer = null;

    //Create singleton instance
    public static SQLThread getInstance() {
        if (instance == null) {
            instance = new SQLThread();
        }
        return instance;
    }

    public SQLThread() {
        //Init the watchdog timer
        if (sqlWatchdogTimer == null) {
            sqlWatchdogTimer = new Timeline(new KeyFrame(
                    Duration.millis(SQL_KEEP_ALIVE_INTERVAL),
                    ae -> sqlConnection.getInstance().CheckAndRestartSQLCon()));
            sqlWatchdogTimer.setCycleCount(Animation.INDEFINITE);
        }
    }

    public void StartSQLThread() {
        //Ensure there is an SQLconnection instance created
        sqlConnection sqlc = sqlConnection.getInstance();
        sqlc.initSQL();
        sqlc.CheckAndRestartSQLCon();
        
        //Now start the Keep Alive thread
        EgLogger.logInfo("Starting SQL Keep Alive Thread: " + SQL_KEEP_ALIVE_INTERVAL + "msec");
        sqlWatchdogTimer.play();
    }

    public void StopSQLThread() {
        EgLogger.logInfo("Stopping SQL Keep Alive Thread");
        sqlWatchdogTimer.stop();
    }

}
