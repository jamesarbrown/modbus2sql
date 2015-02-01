//*************************************************************************//
//com.enrogen.genesys.logger.EgFileHandler                                 //
//Date : 04 March 2012 (Rewrite of 2010 Work - Modbus2SQL Project          //
//Desc : Fileformatter for Logging                                         //
//(c) James A R Brown 2010                                                 //
//*************************************************************************//
package com.enrogen.modbus2sql.logger;

import java.util.logging.*;

public class EgFileHandler {

    FileHandler fh1;

    public EgFileHandler(String filenamePattern, int sizelimit, int maxfiles) {
        try {
            fh1 = new FileHandler(filenamePattern, sizelimit, maxfiles);           
            fh1.setFormatter(new SimpleFormatter());
            fh1.setLevel(Level.ALL);
        } catch (Exception e) {
            System.out.println("com.enrogen.genesys.logger - Unable to Create file handler");
            e.printStackTrace();
        }
    }

    public FileHandler getFileHandler() {
        return fh1;
    }
}
