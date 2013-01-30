//////////////////////////////////////////////////////////////////////////
//com.enrogen.modbus2sql.intHandler.bit32IntHandler
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.intHandler;

public class bit32IntHandler {

    private long signedInt = 0;
    private int bitqty = 32;
    private String ValueBinary;
    private long unSignedInt = 0;
    private boolean negValue = false;
    private boolean debug = false;
    private boolean stopdebug = false;

    public bit32IntHandler(String bits, boolean isUnsigned) {
        ValueBinary = bits;

        try {
            if (bits.length() > bitqty) {
                throw new intConvertorException("Bits Supplied > 32bits : " + bits.length());
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
                unSignedInt = signedInt + 2147483647;
            } else {
                unSignedInt = signedInt;
            }
        }

        if (debug) {
            if (!stopdebug) {
                System.out.println("Incoming bits : " + bits);
                System.out.println("ValueBinary : " + ValueBinary);
                System.out.println("mostSignificantBit : " + mostSignificantBit);
                System.out.println("negvalue : " + negValue);
                System.out.println("SignedInt : " + signedInt);
                System.out.println("unSignedInt : " + unSignedInt);
                stopdebug = true;
            }
        }
    }

    public String getBinaryString() {
        return ValueBinary;
    }

    public Long getSignedIntValue() {
        return signedInt;
    }

    public Long getUnSignedIntValue() {
        return unSignedInt;
    }

    public String getHSBBinaryValue() {
        //Get the HSB
        return getBinaryString().substring(0, 16);
    }

    public int getHSBIntValue() {
        String binaryValue = getHSBBinaryValue();
        return Integer.valueOf(binaryValue, 2);
    }

    public String getLSBBinaryValue() {
        //Get the LSB
        return getBinaryString().substring(16);
    }

    public int getLSBIntValue() {
        String binaryValue = getLSBBinaryValue();
        return Integer.valueOf(binaryValue, 2);
    }

    public class intConvertorException extends Exception {

        public intConvertorException(String msg) {
            super(msg);
        }
    }
}
