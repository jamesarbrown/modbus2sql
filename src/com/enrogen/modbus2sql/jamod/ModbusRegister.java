package com.enrogen.modbus2sql.jamod;

public class ModbusRegister {

    //Variables
    private boolean is32Bit;
    private boolean isSigned;
    private Integer registerNo;
    
    //A 32 bit register
    private final byte[] Register = new byte[4];

    public ModbusRegister() {

    }

    public Long getValue() {
        //We are using a long so that we can have an unsigned 32bit Integer from
        //0 to 4,294,967,295 ie the signing bit is not in our way

        if (!is32Bit) {
            //16Bit
            if (!isSigned) {
                return ((long) (Register[2] & 0xff) << 8
                        | (long) (Register[3] & 0xff));
            } else {
                //Signed Long
                if (getBit(Register[2], 7) == 1) {
                    //-ve Value
                    return (0xffffffffffffffffL << 16
                            | ((long) (Register[2] & 0xff) << 8
                            | (long) (Register[3] & 0xff)));
                } else {
                    //+ve Value
                    return ((long) (Register[2] & 0x7f) << 8
                            | (long) (Register[3] & 0xff));
                }
            }
        } else {
            //32Bit
            if (!isSigned) {
                return ((long) (Register[0] & 0xff) << 24
                        | (long) (Register[1] & 0xff) << 16
                        | (long) (Register[2] & 0xff) << 8
                        | (long) (Register[3] & 0xff));
            } else {
                //Signed Long
                if (getBit(Register[0], 7) == 1) {
                    //-ve Value
                    return (0xffffffffL << 32
                            | (long) (Register[0] & 0xff) << 24
                            | (long) (Register[1] & 0xff) << 16
                            | (long) (Register[2] & 0xff) << 8
                            | (long) (Register[3] & 0xff));
                } else {
                    //+ve Value
                    return ((long) (Register[0] & 0x7f) << 24
                            | (long) (Register[1] & 0xff) << 16
                            | (long) (Register[2] & 0xff) << 8
                            | (long) (Register[3] & 0xff));
                }
            }
        }
    }

    //32Bit Unsigned Integer
    public void setValue(Long UnsignedInteger) {
        Register[0] = (byte) (0xff & (UnsignedInteger >> 24));
        Register[1] = (byte) (0xff & (UnsignedInteger >> 16));
        Register[2] = (byte) (0xff & (UnsignedInteger >> 8));
        Register[3] = (byte) (0xff & UnsignedInteger);
    }

    public void setValue(Integer HighWord, Integer LowWord) {
        Register[0] = (byte) (0xff & (HighWord >> 8));
        Register[1] = (byte) (0xff & (HighWord));
        Register[2] = (byte) (0xff & (LowWord >> 8));
        Register[3] = (byte) (0xff & (LowWord));
    }

    public String getBinaryValue() {
        //This is Modbus... only return a 16 or 32bit string
        if (is32Bit) {
            return Long.toBinaryString(getValue()).substring(32);
        } else {
            return Long.toBinaryString(getValue()).substring(48);
        }
    }

    private int getBit(int n, int k) {
        return (n >> k) & 1;
    }

    public void setIs32Bit(Boolean is32bit) {
        this.is32Bit = is32bit;
    }

    public boolean is32Bit() {
        return is32Bit;
    }

    public void setIsSigned(Boolean isSigned) {
        this.isSigned = isSigned;
    }

    public boolean isSigned() {
        return isSigned;
    }
    
    public void setRegisterNo(Integer Register) {
        this.registerNo = Register;
    }
    
    public Integer getRegisterNo() {
        return registerNo;
    }  
}
