package com.enrogen.modbus2sql.sql;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RegisterBlock {
    
    private final SimpleIntegerProperty rowid = new SimpleIntegerProperty();
    private final SimpleStringProperty DeviceType = new SimpleStringProperty();
    private final SimpleIntegerProperty PageNo = new SimpleIntegerProperty();
    private final SimpleIntegerProperty RegisterStart = new SimpleIntegerProperty();
    private final SimpleIntegerProperty RegisterEnd = new SimpleIntegerProperty();
    private final SimpleStringProperty Description = new SimpleStringProperty();
    private final SimpleIntegerProperty ModbusFunctionCode = new SimpleIntegerProperty();
    
    public RegisterBlock(Integer Rowid, String DeviceType, Integer PageNo, Integer RegisterStart, Integer RegisterEnd, String Description, Integer ModbusFunctionCode ) {
        this.rowid.setValue(Rowid);
        this.DeviceType.setValue(DeviceType);
        this.PageNo.setValue(PageNo);
        this.RegisterStart.setValue(RegisterStart);
        this.RegisterEnd.setValue(RegisterEnd);
        this.Description.setValue(Description);
        this.ModbusFunctionCode.setValue(ModbusFunctionCode);
    }
    
    public Integer getRowID() {
        return rowid.getValue();
    }
    
    public String getDeviceType() {
        return DeviceType.getValue();
    }  
    
    public Integer getPageNo() {
        return PageNo.getValue();
    }
    
    public Integer getRegisterStart() {
        return RegisterStart.getValue();
    }
    
    public Integer getRegisterEnd() {
        return RegisterEnd.getValue();
    }
    
    public String getDescription() {
        return Description.getValue();
    }
 
    public Integer getModbusFunctionCode() {
        return ModbusFunctionCode.getValue();
    }
    
}
