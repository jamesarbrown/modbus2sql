package com.enrogen.modbus2sql.sql;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AlarmFlag {
        
    private final SimpleIntegerProperty RowID = new SimpleIntegerProperty();
    private final SimpleStringProperty DeviceType = new SimpleStringProperty();
    private final SimpleIntegerProperty WarningRegister = new SimpleIntegerProperty();
    private final SimpleIntegerProperty WarningBit = new SimpleIntegerProperty();
    private final SimpleIntegerProperty ShutdownRegister = new SimpleIntegerProperty();
    private final SimpleIntegerProperty ShutdownBit = new SimpleIntegerProperty();
    private final SimpleIntegerProperty TripRegister = new SimpleIntegerProperty();
    private final SimpleIntegerProperty TripBit = new SimpleIntegerProperty();
    
    public AlarmFlag() {
        
    }
    
    public AlarmFlag(Integer ID, String DeviceType, Integer WarningRegister, Integer WarningBit,
            Integer ShutdownRegister, Integer ShutdownBit, Integer TripRegister, Integer TripBit) {
        this.RowID.setValue(ID);
        this.DeviceType.setValue(DeviceType);
        this.WarningRegister.setValue(WarningRegister);
        this.WarningBit.setValue(WarningBit);
        this.ShutdownRegister.setValue(ShutdownRegister);
        this.ShutdownBit.setValue(ShutdownBit);
        this.TripRegister.setValue(TripRegister);
        this.TripBit.setValue(TripBit);
    }
    
    public Integer getUniqueID() {
        return RowID.get();
    }

    public void setUniqueID(Integer UniqueID) {
        this.RowID.set(UniqueID);
    }

    public String getDeviceType() {
        return DeviceType.get();
    }

    public void setDeviceType(String DeviceType) {
        this.DeviceType.set(DeviceType);
    }

    public Integer getWarningRegister() {
        return WarningRegister.get();
    }

    public void setWarningRegister(Integer Register) {
        this.WarningRegister.set(Register);
    }
    
    public Integer getWarningBit() {
        return WarningBit.get();
    }

    public void setWarningBit(Integer Bit) {
        this.WarningBit.set(Bit);
    }
    
    public Integer getShutdownRegister() {
        return ShutdownRegister.get();
    }

    public void setShutdownRegister(Integer Register) {
        this.ShutdownRegister.set(Register);
    }

    public Integer getShutdownBit() {
        return ShutdownBit.get();
    }

    public void setShutdownBit(Integer Bit) {
        this.ShutdownBit.set(Bit);
    }
    
    public Integer getTripRegister() {
        return TripRegister.get();
    }

    public void setTripRegister(Integer Register) {
        this.TripRegister.set(Register);
    }
    
    public Integer getTripBit() {
        return TripBit.get();
    }

    public void setTripBit(Integer Bit) {
        this.TripBit.set(Bit);
    }
}
