//////////////////////////////////////////////////////////////////////////
//com.enrogen.modbus2sql.intHandler.int16to16binary
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.intHandler;

public class int16to16binary {

    private int LSBint;
    private int HSBint;
    private String binaryValue;
    private boolean negValue = false;
    private int intValue;

    //This class deals with converting Long values to the correct
    //binary format before transmission to modbus registers
    //as Signed or unsigned.
    //
    //Unsigned: From 0 to 65535
    //Signed : from −32,768 to 32,767

    public int16to16binary(long bit16Integer, boolean isSigned) {
        //Test we are not being asked for an out of range signed int
        try {
            if (bit16Integer > 32767L) {
                if (isSigned) {
                    throw new intConvertorException("Number to Big for Signed 16 Bit (>32767)");
                }
            }
        } catch (intConvertorException e) {
            e.printStackTrace();
        }

        //Test we are not being asked for an out of range unsigned int
         try {
            if (bit16Integer > 65535L) {
                if (!isSigned) {
                    throw new intConvertorException("Number to Big for UnSigned 16 Bit (>65535)");
                }
            }
        } catch (intConvertorException e) {
            e.printStackTrace();
        }

        Long posInt;
        Long resultInt;

        //Do we have to deal with -ve int
        if (bit16Integer < 0) {
            negValue = true;

            //Lets make life easy, lets make it +ve
            posInt = bit16Integer * -1;
        } else {
            posInt = bit16Integer;
        }

        //Now is is a -ve symbol or 2147483648
        //negValue = MSB was 1
        if (negValue) {
            if (isSigned) {
                //-ve symbol
                binaryValue = Long.toBinaryString(posInt);

                //this will be less than 32 char
                binaryValue = padBinary(binaryValue);

                //Now as this was a neg value and signed we add the MSB
                binaryValue = "1" + binaryValue.substring(1);
            } else {
                //MSB means add 32768
                posInt = posInt + 32768L;

                //We can do this as we are storing as a 64bit signed int
                binaryValue = Long.toBinaryString(posInt);
            }
        } else {
            binaryValue = Long.toBinaryString(posInt);
        }

        binaryValue = padBinary(binaryValue);
        //System.out.println(binaryValue);
    }

    private String padBinary(String string) {
        while (string.length() < 32) {
            string = "0" + string;
        }
        return string;
    }

    public String getBinaryString() {
        return binaryValue;
    }

    public Integer getIntValue() {
        //Return a signed representation... but BITS are correct!!
        return Integer.parseInt(binaryValue, 2);
    }

    public class intConvertorException extends Exception {

        public intConvertorException(String msg) {
            super(msg);
        }
    }
}
