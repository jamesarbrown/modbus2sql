package com.enrogen.modbus2sql.configuration;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.mainWindow;
import com.enrogen.modbus2sql.sql.sqlConnection;
import com.enrogen.modbus2sql.xml.xmlio;
import java.io.File;

public class config implements appInterface {

    private xmlio xmlIO = new xmlio();

    public boolean checkHomeDirExists() {
        //Check we have a default directory created and if not create it
        String modbus2sqlDir = FULL_MODBUS2SQL_PATH;
        boolean exists = (new File(modbus2sqlDir)).exists();
        if (!exists) {
            System.out.println("No Home directory found");
            System.out.println("Creating : " + modbus2sqlDir);
            boolean success = (new File(FULL_MODBUS2SQL_PATH)).mkdir();
        }
        return true;
    }

    public boolean firstStart() {
        String modbus2sqlDir = FULL_MODBUS2SQL_PATH;
        boolean exists = (new File(modbus2sqlDir)).exists();

        //Create if necessary the XML File
        String SettingsXMLFile = FULL_SETTING_XML_PATH;
        exists = (new File(SettingsXMLFile)).exists();
        if (!exists) {
            EgLogger.logWarning("No setting.xml File");
            EgLogger.logWarning("Creating Default: " + SettingsXMLFile);

            xmlIO.createNewXmlFile(SettingsXMLFile);
            xmlIO.addRootNode("Modbus2SQL");
            xmlIO.addSubNode("Modbus2SQL", "default");
            xmlIO.addSubNode("default", "XMLversion", String.valueOf(REQUIRED_XML_VERSION));
            xmlIO.addSubNode("Modbus2SQL", "mysql");
            xmlIO.addSubNode("mysql", "ServerIP", MYSQL_DEFAULT_SERVER);
            xmlIO.addSubNode("mysql", "Port", MYSQL_DEFAULT_PORT);
            xmlIO.addSubNode("mysql", "Username", MYSQL_DEFAULT_USER);
            xmlIO.addSubNode("mysql", "Password", MYSQL_DEFAULT_PASSWORD);
            xmlIO.addSubNode("mysql", "DatabaseName", MYSQL_DEFAULT_DATABASE);
            xmlIO.addSubNode("Modbus2SQL", "Startup");
            xmlIO.addSubNode("Startup", "ModbusAutoStart", "false");
            xmlIO.addSubNode("Modbus2SQL", "Debug");
            xmlIO.addSubNode("Debug", "com.enrogen.sql", "false");
            xmlIO.addSubNode("Debug", "com.enrogen.ModbusConnection", "false");
            xmlIO.addSubNode("Debug", "net.wimpi.jamod", "false");

            boolean success = xmlIO.writeXMLFile();
            if (!success) {
                EgLogger.logSevere("Error Creating setting.xml File");

            }
        }

        EgLogger.logInfo("Reading setting.xml");
        xmlIO.setFileName(SettingsXMLFile);
        xmlIO.parseXmlFile();

        EgLogger.logInfo("Checking setting.xml version");
        String value = xmlIO.readXmlTagValue("default", "XMLversion");
        if (Double.parseDouble(value) < REQUIRED_XML_VERSION) {
            EgLogger.logSevere("The XML Setting file setting.xml is incorrect version");
            return false;
        }

        //text_messages.setText(text_messages.getText().toString() + "\nReading setting.xml");
        String sqlServerIP = xmlIO.readXmlTagValue("mysql", "ServerIP");
        String sqlPort = xmlIO.readXmlTagValue("mysql", "Port");
        String sqlUsername = xmlIO.readXmlTagValue("mysql", "Username");
        String sqlPassword = xmlIO.readXmlTagValue("mysql", "Password");
        String sqlDatabaseName = xmlIO.readXmlTagValue("mysql", "DatabaseName");

        //get a reference to the mainWindow
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();

        //Update the text boxes
        mwc.setText_sqlserverip(sqlServerIP);
        mwc.setText_sqlserverport(sqlPort);
        mwc.setText_sqlusername(sqlUsername);
        mwc.setText_sqlpassword(sqlPassword);
        mwc.setText_sqldatabasename(sqlDatabaseName);

        //Get the checkboxes
        boolean BoolcheckStartModbusAuto = Boolean.valueOf((String) xmlIO.readXmlTagValue("Startup", "ModbusAutoStart"));
        boolean BoolcheckDebugJamod = Boolean.valueOf((String) xmlIO.readXmlTagValue("Debug", "net.wimpi.jamod"));
        boolean BoolcheckDebugModbusConn = Boolean.valueOf((String) xmlIO.readXmlTagValue("Debug", "com.enrogen.ModbusConnection"));
        boolean BoolcheckDebugSQL = Boolean.valueOf((String) xmlIO.readXmlTagValue("Debug", "com.enrogen.sql"));

        //Apply the values
        mwc.setSelected_checkDebugSQL(BoolcheckDebugSQL);
        mwc.setSelected_checkDebugModbusConn(BoolcheckDebugModbusConn);
        mwc.setSelected_checkDebugJamod(BoolcheckDebugJamod);
        mwc.setSelected_checkStartModbusAuto(BoolcheckStartModbusAuto);

        return true;
    }

    public boolean saveSettingXML() {
        EgLogger.logInfo("Saving setting.xml");
        boolean sucess = false;

        //get a reference to the mainWindow
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();

        //SQL Settings
        String username = mwc.getText_sqlusername();
        String password = mwc.getText_sqlpassword();
        String serverip = mwc.getText_sqlserverip();
        String serverport = mwc.getText_sqlserverport();
        String dbname = mwc.getText_sqldatabasename();

        //Others
        String BoolcheckStartModbusAuto = String.valueOf(mwc.getSelected_checkStartModbusAuto());
        String BoolcheckDebugJaMod = String.valueOf(mwc.getSelected_checkDebugJamod());
        String BoolcheckDebugModbusConn = String.valueOf(mwc.getSelected_checkDebugModbusConn());
        String BoolcheckDebugSQL = String.valueOf(mwc.getSelected_checkDebugSQL());

        String SettingsXMLFile = FULL_SETTING_XML_PATH;

        xmlIO.createNewXmlFile(SettingsXMLFile);
        xmlIO.addRootNode("Modbus2SQL");
        xmlIO.addSubNode("Modbus2SQL", "default");
        xmlIO.addSubNode("default", "XMLversion", String.valueOf(REQUIRED_XML_VERSION));
        xmlIO.addSubNode("Modbus2SQL", "mysql");
        xmlIO.addSubNode("mysql", "ServerIP", serverip);
        xmlIO.addSubNode("mysql", "Port", serverport);
        xmlIO.addSubNode("mysql", "Username", username);
        xmlIO.addSubNode("mysql", "Password", password);
        xmlIO.addSubNode("mysql", "DatabaseName", dbname);
        xmlIO.addSubNode("Modbus2SQL", "Startup");
        xmlIO.addSubNode("Startup", "ModbusAutoStart", BoolcheckStartModbusAuto);
        xmlIO.addSubNode("Modbus2SQL", "Debug");
        xmlIO.addSubNode("Debug", "com.enrogen.sql", BoolcheckDebugSQL);
        xmlIO.addSubNode("Debug", "com.enrogen.ModbusConnection", BoolcheckDebugModbusConn);
        xmlIO.addSubNode("Debug", "net.wimpi.jamod", BoolcheckDebugJaMod);

        sucess = xmlIO.writeXMLFile();

        //If ok start the sql with new settings
        if (sucess) {
            EgLogger.logInfo("Sucess : Save setting.xml");
            sqlConnection.getInstance().StartSQL();
        } else {
            EgLogger.logSevere("FAILED : Save setting.xml");
        }

        //This resets the buttons to non-editable
        mwc.disableMySQLTextBoxes();

        return sucess;
    }

    public void readSQLSettings() {
        //Read the xml file
        String SettingsXMLFile = FULL_SETTING_XML_PATH;
        xmlIO.setFileName(SettingsXMLFile);
        xmlIO.parseXmlFile();

        //Get the SQL Setting into the boxes
        EgLogger.logInfo("Reading setting.xml");
        String sqlServerIP = xmlIO.readXmlTagValue("mysql", "ServerIP");
        String sqlPort = xmlIO.readXmlTagValue("mysql", "Port");
        String sqlUsername = xmlIO.readXmlTagValue("mysql", "Username");
        String sqlPassword = xmlIO.readXmlTagValue("mysql", "Password");
        String sqlDatabaseName = xmlIO.readXmlTagValue("mysql", "DatabaseName");

        //get a reference to the mainWindow
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();

        //Update the text boxes
        mwc.setText_sqlserverip(sqlServerIP);
        mwc.setText_sqlserverport(sqlPort);
        mwc.setText_sqlusername(sqlUsername);
        mwc.setText_sqlpassword(sqlPassword);
        mwc.setText_sqldatabasename(sqlDatabaseName);

        mwc.disableMySQLTextBoxes();
    }

    public void setupDebugging() {
        //get a reference to the mainWindow
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();

        //Debug the Jamod Library (provides modbus echo)
        if (mwc.getSelected_checkDebugJamod()) {
            System.setProperty("net.wimpi.modbus.debug", "true");
        } else {
            System.setProperty("net.wimpi.modbus.debug", "false");
        }

        //Debug the Enrogen SQL Library
        if (mwc.getSelected_checkDebugSQL()) {
            System.setProperty("com.enrogen.modbus2sql.sql.debug", "true");
        } else {
            System.setProperty("com.enrogen.modbus2sql.sql.debug", "false");
        }

        //Debug the Enrogen ModbusConnection Library
        if (mwc.getSelected_checkDebugModbusConn()) {
            System.setProperty("com.enrogen.ModbusConnection.debug", "true");
            EgLogger.logInfo("com.enrogen.ModbusConnection.debug - Debugging On");
        } else {
            System.setProperty("com.enrogen.ModbusConnection.debug", "false");
            EgLogger.logInfo("com.enrogen.ModbusConnection.debug - Debugging Off");
        }

    }

}
