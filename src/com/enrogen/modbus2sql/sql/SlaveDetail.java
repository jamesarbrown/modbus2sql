package com.enrogen.modbus2sql.sql;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SlaveDetail {

    private final SimpleIntegerProperty rowid = new SimpleIntegerProperty();
    private final SimpleStringProperty DeviceType = new SimpleStringProperty();
    private final SimpleIntegerProperty ModbusSlaveID = new SimpleIntegerProperty();
    private final SimpleStringProperty LongName = new SimpleStringProperty();
    private final SimpleBooleanProperty UseRS485 = new SimpleBooleanProperty();
    private final SimpleStringProperty IpAddress = new SimpleStringProperty();

    public SlaveDetail(Integer Rowid, String DeviceType, Integer SlaveID, String Description,
            Boolean useRS485, String IpAddress) {
        this.rowid.setValue(Rowid);
        this.DeviceType.setValue(DeviceType);
        this.ModbusSlaveID.setValue(SlaveID);
        this.LongName.setValue(Description);
        this.UseRS485.setValue(useRS485);
        this.IpAddress.setValue(IpAddress);
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

    public Boolean isUseRS485() {
        return UseRS485.getValue();
    }

    public String getIpAddress() {
        return IpAddress.getValue();
    }
}
