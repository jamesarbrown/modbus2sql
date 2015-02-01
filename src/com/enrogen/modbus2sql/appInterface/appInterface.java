//*************************************************************************//
//com.enrogen.modbus2sql.appInterface.appInterface                         //
//Date : 30 Dec 2014                                                       //
//Desc : App specific values                                               //
//(c) James A R Brown 2014                                                 //
//*************************************************************************//
package com.enrogen.modbus2sql.appInterface;

import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.rgb;

public interface appInterface {
    public static final double VERSION = 0.2;
    public static final double REQUIRED_XML_VERSION = 0.1;

    public static final boolean DEBUG_JAMOD=true;
    public static final boolean DEBUG_SQL=true;
    public static final boolean DEBUG_MODBUS_CONNECTION=true;

    //The timer events
    public static final int SYSTEM_ALIVE_POLL_TICKER = 1000; //mSec
    public static final int MODBUS_POLL_TICKER=2000; //mSec
    public static final int WATCHDOG_POLL_TICKER=1000; //mSec

    //Modbus Data is regarded as "current" if younger than
    public static int MYSQL_MODBUS_DATA_CURRENT = 10; //sec
    
    //If no setting.xml file exists it will be created with these parameters
    //You need to delete the setting.xml to recreate if you change these values
    public static final String SETTING_XML_MAINDIRECTORY = System.getProperty("user.home");
    public static final String SETTING_XML_SUBDIRECTORY = ".modbus2sql";
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String SETTING_XML_FILENAME = "setting.xml";
    public static final String FULL_MODBUS2SQL_PATH = SETTING_XML_MAINDIRECTORY +
            FILE_SEPARATOR + SETTING_XML_SUBDIRECTORY;
    public static final String FULL_SETTING_XML_PATH =  FULL_MODBUS2SQL_PATH +
            FILE_SEPARATOR + SETTING_XML_FILENAME;
    
    //Lamp Colors
    public static final Color RED_LAMP_ON = rgb(255,0,0);
    public static final Color RED_LAMP_OFF = rgb(100,0,0);
    public static final Color GREEN_LAMP_ON = rgb(0,255,0);
    public static final Color GREEN_LAMP_OFF = rgb(0,100,0);
    
    //com.enrogen.genesys.sql.SqlConnection
    public static final String MYSQL_DEFAULT_SERVER = "127.0.0.1";
    public static final String MYSQL_DEFAULT_PORT = "3306";
    public static final String MYSQL_DEFAULT_USER = "modbus2sql";
    public static final String MYSQL_DEFAULT_PASSWORD = "modicon";
    public static final String MYSQL_DEFAULT_DATABASE = "modbus2sql";
    public static final String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";
    public static final String MYSQL_CONNECTION_STRING_START = "jdbc:mysql://";
    public static final String SQL_TEST_COMMAND = "SELECT 1;";
    public static final int SQL_KEEP_ALIVE_INTERVAL = 5000; //mSec=5sec
    
    //Logging Setup     
    public static final int LOG_SIZE_LIMIT = 256000; // 256k
    public static final int LOG_MAX_FILES = 3;
    public static final boolean LOG_APPEND = true;
    public static final String LOG_PATH = FULL_MODBUS2SQL_PATH + "/log";
    public static final String LOG_FILENAME = "Modbus2sql%g.log"; 
    public static final String LOG_FILE_INUSE = "Modbus2sql0.log";
    
    //The modbus read types
    public static final int    MODBUS_FUNC_CODE_READ_COILS = 1;
    public static final String MODBUS_FUNC_CODE_READ_COILS_STRING = "Read Coils (1)";
    public static final int    MODBUS_FUNC_CODE_READ_INP_DIS = 2;
    public static final String MODBUS_FUNC_CODE_READ_INP_DIS_STRING = "Read Input Discretes (2)";
    public static final int    MODBUS_FUNC_CODE_READ_MULTIPLE = 3;
    public static final String MODBUS_FUNC_CODE_READ_MULTIPLE_STRING = "Read Multiple Registers (3)";
    public static final int    MODBUS_FUNC_CODE_READ_INP_REG = 4;
    public static final String MODBUS_FUNC_CODE_READ_INP_REG_STRING = "Read Input Registers (4)";
    
    //JavaFX FXML file Locations
    public static final String JAVAFX_FXML_LOCATION = "/com/enrogen/modbus2sql/javafx/fxml/";
    
        
    
    
    
        //Modify the path for the yum installed rxtx drivers in fedora
    public static final String SYSTEM_PATH = System.getProperty("java.library.path");
    public static final String SYSTEM_EXTENDED_PATH = System.setProperty("java.library.path", SYSTEM_PATH
            + ":/usr/lib64/rxtx:/usr/lib/rxtx");

    //HighByte Unimplemented Feature response from contoller.
    public static final Integer HIGH_BYTE_UNIMPLEMENTED_FEATURE = 65535;
    
}
