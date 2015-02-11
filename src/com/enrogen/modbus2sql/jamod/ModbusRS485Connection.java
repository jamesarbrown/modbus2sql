package com.enrogen.modbus2sql.jamod;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.sql.RS485Detail;
import com.enrogen.modbus2sql.sql.RS485DetailSQLController;
import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;

public class ModbusRS485Connection implements appInterface {

    private static ModbusRS485Connection instance;
    private SerialConnection ModSerialConnection = null;
    private boolean debug = true;

    public static ModbusRS485Connection getInstance() {
        if (instance == null) {
            instance = new ModbusRS485Connection();
        }
        return instance;
    }

    public ModbusRS485Connection() {
        try {
            if (Boolean.valueOf(System.getProperty("com.enrogen.ModbusConnection.debug"))) {
                debug = true;
                EgLogger.logInfo("Debugging ON");
            } else {
                debug = false;
            }
        } catch (Exception e) {
            debug = false;
        }
    }

    public SerialConnection getConnection() {
        try {
            if (!isRS485Open()) {
                //Get the RS485 Settings
                RS485Detail rs485 = new RS485DetailSQLController().getRS485Settings();

                //Setup the serial interface and open it
                CreateRS485Connection(rs485.getComPort(), rs485.getBaud(),
                        rs485.getParity(), rs485.getDataBits(), rs485.getStopBits());
                OpenRS485Connection();
            }

            //return the serial connection
            return ModSerialConnection;
        } catch (Exception e) {
            EgLogger.logSevere("Unable to Open RS485 Connection, check port settings");
            return null;
        }
    }

    public boolean isRS485Open() {
        return ModSerialConnection.isOpen();
    }
    
    private void CreateRS485Connection(String serialport, Integer baud, String parity,
            Integer databits, Integer stopbits) {
        try {
            SerialParameters params = new SerialParameters();
            params.setPortName(serialport);
            params.setBaudRate(String.valueOf(baud));
            params.setDatabits(databits);
            params.setParity(parity);
            params.setStopbits(stopbits);
            params.setEncoding(MASTER_ENCODING);
            params.setEcho(COMPORT_ECHO);
            params.setReceiveTimeout(COMPORT_RECEIVE_TIMEOUT);

            ModbusCoupler.getReference().setMaster(MASTER_UNIT);
            ModbusCoupler.getReference().setUnitID(MASTER_UNIT_ID);

            ModSerialConnection = new SerialConnection(params);

            if (debug) {
                EgLogger.logInfo("Created RS485 Connection");
            }
        } catch (Exception e) {
            EgLogger.logSevere("Can Not Open RS485 Connection");
            e.printStackTrace();
        }
    }

    private boolean OpenRS485Connection() {
        try {
            ModSerialConnection.open();
            if (debug) {
                EgLogger.logInfo("Opened RS485 Connection");
            }
        } catch (Exception e) {
            EgLogger.logSevere("]Unable to Open RS485 Connection");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void closeConnection() {
        ModSerialConnection.close();
        if (debug) {
            EgLogger.logInfo("com.enrogen.ModbusConnection - Closed RS485 Connection");
        }
    }

}
