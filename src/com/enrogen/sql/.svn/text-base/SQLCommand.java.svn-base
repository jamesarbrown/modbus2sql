//////////////////////////////////////////////////////////////////////////
//com.enrogen.sql
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SQLCommand implements SQL {
    //Defined in MySQLConnection... or MSSQL if someone writes ;)

    private String SQLServerString;
    private String SQLUserString;
    private String SQLPasswordString;
    private String SQLdefaultDatabaseString;
    private boolean debug;
    private boolean SqlAlive = false;
    private boolean Restarted = false;
    private ResultSetMetaData rsmd = null;
    private int ResultColCount = 0;
    public List BatchSQLCommands = new LinkedList();
    public Connection SqlConnection = null;

    public SQLCommand() {
        try {
            if (Boolean.valueOf(System.getProperty("com.enrogen.sql.debug"))) {
                debug = true;
                System.out.println("com.enrogen.sql - Debugging ON");
            } else {
                debug = false;
            }
        } catch (Exception e) {
            debug = false;
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //Setup the SQL Server parameters
    ////////////////////////////////////////////////////////////////////////
    public void setSQLParams(String SQLServer, String SQLUser, String SQLPassword, String defaultDatabase) {
        SQLServerString = SQLServer;
        SQLUserString = SQLUser;
        SQLPasswordString = SQLPassword;
        SQLdefaultDatabaseString = defaultDatabase;
        if (debug) {
            System.out.println("com.enrogen.sql - SQLServer : " + SQLServerString);
            System.out.println("com.enrogen.sql - SQLUser : " + SQLUserString);
            System.out.println("com.enrogen.sql - SQLPasswordString : " + SQLPassword);
            System.out.println("com.enrogen.sql - SQLdefaultDatabaseString : " + SQLdefaultDatabaseString);
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //Open Close and Restart the SQL Connection
    ////////////////////////////////////////////////////////////////////////
    private boolean openSQLConnection() {
        String driverName = SQL.MYSQL_DRIVER_NAME;
        String SQLUrl = SQL.MYSQL_CONNECTION_STRING_START + SQLServerString + "/" + SQLdefaultDatabaseString;

        try {
            Class.forName(driverName);
            SqlConnection = DriverManager.getConnection(SQLUrl, SQLUserString, SQLPasswordString);
            if (debug) {
                System.out.println("com.enrogen.sql - Opened SQL Connection");
            }
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("com.enrogen.sql - Driver not found at openSQLConnection");
            System.err.println("com.enrogen.sql - SQLURL : " + SQLUrl);
            System.err.println("com.enrogen.sql - driverName : " + driverName);
        } catch (SQLException sqle) {
            System.err.println("com.enrogen.sql - Can Not Connect to SQL Server at openSQLConnection");
            System.err.println("com.enrogen.sql - SQLURL : " + SQLUrl);
            System.err.println("com.enrogen.sql - driverName : " + driverName);
        } catch (Exception e) {
            System.err.println("com.enrogen.sql - Can Not Connect to SQL Server at openSQLConnection");
            System.err.println("com.enrogen.sql - SQLURL : " + SQLUrl);
            System.err.println("com.enrogen.sql - driverName : " + driverName);
        }
        SqlAlive = false;
        return false;
    }

    //Close the standard connection to the MySQL Server
    public void closeSQLConnection() {
        try {
            if (SqlConnection != null) {
                if (!SqlConnection.isClosed()) {
                    SqlConnection.close();
                }
            }
            if (debug) {
                System.out.println("com.enrogen.sql - Closed SQL Connection");
            }
            SqlAlive = false;
        } catch (SQLException sqle) {
            System.err.println("com.enrogen.sql - Could Not Close SQLConnection");
        } catch (Exception e) {
            System.err.println("com.enrogen.sql - Could Not Close SQLConnection");
        }
    }

    public void restartSQLConnection() {
        if (debug) {
            System.out.println("com.enrogen.sql - Restarting SQL Connection");
        }
        closeSQLConnection();
        openSQLConnection();
        Restarted = true;
    }

    ////////////////////////////////////////////////////////////////////////
    //Test SQL with Simple Command
    ////////////////////////////////////////////////////////////////////////
    public boolean checkSQLConnection() {
        boolean Alive = false;

        //Run the SQL command
        try {
            Statement stmt = SqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL.SQL_TEST_COMMAND);
            while (rs.next()) {
                Alive = true;
            }
            stmt.close();
        } catch (SQLException sqle) {
            System.err.println("com.enrogen.sql - SQL Connection is Down");
        } catch (Exception e) {
            System.err.println("com.enrogen.sql - SQL Connection is Down");
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
    public List SQLSelectCommand(String sqlcmd) {
        List resultList = new LinkedList();
        List resultValue = new LinkedList();

        //Run the SQL command
        try {
            Statement stmt = SqlConnection.createStatement();
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
            System.err.println("com.enrogen.sql - Error at SQLSelectCommand");
            System.err.println("com.enrogen.sql - Command was :-");
            System.err.println("com.enrogen.sql : " + sqlcmd);
            sqle.printStackTrace();
        }
        return resultList;
    }

    public int getResultColumnCount() {
        return ResultColCount;
    }

    public void SQLUpdateCommand(String sqlcmd) {
        //Run the SQL command
        try {
            Statement stmt = SqlConnection.createStatement();
            stmt.executeUpdate(sqlcmd);
            stmt.close();
        } catch (SQLException sqle) {
            System.err.println("com.enrogen.sql - Error at SQLUpdateCommand");
            System.err.println("com.enrogen.sql - Command was :-");
            System.err.println("com.enrogen.sql : " + sqlcmd);
            sqle.printStackTrace();
        } catch (Exception e) {
            System.err.println("com.enrogen.sql - Error at SQLUpdateCommand");
            System.err.println("com.enrogen.sql - Command was :-");
            System.err.println("com.enrogen.sql : " + sqlcmd);
            e.printStackTrace();
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
            Statement stmt = SqlConnection.createStatement();

            for (int i = 0; i < BatchSQLCommands.size(); i++) {
                stmt.addBatch(BatchSQLCommands.get(i).toString());
            }
            stmt.executeBatch();
            stmt.close();
        } catch (Exception e) {
            System.err.println("com.enrogen.sql - Error at SQLExecuteBatchCommand");
            System.err.println("com.enrogen.sql - Printing out batch list :-");
            for (int i = 0; i < BatchSQLCommands.size(); i++) {
                System.err.println("com.enrogen.sql : " + BatchSQLCommands.get(i).toString());
            }
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////
    //Keep SQL Alive (stop SQL Timing out)
    ////////////////////////////////////////////////////////////////////////
    //Timer timer = null;
    private Thread KeepAliveThread = null;

    public void CheckAndRestartSQLCon() {
        checkSQLConnection();
        if (!isAlive()) {
            if (debug) {
                System.out.println("com.enrogen.sql - Attempting to Restart SQL Connection");
            }
            restartSQLConnection();
            if (debug) {
                if (isAlive()) {
                    System.out.println("com.enrogen.sql - Sucess");
                } else {
                    System.out.println("com.enrogen.sql - Failed");
                }
            }
        }
    }

    public class keepalivethread extends Thread { // This method is called when the thread runs
        @Override
        public void run() {
            while (true) {
                try {
                    CheckAndRestartSQLCon();
                    if (SqlAlive) {
                        Thread.sleep(SQL_KEEP_ALIVE_INTERVAL); 
                    } else {
                        Thread.sleep(1000);//1sec (rapid attempt to restart)
                    };
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // very important
                    break;
                }
            }
        }
    }

    public void StartKeepAlive() {
        if (debug) {
            System.out.println("com.enrogen.sql - Starting keepalive Thread");
        }
        KeepAliveThread = new keepalivethread();
        KeepAliveThread.start();
    }

    public void StopKeepAlive() throws InterruptedException {
        if (debug) {
            System.out.println("com.enrogen.sql - Stopping keepalive Thread");
        }
        KeepAliveThread.interrupt();
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

    ////////////////////////////////////////////////////////////////////////
    //Returns
    ////////////////////////////////////////////////////////////////////////
    public Connection getSQLConnection() {
        return SqlConnection;
    }
}
