//////////////////////////////////////////////////////////////////////////
//com.enrogen.modbus2sql.intHandler.bit16IntHandler
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.intHandler;

public class bit16IntHandler {

    private long signedInt = 0;
    private int bitqty =16;
    private String ValueBinary;
    private long unSignedInt = 0;
    private boolean negValue = false;

    public bit16IntHandler(String bits, boolean isUnsigned) {
        ValueBinary = bits;

        try {
            if (bits.length() > bitqty) {
                throw new intConvertorException("Bits Supplied > 16bits : " + bits.length());
            }
        } catch (intConvertorException e) {
            e.printStackTrace();
        }

        //Get the MSB
        String mostSignificantBit = String.valueOf(bits.charAt(0));

        //Strip off MSB
        String binarySignedInt = bits.substring(1);
        if (mostSignificantBit.compareTo("1") == 0) {
            negValue = true;
        }

        signedInt = Long.parseLong(bits, 2);

        //Deal with sign
        if (!isUnsigned) {
            if (negValue) {
                signedInt = signedInt * -1;
            }
        } else {
            if (negValue) {
                unSignedInt = signedInt + 32767;
            } else {
                unSignedInt = signedInt;
            }
        }
    }

    public String getBinaryString() {
        return ValueBinary;
    }

    public Long getSignedIntValue() {
        return unSignedInt;
    }

    public Long getUnSignedIntValue() {
        return unSignedInt;
    }

    public class intConvertorException extends Exception {

        public intConvertorException(String msg) {
            super(msg);
        }
    }
}
