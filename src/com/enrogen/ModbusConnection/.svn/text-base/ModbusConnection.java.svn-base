//////////////////////////////////////////////////////////////////////////
//com.enrogen.ModbusConnection
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.ModbusConnection;

public interface ModbusConnection {
    public static final int MASTER_UNIT_ID = 1;
    public static final boolean MASTER_UNIT = true;
    public static final String MASTER_ENCODING = "rtu";
    public static final boolean COMPORT_ECHO = false; //Dip switch 4 on EasySync ES-U-3001-M USB - RS485
    public static final int COMPORT_RECEIVE_TIMEOUT = 500; //mSec //Increase with a bad connection will increase latency
    public static final int REQUEST_RETRIES = 1; //Increase with a bad connection will increase latency
    public static final boolean MODBUS_CONNECTION_DEBUG = true;

    //Do not ammend
    public static final int READ_MULTIPLE_REGISTERS = 3;
    public static final int WRITE_MULTIPLE_REGISTERS = 16;
    public static final int READ_COILS = 1;
    public static final int READ_INPUT_DISCRETES = 2;
    public static final int READ_INPUT_REGISTERS = 4;
    public static final int WRITE_COIL = 5;
    public static final int WRITE_MULTIPLE_COILS = 15;
    public static final int WRITE_SINGLE_REGISTER = 6;
}
