package com.enrogen.modbus2sql.sql;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SlaveDetail {
    
    private final SimpleIntegerProperty rowid = new SimpleIntegerProperty();
    private final SimpleStringProperty DeviceType = new SimpleStringProperty();
    private final SimpleIntegerProperty ModbusSlaveID = new SimpleIntegerProperty();
    private final SimpleStringProperty LongName = new SimpleStringProperty();
    
    public SlaveDetail(Integer Rowid, String DeviceType, Integer SlaveID, String Description ) {
        this.rowid.setValue(Rowid);
        this.DeviceType.setValue(DeviceType);
        this.ModbusSlaveID.setValue(SlaveID);
        this.LongName.setValue(Description);
    }
    
    public Integer getRowID() {
        return rowid.getValue();
    }
    
    public String getDeviceType() {
        return DeviceType.getValue();
    }  
    
    public Integer getSlaveID() {
        return ModbusSlaveID.getValue();
    }
    
    public String getDescription() {
        return LongName.getValue();
    }
}