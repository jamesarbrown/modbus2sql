package com.enrogen.modbus2sql.jamod;

import com.enrogen.modbus2sql.appInterface.appInterface;
import java.util.LinkedList;
import java.util.List;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.WriteMultipleRegistersRequest;
import net.wimpi.modbus.msg.WriteMultipleRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleInputRegister;

public class ModbusWriteMultipleRegistersConnection implements appInterface {

    private WriteMultipleRegistersRequest WMRReq = new WriteMultipleRegistersRequest();
    private WriteMultipleRegistersResponse WMRRes = new WriteMultipleRegistersResponse();
    private ModbusSerialTransaction SerialTrans = new ModbusSerialTransaction();
    private Register[] writeData = null;
    
    private List DataList = new LinkedList();
    private boolean debug;

    public ModbusWriteMultipleRegistersConnection() {
        //Setup debuggging
        try {
            if (Boolean.valueOf(System.getProperty("com.enrogen.ModbusConnection.debug"))) {
                debug = true;
            } else {
                debug = false;
            }
        } catch (Exception e) {
            debug = false;
        }
    }

    public void WriteRequest(Integer RegStart, Integer SlaveID) {
        //Write the list to an array
        int listSize = DataList.size();
        Register[] registers = new Register[listSize];
        for (int i = 0; i < listSize; i++) {
            registers[i] = (SimpleInputRegister) DataList.get(i);
        }

        //Write Multipleregisters
        WMRReq.setReference(RegStart);
        WMRReq.setRegisters(registers);
        WMRReq.setUnitID(SlaveID);
        WMRReq.setHeadless();

        if (debug) {
            System.out.println("com.enrogen.ModbusConnection - Preparing Write Request - RegStart : "
                    + RegStart + " - SlaveID : " + SlaveID);
            System.out.println("com.enrogen.ModbusConnection - Values Are");
            for (int i = 0; i < listSize; i++) {
                SimpleInputRegister sir = (SimpleInputRegister) DataList.get(i);
                System.out.println("com.enrogen.ModbusConnection - Item " + i + " : " + sir.getValue());
            }
        }
    }

    public void addData(Integer DataValue) {
        SimpleInputRegister sir = new SimpleInputRegister(DataValue);
        //sir.setValue(DataValue);
        DataList.add(sir);
    }

    public void clearData() {
        DataList.clear();
        if (debug) {
            System.out.println("com.enrogen.ModbusConnection - Cleared DataList");
        }
    }

    public void ExecuteTransaction(SerialConnection sc) {
        SerialTrans.setSerialConnection(sc);
        SerialTrans.setRetries(REQUEST_RETRIES);
        SerialTrans.setRequest(WMRReq);

        //Execute
        try {
            SerialTrans.execute();
            if (debug) {
                System.out.println("com.enrogen.ModbusConnection - Executed Transaction");
            }
        } catch (ModbusSlaveException mse) {
            System.out.println("com.enrogen.ModbusConnection - ExecuteTransaction Slave Exception");
            mse.printStackTrace();
        } catch (ModbusIOException mie) {
            System.out.println("com.enrogen.ModbusConnection - ExecuteTransaction ModbusIOException");
            mie.printStackTrace();
        } catch (ModbusException me) {
            System.out.println("com.enrogen.ModbusConnection - ExecuteTransaction ModbusException");
            me.printStackTrace();
        }

        WMRRes = (WriteMultipleRegistersResponse) SerialTrans.getResponse();
        clearData();
    }
}
