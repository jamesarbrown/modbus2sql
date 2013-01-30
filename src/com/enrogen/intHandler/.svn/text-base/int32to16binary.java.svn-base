//////////////////////////////////////////////////////////////////////////
//com.enrogen.modbus2sql.intHandler.int32to16
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.intHandler;

public class int32to16binary {

    private int LSBint;
    private int HSBint;
    private String binaryValue;
    private boolean negValue = false;
    private int intValue;

    //This class deals with converting Long values to the correct
    //binary format before transmission to modbus registers
    //as Signed or unsigned.
    //It returns the HSB and LSB values as ints
    //
    //Unsigned: From 0 to 4,294,967,295
    //Signed : from − 2,147,483,648 to 2,147,483,647

    public int32to16binary(long bit32Integer, boolean isSigned) {
        //Test we are not being asked for an out of range signed int
        try {
            if (bit32Integer > 2147483647L) {
                if (isSigned) {
                    throw new intConvertorException("Number to Big for Signed 32 Bit (>214748647)");
                }
            }
        } catch (intConvertorException e) {
            e.printStackTrace();
        }

        //Test we are not being asked for an out of range unsigned int
         try {
            if (bit32Integer > 4294967295L) {
                if (!isSigned) {
                    throw new intConvertorException("Number to Big for UnSigned 32 Bit (>4294967295)");
                }
            }
        } catch (intConvertorException e) {
            e.printStackTrace();
        }

        Long posInt;
        Long resultInt;

        //Do we have to deal with -ve int
        if (bit32Integer < 0) {
            negValue = true;

            //Lets make life easy, lets make it +ve
            posInt = bit32Integer * -1;
        } else {
            posInt = bit32Integer;
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
                //MSB means add 2147483648
                posInt = posInt + 2147483648L;

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

    public String getHSBBinaryValue() {
        //Get the HSB
        return getBinaryString().substring(0, 16);
    }

    public int getHSBIntValue() {
        String binaryValue1 = getHSBBinaryValue();
        return Integer.valueOf(binaryValue1, 2);
    }

    public String getLSBBinaryValue() {
        //Get the LSB
        return getBinaryString().substring(16, 32);
    }

    public int getLSBIntValue() {
        String binaryValue1 = getLSBBinaryValue();
        return Integer.valueOf(binaryValue1, 2);
    }

    public class intConvertorException extends Exception {

        public intConvertorException(String msg) {
            super(msg);
        }
    }
}
