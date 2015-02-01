package com.enrogen.modbus2sql.sql;

public class RS485Detail {
    private String ComPort;
    private Integer Baud;
    private String Parity;
    private Integer DataBits;
    private Integer StopBits;
    
    public RS485Detail() {
    }
    
    public RS485Detail(String ComPort, Integer Baud, String Parity, Integer DataBits, Integer StopBits) {
        this.ComPort = ComPort;
        this.Baud = Baud;
        this.Parity = Parity;
        this.DataBits = DataBits;
        this.StopBits = StopBits;
    }
    
    //Getters and Setters
    public String getComPort() {
        return ComPort;
    }

    public void setComPort(String ComPort) {
        this.ComPort = ComPort;
    }

    public Integer getBaud() {
        return Baud;
    }

    public void setBaud(Integer Baud) {
        this.Baud = Baud;
    }

    public String getParity() {
        return Parity;
    }

    public void setParity(String Parity) {
        this.Parity = Parity;
    }

    public Integer getDataBits() {
        return DataBits;
    }

    public void setDataBits(Integer DataBits) {
        this.DataBits = DataBits;
    }

    public Integer getStopBits() {
        return StopBits;
    }

    public void setStopBits(Integer StopBits) {
        this.StopBits = StopBits;
    }
    
    
}
