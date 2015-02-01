package com.enrogen.modbus2sql.sql;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AlarmDescriptor {

    private final SimpleIntegerProperty RowID = new SimpleIntegerProperty();
    private final SimpleStringProperty DeviceType = new SimpleStringProperty();
    private final SimpleStringProperty AlarmString = new SimpleStringProperty();
    private final SimpleIntegerProperty ModbusRegister = new SimpleIntegerProperty();
    private final SimpleIntegerProperty StartBit = new SimpleIntegerProperty();
    private final SimpleIntegerProperty BitQty = new SimpleIntegerProperty();
    private final SimpleIntegerProperty ValueWarning = new SimpleIntegerProperty();
    private final SimpleIntegerProperty ValueShutdown = new SimpleIntegerProperty();
    private final SimpleIntegerProperty ValueTripped = new SimpleIntegerProperty();
    private final SimpleIntegerProperty ValueUnimplemented = new SimpleIntegerProperty();
    private final SimpleIntegerProperty ValueDisabled = new SimpleIntegerProperty();

    public AlarmDescriptor() {

    }

    //Getters and Setters
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

    public String getAlarmString() {
        return AlarmString.get();
    }

    public void setAlarmString(String Description) {
        this.AlarmString.set(Description);
    }

    public Integer getModbusRegister() {
        return ModbusRegister.get();
    }

    public void setModbusRegister(Integer Register) {
        this.ModbusRegister.set(Register);
    }

    public Integer getStartBit() {
        return StartBit.get();
    }

    public void setStartBit(Integer Bit) {
        this.StartBit.set(Bit);
    }

    public Integer getBitQty() {
        return BitQty.get();
    }

    public void setBitQty(Integer Bits) {
        this.BitQty.set(Bits);
    }

    public Integer getValueWarning() {
        return ValueWarning.get();
    }

    public void setValueWarning(Integer value) {
        this.ValueWarning.set(value);
    }

    public Integer getValueShutdown() {
        return ValueShutdown.get();
    }

    public void setValueShutdown(Integer value) {
        this.ValueShutdown.set(value);
    }

    public Integer getValueTripped() {
        return ValueTripped.get();
    }

    public void setValueTripped(Integer value) {
        this.ValueTripped.set(value);
    }

    public Integer getValueDisabled() {
        return ValueDisabled.get();
    }

    public void setValueDisabled(Integer value) {
        this.ValueDisabled.set(value);
    }

    public Integer getValueUnimplemented() {
        return ValueUnimplemented.get();
    }

    public void setValueUnimplemented(Integer value) {
        this.ValueUnimplemented.set(value);
    }

}
