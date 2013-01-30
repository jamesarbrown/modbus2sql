//////////////////////////////////////////////////////////////////////////
//com.enrogen.ModbusConnection
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.ModbusConnection;

import java.util.LinkedList;
import java.util.List;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;

public class ModbusReadMultipleRegistersConnection {

    private ReadMultipleRegistersRequest RMRReq = new ReadMultipleRegistersRequest();
    private ReadMultipleRegistersResponse RMRRes = new ReadMultipleRegistersResponse();
    private ModbusSerialTransaction SerialTrans = new ModbusSerialTransaction();
    private boolean debug;

    public ModbusReadMultipleRegistersConnection() {
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

    public void ReadRequest(Integer RegStart, Integer RegCount, Integer SlaveID) {
        //Read Multipleregisters
        RMRReq.setReference(RegStart);
        RMRReq.setWordCount(RegCount);
        RMRReq.setUnitID(SlaveID);
        RMRReq.setHeadless();

        if (debug) {
            System.out.println("com.enrogen.ModbusConnection - Preparing ReadRequest - StartReg : " + RegStart
                    + " - RegCount : " + RegCount + " : " + " - SlaveID : " + SlaveID);
        }
    }

    public void ExecuteTransaction(SerialConnection sc) {
        SerialTrans.setSerialConnection(sc);
        SerialTrans.setRetries(ModbusConnection.REQUEST_RETRIES);
        SerialTrans.setRequest(RMRReq);

        //Execute
        try {
            SerialTrans.execute();

            if (debug) {
                System.out.println("com.enrogen.ModbusConnection - Executed ReadRequest");
            }

        } catch (ModbusSlaveException mse) {
            System.out.println("com.enrogen.ModbusConnection - Slave Exception Response : " + mse.getMessage());
        } catch (ModbusIOException mie) {
            System.out.println("com.enrogen.ModbusConnection - ExecuteTransaction ModbusIOException");
            //mie.printStackTrace();
        } catch (ModbusException me) {
            System.out.println("com.enrogen.ModbusConnection - ExecuteTransaction ModbusException");
           // me.printStackTrace();
        }

        RMRRes = (ReadMultipleRegistersResponse) SerialTrans.getResponse();
    }

    public List SlaveResponse() {
        List result = new LinkedList();
        try {
            for (int i = 0; i < RMRRes.getWordCount(); i++) {
                result.add(Integer.valueOf(RMRRes.getRegisterValue(i)));
            }
            RMRRes = null;
        } catch (Exception e) {
            System.out.println("com.enrogen.ModbusConnection - SlaveResponse Exception");
            //e.printStackTrace();
        }
        return result;
    }

    //TODO
    //This method locks out slave requests when it encounters
    //a modbus exception
    private void SlaveLockout(int SlaveID) {

    }

}
