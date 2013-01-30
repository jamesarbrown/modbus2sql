//////////////////////////////////////////////////////////////////////////
//com.enrogen.sql
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////

package com.enrogen.sql;

public interface SQL {
        public static final boolean DEBUG_SQL = true;
        public static final String MYSQL_DRIVER_NAME = "org.gjt.mm.mysql.Driver";
        public static final String MYSQL_CONNECTION_STRING_START = "jdbc:mysql://";
        public static final int MySQLPort = 3306;
        public static final String SQL_TEST_COMMAND = "SELECT 1;";
        public static final int SQL_KEEP_ALIVE_INTERVAL = 300000; //mSec=5min
}
