package com.enrogen.modbus2sql.jamod;

import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.sql.RegisterBlock;
import com.enrogen.modbus2sql.sql.RegisterBlockSQLController;
import com.enrogen.modbus2sql.sql.SlaveDetail;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.ObservableList;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;

public class ModbusDataController implements appInterface {

    //The singleton
    private static ModbusDataController instance;

    //Variables
    boolean debug = false;
    ModbusRS485Connection RS485Connection;

    //The connection types
    private static final ModbusSerialTransaction ModbusSerialTrans = new ModbusSerialTransaction();
    private static final ModbusTCPTransaction ModbusTCPTrans = new ModbusTCPTransaction();

    public static ModbusDataController getInstance() {
        if (instance == null) {
            instance = new ModbusDataController();
        }
        return instance;
    }

    public void ModbusDataController() {
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

    //
    public List<ModbusRegister> readSlave(SlaveDetail slave) {
        //Get the pages of data applicable to this controller
        //We have not embedded the modbus transaction with the SQL
        //so we write out a list and read that in later.
        ObservableList<RegisterBlock> RegisterBlocks = new RegisterBlockSQLController().getRegisterBlocksBySlaveID(slave);
        List<ModbusRegister> AllResponse = new LinkedList();
        
        for (int z = 0; z < RegisterBlocks.size(); z++) {
            RegisterBlock rb = RegisterBlocks.get(z);
            switch (rb.getModbusFunctionCode()) {
                case 3: //Multiple Read Registers //Multiple Write Registers
                     List<ModbusRegister> response = readMultipleRegisters(
                            slave,
                            rb.getRegisterStart(),
                            rb.getRegisterEnd());
                    
                    //Copy these registers into AllResponse
                    Iterator<ModbusRegister> it = response.listIterator();
                    while (it.hasNext()) {
                        AllResponse.add(it.next());
                    }

                //todo - other modbus func types
                }
        }

        return AllResponse;
    }

    private List<ModbusRegister> readMultipleRegisters(SlaveDetail slave, int RegStart, int RegEnd) {
        //Variables
        List<ModbusRegister> response = new LinkedList();
        ReadMultipleRegistersRequest ReadMultipleRequest = new ReadMultipleRegistersRequest();
        ReadMultipleRegistersResponse ReadMultipleResponse = new ReadMultipleRegistersResponse();

        //Setup the request
        int RegCount = RegEnd - RegStart + 1;
        ReadMultipleRequest.setReference(RegStart);
        ReadMultipleRequest.setWordCount(RegCount);
        ReadMultipleRequest.setUnitID(slave.getSlaveID());
        ReadMultipleRequest.setHeadless();

        if (debug) {
            EgLogger.logInfo("Preparing ReadRequest - StartReg : " + RegStart
                    + " - RegCount : " + RegCount + " : " + " - SlaveID : " + slave.getSlaveID());
        }

        if (slave.isUseRS485()) {
            //Init the RS485 Connection if necessary
            SerialConnection serialConnection = ModbusRS485Connection.getInstance().getConnection();
            if (serialConnection == null) {
                return null;
            }
            ModbusSerialTrans.setSerialConnection(serialConnection);
            ModbusSerialTrans.setRetries(REQUEST_RETRIES);

            //Build the request
            ModbusSerialTrans.setRequest(ReadMultipleRequest);

            //Now execute it
            try {
                ModbusSerialTrans.execute();
                if (debug) {
                    System.out.println("Executed ReadRequest");
                }
            } catch (ModbusSlaveException mse) {
                EgLogger.logWarning("Slave Exception : " + mse.getMessage());
            } catch (ModbusIOException mie) {
                EgLogger.logWarning("ExecuteTransaction ModbusIOException");
                //mie.printStackTrace();
            } catch (ModbusException me) {
                EgLogger.logWarning("ExecuteTransaction ModbusException");
                // me.printStackTrace();
            }

            //Finalise the response
            ReadMultipleResponse = (ReadMultipleRegistersResponse) ModbusSerialTrans.getResponse();
            if (ReadMultipleResponse.getWordCount() == 0) {
                EgLogger.logWarning("Slave : " + slave.getSlaveID() + " - No Data Received");
            } else {
                for (int i = 0; i < ReadMultipleResponse.getWordCount(); i++) {  
                    //Insert the response into the low word of the ModbusRegister class
                    //The modbus register will be updated with signing, 32bit etc by the#
                    //SQL controller.
                    ModbusRegister mr = new ModbusRegister();
                    mr.setRegisterNo(RegStart + i);
                    mr.setValue(0,ReadMultipleResponse.getRegister(i).getValue() );
                    response.add(mr);
                }
                
                //Todo - see if this is needed
                ReadMultipleResponse = null;
            }

        } else {
            //Future Ethernet methods
        }

        return response;
    }
}
