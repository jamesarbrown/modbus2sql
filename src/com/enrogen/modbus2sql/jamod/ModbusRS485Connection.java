package com.enrogen.modbus2sql.jamod;

import com.enrogen.modbus2sql.appInterface.appInterface;
import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;

public class ModbusRS485Connection implements appInterface {

    private SerialConnection ModSerialConnection = null;
    private boolean debug = true;

    public ModbusRS485Connection() {
        try {
            if (Boolean.valueOf(System.getProperty("com.enrogen.ModbusConnection.debug"))) {
                debug = true;
                System.out.println("com.enrogen.ModbusConnection - Debugging ON");
            } else {
                debug = false;
            }
        } catch (Exception e) {
            debug = false;
        }
    }

    public void CreateRS485Connection(String serialport, String baud, String parity, Integer databits, Integer stopbits) {
        try {
            SerialParameters params = new SerialParameters();
            params.setPortName(serialport);
            params.setBaudRate(baud);
            params.setDatabits(databits);
            params.setParity(parity);
            params.setStopbits(stopbits);
            params.setEncoding("rtu");
            params.setEcho(COMPORT_ECHO);
            params.setReceiveTimeout(COMPORT_RECEIVE_TIMEOUT);

            ModbusCoupler.getReference().setMaster(MASTER_UNIT);
            ModbusCoupler.getReference().setUnitID(MASTER_UNIT_ID);

            ModSerialConnection = new SerialConnection(params);

            if (debug) {
                System.out.println("com.enrogen.ModbusConnection - Created RS485 Connection");
            }
        } catch (Exception e) {
            System.err.println("com.enrogen.ModbusConnection - Can Not Open RS485 Connection");
            e.printStackTrace();
        }
    }

    public boolean OpenRS485Connection() {
        try {
            ModSerialConnection.open();
            if (debug) {
                System.out.println("com.enrogen.ModbusConnection - Opened RS485 Connection");
            }
        } catch (Exception e) {
            System.err.println("com.enrogen.ModbusConnection - Unable to Open RS485 Connection");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isRS485Open() {
        return ModSerialConnection.isOpen();
    }

    public SerialConnection getSerialRS485Connection() {
        return ModSerialConnection;
    }

    public void DestroyRS485Connection() {
        ModSerialConnection.close();
        if (debug) {
            System.out.println("com.enrogen.ModbusConnection - Closed RS485 Connection");
        }
    }
}

