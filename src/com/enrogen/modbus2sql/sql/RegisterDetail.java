package com.enrogen.modbus2sql.sql;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RegisterDetail {

    private final SimpleIntegerProperty rowid = new SimpleIntegerProperty();
    private final SimpleIntegerProperty RegisterNo = new SimpleIntegerProperty();
    private final SimpleStringProperty DeviceType = new SimpleStringProperty();
    private final SimpleIntegerProperty PageNo = new SimpleIntegerProperty();
    private final SimpleStringProperty Description = new SimpleStringProperty();
    private final SimpleIntegerProperty Bits = new SimpleIntegerProperty();
    private final SimpleIntegerProperty Signed = new SimpleIntegerProperty();
    private final SimpleIntegerProperty LowByteRegister = new SimpleIntegerProperty();
    private final SimpleDoubleProperty ScalingFactor = new SimpleDoubleProperty();
    private final SimpleDoubleProperty MinimumValue = new SimpleDoubleProperty();
    private final SimpleDoubleProperty MaximumValue = new SimpleDoubleProperty();
    private final SimpleStringProperty Units = new SimpleStringProperty();
    private final SimpleIntegerProperty Writeable = new SimpleIntegerProperty();
    private final SimpleIntegerProperty UnimplementedValue = new SimpleIntegerProperty();

    public RegisterDetail(Integer rowid, Integer RegisterNo, String DeviceType, Integer PageNo, String Description,
            Integer Bits, Integer Signed, Integer LowByteRegister, Double ScalingFactor, Double MinimumValue,
             Double MaximumValue, String Units, Integer Writeable, Integer UnimplementedValue) {
        
        this.rowid.setValue(rowid);
        this.RegisterNo.setValue(RegisterNo);
        this.DeviceType.setValue(DeviceType);
        this.PageNo.setValue(PageNo);
        this.Description.setValue(Description);
        this.Bits.setValue(Bits);
        this.Signed.setValue(Signed);
        this.LowByteRegister.setValue(LowByteRegister);
        this.ScalingFactor.setValue(ScalingFactor);
        this.MinimumValue.setValue(MinimumValue);
        this.MaximumValue.setValue(MaximumValue);
        this.Units.setValue(Units);
        this.Writeable.setValue(Writeable);
        this.UnimplementedValue.setValue(UnimplementedValue);
    }
    
    public Integer getRowID() {
        return rowid.getValue();
    }
    
    public Integer getRegisterNo() {
        return RegisterNo.getValue();
    }
    
    public String getDeviceType() {
        return DeviceType.getValue();
    }
    
    public Integer getPageNo() {
        return PageNo.getValue();
    }
    
    public String getDescription() {
        return Description.getValue();
    }
    
    public Integer getBits() {
        return Bits.getValue();
    }
    
    public Integer getSigned() {
        return Signed.getValue();
    }
    
    public Integer getLowByteRegister() {
        return LowByteRegister.getValue();
    }
    
    public Double getScalingFactor() {
        return ScalingFactor.getValue();
    }
    
    public Double getMinimumValue() {
        return MinimumValue.getValue();
    }
    
    public Double getMaximumValue() {
        return MaximumValue.getValue();
    }
    
    public String getUnits() {
        return Units.getValue();
    }
    
    public Integer getWriteable() {
        return Writeable.getValue();
    }
    
    public Integer getUnimplementedValue() {
        return UnimplementedValue.getValue();
    }
    
}
