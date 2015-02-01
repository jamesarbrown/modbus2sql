//*************************************************************************//
//com.enrogen.modbus2sql.logger.EgLogger                                   //
//Date : 04 March 2012 (Rewrite of 2010 Work - Modbus2SQL Project          //
//Desc : Main Entry Class for Logging                                      //
//                                                                         //
//Based on following resources                                             //
//https://blogs.oracle.com/nickstephen/entry/java_redirecting_system_out_and   //
//                                                                         //
//(c) James A R Brown 2014                                                 //
//*************************************************************************//
package com.enrogen.modbus2sql.logger;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.mainWindow;
import java.io.File;
import java.io.PrintStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javafx.application.Platform;

public class EgLogger implements appInterface {

    private static FileHandler fh1;
    private static WindowHandler wh1;
    private static EgLogger EgLoggerSingleton;
    private static final Logger mainLogger = Logger.getLogger("com.enrogen.modbus2sql.EgLogger");

    private EgLogger() {
    }

    public static void setFileHandler(String filename, int logsize, int logfiles) {
        EgFileHandler egfh1 = new EgFileHandler(filename, logsize, logfiles);
        fh1 = egfh1.getFileHandler();
    }

    //Create singleton instance
    public static Logger getLogger() {
        if (EgLoggerSingleton == null) {
            EgLoggerSingleton = new EgLogger();
            try {
                if (!initEgLogger()) {
                    System.out.println("FATAL - Failed to instantate logger...exiting");
                    Platform.exit();
                };
            } catch (Exception e) {
                System.out.println("FATAL - Failed to instantate logger...exiting");
                Platform.exit();
            }
        }
        return mainLogger;
    }

    //Convienience method
    public static void logInfo(String Message) {
        Logger lg = getLogger();
        String className = new Exception().getStackTrace()[1].getClassName();
        while (className.length() < 45) {
            className = className + " ";
        }
        lg.log(Level.INFO, className + Message);
    }

    //Convienience method
    public static void logWarning(String Message) {
        Logger lg = getLogger();
        String className = new Exception().getStackTrace()[1].getClassName();
        while (className.length() < 45) {
            className = className + " ";
        }
        lg.log(Level.WARNING, className + Message);
    }

    //Convienience method
    public static void logSevere(String Message) {
        Logger lg = getLogger();
        String className = new Exception().getStackTrace()[1].getClassName();
        while (className.length() < 45) {
            className = className + " ";
        }
        lg.log(Level.SEVERE, className + Message);
    }

    public static boolean initEgLogger() {
        //Check the directory exists
        File directory = new File(LOG_PATH);
        boolean exists = directory.exists();

        //If it does not make it
        if (!exists) {
            // Create the log directory
            boolean success = directory.mkdir();
            if (!success) {
                System.out.println("FATAL : com.enrogen.modbus2sql.ApplicationBean - There was an error creating the logging directory");
                return false;
            } else {
                System.out.println("WARNING : com.enrogen.modbus2sql.ApplicationBean - There was not a log folder - Created one");
            }
        } else {
            System.out.println("INFO : com.enrogen.genesys.ApplicationBean - Found /logs for logging");
        }

        //Setup the filehandler
        String filename = LOG_PATH + "/" + LOG_FILENAME;
        System.out.println("INFO : com.enrogen.genesys.ApplicationBean - Logging for Application will be to pattern : " + filename);

        //Calls above method to init the file
        setFileHandler(filename, LOG_SIZE_LIMIT, LOG_MAX_FILES);

        // initialize logging to go to rolling log file
        LogManager logManager = LogManager.getLogManager();
        logManager.reset();

        //Tell the logger where the window handler is
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();
        wh1 = new WindowHandler(mwc.getLoggingWindow());

        //Add the logger handler to all loggers
        Logger.getLogger("").addHandler(fh1);
        Logger.getLogger("").addHandler((Handler) wh1);

        //Create a logger for stdout
        LoggingOutputStream los = new LoggingOutputStream(mainLogger, Level.INFO);
        PrintStream ps = new PrintStream(los, true);
        System.setOut(ps);

        //Create a logger for stderr
        LoggingOutputStream los2 = new LoggingOutputStream(mainLogger, Level.SEVERE);
        PrintStream ps2 = new PrintStream(los2, true);
        System.setErr(ps2);

        return true;
    }
}
