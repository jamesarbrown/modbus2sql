package com.enrogen.modbus2sql.sql;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DeviceType {

    private final SimpleIntegerProperty UniqueID = new SimpleIntegerProperty();
    private final SimpleStringProperty DeviceType = new SimpleStringProperty();
    private final SimpleStringProperty LongDesc = new SimpleStringProperty();

    public DeviceType(Integer ID, String DeviceType, String Desc) {
        this.UniqueID.setValue(ID);
        this.DeviceType.setValue(DeviceType);
        this.LongDesc.setValue(Desc);
    }
    
    public Integer getUniqueID() {
        return UniqueID.get();
    }

    public void setUniqueID(Integer UniqueID) {
        this.UniqueID.set(UniqueID);
    }

    public String getDeviceType() {
        return DeviceType.get();
    }

    public void setDeviceType(String DeviceType) {
        this.DeviceType.set(DeviceType);
    }

    public String getLongDesc() {
        return LongDesc.get();
    }

    public void setLongDesc(String LongDesc) {
        this.LongDesc.set(LongDesc);
    }
}
