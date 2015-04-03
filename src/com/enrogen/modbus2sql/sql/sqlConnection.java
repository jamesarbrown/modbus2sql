package com.enrogen.modbus2sql.sql;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.mainWindow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.Timeline;

public class sqlConnection implements appInterface {

    //Standard variables
    private static sqlConnection sqlConnectionSingleton;
    private String SQLServerString;
    private String SQLUserString;
    private String SQLPasswordString;
    private String SQLdefaultDatabaseString;
    private final boolean debug;
    private boolean SqlAlive = false;
    private boolean Restarted = false;
    private ResultSetMetaData rsmd = null;
    private int ResultColCount = 0;
    public List BatchSQLCommands = new LinkedList();
    public Connection connection = null;

    public sqlConnection() {
        if (Boolean.valueOf(System.getProperty("com.enrogen.modbus2sql.sql.debug"))) {
            debug = true;
            EgLogger.logInfo("Debugging ON");
        } else {
            debug = false;
        }
    }

    //Create singleton instance
    public static sqlConnection getInstance() {
        if (sqlConnectionSingleton == null) {
            sqlConnectionSingleton = new sqlConnection();
        }
        return sqlConnectionSingleton;
    }

    //Main routine
    public void initSQL() {
        //Kill the SQL Connection if already open
        closeSQLConnection();
        
        //get a reference to the mainWindow
        mainWindowController mwc = mainWindow.getInstance().getMainWindowController();

        //Get SQL parameters
        String sqlServerIP = mwc.getText_sqlserverip();
        String sqlUsername = mwc.getText_sqlusername();
        String sqlPassword = mwc.getText_sqlpassword();
        String sqlDatabaseName = mwc.getText_sqldatabasename();

        //Open the SQL Connection
        setSQLParams(sqlServerIP, sqlUsername, sqlPassword, sqlDatabaseName);
    }

    private void setSQLParams(String SQLServer, String SQLUser, String SQLPassword, String defaultDatabase) {
        SQLServerString = SQLServer;
        SQLUserString = SQLUser;
        SQLPasswordString = SQLPassword;
        SQLdefaultDatabaseString = defaultDatabase;
        if (debug) {
            EgLogger.logInfo("SQLServer : " + SQLServerString);
            EgLogger.logInfo("SQLUser : " + SQLUserString);
            EgLogger.logInfo("SQLPasswordString : " + SQLPassword);
            EgLogger.logInfo("SQLdefaultDatabaseString : " + SQLdefaultDatabaseString);
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //Open Close and Restart the SQL Connection
    ////////////////////////////////////////////////////////////////////////
    private boolean openSQLConnection() {
        String driverName = MYSQL_DRIVER_NAME;
        String SQLUrl = MYSQL_CONNECTION_STRING_START + SQLServerString + "/" + SQLdefaultDatabaseString;

        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(SQLUrl, SQLUserString, SQLPasswordString);
            SqlAlive = true;
            return true;
        } catch (ClassNotFoundException e) {
            EgLogger.logSevere("Error at openSQLConnection");
            EgLogger.logSevere("SQLURL : " + SQLUrl);
            EgLogger.logSevere("driverName : " + driverName);
        } catch (SQLException sqle) {
            EgLogger.logSevere("Error at openSQLConnection");
            EgLogger.logSevere("SQLURL : " + SQLUrl);
            EgLogger.logSevere("driverName : " + driverName);
        } catch (Exception e) {
            EgLogger.logSevere("Error at openSQLConnection");
            EgLogger.logSevere("SQLURL : " + SQLUrl);
            EgLogger.logSevere("driverName : " + driverName);
        }
        SqlAlive = false;
        return false;
    }

    //Close the standard connection to the MySQL Server
    private void closeSQLConnection() {
        try {
            if (connection != null) {
                if (!connection.isClosed()) {
                    connection.close();
                    if (debug) {
                        EgLogger.logInfo("Closed SQL Connection");
                    }
                }
            }
        } catch (SQLException sqle) {
            EgLogger.logSevere("Error at closeSQLConnection");
        } catch (Exception e) {
            EgLogger.logSevere("Error at closeSQLConnection");
        }

        //Dump the existing SQL connection
        connection = null;
    }

    private void restartSQLConnection() {
        closeSQLConnection();
        openSQLConnection();
        Restarted = true;
    }

    ////////////////////////////////////////////////////////////////////////
    //Test SQL with Simple Command
    ////////////////////////////////////////////////////////////////////////
    private boolean checkSQLConnection() {
        boolean Alive = false;

        //Run the SQL command
        try {
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(SQL_TEST_COMMAND);
                while (rs.next()) {
                    Alive = true;
                }
                stmt.close();
            }
        } catch (SQLException sqle) {
            EgLogger.logSevere("Error at checkSQLConnection");
        } catch (Exception e) {
            EgLogger.logSevere("Error at checkSQLConnection");
        }

        SqlAlive = Alive;
        return Alive;
    }

    public boolean isAlive() {
        return SqlAlive;
    }

    ////////////////////////////////////////////////////////////////////////
    //SQL Insert and Select
    ////////////////////////////////////////////////////////////////////////
    public List<List> SQLSelectCommand(String sqlcmd) {
        List<List> resultList = new LinkedList();
        List<Object> resultValue = new LinkedList();

        //Run the SQL command
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlcmd);
            rsmd = rs.getMetaData();
            ResultColCount = rsmd.getColumnCount();

            while (rs.next()) {
                for (int x = 0; x < rsmd.getColumnCount(); x++) {
                    Object o = rs.getObject(x + 1);
                    resultValue.add(o);
                }
                resultList.add(new LinkedList(resultValue));
                resultValue.clear();
            }
            stmt.close();
        } catch (SQLException sqle) {
            ResultColCount = 0;
            EgLogger.logSevere("Error at SQLSelectCommand");
            EgLogger.logSevere("Command was :-");
            EgLogger.logSevere(sqlcmd);
        }
        return resultList;
    }

    public int getResultColumnCount() {
        return ResultColCount;
    }

    public ResultSetMetaData getResultSetMetaData() {
        return rsmd;
    }

    //Use for insert and update
    public void SQLUpdateCommand(String sqlcmd) {
        //Run the SQL command
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sqlcmd);
            stmt.close();
        } catch (SQLException sqle) {
            EgLogger.logSevere("Error at SQLUpdateCommand");
            EgLogger.logSevere("Command was :-");
            EgLogger.logSevere(sqlcmd);
        } catch (Exception e) {
            EgLogger.logSevere("Error at SQLUpdateCommand");
            EgLogger.logSevere("Command was :-");
            EgLogger.logSevere(sqlcmd);
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //SQL Batch Commands
    ////////////////////////////////////////////////////////////////////////
    public void ClearBatch() {
        BatchSQLCommands.clear();
    }

    public void AddToBatch(String sqlcmd) {
        BatchSQLCommands.add(sqlcmd);
    }

    public void SQLExecuteBatch() {
        //Run the SQL command
        try {
            Statement stmt = connection.createStatement();

            for (int i = 0; i < BatchSQLCommands.size(); i++) {
                stmt.addBatch(BatchSQLCommands.get(i).toString());
            }
            stmt.executeBatch();
            stmt.close();
        } catch (Exception e) {
            EgLogger.logSevere("Error at SQLExecuteBatchCommand");
            EgLogger.logSevere("Printing out batch list :-");
            for (int i = 0; i < BatchSQLCommands.size(); i++) {
                EgLogger.logSevere(BatchSQLCommands.get(i).toString());
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //Keep SQL Alive (stop SQL Timing out)
    ////////////////////////////////////////////////////////////////////////
    public void CheckAndRestartSQLCon() {
        checkSQLConnection();
        if (!isAlive()) {
            EgLogger.logInfo("No SQL Connection, Attempting to Start");
            restartSQLConnection();
            if (debug) {
                if (isAlive()) {
                    EgLogger.logInfo("Successfully Started SQL Connection");
                } else {
                    EgLogger.logSevere("Failed to Start SQL Connection");
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //Flags for Restarted Server
    ////////////////////////////////////////////////////////////////////////
    public boolean isRestarted() {
        return Restarted;
    }

    public void resetRestartedFlag() {
        Restarted = false;
    }

}
